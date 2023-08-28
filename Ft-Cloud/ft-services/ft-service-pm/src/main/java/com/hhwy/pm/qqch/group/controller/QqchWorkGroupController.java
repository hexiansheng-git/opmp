package com.hhwy.pm.qqch.group.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:42
 * @remark 前期策划工作小组
 */
@Validated
@RestController
@RequestMapping("/qqchWorkGroup")
public class QqchWorkGroupController extends BaseController {

    @Autowired
    private IQqchWorkGroupService qqchWorkGroupService;


    /**
     * 点击菜单 或 根据id获取工作小组信息（详情）
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public AjaxResult getQqchWorkGroupById(Long id){
        QqchWorkGroup qqchWorkGroup = qqchWorkGroupService.getQqchWorkGroupById(id);
        return AjaxResult.success(qqchWorkGroup);
    }

    /**
     * 台账（历史记录）
     * @param qqchWorkGroupParam
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchWorkGroupList(@Validated(ValidationGroups.Select.class) QqchWorkGroup qqchWorkGroupParam) {
        startPage();
        List<QqchWorkGroup> qqchWorkGroupList = qqchWorkGroupService.getQqchWorkGroupList(qqchWorkGroupParam);
        return getDataTableAjaxResult(qqchWorkGroupList);
    }

    /**
     * 调整
     * @param id
     * @return
     */
    @GetMapping("/adjust")
    public AjaxResult adjustQqchWorkGroup(Long id){
        QqchWorkGroup qqchWorkGroup = qqchWorkGroupService.adjustQqchWorkGroup(id);
        return AjaxResult.success(qqchWorkGroup);
    }

    /**
     * 新增工作小组
     * @param qqchWorkGroup
     * @return
     */
    @PostMapping("/add")
    public AjaxResult insertQqchWorkGroup(@RequestBody QqchWorkGroup qqchWorkGroup) {
        qqchWorkGroupService.insertQqchWorkGroup(qqchWorkGroup);
        return AjaxResult.success(qqchWorkGroup.getId());
    }

    /**
     * 修改工作小组
     * @param qqchWorkGroup
     * @return
     */
    @PostMapping("/update")
    public AjaxResult updateQqchWorkGroup(@Validated(ValidationGroups.Update.class) @RequestBody QqchWorkGroup qqchWorkGroup) {
        qqchWorkGroupService.updateQqchWorkGroup(qqchWorkGroup);
        return AjaxResult.success(qqchWorkGroup.getId());
    }

    /**
     * 提交
     * @param qqchWorkGroup
     * @return
     */
    @PostMapping("/submit")
    public AjaxResult submit(@Validated({ValidationGroups.Update.class,ValidationGroups.Save.class}) @RequestBody QqchWorkGroup qqchWorkGroup) {
        qqchWorkGroupService.submit(qqchWorkGroup);
        return AjaxResult.success(qqchWorkGroup.getId());
    }

    /**
     * 删除工作小组
     * @param qqchWorkGroupParam
     * @return
     */
    @PostMapping("/remove")
    public AjaxResult deleteQqchWorkGroup(@Validated(ValidationGroups.Delete.class) @RequestBody QqchWorkGroup qqchWorkGroupParam) {
        return toAjax(qqchWorkGroupService.deleteQqchWorkGroup(qqchWorkGroupParam));
    }

    /**
     * 查询指定租户下的工作小组
     * @param qqchWorkGroupParam
     * @return
     */
    @GetMapping("/gmList")
    public AjaxResult gmList(@Validated(ValidationGroups.Select.class) QqchWorkGroup qqchWorkGroupParam) {
        List<QqchWorkGroup> qqchWorkGroupList = qqchWorkGroupService.gmList(qqchWorkGroupParam);
        return getDataTableAjaxResult(qqchWorkGroupList);
    }
}
