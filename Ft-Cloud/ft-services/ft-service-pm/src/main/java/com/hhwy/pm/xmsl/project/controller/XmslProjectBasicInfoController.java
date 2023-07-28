package com.hhwy.pm.xmsl.project.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author han
 * @date 2023-07-03 09:48:24
 * @remark 项目基本信息
 */
@RestController
@RequestMapping("/projectBasicInfo")
public class XmslProjectBasicInfoController extends BaseController{

    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;

    /**
     * 根据id获取项目基本信息
     * @param id
     * @return
     */
    @GetMapping("getProjectBasicInfoById")
    @Validated(ValidationGroups.Get.class)
    public AjaxResult getProjectBasicInfoById(@NotNull(message = "id不能为空",groups = ValidationGroups.Get.class) Long id){
        XmslProjectBasicInfo xmslProjectBasicInfo = projectBasicInfoService.getProjectBasicInfoById(id);
        return AjaxResult.success(xmslProjectBasicInfo);
    }

    /**
     * 获取项目信息详情（带子表）
     * @param projectBasicInfo
     * @return
     */
    @GetMapping("getProjectBasicInfoWithSublist")
    public AjaxResult getProjectBasicInfoWithSublist(@Validated(ValidationGroups.Select.class) XmslProjectBasicInfo projectBasicInfo){
        XmslProjectBasicInfo xmslProjectBasicInfo = projectBasicInfoService.getProjectBasicInfoWithSublist(projectBasicInfo);
        return AjaxResult.success(xmslProjectBasicInfo);
    }

    /**
     * 获取项目信息详情（不带子表）
     * @param projectBasicInfo
     * @return
     */
    @GetMapping("getProjectBasicInfoWithoutSublist")
    public AjaxResult getProjectBasicInfoWithoutSublist(@Validated(ValidationGroups.Select.class) XmslProjectBasicInfo projectBasicInfo){
        XmslProjectBasicInfo xmslProjectBasicInfo = projectBasicInfoService.getProjectBasicInfoWithoutSublist(projectBasicInfo);
        return AjaxResult.success(xmslProjectBasicInfo);
    }

    /**
     * 项目台账
     * @param xmslProjectBasicInfoParam
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getProjectBasicInfoList(@Validated(ValidationGroups.Select.class) XmslProjectBasicInfo xmslProjectBasicInfoParam){
        startPage();
        List<XmslProjectBasicInfo> xmslProjectBasicInfoList = projectBasicInfoService.getProjectBasicInfoList(xmslProjectBasicInfoParam);
        return getDataTableAjaxResult(xmslProjectBasicInfoList);
    }

    /**
     * 新增项目信息
     * @param xmslProjectBasicInfoParam
     * @return
     */
    @PostMapping("/add")
    public AjaxResult insertProjectBasicInfo(@Validated(ValidationGroups.Save.class) @RequestBody XmslProjectBasicInfo xmslProjectBasicInfoParam){
        projectBasicInfoService.insertProjectBasicInfo(xmslProjectBasicInfoParam);
        return AjaxResult.success(xmslProjectBasicInfoParam);
    }

    /**
     * 修改项目信息
     * @param xmslProjectBasicInfoParam
     * @return
     */
    @PostMapping("/update")
    public AjaxResult updateProjectBasicInfo(@Validated(ValidationGroups.Update.class) @RequestBody XmslProjectBasicInfo xmslProjectBasicInfoParam){
        int i = projectBasicInfoService.updateProjectBasicInfo(xmslProjectBasicInfoParam);
        return toAjax(i);
    }
    
    @PostMapping("/remove")
    public AjaxResult deleteProjectBasicInfo(@Validated(ValidationGroups.Delete.class) @RequestBody XmslProjectBasicInfo xmslProjectBasicInfoParam){
        return toAjax(projectBasicInfoService.deleteProjectBasicInfo(xmslProjectBasicInfoParam));
    }

    @PostMapping("/remove/{ids}")
    public AjaxResult deleteProjectBasicInfoByPks(@PathVariable Long[] ids){
        List<Long> projectBasicInfoPkList = Arrays.asList(ids);
        return toAjax(projectBasicInfoService.deleteProjectBasicInfoByPks(projectBasicInfoPkList));
    }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslProjectBasicInfo xmslProjectBasicInfoParam) throws IOException {
        List<XmslProjectBasicInfo> xmslProjectBasicInfoList = projectBasicInfoService.getProjectBasicInfoList(xmslProjectBasicInfoParam);
        ExcelUtils<XmslProjectBasicInfo> util = new ExcelUtils<>(XmslProjectBasicInfo.class);
        util.exportExcel(response, xmslProjectBasicInfoList, DateUtils.getDate());
    }
}
