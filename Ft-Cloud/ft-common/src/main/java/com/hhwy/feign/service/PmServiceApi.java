package com.hhwy.feign.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.SysSyncInfoLog;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.factory.PmServiceFallbackFactory;
import com.hhwy.feign.service.domain.CommonQqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.dto.DesignDisclosurePlanDto;
import com.hhwy.pm.qqch.preparation.technique.manage.domain.QqchPostSetting;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 文件服务
 *
 * @author hhwy
 */
@FeignClient(name = "ft-service-pm", fallbackFactory = PmServiceFallbackFactory.class)
public interface PmServiceApi {


    /**
     *  拆入对应租户的项目信息
     */
    @PostMapping("/projectBasicInfo/addTenant")
    AjaxResult insertProjectTenant(@RequestBody Map map);

    /**
     *  3.6.1测量工作概述
     */
    @PostMapping("/qqchMeasureExpRange/feignList")
    AjaxResult qqchMeasureExpRangeList(@RequestBody CommonQqchMeasureExpRange dto);

    /**
     * 获取项目技术管理部门及岗位设置表
     * @return
     */
    @GetMapping("/qqchPostSetting/getTechDeptList")
    List<QqchPostSetting> getTechDeptList();

    /**
     * 插入同步日志
     * @param log
     * @return
     */
    @PostMapping("/syncInfo/insert")
    AjaxResult insertSyncLog(@RequestBody SysSyncInfoLog log);

    @GetMapping("/projectBasicInfo/getPrjInfo")
    Map<String,Object> getPrjInfo();

    @GetMapping("/projectBasicInfo/getProjectDto")
    ProjectDto getProjectDto();

    @GetMapping("/projectBasicInfo/projectInfo")
    AjaxResult projectInfo();


    /**
     * 3.7.1 测量管理计划
     * @return
     */
    @GetMapping("/qqchMeasureExpPlan/feignList")
    AjaxResult qqchMeasureExpPlanList();

    /**
     * 3.7.2 实验工作计划
     * @return
     */
    @GetMapping("/qqchMeasureExpPlan/feignPlanList")
    AjaxResult feignPlanList();

    /**
     * 3.6.4
     * @return
     */
    @PostMapping("/qqchMeasureExpEqu/feignList")
    AjaxResult qqchMeasureExpEquList();

    /**
     * 3.7.4 实验仪器设备配置计划
     * @return
     */
    @PostMapping("/qqchMeasureExpEqu/feignExperimentList")
    AjaxResult feignExperimentList();


    /**
     * 2.1.2 项目部勘察设计组织机构查询
     * @param
     * @return
     */
    @GetMapping("/qqchSurveyOrganization/apiList")
    AjaxResult getQqchSurveyOrganizationList();


    /**
     * 2.2 勘察设计工作计划
     * @param
     * @return
     */
    @GetMapping("/qqchSurveyWorkPlan/getData")
    AjaxResult getData();

    /**
     * 2.4设计交底策划数据
     * @return
     */
    @GetMapping("/qqchDesignDisclosurePlan/getDisclosurePlanDtoList")
    List<DesignDisclosurePlanDto> getDisclosurePlanDtoList();

    /**
     * 2.1.3 前期策划设备配置查询
     * @param
     * @return
     */
    @GetMapping("/qqchSurveyDesignTeams/list")
    AjaxResult getqqchSurveyDesignTeams();


}
