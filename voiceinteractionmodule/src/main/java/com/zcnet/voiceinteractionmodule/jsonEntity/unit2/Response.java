/**
 *  Zeninte - Connected Car Operation Platform (CCOP)
 *  Copyright(C) 2016, 2017, 2018, Zeninte Development Team
 *  All Right Reserved.
 */
package com.zcnet.voiceinteractionmodule.jsonEntity.unit2;

import java.io.Serializable;
import java.util.List;

/**
 * UNIT 2.0 返回值
 * 
 * @version 2018.08.16
 * @author Zeninte
 * @since JDK1.6
 * 
 */
public class Response implements Serializable {

    /**
     * action_list : [{"action":"satisfy","action_id":"make_call_satisfy","confidence":100}]
     * qu_res : {"candidates":[{"id":"1852794992","bot":"phone","intent":"MAKE_CALL","confidence":100,"intent_need_clarify":false,"match_info":{"matched_length":12,"matched_pattern":"oooooo","matched_ruleid":4},"slots":[{"begin":4,"length":2,"name":"user_name","normalized_word":"","original_word":"张三"}]}]}
     * raw_query : 打电 话给张三
     * schema : {"id":"1852794992","bot":"phone","intent":"MAKE_CALL","confidence":100,"intent_need_clarify":false,"slots":[{"begin":4,"length":2,"name":"user_name","merge_method":"new","normalized_word":"","original_word":"张三"}]}
     */
    private QuResBean qu_res;
    private String raw_query;
    private SchemaBean schema;
    private List<ActionListBean> action_list;

    public QuResBean getQu_res() {
        return qu_res;
    }

    public void setQu_res(QuResBean qu_res) {
        this.qu_res = qu_res;
    }

    public String getRaw_query() {
        return raw_query;
    }

    public void setRaw_query(String raw_query) {
        this.raw_query = raw_query;
    }

    public SchemaBean getSchema() {
        return schema;
    }

    public void setSchema(SchemaBean schema) {
        this.schema = schema;
    }

    public List<ActionListBean> getAction_list() {
        return action_list;
    }

    public void setAction_list(List<ActionListBean> action_list) {
        this.action_list = action_list;
    }

    public static class QuResBean {
        private List<CandidatesBean> candidates;

        public List<CandidatesBean> getCandidates() {
            return candidates;
        }

        public void setCandidates(List<CandidatesBean> candidates) {
            this.candidates = candidates;
        }

        public static class CandidatesBean {
            /**
             * id : 1852794992
             * bot : phone
             * intent : MAKE_CALL
             * confidence : 100
             * intent_need_clarify : false
             * match_info : {"matched_length":12,"matched_pattern":"oooooo","matched_ruleid":4}
             * slots : [{"begin":4,"length":2,"name":"user_name","normalized_word":"","original_word":"张三"}]
             */

            private String bot;
            private String intent;
            private double confidence;
            private boolean intent_need_clarify;
            private MatchInfoBean match_info;
            private List<SlotsBean> slots;

            public String getId() {
                return bot;
            }

            public void setId(String id) {
                this.bot = id;
            }

            public String getBot() {
                return bot;
            }

            public void setBot(String bot) {
                this.bot = bot;
            }

            public String getIntent() {
                return intent;
            }

            public void setIntent(String intent) {
                this.intent = intent;
            }

            public double getConfidence() {
                return confidence;
            }

            public void setConfidence(double confidence) {
                this.confidence = confidence;
            }

            public boolean isIntent_need_clarify() {
                return intent_need_clarify;
            }

            public void setIntent_need_clarify(boolean intent_need_clarify) {
                this.intent_need_clarify = intent_need_clarify;
            }

            public MatchInfoBean getMatch_info() {
                return match_info;
            }

            public void setMatch_info(MatchInfoBean match_info) {
                this.match_info = match_info;
            }

            public List<SlotsBean> getSlots() {
                return slots;
            }

            public void setSlots(List<SlotsBean> slots) {
                this.slots = slots;
            }

            public static class MatchInfoBean {
                /**
                 * matched_length : 12
                 * matched_pattern : oooooo
                 * matched_ruleid : 4
                 */

