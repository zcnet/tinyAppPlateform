package com.zcnet.voiceinteractionmodule.service;

import com.zcnet.voiceinteractionmodule.common.Slot;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SlotService {
    private static Map<Long,Slot> map;
    private static long count= 1;


    public  void insertList(List<Slot> slots) throws Exception{
        for (Slot slot : slots) {
            slot.setId(count);
            map.put(count,slot);
            count++;
        }
    }
    public  Slot getByIntentIdType(Long intentId, String type) throws Exception{
        List<Slot> slotId = IntentSlotService.map.get(intentId);
        if (slotId!=null){
            Slot slot = map.get(slotId);
            if (slot!=null&&slot.getType().equals(type)){
                return slot;
            }
        }
        return null;
    }

    /**
     * 根据词槽名称、内容查询24小时内该用户的最新词槽内容
     * @throws Exception
     */
    public static void getByIntentSlotTypeUID(String intentId, Long userId, Date startTime, Date endTime) throws Exception{

    }
}
