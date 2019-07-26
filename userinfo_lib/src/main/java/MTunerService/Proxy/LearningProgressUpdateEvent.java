package MTunerService.Proxy;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class LearningProgressUpdateEvent extends TRpcEventBase {
	public LearningProgressUpdateEvent(boolean bRegist) throws RpcError
	{
		if (bRegist)
			StartListen("MTunerService.LearningProgressUpdateEvent");
	}

	public void StartListen()  throws RpcError 
	{
		StartListen("MTunerService.LearningProgressUpdateEvent");
	}

	public void EndListen() throws RpcError
	{
		EndListen("MTunerService.LearningProgressUpdateEvent");
	}

	public void ForceTriggerLastEvent() throws RpcError
	{
		ForceTriggerLastEvent("MTunerService.LearningProgressUpdateEvent");
	}

	public void OnTriggered(Value ___input)
	{
		Value ___par = ___input.getValue("parameters");

		int progress;
		progress = ___par.getInt("intN_progress");
		OnLearningProgressUpdateEventTriggered(progress);
	}

	public void OnLearningProgressUpdateEventTriggered(int progress)
	{
		// TODO: write your code for implementation
	}

}
