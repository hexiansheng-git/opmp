package com.hhwy.pm.xmsl.contractInfo.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractSign;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractSignVo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractSignService;
import com.hhwy.utils.customLog.CustomBusinessType;
import com.hhwy.utils.customLog.CustomLogger;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:49
 * @remark  合同信息--签订信息
 */
@Validated
@RestController
@RequestMapping("/xmslContractSign")
public class XmslContractSignController extends BaseController {

    @Autowired
    private IXmslContractSignService xmslContractSignService;


    @PreAuthorize(hasPermi = "xmslContractSign:list")
    @GetMapping
    @CustomLogger(title = "项目设立-合同信息-主合同信息", name = "签订信息", businessType = CustomBusinessType.SELECT)
    public AjaxResult getXmslContractSign(@Validated(ValidationGroups.Get.class)  XmslContractSign xmslContractSignParam) {
        XmslContractSign xmslContractSign = xmslContractSignService.getXmslContractSign(xmslContractSignParam);
        return AjaxResult.success(xmslContractSign);
    }

    @PreAuthorize(hasPermi = "xmslContractSign:list")
    @GetMapping("/list")
    @CustomLogger(title = "项目设立-合同信息-主合同信息", name = "签订信息", businessType = CustomBusinessType.SELECT)
    public AjaxResult getXmslContractSignList(@Validated(ValidationGroups.Select.class)  XmslContractSign xmslContractSignParam) {
        startPage();
        List<XmslContractSign> xmslContractSignList = xmslContractSignService.getXmslContractSignList(xmslContractSignParam);
        return getDataTableAjaxResult(xmslContractSignList);
    }

    @PreAuthorize(hasPermi = "xmslContractSign:add")
    @PostMapping("/add")
    @CustomLogger(title = "项目设立-合同信息-主合同信息", name = "签订信息", businessType = CustomBusinessType.SAVE)
    public AjaxResult insertXmslContractSign(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractSign xmslContractSignParam) {
        xmslContractSignService.insertXmslContractSign(xmslContractSignParam);
        return AjaxResult.success(xmslContractSignParam);
    }


    @PreAuthorize(hasPermi = "xmslContractSign:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractSign(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractSign xmslContractSignParam) {
        return toAjax(xmslContractSignService.updateXmslContractSign(xmslContractSignParam));
    }

    @PreAuthorize(hasPermi = "xmslContractSign:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslContractSignList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslContractSign> xmslContractSignListParam) {
        return toAjax(xmslContractSignService.updateXmslContractSignList(xmslContractSignListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractSign:remove")
    @PostMapping("/delete")
    @CustomLogger(title = "项目设立-合同信息-主合同信息", name = "签订信息", businessType = CustomBusinessType.DELETE)
    public AjaxResult deleteXmslContractSign(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractSign xmslContractSignParam) {
        return toAjax(xmslContractSignService.deleteXmslContractSign(xmslContractSignParam));
    }

    @PreAuthorize(hasPermi = "xmslContractSign:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteXmslContractSignByPks(@PathVariable Long[] ids) {
        List<Long> xmslContractSignPkList = Arrays.asList(ids);
        return toAjax(xmslContractSignService.deleteXmslContractSignByPks(xmslContractSignPkList));
    }

    @GetMapping("/export")
    @CustomLogger(title = "项目设立-合同信息-主合同信息", name = "签订信息", businessType = CustomBusinessType.EXPORT)
    public void export(HttpServletResponse response, XmslContractSign xmslContractSignParam) throws IOException {
        List<XmslContractSign> xmslContractSignList = xmslContractSignService.getXmslContractSignList(xmslContractSignParam);
        ExcelUtils<XmslContractSign> util = new ExcelUtils<>(XmslContractSign.class);
        util.exportExcel(response, xmslContractSignList, DateUtils.getDate());
    }

    /**
     *   签订信息导入
     *
     */
    @PostMapping("import")
    @CustomLogger(title = "项目设立-合同信息-主合同信息", name = "签订信息", businessType = CustomBusinessType.IMPORT)
    public AjaxResult importFile(@RequestParam("file") MultipartFile file ) {
        try {
            ExcelUtils<XmslContractSignVo> util = new ExcelUtils<>(XmslContractSignVo.class);
            List<XmslContractSignVo> xmslContractSignVos = util.importExcel(file.getInputStream());
            return AjaxResult.success(xmslContractSignVos);
        }catch (Exception e){
            throw new RuntimeException("导入失败！");
        }
    }
}
