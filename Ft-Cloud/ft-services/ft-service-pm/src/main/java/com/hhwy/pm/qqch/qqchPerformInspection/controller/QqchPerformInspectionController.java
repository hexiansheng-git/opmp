package com.hhwy.pm.qqch.qqchPerformInspection.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.common.FlowInfoSearchUtil;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspectionDetail;
import com.hhwy.pm.qqch.qqchPerformInspection.service.IQqchPerformInspectionService;
import com.hhwy.pm.xmsl.project.domain.vo.ProjectBasicInfo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBasicInfoService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zqq
 * @date 2023-08-17 10:58:09
 * @remark
 */
@Validated
@RestController
@RequestMapping("/qqchPerformInspection")
public class QqchPerformInspectionController extends BaseController {

    @Autowired
    private IQqchPerformInspectionService qqchPerformInspectionService;
    @Autowired
    private IXmslProjectBasicInfoService projectBasicInfoService;


//    @PreAuthorize(hasPermi = "qqchPerformInspection:list")
    @GetMapping
    public AjaxResult getQqchPerformInspection(@Validated(ValidationGroups.Get.class) QqchPerformInspection qqchPerformInspectionParam) {
        QqchPerformInspection qqchPerformInspection = qqchPerformInspectionService.getQqchPerformInspection(qqchPerformInspectionParam);
        return AjaxResult.success(qqchPerformInspection);
    }

//    @PreAuthorize(hasPermi = "qqchPerformInspection:list")
    @GetMapping("/list")
    public AjaxResult getQqchPerformInspectionList(@Validated(ValidationGroups.Select.class) QqchPerformInspection qqchPerformInspectionParam) {
        startPage();
        List<QqchPerformInspection> qqchPerformInspectionList = qqchPerformInspectionService.getQqchPerformInspectionList(qqchPerformInspectionParam);
        return getDataTableAjaxResult(qqchPerformInspectionList);
    }

//    @PreAuthorize(hasPermi = "qqchPerformInspection:save")
    @PostMapping("/add")
    public AjaxResult insertQqchPerformInspection(@Validated(ValidationGroups.Save.class) @RequestBody QqchPerformInspection qqchPerformInspectionParam) {
        try{
            return AjaxResult.success(qqchPerformInspectionService.insertQqchPerformInspection(qqchPerformInspectionParam));
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

//    @PreAuthorize(hasPermi = "qqchPerformInspection:save")
    @PostMapping("/batchAdd")
    public AjaxResult insertQqchPerformInspectionList(@Validated(ValidationGroups.Save.class) @RequestBody List<QqchPerformInspection> qqchPerformInspectionListParam) {
        qqchPerformInspectionService.insertQqchPerformInspectionList(qqchPerformInspectionListParam);
        return AjaxResult.success(qqchPerformInspectionListParam);
    }

//    @PreAuthorize(hasPermi = "qqchPerformInspection:save")
    @PostMapping("/update")
    public AjaxResult updateQqchPerformInspection(@Validated(ValidationGroups.Update.class) @RequestBody QqchPerformInspection qqchPerformInspectionParam) {
        try{
            qqchPerformInspectionService.updateQqchPerformInspection(qqchPerformInspectionParam);
            return AjaxResult.success();
        }catch (CustomBusinessException e){
            e.printStackTrace();
            return AjaxResult.error(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }

    }

//    @PreAuthorize(hasPermi = "qqchPerformInspection:save")
    @PostMapping("/batchUpdate")
    public AjaxResult updateQqchPerformInspectionList(@Validated(ValidationGroups.Update.class) @RequestBody List<QqchPerformInspection> qqchPerformInspectionListParam) {
        return toAjax(qqchPerformInspectionService.updateQqchPerformInspectionList(qqchPerformInspectionListParam));
    }

//    @PreAuthorize(hasPermi = "qqchPerformInspection:remove")
    @PostMapping("/delete")
    public AjaxResult deleteQqchPerformInspection(@Validated(ValidationGroups.Delete.class) @RequestBody QqchPerformInspection qqchPerformInspectionParam) {
        return toAjax(qqchPerformInspectionService.deleteQqchPerformInspection(qqchPerformInspectionParam));
    }

//    @PreAuthorize(hasPermi = "qqchPerformInspection:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteQqchPerformInspectionByPks(@PathVariable Long[] ids) {
        List<Long> qqchPerformInspectionPkList = Arrays.asList(ids);
        return toAjax(qqchPerformInspectionService.deleteQqchPerformInspectionByPks(qqchPerformInspectionPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchPerformInspection qqchPerformInspectionParam) throws IOException {
        List<QqchPerformInspection> qqchPerformInspectionList = qqchPerformInspectionService.getQqchPerformInspectionList(qqchPerformInspectionParam);
        ExcelUtils<QqchPerformInspection> util = new ExcelUtils<>(QqchPerformInspection.class);
        util.exportExcel(response, qqchPerformInspectionList, DateUtils.getDate());
    }

    /**
     * 获取策划项信息
     * @return
     */
    @GetMapping("/getChEditMenuList")
    public AjaxResult getChEditMenuList(){
        try{
            List<QqchPerformInspectionDetail> list  = qqchPerformInspectionService.getChEditMenuList();
            return AjaxResult.success(list);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 获取策划项信息
     * @return
     */
    @GetMapping("/baseInfoAdd")
    public AjaxResult baseInfoAdd(){
        try{
            List<QqchPerformInspectionDetail> list  = qqchPerformInspectionService.getChEditMenuList();
            QqchPerformInspection qqchPerformInspection = new QqchPerformInspection();
            qqchPerformInspection.setDetailList(list);
            FlowInfoSearchUtil.getFlowInfo(qqchPerformInspection, FlowEnum.QQCH_ZXJC);
            return AjaxResult.success(qqchPerformInspection);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }



    /**
     * 获取检查好主导单位
     * @return
     */
    @GetMapping("/getCheckUnitList")
    public AjaxResult getCheckUnitList(){
        ArrayList<String> checkList = new ArrayList<>();
        checkList.add("海外事业部");
        ProjectBasicInfo projectBasicInfo = projectBasicInfoService.projectInfo();
        String projectName = projectBasicInfo.getProjectName();
        String regionName = projectBasicInfo.getRegionName();
        checkList.add(regionName);
        checkList.add(projectName);
        return AjaxResult.success(checkList);
    }

    /**
     * 详情
     * @param param
     * @return
     */
    @GetMapping("/detail")
    public AjaxResult detail(@Validated(ValidationGroups.Other.class) QqchPerformInspection param){
        try{
            QqchPerformInspection qqchPerformInspection = qqchPerformInspectionService.detail(param.getId());
            return AjaxResult.success(qqchPerformInspection);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    
}
