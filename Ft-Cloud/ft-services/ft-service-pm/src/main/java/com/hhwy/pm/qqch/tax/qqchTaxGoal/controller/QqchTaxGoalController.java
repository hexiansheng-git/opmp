package com.hhwy.pm.qqch.tax.qqchTaxGoal.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.domain.QqchTaxGoal;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.service.IQqchTaxGoalService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcel;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:29
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchTaxGoal")
public class QqchTaxGoalController extends BaseController {

    @Autowired
    private IQqchTaxGoalService qqchTaxGoalService;


//    @PreAuthorize(hasPermi = "qqchTaxGoal:list")
    @GetMapping
    public AjaxResult getQqchTaxGoal(@Validated(ValidationGroups.Get.class) QqchTaxGoal qqchTaxGoalParam) {
        QqchTaxGoal qqchTaxGoal = qqchTaxGoalService.getQqchTaxGoal(qqchTaxGoalParam);
        return AjaxResult.success(qqchTaxGoal);
    }

//    @PreAuthorize(hasPermi = "qqchTaxGoal:list")
    @GetMapping("/list")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.3属地帐税务策划", name = "10.3.1财务目标" ,businessType = CustomBusinessType.SELECT)
    public AjaxResult getQqchTaxGoalList(@Validated(ValidationGroups.Select.class) QqchTaxGoal qqchTaxGoalParam) {
        CompileEntity<List<QqchTaxGoal>> qqchTaxGoalList = qqchTaxGoalService.list(qqchTaxGoalParam);
        return AjaxResult.success(qqchTaxGoalList);
    }

//    @PreAuthorize(hasPermi = "qqchTaxGoal:add")
    @PostMapping("/save")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.3属地帐税务策划", name = "10.3.1财务目标" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody CompileEntity<List<QqchTaxGoal>> list) {
        qqchTaxGoalService.save(list.dealSaveDto());
        return AjaxResult.success(list);
    }

//    @PreAuthorize(hasPermi = "qqchTaxGoal:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.3属地帐税务策划", name = "10.3.1财务目标" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertQqchTaxGoalList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTaxGoal> qqchTaxGoalListParam) {
        qqchTaxGoalService.insertQqchTaxGoalList(qqchTaxGoalListParam);
        return AjaxResult.success(qqchTaxGoalListParam);
    }

//    @PreAuthorize(hasPermi = "qqchTaxGoal:update")
    @PostMapping("/update")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.3属地帐税务策划", name = "10.3.1财务目标" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateQqchTaxGoal(@Validated(ValidationGroups.Update.class) @RequestBody QqchTaxGoal qqchTaxGoalParam) {
        return toAjax(qqchTaxGoalService.updateQqchTaxGoal(qqchTaxGoalParam));
    }

//    @PreAuthorize(hasPermi = "qqchTaxGoal:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.3属地帐税务策划", name = "10.3.1财务目标" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateQqchTaxGoalList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTaxGoal> qqchTaxGoalListParam) {
        return toAjax(qqchTaxGoalService.updateQqchTaxGoalList(qqchTaxGoalListParam));
    }

//    @PreAuthorize(hasPermi = "qqchTaxGoal:remove")
    @PostMapping("/delete")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.3属地帐税务策划", name = "10.3.1财务目标" ,businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteQqchTaxGoal(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTaxGoal qqchTaxGoalParam) {
        return toAjax(qqchTaxGoalService.deleteQqchTaxGoal(qqchTaxGoalParam));
    }

//    @PreAuthorize(hasPermi = "qqchTaxGoal:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTaxGoalByPks(@PathVariable Long[] ids) {
        List<Long> qqchTaxGoalPkList = Arrays.asList(ids);
        return toAjax(qqchTaxGoalService.deleteQqchTaxGoalByPks(qqchTaxGoalPkList));
    }

    @GetMapping("/export")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.3属地帐税务策划", name = "10.3.1财务目标" ,businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response, QqchTaxGoal qqchTaxGoalParam) throws IOException {
        List<QqchTaxGoal> qqchTaxGoalList = qqchTaxGoalService.getQqchTaxGoalList(qqchTaxGoalParam);
        ExcelUtils<QqchTaxGoal> util = new ExcelUtils<>(QqchTaxGoal.class);
        util.exportExcel(response, qqchTaxGoalList, DateUtils.getDate());
    }


    @PostMapping("/importData")
    @CustomLogger(title = "前期策划-前期策划编制-财务策划-10.3属地帐税务策划", name = "10.3.1财务目标" ,businessType = CustomBusinessType.IMPORT)
    public AjaxResult importData(@RequestParam("file") MultipartFile file) throws Exception {
        FtExcelUtil<QqchTaxGoal> excelUtil = new FtExcelUtil<>(QqchTaxGoal.class);
        List<QqchTaxGoal> qqchTaxGoals = excelUtil.importExcel(file.getInputStream(), 3);
        for (int i = 0; i < qqchTaxGoals.size(); i++) {
            qqchTaxGoals.get(i).setId(IdWorker.createId());
        }
        return AjaxResult.success(qqchTaxGoals);
    }
}
