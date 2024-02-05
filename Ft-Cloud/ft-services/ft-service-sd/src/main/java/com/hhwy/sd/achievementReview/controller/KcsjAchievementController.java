package com.hhwy.sd.achievementReview.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.achievementReview.domain.KcsjAchievement;
import com.hhwy.sd.achievementReview.service.IKcsjAchievementService;
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
 * @date 2024-02-05 09:04:08
 * @remark 勘察设计成果评审-成果
 */
@Validated
@RestController
@RequestMapping("/kcsjAchievement")
public class KcsjAchievementController extends BaseController {

    @Autowired
    private IKcsjAchievementService kcsjAchievementService;


    @PreAuthorize(hasPermi = "kcsjAchievement:list")
    @GetMapping
    public AjaxResult getKcsjAchievement(@Validated(ValidationGroups.Get.class) KcsjAchievement kcsjAchievementParam) {
        KcsjAchievement kcsjAchievement = kcsjAchievementService.getKcsjAchievement(kcsjAchievementParam);
        return AjaxResult.success(kcsjAchievement);
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:list")
    @GetMapping("/list")
    public AjaxResult getKcsjAchievementList(@Validated(ValidationGroups.Select.class) KcsjAchievement kcsjAchievementParam) {
        startPage();
        List<KcsjAchievement> kcsjAchievementList = kcsjAchievementService.getKcsjAchievementList(kcsjAchievementParam);
        return getDataTableAjaxResult(kcsjAchievementList);
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjAchievement(@Validated(ValidationGroups.Save.class) @RequestBody KcsjAchievement kcsjAchievementParam) {
        kcsjAchievementService.insertKcsjAchievement(kcsjAchievementParam);
        return AjaxResult.success(kcsjAchievementParam);
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjAchievementList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjAchievement> kcsjAchievementListParam) {
        kcsjAchievementService.insertKcsjAchievementList(kcsjAchievementListParam);
        return AjaxResult.success(kcsjAchievementListParam);
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjAchievement(@Validated(ValidationGroups.Update.class) @RequestBody KcsjAchievement kcsjAchievementParam) {
        return toAjax(kcsjAchievementService.updateKcsjAchievement(kcsjAchievementParam));
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjAchievementList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjAchievement> kcsjAchievementListParam) {
        return toAjax(kcsjAchievementService.updateKcsjAchievementList(kcsjAchievementListParam));
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjAchievement(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjAchievement kcsjAchievementParam) {
        return toAjax(kcsjAchievementService.deleteKcsjAchievement(kcsjAchievementParam));
    }

    @PreAuthorize(hasPermi = "kcsjAchievement:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjAchievementByPks(@PathVariable Long[] ids) {
        List<Long> kcsjAchievementPkList = Arrays.asList(ids);
        return toAjax(kcsjAchievementService.deleteKcsjAchievementByPks(kcsjAchievementPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjAchievement kcsjAchievementParam) throws IOException {
        List<KcsjAchievement> kcsjAchievementList = kcsjAchievementService.getKcsjAchievementList(kcsjAchievementParam);
        ExcelUtils<KcsjAchievement> util = new ExcelUtils<>(KcsjAchievement.class);
        util.exportExcel(response, kcsjAchievementList, DateUtils.getDate());
    }
}
