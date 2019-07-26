package com.zcnet.voiceinteractionmodule.service;

import com.zcnet.voiceinteractionmodule.common.SlotSnapshot;

import java.util.List;
import java.util.Map;

public class SlotSnapshotService {
    private static Map<String,List<SlotSnapshot>> map;
    private static long count= 1;

//    public void insertList(List<SlotSnapshot> slotSnapshots) throws Exception {
//        for (SlotSnapshot slotSnapshot : slotSnapshots) {
//                slotSnapshot.setAiSessionId();
//                map.put(count,slot);
//                count++;
//        }
//    }
//
//    public SlotSnapshot getByTypeSessionid(String type, String sessionId) throws Exception {
//        CommonFilterInfo<SlotSnapshot> filterInfo = new CommonFilterInfo<SlotSnapshot>(SlotSnapshot.class);
//        filterInfo.setEntity(new SlotSnapshot());
//        filterInfo.getEntity().setAiSessionId(sessionId);
//        filterInfo.getEntity().setType(type);
//        ComUtil.processSortInfo(filterInfo, "create_time", "desc", "TIMESTAMP");
//        List<SlotSnapshot> list = selectList(filterInfo);
//        if (!CheckUtil.isNull(list)) {
//            return list.get(0);
//        }
//        return null;
//    }
}
