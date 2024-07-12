package com.hhwy.sp.techManagement.sgjsPaperScore.controller;

import java.util.ArrayList;
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
import com.hhwy.sp.techManagement.sgjsPaperScore.service.ISgjsPaperScoreService;
import com.hhwy.sp.techManagement.sgjsPaperScore.domain.SgjsPaperScore;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author fsd
 * @date 2024-07-10 16:38:08
 * @remark 论文评分
 */
@Validated
@RestController
@RequestMapping("/sgjsPaperScore")
public class SgjsPaperScoreController extends BaseController {

    @Autowired
    private ISgjsPaperScoreService sgjsPaperScoreService;


    @PreAuthorize(hasPermi = "sgjsPaperScore:list")
    @GetMapping
    public AjaxResult getSgjsPaperScore(@Validated(ValidationGroups.Get.class) SgjsPaperScore sgjsPaperScoreParam) {
        SgjsPaperScore sgjsPaperScore = sgjsPaperScoreService.getSgjsPaperScore(sgjsPaperScoreParam);
        return AjaxResult.success(sgjsPaperScore);
    }

    //台账
    @PreAuthorize(hasPermi = "sgjsPaperScore:list")
    @GetMapping("/list")
    public AjaxResult getSgjsPaperScoreList(@Validated(ValidationGroups.Select.class) SgjsPaperScore sgjsPaperScoreParam) {
        List<SgjsPaperScore> sgjsPaperScoreList = sgjsPaperScoreService.getSgjsPaperScoreList(sgjsPaperScoreParam);
        return AjaxResult.success(sgjsPaperScoreList);
    }

    //发起评审
    @PreAuthorize(hasPermi = "sgjsPaperScore:update")
    @PostMapping("/reviewStart")
    public AjaxResult reviewStart(Long[] ids) {
        sgjsPaperScoreService.reviewStart(ids);
        return AjaxResult.success();
    }

    //结束流程
    @PreAuthorize(hasPermi = "sgjsPaperScore:update")
    @PostMapping("/reviewEnd")
    public AjaxResult reviewEnd(@RequestBody Long[] ids) {
        sgjsPaperScoreService.reviewEnd(ids);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgjsPaperScore:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsPaperScore(@Validated(ValidationGroups.Save.class) @RequestBody SgjsPaperScore sgjsPaperScoreParam) {
        sgjsPaperScoreService.insertSgjsPaperScore(sgjsPaperScoreParam);
        return AjaxResult.success(sgjsPaperScoreParam);
    }

    @PreAuthorize(hasPermi = "sgjsPaperScore:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsPaperScoreList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsPaperScore> sgjsPaperScoreListParam) {
        sgjsPaperScoreService.insertSgjsPaperScoreList(sgjsPaperScoreListParam);
        return AjaxResult.success(sgjsPaperScoreListParam);
    }

    @PreAuthorize(hasPermi = "sgjsPaperScore:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsPaperScore(@Validated(ValidationGroups.Update.class) @RequestBody SgjsPaperScore sgjsPaperScoreParam) {
        return toAjax(sgjsPaperScoreService.updateSgjsPaperScore(sgjsPaperScoreParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperScore:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsPaperScoreList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsPaperScore> sgjsPaperScoreListParam) {
        return toAjax(sgjsPaperScoreService.updateSgjsPaperScoreList(sgjsPaperScoreListParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperScore:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsPaperScore(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsPaperScore sgjsPaperScoreParam) {
        return toAjax(sgjsPaperScoreService.deleteSgjsPaperScore(sgjsPaperScoreParam));
    }

    @PreAuthorize(hasPermi = "sgjsPaperScore:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsPaperScoreByPks(@PathVariable Long[] ids) {
        List<Long> sgjsPaperScorePkList = Arrays.asList(ids);
        return toAjax(sgjsPaperScoreService.deleteSgjsPaperScoreByPks(sgjsPaperScorePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsPaperScore sgjsPaperScoreParam) throws IOException {
        List<SgjsPaperScore> sgjsPaperScoreList = sgjsPaperScoreService.getSgjsPaperScoreList(sgjsPaperScoreParam);
        ExcelUtils<SgjsPaperScore> util = new ExcelUtils<>(SgjsPaperScore.class);
        util.exportExcel(response, sgjsPaperScoreList, DateUtils.getDate());
    }
}
