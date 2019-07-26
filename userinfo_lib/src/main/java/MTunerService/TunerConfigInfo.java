package MTunerService;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class TunerConfigInfo
{
	public  int maxFMFreq;
	public  int minFMFreq;
	public  int maxAMFreq;
	public  int minAMFreq;
	public  int FMStepFreq;
	public  int AMStepFreq;
	public Value ___serialize()
	{
		Value ___par = new Value();
		___par.put("uintN_maxFMFreq", maxFMFreq);
		___par.put("uintN_minFMFreq", minFMFreq);
		___par.put("uintN_maxAMFreq", maxAMFreq);
		___par.put("uintN_minAMFreq", minAMFreq);
		___par.put("uintN_FMStepFreq", FMStepFreq);
		___par.put("uintN_AMStepFreq", AMStepFreq);
		return ___par;
	}

	public void ___deserialize(Value ___input)
	{
		Value ___par = ___input;
		maxFMFreq = ___par.getInt("uintN_maxFMFreq");
		minFMFreq = ___par.getInt("uintN_minFMFreq");
		maxAMFreq = ___par.getInt("uintN_maxAMFreq");
		minAMFreq = ___par.getInt("uintN_minAMFreq");
		FMStepFreq = ___par.getInt("uintN_FMStepFreq");
		AMStepFreq = ___par.getInt("uintN_AMStepFreq");
	}

}
