package com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.io.IOException;

import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleWbs.domain.JdglDayScheduleWbs;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.service.IJdglDayScheduleBillService;
import com.hhwy.pm.jdgl.day.schedule.jdglDayScheduleBill.domain.JdglDayScheduleBill;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2023-08-24 14:05:49
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglDayScheduleBill")
public class JdglDayScheduleBillController extends BaseController {

    @Autowired
    private IJdglDayScheduleBillService jdglDayScheduleBillService;


    // @PreAuthorize(hasPermi = "jdglDayScheduleBill:list")
    @GetMapping
    public AjaxResult getJdglDayScheduleBill(@Validated(ValidationGroups.Get.class) JdglDayScheduleBill jdglDayScheduleBillParam) {
        JdglDayScheduleBill jdglDayScheduleBill = jdglDayScheduleBillService.getJdglDayScheduleBill(jdglDayScheduleBillParam);
        return AjaxResult.success(jdglDayScheduleBill);
    }

    // @PreAuthorize(hasPermi = "jdglDayScheduleBill:list")
    @GetMapping("/list")
    public AjaxResult getJdglDayScheduleBillList(@Validated(ValidationGroups.Select.class) JdglDayScheduleBill jdglDayScheduleBillParam) {
        startPage();
        List<JdglDayScheduleBill> jdglDayScheduleBillList = jdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBillParam);
        return getDataTableAjaxResult(jdglDayScheduleBillList);
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleBill:add")
    @PostMapping("/add")
    public AjaxResult insertJdglDayScheduleBill(@Validated(ValidationGroups.Save.class) @RequestBody JdglDayScheduleBill jdglDayScheduleBillParam) {
        jdglDayScheduleBillService.insertJdglDayScheduleBill(jdglDayScheduleBillParam);
        return AjaxResult.success(jdglDayScheduleBillParam);
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleBill:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglDayScheduleBillList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglDayScheduleBill> jdglDayScheduleBillListParam) {
        jdglDayScheduleBillService.insertJdglDayScheduleBillList(jdglDayScheduleBillListParam);
        return AjaxResult.success(jdglDayScheduleBillListParam);
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleBill:update")
    @PostMapping("/update")
    public AjaxResult updateJdglDayScheduleBill(@Validated(ValidationGroups.Update.class) @RequestBody JdglDayScheduleBill jdglDayScheduleBillParam) {
        return toAjax(jdglDayScheduleBillService.updateJdglDayScheduleBill(jdglDayScheduleBillParam));
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleBill:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglDayScheduleBillList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglDayScheduleBill> jdglDayScheduleBillListParam) {
        return toAjax(jdglDayScheduleBillService.updateJdglDayScheduleBillList(jdglDayScheduleBillListParam, null, null));
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleBill:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglDayScheduleBill(@Validated(ValidationGroups.Delete.class) @RequestBody JdglDayScheduleBill jdglDayScheduleBillParam) {
        return toAjax(jdglDayScheduleBillService.deleteJdglDayScheduleBill(jdglDayScheduleBillParam));
    }

    @PreAuthorize(hasPermi = "jdglDayScheduleBill:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglDayScheduleBillByPks(@PathVariable Long[] ids) {
        List<Long> jdglDayScheduleBillPkList = Arrays.asList(ids);
        return toAjax(jdglDayScheduleBillService.deleteJdglDayScheduleBillByPks(jdglDayScheduleBillPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglDayScheduleBill jdglDayScheduleBillParam) throws IOException {
        List<JdglDayScheduleBill> jdglDayScheduleBillList = jdglDayScheduleBillService.getJdglDayScheduleBillList(jdglDayScheduleBillParam);
        ExcelUtils<JdglDayScheduleBill> util = new ExcelUtils<>(JdglDayScheduleBill.class);
        util.exportExcel(response, jdglDayScheduleBillList, DateUtils.getDate());
    }

}
