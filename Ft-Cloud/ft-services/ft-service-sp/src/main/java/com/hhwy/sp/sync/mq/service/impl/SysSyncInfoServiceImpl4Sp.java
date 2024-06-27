package com.hhwy.sp.sync.mq.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReview;
import com.hhwy.sp.experiment.mixRatioManage.domain.SgjsMixRatioManage;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.SgjsCriticalExpReport;
import com.hhwy.sp.experiment.sgjsCriticalExpReport.domain.vo.CriticalExpReportVo;
import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManage;
import com.hhwy.sp.experiment.sgjsExperProgressManage.domain.SgjsExperProgressManageVo;
import com.hhwy.sp.sciTech.sgjsTechMethod.domain.SgjsTechMethod;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;
import com.hhwy.sp.sgjsDiscloseRecord.domain.vo.DiscloseRecordPushVo;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManage;
import com.hhwy.sp.sgjsMeasure.sgjsPlanMeasureManage.domain.SgjsPlanMeasureManageVo;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.SgjsReportMeasureSubmit;
import com.hhwy.sp.sgjsMeasure.sgjsReportMeasureSubmit.domain.vo.ReportMeasureSubmitPushVo;
import com.hhwy.sp.sync.mq.service.ISysSyncInfoService4Sp;
import com.hhwy.sp.techManagement.sgjsPaperPublish.domain.SgjsPaperPublish;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;
import com.hhwy.sp.techTrain.domain.SgjsTechnicalTraining;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据同步节点记录Service业务层处理
 *
 * @author wk
 * @date 2023-09-04
 */
@Service
public class SysSyncInfoServiceImpl4Sp implements ISysSyncInfoService4Sp {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private PmServiceApi pmServiceApi;

