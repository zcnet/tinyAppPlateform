package SourceService.Proxy;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class ISource extends TRpcProxyBase {
	public ISource() throws RpcError {super("SourceService.ISource");}
	public void Register(SourceService.SourceInfo sSourceInfo, ISourceHandler ObjSourceHandler, SourceService.SrcStatus eSrcStatus, int iDataLen) throws RpcError
	{
		String ___u = "17702E69-30CE-F8BA-C8C7-33F1F0CE6819";
		Value ___par = new Value();
		___par.put("SourceInfoN_sSourceInfo",sSourceInfo.___serialize());
		___par.put("Proxy::ISourceHandler::uid", TinyRPC.GetUniqueIdentifier());
		___par.put("Proxy::ISourceHandlerN_ObjSourceHandler", ObjSourceHandler.CppObject());
		___par.put("SrcStatusN_eSrcStatus",(int)eSrcStatus.value());
		___par.put("intN_iDataLen", iDataLen);

		Value out = new Value();
		String methodname = "Register";
		Invoke(___u, ___par, out, 1, methodname);
	}

	public void Deregister(SourceService.SourceInfo sSourceInfo, ISourceHandler ObjSourceHandler) throws RpcError
	{
		String ___u = "346A9F2B-93A5-0677-A3AD-25633775ECF2";
		Value ___par = new Value();
		___par.put("SourceInfoN_sSourceInfo",sSourceInfo.___serialize());
		___par.put("Proxy::ISourceHandler::uid", TinyRPC.GetUniqueIdentifier());
		___par.put("Proxy::ISourceHandlerN_ObjSourceHandler", ObjSourceHandler.CppObject());

		Value out = new Value();
		String methodname = "Deregister";
		Invoke(___u, ___par, out, 2, methodname);
	}

	public void SourceChangeToNext(SourceService.SourceInfo sSourceInfo, ISourceHandler ObjSourceHandler, boolean bActiveHMI) throws RpcError
	{
		String ___u = "40489A01-60D8-598D-DA63-519115D081FD";
		Value ___par = new Value();
		___par.put("SourceInfoN_sSourceInfo",sSourceInfo.___serialize());
		___par.put("Proxy::ISourceHandler::uid", TinyRPC.GetUniqueIdentifier());
		___par.put("Proxy::ISourceHandlerN_ObjSourceHandler", ObjSourceHandler.CppObject());
		___par.put("boolN_bActiveHMI", bActiveHMI);

		Value out = new Value();
		String methodname = "SourceChangeToNext";
		Invoke(___u, ___par, out, 3, methodname);
	}

	public void SourceChangeTo(SourceService.SourceInfo sSourceInfo, ISourceHandler ObjSourceHandler, String strSrcName, TRPCStream SrcData, boolean bActiveHMI) throws RpcError
	{
		String ___u = "D808BCCC-F7D7-89EE-C1B7-393F0C66F985";
		Value ___par = new Value();
		___par.put("SourceInfoN_sSourceInfo",sSourceInfo.___serialize());
		___par.put("Proxy::ISourceHandler::uid", TinyRPC.GetUniqueIdentifier());
		___par.put("Proxy::ISourceHandlerN_ObjSourceHandler", ObjSourceHandler.CppObject());
		___par.put("stringN_strSrcName", strSrcName);
		___par.put("streamN_SrcData", SrcData.Serialize());
		___par.put("boolN_bActiveHMI", bActiveHMI);

		Value out = new Value();
		String methodname = "SourceChangeTo";
		Invoke(___u, ___par, out, 4, methodname);
	}

	public void SetCurrentStatus(SourceService.SourceInfo sSourceInfo, ISourceHandler ObjSourceHandler, SourceService.SrcStatus eSrcStatus) throws RpcError
	{
		String ___u = "EC6AD3AE-F1E7-D547-CAAB-934DE631CF47";
		Value ___par = new Value();
		___par.put("SourceInfoN_sSourceInfo",sSourceInfo.___serialize());
		___par.put("Proxy::ISourceHandler::uid", TinyRPC.GetUniqueIdentifier());
		___par.put("Proxy::ISourceHandlerN_ObjSourceHandler", ObjSourceHandler.CppObject());
		___par.put("SrcStatusN_eSrcStatus",(int)eSrcStatus.value());

		Value out = new Value();
		String methodname = "SetCurrentStatus";
		Invoke(___u, ___par, out, 5, methodname);
	}

	public void SetCurrentData(SourceService.SourceInfo sSourceInfo, ISourceHandler ObjSourceHandler, TRPCStream SrcData, boolean bForce) throws RpcError
	{
		String ___u = "C01870A6-DB6B-DEC9-C5F1-702E56900027";
		Value ___par = new Value();
		___par.put("SourceInfoN_sSourceInfo",sSourceInfo.___serialize());
		___par.put("Proxy::ISourceHandler::uid", TinyRPC.GetUniqueIdentifier());
		___par.put("Proxy::ISourceHandlerN_ObjSourceHandler", ObjSourceHandler.CppObject());
		___par.put("streamN_SrcData", SrcData.Serialize());
		___par.put("boolN_bForce", bForce);

		Value out = new Value();
		String methodname = "SetCurrentData";
		Invoke(___u, ___par, out, 6, methodname);
	}

}
