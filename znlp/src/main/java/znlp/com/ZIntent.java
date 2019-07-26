package znlp.com;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pasio on 2019/1/21.
 */

public class ZIntent {
    public static ZIntent create(int ptr){
        if (ptr == 0)
            return null;
        if (sMap.get(ptr) != null){
            return sMap.get(ptr);
        } else {
            sMap.put(ptr, new ZIntent(ptr));
        }
        return new ZIntent(ptr);
    }

    // note: this method is not locked
    public ZSlot getSlot(String slotName){
        return new ZSlot(getSlotJni(mePtr, slotName));
    }
    public String getIntentName(){
        return getIntentNameJni(mePtr);
    }
    public int getSlotCount(){
        return getSlotCountJni(mePtr);
    }


    private static  Map<Integer, ZIntent> sMap = new HashMap<>();
    private  int mePtr = 0;
    private ZIntent(int ptr){
        this.mePtr = ptr;
    }
    private native int getSlotJni(int ptr, String slotName);
    private native String getIntentNameJni(int ptr);
    private native int getSlotCountJni(int ptr);

}
