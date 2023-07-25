package com.hhwy.pm.qqch.preparation.technique.techManagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchPatentDeclarePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchPatentDeclarePlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:40:39
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchPatentDeclarePlan")
public class QqchPatentDeclarePlanController extends BaseController {

    @Autowired
    private IQqchPatentDeclarePlanService qqchPatentDeclarePlanService;


    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:list")
    @GetMapping
    public AjaxResult getQqchPatentDeclarePlan(@Validated(ValidationGroups.Get.class) QqchPatentDeclarePlan qqchPatentDeclarePlanParam) {
        QqchPatentDeclarePlan qqchPatentDeclarePlan = qqchPatentDeclarePlanService.getQqchPatentDeclarePlan(qqchPatentDeclarePlanParam);
        return AjaxResult.success(qqchPatentDeclarePlan);
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchPatentDeclarePlanList(@Validated(ValidationGroups.Select.class) QqchPatentDeclarePlan qqchPatentDeclarePlanParam) {
        startPage();
        List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList = qqchPatentDeclarePlanService.getQqchPatentDeclarePlanList(qqchPatentDeclarePlanParam);
        return getDataTableAjaxResult(qqchPatentDeclarePlanList);
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchPatentDeclarePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchPatentDeclarePlan qqchPatentDeclarePlanParam) {
        qqchPatentDeclarePlanService.insertQqchPatentDeclarePlan(qqchPatentDeclarePlanParam);
        return AjaxResult.success(qqchPatentDeclarePlanParam);
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchPatentDeclarePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchPatentDeclarePlan> qqchPatentDeclarePlanListParam) {
        qqchPatentDeclarePlanService.insertQqchPatentDeclarePlanList(qqchPatentDeclarePlanListParam);
        return AjaxResult.success(qqchPatentDeclarePlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchPatentDeclarePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchPatentDeclarePlan qqchPatentDeclarePlanParam) {
        return toAjax(qqchPatentDeclarePlanService.updateQqchPatentDeclarePlan(qqchPatentDeclarePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchPatentDeclarePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchPatentDeclarePlan> qqchPatentDeclarePlanListParam) {
        return toAjax(qqchPatentDeclarePlanService.updateQqchPatentDeclarePlanList(qqchPatentDeclarePlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchPatentDeclarePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchPatentDeclarePlan qqchPatentDeclarePlanParam) {
        return toAjax(qqchPatentDeclarePlanService.deleteQqchPatentDeclarePlan(qqchPatentDeclarePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchPatentDeclarePlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchPatentDeclarePlanPkList = Arrays.asList(ids);
        return toAjax(qqchPatentDeclarePlanService.deleteQqchPatentDeclarePlanByPks(qqchPatentDeclarePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchPatentDeclarePlan qqchPatentDeclarePlanParam) throws IOException {
        List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList = qqchPatentDeclarePlanService.getQqchPatentDeclarePlanList(qqchPatentDeclarePlanParam);
        ExcelUtils<QqchPatentDeclarePlan> util = new ExcelUtils<>(QqchPatentDeclarePlan.class);
        util.exportExcel(response, qqchPatentDeclarePlanList, DateUtils.getDate());
    }
}
