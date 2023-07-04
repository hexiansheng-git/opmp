package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.service.IXmslMainTypicalGeologySurveyService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/remove")
    public AjaxResult deleteXmslMainTypicalGeologySurveyByPks(Long[] pks) {
        List<Long> xmslMainTypicalGeologySurveyPkList = Arrays.asList(pks);
        return toAjax(xmslMainTypicalGeologySurveyService
            .deleteXmslMainTypicalGeologySurveyByPks(xmslMainTypicalGeologySurveyPkList));
    }

}
