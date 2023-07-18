package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeHeaderService;
import org.springframework.web.bind.annotation.*;
import com.hhwy.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

/**
 * @author han
 * @date 2023-07-07 18:35:43
 * @remark 比选方案表头
 */
@Validated
@RestController
@RequestMapping("/qqchComparisonSchemeHeader")
public class QqchComparisonSchemeHeaderController extends BaseController {

    @Autowired
    private IQqchComparisonSchemeHeaderService qqchComparisonSchemeHeaderService;
}
