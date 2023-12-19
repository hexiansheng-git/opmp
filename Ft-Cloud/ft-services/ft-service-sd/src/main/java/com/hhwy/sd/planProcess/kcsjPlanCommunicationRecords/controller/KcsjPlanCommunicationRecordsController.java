package com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.domain.KcsjPlanCommunicationRecords;
import com.hhwy.sd.planProcess.kcsjPlanCommunicationRecords.service.IKcsjPlanCommunicationRecordsService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author wll
 * @date 2023-12-15 10:37:09
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjPlanCommunicationRecords")
public class KcsjPlanCommunicationRecordsController extends BaseController {

    @Autowired
    private IKcsjPlanCommunicationRecordsService kcsjPlanCommunicationRecordsService;


    @PreAuthorize(hasPermi = "kcsjPlanCommunicationRecords:list")
    @GetMapping
    public AjaxResult getKcsjPlanCommunicationRecords(@Validated(ValidationGroups.Get.class) KcsjPlanCommunicationRecords kcsjPlanCommunicationRecordsParam) {
        KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords = kcsjPlanCommunicationRecordsService.getKcsjPlanCommunicationRecords(kcsjPlanCommunicationRecordsParam);
        return AjaxResult.success(kcsjPlanCommunicationRecords);
    }


    @PreAuthorize(hasPermi = "kcsjPlanCommunicationRecords:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjPlanCommunicationRecords(@Validated(ValidationGroups.Save.class) @RequestBody KcsjPlanCommunicationRecords kcsjPlanCommunicationRecordsParam) {
        kcsjPlanCommunicationRecordsService.insertKcsjPlanCommunicationRecords(kcsjPlanCommunicationRecordsParam);
        return AjaxResult.success(kcsjPlanCommunicationRecordsParam);
    }



    /**
     * 分页查询&&条件查询
     *
     * @param kcsjPlanCommunicationRecordsParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjPlanCommunicationRecords:list")
    @GetMapping("/list")
    public AjaxResult getKcsjPlanCommunicationRecordsList(@Validated(ValidationGroups.Select.class) KcsjPlanCommunicationRecords kcsjPlanCommunicationRecordsParam) {
       startPage();
        List<KcsjPlanCommunicationRecords> kcsjPlanCommunicationRecordsList = kcsjPlanCommunicationRecordsService.getKcsjPlanCommunicationRecordsList(kcsjPlanCommunicationRecordsParam);
        return getDataTableAjaxResult(kcsjPlanCommunicationRecordsList);
    }



    /**
     * 批量保存数据
     *
     * @param kcsjPlanCommunicationRecordsListParam
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjPlanCommunicationRecords:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjPlanCommunicationRecordsList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjPlanCommunicationRecords> kcsjPlanCommunicationRecordsListParam) {
        kcsjPlanCommunicationRecordsService.insertKcsjPlanCommunicationRecordsList(kcsjPlanCommunicationRecordsListParam);
        return AjaxResult.success(kcsjPlanCommunicationRecordsListParam);
    }


    /**
     * 批量删除
     * @param kcsjPlanCommunicationRecords
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjPlanCommunicationRecords:remove")
    @PostMapping("/delByIds")
    public AjaxResult deleteKcsjPlanCommunicationRecordsByPks(@RequestBody KcsjPlanCommunicationRecords kcsjPlanCommunicationRecords) {
        List<Long> ids = kcsjPlanCommunicationRecords.getIds();
        if (ids.size()>0){
            return toAjax(kcsjPlanCommunicationRecordsService.deleteKcsjPlanCommunicationRecordsByPks(ids));
        }else {
            return AjaxResult.error("未选中数据");
        }


    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjPlanCommunicationRecords kcsjPlanCommunicationRecordsParam) throws IOException {
        List<KcsjPlanCommunicationRecords> kcsjPlanCommunicationRecordsList = kcsjPlanCommunicationRecordsService.getKcsjPlanCommunicationRecordsList(kcsjPlanCommunicationRecordsParam);
        ExcelUtils<KcsjPlanCommunicationRecords> util = new ExcelUtils<>(KcsjPlanCommunicationRecords.class);
        util.exportExcel(response, kcsjPlanCommunicationRecordsList, DateUtils.getDate());
    }

    @PreAuthorize(hasPermi = "kcsjPlanCommunicationRecords:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjPlanCommunicationRecords(@Validated(ValidationGroups.Update.class) @RequestBody KcsjPlanCommunicationRecords kcsjPlanCommunicationRecordsParam) {
        return toAjax(kcsjPlanCommunicationRecordsService.updateKcsjPlanCommunicationRecords(kcsjPlanCommunicationRecordsParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanCommunicationRecords:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjPlanCommunicationRecordsList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjPlanCommunicationRecords> kcsjPlanCommunicationRecordsListParam) {
        return toAjax(kcsjPlanCommunicationRecordsService.updateKcsjPlanCommunicationRecordsList(kcsjPlanCommunicationRecordsListParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanCommunicationRecords:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjPlanCommunicationRecords(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjPlanCommunicationRecords kcsjPlanCommunicationRecordsParam) {
        return toAjax(kcsjPlanCommunicationRecordsService.deleteKcsjPlanCommunicationRecords(kcsjPlanCommunicationRecordsParam));
    }



}
