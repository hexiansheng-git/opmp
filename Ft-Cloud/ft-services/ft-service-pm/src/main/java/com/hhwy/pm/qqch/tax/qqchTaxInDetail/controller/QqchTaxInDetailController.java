package com.hhwy.pm.qqch.tax.qqchTaxInDetail.controller;

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
import com.hhwy.pm.qqch.tax.qqchTaxInDetail.service.IQqchTaxInDetailService;
import com.hhwy.pm.qqch.tax.qqchTaxInDetail.domain.QqchTaxInDetail;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-08-09 18:17:35
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchTaxInDetail")
public class QqchTaxInDetailController extends BaseController{

    @Autowired
    private IQqchTaxInDetailService qqchTaxInDetailService;

                                                                                                                                                                                                                                                                                                                                                                                                    

    @PreAuthorize(hasPermi = "qqchTaxInDetail:list")
    @GetMapping
    public AjaxResult getQqchTaxInDetail(@Validated(ValidationGroups.Get.class)  QqchTaxInDetail qqchTaxInDetailParam){
        QqchTaxInDetail qqchTaxInDetail =  qqchTaxInDetailService.getQqchTaxInDetail(qqchTaxInDetailParam);
        return AjaxResult.success(qqchTaxInDetail);
    }

    @PreAuthorize(hasPermi = "qqchTaxInDetail:list")
    @GetMapping("/list")
    public AjaxResult getQqchTaxInDetailList(@Validated(ValidationGroups.Select.class) QqchTaxInDetail qqchTaxInDetailParam){
        startPage();
        List<QqchTaxInDetail> qqchTaxInDetailList = qqchTaxInDetailService.getQqchTaxInDetailList(qqchTaxInDetailParam);
        return getDataTableAjaxResult(qqchTaxInDetailList);
    }

    @PreAuthorize(hasPermi = "qqchTaxInDetail:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTaxInDetail(@Validated(ValidationGroups.Save.class) @RequestBody QqchTaxInDetail qqchTaxInDetailParam){
        qqchTaxInDetailService.insertQqchTaxInDetail(qqchTaxInDetailParam);
        return AjaxResult.success(qqchTaxInDetailParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxInDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxInDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxInDetail> qqchTaxInDetailListParam){
        qqchTaxInDetailService.insertQqchTaxInDetailList(qqchTaxInDetailListParam);
        return AjaxResult.success(qqchTaxInDetailListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxInDetail:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxInDetail(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxInDetail qqchTaxInDetailParam){
        return toAjax(qqchTaxInDetailService.updateQqchTaxInDetail(qqchTaxInDetailParam));
    }

            @PreAuthorize(hasPermi = "qqchTaxInDetail:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchTaxInDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxInDetail> qqchTaxInDetailListParam){
            return toAjax(qqchTaxInDetailService.updateQqchTaxInDetailList(qqchTaxInDetailListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchTaxInDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxInDetail(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxInDetail qqchTaxInDetailParam){
        return toAjax(qqchTaxInDetailService.deleteQqchTaxInDetail(qqchTaxInDetailParam));
    }

            @PreAuthorize(hasPermi = "qqchTaxInDetail:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchTaxInDetailByPks(@PathVariable Long[] ids){
            List<Long> qqchTaxInDetailPkList = Arrays.asList(ids);
            return toAjax(qqchTaxInDetailService.deleteQqchTaxInDetailByPks(qqchTaxInDetailPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxInDetail qqchTaxInDetailParam) throws IOException {
        List<QqchTaxInDetail> qqchTaxInDetailList = qqchTaxInDetailService.getQqchTaxInDetailList(qqchTaxInDetailParam);
        ExcelUtils<QqchTaxInDetail> util = new ExcelUtils<>(QqchTaxInDetail.class);
        util.exportExcel(response, qqchTaxInDetailList, DateUtils.getDate());
    }
}
