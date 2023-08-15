package com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.domain.QqchSafeMostEnvirRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeMostEnvirRiskList.service.IQqchSafeMostEnvirRiskListDetailService;
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
 * @author zq
 * @date 2023-08-14 14:04:04
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchSafeMostEnvirRiskListDetail")
public class QqchSafeMostEnvirRiskListDetailController extends BaseController{

    @Autowired
    private IQqchSafeMostEnvirRiskListDetailService qqchSafeMostEnvirRiskListDetailService;

                                                                                                                                                                                                                

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskListDetail:list")
    @GetMapping
    public AjaxResult getQqchSafeMostEnvirRiskListDetail(@Validated(ValidationGroups.Get.class) QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetailParam){
        QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetail =  qqchSafeMostEnvirRiskListDetailService.getQqchSafeMostEnvirRiskListDetail(qqchSafeMostEnvirRiskListDetailParam);
        return AjaxResult.success(qqchSafeMostEnvirRiskListDetail);
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskListDetail:list")
    @GetMapping("/list")
    public AjaxResult getQqchSafeMostEnvirRiskListDetailList(@Validated(ValidationGroups.Select.class) QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetailParam){
        startPage();
        List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetailList = qqchSafeMostEnvirRiskListDetailService.getQqchSafeMostEnvirRiskListDetailList(qqchSafeMostEnvirRiskListDetailParam);
        return getDataTableAjaxResult(qqchSafeMostEnvirRiskListDetailList);
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskListDetail:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSafeMostEnvirRiskListDetail(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetailParam){
        qqchSafeMostEnvirRiskListDetailService.insertQqchSafeMostEnvirRiskListDetail(qqchSafeMostEnvirRiskListDetailParam);
        return AjaxResult.success(qqchSafeMostEnvirRiskListDetailParam);
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskListDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSafeMostEnvirRiskListDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetailListParam){
        qqchSafeMostEnvirRiskListDetailService.insertQqchSafeMostEnvirRiskListDetailList(qqchSafeMostEnvirRiskListDetailListParam);
        return AjaxResult.success(qqchSafeMostEnvirRiskListDetailListParam);
    }

    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskListDetail:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSafeMostEnvirRiskListDetail(@Validated(ValidationGroups.Update.class) @RequestBody QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetailParam){
        return toAjax(qqchSafeMostEnvirRiskListDetailService.updateQqchSafeMostEnvirRiskListDetail(qqchSafeMostEnvirRiskListDetailParam));
    }

            @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskListDetail:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchSafeMostEnvirRiskListDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetailListParam){
            return toAjax(qqchSafeMostEnvirRiskListDetailService.updateQqchSafeMostEnvirRiskListDetailList(qqchSafeMostEnvirRiskListDetailListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskListDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSafeMostEnvirRiskListDetail(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetailParam){
        return toAjax(qqchSafeMostEnvirRiskListDetailService.deleteQqchSafeMostEnvirRiskListDetail(qqchSafeMostEnvirRiskListDetailParam));
    }

            @PreAuthorize(hasPermi = "qqchSafeMostEnvirRiskListDetail:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchSafeMostEnvirRiskListDetailByPks(@PathVariable Long[] ids){
            List<Long> qqchSafeMostEnvirRiskListDetailPkList = Arrays.asList(ids);
            return toAjax(qqchSafeMostEnvirRiskListDetailService.deleteQqchSafeMostEnvirRiskListDetailByPks(qqchSafeMostEnvirRiskListDetailPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSafeMostEnvirRiskListDetail qqchSafeMostEnvirRiskListDetailParam) throws IOException {
        List<QqchSafeMostEnvirRiskListDetail> qqchSafeMostEnvirRiskListDetailList = qqchSafeMostEnvirRiskListDetailService.getQqchSafeMostEnvirRiskListDetailList(qqchSafeMostEnvirRiskListDetailParam);
        ExcelUtils<QqchSafeMostEnvirRiskListDetail> util = new ExcelUtils<>(QqchSafeMostEnvirRiskListDetail.class);
        util.exportExcel(response, qqchSafeMostEnvirRiskListDetailList, DateUtils.getDate());
    }
}
