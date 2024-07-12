package com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.domain.SgjsPaperScoreStandard;
import com.hhwy.sp.techManagement.sgjsPaperScore.sgjsPaperScoreStandard.service.ISgjsPaperScoreStandardService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author fsd
 * @date 2024-07-10 16:38:46
 * @remark 评分标准
 */
@Validated
@RestController
@RequestMapping("/sgjsPaperScoreStandard")
public class SgjsPaperScoreStandardController extends BaseController {

    @Autowired
    private ISgjsPaperScoreStandardService sgjsPaperScoreStandardService;


    @PreAuthorize(hasPermi = "sgjsPaperScoreStandard:list")
    @GetMapping
    public AjaxResult getSgjsPaperScoreStandard(@Validated(ValidationGroups.Get.class) SgjsPaperScoreStandard sgjsPaperScoreStandardParam) {
        SgjsPaperScoreStandard sgjsPaperScoreStandard = sgjsPaperScoreStandardService.getSgjsPaperScoreStandard(sgjsPaperScoreStandardParam);
        return AjaxResult.success(sgjsPaperScoreStandard);
    }

    //查询
    @PreAuthorize(hasPermi = "sgjsPaperScoreStandard:list")
    @GetMapping("/list")
    public AjaxResult getSgjsPaperScoreStandardList(@Validated(ValidationGroups.Select.class) SgjsPaperScoreStandard sgjsPaperScoreStandardParam) {
        List<SgjsPaperScoreStandard> sgjsPaperScoreStandardList = sgjsPaperScoreStandardService.getSgjsPaperScoreStandardList(sgjsPaperScoreStandardParam);
        return AjaxResult.success(sgjsPaperScoreStandardList);
    }

    @PreAuthorize(hasPermi = "sgjsPaperScoreStandard:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsPaperScoreStandard(@Validated(ValidationGroups.Save.class) @RequestBody SgjsPaperScoreStandard sgjsPaperScoreStandardParam) {
        sgjsPaperScoreStandardService.insertSgjsPaperScoreStandard(sgjsPaperScoreStandardParam);
        return AjaxResult.success(sgjsPaperScoreStandardParam);
    }

    @PreAuthorize(hasPermi = "sgjsPaperScoreStandard:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsPaperScoreStandardList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsPaperScoreStandard> sgjsPaperScoreStandardListParam) {
        sgjsPaperScoreStandardService.insertSgjsPaperScoreStandardList(sgjsPaperScoreStandardListParam);
        return AjaxResult.success(sgjsPaperScoreStandardListParam);
    }

    @PreAuthorize(hasPermi = "sgjsPaperScoreStandard:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsPaperScoreStandard(@Validated(ValidationGroups.Update.class) @RequestBody SgjsPaperScoreStandard sgjsPaperScoreStandardParam) {
        return toAjax(sgjsPaperScoreStandardService.updateSgjsPaperScoreStandard(sgjsPaperScoreStandardParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperScoreStandard:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsPaperScoreStandardList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsPaperScoreStandard> sgjsPaperScoreStandardListParam) {
        return toAjax(sgjsPaperScoreStandardService.updateSgjsPaperScoreStandardList(sgjsPaperScoreStandardListParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperScoreStandard:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsPaperScoreStandard(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsPaperScoreStandard sgjsPaperScoreStandardParam) {
        return toAjax(sgjsPaperScoreStandardService.deleteSgjsPaperScoreStandard(sgjsPaperScoreStandardParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperScoreStandard:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsPaperScoreStandardByPks(@PathVariable Long[] ids) {
        List<Long> sgjsPaperScoreStandardPkList = Arrays.asList(ids);
        return toAjax(sgjsPaperScoreStandardService.deleteSgjsPaperScoreStandardByPks(sgjsPaperScoreStandardPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsPaperScoreStandard sgjsPaperScoreStandardParam) throws IOException {
        List<SgjsPaperScoreStandard> sgjsPaperScoreStandardList = sgjsPaperScoreStandardService.getSgjsPaperScoreStandardList(sgjsPaperScoreStandardParam);
        ExcelUtils<SgjsPaperScoreStandard> util = new ExcelUtils<>(SgjsPaperScoreStandard.class);
        util.exportExcel(response, sgjsPaperScoreStandardList, DateUtils.getDate());
    }
}
