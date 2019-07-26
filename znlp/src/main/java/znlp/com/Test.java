package znlp.com;

public class Test {
    public Test(){}
    public int unitTest(String filelist[], String logfile){ return unitTestJni( filelist, logfile);}

    private native  int unitTestJni(String filelist[], String logfile);
}
