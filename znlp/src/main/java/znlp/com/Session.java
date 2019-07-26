package znlp.com;

public class Session {
    private int mePtr = 0;
    public Session(){
        mePtr = this.createJni();
    }
    public void release(){ if(mePtr != 0)releaseJni(mePtr); }
    public int analyze(String str){ return analyzeJni(mePtr, str);}
    public String result(){ return resultJni(mePtr);}
    public void reset(){ resultJni(mePtr);}

    private native int createJni();
    private native void releaseJni(int ptr);
    private native int analyzeJni(int ptr, String str);
    private native String resultJni(int ptr);
    private native void resetJni(int ptr);
}
