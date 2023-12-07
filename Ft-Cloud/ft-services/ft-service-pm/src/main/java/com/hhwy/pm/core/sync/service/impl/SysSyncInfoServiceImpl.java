package com.hhwy.pm.core.sync.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.pm.core.sync.domain.SysSyncInfo;
import com.hhwy.pm.core.sync.enums.SyncBusinessEnum;
import com.hhwy.pm.core.sync.mapper.SysSyncInfoMapper;
import com.hhwy.pm.core.sync.service.ISysSyncInfoLogService;
import com.hhwy.pm.core.sync.service.ISysSyncInfoService;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.monthpl.jdglMonthPlan.domain.JdglMonthPlan;
import com.hhwy.pm.jdgl.quarterpl.jdglQuarterPlan.domain.JdglQuarterPlan;
import com.hhwy.pm.jdgl.weekpl.jdglWeekPlan.domain.JdglWeekPlan;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.review.domain.Review;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.ObjectUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据同步节点记录Service业务层处理
 * 
 * @author wk
 * @date 2023-09-04
 */
@Service
public class SysSyncInfoServiceImpl implements ISysSyncInfoService {
    private final int LIMIT_PUSH_SIZE = 500;
    @Autowired
    private SysSyncInfoMapper sysSyncInfoMapper;
    @Resource
    private ISysSyncInfoLogService sysSyncInfoLogService;
    @Autowired
    private IQqchWorkGroupService qqchWorkGroupService;
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;
    @Autowired
    private IXmslContractInfoService contractInfoService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    

    @Override
    public Long getLastId(SyncBusinessEnum syncBusinessEnum) {
        SysSyncInfo syncInfo = selectSysSyncInfoByEnum(syncBusinessEnum);
        if(syncInfo == null){
            syncInfo = new SysSyncInfo();
            syncInfo.setBusinessName(syncBusinessEnum.busType());
            syncInfo.setLastPushId(0L);
            new AddBaseInfoUtil<>().addBaseEntity(syncInfo);
            return syncInfo.getLastPushId();
        }
        return syncInfo.getLastPushId();
    }

    /**
     * 查询数据同步节点记录
     * 
     * @param syncBusinessEnum 数据同步节点记录ID
     * @return 数据同步节点记录
     */
    @Override
    public SysSyncInfo selectSysSyncInfoByEnum(SyncBusinessEnum syncBusinessEnum) {
        SysSyncInfo query = new SysSyncInfo();
        query.setBusinessName(syncBusinessEnum.busType());
        List<SysSyncInfo> list = sysSyncInfoMapper.selectSysSyncInfoList(query);
        if(CollectionUtils.isEmpty(list))
            return null;
        return list.get(0);
    }

