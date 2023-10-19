package com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.service.IJdglDayScheduleService;
import com.hhwy.pm.jdgl.day.schedule.jdglDaySchedule.domain.JdglDaySchedule;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2023-08-24 14:05:37
 * @remark
 */
@Validated
@RestController
@RequestMapping("/jdglDaySchedule")
public class JdglDayScheduleController extends BaseController {

    @Autowired
    private IJdglDayScheduleService jdglDayScheduleService;


//    @PreAuthorize(hasPermi = "jdglDaySchedule:list")
    @GetMapping
    public AjaxResult getJdglDaySchedule(@Validated(ValidationGroups.Get.class) JdglDaySchedule jdglDayScheduleParam) {
        JdglDaySchedule jdglDaySchedule = jdglDayScheduleService.getJdglDaySchedule(jdglDayScheduleParam);
        return AjaxResult.success(jdglDaySchedule);
    }

//    @PreAuthorize(hasPermi = "jdglDaySchedule:list")
    @GetMapping("jdglDayScheduleByPerson")
    public AjaxResult getJdglDayScheduleByPerson(@Validated(ValidationGroups.Get.class) JdglDaySchedule jdglDayScheduleParam) {
        JdglDaySchedule jdglDaySchedule = jdglDayScheduleService.getJdglDayScheduleByPerson(jdglDayScheduleParam);
        return AjaxResult.success(jdglDaySchedule);
    }

//    @PreAuthorize(hasPermi = "jdglDaySchedule:list")
    @GetMapping("/getInit")
    public AjaxResult getInit(@Validated(ValidationGroups.Get.class) JdglDaySchedule jdglDayScheduleParam) {
        JdglDaySchedule jdglDaySchedule = jdglDayScheduleService.getInit(jdglDayScheduleParam);
        return AjaxResult.success(jdglDaySchedule);
    }

//    @PreAuthorize(hasPermi = "jdglDaySchedule:list")
    @GetMapping("/list")
    public AjaxResult getJdglDayScheduleList(@Validated(ValidationGroups.Select.class) JdglDaySchedule jdglDayScheduleParam) {
        startPage();
        List<JdglDaySchedule> jdglDayScheduleList = jdglDayScheduleService.getJdglDayScheduleList(jdglDayScheduleParam);
        return getDataTableAjaxResult(jdglDayScheduleList);
    }

    /**
     * 总部版进度填报调用接口
     * @param startDate
     * @param endDate
     * @return
     */
//    @PreAuthorize(hasPermi = "jdglDaySchedule:list")
    @PostMapping("/getListByDateRange")
    public AjaxResult getListByDateRange(@JsonFormat(pattern = "yyyy-MM-dd") Date startDate,@JsonFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<JdglDaySchedule> listByDateRange = jdglDayScheduleService.getListByDateRange(startDate, endDate);
        return AjaxResult.success(listByDateRange);
    }

    @PreAuthorize(hasPermi = "jdglDaySchedule:add")
    @PostMapping("/add")
    public AjaxResult insertJdglDaySchedule(@Validated(ValidationGroups.Save.class) @RequestBody JdglDaySchedule jdglDayScheduleParam) {
        jdglDayScheduleService.insertJdglDaySchedule(jdglDayScheduleParam);
        Long id = jdglDayScheduleParam.getId();
        JdglDaySchedule jdglDaySchedule = new JdglDaySchedule();
        jdglDaySchedule.setId(id);
        return AjaxResult.success(jdglDayScheduleService.getJdglDaySchedule(jdglDaySchedule));
    }

    @PreAuthorize(hasPermi = "jdglDaySchedule:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertJdglDayScheduleList(@Validated(ValidationGroups.Save.class) @RequestBody List<JdglDaySchedule> jdglDayScheduleListParam) {
        jdglDayScheduleService.insertJdglDayScheduleList(jdglDayScheduleListParam);
        return AjaxResult.success(jdglDayScheduleListParam);
    }

    @PreAuthorize(hasPermi = "jdglDaySchedule:update")
    @PostMapping("/update")
    public AjaxResult updateJdglDaySchedule(@Validated(ValidationGroups.Update.class) @RequestBody JdglDaySchedule jdglDayScheduleParam) {
        jdglDayScheduleService.updateJdglDaySchedule(jdglDayScheduleParam);
        Long id = jdglDayScheduleParam.getId();
        JdglDaySchedule jdglDaySchedule = new JdglDaySchedule();
        jdglDaySchedule.setId(id);
        return AjaxResult.success(jdglDayScheduleService.getJdglDaySchedule(jdglDaySchedule));
    }

    @PreAuthorize(hasPermi = "jdglDaySchedule:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateJdglDayScheduleList(@Validated(ValidationGroups.Update.class) @RequestBody List<JdglDaySchedule> jdglDayScheduleListParam) {
        return toAjax(jdglDayScheduleService.updateJdglDayScheduleList(jdglDayScheduleListParam));
    }

    @PreAuthorize(hasPermi = "jdglDaySchedule:remove")
    @PostMapping("/delete")
    public AjaxResult deleteJdglDaySchedule(@Validated(ValidationGroups.Delete.class) @RequestBody JdglDaySchedule jdglDayScheduleParam) {
        return toAjax(jdglDayScheduleService.deleteJdglDaySchedule(jdglDayScheduleParam));
    }

    @PreAuthorize(hasPermi = "jdglDaySchedule:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteJdglDayScheduleByPks(@PathVariable Long[] ids) {
        List<Long> jdglDaySchedulePkList = Arrays.asList(ids);
        return toAjax(jdglDayScheduleService.deleteJdglDayScheduleByPks(jdglDaySchedulePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, JdglDaySchedule jdglDayScheduleParam) throws IOException {
        List<JdglDaySchedule> jdglDayScheduleList = jdglDayScheduleService.getJdglDayScheduleList(jdglDayScheduleParam);
        ExcelUtils<JdglDaySchedule> util = new ExcelUtils<>(JdglDaySchedule.class);
        util.exportExcel(response, jdglDayScheduleList, DateUtils.getDate());
    }

    /**
     *
     * 更新流程数据
     * @param id 主键
     * @return  监听器
     */
    @RequestMapping(value ="/listener",method = RequestMethod.POST)
    @Transactional
    public AjaxResult updateTaskStatus(@RequestParam ("id") Long id) {
        jdglDayScheduleService.updateTaskStatus(id);
        return AjaxResult.success();
    }
}
