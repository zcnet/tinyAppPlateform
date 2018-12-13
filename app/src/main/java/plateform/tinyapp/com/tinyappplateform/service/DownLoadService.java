package plateform.tinyapp.com.tinyappplateform.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tinyapp.network.DownLoadCallBack;
import com.tinyapp.network.httputil.HttpConstant;
import com.tinyapp.network.okhttp.OkHttp3Utils;
import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.tinyappplateform.bean.WeexUpdateBean;
import com.tinyapp.tinyappplateform.broadcastreceiver.BroadCastConstant;
import com.tinyapp.tinyappplateform.weex.WeexManager;
import com.tinyapp.utils.common.AsscetsUtil;
import com.tinyapp.utils.common.FileUtil;
import com.tinyapp.utils.common.JsonUtil;
import com.tinyapp.utils.des.Base64Util;
import com.tinyapp.utils.des.BoringsslUtils;
import com.tinyapp.utils.des.DesUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengfei on 2018/8/20.
 */

public class DownLoadService extends Service {
    public static final int CARD = 1;
    public static final int UPDATE = 2;
    public static final int MAIN = 3;
    public static final int EDIT = 4;//编辑页面卡片下载
    public static final String INTENTTAG = "status";
    private List<String> downList;
    private String srcfilePath;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        downList = new ArrayList<>();
        srcfilePath=FileUtil.getAbsolutePath(DownLoadService.this);
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        int status = intent.getIntExtra(INTENTTAG, 0);
        String url;
        String savePath;
        String weexId;
        String keyGuid;
        String name;
        if (status == CARD || status == EDIT) {//下载卡片
            AppListBean bean = (AppListBean) intent.getSerializableExtra("appBean");
            weexId = bean.guid + "";
            url = bean.showPackageURL;
            savePath = FileUtil.getSrcFilePath(DownLoadService.this, bean.name);
            keyGuid = bean.keyGuid;
            name = bean.name;
        } else if (status == MAIN) {//下载主包
            WeexUpdateBean.AppListBean bean = (WeexUpdateBean.AppListBean) intent.getSerializableExtra("appBean");
            weexId = bean.guid + "";
            url = bean.packageURL;
            savePath = FileUtil.getSrcFilePath(DownLoadService.this, bean.name);
            keyGuid = bean.keyGuid;
            name = bean.name;
        } else {//下载升级包
            final WeexUpdateBean.AppListBean bean = (WeexUpdateBean.AppListBean) intent.getSerializableExtra("appBean");
            weexId = bean.guid + "";
            url = bean.packageURL;
            savePath = FileUtil.getSrcFilePath(DownLoadService.this, bean.name);
            keyGuid = bean.keyGuid;
            name = bean.name;
        }
        if (!downList.contains(weexId)) {
            downFile(url, savePath, weexId,  name, status);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void downFile(String url, final String savePath, final String weexId, final String name, final int status) {

        OkHttp3Utils.download(url, savePath, new DownLoadCallBack() {
            @Override
            public void onStart() {
                Log.i("zf1", "onStart+" + name + "    status:" + status);
                downList.add(weexId);
            }

            @Override
            public void onError(String code, String failureMsg) {
                Log.i("zf1", "onError" + name + "    status:" + status);
                downLoadError(status,weexId);
            }

            @Override
            public void onLoadProgress(int progress) {

            }

            @Override
            public void onSuccess() {
                try {
                    AsscetsUtil.UnZipFolder(savePath, srcfilePath);
                    final String singFile=FileUtil.getWeexSignFilePath(srcfilePath, name);
                    Map<String,Object> map= JsonUtil.parseFile(singFile);
                    if (map==null){//转换失败
                        downLoadError(status,weexId);
                    }else{
                        String sign= (String) map.get("sign");
                        final byte[] bt = Base64Util.decryptBASE64(sign);
                        final String keyGuid= (String) map.get("keyGuid");
                        final String weexZipFile=FileUtil.getWeexZipFilePath(srcfilePath, name);
                        downKeyFile(keyGuid, new ICallBack() {
                            @Override
                            public void success() {
                                try {
                                    String keyPath = FileUtil.getKeuGuidPath(DownLoadService.this, keyGuid);//密钥文件
                                    String publicKey=DesUtils.getPublicKeyWithFile(keyPath);
                                    FileUtil.deleteFile(new File(singFile));
                                    if(BoringsslUtils.decryptFile(publicKey,bt,weexZipFile)){//解密成功
                                        downLoadSuccess(status,weexZipFile,srcfilePath,weexId,name);
                                    }else{//失败
                                        downLoadError(status,weexId);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    downLoadError(status,weexId);
                                }finally {
                                    WeexManager.getInstance().setToPrefernce();
                                    downList.remove(weexId);
                                }
                            }
                            @Override
                            public void error() {
                                downLoadError(status,weexId);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    downLoadError(status,weexId);
                    WeexManager.getInstance().setToPrefernce();
                    downList.remove(weexId);
                }
            }

            @Override
            public void onWarn() {

            }
        });
    }

    public void downKeyFile(final String keyGuid, final ICallBack callBack) {
        if (FileUtil.isHasKeyGuid(DownLoadService.this, keyGuid + ".xml")) {
            callBack.success();
        } else {
            String url = HttpConstant.GETKEYFILE + "&keyGuid=" + keyGuid;
            String savePath = FileUtil.getAbsolutePath(DownLoadService.this) + "/key/" + keyGuid + ".xml";
            OkHttp3Utils.downLoadForLoader(url, savePath, new DownLoadCallBack() {
                @Override
                public void onStart() {

                }
                @Override
                public void onError(String code, String failureMsg) {
                    callBack.error();
                }

                @Override
                public void onLoadProgress(int progress) {
                }
                @Override
                public void onSuccess() {
                    callBack.success();
                }
                @Override
                public void onWarn() {
                    try {
                        Thread.sleep(100);
                        downKeyFile(keyGuid,callBack);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public interface ICallBack {
        void success();

        void error();
    }

    public void downLoadError(int status,String weexId){
        if(status==CARD){
            sendBroadcast(new Intent().setAction(BroadCastConstant.EDITCARDDOWN).putExtra("weexid", weexId).putExtra("success",false));
            sendBroadcast(new Intent().setAction(BroadCastConstant.MAINCARDDOWN).putExtra("weexid", weexId).putExtra("success",false));
            WeexManager.getInstance().getBeanFromId(weexId).status = 7;
        }else if(status==EDIT){
            sendBroadcast(new Intent().setAction(BroadCastConstant.EDITCARDDOWN).putExtra("weexid", weexId).putExtra("success",false));
            WeexManager.getInstance().getBeanFromId(weexId).status = 7;
        }
        downList.remove(weexId);
    }
    public void downLoadSuccess(int status, String weexZipFile, String weexPath, String weexId, String name) throws Exception {
        if (status == CARD) {
            AsscetsUtil.UnZipFolder(weexZipFile, weexPath+"/"+name);
            FileUtil.deleteFile(new File(weexZipFile));
            WeexManager.getInstance().getBeanFromId(weexId).status = 5;
            Log.i("zf", "MAINCARDDOWN:"+name);
            sendBroadcast(new Intent().setAction(BroadCastConstant.MAINCARDDOWN).putExtra("weexid", weexId).putExtra("success",true));
        } else if (status == MAIN) {
            AsscetsUtil.UnZipFolder(weexZipFile, weexPath+"/"+name);
            FileUtil.deleteFile(new File(weexZipFile));
            WeexManager.getInstance().getBeanFromId(weexId).status = 0;
            sendBroadcast(new Intent().setAction(BroadCastConstant.MAINMAINDOWN).putExtra("weexid", weexId));
        } else if (status == UPDATE) {
            WeexManager.getInstance().getBeanFromId(weexId).status = 2;
        } else {
            AsscetsUtil.UnZipFolder(weexZipFile, weexPath+"/"+name);
            FileUtil.deleteFile(new File(weexZipFile));
            Log.i("zf", "EDITCARDDOWN:"+name);
            sendBroadcast(new Intent().setAction(BroadCastConstant.EDITCARDDOWN).putExtra("weexid", weexId).putExtra("success",true));
        }
    }
}
