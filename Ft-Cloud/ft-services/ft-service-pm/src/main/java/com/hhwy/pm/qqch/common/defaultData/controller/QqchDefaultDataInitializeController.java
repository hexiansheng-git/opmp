package com.hhwy.pm.qqch.common.defaultData.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.defaultData.domain.QqchDefaultDataInitialize;
import com.hhwy.pm.qqch.common.defaultData.service.IQqchDefaultDataInitializeService;
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
 * @date 2023-08-21 10:02:06
 * @remark 默认数据初始化状态
 */
@Validated
@RestController
@RequestMapping("/qqchDefaultDataInitialize")
public class QqchDefaultDataInitializeController extends BaseController {

    @Autowired
    private IQqchDefaultDataInitializeService qqchDefaultDataInitializeService;


    @PreAuthorize(hasPermi = "qqchDefaultDataInitialize:list")
    @GetMapping
    public AjaxResult getQqchDefaultDataInitialize(@Validated(ValidationGroups.Get.class) QqchDefaultDataInitialize qqchDefaultDataInitializeParam) {
        QqchDefaultDataInitialize qqchDefaultDataInitialize = qqchDefaultDataInitializeService.getQqchDefaultDataInitialize(qqchDefaultDataInitializeParam);
        return AjaxResult.success(qqchDefaultDataInitialize);
    }

    @PreAuthorize(hasPermi = "qqchDefaultDataInitialize:list")
    @GetMapping("/list")
    public AjaxResult getQqchDefaultDataInitializeList(@Validated(ValidationGroups.Select.class) QqchDefaultDataInitialize qqchDefaultDataInitializeParam) {
        startPage();
        List<QqchDefaultDataInitialize> qqchDefaultDataInitializeList = qqchDefaultDataInitializeService.getQqchDefaultDataInitializeList(qqchDefaultDataInitializeParam);
        return getDataTableAjaxResult(qqchDefaultDataInitializeList);
    }

    @PreAuthorize(hasPermi = "qqchDefaultDataInitialize:add")
    @PostMapping("/add")
    public AjaxResult insertQqchDefaultDataInitialize(@Validated(ValidationGroups.Save.class) @RequestBody QqchDefaultDataInitialize qqchDefaultDataInitializeParam) {
        qqchDefaultDataInitializeService.insertQqchDefaultDataInitialize(qqchDefaultDataInitializeParam);
        return AjaxResult.success(qqchDefaultDataInitializeParam);
    }

    @PreAuthorize(hasPermi = "qqchDefaultDataInitialize:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchDefaultDataInitializeList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchDefaultDataInitialize> qqchDefaultDataInitializeListParam) {
        qqchDefaultDataInitializeService.insertQqchDefaultDataInitializeList(qqchDefaultDataInitializeListParam);
        return AjaxResult.success(qqchDefaultDataInitializeListParam);
    }

    @PreAuthorize(hasPermi = "qqchDefaultDataInitialize:update")
    @PostMapping("/update")
    public AjaxResult updateQqchDefaultDataInitialize(@Validated(ValidationGroups.Update.class) @RequestBody QqchDefaultDataInitialize qqchDefaultDataInitializeParam) {
        return toAjax(qqchDefaultDataInitializeService.updateQqchDefaultDataInitialize(qqchDefaultDataInitializeParam));
    }

    @PreAuthorize(hasPermi = "qqchDefaultDataInitialize:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchDefaultDataInitializeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchDefaultDataInitialize> qqchDefaultDataInitializeListParam) {
        return toAjax(qqchDefaultDataInitializeService.updateQqchDefaultDataInitializeList(qqchDefaultDataInitializeListParam));
    }

    @PreAuthorize(hasPermi = "qqchDefaultDataInitialize:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchDefaultDataInitialize(@Validated(ValidationGroups.Delete.class) @RequestBody QqchDefaultDataInitialize qqchDefaultDataInitializeParam) {
        return toAjax(qqchDefaultDataInitializeService.deleteQqchDefaultDataInitialize(qqchDefaultDataInitializeParam));
    }

    @PreAuthorize(hasPermi = "qqchDefaultDataInitialize:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchDefaultDataInitializeByPks(@PathVariable Long[] ids) {
        List<Long> qqchDefaultDataInitializePkList = Arrays.asList(ids);
        return toAjax(qqchDefaultDataInitializeService.deleteQqchDefaultDataInitializeByPks(qqchDefaultDataInitializePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchDefaultDataInitialize qqchDefaultDataInitializeParam) throws IOException {
        List<QqchDefaultDataInitialize> qqchDefaultDataInitializeList = qqchDefaultDataInitializeService.getQqchDefaultDataInitializeList(qqchDefaultDataInitializeParam);
        ExcelUtils<QqchDefaultDataInitialize> util = new ExcelUtils<>(QqchDefaultDataInitialize.class);
        util.exportExcel(response, qqchDefaultDataInitializeList, DateUtils.getDate());
    }
}
