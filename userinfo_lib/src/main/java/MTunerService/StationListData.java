package MTunerService;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class StationListData
{
	public  int freq;
	public Value ___serialize()
	{
		Value ___par = new Value();
		___par.put("uintN_freq", freq);
		return ___par;
	}

	public void ___deserialize(Value ___input)
	{
		Value ___par = ___input;
		freq = ___par.getInt("uintN_freq");
	}

}
