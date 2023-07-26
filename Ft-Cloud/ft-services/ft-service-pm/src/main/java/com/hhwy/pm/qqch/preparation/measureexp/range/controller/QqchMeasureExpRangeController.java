package com.hhwy.pm.qqch.preparation.measureexp.range.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpRangeService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-07-25 18:01:34
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchMeasureExpRange")
public class QqchMeasureExpRangeController extends BaseController{

    @Autowired
    private IQqchMeasureExpRangeService qqchMeasureExpRangeService;

                                                                                                                                                                                                                                                                                        

    @PreAuthorize(hasPermi = "qqchMeasureExpRange:list")
    @GetMapping
    public AjaxResult getQqchMeasureExpRange(@Validated(ValidationGroups.Get.class)  QqchMeasureExpRange qqchMeasureExpRangeParam){
        QqchMeasureExpRange qqchMeasureExpRange =  qqchMeasureExpRangeService.getQqchMeasureExpRange(qqchMeasureExpRangeParam);
        return AjaxResult.success(qqchMeasureExpRange);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpRange:list")
    @GetMapping("/list")
    public AjaxResult getQqchMeasureExpRangeList(@Validated(ValidationGroups.Select.class) QqchMeasureExpRange qqchMeasureExpRangeParam){
        startPage();
        List<QqchMeasureExpRange> qqchMeasureExpRangeList = qqchMeasureExpRangeService.getQqchMeasureExpRangeList(qqchMeasureExpRangeParam);
        return getDataTableAjaxResult(qqchMeasureExpRangeList);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpRange:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMeasureExpRange(@Validated(ValidationGroups.Save.class) @RequestBody QqchMeasureExpRange qqchMeasureExpRangeParam){
        qqchMeasureExpRangeService.insertQqchMeasureExpRange(qqchMeasureExpRangeParam);
        return AjaxResult.success(qqchMeasureExpRangeParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpRange:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMeasureExpRangeList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMeasureExpRange> qqchMeasureExpRangeListParam){
        qqchMeasureExpRangeService.insertQqchMeasureExpRangeList(qqchMeasureExpRangeListParam);
        return AjaxResult.success(qqchMeasureExpRangeListParam);
    }

    @PreAuthorize(hasPermi = "qqchMeasureExpRange:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMeasureExpRange(@Validated(ValidationGroups.Update.class) @RequestBody QqchMeasureExpRange qqchMeasureExpRangeParam){
        return toAjax(qqchMeasureExpRangeService.updateQqchMeasureExpRange(qqchMeasureExpRangeParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureExpRange:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchMeasureExpRangeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMeasureExpRange> qqchMeasureExpRangeListParam){
            return toAjax(qqchMeasureExpRangeService.updateQqchMeasureExpRangeList(qqchMeasureExpRangeListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchMeasureExpRange:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMeasureExpRange(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMeasureExpRange qqchMeasureExpRangeParam){
        return toAjax(qqchMeasureExpRangeService.deleteQqchMeasureExpRange(qqchMeasureExpRangeParam));
    }

            @PreAuthorize(hasPermi = "qqchMeasureExpRange:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchMeasureExpRangeByPks(@PathVariable Long[] ids){
            List<Long> qqchMeasureExpRangePkList = Arrays.asList(ids);
            return toAjax(qqchMeasureExpRangeService.deleteQqchMeasureExpRangeByPks(qqchMeasureExpRangePkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMeasureExpRange qqchMeasureExpRangeParam) throws IOException {
        List<QqchMeasureExpRange> qqchMeasureExpRangeList = qqchMeasureExpRangeService.getQqchMeasureExpRangeList(qqchMeasureExpRangeParam);
        ExcelUtils<QqchMeasureExpRange> util = new ExcelUtils<>(QqchMeasureExpRange.class);
        util.exportExcel(response, qqchMeasureExpRangeList, DateUtils.getDate());
    }
}
