package com.hhwy.pm.qqch.preparation.doc.techmae.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
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
public class QqchDocTechMaeController extends BaseController{

    @Autowired
    private IQqchDocTechMaeService qqchDocTechMaeService;

                                                                                                                                                                                                                                                                            

    @PreAuthorize(hasPermi = "qqchDocTechMae:list")
    @GetMapping
    public AjaxResult getQqchDocTechMae(@Validated(ValidationGroups.Get.class)  QqchDocTechMae qqchDocTechMaeParam){
        QqchDocTechMae qqchDocTechMae =  qqchDocTechMaeService.getQqchDocTechMae(qqchDocTechMaeParam);
        return AjaxResult.success(qqchDocTechMae);
    }

    @PreAuthorize(hasPermi = "qqchDocTechMae:list")
    @GetMapping("/list")
    public AjaxResult getQqchDocTechMaeList(@Validated(ValidationGroups.Select.class) QqchDocTechMae qqchDocTechMaeParam){
        startPage();
        List<QqchDocTechMae> qqchDocTechMaeList = qqchDocTechMaeService.getQqchDocTechMaeList(qqchDocTechMaeParam);
        return getDataTableAjaxResult(qqchDocTechMaeList);
    }

    @PreAuthorize(hasPermi = "qqchDocTechMae:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDocTechMae(@Validated(ValidationGroups.Save.class) @RequestBody QqchDocTechMae qqchDocTechMaeParam){
        qqchDocTechMaeService.insertQqchDocTechMae(qqchDocTechMaeParam);
        return AjaxResult.success(qqchDocTechMaeParam);
    }

    @PreAuthorize(hasPermi = "qqchDocTechMae:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDocTechMaeList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchDocTechMae> qqchDocTechMaeListParam){
        qqchDocTechMaeService.insertQqchDocTechMaeList(qqchDocTechMaeListParam);
        return AjaxResult.success(qqchDocTechMaeListParam);
    }

    @PreAuthorize(hasPermi = "qqchDocTechMae:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDocTechMae(@Validated(ValidationGroups.Update.class) @RequestBody QqchDocTechMae qqchDocTechMaeParam){
        return toAjax(qqchDocTechMaeService.updateQqchDocTechMae(qqchDocTechMaeParam));
    }

            @PreAuthorize(hasPermi = "qqchDocTechMae:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchDocTechMaeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDocTechMae> qqchDocTechMaeListParam){
            return toAjax(qqchDocTechMaeService.updateQqchDocTechMaeList(qqchDocTechMaeListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchDocTechMae:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDocTechMae(@Validated(ValidationGroups.Delete.class) @RequestBody QqchDocTechMae qqchDocTechMaeParam){
        return toAjax(qqchDocTechMaeService.deleteQqchDocTechMae(qqchDocTechMaeParam));
    }

            @PreAuthorize(hasPermi = "qqchDocTechMae:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchDocTechMaeByPks(@PathVariable Long[] ids){
            List<Long> qqchDocTechMaePkList = Arrays.asList(ids);
            return toAjax(qqchDocTechMaeService.deleteQqchDocTechMaeByPks(qqchDocTechMaePkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDocTechMae qqchDocTechMaeParam) throws IOException {
        List<QqchDocTechMae> qqchDocTechMaeList = qqchDocTechMaeService.getQqchDocTechMaeList(qqchDocTechMaeParam);
        ExcelUtils<QqchDocTechMae> util = new ExcelUtils<>(QqchDocTechMae.class);
        util.exportExcel(response, qqchDocTechMaeList, DateUtils.getDate());
    }
}
