package com.hhwy.pm.qqch.group.controller;

import java.util.List;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupMemberService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

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

    /**
     * 获取工作小组成员历史
     * @param qqchWorkGroupMember
     * @return
     */
    @GetMapping("history")
    public AjaxResult getEstablishPreliminaryPlanHistory(@Validated(ValidationGroups.Select.class) QqchWorkGroupMember qqchWorkGroupMember){
        startPage();
        List<QqchWorkGroupMember> workGroupMemberList = qqchWorkGroupMemberService.getEstablishPreliminaryPlanHistory(qqchWorkGroupMember);
        return getDataTableAjaxResult(workGroupMemberList);
    }
}
