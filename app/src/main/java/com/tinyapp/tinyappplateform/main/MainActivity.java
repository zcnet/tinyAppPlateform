package com.tinyapp.tinyappplateform.main;

import android.annotation.SuppressLint;
import android.app.SGMStatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.z.tinyapp.common.adapter.listview.CommonAdapter;
import com.z.tinyapp.common.base.BaseActivity;
import com.z.tinyapp.common.base.Layout;
import com.z.tinyapp.utils.logs.sLog;
import com.sun.dragrv.DefaultItemAnimator;
import com.sun.dragrv.DragRecyclerView;
import com.sun.dragrv.DragRecyclerViewParent;
import com.sun.dragrv.LinearLayoutManager;
import com.tinyapp.tinyappplateform.R;
import com.tinyapp.tinyappplateform.adapter.WeexRecycleViewApapter;
import com.tinyapp.tinyappplateform.adapter.itemtouch.DefaultItemTouchHelpCallback;
import com.tinyapp.tinyappplateform.adapter.itemtouch.DefaultItemTouchHelper;
import com.tinyapp.tinyappplateform.adapter.itemtouch.SpacesItemDecoration;
import com.tinyapp.tinyappplateform.bean.AppListBean;
import com.tinyapp.tinyappplateform.service.AppDownloadService;
import com.tinyapp.tinyappplateform.weex.BaseWeexActivity;
import com.tinyapp.tinyappplateform.weexapps.AppDirs;
import com.tinyapp.tinyappplateform.weexapps.AppListMgr;
import com.tinyapp.tinyappplateform.weexapps.SmallCardItem;
import com.tinyapp.tinyappplateform.weexapps.User;
import com.tinyapp.tinyappplateform.weexapps.UserMgr;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhengfei on 2018/7/19.
 */
