package com.hhwy.pm.ehr.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.ehr.domain.Attachment;
import com.hhwy.pm.ehr.domain.PersonCertifyCompetency;
import com.hhwy.pm.ehr.service.IEhrService;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EhrServiceImpl implements IEhrService {

    //sessionUrl
    @Value("${ehr.sessionUrl}")
    private String sessionUrl;
    //证书信息url
    @Value("${ehr.certUrl}")
    private String certUrl;
    //api白云山 密钥
    @Value("${ehr.apiKey}")
    private String apiKey;

    @Value("${ehr.userName}")
    private String userName;

    @Value("${ehr.password}")
    private String password;

    @Value("${ehr.slnName}")
    private String slnName;

    @Value("${ehr.language}")
    private String language;

    @Value("${ehr.dcName}")
    private String dcName;

    @Value("${ehr.dbType}")
    private int dbType;

    @Value("${ehr.authPattern}")
    private String authPattern;

    @Autowired
    private static RedisUtils redisUtils;
    static{
        redisUtils = SpringUtils.getBean(RedisUtils.class);
    }

    public static final String KEY = "EHR::PersonCertifyCompetency";

    @Override
    public Map<String,Object> getCertList(String userName4A) throws  ParserConfigurationException, IOException, SAXException {

        Map<String, String> res = this.getHrSessionId();
        String sessionId = res.get("sessionId");
        String cookie = res.get("cookie");
        String certParam = this.getCertParam(sessionId, userName4A);
        CloseableHttpResponse response = this.cretPost(certUrl, certParam,cookie);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        int state = response.getStatusLine().getStatusCode();
        String result = EntityUtils.toString(entity, "UTF-8");
        Map map = this.getCertInfo(result);
        return map;
    }

    @Override
    public Map<String, List<PersonCertifyCompetency>> getCertListByUserName4As(String userName4As) throws  ParserConfigurationException, IOException, SAXException {
        Map<String, List<PersonCertifyCompetency>> resultMap = new HashMap<>();
        if(StringUtils.isBlank(userName4As)){
            return resultMap;
        }

        String[] userName4AArr = userName4As.split(",");
        Map<Object, Object> map = redisUtils.hGetAll(KEY);
        for (String userName4A : userName4AArr) {
            Object o = map.get(userName4A);
            List<PersonCertifyCompetency> personCertifyCompetencyList = new ArrayList<>();
            if(o == null){
                Map<String, Object> certList = this.getCertList(userName4A);
                Object personCertifyCompetency = certList.get("personCertifyCompetency");
                if(personCertifyCompetency != null) {
                    personCertifyCompetencyList = this.getPersonCertifyCompetencyList(personCertifyCompetency);
                }
                redisUtils.hPut(KEY,userName4A, JSONArray.toJSONString(personCertifyCompetencyList));
            }else {
                Object[] personCertifyCompetencyArr = JSONArray.parseArray(o.toString()).toArray();
                for (Object personObj : personCertifyCompetencyArr) {
                    PersonCertifyCompetency personCertifyCompetency = JSONObject.parseObject(personObj.toString(), PersonCertifyCompetency.class);
                    personCertifyCompetencyList.add(personCertifyCompetency);
                }
            }

            resultMap.put(userName4A,personCertifyCompetencyList);
        }
        return resultMap;
    }

    private List<PersonCertifyCompetency> getPersonCertifyCompetencyList(Object personCertifyCompetencyObj){
        List<PersonCertifyCompetency> personCertifyCompetencyList = new ArrayList<>();
        Object[] personCertifyCompetencyArr = JSONArray.parseArray(personCertifyCompetencyObj.toString()).toArray();
        for (Object personObj : personCertifyCompetencyArr) {
            PersonCertifyCompetency personCertifyCompetency = new PersonCertifyCompetency();

            Map<String,Object> CompetencyMap = JSONObject.parseObject(personObj.toString(), Map.class);
            String categoryName = (String) CompetencyMap.get("category_name");
            String certifiedCompetencyName = (String) CompetencyMap.get("certifiedCompetency_name");
            String przcny = (String) CompetencyMap.get("przcny");
            String levelNumber = (String) CompetencyMap.get("level_number");
            String zymc = (String) CompetencyMap.get("zymc");
            String categoryNumber = (String) CompetencyMap.get("category_number");
            String appointUnit = (String) CompetencyMap.get("appointUnit");
            String certifiedCompetencyNumber = (String) CompetencyMap.get("certifiedCompetency_number");
            String levelName = (String) CompetencyMap.get("level_name");
            String certificateNumber = (String) CompetencyMap.get("certificateNumber");
            Boolean isHighest = (Boolean) CompetencyMap.get("isHighest");
            String obtainDate = (String) CompetencyMap.get("obtainDate");

            Object attachment = CompetencyMap.get("attachment");
            List<Attachment> attachmentList = new ArrayList<>();
            if(attachment != null){
                Object[] attachmentArr = JSONArray.parseArray(attachment.toString()).toArray();
                for (Object attachmentObj : attachmentArr) {
                    Attachment attachment1 = new Attachment();
                    Map<String,String> attachmentMap = JSONObject.parseObject(attachmentObj.toString(), Map.class);
                    String name = attachmentMap.get("name");
                    String id = attachmentMap.get("id");

                    attachment1.setName(name);
                    attachment1.setId(id);
                    attachmentList.add(attachment1);
                }
            }

            personCertifyCompetency.setCategoryName(categoryName);
            personCertifyCompetency.setCertificateName(certifiedCompetencyName);
            personCertifyCompetency.setPrzcny(przcny);
            personCertifyCompetency.setLevelNumber(levelNumber);
            personCertifyCompetency.setZymc(zymc);
            personCertifyCompetency.setCategoryNumber(categoryNumber);
            personCertifyCompetency.setAppointUnit(appointUnit);
            personCertifyCompetency.setCertificateNo(certifiedCompetencyNumber);
            personCertifyCompetency.setLevelName(levelName);
            personCertifyCompetency.setCertificateNumber(certificateNumber);
            personCertifyCompetency.setIsHighest(isHighest);
            personCertifyCompetency.setIssueDate(obtainDate);
            personCertifyCompetency.setAttachmentList(attachmentList);

            personCertifyCompetencyList.add(personCertifyCompetency);
        }

        return personCertifyCompetencyList;
    }

    private Map<String,String> getHrSessionId() throws  ParserConfigurationException, IOException, SAXException {
        String sessionParam = this.getSessionParam();
        CloseableHttpResponse response = this.cretPost(sessionUrl, sessionParam,null);
        //获取cookies
        StringBuilder cookieSb = new StringBuilder();
        Header[] allHeaders = response.getAllHeaders();
        for (Header headr : allHeaders) {
            if(headr.getName() != null && headr.getName().equalsIgnoreCase("Set-Cookie")) {
                String cookie = headr.getValue();
                    cookieSb.append(cookie).append(";");
                }
        }
        //获取结果实体
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        String sessionId = this.getSessionInfo(result);
        Map<String, String> map = new HashMap<>();
        map.put("sessionId",sessionId);
        map.put("cookie",cookieSb.toString());
        return map;
    }



    public  CloseableHttpResponse  cretPost(String url,String param,String cookie) {
        CloseableHttpClient closeableHttpClient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        // 创建Httpclient对象
        closeableHttpClient = HttpClients.createDefault();
        try{
            // 创建http GET请求
            httpPost = new HttpPost(url);
            //封装请求参数
            if(param != null) {
                StringEntity stringEntity = new StringEntity(param, ContentType.TEXT_XML);
                httpPost.setEntity(stringEntity);
            }
            //封装头部信息
            httpPost.addHeader("Content-Type","text/xml");
            httpPost.addHeader("charset","utf-8");
            httpPost.addHeader("apiKey", apiKey);
            httpPost.addHeader("SOAPAction", null);
            httpPost.addHeader("SOAPAction", null);
            // 设置登录接口的cookie到业务接口请求头
            if(cookie!=null){
                httpPost.addHeader("Cookie", cookie);
            }
            //返回信息；
            response = closeableHttpClient.execute(httpPost);
            //获取结果实体
//            HttpEntity entity = response.getEntity();
//            int state = response.getStatusLine().getStatusCode();
//            String result = EntityUtils.toString(entity, "UTF-8");
            return response;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, exception.getMessage(), exception);
        } finally {
            try{
                httpPost.releaseConnection();
            }catch (Exception e){

            }
            if(closeableHttpClient != null){
                try{
                    closeableHttpClient.close();
                } catch (Exception e){

                }
            }
            if(response != null){
                try{
                    response.close();
                } catch (Exception e){

                }
            }
        }
    }

    public static void main(String[] args)  {
        String url = "http://api.cfhec.net/env-101/por-1901/hr/hrlogin/EASLogin";
        String apiKey="1R4xxlzZQj1wV643K9d6erJ7g4y89Zwj";
        String xminfo = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" \n" +
                "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.app.webservice.shr.kingdee.com\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <web:login soapenv:encodingStyle=\"http://schemas.xmlsoao.org/soap/encoding/\">\n" +
                "         <userName xsi:type=\"xsd:string\">test01</userName>\n" +
                "         <password xsi:type=\"xsd:string\">Qwer@123</password>\n" +
                "         <slnName xsi:type=\"xsd:string\">eas</slnName>\n" +
                "         <dcName xsi:type=\"xsd:string\">ceshi230609</dcName>\n" +
                "         <language xsi:type=\"xsd:string\">L2</language>\n" +
                "         <dbType xsi:type=\"xsd:int\">0</dbType>\n" +
                "         <authPattern xsi:type=\"xsd:string\">0</authPattern>\n" +
                "      </web:login>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>\n" +
                "\n";

            CloseableHttpClient closeableHttpClient = null;
            HttpPost httpPost = null;
            CloseableHttpResponse response = null;
            // 创建Httpclient对象
            closeableHttpClient = HttpClients.createDefault();
            try{
                // 创建http GET请求
                httpPost = new HttpPost(url);
                //封装请求参数
                if(xminfo != null) {
                    StringEntity stringEntity = new StringEntity(xminfo, ContentType.TEXT_XML);
                    httpPost.setEntity(stringEntity);
                }
                //封装头部信息
                httpPost.addHeader("Content-Type","text/xml");
                httpPost.addHeader("charset","utf-8");
                httpPost.addHeader("apiKey", apiKey);
                httpPost.addHeader("SOAPAction", null);
                //返回信息；
                response = closeableHttpClient.execute(httpPost);
                //获取结果实体
                HttpEntity entity = response.getEntity();
                int state = response.getStatusLine().getStatusCode();
                String result = EntityUtils.toString(entity, "UTF-8");

                // 获取httpClient响应的请求头header
                Header responseHeader = response.getFirstHeader("X-USER-AUTH");
                HeaderElement[] responseHeaderElements = responseHeader.getElements();

                //请求成功
                //String seessionId = getSessionInfo(result);
                //String userName=SecurityUtils.getSysUser().getUserName();
                //String certParam = getCertParam(seessionId, "2022008083");
                //证件返回信息
                //String certResponse = cretPost("http://api.cfhec.net/env-101/por-1901/hr/QueryPersonInfoService/WSOSFWebserviceFacade", certParam);
                //Map map = getCertInfo(certResponse);
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, exception.getMessage(), exception);
            } finally {
                try{
                    httpPost.releaseConnection();
                }catch (Exception e){

                }
                if(closeableHttpClient != null){
                    try{
                        closeableHttpClient.close();
                    } catch (Exception e){

                    }
                }
                if(response != null){
                    try{
                        response.close();
                    } catch (Exception e){

                    }
                }
            }
        }


    //解析session信息
    public  String getSessionInfo(String xmlStr) throws ParserConfigurationException, IOException, SAXException {
        // 创建 DocumentBuilder 对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(xmlStr.getBytes(Charset.forName("UTF-8")));
        // 解析 SOAP XML 文件
        InputSource in = new InputSource(stream);
        Document document = builder.parse(in);
        // 获取 SOAP Body 元素
        Element body = (Element) document.getElementsByTagName("soapenv:Body").item(0);
        // 获取 GetStockPrice 元素
        Element stockPrice = (Element) body.getElementsByTagName("multiRef").item(0);
        // 获取 StockName 元素的文本内容
        String sessionId = stockPrice.getElementsByTagName("sessionId").item(0).getTextContent();

        return sessionId;
    }

    //封装session请求参数
    public  String getSessionParam()  {
        String sessionParam = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" \n" +
                "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.app.webservice.shr.kingdee.com\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <web:login soapenv:encodingStyle=\"http://schemas.xmlsoao.org/soap/encoding/\">\n" +
                "         <userName xsi:type=\"xsd:string\">"+userName+"</userName>\n" +
                "         <password xsi:type=\"xsd:string\">"+password+"</password>\n" +
                "         <slnName xsi:type=\"xsd:string\">"+slnName+"</slnName>\n" +
                "         <dcName xsi:type=\"xsd:string\">"+dcName+"</dcName>\n" +
                "         <language xsi:type=\"xsd:string\">"+language+"</language>\n" +
                "         <dbType xsi:type=\"xsd:int\">"+dbType+"</dbType>\n" +
                "         <authPattern xsi:type=\"xsd:string\">"+authPattern+"</authPattern>\n" +
                "      </web:login>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>\n" +
                "\n";
        return sessionParam;
    }


    //解析证书信息
    public  Map getCertInfo(String xmlStr) throws ParserConfigurationException, IOException, SAXException {
        Map<String, Object> map = new HashMap<>();
        // 创建 DocumentBuilder 对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(xmlStr.getBytes(Charset.forName("UTF-8")));
        // 解析 SOAP XML 文件
        InputSource in = new InputSource(stream);
        Document document = builder.parse(in);
        // 获取 SOAP Body 元素
        Element body = (Element) document.getElementsByTagName("soapenv:Body").item(0);
        // 获取 proceedOSFReturn 元素
        String certInfo = body.getElementsByTagName("proceedOSFReturn").item(0).getTextContent();
        map = (Map<String, Object>) JSON.parseObject(certInfo, Map.class).get("data");
        return map;
    }
    //封装证书请求参数
    public  String getCertParam(String sessionId,String userName)  {
        String param = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.app.webservice.shr.kingdee.com\">\n" +
                "   <soapenv:Header>\n" +
                "   <ns1:SessionId xmlns:ns1=\"http://login.webservice.bos.kingdee.com\">\n" +
                "   "+sessionId+"\n" +
                "   </ns1:SessionId>\n" +
                "   </soapenv:Header>\n" +
                "   <soapenv:Body>\n" +
                "      <web:proceedOSF soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "         <serviceName xsi:type=\"xsd:string\">QueryPersonInfoService</serviceName>\n" +
                "         <param xsi:type=\"xsd:string\">{\"person_jtgh\":\""+userName+"\"}</param>\n" +
                "      </web:proceedOSF>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>\n";
        return param;
    }
}
