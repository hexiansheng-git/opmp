package com.hhwy.pm.qqch.preparation.survey.document.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchBlueprintManageInventory;
import com.hhwy.pm.qqch.preparation.survey.document.service.IQqchBlueprintManageInventoryService;
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
 * @author han
 * @date 2023-07-13 11:40:34
 * @remark 勘察设计图纸管理清单
 */
@Validated
@RestController
@RequestMapping("/qqchBlueprintManageInventory")
public class QqchBlueprintManageInventoryController extends BaseController {

    @Autowired
    private IQqchBlueprintManageInventoryService qqchBlueprintManageInventoryService;


    @PreAuthorize(hasPermi = "qqchBlueprintManageInventory:list")
    @GetMapping
    public AjaxResult getQqchBlueprintManageInventory(@Validated(ValidationGroups.Get.class) QqchBlueprintManageInventory qqchBlueprintManageInventoryParam) {
        QqchBlueprintManageInventory qqchBlueprintManageInventory = qqchBlueprintManageInventoryService.getQqchBlueprintManageInventory(qqchBlueprintManageInventoryParam);
        return AjaxResult.success(qqchBlueprintManageInventory);
    }

    @PreAuthorize(hasPermi = "qqchBlueprintManageInventory:list")
    @GetMapping("/list")
    public AjaxResult getQqchBlueprintManageInventoryList(@Validated(ValidationGroups.Select.class) QqchBlueprintManageInventory qqchBlueprintManageInventoryParam) {
        startPage();
        List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList = qqchBlueprintManageInventoryService.getQqchBlueprintManageInventoryList(qqchBlueprintManageInventoryParam);
        return getDataTableAjaxResult(qqchBlueprintManageInventoryList);
    }

    @PreAuthorize(hasPermi = "qqchBlueprintManageInventory:add")
    @PostMapping("/add")
    public AjaxResult insertQqchBlueprintManageInventory(@Validated(ValidationGroups.Save.class) @RequestBody QqchBlueprintManageInventory qqchBlueprintManageInventoryParam) {
        qqchBlueprintManageInventoryService.insertQqchBlueprintManageInventory(qqchBlueprintManageInventoryParam);
        return AjaxResult.success(qqchBlueprintManageInventoryParam);
    }

    @PreAuthorize(hasPermi = "qqchBlueprintManageInventory:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchBlueprintManageInventoryList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryListParam) {
        qqchBlueprintManageInventoryService.insertQqchBlueprintManageInventoryList(qqchBlueprintManageInventoryListParam);
        return AjaxResult.success(qqchBlueprintManageInventoryListParam);
    }

    @PreAuthorize(hasPermi = "qqchBlueprintManageInventory:update")
    @PostMapping("/update")
    public AjaxResult updateQqchBlueprintManageInventory(@Validated(ValidationGroups.Update.class) @RequestBody QqchBlueprintManageInventory qqchBlueprintManageInventoryParam) {
        return toAjax(qqchBlueprintManageInventoryService.updateQqchBlueprintManageInventory(qqchBlueprintManageInventoryParam));
    }

    @PreAuthorize(hasPermi = "qqchBlueprintManageInventory:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchBlueprintManageInventoryList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryListParam) {
        return toAjax(qqchBlueprintManageInventoryService.updateQqchBlueprintManageInventoryList(qqchBlueprintManageInventoryListParam));
    }

    @PreAuthorize(hasPermi = "qqchBlueprintManageInventory:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchBlueprintManageInventory(@Validated(ValidationGroups.Delete.class) @RequestBody QqchBlueprintManageInventory qqchBlueprintManageInventoryParam) {
        return toAjax(qqchBlueprintManageInventoryService.deleteQqchBlueprintManageInventory(qqchBlueprintManageInventoryParam));
    }

    @PreAuthorize(hasPermi = "qqchBlueprintManageInventory:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchBlueprintManageInventoryByPks(@PathVariable Long[] ids) {
        List<Long> qqchBlueprintManageInventoryPkList = Arrays.asList(ids);
        return toAjax(qqchBlueprintManageInventoryService.deleteQqchBlueprintManageInventoryByPks(qqchBlueprintManageInventoryPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchBlueprintManageInventory qqchBlueprintManageInventoryParam) throws IOException {
        List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList = qqchBlueprintManageInventoryService.getQqchBlueprintManageInventoryList(qqchBlueprintManageInventoryParam);
        ExcelUtils<QqchBlueprintManageInventory> util = new ExcelUtils<>(QqchBlueprintManageInventory.class);
        util.exportExcel(response, qqchBlueprintManageInventoryList, DateUtils.getDate());
    }
}
