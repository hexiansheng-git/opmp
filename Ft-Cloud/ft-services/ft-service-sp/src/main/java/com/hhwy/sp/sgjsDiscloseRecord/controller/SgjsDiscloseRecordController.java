package com.hhwy.sp.sgjsDiscloseRecord.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sp.common.FlowInfoSearchUtil;
import com.hhwy.sp.core.system.SystemApiService;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord4Update;
import com.hhwy.sp.sgjsDiscloseRecord.service.ISgjsDiscloseRecordService;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private SystemApiService systemApiService;


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


    /**
     * 查询接口
     * @param sgjsDiscloseRecordParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:getList")
    @GetMapping("/getList")
    public AjaxResult getList(@Validated(ValidationGroups.Select.class) SgjsDiscloseRecord sgjsDiscloseRecordParam) {
        startPage();
        List<SgjsDiscloseRecord> sgjsDiscloseRecordList = sgjsDiscloseRecordService.getSgjsDiscloseRecordList(sgjsDiscloseRecordParam);
        FlowInfoSearchUtil.getFlowInfo(sgjsDiscloseRecordList, FlowEnum.SGJS_DISCLOSE_RECORD);
        //流程状态
        List<SysDictData> list = systemApiService.selectDictDataByType("task_status");
        sgjsDiscloseRecordList.forEach(e-> {
            List<SysDictData> dataList = list.stream().filter(t -> t.getDictValue().equals(e.getTaskStatus())).collect(Collectors.toList());
            e.setTaskStatusStr(dataList.get(0).getDictLabel());
        });
        return getDataTableAjaxResult(sgjsDiscloseRecordList);
    }



    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:add")
    @PostMapping("/add")
    @CustomLogger(title = "施工技术-方案安全技术交底",name = "方案安全技术交底",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertSgjsDiscloseRecord(@Validated(ValidationGroups.Save.class) @RequestBody SgjsDiscloseRecord sgjsDiscloseRecordParam) {
        sgjsDiscloseRecordService.insertSgjsDiscloseRecord(sgjsDiscloseRecordParam);
        return AjaxResult.success(sgjsDiscloseRecordParam);
    }

    /**
     * 有id新增
     * 没有id修改
     *
     * @param sgjsDiscloseRecord
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:handleAdd")
    @PostMapping("/handleAdd")
    @CustomLogger(title = "施工技术-方案安全技术交底",name = "方案安全技术交底",businessType = CustomBusinessType.SAVE)
    public AjaxResult handleAdd(@Validated(ValidationGroups.Save.class) @RequestBody SgjsDiscloseRecord sgjsDiscloseRecord) {
        sgjsDiscloseRecordService.handleAdd(sgjsDiscloseRecord);
        return AjaxResult.success(sgjsDiscloseRecord);
    }




    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "施工技术-方案安全技术交底",name = "方案安全技术交底",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertSgjsDiscloseRecordList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsDiscloseRecord> sgjsDiscloseRecordListParam) {
        sgjsDiscloseRecordService.insertSgjsDiscloseRecordList(sgjsDiscloseRecordListParam);
        return AjaxResult.success(sgjsDiscloseRecordListParam);
    }

    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:update")
    @PostMapping("/update")
    @CustomLogger(title = "施工技术-方案安全技术交底",name = "方案安全技术交底",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateSgjsDiscloseRecord(@Validated(ValidationGroups.Update.class) @RequestBody SgjsDiscloseRecord sgjsDiscloseRecordParam) {
        return toAjax(sgjsDiscloseRecordService.updateSgjsDiscloseRecord(sgjsDiscloseRecordParam));
    }



    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:update")
    @GetMapping("/detail")
    @CustomLogger(title = "施工技术-方案安全技术交底",name = "方案安全技术交底",businessType = CustomBusinessType.UPDATE)
    public AjaxResult detail(SgjsDiscloseRecord sgjsDiscloseRecord) {
        return AjaxResult.success(sgjsDiscloseRecordService.detail(sgjsDiscloseRecord));
    }


    /**
     * 保存接口
     * @param
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsDiscloseRecord:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "施工技术-方案安全技术交底",name = "方案安全技术交底",businessType = CustomBusinessType.SAVE)
    public AjaxResult updateSgjsDiscloseRecordList(@Validated(ValidationGroups.Update.class) @RequestBody SgjsDiscloseRecord4Update sgjsDiscloseRecord4Update) {
        List<SgjsDiscloseRecord> sgjsDiscloseRecordListParam = sgjsDiscloseRecord4Update.getTreeList();
        List<Long> delIdList = sgjsDiscloseRecord4Update.getDelIdList();
        int i = 0;
        if(CollectionUtils.isNotEmpty(sgjsDiscloseRecordListParam)) {
            i += sgjsDiscloseRecordService.updateSgjsDiscloseRecordList(sgjsDiscloseRecordListParam);
        }
        if(CollectionUtils.isNotEmpty(delIdList)) {
            i += sgjsDiscloseRecordService.deleteSgjsDiscloseRecordByPks(delIdList);
        }
        return toAjax(i);
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
        List<SgjsDiscloseRecord> sgjsDiscloseRecordList = sgjsDiscloseRecordParam.getExportList();

        if(CollectionUtils.isEmpty(sgjsDiscloseRecordList)) {
            sgjsDiscloseRecordList = sgjsDiscloseRecordService.getSgjsDiscloseRecordList(sgjsDiscloseRecordParam);
        }

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
            //templateName = "newExportDiscloseRecord12.xlsx";
            templateName = "newExportDiscloseRecord12.xlsx";
        }
        // 三级交底模板
        if("three".equals(dataType)) {
            templateName = "exportDiscloseRecord3.xlsx";
        }
        util.exportWithTemplate4FileName(response, sgjsDiscloseRecordList, 2, templateName, "sheet1", "交底记录");

    }


    @GetMapping("/exportOneOrTwo")
    public void exportOneOrTwo(HttpServletResponse response, SgjsDiscloseRecord sgjsDiscloseRecord) throws IOException {
        List<SgjsDiscloseRecord> list = sgjsDiscloseRecordService.getSgjsDiscloseRecordList(sgjsDiscloseRecord);
        ExcelUtils<SgjsDiscloseRecord> util = new ExcelUtils<>(SgjsDiscloseRecord.class);
        FlowInfoSearchUtil.getFlowInfo(list, FlowEnum.SGJS_DISCLOSE_RECORD);
        util.exportExcel(response, list, DateUtils.getDate());
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
//            ExcelUtils<SgjsDiscloseRecord> excelUtils = new ExcelUtils<>(SgjsDiscloseRecord.class);
            FtExcelUtil<SgjsDiscloseRecord> excelUtil = new FtExcelUtil<>(SgjsDiscloseRecord.class);
            List<SgjsDiscloseRecord> sgjsDiscloseRecordList = excelUtil.importExcel("sheet1", file.getInputStream());
            sgjsDiscloseRecordService.importData(sgjsDiscloseRecordList, dataType);
            return AjaxResult.success(sgjsDiscloseRecordList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 一、二级方案安全交底预警
     * @auth lcf
     * @date 2024年08月28日
     *
     * @return
     */
    @GetMapping("/disCloseWarn")
    public AjaxResult disCloseWarn(){
        sgjsDiscloseRecordService.disCloseWarn();
        return AjaxResult.success();
    }


    /**
     * 一、二级方案安全交底流程监听
     * 发消息to项目总工（）
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/listener")
    public AjaxResult disCloseRecordListener(@RequestParam("id") Long id,@RequestParam("status") String status){
        return sgjsDiscloseRecordService.disCloseRecordListener(id,status);
    }
}
