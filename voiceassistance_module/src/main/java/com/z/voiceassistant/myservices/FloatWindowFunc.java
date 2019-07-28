package com.z.voiceassistant.myservices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.z.voiceassistant.R;
import com.z.voiceassistant.constants.FloatWindowConstant;
import com.z.voiceassistant.customizedViews.VoiceWaveView;
import com.z.voiceassistant.listener.IDataCallBack;
import com.z.voiceassistant.models.WxAddWxBean;
import com.z.voiceassistant.models.WxRemoveWxBean;
import com.z.voiceassistant.utils.Logg;
import com.z.voiceassistant.utils.ScreenShotUtil;
import com.google.gson.Gson;
import com.z.tinyapp.utils.common.TextUtil;
import com.z.tinyapp.utils.common.WeexApps;
import com.sun.weexandroid_module.WxRvUtils;

import java.util.HashMap;
import java.util.Map;

import static com.z.voiceassistant.customizedViews.VoiceWaveView.VOICE_WAVE_KEY_COLOR;
import static com.z.voiceassistant.customizedViews.VoiceWaveView.VOICE_WAVE_KEY_OFFSET;
import static com.z.voiceassistant.customizedViews.VoiceWaveView.VOICE_WAVE_KEY_RANGE;
import static com.z.voiceassistant.customizedViews.VoiceWaveView.VOICE_WAVE_KEY_TIME;

/**
 * Created by GongDongdong on 2018/8/8
 */
public class FloatWindowFunc {
    @SuppressWarnings("unused")
    private static final String TAG = "sunHy_FWService";

    private WindowManager mWindowManager;
    private View mFloatView;
    private VoiceWaveView mVoiceWaveView;
    private VoiceWaveView mVoiceWaveView2;
    private VoiceWaveView mVoiceWaveView3;

    //Context
    private Context mCtx;

    private TextView mTvBottom;
    private TextView mTvFull;
    private TextView mTvTtsSay;

    private ImageView mImgExitBottom;
    private ImageView mImgExitTop;
    private ImageView mImgHelpTop;

    private ImageView mImgShowBusyMark;
    private ImageView mImgShowBusyMarkFull;
    private ImageView mImgTtsIcon;

    private FrameLayout mLlUpLayer;
    private LinearLayout mLlJsContainer;
    private NestedScrollView mNsShowContent;
    private View mDivideLine;
    private Animation mCircleAnim;

    private IDataCallBack mDataCallBack;

    //用户之前说过的话
    private String mUserSay = "";

    private boolean isFull = false;

    //上一次添加的Wx的Id
    private Long mLastWxId = -1L;

    //悬浮窗状态 0为默认即未开启 1为半屏 2为全屏
    private int mFwStatus = 0;

    /**
     * 初始化 需要Context
     */
    public FloatWindowFunc(Context ctx, IDataCallBack callBack) {
        mCtx = ctx;
        mDataCallBack = callBack;
    }

