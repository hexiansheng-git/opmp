package com.hhwy.pm.qqch.sgch.sche.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.sche.dto.QqchScheDTO;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author mls
 * @date 2023-07-31 11:22:48
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sche")
public class QqchScheController extends BaseController {

    @Autowired
    private IQqchScheService qqchScheService;


    @PreAuthorize(hasPermi = "qqchScheDiff:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) QqchScheDTO dto) {
        return AjaxResult.success(qqchScheService.list(dto));
    }

    @PreAuthorize(hasPermi = "qqchScheDiff:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchScheDTO dto) {
        qqchScheService.save(dto);
        return AjaxResult.success("success");
    }
}
