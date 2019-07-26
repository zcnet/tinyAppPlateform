package SourceService;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class SourceInfo
{
	public  int iAppID;
	public  SrcType eSrcType = SrcType.eSrcType_Unknown;
	public  String strSrcDescriptor;
	public Value ___serialize()
	{
		Value ___par = new Value();
		___par.put("intN_iAppID", iAppID);
		___par.put("SrcTypeN_eSrcType",(int)eSrcType.value());
		___par.put("stringN_strSrcDescriptor", strSrcDescriptor);
		return ___par;
	}

	public void ___deserialize(Value ___input)
	{
		Value ___par = ___input;
		iAppID = ___par.getInt("intN_iAppID");
		eSrcType = SrcType.valueOf(___par.getInt("SrcTypeN_eSrcType"));
		strSrcDescriptor = ___par.getString("stringN_strSrcDescriptor");
	}

}
