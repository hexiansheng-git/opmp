package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeContentService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

/**
 * @author han
 * @date 2023-07-07 19:02:54
 * @remark 比选方案比选内容
 */
@Validated
@RestController
@RequestMapping("/qqchComparisonSchemeContent")
public class QqchComparisonSchemeContentController extends BaseController {

    @Autowired
    private IQqchComparisonSchemeContentService qqchComparisonSchemeContentService;
}
