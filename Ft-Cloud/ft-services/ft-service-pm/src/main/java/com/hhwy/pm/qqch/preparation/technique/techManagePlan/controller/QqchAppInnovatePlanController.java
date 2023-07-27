package com.hhwy.pm.qqch.preparation.technique.techManagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAppInnovatePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAppInnovatePlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAppInnovatePlanImportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAppInnovatePlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchAppInnovatePlanService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:47
 * @remark 四新应用及创新计划
 */
@Validated
@RestController
@RequestMapping("/qqchAppInnovatePlan")
public class QqchAppInnovatePlanController extends BaseController {

    @Autowired
    private IQqchAppInnovatePlanService qqchAppInnovatePlanService;


    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:list")
    @GetMapping
    public AjaxResult getQqchAppInnovatePlan(@Validated(ValidationGroups.Get.class) QqchAppInnovatePlan qqchAppInnovatePlanParam) {
        QqchAppInnovatePlan qqchAppInnovatePlan = qqchAppInnovatePlanService.getQqchAppInnovatePlan(qqchAppInnovatePlanParam);
        return AjaxResult.success(qqchAppInnovatePlan);
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchAppInnovatePlanList(@Validated(ValidationGroups.Select.class) QqchAppInnovatePlan qqchAppInnovatePlanParam) {
        startPage();
        List<QqchAppInnovatePlan> qqchAppInnovatePlanList = qqchAppInnovatePlanService.getQqchAppInnovatePlanList(qqchAppInnovatePlanParam);
        return getDataTableAjaxResult(qqchAppInnovatePlanList);
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchAppInnovatePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchAppInnovatePlan qqchAppInnovatePlanParam) {
        qqchAppInnovatePlanService.insertQqchAppInnovatePlan(qqchAppInnovatePlanParam);
        return AjaxResult.success(qqchAppInnovatePlanParam);
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchAppInnovatePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchAppInnovatePlan qqchAppInnovatePlanParam) {
        return toAjax(qqchAppInnovatePlanService.updateQqchAppInnovatePlan(qqchAppInnovatePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchAppInnovatePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchAppInnovatePlan> qqchAppInnovatePlanListParam) {
        return toAjax(qqchAppInnovatePlanService.updateQqchAppInnovatePlanList(qqchAppInnovatePlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchAppInnovatePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchAppInnovatePlan qqchAppInnovatePlanParam) {
        return toAjax(qqchAppInnovatePlanService.deleteQqchAppInnovatePlan(qqchAppInnovatePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchAppInnovatePlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchAppInnovatePlanPkList = Arrays.asList(ids);
        return toAjax(qqchAppInnovatePlanService.deleteQqchAppInnovatePlanByPks(qqchAppInnovatePlanPkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    public AjaxResult importData(@RequestPart("file") MultipartFile file){
        ExcelUtils<QqchAppInnovatePlanImportVo> util = new ExcelUtils<>(QqchAppInnovatePlanImportVo.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<QqchAppInnovatePlanImportVo> qqchAppInnovatePlanImportVoList = util.importExcel(inputStream);
            return AjaxResult.success(qqchAppInnovatePlanImportVoList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    /**
     * 导出
     * @param response
     * @param qqchAppInnovatePlan
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchAppInnovatePlan qqchAppInnovatePlan) throws IOException {
        List<QqchAppInnovatePlanExportVo> qqchAppInnovatePlanExportVoList = qqchAppInnovatePlanService.getQqchAppInnovatePlanExportVoList(qqchAppInnovatePlan);
        ExcelUtils<QqchAppInnovatePlanExportVo> util = new ExcelUtils<>(QqchAppInnovatePlanExportVo.class);
        util.exportExcel(response, qqchAppInnovatePlanExportVoList, DateUtils.getDate());
    }

    /**
     * 获取四新应用及创新计划Vo
     * @param qqchAppInnovatePlan
     * @return
     */
    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:list")
    @GetMapping("getQqchAppInnovatePlanVo")
    public AjaxResult getQqchAppInnovatePlanVo(@Validated(ValidationGroups.Get.class) QqchAppInnovatePlan qqchAppInnovatePlan) {
        QqchAppInnovatePlanVo qqchAppInnovatePlanVo = qqchAppInnovatePlanService.getQqchAppInnovatePlanVo(qqchAppInnovatePlan);
        return AjaxResult.success(qqchAppInnovatePlanVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchAppInnovatePlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchAppInnovatePlan:update")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchAppInnovatePlanVo qqchAppInnovatePlanVo) {
        qqchAppInnovatePlanService.save(qqchAppInnovatePlanVo);
        return AjaxResult.success();
    }
}
