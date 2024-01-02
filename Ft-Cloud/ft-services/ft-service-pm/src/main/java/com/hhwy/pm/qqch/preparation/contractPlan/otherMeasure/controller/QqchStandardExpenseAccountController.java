package com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.QqchStandardExpenseAccount;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.service.IQqchStandardExpenseAccountService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-08-16 16:21:18
 * @remark 标准费用科目
 */
@Validated
@RestController
@RequestMapping("/qqchStandardExpenseAccount")
public class QqchStandardExpenseAccountController extends BaseController {

    @Autowired
    private IQqchStandardExpenseAccountService qqchStandardExpenseAccountService;


//    @PreAuthorize(hasPermi = "qqchStandardExpenseAccount:list")
    @GetMapping
    public AjaxResult getQqchStandardExpenseAccount(@Validated(ValidationGroups.Get.class) QqchStandardExpenseAccount qqchStandardExpenseAccountParam) {
        QqchStandardExpenseAccount qqchStandardExpenseAccount = qqchStandardExpenseAccountService.getQqchStandardExpenseAccount(qqchStandardExpenseAccountParam);
        return AjaxResult.success(qqchStandardExpenseAccount);
    }

    /**
     * 获取标准科目列表
     * @param qqchStandardExpenseAccountParam
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchStandardExpenseAccountList(@Validated(ValidationGroups.Select.class) QqchStandardExpenseAccount qqchStandardExpenseAccountParam) {
        List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList = qqchStandardExpenseAccountService.getQqchStandardExpenseAccountList(qqchStandardExpenseAccountParam);
        return AjaxResult.success(qqchStandardExpenseAccountList);
    }

    @PostMapping("changeId")
    public AjaxResult changeId(@RequestBody List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList){
        List<QqchStandardExpenseAccount> list = qqchStandardExpenseAccountService.changeId(qqchStandardExpenseAccountList);
        return AjaxResult.success(list);
    }

//    @PreAuthorize(hasPermi = "qqchStandardExpenseAccount:add")
    @PostMapping("/add")
    public AjaxResult insertQqchStandardExpenseAccount(@Validated(ValidationGroups.Save.class) @RequestBody QqchStandardExpenseAccount qqchStandardExpenseAccountParam) {
        qqchStandardExpenseAccountService.insertQqchStandardExpenseAccount(qqchStandardExpenseAccountParam);
        return AjaxResult.success(qqchStandardExpenseAccountParam);
    }

//    @PreAuthorize(hasPermi = "qqchStandardExpenseAccount:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchStandardExpenseAccountList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchStandardExpenseAccount> qqchStandardExpenseAccountListParam) {
        qqchStandardExpenseAccountService.insertQqchStandardExpenseAccountList(qqchStandardExpenseAccountListParam);
        return AjaxResult.success(qqchStandardExpenseAccountListParam);
    }

//    @PreAuthorize(hasPermi = "qqchStandardExpenseAccount:update")
    @PostMapping("/update")
    public AjaxResult updateQqchStandardExpenseAccount(@Validated(ValidationGroups.Update.class) @RequestBody QqchStandardExpenseAccount qqchStandardExpenseAccountParam) {
        return toAjax(qqchStandardExpenseAccountService.updateQqchStandardExpenseAccount(qqchStandardExpenseAccountParam));
    }

//    @PreAuthorize(hasPermi = "qqchStandardExpenseAccount:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchStandardExpenseAccountList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchStandardExpenseAccount> qqchStandardExpenseAccountListParam) {
        return toAjax(qqchStandardExpenseAccountService.updateQqchStandardExpenseAccountList(qqchStandardExpenseAccountListParam));
    }

//    @PreAuthorize(hasPermi = "qqchStandardExpenseAccount:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchStandardExpenseAccount(@Validated(ValidationGroups.Delete.class) @RequestBody QqchStandardExpenseAccount qqchStandardExpenseAccountParam) {
        return toAjax(qqchStandardExpenseAccountService.deleteQqchStandardExpenseAccount(qqchStandardExpenseAccountParam));
    }

//    @PreAuthorize(hasPermi = "qqchStandardExpenseAccount:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchStandardExpenseAccountByPks(@PathVariable Long[] ids) {
        List<Long> qqchStandardExpenseAccountPkList = Arrays.asList(ids);
        return toAjax(qqchStandardExpenseAccountService.deleteQqchStandardExpenseAccountByPks(qqchStandardExpenseAccountPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchStandardExpenseAccount qqchStandardExpenseAccountParam) throws IOException {
        List<QqchStandardExpenseAccount> qqchStandardExpenseAccountList = qqchStandardExpenseAccountService.getQqchStandardExpenseAccountList(qqchStandardExpenseAccountParam);
        ExcelUtils<QqchStandardExpenseAccount> util = new ExcelUtils<>(QqchStandardExpenseAccount.class);
        util.exportExcel(response, qqchStandardExpenseAccountList, DateUtils.getDate());
    }
}
