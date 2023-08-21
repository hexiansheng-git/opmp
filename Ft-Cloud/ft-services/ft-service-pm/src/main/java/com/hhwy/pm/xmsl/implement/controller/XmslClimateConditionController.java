package com.hhwy.pm.xmsl.implement.controller;

import com.alibaba.excel.EasyExcel;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.listener.ClimateConditionListener;
import com.hhwy.pm.xmsl.implement.domain.XmslClimateCondition;
import com.hhwy.pm.xmsl.implement.service.IXmslClimateConditionService;
import com.hhwy.utils.validation.ValidationGroups;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:07
 * @remark 气候条件
 */
@RestController
@RequestMapping("/xmslClimateCondition")
public class XmslClimateConditionController extends BaseController {

    @Autowired
    private IXmslClimateConditionService xmslClimateConditionService;

    @GetMapping("/getList")
    public AjaxResult getList(
        @Validated(ValidationGroups.Select.class) XmslClimateCondition xmslClimateConditionParam) {
        List<XmslClimateCondition> xmslClimateConditionList = xmslClimateConditionService
            .getXmslClimateConditionList(xmslClimateConditionParam);
        return AjaxResult.success(xmslClimateConditionList);
    }

    @PostMapping("/save")
    public AjaxResult save(
        @Validated(ValidationGroups.Save.class) @RequestBody List<XmslClimateCondition> xmslClimateConditionListParam) {
        xmslClimateConditionService.save(xmslClimateConditionListParam);
        return AjaxResult.success("保存成功！");
    }

    @PostMapping("/remove")
    public AjaxResult deleteXmslClimateConditionByPks(Long[] pks) {
        List<Long> xmslClimateConditionPkList = Arrays.asList(pks);
        return toAjax(xmslClimateConditionService.deleteXmslClimateConditionByPks(xmslClimateConditionPkList));
    }

    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        try {
            ClimateConditionListener readListener = new ClimateConditionListener();
            try {
                // 两行表头
                EasyExcel.read(file.getInputStream(), XmslClimateCondition.class, readListener).headRowNumber(2)
                    .sheet(0).doRead();
                List<XmslClimateCondition> list = readListener.getList();
                return AjaxResult.success(list);
            } catch (IOException e) {
                return AjaxResult.error();
            }
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslClimateCondition xmslClimateConditionParam) throws IOException {
        List<XmslClimateCondition> xmslClimateConditionList = xmslClimateConditionService.getXmslClimateConditionList(xmslClimateConditionParam);
        ExcelUtils<XmslClimateCondition> util = new ExcelUtils<>(XmslClimateCondition.class);
        util.exportExcel(response, xmslClimateConditionList, DateUtils.getDate());
    }
}
