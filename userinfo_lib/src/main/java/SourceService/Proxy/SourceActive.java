package SourceService.Proxy;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class SourceActive extends TRpcEventBase {
	public SourceActive(boolean bRegist) throws RpcError
	{
		if (bRegist)
			StartListen("SourceService.SourceActive");
	}

	public void StartListen()  throws RpcError 
	{
		StartListen("SourceService.SourceActive");
	}

	public void EndListen() throws RpcError
	{
		EndListen("SourceService.SourceActive");
	}

	public void ForceTriggerLastEvent() throws RpcError
	{
		ForceTriggerLastEvent("SourceService.SourceActive");
	}

	public void OnTriggered(Value ___input)
	{
		Value ___par = ___input.getValue("parameters");

		String strSrcName;
		strSrcName = ___par.getString("stringN_strSrcName");
		boolean bActiveHMI;
		bActiveHMI = ___par.getBoolean("boolN_bActiveHMI");
		TRPCStream SrcData = new TRPCStream();
	SrcData.Deserialize(___par.getString("streamN_SrcData"));
		OnSourceActiveTriggered(strSrcName, bActiveHMI, SrcData);
	}

	public void OnSourceActiveTriggered(String strSrcName, boolean bActiveHMI, TRPCStream SrcData)
	{
		// TODO: write your code for implementation
	}

}
