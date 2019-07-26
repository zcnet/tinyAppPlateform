package com.zcnet.voiceinteractionmodule;

import com.google.gson.Gson;
import com.z.tinyapp.utils.logs.sLog;
import com.zcnet.voiceinteractionmodule.jsonEntity.unit2.Response;
import com.zcnet.voiceinteractionmodule.jsonEntity.unit2.Result;
import com.zcnet.voiceinteractionmodule.jsonEntity.unit2.Unit2JsonRootBean;
import com.zcnet.voiceinteractionmodule.jsonEntity.unit2.ZRes;

import java.util.ArrayList;
import java.util.List;

public class VITest {
    public static void main(String[] args) throws Exception {
//        voiceInteraction obj = new voiceInteraction();
//        obj.robotAIDialog("{\"accessToken\":\"IhcjQdkKgbVsV6jRnhUcJOYcSX22LrnJwO1lAYYmsuaorZu5VPZeKq\",\"botId\":1,\"sessionId\":\"1cc43160dc474915be44c00596239e521\",\"content\":\"今天上海天气好吗\",\"frontInfo\":\"{\\\"btConnected\\\":1,\\\"contactBookSync\\\":1}\"}");

//
//        Stack stack  = new Stack();
//        stack.push(new Object());
//        String testData = "{'A':'a','B':'b'}";
//        Gson gson = new Gson();
//        Map<String,String> map = gson.fromJson(testData, Map.class);
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            System.out.println(entry.getKey() +"---------"+ entry.getValue());
//        }
//        voiceAPI voiceAPI = new voiceAPI();
//        voiceAPI.addAIProcess("{\"accessToken\":\"IhcjQdkKgbVsV6jRnhUcJOYcSX22LrnJwO1lAYYmsuaorZu5VPZeKq\",\"botId\":1,\"sessionId\":\"\",\"content\":\"今天上海天气好吗\",\"frontInfo\":\"{\\\"btConnected\\\":1,\\\"contactBookSync\\\":1}\"}");
    }

