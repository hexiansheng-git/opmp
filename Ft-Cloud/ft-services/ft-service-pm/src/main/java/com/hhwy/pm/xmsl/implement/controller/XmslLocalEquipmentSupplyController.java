package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.service.IXmslLocalEquipmentSupplyService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/remove")
    public AjaxResult deleteXmslLocalEquipmentSupplyByPks(Long[] pks) {
        List<Long> xmslLocalEquipmentSupplyPkList = Arrays.asList(pks);
        return toAjax(
            xmslLocalEquipmentSupplyService.deleteXmslLocalEquipmentSupplyByPks(xmslLocalEquipmentSupplyPkList));
    }
}
