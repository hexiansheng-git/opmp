package com.hhwy.sd.outlineReview.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.sd.outlineReview.domain.KcsjOutlineReview;
import com.hhwy.sd.outlineReview.service.IKcsjOutlineReviewService;
import com.hhwy.system.api.domain.SysUser;
import org.springframework.util.Assert;
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

/**
 * @author fushudong
 * @date 2024-02-04 15:29:15
 * @remark
 */
@Validated
@RestController
@RequestMapping("/kcsjOutlineReview")
public class KcsjOutlineReviewController extends BaseController {

    @Autowired
    private IKcsjOutlineReviewService kcsjOutlineReviewService;
    @Autowired
    private SystemServiceApi systemServiceApi;


    @PreAuthorize(hasPermi = "kcsjOutlineReview:list")
    @GetMapping
    public AjaxResult getKcsjOutlineReview(@Validated(ValidationGroups.Get.class) KcsjOutlineReview kcsjOutlineReviewParam) {
        KcsjOutlineReview kcsjOutlineReview = kcsjOutlineReviewService.getKcsjOutlineReview(kcsjOutlineReviewParam);
        return AjaxResult.success(kcsjOutlineReview);
    }

    //历史记录，台账
    @PreAuthorize(hasPermi = "kcsjOutlineReview:list")
    @GetMapping("/list")
    public AjaxResult getKcsjOutlineReviewList(@Validated(ValidationGroups.Select.class) KcsjOutlineReview kcsjOutlineReviewParam) {
        List<KcsjOutlineReview> kcsjOutlineReviewList = kcsjOutlineReviewService.getKcsjOutlineReviewList(kcsjOutlineReviewParam);
        return AjaxResult.success(kcsjOutlineReviewList);
    }

    //详情，编辑
    @PreAuthorize(hasPermi = "kcsjOutlineReview:list")
    @GetMapping("/detail")
    public AjaxResult detail(@Validated(ValidationGroups.Select.class) KcsjOutlineReview kcsjOutlineReviewParam) {
        KcsjOutlineReview kcsjOutlineReview = kcsjOutlineReviewService.getDetail(kcsjOutlineReviewParam);
        return AjaxResult.success(kcsjOutlineReview);
    }

    //调整
    @PreAuthorize(hasPermi = "kcsjOutlineReview:list")
    @GetMapping("/adjust")
    public AjaxResult adjust(@Validated(ValidationGroups.Select.class) KcsjOutlineReview kcsjOutlineReviewParam) {
        KcsjOutlineReview kcsjOutlineReview = kcsjOutlineReviewService.adjust(kcsjOutlineReviewParam);
        return AjaxResult.success(kcsjOutlineReview);
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:add")
    @PostMapping("/add")
    public AjaxResult insertKcsjOutlineReview(@Validated(ValidationGroups.Save.class) @RequestBody KcsjOutlineReview kcsjOutlineReviewParam) {
        Long id = kcsjOutlineReviewService.insertKcsjOutlineReview(kcsjOutlineReviewParam);
        return AjaxResult.success(id);
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertKcsjOutlineReviewList(@Validated(ValidationGroups.Save.class) @RequestBody List<KcsjOutlineReview> kcsjOutlineReviewListParam) {
        kcsjOutlineReviewService.insertKcsjOutlineReviewList(kcsjOutlineReviewListParam);
        return AjaxResult.success(kcsjOutlineReviewListParam);
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:update")
    @PostMapping("/update")
    public AjaxResult updateKcsjOutlineReview(@Validated(ValidationGroups.Update.class) @RequestBody KcsjOutlineReview kcsjOutlineReviewParam) {
        kcsjOutlineReviewService.updateKcsjOutlineReview(kcsjOutlineReviewParam);
        return AjaxResult.success(kcsjOutlineReviewParam.getId());
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateKcsjOutlineReviewList(@Validated(ValidationGroups.Update.class) @RequestBody List<KcsjOutlineReview> kcsjOutlineReviewListParam) {
        return toAjax(kcsjOutlineReviewService.updateKcsjOutlineReviewList(kcsjOutlineReviewListParam));
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:remove")
    @PostMapping("/delete")
    public AjaxResult deleteKcsjOutlineReview(@Validated(ValidationGroups.Delete.class) @RequestBody KcsjOutlineReview kcsjOutlineReviewParam) {
        return toAjax(kcsjOutlineReviewService.deleteKcsjOutlineReview(kcsjOutlineReviewParam));
    }

    @PreAuthorize(hasPermi = "kcsjOutlineReview:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteKcsjOutlineReviewByPks(@PathVariable Integer[] ids) {
        List<Integer> kcsjOutlineReviewPkList = Arrays.asList(ids);
        return toAjax(kcsjOutlineReviewService.deleteKcsjOutlineReviewByPks(kcsjOutlineReviewPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, KcsjOutlineReview kcsjOutlineReviewParam) throws IOException {
        List<KcsjOutlineReview> kcsjOutlineReviewList = kcsjOutlineReviewService.getKcsjOutlineReviewList(kcsjOutlineReviewParam);
        ExcelUtils<KcsjOutlineReview> util = new ExcelUtils<>(KcsjOutlineReview.class);
        util.exportExcel(response, kcsjOutlineReviewList, DateUtils.getDate());
    }

    /***
     * 功能描述: 流程结束监听
     * @param id  业务id
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/listener")
    public void updateTaskStatus(@RequestParam("id") Long id){
        KcsjOutlineReview kcsjOutlineReview = new KcsjOutlineReview();
        kcsjOutlineReview.setId(id);
        kcsjOutlineReview.setTaskStatus("5");
        kcsjOutlineReviewService.updateKcsjOutlineReview(kcsjOutlineReview);
    }

    //获取项目总工下得所有用户
    @RequestMapping("/getUserInfoByRole")
    public AjaxResult getUserInfoByRole(){
        String[] roleKey = {"lead_engineer"};
        AjaxResult ajaxResult = systemServiceApi.selectByRoleKeyList(roleKey);
        Integer code = (Integer) ajaxResult.get("code");
        Assert.isTrue(code.equals(200), "获取用户列表失败");
        String s = JSON.toJSONString(ajaxResult.get("data"));
        List<SysUser> sysUsers = JSON.parseArray(s, SysUser.class);
        String clientIds = sysUsers.stream().map(SysUser::getNickName).collect(Collectors.joining(","));
        return AjaxResult.success(clientIds);
    }
}
