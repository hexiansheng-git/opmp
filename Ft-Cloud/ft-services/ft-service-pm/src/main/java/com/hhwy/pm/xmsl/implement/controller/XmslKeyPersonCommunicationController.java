package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslKeyPersonCommunication;
import com.hhwy.pm.xmsl.implement.service.IXmslKeyPersonCommunicationService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhenglili
 * @date 2023-07-03 13:13:27
 * @remark 重要干系人识别及沟通
 */
@RestController
@RequestMapping("/xmslKeyPersonCommunication")
public class XmslKeyPersonCommunicationController extends BaseController {

    @Autowired
    private IXmslKeyPersonCommunicationService xmslKeyPersonCommunicationService;

    @GetMapping("/getList")
    public AjaxResult getList(XmslKeyPersonCommunication xmslKeyPersonCommunicationParam) {
        List<XmslKeyPersonCommunication> xmslKeyPersonCommunicationList = xmslKeyPersonCommunicationService
            .getXmslKeyPersonCommunicationList(xmslKeyPersonCommunicationParam);
        return AjaxResult.success(xmslKeyPersonCommunicationList);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody List<XmslKeyPersonCommunication> xmslKeyPersonCommunicationListParam) {
        xmslKeyPersonCommunicationService.save(xmslKeyPersonCommunicationListParam);
        return AjaxResult.success("保存成功！");
    }

    @PostMapping("/remove")
    public AjaxResult deleteXmslKeyPersonCommunicationByPks(Long[] pks) {
        List<Long> xmslKeyPersonCommunicationPkList = Arrays.asList(pks);
        return toAjax(
            xmslKeyPersonCommunicationService.deleteXmslKeyPersonCommunicationByPks(xmslKeyPersonCommunicationPkList));
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        ExcelUtils<XmslKeyPersonCommunication> util = new ExcelUtils<>(XmslKeyPersonCommunication.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslKeyPersonCommunication> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslKeyPersonCommunication xmslKeyPersonCommunicationParam)
        throws IOException {
        List<XmslKeyPersonCommunication> xmslKeyPersonCommunicationList =
            xmslKeyPersonCommunicationService.getXmslKeyPersonCommunicationList(xmslKeyPersonCommunicationParam);
        ExcelUtils<XmslKeyPersonCommunication> util = new ExcelUtils<>(XmslKeyPersonCommunication.class);
        util.exportExcel(response, xmslKeyPersonCommunicationList, DateUtils.getDate());
    }
}
