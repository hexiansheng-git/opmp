package com.hhwy.pm.xmsl.bid.controller;

import com.hhwy.pm.xmsl.bid.domain.XmslBidWinHandoverInfo;
import com.hhwy.pm.xmsl.bid.service.IXmslBidWinHandoverInfoService;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-06 15:13:41
 * @remark 中标项目移交信息
 */
@Validated
@RestController
@RequestMapping("/xmslBidWinHandoverInfo")
public class XmslBidWinHandoverInfoController extends BaseController {

    @Autowired
    private IXmslBidWinHandoverInfoService xmslBidWinHandoverInfoService;

    /**
     * 查询列表
     *
     * @param
     * @return
     */
    @GetMapping("/getList")
    public AjaxResult getXmslBidWinHandoverInfo() {
        XmslBidWinHandoverInfo xmslBidWinHandoverInfo = xmslBidWinHandoverInfoService.getXmslBidWinHandoverInfo();
        return AjaxResult.success(xmslBidWinHandoverInfo);
    }
//
//    @GetMapping("/list")
//    public AjaxResult getXmslBidWinHandoverInfoList(
//        @Validated(ValidationGroups.Select.class) @RequestBody XmslBidWinHandoverInfo xmslBidWinHandoverInfoParam) {
//        startPage();
//        List<XmslBidWinHandoverInfo> xmslBidWinHandoverInfoList = xmslBidWinHandoverInfoService
//            .getXmslBidWinHandoverInfoList(xmslBidWinHandoverInfoParam);
//        return getDataTableAjaxResult(xmslBidWinHandoverInfoList);
//    }

    /**
     * 编辑保存
     * @param xmslBidWinHandoverInfoParam
     * @return
     */
    @PostMapping("/save")
    public AjaxResult save(
        @Validated(ValidationGroups.Save.class) @RequestBody XmslBidWinHandoverInfo xmslBidWinHandoverInfoParam) {
        xmslBidWinHandoverInfoService.save(xmslBidWinHandoverInfoParam);
        return AjaxResult.success();
    }

//    @PostMapping("/batchAdd")
//    public AjaxResult insertXmslBidWinHandoverInfoList(
//        @Validated(ValidationGroups.Save.class) @RequestBody List<XmslBidWinHandoverInfo> xmslBidWinHandoverInfoListParam) {
//        xmslBidWinHandoverInfoService.insertXmslBidWinHandoverInfoList(xmslBidWinHandoverInfoListParam);
//        return AjaxResult.success(xmslBidWinHandoverInfoListParam);
//    }
//
//    @PostMapping("/update")
//    public AjaxResult updateXmslBidWinHandoverInfo(
//        @Validated(ValidationGroups.Update.class) @RequestBody XmslBidWinHandoverInfo xmslBidWinHandoverInfoParam) {
//        return toAjax(xmslBidWinHandoverInfoService.updateXmslBidWinHandoverInfo(xmslBidWinHandoverInfoParam));
//    }
//
//    @PostMapping("/batchUpdate")
//    public AjaxResult updateXmslBidWinHandoverInfoList(
//        @Validated(ValidationGroups.Update.class) @RequestBody List<XmslBidWinHandoverInfo> xmslBidWinHandoverInfoListParam) {
//        return toAjax(xmslBidWinHandoverInfoService.updateXmslBidWinHandoverInfoList(xmslBidWinHandoverInfoListParam));
//    }
//
//    @PostMapping("/delete")
//    public AjaxResult deleteXmslBidWinHandoverInfo(
//        @Validated(ValidationGroups.Delete.class) @RequestBody XmslBidWinHandoverInfo xmslBidWinHandoverInfoParam) {
//        return toAjax(xmslBidWinHandoverInfoService.deleteXmslBidWinHandoverInfo(xmslBidWinHandoverInfoParam));
//    }
//
//    @PostMapping("/{ids}")
//    public AjaxResult deleteXmslBidWinHandoverInfoByPks(@PathVariable Long[] ids) {
//        List<Long> xmslBidWinHandoverInfoPkList = Arrays.asList(ids);
//        return toAjax(xmslBidWinHandoverInfoService.deleteXmslBidWinHandoverInfoByPks(xmslBidWinHandoverInfoPkList));
//    }
//
//    @GetMapping("/export")
//    public void export(HttpServletResponse response, XmslBidWinHandoverInfo xmslBidWinHandoverInfoParam)
//        throws IOException {
//        List<XmslBidWinHandoverInfo> xmslBidWinHandoverInfoList = xmslBidWinHandoverInfoService
//            .getXmslBidWinHandoverInfoList(xmslBidWinHandoverInfoParam);
//        ExcelUtils<XmslBidWinHandoverInfo> util = new ExcelUtils<>(XmslBidWinHandoverInfo.class);
//        util.exportExcel(response, xmslBidWinHandoverInfoList, DateUtils.getDate());
//    }
}
