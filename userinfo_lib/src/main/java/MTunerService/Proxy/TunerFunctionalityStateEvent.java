package MTunerService.Proxy;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class TunerFunctionalityStateEvent extends TRpcEventBase {
	public TunerFunctionalityStateEvent(boolean bRegist) throws RpcError
	{
		if (bRegist)
			StartListen("MTunerService.TunerFunctionalityStateEvent");
	}

	public void StartListen()  throws RpcError 
	{
		StartListen("MTunerService.TunerFunctionalityStateEvent");
	}

	public void EndListen() throws RpcError
	{
		EndListen("MTunerService.TunerFunctionalityStateEvent");
	}

	public void ForceTriggerLastEvent() throws RpcError
	{
		ForceTriggerLastEvent("MTunerService.TunerFunctionalityStateEvent");
	}

	public void OnTriggered(Value ___input)
	{
		Value ___par = ___input.getValue("parameters");

		MCommon.FunctionalityState funcitonalityState = new MCommon.FunctionalityState();
		funcitonalityState.___deserialize(___par.getValue("MCommon::FunctionalityStateN_funcitonalityState"));
		OnTunerFunctionalityStateEventTriggered(funcitonalityState);
	}

	public void OnTunerFunctionalityStateEventTriggered(MCommon.FunctionalityState funcitonalityState)
	{
		// TODO: write your code for implementation
	}

}
