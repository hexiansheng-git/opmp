package com.hhwy.pm.qqch.preparation.survey.organization.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganization;
import com.hhwy.pm.qqch.preparation.survey.organization.domain.QqchSurveyOrganizationVo;
import com.hhwy.pm.qqch.preparation.survey.organization.service.IQqchSurveyOrganizationService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ldd
 * @date 2023-07-19 15:37:23
 * @remark  2.1.2 项目部勘察设计组织机构

 */
@Validated
@RestController
@RequestMapping("/qqchSurveyOrganization")
public class QqchSurveyOrganizationController extends BaseController {

    @Autowired
    private IQqchSurveyOrganizationService qqchSurveyOrganizationService;

    /**
     *  列表查询
     * @param qqchSurveyOrganizationParam
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyOrganization:list")
    @GetMapping("/list")
    public AjaxResult getQqchSurveyOrganizationList(@Validated(ValidationGroups.Select.class) QqchSurveyOrganization qqchSurveyOrganizationParam) {
        QqchSurveyOrganizationVo vo = qqchSurveyOrganizationService.getQqchSurveyOrganizationList(qqchSurveyOrganizationParam);
        return AjaxResult.success(vo);
    }


    /**
     * 批增
     *
     * @param qqchSurveyOrganizationVo
     *
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyOrganization:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSurveyOrganizationList(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyOrganizationVo qqchSurveyOrganizationVo) {
        qqchSurveyOrganizationService.save(qqchSurveyOrganizationVo);
        return AjaxResult.success(qqchSurveyOrganizationVo);
    }

    /**
     * 确认
     *
     * @param qqchSurveyOrganizationVo
     *
     * @return
     */
    @PreAuthorize(hasPermi = "qqchSurveyOrganization:confirm")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchSurveyOrganizationVo qqchSurveyOrganizationVo) {
        qqchSurveyOrganizationService.confirm(qqchSurveyOrganizationVo);
        return AjaxResult.success(qqchSurveyOrganizationVo);
    }


}
