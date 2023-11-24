package com.hhwy.pm.xmsl.project.controller;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectInfoWithOther;
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
import java.util.Map;

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
    @GetMapping("getProjectBasicInfo")
    public AjaxResult getProjectBasicInfo(@Validated(ValidationGroups.Select.class) XmslProjectBasicInfo projectBasicInfo){
        XmslProjectBasicInfo xmslProjectBasicInfo = projectBasicInfoService.getProjectBasicInfo(projectBasicInfo);
        return AjaxResult.success(xmslProjectBasicInfo);
    }

    /**
     * 获取项目信息详情（不带子表）
     * @return
     */
    @GetMapping("projectInfo")
    public AjaxResult projectInfo(){
        ProjectBasicInfo projectInfo = projectBasicInfoService.projectInfo();
        return AjaxResult.success(projectInfo);
    }

    @GetMapping("getPrjInfo")
    public Map<String,Object> getPrjInfo(){
        return projectBasicInfoService.getPrjInfo();
    }
    /**
     * 获取项目基本信息（附带其他信息）
     * @return
     */
    @GetMapping("getProjectInfoWithOther")
    public AjaxResult getProjectInfoWithOther() {
        ProjectInfoWithOther projectInfoWithOther = projectBasicInfoService.getProjectInfoWithOther();
        return AjaxResult.success(projectInfoWithOther);
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
        projectBasicInfoService.updateProjectBasicInfo(xmslProjectBasicInfoParam);
        return AjaxResult.success("修改成功！");
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

    /**
     * 新增租户项目信息
     * @param xmslProjectBasicInfoParam
     * @return
     */
    @PostMapping("/addTenant")
    public AjaxResult insertProjectTenant(@RequestBody XmslProjectBasicInfo xmslProjectBasicInfoParam){
        String tenantKey = xmslProjectBasicInfoParam.getProjectCode();
        String dataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey);
        String oldDataSource = TenantDataSourceUtils.getDataSourceNameByTenantKey("master");
        if(StringUtils.isNotBlank(dataSource) && !dataSource.equals(oldDataSource)){
            DynamicDataSourceContextHolder.push(dataSource);
            try {
                projectBasicInfoService.insertProjectInvokeProject(xmslProjectBasicInfoParam);
                return AjaxResult.success();
            }finally {
                DynamicDataSourceContextHolder.poll();
                DynamicDataSourceContextHolder.push(oldDataSource);
            }
        }
        return  AjaxResult.error("数据源为空");
    }


}
