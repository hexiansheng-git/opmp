package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeChangeOrganization;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeChangeOrganizationService;
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
 * @author han
 * @date 2023-07-07 18:35:50
 * @remark 优化变更组织策划
 */
@Validated
@RestController
@RequestMapping("/qqchOptimizeChangeOrganization")
public class QqchOptimizeChangeOrganizationController extends BaseController {

    @Autowired
    private IQqchOptimizeChangeOrganizationService qqchOptimizeChangeOrganizationService;


    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:list")
    @GetMapping
    public AjaxResult getQqchOptimizeChangeOrganization(@Validated(ValidationGroups.Get.class) @RequestBody QqchOptimizeChangeOrganization qqchOptimizeChangeOrganizationParam) {
        QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization = qqchOptimizeChangeOrganizationService.getQqchOptimizeChangeOrganization(qqchOptimizeChangeOrganizationParam);
        return AjaxResult.success(qqchOptimizeChangeOrganization);
    }

    /**
     * 优化变更组织策划台账
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:list")
    @GetMapping("/treeList")
    public AjaxResult getQqchOptimizeChangeOrganizationTreeList() {
        List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList = qqchOptimizeChangeOrganizationService.getQqchOptimizeChangeOrganizationTreeList();
        return AjaxResult.success(qqchOptimizeChangeOrganizationList);
    }

    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:list")
    @GetMapping("/list")
    public AjaxResult getQqchOptimizeChangeOrganizationList(@Validated(ValidationGroups.Select.class) @RequestBody QqchOptimizeChangeOrganization qqchOptimizeChangeOrganizationParam) {
        startPage();
        List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList = qqchOptimizeChangeOrganizationService.getQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationParam);
        return getDataTableAjaxResult(qqchOptimizeChangeOrganizationList);
    }

    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:add")
    @PostMapping("/add")
    public AjaxResult insertQqchOptimizeChangeOrganization(@Validated(ValidationGroups.Save.class) @RequestBody QqchOptimizeChangeOrganization qqchOptimizeChangeOrganizationParam) {
        qqchOptimizeChangeOrganizationService.insertQqchOptimizeChangeOrganization(qqchOptimizeChangeOrganizationParam);
        return AjaxResult.success(qqchOptimizeChangeOrganizationParam);
    }

    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchOptimizeChangeOrganizationList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationListParam) {
        qqchOptimizeChangeOrganizationService.insertQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationListParam);
        return AjaxResult.success(qqchOptimizeChangeOrganizationListParam);
    }

    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:update")
    @PostMapping("/update")
    public AjaxResult updateQqchOptimizeChangeOrganization(@Validated(ValidationGroups.Update.class) @RequestBody QqchOptimizeChangeOrganization qqchOptimizeChangeOrganizationParam) {
        return toAjax(qqchOptimizeChangeOrganizationService.updateQqchOptimizeChangeOrganization(qqchOptimizeChangeOrganizationParam));
    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchOptimizeChangeOrganizationListParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:update")
    @PostMapping("/batchEdit")
    public AjaxResult editQqchOptimizeChangeOrganizationList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationListParam) {
        return toAjax(qqchOptimizeChangeOrganizationService.editQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationListParam));
    }

    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchOptimizeChangeOrganizationList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationListParam) {
        return toAjax(qqchOptimizeChangeOrganizationService.updateQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationListParam));
    }

    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchOptimizeChangeOrganization(@Validated(ValidationGroups.Delete.class) @RequestBody QqchOptimizeChangeOrganization qqchOptimizeChangeOrganizationParam) {
        return toAjax(qqchOptimizeChangeOrganizationService.deleteQqchOptimizeChangeOrganization(qqchOptimizeChangeOrganizationParam));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PreAuthorize(hasPermi = "qqchOptimizeChangeOrganization:remove")
    @PostMapping("/remove/{ids}")
    public AjaxResult deleteQqchOptimizeChangeOrganizationByPks(@PathVariable Long[] ids) {
        List<Long> qqchOptimizeChangeOrganizationPkList = Arrays.asList(ids);
        return toAjax(qqchOptimizeChangeOrganizationService.deleteQqchOptimizeChangeOrganizationByPks(qqchOptimizeChangeOrganizationPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchOptimizeChangeOrganization qqchOptimizeChangeOrganizationParam) throws IOException {
        List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList = qqchOptimizeChangeOrganizationService.getQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationParam);
        ExcelUtils<QqchOptimizeChangeOrganization> util = new ExcelUtils<>(QqchOptimizeChangeOrganization.class);
        util.exportExcel(response, qqchOptimizeChangeOrganizationList, DateUtils.getDate());
    }
}
