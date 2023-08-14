package com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.domain.QqchSafeEnvirRiskListDetail;
import com.hhwy.pm.qqch.preparation.safe.qqchSafeEnvirRiskList.service.IQqchSafeEnvirRiskListDetailService;
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
 * @date 2023-08-14 14:00:39
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchSafeEnvirRiskListDetail")
public class QqchSafeEnvirRiskListDetailController extends BaseController{

    @Autowired
    private IQqchSafeEnvirRiskListDetailService qqchSafeEnvirRiskListDetailService;

                                                                                                                                                                                                                                                    

    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskListDetail:list")
    @GetMapping
    public AjaxResult getQqchSafeEnvirRiskListDetail(@Validated(ValidationGroups.Get.class) QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetailParam){
        QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetail =  qqchSafeEnvirRiskListDetailService.getQqchSafeEnvirRiskListDetail(qqchSafeEnvirRiskListDetailParam);
        return AjaxResult.success(qqchSafeEnvirRiskListDetail);
    }

    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskListDetail:list")
    @GetMapping("/list")
    public AjaxResult getQqchSafeEnvirRiskListDetailList(@Validated(ValidationGroups.Select.class) QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetailParam){
        startPage();
        List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetailList = qqchSafeEnvirRiskListDetailService.getQqchSafeEnvirRiskListDetailList(qqchSafeEnvirRiskListDetailParam);
        return getDataTableAjaxResult(qqchSafeEnvirRiskListDetailList);
    }

    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskListDetail:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSafeEnvirRiskListDetail(@Validated(ValidationGroups.Save.class) @RequestBody QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetailParam){
        qqchSafeEnvirRiskListDetailService.insertQqchSafeEnvirRiskListDetail(qqchSafeEnvirRiskListDetailParam);
        return AjaxResult.success(qqchSafeEnvirRiskListDetailParam);
    }

    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskListDetail:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSafeEnvirRiskListDetailList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetailListParam){
        qqchSafeEnvirRiskListDetailService.insertQqchSafeEnvirRiskListDetailList(qqchSafeEnvirRiskListDetailListParam);
        return AjaxResult.success(qqchSafeEnvirRiskListDetailListParam);
    }

    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskListDetail:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSafeEnvirRiskListDetail(@Validated(ValidationGroups.Update.class) @RequestBody QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetailParam){
        return toAjax(qqchSafeEnvirRiskListDetailService.updateQqchSafeEnvirRiskListDetail(qqchSafeEnvirRiskListDetailParam));
    }

            @PreAuthorize(hasPermi = "qqchSafeEnvirRiskListDetail:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchSafeEnvirRiskListDetailList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetailListParam){
            return toAjax(qqchSafeEnvirRiskListDetailService.updateQqchSafeEnvirRiskListDetailList(qqchSafeEnvirRiskListDetailListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchSafeEnvirRiskListDetail:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSafeEnvirRiskListDetail(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetailParam){
        return toAjax(qqchSafeEnvirRiskListDetailService.deleteQqchSafeEnvirRiskListDetail(qqchSafeEnvirRiskListDetailParam));
    }

            @PreAuthorize(hasPermi = "qqchSafeEnvirRiskListDetail:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchSafeEnvirRiskListDetailByPks(@PathVariable Long[] ids){
            List<Long> qqchSafeEnvirRiskListDetailPkList = Arrays.asList(ids);
            return toAjax(qqchSafeEnvirRiskListDetailService.deleteQqchSafeEnvirRiskListDetailByPks(qqchSafeEnvirRiskListDetailPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSafeEnvirRiskListDetail qqchSafeEnvirRiskListDetailParam) throws IOException {
        List<QqchSafeEnvirRiskListDetail> qqchSafeEnvirRiskListDetailList = qqchSafeEnvirRiskListDetailService.getQqchSafeEnvirRiskListDetailList(qqchSafeEnvirRiskListDetailParam);
        ExcelUtils<QqchSafeEnvirRiskListDetail> util = new ExcelUtils<>(QqchSafeEnvirRiskListDetail.class);
        util.exportExcel(response, qqchSafeEnvirRiskListDetailList, DateUtils.getDate());
    }
}
