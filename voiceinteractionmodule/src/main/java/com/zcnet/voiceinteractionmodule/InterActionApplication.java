package com.zcnet.voiceinteractionmodule;

import android.content.Context;
import android.os.Environment;

import com.z.tinyapp.utils.logs.sLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import znlp.com.ModelMgr;

/**
 * Created by sun on 2019/3/20
 */

public class InterActionApplication {

    private static final String TAG = "sunInterAction";

    static String getConfig() {
        String config = "";

        String path = Environment.getExternalStorageDirectory() + "/vr_module/config/config";

        FileInputStream inputStream;

        if (!new File(path).exists()) {
            sLog.e(TAG, "getConfig: config file not exists  -> " + path);
            return "";
        }

        try {
            inputStream = new FileInputStream(path);

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            config = result.toString("UTF-8");
        } catch (FileNotFoundException e) {
            sLog.e(TAG, "getConfig: FileNotFoundException", e);
        } catch (UnsupportedEncodingException e) {
            sLog.e(TAG, "getConfig: UnsupportedEncodingException", e);
        } catch (IOException e) {
            sLog.e(TAG, "getConfig: IOException", e);
        }

        return config;
    }

    private  static  byte[] readFileToByteArray(String path) {
        File file = new File(path);
        FileInputStream in = null;
        if(!file.exists()) {
            sLog.e(TAG,"File doesn't exist!");
            return null;
        }
        try {
            in = new FileInputStream(file);
            long inSize = in.getChannel().size();//判断FileInputStream中是否有内容
            if (inSize == 0) {
                sLog.d(TAG,"The FileInputStream has no content!");
                return null;
            }

            byte[] buffer = new byte[in.available()];//in.available() 表示要读取的文件中的数据长度
            in.read(buffer);  //将文件中的数据读到buffer中
            return buffer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(null != in)
                    in.close();
            } catch (IOException e) {
                return null;
            }
            //或IoUtils.closeQuietly(in);
        }
    }

    private static byte[] readNLP(Context ct) {
        InputStream is = null;
        byte[] bytes = null;
        try {

            is = ct.getAssets().open("nlp.dat");
            bytes = new byte[is.available()];
            is.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }



    public static void init(Context ct) {
        /*String filelist[] = {
                "/data/local/tmp/vr/common/common_main.txt"
                ,"/data/local/tmp/vr/phone/nlpmain.txt"
                , "/data/local/tmp/vr/navi/navi_main.txt"

        };

        ZParser p = new ZParser();
        int ret = p.parse(filelist);
//        sLog.e(TAG, "Parser ret:" + ret);

        //int ret = ModelMgr.inst().addModel("/data/local/tmp/vr/phone/nlp.dat");
        sLog.e(TAG, "ModeMgr init ret -> " + ret);
        if (0 == ret) {
            ret = p.save("/data/local/tmp/vr/phone/nlp.dat");
            sLog.e(TAG, "addMemoryModel save ret:" + ret);
            //ret = ModelMgr.inst().addMemoryModel(p.header());//add_memory_model((void*)p.header());
            //ret = ModelMgr.inst().addModel("/data/local/tmp/vr/phone/nlp.dat");
            ret = ModelMgr.inst().addBufferModel(readFileToByteArray("/data/local/tmp/vr/phone/nlp.dat"));
            sLog.e(TAG, "addMemoryModel ret:" + ret);
        }*/
        byte[] zlpbuf = readNLP(ct);
        if (null != zlpbuf){
            int ret = ModelMgr.inst().addBufferModel(zlpbuf);
            sLog.e(TAG, "ModeMgr init ret -> " + ret);
        } else {
            sLog.e(TAG, "ModeMgr init ret -> asset nlp.dat error");
        }


       // String contactJson = "{\"contacts\":[{\"contactNameFirst\":\"\",\"contactNameLast\":\"单政肖\",\"contactID\":166,\"telNums\":[{\"number\":\"13052016792\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"张明付\",\"contactID\":196,\"telNums\":[{\"number\":\"18117366973\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"廖云竹\",\"contactID\":208,\"telNums\":[{\"number\":\"13671729862\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"沛沛的手表\",\"contactID\":183,\"telNums\":[{\"number\":\"13198807925\",\"type\":\"其他\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"余美红\",\"contactID\":102,\"telNums\":[{\"number\":\"18227732325\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"假日半岛物业\",\"contactID\":55,\"telNums\":[{\"number\":\"08332106565\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"姜明远\",\"contactID\":62,\"telNums\":[{\"number\":\"15504286322\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王敏delphi\",\"contactID\":52,\"telNums\":[{\"number\":\"18602150446\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王佳洪\",\"contactID\":88,\"telNums\":[{\"number\":\"13395815505\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"郑天堂\",\"contactID\":132,\"telNums\":[{\"number\":\"13816820780\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"吴元春\",\"contactID\":11,\"telNums\":[{\"number\":\"13818973286\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"莉容\",\"contactID\":78,\"telNums\":[{\"number\":\"15821929291\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞权芳\",\"contactID\":216,\"telNums\":[{\"number\":\"13990002886\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"黄长永\",\"contactID\":133,\"telNums\":[{\"number\":\"18721658872\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"李志洋\",\"contactID\":108,\"telNums\":[{\"number\":\"15002166775\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"涂江威\",\"contactID\":159,\"telNums\":[{\"number\":\"15183389599\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"祝华\",\"contactID\":112,\"telNums\":[{\"number\":\"15913305899\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"周剑鸣\",\"contactID\":33,\"telNums\":[{\"number\":\"13817638790\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"石照荣\",\"contactID\":74,\"telNums\":[{\"number\":\"13890020336\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"李慧兵\",\"contactID\":69,\"telNums\":[{\"number\":\"2128967807\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"吴庆\",\"contactID\":73,\"telNums\":[{\"number\":\"13540107928\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"欧阳能钧\",\"contactID\":49,\"telNums\":[{\"number\":\"18823338450\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"孟轩\",\"contactID\":171,\"telNums\":[{\"number\":\"17683131393\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"鹿朋\",\"contactID\":93,\"telNums\":[{\"number\":\"18616209809\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈少越\",\"contactID\":70,\"telNums\":[{\"number\":\"15618206152\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"何老师-化学\",\"contactID\":59,\"telNums\":[{\"number\":\"13795557845\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"董本富\",\"contactID\":87,\"telNums\":[{\"number\":\"18681396885\",\"type\":\"手机\"},{\"number\":\"13890011605\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"祝维华\",\"contactID\":23,\"telNums\":[{\"number\":\"13096007681\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"李奇\",\"contactID\":48,\"telNums\":[{\"number\":\"13482292604\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王宇乾\",\"contactID\":157,\"telNums\":[{\"number\":\"13661657435\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"何永强\",\"contactID\":91,\"telNums\":[{\"number\":\"13701760717\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"孙宇宙\",\"contactID\":192,\"telNums\":[{\"number\":\"15283368604\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王鹏军\",\"contactID\":199,\"telNums\":[{\"number\":\"13817049240\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"程映老师\",\"contactID\":60,\"telNums\":[{\"number\":\"13568322019\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"谭元丰\",\"contactID\":47,\"telNums\":[{\"number\":\"13761621793\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"景玉军\",\"contactID\":136,\"telNums\":[{\"number\":\"15901735160\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"段兵\",\"contactID\":54,\"telNums\":[{\"number\":\"15301696156\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"周忠平\",\"contactID\":3,\"telNums\":[{\"number\":\"15008143740\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"何老师-生物\",\"contactID\":130,\"telNums\":[{\"number\":\"15298203043\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"肖龙\",\"contactID\":44,\"telNums\":[{\"number\":\"15890871896\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"杜婷\",\"contactID\":193,\"telNums\":[{\"number\":\"18081329737\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"康教练\",\"contactID\":172,\"telNums\":[{\"number\":\"13558918800\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"杨大哥\",\"contactID\":25,\"telNums\":[{\"number\":\"13890088058\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"邓红\",\"contactID\":118,\"telNums\":[{\"number\":\"18280731709\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"杨俊\",\"contactID\":195,\"telNums\":[{\"number\":\"13699459975\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"吴轩文\",\"contactID\":9,\"telNums\":[{\"number\":\"15990060908\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"钉钉官方短信\",\"contactID\":114,\"telNums\":[{\"number\":\"106590256203144\",\"type\":\"手机\"},{\"number\":\"1065752551629604916\",\"type\":\"手机\"},{\"number\":\"106575258192144\",\"type\":\"手机\"},{\"number\":\"106906199604916\",\"type\":\"手机\"},{\"number\":\"10655059113144\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"祝波\",\"contactID\":144,\"telNums\":[{\"number\":\"13528380688\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈光荣\",\"contactID\":111,\"telNums\":[{\"number\":\"13990018908\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞明莉\",\"contactID\":14,\"telNums\":[{\"number\":\"13541388445\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞杰芳\",\"contactID\":194,\"telNums\":[{\"number\":\"19969153426\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王砚龙\",\"contactID\":63,\"telNums\":[{\"number\":\"17317906516\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王志杰\",\"contactID\":117,\"telNums\":[{\"number\":\"13162423233\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"余学涛\",\"contactID\":46,\"telNums\":[{\"number\":\"18621368560\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"平安车险\",\"contactID\":19,\"telNums\":[{\"number\":\"08135689382\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"沈小聪\",\"contactID\":191,\"telNums\":[{\"number\":\"15808235276\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"孟鹏\",\"contactID\":13,\"telNums\":[{\"number\":\"13558807224\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"周老师-物理\",\"contactID\":101,\"telNums\":[{\"number\":\"13629020843\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"幸秀洁\",\"contactID\":104,\"telNums\":[{\"number\":\"15328663382\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"卿惠老师\",\"contactID\":98,\"telNums\":[{\"number\":\"18281309998\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"龚东东\",\"contactID\":165,\"telNums\":[{\"number\":\"15058813724\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"四娘\",\"contactID\":4,\"telNums\":[{\"number\":\"18990059056\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"何先生\",\"contactID\":198,\"telNums\":[{\"number\":\"15281390869\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"黄世敏\",\"contactID\":128,\"telNums\":[{\"number\":\"18981369108\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"邓荣根\",\"contactID\":12,\"telNums\":[{\"number\":\"15988446154\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"租车位\",\"contactID\":174,\"telNums\":[{\"number\":\"13628198207\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈琳\",\"contactID\":30,\"telNums\":[{\"number\":\"18521063893\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞孟函\",\"contactID\":20,\"telNums\":[{\"number\":\"18183599918\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"周师傅\",\"contactID\":190,\"telNums\":[{\"number\":\"13541655715\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈章亮\",\"contactID\":158,\"telNums\":[{\"number\":\"18681339666\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王宝\",\"contactID\":154,\"telNums\":[{\"number\":\"18616771546\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"邓国康\",\"contactID\":173,\"telNums\":[{\"number\":\"13795593088\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"护工夜\",\"contactID\":156,\"telNums\":[{\"number\":\"13320742450\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"何昕\",\"contactID\":123,\"telNums\":[{\"number\":\"18602170211\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"胡奕\",\"contactID\":212,\"telNums\":[{\"number\":\"13916252467\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞大哥-出租\",\"contactID\":206,\"telNums\":[{\"number\":\"13980228788\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"沈桂容\",\"contactID\":204,\"telNums\":[{\"number\":\"18095042886\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"张耀文\",\"contactID\":163,\"telNums\":[{\"number\":\"13778500658\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"妮海斌\",\"contactID\":197,\"telNums\":[{\"number\":\"15618200096\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"吴发群\",\"contactID\":188,\"telNums\":[{\"number\":\"13619023130\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"张钰\",\"contactID\":7,\"telNums\":[{\"number\":\"13524621258\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"我的名字\",\"contactNameLast\":\"\",\"contactID\":1,\"telNums\":[]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"刘馨妈妈\",\"contactID\":145,\"telNums\":[{\"number\":\"18008145460\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"朱琳平\",\"contactID\":131,\"telNums\":[{\"number\":\"13852528966\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"motherow\",\"contactID\":32,\"telNums\":[{\"number\":\"13036419160\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"黄德斌\",\"contactID\":160,\"telNums\":[{\"number\":\"13023101720\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈烈星\",\"contactID\":120,\"telNums\":[{\"number\":\"13990058180\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"彭青松\",\"contactID\":201,\"telNums\":[{\"number\":\"13916843168\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"黄丰彪\",\"contactID\":72,\"telNums\":[{\"number\":\"13636470142\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"刘超\",\"contactID\":77,\"telNums\":[{\"number\":\"13438048554\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈沛\",\"contactID\":134,\"telNums\":[{\"number\":\"13729007873\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"贺晔伟\",\"contactID\":5,\"telNums\":[{\"number\":\"13512105192\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"李书昌\",\"contactID\":207,\"telNums\":[{\"number\":\"13166294615\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"刘献兵\",\"contactID\":82,\"telNums\":[{\"number\":\"15921678925\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"张伦\",\"contactID\":27,\"telNums\":[{\"number\":\"13881588924\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"朱教练\",\"contactID\":147,\"telNums\":[{\"number\":\"15881327072\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"郑姑爷\",\"contactID\":65,\"telNums\":[{\"number\":\"13568114987\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞召辉\",\"contactID\":100,\"telNums\":[{\"number\":\"13219668573\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王宇乾\",\"contactID\":110,\"telNums\":[{\"number\":\"18942626668\",\"type\":\"手机\"},{\"number\":\"17717016438\",\"type\":\"家\"},{\"number\":\"17317975729\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"林芸好\",\"contactID\":39,\"telNums\":[{\"number\":\"13478404620\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"A-冷冻室电话\",\"contactID\":148,\"telNums\":[{\"number\":\"08303165402\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"吴恩伟\",\"contactID\":41,\"telNums\":[{\"number\":\"13093112191\",\"type\":\"手机\"},{\"number\":\"18613895681\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"有缘信息部\",\"contactID\":164,\"telNums\":[{\"number\":\"13619024792\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"祁味抄手\",\"contactID\":189,\"telNums\":[{\"number\":\"08136290080\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"朱姝\",\"contactID\":155,\"telNums\":[{\"number\":\"18681337362\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞权芳家\",\"contactID\":138,\"telNums\":[{\"number\":\"6107897\",\"type\":\"家\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞勇明\",\"contactID\":119,\"telNums\":[{\"number\":\"13431218101\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"古仕文\",\"contactID\":18,\"telNums\":[{\"number\":\"18606886481\",\"type\":\"手机\"},{\"number\":\"18980446606\",\"type\":\"家\"},{\"number\":\"13951795595\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"佟嘉男\",\"contactID\":79,\"telNums\":[{\"number\":\"13795394885\",\"type\":\"手机\"},{\"number\":\"15504248737\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"赵飞然\",\"contactID\":113,\"telNums\":[{\"number\":\"02128967763\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞桂英\",\"contactID\":92,\"telNums\":[{\"number\":\"13575758152\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"丁广俊\",\"contactID\":126,\"telNums\":[{\"number\":\"18502125898\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"潘强\",\"contactID\":45,\"telNums\":[{\"number\":\"13983917109\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"邹玉仙\",\"contactID\":76,\"telNums\":[{\"number\":\"15008100771\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"戴志军\",\"contactID\":152,\"telNums\":[{\"number\":\"13774480760\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"幺母舅\",\"contactID\":177,\"telNums\":[{\"number\":\"15168216321\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"徐洪-安居客\",\"contactID\":149,\"telNums\":[{\"number\":\"17723183386\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"孟建\",\"contactID\":176,\"telNums\":[{\"number\":\"17621958581\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"范长青\",\"contactID\":34,\"telNums\":[{\"number\":\"13678226692\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"夏老师\",\"contactID\":22,\"telNums\":[{\"number\":\"13990068166\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"钟淑琼二娘\",\"contactID\":71,\"telNums\":[{\"number\":\"15308229311\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈大姐\",\"contactID\":53,\"telNums\":[{\"number\":\"13684312938\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"张小玲\",\"contactID\":97,\"telNums\":[{\"number\":\"13795565619\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"judy\",\"contactID\":75,\"telNums\":[{\"number\":\"13795264888\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"雷女士\",\"contactID\":26,\"telNums\":[{\"number\":\"13881372826\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"裴林\",\"contactID\":40,\"telNums\":[{\"number\":\"18961828992\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"荣县联通故障\",\"contactID\":95,\"telNums\":[{\"number\":\"18608134739\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"严越强\",\"contactID\":24,\"telNums\":[{\"number\":\"13158826665\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"张明付\",\"contactID\":10,\"telNums\":[{\"number\":\"13564859960\",\"type\":\"手机\"},{\"number\":\"18621511220\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"联通网络故障\",\"contactID\":125,\"telNums\":[{\"number\":\"5609112\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"龙镜宇\",\"contactID\":187,\"telNums\":[{\"number\":\"13981398890\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞方文\",\"contactID\":56,\"telNums\":[{\"number\":\"13568334916\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"二爷\",\"contactID\":81,\"telNums\":[{\"number\":\"13558912050\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"北街邻居\",\"contactID\":186,\"telNums\":[{\"number\":\"13183924850\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"张玉其\",\"contactID\":200,\"telNums\":[{\"number\":\"13890052589\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"徐老师-数学\",\"contactID\":35,\"telNums\":[{\"number\":\"15984181247\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"朱丽烨妈妈\",\"contactID\":143,\"telNums\":[{\"number\":\"13890070056\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"穆永生\",\"contactID\":151,\"telNums\":[{\"number\":\"13917707069\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"son\",\"contactID\":209,\"telNums\":[{\"number\":\"13778557706\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"苑金光\",\"contactID\":109,\"telNums\":[{\"number\":\"15800579526\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"李国金\",\"contactID\":205,\"telNums\":[{\"number\":\"18601606454\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"护工白天\",\"contactID\":153,\"telNums\":[{\"number\":\"15183048591\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"程杰\",\"contactID\":202,\"telNums\":[{\"number\":\"15281325099\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞杰芳\",\"contactID\":21,\"telNums\":[{\"number\":\"18349955866\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞崇文\",\"contactID\":185,\"telNums\":[{\"number\":\"18281376185\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞孟函\",\"contactID\":86,\"telNums\":[{\"number\":\"13198768688\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王显彬\",\"contactID\":203,\"telNums\":[{\"number\":\"17782077793\",\"type\":\"手机\"},{\"number\":\"13818916332\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞大姐\",\"contactID\":43,\"telNums\":[{\"number\":\"13990032176\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈贺\",\"contactID\":83,\"telNums\":[{\"number\":\"13840809384\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"刘馨\",\"contactID\":184,\"telNums\":[{\"number\":\"18095062691\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王乐\",\"contactID\":121,\"telNums\":[{\"number\":\"15026967596\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"高总\",\"contactID\":167,\"telNums\":[{\"number\":\"13882065808\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"朱磊\",\"contactID\":67,\"telNums\":[{\"number\":\"13856061234\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"刘富云\",\"contactID\":161,\"telNums\":[{\"number\":\"13158815300\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"吴平\",\"contactID\":142,\"telNums\":[{\"number\":\"18889083377\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"郭师傅(护栏)\",\"contactID\":137,\"telNums\":[{\"number\":\"15082207988\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"龚翠容\",\"contactID\":169,\"telNums\":[{\"number\":\"17742814198\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"祝正荣\",\"contactID\":106,\"telNums\":[{\"number\":\"18657545032\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"徐顺雪\",\"contactID\":51,\"telNums\":[{\"number\":\"13564143023\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞阿姨\",\"contactID\":141,\"telNums\":[{\"number\":\"15228605936\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞方权\",\"contactID\":168,\"telNums\":[{\"number\":\"13890064546\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈晓军\",\"contactID\":170,\"telNums\":[{\"number\":\"13880738799\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"张友平\",\"contactID\":211,\"telNums\":[{\"number\":\"13795599572\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"廖志平\",\"contactID\":105,\"telNums\":[{\"number\":\"18990059566\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"颜立异\",\"contactID\":162,\"telNums\":[{\"number\":\"18621916447\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"宋立桥\",\"contactID\":68,\"telNums\":[{\"number\":\"13761735035\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"黄颖\",\"contactID\":182,\"telNums\":[{\"number\":\"13778549898\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞方权\",\"contactID\":139,\"telNums\":[{\"number\":\"18990053386\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"13320812506\",\"contactID\":146,\"telNums\":[{\"number\":\"13320812506\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王炯明\",\"contactID\":213,\"telNums\":[{\"number\":\"18092673474\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陆希强\",\"contactID\":129,\"telNums\":[{\"number\":\"13764189336\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"潘嫂\",\"contactID\":89,\"telNums\":[{\"number\":\"18696989429\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"mom\",\"contactID\":8,\"telNums\":[{\"number\":\"13980227145\",\"type\":\"手机\"},{\"number\":\"17748136086\",\"type\":\"家\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陆茵茵\",\"contactID\":107,\"telNums\":[{\"number\":\"15001730096\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"毕光火\",\"contactID\":84,\"telNums\":[{\"number\":\"13808202648\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"伐木累\",\"contactID\":124,\"telNums\":[{\"number\":\"08135632813\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"daisy\",\"contactID\":85,\"telNums\":[{\"number\":\"15618973331\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"廖云东\",\"contactID\":115,\"telNums\":[{\"number\":\"13823159355\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"童老师-语文\",\"contactID\":127,\"telNums\":[{\"number\":\"13808158227\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"沈杰能\",\"contactID\":61,\"telNums\":[{\"number\":\"13558922288\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞吉林\",\"contactID\":181,\"telNums\":[{\"number\":\"13684307006\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"杨小波\",\"contactID\":50,\"telNums\":[{\"number\":\"13777220490\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"余明庆\",\"contactID\":38,\"telNums\":[{\"number\":\"18241653136\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞方进\",\"contactID\":6,\"telNums\":[{\"number\":\"15008141908\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"高贵冬\",\"contactID\":103,\"telNums\":[{\"number\":\"13982742600\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"古永熊\",\"contactID\":36,\"telNums\":[{\"number\":\"13951795595\",\"type\":\"手机\"},{\"number\":\"15757780379\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"朱军\",\"contactID\":90,\"telNums\":[{\"number\":\"15902122836\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王青春\",\"contactID\":210,\"telNums\":[{\"number\":\"15228619157\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"fatherow\",\"contactID\":16,\"telNums\":[{\"number\":\"15984170806\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈章亮\",\"contactID\":99,\"telNums\":[{\"number\":\"13198807339\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"张老师-英语\",\"contactID\":64,\"telNums\":[{\"number\":\"13980231951\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"苏龙平\",\"contactID\":31,\"telNums\":[{\"number\":\"13804944801\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"李泽\",\"contactID\":140,\"telNums\":[{\"number\":\"13585582350\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"吴佳钦\",\"contactID\":80,\"telNums\":[{\"number\":\"13482248154\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"石照容\",\"contactID\":215,\"telNums\":[{\"number\":\"18681367461\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"高黎\",\"contactID\":175,\"telNums\":[{\"number\":\"13882281595\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"me\",\"contactID\":214,\"telNums\":[{\"number\":\"18681332813\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"虞吉财\",\"contactID\":122,\"telNums\":[{\"number\":\"13698245478\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"李鹏\",\"contactID\":28,\"telNums\":[{\"number\":\"13816729050\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"杨姐\",\"contactID\":178,\"telNums\":[{\"number\":\"13518468529\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"石照荣\",\"contactID\":66,\"telNums\":[{\"number\":\"18708360390\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"朱君臣\",\"contactID\":94,\"telNums\":[{\"number\":\"13019491909\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"宋謌\",\"contactID\":96,\"telNums\":[{\"number\":\"13918305707\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王敏\",\"contactID\":37,\"telNums\":[{\"number\":\"13527444997\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王砚龙\",\"contactID\":17,\"telNums\":[{\"number\":\"13342299735\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"程钟\",\"contactID\":58,\"telNums\":[{\"number\":\"13350113555\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"王健\",\"contactID\":135,\"telNums\":[{\"number\":\"18701925107\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"万老师\",\"contactID\":150,\"telNums\":[{\"number\":\"15892242056\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"梁日国\",\"contactID\":116,\"telNums\":[{\"number\":\"18515505964\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"邓弟\",\"contactID\":42,\"telNums\":[{\"number\":\"18862433629\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"张海涛\",\"contactID\":179,\"telNums\":[{\"number\":\"15386537017\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"miller\",\"contactID\":57,\"telNums\":[{\"number\":\"18502199190\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"石四孃\",\"contactID\":15,\"telNums\":[{\"number\":\"13550742220\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"刘斌\",\"contactID\":29,\"telNums\":[{\"number\":\"18302368621\",\"type\":\"手机\"}]},{\"contactNameFirst\":\"\",\"contactNameLast\":\"陈涛\",\"contactID\":180,\"telNums\":[{\"number\":\"18601796970\",\"type\":\"手机\"}]}]}";
                /*"{"+
        "\"contacts\":[{"+
        "\"contactID\": 13,"+
        "\"contactNameFirst\": \"樊小\","+
        "\"contactNameLast\": \"\","+
        "\"nameIndex\": \"f\","+
        "\"telNums\": [{"+
        "\"number\":\"19982833333\","+
        "\"provider\":\"电信\","+
        "\"type\":\"家\","+
        "\"location\":\"上海\""+
        "}]"+
        "},"+
        "{"+
        "\"contactID\": 34,"+
        "\"contactNameFirst\": \"张雨绮\","+
        "\"contactNameLast\": \"\","+
        "\"nameIndex\": \"z\","+
        "\"telNums\": [{"+
        "\"number\":\"18682822222\","+
        "\"provider\":\"联通\","+
        "\"type\":\"家\","+
        "\"location\":\"上海\""+
        "},{\"number\":\"137828222xx\",\"provider\":\"移动\",\"type\":\"家\",\"location\":\"上海\"}]"+
        "},"+
        "{"+
        "\"contactID\": 39,"+
        "\"contactNameFirst\": \"张雨绮\","+
        "\"contactNameLast\": \"\","+
        "\"nameIndex\": \"z\","+
        "\"telNums\": [{"+
        "\"number\":\"137828222xx\","+
        "\"provider\":\"移动\","+
        "\"type\":\"家\","+
        "\"location\":\"上海\""+
        "}]"+
        "},"+
        "{"+
        "\"contactID\": 1,"+
        "\"contactNameFirst\": \"我的编号\","+
        "\"contactNameLast\": \"\","+
        "\"nameIndex\": \"w\","+
        "\"telNums\": [{"+
        "\"number\":\"13282855555\","+
        "\"provider\":\"移动\","+
        "\"type\":\"家\","+
        "\"location\":\"上海\""+
        "}]"+
        "},"+
        "{"+
        "\"contactID\": 100,"+
        "\"contactNameFirst\": \"我的编号x的鱼熊\","+
        "\"contactNameLast\": \"\","+
        "\"nameIndex\": \"w\","+
        "\"telNums\": [{"+
        "\"number\":\"13782856666\","+
        "\"provider\":\"移动\","+
        "\"type\":\"家\","+
        "\"location\":\"成都\""+
        "}]"+
        "},"+
        "{"+
        "\"contactID\": 101,"+
        "\"contactNameFirst\": \"\","+
        "\"contactNameLast\": \"天天都在的鱼鱼熊同台\","+
        "\"nameIndex\": \"w\","+
        "\"telNums\": [{"+
        "\"number\":\"19982856666\","+
        "\"provider\":\"电信\","+
        "\"type\":\"公司\","+
        "\"location\":\"上海\""+
        "}]"+
        "}"+
        "]}";*/
        //sLog.i(null, "contactJson.init:"+contactJson);
        //ContactMgr.inst().init(contactJson);
        //String findjson = ContactMgr.inst().find("{\"name\":\"虞\"}");
        //sLog.i(null, "contact.find"+findjson);
        /*findjson = ContactMgr.inst().find("{\"name\":\"鱼兄\"}");
        sLog.i(null, "contact.find"+findjson);
        findjson = ContactMgr.inst().find("{\"name\":\"鱼\"}");
        sLog.i(null, "contact.find"+findjson);*/
    }
}
