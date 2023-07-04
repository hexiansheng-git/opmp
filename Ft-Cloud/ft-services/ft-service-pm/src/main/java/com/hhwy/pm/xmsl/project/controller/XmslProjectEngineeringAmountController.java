package com.hhwy.pm.xmsl.project.controller;

import java.util.Arrays;
import java.util.List;

import com.hhwy.pm.xmsl.project.domain.XmslProjectEngineeringAmount;
import com.hhwy.pm.xmsl.project.service.IXmslProjectEngineeringAmountService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author han
 * @date 2023-07-03 09:48:34
 * @remark 主要工程数量
 */
@RestController
@RequestMapping("/projectEngineeringAmount")
public class XmslProjectEngineeringAmountController extends BaseController{

    @Autowired
    private IXmslProjectEngineeringAmountService projectEngineeringAmountService;

                                                                                                                                                                                                                                                                                                                
//    @GetMapping
//    public AjaxResult getProjectEngineeringAmount(ProjectEngineeringAmount projectEngineeringAmountParam){
//        ProjectEngineeringAmount projectEngineeringAmount =  projectEngineeringAmountService.getProjectEngineeringAmount(projectEngineeringAmountParam);
//        return AjaxResult.success(projectEngineeringAmount);
//    }
//
//    @GetMapping("/list")
//    public AjaxResult getProjectEngineeringAmountList(ProjectEngineeringAmount projectEngineeringAmountParam){
//        startPage();
//        List<ProjectEngineeringAmount> projectEngineeringAmountList = projectEngineeringAmountService.getProjectEngineeringAmountList(projectEngineeringAmountParam);
//        return getDataTableAjaxResult(projectEngineeringAmountList);
//    }
//
//    @PostMapping
//    public AjaxResult insertProjectEngineeringAmount(@RequestBody ProjectEngineeringAmount projectEngineeringAmountParam){
//        projectEngineeringAmountService.insertProjectEngineeringAmount(projectEngineeringAmountParam);
//        return AjaxResult.success(projectEngineeringAmountParam);
//    }
//
//    @PostMapping("/list")
//    public AjaxResult insertProjectEngineeringAmountList(@RequestBody List<ProjectEngineeringAmount> projectEngineeringAmountListParam){
//        projectEngineeringAmountService.insertProjectEngineeringAmountList(projectEngineeringAmountListParam);
//        return AjaxResult.success(projectEngineeringAmountListParam);
//    }
//
//    @PutMapping
//    public AjaxResult updateProjectEngineeringAmount(@RequestBody ProjectEngineeringAmount projectEngineeringAmountParam){
//        return toAjax(projectEngineeringAmountService.updateProjectEngineeringAmount(projectEngineeringAmountParam));
//    }
//
//    @PutMapping("/list")
//    public AjaxResult updateProjectEngineeringAmountList(@RequestBody List<ProjectEngineeringAmount> projectEngineeringAmountListParam){
//        return toAjax(projectEngineeringAmountService.updateProjectEngineeringAmountList(projectEngineeringAmountListParam));
//    }
    
    @DeleteMapping
    public AjaxResult deleteProjectEngineeringAmount(@RequestBody XmslProjectEngineeringAmount xmslProjectEngineeringAmountParam){
        return toAjax(projectEngineeringAmountService.deleteProjectEngineeringAmount(xmslProjectEngineeringAmountParam));
    }

    @DeleteMapping("/{pks}")
    public AjaxResult deleteProjectEngineeringAmountByPks(@PathVariable Long[] pks){
        List<Long> projectEngineeringAmountPkList = Arrays.asList(pks);
        return toAjax(projectEngineeringAmountService.deleteProjectEngineeringAmountByPks(projectEngineeringAmountPkList));
    }
    
//    @GetMapping("/export")
//    public void export(HttpServletResponse response, ProjectEngineeringAmount projectEngineeringAmountParam) throws IOException {
//        List<ProjectEngineeringAmount> projectEngineeringAmountList = projectEngineeringAmountService.getProjectEngineeringAmountList(projectEngineeringAmountParam);
//        ExcelUtils<ProjectEngineeringAmount> util = new ExcelUtils<>(ProjectEngineeringAmount.class);
//        util.exportExcel(response, projectEngineeringAmountList, DateUtils.getDate());
//    }
}
