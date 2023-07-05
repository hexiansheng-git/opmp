package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslBadGeologySurvey;
import com.hhwy.pm.xmsl.implement.service.IXmslBadGeologySurveyService;
import java.io.IOException;
import java.io.InputStream;
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
 * @date 2023-07-03 11:51:43
 * @remark 地质条件-不良地质调查
 */
@RestController
@RequestMapping("/xmslBadGeologySurvey")
public class XmslBadGeologySurveyController extends BaseController {

    @Autowired
    private IXmslBadGeologySurveyService xmslBadGeologySurveyService;

    @PostMapping("/remove")
    public AjaxResult deleteXmslBadGeologySurveyByPks(Long[] pks) {
        List<Long> xmslBadGeologySurveyPkList = Arrays.asList(pks);
        return toAjax(xmslBadGeologySurveyService.deleteXmslBadGeologySurveyByPks(xmslBadGeologySurveyPkList));
    }

    /**
     * 导入
     *
     * @param file
     * @return
     */
    @PostMapping("/importExcel")
    public AjaxResult importExcel(@RequestPart("file") MultipartFile file) {
        ExcelUtils<XmslBadGeologySurvey> util = new ExcelUtils<>(XmslBadGeologySurvey.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslBadGeologySurvey> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response, XmslBadGeologySurvey xmslBadGeologySurveyParam)
        throws IOException {
        List<XmslBadGeologySurvey> xmslMainTypicalGeologySurveyList = xmslBadGeologySurveyService
            .getXmslBadGeologySurvey(xmslBadGeologySurveyParam);
        ExcelUtils<XmslBadGeologySurvey> util = new ExcelUtils<>(XmslBadGeologySurvey.class);
        util.exportExcel(response, xmslMainTypicalGeologySurveyList, DateUtils.getDate());
    }
}
