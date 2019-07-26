package com.zcnet.voice_callback_lib.voice_data;

public class VABaseNlpInfo {
    private Integer isQuestion;
    private Integer isPolite;
    private Integer emotion;

    public Integer getIsQuestion() {return isQuestion;}
    public void setIsQuestion(Integer isQuestion) {this.isQuestion = isQuestion;}

    public Integer getIsPolite() {return isPolite;}
    public void setIsPolite(Integer isPolite) {this.isPolite = isPolite;}

    public Integer getEmotion() {return emotion;}
    public void setEmotion(Integer emotion) {this.emotion = emotion;}

    public String toString() {
        return "VABaseNlpInfo [isQuestion=" + isQuestion + ", isPolite=" + isPolite
                + ", emotion=" + emotion + "]";
    }
}
