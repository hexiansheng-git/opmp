package com.hhwy.pm.xmsl.project.controller;

import java.util.Arrays;
import java.util.List;

import com.hhwy.pm.xmsl.project.domain.XmslProjectCulvertStructure;
import com.hhwy.pm.xmsl.project.service.IXmslProjectCulvertStructureService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author han
 * @date 2023-07-03 09:48:31
 * @remark 主要涵洞结构形式
 */
@RestController
@RequestMapping("/projectCulvertStructure")
public class XmslProjectCulvertStructureController extends BaseController{

    @Autowired
    private IXmslProjectCulvertStructureService projectCulvertStructureService;

                                                                                                                                                                                                                                                                                                                                        
//    @GetMapping
//    public AjaxResult getProjectCulvertStructure(ProjectCulvertStructure projectCulvertStructureParam){
//        ProjectCulvertStructure projectCulvertStructure =  projectCulvertStructureService.getProjectCulvertStructure(projectCulvertStructureParam);
//        return AjaxResult.success(projectCulvertStructure);
//    }
//
//    @GetMapping("/list")
//    public AjaxResult getProjectCulvertStructureList(ProjectCulvertStructure projectCulvertStructureParam){
//        startPage();
//        List<ProjectCulvertStructure> projectCulvertStructureList = projectCulvertStructureService.getProjectCulvertStructureList(projectCulvertStructureParam);
//        return getDataTableAjaxResult(projectCulvertStructureList);
//    }
//
//    @PostMapping
//    public AjaxResult insertProjectCulvertStructure(@RequestBody ProjectCulvertStructure projectCulvertStructureParam){
//        projectCulvertStructureService.insertProjectCulvertStructure(projectCulvertStructureParam);
//        return AjaxResult.success(projectCulvertStructureParam);
//    }
//
//    @PostMapping("/list")
//    public AjaxResult insertProjectCulvertStructureList(@RequestBody List<ProjectCulvertStructure> projectCulvertStructureListParam){
//        projectCulvertStructureService.insertProjectCulvertStructureList(projectCulvertStructureListParam);
//        return AjaxResult.success(projectCulvertStructureListParam);
//    }
//
//    @PutMapping
//    public AjaxResult updateProjectCulvertStructure(@RequestBody ProjectCulvertStructure projectCulvertStructureParam){
//        return toAjax(projectCulvertStructureService.updateProjectCulvertStructure(projectCulvertStructureParam));
//    }
//
//    @PutMapping("/list")
//    public AjaxResult updateProjectCulvertStructureList(@RequestBody List<ProjectCulvertStructure> projectCulvertStructureListParam){
//        return toAjax(projectCulvertStructureService.updateProjectCulvertStructureList(projectCulvertStructureListParam));
//    }
    
    @DeleteMapping
    public AjaxResult deleteProjectCulvertStructure(@RequestBody XmslProjectCulvertStructure xmslProjectCulvertStructureParam){
        return toAjax(projectCulvertStructureService.deleteProjectCulvertStructure(xmslProjectCulvertStructureParam));
    }

    @DeleteMapping("/{pks}")
    public AjaxResult deleteProjectCulvertStructureByPks(@PathVariable Long[] pks){
        List<Long> projectCulvertStructurePkList = Arrays.asList(pks);
        return toAjax(projectCulvertStructureService.deleteProjectCulvertStructureByPks(projectCulvertStructurePkList));
    }
    
//    @GetMapping("/export")
//    public void export(HttpServletResponse response, ProjectCulvertStructure projectCulvertStructureParam) throws IOException {
//        List<ProjectCulvertStructure> projectCulvertStructureList = projectCulvertStructureService.getProjectCulvertStructureList(projectCulvertStructureParam);
//        ExcelUtils<ProjectCulvertStructure> util = new ExcelUtils<>(ProjectCulvertStructure.class);
//        util.exportExcel(response, projectCulvertStructureList, DateUtils.getDate());
//    }
}
