package com.hhwy.pm.qqch.qqchWorkPlan.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.pm.qqch.qqchWorkPlan.domain.QqchWorkPlan;
import com.hhwy.pm.qqch.qqchWorkPlan.service.IQqchWorkPlanService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author hwj
 * @date 2023-07-12 15:30:52
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchWorkPlan")
public class QqchWorkPlanController extends BaseController {

    @Autowired
    private IQqchWorkPlanService qqchWorkPlanService;


    @GetMapping
    public AjaxResult getQqchWorkPlan(@Validated(ValidationGroups.Get.class) QqchWorkPlan qqchWorkPlanParam) {
        QqchWorkPlan qqchWorkPlan = qqchWorkPlanService.getQqchWorkPlan(qqchWorkPlanParam);
        return AjaxResult.success(qqchWorkPlan);
    }

    /**获取详情数据
     * @param map
     * @return
     */
    @GetMapping("/baseInfo")
    public AjaxResult baseInfo(@RequestParam Map<String, String> map) {
        return AjaxResult.success(qqchWorkPlanService.baseInfo(map));
    }

    /**台账
     * @param qqchWorkPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchWorkPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchWorkPlanList(@Validated(ValidationGroups.Select.class) QqchWorkPlan qqchWorkPlanParam) {
        startPage();
        List<QqchWorkPlan> qqchWorkPlanList = qqchWorkPlanService.getQqchWorkPlanList(qqchWorkPlanParam);
        return getDataTableAjaxResult(qqchWorkPlanList);
    }

    /**新增数据
     * @param qqchWorkPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchWorkPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchWorkPlan(@RequestBody QqchWorkPlan qqchWorkPlanParam) {
        return AjaxResult.success(qqchWorkPlanService.insertQqchWorkPlan(qqchWorkPlanParam));
    }

    /**提交数据
     * @param qqchWorkPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchWorkPlan:submit")
    @PostMapping("/submit")
    public AjaxResult submitQqchWorkPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchWorkPlan qqchWorkPlanParam) {
        return AjaxResult.success(qqchWorkPlanService.submitQqchWorkPlan(qqchWorkPlanParam));
    }

    /**编辑数据
     * @param qqchWorkPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchWorkPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchWorkPlan(@RequestBody QqchWorkPlan qqchWorkPlanParam) {
        return toAjax(qqchWorkPlanService.updateQqchWorkPlan(qqchWorkPlanParam));
    }

    /**调整
     * @param qqchWorkPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchWorkPlan:adjust")
    @PostMapping("/adjust")
    public AjaxResult adjustQqchWorkPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchWorkPlan qqchWorkPlanParam) {
        return AjaxResult.success(qqchWorkPlanService.adjustQqchWorkPlan(qqchWorkPlanParam));
    }

    /**删除
     * @param qqchWorkPlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchWorkPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchWorkPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchWorkPlan qqchWorkPlanParam) {
        return toAjax(qqchWorkPlanService.deleteQqchWorkPlan(qqchWorkPlanParam));
    }

    /**
     * 监听器
     */
    @PostMapping("/listener")
    @ResponseBody
    public AjaxResult listener(@RequestBody Map<String, Object> map) {
//        DelegateTask delegateTask = JSONObject.parseObject(JSONObject.toJSONString(map.get("execution")),DelegateTask.class);
//        Map varMap = delegateTask.getVariables();
//        String businessId = (String)varMap.get("businessId");
//        xcsbCheckEquInfoXzAndZlService.listener(Long.parseLong(businessId));
        //xcsbCheckEquInfoXzAndZlService.listener(Long.parseLong((String)map.get("businessId")));
        return AjaxResult.success("成功");
    }

    @PreAuthorize(hasPermi = "qqchWorkPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchWorkPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchWorkPlan> qqchWorkPlanListParam) {
        return toAjax(qqchWorkPlanService.updateQqchWorkPlanList(qqchWorkPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchWorkPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchWorkPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchWorkPlanPkList = Arrays.asList(ids);
        return toAjax(qqchWorkPlanService.deleteQqchWorkPlanByPks(qqchWorkPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchWorkPlan qqchWorkPlanParam) throws IOException {
        List<QqchWorkPlan> qqchWorkPlanList = qqchWorkPlanService.getQqchWorkPlanList(qqchWorkPlanParam);
        ExcelUtils<QqchWorkPlan> util = new ExcelUtils<>(QqchWorkPlan.class);
        util.exportExcel(response, qqchWorkPlanList, DateUtils.getDate());
    }
    @PreAuthorize(hasPermi = "qqchWorkPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchWorkPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchWorkPlan> qqchWorkPlanListParam) {
        qqchWorkPlanService.insertQqchWorkPlanList(qqchWorkPlanListParam);
        return AjaxResult.success(qqchWorkPlanListParam);
    }

    /**
     * 根据租户标识获取其下工作计划
     * @param qqchWorkPlanParam
     * @return
     */
    @GetMapping("/gmList")
    public AjaxResult gmList(@Validated(ValidationGroups.Select.class) QqchWorkPlan qqchWorkPlanParam) {
        List<QqchWorkPlan> qqchWorkPlanList = qqchWorkPlanService.planListByTenantKey(qqchWorkPlanParam);
        return getDataTableAjaxResult(qqchWorkPlanList);
    }
}
