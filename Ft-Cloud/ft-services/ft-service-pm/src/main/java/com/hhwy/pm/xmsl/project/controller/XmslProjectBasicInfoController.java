package com.hhwy.pm.xmsl.project.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.domain.base.project.ProjectDto;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectInfoWithOther;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    @Value("${gm.back-url}")
    private String gmUrl;

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

    @GetMapping("getProjectDto")
    public ProjectDto getProjectDto() {
        return projectBasicInfoService.getProjectDto();
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
    @CustomLogger(title = "项目设立", name = "项目信息" ,businessType = CustomBusinessType.SAVE)
    public AjaxResult updateProjectBasicInfo(@Validated(ValidationGroups.Update.class) @RequestBody XmslProjectBasicInfo xmslProjectBasicInfoParam){
        try {
            String resStr = JSON.toJSONString(xmslProjectBasicInfoParam);
            projectBasicInfoService.updateProjectBasicInfo(xmslProjectBasicInfoParam);

            String res = HttpRequest.post(gmUrl + "/gm/projectBasicInfo/updateFromPm")
                    .header("Content-Type", "application/json")
                    .header(HttpHeadersUtils.getCommonHeaders())
                    .body(resStr).execute().body();
            JSONObject resObj = JSON.parseObject(res);
            if(!resObj.get("code").equals(200)){
                return AjaxResult.success("项目信息修改成功! 项目信息同步异常请联系管理员进行处理.");
            }
            return AjaxResult.success("修改成功！");
        }catch (Exception e){
            return AjaxResult.error("修改失败！");
        }

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

    /**
     * 获取区域中心
     * @return
     */
    @GetMapping("getHaiWai")
    public AjaxResult getHaiWai(){
        String url = gmUrl + "/system/selfSysDept/getHaiWai";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        return RestTemplateUtils.get(url, httpEntity, AjaxResult.class);
    }

    /**
     * 获取区域下的项目
     * @param regionId 区域中心id
     * @param type 1：海外事业部   2：区域中心
     * @return
     */
    @GetMapping("getPrjByRegionId")
    public AjaxResult getPrjByRegionId(Long regionId,String type){
        String url = gmUrl + "/gm/projectBasicInfo/getAllProject";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(headers);
        AjaxResult result = RestTemplateUtils.get(url, httpEntity, AjaxResult.class);
        List<XmslProjectBasicInfo> prjList = JSONObject.parseArray(JSONObject.toJSONString(result.get("data")), XmslProjectBasicInfo.class);
        if("2".equals(type)){
            prjList = prjList.stream().filter(o -> regionId.equals(o.getRegionId())).collect(Collectors.toList());
        }
        return AjaxResult.success(prjList);
    }
}