    @Override
    @Transactional
    public void pushSgjsDiscloseRecord(DiscloseRecordPushVo pushVo) {
        try {
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            pushVo.setProjectCode(projectDto.getProjectCode());
            List<SgjsDiscloseRecord> list = pushVo.getRecordList();
            if(!CollectionUtils.isEmpty(list)){
                for (SgjsDiscloseRecord temp : list) {
                    temp.setPtVar2(SecurityUtils.getTenantKey());
                    temp.setRegionId(projectDto.getRegionId());
                    temp.setRegionName(projectDto.getRegionName());
                }
            }
            rocketMQTemplate.convertAndSend("sgjs_disclose_record:tenantSuccess", JSONObject.toJSONString(pushVo));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public void pushSgjsExperProgressManage(SgjsExperProgressManageVo sgjsExperProgressManageVo) {
        try {
            List<SgjsExperProgressManage> treeList = sgjsExperProgressManageVo.getTreeList();
            treeList = TreeUtil.treeToListWithoutId(treeList);
            List<JSONObject> finalList = new ArrayList<>();
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            //判断当前项目下是否有数据可推送
            if (!CollectionUtils.isEmpty(treeList)) {
                //有则正常推送
                for (SgjsExperProgressManage sgjsExperProgressManage : treeList) {
                    sgjsExperProgressManage.setPtVar2(SecurityUtils.getTenantKey());
                    if (prjInfo.get("regionId") != null)
                        sgjsExperProgressManage.setRegionId(Long.parseLong(prjInfo.get("regionId").toString()));
                    sgjsExperProgressManage.setRegionName((String) prjInfo.get("regionName"));
                    if (prjInfo.get("projectId") != null)
                        sgjsExperProgressManage.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
                    sgjsExperProgressManage.setProjectName((String) prjInfo.get("projectName"));
                    sgjsExperProgressManage.setRegionId(Long.parseLong(prjInfo.get("regionId").toString()));
                    sgjsExperProgressManage.setRegionName((String) prjInfo.get("regionName"));
                    JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(sgjsExperProgressManage));
                    finalList.add(json);
                }
                rocketMQTemplate.convertAndSend("sgjs_exper_progress_manage:tenantSuccess", JSONObject.toJSONString(finalList));
            } else {
                //没有则传projectId，总部版删除该项目id下的数据
                SgjsExperProgressManage manage = new SgjsExperProgressManage();
                if (prjInfo.get("projectId") != null) {
                    manage.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
                }
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(manage));
                finalList.add(json);
                rocketMQTemplate.convertAndSend("sgjs_exper_progress_manage:tenantSuccess", JSONObject.toJSONString(finalList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public void pushSgjsTechnicalTraining(SgjsTechnicalTraining sgjsTechnicalTraining) {
        try {
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            sgjsTechnicalTraining.setPtVar2(SecurityUtils.getTenantKey());
            if (prjInfo.get("regionId") != null)
                sgjsTechnicalTraining.setRegionId(Long.parseLong(prjInfo.get("regionId").toString()));
            sgjsTechnicalTraining.setRegionName((String) prjInfo.get("regionName"));
            if (prjInfo.get("projectId") != null)
                sgjsTechnicalTraining.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
            sgjsTechnicalTraining.setProjectName((String) prjInfo.get("projectName"));
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(sgjsTechnicalTraining));
            rocketMQTemplate.convertAndSend("sgjs_technical_training:tenantSuccess", JSONObject.toJSONString(json));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public void pushSgjsPlanMeasureManage(SgjsPlanMeasureManageVo vo) {
        try {
            List<SgjsPlanMeasureManage> treeList = vo.getTreeList();
            treeList = TreeUtil.treeToListWithoutId(treeList);
            List<JSONObject> finalList = new ArrayList<>();
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            //判当前项目下是否有数据可推送
            if (!CollectionUtils.isEmpty(treeList)) {
                //有数据，则正常推送
                for (SgjsPlanMeasureManage sgjsPlanMeasureManage : treeList) {
                    sgjsPlanMeasureManage.setPtVar2(SecurityUtils.getTenantKey());
                    if (prjInfo.get("regionId") != null)
                        sgjsPlanMeasureManage.setRegionId(Long.parseLong(prjInfo.get("regionId").toString()));
                    sgjsPlanMeasureManage.setRegionName((String) prjInfo.get("regionName"));
                    if (prjInfo.get("projectId") != null)
                        sgjsPlanMeasureManage.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
                    sgjsPlanMeasureManage.setProjectName((String) prjInfo.get("projectName"));
                    sgjsPlanMeasureManage.setProjectCode((String) prjInfo.get("projectCode"));
                    JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(sgjsPlanMeasureManage));
                    finalList.add(json);
                }
                rocketMQTemplate.convertAndSend("sgjs_plan_measure_manage:tenantSuccess", JSONObject.toJSONString(finalList));
            } else {
                //当前项目无数据可推送   传projectId,总部版删除该项目id下的数据
                SgjsPlanMeasureManage manage = new SgjsPlanMeasureManage();
                if (prjInfo.get("projectId") != null) {
                    manage.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
                }
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(manage));
                finalList.add(json);
                rocketMQTemplate.convertAndSend("sgjs_plan_measure_manage:tenantSuccess", JSONObject.toJSONString(finalList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public void pushSgjsCriticalExpReport(CriticalExpReportVo vo) {
        try {
            List<SgjsCriticalExpReport> list = vo.getReportList();
            List<JSONObject> finalList = new ArrayList<>();
            Map<String, Object> prjInfo = pmServiceApi.getPrjInfo();
            //判断当前项目是否无数据可推送
            if (!CollectionUtils.isEmpty(list)) {
                //有数据，则正常推送，给每个数据赋项目和区域信息
                for (SgjsCriticalExpReport sgjsCriticalExpReport : list) {
                    sgjsCriticalExpReport.setPtVar2(SecurityUtils.getTenantKey());
                    if (prjInfo.get("regionId") != null)
                        sgjsCriticalExpReport.setRegionId(Long.parseLong(prjInfo.get("regionId").toString()));
                    sgjsCriticalExpReport.setRegionName((String) prjInfo.get("regionName"));
                    if (prjInfo.get("projectId") != null)
                        sgjsCriticalExpReport.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
                    sgjsCriticalExpReport.setProjectName((String) prjInfo.get("projectName"));
                    sgjsCriticalExpReport.setPtVar5((String) prjInfo.get("projectCode"));
                    JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(sgjsCriticalExpReport));
                    finalList.add(json);
                }
                rocketMQTemplate.convertAndSend("sgjs_critical_exp_report:tenantSuccess", JSONObject.toJSONString(finalList));
            } else {
                //无数据可推   传一个projectId,总部版删除该项目下的数据
                SgjsCriticalExpReport report = new SgjsCriticalExpReport();
                if (prjInfo.get("projectId") != null) {
                    report.setProjectId(Long.parseLong(prjInfo.get("projectId").toString()));
                }
                JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(report));
                finalList.add(json);
                rocketMQTemplate.convertAndSend("sgjs_critical_exp_report:tenantSuccess", JSONObject.toJSONString(finalList));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushReportMeasureSubmit(ReportMeasureSubmitPushVo pushVo) {
        try {
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            List<SgjsReportMeasureSubmit> reportMeasureSubmitList = pushVo.getInsertList();
            if(!CollectionUtils.isEmpty(reportMeasureSubmitList)){
                for (SgjsReportMeasureSubmit temp : reportMeasureSubmitList) {
                    temp.setProjectName(projectDto.getProjectName());
                    temp.setProjectId(projectDto.getProjectId());
                    temp.setRegionId(projectDto.getRegionId());
                    temp.setRegionName(projectDto.getRegionName());
                    temp.setProjectCode(projectDto.getProjectCode());
                }
            }
            rocketMQTemplate.convertAndSend("sgjs_report_measure_submit:tenantSuccess", JSONObject.toJSONString(pushVo));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushSgjsTechMethod(SgjsTechMethod techMethod) {
        try {
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            techMethod.setProjectId(projectDto.getProjectId());
            techMethod.setRegionId(projectDto.getRegionId());
            techMethod.setRegionName(projectDto.getRegionName());
            techMethod.setPtVar5(projectDto.getProjectCode());
            rocketMQTemplate.convertAndSend("sgjs_tech_method:tenantSuccess", JSONObject.toJSONString(techMethod));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushSgjsPatentDeclare(SgjsPatentDeclare patentDeclare) {
        try {
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            patentDeclare.setPtVar4(projectDto.getProjectCode());
            patentDeclare.setProjectId(projectDto.getProjectId());
            patentDeclare.setProjectName(projectDto.getProjectName());
//            patentDeclare.setRegionId(projectDto.getRegionId());
//            patentDeclare.setRegionName(projectDto.getRegionName());
            rocketMQTemplate.convertAndSend("sgjs_patent_declare:tenantSuccess", JSONObject.toJSONString(patentDeclare));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushSgjsPaperPublish(SgjsPaperPublish paperPublish) {
        try {
            ProjectDto projectDto = pmServiceApi.getProjectDto();
            paperPublish.setPtVar4(projectDto.getProjectCode());
            paperPublish.setProjectId(projectDto.getProjectId());
            paperPublish.setProjectName(projectDto.getProjectName());
            paperPublish.setRegionId(projectDto.getRegionId());
            paperPublish.setRegionName(projectDto.getRegionName());
            rocketMQTemplate.convertAndSend("sgjs_paper_publish:tenantSuccess", JSONObject.toJSONString(paperPublish));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushSgjsBuildSchemeReview(SgjsBuildSchemeReview review) {
        try {
            rocketMQTemplate.convertAndSend("sgjs_build_scheme_review:tenantSuccess", JSONObject.toJSONString(review));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void pushSgjsMixRatioManage(SgjsMixRatioManage mixRatioManage) {
        try {
            rocketMQTemplate.convertAndSend("sgjs_mix_ratio_manage:tenantSuccess", JSONObject.toJSONString(mixRatioManage));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
