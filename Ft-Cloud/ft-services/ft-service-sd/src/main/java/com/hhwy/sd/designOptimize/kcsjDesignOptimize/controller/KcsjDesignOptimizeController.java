package com.hhwy.sd.designOptimize.kcsjDesignOptimize.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.utils.excel.FtExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.service.IKcsjDesignOptimizeService;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.domain.KcsjDesignOptimize;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2024-02-04 13:31:39
 * @remark 设计优化管理
 */
@Validated
@RestController
@RequestMapping("/kcsjDesignOptimize")
public class KcsjDesignOptimizeController extends BaseController {

    @Autowired
    private IKcsjDesignOptimizeService kcsjDesignOptimizeService;


    @PreAuthorize(hasPermi = "kcsjDesignOptimize:list")
    @GetMapping
    public AjaxResult getKcsjDesignOptimize(@Validated(ValidationGroups.Get.class) KcsjDesignOptimize kcsjDesignOptimizeParam) {
        KcsjDesignOptimize kcsjDesignOptimize = kcsjDesignOptimizeService.getKcsjDesignOptimize(kcsjDesignOptimizeParam);
        return AjaxResult.success(kcsjDesignOptimize);
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimize:list")
    @GetMapping("/list")
    public AjaxResult getKcsjDesignOptimizeList(@Validated(ValidationGroups.Select.class) KcsjDesignOptimize kcsjDesignOptimizeParam) {
        startPage();
        List<KcsjDesignOptimize> kcsjDesignOptimizeList = kcsjDesignOptimizeService.getKcsjDesignOptimizeList(kcsjDesignOptimizeParam);
        return getDataTableAjaxResult(kcsjDesignOptimizeList);
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimize:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjDesignOptimize(@Validated(ValidationGroups.Save.class) @RequestBody KcsjDesignOptimize kcsjDesignOptimizeParam) {
        kcsjDesignOptimizeService.insertKcsjDesignOptimize(kcsjDesignOptimizeParam);
        return AjaxResult.success(kcsjDesignOptimizeParam);
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimize:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjDesignOptimizeList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjDesignOptimize> kcsjDesignOptimizeListParam) {
        kcsjDesignOptimizeService.insertKcsjDesignOptimizeList(kcsjDesignOptimizeListParam);
        return AjaxResult.success(kcsjDesignOptimizeListParam);
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimize:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjDesignOptimize(@Validated(ValidationGroups.Update.class) @RequestBody KcsjDesignOptimize kcsjDesignOptimizeParam) {
        return toAjax(kcsjDesignOptimizeService.updateKcsjDesignOptimize(kcsjDesignOptimizeParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimize:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjDesignOptimizeList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjDesignOptimize> kcsjDesignOptimizeListParam) {
        return toAjax(kcsjDesignOptimizeService.updateKcsjDesignOptimizeList(kcsjDesignOptimizeListParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimize:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjDesignOptimize(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjDesignOptimize kcsjDesignOptimizeParam) {
        return toAjax(kcsjDesignOptimizeService.deleteKcsjDesignOptimize(kcsjDesignOptimizeParam));
    }

    @PreAuthorize(hasPermi = "kcsjDesignOptimize:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjDesignOptimizeByPks(@PathVariable Long[] ids) {
        List<Long> kcsjDesignOptimizePkList = Arrays.asList(ids);
        return toAjax(kcsjDesignOptimizeService.deleteKcsjDesignOptimizeByPks(kcsjDesignOptimizePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjDesignOptimize kcsjDesignOptimizeParam) throws IOException {
        String ids = kcsjDesignOptimizeParam.getIds();
        List<Long> ids4L = new ArrayList<>();
        if(StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            for (String s: split) {
                ids4L.add(Long.parseLong(s));
            }
        }
        List<KcsjDesignOptimize> kcsjDesignOptimizeList = null;
        if(CollectionUtils.isNotEmpty(ids4L)) {
            kcsjDesignOptimizeList = kcsjDesignOptimizeService.getKcsjDesignOptimizeList4Ids(ids4L);
        } else {
            kcsjDesignOptimizeList = kcsjDesignOptimizeService.getKcsjDesignOptimizeList(kcsjDesignOptimizeParam);
        }

        FtExcelUtil<KcsjDesignOptimize> util = new FtExcelUtil<>(KcsjDesignOptimize.class);
        util.exportExcel(response, kcsjDesignOptimizeList, DateUtils.getDate());
    }
}