    @Override
    @Transactional
    public void pushQqchWorkGroup(List<QqchWorkGroup> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            XmslContractInfo contractInfo = contractInfoService.getValidMaxVersionContractInfo();
            //获取有效金额万美元
//            contractInfoService.setEffectiveAmountDollar(contractInfo);
//            BigDecimal effectiveAmountDollar = ObjectUtils.nvlBigDecimal(contractInfo.getEffectiveAmountDollar()).divide(new BigDecimal("10000"),4, RoundingMode.HALF_UP);
            List<JSONObject> finalList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                QqchWorkGroup temp = list.get(i);
                temp.setProjectName(projectBasicInfo.getProjectName());
                temp.setProjectId(projectBasicInfo.getProjectId());
                temp.setRegionId(projectBasicInfo.getRegionId());
                temp.setPtVar1(projectBasicInfo.getProjectCategory());
                temp.setPtVar2(projectBasicInfo.getProjectCode());
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(list.get(i)));
                json.put("projectCode",projectBasicInfo.getProjectCode());
                json.put("projectManager",ObjectUtils.nvlString(projectBasicInfo.getProjectManager()));
                json.put("effectiveAmout",BigDecimal.ZERO);
                json.put("winDate", dateTime(contractInfo.getWinDate()) );
                json.put("signDate", dateTime(contractInfo.getSignDate()) );
                json.put("startTime", dateTime(contractInfo.getStartTime()) );
                json.put("completedTime", dateTime(contractInfo.getCompletedTime()) );
                finalList.add(json);
            }
            rocketMQTemplate.convertAndSend("qqch_work_group1:tenantSuccess", JSONObject.toJSONString(finalList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKGROUP_ENUM,ids,1L,System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    private String dateTime(Date date){
        if(date == null){
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    @Override
    @Transactional
    public void pushQqchWorkGroup(QqchWorkGroup qqchWorkGroup) {
        pushQqchWorkGroup(Arrays.asList(qqchWorkGroup));
    }

    @Override
    @Transactional
    public void pushQqchWorkPlan(List<QqchWorkPlan> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            //获取策划编制负责人，
            String planLeader = "";
            try{
                QqchWorkGroup group = qqchWorkGroupService.getValidMaxVersionQqchWorkGroup();
                planLeader = group!=null?group.getPlanEstablishDirector():"";
            }catch(Exception e){
                e.printStackTrace();
            }
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            for (int i = 0; i < list.size(); i++) {
                QqchWorkPlan temp =  list.get(i);
                temp.setProjectName(projectBasicInfo.getProjectName());
                temp.setRegionId(projectBasicInfo.getRegionId());
                temp.setProjectId(projectBasicInfo.getProjectId());
                temp.setPtVar1(projectBasicInfo.getProjectCategory());
                temp.setPtVar2(projectBasicInfo.getProjectCode());
                temp.setPtVar3(planLeader);
                temp.setPtVar4(projectBasicInfo.getProjectManager());
            }
            rocketMQTemplate.convertAndSend("qqch_work_plan1:tenantSuccess", JSONObject.toJSONString(list));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids,list.size()+0L,System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    @Transactional
    public void pushQqchWorkPlan(QqchWorkPlan qqchWorkPlan) {
        pushQqchWorkPlan(Arrays.asList(qqchWorkPlan));
    }

    @Override
    public void pushQqchReview(List<Review> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Review temp =  list.get(i);
                temp.setProjectId(projectBasicInfo.getProjectId());
                temp.setProjectName(projectBasicInfo.getProjectName());
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("regionId", projectBasicInfo.getRegionId());
                jsonObject.put("regionName", projectBasicInfo.getRegionName());
                jsonObject.put("ptVar1", projectBasicInfo.getProjectCategory());
                jsonObject.put("ptVar2", projectBasicInfo.getProjectCode());
                jsonObjList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("qqch_review:tenantSuccess", JSONObject.toJSONString(jsonObjList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids,list.size()+0L,System.currentTimeMillis()-beginMills,status,errMsg);
        }        
    }

    @Override
    public void pushQqchReview(Review review) {
        pushQqchReview(Arrays.asList(review));
    }

    @Override
    public void pushQqchPerformInspection(List<QqchPerformInspection> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                QqchPerformInspection temp = list.get(i);
                temp.setPtVar1(projectBasicInfo.getProjectCategory());
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("projectId",projectBasicInfo.getProjectId());
                jsonObject.put("projectName",projectBasicInfo.getProjectName());
                jsonObject.put("regionId",projectBasicInfo.getRegionId());
                jsonObject.put("regionName",projectBasicInfo.getRegionName());
                jsonObject.put("ptVar1",projectBasicInfo.getProjectCategory());
                jsonObject.put("ptVar2",projectBasicInfo.getProjectCode());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("qqch_performInspection:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids,list.size()+0L,System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushQqchPerformInspection(QqchPerformInspection inspection) {
        pushQqchPerformInspection(Arrays.asList(inspection)); 
    }

    @Override
    public void pushQqchSummaryEvaluation(List<QqchSummaryEvaluation> list) {
        long beginMills = System.currentTimeMillis();
        Integer status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            for (int i = 0; i < list.size(); i++) {
                QqchSummaryEvaluation temp = list.get(i);
                temp.setPtVar1(projectBasicInfo.getProjectCategory());
                temp.setProjectId(projectBasicInfo.getProjectId());
                temp.setProjectName(projectBasicInfo.getProjectName());
                temp.setRegionId(projectBasicInfo.getRegionId());
                temp.setRegionName(projectBasicInfo.getRegionName());
                temp.setPtVar1(projectBasicInfo.getProjectCategory());
                temp.setPtVar2(projectBasicInfo.getProjectCode());
            }
            rocketMQTemplate.convertAndSend("qqch_evaluation:gm", JSONObject.toJSONString(list));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids,list.size()+0L,System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushQqchSummaryEvaluation(QqchSummaryEvaluation evaluation) {
        pushQqchSummaryEvaluation(Arrays.asList(evaluation));
    }

    /**
     * 推送差异化分析
     * @param list
     */
    @Override
    public void pushJdglDiffAnalysis(List<JdglDiffAnalysis> list) {
        long beginMills = System.currentTimeMillis();
        int status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (JdglDiffAnalysis temp : list) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("regionId",projectBasicInfo.getRegionId());
                jsonObject.put("regionName",projectBasicInfo.getRegionName());
                jsonObject.put("projectId", projectBasicInfo.getProjectId());
                jsonObject.put("projectName", projectBasicInfo.getProjectName());
                jsonObject.put("projectCode", projectBasicInfo.getProjectCode());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("jdgl_diff_analysis:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
//            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
//            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids, (long) list.size(),System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushJdglDiffAnalysis(JdglDiffAnalysis diffAnalysis) {
        pushJdglDiffAnalysis(Arrays.asList(diffAnalysis));
    }

    /**
     * 推送进度纠偏
     * @param list
     */
    @Override
    public void pushJdglProgressCorrectionTrack(List<JdglProgressCorrectionTrack> list) {
        long beginMills = System.currentTimeMillis();
        int status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (JdglProgressCorrectionTrack temp : list) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("regionId",projectBasicInfo.getRegionId());
                jsonObject.put("regionName",projectBasicInfo.getRegionName());
                jsonObject.put("projectId", projectBasicInfo.getProjectId());
                jsonObject.put("projectName", projectBasicInfo.getProjectName());
                jsonObject.put("projectCode", projectBasicInfo.getProjectCode());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("jdgl_progress_correction_track:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids, (long) list.size(),System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushJdglProgressCorrectionTrack(JdglProgressCorrectionTrack progressCorrectionTrack) {
        pushJdglProgressCorrectionTrack(Arrays.asList(progressCorrectionTrack));
    }

    /**
     * 推送总体计划
     * @param list
     */
    @Override
    public void pushJdglMainPlan(List<JdglMainPlan> list){
        long beginMills = System.currentTimeMillis();
        int status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (JdglMainPlan temp : list) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("regionId",projectBasicInfo.getRegionId());
                jsonObject.put("regionName",projectBasicInfo.getRegionName());
                jsonObject.put("projectId", projectBasicInfo.getProjectId());
                jsonObject.put("projectName", projectBasicInfo.getProjectName());
                jsonObject.put("projectCode", projectBasicInfo.getProjectCode());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("jdgl_main_plan:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
//            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids, (long) list.size(),System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushJdglMainPlan(JdglMainPlan jdglMainPlan){
        this.pushJdglMainPlan(Arrays.asList(jdglMainPlan));
    }


    /**
     * 推送年度计划
     * @param list
     */
    @Override
    public void pushJdglYearPlan(List<JdglYearPlan> list) {
        long beginMills = System.currentTimeMillis();
        int status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (JdglYearPlan temp : list) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("regionId",projectBasicInfo.getRegionId());
                jsonObject.put("regionName",projectBasicInfo.getRegionName());
                jsonObject.put("projectId", projectBasicInfo.getProjectId());
                jsonObject.put("projectName", projectBasicInfo.getProjectName());
                jsonObject.put("projectCode", projectBasicInfo.getProjectCode());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("jdgl_year_plan:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids, (long) list.size(),System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushJdglYearPlan(JdglYearPlan yearPlan) {
        pushJdglYearPlan(Arrays.asList(yearPlan));
    }


    /**
     * 推送季度计划
     * @param list
     */
    @Override
    public void pushJdglQuarterPlan(List<JdglQuarterPlan> list) {
        long beginMills = System.currentTimeMillis();
        int status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (JdglQuarterPlan temp : list) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("regionId",projectBasicInfo.getRegionId());
                jsonObject.put("regionName",projectBasicInfo.getRegionName());
                jsonObject.put("projectId", projectBasicInfo.getProjectId());
                jsonObject.put("projectName", projectBasicInfo.getProjectName());
                jsonObject.put("projectCode", projectBasicInfo.getProjectCode());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("jdgl_quarter_plan:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids, (long) list.size(),System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushJJdglQuarterPlan(JdglQuarterPlan quarterPlan) {
        pushJdglQuarterPlan(Arrays.asList(quarterPlan));
    }


    /**
     * 推送月度计划
     * @param list
     */
    @Override
    public void pushJdglMonthPlan(List<JdglMonthPlan> list) {
        long beginMills = System.currentTimeMillis();
        int status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (JdglMonthPlan temp : list) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("regionId",projectBasicInfo.getRegionId());
                jsonObject.put("regionName",projectBasicInfo.getRegionName());
                jsonObject.put("projectId", projectBasicInfo.getProjectId());
                jsonObject.put("projectName", projectBasicInfo.getProjectName());
                jsonObject.put("projectCode", projectBasicInfo.getProjectCode());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("jdgl_month_plan:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids, (long) list.size(),System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushJdglMonthPlan(JdglMonthPlan monthPlan) {
        pushJdglMonthPlan(Arrays.asList(monthPlan));
    }


    /**
     * 推送周计划
     * @param list
     */
    @Override
    public void pushJdglWeekPlan(List<JdglWeekPlan> list) {
        long beginMills = System.currentTimeMillis();
        int status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (JdglWeekPlan temp : list) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("regionId",projectBasicInfo.getRegionId());
                jsonObject.put("regionName",projectBasicInfo.getRegionName());
                jsonObject.put("projectId", projectBasicInfo.getProjectId());
                jsonObject.put("projectName", projectBasicInfo.getProjectName());
                jsonObject.put("projectCode", projectBasicInfo.getProjectCode());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("jdgl_week_plan:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids, (long) list.size(),System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushJdglWeekPlan(JdglWeekPlan weekPlan) {
        pushJdglWeekPlan(Arrays.asList(weekPlan));
    }


    /**
     * 推送合同信息
     * @param list
     */
    @Override
    public void pushXmslContractInfo(List<XmslContractInfo> list) {
        long beginMills = System.currentTimeMillis();
        int status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (XmslContractInfo temp : list) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("regionId",projectBasicInfo.getRegionId());
                jsonObject.put("regionName",projectBasicInfo.getRegionName());
                jsonObject.put("projectId", projectBasicInfo.getProjectId());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("xmsl_contract_info:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids, (long) list.size(),System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushXmslContractInfo(XmslContractInfo xmslContractInfo) {
        pushXmslContractInfo(Arrays.asList(xmslContractInfo));
    }


    /**
     * 推送进度填报
     * @param list
     */
    @Override
    public void pushJdglDaySchedule(List<JdglDaySchedule> list) {
        long beginMills = System.currentTimeMillis();
        int status = 1;
        String errMsg = "";
        try{
            ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (JdglDaySchedule temp : list) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(temp));
                jsonObject.put("regionId",projectBasicInfo.getRegionId());
                jsonObject.put("regionName",projectBasicInfo.getRegionName());
                jsonObject.put("projectId", projectBasicInfo.getProjectId());
                jsonObject.put("projectName", projectBasicInfo.getProjectName());
                jsonObject.put("projectCode", projectBasicInfo.getProjectCode());
                jsonObjectList.add(jsonObject);
            }
            rocketMQTemplate.convertAndSend("jdgl_day_schedule:tenantSuccess", JSONObject.toJSONString(jsonObjectList));
        }catch(Exception e){
            e.printStackTrace();
            status = 0;
            errMsg = e.getMessage();
            throw e;
        }finally {
            String ids = list.stream().map(r->r.getId()+"").collect(Collectors.joining(","));
            //3、更新syncInfo
            sysSyncInfoLogService.insert(SyncBusinessEnum.QQCHWORKPLAN_ENUM,ids, (long) list.size(),System.currentTimeMillis()-beginMills,status,errMsg);
        }
    }

    @Override
    public void pushJdglDaySchedule(JdglDaySchedule daySchedule) {
        pushJdglDaySchedule(Arrays.asList(daySchedule));
    }


}
