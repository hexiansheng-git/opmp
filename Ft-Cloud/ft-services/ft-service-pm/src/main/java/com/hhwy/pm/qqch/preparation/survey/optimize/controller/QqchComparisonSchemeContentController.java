package com.hhwy.pm.qqch.preparation.survey.optimize.controller;

import java.util.List;
import java.io.IOException;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchComparisonSchemeContent;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchComparisonSchemeContentService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.poi.ExcelUtils;
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


    @GetMapping("/export")
    public void export(HttpServletResponse response, QqchComparisonSchemeContent qqchComparisonSchemeContentParam) throws IOException {
        List<QqchComparisonSchemeContent> qqchComparisonSchemeContentList = qqchComparisonSchemeContentService.getQqchComparisonSchemeContentList(qqchComparisonSchemeContentParam);
        ExcelUtils<QqchComparisonSchemeContent> util = new ExcelUtils<>(QqchComparisonSchemeContent.class);
        util.exportExcel(response, qqchComparisonSchemeContentList, DateUtils.getDate());
    }
}
