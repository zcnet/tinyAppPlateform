package znlp.com;

import java.util.HashMap;
import java.util.Map;

public class Tools {
    private  static Map<Character, String> numberMap = null;

    public static String normalizeNumber(String text, int type){
        if (null == numberMap) {
            numberMap = new HashMap<Character, String>();
            numberMap.put('零', "0");
            numberMap.put('一', "1");
            numberMap.put('二', "2");
            numberMap.put('三', "3");
            numberMap.put('四', "4");
            numberMap.put('六', "6");
            numberMap.put('七', "7");
            numberMap.put('八', "8");
            numberMap.put('九', "9");
        }
        String ret = new String();
        int len = text.length();
        for (int i = 0; i < len; ++ i){
            char c = text.charAt(i);
            String n = numberMap.get(c);
            if (n != null){
                ret += n;
            } else {
                ret += c;
            }
        }
        return ret;
    }
}
