package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.service.IXmslLocalMaterialsSupplyService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/remove")
    public AjaxResult deleteXmslLocalMaterialsSupplyByPks(Long[] pks) {
        List<Long> xmslLocalMaterialsSupplyPkList = Arrays.asList(pks);
        return toAjax(xmslLocalMaterialsSupplyService.deleteXmslLocalMaterialsSupplyByPks(xmslLocalMaterialsSupplyPkList));
    }
}
