package com.hhwy.pm.qqch.preparation.costControl.operateTarget.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.operateTarget.domain.QqchProjectOperationObjective;
import com.hhwy.pm.qqch.preparation.costControl.operateTarget.domain.vo.QqchProjectOperationObjectiveVo;
import com.hhwy.pm.qqch.preparation.costControl.operateTarget.service.IQqchProjectOperationObjectiveService;
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
 * @date 2023-08-03 13:39:48
 * @remark 项目整体经营目标
 */
@Validated
@RestController
@RequestMapping("/qqchProjectOperationObjective")
public class QqchProjectOperationObjectiveController extends BaseController {

    @Autowired
    private IQqchProjectOperationObjectiveService qqchProjectOperationObjectiveService;


    @PreAuthorize(hasPermi = "qqchProjectOperationObjective:list")
    @GetMapping
    public AjaxResult getQqchProjectOperationObjective(@Validated(ValidationGroups.Get.class) QqchProjectOperationObjective qqchProjectOperationObjectiveParam) {
        QqchProjectOperationObjective qqchProjectOperationObjective = qqchProjectOperationObjectiveService.getQqchProjectOperationObjective(qqchProjectOperationObjectiveParam);
        return AjaxResult.success(qqchProjectOperationObjective);
    }

    @PreAuthorize(hasPermi = "qqchProjectOperationObjective:list")
    @GetMapping("/list")
    public AjaxResult getQqchProjectOperationObjectiveList(@Validated(ValidationGroups.Select.class) QqchProjectOperationObjective qqchProjectOperationObjectiveParam) {
        startPage();
        List<QqchProjectOperationObjective> qqchProjectOperationObjectiveList = qqchProjectOperationObjectiveService.getQqchProjectOperationObjectiveList(qqchProjectOperationObjectiveParam);
        return getDataTableAjaxResult(qqchProjectOperationObjectiveList);
    }

    @PreAuthorize(hasPermi = "qqchProjectOperationObjective:add")
    @PostMapping("/add")
    public AjaxResult insertQqchProjectOperationObjective(@Validated(ValidationGroups.Save.class) @RequestBody QqchProjectOperationObjective qqchProjectOperationObjectiveParam) {
        qqchProjectOperationObjectiveService.insertQqchProjectOperationObjective(qqchProjectOperationObjectiveParam);
        return AjaxResult.success(qqchProjectOperationObjectiveParam);
    }

    @PreAuthorize(hasPermi = "qqchProjectOperationObjective:update")
    @PostMapping("/update")
    public AjaxResult updateQqchProjectOperationObjective(@Validated(ValidationGroups.Update.class) @RequestBody QqchProjectOperationObjective qqchProjectOperationObjectiveParam) {
        return toAjax(qqchProjectOperationObjectiveService.updateQqchProjectOperationObjective(qqchProjectOperationObjectiveParam));
    }

    @PreAuthorize(hasPermi = "qqchProjectOperationObjective:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchProjectOperationObjectiveList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchProjectOperationObjective> qqchProjectOperationObjectiveListParam) {
        return toAjax(qqchProjectOperationObjectiveService.updateQqchProjectOperationObjectiveList(qqchProjectOperationObjectiveListParam));
    }

    @PreAuthorize(hasPermi = "qqchProjectOperationObjective:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchProjectOperationObjective(@Validated(ValidationGroups.Delete.class) @RequestBody QqchProjectOperationObjective qqchProjectOperationObjectiveParam) {
        return toAjax(qqchProjectOperationObjectiveService.deleteQqchProjectOperationObjective(qqchProjectOperationObjectiveParam));
    }

    @PreAuthorize(hasPermi = "qqchProjectOperationObjective:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchProjectOperationObjectiveByPks(@PathVariable Long[] ids) {
        List<Long> qqchProjectOperationObjectivePkList = Arrays.asList(ids);
        return toAjax(qqchProjectOperationObjectiveService.deleteQqchProjectOperationObjectiveByPks(qqchProjectOperationObjectivePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchProjectOperationObjective qqchProjectOperationObjectiveParam) throws IOException {
        List<QqchProjectOperationObjective> qqchProjectOperationObjectiveList = qqchProjectOperationObjectiveService.getQqchProjectOperationObjectiveList(qqchProjectOperationObjectiveParam);
        ExcelUtils<QqchProjectOperationObjective> util = new ExcelUtils<>(QqchProjectOperationObjective.class);
        util.exportExcel(response, qqchProjectOperationObjectiveList, DateUtils.getDate());
    }

    /**
     * 获取项目整体经营目标Vo
     * @param qqchProjectOperationObjective
     * @return
     */
    @GetMapping("getQqchProjectOperationObjectiveVo")
    public AjaxResult getQqchProjectOperationObjectiveVo(@Validated(ValidationGroups.Get.class) QqchProjectOperationObjective qqchProjectOperationObjective) {
        QqchProjectOperationObjectiveVo qqchProjectOperationObjectiveVo = qqchProjectOperationObjectiveService.getQqchProjectOperationObjectiveVo(qqchProjectOperationObjective);
        return AjaxResult.success(qqchProjectOperationObjectiveVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchProjectOperationObjectiveVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchProjectOperationObjective:save")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody QqchProjectOperationObjectiveVo qqchProjectOperationObjectiveVo) {
        qqchProjectOperationObjectiveService.save(qqchProjectOperationObjectiveVo);
        return AjaxResult.success();
    }
}
