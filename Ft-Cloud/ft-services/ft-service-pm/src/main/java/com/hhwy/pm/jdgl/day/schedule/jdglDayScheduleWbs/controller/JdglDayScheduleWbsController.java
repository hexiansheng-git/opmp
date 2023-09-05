package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.IJdglDayScheduleBillService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs4Add;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.service.IJdglDayScheduleWbsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2023-08-24 14:05:56
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglDayScheduleWbs")
public class JdglDayScheduleWbsController extends BaseController {

    @Autowired
    private IJdglDayScheduleWbsService jdglDayScheduleWbsService;

    @Autowired
    private IJdglDayScheduleBillService iJdglDayScheduleBillService;


    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:list")
    @GetMapping
    public AjaxResult getJdglDayScheduleWbs(@Validated(ValidationGroups.Get.class) JdglDayScheduleWbs jdglDayScheduleWbsParam) {
        JdglDayScheduleWbs jdglDayScheduleWbs = jdglDayScheduleWbsService.getJdglDayScheduleWbs(jdglDayScheduleWbsParam);
        return AjaxResult.success(jdglDayScheduleWbs);
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:list")
    @GetMapping("/list")
    public AjaxResult getJdglDayScheduleWbsList(@Validated(ValidationGroups.Select.class) JdglDayScheduleWbs jdglDayScheduleWbsParam) {
//        startPage();
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsService.getJdglDayScheduleWbsList(jdglDayScheduleWbsParam);
        return getDataTableAjaxResult(jdglDayScheduleWbsList);
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:list")
    @GetMapping("/list4Person")
    public AjaxResult getJdglDayScheduleWbsList4Person(@Validated(ValidationGroups.Select.class) JdglDayScheduleWbs jdglDayScheduleWbsParam) {
//        startPage();
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsService.getJdglDayScheduleWbsListByPerson(jdglDayScheduleWbsParam);
        return getDataTableAjaxResult(jdglDayScheduleWbsList);
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:list")
    @GetMapping("/lazyList")
    public AjaxResult getJdglDayScheduleWbsLazyList(@Validated(ValidationGroups.Select.class) JdglDayScheduleWbs jdglDayScheduleWbsParam) {
//        startPage();
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsService.getJdglDayScheduleWbsLazyList(jdglDayScheduleWbsParam);
        return getDataTableAjaxResult(jdglDayScheduleWbsList);
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:add")
    @PostMapping("/add")
    public AjaxResult insertJdglDayScheduleWbs(@Validated(ValidationGroups.Save.class) @RequestBody JdglDayScheduleWbs jdglDayScheduleWbsParam) {
        jdglDayScheduleWbsService.insertJdglDayScheduleWbs(jdglDayScheduleWbsParam);
        return AjaxResult.success(jdglDayScheduleWbsParam);
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglDayScheduleWbsList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglDayScheduleWbs> jdglDayScheduleWbsListParam) {
        jdglDayScheduleWbsService.insertJdglDayScheduleWbsList(jdglDayScheduleWbsListParam);
        return AjaxResult.success(jdglDayScheduleWbsListParam);
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:update")
    @PostMapping("/update")
    public AjaxResult updateJdglDayScheduleWbs(@Validated(ValidationGroups.Update.class) @RequestBody JdglDayScheduleWbs jdglDayScheduleWbsParam) {
        return toAjax(jdglDayScheduleWbsService.updateJdglDayScheduleWbs(jdglDayScheduleWbsParam));
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglDayScheduleWbsList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglDayScheduleWbs> jdglDayScheduleWbsListParam) {
        return toAjax(jdglDayScheduleWbsService.updateJdglDayScheduleWbsList(jdglDayScheduleWbsListParam, null));
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglDayScheduleWbs(@Validated(ValidationGroups.Delete.class) @RequestBody JdglDayScheduleWbs jdglDayScheduleWbsParam) {
        return toAjax(jdglDayScheduleWbsService.deleteJdglDayScheduleWbs(jdglDayScheduleWbsParam));
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglDayScheduleWbsByPks(@PathVariable Long[] ids) {
        List<Long> jdglDayScheduleWbsPkList = Arrays.asList(ids);
        return toAjax(jdglDayScheduleWbsService.deleteJdglDayScheduleWbsByPks(jdglDayScheduleWbsPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglDayScheduleWbs jdglDayScheduleWbsParam) throws IOException {
        List<JdglDayScheduleWbs> jdglDayScheduleWbsList = jdglDayScheduleWbsService.getJdglDayScheduleWbsList(jdglDayScheduleWbsParam);
        ExcelUtils<JdglDayScheduleWbs> util = new ExcelUtils<>(JdglDayScheduleWbs.class);
        util.exportExcel(response, jdglDayScheduleWbsList, DateUtils.getDate());
    }

    /**
     * 根据日期从总进度计划获取形象清单关联的wbs
     * @param date
     * @return
     */
    @GetMapping("/getInitWbs")
    public AjaxResult getInitWbs(Date date) {
        return AjaxResult.success(jdglDayScheduleWbsService.getInitWbs(date));
    }


    @PostMapping("/getInitWbsByList")
    public AjaxResult getInitWbsByList(@RequestBody List<JdglDayScheduleWbs> jdglDayScheduleWbsListParam, @RequestParam("date") Date date) {
        return AjaxResult.success(iJdglDayScheduleBillService.getInitBill(jdglDayScheduleWbsListParam, date));
    }

    /**
     * 获取wbs及图纸复核数据并过滤当前日报的wbs
     * @return
     */
    @PostMapping("/getLazyWbs4NoThis")
    public AjaxResult getAllWbs4NoThis(@RequestBody JdglDayScheduleWbs jdglDayScheduleWbsParam) {
        return AjaxResult.success(jdglDayScheduleWbsService.getLazyWbs4NoThis(jdglDayScheduleWbsParam));
    }

    /**
     * 获取末级节点wbs数据
     * @param jdglDayScheduleWbsParam
     * @return
     */
    @PostMapping("/getLeafWbsList")
    public AjaxResult getLeafWbsList(@RequestBody JdglDayScheduleWbs jdglDayScheduleWbsParam) {
        return AjaxResult.success(jdglDayScheduleWbsService.getLeafWbsList(jdglDayScheduleWbsParam));
    }


    @PreAuthorize(hasPermi = "jdglDayScheduleWbs:add")
    @PostMapping("/addWbsList")
    public AjaxResult addWbsList(@Validated(ValidationGroups.Save.class) @RequestBody JdglDayScheduleWbs4Add jdglDayScheduleWbsListParam) {
        return AjaxResult.success(jdglDayScheduleWbsService.addWbsList(jdglDayScheduleWbsListParam));
    }
}
