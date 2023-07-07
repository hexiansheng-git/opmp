package com.hhwy.pm.qqch.group.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import com.hhwy.utils.validation.ValidationGroups;

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


    @GetMapping
    public AjaxResult getQqchWorkGroup(@Validated(ValidationGroups.Get.class) @RequestBody QqchWorkGroup qqchWorkGroupParam) {
        QqchWorkGroup qqchWorkGroup = qqchWorkGroupService.getQqchWorkGroup(qqchWorkGroupParam);
        return AjaxResult.success(qqchWorkGroup);
    }

    /**
     * 根据id获取工作小组信息
     * @param id
     * @return
     */
    @GetMapping("/getById")
//    @Validated(ValidationGroups.Get.class)
    public AjaxResult getQqchWorkGroupById(@NotNull(message = "id不能为空",groups = ValidationGroups.Get.class) Long id){
        QqchWorkGroup qqchWorkGroup = qqchWorkGroupService.getQqchWorkGroupById(id);
        return AjaxResult.success(qqchWorkGroup);
    }

    /**
     * 台账（历史记录）
     * @param qqchWorkGroupParam
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getQqchWorkGroupList(@Validated(ValidationGroups.Select.class) @RequestBody QqchWorkGroup qqchWorkGroupParam) {
        startPage();
        List<QqchWorkGroup> qqchWorkGroupList = qqchWorkGroupService.getQqchWorkGroupList(qqchWorkGroupParam);
        return getDataTableAjaxResult(qqchWorkGroupList);
    }

    /**
     * 新增工作小组
     * @param qqchWorkGroupParam
     * @return
     */
    @PostMapping("/add")
    public AjaxResult insertQqchWorkGroup(@Validated(ValidationGroups.Save.class) @RequestBody QqchWorkGroup qqchWorkGroupParam) {
        qqchWorkGroupService.insertQqchWorkGroup(qqchWorkGroupParam);
        return AjaxResult.success(qqchWorkGroupParam);
    }

    /**
     * 修改工作小组
     * @param qqchWorkGroupParam
     * @return
     */
    @PostMapping("/update")
    public AjaxResult updateQqchWorkGroup(@Validated(ValidationGroups.Update.class) @RequestBody QqchWorkGroup qqchWorkGroupParam) {
        return toAjax(qqchWorkGroupService.updateQqchWorkGroup(qqchWorkGroupParam));
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
     * 批量删除工作小组
     * @param ids
     * @return
     */
    @PostMapping("/remove/{ids}")
    public AjaxResult deleteQqchWorkGroupByPks(@PathVariable Long[] ids) {
        List<Long> qqchWorkGroupPkList = Arrays.asList(ids);
        return toAjax(qqchWorkGroupService.deleteQqchWorkGroupByPks(qqchWorkGroupPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchWorkGroup qqchWorkGroupParam) throws IOException {
        List<QqchWorkGroup> qqchWorkGroupList = qqchWorkGroupService.getQqchWorkGroupList(qqchWorkGroupParam);
        ExcelUtils<QqchWorkGroup> util = new ExcelUtils<>(QqchWorkGroup.class);
        util.exportExcel(response, qqchWorkGroupList, DateUtils.getDate());
    }
}
