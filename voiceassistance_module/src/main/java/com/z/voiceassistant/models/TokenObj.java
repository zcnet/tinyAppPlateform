package com.z.voiceassistant.models;

import com.z.tinyapp.network.BaseJsonBean;

import java.util.List;

/**
 * Created by GongDongdong on 2018/9/4.
 */

public class TokenObj extends BaseJsonBean {
    public List<UserList> userList;
    public class UserList{
        public String loginName;
        public String accessToken;
        public int expires;
        public String refreshToken;
    }
}
