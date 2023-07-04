package com.hhwy.pm.xmsl.implement.controller;

import com.hhwy.common.core.web.controller.BaseController;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.xmsl.implement.service.IXmslBadGeologySurveyService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("/remove")
    public AjaxResult deleteXmslBadGeologySurveyByPks(Long[] pks) {
        List<Long> xmslBadGeologySurveyPkList = Arrays.asList(pks);
        return toAjax(xmslBadGeologySurveyService.deleteXmslBadGeologySurveyByPks(xmslBadGeologySurveyPkList));
    }
}
