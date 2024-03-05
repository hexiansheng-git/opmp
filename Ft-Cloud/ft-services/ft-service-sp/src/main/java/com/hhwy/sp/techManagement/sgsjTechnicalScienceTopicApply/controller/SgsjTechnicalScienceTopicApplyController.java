//package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopicApply.controller;
//
//import java.util.Arrays;
//import java.util.List;
//import java.io.IOException;
//
//import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;
//import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopicApply.domain.SgsjTechnicalScienceTopicApply;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//
//import com.hhwy.common.core.utils.DateUtils;
//import com.hhwy.common.core.utils.poi.ExcelUtils;
//import com.hhwy.common.core.web.domain.AjaxResult;
//import com.hhwy.common.core.web.controller.BaseController;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopicApply.service.ISgsjTechnicalScienceTopicApplyService;
//
//import org.springframework.validation.annotation.Validated;
//import com.hhwy.utils.validation.ValidationGroups;
//import com.hhwy.common.security.annotation.PreAuthorize;
//
///**
// * @author fushudong
// * @date 2024-03-05 16:56:06
// * @remark
// */
//@Validated
//@RestController
//@RequestMapping("/sgsjTechnicalScienceTopicApply")
//public class SgsjTechnicalScienceTopicApplyController extends BaseController {
//
//    @Autowired
//    private ISgsjTechnicalScienceTopicApplyService sgsjTechnicalScienceTopicApplyService;
//
//    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicApply:list")
//    @GetMapping
//    public AjaxResult getSgsjTechnicalScienceTopicApply(@Validated(ValidationGroups.Get.class) SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApplyParam) {
//        SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply = sgsjTechnicalScienceTopicApplyService.getSgsjTechnicalScienceTopicApply(sgsjTechnicalScienceTopicApplyParam);
//        return AjaxResult.success(sgsjTechnicalScienceTopicApply);
//    }
//
//    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicApply:list")
//    @GetMapping("/list")
//    public AjaxResult getSgsjTechnicalScienceTopicApplyList(@Validated(ValidationGroups.Select.class) SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApplyParam) {
//        startPage();
//        List<SgsjTechnicalScienceTopicApply> sgsjTechnicalScienceTopicApplyList = sgsjTechnicalScienceTopicApplyService.getSgsjTechnicalScienceTopicApplyList(sgsjTechnicalScienceTopicApplyParam);
//        return getDataTableAjaxResult(sgsjTechnicalScienceTopicApplyList);
//    }
//
//    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicApply:add")
//    @PostMapping("/add")
//    public AjaxResult insertSgsjTechnicalScienceTopicApply(@Validated(ValidationGroups.Save.class) @RequestBody SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApplyParam) {
//        sgsjTechnicalScienceTopicApplyService.insertSgsjTechnicalScienceTopicApply(sgsjTechnicalScienceTopicApplyParam);
//        return AjaxResult.success(sgsjTechnicalScienceTopicApplyParam);
//    }
//
//    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicApply:add")
//    @PostMapping("/batchAdd")
//    public AjaxResult insertSgsjTechnicalScienceTopicApplyList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgsjTechnicalScienceTopicApply> sgsjTechnicalScienceTopicApplyListParam) {
//        sgsjTechnicalScienceTopicApplyService.insertSgsjTechnicalScienceTopicApplyList(sgsjTechnicalScienceTopicApplyListParam);
//        return AjaxResult.success(sgsjTechnicalScienceTopicApplyListParam);
//    }
//
//    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicApply:update")
//    @PostMapping("/update")
//    public AjaxResult updateSgsjTechnicalScienceTopicApply(@Validated(ValidationGroups.Update.class) @RequestBody SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApplyParam) {
//        return toAjax(sgsjTechnicalScienceTopicApplyService.updateSgsjTechnicalScienceTopicApply(sgsjTechnicalScienceTopicApplyParam));
//    }
//
//    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicApply:update")
//    @PostMapping("/batchUpdate")
//    public AjaxResult updateSgsjTechnicalScienceTopicApplyList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgsjTechnicalScienceTopicApply> sgsjTechnicalScienceTopicApplyListParam) {
//        return toAjax(sgsjTechnicalScienceTopicApplyService.updateSgsjTechnicalScienceTopicApplyList(sgsjTechnicalScienceTopicApplyListParam));
//    }
//
//    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicApply:remove")
//    @PostMapping("/delete")
//    public AjaxResult deleteSgsjTechnicalScienceTopicApply(@Validated(ValidationGroups.Delete.class) @RequestBody SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApplyParam) {
//        return toAjax(sgsjTechnicalScienceTopicApplyService.deleteSgsjTechnicalScienceTopicApply(sgsjTechnicalScienceTopicApplyParam));
//    }
//
//    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicApply:remove")
//    @PostMapping("/{ids}")
//    public AjaxResult deleteSgsjTechnicalScienceTopicApplyByPks(@PathVariable Long[] ids) {
//        List<Long> sgsjTechnicalScienceTopicApplyPkList = Arrays.asList(ids);
//        return toAjax(sgsjTechnicalScienceTopicApplyService.deleteSgsjTechnicalScienceTopicApplyByPks(sgsjTechnicalScienceTopicApplyPkList));
//    }
//
//    @GetMapping("/export")
//    public void export(HttpServletResponse response, SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApplyParam) throws IOException {
//        List<SgsjTechnicalScienceTopicApply> sgsjTechnicalScienceTopicApplyList = sgsjTechnicalScienceTopicApplyService.getSgsjTechnicalScienceTopicApplyList(sgsjTechnicalScienceTopicApplyParam);
//        ExcelUtils<SgsjTechnicalScienceTopicApply> util = new ExcelUtils<>(SgsjTechnicalScienceTopicApply.class);
//        util.exportExcel(response, sgsjTechnicalScienceTopicApplyList, DateUtils.getDate());
//    }
//
//
//    //课题申请明细
//    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:list")
//    @GetMapping("/applyDetail")
//    public AjaxResult applyDetail(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
//        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicList = sgsjTechnicalScienceTopicApplyService.applyDetail(sgsjTechnicalScienceTopicParam);
//        return AjaxResult.success(sgsjTechnicalScienceTopicList);
//    }
//
//    //课题申请里的保存
//    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopic:add")
//    @PostMapping("/applyAdd")
//    public AjaxResult applyAdd(@Validated(ValidationGroups.Save.class) @RequestBody SgsjTechnicalScienceTopic sgsjTechnicalScienceTopicParam) {
//        sgsjTechnicalScienceTopicApplyService.applyAdd(sgsjTechnicalScienceTopicParam);
//        return AjaxResult.success(sgsjTechnicalScienceTopicParam);
//    }
//
//    /**
//     * 功能描述: 申请流程结束监听
//     * @param id  业务id
//     * 作者: fushudong
//     * 时间: 2024/2/1
//     */
//    @RequestMapping("/appplyListener")
//    public void appplyListener(@RequestParam("id") Long id){
//        SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = new SgsjTechnicalScienceTopic();
//        sgsjTechnicalScienceTopic.setPtVar1(String.valueOf(id));
//        //走第三分支(修改后通过)，流程结束 最终状态为"通过"
//        sgsjTechnicalScienceTopic.setApplyState("3");
//        sgsjTechnicalScienceTopicApplyService.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
//    }
//}
