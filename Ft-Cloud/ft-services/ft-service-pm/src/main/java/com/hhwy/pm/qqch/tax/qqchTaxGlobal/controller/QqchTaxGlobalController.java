package com.hhwy.pm.qqch.tax.qqchTaxGlobal.controller;

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
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.service.IQqchTaxGlobalService;
import com.hhwy.pm.qqch.tax.qqchTaxGlobal.domain.QqchTaxGlobal;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-08-17 16:19:06
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchTaxGlobal")
public class QqchTaxGlobalController extends BaseController{

    @Autowired
    private IQqchTaxGlobalService qqchTaxGlobalService;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    

    @PreAuthorize(hasPermi = "qqchTaxGlobal:list")
    @GetMapping
    public AjaxResult getQqchTaxGlobal(@Validated(ValidationGroups.Get.class)  QqchTaxGlobal qqchTaxGlobalParam){
        QqchTaxGlobal qqchTaxGlobal =  qqchTaxGlobalService.getQqchTaxGlobal(qqchTaxGlobalParam);
        return AjaxResult.success(qqchTaxGlobal);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:list")
    @GetMapping("/list")
    public AjaxResult getQqchTaxGlobalList(@Validated(ValidationGroups.Select.class) QqchTaxGlobal qqchTaxGlobalParam){
        startPage();
        List<QqchTaxGlobal> qqchTaxGlobalList = qqchTaxGlobalService.getQqchTaxGlobalList(qqchTaxGlobalParam);
        return getDataTableAjaxResult(qqchTaxGlobalList);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTaxGlobal(@Validated(ValidationGroups.Save.class) @RequestBody QqchTaxGlobal qqchTaxGlobalParam){
        qqchTaxGlobalService.insertQqchTaxGlobal(qqchTaxGlobalParam);
        return AjaxResult.success(qqchTaxGlobalParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxGlobalList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxGlobal> qqchTaxGlobalListParam){
        qqchTaxGlobalService.insertQqchTaxGlobalList(qqchTaxGlobalListParam);
        return AjaxResult.success(qqchTaxGlobalListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxGlobal:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxGlobal(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxGlobal qqchTaxGlobalParam){
        return toAjax(qqchTaxGlobalService.updateQqchTaxGlobal(qqchTaxGlobalParam));
    }

            @PreAuthorize(hasPermi = "qqchTaxGlobal:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchTaxGlobalList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxGlobal> qqchTaxGlobalListParam){
            return toAjax(qqchTaxGlobalService.updateQqchTaxGlobalList(qqchTaxGlobalListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchTaxGlobal:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxGlobal(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxGlobal qqchTaxGlobalParam){
        return toAjax(qqchTaxGlobalService.deleteQqchTaxGlobal(qqchTaxGlobalParam));
    }

            @PreAuthorize(hasPermi = "qqchTaxGlobal:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchTaxGlobalByPks(@PathVariable Long[] ids){
            List<Long> qqchTaxGlobalPkList = Arrays.asList(ids);
            return toAjax(qqchTaxGlobalService.deleteQqchTaxGlobalByPks(qqchTaxGlobalPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxGlobal qqchTaxGlobalParam) throws IOException {
        List<QqchTaxGlobal> qqchTaxGlobalList = qqchTaxGlobalService.getQqchTaxGlobalList(qqchTaxGlobalParam);
        ExcelUtils<QqchTaxGlobal> util = new ExcelUtils<>(QqchTaxGlobal.class);
        util.exportExcel(response, qqchTaxGlobalList, DateUtils.getDate());
    }
}
