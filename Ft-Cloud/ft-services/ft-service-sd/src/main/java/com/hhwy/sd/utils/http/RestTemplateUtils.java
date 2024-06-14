package com.hhwy.sd.utils.http;

import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestTemplateUtils {

    private static RestTemplate restTemplate;

    private static Logger logger= LoggerFactory.getLogger(RestTemplateUtils.class);

    static {
        try {
            restTemplate = SpringUtils.getBean(RestTemplate.class);
        }catch (Exception e){
            logger.error("Error initializing MyClass:【{}】",e.getMessage());
        }

    }

    public static <T> T get(String url, Class<T> responseType, Object ... uriVariables){
        return send(url,HttpMethod.GET,null,responseType,uriVariables);
    }

    public static <T> T get(String url, Class<T> responseType, Map<String,Object> uriVariables){
        return send(url,HttpMethod.GET,null,responseType,uriVariables);
    }

    public static <T> T get(String url, HttpEntity<?> httpEntity, Class<T> responseType, Object ... uriVariables){
        return send(url,HttpMethod.GET,httpEntity,responseType,uriVariables);
    }

    public static <T> T get(String url, HttpEntity<?> httpEntity, Class<T> responseType, Map<String,Object> uriVariables){
        return send(url,HttpMethod.GET,httpEntity,responseType,uriVariables);
    }

    public static <T> T post(String url, Class<T> responseType, Object ... uriVariables){
        return send(url,HttpMethod.POST,null,responseType,uriVariables);
    }

    public static <T> T post(String url, Class<T> responseType, Map<String,Object> uriVariables){
        return send(url,HttpMethod.POST,null,responseType,uriVariables);
    }

    public static <T> T post(String url, HttpEntity<?> httpEntity, Class<T> responseType, Object ... uriVariables){
        return send(url,HttpMethod.POST,httpEntity,responseType,uriVariables);
    }

    public static <T> T post(String url, HttpEntity<?> httpEntity, Class<T> responseType, Map<String,Object> uriVariables){
        return send(url,HttpMethod.POST,httpEntity,responseType,uriVariables);
    }

    public static <T> T send(String url, HttpMethod method, HttpEntity<?> httpEntity, Class<T> responseType, Map<String,Object> uriVariables){
        ResponseEntity<T> exchange = restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
        return analysisResult(exchange);
    }

    public static <T> T send(String url, HttpMethod method, HttpEntity<?> httpEntity, Class<T> responseType, Object ... uriVariables){
        ResponseEntity<T> exchange = restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
        return analysisResult(exchange);
    }

    private static <T> T analysisResult(ResponseEntity<T> exchange){
        if(exchange.getStatusCode().value() != 200){
            throw new CustomException("请求异常，返回状态码:["+exchange.getStatusCode().value()+"]");
        }
        return exchange.getBody();
    }
}
