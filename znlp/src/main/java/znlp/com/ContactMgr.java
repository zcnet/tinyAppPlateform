package znlp.com;

public class ContactMgr {
    static {
        System.loadLibrary("contact");    //so文件的名字
    }
    public static  ContactMgr inst(){
        return instance;
    }
    public  boolean isInited() { return this.inited;}
    public  void setInited(boolean inited){this.inited=inited;}
    public  void init(String json) {initJni(mePtr, json); }
    public  String find(String json){
        return findJni(mePtr, json);
    }

    ////////
    private boolean inited = false;
    private int mePtr = 0;
    private static ContactMgr instance = new ContactMgr();
    private ContactMgr(){
        mePtr = this.createJni();
    }
    private native int createJni();
    private native void initJni(int ptr, String json);
    private native String findJni(int ptr, String json);
}
