package znlp.com;

/**
 * Created by pasio on 2019/1/21.
 */

public class ModelMgr {
    static {
        System.loadLibrary("znlpif");    //so文件的名字

    }
    public static  ModelMgr inst(){
        return instance;
    }
    public static  void Destroy(){
        if (instance != null && instance.mePtr != 0) {
            instance.destroyJni(instance.mePtr);
        }
        instance = null;
    }
    public int addMemoryModel(int memPtr){
        return addMemoryModelJni(mePtr, memPtr);
    }
    public int addModel(String modelFile){ return addModelJni(mePtr, modelFile); }
    public int addBufferModel(byte[] buf){ return addBufferModelJni(mePtr, buf);}
    public String analyze(String str){
        return analyzeJni(mePtr, str);
    }
    public ZIntent getIntent(String name){
        return  ZIntent.create(getIntentJni(mePtr, name));
    }

    private int mePtr = 0;
    private static ModelMgr instance = new ModelMgr();
    private ModelMgr(){
        mePtr = this.createJni();
    }
    private native int createJni();
    private native void destroyJni(int ptr);
    private native int addMemoryModelJni(int ptr, int memPtr);
    private native  int addModelJni(int ptr, String modelFile);
    private native  int addBufferModelJni(int ptr, byte[] buf);
    private native String analyzeJni(int ptr, String str);
    private native int getIntentJni(int ptr, String name);

}
