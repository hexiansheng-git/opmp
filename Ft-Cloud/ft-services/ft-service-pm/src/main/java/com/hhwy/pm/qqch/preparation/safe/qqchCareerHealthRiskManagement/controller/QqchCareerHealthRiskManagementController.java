package com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.domain.QqchCareerHealthRiskManagement;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.domain.vo.QqchCareerHealthRiskManagementVo;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskManagement.service.IQqchCareerHealthRiskManagementService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 15:01:41
 * @remark 8.7.3 职业健康风险管控策划
 */
@Validated
@RestController
@RequestMapping("/qqchCareerHealthRiskManagement")
public class QqchCareerHealthRiskManagementController extends BaseController {

    @Autowired
    private IQqchCareerHealthRiskManagementService qqchCareerHealthRiskManagementService;

    @PreAuthorize(hasPermi = "qqchCareerHealthRiskManagement:list")
    @GetMapping("/list")
    public AjaxResult getQqchCareerHealthRiskManagementList(@Validated(ValidationGroups.Select.class) QqchCareerHealthRiskManagement qqchCareerHealthRiskManagementParam) {
        QqchCareerHealthRiskManagementVo vo = qqchCareerHealthRiskManagementService.getQqchCareerHealthRiskManagementList(qqchCareerHealthRiskManagementParam);
        return AjaxResult.success(vo);
    }

    @PreAuthorize(hasPermi = "qqchCareerHealthRiskManagement:save")
    @PostMapping("/save")
    public AjaxResult insertQqchCareerHealthRiskManagementList(@Validated(ValidationGroups.Save.class) @RequestBody QqchCareerHealthRiskManagementVo vo) {
        qqchCareerHealthRiskManagementService.save(vo);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "qqchCareerHealthRiskManagement:list")
    @GetMapping
    public AjaxResult getQqchCareerHealthRiskManagement(@Validated(ValidationGroups.Get.class) QqchCareerHealthRiskManagement qqchCareerHealthRiskManagementParam) {
        QqchCareerHealthRiskManagement qqchCareerHealthRiskManagement = qqchCareerHealthRiskManagementService.getQqchCareerHealthRiskManagement(qqchCareerHealthRiskManagementParam);
        return AjaxResult.success(qqchCareerHealthRiskManagement);
    }


    @PreAuthorize(hasPermi = "qqchCareerHealthRiskManagement:add")
    @PostMapping("/add")
    public AjaxResult insertQqchCareerHealthRiskManagement(@Validated(ValidationGroups.Save.class) @RequestBody QqchCareerHealthRiskManagement qqchCareerHealthRiskManagementParam) {
        qqchCareerHealthRiskManagementService.insertQqchCareerHealthRiskManagement(qqchCareerHealthRiskManagementParam);
        return AjaxResult.success(qqchCareerHealthRiskManagementParam);
    }

    @PreAuthorize(hasPermi = "qqchCareerHealthRiskManagement:update")
    @PostMapping("/update")
    public AjaxResult updateQqchCareerHealthRiskManagement(@Validated(ValidationGroups.Update.class) @RequestBody QqchCareerHealthRiskManagement qqchCareerHealthRiskManagementParam) {
        return toAjax(qqchCareerHealthRiskManagementService.updateQqchCareerHealthRiskManagement(qqchCareerHealthRiskManagementParam));
    }

    @PreAuthorize(hasPermi = "qqchCareerHealthRiskManagement:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchCareerHealthRiskManagementList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchCareerHealthRiskManagement> qqchCareerHealthRiskManagementListParam) {
        return toAjax(qqchCareerHealthRiskManagementService.updateQqchCareerHealthRiskManagementList(qqchCareerHealthRiskManagementListParam));
    }

    @PreAuthorize(hasPermi = "qqchCareerHealthRiskManagement:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchCareerHealthRiskManagement(@Validated(ValidationGroups.Delete.class) @RequestBody QqchCareerHealthRiskManagement qqchCareerHealthRiskManagementParam) {
        return toAjax(qqchCareerHealthRiskManagementService.deleteQqchCareerHealthRiskManagement(qqchCareerHealthRiskManagementParam));
    }

    @PreAuthorize(hasPermi = "qqchCareerHealthRiskManagement:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchCareerHealthRiskManagementByPks(@PathVariable Long[] ids) {
        List<Long> qqchCareerHealthRiskManagementPkList = Arrays.asList(ids);
        return toAjax(qqchCareerHealthRiskManagementService.deleteQqchCareerHealthRiskManagementByPks(qqchCareerHealthRiskManagementPkList));
    }

}
