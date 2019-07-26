package MTunerService.Proxy;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class ITunerService extends TRpcProxyBase {
	private ITunerService() throws RpcError {super("MTunerService.ITunerService", true);}
	static ITunerService s_cInst = null;
	public static ITunerService Inst() throws RpcError
	{
		if (null == s_cInst)
		{
			s_cInst = new ITunerService();
		}
		if (!s_cInst.IsValid())
		{
			boolean ret = s_cInst.GetSingletonInstance("MTunerService.ITunerService");
		}
		return s_cInst;
	}

	public MTunerService.EReturnCode ReqTunerConfigInfo(MTunerService.TunerConfigInfo tunerConfigInfo) throws RpcError
	{
		String ___u = "B72ADE57-78D8-CEEE-185C-7FA2DDBC77EF";
		Value ___par = new Value();
		___par.put("TunerConfigInfoN_tunerConfigInfo",tunerConfigInfo.___serialize());

		Value out = new Value();
		String methodname = "ReqTunerConfigInfo";
		Invoke(___u, ___par, out, 1, methodname);

		tunerConfigInfo.___deserialize(___par.getValue("TunerConfigInfoN_tunerConfigInfo"));
		MTunerService.EReturnCode ___retval;
		___retval = MTunerService.EReturnCode.valueOf(out.getInt("EReturnCodeR_return"));
		return ___retval;
	}

	public void ReqSpecifiedStationPlay(int freq) throws RpcError
	{
		String ___u = "56C958B3-12ED-7665-BA61-6ED9663BBA4E";
		Value ___par = new Value();
		___par.put("uintN_freq", freq);

		Value out = new Value();
		String methodname = "ReqSpecifiedStationPlay";
		Invoke(___u, ___par, out, 2, methodname);
	}

	public void ReqTunerSeek(MTunerService.ESeekType type) throws RpcError
	{
		String ___u = "E6C16656-3486-63B9-F119-519C9E5AFA74";
		Value ___par = new Value();
		___par.put("ESeekTypeN_type",(int)type.value());

		Value out = new Value();
		String methodname = "ReqTunerSeek";
		Invoke(___u, ___par, out, 3, methodname);
	}

	public void ReqTunerScan(MTunerService.EScanType type) throws RpcError
	{
		String ___u = "230A1C80-D450-9E23-EBDA-BDAE459A2DF2";
		Value ___par = new Value();
		___par.put("EScanTypeN_type",(int)type.value());

		Value out = new Value();
		String methodname = "ReqTunerScan";
		Invoke(___u, ___par, out, 4, methodname);
	}

	public MTunerService.EReturnCode ReqCurrentStationInfo(MTunerService.StationInfo stationInfo) throws RpcError
	{
		String ___u = "77FE6E70-8335-FE1B-FC08-6741BB96E158";
		Value ___par = new Value();
		___par.put("StationInfoN_stationInfo",stationInfo.___serialize());

		Value out = new Value();
		String methodname = "ReqCurrentStationInfo";
		Invoke(___u, ___par, out, 5, methodname);

		stationInfo.___deserialize(___par.getValue("StationInfoN_stationInfo"));
		MTunerService.EReturnCode ___retval;
		___retval = MTunerService.EReturnCode.valueOf(out.getInt("EReturnCodeR_return"));
		return ___retval;
	}

	public MTunerService.EStationState ReqCurrentTunerState() throws RpcError
	{
		String ___u = "9701FF1C-D5C3-DF36-D3C3-892F75DF052D";
		String in = "{}";

		Value out = new Value();
		String methodname = "ReqCurrentTunerState";
		Invoke(___u, in, out, 6, methodname);

		MTunerService.EStationState ___retval;
		___retval = MTunerService.EStationState.valueOf(out.getInt("EStationStateR_return"));
		return ___retval;
	}

	public MTunerService.EReturnCode ReqPresetList(MTunerService.PresetList presetList) throws RpcError
	{
		String ___u = "061B8147-7964-29E9-870B-274E438F3F1D";
		Value ___par = new Value();
		___par.put("PresetListN_presetList",presetList.___serialize());

		Value out = new Value();
		String methodname = "ReqPresetList";
		Invoke(___u, ___par, out, 7, methodname);

		presetList.___deserialize(___par.getValue("PresetListN_presetList"));
		MTunerService.EReturnCode ___retval;
		___retval = MTunerService.EReturnCode.valueOf(out.getInt("EReturnCodeR_return"));
		return ___retval;
	}

	public MTunerService.EReturnCode ReqStationList(MTunerService.EStationType type, MTunerService.StationList stationList) throws RpcError
	{
		String ___u = "8DDB8961-5B34-C634-00CE-38AC8244E04B";
		Value ___par = new Value();
		___par.put("EStationTypeN_type",(int)type.value());
		___par.put("StationListN_stationList",stationList.___serialize());

		Value out = new Value();
		String methodname = "ReqStationList";
		Invoke(___u, ___par, out, 8, methodname);

		stationList.___deserialize(___par.getValue("StationListN_stationList"));
		MTunerService.EReturnCode ___retval;
		___retval = MTunerService.EReturnCode.valueOf(out.getInt("EReturnCodeR_return"));
		return ___retval;
	}

	public MTunerService.EReturnCode ReqPresetStationCtr(int index, MTunerService.EPresetCtrType type) throws RpcError
	{
		String ___u = "39001AE5-9989-380E-A481-EE6D36867E06";
		Value ___par = new Value();
		___par.put("uintN_index", index);
		___par.put("EPresetCtrTypeN_type",(int)type.value());

		Value out = new Value();
		String methodname = "ReqPresetStationCtr";
		Invoke(___u, ___par, out, 9, methodname);

		MTunerService.EReturnCode ___retval;
		___retval = MTunerService.EReturnCode.valueOf(out.getInt("EReturnCodeR_return"));
		return ___retval;
	}

	public void ReqTunePresetListItem(int index) throws RpcError
	{
		String ___u = "F8A00814-276F-8697-DD96-5B9F27472D56";
		Value ___par = new Value();
		___par.put("uintN_index", index);

		Value out = new Value();
		String methodname = "ReqTunePresetListItem";
		Invoke(___u, ___par, out, 10, methodname);
	}

	public void ReqTuneStationListItem(MTunerService.EStationType type, int index) throws RpcError
	{
		String ___u = "A123071F-EDA4-CC57-CDCB-D5D81C576826";
		Value ___par = new Value();
		___par.put("EStationTypeN_type",(int)type.value());
		___par.put("uintN_index", index);

		Value out = new Value();
		String methodname = "ReqTuneStationListItem";
		Invoke(___u, ___par, out, 11, methodname);
	}

	public void ReqSwitchBandFreq(MTunerService.EStationType type, int freq) throws RpcError
	{
		String ___u = "09BED7BD-9F18-A1DB-9050-648F97B83AB7";
		Value ___par = new Value();
		___par.put("EStationTypeN_type",(int)type.value());
		___par.put("uintN_freq", freq);

		Value out = new Value();
		String methodname = "ReqSwitchBandFreq";
		Invoke(___u, ___par, out, 12, methodname);
	}

	public void ReqVRTunePreset(MTunerService.EPresetType type, MTunerService.EStationType.Ref stationType) throws RpcError
	{
		String ___u = "0B8621E0-9806-6D6C-07B9-8B3BB05453FB";
		Value ___par = new Value();
		___par.put("EPresetTypeN_type",(int)type.value());
		___par.put("EStationTypeN_stationType",(int)stationType.getValue().value());

		Value out = new Value();
		String methodname = "ReqVRTunePreset";
		Invoke(___u, ___par, out, 13, methodname);

		stationType.setValue(MTunerService.EStationType.valueOf(___par.getInt("EStationTypeN_stationType")));
	}

	public MTunerService.EReturnCode ReqRecordTunerAntennaInfoStart() throws RpcError
	{
		String ___u = "E64F6734-31C5-A4DC-03C6-28260C44E2A2";
		String in = "{}";

		Value out = new Value();
		String methodname = "ReqRecordTunerAntennaInfoStart";
		Invoke(___u, in, out, 14, methodname);

		MTunerService.EReturnCode ___retval;
		___retval = MTunerService.EReturnCode.valueOf(out.getInt("EReturnCodeR_return"));
		return ___retval;
	}

	public MTunerService.EReturnCode ReqRecordTunerAntennaInfoEnd() throws RpcError
	{
		String ___u = "709F6EF2-5E7B-C4AA-BE46-6E2CE3748981";
		String in = "{}";

		Value out = new Value();
		String methodname = "ReqRecordTunerAntennaInfoEnd";
		Invoke(___u, in, out, 15, methodname);

		MTunerService.EReturnCode ___retval;
		___retval = MTunerService.EReturnCode.valueOf(out.getInt("EReturnCodeR_return"));
		return ___retval;
	}

	public void ReqTuneStep(MTunerService.ETuneStepType type) throws RpcError
	{
		String ___u = "C4A96F41-23B3-6F2F-6AC3-F19894C3C939";
		Value ___par = new Value();
		___par.put("ETuneStepTypeN_type",(int)type.value());

		Value out = new Value();
		String methodname = "ReqTuneStep";
		Invoke(___u, ___par, out, 16, methodname);
	}

	public MTunerService.EReturnCode ReqTunerAntennaInfoFilePath(ParamString filePath) throws RpcError
	{
		String ___u = "77255CC2-D546-AB4A-6995-C1C874315225";
		Value ___par = new Value();
		___par.put("stringN_filePath", filePath.getValue());

		Value out = new Value();
		String methodname = "ReqTunerAntennaInfoFilePath";
		Invoke(___u, ___par, out, 17, methodname);

		filePath.setValue(___par.getString("stringN_filePath"));
		MTunerService.EReturnCode ___retval;
		___retval = MTunerService.EReturnCode.valueOf(out.getInt("EReturnCodeR_return"));
		return ___retval;
	}

	public MTunerService.EReturnCode RestoreFactoryTuner() throws RpcError
	{
		String ___u = "4C9D8421-0CEC-8228-2C94-6A9BAA79B7A5";
		String in = "{}";

		Value out = new Value();
		String methodname = "RestoreFactoryTuner";
		Invoke(___u, in, out, 18, methodname);

		MTunerService.EReturnCode ___retval;
		___retval = MTunerService.EReturnCode.valueOf(out.getInt("EReturnCodeR_return"));
		return ___retval;
	}

}