    /**
     * TTS播报时显示
     */
    public void showTtsPlay(final String str) {
        if (mFloatView == null) {
            Logg.e(TAG, "showTtsPlay  float window null !!!");
            return;
        }
        Logg.i(TAG, "showTtsPlay: ");
        mFloatView.post(new Runnable() {
            @Override
            public void run() {

                hideBusyMark();

                if (!isFull) {
                    mTvFull.setText("");
                    mTvBottom.setText("");
                    mTvTtsSay.setText(str);
                    showTtsIcon();
                    return;
                }

                View view = LayoutInflater.from(mCtx).inflate(R.layout.layout_tts_say_full, mLlJsContainer, false);
                ((TextView) view.findViewById(R.id.tv_tts_say)).setText(str);

                mLlJsContainer.addView(view);

                mLlJsContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        mNsShowContent.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    /**
     * 清除TTS文言
     */
    public void clearTtsSay() {
        Logg.i(TAG, "clearTtsSay: ");
        if (mFloatView == null && mTvTtsSay != null) {
            Logg.e(TAG, "clearTtsSay  float window null !!!");
            return;
        }
        mFloatView.post(new Runnable() {
            @Override
            public void run() {
                mTvTtsSay.setText("");
            }
        });
    }

    /**
     * 显示User说的话
     */
    public void showUserSay() {
        if (mFloatView == null) {
            Logg.e(TAG, "showUserSay  float window null !!!");
            return;
        }
        mFloatView.post(new Runnable() {
            @Override
            public void run() {
                if (!isFull || TextUtil.isEmpty(mUserSay)) {
                    return;
                }

                View view = LayoutInflater.from(mCtx).inflate(R.layout.layout_user_say_full, mLlJsContainer, false);
                ((TextView) view.findViewById(R.id.tv_user_say)).setText(mUserSay);

                mLlJsContainer.addView(view);

                mLlJsContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        mNsShowContent.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    /**
     * 记录用户说的话
     */
    public void setUserSay(String content) {
        mUserSay = content;
    }

    /**
     * 最下方文字识别完成
     */
    public void middleRecResultFinal(final String result) {
        if (mFloatView == null) {
            Logg.e(TAG, "middleRecResultFinal  float window null !!!");
            return;
        }
        mFloatView.post(new Runnable() {
            @Override
            public void run() {
                mUserSay = result;

                if (isFull) {
                    if (mTvFull != null) {
                        mTvFull.setText(result);
                    }

                    if (mTvBottom != null) {
                        mTvBottom.setText("");
                    }

                    if (mTvTtsSay != null) {
                        mTvTtsSay.setText("");
                    }
                } else {
                    if (mTvBottom != null) {
                        mTvBottom.setText(result);
                    }

                    if (mTvTtsSay != null) {
                        mTvTtsSay.setText("");
                    }

                    if (mTvFull != null) {
                        mTvFull.setText("");
                    }
                }
            }
        });
    }

    /**
     * 清除下方识别文字
     */
    public void clearTvBottom() {
        if (mFloatView == null) {
            Logg.e(TAG, "clearTvBottom  float window null !!!");
            return;
        }
        mFloatView.post(new Runnable() {
            @Override
            public void run() {
                mTvBottom.setText("");
            }
        });
    }

    /**
     * 开始下方Loading动画
     */
    public void startBusyMarkAnimation() {
        if (mFloatView == null) {
            Logg.e(TAG, "startBusyMarkAnimation  float window null !!!");
            return;
        }
        mFloatView.post(new Runnable() {
            @Override
            public void run() {

                if (mImgShowBusyMark.getAnimation() != null && mImgShowBusyMarkFull != null) {
                    return;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mImgShowBusyMark.setBackground(mCtx.getResources().getDrawable(R.drawable.loading_bottom));
                    mImgShowBusyMarkFull.setBackground(mCtx.getResources().getDrawable(R.drawable.loading_bottom));
                }

                if (isFull) {

                    if (mImgShowBusyMarkFull != null) {
                        mImgShowBusyMarkFull.setVisibility(View.VISIBLE);
                        mImgShowBusyMark.setVisibility(View.GONE);
                        if (mImgShowBusyMarkFull.getAnimation() == null || !mImgShowBusyMarkFull.getAnimation().hasStarted()) {
                            mImgShowBusyMarkFull.startAnimation(mCircleAnim);  //开始动画
                        }
                    }

                } else {
                    if (mImgShowBusyMark != null) {
                        mImgShowBusyMark.setVisibility(View.VISIBLE);
                        mImgShowBusyMarkFull.setVisibility(View.GONE);
                        if (mImgShowBusyMark.getAnimation() == null || !mImgShowBusyMark.getAnimation().hasStarted()) {
                            mImgShowBusyMark.startAnimation(mCircleAnim);  //开始动画
                        }
                    }

                }

            }
        });
    }

    /**
     * 隐藏下方Loading动画
     */
    public void hideBusyMark() {

        if (mFloatView == null) {
            Logg.e(TAG, "hideBusyMark  float window null !!!");
            return;
        }
        mFloatView.post(new Runnable() {
            @Override
            public void run() {

                if (mImgShowBusyMark != null && mImgShowBusyMarkFull != null) {
                    mImgShowBusyMark.clearAnimation();
                    mImgShowBusyMarkFull.clearAnimation();
                    mImgShowBusyMark.setVisibility(View.INVISIBLE);
                    mImgShowBusyMarkFull.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * 显示下方TTS播放图标
     */
    public void showTtsIcon() {
        if (mFloatView == null) {
            Logg.e(TAG, "showTtsIcon  float window null !!!");
            return;
        }
        mFloatView.post(new Runnable() {
            @Override
            public void run() {
                showExitHelpButton();
                hideBusyMark();
                mImgTtsIcon.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 隐藏下方TTS图标
     */
    public void hideTtsIcon() {
        if (mFloatView == null) {
            Logg.e(TAG, "hideTtsIcon  float window null !!!");
            return;
        }
        mFloatView.post(new Runnable() {
            @Override
            public void run() {
                mImgTtsIcon.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 显示上方退出图标/隐藏下方退出图标
     */
    public void showExitHelpButton() {
        if (mFloatView == null) {
            Logg.e(TAG, "showExitHelpButton  float window null !!!");
            return;
        }
        mFloatView.post(new Runnable() {
            @Override
            public void run() {
                if (isFull) {
                    mImgExitBottom.setVisibility(View.INVISIBLE);
                    mImgExitTop.setVisibility(View.VISIBLE);
                    mImgHelpTop.setVisibility(View.VISIBLE);
                } else {
                    mImgExitBottom.setVisibility(View.VISIBLE);
                    mImgExitTop.setVisibility(View.INVISIBLE);
                    mImgHelpTop.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /**
     * 根据音量大小更改悬浮窗下方动画幅度
     */
    public void updateVoiceWaveView(final int audioStrength) {
        if (mFloatView == null) {
            Logg.e(TAG, "updateVoiceWaveView  float window null !!!");
            return;
        }
        mFloatView.post(new Runnable() {
            @Override
            public void run() {

                if (mVoiceWaveView != null) {
                    Bundle bundle0 = new Bundle();
                    bundle0.putString(VOICE_WAVE_KEY_COLOR, "#800cd2f3");
                    bundle0.putInt(VOICE_WAVE_KEY_OFFSET, 30);
                    bundle0.putInt(VOICE_WAVE_KEY_TIME, 30);
                    bundle0.putFloat(VOICE_WAVE_KEY_RANGE, audioStrength);
                    mVoiceWaveView.updateDate(bundle0);
                }

                if (mVoiceWaveView2 != null) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(VOICE_WAVE_KEY_COLOR, "#98279fe5");
                    bundle1.putInt(VOICE_WAVE_KEY_OFFSET, 40);
                    bundle1.putInt(VOICE_WAVE_KEY_TIME, 50);
                    bundle1.putFloat(VOICE_WAVE_KEY_RANGE, audioStrength);
                    mVoiceWaveView2.updateDate(bundle1);
                }

                if (mVoiceWaveView3 != null) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(VOICE_WAVE_KEY_COLOR, "#983070cd");
                    bundle2.putInt(VOICE_WAVE_KEY_OFFSET, 50);
                    bundle2.putInt(VOICE_WAVE_KEY_TIME, 80);
                    bundle2.putFloat(VOICE_WAVE_KEY_RANGE, audioStrength);
                    mVoiceWaveView3.updateDate(bundle2);
                }
            }
        });
    }

    /**
     * 显示悬浮窗
     */
    public void showFloatWindow() {
        new Handler(mCtx.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                isFull = false;

                mFloatView = LayoutInflater.from(mCtx).inflate(R.layout.anim_mic_voice_layout, mLlJsContainer, false);

                mVoiceWaveView = (VoiceWaveView) mFloatView.findViewById(R.id.wave_view);
                mVoiceWaveView2 = (VoiceWaveView) mFloatView.findViewById(R.id.wave_view2);
                mVoiceWaveView3 = (VoiceWaveView) mFloatView.findViewById(R.id.wave_view3);

                mTvBottom = (TextView) mFloatView.findViewById(R.id.tv_show_sentence);
                mTvFull = (TextView) mFloatView.findViewById(R.id.tv_show_sentence_full);
                mTvTtsSay = (TextView) mFloatView.findViewById(R.id.tv_show_tts_say);
                mImgExitBottom = (ImageView) mFloatView.findViewById(R.id.iv_exit);
                mImgShowBusyMark = (ImageView) mFloatView.findViewById(R.id.iv_show_busymark);
                mImgShowBusyMarkFull = (ImageView) mFloatView.findViewById(R.id.iv_show_busymark_full);
                mImgTtsIcon = (ImageView) mFloatView.findViewById(R.id.iv_tts_play);

                mImgExitTop = (ImageView) mFloatView.findViewById(R.id.iv_exit_full);
                mImgHelpTop = (ImageView) mFloatView.findViewById(R.id.iv_help);

                mImgExitBottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
                        mDataCallBack.getFwDataBack(bundle);
                    }
                });

                mImgExitTop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_EXIT);
                        mDataCallBack.getFwDataBack(bundle);
                    }
                });

                mLlUpLayer = (FrameLayout) mFloatView.findViewById(R.id.fl_upLayer);

                mLlJsContainer = (LinearLayout) mFloatView.findViewById(R.id.ll_js_container);
                mNsShowContent = (NestedScrollView) mFloatView.findViewById(R.id.sv_show_content);
                mDivideLine = mFloatView.findViewById(R.id.viewshowbottom);

                mNsShowContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        //上滑 并且 正在显示底部栏
                        if (scrollY - oldScrollY > 0) {
                            mDivideLine.setVisibility(View.INVISIBLE);

                            //将Y属性变为底部栏高度  (相当于隐藏了)
                            mDivideLine.animate().translationY(mDivideLine.getHeight());
                        } else if (scrollY - oldScrollY < 0) {

                            mDivideLine.animate().translationY(0);
                            mDivideLine.setVisibility(View.VISIBLE);
                        }
                    }
                });

                mCircleAnim = AnimationUtils.loadAnimation(mCtx, R.anim.anim_round_rotate);
                LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
                mCircleAnim.setInterpolator(interpolator);

                Bundle bundle = new Bundle();
                bundle.putString(VOICE_WAVE_KEY_COLOR, "#70B0E0E6");
                bundle.putInt(VOICE_WAVE_KEY_OFFSET, 30);
                bundle.putInt(VOICE_WAVE_KEY_TIME, 30);
                bundle.putFloat(VOICE_WAVE_KEY_RANGE, 1 * 5f);
                mVoiceWaveView.updateDate(bundle);

                bundle = new Bundle();
                bundle.putString(VOICE_WAVE_KEY_COLOR, "#7090EE90");
                bundle.putInt(VOICE_WAVE_KEY_OFFSET, 40);
                bundle.putInt(VOICE_WAVE_KEY_TIME, 50);
                bundle.putFloat(VOICE_WAVE_KEY_RANGE, 1 * 5f);
                mVoiceWaveView2.updateDate(bundle);

                bundle = new Bundle();
                bundle.putString(VOICE_WAVE_KEY_COLOR, "#70EEE685");
                bundle.putInt(VOICE_WAVE_KEY_OFFSET, 50);
                bundle.putInt(VOICE_WAVE_KEY_TIME, 80);
                bundle.putFloat(VOICE_WAVE_KEY_RANGE, 1 * 5f);
                mVoiceWaveView3.updateDate(bundle);

                mWindowManager = (WindowManager) mCtx.getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams wmParams = getWindowParam(128);

                Bitmap bitmap = ScreenShotUtil.getScreenShot(mCtx, ScreenShotUtil.KEY_SMALL);

                if (bitmap != null) {
                    ((ImageView) mFloatView.findViewById(R.id.img_float_window_small)).setImageBitmap(bitmap);
                }

                showExitHelpButton();

                mWindowManager.addView(mFloatView, wmParams);

                //更改悬浮窗状态
                mFwStatus = 1;

                Logg.e(TAG, "showFloatWindow : add over");
                bundle = new Bundle();
                bundle.putString(FloatWindowConstant.MESSAGE_WHAT, FloatWindowConstant.MESSAGE_INIT);
                mDataCallBack.getFwDataBack(bundle);
            }
        });


    }

    /**
     * 悬浮窗转为全屏显示
     */
    private void changeToFullscreen() {

        if (mWindowManager == null) {
            mWindowManager = (WindowManager) mCtx.getSystemService(Context.WINDOW_SERVICE);
        }
        if (mFloatView == null) {
            Logg.e(TAG, " float window null !!!");
            return;
        }

        if (isFull) {
            return;
        }

        isFull = true;

        final WindowManager.LayoutParams wmParams = getWindowParam(720);

        mFloatView.post(new Runnable() {
            @Override
            public void run() {

                (mFloatView.findViewById(R.id.img_float_window_small)).setVisibility(View.INVISIBLE);

                ((ImageView) mFloatView.findViewById(R.id.img_float_window_large)).
                        setImageBitmap(
                                ScreenShotUtil.getScreenShot(
                                        mCtx,
                                        ScreenShotUtil.KEY_ALL
                                )
                        );
                mWindowManager.updateViewLayout(mFloatView, wmParams);

                //更改悬浮窗状态
                mFwStatus = 2;

                showExitHelpButton();
            }
        });

    }

    /**
     * 获取悬浮窗参数
     */
    private WindowManager.LayoutParams getWindowParam(int height) {

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                1280,
                height,
                WindowManager.LayoutParams.TYPE_SGM_BEGIN_SYSTEM_WINDOW, // the window type
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        // this will allow the navbar to run in an overlay on devices that support this
        lp.gravity = Gravity.START | Gravity.TOP;
        lp.x = 0;
        lp.y = 720 - height;
        lp.setTitle("BT Text Window");
        lp.windowAnimations = -1;

        return lp;
    }

    /**
     * 添加Wx
     */
    public void addWxView(int moduleIndex, final Map<String, Object> map, final String path) {

        mLlUpLayer.post(new Runnable() {
            @Override
            public void run() {
                mLlUpLayer.setVisibility(View.VISIBLE);
            }
        });

        int type = (int) map.get(FloatWindowConstant.MODULE_DISPLAY_TYPE);

        map.remove(FloatWindowConstant.MODULE_DISPLAY_TYPE);

        if (mLastWxId != -1L) {
            removeLastWx();
        }

        if (type == FloatWindowConstant.MODULE_DISPLAY_TYPE_NO) {
            mLastWxId = -1L;
            map.put("name", System.currentTimeMillis());
        } else {
            mLastWxId = System.currentTimeMillis();
            map.put("name", mLastWxId);
        }
        if (type == FloatWindowConstant.MODULE_DISPLAY_TYPE_FULL) {
            changeToFullscreen();
        }

        Logg.i(TAG, "addWxView: bo add data ->" + new Gson().toJson(map));
        Logg.i(TAG, "addWxView: bo add path-> " + path);


        switch (moduleIndex) {

            case FloatWindowConstant.MODULE_CALL:

                if (type == FloatWindowConstant.MODULE_DISPLAY_TYPE_NO) {
                    String tel_searchname_path = WeexApps.getAppListMgr().findVRCardPath("liteapp_voiceassistant_offline", "tel_searchname");
                    WxRvUtils.getFloatWindowViewNoShow(mLlUpLayer.getContext(), tel_searchname_path, map);
                    break;
                }

                mLlUpLayer.post(new Runnable() {
                    @Override
                    public void run() {
                        String tel_searchname_path = WeexApps.getAppListMgr().findVRCardPath("liteapp_voiceassistant_offline", "tel_searchname");
                        WxRvUtils.getFloatWindowView(mLlUpLayer.getContext(), mLlJsContainer, tel_searchname_path, map, new WxRvUtils.IJsLoadSuccessCallBack() {
                            @Override
                            public void onAdded() {
                                mNsShowContent.fullScroll(ScrollView.FOCUS_DOWN);
                                mDivideLine.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
                break;
            case FloatWindowConstant.MODULE_LIST:
                if (type == FloatWindowConstant.MODULE_DISPLAY_TYPE_NO) {
                    String tel_contact_list_path = WeexApps.getAppListMgr().findVRCardPath("liteapp_voiceassistant_offline", "tel_contact_list");
                    WxRvUtils.getFloatWindowViewNoShow(mLlUpLayer.getContext(), tel_contact_list_path, map);
                    break;
                }

                mLlUpLayer.post(new Runnable() {
                    @Override
                    public void run() {
                        String tel_contact_list_path = WeexApps.getAppListMgr().findVRCardPath("liteapp_voiceassistant_offline", "tel_contact_list");
                        WxRvUtils.getFloatWindowView(mLlUpLayer.getContext(), mLlJsContainer, tel_contact_list_path, map, new WxRvUtils.IJsLoadSuccessCallBack() {
                            @Override
                            public void onAdded() {
                                mNsShowContent.fullScroll(ScrollView.FOCUS_DOWN);
                                mDivideLine.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
                break;
        }

    }

    /**
     * 添加Wx
     */
    public void addOtherWx(final WxAddWxBean bean) {
        if (mFloatView == null || mLlJsContainer == null) {
            Logg.e(TAG, "coverWx: mFloatView == null||mLlJsContainer ==null !!!");
            return;
        }

        if (mLastWxId != -1L) {
            removeLastWx();
        }

        Logg.i(TAG, "addOtherWx: bo add data ->" + bean.getData());
        Logg.i(TAG, "addOtherWx: bo add path-> " + bean.getPath());
        mLlJsContainer.post(new Runnable() {
            @Override
            public void run() {

                if (!TextUtil.isEmpty(bean.getText())) {
                    showTtsPlay(bean.getText());
                }

                Map<String, Object> map = new HashMap<>();
                mLastWxId = System.currentTimeMillis();
                map.put("name", mLastWxId);
                map.put("data", bean.getData());

                if (TextUtil.isEmpty(bean.getPath())) {
                    return;
                }

                String name = bean.getName();
                /*
                String path = bean.getPath();
                int idx = -1;
                idx = path.indexOf("weex.js");
                if (idx != -1){
                    path = path.substring(0, idx);
                } else {
                    idx = path.indexOf(".js");
                    if (idx != -1){
                        path = path.substring(0, idx);
                    }
                }*/
                String tel_path = WeexApps.getAppListMgr().findVRCardPath("liteapp_voiceassistant_offline", name);

                if (bean.getType() == 1) {

                    changeToFullscreen();
                    WxRvUtils.getFloatWindowView(
                            mCtx,
                            mLlJsContainer,
                            tel_path,
                            map,
                            new WxRvUtils.IJsLoadSuccessCallBack() {
                                @Override
                                public void onAdded() {
                                    mNsShowContent.fullScroll(ScrollView.FOCUS_DOWN);
                                    mDivideLine.setVisibility(View.VISIBLE);
                                }
                            }
                    );
                } else {
                    WxRvUtils.getFloatWindowViewNoShow(
                            mCtx,
                            tel_path,
                            map
                    );
                }
            }
        });
    }

    /**
     * 清除对应Tag的Wx
     */
    public void removeWx(final WxRemoveWxBean bean) {
        if (mFloatView == null || mLlJsContainer == null) {
            Logg.e(TAG, "clearView: mFloatView == null||mLlJsContainer ==null !!!");
            return;
        }
        mLlJsContainer.post(new Runnable() {
            @Override
            public void run() {

                View view = mLlJsContainer.findViewWithTag(bean.getId());
                if (view == null || mCtx == null) {
                    Logg.i(TAG, "run: removeWx view or context null0!!!");
                    return;
                }
                mLlJsContainer.removeView(view);
            }
        });
    }

    /**
     * 清除上一个Wx的点击事件
     */
    private void removeLastWx() {
        if (mFloatView == null || mLlJsContainer == null) {
            Logg.e(TAG, "clearView: mFloatView == null||mLlJsContainer ==null !!!");
            return;
        }
        mLlJsContainer.post(new Runnable() {
            @Override
            public void run() {

                if (mLastWxId == -1L) {
                    return;
                }

                View view = mLlJsContainer.findViewWithTag(mLastWxId);
                if (view == null || mCtx == null) {
                    Logg.i(TAG, "run: removeWx view or context null1!!!");
                    return;
                }
                view.setDrawingCacheEnabled(true);
                int index = mLlJsContainer.indexOfChild(view);
                View v = new View(mCtx);
                v.setBackground(new BitmapDrawable(mCtx.getResources(), Bitmap.createBitmap(view.getDrawingCache())));
                v.setLayoutParams(new LinearLayout.LayoutParams(view.getWidth(), view.getHeight()));
                mLlJsContainer.addView(v, index);
                mLlJsContainer.removeView(view);
            }
        });
    }

    /**
     * 移除悬浮窗
     */
    public void removeFloatWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mFloatView != null && mFloatView.isAttachedToWindow()) {
                mWindowManager.removeViewImmediate(mFloatView);
                mVoiceWaveView = null;
                mVoiceWaveView2 = null;
                mVoiceWaveView3 = null;
                mWindowManager = null;
                mFloatView = null;

                //更改悬浮窗状态
                mFwStatus = 0;
            }
        }
    }

    /**
     * 获取悬浮窗状态
     */
    public int getStatus(){
        return mFwStatus;
    }
}
