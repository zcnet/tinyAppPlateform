package com.z.voiceassistant.models;

import java.util.List;

/**
 * Created by sun on 2018/12/4
 */

public class WxCallLogBean {
    /**
     * count : 2
     * content : [{"callId":-1,"callTime":"13/26/29","nameFirst":"张","nameLast":"三","telNum":"1388888888"},{"callId":7,"callTime":"11/10/50","nameFirst":"李","nameLast":"四","telNum":"1389999999"}]
     */

    private int count;
    private List<ContentBean> content;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * callId : -1
         * callTime : 13/26/29
         * nameFirst : 张
         * nameLast : 三
         * telNum : 1388888888
         */

        private int callId;
        private String callTime;
        private String nameFirst;
        private String nameLast;
        private String telNum;
        private String callDate;

        public int getCallId() {
            return callId;
        }

        public void setCallId(int callId) {
            this.callId = callId;
        }

        public String getCallTime() {
            return callTime;
        }

        public void setCallTime(String callTime) {
            this.callTime = callTime;
        }

        public String getNameFirst() {
            return nameFirst;
        }

        public void setNameFirst(String nameFirst) {
            this.nameFirst = nameFirst;
        }

        public String getNameLast() {
            return nameLast;
        }

        public void setNameLast(String nameLast) {
            this.nameLast = nameLast;
        }

        public String getTelNum() {
            return telNum;
        }

        public void setTelNum(String telNum) {
            this.telNum = telNum;
        }

        public String getCallDate() {
            return callDate;
        }

        public void setCallDate(String callDate) {
            this.callDate = callDate;
        }
    }
}
