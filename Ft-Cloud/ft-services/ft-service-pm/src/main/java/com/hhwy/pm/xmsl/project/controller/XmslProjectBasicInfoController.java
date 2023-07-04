package com.hhwy.pm.xmsl.project.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
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
    public AjaxResult getProjectBasicInfoById(Long id){
        if(id == null){
            return AjaxResult.error("id不能为空！");
        }
        XmslProjectBasicInfo xmslProjectBasicInfo = projectBasicInfoService.getProjectBasicInfoById(id);
        return AjaxResult.success(xmslProjectBasicInfo);
    }

    @GetMapping
    public AjaxResult getProjectBasicInfo(XmslProjectBasicInfo xmslProjectBasicInfoParam){
        XmslProjectBasicInfo xmslProjectBasicInfo =  projectBasicInfoService.getProjectBasicInfo(xmslProjectBasicInfoParam);
        return AjaxResult.success(xmslProjectBasicInfo);
    }

    /**
     * 项目台账
     * @param xmslProjectBasicInfoParam
     * @return
     */
    @GetMapping("/list")
    public AjaxResult getProjectBasicInfoList(XmslProjectBasicInfo xmslProjectBasicInfoParam){
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
    public AjaxResult insertProjectBasicInfo(@RequestBody XmslProjectBasicInfo xmslProjectBasicInfoParam){
        projectBasicInfoService.insertProjectBasicInfo(xmslProjectBasicInfoParam);
        return AjaxResult.success(xmslProjectBasicInfoParam);
    }

    @PostMapping("/list")
    public AjaxResult insertProjectBasicInfoList(@RequestBody List<XmslProjectBasicInfo> xmslProjectBasicInfoListParam){
        projectBasicInfoService.insertProjectBasicInfoList(xmslProjectBasicInfoListParam);
        return AjaxResult.success(xmslProjectBasicInfoListParam);
    }

    /**
     * 修改项目信息
     * @param xmslProjectBasicInfoParam
     * @return
     */
    @PutMapping("/update")
    public AjaxResult updateProjectBasicInfo(@RequestBody XmslProjectBasicInfo xmslProjectBasicInfoParam){
        int i = projectBasicInfoService.updateProjectBasicInfo(xmslProjectBasicInfoParam);
        return toAjax(i);
    }

    @PutMapping("/list")
    public AjaxResult updateProjectBasicInfoList(@RequestBody List<XmslProjectBasicInfo> xmslProjectBasicInfoListParam){
        return toAjax(projectBasicInfoService.updateProjectBasicInfoList(xmslProjectBasicInfoListParam));
    }
    
    @DeleteMapping("/remove")
    public AjaxResult deleteProjectBasicInfo(@RequestBody XmslProjectBasicInfo xmslProjectBasicInfoParam){
        return toAjax(projectBasicInfoService.deleteProjectBasicInfo(xmslProjectBasicInfoParam));
    }

    @DeleteMapping("/remove/{pks}")
    public AjaxResult deleteProjectBasicInfoByPks(@PathVariable Long[] pks){
        List<Long> projectBasicInfoPkList = Arrays.asList(pks);
        return toAjax(projectBasicInfoService.deleteProjectBasicInfoByPks(projectBasicInfoPkList));
    }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslProjectBasicInfo xmslProjectBasicInfoParam) throws IOException {
        List<XmslProjectBasicInfo> xmslProjectBasicInfoList = projectBasicInfoService.getProjectBasicInfoList(xmslProjectBasicInfoParam);
        ExcelUtils<XmslProjectBasicInfo> util = new ExcelUtils<>(XmslProjectBasicInfo.class);
        util.exportExcel(response, xmslProjectBasicInfoList, DateUtils.getDate());
    }
}
