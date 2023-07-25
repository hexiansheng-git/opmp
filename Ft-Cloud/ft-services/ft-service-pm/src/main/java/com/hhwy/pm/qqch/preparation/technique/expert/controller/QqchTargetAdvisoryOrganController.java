package com.hhwy.pm.qqch.preparation.technique.expert.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetAdvisoryOrgan;
import com.hhwy.pm.qqch.preparation.technique.expert.service.IQqchTargetAdvisoryOrganService;
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
 * @date 2023-07-25 10:56:43
 * @remark 外部目标咨询机构选择
 */
@Validated
@RestController
@RequestMapping("/qqchTargetAdvisoryOrgan")
public class QqchTargetAdvisoryOrganController extends BaseController {

    @Autowired
    private IQqchTargetAdvisoryOrganService qqchTargetAdvisoryOrganService;


    @PreAuthorize(hasPermi = "qqchTargetAdvisoryOrgan:list")
    @GetMapping
    public AjaxResult getQqchTargetAdvisoryOrgan(@Validated(ValidationGroups.Get.class) QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrganParam) {
        QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan = qqchTargetAdvisoryOrganService.getQqchTargetAdvisoryOrgan(qqchTargetAdvisoryOrganParam);
        return AjaxResult.success(qqchTargetAdvisoryOrgan);
    }

    @PreAuthorize(hasPermi = "qqchTargetAdvisoryOrgan:list")
    @GetMapping("/list")
    public AjaxResult getQqchTargetAdvisoryOrganList(@Validated(ValidationGroups.Select.class) QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrganParam) {
        startPage();
        List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList = qqchTargetAdvisoryOrganService.getQqchTargetAdvisoryOrganList(qqchTargetAdvisoryOrganParam);
        return getDataTableAjaxResult(qqchTargetAdvisoryOrganList);
    }

    @PreAuthorize(hasPermi = "qqchTargetAdvisoryOrgan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchTargetAdvisoryOrgan(@Validated(ValidationGroups.Save.class) @RequestBody QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrganParam) {
        qqchTargetAdvisoryOrganService.insertQqchTargetAdvisoryOrgan(qqchTargetAdvisoryOrganParam);
        return AjaxResult.success(qqchTargetAdvisoryOrganParam);
    }

    @PreAuthorize(hasPermi = "qqchTargetAdvisoryOrgan:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchTargetAdvisoryOrganList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganListParam) {
        qqchTargetAdvisoryOrganService.insertQqchTargetAdvisoryOrganList(qqchTargetAdvisoryOrganListParam);
        return AjaxResult.success(qqchTargetAdvisoryOrganListParam);
    }

    @PreAuthorize(hasPermi = "qqchTargetAdvisoryOrgan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchTargetAdvisoryOrgan(@Validated(ValidationGroups.Update.class) @RequestBody QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrganParam) {
        return toAjax(qqchTargetAdvisoryOrganService.updateQqchTargetAdvisoryOrgan(qqchTargetAdvisoryOrganParam));
    }

    @PreAuthorize(hasPermi = "qqchTargetAdvisoryOrgan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchTargetAdvisoryOrganList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganListParam) {
        return toAjax(qqchTargetAdvisoryOrganService.updateQqchTargetAdvisoryOrganList(qqchTargetAdvisoryOrganListParam));
    }

    @PreAuthorize(hasPermi = "qqchTargetAdvisoryOrgan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchTargetAdvisoryOrgan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrganParam) {
        return toAjax(qqchTargetAdvisoryOrganService.deleteQqchTargetAdvisoryOrgan(qqchTargetAdvisoryOrganParam));
    }

    @PreAuthorize(hasPermi = "qqchTargetAdvisoryOrgan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchTargetAdvisoryOrganByPks(@PathVariable Long[] ids) {
        List<Long> qqchTargetAdvisoryOrganPkList = Arrays.asList(ids);
        return toAjax(qqchTargetAdvisoryOrganService.deleteQqchTargetAdvisoryOrganByPks(qqchTargetAdvisoryOrganPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrganParam) throws IOException {
        List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList = qqchTargetAdvisoryOrganService.getQqchTargetAdvisoryOrganList(qqchTargetAdvisoryOrganParam);
        ExcelUtils<QqchTargetAdvisoryOrgan> util = new ExcelUtils<>(QqchTargetAdvisoryOrgan.class);
        util.exportExcel(response, qqchTargetAdvisoryOrganList, DateUtils.getDate());
    }
}
