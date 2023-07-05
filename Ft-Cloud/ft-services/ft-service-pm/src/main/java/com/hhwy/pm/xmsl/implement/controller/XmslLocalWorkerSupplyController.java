package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslLocalWorkerSupply;
import com.hhwy.pm.xmsl.implement.service.IXmslLocalWorkerSupplyService;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:57
 * @remark 当地资源供应-属地工人供应情况
 */
@RestController
@RequestMapping("/xmslLocalWorkerSupply")
public class XmslLocalWorkerSupplyController extends BaseController {

    @Autowired
    private IXmslLocalWorkerSupplyService xmslLocalWorkerSupplyService;

    @PostMapping("/remove")
    public AjaxResult deleteXmslLocalWorkerSupplyByPks(Long[] pks) {
        List<Long> xmslLocalWorkerSupplyPkList = Arrays.asList(pks);
        return toAjax(xmslLocalWorkerSupplyService.deleteXmslLocalWorkerSupplyByPks(xmslLocalWorkerSupplyPkList));
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        ExcelUtils<XmslLocalWorkerSupply> util = new ExcelUtils<>(XmslLocalWorkerSupply.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslLocalWorkerSupply> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
