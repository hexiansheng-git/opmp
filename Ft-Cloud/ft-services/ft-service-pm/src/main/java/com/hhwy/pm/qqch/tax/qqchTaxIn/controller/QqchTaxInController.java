package com.hhwy.pm.qqch.tax.qqchTaxIn.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxIn;
import com.hhwy.pm.qqch.tax.qqchTaxIn.service.IQqchTaxInService;
import com.hhwy.pm.qqch.tax.qqchTaxIn.vo.TaxInVO;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 10.3.3 财务策划-属地账税务策划-属地账收入明细
 * @author mls
 * @date 2023-08-09 18:17:32
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchTaxIn")
public class QqchTaxInController extends BaseController {

    @Autowired
    private IQqchTaxInService qqchTaxInService;


//    @PreAuthorize(hasPermi = "qqchTaxIn:list")
    @GetMapping
    public AjaxResult getQqchTaxIn(@Validated(ValidationGroups.Get.class) QqchTaxIn qqchTaxInParam) {
        QqchTaxIn qqchTaxIn = qqchTaxInService.getQqchTaxIn(qqchTaxInParam);
        return AjaxResult.success(qqchTaxIn);
    }


//    @PreAuthorize(hasPermi = "qqchTaxIn:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTaxInList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxIn> qqchTaxInListParam) {
        qqchTaxInService.insertQqchTaxInList(qqchTaxInListParam);
        return AjaxResult.success(qqchTaxInListParam);
    }

//    @PreAuthorize(hasPermi = "qqchTaxIn:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTaxIn(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxIn qqchTaxInParam) {
        return toAjax(qqchTaxInService.updateQqchTaxIn(qqchTaxInParam));
    }

//    @PreAuthorize(hasPermi = "qqchTaxIn:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTaxInList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxIn> qqchTaxInListParam) {
        return toAjax(qqchTaxInService.updateQqchTaxInList(qqchTaxInListParam));
    }

//    @PreAuthorize(hasPermi = "qqchTaxIn:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTaxIn(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxIn qqchTaxInParam) {
        return toAjax(qqchTaxInService.deleteQqchTaxIn(qqchTaxInParam));
    }

//    @PreAuthorize(hasPermi = "qqchTaxIn:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTaxInByPks(@PathVariable Long[] ids) {
        List<Long> qqchTaxInPkList = Arrays.asList(ids);
        return toAjax(qqchTaxInService.deleteQqchTaxInByPks(qqchTaxInPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTaxIn qqchTaxInParam) throws IOException {
        List<QqchTaxIn> qqchTaxInList = qqchTaxInService.getQqchTaxInList(qqchTaxInParam);
        ExcelUtils<QqchTaxIn> util = new ExcelUtils<>(QqchTaxIn.class);
        util.exportExcel(response, qqchTaxInList, DateUtils.getDate());
    }


//    @PreAuthorize(hasPermi = "qqchTaxIn:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) QqchTaxIn qqchTaxInParam) {
        CompileEntity<TaxInVO> qqchTaxInList = qqchTaxInService.list(qqchTaxInParam);
        return AjaxResult.success(qqchTaxInList);
    }

//    @PreAuthorize(hasPermi = "qqchTaxIn:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody CompileEntity<TaxInVO> qqchTaxInParam) {
        qqchTaxInService.save(qqchTaxInParam);
        return AjaxResult.success(qqchTaxInParam);
    }
}



