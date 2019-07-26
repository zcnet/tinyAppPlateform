package com.zcnet.voiceinteractionmodule.common;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.Serializable;
import java.util.Date;

public abstract class Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date createTime;
    private Long createUserId;
    private Date updateTime;
    private Long updateUserId;
    private Character deleteFlag;

    public Entity() {
    }

    public Character getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(Character deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public abstract Object getKey();

    public abstract void setKey(Object var1);

    public String getTableName() {
        StringBuilder tableName = new StringBuilder("tb_");
        String simpleName = this.getClass().getSimpleName();
        int i = 0;

        int j;
        for(j = 1; j < simpleName.length(); ++j) {
            if (Character.isUpperCase(simpleName.charAt(j))) {
                tableName.append(simpleName.substring(i, j).toLowerCase());
                tableName.append("_");
                i = j;
            }
        }

        tableName.append(simpleName.substring(i, j).toLowerCase());
        return tableName.toString();
    }



    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return this.updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

}
