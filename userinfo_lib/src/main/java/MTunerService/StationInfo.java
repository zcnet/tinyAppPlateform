package MTunerService;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class StationInfo
{
	public  EStationType stationType = MTunerService.EStationType.STATIONTYPE_UNKNOWN;
	public  int tunerFreq;
	public  int sigQuality;
	public  EAudioType audioType = EAudioType.TUNER_UNKNOWN;
	public Value ___serialize()
	{
		Value ___par = new Value();
		___par.put("EStationTypeN_stationType",(int)stationType.value());
		___par.put("uintN_tunerFreq", tunerFreq);
		___par.put("uintN_sigQuality", sigQuality);
		___par.put("EAudioTypeN_audioType",(int)audioType.value());
		return ___par;
	}

	public void ___deserialize(Value ___input)
	{
		Value ___par = ___input;
		stationType = EStationType.valueOf(___par.getInt("EStationTypeN_stationType"));
		tunerFreq = ___par.getInt("uintN_tunerFreq");
		sigQuality = ___par.getInt("uintN_sigQuality");
		audioType = EAudioType.valueOf(___par.getInt("EAudioTypeN_audioType"));
	}

}
