package com.hhwy.pm.qqch.preparation.survey.organization.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganization;
import com.hhwy.pm.qqch.preparation.survey.organization.service.IQqchSurveyOrganizationService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-19 15:37:23
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchSurveyOrganization")
public class QqchSurveyOrganizationController extends BaseController {

    @Autowired
    private IQqchSurveyOrganizationService qqchSurveyOrganizationService;



    @PreAuthorize(hasPermi = "qqchSurveyOrganization:list")
    @GetMapping
    public AjaxResult getQqchSurveyOrganization(@Validated(ValidationGroups.Get.class) QqchSurveyOrganization qqchSurveyOrganizationParam) {
        QqchSurveyOrganization qqchSurveyOrganization = qqchSurveyOrganizationService.getQqchSurveyOrganization(qqchSurveyOrganizationParam);
        return AjaxResult.success(qqchSurveyOrganization);
    }

    /**
     *  列表查询
     * @param qqchSurveyOrganizationParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyOrganization:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyOrganizationList(@Validated(ValidationGroups.Select.class) QqchSurveyOrganization qqchSurveyOrganizationParam) {
        //startPage();
        List<QqchSurveyOrganization> qqchSurveyOrganizationList = qqchSurveyOrganizationService.getQqchSurveyOrganizationList(qqchSurveyOrganizationParam);
        return getDataTableAjaxResult(qqchSurveyOrganizationList);
    }

    @PreAuthorize(hasPermi = "qqchSurveyOrganization:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSurveyOrganization(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyOrganization qqchSurveyOrganizationParam) {
        qqchSurveyOrganizationService.insertQqchSurveyOrganization(qqchSurveyOrganizationParam);
        return AjaxResult.success(qqchSurveyOrganizationParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyOrganization:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSurveyOrganizationList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSurveyOrganization> qqchSurveyOrganizationListParam) {
        qqchSurveyOrganizationService.insertQqchSurveyOrganizationList(qqchSurveyOrganizationListParam);
        return AjaxResult.success(qqchSurveyOrganizationListParam);
    }

    @PreAuthorize(hasPermi = "qqchSurveyOrganization:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSurveyOrganization(@Validated(ValidationGroups.Update.class) @RequestBody QqchSurveyOrganization qqchSurveyOrganizationParam) {
        return toAjax(qqchSurveyOrganizationService.updateQqchSurveyOrganization(qqchSurveyOrganizationParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyOrganization:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSurveyOrganizationList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSurveyOrganization> qqchSurveyOrganizationListParam) {
        return toAjax(qqchSurveyOrganizationService.updateQqchSurveyOrganizationList(qqchSurveyOrganizationListParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyOrganization:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSurveyOrganization(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSurveyOrganization qqchSurveyOrganizationParam) {
        return toAjax(qqchSurveyOrganizationService.deleteQqchSurveyOrganization(qqchSurveyOrganizationParam));
    }

    @PreAuthorize(hasPermi = "qqchSurveyOrganization:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSurveyOrganizationByPks(@PathVariable Long[] ids) {
        List<Long> qqchSurveyOrganizationPkList = Arrays.asList(ids);
        return toAjax(qqchSurveyOrganizationService.deleteQqchSurveyOrganizationByPks(qqchSurveyOrganizationPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSurveyOrganization qqchSurveyOrganizationParam) throws IOException {
        List<QqchSurveyOrganization> qqchSurveyOrganizationList = qqchSurveyOrganizationService.getQqchSurveyOrganizationList(qqchSurveyOrganizationParam);
        ExcelUtils<QqchSurveyOrganization> util = new ExcelUtils<>(QqchSurveyOrganization.class);
        util.exportExcel(response, qqchSurveyOrganizationList, DateUtils.getDate());
    }
}
