package com.hhwy.sp.techData.sgjsTechnicalData.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sp.techData.sgjsTechnicalData.domain.SgjsTechnicalData4Update;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprint;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.techData.sgjsTechnicalData.service.ISgjsTechnicalDataService;
import com.hhwy.sp.techData.sgjsTechnicalData.domain.SgjsTechnicalData;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2024-01-22 09:26:47
 * @remark 技术文件管理-技术资料管理
 */
@Validated
@RestController
@RequestMapping("/sgjsTechnicalData")
public class SgjsTechnicalDataController extends BaseController {

    @Autowired
    private ISgjsTechnicalDataService sgjsTechnicalDataService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private PmServiceApi pmServiceApi;
    private static ProjectDto projectInfo;

    private ProjectDto getProjectDto(){
        if (projectInfo != null) return projectInfo;
        return pmServiceApi.getProjectDto();
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalData:list")
    @GetMapping
    public AjaxResult getSgjsTechnicalData(@Validated(ValidationGroups.Get.class) SgjsTechnicalData sgjsTechnicalDataParam) {
        SgjsTechnicalData sgjsTechnicalData = sgjsTechnicalDataService.getSgjsTechnicalData(sgjsTechnicalDataParam);
        return AjaxResult.success(sgjsTechnicalData);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalData:list")
    @GetMapping("/list")
    public AjaxResult getSgjsTechnicalDataList(@Validated(ValidationGroups.Select.class) SgjsTechnicalData sgjsTechnicalDataParam) {
        startPage();
        List<SgjsTechnicalData> sgjsTechnicalDataList = sgjsTechnicalDataService.getSgjsTechnicalDataList(sgjsTechnicalDataParam);
        return getDataTableAjaxResult(sgjsTechnicalDataList);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalData:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechnicalData(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechnicalData sgjsTechnicalDataParam) {
        sgjsTechnicalDataService.insertSgjsTechnicalData(sgjsTechnicalDataParam);
        return AjaxResult.success(sgjsTechnicalDataParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalData:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechnicalDataList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsTechnicalData> sgjsTechnicalDataListParam) {
        sgjsTechnicalDataService.insertSgjsTechnicalDataList(sgjsTechnicalDataListParam);
        return AjaxResult.success(sgjsTechnicalDataListParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalData:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsTechnicalData(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalData sgjsTechnicalDataParam) {
        return toAjax(sgjsTechnicalDataService.updateSgjsTechnicalData(sgjsTechnicalDataParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalData:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechnicalDataList(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalData4Update sgjsTechnicalData4Update) {
        Long dataCatalogId = sgjsTechnicalData4Update.getDataCatalogId();

        List<SgjsTechnicalData> treeList = sgjsTechnicalData4Update.getTreeList();
        int i = 0;
        if(CollectionUtils.isNotEmpty(treeList)) {
            i += sgjsTechnicalDataService.updateSgjsTechnicalDataList(dataCatalogId, treeList);
        }

        List<Long> delIdList = sgjsTechnicalData4Update.getDelIdList();
        if(CollectionUtils.isNotEmpty(delIdList)) {
            sgjsTechnicalDataService.deleteSgjsTechnicalDataByPks(delIdList);
        }
        doSendGm();
        return AjaxResult.success(i);
    }

    //数据推送总部版
    public void doSendGm(){
        SgjsTechnicalData sgjsTechnicalData = new SgjsTechnicalData();
        List<SgjsTechnicalData> sgjsTechnicalDataList = sgjsTechnicalDataService.getList(sgjsTechnicalData);
        if (CollUtil.isEmpty(sgjsTechnicalDataList)) {
            //集合为空，推送一个项目编号
            Long projectId = getProjectDto().getProjectId();
            sgjsTechnicalData.setProjectId(projectId);
            sgjsTechnicalDataList.add(sgjsTechnicalData);
        }
        rocketMQTemplate.convertAndSend("sgjs_technical_data:tenantSuccess", sgjsTechnicalDataList);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalData:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechnicalData(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsTechnicalData sgjsTechnicalDataParam) {
        return toAjax(sgjsTechnicalDataService.deleteSgjsTechnicalData(sgjsTechnicalDataParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalData:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsTechnicalDataByPks(@PathVariable Long[] ids) {
        List<Long> sgjsTechnicalDataPkList = Arrays.asList(ids);
        return toAjax(sgjsTechnicalDataService.deleteSgjsTechnicalDataByPks(sgjsTechnicalDataPkList));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalData:export")
    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsTechnicalData sgjsTechnicalDataParam) throws IOException {
        List<SgjsTechnicalData> sgjsTechnicalDataList = sgjsTechnicalDataService.getSgjsTechnicalDataList(sgjsTechnicalDataParam);
//        ExcelUtils<SgjsTechnicalData> util = new ExcelUtils<>(SgjsTechnicalData.class);
        FtExcelUtil<SgjsTechnicalData> util = new FtExcelUtil<>(SgjsTechnicalData.class);
        util.exportExcel(response, TreeUtil.treeToList(sgjsTechnicalDataList), DateUtils.getDate());
    }
}
