package MTunerService;
import java.util.HashMap;
import java.util.Map;
import tinyrpc.*;
public class StationList
{
	public  EStationType stationType = MTunerService.EStationType.STATIONTYPE_UNKNOWN;
	public  int totalNumOfStation;
	public  Map<Integer, StationListData> stationListData = new HashMap<Integer, StationListData>();
	public Value ___serialize()
	{
		Value ___par = new Value();
		___par.put("EStationTypeN_stationType",(int)stationType.value());
		___par.put("uintN_totalNumOfStation", totalNumOfStation);
		{ ValueArray ___arrObj = ___par.getValueArray("StationListDataV_stationListData");
		if( null != ___arrObj){
		___arrObj.clear();
		for (Map.Entry<Integer, StationListData> entry : stationListData.entrySet()) {
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
		stationType = EStationType.valueOf(___par.getInt("EStationTypeN_stationType"));
		totalNumOfStation = ___par.getInt("uintN_totalNumOfStation");
		{ ValueArray ___arrObj = ___par.getValueArray("StationListDataV_stationListData");
		if( null != ___arrObj){
		int ___sz = ___arrObj.length();
		stationListData.clear();
		for (int ___i = 0; ___i < ___sz; ++___i)
		{
			Value ___arrnode = ___arrObj.getValue(___i);
			StationListData o = new StationListData();
			o.___deserialize(___arrnode.getValue("v"));
			stationListData.put(___arrnode.getInt("i"), o);
		}}
		}
	}

}
