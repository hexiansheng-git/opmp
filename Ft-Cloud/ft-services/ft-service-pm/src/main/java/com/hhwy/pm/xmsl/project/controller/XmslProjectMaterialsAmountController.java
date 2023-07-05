package com.hhwy.pm.xmsl.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectMaterialsAmount;
import com.hhwy.pm.xmsl.project.service.IXmslProjectMaterialsAmountService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

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

    
    @PostMapping
    public AjaxResult deleteProjectMaterialsAmount(@RequestBody XmslProjectMaterialsAmount xmslProjectMaterialsAmountParam){
        return toAjax(projectMaterialsAmountService.deleteProjectMaterialsAmount(xmslProjectMaterialsAmountParam));
    }

    @PostMapping("/remove/{ids}")
    public AjaxResult deleteProjectMaterialsAmountByPks(@PathVariable Long[] ids){
        List<Long> projectMaterialsAmountPkList = Arrays.asList(ids);
        return toAjax(projectMaterialsAmountService.deleteProjectMaterialsAmountByPks(projectMaterialsAmountPkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("import")
    public AjaxResult importProjectMaterialsAmount(@RequestPart("file") MultipartFile file){
        ExcelUtils<XmslProjectMaterialsAmount> util = new ExcelUtils<>(XmslProjectMaterialsAmount.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslProjectMaterialsAmount> xmslProjectMaterialsAmountList = util.importExcel(inputStream);
            return AjaxResult.success(xmslProjectMaterialsAmountList);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
    
    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslProjectMaterialsAmount projectMaterialsAmountParam) throws IOException {
        List<XmslProjectMaterialsAmount> projectMaterialsAmountList = projectMaterialsAmountService.getProjectMaterialsAmountList(projectMaterialsAmountParam);
        ExcelUtils<XmslProjectMaterialsAmount> util = new ExcelUtils<>(XmslProjectMaterialsAmount.class);
        util.exportExcel(response, projectMaterialsAmountList, DateUtils.getDate());
    }
}
