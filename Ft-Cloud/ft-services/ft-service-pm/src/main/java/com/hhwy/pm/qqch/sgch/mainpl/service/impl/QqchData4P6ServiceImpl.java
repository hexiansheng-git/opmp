package com.hhwy.pm.qqch.sgch.mainpl.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.ActivityConstField;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.PredecessorRelationships;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.ProjectInfo;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.WbsInfo;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.qqch.sgch.mainpl.domain.*;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchData4P6Service;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemPreService;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.pm.qqch.sgch.prodplan.service.IQqchProdPlanService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class QqchData4P6ServiceImpl implements IQqchData4P6Service {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IQqchMainPlanItemService qqchMainPlanItemService;

    @Autowired
    private SystemServiceApi systemServiceApi;
    
    @Autowired
    private IQqchMainPlanItemPreService iQqchMainPlanItemPreService;

    @Autowired
    private IQqchProdPlanService qqchProdPlanService;

    @Value("${p6.ip_port}")
    private String p6IpPort;

    private String pre = "";

    @Override
    public List<QqchMainPlanItem> initQqchData4P6(String tenantKey, BigDecimal version) {

        version = VersionUtil.getVersion(QqchMainPlanItem.TABLE_NAME, version);

        List<QqchMainPlanItem> returnList = new ArrayList<>();

        System.out.println("--获取p6项目数据--租户:" + tenantKey + "--开始:" +  DateUtils.getTime());
        ProjectInfo projectInfo = getProjectInfo(tenantKey);
        System.out.println("--获取p6项目数据--租户:" + tenantKey + "--结束:" +  DateUtils.getTime());
        if(projectInfo == null)  return returnList;

        String urlwbs= p6IpPort + pre + "/wbsInfo";
        String urlwork= p6IpPort + pre + "/activityInfo";

        HttpEntity<?> entity=new HttpEntity(new HttpHeaders());

        Map<String, Object> params = new HashMap<>();
        String projectId = projectInfo.getProjectCode();
        params.put("projectId", StringUtils.isEmpty(projectId) ? tenantKey : projectId);
        ParameterizedTypeReference<List<WbsInfo>> responseType4Wbs = new ParameterizedTypeReference<List<WbsInfo>>() {};
        ParameterizedTypeReference<List<ActivityConstField>> responseType4Work = new ParameterizedTypeReference<List<ActivityConstField>>() {};

        System.out.println("--获取p6 wbs数据--租户:" + tenantKey + "--开始:" +  DateUtils.getTime());
        // 获取p6 wbs数据
        ResponseEntity<List<WbsInfo>> wbsResult = restTemplate.exchange(urlwbs + "?projectId={projectId}", HttpMethod.GET, entity, responseType4Wbs, params);
        System.out.println("--获取p6 wbs数据--租户:" + tenantKey + "--结束:" +  DateUtils.getTime() + "-- 数量:" + (wbsResult.getBody() == null ? 0 : wbsResult.getBody().size()));
        System.out.println("--获取p6 作业数据--租户:" + tenantKey + "--开始:" +  DateUtils.getTime());
        // 获取p6 作业数据
        ResponseEntity<List<ActivityConstField>> workResult = restTemplate.exchange(urlwork + "?projectId={projectId}", HttpMethod.GET, entity, responseType4Work, params);
        System.out.println("--获取p6 作业数据--租户:" + tenantKey + "--结束:" +  DateUtils.getTime() + "-- 数量:" + (workResult.getBody() == null ? 0 : workResult.getBody().size()));

        // 获取转换后的p6逻辑关系数据
        List<QqchMainPlanItemPre> relInfos = new ArrayList<>();//getPre(projectId);

        List<WbsInfo> wbsInfos = wbsResult.getBody();
        List<ActivityConstField> workInfos = workResult.getBody();
        if(!CollectionUtils.isEmpty(wbsInfos) && !CollectionUtils.isEmpty(workInfos)) {
            for (WbsInfo wbsInfo : wbsInfos) {
                QqchMainPlanItem qqchMainPlanItem = new QqchMainPlanItem();
                Long id = IdWorker.createId();
                qqchMainPlanItem.setId(id);
//                qqchMainPlanItem.setPid();
//                qqchMainPlanItem.setMainPlanId(mainPlanId);
                qqchMainPlanItem.setVersion(version);
                qqchMainPlanItem.setItemCode(wbsInfo.getCode());
                qqchMainPlanItem.setItemName(wbsInfo.getName());
                qqchMainPlanItem.setPlannedDuration(wbsInfo.getSummaryPlannedDuration());
                qqchMainPlanItem.setTotalFloat(wbsInfo.getSummaryTotalFloat());
                qqchMainPlanItem.setSort(StatisticsUtils.isNumeric2(wbsInfo.getCode()) ? Integer.valueOf(wbsInfo.getCode()) : 0);
//                qqchMainPlanItem.setExecuterId();
//                qqchMainPlanItem.setExecuter();
                qqchMainPlanItem.setStartDate(wbsInfo.getStartDate());
                qqchMainPlanItem.setFinishDate(wbsInfo.getFinishDate());
//                qqchMainPlanItem.setIsCritical();
                qqchMainPlanItem.setWbsCode(wbsInfo.getCode());
                qqchMainPlanItem.setWbsObjectId(wbsInfo.getId());
                qqchMainPlanItem.setWbsParentObjectId(wbsInfo.getParentObjectId());
//                qqchMainPlanItem.setWbsPcode(wbsInfo.getParentObjectId());
                qqchMainPlanItem.setWbsName(wbsInfo.getName());
//                qqchMainPlanItem.setProjectId();
//                qqchMainPlanItem.setProjectCode();
//                qqchMainPlanItem.setProjectName();
//                qqchMainPlanItem.setFreeFloat();
                qqchMainPlanItem.setRemainingDuration(wbsInfo.getSummaryRemainingDuration());
                qqchMainPlanItem.setActualStartDate(wbsInfo.getSummaryActualStartDate());
                qqchMainPlanItem.setActualFinishDate(wbsInfo.getSummaryActualFinishDate());
                qqchMainPlanItem.setUnit(wbsInfo.getUnit());
                qqchMainPlanItem.setQuantity(wbsInfo.getQuantity());
                qqchMainPlanItem.setSchedulePercentComplete(wbsInfo.getSummarySchedulePercentComplete());
                qqchMainPlanItem.setExpectedFinishDate(wbsInfo.getSummaryProgressFinishDate());
                qqchMainPlanItem.setFinishDateVariance(wbsInfo.getSummaryFinishDateVariance());
//                qqchMainPlanItem.setLagReason();
//                qqchMainPlanItem.setCorrectionTarget();
//                qqchMainPlanItem.setConcreteMeasure();
//                qqchMainPlanItem.setCorrectionTarget();
//                qqchMainPlanItem.setPredecessorActivityCode();
//                qqchMainPlanItem.setPredecessorActivityName();
//                qqchMainPlanItem.setAncestors();
                qqchMainPlanItem.setItemType("wbs");
                qqchMainPlanItem.setLeaf("0");
                qqchMainPlanItem.setBaselineStartDate(wbsInfo.getSummaryBaselineStartDate());
                qqchMainPlanItem.setBaselineFinishDate(wbsInfo.getSummaryBaselineFinishDate());
//                qqchMainPlanItem.setTaskType();
                qqchMainPlanItem.setRemainingEarlyStartDate(wbsInfo.getSummaryRemainingStartDate());
                qqchMainPlanItem.setRemainingEarlyFinishDate(wbsInfo.getSummaryRemainingFinishDate());
                returnList.add(qqchMainPlanItem);
            }
            for (ActivityConstField activityInfo : workInfos) {
                QqchMainPlanItem qqchMainPlanItem = new QqchMainPlanItem();
                String p6Id = activityInfo.getId();
                Long id = IdWorker.createId();
                qqchMainPlanItem.setId(id);
                if(!CollectionUtils.isEmpty(relInfos)) {
                    for (QqchMainPlanItemPre qqchMainPlanItemPre : relInfos) {
//                        qqchMainPlanItemPre.setMainPlanId(mainPlanId);
                        if(p6Id.equals(qqchMainPlanItemPre.getItemCode())){
                            qqchMainPlanItemPre.setItemId(id);
                        }
                        if(p6Id.equals(qqchMainPlanItemPre.getPredecessorItemCode())) {
                            qqchMainPlanItemPre.setPredecessorItemId(id);
                        }
                    }
                }
//                qqchMainPlanItem.setPid();
//                qqchMainPlanItem.setMainPlanId(mainPlanId);
                qqchMainPlanItem.setVersion(version);
                qqchMainPlanItem.setItemCode(p6Id);
                qqchMainPlanItem.setItemName(activityInfo.getName());
                qqchMainPlanItem.setPlannedDuration(activityInfo.getPlannedDuration());
                qqchMainPlanItem.setTotalFloat(activityInfo.getTotalFloat());
//                qqchMainPlanItem.setExecuterId();
                qqchMainPlanItem.setExecuter(activityInfo.getExecuter());
                qqchMainPlanItem.setStartDate(activityInfo.getStartDate());
                qqchMainPlanItem.setFinishDate(activityInfo.getFinishDate());
                qqchMainPlanItem.setIsCritical(activityInfo.getIsCritical() != null && activityInfo.getIsCritical() ?"1":"0");
                qqchMainPlanItem.setIsLongestPath(activityInfo.getIsLongestPath() != null && activityInfo.getIsLongestPath() ?"1":"0");
                qqchMainPlanItem.setWbsCode(activityInfo.getWbsCode());
//                qqchMainPlanItem.setWbsObjectId();
                qqchMainPlanItem.setWbsParentObjectId(activityInfo.getWbsObjectId());
//                qqchMainPlanItem.setWbsPcode();
                qqchMainPlanItem.setWbsName(activityInfo.getWbsName());
//                qqchMainPlanItem.setProjectId();
//                qqchMainPlanItem.setProjectCode(activityInfo.getProjectId());
//                qqchMainPlanItem.setProjectName();
                qqchMainPlanItem.setFreeFloat(activityInfo.getFreeFloat());
                qqchMainPlanItem.setRemainingDuration(activityInfo.getRemainingDuration());
                qqchMainPlanItem.setActualStartDate(activityInfo.getActualStartDate());
                qqchMainPlanItem.setActualFinishDate(activityInfo.getActualFinishDate());
                qqchMainPlanItem.setUnit(activityInfo.getUnit());
                qqchMainPlanItem.setQuantity(activityInfo.getQuantity());
                qqchMainPlanItem.setSchedulePercentComplete(activityInfo.getSchedulePercentComplete());
                qqchMainPlanItem.setExpectedFinishDate(activityInfo.getExpectedFinishDate());
                qqchMainPlanItem.setFinishDateVariance(activityInfo.getFinishDateVariance());
                qqchMainPlanItem.setLagReason(activityInfo.getLagReason());
                qqchMainPlanItem.setCorrectionTarget(activityInfo.getCorrectionTarget());
                qqchMainPlanItem.setConcreteMeasure(activityInfo.getConcreteMeasure());
                qqchMainPlanItem.setCorrectionCompDate(activityInfo.getCorrectionCompDate());
//                qqchMainPlanItem.setPredecessorActivityCode();
//                qqchMainPlanItem.setPredecessorActivityName();
//                qqchMainPlanItem.setAncestors();
                qqchMainPlanItem.setItemType("item");
                qqchMainPlanItem.setBaselineStartDate(activityInfo.getBaselineStartDate());
                qqchMainPlanItem.setBaselineFinishDate(activityInfo.getBaselineFinishDate());
                qqchMainPlanItem.setLeaf("1");
//                qqchMainPlanItem.setUpdateTime(activityInfo.getLastUpdateDate());
                qqchMainPlanItem.setRemainingEarlyStartDate(activityInfo.getRemainingEarlyStartDate());
                qqchMainPlanItem.setRemainingEarlyFinishDate(activityInfo.getRemainingEarlyFinishDate());
                qqchMainPlanItem.setTaskType(activityInfo.getType());
                returnList.add(qqchMainPlanItem);
            }

            if(!CollectionUtils.isEmpty(returnList)) {
                for (QqchMainPlanItem qqchMainPlanItem : returnList) {
                    QqchMainPlanItem qqchMainPlanItem1 = returnList.stream().filter(
                            vo -> qqchMainPlanItem.getWbsParentObjectId().equals(vo.getWbsObjectId()))
                            .findFirst().orElse(null);
                    if(qqchMainPlanItem1 != null) {
                        qqchMainPlanItem.setPid(qqchMainPlanItem1.getId());
                    }
                }
                setWbsCode(returnList);
                setWbsDate(returnList);
//                System.out.println(returnList);
                qqchMainPlanItemService.deleteQqchMainPlanByVersion(version);
                qqchMainPlanItemService.insertQqchMainPlanItemList(returnList);
                qqchProdPlanService.putProdPlanData(version);
//                iQqchMainPlanItemPreService.insertQqchMainPlanItemPreList(relInfos);
            }
        }

        return returnList;
    }
    /**
     * 给wbs赋值开始结束时间
     * @param qqchMainPlanItems
     */
    private void setWbsDate(List<QqchMainPlanItem> qqchMainPlanItems){
        if(qqchMainPlanItems != null) {
            List<QqchMainPlanItem> workList = qqchMainPlanItems.stream().filter(vo -> QqchMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(workList)) {
//                iteration4Date(qqchMainPlanItems, collect);
                for (QqchMainPlanItem qqchMainPlanItem : qqchMainPlanItems) {
                    if(QqchMainPlanItem.ITEMTYPE_WBS.equals(qqchMainPlanItem.getItemType())) {
                        List<QqchMainPlanItem> workInWbs = workList.stream().filter(vo -> vo.getAncestors().contains(qqchMainPlanItem.getAncestors())).collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(workInWbs)) {
                            Date startDate = workInWbs.get(0).getStartDate();
                            Date finishDate = workInWbs.get(0).getFinishDate();
                            for (QqchMainPlanItem qqchMainPlanItem1 : workInWbs) {
                                if(startDate == null) {
                                    startDate = qqchMainPlanItem1.getStartDate();
                                } else {
                                    if(startDate.after(qqchMainPlanItem1.getStartDate())) {
                                        startDate = qqchMainPlanItem1.getStartDate();
                                    }
                                }
                                if(finishDate == null) {
                                    finishDate = qqchMainPlanItem1.getFinishDate();
                                } else {
                                    if(finishDate.before(qqchMainPlanItem1.getFinishDate())) {
                                        finishDate = qqchMainPlanItem1.getFinishDate();
                                    }
                                }
                            }
                            qqchMainPlanItem.setStartDate(startDate);
                            qqchMainPlanItem.setFinishDate(finishDate);
                        }
                    }
                }
            }
        }
    }

    /**
     * 给wbs拼接wbs编码
     * @param qqchMainPlanItems
     */
    private void setWbsCode(List<QqchMainPlanItem> qqchMainPlanItems) {

        if(CollectionUtils.isEmpty(qqchMainPlanItems)) {
            return;
        }

        List<QqchMainPlanItem> collect = qqchMainPlanItems.stream().filter(vo -> vo.getPid() == null).collect(Collectors.toList());

        teration4WbsCode(qqchMainPlanItems, collect);
    }

    /**
     * 迭代拼接wbs编码
     * @param allItem
     * @param thisItems
     */
    public void teration4WbsCode(List<QqchMainPlanItem> allItem, List<QqchMainPlanItem> thisItems) {

        if(CollectionUtils.isEmpty(allItem) || CollectionUtils.isEmpty(thisItems)) {
            return;
        }

        List<QqchMainPlanItem> childItem = new ArrayList<>();
        for (QqchMainPlanItem qqchMainPlanItem : thisItems) {
            if(qqchMainPlanItem.getPid() == null) qqchMainPlanItem.setAncestors(qqchMainPlanItem.getWbsObjectId());
            for (QqchMainPlanItem qqchMainPlanItem1 : allItem) {
                if(qqchMainPlanItem.getId() != null && qqchMainPlanItem.getId().equals(qqchMainPlanItem1.getPid())) {
                    if("wbs".equals(qqchMainPlanItem1.getItemType())) {
                        qqchMainPlanItem1.setItemCode(qqchMainPlanItem.getItemCode() + "-" + qqchMainPlanItem1.getItemCode());
                        qqchMainPlanItem1.setWbsCode(qqchMainPlanItem1.getItemCode());
                        qqchMainPlanItem1.setAncestors(qqchMainPlanItem.getAncestors() + "," + qqchMainPlanItem1.getWbsObjectId());
                    } else {
                        qqchMainPlanItem1.setWbsCode(qqchMainPlanItem.getItemCode());
                        qqchMainPlanItem1.setAncestors(qqchMainPlanItem.getAncestors() + "," + qqchMainPlanItem1.getItemCode());
                    }

                    childItem.add(qqchMainPlanItem1);
                }
            }
        }
        teration4WbsCode(allItem, childItem);
    }

    /**
     * 根据项目编码从p6获取项目数据接口
     * @param projectCode
     * @return
     */
    public ProjectInfo getProjectInfo(String projectCode) {
        String urlProj = p6IpPort + pre + "/projectInfo";
        ParameterizedTypeReference<List<ProjectInfo>> responseType4Proj = new ParameterizedTypeReference<List<ProjectInfo>>() {};
        HttpEntity<?> entity=new HttpEntity(new HttpHeaders());

        Map<String, Object> params = new HashMap<>();
        // 获取p6 项目数据
        ResponseEntity<List<ProjectInfo>> projResult = restTemplate.exchange(urlProj, HttpMethod.GET, entity, responseType4Proj, params);
        List<ProjectInfo> body = projResult.getBody();
        if(CollectionUtils.isEmpty(body)) {
            return  null;
        }
        List<ProjectInfo> collect = body.stream().filter(vo -> StringUtils.isNotEmpty(vo.getProjectId())).collect(Collectors.toList());
        ProjectInfo projectInfo = body.stream().filter(vo -> projectCode.equalsIgnoreCase(vo.getProjectId())).findFirst().orElse(null);
        if(projectInfo == null) {
            projectInfo = body.stream().filter(vo -> projectCode.equalsIgnoreCase(vo.getProjectCode())).findFirst().orElse(null);
        }
        return projectInfo;
    }


    /**
     * 获取转换后的逻辑关系数据
     * @return
     */
    public List<QqchMainPlanItemPre> getPre(String projectId) {

        List<QqchMainPlanItemPre> returnList = new ArrayList<>();

        String urlRel = p6IpPort + pre + "/relationInfo";

        HttpEntity<?> entity=new HttpEntity(new HttpHeaders());

        Map<String, Object> params = new HashMap<>();

        params.put("projectId", projectId);

        ParameterizedTypeReference<List<PredecessorRelationships>> responseType4Rel = new ParameterizedTypeReference<List<PredecessorRelationships>>() {};
        // 获取p6 逻辑关系数据
        ResponseEntity<List<PredecessorRelationships>> relResult = restTemplate.exchange(urlRel + "?projectId={projectId}", HttpMethod.GET, entity, responseType4Rel, params);

        List<PredecessorRelationships> relInfos = relResult.getBody();

        if(!CollectionUtils.isEmpty(relInfos)) {

            for (PredecessorRelationships predecessorRelationships : relInfos) {
                QqchMainPlanItemPre qqchMainPlanItemPre = new QqchMainPlanItemPre();
                qqchMainPlanItemPre.setId(IdWorker.createId());
                qqchMainPlanItemPre.setItemCode(predecessorRelationships.getSuccessorActivityId());
                qqchMainPlanItemPre.setPredecessorItemCode(predecessorRelationships.getPredecessorActivityId());
                qqchMainPlanItemPre.setPredecessorItemName(predecessorRelationships.getPredecessorActivityName());
                qqchMainPlanItemPre.setType(predecessorRelationships.getType());
                returnList.add(qqchMainPlanItemPre);
            }
        }

        return returnList;
    }

}
