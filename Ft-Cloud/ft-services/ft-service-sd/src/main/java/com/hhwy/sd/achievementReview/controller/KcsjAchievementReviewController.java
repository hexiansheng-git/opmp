package com.hhwy.sd.achievementReview.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sd.achievementReview.domain.KcsjAchievementReview;
import com.hhwy.sd.achievementReview.service.IKcsjAchievementReviewService;
import com.hhwy.sd.common.FlowInfoSearchUtil;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
 * @date 2024-02-05 09:04:12
 * @remark 勘察设计成果评审-成果评审
 */
@Validated
@RestController
@RequestMapping("/kcsjAchievementReview")
public class KcsjAchievementReviewController extends BaseController {

    @Autowired
    private IKcsjAchievementReviewService kcsjAchievementReviewService;


    @PreAuthorize(hasPermi = "kcsjAchievementReview:list")
    @GetMapping
    public AjaxResult getKcsjAchievementReview(@Validated(ValidationGroups.Get.class) KcsjAchievementReview kcsjAchievementReviewParam) {
        KcsjAchievementReview kcsjAchievementReview = kcsjAchievementReviewService.getKcsjAchievementReview(kcsjAchievementReviewParam);
        return AjaxResult.success(kcsjAchievementReview);
    }

    /**
     * 台账
     * @param kcsjAchievementReviewParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjAchievementReview:list")
    @GetMapping("/list")
    public AjaxResult getKcsjAchievementReviewList(@Validated(ValidationGroups.Select.class) KcsjAchievementReview kcsjAchievementReviewParam) {
        startPage();
        List<KcsjAchievementReview> kcsjAchievementReviewList = kcsjAchievementReviewService.getKcsjAchievementReviewList(kcsjAchievementReviewParam);
        FlowInfoSearchUtil.getFlowInfo(kcsjAchievementReviewList, FlowEnum.KCSJ_ACHIEVEMENT_REVIEW);
        return getDataTableAjaxResult(kcsjAchievementReviewList);
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("getById")
    public AjaxResult getKcsjAchievementReviewById(Long id){
        KcsjAchievementReview kcsjAchievementReview = kcsjAchievementReviewService.getKcsjAchievementReviewById(id);
        FlowInfoSearchUtil.getFlowInfo(kcsjAchievementReview, FlowEnum.KCSJ_ACHIEVEMENT_REVIEW);
        return AjaxResult.success(kcsjAchievementReview);
    }

    /**
     * 保存
     * @param review
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjAchievementReview:save")
    @PostMapping("/save")
    @CustomLogger(title = "勘察设计-勘察设计成果评审-成果评审",name = "成果评审",businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody KcsjAchievementReview review){
        Long id = kcsjAchievementReviewService.save(review);
        return AjaxResult.success(id);
    }

    @PreAuthorize(hasPermi = "kcsjAchievementReview:add")
    @PostMapping("/add")
    @CustomLogger(title = "勘察设计-勘察设计成果评审-成果评审",name = "成果评审",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjAchievementReview(@Validated(ValidationGroups.Save.class) @RequestBody KcsjAchievementReview kcsjAchievementReviewParam) {
        kcsjAchievementReviewService.insertKcsjAchievementReview(kcsjAchievementReviewParam);
        return AjaxResult.success(kcsjAchievementReviewParam);
    }

    @PreAuthorize(hasPermi = "kcsjAchievementReview:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "勘察设计-勘察设计成果评审-成果评审",name = "成果评审",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjAchievementReviewList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjAchievementReview> kcsjAchievementReviewListParam) {
        kcsjAchievementReviewService.insertKcsjAchievementReviewList(kcsjAchievementReviewListParam);
        return AjaxResult.success(kcsjAchievementReviewListParam);
    }

    @PreAuthorize(hasPermi = "kcsjAchievementReview:update")
    @PostMapping("/update")
    @CustomLogger(title = "勘察设计-勘察设计成果评审-成果评审",name = "成果评审",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjAchievementReview(@Validated(ValidationGroups.Update.class) @RequestBody KcsjAchievementReview kcsjAchievementReviewParam) {
        return toAjax(kcsjAchievementReviewService.updateKcsjAchievementReview(kcsjAchievementReviewParam));
    }

    @PreAuthorize(hasPermi = "kcsjAchievementReview:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "勘察设计-勘察设计成果评审-成果评审",name = "成果评审",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjAchievementReviewList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjAchievementReview> kcsjAchievementReviewListParam) {
        return toAjax(kcsjAchievementReviewService.updateKcsjAchievementReviewList(kcsjAchievementReviewListParam));
    }

    @PreAuthorize(hasPermi = "kcsjAchievementReview:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjAchievementReview(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjAchievementReview kcsjAchievementReviewParam) {
        return toAjax(kcsjAchievementReviewService.deleteKcsjAchievementReview(kcsjAchievementReviewParam));
    }

    @PreAuthorize(hasPermi = "kcsjAchievementReview:remove")
    @PostMapping("/deleteById/{id}")
    public AjaxResult deleteById(@PathVariable("id") Long id) {
        kcsjAchievementReviewService.deleteById(id);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "kcsjAchievementReview:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjAchievementReviewByPks(@PathVariable Long[] ids) {
        List<Long> kcsjAchievementReviewPkList = Arrays.asList(ids);
        return toAjax(kcsjAchievementReviewService.deleteKcsjAchievementReviewByPks(kcsjAchievementReviewPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjAchievementReview kcsjAchievementReviewParam) throws IOException {
        List<KcsjAchievementReview> kcsjAchievementReviewList = kcsjAchievementReviewService.getKcsjAchievementReviewList(kcsjAchievementReviewParam);
        ExcelUtils<KcsjAchievementReview> util = new ExcelUtils<>(KcsjAchievementReview.class);
        util.exportExcel(response, kcsjAchievementReviewList, DateUtils.getDate());
    }

    /**
     * 提交监听器
     * @param id
     * @return
     */
    @PostMapping("submit")
    public AjaxResult submitKcsjAchievementReviewProcess(Long id){
        kcsjAchievementReviewService.submit(id);
        return AjaxResult.success();
    }

    /**
     * 结束监听器
     * @param id
     * @return
     */
    @PostMapping("listener")
    public AjaxResult updateKcsjAchievementReviewProcess(Long id){
        kcsjAchievementReviewService.updateKcsjAchievementReviewProcess(id);
        return AjaxResult.success();
    }
}
