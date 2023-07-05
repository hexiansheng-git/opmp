package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslBasicFacilitiesConditions;
import com.hhwy.pm.xmsl.implement.service.IXmslBasicFacilitiesConditionsService;
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
 * @date 2023-07-03 11:59:44
 * @remark 水、电、交通、通讯条件
 */
@RestController
@RequestMapping("/xmslBasicFacilitiesConditions")
public class XmslBasicFacilitiesConditionsController extends BaseController {

    @Autowired
    private IXmslBasicFacilitiesConditionsService xmslBasicFacilitiesConditionsService;

    @GetMapping("/getList")
    public AjaxResult getList(
        XmslBasicFacilitiesConditions xmslBasicFacilitiesConditionsParam) {
        List<XmslBasicFacilitiesConditions> xmslBasicFacilitiesConditionsList = xmslBasicFacilitiesConditionsService
            .getXmslBasicFacilitiesConditionsList(xmslBasicFacilitiesConditionsParam);
        return AjaxResult.success(xmslBasicFacilitiesConditionsList);
    }

    @PostMapping("/save")
    public AjaxResult save(
        @RequestBody List<XmslBasicFacilitiesConditions> xmslBasicFacilitiesConditionsListParam) {
        xmslBasicFacilitiesConditionsService.save(xmslBasicFacilitiesConditionsListParam);
        return AjaxResult.success("保存成功！");
    }

    @PostMapping("/remove")
    public AjaxResult deleteXmslBasicFacilitiesConditionsByPks(Long[] pks) {
        List<Long> xmslBasicFacilitiesConditionsPkList = Arrays.asList(pks);
        return toAjax(xmslBasicFacilitiesConditionsService
            .deleteXmslBasicFacilitiesConditionsByPks(xmslBasicFacilitiesConditionsPkList));
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        ExcelUtils<XmslBasicFacilitiesConditions> util = new ExcelUtils<>(XmslBasicFacilitiesConditions.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslBasicFacilitiesConditions> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
