package com.hhwy.utils.http;


import com.alibaba.fastjson.JSONObject;
import com.hhwy.utils.exception.CustomBusinessException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

public class HttpRequestUtils {

    private static Logger logger= LoggerFactory.getLogger(HttpRequestUtils.class);

    private static String defaultContentType = "application/json;charset=UTF-8";
    private static int success_status = 200;//请求成功标识

    public static String get(String url) throws CustomBusinessException {
        return get(url, null, null);
    }
    public static String get(String url, Map<String, String> headers) throws CustomBusinessException {
        return get(url, headers, null);
    }
    public static String get(String url, Map<String, String> headers, Map<String, Object> requestObj) throws CustomBusinessException {
        CloseableHttpClient closeableHttpClient = null;
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        // 创建Httpclient对象
        closeableHttpClient = HttpClients.createDefault();
        //封装请求参数
        try{
            String reqParams = "";
            if(requestObj != null) {
                Iterator<String> reqs = requestObj.keySet().iterator();
                while (reqs.hasNext()){
                    String k = reqs.next();
                    reqParams += "&" + k +"=" + requestObj.get(k);
                }
                reqParams = reqParams.substring(1);
            }
            // 创建http GET请求
            httpGet = new HttpGet(url + "?" + reqParams);
            //封装头部信息
//            httpGet.addHeader("Content-Type", defaultContentType);
            if(headers != null){
                Iterator<String> keys = headers.keySet().iterator();
                while (keys.hasNext()){
                    String k = keys.next();
                    httpGet.addHeader(k, headers.get(k));
                }
            }
            //返回信息；
            response = closeableHttpClient.execute(httpGet);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            int state = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(entity, "UTF-8");
            logger.info(String.format("请求：%s, 返回值：%s", httpGet.getURI().toString(), result));
            if(success_status == state){
                //请求成功
                return result;
            }else{
                throw new CustomBusinessException("请求异常");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, exception.getMessage(), exception);
        } finally {
            try{
                httpGet.releaseConnection();
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


    public static String post(String url) throws CustomBusinessException {
        return post(url, null, null);
    }
    public static String post(String url, Map<String, String> headers) throws CustomBusinessException {
        return post(url, headers, null);
    }
    public static String post(String url, Map<String, String> headers, Map<String, Object> requestObj) throws CustomBusinessException {
        CloseableHttpClient closeableHttpClient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        // 创建Httpclient对象
        closeableHttpClient = HttpClients.createDefault();
        try{
            // 创建http GET请求
            httpPost = new HttpPost(url);
            //封装请求参数
            if(requestObj != null) {
                StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(requestObj), ContentType.APPLICATION_JSON);
                httpPost.setEntity(stringEntity);
            }
            //封装头部信息
            httpPost.addHeader("Content-Type", defaultContentType);
            if(headers != null){
                Iterator<String> keys = headers.keySet().iterator();
                while (keys.hasNext()){
                    String k = keys.next();
                    httpPost.addHeader(k, headers.get(k));
                }
            }
            //返回信息；
            response = closeableHttpClient.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            int state = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(entity, "UTF-8");
            logger.info(String.format("请求：%s, 返回值：%s", httpPost.getURI().toString(), result));
            if(success_status == state){
                //请求成功
                return result;
            }else{
                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "请求异常！");
            }
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

}
