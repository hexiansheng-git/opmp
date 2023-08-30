package com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.controller;

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
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.service.IJdglMainPlanWbsService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanWbs.domain.JdglMainPlanWbs;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:35
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglMainPlanWbs")
public class JdglMainPlanWbsController extends BaseController {

    @Autowired
    private IJdglMainPlanWbsService jdglMainPlanWbsService;


    @PreAuthorize(hasPermi = "jdglMainPlanWbs:list")
    @GetMapping
    public AjaxResult getJdglMainPlanWbs(@Validated(ValidationGroups.Get.class) JdglMainPlanWbs jdglMainPlanWbsParam) {
        JdglMainPlanWbs jdglMainPlanWbs = jdglMainPlanWbsService.getJdglMainPlanWbs(jdglMainPlanWbsParam);
        return AjaxResult.success(jdglMainPlanWbs);
    }

    @PreAuthorize(hasPermi = "jdglMainPlanWbs:list")
    @GetMapping("/list")
    public AjaxResult getJdglMainPlanWbsList(@Validated(ValidationGroups.Select.class) JdglMainPlanWbs jdglMainPlanWbsParam) {
        startPage();
        List<JdglMainPlanWbs> jdglMainPlanWbsList = jdglMainPlanWbsService.getJdglMainPlanWbsList(jdglMainPlanWbsParam);
        return getDataTableAjaxResult(jdglMainPlanWbsList);
    }

    @PreAuthorize(hasPermi = "jdglMainPlanWbs:add")
    @PostMapping("/add")
    public AjaxResult insertJdglMainPlanWbs(@Validated(ValidationGroups.Save.class) @RequestBody JdglMainPlanWbs jdglMainPlanWbsParam) {
        jdglMainPlanWbsService.insertJdglMainPlanWbs(jdglMainPlanWbsParam);
        return AjaxResult.success(jdglMainPlanWbsParam);
    }

    @PreAuthorize(hasPermi = "jdglMainPlanWbs:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglMainPlanWbsList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglMainPlanWbs> jdglMainPlanWbsListParam) {
        jdglMainPlanWbsService.insertJdglMainPlanWbsList(jdglMainPlanWbsListParam);
        return AjaxResult.success(jdglMainPlanWbsListParam);
    }

    @PreAuthorize(hasPermi = "jdglMainPlanWbs:update")
    @PostMapping("/update")
    public AjaxResult updateJdglMainPlanWbs(@Validated(ValidationGroups.Update.class) @RequestBody JdglMainPlanWbs jdglMainPlanWbsParam) {
        return toAjax(jdglMainPlanWbsService.updateJdglMainPlanWbs(jdglMainPlanWbsParam));
    }

    @PreAuthorize(hasPermi = "jdglMainPlanWbs:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglMainPlanWbsList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglMainPlanWbs> jdglMainPlanWbsListParam) {
        return toAjax(jdglMainPlanWbsService.updateJdglMainPlanWbsList(jdglMainPlanWbsListParam));
    }

    @PreAuthorize(hasPermi = "jdglMainPlanWbs:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglMainPlanWbs(@Validated(ValidationGroups.Delete.class) @RequestBody JdglMainPlanWbs jdglMainPlanWbsParam) {
        return toAjax(jdglMainPlanWbsService.deleteJdglMainPlanWbs(jdglMainPlanWbsParam));
    }

    @PreAuthorize(hasPermi = "jdglMainPlanWbs:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglMainPlanWbsByPks(@PathVariable Long[] ids) {
        List<Long> jdglMainPlanWbsPkList = Arrays.asList(ids);
        return toAjax(jdglMainPlanWbsService.deleteJdglMainPlanWbsByPks(jdglMainPlanWbsPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglMainPlanWbs jdglMainPlanWbsParam) throws IOException {
        List<JdglMainPlanWbs> jdglMainPlanWbsList = jdglMainPlanWbsService.getJdglMainPlanWbsList(jdglMainPlanWbsParam);
        ExcelUtils<JdglMainPlanWbs> util = new ExcelUtils<>(JdglMainPlanWbs.class);
        util.exportExcel(response, jdglMainPlanWbsList, DateUtils.getDate());
    }
}
