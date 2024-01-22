package com.hhwy.sp.techData.sgjsTechnicalDataCatalog.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.hhwy.sp.techData.sgjsTechnicalDataCatalog.domain.SgjsTechnicalDataCatalog4Update;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.techData.sgjsTechnicalDataCatalog.service.ISgjsTechnicalDataCatalogService;
import com.hhwy.sp.techData.sgjsTechnicalDataCatalog.domain.SgjsTechnicalDataCatalog;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2024-01-22 09:26:41
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsTechnicalDataCatalog")
public class SgjsTechnicalDataCatalogController extends BaseController {

    @Autowired
    private ISgjsTechnicalDataCatalogService sgjsTechnicalDataCatalogService;


    @PreAuthorize(hasPermi = "sgjsTechnicalDataCatalog:list")
    @GetMapping
    public AjaxResult getSgjsTechnicalDataCatalog(@Validated(ValidationGroups.Get.class) SgjsTechnicalDataCatalog sgjsTechnicalDataCatalogParam) {
        SgjsTechnicalDataCatalog sgjsTechnicalDataCatalog = sgjsTechnicalDataCatalogService.getSgjsTechnicalDataCatalog(sgjsTechnicalDataCatalogParam);
        return AjaxResult.success(sgjsTechnicalDataCatalog);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataCatalog:list")
    @GetMapping("/list")
    public AjaxResult getSgjsTechnicalDataCatalogList(@Validated(ValidationGroups.Select.class) SgjsTechnicalDataCatalog sgjsTechnicalDataCatalogParam) {
        startPage();
        List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogList = sgjsTechnicalDataCatalogService.getSgjsTechnicalDataCatalogList(sgjsTechnicalDataCatalogParam);
        return getDataTableAjaxResult(sgjsTechnicalDataCatalogList);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataCatalog:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechnicalDataCatalog(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechnicalDataCatalog sgjsTechnicalDataCatalogParam) {
        sgjsTechnicalDataCatalogService.insertSgjsTechnicalDataCatalog(sgjsTechnicalDataCatalogParam);
        return AjaxResult.success(sgjsTechnicalDataCatalogParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataCatalog:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechnicalDataCatalogList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogListParam) {
        sgjsTechnicalDataCatalogService.insertSgjsTechnicalDataCatalogList(sgjsTechnicalDataCatalogListParam);
        return AjaxResult.success(sgjsTechnicalDataCatalogListParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataCatalog:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsTechnicalDataCatalog(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalDataCatalog sgjsTechnicalDataCatalogParam) {
        return toAjax(sgjsTechnicalDataCatalogService.updateSgjsTechnicalDataCatalog(sgjsTechnicalDataCatalogParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataCatalog:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechnicalDataCatalogList(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalDataCatalog4Update sgjsTechnicalDataCatalog4Update) {
        List<SgjsTechnicalDataCatalog> treeList = sgjsTechnicalDataCatalog4Update.getTreeList();
        int i = 0;
        if(CollectionUtils.isNotEmpty(treeList)) {
            i += sgjsTechnicalDataCatalogService.updateSgjsTechnicalDataCatalogList(treeList);
        }
        List<Long> delIdList = sgjsTechnicalDataCatalog4Update.getDelIdList();
        if(CollectionUtils.isNotEmpty(delIdList)) {
            i += sgjsTechnicalDataCatalogService.deleteSgjsTechnicalDataCatalogByPks(delIdList);
        }
        return AjaxResult.success(i);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataCatalog:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechnicalDataCatalog(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsTechnicalDataCatalog sgjsTechnicalDataCatalogParam) {
        return toAjax(sgjsTechnicalDataCatalogService.deleteSgjsTechnicalDataCatalog(sgjsTechnicalDataCatalogParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataCatalog:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsTechnicalDataCatalogByPks(@PathVariable Long[] ids) {
        List<Long> sgjsTechnicalDataCatalogPkList = Arrays.asList(ids);
        return toAjax(sgjsTechnicalDataCatalogService.deleteSgjsTechnicalDataCatalogByPks(sgjsTechnicalDataCatalogPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsTechnicalDataCatalog sgjsTechnicalDataCatalogParam) throws IOException {
        List<SgjsTechnicalDataCatalog> sgjsTechnicalDataCatalogList = sgjsTechnicalDataCatalogService.getSgjsTechnicalDataCatalogList(sgjsTechnicalDataCatalogParam);
        ExcelUtils<SgjsTechnicalDataCatalog> util = new ExcelUtils<>(SgjsTechnicalDataCatalog.class);
        util.exportExcel(response, sgjsTechnicalDataCatalogList, DateUtils.getDate());
    }
}
