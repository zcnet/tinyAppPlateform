package SourceService.Proxy;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class ISourceHandler extends TCallBackBase {
	private static boolean ___bRegistered = false;
	public ISourceHandler() throws RpcError {
		TRPCInterface cInterface = TRPCHostMgr.Inst().RegisterObservableInterface("SourceService.ISourceHandler");
		if (!___bRegistered)
		{
			___bRegistered = true;
			String ___u1 = "3773F476-D6A3-DBD5-1EDB-9411E02E80F4";
			cInterface.AddMethod(___u1, 1);
			String ___u2 = "EA359FC3-4D39-F177-CC0C-A9A13FDAE725";
			cInterface.AddMethod(___u2, 1);
			String ___u3 = "2898A4D1-2788-5F03-57CC-FD48EC1B7BC1";
			cInterface.AddMethod(___u3, 1);
			String ___u4 = "4F78BD1A-0D2E-E7E1-A4BA-78C72657C243";
			cInterface.AddMethod(___u4, 1);
			String ___u5 = "190D0DE9-05D1-A933-73B0-19C8DF941EE1";
			cInterface.AddMethod(___u5, 1);
			String ___u6 = "0C9F9944-E3CF-2E54-C23A-03FA616CDB9C";
			cInterface.AddMethod(___u6, 1);
			String ___u7 = "C6DB361D-3D32-7ABA-AD79-E4F75E687E16";
			cInterface.AddMethod(___u7, 1);
			String ___u8 = "6340BF6F-B47E-1A3E-613A-C378CE2CBC70";
			cInterface.AddMethod(___u8, 1);
		}
		cInterface.AddObservableInstance("SourceService.ISourceHandler", this.CppObject());
	}

	public void OnRegistrationFeedback(boolean bSuccess, TRPCStream SrcData, SourceService.SrcStatus eCurrStatus) throws RpcError
	{
	}

	public void OnDeregistrationFeedback(boolean bSuccess, SourceService.SrcStatus eCurrStatus) throws RpcError
	{
	}

	public void OnStatusChanged(boolean bSuccess, SourceService.SrcStatus eCurrStatus) throws RpcError
	{
	}

	public boolean OnRequestChangeStatus(SourceService.SrcStatus ePrevStatus, SourceService.SrcStatus.Ref eCurrStatus, TRPCStream sData, SourceService.ReserveType eReserveType, boolean bActiveHMI) throws RpcError
	{
		// TODO: write your code for implementation
		boolean retval = false;
		return retval;
	}

	public void OnRequestSourceData(int iSrcDataLen, TRPCStream SrcData) throws RpcError
	{
	}

	public void OnSetDataFeedback(boolean bSuccess) throws RpcError
	{
	}

	public void OnTriggerSeekUp(SourceService.TriggerSrcType eType, SourceService.MPMgrKeyCmdType eCmdType) throws RpcError
	{
	}

	public void OnTriggerSeekDown(SourceService.TriggerSrcType eType, SourceService.MPMgrKeyCmdType eCmdType) throws RpcError
	{
	}

	static int ___ISourceHandler_OnRegistrationFeedback_obsvhdr(Object ___obj, Value ___input, Value ___output) throws RpcError
	{
		Value ___par = ___input.getValue("parameters");

		boolean bSuccess;
		bSuccess = ___par.getBoolean("boolN_bSuccess");
		TRPCStream SrcData = new TRPCStream();
	SrcData.Deserialize(___par.getString("streamN_SrcData"));
		SourceService.SrcStatus eCurrStatus;
		eCurrStatus = SourceService.SrcStatus.valueOf(___par.getInt("SrcStatusN_eCurrStatus"));
		((ISourceHandler)___obj).OnRegistrationFeedback(bSuccess, SrcData, eCurrStatus);
		return 0;
	}

	static int ___ISourceHandler_OnDeregistrationFeedback_obsvhdr(Object ___obj, Value ___input, Value ___output) throws RpcError
	{
		Value ___par = ___input.getValue("parameters");

		boolean bSuccess;
		bSuccess = ___par.getBoolean("boolN_bSuccess");
		SourceService.SrcStatus eCurrStatus;
		eCurrStatus = SourceService.SrcStatus.valueOf(___par.getInt("SrcStatusN_eCurrStatus"));
		((ISourceHandler)___obj).OnDeregistrationFeedback(bSuccess, eCurrStatus);
		return 0;
	}

	static int ___ISourceHandler_OnStatusChanged_obsvhdr(Object ___obj, Value ___input, Value ___output) throws RpcError
	{
		Value ___par = ___input.getValue("parameters");

		boolean bSuccess;
		bSuccess = ___par.getBoolean("boolN_bSuccess");
		SourceService.SrcStatus eCurrStatus;
		eCurrStatus = SourceService.SrcStatus.valueOf(___par.getInt("SrcStatusN_eCurrStatus"));
		((ISourceHandler)___obj).OnStatusChanged(bSuccess, eCurrStatus);
		return 0;
	}

	static int ___ISourceHandler_OnRequestChangeStatus_obsvhdr(Object ___obj, Value ___input, Value ___output) throws RpcError
	{
		Value ___par = ___input.getValue("parameters");

		SourceService.SrcStatus ePrevStatus;
		ePrevStatus = SourceService.SrcStatus.valueOf(___par.getInt("SrcStatusN_ePrevStatus"));
		SourceService.SrcStatus eCurrStatus;
		eCurrStatus = SourceService.SrcStatus.valueOf(___par.getInt("SrcStatusN_eCurrStatus"));
		SourceService.SrcStatus.Ref eCurrStatus_tmp = new SourceService.SrcStatus.Ref(eCurrStatus);
		TRPCStream sData = new TRPCStream();
	sData.Deserialize(___par.getString("streamN_sData"));
		SourceService.ReserveType eReserveType;
		eReserveType = SourceService.ReserveType.valueOf(___par.getInt("ReserveTypeN_eReserveType"));
		boolean bActiveHMI;
		bActiveHMI = ___par.getBoolean("boolN_bActiveHMI");
		boolean ___retval = ((ISourceHandler)___obj).OnRequestChangeStatus(ePrevStatus, eCurrStatus_tmp, sData, eReserveType, bActiveHMI);

		eCurrStatus=eCurrStatus_tmp.getValue();
		___par.put("SrcStatusN_eCurrStatus",(int)eCurrStatus.value());
		___output.put("boolR_return",___retval);
		___output.put("MethodStaticHdrFlags", "OutArgsExist");
		return 1;
	}

	static int ___ISourceHandler_OnRequestSourceData_obsvhdr(Object ___obj, Value ___input, Value ___output) throws RpcError
	{
		Value ___par = ___input.getValue("parameters");

		int iSrcDataLen;
		iSrcDataLen = ___par.getInt("uintN_iSrcDataLen");
		TRPCStream SrcData = new TRPCStream();
	SrcData.Deserialize(___par.getString("streamN_SrcData"));
		((ISourceHandler)___obj).OnRequestSourceData(iSrcDataLen, SrcData);

		___par.put("streamN_SrcData", SrcData.Serialize());
		___output.put("MethodStaticHdrFlags", "OutArgsExist");
		return 1;
	}

	static int ___ISourceHandler_OnSetDataFeedback_obsvhdr(Object ___obj, Value ___input, Value ___output) throws RpcError
	{
		Value ___par = ___input.getValue("parameters");

		boolean bSuccess;
		bSuccess = ___par.getBoolean("boolN_bSuccess");
		((ISourceHandler)___obj).OnSetDataFeedback(bSuccess);
		return 0;
	}

	static int ___ISourceHandler_OnTriggerSeekUp_obsvhdr(Object ___obj, Value ___input, Value ___output) throws RpcError
	{
		Value ___par = ___input.getValue("parameters");

		SourceService.TriggerSrcType eType;
		eType = SourceService.TriggerSrcType.valueOf(___par.getInt("TriggerSrcTypeN_eType"));
		SourceService.MPMgrKeyCmdType eCmdType;
		eCmdType = SourceService.MPMgrKeyCmdType.valueOf(___par.getInt("MPMgrKeyCmdTypeN_eCmdType"));
		((ISourceHandler)___obj).OnTriggerSeekUp(eType, eCmdType);
		return 0;
	}

	static int ___ISourceHandler_OnTriggerSeekDown_obsvhdr(Object ___obj, Value ___input, Value ___output) throws RpcError
	{
		Value ___par = ___input.getValue("parameters");

		SourceService.TriggerSrcType eType;
		eType = SourceService.TriggerSrcType.valueOf(___par.getInt("TriggerSrcTypeN_eType"));
		SourceService.MPMgrKeyCmdType eCmdType;
		eCmdType = SourceService.MPMgrKeyCmdType.valueOf(___par.getInt("MPMgrKeyCmdTypeN_eCmdType"));
		((ISourceHandler)___obj).OnTriggerSeekDown(eType, eCmdType);
		return 0;
	}

	public int obsvhdr(Value ___input, Value ___output)  throws RpcError {
		Value ___par = ___input.getValue("parameters");
		int id = ___input.getInt("methodid");
		switch (id) {
			case 1:
				return ___ISourceHandler_OnRegistrationFeedback_obsvhdr(this, ___input, ___output);
			case 2:
				return ___ISourceHandler_OnDeregistrationFeedback_obsvhdr(this, ___input, ___output);
			case 3:
				return ___ISourceHandler_OnStatusChanged_obsvhdr(this, ___input, ___output);
			case 4:
				return ___ISourceHandler_OnRequestChangeStatus_obsvhdr(this, ___input, ___output);
			case 5:
				return ___ISourceHandler_OnRequestSourceData_obsvhdr(this, ___input, ___output);
			case 6:
				return ___ISourceHandler_OnSetDataFeedback_obsvhdr(this, ___input, ___output);
			case 7:
				return ___ISourceHandler_OnTriggerSeekUp_obsvhdr(this, ___input, ___output);
			case 8:
				return ___ISourceHandler_OnTriggerSeekDown_obsvhdr(this, ___input, ___output);
			default:break;
		}
		return 0;
	}
}