    public static Unit2JsonRootBean getBaiduRespone(){

        Gson gson = new Gson();
        Unit2JsonRootBean a = gson.fromJson("{\n" +
                "    \"result\": {\n" +
                "        \"version\": \"2.0\",\n" +
                "        \"timestamp\": \"2019-02-22 13:08:02.790\",\n" +
                "        \"service_id\": \"S10510\",\n" +
                "        \"log_id\": \"d8012ee0-365f-11e9-90d4-a7e6a5de6bf0\",\n" +
                "        \"bot_session\": \"service-session-id-1550812082790-2c6549854b1c426dbe695eddb92bbe42\",\n" +
                "        \"interaction_id\": \"service-interactive-id-1550812082790-e942e089059340b688939684d7990117\",\n" +
                "        \"response_list\": [\n" +
                "            {\n" +
                "                \"status\": 0,\n" +
                "                \"msg\": \"ok\",\n" +
                "                \"origin\": \"14509\",\n" +
                "                \"schema\": {\n" +
                "                    \"intent_confidence\": 100,\n" +
                "                    \"slots\": [\n" +
                "                        {\n" +
                "                            \"word_type\": \"\",\n" +
                "                            \"confidence\": 100,\n" +
                "                            \"length\": 2,\n" +
                "                            \"name\": \"user_name\",\n" +
                "                            \"original_word\": \"张三\",\n" +
                "                            \"sub_slots\": [],\n" +
                "                            \"session_offset\": 0,\n" +
                "                            \"begin\": 2,\n" +
                "                            \"normalized_word\": \"张三\",\n" +
                "                            \"merge_method\": \"updated\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"domain_confidence\": 0,\n" +
                "                    \"intent\": \"MAKE_CALL\"\n" +
                "                },\n" +
                "                \"action_list\": [\n" +
                "                    {\n" +
                "                        \"action_id\": \"make_call_satisfy\",\n" +
                "                        \"refine_detail\": {\n" +
                "                            \"option_list\": [],\n" +
                "                            \"interact\": \"\",\n" +
                "                            \"clarify_reason\": \"\"\n" +
                "                        },\n" +
                "                        \"confidence\": 100,\n" +
                "                        \"custom_reply\": \"{\\\"event_name\\\":\\\"EXEC\\\",\\\"func\\\":\\\"phone_make_call\\\"}\\n\",\n" +
                "                        \"say\": \"\",\n" +
                "                        \"type\": \"event\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"qu_res\": {\n" +
                "                    \"candidates\": [\n" +
                "                        {\n" +
                "                            \"intent_confidence\": 100,\n" +
                "                            \"match_info\": \"{\\\"group_id\\\":\\\"25\\\",\\\"id\\\":\\\"1117349\\\",\\\"informal_word\\\":\\\"的\\\",\\\"match_keywords\\\":\\\" kw_call:拨打  kw_phone:电话\\\",\\\"match_pattern\\\":\\\"[D:kw_call]\\\\t[D:user_name]\\\\t[D:kw_phone]\\\",\\\"ori_pattern\\\":\\\"[D:kw_call]\\\\t[D:user_name]\\\\t[D:kw_phone]\\\",\\\"ori_slots\\\":{\\\"confidence\\\":100.0,\\\"domain_confidence\\\":0.0,\\\"extra_info\\\":{},\\\"from_who\\\":\\\"smart_qu\\\",\\\"intent\\\":\\\"MAKE_CALL\\\",\\\"intent_confidence\\\":100.0,\\\"intent_need_clarify\\\":false,\\\"match_info\\\":\\\"[D:kw_call] kw_call:拨打\\\\t[D:user_name] \\\\t[D:kw_phone] kw_phone:电话\\\",\\\"slots\\\":[{\\\"begin\\\":0,\\\"confidence\\\":100.0,\\\"father_idx\\\":-1,\\\"length\\\":4,\\\"name\\\":\\\"kw_call\\\",\\\"need_clarify\\\":false,\\\"normalized_word\\\":\\\"\\\",\\\"original_word\\\":\\\"拨打\\\",\\\"word_type\\\":\\\"\\\"},{\\\"begin\\\":4,\\\"confidence\\\":100.0,\\\"father_idx\\\":-1,\\\"length\\\":4,\\\"name\\\":\\\"user_name\\\",\\\"need_clarify\\\":false,\\\"normalized_word\\\":\\\"\\\",\\\"original_word\\\":\\\"张三\\\",\\\"word_type\\\":\\\"\\\"},{\\\"begin\\\":10,\\\"confidence\\\":100.0,\\\"father_idx\\\":-1,\\\"length\\\":4,\\\"name\\\":\\\"kw_phone\\\",\\\"need_clarify\\\":false,\\\"normalized_word\\\":\\\"\\\",\\\"original_word\\\":\\\"电话\\\",\\\"word_type\\\":\\\"\\\"}]},\\\"real_threshold\\\":1.0,\\\"threshold\\\":1.0}\",\n" +
                "                            \"slots\": [\n" +
                "                                {\n" +
                "                                    \"word_type\": \"\",\n" +
                "                                    \"father_idx\": -1,\n" +
                "                                    \"confidence\": 100,\n" +
                "                                    \"length\": 2,\n" +
                "                                    \"name\": \"user_name\",\n" +
                "                                    \"original_word\": \"张三\",\n" +
                "                                    \"begin\": 2,\n" +
                "                                    \"need_clarify\": false,\n" +
                "                                    \"normalized_word\": \"张三\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"extra_info\": {\n" +
                "                                \"group_id\": \"25\",\n" +
                "                                \"real_threshold\": \"1\",\n" +
                "                                \"threshold\": \"1\"\n" +
                "                            },\n" +
                "                            \"confidence\": 100,\n" +
                "                            \"domain_confidence\": 0,\n" +
                "                            \"from_who\": \"pow-slu-lev1\",\n" +
                "                            \"intent\": \"MAKE_CALL\",\n" +
                "                            \"intent_need_clarify\": false\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"intent_confidence\": 100,\n" +
                "                            \"match_info\": \"{\\\"group_id\\\":\\\"26\\\",\\\"id\\\":\\\"1117350\\\",\\\"informal_word\\\":\\\"的\\\",\\\"match_keywords\\\":\\\" kw_call:拨打  kw_phone:电话\\\",\\\"match_pattern\\\":\\\"[D:kw_call]\\\\t[D:user_name]\\\\t[D:kw_phone]\\\",\\\"ori_pattern\\\":\\\"[D:kw_call]\\\\t[D:user_name]\\\\t[D:kw_phone]\\\",\\\"ori_slots\\\":{\\\"confidence\\\":100.0,\\\"domain_confidence\\\":0.0,\\\"extra_info\\\":{},\\\"from_who\\\":\\\"smart_qu\\\",\\\"intent\\\":\\\"MAKE_CALL\\\",\\\"intent_confidence\\\":100.0,\\\"intent_need_clarify\\\":false,\\\"match_info\\\":\\\"[D:kw_call] kw_call:拨打\\\\t[D:user_name] \\\\t[D:kw_phone] kw_phone:电话\\\",\\\"slots\\\":[{\\\"begin\\\":0,\\\"confidence\\\":100.0,\\\"father_idx\\\":-1,\\\"length\\\":4,\\\"name\\\":\\\"kw_call\\\",\\\"need_clarify\\\":false,\\\"normalized_word\\\":\\\"\\\",\\\"original_word\\\":\\\"拨打\\\",\\\"word_type\\\":\\\"\\\"},{\\\"begin\\\":4,\\\"confidence\\\":100.0,\\\"father_idx\\\":-1,\\\"length\\\":4,\\\"name\\\":\\\"user_name\\\",\\\"need_clarify\\\":false,\\\"normalized_word\\\":\\\"\\\",\\\"original_word\\\":\\\"张三\\\",\\\"word_type\\\":\\\"\\\"},{\\\"begin\\\":10,\\\"confidence\\\":100.0,\\\"father_idx\\\":-1,\\\"length\\\":4,\\\"name\\\":\\\"kw_phone\\\",\\\"need_clarify\\\":false,\\\"normalized_word\\\":\\\"\\\",\\\"original_word\\\":\\\"电话\\\",\\\"word_type\\\":\\\"\\\"}]},\\\"real_threshold\\\":1.0,\\\"threshold\\\":1.0}\",\n" +
                "                            \"slots\": [\n" +
                "                                {\n" +
                "                                    \"word_type\": \"\",\n" +
                "                                    \"father_idx\": -1,\n" +
                "                                    \"confidence\": 100,\n" +
                "                                    \"length\": 2,\n" +
                "                                    \"name\": \"user_name\",\n" +
                "                                    \"original_word\": \"张三\",\n" +
                "                                    \"begin\": 2,\n" +
                "                                    \"need_clarify\": false,\n" +
                "                                    \"normalized_word\": \"张三\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"extra_info\": {\n" +
                "                                \"group_id\": \"26\",\n" +
                "                                \"real_threshold\": \"1\",\n" +
                "                                \"threshold\": \"1\"\n" +
                "                            },\n" +
                "                            \"confidence\": 100,\n" +
                "                            \"domain_confidence\": 0,\n" +
                "                            \"from_who\": \"pow-slu-lev1\",\n" +
                "                            \"intent\": \"MAKE_CALL\",\n" +
                "                            \"intent_need_clarify\": false\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"intent_confidence\": 100,\n" +
                "                            \"match_info\": \"{\\\"group_id\\\":\\\"38\\\",\\\"id\\\":\\\"1117363\\\",\\\"informal_word\\\":\\\"的\\\",\\\"match_keywords\\\":\\\" kw_call:拨打\\\",\\\"match_pattern\\\":\\\"[D:kw_call][D:user_name]\\\",\\\"ori_pattern\\\":\\\"[D:kw_call][D:user_name]\\\",\\\"ori_slots\\\":{\\\"confidence\\\":100.0,\\\"domain_confidence\\\":0.0,\\\"extra_info\\\":{},\\\"from_who\\\":\\\"smart_qu\\\",\\\"intent\\\":\\\"MAKE_CALL\\\",\\\"intent_confidence\\\":100.0,\\\"intent_need_clarify\\\":false,\\\"match_info\\\":\\\"[D:kw_call][D:user_name] kw_call:拨打\\\",\\\"slots\\\":[{\\\"begin\\\":0,\\\"confidence\\\":100.0,\\\"father_idx\\\":-1,\\\"length\\\":4,\\\"name\\\":\\\"kw_call\\\",\\\"need_clarify\\\":false,\\\"normalized_word\\\":\\\"\\\",\\\"original_word\\\":\\\"拨打\\\",\\\"word_type\\\":\\\"\\\"},{\\\"begin\\\":4,\\\"confidence\\\":100.0,\\\"father_idx\\\":-1,\\\"length\\\":4,\\\"name\\\":\\\"user_name\\\",\\\"need_clarify\\\":false,\\\"normalized_word\\\":\\\"\\\",\\\"original_word\\\":\\\"张三\\\",\\\"word_type\\\":\\\"\\\"}]},\\\"real_threshold\\\":0.7142857313156128,\\\"threshold\\\":0.6999999880790710}\",\n" +
                "                            \"slots\": [\n" +
                "                                {\n" +
                "                                    \"word_type\": \"\",\n" +
                "                                    \"father_idx\": -1,\n" +
                "                                    \"confidence\": 100,\n" +
                "                                    \"length\": 2,\n" +
                "                                    \"name\": \"user_name\",\n" +
                "                                    \"original_word\": \"张三\",\n" +
                "                                    \"begin\": 2,\n" +
                "                                    \"need_clarify\": false,\n" +
                "                                    \"normalized_word\": \"张三\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"extra_info\": {\n" +
                "                                \"group_id\": \"38\",\n" +
                "                                \"real_threshold\": \"0.714286\",\n" +
                "                                \"threshold\": \"0.7\"\n" +
                "                            },\n" +
                "                            \"confidence\": 100,\n" +
                "                            \"domain_confidence\": 0,\n" +
                "                            \"from_who\": \"pow-slu-lev1\",\n" +
                "                            \"intent\": \"MAKE_CALL\",\n" +
                "                            \"intent_need_clarify\": false\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"intent_confidence\": 99.982353210449,\n" +
                "                            \"match_info\": \"\",\n" +
                "                            \"slots\": [\n" +
                "                                {\n" +
                "                                    \"word_type\": \"\",\n" +
                "                                    \"father_idx\": -1,\n" +
                "                                    \"confidence\": 99.855819702148,\n" +
                "                                    \"length\": 2,\n" +
                "                                    \"name\": \"user_name\",\n" +
                "                                    \"original_word\": \"张三\",\n" +
                "                                    \"begin\": 2,\n" +
                "                                    \"need_clarify\": false,\n" +
                "                                    \"normalized_word\": \"\"\n" +
                "                                }\n" +
                "                            ],\n" +
                "                            \"extra_info\": {},\n" +
                "                            \"confidence\": 99.982353210449,\n" +
                "                            \"domain_confidence\": 0,\n" +
                "                            \"from_who\": \"mult-slu\",\n" +
                "                            \"intent\": \"MAKE_CALL\",\n" +
                "                            \"intent_need_clarify\": false\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"qu_res_chosen\": \"{\\\"confidence\\\":100.0,\\\"domain_confidence\\\":0.0,\\\"extra_info\\\":{\\\"group_id\\\":\\\"25\\\",\\\"real_threshold\\\":\\\"1\\\",\\\"threshold\\\":\\\"1\\\"},\\\"from_who\\\":\\\"pow-slu-lev1\\\",\\\"intent\\\":\\\"MAKE_CALL\\\",\\\"intent_confidence\\\":100.0,\\\"intent_need_clarify\\\":false,\\\"match_info\\\":\\\"{\\\\\\\"group_id\\\\\\\":\\\\\\\"25\\\\\\\",\\\\\\\"id\\\\\\\":\\\\\\\"1117349\\\\\\\",\\\\\\\"informal_word\\\\\\\":\\\\\\\"的\\\\\\\",\\\\\\\"match_keywords\\\\\\\":\\\\\\\" kw_call:拨打  kw_phone:电话\\\\\\\",\\\\\\\"match_pattern\\\\\\\":\\\\\\\"[D:kw_call]\\\\\\\\t[D:user_name]\\\\\\\\t[D:kw_phone]\\\\\\\",\\\\\\\"ori_pattern\\\\\\\":\\\\\\\"[D:kw_call]\\\\\\\\t[D:user_name]\\\\\\\\t[D:kw_phone]\\\\\\\",\\\\\\\"ori_slots\\\\\\\":{\\\\\\\"confidence\\\\\\\":100.0,\\\\\\\"domain_confidence\\\\\\\":0.0,\\\\\\\"extra_info\\\\\\\":{},\\\\\\\"from_who\\\\\\\":\\\\\\\"smart_qu\\\\\\\",\\\\\\\"intent\\\\\\\":\\\\\\\"MAKE_CALL\\\\\\\",\\\\\\\"intent_confidence\\\\\\\":100.0,\\\\\\\"intent_need_clarify\\\\\\\":false,\\\\\\\"match_info\\\\\\\":\\\\\\\"[D:kw_call] kw_call:拨打\\\\\\\\t[D:user_name] \\\\\\\\t[D:kw_phone] kw_phone:电话\\\\\\\",\\\\\\\"slots\\\\\\\":[{\\\\\\\"begin\\\\\\\":0,\\\\\\\"confidence\\\\\\\":100.0,\\\\\\\"father_idx\\\\\\\":-1,\\\\\\\"length\\\\\\\":4,\\\\\\\"name\\\\\\\":\\\\\\\"kw_call\\\\\\\",\\\\\\\"need_clarify\\\\\\\":false,\\\\\\\"normalized_word\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"original_word\\\\\\\":\\\\\\\"拨打\\\\\\\",\\\\\\\"word_type\\\\\\\":\\\\\\\"\\\\\\\"},{\\\\\\\"begin\\\\\\\":4,\\\\\\\"confidence\\\\\\\":100.0,\\\\\\\"father_idx\\\\\\\":-1,\\\\\\\"length\\\\\\\":4,\\\\\\\"name\\\\\\\":\\\\\\\"user_name\\\\\\\",\\\\\\\"need_clarify\\\\\\\":false,\\\\\\\"normalized_word\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"original_word\\\\\\\":\\\\\\\"张三\\\\\\\",\\\\\\\"word_type\\\\\\\":\\\\\\\"\\\\\\\"},{\\\\\\\"begin\\\\\\\":10,\\\\\\\"confidence\\\\\\\":100.0,\\\\\\\"father_idx\\\\\\\":-1,\\\\\\\"length\\\\\\\":4,\\\\\\\"name\\\\\\\":\\\\\\\"kw_phone\\\\\\\",\\\\\\\"need_clarify\\\\\\\":false,\\\\\\\"normalized_word\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"original_word\\\\\\\":\\\\\\\"电话\\\\\\\",\\\\\\\"word_type\\\\\\\":\\\\\\\"\\\\\\\"}]},\\\\\\\"real_threshold\\\\\\\":1.0,\\\\\\\"threshold\\\\\\\":1.0}\\\",\\\"slots\\\":[{\\\"begin\\\":4,\\\"confidence\\\":100.0,\\\"father_idx\\\":-1,\\\"length\\\":4,\\\"name\\\":\\\"user_name\\\",\\\"need_clarify\\\":false,\\\"normalized_word\\\":\\\"张三\\\",\\\"original_word\\\":\\\"张三\\\",\\\"word_type\\\":\\\"\\\"}]}\\n\",\n" +
                "                    \"sentiment_analysis\": {\n" +
                "                        \"pval\": 0.999,\n" +
                "                        \"label\": \"1\"\n" +
                "                    },\n" +
                "                    \"lexical_analysis\": [\n" +
                "                        {\n" +
                "                            \"etypes\": [],\n" +
                "                            \"basic_word\": [\n" +
                "                                \"拨打\"\n" +
                "                            ],\n" +
                "                            \"weight\": 0.325,\n" +
                "                            \"term\": \"拨打\",\n" +
                "                            \"type\": \"34\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"etypes\": [\n" +
                "                                \"sys_per\",\n" +
                "                                \"sys_per_generic\",\n" +
                "                                \"user_name\"\n" +
                "                            ],\n" +
                "                            \"basic_word\": [\n" +
                "                                \"张\",\n" +
                "                                \"三\"\n" +
                "                            ],\n" +
                "                            \"weight\": 0.269,\n" +
                "                            \"term\": \"张三\",\n" +
                "                            \"type\": \"sys_per\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"etypes\": [],\n" +
                "                            \"basic_word\": [\n" +
                "                                \"的\"\n" +
                "                            ],\n" +
                "                            \"weight\": 0.134,\n" +
                "                            \"term\": \"的\",\n" +
                "                            \"type\": \"33\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"etypes\": [],\n" +
                "                            \"basic_word\": [\n" +
                "                                \"电话\"\n" +
                "                            ],\n" +
                "                            \"weight\": 0.269,\n" +
                "                            \"term\": \"电话\",\n" +
                "                            \"type\": \"21\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"raw_query\": \"拨打张三的电话\",\n" +
                "                    \"status\": 0,\n" +
                "                    \"timestamp\": 0\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"status\": 0,\n" +
                "                \"msg\": \"ok\",\n" +
                "                \"origin\": \"29376\",\n" +
                "                \"schema\": {\n" +
                "                    \"intent_confidence\": 0,\n" +
                "                    \"slots\": [],\n" +
                "                    \"domain_confidence\": 0,\n" +
                "                    \"intent\": \"\"\n" +
                "                },\n" +
                "                \"action_list\": [\n" +
                "                    {\n" +
                "                        \"action_id\": \"fail_action\",\n" +
                "                        \"refine_detail\": {\n" +
                "                            \"option_list\": [],\n" +
                "                            \"interact\": \"\",\n" +
                "                            \"clarify_reason\": \"\"\n" +
                "                        },\n" +
                "                        \"confidence\": 100,\n" +
                "                        \"custom_reply\": \"\",\n" +
                "                        \"say\": \"我不知道应该怎么答复您。\",\n" +
                "                        \"type\": \"failure\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"qu_res\": {\n" +
                "                    \"candidates\": [],\n" +
                "                    \"qu_res_chosen\": \"\",\n" +
                "                    \"sentiment_analysis\": {\n" +
                "                        \"pval\": 0.999,\n" +
                "                        \"label\": \"1\"\n" +
                "                    },\n" +
                "                    \"lexical_analysis\": [\n" +
                "                        {\n" +
                "                            \"etypes\": [],\n" +
                "                            \"basic_word\": [\n" +
                "                                \"拨打\"\n" +
                "                            ],\n" +
                "                            \"weight\": 0.325,\n" +
                "                            \"term\": \"拨打\",\n" +
                "                            \"type\": \"34\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"etypes\": [\n" +
                "                                \"sys_per\",\n" +
                "                                \"sys_per_generic\"\n" +
                "                            ],\n" +
                "                            \"basic_word\": [\n" +
                "                                \"张\",\n" +
                "                                \"三\"\n" +
                "                            ],\n" +
                "                            \"weight\": 0.269,\n" +
                "                            \"term\": \"张三\",\n" +
                "                            \"type\": \"sys_per\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"etypes\": [],\n" +
                "                            \"basic_word\": [\n" +
                "                                \"的\"\n" +
                "                            ],\n" +
                "                            \"weight\": 0.134,\n" +
                "                            \"term\": \"的\",\n" +
                "                            \"type\": \"33\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"etypes\": [],\n" +
                "                            \"basic_word\": [\n" +
                "                                \"电话\"\n" +
                "                            ],\n" +
                "                            \"weight\": 0.269,\n" +
                "                            \"term\": \"电话\",\n" +
                "                            \"type\": \"21\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"raw_query\": \"拨打张三的电话\",\n" +
                "                    \"status\": 0,\n" +
                "                    \"timestamp\": 0\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"error_code\": 0\n" +
                "}",Unit2JsonRootBean.class);
        return a;
    }

    public static Unit2JsonRootBean getZNLPResponse(String str){
        Unit2JsonRootBean bean = new Unit2JsonRootBean();
        bean.setResult(new Result());
        List<Response> list = new ArrayList<>();
        SessionInstance.getInstance().addToPickuplist("听沃尔的他的沃尔玛超市");
        SessionInstance.getInstance().addToPickuplist("家乐福超市");
        SessionInstance.getInstance().addToPickuplist("家乐福停车场");
        SessionInstance.getInstance().addToPickuplist("阳光家园小区");
        SessionInstance.getInstance().addToPickuplist("荣新小学校");
        String znlpStr = SessionInstance.getInstance().analyze(str);
        SessionInstance.getInstance().clearPickuplist();
        sLog.e("sungetZNLPResponse","znlp str - >> "+znlpStr);
        ZRes res = new Gson().fromJson(znlpStr, ZRes.class);
        if ("pickuplist".equals(res.getAction()) && res.getPickuplist() != null){
            list.add(res.getPickuplist());
        } else {
            list.add(res.getBot());
        }
        bean.setAction(res.getAction());
        bean.getResult().setResponse_list(list);
        return bean;
    }
}
