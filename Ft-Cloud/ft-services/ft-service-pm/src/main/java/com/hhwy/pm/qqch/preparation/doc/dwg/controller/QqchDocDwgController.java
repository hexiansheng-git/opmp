package com.hhwy.pm.qqch.preparation.doc.dwg.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.doc.dwg.domain.QqchDocDwgVo;
import com.hhwy.pm.qqch.preparation.doc.tech.domain.QqchDocTechVo;
import com.hhwy.utils.exception.CustomBusinessException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.qqch.preparation.doc.dwg.service.IQqchDocDwgService;
import com.hhwy.pm.qqch.preparation.doc.dwg.domain.QqchDocDwg;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:25:42
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchDocDwg")
public class QqchDocDwgController extends BaseController {

    @Autowired
    private IQqchDocDwgService qqchDocDwgService;


//    @PreAuthorize(hasPermi = "qqchDocDwg:list")
    @GetMapping("/list")
    public AjaxResult getQqchDocDwgList(BigDecimal version) {
        QqchDocDwgVo qqchDocDwg = qqchDocDwgService.getQqchDocDwgVo(version);
        return AjaxResult.success(qqchDocDwg);
    }

    /**编辑、提交、确认接口
     * @param
     * @return
     */
//    @PreAuthorize(hasPermi = "qqchDocTech:add")
    @PostMapping("/save")
    public AjaxResult insertQqchDocTech(@Validated(ValidationGroups.Update.class) @RequestBody QqchDocDwgVo qqchDocDwgVo) {
        try{
            return AjaxResult.success( qqchDocDwgService.insertQqchDocDwgVo(qqchDocDwgVo));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }
}
