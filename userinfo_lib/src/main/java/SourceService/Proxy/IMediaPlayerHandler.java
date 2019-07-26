package SourceService.Proxy;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class IMediaPlayerHandler extends TCallBackBase {
	private static boolean ___bRegistered = false;
	public IMediaPlayerHandler() throws RpcError {
		TRPCInterface cInterface = TRPCHostMgr.Inst().RegisterObservableInterface("SourceService.IMediaPlayerHandler");
		if (!___bRegistered)
		{
			___bRegistered = true;
			String ___u1 = "B81A02B5-EC80-B853-B28A-197F4DCF6547";
			cInterface.AddMethod(___u1, 1);
		}
		cInterface.AddObservableInstance("SourceService.IMediaPlayerHandler", this.CppObject());
	}

	public void OnSourceActivated(String strSrcName, boolean bActiveHMI, TRPCStream SrcData) throws RpcError
	{
	}

	static int ___IMediaPlayerHandler_OnSourceActivated_obsvhdr(Object ___obj, Value ___input, Value ___output) throws RpcError
	{
		Value ___par = ___input.getValue("parameters");

		String strSrcName;
		strSrcName = ___par.getString("stringN_strSrcName");
		boolean bActiveHMI;
		bActiveHMI = ___par.getBoolean("boolN_bActiveHMI");
		TRPCStream SrcData = new TRPCStream();
	SrcData.Deserialize(___par.getString("streamN_SrcData"));
		((IMediaPlayerHandler)___obj).OnSourceActivated(strSrcName, bActiveHMI, SrcData);
		return 0;
	}

	public int obsvhdr(Value ___input, Value ___output)  throws RpcError {
		Value ___par = ___input.getValue("parameters");
		int id = ___input.getInt("methodid");
		switch (id) {
			case 1:
				return ___IMediaPlayerHandler_OnSourceActivated_obsvhdr(this, ___input, ___output);
			default:break;
		}
		return 0;
	}
}
