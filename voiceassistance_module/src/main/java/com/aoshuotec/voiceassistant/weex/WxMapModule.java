package com.aoshuotec.voiceassistant.weex;

import android.content.Intent;
import android.graphics.Bitmap;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.aoshuotec.voiceassistant.application.MApplication;
import com.aoshuotec.voiceassistant.models.WxMapBean;
import com.aoshuotec.voiceassistant.models.WxPoiBean;
import com.aoshuotec.voiceassistant.utils.ImgUtils;
import com.aoshuotec.voiceassistant.utils.LocationUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.z.tinyapp.utils.common.TextUtil;
import com.z.tinyapp.utils.logs.sLog;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sun on 2019/3/28
 */

public class WxMapModule extends WXModule {

    private static final String TAG = "sunWxMapModule";

    private static final String SOURCE_APP = "VA";

    /**
     * 打开高德地图APP
     */
    @JSMethod(uiThread = false)
    public void openAMAP() {
        sLog.i(TAG, "openAMAP: ");
        Intent intent = new Intent();
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
        intent.putExtra("KEY_TYPE", 10034);
        intent.putExtra("SOURCE_APP", SOURCE_APP);
        MApplication.getContext().sendBroadcast(intent);
    }

    /**
     * 跳转高德地图直接导航
     */
    @JSMethod(uiThread = false)
    public void openAMAPWithData(String pointName, String lat, String lon, String entry_lat, String entry_lon, String dev, String style) {
        sLog.i(TAG, "openAMAPWithData: ");
        Intent intent = new Intent();
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
//        intent.putExtra("KEY_TYPE", 10038);
//        intent.putExtra("POINAME","厦门大学");
//        intent.putExtra("LAT",24.444593);
//        intent.putExtra("LON",118.101011);
//        intent.putExtra("ENTRY_LAT",24.444593);
//        intent.putExtra("ENTRY_LON",118.101011);
//        intent.putExtra("DEV",0);
//        intent.putExtra("STYLE",0);

        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
        intent.putExtra("KEY_TYPE", 10038);
        intent.putExtra("POINAME", pointName);
        intent.putExtra("LAT", lat);
        intent.putExtra("LON", lon);
        intent.putExtra("ENTRY_LAT", entry_lat);
        intent.putExtra("ENTRY_LON", entry_lon);
        intent.putExtra("DEV", dev);
        intent.putExtra("STYLE", style);
        intent.putExtra("SOURCE_APP", SOURCE_APP);
        MApplication.getContext().sendBroadcast(intent);
    }

    /**
     * 最小化高德地图APP，即高德地图进入后台
     */
    @JSMethod(uiThread = false)
    public void minApp() {
        sLog.i(TAG, "minApp: ");
        Intent intent = new Intent();
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
        intent.putExtra("KEY_TYPE", 10031);
        MApplication.getContext().sendBroadcast(intent);
    }

    /**
     * 关闭高德地图APP
     */
    @JSMethod(uiThread = false)
    public void closeAMAP() {
        sLog.i(TAG, "closeAMAP: ");
        Intent intent = new Intent();
        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
        intent.putExtra("KEY_TYPE", 10021);
        MApplication.getContext().sendBroadcast(intent);
    }

