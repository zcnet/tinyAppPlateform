package com.zcnet.voiceinteractionmodule.InterData;

public class frontInfoData {
    public frontInfoData(int btConnected, int contactBookSync) {
        this.btConnected = btConnected;
        this.contactBookSync = contactBookSync;
    }

    private int btConnected;
    private int contactBookSync;

    public int getBtConnected() {
        return btConnected;
    }

    public void setBtConnected(int btConnected) {
        this.btConnected = btConnected;
    }

    public int getContactBookSync() {
        return contactBookSync;
    }

    public void setContactBookSync(int contactBookSync) {
        this.contactBookSync = contactBookSync;
    }
}
