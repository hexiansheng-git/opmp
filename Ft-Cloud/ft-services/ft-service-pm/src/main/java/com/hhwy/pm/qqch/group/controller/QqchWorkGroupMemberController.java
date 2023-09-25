package com.hhwy.pm.qqch.group.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.domain.vo.WorkGroupMemberQueryVo;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupMemberService;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:45
 * @remark  前期策划工作小组成员
 */
@Validated
@RestController
@RequestMapping("/qqchWorkGroupMember")
public class QqchWorkGroupMemberController extends BaseController {

    @Autowired
    private IQqchWorkGroupMemberService qqchWorkGroupMemberService;

    @Autowired
    private IQqchWorkGroupService qqchWorkGroupService;

    /**
     * 获取工作小组成员历史
     * @param queryVo
     * @return
     */
    @PostMapping("history")
    public AjaxResult getEstablishPreliminaryPlanHistory(@Validated(ValidationGroups.Select.class) @RequestBody WorkGroupMemberQueryVo queryVo){
        List<QqchWorkGroupMember> workGroupMemberList = qqchWorkGroupMemberService.getEstablishPreliminaryPlanHistory(queryVo);
        return AjaxResult.success(workGroupMemberList);
    }

    /**
     * 获取最新生效版本的工作小组成员
     * @return
     */
    @GetMapping("getValidMaxVersionWorkGroupMemberList")
    public AjaxResult getValidMaxVersionWorkGroupMemberList(QqchWorkGroupMember qqchWorkGroupMember) {
        List<QqchWorkGroupMember> workGroupMemberList = new ArrayList<>();

        //获取当前最新生效版本的工作小组
        QqchWorkGroup validMaxVersionQqchWorkGroup = qqchWorkGroupService.getValidMaxVersionQqchWorkGroup();
        if(validMaxVersionQqchWorkGroup != null){
            qqchWorkGroupMember.setWorkGroupId(validMaxVersionQqchWorkGroup.getId());
            startPage();
            workGroupMemberList = qqchWorkGroupMemberService.getQqchWorkGroupMemberList(qqchWorkGroupMember);
        }
        return getDataTableAjaxResult(workGroupMemberList);
    }
}
