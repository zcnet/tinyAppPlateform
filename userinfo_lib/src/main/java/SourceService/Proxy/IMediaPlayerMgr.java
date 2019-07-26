package SourceService.Proxy;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class IMediaPlayerMgr extends TRpcProxyBase {
	private IMediaPlayerMgr() throws RpcError {super("SourceService.IMediaPlayerMgr", true);}
	static IMediaPlayerMgr s_cInst = null;
	public static IMediaPlayerMgr Inst() throws RpcError
	{
		if (null == s_cInst)
		{
			s_cInst = new IMediaPlayerMgr();
		}
		if (!s_cInst.IsValid())
		{
			boolean ret = s_cInst.GetSingletonInstance("SourceService.IMediaPlayerMgr");
		}
		return s_cInst;
	}

	public boolean ChangeToNextSource(boolean bActiveHMI) throws RpcError
	{
		String ___u = "9836E0F8-CC92-5A9C-E102-18C49C18C444";
		Value ___par = new Value();
		___par.put("boolN_bActiveHMI", bActiveHMI);

		Value out = new Value();
		String methodname = "ChangeToNextSource";
		Invoke(___u, ___par, out, 1, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public boolean ChangeToNextTunerSource(boolean bActiveHMI) throws RpcError
	{
		String ___u = "1D2D33DD-4991-21B1-BCE7-CDCD4B65CDAF";
		Value ___par = new Value();
		___par.put("boolN_bActiveHMI", bActiveHMI);

		Value out = new Value();
		String methodname = "ChangeToNextTunerSource";
		Invoke(___u, ___par, out, 2, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public boolean ChangeToNextOtherSource(boolean bActiveHMI) throws RpcError
	{
		String ___u = "B0F38F65-B151-183A-C2FD-BED6F753D99A";
		Value ___par = new Value();
		___par.put("boolN_bActiveHMI", bActiveHMI);

		Value out = new Value();
		String methodname = "ChangeToNextOtherSource";
		Invoke(___u, ___par, out, 3, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public SourceService.SourceInfo GetActiveSource() throws RpcError
	{
		String ___u = "12EA9D0E-F31D-CC3B-ED4F-0A29634FD60D";
		String in = "{}";

		Value out = new Value();
		String methodname = "GetActiveSource";
		Invoke(___u, in, out, 4, methodname);

		SourceService.SourceInfo ___retval = new SourceService.SourceInfo();
		___retval.___deserialize(out.getValue("SourceInfoR_return"));
		return ___retval;
	}

	public boolean Activate(String strSrc, boolean bActiveHMI) throws RpcError
	{
		String ___u = "1D840806-9AE1-7F0A-CE6A-9FFA0BBAFF4D";
		Value ___par = new Value();
		___par.put("stringN_strSrc", strSrc);
		___par.put("boolN_bActiveHMI", bActiveHMI);

		Value out = new Value();
		String methodname = "Activate";
		Invoke(___u, ___par, out, 5, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public boolean ActivateSource(String strSrc, boolean bActiveHMI, TRPCStream SrcData) throws RpcError
	{
		String ___u = "0829D490-C10C-FBB9-C685-DBE33667878C";
		Value ___par = new Value();
		___par.put("stringN_strSrc", strSrc);
		___par.put("boolN_bActiveHMI", bActiveHMI);
		___par.put("streamN_SrcData", SrcData.Serialize());

		Value out = new Value();
		String methodname = "ActivateSource";
		Invoke(___u, ___par, out, 6, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public boolean SetInitialMode(SourceService.InitialMode eMode) throws RpcError
	{
		String ___u = "72C96F55-1E1B-7CA8-BB3D-2F8802862025";
		Value ___par = new Value();
		___par.put("InitialModeN_eMode",(int)eMode.value());

		Value out = new Value();
		String methodname = "SetInitialMode";
		Invoke(___u, ___par, out, 7, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public boolean EnterTODMode() throws RpcError
	{
		String ___u = "2D4BB1D9-BB90-FC5D-B11B-C43E3205AAE2";
		String in = "{}";

		Value out = new Value();
		String methodname = "EnterTODMode";
		Invoke(___u, in, out, 8, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public boolean ExitTODMode() throws RpcError
	{
		String ___u = "276F205F-5D31-105C-DF87-6CF2B0547C5C";
		String in = "{}";

		Value out = new Value();
		String methodname = "ExitTODMode";
		Invoke(___u, in, out, 9, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public boolean SetInitializationFinished() throws RpcError
	{
		String ___u = "16D1F84F-A44B-D185-7E2F-DFB980968A8C";
		String in = "{}";

		Value out = new Value();
		String methodname = "SetInitializationFinished";
		Invoke(___u, in, out, 10, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public boolean SaveBeforeShutDown() throws RpcError
	{
		String ___u = "C1ECA19C-732B-3CF3-C052-A6834158E539";
		String in = "{}";

		Value out = new Value();
		String methodname = "SaveBeforeShutDown";
		Invoke(___u, in, out, 11, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public SourceService.SrcChgType GetSourceChangeStatus() throws RpcError
	{
		String ___u = "A6837670-AE49-7698-E484-C82983752BD1";
		String in = "{}";

		Value out = new Value();
		String methodname = "GetSourceChangeStatus";
		Invoke(___u, in, out, 12, methodname);

		SourceService.SrcChgType ___retval;
		___retval = SourceService.SrcChgType.valueOf(out.getInt("SrcChgTypeR_return"));
		return ___retval;
	}

	public boolean AddListener(String strIdentifer, IMediaPlayerHandler objHdr) throws RpcError
	{
		String ___u = "AFBF8A14-8053-E192-7669-F5294D8805D6";
		Value ___par = new Value();
		___par.put("stringN_strIdentifer", strIdentifer);
		___par.put("Proxy::IMediaPlayerHandler::uid", TinyRPC.GetUniqueIdentifier());
		___par.put("Proxy::IMediaPlayerHandlerN_objHdr", objHdr.CppObject());

		Value out = new Value();
		String methodname = "AddListener";
		Invoke(___u, ___par, out, 13, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public boolean RemoveListener(String strIdentifer) throws RpcError
	{
		String ___u = "E04ADBFF-D16C-6124-0CA5-635CA477D356";
		Value ___par = new Value();
		___par.put("stringN_strIdentifer", strIdentifer);

		Value out = new Value();
		String methodname = "RemoveListener";
		Invoke(___u, ___par, out, 14, methodname);

		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

	public int GetHistorySourceCount() throws RpcError
	{
		String ___u = "8F437366-97CA-F696-BDF3-9C064F766C3D";
		String in = "{}";

		Value out = new Value();
		String methodname = "GetHistorySourceCount";
		Invoke(___u, in, out, 15, methodname);

		int ___retval;
		___retval = out.getInt("uintR_return");
		return ___retval;
	}

	public String GetHistorySourceByIndex(int index) throws RpcError
	{
		String ___u = "3BA3BA38-DC21-5130-80B5-809C63517D57";
		Value ___par = new Value();
		___par.put("uintN_index", index);

		Value out = new Value();
		String methodname = "GetHistorySourceByIndex";
		Invoke(___u, ___par, out, 16, methodname);

		String ___retval;
		___retval = out.getString("stringR_return");
		return ___retval;
	}

	public int GetSourceCount() throws RpcError
	{
		String ___u = "94D1638B-63A9-56D2-B57D-BE57E9ADF740";
		String in = "{}";

		Value out = new Value();
		String methodname = "GetSourceCount";
		Invoke(___u, in, out, 17, methodname);

		int ___retval;
		___retval = out.getInt("uintR_return");
		return ___retval;
	}

	public String GetSourceByIndex(int index) throws RpcError
	{
		String ___u = "C30241FC-CB8D-E247-75E3-88A24133875B";
		Value ___par = new Value();
		___par.put("uintN_index", index);

		Value out = new Value();
		String methodname = "GetSourceByIndex";
		Invoke(___u, ___par, out, 18, methodname);

		String ___retval;
		___retval = out.getString("stringR_return");
		return ___retval;
	}

	public boolean GetSourceInfoList(Map<Integer, SourceService.SourceInfo> sourceInfoList) throws RpcError
	{
		String ___u = "44E3C0A2-93BD-4024-4789-6A32F5C1B36F";
		Value ___par = new Value();
		{ ValueArray ___arrObj = ___par.getValueArray("SourceInfoV_sourceInfoList");
		if( null != ___arrObj){
		___arrObj.clear();
		for (Map.Entry<Integer, SourceService.SourceInfo> entry : sourceInfoList.entrySet()) {
			Value ___arrNode = new Value();
			___arrNode.put("i", entry.getKey().intValue());
			___arrNode.put("v", entry.getValue().___serialize());
			___arrObj.put(___arrNode);
		}}}

		Value out = new Value();
		String methodname = "GetSourceInfoList";
		Invoke(___u, ___par, out, 19, methodname);

		{ ValueArray ___arrObj = ___par.getValueArray("SourceInfoV_sourceInfoList");
		if( null != ___arrObj){
		int ___sz = ___arrObj.length();
		sourceInfoList.clear();
		for (int ___i = 0; ___i < ___sz; ++___i)
		{
			Value ___arrnode = ___arrObj.getValue(___i);
			SourceService.SourceInfo o = new SourceService.SourceInfo();
			o.___deserialize(___arrnode.getValue("v"));
			sourceInfoList.put(___arrnode.getInt("i"), o);
		}}
		}
		boolean ___retval;
		___retval = out.getBoolean("boolR_return");
		return ___retval;
	}

}
