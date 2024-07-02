package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.*;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.vo.ActivityInfoVoBean;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglData4P6Service;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemPreService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItemPre;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemPreService;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchMainPlanItemService qqchMainPlanItemService;
    @Autowired
    private IQqchMainPlanItemPreService qqchMainPlanItemPreService;

    @Value("${p6.ip_port}")
    private String p6IpPort;

    private String pre = "";

    private String alex = "";

    //从1.2.1获取数据，生成基线计划
    @Transactional
    public void syncData(){
        log.info("从1.2.1获取数据，生成基线计划开始.....");
        /*清空表*/
        jdglMainPlanService.deleteJdglMainPlan(new JdglMainPlan());
        jdglMainPlanItemService.deleteJdglMainPlanItem(new JdglMainPlanItem());
        iJdglMainPlanItemPreService.deleteJdglMainPlanItemPre(new JdglMainPlanItemPre());
        log.info("从1.2.1获取数据，生成基线计划.....1");
        /*保存 总体进度计划主表*/
        Long mainPlanId = IdWorker.createId();
        Calendar cl = Calendar.getInstance();
        cl.setTime(DateUtils.getNowDate());
        String datePro = "Y" + cl.get(Calendar.YEAR) + "M" + (cl.get(Calendar.MONTH) + 1) + "W" + (cl.get(Calendar.WEEK_OF_MONTH));
        JdglMainPlan usingJdglMainPlan = new JdglMainPlan();
        usingJdglMainPlan.setId(mainPlanId);
        usingJdglMainPlan.setIsUse("1");
        usingJdglMainPlan.setVersion(datePro + "." + 1);
        usingJdglMainPlan.setVersionPro(datePro);
        usingJdglMainPlan.setNum(1);
        usingJdglMainPlan.setPtVar2("1");
        usingJdglMainPlan.setPtVar3(DateUtil.format(new Date(), "yyyy年MM月dd日 HH") + ":00");
        jdglMainPlanService.insertJdglMainPlan(usingJdglMainPlan);
        log.info("从1.2.1获取数据，生成基线计划.....2");
        /*保存总体计划详细数据*/
        //查询1.2.1基线计划
        QqchMainPlanItem qqchMainPlanItem = new QqchMainPlanItem();
        List<QqchMainPlanItem> itemList = qqchMainPlanItemService.getItemList(qqchMainPlanItem);
        if (CollUtil.isNotEmpty(itemList)) {
            log.info("从1.2.1获取数据，生成基线计划.....3");
            CopyOptions copyOptions = new CopyOptions();
            copyOptions.ignoreNullValue();
            List<JdglMainPlanItem> jdglMainPlanItems = BeanUtil.copyToList(itemList, JdglMainPlanItem.class);
            jdglMainPlanItems.forEach(p -> {
                p.setMainPlanId(mainPlanId);
                p.setId(IdWorker.createId());
                p.setCreateTime(DateUtils.getNowDate());
            });
            jdglMainPlanItemService.insertJdglMainPlanItemList(jdglMainPlanItems);
            log.info("从1.2.1获取数据，生成基线计划.....4");
        }
        List<QqchMainPlanItemPre> qqchMainPlanItemPreList = qqchMainPlanItemPreService.getQqchMainPlanItemPreList(new QqchMainPlanItemPre());
        if (CollUtil.isNotEmpty(qqchMainPlanItemPreList)) {
            log.info("从1.2.1获取数据，生成基线计划.....5");
            CopyOptions copyOptions = new CopyOptions();
            copyOptions.ignoreNullValue();
            List<JdglMainPlanItemPre> jdglMainPlanItemPres = BeanUtil.copyToList(itemList, JdglMainPlanItemPre.class);
            jdglMainPlanItemPres.forEach(p -> {
                p.setMainPlanId(mainPlanId);
                p.setId(IdWorker.createId());
                p.setCreateTime(DateUtils.getNowDate());
            });
            iJdglMainPlanItemPreService.insertJdglMainPlanItemPreList(jdglMainPlanItemPres);
            log.info("从1.2.1获取数据，生成基线计划.....6");
        }
        log.info("从1.2.1获取数据，生成基线计划结束.....");
    }

    @Override
    public List<JdglMainPlanItem> initJdglData4P6ByThis() {
        String tenantKey = SecurityUtils.getTenantKey();
        if(StringUtils.isEmpty(tenantKey)) {
            return new ArrayList<>();
        }
        this.alex = "RealTime";
        return initJdglData4P6ByOne(tenantKey);
    }

    //根据租户拉取P6数据
    @Override
    public List<JdglMainPlanItem> initJdglData4P6ByOne(String tenantKey) {

        List<JdglMainPlanItem> returnList = new ArrayList<>();

        //判断当前租户"前期策划评审"三阶段是否完成，如果未完成则不需拉取
//        String stage = qqchReviewService.getStage();
//        if (!PmConstant.END_STAGE.equals(stage)){
//            log.warn("根据租户拉取P6数据 - 前期策划评审三阶段未结束，不能获取数据");
//            return returnList;
//        }

        System.out.println("--获取p6项目数据--租户:" + tenantKey + "--开始:" +  DateUtils.getTime());
        ProjectInfo projectInfo = getProjectInfo(tenantKey);
        System.out.println("--获取p6项目数据--租户:" + tenantKey + "--结束:" +  DateUtils.getTime());
        if (projectInfo == null) {
            System.out.println("--未获取到p6项目数据--租户:" + tenantKey);
            return returnList;
        }

        String urlwbs = p6IpPort + pre + "/wbsInfo" + this.alex;
        String urlwork = p6IpPort + pre + "/activityInfo" + this.alex;

        HttpEntity<?> entity = new HttpEntity(new HttpHeaders());

        Map<String, Object> params = new HashMap<>();
        String projectId = projectInfo.getProjectCode();
        params.put("projectId", StringUtils.isEmpty(projectId) ? tenantKey : projectId);
        ParameterizedTypeReference<List<WbsInfo>> responseType4Wbs = new ParameterizedTypeReference<List<WbsInfo>>() {
        };
        ParameterizedTypeReference<List<ActivityConstField>> responseType4Work = new ParameterizedTypeReference<List<ActivityConstField>>() {
        };

        System.out.println("--获取p6 wbs数据--租户:" + tenantKey + "--开始:" +  DateUtils.getTime());
        // 获取p6 wbs数据
        ResponseEntity<List<WbsInfo>> wbsResult = restTemplate.exchange(urlwbs + "?projectId={projectId}", HttpMethod.GET, entity, responseType4Wbs, params);
        System.out.println("--获取p6 wbs数据--租户:" + tenantKey + "--结束:" +  DateUtils.getTime() + "-- 数量:" + (wbsResult.getBody() == null ? 0 : wbsResult.getBody().size()));
        System.out.println("--获取p6 作业数据--租户:" + tenantKey + "--开始:" +  DateUtils.getTime());
        // 获取p6 作业数据
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        requestFactory.setConnectTimeout(1000);
//        requestFactory.setReadTimeout(3600000);
//        RestTemplate restTemplateTimeout = new RestTemplate(requestFactory);
//        ResponseEntity<List<ActivityConstField>> workResult = restTemplateTimeout.exchange(urlwork + "?projectId={projectId}", HttpMethod.GET, entity, responseType4Work, params);
//        System.out.println("--获取p6 作业数据--租户:" + tenantKey + "--结束:" +  DateUtils.getTime() + "-- 数量:" + (workResult.getBody() == null ? 0 : workResult.getBody().size()));

//        if("PJ2016004237".equalsIgnoreCase(tenantKey)) {
//            try {
//                boolean isEnd = true;
//                // 每隔5秒钟发送一次心跳消息
//                while (isEnd) {
//                    Thread thread = Thread.currentThread();
//                    String name = thread.getName();
//                    Thread.State state = thread.getState();
//                    System.out.println("获取p6数据线程租户：" +tenantKey+"，当前线程名：" + name+"，当前线程状态：" + state);
//                    if(Thread.State.TERMINATED == state) isEnd = false;
//                    // 暂停5秒钟
//                    Thread.sleep(5000);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        HttpClient client = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(3600));
        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(client))
                .build();
        List<ActivityConstField> workInfos = webClient.get().uri(urlwork + "?projectId={projectId}", projectId).retrieve().bodyToFlux(ActivityConstField.class).collectList().block();
        System.out.println("--获取p6 作业数据--租户:" + tenantKey + "--结束:" +  DateUtils.getTime() + "-- 数量:" + (workInfos == null ? 0 : workInfos.size()));

        // 获取当前启用的总体计划主表数据
        JdglMainPlan usingJdglMainPlan = jdglMainPlanService.getUsingJdglMainPlan();
        Long mainPlanId = IdWorker.createId();
        Date nowDate = DateUtils.getNowDate();
        Calendar cl = Calendar.getInstance();
        cl.setTime(nowDate);
        String datePro = "Y" + cl.get(Calendar.YEAR) + "M" + (cl.get(Calendar.MONTH) + 1) + "W" + (cl.get(Calendar.WEEK_OF_MONTH));
        List<JdglMainPlanItem> jdglMainPlanItemList = null;
        if (usingJdglMainPlan != null) {
            jdglMainPlanItemList = usingJdglMainPlan.getJdglMainPlanItemList();
//            usingJdglMainPlan.setIsUse("0");
            jdglMainPlanService.deleteJdglMainPlan(usingJdglMainPlan);
            usingJdglMainPlan.setId(mainPlanId);
            String versionPro = usingJdglMainPlan.getVersionPro();
            if (datePro.equals(versionPro)) {
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

        if(CollectionUtils.isEmpty(wbsInfos)) {
            System.out.println("--未获取到p6wbs数据--租户:" + tenantKey);
        }
        if(CollectionUtils.isEmpty(workInfos)) {
            System.out.println("--未获取到p6作业数据--租户:" + tenantKey);
        }

//        List<ActivityConstField> workInfos = workResult.getBody();

        if (!CollectionUtils.isEmpty(wbsInfos)) {
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
                jdglMainPlanItem.setUnit(wbsInfo.getUnit());
                jdglMainPlanItem.setQuantity(wbsInfo.getQuantity());
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
            List<JdglMainPlanItem> workMainPlanItemList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(workInfos)) {
                for (ActivityConstField activityInfo : workInfos) {
                    JdglMainPlanItem jdglMainPlanItem = new JdglMainPlanItem();
                    String p6Id = activityInfo.getId();
                    Long id = IdWorker.createId();
                    jdglMainPlanItem.setId(id);
//                jdglMainPlanItem.setPid();
                    jdglMainPlanItem.setMainPlanId(mainPlanId);
                    jdglMainPlanItem.setItemCode(p6Id);
                    jdglMainPlanItem.setItemName(activityInfo.getName());
                    jdglMainPlanItem.setPlannedDuration(activityInfo.getPlannedDuration());
                    jdglMainPlanItem.setTotalFloat(activityInfo.getTotalFloat());
//                jdglMainPlanItem.setExecuterId();
                    jdglMainPlanItem.setExecuter(activityInfo.getExecuter());
                    if(CollectionUtils.isNotEmpty(jdglMainPlanItemList)) {
                        JdglMainPlanItem jdglMainPlanItem1 = jdglMainPlanItemList.stream().filter(vo -> p6Id.equals(vo.getItemCode())).findFirst().orElse(null);
                        if(jdglMainPlanItem1 != null) {
                            jdglMainPlanItem.setExecuterId(jdglMainPlanItem1.getExecuterId());
                            jdglMainPlanItem.setExecuter(jdglMainPlanItem1.getExecuter());
                        }
                    }
                    jdglMainPlanItem.setStartDate(activityInfo.getStartDate());
                    jdglMainPlanItem.setFinishDate(activityInfo.getFinishDate());
                    jdglMainPlanItem.setIsCritical(activityInfo.getIsCritical() != null && activityInfo.getIsCritical() ? "1" : "0");
                    jdglMainPlanItem.setIsLongestPath(activityInfo.getIsLongestPath() != null && activityInfo.getIsLongestPath() ? "1" : "0");
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
                    Integer finishDateVariance = activityInfo.getFinishDateVariance();
                    jdglMainPlanItem.setFinishDateVariance(finishDateVariance != null ? activityInfo.getFinishDateVariance() / 8 : 0);
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
                    workMainPlanItemList.add(jdglMainPlanItem);
                }
            }

            // 处理作业逻辑关系数据
            if (!CollectionUtils.isEmpty(workMainPlanItemList)) {
                PlanItemPreThread planItemPreThread = new PlanItemPreThread();
                planItemPreThread.setMainPlanId(mainPlanId);
                planItemPreThread.setProjectId(projectId);
                planItemPreThread.setWorkInfos(workMainPlanItemList);
                planItemPreThread.setTenantKey(tenantKey);
                Thread thread = new Thread(planItemPreThread);
                thread.start();
            }

            if (!CollectionUtils.isEmpty(returnList)) {
                for (JdglMainPlanItem jdglMainPlanItem : returnList) {
                    JdglMainPlanItem jdglMainPlanItem1 = returnList.stream().filter(
                            vo -> jdglMainPlanItem.getWbsParentObjectId().equals(vo.getWbsObjectId()))
                            .findFirst().orElse(null);
                    if (jdglMainPlanItem1 != null) {
                        jdglMainPlanItem.setPid(jdglMainPlanItem1.getId());
                    }
                }
                setWbsCode(returnList);
                setWbsDate(returnList);
//                System.out.println(returnList);
                jdglMainPlanItemService.insertJdglMainPlanItemList(returnList);

            }
        }

        return returnList;

    }

    private class PlanItemPreThread implements Runnable {
        public Long mainPlanId;
        public String projectId;
        public String tenantKey;
        public List<JdglMainPlanItem> workInfos;

        public Long getMainPlanId() {
            return mainPlanId;
        }

        public void setMainPlanId(Long mainPlanId) {
            this.mainPlanId = mainPlanId;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public List<JdglMainPlanItem> getWorkInfos() {
            return workInfos;
        }

        public void setWorkInfos(List<JdglMainPlanItem> workInfos) {
            this.workInfos = workInfos;
        }

        public String getTenantKey() {
            return tenantKey;
        }

        public void setTenantKey(String tenantKey) {
            this.tenantKey = tenantKey;
        }

        @Override
        public void run() {
            addMainPlanItemPreList(this.mainPlanId, this.projectId, this.tenantKey, this.workInfos);
        }

        public void addMainPlanItemPreList(Long mainPlanId, String projectId, String tenantKey, List<JdglMainPlanItem> workInfos) {

            if (StringUtils.isEmpty(tenantKey)) {
                throw new RuntimeException("tenantKey参数异常");
            }

            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
            try {
                System.out.println("--获取p6 作业逻辑关系数据--租户:" + tenantKey + "--开始:" +  DateUtils.getTime());
                // 获取转换后的p6逻辑关系数据
                List<JdglMainPlanItemPre> relInfos = getPre(projectId);
                System.out.println("--获取p6 作业逻辑关系数据--租户:" + tenantKey + "--结束:" +  DateUtils.getTime() +"-- 数量:" + (relInfos == null ? 0 : relInfos.size()));

                if(CollectionUtils.isEmpty(relInfos)) {
                    System.out.println("--未获取到p6作业逻辑数据--租户:" + tenantKey);
                }

                if (!CollectionUtils.isEmpty(relInfos) && !CollectionUtils.isEmpty(workInfos)) {
                    for (JdglMainPlanItem jdglMainPlanItem : workInfos) {
                        String p6Id = jdglMainPlanItem.getItemCode();
                        Long id = jdglMainPlanItem.getId();
                        for (JdglMainPlanItemPre jdglMainPlanItemPre : relInfos) {
                            jdglMainPlanItemPre.setMainPlanId(mainPlanId);
                            if (p6Id.equals(jdglMainPlanItemPre.getItemCode())) {
                                jdglMainPlanItemPre.setItemId(id);
                            }
                            if (p6Id.equals(jdglMainPlanItemPre.getPredecessorItemCode())) {
                                jdglMainPlanItemPre.setPredecessorItemId(id);
                            }
                        }
                    }
                }
                iJdglMainPlanItemPreService.insertJdglMainPlanItemPreList(relInfos);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomBusinessException(e.getMessage());
            } finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        }
    }


    @Override
    public List<JdglMainPlanItem> initAllJdglData4P6() {

        // 获取租户集合
        List<String> tenantKeyList = new ArrayList<>();
        List<SysTenant> sysTenants = systemServiceApi.tenantList();

        if (!CollectionUtils.isEmpty(sysTenants)) {
            sysTenants.forEach(vo -> tenantKeyList.add(vo.getTenantKey()));
        }

        if (!CollectionUtils.isEmpty(tenantKeyList)) {

            // 创建固定数量的线程池
            int threadPoolSize = 5;
            ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);


            for (String tenantKey : tenantKeyList) {
                executorService.execute(() -> {
                    //切换租户
                    String oldDataSource = DynamicDataSourceContextHolder.peek();
                    DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
                    log.info("切换---线程：{} --- 原数据源：{} --- 新数据源：{}", Thread.currentThread().getName(), oldDataSource, TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
                    try {
                        initJdglData4P6ByOne(tenantKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("租户"+ tenantKey + "获取p6异常:-----------------" + e.getMessage());
//                        throw new CustomBusinessException(e.getMessage());
                    } finally {
                        DynamicDataSourceContextHolder.poll();
                        DynamicDataSourceContextHolder.push(oldDataSource);
                        log.info("还原---线程：{} --- 原数据源：{} --- 新数据源：{}", Thread.currentThread().getName(), oldDataSource, TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
                    }
                });
            }

            executorService.shutdown();

            // 等待线程池执行结束
            while (!executorService.isTerminated()) {
                Thread.yield();
            }

        }
        log.info("结束");
        return null;
    }

    @Override
    public List<JdglMainPlanItem> initOneJdglData4P6ByTenent(String projectId) {

        if (StringUtils.isEmpty(projectId)) {
            throw new RuntimeException("projectId参数异常");
        }

        // 创建固定数量的线程池
        int threadPoolSize = 1;
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        executorService.execute(() -> {
            String oldDataSource = DynamicDataSourceContextHolder.peek();
            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(projectId));
            try {
                initJdglData4P6ByOne(projectId);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("租户" + projectId + "获取p6异常:-----------------" + e.getMessage());
//            throw new CustomBusinessException(e.getMessage());
            } finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        });

        executorService.shutdown();

        // 等待线程池执行结束
        while (!executorService.isTerminated()) {
            Thread.yield();
        }

        return null;
    }

//    @Override
//    public List<JdglMainPlanItem> initOneJdglData4P6ByTenent(String projectId) {
//
//        if (StringUtils.isEmpty(projectId)) {
//            throw new RuntimeException("projectId参数异常");
//        }
//
//        String oldDataSource = DynamicDataSourceContextHolder.peek();
//        DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(projectId));
//        try {
//            initJdglData4P6ByOne(projectId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("租户"+ projectId + "获取p6异常:-----------------" + e.getMessage());
////            throw new CustomBusinessException(e.getMessage());
//        } finally {
//            DynamicDataSourceContextHolder.poll();
//            DynamicDataSourceContextHolder.push(oldDataSource);
//        }
//
//        return null;
//    }

    /**
     * 给wbs赋值开始结束时间
     *
     * @param jdglMainPlanItems
     */
    private void setWbsDate(List<JdglMainPlanItem> jdglMainPlanItems) {
        if (jdglMainPlanItems != null) {
            List<JdglMainPlanItem> workList = jdglMainPlanItems.stream().filter(vo -> JdglMainPlanItem.ITEMTYPE_ITEM.equals(vo.getItemType())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(workList)) {
//                iteration4Date(jdglMainPlanItems, collect);
                for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItems) {
                    if (JdglMainPlanItem.ITEMTYPE_WBS.equals(jdglMainPlanItem.getItemType())) {
                        List<JdglMainPlanItem> workInWbs = workList.stream().filter(vo -> StringUtils.isNotEmpty(vo.getAncestors()) && vo.getAncestors().contains(jdglMainPlanItem.getAncestors())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(workInWbs)) {
                            Date startDate = workInWbs.get(0).getStartDate();
                            Date finishDate = workInWbs.get(0).getFinishDate();
                            for (JdglMainPlanItem jdglMainPlanItem1 : workInWbs) {
                                if (startDate == null) {
                                    startDate = jdglMainPlanItem1.getStartDate();
                                } else {
                                    if (startDate.after(jdglMainPlanItem1.getStartDate())) {
                                        startDate = jdglMainPlanItem1.getStartDate();
                                    }
                                }
                                if (finishDate == null) {
                                    finishDate = jdglMainPlanItem1.getFinishDate();
                                } else {
                                    if (finishDate.before(jdglMainPlanItem1.getFinishDate())) {
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
     *
     * @param AllItems
     * @param thisItems
     */
    private void iteration4Date(List<JdglMainPlanItem> AllItems, List<JdglMainPlanItem> thisItems) {
        if (CollectionUtils.isEmpty(thisItems) || CollectionUtils.isEmpty(AllItems)) {
            return;
        }
        Set<Long> parentIds = new HashSet<>();
        List<JdglMainPlanItem> parentItems = new ArrayList<>();
        for (JdglMainPlanItem jdglMainPlanItem : thisItems) {
            if (jdglMainPlanItem.getPid() != null) parentIds.add(jdglMainPlanItem.getPid());
        }
        if (CollectionUtils.isEmpty(parentIds)) {
            return;
        }
        for (Long parentId : parentIds) {
            for (JdglMainPlanItem jdglMainPlanItem : AllItems) {
                if (parentId.equals(jdglMainPlanItem.getId())) {
                    if ("8".equals(jdglMainPlanItem.getItemCode())) {
                        System.out.println("11111");
                    }
                    List<JdglMainPlanItem> collect = thisItems.stream().filter(vo -> parentId.equals(vo.getPid())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(collect)) {
                        Date startDate = collect.get(0).getStartDate();
                        Date finishDate = collect.get(0).getFinishDate();
                        for (JdglMainPlanItem jdglMainPlanItem1 : collect) {
                            if (startDate == null) {
                                startDate = jdglMainPlanItem1.getStartDate();
                            } else {
                                if (startDate.after(jdglMainPlanItem1.getStartDate())) {
                                    startDate = jdglMainPlanItem1.getStartDate();
                                }
                            }
                            if (finishDate == null) {
                                finishDate = jdglMainPlanItem1.getFinishDate();
                            } else {
                                if (finishDate.before(jdglMainPlanItem1.getFinishDate())) {
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

        iteration4Date(AllItems, parentItems);
    }

    /**
     * 给wbs拼接wbs编码
     *
     * @param jdglMainPlanItems
     */
    private void setWbsCode(List<JdglMainPlanItem> jdglMainPlanItems) {

        if (CollectionUtils.isEmpty(jdglMainPlanItems)) {
            return;
        }

        List<JdglMainPlanItem> collect = jdglMainPlanItems.stream().filter(vo -> vo.getPid() == null).collect(Collectors.toList());

        teration4WbsCode(jdglMainPlanItems, collect);
    }

    /**
     * 迭代拼接wbs编码
     *
     * @param allItem
     * @param thisItems
     */
    public void teration4WbsCode(List<JdglMainPlanItem> allItem, List<JdglMainPlanItem> thisItems) {

        if (CollectionUtils.isEmpty(allItem) || CollectionUtils.isEmpty(thisItems)) {
            return;
        }

        List<JdglMainPlanItem> childItem = new ArrayList<>();
        for (JdglMainPlanItem jdglMainPlanItem : thisItems) {
            if (jdglMainPlanItem.getPid() == null) jdglMainPlanItem.setAncestors(jdglMainPlanItem.getWbsObjectId());
            for (JdglMainPlanItem jdglMainPlanItem1 : allItem) {
                if (jdglMainPlanItem.getId() != null && jdglMainPlanItem.getId().equals(jdglMainPlanItem1.getPid())) {
                    if ("wbs".equals(jdglMainPlanItem1.getItemType())) {
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
     *
     * @param projectCode
     * @return
     */
    public ProjectInfo getProjectInfo(String projectCode) {
        String urlProj = p6IpPort + pre + "/projectInfoRealTime";
        ParameterizedTypeReference<List<ProjectInfo>> responseType4Proj = new ParameterizedTypeReference<List<ProjectInfo>>() {
        };
        HttpEntity<?> entity = new HttpEntity(new HttpHeaders());

        Map<String, Object> params = new HashMap<>();
        // 获取p6 项目数据
        ResponseEntity<List<ProjectInfo>> projResult = restTemplate.exchange(urlProj, HttpMethod.GET, entity, responseType4Proj, params);
        List<ProjectInfo> body = projResult.getBody();
        if (CollectionUtils.isEmpty(body)) {
            return null;
        }
        List<ProjectInfo> collect = body.stream().filter(vo -> StringUtils.isNotEmpty(vo.getProjectId())).collect(Collectors.toList());
        ProjectInfo projectInfo = body.stream().filter(vo -> projectCode.equals(vo.getProjectId())).findFirst().orElse(null);
        if (projectInfo == null) {
            projectInfo = body.stream().filter(vo -> projectCode.equals(vo.getProjectCode())).findFirst().orElse(null);
        }
        return projectInfo;
    }

    @Override
    public String initJdglWorkPreData4P6ByTenent(String tenantKey) {

        String returnStr = "";

        ProjectInfo projectInfo = getProjectInfo(tenantKey);
        if (projectInfo == null) return returnStr;

        String projectId = projectInfo.getProjectCode();

        List<JdglMainPlanItem> mainPlanItemList = jdglMainPlanItemService.getUsingJdglMainPlanItemList(new JdglMainPlanItem());

        if (CollectionUtils.isEmpty(mainPlanItemList)) {
            return returnStr;
        }

        Long mainPlanId = mainPlanItemList.get(0).getMainPlanId();

        addMainPlanItemPreList(mainPlanId, projectId, mainPlanItemList);

        return "初始化成功!";
    }

    @Override
    public void pushUserToP6(List<ActivityInfoVoBean> activityInfoVoBeanList) {
        try {
            System.out.println("----------------------推送作业用户数据开始---------------------");
            String url = p6IpPort + pre + "/updateActivityConstomField";

            Map<String, Object> params = new HashMap<>();
            params.put("projectId", SecurityUtils.getTenantKey());
//            params.put("projectId", "test-01");
            params.put("activityList", activityInfoVoBeanList);
//            params.put("jobPerson", jobPerson);
            HttpEntity<?> entity = new HttpEntity(params, new HttpHeaders());
            restTemplate.exchange(url, HttpMethod.POST, entity, Object.class, params);
            System.out.println("----------------------推送作业用户数据结束---------------------");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    @Override
//    public void pushUserToP6(String activityCode, String jobPerson) {
//
//        try {
//            System.out.println("----------------------" + "修改" + activityCode + "作业开始---------------------");
//            String url = p6IpPort + pre + "/updateActivityConstomField";
//            HttpEntity<?> entity = new HttpEntity(new HttpHeaders());
//            ParameterizedTypeReference responseType = new ParameterizedTypeReference() {
//            };
//            Map<String, Object> params = new HashMap<>();
//            params.put("projectId", SecurityUtils.getTenantKey());
//            params.put("activityCode", activityCode);
//            params.put("jobPerson", jobPerson);
//            restTemplate.exchange(url + "?projectId={projectId}&activityCode=${activityCode}&jobPerson=${jobPerson}", HttpMethod.GET, entity, responseType, params);
//            System.out.println("----------------------" + "修改" + activityCode + "作业结束---------------------");
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//    }


    /**
     * 获取转换后的逻辑关系数据
     *
     * @return
     */
    public List<JdglMainPlanItemPre> getPre(String projectId) {

        List<JdglMainPlanItemPre> returnList = new ArrayList<>();

        String urlRel = p6IpPort + pre + "/relationInfo"  + this.alex;

        HttpEntity<?> entity = new HttpEntity(new HttpHeaders());

        Map<String, Object> params = new HashMap<>();

        params.put("projectId", projectId);

        ParameterizedTypeReference<List<PredecessorRelationships>> responseType4Rel = new ParameterizedTypeReference<List<PredecessorRelationships>>() {
        };
        // 获取p6 逻辑关系数据
        ResponseEntity<List<PredecessorRelationships>> relResult = restTemplate.exchange(urlRel + "?projectId={projectId}", HttpMethod.GET, entity, responseType4Rel, params);

        List<PredecessorRelationships> relInfos = relResult.getBody();

        if (!CollectionUtils.isEmpty(relInfos)) {

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

    public void addMainPlanItemPreList(Long mainPlanId, String projectId, List<JdglMainPlanItem> workInfos) {
        // 获取转换后的p6逻辑关系数据
        List<JdglMainPlanItemPre> relInfos = getPre(projectId);

        if (!CollectionUtils.isEmpty(relInfos) && !CollectionUtils.isEmpty(workInfos)) {
            for (JdglMainPlanItem jdglMainPlanItem : workInfos) {
                String p6Id = jdglMainPlanItem.getItemCode();
                Long id = jdglMainPlanItem.getId();
                for (JdglMainPlanItemPre jdglMainPlanItemPre : relInfos) {
                    jdglMainPlanItemPre.setMainPlanId(mainPlanId);
                    if (p6Id.equals(jdglMainPlanItemPre.getItemCode())) {
                        jdglMainPlanItemPre.setItemId(id);
                    }
                    if (p6Id.equals(jdglMainPlanItemPre.getPredecessorItemCode())) {
                        jdglMainPlanItemPre.setPredecessorItemId(id);
                    }
                }
            }
        }

        iJdglMainPlanItemPreService.insertJdglMainPlanItemPreList(relInfos);
    }

}
