package com.hhwy.pm.qqch.sgch.sche.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheCorr;
import com.hhwy.pm.qqch.sgch.sche.dto.QqchScheDTO;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:48
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sche")
public class QqchScheController extends BaseController {

    @Autowired
    private IQqchScheService qqchScheService;


//    @PreAuthorize(hasPermi = "qqchScheDiff:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) QqchScheDTO dto) {
        return AjaxResult.success(qqchScheService.list(dto));
    }

//    @PreAuthorize(hasPermi = "qqchScheDiff:save")
    @PostMapping("/save")
    public AjaxResult save(@RequestBody QqchScheDTO dto) {
        qqchScheService.save(dto);
        return AjaxResult.success("操作成功");
    }


    @PostMapping("/importCorr")
    public AjaxResult importCorr(@RequestParam("file") MultipartFile file) {
        FtExcelUtil<QqchScheCorr> excelUtil = new FtExcelUtil<>(QqchScheCorr.class);
        try {
            List<QqchScheCorr> qqchScheCorrs = excelUtil.importTreeExcel(file.getInputStream());
            return AjaxResult.success(qqchScheCorrs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/downCorr")
    public void downCorr(HttpServletRequest request, HttpServletResponse response) {
        FtExcelUtil<QqchScheCorr> excelUtil = new FtExcelUtil<>(QqchScheCorr.class);
        try {
            excelUtil.downloadTemplate(request, response, "importCorr.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
