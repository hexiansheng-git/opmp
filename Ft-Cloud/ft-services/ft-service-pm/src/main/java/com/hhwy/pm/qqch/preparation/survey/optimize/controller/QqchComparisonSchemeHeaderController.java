package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeHeader;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeHeaderService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
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


    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchComparisonSchemeHeader qqchComparisonSchemeHeaderParam) throws IOException {
        List<QqchComparisonSchemeHeader> qqchComparisonSchemeHeaderList = qqchComparisonSchemeHeaderService.getQqchComparisonSchemeHeaderList(qqchComparisonSchemeHeaderParam);
        ExcelUtils<QqchComparisonSchemeHeader> util = new ExcelUtils<>(QqchComparisonSchemeHeader.class);
        util.exportExcel(response, qqchComparisonSchemeHeaderList, DateUtils.getDate());
    }
}
