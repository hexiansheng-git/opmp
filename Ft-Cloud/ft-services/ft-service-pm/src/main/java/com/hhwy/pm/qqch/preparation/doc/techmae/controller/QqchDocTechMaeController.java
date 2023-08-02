package com.hhwy.pm.qqch.preparation.doc.techmae.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTechVo;
import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMaeV0;
import com.hhwy.utils.exception.CustomBusinessException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.qqch.preparation.doc.techmae.service.IQqchDocTechMaeService;
import com.hhwy.pm.qqch.preparation.doc.techmae.domain.QqchDocTechMae;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:25:47
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchDocTechMae")
public class QqchDocTechMaeController extends BaseController {

    @Autowired
    private IQqchDocTechMaeService qqchDocTechMaeService;

    @PreAuthorize(hasPermi = "qqchDocTechMae:list")
    @GetMapping("/list")
    public AjaxResult getQqchDocTechMaeList(BigDecimal version) {
        QqchDocTechMaeV0 qqchDocTechVo = qqchDocTechMaeService.geteQqchDocTechMaeVo(version);
        return AjaxResult.success(qqchDocTechVo);
    }

    /**编辑、提交、确认接口
     * @param
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDocTech:add")
    @PostMapping("/save")
    public AjaxResult insertQqchDocTech(@Validated(ValidationGroups.Update.class) @RequestBody QqchDocTechMaeV0 qqchDocTechParam) {
        try{
            return AjaxResult.success( qqchDocTechMaeService.inserteQqchDocTechMaeVo(qqchDocTechParam));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

}
