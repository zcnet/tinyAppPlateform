package plateform.tinyapp.com.tinyappplateform.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tinyapp.common.adapter.listview.CommonAdapter;
import com.tinyapp.common.base.BaseActivity;
import com.tinyapp.common.base.Layout;
import com.tinyapp.tinyappplateform.R;
import com.tinyapp.tinyappplateform.TapApplication;
import com.tinyapp.tinyappplateform.adapter.WeexListAdapter;
import com.tinyapp.tinyappplateform.adapter.WeexRecycleViewApapter;
import com.tinyapp.tinyappplateform.adapter.itemtouch.DefaultItemTouchHelpCallback;
import com.tinyapp.tinyappplateform.adapter.itemtouch.DefaultItemTouchHelper;
import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.tinyappplateform.bean.WeexBeanList;
import com.tinyapp.tinyappplateform.broadcastreceiver.BroadCastConstant;
import com.tinyapp.tinyappplateform.broadcastreceiver.MainBroadcastReceiver;
import com.tinyapp.tinyappplateform.edit.EditActivity;
import com.tinyapp.tinyappplateform.service.DownLoadService;
import com.tinyapp.tinyappplateform.weex.BaseWeexActivity;
import com.tinyapp.tinyappplateform.weex.WeexManager;
import com.tinyapp.userinfo.user.BindUserManager;
import com.tinyapp.userinfo.user.UserInfo;
import com.tinyapp.utils.common.APKVersionCodeUtils;
import com.tinyapp.utils.common.FileUtil;
import com.tinyapp.utils.common.JsonUtil;
import com.tinyapp.utils.common.PrefernceUtil;
import com.tinyapp.utils.common.ToastUtil;
import com.tinyapp.utils.thred.ThreadPoolUtil;
import com.sun.dragrv.DefaultItemAnimator;
import com.sun.dragrv.DragRecyclerView;
import com.sun.dragrv.LinearLayoutManager;
import com.sun.weexandroid_module.WxAndroidApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhengfei on 2018/7/19.
 */
