package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.*;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglData4P6Service;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemPreService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class JdglData4P6ServiceImpl implements IJdglData4P6Service {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IJdglMainPlanService jdglMainPlanService;

    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;

    @Autowired
    private SystemServiceApi systemServiceApi;
    
    @Autowired
    private IJdglMainPlanItemPreService iJdglMainPlanItemPreService;

    @Value("${p6.ip_port}")
    private String p6IpPort;

    private String pre = "/p6";

//    private static Map<String,String> typeMap = new HashMap<>();
//    static {
//        typeMap.put("Start to Start", "0");
//        typeMap.put("Start to Finish", "1");
//        typeMap.put("Finish to Start", "2");
//        typeMap.put("Finish to Finish", "3");
//    }

    @Override
    public List<JdglMainPlanItem> initJdglData4P6() {
        return initJdglData4P6ByOne("Nepal-6-3");
    }

    @Override
    public List<JdglMainPlanItem> initJdglData4P6ByOne(String tenantKey) {

        List<JdglMainPlanItem> returnList = new ArrayList<>();

        ProjectInfo projectInfo = getProjectInfo(tenantKey);
        if(projectInfo == null)  return returnList;

        String urlwbs= p6IpPort + pre + "/wbsInfo";
        String urlwork= p6IpPort + pre + "/activityInfo";

        HttpEntity<?> entity=new HttpEntity(new HttpHeaders());

        Map<String, Object> params = new HashMap<>();
        String projectId = projectInfo.getProjectCode();
        params.put("projectId", StringUtils.isEmpty(projectId) ? tenantKey : projectId);
        ParameterizedTypeReference<List<WbsInfo>> responseType4Wbs = new ParameterizedTypeReference<List<WbsInfo>>() {};
        ParameterizedTypeReference<List<ActivityConstField>> responseType4Work = new ParameterizedTypeReference<List<ActivityConstField>>() {};

        // 获取p6 wbs数据
        ResponseEntity<List<WbsInfo>> wbsResult = restTemplate.exchange(urlwbs + "?projectId={projectId}", HttpMethod.GET, entity, responseType4Wbs, params);
        // 获取p6 作业数据
        ResponseEntity<List<ActivityConstField>> workResult = restTemplate.exchange(urlwork + "?projectId={projectId}", HttpMethod.GET, entity, responseType4Work, params);
        // 获取转换后的p6逻辑关系数据
        List<JdglMainPlanItemPre> relInfos = new ArrayList<>();//getPre(projectId);

        // 获取当前启用的总体计划主表数据
        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlan();
        Long mainPlanId = IdWorker.createId();
        Date nowDate = DateUtils.getNowDate();
        Calendar cl = Calendar.getInstance();
        cl.setTime(nowDate);
        String datePro = "Y" + cl.get(Calendar.YEAR) + "M" +  (cl.get(Calendar.MONTH)+1) + "W" + (cl.get(Calendar.WEEK_OF_MONTH));
        if(usingJdglMainPlan != null) {
            usingJdglMainPlan.setIsUse("0");
            jdglMainPlanService.updateJdglMainPlan(usingJdglMainPlan);
            usingJdglMainPlan.setId(mainPlanId);
            String versionPro = usingJdglMainPlan.getVersionPro();
            if(datePro.equals(versionPro)) {
                Integer num = usingJdglMainPlan.getNum() + 1;
                usingJdglMainPlan.setVersion(versionPro + "." + num);
                usingJdglMainPlan.setNum(num);
            } else {
                usingJdglMainPlan.setVersion(datePro + "." + 1);
                usingJdglMainPlan.setVersionPro(datePro);
                usingJdglMainPlan.setNum(1);
            }
            usingJdglMainPlan.setIsUse("1");
        } else {
            usingJdglMainPlan = new JdglMainPlan();
            usingJdglMainPlan.setId(mainPlanId);
            usingJdglMainPlan.setIsUse("1");
            usingJdglMainPlan.setVersion(datePro + "." + 1);
            usingJdglMainPlan.setVersionPro(datePro);
            usingJdglMainPlan.setNum(1);
        }
        jdglMainPlanService.insertJdglMainPlan(usingJdglMainPlan);

        List<WbsInfo> wbsInfos = wbsResult.getBody();
        List<ActivityConstField> workInfos = workResult.getBody();
        if(!CollectionUtils.isEmpty(wbsInfos) && !CollectionUtils.isEmpty(workInfos)) {
            for (WbsInfo wbsInfo : wbsInfos) {
                JdglMainPlanItem jdglMainPlanItem = new JdglMainPlanItem();
                Long id = IdWorker.createId();
                jdglMainPlanItem.setId(id);
//                jdglMainPlanItem.setPid();
                jdglMainPlanItem.setMainPlanId(mainPlanId);
                jdglMainPlanItem.setItemCode(wbsInfo.getCode());
                jdglMainPlanItem.setItemName(wbsInfo.getName());
                jdglMainPlanItem.setPlannedDuration(wbsInfo.getSummaryPlannedDuration());
                jdglMainPlanItem.setTotalFloat(wbsInfo.getSummaryTotalFloat());
                jdglMainPlanItem.setSort(StatisticsUtils.isNumeric2(wbsInfo.getCode()) ? Integer.valueOf(wbsInfo.getCode()) : 0);
//                jdglMainPlanItem.setExecuterId();
//                jdglMainPlanItem.setExecuter();
                jdglMainPlanItem.setStartDate(wbsInfo.getStartDate());
                jdglMainPlanItem.setFinishDate(wbsInfo.getFinishDate());
//                jdglMainPlanItem.setIsCritical();
                jdglMainPlanItem.setWbsCode(wbsInfo.getCode());
                jdglMainPlanItem.setWbsObjectId(wbsInfo.getId());
                jdglMainPlanItem.setWbsParentObjectId(wbsInfo.getParentObjectId());
//                jdglMainPlanItem.setWbsPcode(wbsInfo.getParentObjectId());
                jdglMainPlanItem.setWbsName(wbsInfo.getName());
//                jdglMainPlanItem.setProjectId();
//                jdglMainPlanItem.setProjectCode();
//                jdglMainPlanItem.setProjectName();
//                jdglMainPlanItem.setFreeFloat();
                jdglMainPlanItem.setRemainingDuration(wbsInfo.getSummaryRemainingDuration());
                jdglMainPlanItem.setActualStartDate(wbsInfo.getSummaryActualStartDate());
                jdglMainPlanItem.setActualFinishDate(wbsInfo.getSummaryActualFinishDate());
//                jdglMainPlanItem.setUnit();
//                jdglMainPlanItem.setQuantity();
                jdglMainPlanItem.setSchedulePercentComplete(wbsInfo.getSummarySchedulePercentComplete());
                jdglMainPlanItem.setExpectedFinishDate(wbsInfo.getSummaryProgressFinishDate());
                jdglMainPlanItem.setFinishDateVariance(wbsInfo.getSummaryFinishDateVariance());
//                jdglMainPlanItem.setLagReason();
//                jdglMainPlanItem.setCorrectionTarget();
//                jdglMainPlanItem.setConcreteMeasure();
//                jdglMainPlanItem.setCorrectionTarget();
//                jdglMainPlanItem.setPredecessorActivityCode();
//                jdglMainPlanItem.setPredecessorActivityName();
//                jdglMainPlanItem.setAncestors();
                jdglMainPlanItem.setItemType("wbs");
                jdglMainPlanItem.setLeaf("0");
                jdglMainPlanItem.setBaselineStartDate(wbsInfo.getSummaryBaselineStartDate());
                jdglMainPlanItem.setBaselineFinishDate(wbsInfo.getSummaryBaselineFinishDate());
//                jdglMainPlanItem.setTaskType();
                jdglMainPlanItem.setRemainingEarlyStartDate(wbsInfo.getSummaryRemainingStartDate());
                jdglMainPlanItem.setRemainingEarlyFinishDate(wbsInfo.getSummaryRemainingFinishDate());
                returnList.add(jdglMainPlanItem);
            }
            for (ActivityConstField activityInfo : workInfos) {
                JdglMainPlanItem jdglMainPlanItem = new JdglMainPlanItem();
                String p6Id = activityInfo.getId();
                Long id = IdWorker.createId();
                jdglMainPlanItem.setId(id);
                if(!CollectionUtils.isEmpty(relInfos)) {
                    for (JdglMainPlanItemPre jdglMainPlanItemPre : relInfos) {
                        jdglMainPlanItemPre.setMainPlanId(mainPlanId);
                        if(p6Id.equals(jdglMainPlanItemPre.getItemCode())){
                            jdglMainPlanItemPre.setItemId(id);
                        }
                        if(p6Id.equals(jdglMainPlanItemPre.getPredecessorItemCode())) {
                            jdglMainPlanItemPre.setPredecessorItemId(id);
                        }
                    }
                }
//                jdglMainPlanItem.setPid();
                jdglMainPlanItem.setMainPlanId(mainPlanId);
                jdglMainPlanItem.setItemCode(p6Id);
                jdglMainPlanItem.setItemName(activityInfo.getName());
                jdglMainPlanItem.setPlannedDuration(activityInfo.getPlannedDuration());
                jdglMainPlanItem.setTotalFloat(activityInfo.getTotalFloat());
//                jdglMainPlanItem.setExecuterId();
                jdglMainPlanItem.setExecuter(activityInfo.getExecuter());
                jdglMainPlanItem.setStartDate(activityInfo.getStartDate());
                jdglMainPlanItem.setFinishDate(activityInfo.getFinishDate());
                jdglMainPlanItem.setIsCritical(activityInfo.getCritical()?"1":"0");
                jdglMainPlanItem.setWbsCode(activityInfo.getWbsCode());
//                jdglMainPlanItem.setWbsObjectId();
                jdglMainPlanItem.setWbsParentObjectId(activityInfo.getWbsObjectId());
//                jdglMainPlanItem.setWbsPcode();
                jdglMainPlanItem.setWbsName(activityInfo.getWbsName());
//                jdglMainPlanItem.setProjectId();
//                jdglMainPlanItem.setProjectCode(activityInfo.getProjectId());
//                jdglMainPlanItem.setProjectName();
                jdglMainPlanItem.setFreeFloat(activityInfo.getFreeFloat());
                jdglMainPlanItem.setRemainingDuration(activityInfo.getRemainingDuration());
                jdglMainPlanItem.setActualStartDate(activityInfo.getActualStartDate());
                jdglMainPlanItem.setActualFinishDate(activityInfo.getActualFinishDate());
                jdglMainPlanItem.setUnit(activityInfo.getUnit());
                jdglMainPlanItem.setQuantity(activityInfo.getQuantity());
                jdglMainPlanItem.setSchedulePercentComplete(activityInfo.getSchedulePercentComplete());
                jdglMainPlanItem.setExpectedFinishDate(activityInfo.getExpectedFinishDate());
                jdglMainPlanItem.setFinishDateVariance(activityInfo.getFinishDateVariance());
                jdglMainPlanItem.setLagReason(activityInfo.getLagReason());
                jdglMainPlanItem.setCorrectionTarget(activityInfo.getCorrectionTarget());
                jdglMainPlanItem.setConcreteMeasure(activityInfo.getConcreteMeasure());
                jdglMainPlanItem.setCorrectionCompDate(activityInfo.getCorrectionCompDate());
//                jdglMainPlanItem.setPredecessorActivityCode();
//                jdglMainPlanItem.setPredecessorActivityName();
//                jdglMainPlanItem.setAncestors();
                jdglMainPlanItem.setItemType("item");
                jdglMainPlanItem.setBaselineStartDate(activityInfo.getBaselineStartDate());
                jdglMainPlanItem.setBaselineFinishDate(activityInfo.getBaselineFinishDate());
                jdglMainPlanItem.setLeaf("1");
//                jdglMainPlanItem.setUpdateTime(activityInfo.getLastUpdateDate());
                jdglMainPlanItem.setRemainingEarlyStartDate(activityInfo.getRemainingEarlyStartDate());
                jdglMainPlanItem.setRemainingEarlyFinishDate(activityInfo.getRemainingEarlyFinishDate());
                jdglMainPlanItem.setTaskType(activityInfo.getType());
                returnList.add(jdglMainPlanItem);
            }

            if(!CollectionUtils.isEmpty(returnList)) {
                for (JdglMainPlanItem jdglMainPlanItem : returnList) {
                    JdglMainPlanItem jdglMainPlanItem1 = returnList.stream().filter(
                            vo -> jdglMainPlanItem.getWbsParentObjectId().equals(vo.getWbsObjectId()))
                            .findFirst().orElse(null);
                    if(jdglMainPlanItem1 != null) {
                        jdglMainPlanItem.setPid(jdglMainPlanItem1.getId());
                    }
                }
                setWbsCode(returnList);
                setWbsDate(returnList);
//                System.out.println(returnList);
                jdglMainPlanItemService.insertJdglMainPlanItemList(returnList);
                iJdglMainPlanItemPreService.insertJdglMainPlanItemPreList(relInfos);
            }
        }

        return returnList;

    }

    @Override
    public List<JdglMainPlanItem> initAllJdglData4P6() {

        // 获取租户集合
        List<String> tenantKeyList = new ArrayList<>();
        List<SysTenant> sysTenants = systemServiceApi.tenantList();

        if(!CollectionUtils.isEmpty(sysTenants)) {
            sysTenants.forEach(vo -> tenantKeyList.add(vo.getTenantKey()));
        }

        if(!CollectionUtils.isEmpty(tenantKeyList)) {

            // 创建固定数量的线程池
            int threadPoolSize = 10;
            ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);


            for (String tenantKey : tenantKeyList) {
                executorService.execute(() -> {
                    //切换租户
                    String oldDataSource = DynamicDataSourceContextHolder.peek();
                    DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
                    try {
                        initJdglData4P6ByOne(tenantKey);
                    }catch (Exception e){
                        e.printStackTrace();
                        throw new CustomBusinessException(e.getMessage());
                    }finally {
                        DynamicDataSourceContextHolder.poll();
                        DynamicDataSourceContextHolder.push(oldDataSource);
                    }
                });
            }

            executorService.shutdown();

            // 等待线程池执行结束
            while (!executorService.isTerminated()) {
                Thread.yield();
            }

        }

        return null;
    }

    @Override
    public List<JdglMainPlanItem> initOneJdglData4P6ByTenent(String projectId) {

        if(StringUtils.isEmpty(projectId)) {
            throw new RuntimeException("projectId参数异常");
        }

        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(projectId));
        try {
            initJdglData4P6ByOne(projectId);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomBusinessException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }

        return null;
    }

    /**
     * 给wbs赋值开始结束时间
     * @param jdglMainPlanItems
     */
    private void setWbsDate(List<JdglMainPlanItem> jdglMainPlanItems){
        if(jdglMainPlanItems != null) {
            List<JdglMainPlanItem> workList = jdglMainPlanItems.stream().filter(vo -> JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(workList)) {
//                iteration4Date(jdglMainPlanItems, collect);
                for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItems) {
                    if(JdglMainPlanItem.ITEMTYPE_WBS.equals(jdglMainPlanItem.getItemType())) {
                        List<JdglMainPlanItem> workInWbs = workList.stream().filter(vo -> vo.getAncestors().contains(jdglMainPlanItem.getAncestors())).collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(workInWbs)) {
                            Date startDate = workInWbs.get(0).getStartDate();
                            Date finishDate = workInWbs.get(0).getFinishDate();
                            for (JdglMainPlanItem jdglMainPlanItem1 : workInWbs) {
                                if(startDate == null) {
                                    startDate = jdglMainPlanItem1.getStartDate();
                                } else {
                                    if(startDate.after(jdglMainPlanItem1.getStartDate())) {
                                        startDate = jdglMainPlanItem1.getStartDate();
                                    }
                                }
                                if(finishDate == null) {
                                    finishDate = jdglMainPlanItem1.getFinishDate();
                                } else {
                                    if(finishDate.before(jdglMainPlanItem1.getFinishDate())) {
                                        finishDate = jdglMainPlanItem1.getFinishDate();
                                    }
                                }
                            }
                            jdglMainPlanItem.setStartDate(startDate);
                            jdglMainPlanItem.setFinishDate(finishDate);
                        }
                    }
                }
            }
        }
    }

    /**
     * 迭代开始结束时间赋值(废弃，数据会错位)
     * @param AllItems
     * @param thisItems
     */
    private void iteration4Date(List<JdglMainPlanItem> AllItems, List<JdglMainPlanItem> thisItems) {
        if(CollectionUtils.isEmpty(thisItems) || CollectionUtils.isEmpty(AllItems)) {
            return;
        }
        Set<Long> parentIds = new HashSet<>();
        List<JdglMainPlanItem> parentItems = new ArrayList<>();
        for (JdglMainPlanItem jdglMainPlanItem : thisItems) {
            if(jdglMainPlanItem.getPid() != null) parentIds.add(jdglMainPlanItem.getPid());
        }
        if(CollectionUtils.isEmpty(parentIds)) {
            return;
        }
        for (Long parentId : parentIds) {
            for (JdglMainPlanItem jdglMainPlanItem : AllItems) {
                if(parentId.equals(jdglMainPlanItem.getId())) {
                    if("8".equals(jdglMainPlanItem.getItemCode())) {
                        System.out.println("11111");
                    }
                    List<JdglMainPlanItem> collect = thisItems.stream().filter(vo -> parentId.equals(vo.getPid())).collect(Collectors.toList());
                    if(!CollectionUtils.isEmpty(collect)) {
                        Date startDate = collect.get(0).getStartDate();
                        Date finishDate = collect.get(0).getFinishDate();
                        for (JdglMainPlanItem jdglMainPlanItem1 : collect) {
                            if(startDate == null) {
                                startDate = jdglMainPlanItem1.getStartDate();
                            } else {
                                if(startDate.after(jdglMainPlanItem1.getStartDate())) {
                                    startDate = jdglMainPlanItem1.getStartDate();
                                }
                            }
                            if(finishDate == null) {
                                finishDate = jdglMainPlanItem1.getFinishDate();
                            } else {
                                if(finishDate.before(jdglMainPlanItem1.getFinishDate())) {
                                    finishDate = jdglMainPlanItem1.getFinishDate();
                                }
                            }
                        }
                        jdglMainPlanItem.setStartDate(startDate);
                        jdglMainPlanItem.setFinishDate(finishDate);
                    }
                    parentItems.add(jdglMainPlanItem);
                }
            }
        }

        iteration4Date(AllItems,parentItems);
    }

    /**
     * 给wbs拼接wbs编码
     * @param jdglMainPlanItems
     */
    private void setWbsCode(List<JdglMainPlanItem> jdglMainPlanItems) {

        if(CollectionUtils.isEmpty(jdglMainPlanItems)) {
            return;
        }

        List<JdglMainPlanItem> collect = jdglMainPlanItems.stream().filter(vo -> vo.getPid() == null).collect(Collectors.toList());

        teration4WbsCode(jdglMainPlanItems, collect);
    }

    /**
     * 迭代拼接wbs编码
     * @param allItem
     * @param thisItems
     */
    public void teration4WbsCode(List<JdglMainPlanItem> allItem, List<JdglMainPlanItem> thisItems) {

        if(CollectionUtils.isEmpty(allItem) || CollectionUtils.isEmpty(thisItems)) {
            return;
        }

        List<JdglMainPlanItem> childItem = new ArrayList<>();
        for (JdglMainPlanItem jdglMainPlanItem : thisItems) {
            if(jdglMainPlanItem.getPid() == null) jdglMainPlanItem.setAncestors(jdglMainPlanItem.getWbsObjectId());
            for (JdglMainPlanItem jdglMainPlanItem1 : allItem) {
                if(jdglMainPlanItem.getId() != null && jdglMainPlanItem.getId().equals(jdglMainPlanItem1.getPid())) {
                    if("wbs".equals(jdglMainPlanItem1.getItemType())) {
                        jdglMainPlanItem1.setItemCode(jdglMainPlanItem.getItemCode() + "-" + jdglMainPlanItem1.getItemCode());
                        jdglMainPlanItem1.setWbsCode(jdglMainPlanItem1.getItemCode());
                        jdglMainPlanItem1.setAncestors(jdglMainPlanItem.getAncestors() + "," + jdglMainPlanItem1.getWbsObjectId());
                    } else {
                        jdglMainPlanItem1.setWbsCode(jdglMainPlanItem.getItemCode());
                        jdglMainPlanItem1.setAncestors(jdglMainPlanItem.getAncestors() + "," + jdglMainPlanItem1.getItemCode());
                    }

                    childItem.add(jdglMainPlanItem1);
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
        ProjectInfo projectInfo = body.stream().filter(vo -> projectCode.equals(vo.getProjectId())).findFirst().orElse(null);
        if(projectInfo == null) {
            projectInfo = body.stream().filter(vo -> projectCode.equals(vo.getProjectCode())).findFirst().orElse(null);
        }
        return projectInfo;
    }


    /**
     * 获取转换后的逻辑关系数据
     * @return
     */
    public List<JdglMainPlanItemPre> getPre(String projectId) {

        List<JdglMainPlanItemPre> returnList = new ArrayList<>();

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
                JdglMainPlanItemPre jdglMainPlanItemPre = new JdglMainPlanItemPre();
                jdglMainPlanItemPre.setId(IdWorker.createId());
                jdglMainPlanItemPre.setItemCode(predecessorRelationships.getSuccessorActivityId());
                jdglMainPlanItemPre.setPredecessorItemCode(predecessorRelationships.getPredecessorActivityId());
                jdglMainPlanItemPre.setPredecessorItemName(predecessorRelationships.getPredecessorActivityName());
                jdglMainPlanItemPre.setType(predecessorRelationships.getType());
                returnList.add(jdglMainPlanItemPre);
            }
        }

        return returnList;
    }

}
