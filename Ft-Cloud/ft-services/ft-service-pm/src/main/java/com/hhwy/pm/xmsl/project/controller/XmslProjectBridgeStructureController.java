package com.hhwy.pm.xmsl.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectBridgeStructure;
import com.hhwy.pm.xmsl.project.service.IXmslProjectBridgeStructureService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

/**
 * @author han
 * @date 2023-07-03 09:48:28
 * @remark 主要桥梁结构形式
 */
@RestController
@RequestMapping("/projectBridgeStructure")
public class XmslProjectBridgeStructureController extends BaseController{

    @Autowired
    private IXmslProjectBridgeStructureService projectBridgeStructureService;

    
    @PostMapping
    public AjaxResult deleteProjectBridgeStructure(@RequestBody XmslProjectBridgeStructure xmslProjectBridgeStructureParam){
        return toAjax(projectBridgeStructureService.deleteProjectBridgeStructure(xmslProjectBridgeStructureParam));
    }

    @PostMapping("/remove/{ids}")
    public AjaxResult deleteProjectBridgeStructureByPks(@PathVariable Long[] ids){
        List<Long> projectBridgeStructurePkList = Arrays.asList(ids);
        return toAjax(projectBridgeStructureService.deleteProjectBridgeStructureByPks(projectBridgeStructurePkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("import")
    public AjaxResult importProjectBridgeStructure(@RequestPart("file") MultipartFile file){
        ExcelUtils<XmslProjectBridgeStructure> util = new ExcelUtils<>(XmslProjectBridgeStructure.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList = util.importExcel(inputStream);
            return AjaxResult.success(xmslProjectBridgeStructureList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslProjectBridgeStructure xmslProjectBridgeStructureParam) throws IOException {
        List<XmslProjectBridgeStructure> xmslProjectBridgeStructureList = projectBridgeStructureService.getProjectBridgeStructureList(xmslProjectBridgeStructureParam);
        ExcelUtils<XmslProjectBridgeStructure> util = new ExcelUtils<>(XmslProjectBridgeStructure.class);
        util.exportExcel(response, xmslProjectBridgeStructureList, DateUtils.getDate());
    }
}
