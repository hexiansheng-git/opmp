package com.hhwy.pm.qqch.preparation.survey.document.controller;

import com.hhwy.pm.qqch.preparation.survey.document.domain.vo.QqchManageProcedureVo;
import com.hhwy.pm.qqch.preparation.survey.document.service.IQqchManageProcedureService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-13 11:40:23
 * @remark 管理程序
 */
@Validated
@RestController
@RequestMapping("/qqchManageProcedure")
public class QqchManageProcedureController extends BaseController {

    @Autowired
    private IQqchManageProcedureService qqchManageProcedureService;

    /**
     * 台账
     * @return
     */
    @PreAuthorize(hasPermi = "qqchManageProcedure:list")
    @GetMapping("/list")
    public AjaxResult getQqchManageProcedureList(BigDecimal version) {
        QqchManageProcedureVo qqchManageProcedureVo = qqchManageProcedureService.getQqchManageProcedureVo(version);
        return AjaxResult.success(qqchManageProcedureVo);
    }

    /**
     * 保存
     * @param qqchManageProcedureVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchManageProcedure:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchManageProcedureVo qqchManageProcedureVo) {
        qqchManageProcedureService.save(qqchManageProcedureVo);
        return AjaxResult.success();
    }

    /**
     * 确认
     * @param qqchManageProcedureVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchManageProcedure:add")
    @PostMapping("/confirm")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody QqchManageProcedureVo qqchManageProcedureVo) {
        qqchManageProcedureService.confirm(qqchManageProcedureVo);
        return AjaxResult.success();
    }
}
