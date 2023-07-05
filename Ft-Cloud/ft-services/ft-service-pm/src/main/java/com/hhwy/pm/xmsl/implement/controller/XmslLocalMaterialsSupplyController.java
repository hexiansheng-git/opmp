package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslLocalMaterialsSupply;
import com.hhwy.pm.xmsl.implement.service.IXmslLocalMaterialsSupplyService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:53
 * @remark 当地资源供应-属地物资供应情况
 */
@RestController
@RequestMapping("/xmslLocalMaterialsSupply")
public class XmslLocalMaterialsSupplyController extends BaseController {

    @Autowired
    private IXmslLocalMaterialsSupplyService xmslLocalMaterialsSupplyService;

    @PostMapping("/remove")
    public AjaxResult deleteXmslLocalMaterialsSupplyByPks(Long[] pks) {
        List<Long> xmslLocalMaterialsSupplyPkList = Arrays.asList(pks);
        return toAjax(
            xmslLocalMaterialsSupplyService.deleteXmslLocalMaterialsSupplyByPks(xmslLocalMaterialsSupplyPkList));
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        ExcelUtils<XmslLocalMaterialsSupply> util = new ExcelUtils<>(XmslLocalMaterialsSupply.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslLocalMaterialsSupply> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslLocalMaterialsSupply xmslLocalMaterialsSupplyParam)
        throws IOException {
        List<XmslLocalMaterialsSupply> xmslLocalMaterialsSupplyList =
            xmslLocalMaterialsSupplyService.getXmslLocalMaterialsSupplyList(xmslLocalMaterialsSupplyParam);
        ExcelUtils<XmslLocalMaterialsSupply> util = new ExcelUtils<>(XmslLocalMaterialsSupply.class);
        util.exportExcel(response, xmslLocalMaterialsSupplyList, DateUtils.getDate());
    }
}
