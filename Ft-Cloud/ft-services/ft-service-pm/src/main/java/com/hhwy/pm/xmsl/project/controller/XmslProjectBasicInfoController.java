package com.hhwy.pm.xmsl.project.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

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
        if(id == null){
            return AjaxResult.error("id不能为空！");
        }
        XmslProjectBasicInfo xmslProjectBasicInfo = projectBasicInfoService.getProjectBasicInfoById(id);
        return AjaxResult.success(xmslProjectBasicInfo);
    }

    @GetMapping
    public AjaxResult getProjectBasicInfo(@RequestBody XmslProjectBasicInfo xmslProjectBasicInfoParam){
        XmslProjectBasicInfo xmslProjectBasicInfo =  projectBasicInfoService.getProjectBasicInfo(xmslProjectBasicInfoParam);
        return AjaxResult.success(xmslProjectBasicInfo);
    }

    /**
     * 项目台账
     * @param xmslProjectBasicInfoParam
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getProjectBasicInfoList(@Validated(ValidationGroups.Select.class) @RequestBody XmslProjectBasicInfo xmslProjectBasicInfoParam){
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
