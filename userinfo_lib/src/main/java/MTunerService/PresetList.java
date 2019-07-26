package MTunerService;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class PresetList
{
	public  int totalNumOfPreset;
	public  Map<Integer, PresetListData> presetListData = new HashMap<Integer, PresetListData>();
	public Value ___serialize()
	{
		Value ___par = new Value();
		___par.put("uintN_totalNumOfPreset", totalNumOfPreset);
		{ ValueArray ___arrObj = ___par.getValueArray("PresetListDataV_presetListData");
		if( null != ___arrObj){
		___arrObj.clear();
		for (Map.Entry<Integer, PresetListData> entry : presetListData.entrySet()) {
			Value ___arrNode = new Value();
			___arrNode.put("i", entry.getKey().intValue());
			___arrNode.put("v", entry.getValue().___serialize());
			___arrObj.put(___arrNode);
		}}}
		return ___par;
	}

	public void ___deserialize(Value ___input)
	{
		Value ___par = ___input;
		totalNumOfPreset = ___par.getInt("uintN_totalNumOfPreset");
		{ ValueArray ___arrObj = ___par.getValueArray("PresetListDataV_presetListData");
		if( null != ___arrObj){
		int ___sz = ___arrObj.length();
		presetListData.clear();
		for (int ___i = 0; ___i < ___sz; ++___i)
		{
			Value ___arrnode = ___arrObj.getValue(___i);
			PresetListData o = new PresetListData();
			o.___deserialize(___arrnode.getValue("v"));
			presetListData.put(___arrnode.getInt("i"), o);
		}}
		}
	}

}
