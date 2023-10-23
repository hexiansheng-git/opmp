package com.hhwy.pm.ehr.service;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface IEhrService {

    /**
     * 获取证书信息
     * @param userNameList   用户登录名称list
     * @return
     */
    Map<String,Object>  getCertList(String userName4A) throws ServiceException, ParserConfigurationException, IOException, SAXException;
    


}
