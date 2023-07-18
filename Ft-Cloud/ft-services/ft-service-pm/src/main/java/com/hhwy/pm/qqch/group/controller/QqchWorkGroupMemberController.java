package com.hhwy.pm.qqch.group.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupMemberService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;

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
     * @param directorId 成员id
     * @return
     */
    @GetMapping("history")
    public AjaxResult getEstablishPreliminaryPlanHistory(Long directorId){
        List<QqchWorkGroupMember> workGroupMemberList = qqchWorkGroupMemberService.getEstablishPreliminaryPlanHistory(directorId);
        return AjaxResult.success(workGroupMemberList);
    }
}
