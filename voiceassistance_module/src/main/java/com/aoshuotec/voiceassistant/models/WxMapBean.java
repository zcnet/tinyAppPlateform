package com.aoshuotec.voiceassistant.models;

public class WxMapBean {

    private double lat;
    private double lon;
    private String markerPath;
    private String pngPath;

    public WxMapBean(double lat, double lon, String markerPath, String pngPath) {
        this.lat = lat;
        this.lon = lon;
        this.markerPath = markerPath;
        this.pngPath = pngPath;
    }

    public String getPngPath() {
        return pngPath;
    }

    public void setPngPath(String pngPath) {
        this.pngPath = pngPath;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getMarkerPath() {
        return markerPath;
    }

    public void setMarkerPath(String markerPath) {
        this.markerPath = markerPath;
    }
}
