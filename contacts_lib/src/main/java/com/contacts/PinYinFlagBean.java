package com.contacts;

/**
 * Created by sun on 2018/12/25
 */

class PinYinFlagBean {

    /**
     * type : 1
     * carrierOperator : 移动
     */

    private int type = -1;
    private String carrierOperator;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCarrierOperator() {
        return carrierOperator;
    }

    public void setCarrierOperator(String carrierOperator) {
        this.carrierOperator = carrierOperator;
    }
}
