package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:27
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglMainPlanItem")
public class JdglMainPlanItemController extends BaseController {

    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;


    // // @PreAuthorize(hasPermi = "jdglMainPlanItem:list")
    @GetMapping
    public AjaxResult getJdglMainPlanItem(@Validated(ValidationGroups.Get.class) JdglMainPlanItem jdglMainPlanItemParam) {
        JdglMainPlanItem jdglMainPlanItem = jdglMainPlanItemService.getJdglMainPlanItem(jdglMainPlanItemParam);
        return AjaxResult.success(jdglMainPlanItem);
    }

    // // @PreAuthorize(hasPermi = "jdglMainPlanItem:list")
    @GetMapping("/getUsing4One")
    public AjaxResult getUsing4One(@Validated(ValidationGroups.Get.class) JdglMainPlanItem jdglMainPlanItemParam) {
        JdglMainPlanItem jdglMainPlanItem = jdglMainPlanItemService.getUsing4One(jdglMainPlanItemParam);
        return AjaxResult.success(jdglMainPlanItem);
    }

    // // @PreAuthorize(hasPermi = "jdglMainPlanItem:list")
    @GetMapping("/list")
    public AjaxResult getJdglMainPlanItemList(@Validated(ValidationGroups.Select.class) JdglMainPlanItem jdglMainPlanItemParam) {
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.getJdglMainPlanItemList(jdglMainPlanItemParam);
        return getDataTableAjaxResult(jdglMainPlanItemList);
    }

    // // @PreAuthorize(hasPermi = "jdglMainPlanItem:list")
    @GetMapping("/treelist")
    public AjaxResult treelist(@Validated(ValidationGroups.Select.class) JdglMainPlanItem jdglMainPlanItemParam) {
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.treelist(jdglMainPlanItemParam);
        return getDataTableAjaxResult(jdglMainPlanItemList);
    }

    // // @PreAuthorize(hasPermi = "jdglMainPlanItem:list")
    @GetMapping("/getUsinglist")
    public AjaxResult getUsingJdglMainPlanItemList(@Validated(ValidationGroups.Select.class) JdglMainPlanItem jdglMainPlanItemParam) {
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.getUsingJdglMainPlanItemList(jdglMainPlanItemParam);
        return getDataTableAjaxResult(jdglMainPlanItemList);
    }



    // // @PreAuthorize(hasPermi = "jdglMainPlanItem:list")
    @GetMapping("/getKeyRoad")
    public AjaxResult getKeyRoad(@Validated(ValidationGroups.Select.class) JdglMainPlanItem jdglMainPlanItemParam) {
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.getKeyRoad(jdglMainPlanItemParam);
        return getDataTableAjaxResult(jdglMainPlanItemList);
    }

    // @PreAuthorize(hasPermi = "jdglMainPlanItem:add")
    @PostMapping("/add")
    public AjaxResult insertJdglMainPlanItem(@Validated(ValidationGroups.Save.class) @RequestBody JdglMainPlanItem jdglMainPlanItemParam) {
        jdglMainPlanItemService.insertJdglMainPlanItem(jdglMainPlanItemParam);
        return AjaxResult.success(jdglMainPlanItemParam);
    }

    // @PreAuthorize(hasPermi = "jdglMainPlanItem:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglMainPlanItemList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglMainPlanItem> jdglMainPlanItemListParam) {
        jdglMainPlanItemService.insertJdglMainPlanItemList(jdglMainPlanItemListParam);
        return AjaxResult.success(jdglMainPlanItemListParam);
    }

    // @PreAuthorize(hasPermi = "jdglMainPlanItem:update")
    @PostMapping("/update")
    public AjaxResult updateJdglMainPlanItem(@Validated(ValidationGroups.Update.class) @RequestBody JdglMainPlanItem jdglMainPlanItemParam) {
        return toAjax(jdglMainPlanItemService.updateJdglMainPlanItem(jdglMainPlanItemParam));
    }

    // @PreAuthorize(hasPermi = "jdglMainPlanItem:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglMainPlanItemList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglMainPlanItem> jdglMainPlanItemListParam) {
        return toAjax(jdglMainPlanItemService.updateJdglMainPlanItemList(jdglMainPlanItemListParam));
    }

    // @PreAuthorize(hasPermi = "jdglMainPlanItem:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglMainPlanItem(@Validated(ValidationGroups.Delete.class) @RequestBody JdglMainPlanItem jdglMainPlanItemParam) {
        return toAjax(jdglMainPlanItemService.deleteJdglMainPlanItem(jdglMainPlanItemParam));
    }

    // @PreAuthorize(hasPermi = "jdglMainPlanItem:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglMainPlanItemByPks(@PathVariable Long[] ids) {
        List<Long> jdglMainPlanItemPkList = Arrays.asList(ids);
        return toAjax(jdglMainPlanItemService.deleteJdglMainPlanItemByPks(jdglMainPlanItemPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglMainPlanItem jdglMainPlanItemParam) throws IOException {
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.getJdglMainPlanItemList(jdglMainPlanItemParam);
        ExcelUtils<JdglMainPlanItem> util = new ExcelUtils<>(JdglMainPlanItem.class);
        util.exportExcel(response, jdglMainPlanItemList, DateUtils.getDate());
    }

    // // @PreAuthorize(hasPermi = "jdglMainPlanItem:list")
    @PostMapping("/getUsingListByDate")
    public AjaxResult getUsingJdglMainPlanItemListByDate(@JsonFormat(pattern = "yyyy-MM-dd") Date date) {
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDate(date);
        return getDataTableAjaxResult(jdglMainPlanItemList);
    }

    // // @PreAuthorize(hasPermi = "jdglMainPlanItem:list")
    @PostMapping("/getUsingListByDateRange")
    public AjaxResult getUsingJdglMainPlanItemListByDate(@JsonFormat(pattern = "yyyy-MM-dd") Date startDate,@JsonFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(startDate, endDate);
        return getDataTableAjaxResult(jdglMainPlanItemList);
    }

    /**
     * 获取项目开始与结束
     * @return
     */
    @GetMapping("/getProjStartAndFinish")
    public AjaxResult getProjStartAndFinish() {
        return AjaxResult.success(jdglMainPlanItemService.getProjStartAndFinish());
    }

}
