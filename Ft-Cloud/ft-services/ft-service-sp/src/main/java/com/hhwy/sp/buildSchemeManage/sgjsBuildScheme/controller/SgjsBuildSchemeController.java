package com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Constant;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.domain.SgjsBuildScheme;
import com.hhwy.sp.buildSchemeManage.sgjsBuildScheme.service.ISgjsBuildSchemeService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 功能描述: 施工方案管理 - 施工方案清单
 * 作者: fushudong
 * 时间: 2024-03-19
 */
@Validated
@RestController
@RequestMapping("/sgjsBuildScheme")
public class SgjsBuildSchemeController extends BaseController {

    @Autowired
    private ISgjsBuildSchemeService sgjsBuildSchemeService;

    @PreAuthorize(hasPermi = "sgjsBuildScheme:list")
    @GetMapping
    public AjaxResult getSgjsBuildScheme(@Validated(ValidationGroups.Get.class) SgjsBuildScheme sgjsBuildSchemeParam) {
        SgjsBuildScheme sgjsBuildScheme = sgjsBuildSchemeService.getSgjsBuildScheme(sgjsBuildSchemeParam);
        return AjaxResult.success(sgjsBuildScheme);
    }

    //台账、历史记录
    @PreAuthorize(hasPermi = "sgjsBuildScheme:list")
    @GetMapping("/list")
    public AjaxResult getSgjsBuildSchemeList(@Validated(ValidationGroups.Select.class) SgjsBuildScheme sgjsBuildSchemeParam) {
        List<SgjsBuildScheme> sgjsBuildSchemeList = sgjsBuildSchemeService.getSgjsBuildSchemeList(sgjsBuildSchemeParam);
        return AjaxResult.success(sgjsBuildSchemeList);
    }

    //详情
    @PreAuthorize(hasPermi = "sgjsBuildScheme:list")
    @GetMapping("/detail")
    public AjaxResult detail(@Validated(ValidationGroups.Select.class) SgjsBuildScheme sgjsBuildSchemeParam) {
        SgjsBuildScheme sgjsBuildScheme = sgjsBuildSchemeService.detail(sgjsBuildSchemeParam);
        return AjaxResult.success(sgjsBuildScheme);
    }

    //调整
    @PreAuthorize(hasPermi = "sgjsBuildScheme:list")
    @GetMapping("/adjust")
    public AjaxResult adjust(@Validated(ValidationGroups.Select.class) SgjsBuildScheme sgjsBuildSchemeParam) {
        SgjsBuildScheme sgjsBuildScheme = sgjsBuildSchemeService.adjust(sgjsBuildSchemeParam);
        return AjaxResult.success(sgjsBuildScheme);
    }



    //保存、提交
    @PreAuthorize(hasPermi = "sgjsBuildScheme:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsBuildScheme(@Validated(ValidationGroups.Save.class) @RequestBody SgjsBuildScheme sgjsBuildSchemeParam) {
        Long id = sgjsBuildSchemeService.insertSgjsBuildScheme(sgjsBuildSchemeParam);
        return AjaxResult.success(id);
    }

    @PreAuthorize(hasPermi = "sgjsBuildScheme:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsBuildSchemeList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsBuildScheme> sgjsBuildSchemeListParam) {
        sgjsBuildSchemeService.insertSgjsBuildSchemeList(sgjsBuildSchemeListParam);
        return AjaxResult.success(sgjsBuildSchemeListParam);
    }

    @PreAuthorize(hasPermi = "sgjsBuildScheme:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsBuildScheme(@Validated(ValidationGroups.Update.class) @RequestBody SgjsBuildScheme sgjsBuildSchemeParam) {
        return toAjax(sgjsBuildSchemeService.updateSgjsBuildScheme(sgjsBuildSchemeParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildScheme:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsBuildSchemeList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsBuildScheme> sgjsBuildSchemeListParam) {
        return toAjax(sgjsBuildSchemeService.updateSgjsBuildSchemeList(sgjsBuildSchemeListParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildScheme:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsBuildScheme(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsBuildScheme sgjsBuildSchemeParam) {
        return toAjax(sgjsBuildSchemeService.deleteSgjsBuildScheme(sgjsBuildSchemeParam));
    }

    @PreAuthorize(hasPermi = "sgjsBuildScheme:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsBuildSchemeByPks(@PathVariable Long[] ids) {
        List<Long> sgjsBuildSchemePkList = Arrays.asList(ids);
        return toAjax(sgjsBuildSchemeService.deleteSgjsBuildSchemeByPks(sgjsBuildSchemePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsBuildScheme sgjsBuildSchemeParam) throws IOException {
        List<SgjsBuildScheme> sgjsBuildSchemeList = sgjsBuildSchemeService.getSgjsBuildSchemeList(sgjsBuildSchemeParam);
        ExcelUtils<SgjsBuildScheme> util = new ExcelUtils<>(SgjsBuildScheme.class);
        util.exportExcel(response, sgjsBuildSchemeList, DateUtils.getDate());
    }

    /*
    * 功能描述: 流程结束监听器
    * @param: id 业务id
    * @param: isPass 1通过 0不通过
    */
    @RequestMapping("/listener")
    public void updateBuildScheme(@RequestParam("id") Long id, @RequestParam("isPass") String isPass){
        SgjsBuildScheme sgjsBuildScheme = new SgjsBuildScheme();
        sgjsBuildScheme.setTaskStatus("5");
        sgjsBuildScheme.setId(id);
        sgjsBuildScheme.setPtVar3(isPass);
        if (StrUtil.isBlank(isPass)){
            //设置为无效
            sgjsBuildScheme.setValid("0");
        }else {
            //0不通过 1通过
            sgjsBuildScheme.setValid(isPass);
        }
        sgjsBuildSchemeService.updateSgjsBuildScheme(sgjsBuildScheme);
    }
}
