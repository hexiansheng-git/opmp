package com.hhwy.pm.qqch.preparation.technique.techManagePlan.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchPatentDeclarePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchPatentDeclarePlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchPatentDeclarePlanImportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchPatentDeclarePlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchPatentDeclarePlanService;
import com.hhwy.utils.excel.FtExcelUtil;
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
 * @date 2023-07-25 10:40:39
 * @remark 专利申报计划
 */
@Validated
@RestController
@RequestMapping("/qqchPatentDeclarePlan")
public class QqchPatentDeclarePlanController extends BaseController {

    @Autowired
    private IQqchPatentDeclarePlanService qqchPatentDeclarePlanService;


    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:list")
    @GetMapping
    public AjaxResult getQqchPatentDeclarePlan(@Validated(ValidationGroups.Get.class) QqchPatentDeclarePlan qqchPatentDeclarePlanParam) {
        QqchPatentDeclarePlan qqchPatentDeclarePlan = qqchPatentDeclarePlanService.getQqchPatentDeclarePlan(qqchPatentDeclarePlanParam);
        return AjaxResult.success(qqchPatentDeclarePlan);
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:list")
    @GetMapping("/list")
    public AjaxResult getQqchPatentDeclarePlanList(@Validated(ValidationGroups.Select.class) QqchPatentDeclarePlan qqchPatentDeclarePlanParam) {
        startPage();
        List<QqchPatentDeclarePlan> qqchPatentDeclarePlanList = qqchPatentDeclarePlanService.getQqchPatentDeclarePlanList(qqchPatentDeclarePlanParam);
        return getDataTableAjaxResult(qqchPatentDeclarePlanList);
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:add")
    @PostMapping("/add")
    public AjaxResult insertQqchPatentDeclarePlan(@Validated(ValidationGroups.Save.class) @RequestBody QqchPatentDeclarePlan qqchPatentDeclarePlanParam) {
        qqchPatentDeclarePlanService.insertQqchPatentDeclarePlan(qqchPatentDeclarePlanParam);
        return AjaxResult.success(qqchPatentDeclarePlanParam);
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:update")
    @PostMapping("/update")
    public AjaxResult updateQqchPatentDeclarePlan(@Validated(ValidationGroups.Update.class) @RequestBody QqchPatentDeclarePlan qqchPatentDeclarePlanParam) {
        return toAjax(qqchPatentDeclarePlanService.updateQqchPatentDeclarePlan(qqchPatentDeclarePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchPatentDeclarePlanList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchPatentDeclarePlan> qqchPatentDeclarePlanListParam) {
        return toAjax(qqchPatentDeclarePlanService.updateQqchPatentDeclarePlanList(qqchPatentDeclarePlanListParam));
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchPatentDeclarePlan(@Validated(ValidationGroups.Delete.class) @RequestBody QqchPatentDeclarePlan qqchPatentDeclarePlanParam) {
        return toAjax(qqchPatentDeclarePlanService.deleteQqchPatentDeclarePlan(qqchPatentDeclarePlanParam));
    }

    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchPatentDeclarePlanByPks(@PathVariable Long[] ids) {
        List<Long> qqchPatentDeclarePlanPkList = Arrays.asList(ids);
        return toAjax(qqchPatentDeclarePlanService.deleteQqchPatentDeclarePlanByPks(qqchPatentDeclarePlanPkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("/import")
    public AjaxResult importData(@RequestPart("file") MultipartFile file){
        FtExcelUtil<QqchPatentDeclarePlanImportVo> util = new FtExcelUtil<>(QqchPatentDeclarePlanImportVo.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<QqchPatentDeclarePlanImportVo> qqchPatentDeclarePlanImportVoList = util.importExcel(inputStream);
            return AjaxResult.success(qqchPatentDeclarePlanImportVoList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    /**
     * 导出
     * @param response
     * @param qqchPatentDeclarePlan
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchPatentDeclarePlan qqchPatentDeclarePlan) throws IOException {
        List<QqchPatentDeclarePlanExportVo> qqchPatentDeclarePlanExportVoList = qqchPatentDeclarePlanService.getQqchPatentDeclarePlanExportVoList(qqchPatentDeclarePlan);
        ExcelUtils<QqchPatentDeclarePlanExportVo> util = new ExcelUtils<>(QqchPatentDeclarePlanExportVo.class);
        util.exportExcel(response, qqchPatentDeclarePlanExportVoList, DateUtils.getDate());
    }

    /**
     * 获取专利申报计划Vo
     * @param qqchPatentDeclarePlan
     * @return
     */
    @GetMapping("getQqchPatentDeclarePlanVo")
    public AjaxResult getQqchPatentDeclarePlanVo(@Validated(ValidationGroups.Get.class) QqchPatentDeclarePlan qqchPatentDeclarePlan) {
        QqchPatentDeclarePlanVo qqchPatentDeclarePlanVo = qqchPatentDeclarePlanService.getQqchPatentDeclarePlanVo(qqchPatentDeclarePlan);
        return AjaxResult.success(qqchPatentDeclarePlanVo);
    }

    /**
     * 保存/确认/提交
     * @param qqchPatentDeclarePlanVo
     * @return
     */
    @PreAuthorize(hasPermi = "qqchPatentDeclarePlan:save")
    @PostMapping("/save")
    public AjaxResult save(@Validated(ValidationGroups.Save.class) @RequestBody QqchPatentDeclarePlanVo qqchPatentDeclarePlanVo) {
        qqchPatentDeclarePlanService.save(qqchPatentDeclarePlanVo);
        return AjaxResult.success();
    }
}
