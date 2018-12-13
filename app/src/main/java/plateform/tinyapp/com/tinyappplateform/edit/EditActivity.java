package plateform.tinyapp.com.tinyappplateform.edit;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.az.madison.imageloader.ImageLoaderUtil;
import com.tinyapp.common.adapter.listview.CommonAdapter;
import com.tinyapp.common.adapter.listview.ViewHolder;
import com.tinyapp.common.base.BaseActivity;
import com.tinyapp.common.base.Layout;
import com.tinyapp.tinyappplateform.R;
import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.tinyappplateform.broadcastreceiver.BroadCastConstant;
import com.tinyapp.tinyappplateform.broadcastreceiver.MainBroadcastReceiver;
import com.tinyapp.tinyappplateform.service.DownLoadService;
import com.tinyapp.tinyappplateform.service.UpdateService;
import com.tinyapp.tinyappplateform.weex.WeexManager;
import com.tinyapp.utils.common.FileUtil;
import com.tinyapp.utils.common.LauncherUtil;
import com.tinyapp.utils.common.ToastUtil;

import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhengfei on 2018/8/9.
 */
@Layout(R.layout.activity_edit)
public class EditActivity extends BaseActivity implements EditView{
    @BindView(R.id.tv_edit_back)
    TextView tvEditBack;
    @BindView(R.id.lv_edit)
    ListView lvEdit;
    EditPresent present;
    private MainBroadcastReceiver broadcastReceiver;
    private  int color1= Color.parseColor("#e5e5e5");
    private  int color2= Color.parseColor("#f9e9e9");
    private CommonAdapter<AppListBean> cardAdapter;
    private int weexLen;
    @Override
    protected void initView() {
        present=new EditPresent();
        present.attachView(this);
    }

    @Override
    protected void initData() {
        showLoading();
        present.getWeexList();
        initBroadcast();
    }

