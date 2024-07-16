package com.hhwy.system.warn.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.WarnItem;
import com.hhwy.constant.WarnScopeType;
import com.hhwy.domain.base.system.warn.TWarn;
import com.hhwy.domain.base.system.warn.TWarnRecord;
import com.hhwy.system.api.domain.SysRole;
import com.hhwy.system.core.mapper.SysRoleMapper;
import com.hhwy.system.push.WarnPushMenHu;
import com.hhwy.system.warn.push.Warn2Push;
import com.hhwy.system.warn.service.ITWarnService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private Warn2Push warn2Push;
    @Autowired
    private WarnPushMenHu warnPushMenHu;


//    @PreAuthorize(hasPermi = "tWarn:list")
    @GetMapping
    public AjaxResult getTWarn(@Validated(ValidationGroups.Get.class) TWarn tWarnParam) {
        TWarn tWarn = tWarnService.getTWarn(tWarnParam);
        return AjaxResult.success(tWarn);
    }

    @GetMapping({"/selfAllList"})
    public AjaxResult selfAllList(TWarn warn) {
        Map<String, Object> params = new HashMap<>();
        params.put("deptId", SecurityUtils.getSysUser().getDeptId());
        params.put("userName", SecurityUtils.getUserName());
        Long userId = SecurityUtils.getUserId();
        String tenantKey = SecurityUtils.getTenantKey();
        List<SysRole> sysRoles = roleMapper.selectRoleListByUserId(userId, tenantKey, Collections.singletonList("master"));
        String roleKeys = sysRoles.stream().map(SysRole::getRoleKey).collect(Collectors.joining(","));
        params.put("roleKeys",roleKeys);
        warn.setParams(params);
        warn.setTenantKey(tenantKey);
        startPage();
        List<TWarn> list = this.tWarnService.selectWarnListForSelf(warn);
        return getDataTableAjaxResult(list);
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
     * 发送预警 同时推送总部
     * @param tWarn
     * @return
     */
    @PostMapping("/addWarn")
    public AjaxResult addWarn(@Validated(ValidationGroups.Save.class) @RequestBody TWarn tWarn) {
        return toAjax(tWarnService.addWarn(tWarn));
    }

    /**
     * 发送预警 不推送总部
     * @param tWarn
     * @return
     */
    @PostMapping("/addWarnNonGm")
    public AjaxResult addWarnNonGm(@Validated(ValidationGroups.Save.class) @RequestBody TWarn tWarn) {
        return toAjax(tWarnService.addWarnNonGm(tWarn));
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

//    @PreAuthorize(hasPermi = "tWarn:add")
    @PostMapping("/batchAddToGm")
    public AjaxResult insertTWarnListToGm(@Validated(ValidationGroups.Save.class) @RequestBody List<TWarn> tWarnListParam) {
        tWarnService.insertTWarnListToGm(tWarnListParam);
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

    @PostMapping("selectByRoleKeyList")
    public AjaxResult selectByRoleKeyList(@RequestBody String[] roleKeyList){
        return AjaxResult.success(tWarnService.selectByRoleKeyList(roleKeyList));
    }

    /**
     * 根据角色和租户获取用户列表
     * @param roleKeyList 角色列表
     * @param tenantKey 租户
     * @return 用户列表
     */
    @PostMapping("selectByRoleAndTenant")
    public AjaxResult selectByRoleAndTenant(@RequestParam String[] roleKeyList, @RequestParam(value = "tenantKey", required = false) String tenantKey){
        return AjaxResult.success(tWarnService.selectByRoleKeyList(roleKeyList, tenantKey));
    }
    
    @GetMapping("/pushWarn/{id}")
    public AjaxResult pushWarn(@PathVariable("id") Long mainId) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        TWarn query = new TWarn();
        query.setWarnId(mainId);
        TWarn warn = this.tWarnService.getTWarn(query);
        warn2Push.push(warn);
        return AjaxResult.success();
    }

    //推送到中交门户
    @GetMapping("/pushMenhuWarn/{id}")
    public AjaxResult pushMenhuWarn(@PathVariable("id") Long mainId) {
        if(!SecurityUtils.getSysUser().isAdmin())
            return AjaxResult.error("ERROR");
        TWarn query = new TWarn();
        query.setWarnId(mainId);
        TWarn warn = this.tWarnService.getTWarn(query);
        warnPushMenHu.push(warn);
        return AjaxResult.success();
    }

}
