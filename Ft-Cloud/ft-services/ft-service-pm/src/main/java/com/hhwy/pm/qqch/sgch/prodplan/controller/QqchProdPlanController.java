package com.hhwy.pm.qqch.sgch.prodplan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.prodplan.domain.QqchProdPlan;
import com.hhwy.pm.qqch.sgch.prodplan.service.IQqchProdPlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-17 16:20:08
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchProdPlan")
public class QqchProdPlanController extends BaseController {

    @Autowired
    private IQqchProdPlanService qqchProdPlanService;


    @PreAuthorize(hasPermi = "qqchProdPlan:list")
    @GetMapping
    public AjaxResult getQqchProdPlan(@Validated(ValidationGroups.Get.class) QqchProdPlan qqchProdPlanParam) {
        QqchProdPlan qqchProdPlan = qqchProdPlanService.getQqchProdPlan(qqchProdPlanParam);
        return AjaxResult.success(qqchProdPlan);
    }

    @PreAuthorize(hasPermi = "qqchProdPlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchProdPlanList(@Validated(ValidationGroups.Select.class) QqchProdPlan qqchProdPlanParam) {
        startPage();
        List<QqchProdPlan> qqchProdPlanList = qqchProdPlanService.getQqchProdPlanList(qqchProdPlanParam);
        return getDataTableAjaxResult(qqchProdPlanList);
    }

    @PreAuthorize(hasPermi = "qqchProdPlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchProdPlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchProdPlan qqchProdPlanParam) {
        qqchProdPlanService.insertQqchProdPlan(qqchProdPlanParam);
        return AjaxResult.success(qqchProdPlanParam);
    }

    @PreAuthorize(hasPermi = "qqchProdPlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchProdPlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchProdPlan> qqchProdPlanListParam) {
        qqchProdPlanService.insertQqchProdPlanList(qqchProdPlanListParam);
        return AjaxResult.success(qqchProdPlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchProdPlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchProdPlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchProdPlan qqchProdPlanParam) {
        return toAjax(qqchProdPlanService.updateQqchProdPlan(qqchProdPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchProdPlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchProdPlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchProdPlan> qqchProdPlanListParam) {
        return toAjax(qqchProdPlanService.updateQqchProdPlanList(qqchProdPlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchProdPlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchProdPlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchProdPlan qqchProdPlanParam) {
        return toAjax(qqchProdPlanService.deleteQqchProdPlan(qqchProdPlanParam));
    }

    @PreAuthorize(hasPermi = "qqchProdPlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchProdPlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchProdPlanPkList = Arrays.asList(ids);
        return toAjax(qqchProdPlanService.deleteQqchProdPlanByPks(qqchProdPlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchProdPlan qqchProdPlanParam) throws IOException {
        List<QqchProdPlan> qqchProdPlanList = qqchProdPlanService.getQqchProdPlanList(qqchProdPlanParam);
        ExcelUtils<QqchProdPlan> util = new ExcelUtils<>(QqchProdPlan.class);
        util.exportExcel(response, qqchProdPlanList, DateUtils.getDate());
    }


    @PreAuthorize(hasPermi = "qqchProdPlan:list")
    @GetMapping("/getList")
    public AjaxResult getList(@Validated(ValidationGroups.Select.class) QqchProdPlan dto) {
        CompileEntity<List<QqchProdPlan>> qqchProdPlanList = qqchProdPlanService.getList(dto);
        return AjaxResult.success(qqchProdPlanList);
    }
}
