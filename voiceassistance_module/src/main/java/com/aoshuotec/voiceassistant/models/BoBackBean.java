package com.aoshuotec.voiceassistant.models;

import com.z.tinyapp.network.BaseJsonBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2018/10/11
 */

public class BoBackBean extends BaseJsonBean implements Serializable{

    /**
     * botId : 1
     * sessionId : 1cc43160dc474915be44c00596239e521
     * intent : [{"name":"MAKE_CALL","actionCount":1,"slots":[{"name":"user_phone_num","originalWord":"原始词","normalizedWord":"规整词","transformerWord":"转换词","must":"yes","type":"userdefined","resetWhenIntentRecognized":"no","clarify":"ask_for_phone_num"}],"slotGroups":[{"name":"make_call","must":"yes","least_fill_slot":1,"slots":[{"name":"user_contact_name","must":"no"},{"name":"user_phone_num","must":"no"}]}]}]
     * vaActionList : [{"name":"play:播放文字","priority":0,"interval":0,"retry":0,"doActionNow":1,"textList":[{"text":"您是要拨打13888888888吗？","interval":0}]},{"name":"display:显示文字","priority":0,"interval":0,"textList":[{"text":"您是要拨打13888888888吗？","interval":0}]},{"name":"rec:录制音频","priority":1,"interval":0},{"name":"execaction:执行业务动作","priority":2,"interval":0}]
     * errorTextList : [{"errorHintNum":3,"textList":[{"text":"发生错误请重试第一次"},{"text":"发生错误请重试第二次"},{"text":"发生错误请重试最后一次"}]}]
     * buActionList : [{"appName":"callCard","moduleName":"callModule","displayType":1,"isReuse":0,"content":"{\"actionName\":\"查找\",\"num\":\"13888888888\",\"type\":\"移动\",\"name\":\"zhangsan\"}"}]
     */

    private int botId;
    private String sessionId;
    private List<IntentBean> intent;
    private List<VaActionListBean> vaActionList = new ArrayList<>();
    private List<ErrorTextListBean> errorTextList= new ArrayList<>();
    private List<BuActionListBean> buActionList= new ArrayList<>();

    public int getBotId() {
        return botId;
    }

