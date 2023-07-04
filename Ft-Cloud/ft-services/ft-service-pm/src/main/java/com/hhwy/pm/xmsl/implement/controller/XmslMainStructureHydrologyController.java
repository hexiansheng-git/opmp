package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslMainStructureHydrology;
import com.hhwy.pm.xmsl.implement.service.IXmslMainStructureHydrologyService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:02
 * @remark 主要构造物水文条件
 */
@RestController
@RequestMapping("/xmslMainStructureHydrology")
public class XmslMainStructureHydrologyController extends BaseController {

    @Autowired
    private IXmslMainStructureHydrologyService xmslMainStructureHydrologyService;

    @GetMapping("/getList")
    public AjaxResult getXmslMainStructureHydrologyList(XmslMainStructureHydrology xmslMainStructureHydrologyParam) {
        List<XmslMainStructureHydrology> xmslMainStructureHydrologyList = xmslMainStructureHydrologyService
            .getXmslMainStructureHydrologyList(xmslMainStructureHydrologyParam);
        return AjaxResult.success(xmslMainStructureHydrologyList);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody List<XmslMainStructureHydrology> xmslMainStructureHydrologyListParam) {
        xmslMainStructureHydrologyService.save(xmslMainStructureHydrologyListParam);
        return AjaxResult.success("保存成功！");
    }

    @DeleteMapping("/remove")
    public AjaxResult deleteXmslMainStructureHydrologyByPks(Long[] pks) {
        List<Long> xmslMainStructureHydrologyPkList = Arrays.asList(pks);
        return toAjax(xmslMainStructureHydrologyService
            .deleteXmslMainStructureHydrologyByPks(xmslMainStructureHydrologyPkList));
    }
}
