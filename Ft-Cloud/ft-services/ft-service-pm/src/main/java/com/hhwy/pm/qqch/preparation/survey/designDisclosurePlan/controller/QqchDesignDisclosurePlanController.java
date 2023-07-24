package com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.QqchDesignDisclosurePlan;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.service.IQqchDesignDisclosurePlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:47:23
 * @remark 2.4 设计交底策划
 */
@Validated
@RestController
@RequestMapping("/qqchDesignDisclosurePlan")
public class QqchDesignDisclosurePlanController extends BaseController {

    @Autowired
    private IQqchDesignDisclosurePlanService qqchDesignDisclosurePlanService;


    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:list")
    @GetMapping
    public AjaxResult getQqchDesignDisclosurePlan(@Validated(ValidationGroups.Get.class) QqchDesignDisclosurePlan qqchDesignDisclosurePlanParam) {
        QqchDesignDisclosurePlan qqchDesignDisclosurePlan = qqchDesignDisclosurePlanService.getQqchDesignDisclosurePlan(qqchDesignDisclosurePlanParam);
        return AjaxResult.success(qqchDesignDisclosurePlan);
    }

    /**
     * 列表接口
     *
     * @param qqchDesignDisclosurePlanParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchDesignDisclosurePlanList(@Validated(ValidationGroups.Select.class) QqchDesignDisclosurePlan qqchDesignDisclosurePlanParam) {
       // startPage();
        List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList = qqchDesignDisclosurePlanService.getQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanParam);
        return getDataTableAjaxResult(qqchDesignDisclosurePlanList);
    }

    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDesignDisclosurePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchDesignDisclosurePlan qqchDesignDisclosurePlanParam) {
        qqchDesignDisclosurePlanService.insertQqchDesignDisclosurePlan(qqchDesignDisclosurePlanParam);
        return AjaxResult.success(qqchDesignDisclosurePlanParam);
    }

    /**
     *  新增，修改接口
     *
     * @param qqchDesignDisclosurePlanListParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDesignDisclosurePlanList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanListParam) {
        qqchDesignDisclosurePlanService.insertQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanListParam);
        return AjaxResult.success(qqchDesignDisclosurePlanListParam);
    }

    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDesignDisclosurePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchDesignDisclosurePlan qqchDesignDisclosurePlanParam) {
        return toAjax(qqchDesignDisclosurePlanService.updateQqchDesignDisclosurePlan(qqchDesignDisclosurePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchDesignDisclosurePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanListParam) {
        return toAjax(qqchDesignDisclosurePlanService.updateQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDesignDisclosurePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchDesignDisclosurePlan qqchDesignDisclosurePlanParam) {
        return toAjax(qqchDesignDisclosurePlanService.deleteQqchDesignDisclosurePlan(qqchDesignDisclosurePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchDesignDisclosurePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchDesignDisclosurePlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchDesignDisclosurePlanPkList = Arrays.asList(ids);
        return toAjax(qqchDesignDisclosurePlanService.deleteQqchDesignDisclosurePlanByPks(qqchDesignDisclosurePlanPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDesignDisclosurePlan qqchDesignDisclosurePlanParam) throws IOException {
        List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList = qqchDesignDisclosurePlanService.getQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanParam);
        ExcelUtils<QqchDesignDisclosurePlan> util = new ExcelUtils<>(QqchDesignDisclosurePlan.class);
        util.exportExcel(response, qqchDesignDisclosurePlanList, DateUtils.getDate());
    }
}
