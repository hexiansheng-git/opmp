package com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.service.ISgjsControlPointRetestService;
import com.hhwy.sp.sgjsMeasure.sgjsControlPointRetest.domain.SgjsControlPointRetest;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * 施工技术--测量管理--控制点复测
 *
 * @author lcf
 * @date 2024-03-12 14:54:13
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsControlPointRetest")
public class SgjsControlPointRetestController extends BaseController {

    @Autowired
    private ISgjsControlPointRetestService sgjsControlPointRetestService;


    @PreAuthorize(hasPermi = "sgjsControlPointRetest:list")
    @GetMapping
    public AjaxResult getSgjsControlPointRetest(@Validated(ValidationGroups.Get.class) SgjsControlPointRetest sgjsControlPointRetestParam) {
        SgjsControlPointRetest sgjsControlPointRetest = sgjsControlPointRetestService.getSgjsControlPointRetest(sgjsControlPointRetestParam);
        return AjaxResult.success(sgjsControlPointRetest);
    }

    @PreAuthorize(hasPermi = "sgjsControlPointRetest:list")
    @GetMapping("/list")
    public AjaxResult getSgjsControlPointRetestList(@Validated(ValidationGroups.Select.class) SgjsControlPointRetest sgjsControlPointRetestParam) {
        startPage();
        List<SgjsControlPointRetest> sgjsControlPointRetestList = sgjsControlPointRetestService.getSgjsControlPointRetestList(sgjsControlPointRetestParam);
        return getDataTableAjaxResult(sgjsControlPointRetestList);
    }

    @PreAuthorize(hasPermi = "sgjsControlPointRetest:add")
    @PostMapping("/add")
    @CustomLogger(title = "施工技术--测量管理--控制点复测", name = "控制点复测" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertSgjsControlPointRetest(@Validated(ValidationGroups.Save.class) @RequestBody SgjsControlPointRetest sgjsControlPointRetestParam) {
        sgjsControlPointRetestService.insertSgjsControlPointRetest(sgjsControlPointRetestParam);
        return AjaxResult.success(sgjsControlPointRetestParam);
    }

    @PreAuthorize(hasPermi = "sgjsControlPointRetest:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "施工技术--测量管理--控制点复测", name = "控制点复测" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult insertSgjsControlPointRetestList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsControlPointRetest> sgjsControlPointRetestListParam) {
        sgjsControlPointRetestService.insertSgjsControlPointRetestList(sgjsControlPointRetestListParam);
        return AjaxResult.success(sgjsControlPointRetestListParam);
    }

    @PreAuthorize(hasPermi = "sgjsControlPointRetest:update")
    @PostMapping("/update")
    @CustomLogger(title = "施工技术--测量管理--控制点复测", name = "控制点复测" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateSgjsControlPointRetest(@Validated(ValidationGroups.Update.class) @RequestBody SgjsControlPointRetest sgjsControlPointRetestParam) {
        return toAjax(sgjsControlPointRetestService.updateSgjsControlPointRetest(sgjsControlPointRetestParam));
    }

    @PreAuthorize(hasPermi = "sgjsControlPointRetest:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "施工技术--测量管理--控制点复测", name = "控制点复测" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateSgjsControlPointRetestList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsControlPointRetest> sgjsControlPointRetestListParam) {
        return toAjax(sgjsControlPointRetestService.updateSgjsControlPointRetestList(sgjsControlPointRetestListParam));
    }

    @PreAuthorize(hasPermi = "sgjsControlPointRetest:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsControlPointRetest(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsControlPointRetest sgjsControlPointRetestParam) {
        return toAjax(sgjsControlPointRetestService.deleteSgjsControlPointRetest(sgjsControlPointRetestParam));
    }

    @PreAuthorize(hasPermi = "sgjsControlPointRetest:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsControlPointRetestByPks(@PathVariable Long[] ids) {
        List<Long> sgjsControlPointRetestPkList = Arrays.asList(ids);
        return toAjax(sgjsControlPointRetestService.deleteSgjsControlPointRetestByPks(sgjsControlPointRetestPkList));
    }

    @PostMapping("/export")
    @PreAuthorize(hasPermi = "sgjsControlPointRetest:export")
    @CustomLogger(title = "施工技术--测量管理--控制点复测", name = "控制点复测" ,businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response, @RequestBody SgjsControlPointRetest sgjsControlPointRetestParam) throws IOException {
        List<SgjsControlPointRetest> sgjsControlPointRetestList = sgjsControlPointRetestService.getSgjsControlPointRetestList(sgjsControlPointRetestParam);
        ExcelUtils<SgjsControlPointRetest> util = new ExcelUtils<>(SgjsControlPointRetest.class);
        util.exportExcel(response, sgjsControlPointRetestList, DateUtils.getDate());
    }
}
