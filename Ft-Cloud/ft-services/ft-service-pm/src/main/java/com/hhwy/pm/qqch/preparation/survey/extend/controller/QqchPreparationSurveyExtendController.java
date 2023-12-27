package com.hhwy.pm.qqch.preparation.survey.extend.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.extend.domain.EnvReport;
import com.hhwy.pm.qqch.preparation.survey.extend.domain.QqchPreparationSurveyExtend;
import com.hhwy.pm.qqch.preparation.survey.extend.service.IQqchPreparationSurveyExtendService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:36:03
 * @remark 前期策划-前期策划编制-勘察设计策划-扩展
 */
@Validated
@RestController
@RequestMapping("/qqchPreparationSurveyExtend")
public class QqchPreparationSurveyExtendController extends BaseController {

    @Autowired
    private IQqchPreparationSurveyExtendService qqchPreparationSurveyExtendService;

    /**
     * 获取扩展数据
     * @return
     */
    @PreAuthorize(hasPermi = "qqchPreparationSurveyExtend:list")
    @GetMapping
    public AjaxResult getQqchPreparationSurveyExtend(QqchPreparationSurveyExtend qqchPreparationSurveyExtend) {
        qqchPreparationSurveyExtend = qqchPreparationSurveyExtendService.getQqchPreparationSurveyExtend(qqchPreparationSurveyExtend);
        return AjaxResult.success(qqchPreparationSurveyExtend);
    }

    @PreAuthorize(hasPermi = "qqchPreparationSurveyExtend:list")
    @GetMapping("/list")
    public AjaxResult getQqchPreparationSurveyExtendList(@Validated(ValidationGroups.Select.class) QqchPreparationSurveyExtend qqchPreparationSurveyExtendParam) {
        startPage();
        List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendList = qqchPreparationSurveyExtendService.getQqchPreparationSurveyExtendList(qqchPreparationSurveyExtendParam);
        return getDataTableAjaxResult(qqchPreparationSurveyExtendList);
    }

    /**
     * 新增扩展数据
     * @param qqchPreparationSurveyExtendParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchPreparationSurveyExtend:add")
    @PostMapping("/add")
    public AjaxResult insertQqchPreparationSurveyExtend(@Validated(ValidationGroups.Save.class) @RequestBody QqchPreparationSurveyExtend qqchPreparationSurveyExtendParam) {
        qqchPreparationSurveyExtendService.insertQqchPreparationSurveyExtend(qqchPreparationSurveyExtendParam);
        return AjaxResult.success(qqchPreparationSurveyExtendParam);
    }

    /**
     * 获取附件
     * @param version 版本
     * @param moduleIdentity 功能标识
     * @return
     */
    @GetMapping("getEnvReport")
    public AjaxResult getEnvReport(BigDecimal version,String moduleIdentity){
        EnvReport envReport = qqchPreparationSurveyExtendService.getEnvReport(version,moduleIdentity);
        return AjaxResult.success(envReport);
    }

    /**
     * 保存附件
     * @param envReport
     * @return
     */
    @PostMapping("saveEnvReport")
    public AjaxResult saveEnvReport(@RequestBody EnvReport envReport){
        qqchPreparationSurveyExtendService.saveEnvReport(envReport);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchPreparationSurveyExtend:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchPreparationSurveyExtendList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendListParam) {
        qqchPreparationSurveyExtendService.insertQqchPreparationSurveyExtendList(qqchPreparationSurveyExtendListParam);
        return AjaxResult.success(qqchPreparationSurveyExtendListParam);
    }

    @PreAuthorize(hasPermi = "qqchPreparationSurveyExtend:update")
    @PostMapping("/update")
    public AjaxResult updateQqchPreparationSurveyExtend(@Validated(ValidationGroups.Update.class) @RequestBody QqchPreparationSurveyExtend qqchPreparationSurveyExtendParam) {
        return toAjax(qqchPreparationSurveyExtendService.updateQqchPreparationSurveyExtend(qqchPreparationSurveyExtendParam));
    }

    @PreAuthorize(hasPermi = "qqchPreparationSurveyExtend:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchPreparationSurveyExtendList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendListParam) {
        return toAjax(qqchPreparationSurveyExtendService.updateQqchPreparationSurveyExtendList(qqchPreparationSurveyExtendListParam));
    }

    @PreAuthorize(hasPermi = "qqchPreparationSurveyExtend:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchPreparationSurveyExtend(@Validated(ValidationGroups.Delete.class) @RequestBody QqchPreparationSurveyExtend qqchPreparationSurveyExtendParam) {
        return toAjax(qqchPreparationSurveyExtendService.deleteQqchPreparationSurveyExtend(qqchPreparationSurveyExtendParam));
    }

    @PreAuthorize(hasPermi = "qqchPreparationSurveyExtend:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchPreparationSurveyExtendByPks(@PathVariable Long[] ids) {
        List<Long> qqchPreparationSurveyExtendPkList = Arrays.asList(ids);
        return toAjax(qqchPreparationSurveyExtendService.deleteQqchPreparationSurveyExtendByPks(qqchPreparationSurveyExtendPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchPreparationSurveyExtend qqchPreparationSurveyExtendParam) throws IOException {
        List<QqchPreparationSurveyExtend> qqchPreparationSurveyExtendList = qqchPreparationSurveyExtendService.getQqchPreparationSurveyExtendList(qqchPreparationSurveyExtendParam);
        ExcelUtils<QqchPreparationSurveyExtend> util = new ExcelUtils<>(QqchPreparationSurveyExtend.class);
        util.exportExcel(response, qqchPreparationSurveyExtendList, DateUtils.getDate());
    }
}
