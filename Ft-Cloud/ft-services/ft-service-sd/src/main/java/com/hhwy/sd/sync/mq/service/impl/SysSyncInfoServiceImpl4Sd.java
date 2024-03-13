package com.hhwy.sd.sync.mq.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sd.achievementReview.domain.KcsjAchievement;
import com.hhwy.sd.achievementReview.domain.KcsjAchievementReview;
import com.hhwy.sd.achievementReview.domain.vo.AchievementPushVo;
import com.hhwy.sd.designDocumentApproval.domain.KcsjDesignDocumentApproval;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjEngineeringQuantitiesBill.domain.KcsjEngineeringQuantitiesBill;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.KcsjMaterialsList;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.domain.KcsjDesignOptimize;
import com.hhwy.sd.disclosureRecord.domain.KcsjDisclosureRecord;
import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.domain.KcsjPlanCommunicationRecords;
import com.hhwy.sd.planProcess.kcsjPlanMonthlyReport.domain.KcsjPlanMonthlyReport;
import com.hhwy.sd.sync.mq.ISysSyncInfoService4Sd;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wll
 * 2024/3/6
 */
@Service
public class SysSyncInfoServiceImpl4Sd implements ISysSyncInfoService4Sd {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private PmServiceApi pmServiceApi;


    @Override
    @Transactional
    public void pushKcsjPlanCommunicationRecords(List<KcsjPlanCommunicationRecords> list) {

        try{
            List<JSONObject> finalList = new ArrayList<>();
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            for (int i = 0; i < list.size(); i++) {
                KcsjPlanCommunicationRecords temp = list.get(i);
                temp.setPtVar2(SecurityUtils.getTenantKey());
                if(prjInfo.get("regionId") != null)temp.setRegionId(Long.parseLong(prjInfo.get("regionId").toString()));
                temp.setRegionName((String) prjInfo.get("regionName"));
                if(prjInfo.get("projectId") != null)temp.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
                temp.setProjectName((String) prjInfo.get("projectName"));
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(list.get(i)));
                finalList.add(json);
            }
            rocketMQTemplate.convertAndSend("kcsj_plan_communication_records:tenantSuccess", JSONObject.toJSONString(finalList));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushPlanMonthlyReport(List<KcsjPlanMonthlyReport> list){
        try{
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            List<JSONObject> finalList = new ArrayList<>();
            for (KcsjPlanMonthlyReport temp : list) {
                temp.setProjectName(projectDto.getProjectName());
                temp.setProjectId(projectDto.getProjectId());
                temp.setRegionId(projectDto.getRegionId());
                temp.setRegionName(projectDto.getRegionName());
                temp.setPtVar5(projectDto.getProjectCode());
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(temp));
                finalList.add(json);
            }
            rocketMQTemplate.convertAndSend("kcsj_plan_monthly_report:tenantSuccess", JSONObject.toJSONString(finalList));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushAchievement(AchievementPushVo pushVo){
        try{
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            List<KcsjAchievement> achievementList = pushVo.getAchievementList();
            for (KcsjAchievement temp : achievementList) {
                temp.setProjectName(projectDto.getProjectName());
                temp.setProjectId(projectDto.getProjectId());
                temp.setRegionId(projectDto.getRegionId());
                temp.setRegionName(projectDto.getRegionName());
                temp.setPtVar5(projectDto.getProjectCode());
            }
            rocketMQTemplate.convertAndSend("kcsj_achievement:tenantSuccess", JSONObject.toJSONString(pushVo));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushAchievementReview(KcsjAchievementReview review){
        try{
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            review.setProjectName(projectDto.getProjectName());
            review.setProjectId(projectDto.getProjectId());
            review.setRegionId(projectDto.getRegionId());
            review.setRegionName(projectDto.getRegionName());
            review.setPtVar5(projectDto.getProjectCode());
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(review));
            List<KcsjAchievement> achievementList = review.getAchievementList();
            for (KcsjAchievement achievement : achievementList) {
                achievement.setProjectName(projectDto.getProjectName());
                achievement.setProjectId(projectDto.getProjectId());
                achievement.setRegionId(projectDto.getRegionId());
                achievement.setRegionName(projectDto.getRegionName());
                achievement.setPtVar5(projectDto.getProjectCode());
            }
            rocketMQTemplate.convertAndSend("kcsj_achievement_review:tenantSuccess", JSONObject.toJSONString(json));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushAchievementReview4Delete(Long id){
        try{
            rocketMQTemplate.convertAndSend("kcsj_achievement_review_delete:delete", JSONObject.toJSONString(id));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushDisclosureRecord(List<KcsjDisclosureRecord> list){
        try{
            Map<String,Object> map = new HashMap<>();
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            for (KcsjDisclosureRecord temp : list) {
                temp.setProjectName(projectDto.getProjectName());
                temp.setProjectId(projectDto.getProjectId());
                temp.setRegionId(projectDto.getRegionId());
                temp.setRegionName(projectDto.getRegionName());
                temp.setPtVar5(projectDto.getProjectCode());
            }
            map.put("recordList",list);
            map.put("projectCode",projectDto.getProjectCode());
            rocketMQTemplate.convertAndSend("kcsj_disclosure_record:tenantSuccess", JSONObject.toJSONString(map));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushDesignOptimize(KcsjDesignOptimize optimize){
        try{
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            optimize.setProjectName(projectDto.getProjectName());
            optimize.setProjectId(projectDto.getProjectId());
            optimize.setRegionId(projectDto.getRegionId());
            optimize.setRegionName(projectDto.getRegionName());
            optimize.setPtVar5(projectDto.getProjectCode());
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(optimize));
            rocketMQTemplate.convertAndSend("kcsj_design_optimize:tenantSuccess", JSONObject.toJSONString(json));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushDesignOptimize4Delete(List<Long> ids){
        try{
            rocketMQTemplate.convertAndSend("kcsj_design_optimize_delete:delete", JSONObject.toJSONString(ids));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 推送设计文件报批
     * @param list
     */
    @Override
    public void pushKcsjDesignDocumentApproval(List<KcsjDesignDocumentApproval> list) {
        try{
            List<JSONObject> finalList = new ArrayList<>();
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            for (int i = 0; i < list.size(); i++) {
                KcsjDesignDocumentApproval temp = list.get(i);
                temp.setPtVar2(SecurityUtils.getTenantKey());
                if(prjInfo.get("regionId") != null)temp.setRegionId(Long.parseLong(prjInfo.get("regionId").toString()));
                temp.setRegionName((String) prjInfo.get("regionName"));
                if(prjInfo.get("projectId") != null)temp.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
                temp.setProjectName((String) prjInfo.get("projectName"));
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(list.get(i)));
                finalList.add(json);
            }
            rocketMQTemplate.convertAndSend("kcsj_design_document_approval:tenantSuccess", JSONObject.toJSONString(finalList));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 推送工程量清单数据
     * @param kcsjEngineeringQuantitiesBill
     */
    @Override
    public void pushKcsjEngineeringQuantitiesBill(KcsjEngineeringQuantitiesBill kcsjEngineeringQuantitiesBill) {
        try{

            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            kcsjEngineeringQuantitiesBill.setPtVar2(SecurityUtils.getTenantKey());
            if(prjInfo.get("regionId") != null)kcsjEngineeringQuantitiesBill.setRegionId(Long.parseLong(prjInfo.get("regionId").toString()));
            kcsjEngineeringQuantitiesBill.setRegionName((String) prjInfo.get("regionName"));
            if(prjInfo.get("projectId") != null)kcsjEngineeringQuantitiesBill.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
            kcsjEngineeringQuantitiesBill.setProjectName((String) prjInfo.get("projectName"));
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(kcsjEngineeringQuantitiesBill));
            rocketMQTemplate.convertAndSend("kcsj_engineering_quantities_bill:tenantSuccess", JSONObject.toJSONString(json));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }




    /**
     * 推送主材清单数据
     * @param kcsjMaterialsList
     */
    @Override
    public void pushKcsjMaterialsList(KcsjMaterialsList kcsjMaterialsList) {
        try{

            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            kcsjMaterialsList.setPtVar2(SecurityUtils.getTenantKey());
            if(prjInfo.get("regionId") != null)kcsjMaterialsList.setRegionId(Long.parseLong(prjInfo.get("regionId").toString()));
            kcsjMaterialsList.setRegionName((String) prjInfo.get("regionName"));
            if(prjInfo.get("projectId") != null)kcsjMaterialsList.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
            kcsjMaterialsList.setProjectName((String) prjInfo.get("projectName"));
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(kcsjMaterialsList));
            rocketMQTemplate.convertAndSend("kcsj_materials_list:tenantSuccess", JSONObject.toJSONString(json));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
