package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.service.ISgjsTechnicalNormalTopicCostService;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/***
 * 功能描述: 科技管理 - 一般课题研发管理 - 年费用
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Validated
@RestController
@RequestMapping("/sgjsTechnicalNormalTopicCost")
public class SgjsTechnicalNormalTopicCostController extends BaseController {

    @Autowired
    private ISgjsTechnicalNormalTopicCostService sgjsTechnicalNormalTopicCostService;


    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopicCost:list")
    @GetMapping
    public AjaxResult getSgjsTechnicalNormalTopicCost(@Validated(ValidationGroups.Get.class) SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCostParam) {
        SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost = sgjsTechnicalNormalTopicCostService.getSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCostParam);
        return AjaxResult.success(sgjsTechnicalNormalTopicCost);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopicCost:list")
    @GetMapping("/list")
    public AjaxResult getSgjsTechnicalNormalTopicCostList(@Validated(ValidationGroups.Select.class) SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCostParam) {
        startPage();
        List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostList = sgjsTechnicalNormalTopicCostService.getSgjsTechnicalNormalTopicCostList(sgjsTechnicalNormalTopicCostParam);
        return getDataTableAjaxResult(sgjsTechnicalNormalTopicCostList);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopicCost:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechnicalNormalTopicCost(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCostParam) {
        sgjsTechnicalNormalTopicCostService.insertSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCostParam);
        return AjaxResult.success(sgjsTechnicalNormalTopicCostParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopicCost:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechnicalNormalTopicCostList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostListParam) {
        sgjsTechnicalNormalTopicCostService.insertSgjsTechnicalNormalTopicCostList(sgjsTechnicalNormalTopicCostListParam);
        return AjaxResult.success(sgjsTechnicalNormalTopicCostListParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopicCost:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsTechnicalNormalTopicCost(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCostParam) {
        return toAjax(sgjsTechnicalNormalTopicCostService.updateSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCostParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopicCost:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechnicalNormalTopicCostList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostListParam) {
        return toAjax(sgjsTechnicalNormalTopicCostService.updateSgjsTechnicalNormalTopicCostList(sgjsTechnicalNormalTopicCostListParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopicCost:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechnicalNormalTopicCost(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCostParam) {
        return toAjax(sgjsTechnicalNormalTopicCostService.deleteSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCostParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopicCost:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsTechnicalNormalTopicCostByPks(@PathVariable Long[] ids) {
        List<Long> sgjsTechnicalNormalTopicCostPkList = Arrays.asList(ids);
        return toAjax(sgjsTechnicalNormalTopicCostService.deleteSgjsTechnicalNormalTopicCostByPks(sgjsTechnicalNormalTopicCostPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCostParam) throws IOException {
        List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCostList = sgjsTechnicalNormalTopicCostService.getSgjsTechnicalNormalTopicCostList(sgjsTechnicalNormalTopicCostParam);
        ExcelUtils<SgjsTechnicalNormalTopicCost> util = new ExcelUtils<>(SgjsTechnicalNormalTopicCost.class);
        util.exportExcel(response, sgjsTechnicalNormalTopicCostList, DateUtils.getDate());
    }
}
