package com.z.voiceassistant.models;

import java.util.List;

/**
 * Created by sun on 2018/12/28
 */

public class ThirdUploadContactBean {

    /**
     * flowId : 1cc43160dc474915be44c00596239e521-0001
     * phoneList : [{"userId":330,"numList":[{"num":"16652046020","type":"移动"},{"num":"16652046033","type":"移动"}],"contractName":"zhangsan"}]
     * numPerPage : 5
     */

    private String flowId;
    private int numPerPage;
    private List<PhoneListBean> phoneList;

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public List<PhoneListBean> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<PhoneListBean> phoneList) {
        this.phoneList = phoneList;
    }

    public static class PhoneListBean {
        /**
         * userId : 330
         * numList : [{"num":"16652046020","type":"移动"},{"num":"16652046033","type":"移动"}]
         * contractName : zhangsan
         */

        private int userId;
        private String contractName;
        private List<NumListBean> numList;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getContractName() {
            return contractName;
        }

        public void setContractName(String contractName) {
            this.contractName = contractName;
        }

        public List<NumListBean> getNumList() {
            return numList;
        }

        public void setNumList(List<NumListBean> numList) {
            this.numList = numList;
        }

        public static class NumListBean {
            /**
             * num : 16652046020
             * type : 移动
             */

            private String num;
            private String type;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
