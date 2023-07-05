package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslConstructionInterference;
import com.hhwy.pm.xmsl.implement.service.IXmslConstructionInterferenceService;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
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
 * @date 2023-07-03 12:52:29
 * @remark 施工干扰
 */
@RestController
@RequestMapping("/xmslConstructionInterference")
public class XmslConstructionInterferenceController extends BaseController {

    @Autowired
    private IXmslConstructionInterferenceService xmslConstructionInterferenceService;

    @GetMapping("/getList")
    public AjaxResult getXmslConstructionInterferenceList(
        XmslConstructionInterference xmslConstructionInterferenceParam) {
        List<XmslConstructionInterference> xmslConstructionInterferenceList = xmslConstructionInterferenceService
            .getXmslConstructionInterferenceList(xmslConstructionInterferenceParam);
        return AjaxResult.success(xmslConstructionInterferenceList);
    }

    @PostMapping("/save")
    public AjaxResult save(@RequestBody List<XmslConstructionInterference> xmslConstructionInterferenceListParam) {
        xmslConstructionInterferenceService.save(xmslConstructionInterferenceListParam);
        return AjaxResult.success("保存成功！");
    }

    @PostMapping("/remove")
    public AjaxResult deleteXmslConstructionInterferenceByPks(Long[] pks) {
        List<Long> xmslConstructionInterferencePkList = Arrays.asList(pks);
        return toAjax(xmslConstructionInterferenceService
            .deleteXmslConstructionInterferenceByPks(xmslConstructionInterferencePkList));
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        ExcelUtils<XmslConstructionInterference> util = new ExcelUtils<>(XmslConstructionInterference.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslConstructionInterference> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
