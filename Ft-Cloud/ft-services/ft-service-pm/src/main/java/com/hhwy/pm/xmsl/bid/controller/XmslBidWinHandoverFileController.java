package com.hhwy.pm.xmsl.bid.controller;

import com.hhwy.pm.xmsl.bid.service.IXmslBidWinHandoverFileService;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-06 15:18:31
 * @remark 中标项目移交文件
 */
@Validated
@RestController
@RequestMapping("/xmslBidWinHandoverFile")
public class XmslBidWinHandoverFileController extends BaseController {

    @Autowired
    private IXmslBidWinHandoverFileService xmslBidWinHandoverFileService;

//    @GetMapping
//    public AjaxResult getXmslBidWinHandoverFile(
//        @Validated(ValidationGroups.Get.class) @RequestBody XmslBidWinHandoverFile xmslBidWinHandoverFileParam) {
//        XmslBidWinHandoverFile xmslBidWinHandoverFile = xmslBidWinHandoverFileService
//            .getXmslBidWinHandoverFile(xmslBidWinHandoverFileParam);
//        return AjaxResult.success(xmslBidWinHandoverFile);
//    }
//
//    @GetMapping("/list")
//    public AjaxResult getXmslBidWinHandoverFileList(
//        @Validated(ValidationGroups.Select.class) @RequestBody XmslBidWinHandoverFile xmslBidWinHandoverFileParam) {
//        startPage();
//        List<XmslBidWinHandoverFile> xmslBidWinHandoverFileList = xmslBidWinHandoverFileService
//            .getXmslBidWinHandoverFileList(xmslBidWinHandoverFileParam);
//        return getDataTableAjaxResult(xmslBidWinHandoverFileList);
//    }
//
//    @PostMapping("/add")
//    public AjaxResult insertXmslBidWinHandoverFile(
//        @Validated(ValidationGroups.Save.class) @RequestBody XmslBidWinHandoverFile xmslBidWinHandoverFileParam) {
//        xmslBidWinHandoverFileService.insertXmslBidWinHandoverFile(xmslBidWinHandoverFileParam);
//        return AjaxResult.success(xmslBidWinHandoverFileParam);
//    }
//
//    @PostMapping("/batchAdd")
//    public AjaxResult insertXmslBidWinHandoverFileList(
//        @Validated(ValidationGroups.Save.class) @RequestBody List<XmslBidWinHandoverFile> xmslBidWinHandoverFileListParam) {
//        xmslBidWinHandoverFileService.insertXmslBidWinHandoverFileList(xmslBidWinHandoverFileListParam);
//        return AjaxResult.success(xmslBidWinHandoverFileListParam);
//    }
//
//    @PostMapping("/update")
//    public AjaxResult updateXmslBidWinHandoverFile(
//        @Validated(ValidationGroups.Update.class) @RequestBody XmslBidWinHandoverFile xmslBidWinHandoverFileParam) {
//        return toAjax(
//            xmslBidWinHandoverFileService.updateXmslBidWinHandoverFile(xmslBidWinHandoverFileParam));
//    }
//
//    @PostMapping("/batchUpdate")
//    public AjaxResult updateXmslBidWinHandoverFileList(
//        @Validated(ValidationGroups.Update.class) @RequestBody List<XmslBidWinHandoverFile> xmslBidWinHandoverFileListParam) {
//        return toAjax(xmslBidWinHandoverFileService
//            .updateXmslBidWinHandoverFileList(xmslBidWinHandoverFileListParam));
//    }
//
//    @PostMapping("/delete")
//    public AjaxResult deleteXmslBidWinHandoverFile(
//        @Validated(ValidationGroups.Delete.class) @RequestBody XmslBidWinHandoverFile xmslBidWinHandoverFileParam) {
//        return toAjax(
//            xmslBidWinHandoverFileService.deleteXmslBidWinHandoverFile(xmslBidWinHandoverFileParam));
//    }

    @PostMapping("/remove")
    public AjaxResult deleteXmslBidWinHandoverFileByPks(Long[] ids) {
        List<Long> xmslBidWinHandoverFilePkList = Arrays.asList(ids);
        return toAjax(
            xmslBidWinHandoverFileService.deleteXmslBidWinHandoverFileByPks(xmslBidWinHandoverFilePkList));
    }
//
//    @GetMapping("/export")
//    public void export(HttpServletResponse response, XmslBidWinHandoverFile xmslBidWinHandoverFileParam)
//        throws IOException {
//        List<XmslBidWinHandoverFile> xmslBidWinHandoverFileList = xmslBidWinHandoverFileService
//            .getXmslBidWinHandoverFileList(xmslBidWinHandoverFileParam);
//        ExcelUtils<XmslBidWinHandoverFile> util = new ExcelUtils<>(XmslBidWinHandoverFile.class);
//        util.exportExcel(response, xmslBidWinHandoverFileList, DateUtils.getDate());
//    }
}
