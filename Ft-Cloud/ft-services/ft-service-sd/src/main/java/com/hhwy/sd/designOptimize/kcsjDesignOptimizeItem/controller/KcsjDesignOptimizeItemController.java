package com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.io.IOException;
import java.util.Set;

import com.hhwy.sd.designOptimize.kcsjDesignOptimize.domain.KcsjDesignOptimize;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import com.sun.xml.internal.ws.policy.AssertionSet;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cjh
 * @date 2024-02-04 13:31:49
 * @remark 设计优化管理
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
        return toAjax(kcsjDesignOptimizeItemService.updateKcsjDesignOptimizeItemList(optimizeId, kcsjDesignOptimizeItemListParam,new KcsjDesignOptimize()));
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
        FtExcelUtil<KcsjDesignOptimizeItem> util = new FtExcelUtil<>(KcsjDesignOptimizeItem.class);
        util.exportExcel(response, kcsjDesignOptimizeItemList, "sheet1");
    }

    @PostMapping("/import")
    public AjaxResult importData(@RequestPart("file") MultipartFile file) throws Exception {
        FtExcelUtil<KcsjDesignOptimizeItem> excelUtil = new FtExcelUtil<>(KcsjDesignOptimizeItem.class);
        List<KcsjDesignOptimizeItem> list = excelUtil.importExcel("数据", file.getInputStream());
        Set<String> codeSet = new HashSet<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            KcsjDesignOptimizeItem vo = list.get(i);
            BigDecimal beforePrice = ObjectUtils.nvlBigDecimal(vo.getBeforeOptimizeQty()).
                    multiply(ObjectUtils.nvlBigDecimal(vo.getBeforeUnitPrice()));
            vo.setBeforePrice(beforePrice);
            BigDecimal afterPrice = ObjectUtils.nvlBigDecimal(vo.getAfterOptimizeQty()).
                    multiply(ObjectUtils.nvlBigDecimal(vo.getEstimatePrice()));
            vo.setAfterPrice(afterPrice);        
            vo.setEstimateAmt(ObjectUtils.nvlBigDecimal(vo.getAfterPrice()).subtract(
                    ObjectUtils.nvlBigDecimal(vo.getBeforePrice())));
            Assert.isTrue(!codeSet.contains(vo.getItemCode()), "主材/清单编码["+vo.getItemCode()+"]已存在，无法重复录入");
            codeSet.add(vo.getItemCode());
        }
        return AjaxResult.success(list);
    }
}
