package com.hhwy.pm.xmsl.contractInfo.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.annotation.PreAuthorize;
import com.hhwy.excel.Util;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractPayinfo;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.XmslContractPayinfoVo;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractPayinfoService;
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
 * @date 2023-07-10 14:17:46
 * @remark  合同信息--项目支付信息
 */
@Validated
@RestController
@RequestMapping("/xmslContractPayinfo")
public class XmslContractPayinfoController extends BaseController {

    @Autowired
    private IXmslContractPayinfoService xmslContractPayinfoService;
    @Autowired
    private SystemServiceApi systemServiceApi;

    private static final  String type="rate_type";


    @PreAuthorize(hasPermi = "xmslContractPayinfo:list")
    @GetMapping
    public AjaxResult getXmslContractPayinfo(@Validated(ValidationGroups.Get.class) @RequestBody XmslContractPayinfo xmslContractPayinfoParam) {
        XmslContractPayinfo xmslContractPayinfo = xmslContractPayinfoService.getXmslContractPayinfo(xmslContractPayinfoParam);
        return AjaxResult.success(xmslContractPayinfo);
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:list")
    @GetMapping("/list")
    public AjaxResult getXmslContractPayinfoList(@Validated(ValidationGroups.Select.class) @RequestBody XmslContractPayinfo xmslContractPayinfoParam) {
        startPage();
        List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractPayinfoService.getXmslContractPayinfoList(xmslContractPayinfoParam);
        return getDataTableAjaxResult(xmslContractPayinfoList);
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:add")
    @PostMapping("/add")
    public AjaxResult insertXmslContractPayinfo(@Validated(ValidationGroups.Save.class) @RequestBody XmslContractPayinfo xmslContractPayinfoParam) {
        xmslContractPayinfoService.insertXmslContractPayinfo(xmslContractPayinfoParam);
        return AjaxResult.success(xmslContractPayinfoParam);
    }


    @PreAuthorize(hasPermi = "xmslContractPayinfo:update")
    @PostMapping("/update")
    public AjaxResult updateXmslContractPayinfo(@Validated(ValidationGroups.Update.class) @RequestBody XmslContractPayinfo xmslContractPayinfoParam) {
        return toAjax(xmslContractPayinfoService.updateXmslContractPayinfo(xmslContractPayinfoParam));
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:update")
    @PostMapping("/batchUpdate")
    public AjaxResult updateXmslContractPayinfoList(@Validated(ValidationGroups.Update.class) @RequestBody List<XmslContractPayinfo> xmslContractPayinfoListParam) {
        return toAjax(xmslContractPayinfoService.updateXmslContractPayinfoList(xmslContractPayinfoListParam));
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:remove")
    @PostMapping("/delete")
    public AjaxResult deleteXmslContractPayinfo(@Validated(ValidationGroups.Delete.class) @RequestBody XmslContractPayinfo xmslContractPayinfoParam) {
        return toAjax(xmslContractPayinfoService.deleteXmslContractPayinfo(xmslContractPayinfoParam));
    }

    @PreAuthorize(hasPermi = "xmslContractPayinfo:remove")
    @PostMapping("/{ids}")
    public AjaxResult deleteXmslContractPayinfoByPks(@PathVariable Long[] ids) {
        List<Long> xmslContractPayinfoPkList = Arrays.asList(ids);
        return toAjax(xmslContractPayinfoService.deleteXmslContractPayinfoByPks(xmslContractPayinfoPkList));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslContractPayinfo xmslContractPayinfoParam) throws IOException {
        List<XmslContractPayinfo> xmslContractPayinfoList = xmslContractPayinfoService.getXmslContractPayinfoList(xmslContractPayinfoParam);
        ExcelUtils<XmslContractPayinfo> util = new ExcelUtils<>(XmslContractPayinfo.class);
        util.exportExcel(response, xmslContractPayinfoList, DateUtils.getDate());
    }

    /**
     *   项目支付信息导入
     *
     */
    @PostMapping("import")
    public AjaxResult importFile(@RequestParam("file") MultipartFile file ) {
        try {
            ExcelUtils<XmslContractPayinfoVo> util = new ExcelUtils<>(XmslContractPayinfoVo.class);
            List<XmslContractPayinfoVo> xmslContractPayinfoVos = util.importExcel(file.getInputStream());
            //字典项处理
            for (XmslContractPayinfoVo xmslContractPayinfoVo : xmslContractPayinfoVos) {
                String rateType = xmslContractPayinfoVo.getRateType();
                Util util1 = new Util();
                String value = util1.reverseDict("rate_type", rateType);
                xmslContractPayinfoVo.setRateType(value);
            }
            return AjaxResult.success(xmslContractPayinfoVos);
        }catch (Exception e){
            throw new RuntimeException("导入失败！");
        }
    }

}
