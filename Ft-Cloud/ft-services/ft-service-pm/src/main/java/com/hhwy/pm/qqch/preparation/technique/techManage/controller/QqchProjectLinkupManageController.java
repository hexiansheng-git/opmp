package com.hhwy.pm.qqch.preparation.technique.techManage.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManage.domain.QqchProjectLinkupManage;
import com.hhwy.pm.qqch.preparation.technique.techManage.service.IQqchProjectLinkupManageService;
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
 * @date 2023-07-25 11:24:02
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchProjectLinkupManage")
public class QqchProjectLinkupManageController extends BaseController {

    @Autowired
    private IQqchProjectLinkupManageService qqchProjectLinkupManageService;


    @PreAuthorize(hasPermi = "qqchProjectLinkupManage:list")
    @GetMapping
    public AjaxResult getQqchProjectLinkupManage(@Validated(ValidationGroups.Get.class) QqchProjectLinkupManage qqchProjectLinkupManageParam) {
        QqchProjectLinkupManage qqchProjectLinkupManage = qqchProjectLinkupManageService.getQqchProjectLinkupManage(qqchProjectLinkupManageParam);
        return AjaxResult.success(qqchProjectLinkupManage);
    }

    @PreAuthorize(hasPermi = "qqchProjectLinkupManage:list")
    @GetMapping("/list")
    public AjaxResult getQqchProjectLinkupManageList(@Validated(ValidationGroups.Select.class) QqchProjectLinkupManage qqchProjectLinkupManageParam) {
        startPage();
        List<QqchProjectLinkupManage> qqchProjectLinkupManageList = qqchProjectLinkupManageService.getQqchProjectLinkupManageList(qqchProjectLinkupManageParam);
        return getDataTableAjaxResult(qqchProjectLinkupManageList);
    }

    @PreAuthorize(hasPermi = "qqchProjectLinkupManage:add")
    @PostMapping("/add")
    public AjaxResult insertQqchProjectLinkupManage(@Validated(ValidationGroups.Save.class) @RequestBody QqchProjectLinkupManage qqchProjectLinkupManageParam) {
        qqchProjectLinkupManageService.insertQqchProjectLinkupManage(qqchProjectLinkupManageParam);
        return AjaxResult.success(qqchProjectLinkupManageParam);
    }

    @PreAuthorize(hasPermi = "qqchProjectLinkupManage:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchProjectLinkupManageList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchProjectLinkupManage> qqchProjectLinkupManageListParam) {
        qqchProjectLinkupManageService.insertQqchProjectLinkupManageList(qqchProjectLinkupManageListParam);
        return AjaxResult.success(qqchProjectLinkupManageListParam);
    }

    @PreAuthorize(hasPermi = "qqchProjectLinkupManage:update")
    @PostMapping("/update")
    public AjaxResult updateQqchProjectLinkupManage(@Validated(ValidationGroups.Update.class) @RequestBody QqchProjectLinkupManage qqchProjectLinkupManageParam) {
        return toAjax(qqchProjectLinkupManageService.updateQqchProjectLinkupManage(qqchProjectLinkupManageParam));
    }

    @PreAuthorize(hasPermi = "qqchProjectLinkupManage:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchProjectLinkupManageList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchProjectLinkupManage> qqchProjectLinkupManageListParam) {
        return toAjax(qqchProjectLinkupManageService.updateQqchProjectLinkupManageList(qqchProjectLinkupManageListParam));
    }

    @PreAuthorize(hasPermi = "qqchProjectLinkupManage:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchProjectLinkupManage(@Validated(ValidationGroups.Delete.class) @RequestBody QqchProjectLinkupManage qqchProjectLinkupManageParam) {
        return toAjax(qqchProjectLinkupManageService.deleteQqchProjectLinkupManage(qqchProjectLinkupManageParam));
    }

    @PreAuthorize(hasPermi = "qqchProjectLinkupManage:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchProjectLinkupManageByPks(@PathVariable Long[] ids) {
        List<Long> qqchProjectLinkupManagePkList = Arrays.asList(ids);
        return toAjax(qqchProjectLinkupManageService.deleteQqchProjectLinkupManageByPks(qqchProjectLinkupManagePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchProjectLinkupManage qqchProjectLinkupManageParam) throws IOException {
        List<QqchProjectLinkupManage> qqchProjectLinkupManageList = qqchProjectLinkupManageService.getQqchProjectLinkupManageList(qqchProjectLinkupManageParam);
        ExcelUtils<QqchProjectLinkupManage> util = new ExcelUtils<>(QqchProjectLinkupManage.class);
        util.exportExcel(response, qqchProjectLinkupManageList, DateUtils.getDate());
    }
}
