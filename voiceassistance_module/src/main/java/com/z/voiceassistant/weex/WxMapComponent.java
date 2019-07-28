package com.z.voiceassistant.weex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

import com.amap.api.maps.MapView;
import com.z.tinyapp.utils.logs.sLog;
import com.sun.weexandroid_module.WxRvUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;

//import com.taobao.weex.dom.WXDomObject;


/**
 * Created by sun on 2019/3/28
 */

public class WxMapComponent extends WXComponent<MapView> {

    private static final String TAG = "sunWxMapComponent";

    public static MapView sMapView;

    public WxMapComponent(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance,  parent,  basicComponentData);
    }

    public WxMapComponent(WXSDKInstance instance, WXVContainer parent, int type, BasicComponentData basicComponentData) {
        super( instance,  parent,  type,  basicComponentData);
    }

    @Override
    protected MapView initComponentHostView(@NonNull Context context) {
        sLog.i(TAG, "initComponentHostView: --> create");
        MapView mapView = new MapView(context);
        mapView.onCreate(null);
        mapView.getMap().getUiSettings().setZoomControlsEnabled(false);
        mapView.getMap().getUiSettings().setAllGesturesEnabled(false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(420, 442);
        mapView.setLayoutParams(params);
        sMapView = mapView;
        sLog.i(TAG, "initComponentHostView: --> return");

        WxRvUtils.send2Wx("map_create");

        return mapView;
    }

    @Override
    public void destroy() {
        super.destroy();
        sLog.i(TAG, "initComponentHostView: --> destroy");
        getHostView().onDestroy();
    }
}
