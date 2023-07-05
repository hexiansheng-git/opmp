package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslLocalEquipmentSupply;
import com.hhwy.pm.xmsl.implement.service.IXmslLocalEquipmentSupplyService;
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
 * @date 2023-07-03 12:52:47
 * @remark 当地资源供应-属地设备供应情况
 */
@RestController
@RequestMapping("/xmslLocalEquipmentSupply")
public class XmslLocalEquipmentSupplyController extends BaseController {

    @Autowired
    private IXmslLocalEquipmentSupplyService xmslLocalEquipmentSupplyService;

    @PostMapping("/remove")
    public AjaxResult deleteXmslLocalEquipmentSupplyByPks(Long[] pks) {
        List<Long> xmslLocalEquipmentSupplyPkList = Arrays.asList(pks);
        return toAjax(
            xmslLocalEquipmentSupplyService.deleteXmslLocalEquipmentSupplyByPks(xmslLocalEquipmentSupplyPkList));
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        ExcelUtils<XmslLocalEquipmentSupply> util = new ExcelUtils<>(XmslLocalEquipmentSupply.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslLocalEquipmentSupply> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
