package com.hhwy.sp.techOrg.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.Map;

import com.hhwy.sp.techOrg.domain.SgjsExperimentPosition;
import com.hhwy.sp.techOrg.service.ISgjsExperimentPositionService;
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
 * @date 2023-11-20 11:39:49
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsExperimentPosition")
public class SgjsExperimentPositionController extends BaseController{

    @Autowired
    private ISgjsExperimentPositionService sgjsExperimentPositionService;


    /**
     * 列表查询
     *
     * @param sgjsExperimentPositionParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsExperimentPosition:list")
    @GetMapping("/list")
    public AjaxResult getSgjsExperimentPositionList(@Validated(ValidationGroups.Select.class) SgjsExperimentPosition sgjsExperimentPositionParam){
        startPage();
        List<SgjsExperimentPosition> sgjsExperimentPositionList = sgjsExperimentPositionService.getSgjsExperimentPositionList(sgjsExperimentPositionParam);
        return getDataTableAjaxResult(sgjsExperimentPositionList);
    }


    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsExperimentPosition:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsExperimentPositionList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsExperimentPosition> list){
        sgjsExperimentPositionService.insertSgjsExperimentPositionList(list);
        return AjaxResult.success(list);
    }


    @PreAuthorize(hasPermi = "sgjsExperimentPosition:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsExperimentPosition(@Validated(ValidationGroups.Save.class) @RequestBody SgjsExperimentPosition sgjsExperimentPositionParam){
        sgjsExperimentPositionService.insertSgjsExperimentPosition(sgjsExperimentPositionParam);
        return AjaxResult.success(sgjsExperimentPositionParam);
    }


    @PreAuthorize(hasPermi = "sgjsExperimentPosition:list")
    @GetMapping
    public AjaxResult getSgjsExperimentPosition(@Validated(ValidationGroups.Get.class)  SgjsExperimentPosition sgjsExperimentPositionParam){
        SgjsExperimentPosition sgjsExperimentPosition =  sgjsExperimentPositionService.getSgjsExperimentPosition(sgjsExperimentPositionParam);
        return AjaxResult.success(sgjsExperimentPosition);
    }

    @PreAuthorize(hasPermi = "sgjsExperimentPosition:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsExperimentPosition(@Validated(ValidationGroups.Update.class) @RequestBody SgjsExperimentPosition sgjsExperimentPositionParam){
        return toAjax(sgjsExperimentPositionService.updateSgjsExperimentPosition(sgjsExperimentPositionParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentPosition:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsExperimentPositionList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsExperimentPosition> sgjsExperimentPositionListParam){
        return toAjax(sgjsExperimentPositionService.updateSgjsExperimentPositionList(sgjsExperimentPositionListParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentPosition:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsExperimentPosition(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsExperimentPosition sgjsExperimentPositionParam){
        return toAjax(sgjsExperimentPositionService.deleteSgjsExperimentPosition(sgjsExperimentPositionParam));
    }

    @PreAuthorize(hasPermi = "sgjsExperimentPosition:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsExperimentPositionByPks(@PathVariable Long[] ids){
        List<Long> sgjsExperimentPositionPkList = Arrays.asList(ids);
        return toAjax(sgjsExperimentPositionService.deleteSgjsExperimentPositionByPks(sgjsExperimentPositionPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsExperimentPosition sgjsExperimentPositionParam) throws IOException {
        List<SgjsExperimentPosition> sgjsExperimentPositionList = sgjsExperimentPositionService.getSgjsExperimentPositionList(sgjsExperimentPositionParam);
        ExcelUtils<SgjsExperimentPosition> util = new ExcelUtils<>(SgjsExperimentPosition.class);
        util.exportExcel(response, sgjsExperimentPositionList, DateUtils.getDate());
    }

    /**
     * 同步前期策划
     * dataType 传2就是3.7.1  传1：3.6.1
     *
     * @param map
     */
    @PostMapping("/sync")
    public AjaxResult sync(@RequestBody Map<String,Object> map){
        AjaxResult result = sgjsExperimentPositionService.sync(map);
        return result;
    }
}
