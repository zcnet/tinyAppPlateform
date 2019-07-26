package MTunerService.Proxy;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class ReportReturnCodeEvent extends TRpcEventBase {
	public ReportReturnCodeEvent(boolean bRegist) throws RpcError
	{
		if (bRegist)
			StartListen("MTunerService.ReportReturnCodeEvent");
	}

	public void StartListen()  throws RpcError 
	{
		StartListen("MTunerService.ReportReturnCodeEvent");
	}

	public void EndListen() throws RpcError
	{
		EndListen("MTunerService.ReportReturnCodeEvent");
	}

	public void ForceTriggerLastEvent() throws RpcError
	{
		ForceTriggerLastEvent("MTunerService.ReportReturnCodeEvent");
	}

	public void OnTriggered(Value ___input)
	{
		Value ___par = ___input.getValue("parameters");

		MTunerService.EReturnCode returnCode;
		returnCode = MTunerService.EReturnCode.valueOf(___par.getInt("EReturnCodeN_returnCode"));
		OnReportReturnCodeEventTriggered(returnCode);
	}

	public void OnReportReturnCodeEventTriggered(MTunerService.EReturnCode returnCode)
	{
		// TODO: write your code for implementation
	}

}
