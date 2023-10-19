package com.hhwy.pm.qqch.preparation.doc.tech.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTechVo;
import com.hhwy.utils.exception.CustomBusinessException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.qqch.preparation.doc.tech.service.IQqchDocTechService;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTech;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:25:45
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchDocTech")
public class QqchDocTechController extends BaseController {

    @Autowired
    private IQqchDocTechService qqchDocTechService;

//    @PreAuthorize(hasPermi = "qqchDocTech:list")
    @GetMapping("/list")
    public AjaxResult getQqchDocTechList(BigDecimal version) {
        QqchDocTechVo qqchDocTechVo = qqchDocTechService.getQqchDocTechListVo(version);
        return AjaxResult.success(qqchDocTechVo);
    }

    /**编辑、提交、确认接口
     * @param
     * @return
     */
    @PreAuthorize(hasPermi = "qqchDocTech:add")
    @PostMapping("/save")
    public AjaxResult insertQqchDocTech(@Validated(ValidationGroups.Update.class) @RequestBody QqchDocTechVo qqchDocTechParam) {
        try{
            return AjaxResult.success( qqchDocTechService.insertQqchDocTechListVo(qqchDocTechParam));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
