package com.hhwy.pm.xmsl.contractInfo.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInsure;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInsureService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-10 14:17:36
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslContractInsure")
public class XmslContractInsureController extends BaseController {

    @Autowired
    private IXmslContractInsureService xmslContractInsureService;


    @PreAuthorize(hasPermi = "xmslContractInsure:list")
    @GetMapping
    public AjaxResult getXmslContractInsure(@Validated(ValidationGroups.Get.class) @RequestBody XmslContractInsure xmslContractInsureParam) {
        XmslContractInsure xmslContractInsure = xmslContractInsureService.getXmslContractInsure(xmslContractInsureParam);
        return AjaxResult.success(xmslContractInsure);
    }

    @PreAuthorize(hasPermi = "xmslContractInsure:list")
    @GetMapping("/list")
    public AjaxResult getXmslContractInsureList(@Validated(ValidationGroups.Select.class) @RequestBody XmslContractInsure xmslContractInsureParam) {
        startPage();
        List<XmslContractInsure> xmslContractInsureList = xmslContractInsureService.getXmslContractInsureList(xmslContractInsureParam);
        return getDataTableAjaxResult(xmslContractInsureList);
    }

    @PreAuthorize(hasPermi = "xmslContractInsure:add")
    @PostMapping("/add")
    public AjaxResult insertXmslContractInsure(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractInsure xmslContractInsureParam) {
        xmslContractInsureService.insertXmslContractInsure(xmslContractInsureParam);
        return AjaxResult.success(xmslContractInsureParam);
    }

    @PreAuthorize(hasPermi = "xmslContractInsure:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertXmslContractInsureList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslContractInsure> xmslContractInsureListParam) {
        xmslContractInsureService.insertXmslContractInsureList(xmslContractInsureListParam);
        return AjaxResult.success(xmslContractInsureListParam);
    }

    @PreAuthorize(hasPermi = "xmslContractInsure:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractInsure(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractInsure xmslContractInsureParam) {
        return toAjax(xmslContractInsureService.updateXmslContractInsure(xmslContractInsureParam));
    }

    @PreAuthorize(hasPermi = "xmslContractInsure:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslContractInsureList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslContractInsure> xmslContractInsureListParam) {
        return toAjax(xmslContractInsureService.updateXmslContractInsureList(xmslContractInsureListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractInsure:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslContractInsure(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractInsure xmslContractInsureParam) {
        return toAjax(xmslContractInsureService.deleteXmslContractInsure(xmslContractInsureParam));
    }

    @PreAuthorize(hasPermi = "xmslContractInsure:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteXmslContractInsureByPks(@PathVariable Long[] ids) {
        List<Long> xmslContractInsurePkList = Arrays.asList(ids);
        return toAjax(xmslContractInsureService.deleteXmslContractInsureByPks(xmslContractInsurePkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslContractInsure xmslContractInsureParam) throws IOException {
        List<XmslContractInsure> xmslContractInsureList = xmslContractInsureService.getXmslContractInsureList(xmslContractInsureParam);
        ExcelUtils<XmslContractInsure> util = new ExcelUtils<>(XmslContractInsure.class);
        util.exportExcel(response, xmslContractInsureList, DateUtils.getDate());
    }
}
