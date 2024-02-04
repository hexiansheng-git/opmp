package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.service.ISgsjTechnicalScienceTopicService;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/***
 * 功能描述: 科技管理 - 科研课题研发管理
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Validated
@RestController
@RequestMapping("/sgsjTechnicalScienceTopic")
public class SgsjTechnicalScienceTopicController extends BaseController {

    @Autowired
    private ISgsjTechnicalScienceTopicService sgsjTechnicalScienceTopicService;


    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
    @GetMapping
    public AjaxResult getSgsjTechnicalScienceTopic(@Validated(ValidationGroups.Get.class) SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = sgsjTechnicalScienceTopicService.getSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopic);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
    @GetMapping("/listPage")
    public AjaxResult listPage(@Validated(ValidationGroups.Select.class) SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        startPage();
        List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicService.getSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicParam);
        return getDataTableAjaxResult(sgsjTechnicalScienceTopicList);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
    @GetMapping("/list")
    public AjaxResult list(@Validated(ValidationGroups.Select.class) SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicService.getSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicList);
    }
    //课题申请明细
    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
    @GetMapping("/applyDetail")
    public AjaxResult applyDetail(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicService.applyDetail(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicList);
    }

    //立项明细
    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
    @GetMapping("/lxDetail")
    public AjaxResult lxDetail(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicService.getDetail(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicList);
    }

    //课题申请里的保存
    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:add")
    @PostMapping("/applyAdd")
    public AjaxResult applyAdd(@Validated(ValidationGroups.Save.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        sgsjTechnicalScienceTopicService.applyAdd(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicParam);
    }

    //课题立项里的保存
    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:add")
    @PostMapping("/lxAdd")
    public AjaxResult insertSgsjTechnicalScienceTopic(@Validated(ValidationGroups.Save.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        sgsjTechnicalScienceTopicService.insertSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicParam);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgsjTechnicalScienceTopicList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicListParam) {
        sgsjTechnicalScienceTopicService.insertSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicListParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicListParam);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:update")
    @PostMapping("/update")
    public AjaxResult updateSgsjTechnicalScienceTopic(@Validated(ValidationGroups.Update.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        return toAjax(sgsjTechnicalScienceTopicService.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopicParam));
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgsjTechnicalScienceTopicList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicListParam) {
        return toAjax(sgsjTechnicalScienceTopicService.updateSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicListParam));
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgsjTechnicalScienceTopic(@Validated(ValidationGroups.Delete.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        return toAjax(sgsjTechnicalScienceTopicService.deleteSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopicParam));
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:remove")
    @PostMapping("/deleteById")
    public AjaxResult deleteById(@Validated(ValidationGroups.Delete.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
        sgsjTechnicalScienceTopicService.deleteById(sgsjTechnicalScienceTopicParam);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgsjTechnicalScienceTopicByPks(@PathVariable Long[] ids) {
        List<Long> sgsjTechnicalScienceTopicPkList = Arrays.asList(ids);
        return toAjax(sgsjTechnicalScienceTopicService.deleteSgsjTechnicalScienceTopicByPks(sgsjTechnicalScienceTopicPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) throws IOException {
        List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicService.getSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicParam);
        ExcelUtils<SgsjTechnicalScienceTopic> util = new ExcelUtils<>(SgsjTechnicalScienceTopic.class);
        util.exportExcel(response, sgsjTechnicalScienceTopicList, DateUtils.getDate());
    }

    /***
     * 功能描述: 流程结束监听
     * @param id  业务id
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/listener")
    public void updateTaskStatus(@RequestParam("id") Long id){
        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = new SgsjTechnicalScienceTopic();
        sgsjTechnicalScienceTopic.setId(id);
        sgsjTechnicalScienceTopic.setTaskStatus("5");
        sgsjTechnicalScienceTopicService.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
    }

    /***
     * 功能描述: 消息发布
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/messagePublic")
    public void messagePublic(){
        sgsjTechnicalScienceTopicService.messagePublic();
    }
}
