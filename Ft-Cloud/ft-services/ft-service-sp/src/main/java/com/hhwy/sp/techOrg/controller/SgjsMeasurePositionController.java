package com.hhwy.sp.techOrg.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.sp.techOrg.domain.SgjsMeasurePosition;
import com.hhwy.sp.techOrg.service.ISgjsMeasurePositionService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author lcf
 * @date 2023-11-20 10:41:18
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsMeasurePosition")
public class SgjsMeasurePositionController extends BaseController{

    @Autowired
    private ISgjsMeasurePositionService sgjsMeasurePositionService;

    /**
     * 列表查询
     *
     * @param sgjsMeasurePositionParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsMeasurePosition:list")
    @GetMapping("/list")
    public AjaxResult getSgjsMeasurePositionList(@Validated(ValidationGroups.Select.class) SgjsMeasurePosition sgjsMeasurePositionParam){
        startPage();
        List<SgjsMeasurePosition> sgjsMeasurePositionList = sgjsMeasurePositionService.getSgjsMeasurePositionList(sgjsMeasurePositionParam);
        return getDataTableAjaxResult(sgjsMeasurePositionList);
    }

    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsMeasurePosition:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsMeasurePositionList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsMeasurePosition> list){
        sgjsMeasurePositionService.insertSgjsMeasurePositionList(list);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgjsMeasurePosition:list")
    @GetMapping
    public AjaxResult getSgjsMeasurePosition(@Validated(ValidationGroups.Get.class)  SgjsMeasurePosition sgjsMeasurePositionParam){
        SgjsMeasurePosition sgjsMeasurePosition =  sgjsMeasurePositionService.getSgjsMeasurePosition(sgjsMeasurePositionParam);
        return AjaxResult.success(sgjsMeasurePosition);
    }



    @PreAuthorize(hasPermi = "sgjsMeasurePosition:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsMeasurePosition(@Validated(ValidationGroups.Save.class) @RequestBody SgjsMeasurePosition sgjsMeasurePositionParam){
        sgjsMeasurePositionService.insertSgjsMeasurePosition(sgjsMeasurePositionParam);
        return AjaxResult.success(sgjsMeasurePositionParam);
    }



    @PreAuthorize(hasPermi = "sgjsMeasurePosition:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsMeasurePosition(@Validated(ValidationGroups.Update.class) @RequestBody SgjsMeasurePosition sgjsMeasurePositionParam){
        return toAjax(sgjsMeasurePositionService.updateSgjsMeasurePosition(sgjsMeasurePositionParam));
    }

    @PreAuthorize(hasPermi = "sgjsMeasurePosition:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsMeasurePositionList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsMeasurePosition> sgjsMeasurePositionListParam){
        return toAjax(sgjsMeasurePositionService.updateSgjsMeasurePositionList(sgjsMeasurePositionListParam));
    }

    @PreAuthorize(hasPermi = "sgjsMeasurePosition:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsMeasurePosition(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsMeasurePosition sgjsMeasurePositionParam){
        return toAjax(sgjsMeasurePositionService.deleteSgjsMeasurePosition(sgjsMeasurePositionParam));
    }

    @PreAuthorize(hasPermi = "sgjsMeasurePosition:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsMeasurePositionByPks(@PathVariable Long[] ids){
        List<Long> sgjsMeasurePositionPkList = Arrays.asList(ids);
        return toAjax(sgjsMeasurePositionService.deleteSgjsMeasurePositionByPks(sgjsMeasurePositionPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsMeasurePosition sgjsMeasurePositionParam) throws IOException {
        List<SgjsMeasurePosition> sgjsMeasurePositionList = sgjsMeasurePositionService.getSgjsMeasurePositionList(sgjsMeasurePositionParam);
        ExcelUtils<SgjsMeasurePosition> util = new ExcelUtils<>(SgjsMeasurePosition.class);
        util.exportExcel(response, sgjsMeasurePositionList, DateUtils.getDate());
    }
}