@Layout(R.layout.activity_tiny_mian)
public class MainActivity extends BaseActivity implements MainView {
    MainPresent presenter;
    //@BindView(R.id.tv_search)
    //TextView tvSearch;
    @BindView(R.id.rv_weexcard)
    DragRecyclerView rvWeexcard;
    @BindView(R.id.lv_weexnotice)
    ListView lvWeexnotice;
    //@BindView(R.id.tv_editor)
    //TextView tvEditor;
    @BindView(R.id.ll_big_weexcard)
    LinearLayout llBigWeexCard;
    @BindView(R.id.rv_weexcard_p)
    DragRecyclerViewParent rvWeexcardP;
    @BindView(R.id.rl_big_weexcard)
    RelativeLayout rlBigWeexCard;
    @BindView(R.id.ll_card_wrap)
    LinearLayout llCardWrap;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.ll_edit)
    LinearLayout llEdit;


    /**
     * 滑动拖拽的帮助类
     */
    private DefaultItemTouchHelper itemTouchHelper;
    private static final int MAINBACKCODE = 200;
    private boolean initalWeex = false;
    private CommonAdapter<AppListBean> cardAdapter1;
    //    private WeexListAdapter cardAdapter;
    private WeexRecycleViewApapter rvCardAdapter;


    private GestureDetector mGestureDetector;

    @Override
    public ViewGroup getllBigWeexCard() {
        return llBigWeexCard;
    }

    @Override
    public ListView getlvWeexnotice() {
        return lvWeexnotice;
    }

    @Override
    public LinearLayout getllCardWrap() {
        return llCardWrap;
    }

    @Override
    public void reloadSmallCard() {
        initalCardAdapter(true);
    }

    @Override
    protected void initView() {
        showLoading();
        presenter = new MainPresent();
        presenter.attachView(this);
        presenter.init();
        initalRecycleView();
    }

    public void initalRecycleView() {
        rvWeexcard.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvWeexcard.setLayoutManager(linearLayoutManager);
        rvWeexcard.addItemDecoration(new SpacesItemDecoration(31));
    }


    @Override
    protected void initData() {

        presenter.initData();

        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mainPath = new AppDirs(MainActivity.this, "__").getRootDir() +
                        AppListMgr.getInstance().findBigCard("liteapp_systembase", "search_main").path;//"/service/WeexMain/search_main.js";
                startActivity(new Intent(MainActivity.this, BaseWeexActivity.class)
                        .putExtra(BaseWeexActivity.JSPATH, mainPath));
            }
        });

        llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mainPath = new AppDirs(MainActivity.this, "__").getRootDir() +
                        AppListMgr.getInstance().findBigCard("liteapp_systembase", "edit_main").path;//"/service/WeexMain/search_main.js";
                startActivity(new Intent(MainActivity.this, BaseWeexActivity.class)
                        .putExtra(BaseWeexActivity.JSPATH, mainPath));
            }
        });

        mGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                sLog.i(null, "onScroll -- distanceX:" + distanceX + " distanceY:" + distanceY);
                synchronized (MainActivity.this) {
                    if (distanceY > 10) {
                        presenter.collapseBigCard();
                    }
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                sLog.i(null, "onFling -- velocityX:" + velocityX + " velocityY:" + velocityY);
                return false;
            }
        });

        rlBigWeexCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        rlBigWeexCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });

        rlBigWeexCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });

        rvWeexcard.setOnScrollListener(new DragRecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(DragRecyclerView dragRecyclerView, int newState) {
                super.onScrollStateChanged(dragRecyclerView, newState);
            }

            @Override
            public void onScrolled(DragRecyclerView dragRecyclerView, int dx, int dy) {
                super.onScrolled(dragRecyclerView, dx, dy);
                sLog.i(null, "onScrolled:" +dy);
                synchronized (MainActivity.this) {
                    if (dy < 0 ) {
                        presenter.collapseEditCard();
                    }
                }
            }
        });



        rvWeexcardP.setRecyclerViewParentListener(new DragRecyclerViewParent.IRecyclerViewParentListener() {
            @Override
            public void onFlingDown() {
                sLog.i(null, "IRecyclerViewParentListener.onRefresh down");
                synchronized (MainActivity.this) {
                    presenter.extendBigCard();
                }

            }

            @Override
            public void onFlingUp() {
                sLog.i(null, "IRecyclerViewParentListener.onRefresh up");
                synchronized (MainActivity.this) {
                    presenter.extendEditCard();
                }
            }
        });



        //Intent intent = new Intent();
        //intent.setComponent(new ComponentName(this, FunctionManager.class));
        //startService(intent);
    }


    public void initalCardAdapter(boolean reset) {
        if (rvCardAdapter == null || reset) {
            User curUser = UserMgr.getInstance().getCurrentUser();
            List<SmallCardItem> list =  curUser.getSmallCardAppList(true);
            int height = curUser.getSmallCardAppListHeight();
            setSmallCardWrapHeight(height);
            rvCardAdapter = new WeexRecycleViewApapter(MainActivity.this,  list,rvWeexcard.getLayoutManager());
            rvCardAdapter.setOnItemClickListener(new WeexRecycleViewApapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    List<SmallCardItem> list = UserMgr.getInstance().getCurrentUser().getSmallCardAppList(false);
                    if (null != list && list.size() > 0 && position < list.size()) {
                        SmallCardItem sci = list.get(position);

                        String mainPath = new AppDirs(MainActivity.this, "__").getRootDir() + sci.app.appInfo.apppath;
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
        @SuppressLint("WrongConstant")
        SGMStatusBarManager sgmStatusBarManager = (SGMStatusBarManager)this.getSystemService(Context.SGM_STATUS_BAR_SERVICE);
        sgmStatusBarManager.setStatusBarStyle(SGMStatusBarManager.SGM_STATUSBAR_STYLE_HIDE, getWindow());
    }

    private void setSmallCardWrapHeight(int height){
        if (height > 467)
            height = 467;
        ViewGroup.LayoutParams lp = rvWeexcard.getLayoutParams();
        if (lp.height != height){
            lp.height = height;
            rvWeexcard.setLayoutParams(lp);
        }

        ViewGroup.LayoutParams lp1 = rvWeexcardP.getLayoutParams();
        if (lp1.height != height){
            lp1.height = height;
            rvWeexcardP.setLayoutParams(lp1);
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
                }
            });
        }
    }

    @Override
    public void changedAdapter() {
        if (rvCardAdapter != null) {
            rvCardAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public ViewGroup getllSearch() {
        return llSearch;
    }

    @Override
    public ViewGroup getllEdit() {
        return llEdit;
    }

    //显示主页面
    public void showMainContent() {
        //TODO 判断绑定账户
        if (initalWeex) {
            initalCardAdapter(false);
            hideLoading();
        }
    }

    @Override
    public void hideLoading() {
        showContent();
    }

    @Override
    protected void onDestroy() {
//        stopService(new Intent(MainActivity.this, VAManager.class));
        stopService(new Intent(MainActivity.this, AppDownloadService.class));
        super.onDestroy();
        presenter.Destroy();
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAINBACKCODE) {
            initalCardAdapter(false);
        }
    }


    private DefaultItemTouchHelpCallback.OnItemTouchCallbackListener onItemTouchCallbackListener = new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
        @Override
        public void onSwiped(int adapterPosition) {
            if (null != UserMgr.getInstance().getCurrentUser().getSmallCardAppList(false)) {
                UserMgr.getInstance().getCurrentUser().getSmallCardAppList(false).remove(adapterPosition);
                rvCardAdapter.notifyItemRemoved(adapterPosition);
                int height = UserMgr.getInstance().getCurrentUser().getSmallCardAppListHeight();
                setSmallCardWrapHeight(height);
            }
        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            if (null != UserMgr.getInstance().getCurrentUser().getSmallCardAppList(false)) {
                // 更换数据源中的数据Item的位置
                Collections.swap(UserMgr.getInstance().getCurrentUser().getSmallCardAppList(false),
                        srcPosition, targetPosition);
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
                sLog.e("Runtime", "onDrag: --w->  "+ width3+"  --h->  "+ height3);
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
//            wmParams.x = ((int) x)-width/2<=176?176:((int) x)-width/2;
//            wmParams.y = ((int) y)-height/2<=74?74: ((int) y)-height/2;
            wmParams.x = ((int) x)-width/2;
            wmParams.y = ((int) y)-height/2;
            sLog.e("Runtime", "onDrag: --x->  "+ wmParams.x+"  --y->  "+ wmParams.y);


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
            sLog.e("sunhy", "onDragOver: " );
            if(imageView!=null&&imageView.getParent()!=null){
                mWindowManager.removeView(imageView);
            }
        }

        @Override
        public void onStartDrag(DragRecyclerView.ViewHolder view,float x,float y) {
            sLog.e("RunTime", "onLongClick: ------------------------>");
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
        wmParams.gravity = Gravity.TOP|Gravity.LEFT;
    }

    public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number){
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg

                .getWidth(), sourceImg.getHeight());// 获得图片的ARGB值

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