                private int matched_length;
                private String matched_pattern;
                private int matched_ruleid;

                public int getMatched_length() {
                    return matched_length;
                }

                public void setMatched_length(int matched_length) {
                    this.matched_length = matched_length;
                }

                public String getMatched_pattern() {
                    return matched_pattern;
                }

                public void setMatched_pattern(String matched_pattern) {
                    this.matched_pattern = matched_pattern;
                }

                public int getMatched_ruleid() {
                    return matched_ruleid;
                }

                public void setMatched_ruleid(int matched_ruleid) {
                    this.matched_ruleid = matched_ruleid;
                }
            }

            public static class SlotsBean {
                /**
                 * begin : 4
                 * length : 2
                 * name : user_name
                 * normalized_word :
                 * original_word : 张三
                 */

                private int begin;
                private int length;
                private String name;
                private String normalized_word;
                private String original_word;

                public int getBegin() {
                    return begin;
                }

                public void setBegin(int begin) {
                    this.begin = begin;
                }

                public int getLength() {
                    return length;
                }

                public void setLength(int length) {
                    this.length = length;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getNormalized_word() {
                    return normalized_word;
                }

                public void setNormalized_word(String normalized_word) {
                    this.normalized_word = normalized_word;
                }

                public String getOriginal_word() {
                    return original_word;
                }

                public void setOriginal_word(String original_word) {
                    this.original_word = original_word;
                }
            }
        }
    }

    public static class SchemaBean {
        /**
         * id : 1852794992
         * bot : phone
         * intent : MAKE_CALL
         * confidence : 100
         * intent_need_clarify : false
         * slots : [{"begin":4,"length":2,"name":"user_name","merge_method":"new","normalized_word":"","original_word":"张三"}]
         */

        private String bot;
        private String intent;
        private double confidence;
        private boolean intent_need_clarify;
        private List<SlotsBeanX> slots;
        private int item_index;
        private String original_text;

        public int getItem_index() {
            return item_index;
        }

        public void setItem_index(int item_index) {
            this.item_index = item_index;
        }

        public String getOriginal_text() {
            return original_text;
        }

        public void setOriginal_text(String original_text) {
            this.original_text = original_text;
        }

        public String getId() {
            return bot;
        }

        public void setId(String id) {
            this.bot = id;
        }

        public String getBot() {
            return bot;
        }

        public void setBot(String bot) {
            this.bot = bot;
        }

        public String getIntent() {
            return intent;
        }

        public void setIntent(String intent) {
            this.intent = intent;
        }

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }

        public boolean isIntent_need_clarify() {
            return intent_need_clarify;
        }

        public void setIntent_need_clarify(boolean intent_need_clarify) {
            this.intent_need_clarify = intent_need_clarify;
        }

        public List<SlotsBeanX> getSlots() {
            return slots;
        }

        public void setSlots(List<SlotsBeanX> slots) {
            this.slots = slots;
        }

        public static class SlotsBeanX {
            /**
             * begin : 4
             * length : 2
             * name : user_name
             * merge_method : new
             * normalized_word :
             * original_word : 张三
             */

            private int begin;
            private int length;
            private String name;
            private String merge_method;
            private String normalized_word;
            private String original_word;

            public int getBegin() {
                return begin;
            }

            public void setBegin(int begin) {
                this.begin = begin;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMerge_method() {
                return merge_method;
            }

            public void setMerge_method(String merge_method) {
                this.merge_method = merge_method;
            }

            public String getNormalized_word() {
                return normalized_word;
            }

            public void setNormalized_word(String normalized_word) {
                this.normalized_word = normalized_word;
            }

            public String getOriginal_word() {
                return original_word;
            }

            public void setOriginal_word(String original_word) {
                this.original_word = original_word;
            }
        }
    }

    public static class ActionListBean {
        /**
         * action : satisfy
         * action_id : make_call_satisfy
         * confidence : 100
         */

        private String action;
        private String action_id;
        private double confidence;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getAction_id() {
            return action_id;
        }

        public void setAction_id(String action_id) {
            this.action_id = action_id;
        }

        public double getConfidence() {
            return confidence;
        }

        public void setConfidence(double confidence) {
            this.confidence = confidence;
        }
    }
}
