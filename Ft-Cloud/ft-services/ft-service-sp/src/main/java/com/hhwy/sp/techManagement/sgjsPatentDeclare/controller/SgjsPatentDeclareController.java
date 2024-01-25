package com.hhwy.sp.techManagement.sgjsPatentDeclare.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.domain.SgjsPatentDeclare;
import com.hhwy.sp.techManagement.sgjsPatentDeclare.service.ISgjsPatentDeclareService;
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
 * @date 2024-01-25 11:01:25
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsPatentDeclare")
public class SgjsPatentDeclareController extends BaseController {

    @Autowired
    private ISgjsPatentDeclareService sgjsPatentDeclareService;


    /**
     * 根据id获取数据
     * @param id
     * @param type 1：编辑   2：成果登记
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsPatentDeclare:list")
    @GetMapping("getSgjsPatentDeclareById")
    public AjaxResult getSgjsPatentDeclareById(Long id,String type) {
        SgjsPatentDeclare sgjsPatentDeclare = sgjsPatentDeclareService.getSgjsPatentDeclareById(id, type);
        return AjaxResult.success(sgjsPatentDeclare);
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:list")
    @GetMapping("/list")
    public AjaxResult getSgjsPatentDeclareList(@Validated(ValidationGroups.Select.class) SgjsPatentDeclare sgjsPatentDeclareParam) {
        startPage();
        List<SgjsPatentDeclare> sgjsPatentDeclareList = sgjsPatentDeclareService.getSgjsPatentDeclareList(sgjsPatentDeclareParam);
        return getDataTableAjaxResult(sgjsPatentDeclareList);
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsPatentDeclare(@Validated(ValidationGroups.Save.class) @RequestBody SgjsPatentDeclare sgjsPatentDeclareParam) {
        sgjsPatentDeclareService.insertSgjsPatentDeclare(sgjsPatentDeclareParam);
        return AjaxResult.success(sgjsPatentDeclareParam);
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsPatentDeclareList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsPatentDeclare> sgjsPatentDeclareListParam) {
        sgjsPatentDeclareService.insertSgjsPatentDeclareList(sgjsPatentDeclareListParam);
        return AjaxResult.success(sgjsPatentDeclareListParam);
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsPatentDeclare(@Validated(ValidationGroups.Update.class) @RequestBody SgjsPatentDeclare sgjsPatentDeclareParam) {
        return toAjax(sgjsPatentDeclareService.updateSgjsPatentDeclare(sgjsPatentDeclareParam));
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsPatentDeclareList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsPatentDeclare> sgjsPatentDeclareListParam) {
        return toAjax(sgjsPatentDeclareService.updateSgjsPatentDeclareList(sgjsPatentDeclareListParam));
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsPatentDeclare(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsPatentDeclare sgjsPatentDeclareParam) {
        return toAjax(sgjsPatentDeclareService.deleteSgjsPatentDeclare(sgjsPatentDeclareParam));
    }

    @PreAuthorize(hasPermi = "sgjsPatentDeclare:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsPatentDeclareByPks(@PathVariable Long[] ids) {
        List<Long> sgjsPatentDeclarePkList = Arrays.asList(ids);
        return toAjax(sgjsPatentDeclareService.deleteSgjsPatentDeclareByPks(sgjsPatentDeclarePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsPatentDeclare sgjsPatentDeclareParam) throws IOException {
        List<SgjsPatentDeclare> sgjsPatentDeclareList = sgjsPatentDeclareService.getSgjsPatentDeclareList(sgjsPatentDeclareParam);
        ExcelUtils<SgjsPatentDeclare> util = new ExcelUtils<>(SgjsPatentDeclare.class);
        util.exportExcel(response, sgjsPatentDeclareList, DateUtils.getDate());
    }
}
