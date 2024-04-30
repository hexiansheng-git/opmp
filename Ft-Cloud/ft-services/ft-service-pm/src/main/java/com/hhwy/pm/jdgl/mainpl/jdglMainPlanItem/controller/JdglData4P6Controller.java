package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.ProjectInfo;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglData4P6Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jdglData4P6")
public class JdglData4P6Controller {

    @Autowired
    private IJdglData4P6Service jdglData4P6Service;

    /**
     * 从1.2.1同步数据，测试用
     * @param
     * @return
     */
    @PostMapping("/syncData")
    public AjaxResult syncData() {
        jdglData4P6Service.syncData();
        return AjaxResult.success();
    }

    /**
     * 根据租户编号拉取p6数据(不切租户)
     * @param
     * @return
     */
    @PostMapping("/initJdglData4P6ByThis")
    public AjaxResult initJdglData4P6ByThis() {
        return AjaxResult.success(jdglData4P6Service.initJdglData4P6ByThis());
    }

    /**
     * 根据租户编号拉取p6数据(不切租户)
     * @param tenantKey
     * @return
     */
    @PostMapping("/initJdglData4P6ByOne")
    public AjaxResult initJdglData4P6ByOne(String tenantKey) {
        return AjaxResult.success(jdglData4P6Service.initJdglData4P6ByOne(tenantKey));
    }

    /**
     * 拉取所有租户p6数据(切租户)
     * @param
     * @return
     */
    @PostMapping("/initJdglData4P6ByAll")
    public AjaxResult initJdglData4P6ByAll() {
        return AjaxResult.success(jdglData4P6Service.initAllJdglData4P6());
    }

    /**
     * 根据租户编号拉取p6数据(切租户)
     * @param projectId
     * @return
     */
    @PostMapping("/initOneJdglData4P6ByTenent")
    public AjaxResult initOneJdglData4P6ByTenent(String projectId) {
        return AjaxResult.success(jdglData4P6Service.initOneJdglData4P6ByTenent(projectId));
    }

    /**
     * 根据租户编号拉取p6作业逻辑数据(切租户)
     * @param projectId
     * @return
     */
    @PostMapping("/initJdglWorkPreData4P6ByTenent")
    public AjaxResult initJdglWorkPreData4P6ByTenent(String projectId) {
        return AjaxResult.success(jdglData4P6Service.initJdglWorkPreData4P6ByTenent(projectId));
    }

    /**
     * 根据项目编码获取项目信息
     * @param projectCode
     * @return
     */
    @GetMapping("/getProjectInfo")
    public AjaxResult getProjectInfo(String projectCode) {
        return AjaxResult.success(jdglData4P6Service.getProjectInfo(projectCode));
    }

}
