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


    @GetMapping
    public AjaxResult getQqchWorkGroupMember(@Validated(ValidationGroups.Get.class) @RequestBody QqchWorkGroupMember qqchWorkGroupMemberParam) {
        QqchWorkGroupMember qqchWorkGroupMember = qqchWorkGroupMemberService.getQqchWorkGroupMember(qqchWorkGroupMemberParam);
        return AjaxResult.success(qqchWorkGroupMember);
    }

    /**
     * 工作小组成员台账
     * @param qqchWorkGroupMemberParam
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchWorkGroupMemberList(@Validated(ValidationGroups.Select.class) @RequestBody QqchWorkGroupMember qqchWorkGroupMemberParam) {
        startPage();
        List<QqchWorkGroupMember> qqchWorkGroupMemberList = qqchWorkGroupMemberService.getQqchWorkGroupMemberList(qqchWorkGroupMemberParam);
        return getDataTableAjaxResult(qqchWorkGroupMemberList);
    }

    @PostMapping("/add")
    public AjaxResult insertQqchWorkGroupMember(@Validated(ValidationGroups.Save.class) @RequestBody QqchWorkGroupMember qqchWorkGroupMemberParam) {
        qqchWorkGroupMemberService.insertQqchWorkGroupMember(qqchWorkGroupMemberParam);
        return AjaxResult.success(qqchWorkGroupMemberParam);
    }

    @PostMapping("/update")
    public AjaxResult updateQqchWorkGroupMember(@Validated(ValidationGroups.Update.class) @RequestBody QqchWorkGroupMember qqchWorkGroupMemberParam) {
        return toAjax(qqchWorkGroupMemberService.updateQqchWorkGroupMember(qqchWorkGroupMemberParam));
    }

    /**
     * 批量删除工作小组成员
     * @param ids
     * @return
     */
    @PostMapping("/remove/{ids}")
    public AjaxResult deleteQqchWorkGroupMemberByPks(@PathVariable Long[] ids) {
        List<Long> qqchWorkGroupMemberPkList = Arrays.asList(ids);
        return toAjax(qqchWorkGroupMemberService.deleteQqchWorkGroupMemberByPks(qqchWorkGroupMemberPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchWorkGroupMember qqchWorkGroupMemberParam) throws IOException {
        List<QqchWorkGroupMember> qqchWorkGroupMemberList = qqchWorkGroupMemberService.getQqchWorkGroupMemberList(qqchWorkGroupMemberParam);
        ExcelUtils<QqchWorkGroupMember> util = new ExcelUtils<>(QqchWorkGroupMember.class);
        util.exportExcel(response, qqchWorkGroupMemberList, DateUtils.getDate());
    }
}
