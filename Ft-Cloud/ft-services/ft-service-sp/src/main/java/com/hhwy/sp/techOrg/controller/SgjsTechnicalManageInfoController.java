package com.hhwy.sp.techOrg.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import com.hhwy.sp.techOrg.domain.SgjsTechnicalManageInfo;
import com.hhwy.sp.techOrg.service.ISgjsTechnicalManageInfoService;
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
 * 技术管理机构管理-在场/离场记录
 * @author lcf
 * @date 2023-11-23 10:21:37
 * @remark
 */
@Validated
@RestController
@RequestMapping("/sgjsTechnicalManage/info")
public class SgjsTechnicalManageInfoController extends BaseController{

    @Autowired
    private ISgjsTechnicalManageInfoService sgjsTechnicalManageInfoService;


    @PreAuthorize(hasPermi = "sgjsTechnicalManageInfo:list")
    @GetMapping
    public AjaxResult getSgjsTechnicalManageInfo(@Validated(ValidationGroups.Get.class)  SgjsTechnicalManageInfo sgjsTechnicalManageInfoParam){
        SgjsTechnicalManageInfo sgjsTechnicalManageInfo =  sgjsTechnicalManageInfoService.getSgjsTechnicalManageInfo(sgjsTechnicalManageInfoParam);
        return AjaxResult.success(sgjsTechnicalManageInfo);
    }

    /**
     * 查询
     *
     * @param sgjsTechnicalManageInfoParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalManageInfo:list")
    @GetMapping("/list")
    public AjaxResult getSgjsTechnicalManageInfoList(@Validated(ValidationGroups.Select.class) SgjsTechnicalManageInfo sgjsTechnicalManageInfoParam){
        List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoList = sgjsTechnicalManageInfoService.getSgjsTechnicalManageInfoList(sgjsTechnicalManageInfoParam);
        return getDataTableAjaxResult(sgjsTechnicalManageInfoList);
    }

    /**
     * 新增
     *
     * @param sgjsTechnicalManageInfoParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalManageInfo:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechnicalManageInfo(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechnicalManageInfo sgjsTechnicalManageInfoParam){
        sgjsTechnicalManageInfoService.insertSgjsTechnicalManageInfo(sgjsTechnicalManageInfoParam);
        return AjaxResult.success();
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalManageInfo:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechnicalManageInfoList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoListParam){
        sgjsTechnicalManageInfoService.insertSgjsTechnicalManageInfoList(sgjsTechnicalManageInfoListParam);
        return AjaxResult.success();
    }

    /**
     * 编辑
     *
     * @param sgjsTechnicalManageInfoParam
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalManageInfo:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsTechnicalManageInfo(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechnicalManageInfo sgjsTechnicalManageInfoParam){
        return toAjax(sgjsTechnicalManageInfoService.updateSgjsTechnicalManageInfo(sgjsTechnicalManageInfoParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalManageInfo:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechnicalManageInfoList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoListParam){
        return toAjax(sgjsTechnicalManageInfoService.updateSgjsTechnicalManageInfoList(sgjsTechnicalManageInfoListParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechnicalManageInfo:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechnicalManageInfo(@RequestBody List<Long> ids){
        return toAjax(sgjsTechnicalManageInfoService.deleteSgjsTechnicalManageInfoByPks(ids));
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @PreAuthorize(hasPermi = "sgjsTechnicalManageInfo:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsTechnicalManageInfoByPks(@PathVariable Long[] ids){
        List<Long> sgjsTechnicalManageInfoPkList = Arrays.asList(ids);
        return toAjax(sgjsTechnicalManageInfoService.deleteSgjsTechnicalManageInfoByPks(sgjsTechnicalManageInfoPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsTechnicalManageInfo sgjsTechnicalManageInfoParam) throws IOException {
        List<SgjsTechnicalManageInfo> sgjsTechnicalManageInfoList = sgjsTechnicalManageInfoService.getSgjsTechnicalManageInfoList(sgjsTechnicalManageInfoParam);
        ExcelUtils<SgjsTechnicalManageInfo> util = new ExcelUtils<>(SgjsTechnicalManageInfo.class);
        util.exportExcel(response, sgjsTechnicalManageInfoList, DateUtils.getDate());
    }
}
