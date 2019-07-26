package znlp.com;

/**
 * Created by pasio on 2019/1/21.
 */

public class ZSlot {

    public int getSlotCount() {
        return getSlotCountJni(mePtr);
    }

    public int getSlotMinLength() {
        return getSlotMinLengthJni(mePtr);
    }

    public int getSlotMaxLength() {
        return getSlotMaxLengthJni(mePtr);
    }

    public int getRegeItemCount() {
        return getRegeItemCountJni(mePtr);
    }

    public int getRegeItemStart() {
        return getRegeItemStartJni(mePtr);
    }

    public int getFlags() {
        return getFlagsJni(mePtr);
    }

    public String getSlotName() {
        return getSlotNameJni(mePtr);
    }


    public int getWildMinLength(){
        return getWildMinLengthJni(mePtr);
    }
    public int getWildMaxLength(){
        return getWildMaxLengthJni(mePtr);
    }
    public void dynamicDictAddItem(String str){
        dynamicDictAddItemJni(mePtr, str);
    }

    public ZSlot(int ptr){
        mePtr = ptr;
    }
    private int mePtr = 0;
    private native int getSlotCountJni(int ptr);
    private native int getSlotMinLengthJni(int ptr);
    private native int getSlotMaxLengthJni(int ptr);
    private native int getRegeItemCountJni(int ptr);
    private native int getRegeItemStartJni(int ptr);
    private native int getFlagsJni(int ptr);
    private native String getSlotNameJni(int ptr);
    private native int getWildMinLengthJni(int ptr);
    private native int getWildMaxLengthJni(int ptr);
    private native void dynamicDictAddItemJni(int ptr, String str);

}
