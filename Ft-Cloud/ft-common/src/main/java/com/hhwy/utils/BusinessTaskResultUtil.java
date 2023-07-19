package com.hhwy.utils;
/**
 * @description 业务数据获取流程信息组件
 * @date 2023-02-02 17:55
 * @author zq
 */

import com.hhwy.enums.FlowEnum;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.exception.CustomBusinessException;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author zq
 * @date 2023年02月02日 17:55
 */
public class BusinessTaskResultUtil {
    //    private static IPmsServiceApi pmsServiceApi;
//    private static IActivitiServiceApi activitiServiceApi;
    static {
//        pmsServiceApi = SpringUtils.getBean(IPmsServiceApi.class);
//        activitiServiceApi=SpringUtils.getBean(IActivitiServiceApi.class);
    }

    public static <T extends CommonBaseEntity> List<T> handleProcessData(List<T> list, FlowEnum flowEnum) {

        String tableName = flowEnum.getTableName();
        CommonAssert.notBlank(tableName, "表名称不能为空");
        String processKey = flowEnum.getProcessKey();
        CommonAssert.notBlank(processKey, "流程key不能为空");
        if (CollectionUtils.isEmpty(list)) return list;
        List<Long> ids = list.stream().map(CommonBaseEntity::getId).filter(Objects::nonNull).collect(Collectors.toList());
        // 如果是一个数据的话  就有可能是新增
        if (list.size() == 1 && list.get(0).getId() == null) {
            T insertData = list.get(0);
            insertData.setBusinessTableName(tableName);
            insertData.setProcessKey(processKey);
            return list;
        }


        if (CollectionUtils.isEmpty(ids) || ids.size() != list.size())
            throw new CustomBusinessException("业务数据Id不能为空");

//        if(!ObjectNullUtil.isEmpty(planList)){
//            List<Long> ids = planList.stream().map(t -> t.getId()).collect(Collectors.toList());
//            Activit  activityInfoVo = new ActivityBusinessInfo();
////            activityInfoVo.setBusinessId(StringUtils.join(ids,","));
//            activityInfoVo.setBusinessIds(ids);
//            activityInfoVo.setBusinessTableName(tableName);
//            AjaxResult result = pmsServiceApi.selActByInfo(activityInfoVo);
//            if("200".equals(result.get("code").toString())){
//                List<ActivityBusinessInfo> data = JSONArray.parseArray(JSON.toJSONString(result.get("data")), ActivityBusinessInfo.class);
//                //@auth lcf 2023年02月38 处理流程退回第一节点并判断流程发起人是否当前系统登录人
//                Map<String, JudeFirstNodeVo> judeFirstNodeVoMap = handleRecallBack(data);
//
//                Map<String, ActivityBusinessInfo> businessInfoMap = data.stream().collect(Collectors.groupingBy(t -> t.getBusinessId(), Collectors.collectingAndThen(Collectors.toList(), v -> v.get(0))));
//                for (CommonBaseEntity totalDemandPlan : planList) {
//                    ActivityBusinessInfo activityBusinessInfo = businessInfoMap.get(totalDemandPlan.getId().toString());
//                    if(!ObjectNullUtil.isEmpty(activityBusinessInfo)){
//                        totalDemandPlan.setTaskId(activityBusinessInfo.getProcessTaskId());
//                        totalDemandPlan.setTaskStatus(activityBusinessInfo.getTaskStatus());
//                        totalDemandPlan.setInstanceId(activityBusinessInfo.getProcessInstanceId());
//                        totalDemandPlan.setProcessTaskMan(activityBusinessInfo.getProcessTaskMan());
//                        totalDemandPlan.setProcessTaskName(activityBusinessInfo.getProcessTaskName());
//                        totalDemandPlan.setFormUrl(activityBusinessInfo.getFormUrl());
//                        totalDemandPlan.setBusinessTableName(activityBusinessInfo.getBusinessTableName());
//                        totalDemandPlan.setIsDoc(activityBusinessInfo.getIsDoc());
//                        totalDemandPlan.setActivityTableId(activityBusinessInfo.getId().toString());
//                        JudeFirstNodeVo nodeVo = judeFirstNodeVoMap.get(activityBusinessInfo.getProcessInstanceId());
//                        totalDemandPlan.setIsFirstNode("0");
//                        if(null!=nodeVo){
//                            if(nodeVo.getIsFirstNode().equals("true")){
//                                //是第一节点  判断发起人是否是当前登录人
//                                String userName = SecurityUtils.getUserName();
//                                String processTaskMan = activityBusinessInfo.getProcessTaskMan();
//                                if(userName.equals(processTaskMan)){
//                                    totalDemandPlan.setIsFirstNode("1");
//                                }
//                            }
//                        }
//
//                    }
//                }
//            }
//        }

        return list;
    }


    /**
     * @param t
     * @param flowEnum
     * @param <T>
     */
    public static <T extends CommonBaseEntity> T handleProcessData(T t, FlowEnum flowEnum) {
        return handleProcessData(Collections.singletonList(t), flowEnum).get(0);
    }


    /**
     * 根据taskId 查询改节点是否是第一节点
     *
     * @param data
     * @return
     */
//    private static Map<String, JudeFirstNodeVo> handleRecallBack(List<ActivityBusinessInfo> data){
//        List<ActivityBusinessInfo> list = data.stream().filter(info -> !"-".equals(info.getProcessTaskId()) && info.getProcessTaskId()!=null).collect(Collectors.toList());
//        List<String> taskIdList = list.stream().map(e -> e.getProcessTaskId()).collect(Collectors.toList());
//        String[] taskIdsStr = taskIdList.toArray(new String[taskIdList.size()]);
//        AjaxResult result = activitiServiceApi.judgeFirstNode(taskIdsStr);
//        List<Map<String,Object>> mapList =null;
//        if(result.get("code").toString().equals("200")){
//            mapList = (List<Map<String,Object>>)result.get("data");
//        }
//        Map<String, JudeFirstNodeVo> judeFirstNodeVoMap=new HashMap<>();
//        if(!CollectionUtils.isEmpty(mapList)){
//            for (int i = 0; i < mapList.size(); i++) {
//                Map map = mapList.get(i);
//                String isFirstNode = map.get("isFirstNode").toString();
//                String processInstanceId = map.get("processInstanceId").toString();
//                String taskId = map.get("taskId").toString();
//                JudeFirstNodeVo vo=new JudeFirstNodeVo();
//                vo.setIsFirstNode(isFirstNode);
//                vo.setProcessInstanceId(processInstanceId);
//                vo.setTaskId(taskId);
//                judeFirstNodeVoMap.put(processInstanceId,vo);
//            }
//        }
//        return judeFirstNodeVoMap;
//    }

}
