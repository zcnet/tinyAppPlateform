package com.aoshuotec.voiceassistant.models;

import java.util.List;

/**
 * Created by sun on 2018/12/4
 */

public class MissingCallLogBean {

    /**
     * flowId : 1cc43160dc474915be44c00596239e521-0001
     * missingList : [{"recordNo":1,"num":"13888888888","type":"移动","contractName":"zhangsan"},{"recordNo":2,"num":"15912345678","type":"移动","contractName":"lisi"},{"recordNo":3,"num":"18187654321","type":"移动","contractName":"wangwu"}]
     * numPerPage : 5
     */

    private String flowId;
    private int numPerPage;
    private List<MissingListBean> missingList;

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

    public List<MissingListBean> getMissingList() {
        return missingList;
    }

    public void setMissingList(List<MissingListBean> missingList) {
        this.missingList = missingList;
    }

    public static class MissingListBean {
        /**
         * recordNo : 1
         * num : 13888888888
         * type : 移动
         * contractName : zhangsan
         */

        private int recordNo;
        private String num;
        private String type;
        private String contractName;

        public int getRecordNo() {
            return recordNo;
        }

        public void setRecordNo(int recordNo) {
            this.recordNo = recordNo;
        }

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

        public String getContractName() {
            return contractName;
        }

        public void setContractName(String contractName) {
            this.contractName = contractName;
        }
    }
}