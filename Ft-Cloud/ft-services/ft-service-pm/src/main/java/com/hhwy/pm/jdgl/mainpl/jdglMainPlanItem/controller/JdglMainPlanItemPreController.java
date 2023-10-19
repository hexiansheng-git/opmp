package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItemPre;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemPreService;
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
 * @author cjh
 * @date 2023-09-19 11:49:57
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglMainPlanItemPre")
public class JdglMainPlanItemPreController extends BaseController {

    @Autowired
    private IJdglMainPlanItemPreService jdglMainPlanItemPreService;


    //  @PreAuthorize(hasPermi = "jdglMainPlanItemPre:list")
    @GetMapping
    public AjaxResult getJdglMainPlanItemPre(@Validated(ValidationGroups.Get.class) JdglMainPlanItemPre jdglMainPlanItemPreParam) {
        JdglMainPlanItemPre jdglMainPlanItemPre = jdglMainPlanItemPreService.getJdglMainPlanItemPre(jdglMainPlanItemPreParam);
        return AjaxResult.success(jdglMainPlanItemPre);
    }

    //  @PreAuthorize(hasPermi = "jdglMainPlanItemPre:list")
    @GetMapping("/list")
    public AjaxResult getJdglMainPlanItemPreList(@Validated(ValidationGroups.Select.class) JdglMainPlanItemPre jdglMainPlanItemPreParam) {
        startPage();
        List<JdglMainPlanItemPre> jdglMainPlanItemPreList = jdglMainPlanItemPreService.getJdglMainPlanItemPreList(jdglMainPlanItemPreParam);
        return getDataTableAjaxResult(jdglMainPlanItemPreList);
    }

    @PreAuthorize(hasPermi = "jdglMainPlanItemPre:add")
    @PostMapping("/add")
    public AjaxResult insertJdglMainPlanItemPre(@Validated(ValidationGroups.Save.class) @RequestBody JdglMainPlanItemPre jdglMainPlanItemPreParam) {
        jdglMainPlanItemPreService.insertJdglMainPlanItemPre(jdglMainPlanItemPreParam);
        return AjaxResult.success(jdglMainPlanItemPreParam);
    }

    @PreAuthorize(hasPermi = "jdglMainPlanItemPre:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglMainPlanItemPreList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglMainPlanItemPre> jdglMainPlanItemPreListParam) {
        jdglMainPlanItemPreService.insertJdglMainPlanItemPreList(jdglMainPlanItemPreListParam);
        return AjaxResult.success(jdglMainPlanItemPreListParam);
    }

    @PreAuthorize(hasPermi = "jdglMainPlanItemPre:update")
    @PostMapping("/update")
    public AjaxResult updateJdglMainPlanItemPre(@Validated(ValidationGroups.Update.class) @RequestBody JdglMainPlanItemPre jdglMainPlanItemPreParam) {
        return toAjax(jdglMainPlanItemPreService.updateJdglMainPlanItemPre(jdglMainPlanItemPreParam));
    }

    @PreAuthorize(hasPermi = "jdglMainPlanItemPre:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglMainPlanItemPreList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglMainPlanItemPre> jdglMainPlanItemPreListParam) {
        return toAjax(jdglMainPlanItemPreService.updateJdglMainPlanItemPreList(jdglMainPlanItemPreListParam));
    }

    @PreAuthorize(hasPermi = "jdglMainPlanItemPre:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglMainPlanItemPre(@Validated(ValidationGroups.Delete.class) @RequestBody JdglMainPlanItemPre jdglMainPlanItemPreParam) {
        return toAjax(jdglMainPlanItemPreService.deleteJdglMainPlanItemPre(jdglMainPlanItemPreParam));
    }

    @PreAuthorize(hasPermi = "jdglMainPlanItemPre:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglMainPlanItemPreByPks(@PathVariable Long[] ids) {
        List<Long> jdglMainPlanItemPrePkList = Arrays.asList(ids);
        return toAjax(jdglMainPlanItemPreService.deleteJdglMainPlanItemPreByPks(jdglMainPlanItemPrePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglMainPlanItemPre jdglMainPlanItemPreParam) throws IOException {
        List<JdglMainPlanItemPre> jdglMainPlanItemPreList = jdglMainPlanItemPreService.getJdglMainPlanItemPreList(jdglMainPlanItemPreParam);
        ExcelUtils<JdglMainPlanItemPre> util = new ExcelUtils<>(JdglMainPlanItemPre.class);
        util.exportExcel(response, jdglMainPlanItemPreList, DateUtils.getDate());
    }
}
