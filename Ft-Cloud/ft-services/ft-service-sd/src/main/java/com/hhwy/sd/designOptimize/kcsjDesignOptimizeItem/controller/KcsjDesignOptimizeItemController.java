package com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.controller;

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
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.service.IKcsjDesignOptimizeItemService;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.domain.KcsjDesignOptimizeItem;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2024-02-04 13:31:49
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjDesignOptimizeItem")
public class KcsjDesignOptimizeItemController extends BaseController {

    @Autowired
    private IKcsjDesignOptimizeItemService kcsjDesignOptimizeItemService;


    @PreAuthorize(hasPermi = "kcsjDesignOptimizeItem:list")
    @GetMapping
    public AjaxResult getKcsjDesignOptimizeItem(@Validated(ValidationGroups.Get.class) KcsjDesignOptimizeItem kcsjDesignOptimizeItemParam) {
        KcsjDesignOptimizeItem kcsjDesignOptimizeItem = kcsjDesignOptimizeItemService.getKcsjDesignOptimizeItem(kcsjDesignOptimizeItemParam);
        return AjaxResult.success(kcsjDesignOptimizeItem);
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimizeItem:list")
    @GetMapping("/list")
    public AjaxResult getKcsjDesignOptimizeItemList(@Validated(ValidationGroups.Select.class) KcsjDesignOptimizeItem kcsjDesignOptimizeItemParam) {
        startPage();
        List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList = kcsjDesignOptimizeItemService.getKcsjDesignOptimizeItemList(kcsjDesignOptimizeItemParam);
        return getDataTableAjaxResult(kcsjDesignOptimizeItemList);
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimizeItem:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjDesignOptimizeItem(@Validated(ValidationGroups.Save.class) @RequestBody KcsjDesignOptimizeItem kcsjDesignOptimizeItemParam) {
        kcsjDesignOptimizeItemService.insertKcsjDesignOptimizeItem(kcsjDesignOptimizeItemParam);
        return AjaxResult.success(kcsjDesignOptimizeItemParam);
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimizeItem:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjDesignOptimizeItemList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemListParam) {
        kcsjDesignOptimizeItemService.insertKcsjDesignOptimizeItemList(kcsjDesignOptimizeItemListParam);
        return AjaxResult.success(kcsjDesignOptimizeItemListParam);
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimizeItem:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjDesignOptimizeItem(@Validated(ValidationGroups.Update.class) @RequestBody KcsjDesignOptimizeItem kcsjDesignOptimizeItemParam) {
        return toAjax(kcsjDesignOptimizeItemService.updateKcsjDesignOptimizeItem(kcsjDesignOptimizeItemParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimizeItem:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjDesignOptimizeItemList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemListParam) {
        Long optimizeId = kcsjDesignOptimizeItemListParam.get(0).getOptimizeId();
        return toAjax(kcsjDesignOptimizeItemService.updateKcsjDesignOptimizeItemList(optimizeId, kcsjDesignOptimizeItemListParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimizeItem:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjDesignOptimizeItem(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjDesignOptimizeItem kcsjDesignOptimizeItemParam) {
        return toAjax(kcsjDesignOptimizeItemService.deleteKcsjDesignOptimizeItem(kcsjDesignOptimizeItemParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimizeItem:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjDesignOptimizeItemByPks(@PathVariable Long[] ids) {
        List<Long> kcsjDesignOptimizeItemPkList = Arrays.asList(ids);
        return toAjax(kcsjDesignOptimizeItemService.deleteKcsjDesignOptimizeItemByPks(kcsjDesignOptimizeItemPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjDesignOptimizeItem kcsjDesignOptimizeItemParam) throws IOException {
        List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList = kcsjDesignOptimizeItemService.getKcsjDesignOptimizeItemList(kcsjDesignOptimizeItemParam);
        ExcelUtils<KcsjDesignOptimizeItem> util = new ExcelUtils<>(KcsjDesignOptimizeItem.class);
        util.exportExcel(response, kcsjDesignOptimizeItemList, DateUtils.getDate());
    }
}
