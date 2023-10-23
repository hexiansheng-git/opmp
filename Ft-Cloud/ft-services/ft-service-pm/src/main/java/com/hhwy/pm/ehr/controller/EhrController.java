package com.hhwy.pm.ehr.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.core.web.page.TableDataInfo;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.ehr.service.IEhrService;
import com.hhwy.pm.gm.service.IGmThirdService;
import com.hhwy.pm.qqch.evaluation.domain.QqchSummaryEvaluation;
import com.hhwy.pm.qqch.qqchPerformInspection.domain.QqchPerformInspection;
import com.hhwy.pm.qqch.review.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 调用人资系统获取人员的证书信息
 */
@RestController
@RequestMapping("/ehr")
public class EhrController {


    @Autowired
    IEhrService ehrService;


    @PostMapping("/getCertList")
    public AjaxResult getCertList() {
        try {
            ehrService.getCertList(SecurityUtils.getUserName());
        } catch (ServiceException | ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success("1");
    }



}
