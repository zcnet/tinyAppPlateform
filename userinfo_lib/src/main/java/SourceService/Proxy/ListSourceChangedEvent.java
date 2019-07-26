package SourceService.Proxy;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class ListSourceChangedEvent extends TRpcEventBase {
	public ListSourceChangedEvent(boolean bRegist) throws RpcError
	{
		if (bRegist)
			StartListen("SourceService.ListSourceChangedEvent");
	}

	public void StartListen()  throws RpcError 
	{
		StartListen("SourceService.ListSourceChangedEvent");
	}

	public void EndListen() throws RpcError
	{
		EndListen("SourceService.ListSourceChangedEvent");
	}

	public void ForceTriggerLastEvent() throws RpcError
	{
		ForceTriggerLastEvent("SourceService.ListSourceChangedEvent");
	}

	public void OnTriggered(Value ___input)
	{
		Value ___par = ___input.getValue("parameters");

		String strSrcName;
		strSrcName = ___par.getString("stringN_strSrcName");
		SourceService.SrcStatus eSrcStatus;
		eSrcStatus = SourceService.SrcStatus.valueOf(___par.getInt("SrcStatusN_eSrcStatus"));
		OnListSourceChangedEventTriggered(strSrcName, eSrcStatus);
	}

	public void OnListSourceChangedEventTriggered(String strSrcName, SourceService.SrcStatus eSrcStatus)
	{
		// TODO: write your code for implementation
	}

}
