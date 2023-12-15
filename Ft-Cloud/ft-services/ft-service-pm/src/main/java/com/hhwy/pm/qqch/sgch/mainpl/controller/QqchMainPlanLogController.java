package com.hhwy.pm.qqch.sgch.mainpl.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanLog;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanLogService;
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
 * @date 2023-12-15 17:00:32
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchMainPlanLog")
public class QqchMainPlanLogController extends BaseController {

    @Autowired
    private IQqchMainPlanLogService qqchMainPlanLogService;


    @PreAuthorize(hasPermi = "qqchMainPlanLog:list")
    @GetMapping
    public AjaxResult getQqchMainPlanLog(@Validated(ValidationGroups.Get.class) QqchMainPlanLog qqchMainPlanLogParam) {
        QqchMainPlanLog qqchMainPlanLog = qqchMainPlanLogService.getQqchMainPlanLog(qqchMainPlanLogParam);
        return AjaxResult.success(qqchMainPlanLog);
    }

    @PreAuthorize(hasPermi = "qqchMainPlanLog:list")
    @GetMapping("/list")
    public AjaxResult getQqchMainPlanLogList(@Validated(ValidationGroups.Select.class) QqchMainPlanLog qqchMainPlanLogParam) {
        startPage();
        List<QqchMainPlanLog> qqchMainPlanLogList = qqchMainPlanLogService.getQqchMainPlanLogList(qqchMainPlanLogParam);
        return getDataTableAjaxResult(qqchMainPlanLogList);
    }

    @GetMapping("/getLastTime")
    public AjaxResult getLastTime(@Validated(ValidationGroups.Get.class) QqchMainPlanLog qqchMainPlanLogParam) {
        qqchMainPlanLogParam.setOperation("allUpdateTimeNum");
        QqchMainPlanLog qqchMainPlanLog = qqchMainPlanLogService.getQqchMainPlanLog(qqchMainPlanLogParam);
        return AjaxResult.success(qqchMainPlanLog);
    }

    @PreAuthorize(hasPermi = "qqchMainPlanLog:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMainPlanLog(@Validated(ValidationGroups.Save.class) @RequestBody QqchMainPlanLog qqchMainPlanLogParam) {
        qqchMainPlanLogService.insertQqchMainPlanLog(qqchMainPlanLogParam);
        return AjaxResult.success(qqchMainPlanLogParam);
    }

    @PreAuthorize(hasPermi = "qqchMainPlanLog:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMainPlanLogList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMainPlanLog> qqchMainPlanLogListParam) {
        qqchMainPlanLogService.insertQqchMainPlanLogList(qqchMainPlanLogListParam);
        return AjaxResult.success(qqchMainPlanLogListParam);
    }

    @PreAuthorize(hasPermi = "qqchMainPlanLog:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMainPlanLog(@Validated(ValidationGroups.Update.class) @RequestBody QqchMainPlanLog qqchMainPlanLogParam) {
        return toAjax(qqchMainPlanLogService.updateQqchMainPlanLog(qqchMainPlanLogParam));
    }

    @PreAuthorize(hasPermi = "qqchMainPlanLog:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchMainPlanLogList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMainPlanLog> qqchMainPlanLogListParam) {
        return toAjax(qqchMainPlanLogService.updateQqchMainPlanLogList(qqchMainPlanLogListParam));
    }

    @PreAuthorize(hasPermi = "qqchMainPlanLog:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMainPlanLog(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMainPlanLog qqchMainPlanLogParam) {
        return toAjax(qqchMainPlanLogService.deleteQqchMainPlanLog(qqchMainPlanLogParam));
    }

    @PreAuthorize(hasPermi = "qqchMainPlanLog:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchMainPlanLogByPks(@PathVariable Long[] ids) {
        List<Long> qqchMainPlanLogPkList = Arrays.asList(ids);
        return toAjax(qqchMainPlanLogService.deleteQqchMainPlanLogByPks(qqchMainPlanLogPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMainPlanLog qqchMainPlanLogParam) throws IOException {
        List<QqchMainPlanLog> qqchMainPlanLogList = qqchMainPlanLogService.getQqchMainPlanLogList(qqchMainPlanLogParam);
        ExcelUtils<QqchMainPlanLog> util = new ExcelUtils<>(QqchMainPlanLog.class);
        util.exportExcel(response, qqchMainPlanLogList, DateUtils.getDate());
    }
}
