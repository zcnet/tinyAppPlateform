package MTunerService.Proxy;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class TunerStateChangeEvent extends TRpcEventBase {
	public TunerStateChangeEvent(boolean bRegist) throws RpcError
	{
		if (bRegist)
			StartListen("MTunerService.TunerStateChangeEvent");
	}

	public void StartListen()  throws RpcError 
	{
		StartListen("MTunerService.TunerStateChangeEvent");
	}

	public void EndListen() throws RpcError
	{
		EndListen("MTunerService.TunerStateChangeEvent");
	}

	public void ForceTriggerLastEvent() throws RpcError
	{
		ForceTriggerLastEvent("MTunerService.TunerStateChangeEvent");
	}

	public void OnTriggered(Value ___input)
	{
		Value ___par = ___input.getValue("parameters");

		MTunerService.EStationState state;
		state = MTunerService.EStationState.valueOf(___par.getInt("EStationStateN_state"));
		OnTunerStateChangeEventTriggered(state);
	}

	public void OnTunerStateChangeEventTriggered(MTunerService.EStationState state)
	{
		// TODO: write your code for implementation
	}

}
