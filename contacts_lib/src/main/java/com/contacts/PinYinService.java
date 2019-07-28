package com.contacts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

import com.z.tinyapp.utils.common.JsonUtil;
import com.z.tinyapp.utils.logs.sLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import MCommon.EResult;
import znlp.com.ContactMgr;

/**
 * Created by sun on 2018/8/29
 */
public class PinYinService extends Service {

    private static ArrayList<PinYinModule> mModuleList = new ArrayList<>(30);

    private static File fileParent = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "tinyApp");
    private static File file = new File(fileParent.getPath() + File.separator + "data.txt");
    private static File zipFile = new File(fileParent.getPath() + File.separator + "Contacts.zip");
    private static File downFile = new File(fileParent.getPath() + File.separator + "ContactsBack.zip");
    private static File dataBackFile = new File(fileParent.getPath() + File.separator + "dataBack.txt");


    private static final String TAG = "sunHy_PinYinService";


    @Override
    public void onCreate() {
        super.onCreate();
        init();
        PinYinUtils.showLogInfo(TAG, "PinYinService -- service onCreate -- ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        PinYinUtils.showLogInfo(TAG, "PinYinService -- service onBind -- ");
        return new MBinder();
    }

    private static String phoneNumType2String(EPhoneNumType type){
        String ret = "未知";
        switch (type){
            case PHONE_NUM_TYPE_UNKNOWN:
                ret = "未知";
                break;
            case PHONE_NUM_TYPE_HOME:
                ret = "家";
                break;
            case PHONE_NUM_TYPE_WORK:
                ret = "工作";
                break;
            case PHONE_NUM_TYPE_MOBILE:
                ret = "手机";
                break;
            case PHONE_NUM_TYPE_MOBILE_WORK:
                ret = "工作手机";
                break;
            case PHONE_NUM_TYPE_FAX:
                ret = "传真";
                break;
            case PHONE_NUM_TYPE_PAGER:
                ret = "传呼";
                break;
            case PHONE_NUM_TYPE_OTHER:
                ret = "其他";
                break;
        }
        return ret;
    }
    private static EPhoneNumType phoneNumType2EPhoneNumType(String type) {
        EPhoneNumType ret = EPhoneNumType.PHONE_NUM_TYPE_UNKNOWN;
        switch (type){
            case "未知":
                ret = EPhoneNumType.PHONE_NUM_TYPE_UNKNOWN;
                break;
            case "家":
                ret = EPhoneNumType.PHONE_NUM_TYPE_HOME;
                break;
            case "工作":
                ret = EPhoneNumType.PHONE_NUM_TYPE_WORK;
                break;
            case "手机":
                ret = EPhoneNumType.PHONE_NUM_TYPE_MOBILE;
                break;
            case "工作手机":
                ret = EPhoneNumType.PHONE_NUM_TYPE_MOBILE_WORK;
                break;
            case "传真":
                ret = EPhoneNumType.PHONE_NUM_TYPE_FAX;
                break;
            case "传呼":
                ret = EPhoneNumType.PHONE_NUM_TYPE_PAGER;
                break;
            case "其他":
                ret = EPhoneNumType.PHONE_NUM_TYPE_OTHER;
                break;
        }
        return ret;
    }

    public static String getEncoding(String str)
    {
        String encode;

        encode = "UTF-16";
        try
        {
            if(str.equals(new String(str.getBytes(), encode)))
        {
            return "<< " + str + " >> "+encode;
        }
        }
        catch(Exception ex) {}

        encode = "ASCII";
        try
        {
            if(str.equals(new String(str.getBytes(), encode)))
        {
            return "<< " + str + " >> ASCII";
        }
        }
        catch(Exception ex) {}

        encode = "ISO-8859-1";
        try
        {
            if(str.equals(new String(str.getBytes(), encode)))
        {
            return "<< " + str + " >> "+encode;
        }
        }
        catch(Exception ex) {}

        encode = "GB2312";
        try
        {
            if(str.equals(new String(str.getBytes(), encode)))
        {
            return "<< " + str + " >> "+encode;
        }
        }
        catch(Exception ex) {}

        encode = "UTF-8";
        try
        {
            if(str.equals(new String(str.getBytes(), encode)))
	    {
            return "<< " + str + " >> "+encode;
        }
        }
        catch(Exception ex) {}

        /*
         *......待完善
         */

        return "未识别编码格式";
    }

    static void read() {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            /* 读入TXT文件 */
            String pathname = "/data/local/tmp/vr/contact.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void write(String str) {
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw


            /* 写入Txt文件 */
            File writename = new File("/data/local/tmp/vr/contact.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(str);
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void onUpdateContacts_new(final Map<Integer, ContactListInfo> map) {
        synchronized (PinYinService.class)
        {
            if (!ContactMgr.inst().isInited())
            {
                ContactMgr.inst().setInited(true);
                JSONObject contactInfo = new JSONObject();
                JSONArray contacts = new JSONArray();
                //List<PinYinModule> list = new ArrayList<>();
                Iterator<Map.Entry<Integer, ContactListInfo>> iterator = map.entrySet().iterator();
                //PinYinModule module;
                PinYinUtils.showLogInfo(TAG, "---size--->" + map.size());

                JSONArray conts = new JSONArray();

                try {
                    while (iterator.hasNext()) {
                        Map.Entry<Integer, ContactListInfo> mEntry = iterator.next();
                        ContactListInfo value = mEntry.getValue();

                        //module = new PinYinModule(value.contactNameFirst + value.contactNameLast);
                        EResult.Ref detailRes = new EResult.Ref(EResult.EFailure);
                        ContactDetailInfo detailInfoOne = new ContactDetailInfo();
                        try {
                            IBluetooth.Inst().GetContactDetailInfo(detailRes, value.contactID, detailInfoOne);
                        } catch (RpcError rpcError) {
                            PinYinUtils.showLogInfo(TAG,"error IBluetooth.Inst().GetContactDetailInfo");
                        }
                        JSONObject contact = new JSONObject();
                        contact.put("contactNameFirst", value.contactNameFirst);
                        contact.put("contactNameLast", value.contactNameLast);
                        contact.put("contactID", value.contactID);
                        JSONArray telNums = new JSONArray();

                        JSONObject cont = new JSONObject();
                        cont.put("name", value.contactID);
                        JSONArray tels = new JSONArray();

                        //sLog.i(null, "fl_name coding:" + getEncoding(value.contactNameFirst) + " " + getEncoding(value.contactNameLast));

                        Map<Integer, TelephoneNum> detailGetTel = detailInfoOne.telNumList;
                        PinYinModule.PhoneNumInfo phoneNumInfo;
                        for (Integer i : detailGetTel.keySet()) {

                            //phoneNumInfo = new PinYinModule.PhoneNumInfo();
                            //phoneNumInfo.setTel(detailGetTel.get(i).number);
                            //phoneNumInfo.setType(detailGetTel.get(i).type.value());
                            //module.addPhoneInfo(phoneNumInfo);

                            JSONObject tel = new JSONObject();
                            tel.put("number", detailGetTel.get(i).number);
                            tel.put("type", phoneNumType2String(detailGetTel.get(i).type));
                            tel.put("carrierOperator", "");
                            tel.put("province", "");
                            tel.put("city", "");
                            telNums.put(tel);

                            JSONObject t = new JSONObject();
                            t.put("tel", detailGetTel.get(i).number);
                            t.put("carrierOperator", "");
                            t.put("province", "");
                            t.put("city", "");
                            tels.put(t);
                            //sLog.i(null, "number_type_name coding:" + getEncoding(detailGetTel.get(i).number) + " " + getEncoding(phoneNumType2String(detailGetTel.get(i).type)));
                        }
                        contact.put("telNums", telNums);
                        //list.add(module);
                        contacts.put(contact);
                        //sLog.i(null, "contact:loop");

                        cont.put("telNums", tels);
                        conts.put(cont);
                    }
                    contacts = getContsDetial(contacts, conts.toString());
                    contactInfo.put("contacts", contacts);
                    sLog.i(null, "contactInfo len:"+contacts.length());
                    String str = contactInfo.toString();
                    write(str);
                    sLog.i(null, "contactInfo strlen:"+str.length());
                    ContactMgr.inst().init(str);
                    sLog.i(null, "contact: init end");

                    /*
                    {
                        "name": "蒋佳龙",
                        "telNums": [
                            {
                                "tel": "13238828990",
                                "carrierOperator": "联通", // 需要填充
                                "province": "辽宁", // 需要填充
                                "city": "沈阳" // 需要填充
                            },
                            {
                                "tel": "13238828991",
                                "carrierOperator": "",
                                "province": "",
                                "city": ""
                            }
                        ]
                    }
                     */


                } catch (JSONException e) {
                    e.printStackTrace();
                    sLog.i(null, "contact:error");
                }

                //String findjson = ContactMgr.inst().find("{\"name\":\"东\"}");
                //sLog.i(null, "contact.find"+findjson);
            }}
    }

    /**
     * 更新通讯录
     */
    public static void onUpdateContacts(final Map<Integer, ContactListInfo> map){
        onUpdateContacts_new(map);
    }

    private static JSONArray getContsDetial(JSONArray contacts, String str){
        boolean a = false;

        if (!fileParent.exists()) {
            a = fileParent.mkdirs();
            sLog.e(TAG, "onUpdateContacts: fileParent.mkdirs()" + a);
        }

        if (downFile.exists()) {
            a = downFile.delete();
            sLog.e(TAG, "onUpdateContacts: downFile delete-->" + a);
        }
        try {
            a = downFile.createNewFile();
        } catch (IOException e) {
            sLog.e(TAG, "onUpdateContacts: -->downFile.createNewFile()-->" + a);
        }

        if (file.exists()) {
            a = file.delete();
            sLog.e(TAG, "onUpdateContacts: file delete-->" + a);
        }

        try {
            a = file.getParentFile().mkdir();
            sLog.e(TAG, "onUpdateContacts: -->file.getParentFile().mkdir()-->" + a);
            a = file.createNewFile();
            sLog.e(TAG, "onUpdateContacts: -->file.createNewFile()-->" + a);
        } catch (IOException e) {
            sLog.e(TAG, "onUpdateContacts: 创建txt文件失败", e);
        }

        if (zipFile.exists()) {
            a = zipFile.delete();
            sLog.e(TAG, "onUpdateContacts: zipFile delete-->" + a);
        }
        try {
            a = zipFile.getParentFile().mkdir();
            sLog.e(TAG, "onUpdateContacts: -->zipFile.getParentFile().mkdir()" + a);
            a = zipFile.createNewFile();
            sLog.e(TAG, "onUpdateContacts: -->zipFile.createNewFile()" + a);

        } catch (IOException e) {
            sLog.e(TAG, "onUpdateContacts: 创建zip文件失败", e);
        }

        if (dataBackFile.exists()) {
            a = dataBackFile.delete();
            sLog.e(TAG, "onUpdateContacts: dataBackFile delete-->" + a);
        }
        try {
            a = dataBackFile.getParentFile().mkdir();
            sLog.e(TAG, "onUpdateContacts: -->dataBackFile.getParentFile().mkdir()" + a);
            a = dataBackFile.createNewFile();
            sLog.e(TAG, "onUpdateContacts: -->dataBackFile.createNewFile()" + a);

        } catch (IOException e) {
            sLog.e(TAG, "onUpdateContacts: 创建zip文件失败", e);
        }

        try {
            //sLog.i(null, "getTelsDetail post:" + str);
            PinYinUtils.write2Txt(str, file);
            PinYinUtils.zip(file, zipFile);
            PinYinUtils.upload(zipFile, downFile);
            PinYinUtils.unZipFolder(downFile, dataBackFile);
            String text = PinYinUtils.readFromDataStrBack(dataBackFile);
            //sLog.i(null, "getTelsDetail get:" + text);
            JSONArray conts = new JSONArray(text);
            int len = contacts.length();
            if (len == conts.length()) {
                for (int i = 0; i < len; ++ i) {
                    JSONObject ct =  contacts.getJSONObject(i);
                    JSONObject cs =  conts.getJSONObject(i);
                    if (ct.getInt("contactID") == cs.getInt("name")){
                        JSONArray ttels = ct.getJSONArray("telNums");
                        JSONArray stels = cs.getJSONArray("telNums");
                        int sz = ttels.length();
                        if (sz == stels.length()){
                            for (int m = 0; m < sz; ++ m){
                                JSONObject t = ttels.getJSONObject(m);
                                JSONObject s = stels.getJSONObject(m);
                                t.put("carrierOperator", s.getString("carrierOperator"));
                                t.put("province", s.getString("province"));
                                t.put("city", s.getString("city"));
                            }
                        }
                    }

                }
            }

        }catch (JSONException e) {
            sLog.e(TAG, "onUpdateContacts: IOException", e);
        } catch (IOException e) {
            sLog.e(TAG, "onUpdateContacts: IOException", e);
        } catch (Exception e) {
            sLog.e(TAG, "onUpdateContacts: Exception", e);
        }

        return contacts;
    }


    @SuppressWarnings("unused")
    public class MBinder extends Binder {

        /**
         * 通过姓名排序
         */
        public List<Contact> searchContacts(String name) {
            return PinYinService.searchContacts(name);
        }

        /**
         * 通过姓名排序
         */
        public List<Contact> searchContacts(String name,String flag) {
            return PinYinService.searchContacts(name);
        }

        /**
         * 通过姓名首字母排序
         */
        ArrayList<PinYinModule> compareContacts(Map<Integer, ContactListInfo> map) {
            return PinYinService.compareContacts();
        }

        /**
         * 获取从 通讯录获取的联系人列表(无排序)
         */
        //public ArrayList<PinYinModule> getContactsList() {
        //    return PinYinService.this.getContactsList();
        //}

        /**
         * 打电话
         */
        public void callPhone(Context ctx, String num) {

            // 使用意图来打电话

            Intent intent = new Intent();

            // 设置动作:做什么事情

            // Intent.ACTION_CALL:打电话

            // Intent.ACTION_VIEW把电话号码放入系统默认的打电话的程序

            intent.setAction("android.intent.action.mycall");

            // 设置数据:电话号码

            // java.net.URI

            // android.net.Uri

            // tel:前缀 固定格式

            Uri data = Uri.parse("tel:" + num);

            // Uri data = Uri.parse("http://wap.baidu.com");

            intent.setData(data);

            // 发出请求:把当前的PhoneActivity变成系统的打电话的Activity

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ctx.startActivity(intent);
        }

    }

    void init() {
        if (!file.exists()) {
            boolean a;
            try {
                a = file.getParentFile().mkdir();
                sLog.e(TAG, "init: -->getParentFile().mkdir()" + a);
                a = file.createNewFile();
                sLog.e(TAG, "init: -->file.createNewFile()" + a);
            } catch (IOException e) {
                sLog.e(TAG, "init: 创建txt文件失败", e);
            }


        }
        if (!zipFile.exists()) {
            boolean a;
            try {
                a = zipFile.getParentFile().mkdir();
                sLog.e(TAG, "init: -->zipFile.getParentFile().mkdir()" + a);
                a = zipFile.createNewFile();
                sLog.e(TAG, "init: -->zipFile.createNewFile()" + a);

            } catch (IOException e) {
                sLog.e(TAG, "init: 创建zip文件失败", e);
            }
        }

//
//        PinYinModule module = new PinYinModule("张一三");
//        PinYinModule module2 = new PinYinModule("张一二");
//        PinYinModule module3 = new PinYinModule("张一一");
//        PinYinModule module4 = new PinYinModule("张一五");
//
//        PinYinModule.PhoneNumInfo info = new PinYinModule.PhoneNumInfo();
//        info.setTel("19805004161");
//        PinYinModule.PhoneNumInfo info4 = new PinYinModule.PhoneNumInfo();
//        info4.setTel("19805004161");
//        module.addPhoneInfo(info);
//        module.addPhoneInfo(info4);
//
//        PinYinModule.PhoneNumInfo info1 = new PinYinModule.PhoneNumInfo();
//        info1.setTel("19805004161");
//        module2.addPhoneInfo(info1);
//
//        PinYinModule.PhoneNumInfo info2 = new PinYinModule.PhoneNumInfo();
//        info2.setTel("19805004161");
//        module3.addPhoneInfo(info2);
//
//        PinYinModule.PhoneNumInfo info3 = new PinYinModule.PhoneNumInfo();
//        info3.setTel("19805004161");
//        module4.addPhoneInfo(info3);
//
//        mModuleList.add(module);
//        mModuleList.add(module2);
//        mModuleList.add(module3);
//        mModuleList.add(module4);
    }

    public static List<Contact> searchContacts(String name){
        String foundJsonStr = ContactMgr.inst().find("{\"name\":\""+name+"\"}");
        sLog.i(null, "find:"+foundJsonStr);
        return JsonUtil.parseList(foundJsonStr, Contact.class);
    }



    public static List<Contact> searchContacts(String name, String flag){
        return searchContacts(name);
    }

    public static List<Contact> searchContacts(String name,  String userPhoneType, String userPhoneLoc, String userSubNum){
        userPhoneType = normalizeSlots(userPhoneType);
        userPhoneLoc = normalizeSlots(userPhoneLoc);
        userSubNum = normalizeSlots(userSubNum);

        String carrierOperator = null;
        String type = null;
        String province = userPhoneLoc;
        String city = userPhoneLoc;

        if (null != userPhoneType){
            if (userPhoneType.indexOf("移动")>=0){
                carrierOperator = "移动";
            } else if (userPhoneType.indexOf("联通")>=0){
                carrierOperator = "联通";
            } else if (userPhoneType.indexOf("电信")>=0){
                carrierOperator = "电信";
            } else {
                type = userPhoneType;
            }
        }

        return searchContacts(name, type, carrierOperator, province, city, userSubNum);
    }

    private static String normalizeSlots(String text){
        if (text!= null  && "none".equals(text))
            return null;
        return text;
    }
    private static List<Contact> searchContacts(String name,  String type,
                                               String carrierOperator, String province, String city,
                                               String subNum) {

        List<Contact> oris = searchContacts(name);
        List<Contact> rets = mergingContact(oris);
        rets = matchContact(rets, name, type, carrierOperator, province, city, subNum);
        Collections.sort(rets);
        oris = null;
        return rets;
    }

    private static boolean matchStr(String a, String b){
        if (null != a && !a.isEmpty() && null != b && !b.isEmpty()) {
            if(a.indexOf(b)>=0 || b.indexOf(a)>=0){
                return  true;
            } else {
                return false;
            }
        } else if (null == a || a.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    /**

    /**
     * 根据首字母排序
     */
    public static ArrayList<PinYinModule> compareContacts() {
        return PinYinUtils.compareSortList(mModuleList);
    }

    private static List<Contact> matchContact(List<Contact> oris, String name,  String type,
                                   String carrierOperator, String province, String city,
                                   String subNum){
        List<Contact> rets = new ArrayList<>();
        int rawNameLenP100 = name.length()*100;
        Contact fullCont = null;
        int full_count = 0;
        for (Contact cont: oris){
            List<TelNumber> tels = cont.getTelNums();
            Contact cont1 = null;
            List<TelNumber> tels1 = null;
            for (TelNumber tel: tels){
                boolean bSubNum = true;
                boolean bType = matchStr(type, tel.getType());
                boolean bCarrierOperator = matchStr(carrierOperator, tel.getCarrierOperator());
                boolean bProvince = matchStr(province, tel.getProvince());
                boolean bCity = matchStr(city, tel.getCity());
                if (null == tel.getNumber()) bSubNum = false;
                if (null != subNum && !subNum.isEmpty()) {
                    if (tel.getNumber().indexOf(subNum) >= 0) {
                        bSubNum = true;
                    } else {
                        bSubNum = false;
                    }
                }
                if (bSubNum && bType && bCarrierOperator && bProvince && bCity) {
                    if (null == cont1) {
                        cont1 = new Contact();
                        tels1 = new ArrayList<>();
                        cont1.setContactID(cont.getContactID());
                        cont1.setContactNameFirst(cont.getContactNameFirst());
                        cont1.setContactNameLast(cont.getContactNameLast());

                        int oriNameLen = (cont1.getContactNameFirst()+cont1.getContactNameLast()).length();
                        int matchedDe = 0;
                        if(oriNameLen>0){
                            matchedDe = rawNameLenP100/(oriNameLen);
                            if (matchedDe > 100)
                                matchedDe = 100;
                        }
                        cont1.setMatchedDegree(matchedDe);
                    }
                    tels1.add(tel);
                }
            }
            if (null != cont1 && null != tels1){
                cont1.setTelNums(tels1);
                rets.add(cont1);
                String cntname = cont.getContactNameFirst()+cont.getContactNameLast();
                if (cntname.length() == name.length()){
                    fullCont = cont1;
                    full_count ++;
                    cont1.setMatchedDegree(100);
                }
            }
        }

        if (full_count == 1 && fullCont != null){
            rets = null;
            rets = new ArrayList<>();
            rets.add(fullCont);
        }

        return rets;
    }

    private static List<Contact> mergingContact(List<Contact> orig){
        Map<String, Contact> map = new HashMap<>();
        int len = orig.size();
        boolean []rs = new boolean[len];
        for (int i = 0; i < len; ++ i){
            Contact cont = orig.get(i);
            String name = cont.getContactNameFirst() + cont.getContactNameLast();
            Contact c = map.get(name);
            if (c == null){
                rs[i] = false;
                map.put(name, cont);
            } else {
                rs[i] = true;
                for (TelNumber t: cont.getTelNums()){
                    c.getTelNums().add(t);
                }
            }
        }
        for (int j = len-1; j >= 0; -- j){
            if (rs[j]){
                orig.remove(j);
            }
        }
        return orig;
    }



    //ArrayList<PinYinModule> getContactsList() {
    //    return mModuleList;
    //}

    @Override
    public void onDestroy() {
        super.onDestroy();
        PinYinUtils.showLogInfo(TAG, "PinYinService -- service onDestroy -- ");
        mModuleList = null;
        mModuleList = null;
    }
}