    /**
     * 添加Marker
     */
    @JSMethod(uiThread = false)
    public void addMarker(String locationMarkerPath, String data) {

        sLog.i(TAG, "addMarker: ---> start " + locationMarkerPath);

        if (WxMapComponent.sMapView != null) {
            WxMapComponent.sMapView.getMap().clear();
        }

        List<WxMapBean> list = new Gson().fromJson(data, new TypeToken<List<WxMapBean>>() {
        }.getType());

        LatLngBounds.Builder lngBounds = new LatLngBounds.Builder();

        for (WxMapBean bean : list) {

            final LatLng latLng = new LatLng(bean.getLat(), bean.getLon());
            lngBounds.include(latLng);
            String pngPath = bean.getPngPath();
            String markerPath = bean.getMarkerPath();

            if (TextUtil.isEmpty(pngPath) && !TextUtil.isEmpty(markerPath)) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.draggable(false);
                markerOptions.icon(BitmapDescriptorFactory.fromPath(markerPath));
                WxMapComponent.sMapView.getMap().addMarker(markerOptions);
                sLog.i(TAG, "addMarker: ---> " + markerPath);
                continue;
            }
            ImgUtils.composeImage(bean.getMarkerPath(), bean.getPngPath(), new JSCallback() {
                @Override
                public void invoke(Object data) {

                }

                @Override
                public void invokeAndKeepAlive(Object data) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.draggable(false);
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap((Bitmap) data));
                    sLog.i(TAG, "addMarker: --invokeAndKeepAlive -> ");
                    if (WxMapComponent.sMapView != null && WxMapComponent.sMapView.getMap() != null) {
                        WxMapComponent.sMapView.getMap().addMarker(markerOptions);
                    } else {
                        sLog.i("sunMapModule", "invokeAndKeepAlive: WxMapComponent.sMapView.getMap() == null !!");
                    }
                }
            });
        }

        WxMapComponent.sMapView.getMap().animateCamera(CameraUpdateFactory.newLatLngBounds(lngBounds.build(), 70));
        sLog.i(TAG, "addMarker: --animate Camera -> ");

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(LocationUtils.getsInstance().getLat(), LocationUtils.getsInstance().getLon()));
        markerOptions.draggable(false);
        markerOptions.icon(BitmapDescriptorFactory.fromPath(locationMarkerPath));
        WxMapComponent.sMapView.getMap().addMarker(markerOptions);
    }

    /**
     * 关键字检索
     * https://lbs.amap.com/api/android-sdk/guide/map-data/poi/
     */
    @JSMethod(uiThread = false)
    public void poiSearch(String str, final String poiCode, String cityCode, int pageSize, int pageNum, final JSCallback poiSearch, final JSCallback poiItemSearch) {
        sLog.i(TAG, "poiSearch: start");
        PoiSearch.Query query = new PoiSearch.Query(str, poiCode, cityCode);
        query.setPageSize(pageSize);
        query.setPageNum(pageNum);
        PoiSearch search = new PoiSearch(MApplication.getContext(), query);
        search.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                sLog.i(TAG, "poiSearch: middle"+i+"\n"+new Gson().toJson(poiResult));

                WxPoiBean bean = new WxPoiBean();
                bean.setPageCount(poiResult.getPageCount());
                List<WxPoiBean.PoiItemBean> poiList = new ArrayList<>();
                WxPoiBean.PoiItemBean poiItemBean;
                PoiItem poiItem;
                for (int i1 = 0; i1 < poiResult.getPois().size(); i1++) {

                    poiItem = poiResult.getPois().get(i1);
                    poiItemBean = new WxPoiBean.PoiItemBean();
                    poiItemBean.setTypeCode(poiItem.getTypeCode());
                    poiItemBean.setAdCode(poiItem.getAdCode());
                    poiItemBean.setCityCode(poiItem.getCityCode());
                    poiItemBean.setTypeDes(poiItem.getTypeDes());
                    poiItemBean.setDistance(poiItem.getDistance());
                    poiItemBean.setLatLonPoint(new WxPoiBean.PoiItemBean.LatLonPointBean(
                            poiItem.getLatLonPoint().getLatitude(),
                            poiItem.getLatLonPoint().getLongitude()
                    ));
                    poiItemBean.setTitle(poiItem.getTitle());
                    poiItemBean.setSnippet(poiItem.getSnippet());
                    poiItemBean.setProvinceName(poiItem.getProvinceName());
                    poiItemBean.setCityName(poiItem.getCityName());
                    poiItemBean.setAdName(poiItem.getAdName());
                    poiList.add(poiItemBean);
                }
                bean.setPoiItem(poiList);

                Map<String, String> map = new HashMap<>();
                map.put("data", new Gson().toJson(bean));
                map.put("code", String.valueOf(i));
                poiSearch.invokeAndKeepAlive(new Gson().toJson(map));
                sLog.i(TAG, "poiSearch: end");
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
                sLog.i(TAG, "poiSearch: onPoiItemSearched end");

                Map<String, String> map = new HashMap<>();
                map.put("data", new Gson().toJson(poiItem));
                map.put("code", String.valueOf(i));
                poiItemSearch.invokeAndKeepAlive(new Gson().toJson(map));
            }
        });
        search.searchPOIAsyn();
    }

    /**
     * 计算两点距离
     */
    @JSMethod(uiThread = false)
    public void calculateDistance(double endLat, double endLon, JSCallback callback) {
        float temp = AMapUtils.calculateLineDistance(new LatLng(LocationUtils.getsInstance().getLat(), LocationUtils.getsInstance().getLon()), new LatLng(endLat, endLon));
        if (callback != null) {
            callback.invokeAndKeepAlive(temp);
        }
    }

}
