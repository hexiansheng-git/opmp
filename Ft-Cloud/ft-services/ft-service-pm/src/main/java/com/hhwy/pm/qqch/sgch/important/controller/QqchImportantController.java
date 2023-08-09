package com.hhwy.pm.qqch.sgch.important.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.common.domain.CompileDTO;
import com.hhwy.pm.qqch.sgch.important.domain.QqchImportant;
import com.hhwy.pm.qqch.sgch.important.service.IQqchImportantService;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheCorr;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 11:17:04
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchImportant")
public class QqchImportantController extends BaseController {

    @Autowired
    private IQqchImportantService qqchImportantService;


    @PreAuthorize(hasPermi = "qqchImportant:list")
    @GetMapping
    public AjaxResult getQqchImportant(@Validated(ValidationGroups.Get.class) CompileDTO<QqchImportant> qqchImportantParam) {
        QqchImportant qqchImportant = qqchImportantService.getQqchImportant(qqchImportantParam.dealListDto());
        return AjaxResult.success(qqchImportant);
    }

    @PreAuthorize(hasPermi = "qqchImportant:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) QqchImportant qqchImportantParam) {
        CompileDTO qqchImportantList = qqchImportantService.list(qqchImportantParam);
        return AjaxResult.success(qqchImportantList);
    }

    @PreAuthorize(hasPermi = "qqchImportant:add")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody CompileDTO<List<QqchImportant>> dto) {
        List<QqchImportant> qqchImportants = dto.dealSaveDto();
        qqchImportantService.save(qqchImportants);
        return AjaxResult.success(qqchImportants);
    }


    @PreAuthorize(hasPermi = "qqchImportant:importData")
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) {
        FtExcelUtil<QqchImportant> excelUtil = new FtExcelUtil<>(QqchImportant.class);
        try {
            List<QqchImportant> qqchImportants = excelUtil.importTreeExcel(file.getInputStream());
            return AjaxResult.success(qqchImportants);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/exportTemp")
    public void exportTemp(HttpServletRequest request, HttpServletResponse response) {
        FtExcelUtil<QqchScheCorr> excelUtil = new FtExcelUtil<>(QqchScheCorr.class);
        try {
            excelUtil.downloadTemplate(request, response, "importCorr.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PreAuthorize(hasPermi = "qqchImportant:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchImportantList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchImportant> qqchImportantListParam) {
        qqchImportantService.insertQqchImportantList(qqchImportantListParam);
        return AjaxResult.success(qqchImportantListParam);
    }

    @PreAuthorize(hasPermi = "qqchImportant:update")
    @PostMapping("/update")
    public AjaxResult updateQqchImportant(@Validated(ValidationGroups.Update.class) @RequestBody QqchImportant qqchImportantParam) {
        return toAjax(qqchImportantService.updateQqchImportant(qqchImportantParam));
    }

    @PreAuthorize(hasPermi = "qqchImportant:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchImportantList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchImportant> qqchImportantListParam) {
        return toAjax(qqchImportantService.updateQqchImportantList(qqchImportantListParam));
    }

    @PreAuthorize(hasPermi = "qqchImportant:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchImportant(@Validated(ValidationGroups.Delete.class) @RequestBody QqchImportant qqchImportantParam) {
        return toAjax(qqchImportantService.deleteQqchImportant(qqchImportantParam));
    }

    @PreAuthorize(hasPermi = "qqchImportant:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchImportantByPks(@PathVariable Long[] ids) {
        List<Long> qqchImportantPkList = Arrays.asList(ids);
        return toAjax(qqchImportantService.deleteQqchImportantByPks(qqchImportantPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchImportant qqchImportantParam) throws IOException {
        List<QqchImportant> qqchImportantList = qqchImportantService.getQqchImportantList(qqchImportantParam);
        ExcelUtils<QqchImportant> util = new ExcelUtils<>(QqchImportant.class);
        util.exportExcel(response, qqchImportantList, DateUtils.getDate());
    }


}
