package com.hhwy.pm.xmsl.contractInfo.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractInfo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractInfoService;
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
 * @date 2023-07-10 13:57:39
 * @remark  合同信息--主合同信息Controller
 */
@Validated
@RestController
@RequestMapping("/xmslContractInfo")
public class XmslContractInfoController extends BaseController {

    @Autowired
    private IXmslContractInfoService xmslContractInfoService;


    @PreAuthorize(hasPermi = "xmslContractInfo:list")
    @GetMapping
    public AjaxResult getXmslContractInfo(@Validated(ValidationGroups.Get.class) @RequestBody XmslContractInfo xmslContractInfoParam) {
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(xmslContractInfoParam);
        return AjaxResult.success(xmslContractInfo);
    }

    @PreAuthorize(hasPermi = "xmslContractInfo:list")
    @GetMapping("/list")
    public AjaxResult getXmslContractInfoList(@Validated(ValidationGroups.Select.class) @RequestBody XmslContractInfo xmslContractInfoParam) {
        startPage();
        List<XmslContractInfo> xmslContractInfoList = xmslContractInfoService.getXmslContractInfoList(xmslContractInfoParam);
        return getDataTableAjaxResult(xmslContractInfoList);
    }

    /**
     *  主合同信息新增
     *
     * @param xmslContractInfoParam
     * @return
     */
    @PreAuthorize(hasPermi = "xmslContractInfo:add")
    @PostMapping("/add")
    public AjaxResult insertXmslContractInfo(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractInfo xmslContractInfoParam) {
        xmslContractInfoService.insertXmslContractInfo(xmslContractInfoParam);
        return AjaxResult.success(xmslContractInfoParam);
    }


    @PreAuthorize(hasPermi = "xmslContractInfo:add")
    @PostMapping("/batchAdd")
    public AjaxResult insertXmslContractInfoList(@Validated(ValidationGroups.Save.class) @RequestBody List<XmslContractInfo> xmslContractInfoListParam) {
        xmslContractInfoService.insertXmslContractInfoList(xmslContractInfoListParam);
        return AjaxResult.success(xmslContractInfoListParam);
    }


    /**
     *  主合同信息修改
     *
     * @param xmslContractInfoParam
     * @return
     */
    @PreAuthorize(hasPermi = "xmslContractInfo:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractInfo(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractInfo xmslContractInfoParam) {
        return toAjax(xmslContractInfoService.updateXmslContractInfo(xmslContractInfoParam));
    }


    @PreAuthorize(hasPermi = "xmslContractInfo:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslContractInfoList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslContractInfo> xmslContractInfoListParam) {
        return toAjax(xmslContractInfoService.updateXmslContractInfoList(xmslContractInfoListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractInfo:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslContractInfo(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractInfo xmslContractInfoParam) {
        return toAjax(xmslContractInfoService.deleteXmslContractInfo(xmslContractInfoParam));
    }

    @PreAuthorize(hasPermi = "xmslContractInfo:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteXmslContractInfoByPks(@PathVariable Long[] ids) {
        List<Long> xmslContractInfoPkList = Arrays.asList(ids);
        return toAjax(xmslContractInfoService.deleteXmslContractInfoByPks(xmslContractInfoPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslContractInfo xmslContractInfoParam) throws IOException {
        List<XmslContractInfo> xmslContractInfoList = xmslContractInfoService.getXmslContractInfoList(xmslContractInfoParam);
        ExcelUtils<XmslContractInfo> util = new ExcelUtils<>(XmslContractInfo.class);
        util.exportExcel(response, xmslContractInfoList, DateUtils.getDate());
    }
}
