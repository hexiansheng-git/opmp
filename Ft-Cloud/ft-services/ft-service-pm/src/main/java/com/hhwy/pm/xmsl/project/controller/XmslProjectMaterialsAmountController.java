package com.hhwy.pm.xmsl.project.controller;

import java.util.Arrays;
import java.util.List;

import com.hhwy.pm.xmsl.project.domain.XmslProjectMaterialsAmount;
import com.hhwy.pm.xmsl.project.service.IXmslProjectMaterialsAmountService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author han
 * @date 2023-07-03 09:48:36
 * @remark 主要材料数量
 */
@RestController
@RequestMapping("/projectMaterialsAmount")
public class XmslProjectMaterialsAmountController extends BaseController{

    @Autowired
    private IXmslProjectMaterialsAmountService projectMaterialsAmountService;

                                                                                                                                                                                                                                                                                                    
//    @GetMapping
//    public AjaxResult getProjectMaterialsAmount(ProjectMaterialsAmount projectMaterialsAmountParam){
//        ProjectMaterialsAmount projectMaterialsAmount =  projectMaterialsAmountService.getProjectMaterialsAmount(projectMaterialsAmountParam);
//        return AjaxResult.success(projectMaterialsAmount);
//    }
//
//    @GetMapping("/list")
//    public AjaxResult getProjectMaterialsAmountList(ProjectMaterialsAmount projectMaterialsAmountParam){
//        startPage();
//        List<ProjectMaterialsAmount> projectMaterialsAmountList = projectMaterialsAmountService.getProjectMaterialsAmountList(projectMaterialsAmountParam);
//        return getDataTableAjaxResult(projectMaterialsAmountList);
//    }
//
//    @PostMapping
//    public AjaxResult insertProjectMaterialsAmount(@RequestBody ProjectMaterialsAmount projectMaterialsAmountParam){
//        projectMaterialsAmountService.insertProjectMaterialsAmount(projectMaterialsAmountParam);
//        return AjaxResult.success(projectMaterialsAmountParam);
//    }
//
//    @PostMapping("/list")
//    public AjaxResult insertProjectMaterialsAmountList(@RequestBody List<ProjectMaterialsAmount> projectMaterialsAmountListParam){
//        projectMaterialsAmountService.insertProjectMaterialsAmountList(projectMaterialsAmountListParam);
//        return AjaxResult.success(projectMaterialsAmountListParam);
//    }
//
//    @PutMapping
//    public AjaxResult updateProjectMaterialsAmount(@RequestBody ProjectMaterialsAmount projectMaterialsAmountParam){
//        return toAjax(projectMaterialsAmountService.updateProjectMaterialsAmount(projectMaterialsAmountParam));
//    }
//
//    @PutMapping("/list")
//    public AjaxResult updateProjectMaterialsAmountList(@RequestBody List<ProjectMaterialsAmount> projectMaterialsAmountListParam){
//        return toAjax(projectMaterialsAmountService.updateProjectMaterialsAmountList(projectMaterialsAmountListParam));
//    }
    
    @DeleteMapping
    public AjaxResult deleteProjectMaterialsAmount(@RequestBody XmslProjectMaterialsAmount xmslProjectMaterialsAmountParam){
        return toAjax(projectMaterialsAmountService.deleteProjectMaterialsAmount(xmslProjectMaterialsAmountParam));
    }

    @DeleteMapping("/{pks}")
    public AjaxResult deleteProjectMaterialsAmountByPks(@PathVariable Long[] pks){
        List<Long> projectMaterialsAmountPkList = Arrays.asList(pks);
        return toAjax(projectMaterialsAmountService.deleteProjectMaterialsAmountByPks(projectMaterialsAmountPkList));
    }
    
//    @GetMapping("/export")
//    public void export(HttpServletResponse response, ProjectMaterialsAmount projectMaterialsAmountParam) throws IOException {
//        List<ProjectMaterialsAmount> projectMaterialsAmountList = projectMaterialsAmountService.getProjectMaterialsAmountList(projectMaterialsAmountParam);
//        ExcelUtils<ProjectMaterialsAmount> util = new ExcelUtils<>(ProjectMaterialsAmount.class);
//        util.exportExcel(response, projectMaterialsAmountList, DateUtils.getDate());
//    }
}
