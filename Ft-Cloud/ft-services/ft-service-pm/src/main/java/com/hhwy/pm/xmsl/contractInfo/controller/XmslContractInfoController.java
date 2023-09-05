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
import org.springframework.transaction.annotation.Transactional;
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


//    @PreAuthorize(hasPermi = "xmslContractInfo:list")
    @GetMapping
    public AjaxResult getXmslContractInfo(@Validated(ValidationGroups.Get.class)  XmslContractInfo xmslContractInfoParam) {
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(xmslContractInfoParam);
        return AjaxResult.success(xmslContractInfo);
    }

    @PreAuthorize(hasPermi = "xmslContractInfo:list")
    @GetMapping("/list")
    public AjaxResult getXmslContractInfoList(@Validated(ValidationGroups.Select.class)  XmslContractInfo xmslContractInfoParam) {
        startPage();
        List<XmslContractInfo> xmslContractInfoList = xmslContractInfoService.getXmslContractInfoList(xmslContractInfoParam);
        return getDataTableAjaxResult(xmslContractInfoList);
    }

    /**
     *  主合同信息调整
     */
    @PreAuthorize(hasPermi = "xmslContractInfo:adjust")
    @GetMapping("/adjust")
    public AjaxResult adjustXmslContractInfo(@Validated(ValidationGroups.Select.class) XmslContractInfo xmslContractInfoParam) {
        XmslContractInfo xmslContractInfo = xmslContractInfoService.adjustXmslContractInfo(xmslContractInfoParam);
        return AjaxResult.success(xmslContractInfo);
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
        return AjaxResult.success(xmslContractInfoService.insertXmslContractInfo(xmslContractInfoParam));
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
        xmslContractInfoService.updateXmslContractInfo(xmslContractInfoParam);
        return AjaxResult.success(xmslContractInfoParam.getId());
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

    @PostMapping("/export")
    public void export(HttpServletResponse response, XmslContractInfo xmslContractInfoParam) throws IOException {
        List<XmslContractInfo> xmslContractInfoList = xmslContractInfoService.getXmslContractInfoList(xmslContractInfoParam);
        ExcelUtils<XmslContractInfo> util = new ExcelUtils<>(XmslContractInfo.class);
        util.exportExcel(response, xmslContractInfoList, DateUtils.getDate());
    }

    /**
     *  主合同信息修改
     *
     * @param id 主键
     * @return  监听器
     */
    @RequestMapping(value ="/listener",method = RequestMethod.POST)
    @Transactional
    public AjaxResult updateContract(@RequestParam ("id") Long id) {
        //修改其它合同信息为无效
        xmslContractInfoService.updateAllToInvalid();

        XmslContractInfo xmslContractInfo1 = new XmslContractInfo();
        xmslContractInfo1.setId(id);
        XmslContractInfo xmslContractInfo = xmslContractInfoService.getXmslContractInfo(xmslContractInfo1);
        xmslContractInfo.setTaskStatus("5");//流程结束
        xmslContractInfo.setValid("1"); //版本生效
        xmslContractInfoService.updateXmslContractInfo1(xmslContractInfo);
        return AjaxResult.success();
    }

}
