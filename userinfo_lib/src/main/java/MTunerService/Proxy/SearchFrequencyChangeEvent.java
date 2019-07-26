package MTunerService.Proxy;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class SearchFrequencyChangeEvent extends TRpcEventBase {
	public SearchFrequencyChangeEvent(boolean bRegist) throws RpcError
	{
		if (bRegist)
			StartListen("MTunerService.SearchFrequencyChangeEvent");
	}

	public void StartListen()  throws RpcError 
	{
		StartListen("MTunerService.SearchFrequencyChangeEvent");
	}

	public void EndListen() throws RpcError
	{
		EndListen("MTunerService.SearchFrequencyChangeEvent");
	}

	public void ForceTriggerLastEvent() throws RpcError
	{
		ForceTriggerLastEvent("MTunerService.SearchFrequencyChangeEvent");
	}

	public void OnTriggered(Value ___input)
	{
		Value ___par = ___input.getValue("parameters");

		int freq;
		freq = ___par.getInt("uintN_freq");
		OnSearchFrequencyChangeEventTriggered(freq);
	}

	public void OnSearchFrequencyChangeEventTriggered(int freq)
	{
		// TODO: write your code for implementation
	}

}
