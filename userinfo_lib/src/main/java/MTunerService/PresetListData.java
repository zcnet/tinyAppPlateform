package MTunerService;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class PresetListData
{
	public  int freq;
	public  EStationType stationType = EStationType.STATIONTYPE_UNKNOWN;
	public Value ___serialize()
	{
		Value ___par = new Value();
		___par.put("uintN_freq", freq);
		___par.put("EStationTypeN_stationType",(int)stationType.value());
		return ___par;
	}

	public void ___deserialize(Value ___input)
	{
		Value ___par = ___input;
		freq = ___par.getInt("uintN_freq");
		stationType = EStationType.valueOf(___par.getInt("EStationTypeN_stationType"));
	}

}
