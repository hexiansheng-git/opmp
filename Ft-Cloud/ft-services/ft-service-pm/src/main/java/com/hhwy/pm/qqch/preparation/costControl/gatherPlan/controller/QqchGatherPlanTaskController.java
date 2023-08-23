package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.QqchGatherPlanTask;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.service.IQqchGatherPlanTaskService;
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
 * @date 2023-08-23 11:10:35
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchGatherPlanTask")
public class QqchGatherPlanTaskController extends BaseController {

    @Autowired
    private IQqchGatherPlanTaskService qqchGatherPlanTaskService;


    @PreAuthorize(hasPermi = "qqchGatherPlanTask:list")
    @GetMapping
    public AjaxResult getQqchGatherPlanTask(@Validated(ValidationGroups.Get.class) QqchGatherPlanTask qqchGatherPlanTaskParam) {
        QqchGatherPlanTask qqchGatherPlanTask = qqchGatherPlanTaskService.getQqchGatherPlanTask(qqchGatherPlanTaskParam);
        return AjaxResult.success(qqchGatherPlanTask);
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTask:list")
    @GetMapping("/list")
    public AjaxResult getQqchGatherPlanTaskList(@Validated(ValidationGroups.Select.class) QqchGatherPlanTask qqchGatherPlanTaskParam) {
        startPage();
        List<QqchGatherPlanTask> qqchGatherPlanTaskList = qqchGatherPlanTaskService.getQqchGatherPlanTaskList(qqchGatherPlanTaskParam);
        return getDataTableAjaxResult(qqchGatherPlanTaskList);
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTask:add")
    @PostMapping("/add")
    public AjaxResult insertQqchGatherPlanTask(@Validated(ValidationGroups.Save.class) @RequestBody QqchGatherPlanTask qqchGatherPlanTaskParam) {
        qqchGatherPlanTaskService.insertQqchGatherPlanTask(qqchGatherPlanTaskParam);
        return AjaxResult.success(qqchGatherPlanTaskParam);
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTask:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchGatherPlanTaskList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchGatherPlanTask> qqchGatherPlanTaskListParam) {
        qqchGatherPlanTaskService.insertQqchGatherPlanTaskList(qqchGatherPlanTaskListParam);
        return AjaxResult.success(qqchGatherPlanTaskListParam);
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTask:update")
    @PostMapping("/update")
    public AjaxResult updateQqchGatherPlanTask(@Validated(ValidationGroups.Update.class) @RequestBody QqchGatherPlanTask qqchGatherPlanTaskParam) {
        return toAjax(qqchGatherPlanTaskService.updateQqchGatherPlanTask(qqchGatherPlanTaskParam));
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTask:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchGatherPlanTaskList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchGatherPlanTask> qqchGatherPlanTaskListParam) {
        return toAjax(qqchGatherPlanTaskService.updateQqchGatherPlanTaskList(qqchGatherPlanTaskListParam));
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTask:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchGatherPlanTask(@Validated(ValidationGroups.Delete.class) @RequestBody QqchGatherPlanTask qqchGatherPlanTaskParam) {
        return toAjax(qqchGatherPlanTaskService.deleteQqchGatherPlanTask(qqchGatherPlanTaskParam));
    }

    @PreAuthorize(hasPermi = "qqchGatherPlanTask:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchGatherPlanTaskByPks(@PathVariable Long[] ids) {
        List<Long> qqchGatherPlanTaskPkList = Arrays.asList(ids);
        return toAjax(qqchGatherPlanTaskService.deleteQqchGatherPlanTaskByPks(qqchGatherPlanTaskPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchGatherPlanTask qqchGatherPlanTaskParam) throws IOException {
        List<QqchGatherPlanTask> qqchGatherPlanTaskList = qqchGatherPlanTaskService.getQqchGatherPlanTaskList(qqchGatherPlanTaskParam);
        ExcelUtils<QqchGatherPlanTask> util = new ExcelUtils<>(QqchGatherPlanTask.class);
        util.exportExcel(response, qqchGatherPlanTaskList, DateUtils.getDate());
    }
}
