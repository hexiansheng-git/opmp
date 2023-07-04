package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslKeyPersonCommunication;
import com.hhwy.pm.xmsl.implement.service.IXmslKeyPersonCommunicationService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AjaxResult save(
        @RequestBody List<XmslKeyPersonCommunication> xmslKeyPersonCommunicationListParam) {
        xmslKeyPersonCommunicationService.save(xmslKeyPersonCommunicationListParam);
        return  AjaxResult.success("保存成功！");
    }

    @DeleteMapping("/remove")
    public AjaxResult deleteXmslKeyPersonCommunicationByPks(@PathVariable Long[] pks) {
        List<Long> xmslKeyPersonCommunicationPkList = Arrays.asList(pks);
        return toAjax(
            xmslKeyPersonCommunicationService.deleteXmslKeyPersonCommunicationByPks(xmslKeyPersonCommunicationPkList));
    }
}
