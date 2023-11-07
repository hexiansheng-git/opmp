package com.hhwy.pm.qqch.qqchChange.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.qqchChange.domain.QqchChange;
import com.hhwy.pm.qqch.qqchChange.service.IQqchChangeService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 前期策划变更
 * @author wk
 * @date 2023-11-06 17:41:43
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchChange")
public class QqchChangeController extends BaseController {

    @Autowired
    private IQqchChangeService qqchChangeService;

    @PreAuthorize(hasPermi = "qqchChange:list")
    @GetMapping("/list")
    public AjaxResult getQqchChangeList(@Validated(ValidationGroups.Select.class) QqchChange qqchChangeParam) {
        startPage();
        List<QqchChange> qqchChangeList = qqchChangeService.list(qqchChangeParam);
        return getDataTableAjaxResult(qqchChangeList);
    }

    @PreAuthorize(hasPermi = "qqchChange:add")
    @PostMapping("/add")
    public AjaxResult insertQqchChange(@Validated(ValidationGroups.Save.class) @RequestBody QqchChange qqchChangeParam) {
        qqchChangeService.insertQqchChange(qqchChangeParam);
        return AjaxResult.success(qqchChangeParam);
    }

    @PreAuthorize(hasPermi = "qqchChange:update")
    @PostMapping("/update")
    public AjaxResult updateQqchChange(@Validated(ValidationGroups.Update.class) @RequestBody QqchChange qqchChangeParam) {
        return toAjax(qqchChangeService.updateQqchChange(qqchChangeParam));
    }

    @PreAuthorize(hasPermi = "qqchChange:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchChangeList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchChange> qqchChangeListParam) {
        return toAjax(qqchChangeService.updateQqchChangeList(qqchChangeListParam));
    }

    @PreAuthorize(hasPermi = "qqchChange:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchChange(@Validated(ValidationGroups.Delete.class) @RequestBody QqchChange qqchChangeParam) {
        return toAjax(qqchChangeService.deleteQqchChange(qqchChangeParam));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchChange qqchChangeParam) throws IOException {
        List<QqchChange> qqchChangeList = qqchChangeService.getQqchChangeList(qqchChangeParam);
        ExcelUtils<QqchChange> util = new ExcelUtils<>(QqchChange.class);
        util.exportExcel(response, qqchChangeList, DateUtils.getDate());
    }
}
