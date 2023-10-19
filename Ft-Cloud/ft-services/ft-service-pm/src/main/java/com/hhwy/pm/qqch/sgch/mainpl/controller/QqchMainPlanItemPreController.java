package com.hhwy.pm.qqch.sgch.mainpl.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItemPre;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanItemPreService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author cjh
 * @date 2023-09-19 11:49:57
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchMainPlanItemPre")
public class QqchMainPlanItemPreController extends BaseController {

    @Autowired
    private IQqchMainPlanItemPreService qqchMainPlanItemPreService;


    //  @PreAuthorize(hasPermi = "qqchMainPlanItemPre:list")
    @GetMapping
    public AjaxResult getQqchMainPlanItemPre(@Validated(ValidationGroups.Get.class) QqchMainPlanItemPre qqchMainPlanItemPreParam) {
        QqchMainPlanItemPre qqchMainPlanItemPre = qqchMainPlanItemPreService.getQqchMainPlanItemPre(qqchMainPlanItemPreParam);
        return AjaxResult.success(qqchMainPlanItemPre);
    }

    //  @PreAuthorize(hasPermi = "qqchMainPlanItemPre:list")
    @GetMapping("/list")
    public AjaxResult getQqchMainPlanItemPreList(@Validated(ValidationGroups.Select.class) QqchMainPlanItemPre qqchMainPlanItemPreParam) {
        startPage();
        List<QqchMainPlanItemPre> qqchMainPlanItemPreList = qqchMainPlanItemPreService.getQqchMainPlanItemPreList(qqchMainPlanItemPreParam);
        return getDataTableAjaxResult(qqchMainPlanItemPreList);
    }

    @PreAuthorize(hasPermi = "qqchMainPlanItemPre:add")
    @PostMapping("/add")
    public AjaxResult insertQqchMainPlanItemPre(@Validated(ValidationGroups.Save.class) @RequestBody QqchMainPlanItemPre qqchMainPlanItemPreParam) {
        qqchMainPlanItemPreService.insertQqchMainPlanItemPre(qqchMainPlanItemPreParam);
        return AjaxResult.success(qqchMainPlanItemPreParam);
    }

    @PreAuthorize(hasPermi = "qqchMainPlanItemPre:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchMainPlanItemPreList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchMainPlanItemPre> qqchMainPlanItemPreListParam) {
        qqchMainPlanItemPreService.insertQqchMainPlanItemPreList(qqchMainPlanItemPreListParam);
        return AjaxResult.success(qqchMainPlanItemPreListParam);
    }

    @PreAuthorize(hasPermi = "qqchMainPlanItemPre:update")
    @PostMapping("/update")
    public AjaxResult updateQqchMainPlanItemPre(@Validated(ValidationGroups.Update.class) @RequestBody QqchMainPlanItemPre qqchMainPlanItemPreParam) {
        return toAjax(qqchMainPlanItemPreService.updateQqchMainPlanItemPre(qqchMainPlanItemPreParam));
    }

    @PreAuthorize(hasPermi = "qqchMainPlanItemPre:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchMainPlanItemPreList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchMainPlanItemPre> qqchMainPlanItemPreListParam) {
        return toAjax(qqchMainPlanItemPreService.updateQqchMainPlanItemPreList(qqchMainPlanItemPreListParam));
    }

    @PreAuthorize(hasPermi = "qqchMainPlanItemPre:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchMainPlanItemPre(@Validated(ValidationGroups.Delete.class) @RequestBody QqchMainPlanItemPre qqchMainPlanItemPreParam) {
        return toAjax(qqchMainPlanItemPreService.deleteQqchMainPlanItemPre(qqchMainPlanItemPreParam));
    }

    @PreAuthorize(hasPermi = "qqchMainPlanItemPre:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchMainPlanItemPreByPks(@PathVariable Long[] ids) {
        List<Long> qqchMainPlanItemPrePkList = Arrays.asList(ids);
        return toAjax(qqchMainPlanItemPreService.deleteQqchMainPlanItemPreByPks(qqchMainPlanItemPrePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchMainPlanItemPre qqchMainPlanItemPreParam) throws IOException {
        List<QqchMainPlanItemPre> qqchMainPlanItemPreList = qqchMainPlanItemPreService.getQqchMainPlanItemPreList(qqchMainPlanItemPreParam);
        ExcelUtils<QqchMainPlanItemPre> util = new ExcelUtils<>(QqchMainPlanItemPre.class);
        util.exportExcel(response, qqchMainPlanItemPreList, DateUtils.getDate());
    }
}
