package com.zcnet.voiceinteractionmodule.common;

import java.io.Serializable;

public class Style extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer style;

    public Style() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStyle() {
        return this.style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public Object getKey() {
        return this.getId();
    }

    public void setKey(Object key) {
        this.setId((Long)key);
    }

}
