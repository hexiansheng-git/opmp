package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslExtend;
import com.hhwy.pm.xmsl.implement.service.IXmslExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:35
 * @remark 当地政策要点说明, 社会和人文条件说明, 气候条件附件
 */
@RestController
@RequestMapping("/xmslExtend")
public class XmslExtendController extends BaseController {

    @Autowired
    private IXmslExtendService xmslExtendService;

    @GetMapping("/getXmslExtend")
    public AjaxResult getXmslExtend(XmslExtend xmslExtendParam) {
        XmslExtend xmslExtend = xmslExtendService.getXmslExtend(xmslExtendParam);
        return AjaxResult.success(xmslExtend);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody XmslExtend xmslExtendParam) {
        XmslExtend xmslExtend = xmslExtendService.save(xmslExtendParam);
        return AjaxResult.success(xmslExtend);
    }
}
