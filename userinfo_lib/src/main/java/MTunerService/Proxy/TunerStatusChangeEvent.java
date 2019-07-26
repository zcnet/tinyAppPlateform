package MTunerService.Proxy;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class TunerStatusChangeEvent extends TRpcEventBase {
	public TunerStatusChangeEvent(boolean bRegist) throws RpcError
	{
		if (bRegist)
			StartListen("MTunerService.TunerStatusChangeEvent");
	}

	public void StartListen()  throws RpcError 
	{
		StartListen("MTunerService.TunerStatusChangeEvent");
	}

	public void EndListen() throws RpcError
	{
		EndListen("MTunerService.TunerStatusChangeEvent");
	}

	public void ForceTriggerLastEvent() throws RpcError
	{
		ForceTriggerLastEvent("MTunerService.TunerStatusChangeEvent");
	}

	public void OnTriggered(Value ___input)
	{
		Value ___par = ___input.getValue("parameters");

		MTunerService.StationInfo currentStation = new MTunerService.StationInfo();
		currentStation.___deserialize(___par.getValue("StationInfoN_currentStation"));
		OnTunerStatusChangeEventTriggered(currentStation);
	}

	public void OnTunerStatusChangeEventTriggered(MTunerService.StationInfo currentStation)
	{
		// TODO: write your code for implementation
	}

}
