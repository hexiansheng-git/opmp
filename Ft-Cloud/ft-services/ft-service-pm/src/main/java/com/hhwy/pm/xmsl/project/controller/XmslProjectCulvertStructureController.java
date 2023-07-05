package com.hhwy.pm.xmsl.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectCulvertStructure;
import com.hhwy.pm.xmsl.project.service.IXmslProjectCulvertStructureService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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

    
    @PostMapping
    public AjaxResult deleteProjectCulvertStructure(@RequestBody XmslProjectCulvertStructure xmslProjectCulvertStructureParam){
        return toAjax(projectCulvertStructureService.deleteProjectCulvertStructure(xmslProjectCulvertStructureParam));
    }

    @PostMapping("/remove/{ids}")
    public AjaxResult deleteProjectCulvertStructureByPks(@PathVariable Long[] ids){
        List<Long> projectCulvertStructurePkList = Arrays.asList(ids);
        return toAjax(projectCulvertStructureService.deleteProjectCulvertStructureByPks(projectCulvertStructurePkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("import")
    public AjaxResult importProjectCulvertStructure(@RequestPart("file") MultipartFile file){
        ExcelUtils<XmslProjectCulvertStructure> util = new ExcelUtils<>(XmslProjectCulvertStructure.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslProjectCulvertStructure> xmslProjectCulvertStructureList = util.importExcel(inputStream);
            return AjaxResult.success(xmslProjectCulvertStructureList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslProjectCulvertStructure projectCulvertStructureParam) throws IOException {
        List<XmslProjectCulvertStructure> projectCulvertStructureList = projectCulvertStructureService.getProjectCulvertStructureList(projectCulvertStructureParam);
        ExcelUtils<XmslProjectCulvertStructure> util = new ExcelUtils<>(XmslProjectCulvertStructure.class);
        util.exportExcel(response, projectCulvertStructureList, DateUtils.getDate());
    }
}
