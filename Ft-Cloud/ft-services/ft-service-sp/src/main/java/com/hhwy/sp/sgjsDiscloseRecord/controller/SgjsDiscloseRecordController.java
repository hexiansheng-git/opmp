package com.hhwy.sp.sgjsDiscloseRecord.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;
import com.hhwy.sp.sgjsDiscloseRecord.service.ISgjsDiscloseRecordService;
import com.hhwy.utils.excel.FtExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cjh
 * @date 2023-11-23 14:47:22
 * @remark 交底记录管理
 */
@Validated
@RestController
@RequestMapping("/sgjsDiscloseRecord")
public class SgjsDiscloseRecordController extends BaseController {

    @Autowired
    private ISgjsDiscloseRecordService sgjsDiscloseRecordService;


    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:list")
    @GetMapping
    public AjaxResult getSgjsDiscloseRecord(@Validated(ValidationGroups.Get.class) SgjsDiscloseRecord sgjsDiscloseRecordParam) {
        SgjsDiscloseRecord sgjsDiscloseRecord = sgjsDiscloseRecordService.getSgjsDiscloseRecord(sgjsDiscloseRecordParam);
        return AjaxResult.success(sgjsDiscloseRecord);
    }

    /**
     * 查询接口
     * @param sgjsDiscloseRecordParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:list")
    @GetMapping("/list")
    public AjaxResult getSgjsDiscloseRecordList(@Validated(ValidationGroups.Select.class) SgjsDiscloseRecord sgjsDiscloseRecordParam) {
        startPage();
        List<SgjsDiscloseRecord> sgjsDiscloseRecordList = sgjsDiscloseRecordService.getSgjsDiscloseRecordList(sgjsDiscloseRecordParam);
        return getDataTableAjaxResult(sgjsDiscloseRecordList);
    }

    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsDiscloseRecord(@Validated(ValidationGroups.Save.class) @RequestBody SgjsDiscloseRecord sgjsDiscloseRecordParam) {
        sgjsDiscloseRecordService.insertSgjsDiscloseRecord(sgjsDiscloseRecordParam);
        return AjaxResult.success(sgjsDiscloseRecordParam);
    }

    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsDiscloseRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsDiscloseRecord> sgjsDiscloseRecordListParam) {
        sgjsDiscloseRecordService.insertSgjsDiscloseRecordList(sgjsDiscloseRecordListParam);
        return AjaxResult.success(sgjsDiscloseRecordListParam);
    }

    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsDiscloseRecord(@Validated(ValidationGroups.Update.class) @RequestBody SgjsDiscloseRecord sgjsDiscloseRecordParam) {
        return toAjax(sgjsDiscloseRecordService.updateSgjsDiscloseRecord(sgjsDiscloseRecordParam));
    }

    /**
     * 保存接口
     * @param sgjsDiscloseRecordListParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsDiscloseRecordList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsDiscloseRecord> sgjsDiscloseRecordListParam) {
        return toAjax(sgjsDiscloseRecordService.updateSgjsDiscloseRecordList(sgjsDiscloseRecordListParam));
    }

    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsDiscloseRecord(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsDiscloseRecord sgjsDiscloseRecordParam) {
        return toAjax(sgjsDiscloseRecordService.deleteSgjsDiscloseRecord(sgjsDiscloseRecordParam));
    }

    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsDiscloseRecordByPks(@PathVariable Long[] ids) {
        List<Long> sgjsDiscloseRecordPkList = Arrays.asList(ids);
        return toAjax(sgjsDiscloseRecordService.deleteSgjsDiscloseRecordByPks(sgjsDiscloseRecordPkList));
    }

//    @GetMapping("/export")
//    public void export(HttpServletResponse response, SgjsDiscloseRecord sgjsDiscloseRecordParam) throws IOException {
//        List<SgjsDiscloseRecord> sgjsDiscloseRecordList = sgjsDiscloseRecordService.getSgjsDiscloseRecordList(sgjsDiscloseRecordParam);
//        ExcelUtils<SgjsDiscloseRecord> util = new ExcelUtils<>(SgjsDiscloseRecord.class);
//        util.exportExcel(response, sgjsDiscloseRecordList, DateUtils.getDate());
//    }

    /**
     * 导出接口
     * @param response
     * @param sgjsDiscloseRecordParam
     * @throws IOException
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response,@RequestBody SgjsDiscloseRecord sgjsDiscloseRecordParam) throws IOException {
        String dataType = sgjsDiscloseRecordParam.getDataType();
        if(StringUtils.isEmpty(dataType)) {
            throw new RuntimeException("参数异常！");
        }

        List<SgjsDiscloseRecord> sgjsDiscloseRecordList = sgjsDiscloseRecordService.getSgjsDiscloseRecordList(sgjsDiscloseRecordParam);

        if(CollectionUtils.isNotEmpty(sgjsDiscloseRecordList)) {
            sgjsDiscloseRecordList.stream().forEach(vo -> {
                switch (vo.getDiscloseLevel()) {
                    case "1":
                        vo.setDiscloseLevel("一级交底");
                        break;
                    case "2":
                        vo.setDiscloseLevel("二级交底");
                        break;
                    case "3":
                        vo.setDiscloseLevel("三级交底");
                        break;
                }
            });
        }

        FtExcelUtil<SgjsDiscloseRecord> util = new FtExcelUtil<>(SgjsDiscloseRecord.class);
        String templateName = "";
        // 一、二级交底模板
        if("oneOrTwo".equals(dataType)) {
            templateName = "exportDiscloseRecord12.xlsx";
        }
        // 三级交底模板
        if("three".equals(dataType)) {
            templateName = "exportDiscloseRecord3.xlsx";
        }

        util.exportWithTemplate4FileName(response, sgjsDiscloseRecordList, 2, templateName, "sheet1", "交底记录");

    }

    /**
     * 导入接口
     * @param file
     * @param dataType 页签:oneOrTwo（一、二级交底）、three（三级交底）
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:update")
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file,@RequestParam("dataType") String dataType) {
        try {
            ExcelUtils<SgjsDiscloseRecord> excelUtils = new ExcelUtils<>(SgjsDiscloseRecord.class);
            List<SgjsDiscloseRecord> sgjsDiscloseRecordList = excelUtils.importExcel("sheet1", file.getInputStream());
            sgjsDiscloseRecordService.importData(sgjsDiscloseRecordList, dataType);
            return AjaxResult.success(sgjsDiscloseRecordList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
