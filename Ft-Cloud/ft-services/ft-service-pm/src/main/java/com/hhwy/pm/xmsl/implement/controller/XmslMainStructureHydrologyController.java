package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslMainStructureHydrology;
import com.hhwy.pm.xmsl.implement.service.IXmslMainStructureHydrologyService;
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

    @PostMapping("/remove")
    public AjaxResult deleteXmslMainStructureHydrologyByPks(Long[] pks) {
        List<Long> xmslMainStructureHydrologyPkList = Arrays.asList(pks);
        return toAjax(xmslMainStructureHydrologyService
            .deleteXmslMainStructureHydrologyByPks(xmslMainStructureHydrologyPkList));
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        ExcelUtils<XmslMainStructureHydrology> util = new ExcelUtils<>(XmslMainStructureHydrology.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslMainStructureHydrology> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslMainStructureHydrology xmslMainStructureHydrologyParam)
        throws IOException {
        List<XmslMainStructureHydrology> xmslMainStructureHydrologyList =
            xmslMainStructureHydrologyService.getXmslMainStructureHydrologyList(xmslMainStructureHydrologyParam);
        ExcelUtils<XmslMainStructureHydrology> util = new ExcelUtils<>(XmslMainStructureHydrology.class);
        util.exportExcel(response, xmslMainStructureHydrologyList, DateUtils.getDate());
    }
}
