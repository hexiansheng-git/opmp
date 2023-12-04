package com.hhwy.system.warn.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.domain.base.system.warn.TWarnRecord;
import com.hhwy.system.warn.service.ITWarnService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-09-26 17:52:25
 * @remark
 */
@Validated
@RestController
@RequestMapping("/tWarn")
public class TWarnController extends BaseController {

    @Autowired
    private ITWarnService tWarnService;


//    @PreAuthorize(hasPermi = "tWarn:list")
    @GetMapping
    public AjaxResult getTWarn(@Validated(ValidationGroups.Get.class) TWarn tWarnParam) {
        TWarn tWarn = tWarnService.getTWarn(tWarnParam);
        return AjaxResult.success(tWarn);
    }

    @GetMapping({"/selfAllList"})
    public AjaxResult selfAllList(TWarn warn) {
        List<TWarn> list = this.tWarnService.selectWarnListForSelf(warn);
        return AjaxResult.success(list);
    }

    @PutMapping({"/changeHandleStatus"})
    public AjaxResult changeHandleStatus(@RequestBody TWarnRecord record) {
        return toAjax(tWarnService.changeHandleStatus(record));
    }

    @PutMapping({"/batchChangeHandleStatus/{status}/{warnIds}"})
    public AjaxResult batchChangeHandleStatus(@PathVariable Long[] warnIds, @PathVariable String status) {
        tWarnService.batchChangeHandleStatus(warnIds, status);
        return AjaxResult.success();
    }


//    @PreAuthorize(hasPermi = "tWarn:list")
    @GetMapping("/list")
    public AjaxResult getTWarnList(@Validated(ValidationGroups.Select.class) TWarn tWarnParam) {
        startPage();
        List<TWarn> tWarnList = tWarnService.getTWarnList(tWarnParam);
        return getDataTableAjaxResult(tWarnList);
    }

    /**
     * 发送预警
     * @param tWarn
     * @return
     */
    @PostMapping("/addWarn")
    public AjaxResult addWarn(@Validated(ValidationGroups.Save.class) @RequestBody TWarn tWarn) {
        return toAjax(tWarnService.addWarn(tWarn));
    }

    @PostMapping("/addWarn1")
    public AjaxResult addWarn(WarnItem warnItem, WarnScopeType warnScopeType, String warnScope, String warnUrl, String projectName, String tenantKey){
        return toAjax(tWarnService.addWarn(warnItem,warnScopeType,warnScope,warnUrl,projectName,tenantKey));
    }

    @PreAuthorize(hasPermi = "tWarn:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertTWarnList(@Validated(ValidationGroups.Save.class) @RequestBody List<TWarn> tWarnListParam) {
        tWarnService.insertTWarnList(tWarnListParam);
        return AjaxResult.success(tWarnListParam);
    }

    @PreAuthorize(hasPermi = "tWarn:update")
    @PostMapping("/update")
    public AjaxResult updateTWarn(@Validated(ValidationGroups.Update.class) @RequestBody TWarn tWarnParam) {
        return toAjax(tWarnService.updateTWarn(tWarnParam));
    }

    @PreAuthorize(hasPermi = "tWarn:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateTWarnList(@Validated(ValidationGroups.Update.class) @RequestBody List<TWarn> tWarnListParam) {
        return toAjax(tWarnService.updateTWarnList(tWarnListParam));
    }

    @PreAuthorize(hasPermi = "tWarn:remove")
    @PostMapping("/delete")
    public AjaxResult deleteTWarn(@Validated(ValidationGroups.Delete.class) @RequestBody TWarn tWarnParam) {
        return toAjax(tWarnService.deleteTWarn(tWarnParam));
    }

    @PreAuthorize(hasPermi = "tWarn:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteTWarnByPks(@PathVariable Long[] ids) {
        List<Long> tWarnPkList = Arrays.asList(ids);
        return toAjax(tWarnService.deleteTWarnByPks(tWarnPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, TWarn tWarnParam) throws IOException {
        List<TWarn> tWarnList = tWarnService.getTWarnList(tWarnParam);
        ExcelUtils<TWarn> util = new ExcelUtils<>(TWarn.class);
        util.exportExcel(response, tWarnList, DateUtils.getDate());
    }

    @GetMapping("selectByRoleKeyList")
    public AjaxResult selectByRoleKeyList(String[] roleKeyList){
        return AjaxResult.success(tWarnService.selectByRoleKeyList(roleKeyList));
    }
}
