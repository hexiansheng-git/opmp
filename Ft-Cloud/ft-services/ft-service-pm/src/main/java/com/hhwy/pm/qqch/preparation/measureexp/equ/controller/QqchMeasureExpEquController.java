package com.hhwy.pm.qqch.preparation.measureexp.equ.controller;

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
import com.hhwy.pm.qqch.preparation.measureexp.equ.service.IQqchMeasureExpEquService;
import com.hhwy.pm.qqch.preparation.measureexp.equ.domain.QqchMeasureExpEqu;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:00:16
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureExpEqu")
public class QqchMeasureExpEquController extends BaseController{

    @Autowired
    private IQqchMeasureExpEquService qqchMeasureExpEquService;

                                                                                                                                                                                                                                                                                                                                                    

    @PreAuthorize(hasPermi = "qqchMeasureExpEqu:list")
    @GetMapping
    public AjaxResult getQqchMeasureExpEqu(@Validated(ValidationGroups.Get.class)  QqchMeasureExpEqu qqchMeasureExpEquParam){
        QqchMeasureExpEqu qqchMeasureExpEqu =  qqchMeasureExpEquService.getQqchMeasureExpEqu(qqchMeasureExpEquParam);
        return AjaxResult.success(qqchMeasureExpEqu);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpEqu:list")
    @GetMapping("/list")
    public AjaxResult getQqchMeasureExpEquList(@Validated(ValidationGroups.Select.class) QqchMeasureExpEqu qqchMeasureExpEquParam){
        startPage();
        List<QqchMeasureExpEqu> qqchMeasureExpEquList = qqchMeasureExpEquService.getQqchMeasureExpEquList(qqchMeasureExpEquParam);
        return getDataTableAjaxResult(qqchMeasureExpEquList);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpEqu:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMeasureExpEqu(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpEqu qqchMeasureExpEquParam){
        qqchMeasureExpEquService.insertQqchMeasureExpEqu(qqchMeasureExpEquParam);
        return AjaxResult.success(qqchMeasureExpEquParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpEqu:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMeasureExpEquList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMeasureExpEqu> qqchMeasureExpEquListParam){
        qqchMeasureExpEquService.insertQqchMeasureExpEquList(qqchMeasureExpEquListParam);
        return AjaxResult.success(qqchMeasureExpEquListParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpEqu:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMeasureExpEqu(@Validated(ValidationGroups.Update.class) @RequestBody QqchMeasureExpEqu qqchMeasureExpEquParam){
        return toAjax(qqchMeasureExpEquService.updateQqchMeasureExpEqu(qqchMeasureExpEquParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureExpEqu:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchMeasureExpEquList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMeasureExpEqu> qqchMeasureExpEquListParam){
            return toAjax(qqchMeasureExpEquService.updateQqchMeasureExpEquList(qqchMeasureExpEquListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchMeasureExpEqu:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMeasureExpEqu(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMeasureExpEqu qqchMeasureExpEquParam){
        return toAjax(qqchMeasureExpEquService.deleteQqchMeasureExpEqu(qqchMeasureExpEquParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureExpEqu:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchMeasureExpEquByPks(@PathVariable Long[] ids){
            List<Long> qqchMeasureExpEquPkList = Arrays.asList(ids);
            return toAjax(qqchMeasureExpEquService.deleteQqchMeasureExpEquByPks(qqchMeasureExpEquPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMeasureExpEqu qqchMeasureExpEquParam) throws IOException {
        List<QqchMeasureExpEqu> qqchMeasureExpEquList = qqchMeasureExpEquService.getQqchMeasureExpEquList(qqchMeasureExpEquParam);
        ExcelUtils<QqchMeasureExpEqu> util = new ExcelUtils<>(QqchMeasureExpEqu.class);
        util.exportExcel(response, qqchMeasureExpEquList, DateUtils.getDate());
    }
}
