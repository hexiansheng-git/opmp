package com.hhwy.pm.jdgl.diff.analysis.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglDiffAnalysisSv;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglDiffAnalysisSvService;
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
 * @date 2023-08-28 16:24:27
 * @remark 差异化分析-sv偏差分析
 */
@Validated
@RestController
@RequestMapping("/jdglDiffAnalysisSv")
public class JdglDiffAnalysisSvController extends BaseController {

    @Autowired
    private IJdglDiffAnalysisSvService jdglDiffAnalysisSvService;


    //  @PreAuthorize(hasPermi = "jdglDiffAnalysisSv:list")
    @GetMapping
    public AjaxResult getJdglDiffAnalysisSv(@Validated(ValidationGroups.Get.class) JdglDiffAnalysisSv jdglDiffAnalysisSvParam) {
        JdglDiffAnalysisSv jdglDiffAnalysisSv = jdglDiffAnalysisSvService.getJdglDiffAnalysisSv(jdglDiffAnalysisSvParam);
        return AjaxResult.success(jdglDiffAnalysisSv);
    }

    //  @PreAuthorize(hasPermi = "jdglDiffAnalysisSv:list")
    @GetMapping("/list")
    public AjaxResult getJdglDiffAnalysisSvList(@Validated(ValidationGroups.Select.class) JdglDiffAnalysisSv jdglDiffAnalysisSvParam) {
        List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList = jdglDiffAnalysisSvService.getJdglDiffAnalysisSvList(jdglDiffAnalysisSvParam);
        return getDataTableAjaxResult(jdglDiffAnalysisSvList);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisSv:add")
    @PostMapping("/add")
    public AjaxResult insertJdglDiffAnalysisSv(@Validated(ValidationGroups.Save.class) @RequestBody JdglDiffAnalysisSv jdglDiffAnalysisSvParam) {
        jdglDiffAnalysisSvService.insertJdglDiffAnalysisSv(jdglDiffAnalysisSvParam);
        return AjaxResult.success(jdglDiffAnalysisSvParam);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisSv:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglDiffAnalysisSvList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglDiffAnalysisSv> jdglDiffAnalysisSvListParam) {
        jdglDiffAnalysisSvService.insertJdglDiffAnalysisSvList(jdglDiffAnalysisSvListParam);
        return AjaxResult.success(jdglDiffAnalysisSvListParam);
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisSv:update")
    @PostMapping("/update")
    public AjaxResult updateJdglDiffAnalysisSv(@Validated(ValidationGroups.Update.class) @RequestBody JdglDiffAnalysisSv jdglDiffAnalysisSvParam) {
        return toAjax(jdglDiffAnalysisSvService.updateJdglDiffAnalysisSv(jdglDiffAnalysisSvParam));
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisSv:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglDiffAnalysisSvList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglDiffAnalysisSv> jdglDiffAnalysisSvListParam) {
        return toAjax(jdglDiffAnalysisSvService.updateJdglDiffAnalysisSvList(jdglDiffAnalysisSvListParam));
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisSv:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglDiffAnalysisSv(@Validated(ValidationGroups.Delete.class) @RequestBody JdglDiffAnalysisSv jdglDiffAnalysisSvParam) {
        return toAjax(jdglDiffAnalysisSvService.deleteJdglDiffAnalysisSv(jdglDiffAnalysisSvParam));
    }

    @PreAuthorize(hasPermi = "jdglDiffAnalysisSv:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglDiffAnalysisSvByPks(@PathVariable Long[] ids) {
        List<Long> jdglDiffAnalysisSvPkList = Arrays.asList(ids);
        return toAjax(jdglDiffAnalysisSvService.deleteJdglDiffAnalysisSvByPks(jdglDiffAnalysisSvPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglDiffAnalysisSv jdglDiffAnalysisSvParam) throws IOException {
        List<JdglDiffAnalysisSv> jdglDiffAnalysisSvList = jdglDiffAnalysisSvService.getJdglDiffAnalysisSvList(jdglDiffAnalysisSvParam);
        ExcelUtils<JdglDiffAnalysisSv> util = new ExcelUtils<>(JdglDiffAnalysisSv.class);
        util.exportExcel(response, jdglDiffAnalysisSvList, DateUtils.getDate());
    }

    //  @PreAuthorize(hasPermi = "jdglDiffAnalysisSv:list")
    @GetMapping("/getPlanAndComp")
    public AjaxResult getPlanAndComp(JdglDiffAnalysisSv jdglDiffAnalysisSvParam) {
        return AjaxResult.success(jdglDiffAnalysisSvService.getPlanAndComp(jdglDiffAnalysisSvParam));
    }
}
