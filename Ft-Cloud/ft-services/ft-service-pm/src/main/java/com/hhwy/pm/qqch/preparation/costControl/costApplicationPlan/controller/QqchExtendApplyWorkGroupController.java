package com.hhwy.pm.qqch.preparation.costControl.costApplicationPlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.costControl.costApplicationPlan.domain.QqchExtendApplyWorkGroup;
import com.hhwy.pm.qqch.preparation.costControl.costApplicationPlan.domain.vo.QqchExtendApplyWorkGroupVo;
import com.hhwy.pm.qqch.preparation.costControl.costApplicationPlan.service.IQqchExtendApplyWorkGroupService;
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
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchExtendApplyWorkGroup")
public class QqchExtendApplyWorkGroupController extends BaseController {

    @Autowired
    private IQqchExtendApplyWorkGroupService qqchExtendApplyWorkGroupService;


    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:list")
    @GetMapping
    public AjaxResult getQqchExtendApplyWorkGroup(@Validated(ValidationGroups.Get.class) QqchExtendApplyWorkGroup qqchExtendApplyWorkGroupParam) {
        QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup = qqchExtendApplyWorkGroupService.getQqchExtendApplyWorkGroup(qqchExtendApplyWorkGroupParam);
        return AjaxResult.success(qqchExtendApplyWorkGroup);
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:list")
    @GetMapping("/list")
    public AjaxResult getQqchExtendApplyWorkGroupList(@Validated(ValidationGroups.Select.class) QqchExtendApplyWorkGroup qqchExtendApplyWorkGroupParam) {
        startPage();
        List<QqchExtendApplyWorkGroup> qqchExtendApplyWorkGroupList = qqchExtendApplyWorkGroupService.getQqchExtendApplyWorkGroupList(qqchExtendApplyWorkGroupParam);
        return getDataTableAjaxResult(qqchExtendApplyWorkGroupList);
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:add")
    @PostMapping("/add")
    public AjaxResult insertQqchExtendApplyWorkGroup(@Validated(ValidationGroups.Save.class) @RequestBody QqchExtendApplyWorkGroup qqchExtendApplyWorkGroupParam) {
        qqchExtendApplyWorkGroupService.insertQqchExtendApplyWorkGroup(qqchExtendApplyWorkGroupParam);
        return AjaxResult.success(qqchExtendApplyWorkGroupParam);
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:update")
    @PostMapping("/update")
    public AjaxResult updateQqchExtendApplyWorkGroup(@Validated(ValidationGroups.Update.class) @RequestBody QqchExtendApplyWorkGroup qqchExtendApplyWorkGroupParam) {
        return toAjax(qqchExtendApplyWorkGroupService.updateQqchExtendApplyWorkGroup(qqchExtendApplyWorkGroupParam));
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchExtendApplyWorkGroupList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchExtendApplyWorkGroup> qqchExtendApplyWorkGroupListParam) {
        return toAjax(qqchExtendApplyWorkGroupService.updateQqchExtendApplyWorkGroupList(qqchExtendApplyWorkGroupListParam));
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchExtendApplyWorkGroup(@Validated(ValidationGroups.Delete.class) @RequestBody QqchExtendApplyWorkGroup qqchExtendApplyWorkGroupParam) {
        return toAjax(qqchExtendApplyWorkGroupService.deleteQqchExtendApplyWorkGroup(qqchExtendApplyWorkGroupParam));
    }

    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchExtendApplyWorkGroupByPks(@PathVariable Long[] ids) {
        List<Long> qqchExtendApplyWorkGroupPkList = Arrays.asList(ids);
        return toAjax(qqchExtendApplyWorkGroupService.deleteQqchExtendApplyWorkGroupByPks(qqchExtendApplyWorkGroupPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchExtendApplyWorkGroup qqchExtendApplyWorkGroupParam) throws IOException {
        List<QqchExtendApplyWorkGroup> qqchExtendApplyWorkGroupList = qqchExtendApplyWorkGroupService.getQqchExtendApplyWorkGroupList(qqchExtendApplyWorkGroupParam);
        ExcelUtils<QqchExtendApplyWorkGroup> util = new ExcelUtils<>(QqchExtendApplyWorkGroup.class);
        util.exportExcel(response, qqchExtendApplyWorkGroupList, DateUtils.getDate());
    }

    /**
     * 获取Vo
     * @param qqchExtendApplyWorkGroup
     * @return
     */
    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:list")
    @GetMapping("getQqchExtendApplyWorkGroupVo")
    public AjaxResult getQqchExtendApplyWorkGroupVo(@Validated(ValidationGroups.Get.class) QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup) {
        QqchExtendApplyWorkGroupVo qqchExtendApplyWorkGroupVo = qqchExtendApplyWorkGroupService.getQqchExtendApplyWorkGroupVo(qqchExtendApplyWorkGroup);
        return AjaxResult.success(qqchExtendApplyWorkGroupVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchExtendApplyWorkGroupVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchExtendApplyWorkGroup:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchExtendApplyWorkGroupVo qqchExtendApplyWorkGroupVo) {
        qqchExtendApplyWorkGroupService.save(qqchExtendApplyWorkGroupVo);
        return AjaxResult.success();
    }
}
