package com.hhwy.sd.groupManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.groupManage.domain.KcsjGroupManageApproachStaff;
import com.hhwy.sd.groupManage.service.IKcsjGroupManageApproachStaffService;
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
 * @date 2023-12-13 17:37:16
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjGroupManageApproachStaff")
public class KcsjGroupManageApproachStaffController extends BaseController {

    @Autowired
    private IKcsjGroupManageApproachStaffService kcsjGroupManageApproachStaffService;


    @PreAuthorize(hasPermi = "kcsjGroupManageApproachStaff:list")
    @GetMapping
    public AjaxResult getKcsjGroupManageApproachStaff(@Validated(ValidationGroups.Get.class) KcsjGroupManageApproachStaff kcsjGroupManageApproachStaffParam) {
        KcsjGroupManageApproachStaff kcsjGroupManageApproachStaff = kcsjGroupManageApproachStaffService.getKcsjGroupManageApproachStaff(kcsjGroupManageApproachStaffParam);
        return AjaxResult.success(kcsjGroupManageApproachStaff);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageApproachStaff:list")
    @GetMapping("/list")
    public AjaxResult getKcsjGroupManageApproachStaffList(@Validated(ValidationGroups.Select.class) KcsjGroupManageApproachStaff kcsjGroupManageApproachStaffParam) {
        startPage();
        List<KcsjGroupManageApproachStaff> kcsjGroupManageApproachStaffList = kcsjGroupManageApproachStaffService.getKcsjGroupManageApproachStaffList(kcsjGroupManageApproachStaffParam);
        return getDataTableAjaxResult(kcsjGroupManageApproachStaffList);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageApproachStaff:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjGroupManageApproachStaff(@Validated(ValidationGroups.Save.class) @RequestBody KcsjGroupManageApproachStaff kcsjGroupManageApproachStaffParam) {
        kcsjGroupManageApproachStaffService.insertKcsjGroupManageApproachStaff(kcsjGroupManageApproachStaffParam);
        return AjaxResult.success(kcsjGroupManageApproachStaffParam);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageApproachStaff:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjGroupManageApproachStaffList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjGroupManageApproachStaff> kcsjGroupManageApproachStaffListParam) {
        kcsjGroupManageApproachStaffService.insertKcsjGroupManageApproachStaffList(kcsjGroupManageApproachStaffListParam);
        return AjaxResult.success(kcsjGroupManageApproachStaffListParam);
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageApproachStaff:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjGroupManageApproachStaff(@Validated(ValidationGroups.Update.class) @RequestBody KcsjGroupManageApproachStaff kcsjGroupManageApproachStaffParam) {
        return toAjax(kcsjGroupManageApproachStaffService.updateKcsjGroupManageApproachStaff(kcsjGroupManageApproachStaffParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageApproachStaff:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjGroupManageApproachStaffList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjGroupManageApproachStaff> kcsjGroupManageApproachStaffListParam) {
        return toAjax(kcsjGroupManageApproachStaffService.updateKcsjGroupManageApproachStaffList(kcsjGroupManageApproachStaffListParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageApproachStaff:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjGroupManageApproachStaff(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjGroupManageApproachStaff kcsjGroupManageApproachStaffParam) {
        return toAjax(kcsjGroupManageApproachStaffService.deleteKcsjGroupManageApproachStaff(kcsjGroupManageApproachStaffParam));
    }

    @PreAuthorize(hasPermi = "kcsjGroupManageApproachStaff:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjGroupManageApproachStaffByPks(@PathVariable Long[] ids) {
        List<Long> kcsjGroupManageApproachStaffPkList = Arrays.asList(ids);
        return toAjax(kcsjGroupManageApproachStaffService.deleteKcsjGroupManageApproachStaffByPks(kcsjGroupManageApproachStaffPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjGroupManageApproachStaff kcsjGroupManageApproachStaffParam) throws IOException {
        List<KcsjGroupManageApproachStaff> kcsjGroupManageApproachStaffList = kcsjGroupManageApproachStaffService.getKcsjGroupManageApproachStaffList(kcsjGroupManageApproachStaffParam);
        ExcelUtils<KcsjGroupManageApproachStaff> util = new ExcelUtils<>(KcsjGroupManageApproachStaff.class);
        util.exportExcel(response, kcsjGroupManageApproachStaffList, DateUtils.getDate());
    }
}
