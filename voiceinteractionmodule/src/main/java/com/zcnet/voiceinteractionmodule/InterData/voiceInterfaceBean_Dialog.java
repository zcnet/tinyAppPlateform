package com.zcnet.voiceinteractionmodule.InterData;

import java.io.Serializable;

public class voiceInterfaceBean_Dialog extends voiceInterfaceBaseBean implements Serializable {

    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
