package com.hhwy.pm.qqch.preparation.costControl.postDuty.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.QqchCostControlPostDuty;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.vo.QqchCostControlPostDutyVo;
import com.hhwy.pm.qqch.preparation.costControl.postDuty.service.IQqchCostControlPostDutyService;
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
 * @date 2023-08-08 17:48:45
 * @remark 成本管控岗位责任
 */
@Validated
@RestController
@RequestMapping("/qqchExtendApplyWorkGroup")
public class QqchCostControlPostDutyController extends BaseController {

    @Autowired
    private IQqchCostControlPostDutyService qqchExtendApplyWorkGroupService;


    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:list")
    @GetMapping
    public AjaxResult getQqchExtendApplyWorkGroup(@Validated(ValidationGroups.Get.class) QqchCostControlPostDuty qqchCostControlPostDutyParam) {
        QqchCostControlPostDuty qqchCostControlPostDuty = qqchExtendApplyWorkGroupService.getQqchExtendApplyWorkGroup(qqchCostControlPostDutyParam);
        return AjaxResult.success(qqchCostControlPostDuty);
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:list")
    @GetMapping("/list")
    public AjaxResult getQqchExtendApplyWorkGroupList(@Validated(ValidationGroups.Select.class) QqchCostControlPostDuty qqchCostControlPostDutyParam) {
        startPage();
        List<QqchCostControlPostDuty> qqchCostControlPostDutyList = qqchExtendApplyWorkGroupService.getQqchExtendApplyWorkGroupList(qqchCostControlPostDutyParam);
        return getDataTableAjaxResult(qqchCostControlPostDutyList);
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:add")
    @PostMapping("/add")
    public AjaxResult insertQqchExtendApplyWorkGroup(@Validated(ValidationGroups.Save.class) @RequestBody QqchCostControlPostDuty qqchCostControlPostDutyParam) {
        qqchExtendApplyWorkGroupService.insertQqchExtendApplyWorkGroup(qqchCostControlPostDutyParam);
        return AjaxResult.success(qqchCostControlPostDutyParam);
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:update")
    @PostMapping("/update")
    public AjaxResult updateQqchExtendApplyWorkGroup(@Validated(ValidationGroups.Update.class) @RequestBody QqchCostControlPostDuty qqchCostControlPostDutyParam) {
        return toAjax(qqchExtendApplyWorkGroupService.updateQqchExtendApplyWorkGroup(qqchCostControlPostDutyParam));
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchExtendApplyWorkGroupList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchCostControlPostDuty> qqchCostControlPostDutyListParam) {
        return toAjax(qqchExtendApplyWorkGroupService.updateQqchExtendApplyWorkGroupList(qqchCostControlPostDutyListParam));
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchExtendApplyWorkGroup(@Validated(ValidationGroups.Delete.class) @RequestBody QqchCostControlPostDuty qqchCostControlPostDutyParam) {
        return toAjax(qqchExtendApplyWorkGroupService.deleteQqchExtendApplyWorkGroup(qqchCostControlPostDutyParam));
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchExtendApplyWorkGroupByPks(@PathVariable Long[] ids) {
        List<Long> qqchExtendApplyWorkGroupPkList = Arrays.asList(ids);
        return toAjax(qqchExtendApplyWorkGroupService.deleteQqchExtendApplyWorkGroupByPks(qqchExtendApplyWorkGroupPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchCostControlPostDuty qqchCostControlPostDutyParam) throws IOException {
        List<QqchCostControlPostDuty> qqchCostControlPostDutyList = qqchExtendApplyWorkGroupService.getQqchExtendApplyWorkGroupList(qqchCostControlPostDutyParam);
        ExcelUtils<QqchCostControlPostDuty> util = new ExcelUtils<>(QqchCostControlPostDuty.class);
        util.exportExcel(response, qqchCostControlPostDutyList, DateUtils.getDate());
    }

    /**
     * 获取Vo
     * @param qqchCostControlPostDuty
     * @return
     */
    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:list")
    @GetMapping("getQqchExtendApplyWorkGroupVo")
    public AjaxResult getQqchExtendApplyWorkGroupVo(@Validated(ValidationGroups.Get.class) QqchCostControlPostDuty qqchCostControlPostDuty) {
        QqchCostControlPostDutyVo qqchCostControlPostDutyVo = qqchExtendApplyWorkGroupService.getQqchExtendApplyWorkGroupVo(qqchCostControlPostDuty);
        return AjaxResult.success(qqchCostControlPostDutyVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchCostControlPostDutyVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchCostControlPostDutyVo qqchCostControlPostDutyVo) {
        qqchExtendApplyWorkGroupService.save(qqchCostControlPostDutyVo);
        return AjaxResult.success();
    }
}
