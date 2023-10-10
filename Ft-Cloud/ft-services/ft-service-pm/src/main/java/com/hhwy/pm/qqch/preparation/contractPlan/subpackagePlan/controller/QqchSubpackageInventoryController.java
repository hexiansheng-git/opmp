package com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.domain.QqchSubpackageInventory;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.domain.vo.SubpackageInventoryCollectVo;
import com.hhwy.pm.qqch.preparation.contractPlan.subpackagePlan.service.IQqchSubpackageInventoryService;
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
 * @date 2023-08-03 13:35:47
 * @remark 分包清单
 */
@Validated
@RestController
@RequestMapping("/qqchSubpackageInventory")
public class QqchSubpackageInventoryController extends BaseController {

    @Autowired
    private IQqchSubpackageInventoryService qqchSubpackageInventoryService;


//    @PreAuthorize(hasPermi = "qqchSubpackageInventory:list")
    @GetMapping
    public AjaxResult getQqchSubpackageInventory(@Validated(ValidationGroups.Get.class) QqchSubpackageInventory qqchSubpackageInventoryParam) {
        QqchSubpackageInventory qqchSubpackageInventory = qqchSubpackageInventoryService.getQqchSubpackageInventory(qqchSubpackageInventoryParam);
        return AjaxResult.success(qqchSubpackageInventory);
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageInventory:list")
    @GetMapping("/list")
    public AjaxResult getQqchSubpackageInventoryList(@Validated(ValidationGroups.Select.class) QqchSubpackageInventory qqchSubpackageInventoryParam) {
        startPage();
        List<QqchSubpackageInventory> qqchSubpackageInventoryList = qqchSubpackageInventoryService.getQqchSubpackageInventoryList(qqchSubpackageInventoryParam);
        return getDataTableAjaxResult(qqchSubpackageInventoryList);
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageInventory:add")
    @PostMapping("/add")
    public AjaxResult insertQqchSubpackageInventory(@Validated(ValidationGroups.Save.class) @RequestBody QqchSubpackageInventory qqchSubpackageInventoryParam) {
        qqchSubpackageInventoryService.insertQqchSubpackageInventory(qqchSubpackageInventoryParam);
        return AjaxResult.success(qqchSubpackageInventoryParam);
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageInventory:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchSubpackageInventoryList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchSubpackageInventory> qqchSubpackageInventoryListParam) {
        qqchSubpackageInventoryService.insertQqchSubpackageInventoryList(qqchSubpackageInventoryListParam);
        return AjaxResult.success(qqchSubpackageInventoryListParam);
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageInventory:update")
    @PostMapping("/update")
    public AjaxResult updateQqchSubpackageInventory(@Validated(ValidationGroups.Update.class) @RequestBody QqchSubpackageInventory qqchSubpackageInventoryParam) {
        return toAjax(qqchSubpackageInventoryService.updateQqchSubpackageInventory(qqchSubpackageInventoryParam));
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageInventory:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchSubpackageInventoryList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchSubpackageInventory> qqchSubpackageInventoryListParam) {
        return toAjax(qqchSubpackageInventoryService.updateQqchSubpackageInventoryList(qqchSubpackageInventoryListParam));
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageInventory:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchSubpackageInventory(@Validated(ValidationGroups.Delete.class) @RequestBody QqchSubpackageInventory qqchSubpackageInventoryParam) {
        return toAjax(qqchSubpackageInventoryService.deleteQqchSubpackageInventory(qqchSubpackageInventoryParam));
    }

//    @PreAuthorize(hasPermi = "qqchSubpackageInventory:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchSubpackageInventoryByPks(@PathVariable Long[] ids) {
        List<Long> qqchSubpackageInventoryPkList = Arrays.asList(ids);
        return toAjax(qqchSubpackageInventoryService.deleteQqchSubpackageInventoryByPks(qqchSubpackageInventoryPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchSubpackageInventory qqchSubpackageInventoryParam) throws IOException {
        List<QqchSubpackageInventory> qqchSubpackageInventoryList = qqchSubpackageInventoryService.getQqchSubpackageInventoryList(qqchSubpackageInventoryParam);
        ExcelUtils<QqchSubpackageInventory> util = new ExcelUtils<>(QqchSubpackageInventory.class);
        util.exportExcel(response, qqchSubpackageInventoryList, DateUtils.getDate());
    }

    /**
     * 分包清单汇总
     * @param qqchSubpackageInventory
     * @return
     */
    @GetMapping("/collectList")
    public AjaxResult getSubpackageInventoryCollectVoList(@Validated(ValidationGroups.Select.class) QqchSubpackageInventory qqchSubpackageInventory) {
        List<SubpackageInventoryCollectVo> subpackageInventoryCollectVoList = qqchSubpackageInventoryService.getSubpackageInventoryCollectVoList(qqchSubpackageInventory);
        return AjaxResult.success(subpackageInventoryCollectVoList);
    }
}
