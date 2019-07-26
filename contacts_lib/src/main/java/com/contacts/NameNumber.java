package com.contacts;

import java.io.Serializable;

/**
 * Created by sun on 2018/8/21
 */

class NameNumber implements Serializable {
    //声母
    int s = 0;
    //韵母
    int y = 0;
    //数字
    int x = -1;
    //是否已经匹配到
    boolean isSame = false;

    void clear() {
        isSame = false;
    }

    @Override
    public String toString() {
        return "NameNumber{" + "s=" + s + ", y=" + y + '}';
    }
}
