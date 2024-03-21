package com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.controller;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.service.ISgjsBuildSchemeListService;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.EasyExcelListener;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: 施工方案管理 - 施工方案清单详细清单
 * 作者: fushudong
 * 时间: 2024-03-19
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildSchemeList")
public class SgjsBuildSchemeListController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeListService sgjsBuildSchemeListService;

    //选择原有方案
    @PreAuthorize(hasPermi = "sgjsBuildSchemeList:list")
    @GetMapping("/getLastValidScheme")
    public AjaxResult getLastValidScheme(@Validated(ValidationGroups.Get.class) SgjsBuildSchemeList sgjsBuildSchemeListParam) {
        List<SgjsBuildSchemeList> sgjsBuildSchemeListList = sgjsBuildSchemeListService.getLastValidScheme(sgjsBuildSchemeListParam);
        return AjaxResult.success(sgjsBuildSchemeListList);
    }

    //危大工程清单查询
    @PreAuthorize(hasPermi = "sgjsBuildSchemeList:list")
    @GetMapping("/getRiskList")
    public AjaxResult getRiskList(@Validated(ValidationGroups.Get.class) SgjsBuildSchemeList sgjsBuildSchemeListParam) {
        List<SgjsBuildSchemeList> sgjsBuildSchemeListList = sgjsBuildSchemeListService.getRiskList(sgjsBuildSchemeListParam);
        return AjaxResult.success(sgjsBuildSchemeListList);
    }

    //台账查询
    @PreAuthorize(hasPermi = "sgjsBuildSchemeList:list")
    @GetMapping("/list")
    public AjaxResult getSgjsBuildSchemeListList(@Validated(ValidationGroups.Select.class) SgjsBuildSchemeList sgjsBuildSchemeListParam) {
        List<SgjsBuildSchemeList> sgjsBuildSchemeListList = sgjsBuildSchemeListService.getSgjsBuildSchemeListList(sgjsBuildSchemeListParam);
        return AjaxResult.success(sgjsBuildSchemeListList);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeList:list")
    @GetMapping
    public AjaxResult getSgjsBuildSchemeList(@Validated(ValidationGroups.Get.class) SgjsBuildSchemeList sgjsBuildSchemeListParam) {
        SgjsBuildSchemeList sgjsBuildSchemeList = sgjsBuildSchemeListService.getSgjsBuildSchemeList(sgjsBuildSchemeListParam);
        return AjaxResult.success(sgjsBuildSchemeList);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeList:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsBuildSchemeList(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildSchemeList sgjsBuildSchemeListParam) {
        sgjsBuildSchemeListService.insertSgjsBuildSchemeList(sgjsBuildSchemeListParam);
        return AjaxResult.success(sgjsBuildSchemeListParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeList:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsBuildSchemeListList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsBuildSchemeList> sgjsBuildSchemeListListParam) {
        sgjsBuildSchemeListService.insertSgjsBuildSchemeListList(sgjsBuildSchemeListListParam);
        return AjaxResult.success(sgjsBuildSchemeListListParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeList:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsBuildSchemeList(@Validated(ValidationGroups.Update.class) @RequestBody SgjsBuildSchemeList sgjsBuildSchemeListParam) {
        return toAjax(sgjsBuildSchemeListService.updateSgjsBuildSchemeList(sgjsBuildSchemeListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeList:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsBuildSchemeListList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsBuildSchemeList> sgjsBuildSchemeListListParam) {
        return toAjax(sgjsBuildSchemeListService.updateSgjsBuildSchemeListList(sgjsBuildSchemeListListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeList:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsBuildSchemeList(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsBuildSchemeList sgjsBuildSchemeListParam) {
        return toAjax(sgjsBuildSchemeListService.deleteSgjsBuildSchemeList(sgjsBuildSchemeListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildSchemeList:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsBuildSchemeListByPks(@PathVariable Long[] ids) {
        List<Long> sgjsBuildSchemeListPkList = Arrays.asList(ids);
        return toAjax(sgjsBuildSchemeListService.deleteSgjsBuildSchemeListByPks(sgjsBuildSchemeListPkList));
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsBuildSchemeList sgjsBuildSchemeListParam) throws IOException {
        List<SgjsBuildSchemeList> sgjsBuildSchemeListList = sgjsBuildSchemeListService.getSgjsBuildSchemeListList(sgjsBuildSchemeListParam);
        ExcelUtils<SgjsBuildSchemeList> util = new ExcelUtils<>(SgjsBuildSchemeList.class);
        util.exportExcel(response, sgjsBuildSchemeListList, DateUtils.getDate());
    }

    //导入
    @GetMapping("/import")
    public AjaxResult importExcel(MultipartFile file) throws IOException {
        EasyExcelListener listener = new EasyExcelListener();
        EasyExcel.read(file.getInputStream(), listener).sheet(0).doRead();
        List<Map<Integer, String>> headList = listener.getHeadList();
        if (CollUtil.isEmpty(headList)) return AjaxResult.error("表头为空");
        List<Map<Integer, String>> dataList = listener.getDataList();
        if (CollUtil.isEmpty(dataList)) return AjaxResult.error("数据为空");
        return sgjsBuildSchemeListService.importData(headList, dataList);
    }


}
