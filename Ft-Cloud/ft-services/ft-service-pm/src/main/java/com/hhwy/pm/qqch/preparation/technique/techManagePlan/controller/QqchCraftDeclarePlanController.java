package com.hhwy.pm.qqch.preparation.technique.techManagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchCraftDeclarePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchCraftDeclarePlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchCraftDeclarePlanImportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchCraftDeclarePlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchCraftDeclarePlanService;
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
 * @date 2023-07-25 10:40:02
 * @remark 工艺工法申报计划
 */
@Validated
@RestController
@RequestMapping("/qqchCraftDeclarePlan")
public class QqchCraftDeclarePlanController extends BaseController {

    @Autowired
    private IQqchCraftDeclarePlanService qqchCraftDeclarePlanService;


    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:list")
    @GetMapping
    public AjaxResult getQqchCraftDeclarePlan(@Validated(ValidationGroups.Get.class) QqchCraftDeclarePlan qqchCraftDeclarePlanParam) {
        QqchCraftDeclarePlan qqchCraftDeclarePlan = qqchCraftDeclarePlanService.getQqchCraftDeclarePlan(qqchCraftDeclarePlanParam);
        return AjaxResult.success(qqchCraftDeclarePlan);
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchCraftDeclarePlanList(@Validated(ValidationGroups.Select.class) QqchCraftDeclarePlan qqchCraftDeclarePlanParam) {
        startPage();
        List<QqchCraftDeclarePlan> qqchCraftDeclarePlanList = qqchCraftDeclarePlanService.getQqchCraftDeclarePlanList(qqchCraftDeclarePlanParam);
        return getDataTableAjaxResult(qqchCraftDeclarePlanList);
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchCraftDeclarePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchCraftDeclarePlan qqchCraftDeclarePlanParam) {
        qqchCraftDeclarePlanService.insertQqchCraftDeclarePlan(qqchCraftDeclarePlanParam);
        return AjaxResult.success(qqchCraftDeclarePlanParam);
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchCraftDeclarePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchCraftDeclarePlan qqchCraftDeclarePlanParam) {
        return toAjax(qqchCraftDeclarePlanService.updateQqchCraftDeclarePlan(qqchCraftDeclarePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchCraftDeclarePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchCraftDeclarePlan> qqchCraftDeclarePlanListParam) {
        return toAjax(qqchCraftDeclarePlanService.updateQqchCraftDeclarePlanList(qqchCraftDeclarePlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchCraftDeclarePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchCraftDeclarePlan qqchCraftDeclarePlanParam) {
        return toAjax(qqchCraftDeclarePlanService.deleteQqchCraftDeclarePlan(qqchCraftDeclarePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchCraftDeclarePlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchCraftDeclarePlanPkList = Arrays.asList(ids);
        return toAjax(qqchCraftDeclarePlanService.deleteQqchCraftDeclarePlanByPks(qqchCraftDeclarePlanPkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    public AjaxResult importData(@RequestPart("file") MultipartFile file){
        ExcelUtils<QqchCraftDeclarePlanImportVo> util = new ExcelUtils<>(QqchCraftDeclarePlanImportVo.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<QqchCraftDeclarePlanImportVo> qqchCraftDeclarePlanImportVoList = util.importExcel(inputStream);
            return AjaxResult.success(qqchCraftDeclarePlanImportVoList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    /**
     * 导出
     * @param response
     * @param qqchCraftDeclarePlan
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchCraftDeclarePlan qqchCraftDeclarePlan) throws IOException {
        List<QqchCraftDeclarePlanExportVo> qqchCraftDeclarePlanExportVoList = qqchCraftDeclarePlanService.getQqchCraftDeclarePlanExportVoList(qqchCraftDeclarePlan);
        ExcelUtils<QqchCraftDeclarePlanExportVo> util = new ExcelUtils<>(QqchCraftDeclarePlanExportVo.class);
        util.exportExcel(response, qqchCraftDeclarePlanExportVoList, DateUtils.getDate());
    }

    /**
     * 获取工艺工法申报计划Vo
     * @param qqchCraftDeclarePlan
     * @return
     */
    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:list")
    @GetMapping("getQqchCraftDeclarePlanVo")
    public AjaxResult getQqchCraftDeclarePlanVo(@Validated(ValidationGroups.Get.class) QqchCraftDeclarePlan qqchCraftDeclarePlan) {
        QqchCraftDeclarePlanVo qqchCraftDeclarePlanVo = qqchCraftDeclarePlanService.getQqchCraftDeclarePlanVo(qqchCraftDeclarePlan);
        return AjaxResult.success(qqchCraftDeclarePlanVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchCraftDeclarePlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchCraftDeclarePlan:update")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchCraftDeclarePlanVo qqchCraftDeclarePlanVo) {
        qqchCraftDeclarePlanService.save(qqchCraftDeclarePlanVo);
        return AjaxResult.success();
    }
}
