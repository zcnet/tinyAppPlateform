package com.zcnet.voiceinteractionmodule.InterData;

import java.io.Serializable;

public class voiceInterfaceBean_DialogIntent extends voiceInterfaceBaseBean implements Serializable {

    private voiceInterfaceSlotBean intent;


    public voiceInterfaceSlotBean getIntent() {
        return intent;
    }

    public void setIntent(voiceInterfaceSlotBean intent) {
        this.intent = intent;
    }
}
