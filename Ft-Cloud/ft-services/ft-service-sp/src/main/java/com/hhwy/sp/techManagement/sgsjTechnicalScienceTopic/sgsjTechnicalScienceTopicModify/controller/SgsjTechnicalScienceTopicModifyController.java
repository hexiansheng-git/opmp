package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.domain.SgsjTechnicalScienceTopicModify;
import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.sgsjTechnicalScienceTopicModify.service.ISgsjTechnicalScienceTopicModifyService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/***
 * 功能描述: 科技管理 - 科研课题研发管理 修改记录
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Validated
@RestController
@RequestMapping("/sgsjTechnicalScienceTopicModify")
public class SgsjTechnicalScienceTopicModifyController extends BaseController {

    @Autowired
    private ISgsjTechnicalScienceTopicModifyService sgsjTechnicalScienceTopicModifyService;


    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicModify:list")
    @GetMapping
    public AjaxResult getSgsjTechnicalScienceTopicModify(@Validated(ValidationGroups.Get.class) SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModifyParam) {
        SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModify = sgsjTechnicalScienceTopicModifyService.getSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModifyParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicModify);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicModify:list")
    @GetMapping("/list")
    public AjaxResult getSgsjTechnicalScienceTopicModifyList(@Validated(ValidationGroups.Select.class) SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModifyParam) {
        List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList = sgsjTechnicalScienceTopicModifyService.getSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModifyParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicModifyList);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicModify:add")
    @PostMapping("/add")
    public AjaxResult insertSgsjTechnicalScienceTopicModify(@Validated(ValidationGroups.Save.class) @RequestBody SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModifyParam) {
        sgsjTechnicalScienceTopicModifyService.insertSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModifyParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicModifyParam);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicModify:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgsjTechnicalScienceTopicModifyList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyListParam) {
        sgsjTechnicalScienceTopicModifyService.insertSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModifyListParam);
        return AjaxResult.success(sgsjTechnicalScienceTopicModifyListParam);
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicModify:update")
    @PostMapping("/update")
    public AjaxResult updateSgsjTechnicalScienceTopicModify(@Validated(ValidationGroups.Update.class) @RequestBody SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModifyParam) {
        return toAjax(sgsjTechnicalScienceTopicModifyService.updateSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModifyParam));
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicModify:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgsjTechnicalScienceTopicModifyList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyListParam) {
        return toAjax(sgsjTechnicalScienceTopicModifyService.updateSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModifyListParam));
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicModify:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgsjTechnicalScienceTopicModify(@Validated(ValidationGroups.Delete.class) @RequestBody SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModifyParam) {
        return toAjax(sgsjTechnicalScienceTopicModifyService.deleteSgsjTechnicalScienceTopicModify(sgsjTechnicalScienceTopicModifyParam));
    }

    @PreAuthorize(hasPermi = "sgsjTechnicalScienceTopicModify:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgsjTechnicalScienceTopicModifyByPks(@PathVariable Long[] ids) {
        List<Long> sgsjTechnicalScienceTopicModifyPkList = Arrays.asList(ids);
        return toAjax(sgsjTechnicalScienceTopicModifyService.deleteSgsjTechnicalScienceTopicModifyByPks(sgsjTechnicalScienceTopicModifyPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgsjTechnicalScienceTopicModify sgsjTechnicalScienceTopicModifyParam) throws IOException {
        List<SgsjTechnicalScienceTopicModify> sgsjTechnicalScienceTopicModifyList = sgsjTechnicalScienceTopicModifyService.getSgsjTechnicalScienceTopicModifyList(sgsjTechnicalScienceTopicModifyParam);
        ExcelUtils<SgsjTechnicalScienceTopicModify> util = new ExcelUtils<>(SgsjTechnicalScienceTopicModify.class);
        util.exportExcel(response, sgsjTechnicalScienceTopicModifyList, DateUtils.getDate());
    }
}
