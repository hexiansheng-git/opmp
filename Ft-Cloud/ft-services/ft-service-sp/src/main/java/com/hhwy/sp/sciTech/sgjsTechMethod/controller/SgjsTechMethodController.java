package com.hhwy.sp.sciTech.sgjsTechMethod.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import com.hhwy.sp.sciTech.sgjsTechMethod.service.ISgjsTechMethodService;
import com.hhwy.sp.sciTech.sgjsTechMethod.domain.SgjsTechMethod;

import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;
import com.hhwy.common.security.annotation.PreAuthorize;

/**
 * @author cjh
 * @date 2024-01-25 10:11:14
 * @remark 科技管理-工艺工法管理
 */
@Validated
@RestController
@RequestMapping("/sgjsTechMethod")
public class SgjsTechMethodController extends BaseController {

    @Autowired
    private ISgjsTechMethodService sgjsTechMethodService;


    @PreAuthorize(hasPermi = "sgjsTechMethod:list")
    @GetMapping
    public AjaxResult getSgjsTechMethod(@Validated(ValidationGroups.Get.class) SgjsTechMethod sgjsTechMethodParam) {
        SgjsTechMethod sgjsTechMethod = sgjsTechMethodService.getSgjsTechMethod(sgjsTechMethodParam);
        return AjaxResult.success(sgjsTechMethod);
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:list")
    @GetMapping("/list")
    public AjaxResult getSgjsTechMethodList(@Validated(ValidationGroups.Select.class) SgjsTechMethod sgjsTechMethodParam) {
        startPage();
        List<SgjsTechMethod> sgjsTechMethodList = sgjsTechMethodService.getSgjsTechMethodList(sgjsTechMethodParam);
        return getDataTableAjaxResult(sgjsTechMethodList);
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:add")
    @PostMapping("/add")
    public AjaxResult insertSgjsTechMethod(@Validated(ValidationGroups.Save.class) @RequestBody SgjsTechMethod sgjsTechMethodParam) {
        sgjsTechMethodService.insertSgjsTechMethod(sgjsTechMethodParam);
        return AjaxResult.success(sgjsTechMethodParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertSgjsTechMethodList(@Validated(ValidationGroups.Save.class) @RequestBody List<SgjsTechMethod> sgjsTechMethodListParam) {
        sgjsTechMethodService.insertSgjsTechMethodList(sgjsTechMethodListParam);
        return AjaxResult.success(sgjsTechMethodListParam);
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:update")
    @PostMapping("/update")
    public AjaxResult updateSgjsTechMethod(@Validated(ValidationGroups.Update.class) @RequestBody SgjsTechMethod sgjsTechMethodParam) {
        return AjaxResult.success(sgjsTechMethodService.updateSgjsTechMethod(sgjsTechMethodParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateSgjsTechMethodList(@Validated(ValidationGroups.Update.class) @RequestBody List<SgjsTechMethod> sgjsTechMethodListParam) {
        return toAjax(sgjsTechMethodService.updateSgjsTechMethodList(sgjsTechMethodListParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:remove")
    @PostMapping("/delete")
    public AjaxResult deleteSgjsTechMethod(@Validated(ValidationGroups.Delete.class) @RequestBody SgjsTechMethod sgjsTechMethodParam) {
        return toAjax(sgjsTechMethodService.deleteSgjsTechMethod(sgjsTechMethodParam));
    }

    @PreAuthorize(hasPermi = "sgjsTechMethod:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteSgjsTechMethodByPks(@PathVariable Long[] ids) {
        List<Long> sgjsTechMethodPkList = Arrays.asList(ids);
        return toAjax(sgjsTechMethodService.deleteSgjsTechMethodByPks(sgjsTechMethodPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, SgjsTechMethod sgjsTechMethodParam) throws IOException {
        List<SgjsTechMethod> sgjsTechMethodList = sgjsTechMethodService.getSgjsTechMethodList(sgjsTechMethodParam);
        ExcelUtils<SgjsTechMethod> util = new ExcelUtils<>(SgjsTechMethod.class);
        util.exportExcel(response, sgjsTechMethodList, DateUtils.getDate());
    }

    /**
     *
     * 更新流程数据
     * @param id 主键
     * @return  监听器
     */
    @RequestMapping(value ="/listener",method = RequestMethod.POST)
    @Transactional
    public AjaxResult updateTaskStatus(@RequestParam ("id") Long id, @RequestParam ("isPass") String isPass) {
        sgjsTechMethodService.updateTaskStatus(id, isPass);
        return AjaxResult.success();
    }

    /***
     * 功能描述: 消息发布
     * 作者: fushudong
     * 时间: 2024/2/1
     */
    @RequestMapping("/messagePublic")
    public AjaxResult messagePublic(){
        return sgjsTechMethodService.messagePublic();
    }
}
