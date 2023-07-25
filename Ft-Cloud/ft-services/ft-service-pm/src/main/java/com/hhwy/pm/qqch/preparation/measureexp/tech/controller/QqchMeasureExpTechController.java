package com.hhwy.pm.qqch.preparation.measureexp.tech.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.measureexp.tech.domain.QqchMeasureExpTech;
import com.hhwy.pm.qqch.preparation.measureexp.tech.service.IQqchMeasureExpTechService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:01:36
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureExpTech")
public class QqchMeasureExpTechController extends BaseController{

    @Autowired
    private IQqchMeasureExpTechService qqchMeasureExpTechService;

                                                                                                                                                                                                                                                                                                                

    @PreAuthorize(hasPermi = "qqchMeasureExpTech:list")
    @GetMapping
    public AjaxResult getQqchMeasureExpTech(@Validated(ValidationGroups.Get.class) QqchMeasureExpTech qqchMeasureExpTechParam){
        QqchMeasureExpTech qqchMeasureExpTech =  qqchMeasureExpTechService.getQqchMeasureExpTech(qqchMeasureExpTechParam);
        return AjaxResult.success(qqchMeasureExpTech);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpTech:list")
    @GetMapping("/list")
    public AjaxResult getQqchMeasureExpTechList(@Validated(ValidationGroups.Select.class) QqchMeasureExpTech qqchMeasureExpTechParam){
        startPage();
        List<QqchMeasureExpTech> qqchMeasureExpTechList = qqchMeasureExpTechService.getQqchMeasureExpTechList(qqchMeasureExpTechParam);
        return getDataTableAjaxResult(qqchMeasureExpTechList);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpTech:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMeasureExpTech(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpTech qqchMeasureExpTechParam){
        qqchMeasureExpTechService.insertQqchMeasureExpTech(qqchMeasureExpTechParam);
        return AjaxResult.success(qqchMeasureExpTechParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpTech:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMeasureExpTechList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMeasureExpTech> qqchMeasureExpTechListParam){
        qqchMeasureExpTechService.insertQqchMeasureExpTechList(qqchMeasureExpTechListParam);
        return AjaxResult.success(qqchMeasureExpTechListParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpTech:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMeasureExpTech(@Validated(ValidationGroups.Update.class) @RequestBody QqchMeasureExpTech qqchMeasureExpTechParam){
        return toAjax(qqchMeasureExpTechService.updateQqchMeasureExpTech(qqchMeasureExpTechParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureExpTech:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchMeasureExpTechList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMeasureExpTech> qqchMeasureExpTechListParam){
            return toAjax(qqchMeasureExpTechService.updateQqchMeasureExpTechList(qqchMeasureExpTechListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchMeasureExpTech:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMeasureExpTech(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMeasureExpTech qqchMeasureExpTechParam){
        return toAjax(qqchMeasureExpTechService.deleteQqchMeasureExpTech(qqchMeasureExpTechParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureExpTech:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchMeasureExpTechByPks(@PathVariable Long[] ids){
            List<Long> qqchMeasureExpTechPkList = Arrays.asList(ids);
            return toAjax(qqchMeasureExpTechService.deleteQqchMeasureExpTechByPks(qqchMeasureExpTechPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMeasureExpTech qqchMeasureExpTechParam) throws IOException {
        List<QqchMeasureExpTech> qqchMeasureExpTechList = qqchMeasureExpTechService.getQqchMeasureExpTechList(qqchMeasureExpTechParam);
        ExcelUtils<QqchMeasureExpTech> util = new ExcelUtils<>(QqchMeasureExpTech.class);
        util.exportExcel(response, qqchMeasureExpTechList, DateUtils.getDate());
    }
}
