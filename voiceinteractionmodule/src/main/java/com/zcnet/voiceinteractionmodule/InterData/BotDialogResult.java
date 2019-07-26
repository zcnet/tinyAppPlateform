package com.zcnet.voiceinteractionmodule.InterData;

import com.zcnet.voiceinteractionmodule.usered.APIResult;

public class BotDialogResult extends APIResult {
    private String frontSessionRoundId;

    /**
     * @return the frontSessionRoundId
     */
    public String getFrontSessionRoundId() {
        return frontSessionRoundId;
    }

    /**
     * @param frontSessionRoundId
     *            the frontSessionRoundId to set
     */
    public void setFrontSessionRoundId(String frontSessionRoundId) {
        this.frontSessionRoundId = frontSessionRoundId;
    }
}
