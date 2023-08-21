package com.hhwy.pm.xmsl.implement.controller;

import com.alibaba.excel.EasyExcel;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.listener.MainTypicalGeologySurveyListener;
import com.hhwy.pm.xmsl.implement.domain.XmslMainTypicalGeologySurvey;
import com.hhwy.pm.xmsl.implement.service.IXmslMainTypicalGeologySurveyService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:07
 * @remark 地质条件-主线典型地质勘察
 */
@RestController
@RequestMapping("/xmslMainTypicalGeologySurvey")
public class XmslMainTypicalGeologySurveyController extends BaseController {

    @Autowired
    private IXmslMainTypicalGeologySurveyService xmslMainTypicalGeologySurveyService;

    @PostMapping("/remove")
    public AjaxResult deleteXmslMainTypicalGeologySurveyByPks(Long[] pks) {
        List<Long> xmslMainTypicalGeologySurveyPkList = Arrays.asList(pks);
        return toAjax(xmslMainTypicalGeologySurveyService
            .deleteXmslMainTypicalGeologySurveyByPks(xmslMainTypicalGeologySurveyPkList));
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        try {
            MainTypicalGeologySurveyListener readListener = new MainTypicalGeologySurveyListener();
            try {
                // 两行表头
                EasyExcel.read(file.getInputStream(), XmslMainTypicalGeologySurvey.class, readListener).headRowNumber(2)
                    .sheet(0).doRead();
                List<XmslMainTypicalGeologySurvey> list = readListener.getList();
                return AjaxResult.success(list);
            } catch (IOException e) {
                return AjaxResult.error();
            }
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslMainTypicalGeologySurvey xmslMainTypicalGeologySurveyParam)
        throws IOException {
        List<XmslMainTypicalGeologySurvey> xmslMainTypicalGeologySurveyList =
            xmslMainTypicalGeologySurveyService.getXmslMainTypicalGeologySurvey(xmslMainTypicalGeologySurveyParam);
        ExcelUtils<XmslMainTypicalGeologySurvey> util = new ExcelUtils<>(XmslMainTypicalGeologySurvey.class);
        util.exportExcel(response, xmslMainTypicalGeologySurveyList, DateUtils.getDate());
    }
}
