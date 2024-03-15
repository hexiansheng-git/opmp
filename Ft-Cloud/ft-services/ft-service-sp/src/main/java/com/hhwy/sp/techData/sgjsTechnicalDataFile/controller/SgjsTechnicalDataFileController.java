package com.hhwy.sp.techData.sgjsTechnicalDataFile.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.sp.techData.sgjsTechnicalDataCatalog.domain.SgjsTechnicalDataCatalog;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.techData.sgjsTechnicalDataFile.service.ISgjsTechnicalDataFileService;
import com.hhwy.sp.techData.sgjsTechnicalDataFile.domain.SgjsTechnicalDataFile;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2024-02-27 15:10:21
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsTechnicalDataFile")
public class SgjsTechnicalDataFileController extends BaseController {

    @Autowired
    private ISgjsTechnicalDataFileService sgjsTechnicalDataFileService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping
    public AjaxResult getSgjsTechnicalDataFile(@Validated(ValidationGroups.Get.class) SgjsTechnicalDataFile sgjsTechnicalDataFileParam) {
        SgjsTechnicalDataFile sgjsTechnicalDataFile = sgjsTechnicalDataFileService.getSgjsTechnicalDataFile(sgjsTechnicalDataFileParam);
        return AjaxResult.success(sgjsTechnicalDataFile);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataFile:list")
    @GetMapping("/list")
    public AjaxResult getSgjsTechnicalDataFileList(@Validated(ValidationGroups.Select.class) SgjsTechnicalDataFile sgjsTechnicalDataFileParam) {
        startPage();
        List<SgjsTechnicalDataFile> sgjsTechnicalDataFileList = sgjsTechnicalDataFileService.getSgjsTechnicalDataFileList(sgjsTechnicalDataFileParam);
        return getDataTableAjaxResult(sgjsTechnicalDataFileList);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataFile:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechnicalDataFile(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechnicalDataFile sgjsTechnicalDataFileParam) {
        sgjsTechnicalDataFileService.insertSgjsTechnicalDataFile(sgjsTechnicalDataFileParam);
        return AjaxResult.success(sgjsTechnicalDataFileParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataFile:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechnicalDataFileList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsTechnicalDataFile> sgjsTechnicalDataFileListParam) {
        sgjsTechnicalDataFileService.insertSgjsTechnicalDataFileList(sgjsTechnicalDataFileListParam);
        return AjaxResult.success(sgjsTechnicalDataFileListParam);
    }

    @PostMapping("/update")
    public AjaxResult updateSgjsTechnicalDataFile(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalDataFile sgjsTechnicalDataFileParam) {
        int i = sgjsTechnicalDataFileService.updateSgjsTechnicalDataFile(sgjsTechnicalDataFileParam);
        doSendGm();
        return toAjax(i);
    }

    //数据推送总部版
    public void doSendGm(){
        SgjsTechnicalDataFile sgjsTechnicalDataFile = new SgjsTechnicalDataFile();
        List<SgjsTechnicalDataFile> sgjsTechnicalDataFileList = sgjsTechnicalDataFileService.getSgjsTechnicalDataFileList(sgjsTechnicalDataFile);
        rocketMQTemplate.convertAndSend("sgjs_technical_data_file:tenantSuccess", sgjsTechnicalDataFileList);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataFile:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechnicalDataFileList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsTechnicalDataFile> sgjsTechnicalDataFileListParam) {
        return toAjax(sgjsTechnicalDataFileService.updateSgjsTechnicalDataFileList(sgjsTechnicalDataFileListParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataFile:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechnicalDataFile(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsTechnicalDataFile sgjsTechnicalDataFileParam) {
        return toAjax(sgjsTechnicalDataFileService.deleteSgjsTechnicalDataFile(sgjsTechnicalDataFileParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalDataFile:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsTechnicalDataFileByPks(@PathVariable Long[] ids) {
        List<Long> sgjsTechnicalDataFilePkList = Arrays.asList(ids);
        return toAjax(sgjsTechnicalDataFileService.deleteSgjsTechnicalDataFileByPks(sgjsTechnicalDataFilePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsTechnicalDataFile sgjsTechnicalDataFileParam) throws IOException {
        List<SgjsTechnicalDataFile> sgjsTechnicalDataFileList = sgjsTechnicalDataFileService.getSgjsTechnicalDataFileList(sgjsTechnicalDataFileParam);
        ExcelUtils<SgjsTechnicalDataFile> util = new ExcelUtils<>(SgjsTechnicalDataFile.class);
        util.exportExcel(response, sgjsTechnicalDataFileList, DateUtils.getDate());
    }
}
