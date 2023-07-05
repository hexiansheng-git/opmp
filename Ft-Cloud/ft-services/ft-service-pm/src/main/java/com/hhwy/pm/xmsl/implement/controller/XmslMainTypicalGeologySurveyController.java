package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.domain.XmslMainTypicalGeologySurvey;
import com.hhwy.pm.xmsl.implement.service.IXmslMainTypicalGeologySurveyService;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
        ExcelUtils<XmslMainTypicalGeologySurvey> util = new ExcelUtils<>(XmslMainTypicalGeologySurvey.class);
        try {
            InputStream inputStream = file.getInputStream();
            List<XmslMainTypicalGeologySurvey> list = util.importExcel(inputStream);
            return AjaxResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException("导入失败！");
        }
    }
}
