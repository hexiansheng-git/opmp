package com.hhwy.sp.buildSchemeManage.review.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeReviewStaff;
import com.hhwy.sp.buildSchemeManage.review.service.ISgjsBuildSchemeReviewStaffService;
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
 * @date 2024-03-20 09:39:55
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildSchemeReviewStaff")
public class SgjsBuildSchemeReviewStaffController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeReviewStaffService sgjsBuildSchemeReviewStaffService;


    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewStaff:list")
    @GetMapping
    public AjaxResult getSgjsBuildSchemeReviewStaff(@Validated(ValidationGroups.Get.class) SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaffParam) {
        SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaff = sgjsBuildSchemeReviewStaffService.getSgjsBuildSchemeReviewStaff(sgjsBuildSchemeReviewStaffParam);
        return AjaxResult.success(sgjsBuildSchemeReviewStaff);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewStaff:list")
    @GetMapping("/list")
    public AjaxResult getSgjsBuildSchemeReviewStaffList(@Validated(ValidationGroups.Select.class) SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaffParam) {
        startPage();
        List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffList = sgjsBuildSchemeReviewStaffService.getSgjsBuildSchemeReviewStaffList(sgjsBuildSchemeReviewStaffParam);
        return getDataTableAjaxResult(sgjsBuildSchemeReviewStaffList);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewStaff:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsBuildSchemeReviewStaff(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaffParam) {
        sgjsBuildSchemeReviewStaffService.insertSgjsBuildSchemeReviewStaff(sgjsBuildSchemeReviewStaffParam);
        return AjaxResult.success(sgjsBuildSchemeReviewStaffParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewStaff:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsBuildSchemeReviewStaffList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffListParam) {
        sgjsBuildSchemeReviewStaffService.insertSgjsBuildSchemeReviewStaffList(sgjsBuildSchemeReviewStaffListParam);
        return AjaxResult.success(sgjsBuildSchemeReviewStaffListParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewStaff:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsBuildSchemeReviewStaff(@Validated(ValidationGroups.Update.class) @RequestBody SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaffParam) {
        return toAjax(sgjsBuildSchemeReviewStaffService.updateSgjsBuildSchemeReviewStaff(sgjsBuildSchemeReviewStaffParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewStaff:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsBuildSchemeReviewStaffList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffListParam) {
        return toAjax(sgjsBuildSchemeReviewStaffService.updateSgjsBuildSchemeReviewStaffList(sgjsBuildSchemeReviewStaffListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewStaff:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsBuildSchemeReviewStaff(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaffParam) {
        return toAjax(sgjsBuildSchemeReviewStaffService.deleteSgjsBuildSchemeReviewStaff(sgjsBuildSchemeReviewStaffParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeReviewStaff:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsBuildSchemeReviewStaffByPks(@PathVariable Long[] ids) {
        List<Long> sgjsBuildSchemeReviewStaffPkList = Arrays.asList(ids);
        return toAjax(sgjsBuildSchemeReviewStaffService.deleteSgjsBuildSchemeReviewStaffByPks(sgjsBuildSchemeReviewStaffPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsBuildSchemeReviewStaff sgjsBuildSchemeReviewStaffParam) throws IOException {
        List<SgjsBuildSchemeReviewStaff> sgjsBuildSchemeReviewStaffList = sgjsBuildSchemeReviewStaffService.getSgjsBuildSchemeReviewStaffList(sgjsBuildSchemeReviewStaffParam);
        ExcelUtils<SgjsBuildSchemeReviewStaff> util = new ExcelUtils<>(SgjsBuildSchemeReviewStaff.class);
        util.exportExcel(response, sgjsBuildSchemeReviewStaffList, DateUtils.getDate());
    }
}
