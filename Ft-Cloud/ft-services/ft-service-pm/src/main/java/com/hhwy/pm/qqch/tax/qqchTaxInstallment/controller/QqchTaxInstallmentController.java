package com.hhwy.pm.qqch.tax.qqchTaxInstallment.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInService;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.vo.InstallmentVO;
import com.hhwy.pm.qqch.utils.VersionUtil;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.service.IQqchTaxInstallmentService;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.domain.QqchTaxInstallment;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author mls
 * @date 2023-08-14 00:02:28
 * @remark 
 */
@Validated
@RestController
@RequestMapping("/qqchTaxInstallment")
public class QqchTaxInstallmentController extends BaseController{

    @Autowired
    private IQqchTaxInstallmentService qqchTaxInstallmentService;
    
    @Autowired
    private IQqchTaxInService qqchTaxInService;
    private static final String TN = "qqch_tax_installment";
                                                                                                                                                                                                                                                                                                                                        

    @PreAuthorize(hasPermi = "qqchTaxInstallment:list")
    @GetMapping
    public AjaxResult getQqchTaxInstallment(@Validated(ValidationGroups.Get.class)  QqchTaxInstallment qqchTaxInstallmentParam){
        QqchTaxInstallment qqchTaxInstallment =  qqchTaxInstallmentService.getQqchTaxInstallment(qqchTaxInstallmentParam);
        return AjaxResult.success(qqchTaxInstallment);
    }

    @PreAuthorize(hasPermi = "qqchTaxInstallment:list")
    @GetMapping("/list")
    public AjaxResult getQqchTaxInstallmentList(@Validated(ValidationGroups.Select.class) QqchTaxInstallment dto){
        QqchTaxInstallment qqchTaxInstallment = CompileEntity.dealListDto(VersionUtil.getVersion(TN, dto.getVersion()), dto);
        InstallmentVO sss= qqchTaxInstallmentService.list(qqchTaxInstallment);
        return AjaxResult.success(sss);
    }

    /**
     * 确认接口
     * 
     * @param dto 
     * @return
     */
    @PreAuthorize(hasPermi = "qqchTaxInstallment:confirm")
    @PostMapping("/save")
    public AjaxResult confirm(@Validated(ValidationGroups.Save.class) @RequestBody CompileEntity<QqchTaxInstallment >dto){
        // 20230915 改成只有确认按钮 确认按钮就是为了在评审功能已确认功能加1
        qqchTaxInstallmentService.confirm(dto.dealSaveDto());
        return AjaxResult.success(dto);
    }


    @PreAuthorize(hasPermi = "qqchTaxInstallment:refresh")
    @GetMapping("/refresh")
    public AjaxResult refresh(QqchTaxInstallment dto){
        return AjaxResult.success(qqchTaxInstallmentService.refresh(dto));
    }



    @PreAuthorize(hasPermi = "qqchTaxInstallment:refresh")
    @GetMapping("/timeList")
    public AjaxResult refresh(){
        List<String> yearList = qqchTaxInService.getYearList();
        return AjaxResult.success(yearList);
    }
    
    
    
    
    
    @PreAuthorize(hasPermi = "qqchTaxInstallment:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxInstallmentList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxInstallment> qqchTaxInstallmentListParam){
        qqchTaxInstallmentService.insertQqchTaxInstallmentList(qqchTaxInstallmentListParam);
        return AjaxResult.success(qqchTaxInstallmentListParam);
    }

    @PreAuthorize(hasPermi = "qqchTaxInstallment:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxInstallment(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxInstallment qqchTaxInstallmentParam){
        return toAjax(qqchTaxInstallmentService.updateQqchTaxInstallment(qqchTaxInstallmentParam));
    }

            @PreAuthorize(hasPermi = "qqchTaxInstallment:update")
        @PostMapping("/batchUpdate")
        public AjaxResult updateQqchTaxInstallmentList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxInstallment> qqchTaxInstallmentListParam){
            return toAjax(qqchTaxInstallmentService.updateQqchTaxInstallmentList(qqchTaxInstallmentListParam));
        }
    
    @PreAuthorize(hasPermi = "qqchTaxInstallment:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxInstallment(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxInstallment qqchTaxInstallmentParam){
        return toAjax(qqchTaxInstallmentService.deleteQqchTaxInstallment(qqchTaxInstallmentParam));
    }

            @PreAuthorize(hasPermi = "qqchTaxInstallment:remove")
        @PostMapping("/{ids}")
        public AjaxResult deleteQqchTaxInstallmentByPks(@PathVariable Long[] ids){
            List<Long> qqchTaxInstallmentPkList = Arrays.asList(ids);
            return toAjax(qqchTaxInstallmentService.deleteQqchTaxInstallmentByPks(qqchTaxInstallmentPkList));
        }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxInstallment qqchTaxInstallmentParam) throws IOException {
        List<QqchTaxInstallment> qqchTaxInstallmentList = qqchTaxInstallmentService.getQqchTaxInstallmentList(qqchTaxInstallmentParam);
        ExcelUtils<QqchTaxInstallment> util = new ExcelUtils<>(QqchTaxInstallment.class);
        util.exportExcel(response, qqchTaxInstallmentList, DateUtils.getDate());
    }
}
