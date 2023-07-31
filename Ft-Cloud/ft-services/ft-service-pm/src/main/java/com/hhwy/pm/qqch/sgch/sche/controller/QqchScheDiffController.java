package com.hhwy.pm.qqch.sgch.sche.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiff;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheDiffService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:48
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchScheDiff")
public class QqchScheDiffController extends BaseController {

    @Autowired
    private IQqchScheDiffService qqchScheDiffService;


    @PreAuthorize(hasPermi = "qqchScheDiff:list")
    @GetMapping
    public AjaxResult getQqchScheDiff(@Validated(ValidationGroups.Get.class) QqchScheDiff qqchScheDiffParam) {
        QqchScheDiff qqchScheDiff = qqchScheDiffService.getQqchScheDiff(qqchScheDiffParam);
        return AjaxResult.success(qqchScheDiff);
    }

    @PreAuthorize(hasPermi = "qqchScheDiff:list")
    @GetMapping("/list")
    public AjaxResult getQqchScheDiffList(@Validated(ValidationGroups.Select.class) QqchScheDiff qqchScheDiffParam) {
        startPage();
        List<QqchScheDiff> qqchScheDiffList = qqchScheDiffService.getQqchScheDiffList(qqchScheDiffParam);
        return getDataTableAjaxResult(qqchScheDiffList);
    }

    @PreAuthorize(hasPermi = "qqchScheDiff:add")
    @PostMapping("/add")
    public AjaxResult insertQqchScheDiff(@Validated(ValidationGroups.Save.class) @RequestBody QqchScheDiff qqchScheDiffParam) {
        qqchScheDiffService.insertQqchScheDiff(qqchScheDiffParam);
        return AjaxResult.success(qqchScheDiffParam);
    }

    @PreAuthorize(hasPermi = "qqchScheDiff:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchScheDiffList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchScheDiff> qqchScheDiffListParam) {
        qqchScheDiffService.insertQqchScheDiffList(qqchScheDiffListParam);
        return AjaxResult.success(qqchScheDiffListParam);
    }

    @PreAuthorize(hasPermi = "qqchScheDiff:update")
    @PostMapping("/update")
    public AjaxResult updateQqchScheDiff(@Validated(ValidationGroups.Update.class) @RequestBody QqchScheDiff qqchScheDiffParam) {
        return toAjax(qqchScheDiffService.updateQqchScheDiff(qqchScheDiffParam));
    }

    @PreAuthorize(hasPermi = "qqchScheDiff:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchScheDiffList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchScheDiff> qqchScheDiffListParam) {
        return toAjax(qqchScheDiffService.updateQqchScheDiffList(qqchScheDiffListParam));
    }

    @PreAuthorize(hasPermi = "qqchScheDiff:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchScheDiff(@Validated(ValidationGroups.Delete.class) @RequestBody QqchScheDiff qqchScheDiffParam) {
        return toAjax(qqchScheDiffService.deleteQqchScheDiff(qqchScheDiffParam));
    }

    @PreAuthorize(hasPermi = "qqchScheDiff:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchScheDiffByPks(@PathVariable Long[] ids) {
        List<Long> qqchScheDiffPkList = Arrays.asList(ids);
        return toAjax(qqchScheDiffService.deleteQqchScheDiffByPks(qqchScheDiffPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchScheDiff qqchScheDiffParam) throws IOException {
        List<QqchScheDiff> qqchScheDiffList = qqchScheDiffService.getQqchScheDiffList(qqchScheDiffParam);
        ExcelUtils<QqchScheDiff> util = new ExcelUtils<>(QqchScheDiff.class);
        util.exportExcel(response, qqchScheDiffList, DateUtils.getDate());
    }
}
