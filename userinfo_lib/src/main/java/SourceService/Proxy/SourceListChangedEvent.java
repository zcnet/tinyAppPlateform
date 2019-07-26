package SourceService.Proxy;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class SourceListChangedEvent extends TRpcEventBase {
	public SourceListChangedEvent(boolean bRegist) throws RpcError
	{
		if (bRegist)
			StartListen("SourceService.SourceListChangedEvent");
	}

	public void StartListen()  throws RpcError 
	{
		StartListen("SourceService.SourceListChangedEvent");
	}

	public void EndListen() throws RpcError
	{
		EndListen("SourceService.SourceListChangedEvent");
	}

	public void ForceTriggerLastEvent() throws RpcError
	{
		ForceTriggerLastEvent("SourceService.SourceListChangedEvent");
	}

	public void OnTriggered(Value ___input)
	{
		Value ___par = ___input.getValue("parameters");

		OnSourceListChangedEventTriggered();
	}

	public void OnSourceListChangedEventTriggered()
	{
		// TODO: write your code for implementation
	}

}
