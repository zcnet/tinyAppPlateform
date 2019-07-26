package SourceService.Proxy;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class SourceServiceStatusChangeEvent extends TRpcEventBase {
	public SourceServiceStatusChangeEvent(boolean bRegist) throws RpcError
	{
		if (bRegist)
			StartListen("SourceService.SourceServiceStatusChangeEvent");
	}

	public void StartListen()  throws RpcError 
	{
		StartListen("SourceService.SourceServiceStatusChangeEvent");
	}

	public void EndListen() throws RpcError
	{
		EndListen("SourceService.SourceServiceStatusChangeEvent");
	}

	public void ForceTriggerLastEvent() throws RpcError
	{
		ForceTriggerLastEvent("SourceService.SourceServiceStatusChangeEvent");
	}

	public void OnTriggered(Value ___input)
	{
		Value ___par = ___input.getValue("parameters");

		SourceService.ServiceStatus eStatus;
		eStatus = SourceService.ServiceStatus.valueOf(___par.getInt("ServiceStatusN_eStatus"));
		OnSourceServiceStatusChangeEventTriggered(eStatus);
	}

	public void OnSourceServiceStatusChangeEventTriggered(SourceService.ServiceStatus eStatus)
	{
		// TODO: write your code for implementation
	}

}
