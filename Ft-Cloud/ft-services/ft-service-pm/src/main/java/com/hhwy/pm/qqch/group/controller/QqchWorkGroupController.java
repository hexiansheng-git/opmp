package com.hhwy.pm.qqch.group.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
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
        FlowInfoSearchUtil.getFlowInfo(qqchWorkGroup, FlowEnum.QQCH_WORK_GROUP);
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
    @PreAuthorize(hasPermi = "qqchWorkGroup:adjust")
    public AjaxResult adjustQqchWorkGroup(Long id){
        QqchWorkGroup qqchWorkGroup = qqchWorkGroupService.adjustQqchWorkGroup(id);
        FlowInfoSearchUtil.getFlowInfo(qqchWorkGroup, FlowEnum.QQCH_WORK_GROUP);
        return AjaxResult.success(qqchWorkGroup);
    }

    /**
     * 新增工作小组
     * @param qqchWorkGroup
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize(hasPermi = "qqchWorkGroup:save")
    @CustomLogger(title = "前期策划", name = "前期策划工作小组" ,businessType = CustomBusinessType.SAVE)
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
    @PreAuthorize(hasPermi = "qqchWorkGroup:save")
    @CustomLogger(title = "前期策划", name = "前期策划工作小组" ,businessType = CustomBusinessType.UPDATE)
    public AjaxResult updateQqchWorkGroup(@RequestBody QqchWorkGroup qqchWorkGroup) {
        qqchWorkGroupService.updateQqchWorkGroup(qqchWorkGroup);
        return AjaxResult.success(qqchWorkGroup.getId());
    }

    /**
     * 提交
     * @param qqchWorkGroup
     * @return
     */
    @PostMapping("/submit")
    @PreAuthorize(hasPermi = "qqchWorkGroup:submit")
    public AjaxResult submit(@RequestBody QqchWorkGroup qqchWorkGroup) {
        qqchWorkGroupService.submit(qqchWorkGroup);
        return AjaxResult.success(qqchWorkGroup.getId());
    }

    /**
     * 删除工作小组
     * @param qqchWorkGroupParam
     * @return
     */
    @PostMapping("/remove")
    @PreAuthorize(hasPermi = "qqchWorkGroup:remove")
    @CustomLogger(title = "前期策划", name = "前期策划工作小组" ,businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteQqchWorkGroup(@Validated(ValidationGroups.Delete.class) @RequestBody QqchWorkGroup qqchWorkGroupParam) {
        return toAjax(qqchWorkGroupService.deleteQqchWorkGroup(qqchWorkGroupParam));
    }

    /**
     * 监听器
     */
    @PostMapping("/listener")
    public AjaxResult updateWorkGroupProcess(@RequestParam("id") Long id) {
        qqchWorkGroupService.updateWorkGroupProcess(id);
        return AjaxResult.success("成功");
    }

    /**
     * 发送预警消息
     * @return
     */
    @GetMapping("workGroupSetUpWarn")
    public AjaxResult workGroupSetUpWarn() {
        qqchWorkGroupService.workGroupSetUpWarn();
        return AjaxResult.success();
    }
}
