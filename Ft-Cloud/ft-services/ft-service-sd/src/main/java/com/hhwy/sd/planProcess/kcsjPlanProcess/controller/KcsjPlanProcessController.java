package com.hhwy.sd.planProcess.kcsjPlanProcess.controller;

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
import com.hhwy.sd.planProcess.kcsjPlanProcess.service.IKcsjPlanProcessService;
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjPlanProcess;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2023-12-18 11:13:27
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjPlanProcess")
public class KcsjPlanProcessController extends BaseController {

    @Autowired
    private IKcsjPlanProcessService kcsjPlanProcessService;


    @PreAuthorize(hasPermi = "kcsjPlanProcess:list")
    @GetMapping
    public AjaxResult getKcsjPlanProcess(@Validated(ValidationGroups.Get.class) KcsjPlanProcess kcsjPlanProcessParam) {
        KcsjPlanProcess kcsjPlanProcess = kcsjPlanProcessService.getKcsjPlanProcess(kcsjPlanProcessParam);
        return AjaxResult.success(kcsjPlanProcess);
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:list")
    @GetMapping("/list")
    public AjaxResult getKcsjPlanProcessList(@Validated(ValidationGroups.Select.class) KcsjPlanProcess kcsjPlanProcessParam) {
        startPage();
        List<KcsjPlanProcess> kcsjPlanProcessList = kcsjPlanProcessService.getKcsjPlanProcessList(kcsjPlanProcessParam);
        return getDataTableAjaxResult(kcsjPlanProcessList);
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjPlanProcess(@Validated(ValidationGroups.Save.class) @RequestBody KcsjPlanProcess kcsjPlanProcessParam) {
        kcsjPlanProcessService.insertKcsjPlanProcess(kcsjPlanProcessParam);
        return AjaxResult.success(kcsjPlanProcessParam);
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjPlanProcessList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjPlanProcess> kcsjPlanProcessListParam) {
        kcsjPlanProcessService.insertKcsjPlanProcessList(kcsjPlanProcessListParam);
        return AjaxResult.success(kcsjPlanProcessListParam);
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjPlanProcess(@Validated(ValidationGroups.Update.class) @RequestBody KcsjPlanProcess kcsjPlanProcessParam) {
        return toAjax(kcsjPlanProcessService.updateKcsjPlanProcess(kcsjPlanProcessParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjPlanProcessList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjPlanProcess> kcsjPlanProcessListParam) {
        return toAjax(kcsjPlanProcessService.updateKcsjPlanProcessList(kcsjPlanProcessListParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjPlanProcess(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjPlanProcess kcsjPlanProcessParam) {
        return toAjax(kcsjPlanProcessService.deleteKcsjPlanProcess(kcsjPlanProcessParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjPlanProcessByPks(@PathVariable Long[] ids) {
        List<Long> kcsjPlanProcessPkList = Arrays.asList(ids);
        return toAjax(kcsjPlanProcessService.deleteKcsjPlanProcessByPks(kcsjPlanProcessPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjPlanProcess kcsjPlanProcessParam) throws IOException {
        List<KcsjPlanProcess> kcsjPlanProcessList = kcsjPlanProcessService.getKcsjPlanProcessList(kcsjPlanProcessParam);
        ExcelUtils<KcsjPlanProcess> util = new ExcelUtils<>(KcsjPlanProcess.class);
        util.exportExcel(response, kcsjPlanProcessList, DateUtils.getDate());
    }
}
