package com.hhwy.pm.jdgl.diff.analysis.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.Map;

import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisCorrect;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisCorrectService;
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
 * @author 陈锦豪
 * @date 2023-08-28 16:24:13
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglDiffAnalysisCorrect")
public class JdglDiffAnalysisCorrectController extends BaseController {

    @Autowired
    private IJdglDiffAnalysisCorrectService jdglDiffAnalysisCorrectService;


    @PreAuthorize(hasPermi = "jdglDiffAnalysisCorrect:list")
    @GetMapping
    public AjaxResult getJdglDiffAnalysisCorrect(@Validated(ValidationGroups.Get.class) JdglDiffAnalysisCorrect jdglDiffAnalysisCorrectParam) {
        JdglDiffAnalysisCorrect jdglDiffAnalysisCorrect = jdglDiffAnalysisCorrectService.getJdglDiffAnalysisCorrect(jdglDiffAnalysisCorrectParam);
        return AjaxResult.success(jdglDiffAnalysisCorrect);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisCorrect:list")
    @GetMapping("/getInit")
    public AjaxResult getInitDiffAnalysisCorrect(@Validated(ValidationGroups.Get.class) JdglDiffAnalysisCorrect jdglDiffAnalysisCorrectParam) {
        Map<String, List<JdglDiffAnalysisCorrect>> jdglDiffAnalysisCorrectList = jdglDiffAnalysisCorrectService.getInitDiffAnalysisCorrect(jdglDiffAnalysisCorrectParam);
        return AjaxResult.success(jdglDiffAnalysisCorrectList);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisCorrect:list")
    @GetMapping("/list")
    public AjaxResult getJdglDiffAnalysisCorrectList(@Validated(ValidationGroups.Select.class) JdglDiffAnalysisCorrect jdglDiffAnalysisCorrectParam) {
        startPage();
        List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList = jdglDiffAnalysisCorrectService.getJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrectParam);
        return getDataTableAjaxResult(jdglDiffAnalysisCorrectList);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisCorrect:list")
    @GetMapping("/maplist")
    public AjaxResult getJdglDiffAnalysisCorrectMapList(@Validated(ValidationGroups.Select.class) JdglDiffAnalysisCorrect jdglDiffAnalysisCorrectParam) {
        Map<String, List<JdglDiffAnalysisCorrect>> jdglDiffAnalysisCorrectList = jdglDiffAnalysisCorrectService.getJdglDiffAnalysisCorrectMapList(jdglDiffAnalysisCorrectParam);
        return AjaxResult.success(jdglDiffAnalysisCorrectList);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisCorrect:add")
    @PostMapping("/add")
    public AjaxResult insertJdglDiffAnalysisCorrect(@Validated(ValidationGroups.Save.class) @RequestBody JdglDiffAnalysisCorrect jdglDiffAnalysisCorrectParam) {
        jdglDiffAnalysisCorrectService.insertJdglDiffAnalysisCorrect(jdglDiffAnalysisCorrectParam);
        return AjaxResult.success(jdglDiffAnalysisCorrectParam);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisCorrect:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglDiffAnalysisCorrectList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectListParam) {
        jdglDiffAnalysisCorrectService.insertJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrectListParam);
        return AjaxResult.success(jdglDiffAnalysisCorrectListParam);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisCorrect:update")
    @PostMapping("/update")
    public AjaxResult updateJdglDiffAnalysisCorrect(@Validated(ValidationGroups.Update.class) @RequestBody JdglDiffAnalysisCorrect jdglDiffAnalysisCorrectParam) {
        return toAjax(jdglDiffAnalysisCorrectService.updateJdglDiffAnalysisCorrect(jdglDiffAnalysisCorrectParam));
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisCorrect:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglDiffAnalysisCorrectList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectListParam) {
        return toAjax(jdglDiffAnalysisCorrectService.updateJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrectListParam));
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisCorrect:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglDiffAnalysisCorrect(@Validated(ValidationGroups.Delete.class) @RequestBody JdglDiffAnalysisCorrect jdglDiffAnalysisCorrectParam) {
        return toAjax(jdglDiffAnalysisCorrectService.deleteJdglDiffAnalysisCorrect(jdglDiffAnalysisCorrectParam));
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisCorrect:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglDiffAnalysisCorrectByPks(@PathVariable Long[] ids) {
        List<Long> jdglDiffAnalysisCorrectPkList = Arrays.asList(ids);
        return toAjax(jdglDiffAnalysisCorrectService.deleteJdglDiffAnalysisCorrectByPks(jdglDiffAnalysisCorrectPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglDiffAnalysisCorrect jdglDiffAnalysisCorrectParam) throws IOException {
        List<JdglDiffAnalysisCorrect> jdglDiffAnalysisCorrectList = jdglDiffAnalysisCorrectService.getJdglDiffAnalysisCorrectList(jdglDiffAnalysisCorrectParam);
        ExcelUtils<JdglDiffAnalysisCorrect> util = new ExcelUtils<>(JdglDiffAnalysisCorrect.class);
        util.exportExcel(response, jdglDiffAnalysisCorrectList, DateUtils.getDate());
    }
}
