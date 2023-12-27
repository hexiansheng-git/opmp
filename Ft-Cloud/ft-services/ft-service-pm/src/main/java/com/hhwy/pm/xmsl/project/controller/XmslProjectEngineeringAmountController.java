package com.hhwy.pm.xmsl.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.pm.xmsl.project.domain.XmslProjectEngineeringAmount;
import com.hhwy.pm.xmsl.project.domain.vo.XmslProjectEngineeringAmountExportVo;
import com.hhwy.pm.xmsl.project.domain.vo.XmslProjectEngineeringAmountImportVo;
import com.hhwy.pm.xmsl.project.service.IXmslProjectEngineeringAmountService;
import com.hhwy.utils.excel.FtExcelUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

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

    
    @PostMapping
    public AjaxResult deleteProjectEngineeringAmount(@RequestBody XmslProjectEngineeringAmount xmslProjectEngineeringAmountParam){
        return toAjax(projectEngineeringAmountService.deleteProjectEngineeringAmount(xmslProjectEngineeringAmountParam));
    }

    @PostMapping("/remove/{ids}")
    public AjaxResult deleteProjectEngineeringAmountByPks(@PathVariable Long[] ids){
        List<Long> projectEngineeringAmountPkList = Arrays.asList(ids);
        return toAjax(projectEngineeringAmountService.deleteProjectEngineeringAmountByPks(projectEngineeringAmountPkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
//    @PostMapping("import")
//    public AjaxResult importProjectEngineeringAmount(@RequestPart("file") MultipartFile file){
//        ExcelUtils<XmslProjectEngineeringAmountImportVo> util = new ExcelUtils<>(XmslProjectEngineeringAmountImportVo.class);
//        try {
//            InputStream inputStream = file.getInputStream();
//            List<XmslProjectEngineeringAmountImportVo> xmslProjectEngineeringAmountImportVoList = util.importExcel(inputStream);
//            for (XmslProjectEngineeringAmountImportVo xmslProjectEngineeringAmountImportVo : xmslProjectEngineeringAmountImportVoList) {
//                xmslProjectEngineeringAmountImportVo.setTreeId(IdWorker.createId());
//            }
//            for (XmslProjectEngineeringAmountImportVo projectEngineeringAmountImportVo1 : xmslProjectEngineeringAmountImportVoList) {
//                String parentInnerCode = projectEngineeringAmountImportVo1.getParentInnerCode();
//                if(StringUtils.isNotBlank(parentInnerCode)){
//                    for (XmslProjectEngineeringAmountImportVo projectEngineeringAmountImportVo2 : xmslProjectEngineeringAmountImportVoList) {
//                        if(parentInnerCode.equals(projectEngineeringAmountImportVo2.getInnerCode())){
//                            projectEngineeringAmountImportVo1.setParentTreeId(projectEngineeringAmountImportVo2.getTreeId());
//                            break;
//                        }
//                    }
//                }
//            }
//            ArrayList<XmslProjectEngineeringAmount> resultList = new ArrayList<>();
//            for (XmslProjectEngineeringAmountImportVo projectEngineeringAmountImportVo : xmslProjectEngineeringAmountImportVoList) {
//                XmslProjectEngineeringAmount xmslProjectEngineeringAmount = new XmslProjectEngineeringAmount();
//                BeanUtils.copyProperties(projectEngineeringAmountImportVo,xmslProjectEngineeringAmount);
//                resultList.add(xmslProjectEngineeringAmount);
//            }
//            return AjaxResult.success(resultList);
//        } catch (Exception e) {
//            throw new RuntimeException("导入失败！");
//        }
//    }
    @PostMapping("/import")
    public AjaxResult importData(@RequestPart("file") MultipartFile file) throws Exception {
        FtExcelUtil<XmslProjectEngineeringAmountImportVo> excelUtil = new FtExcelUtil<>(XmslProjectEngineeringAmountImportVo.class);
        List<XmslProjectEngineeringAmountImportVo> list = excelUtil.importTreeExcelxx(file.getInputStream(),XmslProjectEngineeringAmountImportVo::getChildren);
        projectEngineeringAmountService.checkoutImportData(list);
        ListTreeUtil.preserveIdPid(list,XmslProjectEngineeringAmountImportVo::setId,XmslProjectEngineeringAmountImportVo::setPid,XmslProjectEngineeringAmountImportVo::getChildren);
        return AjaxResult.success(list);
    }
    /**
     * 导出
     * @param response
     * @param projectEngineeringAmountParam
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response,@Validated(ValidationGroups.Select.class)  XmslProjectEngineeringAmount projectEngineeringAmountParam) throws IOException {
        List<XmslProjectEngineeringAmountExportVo> projectEngineeringAmountExportVoList = projectEngineeringAmountService.getProjectEngineeringAmountExportVoList(projectEngineeringAmountParam);
        ExcelUtils<XmslProjectEngineeringAmountExportVo> util = new ExcelUtils<>(XmslProjectEngineeringAmountExportVo.class);
        util.exportExcel(response, projectEngineeringAmountExportVoList, DateUtils.getDate());
    }
}