@Layout(R.layout.activity_tiny_mian)
public class MainActivity extends BaseActivity implements MainView {
    MainPresent presenter;
    private MainBroadcastReceiver broadcastReceiver;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rv_weexcard)
    DragRecyclerView rvWeexcard;
    @BindView(R.id.lv_weexnotice)
    ListView lvWeexnotice;
    @BindView(R.id.tv_editor)
    TextView tvEditor;

    /**
     * 滑动拖拽的帮助类
     */
    private DefaultItemTouchHelper itemTouchHelper;
    private static final int MAINBACKCODE = 200;
    private boolean initalWeex = false;
    private boolean initalUser = false;
    private CommonAdapter<AppListBean> cardAdapter1;
    private WeexListAdapter noticeAdapter;
    //    private WeexListAdapter cardAdapter;
    private WeexRecycleViewApapter rvCardAdapter;
    private WeexBeanList weexBeanList;
    private List<AppListBean> listNofit;

    @Override
    protected void initView() {
        showLoading();
        presenter = new MainPresent();
        presenter.attachView(this);
        initalRecycleView();

    }

    public void initalRecycleView() {
        rvWeexcard.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvWeexcard.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                WxAndroidApplication.initWeex(TapApplication.getInstance() );
                presenter.initalWeexData(MainActivity.this);
//                bindService();
                presenter.getWeexList();
            }
        });
        initBroadcast();
    }


    public void initalCardAdapter() {
        presenter.initalDatas();
        if (rvCardAdapter == null) {
            rvCardAdapter = new WeexRecycleViewApapter(MainActivity.this, presenter.getWeexDatas(), rvWeexcard.getLayoutManager());
            rvCardAdapter.setOnItemClickListener(new WeexRecycleViewApapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (presenter.getWeexDatas().get(position).status == 5 || presenter.getWeexDatas().get(position).status == 6) {
                        ToastUtil.show(MainActivity.this, "正在下载该应�?..");
                    } else {
                        String mainPath = FileUtil.getAbsolutePath(MainActivity.this) + presenter.getWeexDatas().get(position).mainpathfile;
                        startActivity(new Intent(MainActivity.this, BaseWeexActivity.class)
                                .putExtra(BaseWeexActivity.JSPATH, mainPath));
                    }
                }
            });
            itemTouchHelper = new DefaultItemTouchHelper(onItemTouchCallbackListener);
            itemTouchHelper.attachToRecyclerView(rvWeexcard);
            rvCardAdapter.setItemTouchHelper(itemTouchHelper);
            itemTouchHelper.setDragEnable(true);
            itemTouchHelper.setSwipeEnable(true);
        }
        rvWeexcard.setAdapter(rvCardAdapter);
    }


    @Override
    public void showMianDate(WeexBeanList weexBeanList) {
        this.weexBeanList = weexBeanList;
        if (initalWeex) {
            presenter.checkPackage(weexBeanList, MainActivity.this);
        }
    }

    @Override
    public void initalWeexBack(boolean isSuccess) {
        if (isSuccess) {
            initalWeex = true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMainContent();
                    if (weexBeanList != null) {
                        presenter.checkPackage(weexBeanList, MainActivity.this);
                    }
                }
            });
        }
    }

    @Override
    public void changedAdapter() {
        if (rvCardAdapter != null) {
            presenter.initalDatas();
            rvCardAdapter.notifyDataSetChanged();
        }

    }

    //显示主页�?
    private void showMainContent() {
        //TODO 判断绑定账户
        if (initalWeex) {
            initalCardAdapter();
            hideLoading();
        }
    }

    @Override
    public void hideLoading() {
        showContent();
    }

    public void bindService() {
        BindUserManager.initial(MainActivity.this);
        BindUserManager.getInstenst().setCallBack(new BindUserManager.BindBack() {
            @Override
            public void bindSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //绑定成功并获取到了用户信�?
                        if (!TextUtils.isEmpty(UserInfo.getInstance().getAccessToken())) {
                            getUserSuccess();
                        } else {//没有获取到用户信�?
                            presenter.showPopWindow(MainActivity.this);
                        }
                    }
                });
            }
        }).bindService();
    }

    private void initBroadcast() {
        broadcastReceiver = new MainBroadcastReceiver();
        broadcastReceiver.setCallback(new MainBroadcastReceiver.ICallback() {
            @Override
            public void updataBack(String guid, String version) {//更新应用通知
                AppListBean bean = WeexManager.getInstance().getBeanFromId(guid);
                if (APKVersionCodeUtils.compareVersion(bean.version, version) < 0) {
                    bean.status = 1;//正在更新
                    presenter.getUpdate(MainActivity.this, bean, true);
                    PrefernceUtil.putString(bean.name, JsonUtil.toJSONString(bean));
                }
            }

            @Override
            public void cardDown(String weexid, Boolean isSuccess) {//卡片包下�?
                synchronized (this) {
                    if (isSuccess) {
                        AppListBean bean = WeexManager.getInstance().getBeanFromId(weexid);
                        Log.i("zf1", "cardDown:" + bean.name);
                        presenter.initalDatas();
                        if (rvCardAdapter != null) {
                            rvCardAdapter.notifyDataSetChanged();
                        }
                        bean.status = 6;
                        presenter.getUpdate(MainActivity.this, bean, false);
                    }
                }
            }

            @Override
            public void nofit(String weexId) {//接收到weex推送消�?
                if (listNofit == null) {
                    listNofit = new ArrayList<>();
                }
                listNofit.add(WeexManager.getInstance().getBeanFromId(weexId));
                if (noticeAdapter == null) {
                    noticeAdapter = new WeexListAdapter(MainActivity.this, listNofit, lvWeexnotice);
                    lvWeexnotice.setAdapter(noticeAdapter);
                } else {
                    noticeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public synchronized void mainDown(String giud) {//主应用包下载成功
                for (AppListBean bean : presenter.getWeexDatas()) {
                    if (bean.guid.equals(giud)) {
                        bean.status = 0;
                    }
                }
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastConstant.ACCOUNTLOGOUT_ACTION);
        filter.addAction(BroadCastConstant.PUSH_ACTION);
        filter.addAction(BroadCastConstant.MAINCARDDOWN);
        filter.addAction(BroadCastConstant.SENDNOFIT);
        this.registerReceiver(broadcastReceiver, filter);
    }


    @Override
    protected void onDestroy() {
//        stopService(new Intent(MainActivity.this, VAManager.class));
        stopService(new Intent(MainActivity.this,DownLoadService.class));
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);

    }

    @OnClick({R.id.tv_search, R.id.tv_editor,R.id.tv_closeapp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                sendBroadcast(new Intent().setAction(BroadCastConstant.SENDNOFIT).putExtra("weexid", "49204EB5-2BD9-498C-A9F1-2F4C3F63576230"));
                break;
            case R.id.tv_editor:
                startActivityForResult(new Intent(MainActivity.this, EditActivity.class), MAINBACKCODE);
                break;
//            case R.id.tv_dongdong:
//                new BindServiceAndStart().startRecog(this);
//                break;
            case R.id.tv_closeapp:
                System.exit(0);
                break;
        }
    }

    //绑定账户成功
    public void getUserSuccess() {
        initalUser = true;
        showMainContent();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAINBACKCODE) {
            initalCardAdapter();
        }
    }


    private DefaultItemTouchHelpCallback.OnItemTouchCallbackListener onItemTouchCallbackListener = new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
        @Override
        public void onSwiped(int adapterPosition) {
            if (presenter.getWeexDatas() != null) {
                WeexManager.getInstance().getBeanFromId(presenter.getWeexDatas().get(adapterPosition).guid).isUsed = false;
                presenter.getWeexDatas().remove(adapterPosition);
                rvCardAdapter.notifyItemRemoved(adapterPosition);
            }
        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            if (presenter.getWeexDatas() != null) {
                // 更换数据源中的数据Item的位�?
                Collections.swap(presenter.getWeexDatas(), srcPosition, targetPosition);
                // 更新UI中的Item的位置，主要是给用户看到交互效果
                rvCardAdapter.notifyItemMoved(srcPosition, targetPosition);
                return true;
            }
            return false;
        }
        @Override
        public void onDrag(DragRecyclerView.ViewHolder holder, float x, float y) {
            if(!isOnStartDrag){
                return;
            }
            if(holder==null){
                return;
            }
            if(imageView ==null){
                initFloatWindowImg();
                Resources resources = MainActivity.this.getResources();
                DisplayMetrics dm = resources.getDisplayMetrics();
                int width3 = dm.widthPixels;
                int height3 = dm.heightPixels;
                Log.e("Runtime", "onDrag: --w->  "+ width3+"  --h->  "+ height3);
            }

            if(wmParams==null){
                initWindowParams();
            }

            if(mWindowManager ==null){
                showFloatWindow(wmParams);
                imageView.setVisibility(View.INVISIBLE);
            }

            if(mHolder==null||holder.getAdapterPosition() != mHolder.getAdapterPosition()){
                if(holder.itemView==null){
                    return;
                }
                mHolder = holder;
                mHolder.itemView.setDrawingCacheEnabled(true);
                mBitmap = holder.itemView.getDrawingCache();
                imageView.setImageBitmap(getTransparentBitmap(mBitmap,70));
                imageView.setVisibility(View.VISIBLE);
            }

            if(wmParams==null){
                return;
            }
            if(width==0&&mHolder!=null){
                width = mHolder.itemView.getWidth()*6/5;
            }
            if(height==0&&mHolder!=null){
                height = mHolder.itemView.getHeight()*6/5;
            }
            wmParams.width = width;
            wmParams.height =height;
            wmParams.x = ((int) x)-width/2<=176?176:((int) x)-width/2;
            wmParams.y = ((int) y)-height/2<=74?74: ((int) y)-height/2;
            Log.e("Runtime", "onDrag: --x->  "+ wmParams.x+"  --y->  "+ wmParams.y);


            if(mWindowManager!=null){
                if(imageView.getParent()==null){
                    mWindowManager.addView(imageView,wmParams);
                }
                mWindowManager.updateViewLayout(imageView,wmParams);
            }
        }

        @Override
        public void onDragOver() {
            isOnStartDrag = false;
            Log.e("sunhy", "onDragOver: " );
            if(imageView!=null&&imageView.getParent()!=null){
                mWindowManager.removeView(imageView);
            }
        }

        @Override
        public void onStartDrag(DragRecyclerView.ViewHolder view,float x,float y) {
            Log.e("RunTime", "onLongClick: ------------------------>");
            isOnStartDrag = true;
            onDrag(view, x, y);
        }
    };

    private DragRecyclerView.ViewHolder mHolder;
    private Bitmap mBitmap;
    private WindowManager mWindowManager;
    private ImageView imageView;
    private WindowManager.LayoutParams wmParams;
    private int width;
    private int height;
    private boolean isOnStartDrag = false;

    private void showFloatWindow(WindowManager.LayoutParams layoutParams){
        mWindowManager= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(imageView,layoutParams);
    }

    private void initFloatWindowImg(){
        if(imageView==null){
            imageView= (ImageView) LayoutInflater.from(this).inflate(R.layout.app_layout_float_window,null,false);
        }
        imageView.setImageBitmap(mBitmap);
    }

    private void initWindowParams(){
        wmParams = new WindowManager.LayoutParams();
        wmParams.packageName = getPackageName();
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SCALED
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_BLUR_BEHIND
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.gravity = Gravity.TOP| Gravity.LEFT;
    }

    public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number){
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg

                .getWidth(), sourceImg.getHeight());// 获得图片的ARGB�?

        number = number * 255 / 100;

        for (int i = 0; i < argb.length; i++) {

            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);

        }

        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg

                .getHeight(), Bitmap.Config.ARGB_8888);

        return sourceImg;
    }

    @Override
    protected boolean isimmersive() {
        return false;
    }
}
