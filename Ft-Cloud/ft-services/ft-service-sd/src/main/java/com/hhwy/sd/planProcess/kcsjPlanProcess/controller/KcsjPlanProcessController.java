package com.hhwy.sd.planProcess.kcsjPlanProcess.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjPlanProcess4Update;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sd.planProcess.kcsjPlanProcess.service.IKcsjPlanProcessService;
import com.hhwy.sd.planProcess.kcsjPlanProcess.domain.KcsjPlanProcess;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2023-12-18 11:13:27
 * @remark 勘察设计管理-计划进度管理-计划进度
 */
@Validated
@RestController
@RequestMapping("/kcsjPlanProcess")
public class KcsjPlanProcessController extends BaseController {

    @Autowired
    private IKcsjPlanProcessService kcsjPlanProcessService;


    @PreAuthorize(hasPermi = "kcsjPlanProcess:list")
    @GetMapping
    public AjaxResult getKcsjPlanProcess(@Validated(ValidationGroups.Get.class) KcsjPlanProcess kcsjPlanProcessParam) {
        KcsjPlanProcess kcsjPlanProcess = kcsjPlanProcessService.getKcsjPlanProcess(kcsjPlanProcessParam);
        return AjaxResult.success(kcsjPlanProcess);
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:list")
    @GetMapping("/list")
    public AjaxResult getKcsjPlanProcessList(@Validated(ValidationGroups.Select.class) KcsjPlanProcess kcsjPlanProcessParam) {
        startPage();
        List<KcsjPlanProcess> kcsjPlanProcessList = kcsjPlanProcessService.getKcsjPlanProcessList(kcsjPlanProcessParam);
        return getDataTableAjaxResult(kcsjPlanProcessList);
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:add")
    @PostMapping("/add")
    @CustomLogger(title = "勘察设计-勘察设计进度管理-计划进度",name = "计划进度",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjPlanProcess(@Validated(ValidationGroups.Save.class) @RequestBody KcsjPlanProcess kcsjPlanProcessParam) {
        kcsjPlanProcessService.insertKcsjPlanProcess(kcsjPlanProcessParam);
        return AjaxResult.success(kcsjPlanProcessParam);
    }

    /**
     * 同步前期策划工作计划
     * @return
     */
    @PreAuthorize(hasPermi = "kcsjPlanProcess:update")
    @GetMapping("/sync")
    public AjaxResult sync() {
        kcsjPlanProcessService.sync();
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:add")
    @PostMapping("/batchAdd")
    @CustomLogger(title = "勘察设计-勘察设计进度管理-计划进度",name = "计划进度",businessType = CustomBusinessType.SAVE)
    public AjaxResult insertKcsjPlanProcessList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjPlanProcess> kcsjPlanProcessListParam) {
        kcsjPlanProcessService.insertKcsjPlanProcessList(kcsjPlanProcessListParam);
        return AjaxResult.success(kcsjPlanProcessListParam);
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:update")
    @PostMapping("/update")
    @CustomLogger(title = "勘察设计-勘察设计进度管理-计划进度",name = "计划进度",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjPlanProcess(@Validated(ValidationGroups.Update.class) @RequestBody KcsjPlanProcess kcsjPlanProcessParam) {
        return toAjax(kcsjPlanProcessService.updateKcsjPlanProcess(kcsjPlanProcessParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:update")
    @PostMapping("/batchUpdate")
    @CustomLogger(title = "勘察设计-勘察设计进度管理-计划进度",name = "计划进度",businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateKcsjPlanProcessList(@Validated(ValidationGroups.Update.class) @RequestBody KcsjPlanProcess4Update kcsjPlanProcess4Update) {
        List<KcsjPlanProcess> treeList = kcsjPlanProcess4Update.getTreeList();
        int i = 0;
        if(CollectionUtils.isNotEmpty(treeList)) {
            i += kcsjPlanProcessService.updateKcsjPlanProcessList(treeList);
        }
        List<Long> delIdList = kcsjPlanProcess4Update.getDelIdList();
//        if(CollectionUtils.isNotEmpty(delIdList)) {
        try{
            i += kcsjPlanProcessService.deleteKcsjPlanProcessByPks(delIdList);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
//        }
        return AjaxResult.success("");
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjPlanProcess(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjPlanProcess kcsjPlanProcessParam) {
        return toAjax(kcsjPlanProcessService.deleteKcsjPlanProcess(kcsjPlanProcessParam));
    }

    @PreAuthorize(hasPermi = "kcsjPlanProcess:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjPlanProcessByPks(@PathVariable Long[] ids) {
        List<Long> kcsjPlanProcessPkList = Arrays.asList(ids);
        return toAjax(kcsjPlanProcessService.deleteKcsjPlanProcessByPks(kcsjPlanProcessPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjPlanProcess kcsjPlanProcessParam) throws IOException {
        List<KcsjPlanProcess> kcsjPlanProcessList = kcsjPlanProcessService.getKcsjPlanProcessList(kcsjPlanProcessParam);
        ExcelUtils<KcsjPlanProcess> util = new ExcelUtils<>(KcsjPlanProcess.class);
        util.exportExcel(response, kcsjPlanProcessList, DateUtils.getDate());
    }

    /**
     * 勘察设计--计划进度 预警消息发送
     *
     * @author lcf
     * @date 2024-04-01
     * @return
     */
    @GetMapping("/jobPlanProcess")
    public AjaxResult jobPlanProcess(){
        AjaxResult result=kcsjPlanProcessService.jobPlanProcess();
        return result;
    }
}