    public void setBotId(int botId) {
        this.botId = botId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<IntentBean> getIntent() {
        return intent;
    }

    public void setIntent(List<IntentBean> intent) {
        this.intent = intent;
    }

    public List<VaActionListBean> getVaActionList() {
        return vaActionList;
    }

    public void setVaActionList(List<VaActionListBean> vaActionList) {
        this.vaActionList = vaActionList;
    }

    public List<ErrorTextListBean> getErrorTextList() {
        return errorTextList;
    }

    public void setErrorTextList(List<ErrorTextListBean> errorTextList) {
        this.errorTextList = errorTextList;
    }

    public List<BuActionListBean> getBuActionList() {
        return buActionList;
    }

    public void setBuActionList(List<BuActionListBean> buActionList) {
        this.buActionList = buActionList;
    }

    public static class IntentBean {
        /**
         * name : MAKE_CALL
         * actionCount : 1
         * slots : [{"name":"user_phone_num","originalWord":"原始词","normalizedWord":"规整词","transformerWord":"转换词","must":"yes","type":"userdefined","resetWhenIntentRecognized":"no","clarify":"ask_for_phone_num"}]
         * slotGroups : [{"name":"make_call","must":"yes","least_fill_slot":1,"slots":[{"name":"user_contact_name","must":"no"},{"name":"user_phone_num","must":"no"}]}]
         */

        private String name;
        private int actionCount;
        private List<SlotsBean> slots;
        private List<SlotGroupsBean> slotGroups;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getActionCount() {
            return actionCount;
        }

        public void setActionCount(int actionCount) {
            this.actionCount = actionCount;
        }

        public List<SlotsBean> getSlots() {
            return slots;
        }

        public void setSlots(List<SlotsBean> slots) {
            this.slots = slots;
        }

        public List<SlotGroupsBean> getSlotGroups() {
            return slotGroups;
        }

        public void setSlotGroups(List<SlotGroupsBean> slotGroups) {
            this.slotGroups = slotGroups;
        }

        public static class SlotsBean {
            /**
             * name : user_phone_num
             * originalWord : 原始词
             * normalizedWord : 规整词
             * transformerWord : 转换词
             * must : yes
             * type : userdefined
             * resetWhenIntentRecognized : no
             * clarify : ask_for_phone_num
             */

            private String name;
            private String originalWord;
            private String normalizedWord;
            private String transformerWord;
            private String must;
            private String type;
            private String resetWhenIntentRecognized;
            private String clarify;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOriginalWord() {
                return originalWord;
            }

            public void setOriginalWord(String originalWord) {
                this.originalWord = originalWord;
            }

            public String getNormalizedWord() {
                return normalizedWord;
            }

            public void setNormalizedWord(String normalizedWord) {
                this.normalizedWord = normalizedWord;
            }

            public String getTransformerWord() {
                return transformerWord;
            }

            public void setTransformerWord(String transformerWord) {
                this.transformerWord = transformerWord;
            }

            public String getMust() {
                return must;
            }

            public void setMust(String must) {
                this.must = must;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getResetWhenIntentRecognized() {
                return resetWhenIntentRecognized;
            }

            public void setResetWhenIntentRecognized(String resetWhenIntentRecognized) {
                this.resetWhenIntentRecognized = resetWhenIntentRecognized;
            }

            public String getClarify() {
                return clarify;
            }

            public void setClarify(String clarify) {
                this.clarify = clarify;
            }
        }

        public static class SlotGroupsBean {
            /**
             * name : make_call
             * must : yes
             * least_fill_slot : 1
             * slots : [{"name":"user_contact_name","must":"no"},{"name":"user_phone_num","must":"no"}]
             */

            private String name;
            private String must;
            private int least_fill_slot;
            private List<SlotsBeanX> slots;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMust() {
                return must;
            }

            public void setMust(String must) {
                this.must = must;
            }

            public int getLeast_fill_slot() {
                return least_fill_slot;
            }

            public void setLeast_fill_slot(int least_fill_slot) {
                this.least_fill_slot = least_fill_slot;
            }

            public List<SlotsBeanX> getSlots() {
                return slots;
            }

            public void setSlots(List<SlotsBeanX> slots) {
                this.slots = slots;
            }

            public static class SlotsBeanX {
                /**
                 * name : user_contact_name
                 * must : no
                 */

                private String name;
                private String must;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getMust() {
                    return must;
                }

                public void setMust(String must) {
                    this.must = must;
                }
            }
        }
    }

    public static class VaActionListBean {
        /**
         * name : play:播放文字
         * priority : 0
         * interval : 0
         * retry : 0
         * doActionNow : 1
         * textList : [{"text":"您是要拨打13888888888吗？","interval":0}]
         */

        private String name;
        private int priority;
        private int interval;
        private int retry;
        private int doActionNow;
        private List<TextListBean> textList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getRetry() {
            return retry;
        }

        public void setRetry(int retry) {
            this.retry = retry;
        }

        public int getDoActionNow() {
            return doActionNow;
        }

        public void setDoActionNow(int doActionNow) {
            this.doActionNow = doActionNow;
        }

        public List<TextListBean> getTextList() {
            return textList;
        }

        public void setTextList(List<TextListBean> textList) {
            this.textList = textList;
        }

        public static class TextListBean {
            /**
             * text : 您是要拨打13888888888吗？
             * interval : 0
             */

            private String text;
            private int interval;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public int getInterval() {
                return interval;
            }

            public void setInterval(int interval) {
                this.interval = interval;
            }
        }
    }

    public static class ErrorTextListBean {
        /**
         * errorHintNum : 3
         * textList : [{"text":"发生错误请重试第一次"},{"text":"发生错误请重试第二次"},{"text":"发生错误请重试最后一次"}]
         */

        private int errorHintNum;
        private List<TextListBeanX> textList;

        public int getErrorHintNum() {
            return errorHintNum;
        }

        public void setErrorHintNum(int errorHintNum) {
            this.errorHintNum = errorHintNum;
        }

        public List<TextListBeanX> getTextList() {
            return textList;
        }

        public void setTextList(List<TextListBeanX> textList) {
            this.textList = textList;
        }

        public static class TextListBeanX {
            /**
             * text : 发生错误请重试第一次
             */

            private String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }

    public static class BuActionListBean {
        /**
         * appName : callCard
         * moduleName : callModule
         * displayType : 1
         * isReuse : 0
         * content : {"actionName":"查找","num":"13888888888","type":"移动","name":"zhangsan"}
         */

        private String appName;
        private String moduleName;
        private int displayType;
        private int isReuse;
        private String content;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getModuleName() {
            return moduleName;
        }

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public int getDisplayType() {
            return displayType;
        }

        public void setDisplayType(int displayType) {
            this.displayType = displayType;
        }

        public int getIsReuse() {
            return isReuse;
        }

        public void setIsReuse(int isReuse) {
            this.isReuse = isReuse;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}