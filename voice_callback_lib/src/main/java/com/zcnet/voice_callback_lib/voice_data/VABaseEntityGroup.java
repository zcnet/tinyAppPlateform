package com.zcnet.voice_callback_lib.voice_data;

import java.util.List;

public class VABaseEntityGroup {
    private String name;
    private String must;
    private Integer leastFillSlot;
    private List<VABaseEntityGroupEntity> slots;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getMust() {return must;}
    public void setMust(String must) {this.must = must;}

    public Integer getLeastFillSlot() {return leastFillSlot;}
    public void setLeastFillSlot(Integer leastFillSlot) {this.leastFillSlot = leastFillSlot;}

    public List<VABaseEntityGroupEntity> getSlots() {return slots;}
    public void setSlots(List<VABaseEntityGroupEntity> slots) {this.slots = slots;}
}
