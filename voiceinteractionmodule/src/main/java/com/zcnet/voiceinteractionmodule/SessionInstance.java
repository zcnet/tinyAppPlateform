package com.zcnet.voiceinteractionmodule;

import znlp.com.Pickuplist;
import znlp.com.Session;

/**
 * Created by sun on 2019/4/13
 */

public class SessionInstance {
    private static SessionInstance ourInstance;

    private Session session;

    public static SessionInstance getInstance() {
        synchronized (SessionInstance.class) {
            if (ourInstance == null) {
                ourInstance = new SessionInstance();
            }
        }
        return ourInstance;
    }

    private SessionInstance() {
    }

    String analyze(String str) {
        if (null == session) {
            session = new Session();
        }
        session.analyze(str);
        return session.result();
    }


    public void release() {
        clearPickuplist();
        session.release();
        session = null;
    }

    public void addToPickuplist(String str){
        Pickuplist.inst().append(str);
    }
    public void clearPickuplist(){
        Pickuplist.inst().clear();
    }

}