    public void initAdapter(){
        weexLen=WeexManager.getInstance().weexList.appList.size();
        cardAdapter=new CommonAdapter<AppListBean>(EditActivity.this,R.layout.layout_cardweex_item,  present.getWeexDatas()) {
            @Override
            protected void convert(ViewHolder viewHolder, final AppListBean item, int position) {
                TextView updateTv= viewHolder.getView(R.id.tv_update);
                TextView upTv= viewHolder.getView(R.id.tv_up);
                TextView downTv= viewHolder.getView(R.id.tv_down);
                TextView moveTv= viewHolder.getView(R.id.tv_move);
                moveTv.setTag(position);
                ImageView icon=viewHolder.getView(R.id.iv_card_icon);
                ImageLoaderUtil.getInstance().loadImage(item.icon,icon);
                viewHolder.setText(R.id.tv_card_name,item.name);
                viewHolder.setText(R.id.tv_card_info,item.intro);
                if(item.isUsed){
                    moveTv.setText("+");
                }else{
                    moveTv.setText("-");
                }
                upTv.setTag(position);
                downTv.setTag(position);
                if(item.isAssociate==1){//是否授权
                    viewHolder.getConvertView().setBackgroundColor(color2);
                    if(item.status==4){
                        updateTv.setVisibility(View.VISIBLE);
                        moveTv.setClickable(true);
                        updateTv.setText("正在下载");
                    }else if (item.status==7){
                        updateTv.setVisibility(View.VISIBLE);
                        updateTv.setText("下载");
                        moveTv.setClickable(true);
                    }else{
                        updateTv.setVisibility(View.INVISIBLE);
                        updateTv.setText("下载");
                        moveTv.setClickable(true);
                    }
                }else{
                    viewHolder.getConvertView().setBackgroundColor(color1);
                    updateTv.setVisibility(View.VISIBLE);
                    updateTv.setText("下载");
                    moveTv.setClickable(false);
                }
                updateTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str= (String) ((TextView)v).getText();
                        if(str.equals("下载")){
                            if(item.status==7){
                                AppListBean bean=WeexManager.getInstance().getBeanFromId(item.guid);
                                bean.status=4;
                                Intent intent=new Intent(EditActivity.this, DownLoadService.class);
                                intent.putExtra(DownLoadService.INTENTTAG,DownLoadService.CARD);
                                intent.putExtra("appBean",bean);
                                startService(intent);
                            }else{
                                present.associationGet(EditActivity.this,item.guid);
                            }
                            ((TextView)v).setText("正在下载");
                        }
                    }
                });
                upTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index= (int) v.getTag();
                        if(index>=weexLen){
                            return;
                        }
                        if(index==0){
                            ToastUtil.show(EditActivity.this,"已经是第一个了");
                        }else{
                            Collections.swap(present.getWeexDatas(),index,index-1);
                            cardAdapter.notifyDataSetChanged();
                            Collections.swap(WeexManager.getInstance().weexList.appList,index,index-1);
                        }
                    }
                });
                downTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index= (int) v.getTag();
                        if(index>=weexLen){
                            return;
                        }
                        if(index==weexLen-1){
                            ToastUtil.show(EditActivity.this,"已经是最后一个了");
                        }else{
                            Collections.swap(present.getWeexDatas(),index,index+1);
                            cardAdapter.notifyDataSetChanged();
                            Collections.swap(WeexManager.getInstance().weexList.appList,index,index+1);
                        }

                    }
                });
                moveTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index= (int) v.getTag();
                        if(index>=weexLen){
                            return;
                        }
                        String str= (String) ((TextView)v).getText();
                        if(str.equals("-")){
                            WeexManager.getInstance().weexList.appList.get(index).isUsed=true;
                            ((TextView) v).setText("+");
                            String strPath= FileUtil.getAbsolutePath(EditActivity.this)+ item.mainpathfile;
                            LauncherUtil.addShortcut(EditActivity.this,item.name,R.drawable.txs_fail,strPath);
                        }else{
                            WeexManager.getInstance().weexList.appList.get(index).isUsed=false;
                            ((TextView) v).setText("-");
                            String strPath= FileUtil.getAbsolutePath(EditActivity.this)+ item.mainpathfile;
                            LauncherUtil.delShortcut(EditActivity.this,item.name,R.drawable.txs_fail,strPath);
                        }
                    }
                });
            }
        };
    }

    @OnClick(R.id.tv_edit_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        startService(new Intent(EditActivity.this, UpdateService.class));
        super.onBackPressed();
        setResult(2);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void hideLoading() {
        showContent();
    }

    @Override
    public void showMainData() {
        hideLoading();
        initAdapter();
        lvEdit.setAdapter(cardAdapter);
    }

    @Override
    public void associationFild(String guid) {

    }

    private void initBroadcast() {
        broadcastReceiver = new MainBroadcastReceiver();
        broadcastReceiver.setCallback(new MainBroadcastReceiver.ICallback() {
            @Override
            public void updataBack(String guid, String version) {}
            @Override
            public void cardDown(String weexid, Boolean isSuccess) {
                synchronized (this){
                    if(isSuccess){
                        for (int i=0;i<present.getWeexDatas().size();i++){
                            AppListBean bean=present.getWeexDatas().get(i);
                            if((bean.guid+"").equals(weexid)){
                                bean.status=5;
                                bean.isUsed=true;
                                bean.isAssociate=1;
                                int size=WeexManager.weexManager.weexList.appList.size();
                                if(i+1>=size){//自己发送的通知
                                    present.getUpdate(EditActivity.this,bean,false);
                                    present.getWeexDatas().remove(bean);
                                    present.getWeexDatas().add(size-1,bean);
                                    weexLen=size;
                                }
                                break;
                            }
                        }
                        cardAdapter.notifyDataSetChanged();
                    }else{
                        for (AppListBean bean:present.getWeexDatas()) {
                            if((bean.guid+"").equals(weexid)){
                                bean.status=7;
                                ToastUtil.show(EditActivity.this,"下载失败");
                                break;
                            }
                        }
                        cardAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void nofit(String weexId) {}
            @Override
            public void mainDown(String giud) {}
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastConstant.EDITCARDDOWN);
        filter.addAction(BroadCastConstant.MAINCARDDOWN);
        this.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected boolean isimmersive() {
        return false;
    }
}
