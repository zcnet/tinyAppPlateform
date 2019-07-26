package com.zcnet.voiceinteractionmodule.service;

import com.zcnet.voiceinteractionmodule.common.Slot;

import java.util.List;
import java.util.Map;

public class IntentSlotService {
    public static Map<Long,List<Slot>> map;

//    public IntentSlot getByIntentIdAndSlotId(Long intentId, Long slotId) throws Exception {
//
//        CommonFilterInfo<IntentSlot> filterInfo = new CommonFilterInfo<IntentSlot>(IntentSlot.class);
//        filterInfo.setEntity(new IntentSlot());
//
//        filterInfo.getEntity().setIntent(new FKEntity<Intent>(intentId, null));
//        filterInfo.getEntity().setSlot(new FKEntity<Slot>(slotId, null));
//
//        List<IntentSlot> list = intentSlotDAO.selectList(filterInfo);
//        if (list != null && list.size() > 1) {
//            log.error(WebApiErrorCodes.E_MULTI_RECORD.getMessage("tb_intentSlot", "id"));
//        }
//        if (list != null && list.size() > 0) {
//            return list.get(0);
//        }
//
//        return null;
//    }
//
//    public int delete(IntentSlot intentSlot) throws Exception {
//        int retVal = intentSlotDAO.delete(intentSlot);
//        if (retVal == 0) {
//            throw new NoRowsAffectedErrorException("delete", "tb_intent_slot", null);
//        }
//        return retVal;
//    }

    public void insertList(Long intentId, List<Slot> slotList) {
        map.put(intentId,slotList);
    }

    public List<Slot> getIntentSlots(Long intentId){
        return map.get(intentId);
    }
}
