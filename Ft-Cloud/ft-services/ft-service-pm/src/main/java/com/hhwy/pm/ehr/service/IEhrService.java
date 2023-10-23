package com.hhwy.pm.ehr.service;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;


public interface IEhrService {

    /**
     * 获取证书信息
     * @param userName4A   用户登录名称list
     * @return
     */
    Map<String,Object>  getCertList(String userName4A) throws  ParserConfigurationException, IOException, SAXException;



}
