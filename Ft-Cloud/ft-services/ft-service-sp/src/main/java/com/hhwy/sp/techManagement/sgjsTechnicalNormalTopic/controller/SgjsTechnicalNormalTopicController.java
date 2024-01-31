package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.controller;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.EasyExcelListener;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopic;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service.ISgjsTechnicalNormalTopicService;
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

/***
 * 功能描述: 科技管理 - 一般课题研发管理
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Validated
@RestController
@RequestMapping("/sgjsTechnicalNormalTopic")
public class SgjsTechnicalNormalTopicController extends BaseController {

    @Autowired
    private ISgjsTechnicalNormalTopicService sgjsTechnicalNormalTopicService;


    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopic:list")
    @GetMapping
    public AjaxResult getSgjsTechnicalNormalTopic(@Validated(ValidationGroups.Get.class) SgjsTechnicalNormalTopic sgjsTechnicalNormalTopicParam) {
        SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic = sgjsTechnicalNormalTopicService.getSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopicParam);
        return AjaxResult.success(sgjsTechnicalNormalTopic);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopic:list")
    @GetMapping("/list")
    public AjaxResult getSgjsTechnicalNormalTopicList(@Validated(ValidationGroups.Select.class) SgjsTechnicalNormalTopic sgjsTechnicalNormalTopicParam) {
        List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList = sgjsTechnicalNormalTopicService.getSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicParam);
        return AjaxResult.success(sgjsTechnicalNormalTopicList);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopic:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechnicalNormalTopic(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechnicalNormalTopic sgjsTechnicalNormalTopicParam) {
        sgjsTechnicalNormalTopicService.insertSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopicParam);
        return AjaxResult.success(sgjsTechnicalNormalTopicParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopic:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechnicalNormalTopicList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicListParam) {
        sgjsTechnicalNormalTopicService.insertSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicListParam);
        return AjaxResult.success(sgjsTechnicalNormalTopicListParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopic:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsTechnicalNormalTopic(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalNormalTopic sgjsTechnicalNormalTopicParam) {
        return toAjax(sgjsTechnicalNormalTopicService.updateSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopicParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopic:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechnicalNormalTopicList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicListParam) {
        return toAjax(sgjsTechnicalNormalTopicService.updateSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicListParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopic:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechnicalNormalTopic(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsTechnicalNormalTopic sgjsTechnicalNormalTopicParam) {
        return toAjax(sgjsTechnicalNormalTopicService.deleteSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopicParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalNormalTopic:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsTechnicalNormalTopicByPks(@PathVariable Long[] ids) {
        List<Long> sgjsTechnicalNormalTopicPkList = Arrays.asList(ids);
        return toAjax(sgjsTechnicalNormalTopicService.deleteSgjsTechnicalNormalTopicByPks(sgjsTechnicalNormalTopicPkList));
    }

    @GetMapping("/export1")
    public void export1(HttpServletResponse response, SgjsTechnicalNormalTopic sgjsTechnicalNormalTopicParam) throws IOException {
        List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList = sgjsTechnicalNormalTopicService.getSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicParam);
        ExcelUtils<SgjsTechnicalNormalTopic> util = new ExcelUtils<>(SgjsTechnicalNormalTopic.class);
        util.exportExcel(response, sgjsTechnicalNormalTopicList, DateUtils.getDate());
    }

    //导出
    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsTechnicalNormalTopic sgjsTechnicalNormalTopicParam) throws Exception {
        sgjsTechnicalNormalTopicService.export(response, sgjsTechnicalNormalTopicParam);
    }

    //导入
    @PostMapping("/import")
    public AjaxResult importExcel(MultipartFile file) throws IOException {
        EasyExcelListener listener = new EasyExcelListener();
        EasyExcel.read(file.getInputStream(), listener).sheet(0).doRead();
        List<Map<Integer, String>> headList = listener.getHeadList();
        if (CollUtil.isEmpty(headList)) return AjaxResult.error("表头为空");
        List<Map<Integer, String>> dataList = listener.getDataList();
        if (CollUtil.isEmpty(dataList)) return AjaxResult.error("数据为空");
        return sgjsTechnicalNormalTopicService.importData(headList, dataList);
    }
}
