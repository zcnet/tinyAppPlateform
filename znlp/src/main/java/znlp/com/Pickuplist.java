package znlp.com;

public class Pickuplist {
    public static  Pickuplist inst(){
        return instance;
    }
    public void append(String str){
        appendJni(mePtr, str);
    }
    public void clear(){
        clearJni(mePtr);
    }

    ////////
    private int mePtr = 0;
    private static Pickuplist instance = new Pickuplist();
    private Pickuplist(){
        mePtr = this.createJni();
    }
    private native int createJni();
    private native void appendJni(int ptr, String str);
    private native void clearJni(int ptr);
}
