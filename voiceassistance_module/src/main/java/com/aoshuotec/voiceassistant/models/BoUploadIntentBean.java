package com.aoshuotec.voiceassistant.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2018/12/19
 */

public class BoUploadIntentBean {

    /**
     * accessToken : string
     * idpUserId : onwerclub209
     * sessionId : ee87090693eb4bfc9253b512d617b65e
     * botId : 14509
     * intent : {"intentName":"MAKE_CALL","slots":[]}
     * reqParam : [{"key":"GPS","value":"{\"lon\":\"121.11\",\"lat\":\"31.222\",\"address\":\"上海浦东新区\"}"},{"key":"BLE","value":"{\"isOpen\":\"on\"}"},{"key":"contactSync","value":"{\"isSync\":\"on\"}"},{"key":"callStatus","value":"{\" callStatus \":\"\"}"},{"key":"phoneNum","value":"{\"phoneNum\":\"13817541521\"}"}]
     */

    private String accessToken;
    private String idpUserId;
    private String sessionId;
    private String botId;
    private IntentBean intent;
    private List<ReqParamBean> reqParam;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getIdpUserId() {
        return idpUserId;
    }

    public void setIdpUserId(String idpUserId) {
        this.idpUserId = idpUserId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getBotId() {
        return botId;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public IntentBean getIntent() {
        return intent;
    }

    public void setIntent(IntentBean intent) {
        this.intent = intent;
    }

    public List<ReqParamBean> getReqParam() {
        return reqParam;
    }

    public void setReqParam(List<ReqParamBean> reqParam) {
        this.reqParam = reqParam;
    }

    public static class IntentBean {
        /**
         * intentName : MAKE_CALL
         * slots : []
         */

        private String intentName;
        private List<String> slots= new ArrayList<>();

        public String getIntentName() {
            return intentName;
        }

        public void setIntentName(String intentName) {
            this.intentName = intentName;
        }

        public List<String> getSlots() {
            return slots;
        }

        public void setSlots(List<String> slots) {
            this.slots = slots;
        }
    }

    public static class ReqParamBean {
        /**
         * key : GPS
         * value : {"lon":"121.11","lat":"31.222","address":"上海浦东新区"}
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}