package znlp.com;

/**
 * Created by pasio on 2019/1/21.
 */

public class ZParser {
    static {
        System.loadLibrary("znlpif");    //so文件的名字

    }
    public int parse(String filelist[]){
        return parseJni(mePtr, filelist);
    }
    public int save(String filename){
        return saveJni(mePtr, filename);
    }
    public int header() {
        return headerJni(mePtr);
    }

    public void destroy(){
        destroyJni(mePtr);
    }

    public ZParser(){
        mePtr = createJni();
    }

    private int mePtr = 0;
    private native int createJni();
    private native void destroyJni(int ptr);
    private native int parseJni(int ptr, String filelist[]);
    private native int saveJni(int ptr, String filename);
    private native int headerJni(int ptr);
}
