package com.hhwy.pm.ehr.controller;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.ehr.domain.PersonCertifyCompetency;
import com.hhwy.pm.ehr.service.IEhrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success("1");
    }

    @PostMapping("test1")
    public AjaxResult test1(String userNames){
        try {
            ehrService.getCertList(userNames);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success("1");
    }

    @PostMapping("test3")
    public AjaxResult test3(String userNames){
        try {
            for(int i=1; i<=10; i++){
                Map<String, Object> res = ehrService.getCertList("2022008083");
                System.out.print("第"+i+"次请求返回结果:"+res.toString());
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success("1");
    }

}
