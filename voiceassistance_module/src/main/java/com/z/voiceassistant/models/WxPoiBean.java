package com.z.voiceassistant.models;

import java.util.List;

/**
 * Created by sun on 2019/4/10
 */

public class WxPoiBean {


    /**
     * pageCount : 30
     * poiItem : [{"typeCode":"190302","adCode":"310114","cityCode":"021","typeDes":"姓名地址","distance":-1,"latLonPoint":{"lat":1.1,"lon":1.1},"title":"AAA","snippet":"浦东","provinceName":"上海","cityName":"上海","adName":"浦东"}]
     */

    private int pageCount;
    private List<PoiItemBean> poiItem;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<PoiItemBean> getPoiItem() {
        return poiItem;
    }

    public void setPoiItem(List<PoiItemBean> poiItem) {
        this.poiItem = poiItem;
    }

    public static class PoiItemBean {
        /**
         * typeCode : 190302
         * adCode : 310114
         * cityCode : 021
         * typeDes : 姓名地址
         * distance : -1
         * latLonPoint : {"lat":1.1,"lon":1.1}
         * title : AAA
         * snippet : 浦东
         * provinceName : 上海
         * cityName : 上海
         * adName : 浦东
         */

        private String typeCode;
        private String adCode;
        private String cityCode;
        private String typeDes;
        private int distance;
        private LatLonPointBean latLonPoint;
        private String title;
        private String snippet;
        private String provinceName;
        private String cityName;
        private String adName;

        public String getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        public String getAdCode() {
            return adCode;
        }

        public void setAdCode(String adCode) {
            this.adCode = adCode;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getTypeDes() {
            return typeDes;
        }

        public void setTypeDes(String typeDes) {
            this.typeDes = typeDes;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public LatLonPointBean getLatLonPoint() {
            return latLonPoint;
        }

        public void setLatLonPoint(LatLonPointBean latLonPoint) {
            this.latLonPoint = latLonPoint;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSnippet() {
            return snippet;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAdName() {
            return adName;
        }

        public void setAdName(String adName) {
            this.adName = adName;
        }

        public static class LatLonPointBean {
            public LatLonPointBean(double lat, double lon) {
                this.lat = lat;
                this.lon = lon;
            }

            /**
             * lat : 1.1
             * lon : 1.1
             */


            private double lat;
            private double lon;

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
        }
    }
}
