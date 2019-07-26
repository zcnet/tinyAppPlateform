package com.zcnet.voiceinteractionmodule.util;

import java.util.Collection;
import java.util.Map;

public class CheckUtil {
    public CheckUtil() {
    }

    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return "".equals(obj);
        } else if (obj instanceof Object[]) {
            return ((Object[]) ((Object[]) obj)).length == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).size() == 0;
        } else if (obj instanceof Map) {
            return ((Map) obj).size() == 0;
        } else {
            return false;
        }
    }
}
