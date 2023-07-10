package com.hhwy.pm.xmsl.contractInfo.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractGeneral;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractGeneralService;
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
 * @date 2023-07-10 14:17:30
 * @remark
 */
@Validated
@RestController
@RequestMapping("/xmslContractGeneral")
public class XmslContractGeneralController extends BaseController {

    @Autowired
    private IXmslContractGeneralService xmslContractGeneralService;


    @PreAuthorize(hasPermi = "xmslContractGeneral:list")
    @GetMapping
    public AjaxResult getXmslContractGeneral(@Validated(ValidationGroups.Get.class) @RequestBody XmslContractGeneral xmslContractGeneralParam) {
        XmslContractGeneral xmslContractGeneral = xmslContractGeneralService.getXmslContractGeneral(xmslContractGeneralParam);
        return AjaxResult.success(xmslContractGeneral);
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:list")
    @GetMapping("/list")
    public AjaxResult getXmslContractGeneralList(@Validated(ValidationGroups.Select.class) @RequestBody XmslContractGeneral xmslContractGeneralParam) {
        startPage();
        List<XmslContractGeneral> xmslContractGeneralList = xmslContractGeneralService.getXmslContractGeneralList(xmslContractGeneralParam);
        return getDataTableAjaxResult(xmslContractGeneralList);
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:add")
    @PostMapping("/add")
    public AjaxResult insertXmslContractGeneral(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractGeneral xmslContractGeneralParam) {
        xmslContractGeneralService.insertXmslContractGeneral(xmslContractGeneralParam);
        return AjaxResult.success(xmslContractGeneralParam);
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertXmslContractGeneralList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslContractGeneral> xmslContractGeneralListParam) {
        xmslContractGeneralService.insertXmslContractGeneralList(xmslContractGeneralListParam);
        return AjaxResult.success(xmslContractGeneralListParam);
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractGeneral(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractGeneral xmslContractGeneralParam) {
        return toAjax(xmslContractGeneralService.updateXmslContractGeneral(xmslContractGeneralParam));
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslContractGeneralList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslContractGeneral> xmslContractGeneralListParam) {
        return toAjax(xmslContractGeneralService.updateXmslContractGeneralList(xmslContractGeneralListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslContractGeneral(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractGeneral xmslContractGeneralParam) {
        return toAjax(xmslContractGeneralService.deleteXmslContractGeneral(xmslContractGeneralParam));
    }

    @PreAuthorize(hasPermi = "xmslContractGeneral:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteXmslContractGeneralByPks(@PathVariable Long[] ids) {
        List<Long> xmslContractGeneralPkList = Arrays.asList(ids);
        return toAjax(xmslContractGeneralService.deleteXmslContractGeneralByPks(xmslContractGeneralPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslContractGeneral xmslContractGeneralParam) throws IOException {
        List<XmslContractGeneral> xmslContractGeneralList = xmslContractGeneralService.getXmslContractGeneralList(xmslContractGeneralParam);
        ExcelUtils<XmslContractGeneral> util = new ExcelUtils<>(XmslContractGeneral.class);
        util.exportExcel(response, xmslContractGeneralList, DateUtils.getDate());
    }
}
