package com.zcnet.voiceinteractionmodule.service;

import com.zcnet.voiceinteractionmodule.common.Intent;

import java.util.List;
import java.util.Map;

public class IntentService {
    private Map<Long,Intent> map;
    private static long count= 1;

    public void insert(Intent intent)  {
        intent.setId(count);
        map.put(count,intent);
        count++;
    }

    public Intent getById(Long id)   {
        return map.get(id);
    }

    public void update(Intent intent){
        map.put(intent.getId(),intent);
    }

    public List<Intent> getByUidFrontSessionIdUpdateTime(Long userId, String frontSessionId) throws Exception {
        return null;
    }

}
