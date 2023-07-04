package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.service.IXmslLocalWorkerSupplyService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/remove")
    public AjaxResult deleteXmslLocalWorkerSupplyByPks(Long[] pks) {
        List<Long> xmslLocalWorkerSupplyPkList = Arrays.asList(pks);
        return toAjax(xmslLocalWorkerSupplyService.deleteXmslLocalWorkerSupplyByPks(xmslLocalWorkerSupplyPkList));
    }
}
