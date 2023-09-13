package com.hhwy.pm.jdgl.diff.analysis.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysis;
import com.hhwy.pm.jdgl.diff.analysis.domain.vo.DiffAnalysisQueryVo;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 陈锦豪
 * @date 2023-08-28 15:06:24
 * @remark 差异化分析
 */
@Validated
@RestController
@RequestMapping("/jdglDiffAnalysis")
public class JdglDiffAnalysisController extends BaseController {

    @Autowired
    private IJdglDiffAnalysisService jdglDiffAnalysisService;


    @PreAuthorize(hasPermi = "jdglDiffAnalysis:list")
    @GetMapping
    public AjaxResult getJdglDiffAnalysis(@Validated(ValidationGroups.Get.class) JdglDiffAnalysis jdglDiffAnalysisParam) {
        JdglDiffAnalysis jdglDiffAnalysis = jdglDiffAnalysisService.getJdglDiffAnalysis(jdglDiffAnalysisParam);
        return AjaxResult.success(jdglDiffAnalysis);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysis:list")
    @GetMapping("/list")
    public AjaxResult getJdglDiffAnalysisList(@Validated(ValidationGroups.Select.class) JdglDiffAnalysis jdglDiffAnalysisParam) {
        startPage();
        List<JdglDiffAnalysis> jdglDiffAnalysisList = jdglDiffAnalysisService.getJdglDiffAnalysisList(jdglDiffAnalysisParam);
        return getDataTableAjaxResult(jdglDiffAnalysisList);
    }

    /**
     * 查询租户下的数据
     * @param queryVo
     * @return
     */
    @PostMapping("/gmList")
    public AjaxResult gmList(@RequestBody DiffAnalysisQueryVo queryVo) {
        List<JdglDiffAnalysis> jdglDiffAnalysisList = jdglDiffAnalysisService.gmList(queryVo);
        return AjaxResult.success(jdglDiffAnalysisList);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysis:add")
    @PostMapping("/add")
    public AjaxResult insertJdglDiffAnalysis(@Validated(ValidationGroups.Save.class) @RequestBody JdglDiffAnalysis jdglDiffAnalysisParam) {
        jdglDiffAnalysisService.insertJdglDiffAnalysis(jdglDiffAnalysisParam);
        return AjaxResult.success(jdglDiffAnalysisParam);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysis:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglDiffAnalysisList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglDiffAnalysis> jdglDiffAnalysisListParam) {
        jdglDiffAnalysisService.insertJdglDiffAnalysisList(jdglDiffAnalysisListParam);
        return AjaxResult.success(jdglDiffAnalysisListParam);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysis:update")
    @PostMapping("/update")
    public AjaxResult updateJdglDiffAnalysis(@Validated(ValidationGroups.Update.class) @RequestBody JdglDiffAnalysis jdglDiffAnalysisParam) {
        return toAjax(jdglDiffAnalysisService.updateJdglDiffAnalysis(jdglDiffAnalysisParam));
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysis:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglDiffAnalysisList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglDiffAnalysis> jdglDiffAnalysisListParam) {
        return toAjax(jdglDiffAnalysisService.updateJdglDiffAnalysisList(jdglDiffAnalysisListParam));
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysis:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglDiffAnalysis(@Validated(ValidationGroups.Delete.class) @RequestBody JdglDiffAnalysis jdglDiffAnalysisParam) {
        return toAjax(jdglDiffAnalysisService.deleteJdglDiffAnalysis(jdglDiffAnalysisParam));
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysis:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglDiffAnalysisByPks(@PathVariable Long[] ids) {
        List<Long> jdglDiffAnalysisPkList = Arrays.asList(ids);
        return toAjax(jdglDiffAnalysisService.deleteJdglDiffAnalysisByPks(jdglDiffAnalysisPkList));
    }

    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response, JdglDiffAnalysis jdglDiffAnalysisParam) throws IOException {
        List<JdglDiffAnalysis> jdglDiffAnalysisList = jdglDiffAnalysisService.getJdglDiffAnalysisList(jdglDiffAnalysisParam);
        ExcelUtils<JdglDiffAnalysis> util = new ExcelUtils<>(JdglDiffAnalysis.class);
        util.exportExcel(response, jdglDiffAnalysisList, DateUtils.getDate());
    }
}
