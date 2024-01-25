package com.hhwy.sp.common.sgjsAchievementAward.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.common.sgjsAchievementAward.domain.SgjsAchievementAward;
import com.hhwy.sp.common.sgjsAchievementAward.service.ISgjsAchievementAwardService;
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
 * @date 2024-01-25 09:45:38
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsAchievementAward")
public class SgjsAchievementAwardController extends BaseController {

    @Autowired
    private ISgjsAchievementAwardService sgjsAchievementAwardService;


    @PreAuthorize(hasPermi = "sgjsAchievementAward:list")
    @GetMapping
    public AjaxResult getSgjsAchievementAward(@Validated(ValidationGroups.Get.class) SgjsAchievementAward sgjsAchievementAwardParam) {
        SgjsAchievementAward sgjsAchievementAward = sgjsAchievementAwardService.getSgjsAchievementAward(sgjsAchievementAwardParam);
        return AjaxResult.success(sgjsAchievementAward);
    }

    @PreAuthorize(hasPermi = "sgjsAchievementAward:list")
    @GetMapping("/list")
    public AjaxResult getSgjsAchievementAwardList(@Validated(ValidationGroups.Select.class) SgjsAchievementAward sgjsAchievementAwardParam) {
        startPage();
        List<SgjsAchievementAward> sgjsAchievementAwardList = sgjsAchievementAwardService.getSgjsAchievementAwardList(sgjsAchievementAwardParam);
        return getDataTableAjaxResult(sgjsAchievementAwardList);
    }

    @PreAuthorize(hasPermi = "sgjsAchievementAward:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsAchievementAward(@Validated(ValidationGroups.Save.class) @RequestBody SgjsAchievementAward sgjsAchievementAwardParam) {
        sgjsAchievementAwardService.insertSgjsAchievementAward(sgjsAchievementAwardParam);
        return AjaxResult.success(sgjsAchievementAwardParam);
    }

    @PreAuthorize(hasPermi = "sgjsAchievementAward:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsAchievementAwardList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsAchievementAward> sgjsAchievementAwardListParam) {
        sgjsAchievementAwardService.insertSgjsAchievementAwardList(sgjsAchievementAwardListParam);
        return AjaxResult.success(sgjsAchievementAwardListParam);
    }

    @PreAuthorize(hasPermi = "sgjsAchievementAward:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsAchievementAward(@Validated(ValidationGroups.Update.class) @RequestBody SgjsAchievementAward sgjsAchievementAwardParam) {
        return toAjax(sgjsAchievementAwardService.updateSgjsAchievementAward(sgjsAchievementAwardParam));
    }

    @PreAuthorize(hasPermi = "sgjsAchievementAward:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsAchievementAwardList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsAchievementAward> sgjsAchievementAwardListParam) {
        return toAjax(sgjsAchievementAwardService.updateSgjsAchievementAwardList(sgjsAchievementAwardListParam));
    }

    @PreAuthorize(hasPermi = "sgjsAchievementAward:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsAchievementAward(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsAchievementAward sgjsAchievementAwardParam) {
        return toAjax(sgjsAchievementAwardService.deleteSgjsAchievementAward(sgjsAchievementAwardParam));
    }

    @PreAuthorize(hasPermi = "sgjsAchievementAward:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsAchievementAwardByPks(@PathVariable Long[] ids) {
        List<Long> sgjsAchievementAwardPkList = Arrays.asList(ids);
        return toAjax(sgjsAchievementAwardService.deleteSgjsAchievementAwardByPks(sgjsAchievementAwardPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsAchievementAward sgjsAchievementAwardParam) throws IOException {
        List<SgjsAchievementAward> sgjsAchievementAwardList = sgjsAchievementAwardService.getSgjsAchievementAwardList(sgjsAchievementAwardParam);
        ExcelUtils<SgjsAchievementAward> util = new ExcelUtils<>(SgjsAchievementAward.class);
        util.exportExcel(response, sgjsAchievementAwardList, DateUtils.getDate());
    }
}
