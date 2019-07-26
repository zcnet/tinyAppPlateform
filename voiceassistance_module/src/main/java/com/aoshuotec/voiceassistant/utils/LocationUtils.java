package com.aoshuotec.voiceassistant.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.aoshuotec.voiceassistant.constants.LocationConstant;
import com.aoshuotec.voiceassistant.listener.IDataCallBack;

/**
 * Created by sun on 2018/12/20
 */
public class LocationUtils {

    public static final String TAG = "sunLocation";

    private static LocationUtils sInstance = new LocationUtils();

    private Context mContext;
    private IDataCallBack mCallBack;

    private double mLat = 31.23691;
    private double mLon = 121.50109;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Logg.i(TAG, "onLocationChanged: ");
            mLat = location.getLatitude();
            mLon = location.getLongitude();
            sendLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    public static LocationUtils getsInstance() {
        synchronized (LocationUtils.class) {
            if (sInstance == null) {
                sInstance = new LocationUtils();
            }
        }
        return sInstance;
    }

    private LocationUtils() {

    }

    public void register(Context context, IDataCallBack callBack) {
        mContext = context;
        mCallBack = callBack;
        initLocation();
    }

    private void initLocation() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        // 获取所有可用的位置提供器
        if (locationManager == null) {
            Logg.i(TAG, "initLocation: location manager null");
            return;
        }

        if (locationManager.getAllProviders() == null || locationManager.getAllProviders().size() == 0) {
            Logg.i(TAG, "initLocation: location manager null");
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
    }

    private void sendLocation(Location location) {

        if (location == null) {
            Logg.i(TAG, "sendLocation: location null");
            return;
        }

        if (location.getLongitude() != 0) {
            mLon = location.getLongitude();
        }

        if (location.getLatitude() != 0) {
            mLat = location.getLatitude();
        }

        Bundle bundle = new Bundle();
        bundle.putDouble(LocationConstant.MESSAGE_LONGITUDE, location.getLongitude());
        bundle.putDouble(LocationConstant.MESSAGE_LATITUDE, location.getLatitude());
        mCallBack.getLocationDataBack(bundle);
    }

    public double getLat() {
        return mLat;
    }

    public double getLon() {
        return mLon;
    }
}
