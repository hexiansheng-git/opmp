package com.hhwy.utils;

import com.hhwy.utils.exception.CustomBusinessException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 *  httpClient 工具类
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2021/6/22 15:26   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2021/6/22 15:26    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
public class HttpClientUtil {
    /* post请求 */
    public final static String METHOD_POST = "post";
    /* get请求 */
    public final static String METHOD_GET = "get";
    /* put请求 */
    public final static String METHOD_PUT = "put";
    /* delete请求 */
    public final static String METHOD_DELETE = "delete";

    /**
     * 自定义处理请求返回对象类
     *
     * @param <T>
     */
    public abstract static class HandlerResponse<T> {

        public abstract T handler(CloseableHttpResponse response) throws IOException;
    }

    /**
     * 发送get请求，获取string结果
     *
     * @param url
     * @return
     * @throws CustomBusinessException
     */
    public String get(String url) throws CustomBusinessException {
        return HttpClientUtil.get(url, null);
    }

    /**
     * 发送get请求，获取string结果
     *
     * @param url
     * @param map 参数map
     * @return
     */
    public static String get(String url, Map<String, Object> map)
            throws CustomBusinessException {
        String content = null;
        try {
            content = HttpClientUtil.send(url, HttpClientUtil.METHOD_GET, map);
        } catch (CustomBusinessException be) {
            throw new CustomBusinessException(be.getMessage());
        } catch (Exception e) {
            throw new CustomBusinessException(e.getMessage());
        }
        return content;
    }

    /**
     * 发送请求，返回string
     *
     * @param url
     * @param method
     * @return
     * @throws CustomBusinessException
     */
    public static String send(String url, String method)
            throws CustomBusinessException {
        Object result = HttpClientUtil.send(url, method, null, null);
        if (result == null)
            return null;
        return result.toString();
    }

    /**
     * 发送请求
     *
     * @param url
     * @param method
     * @param paramMap
     * @return
     * @throws CustomBusinessException
     */
    public static String send(String url, String method, Map<String, Object> paramMap)
            throws CustomBusinessException {
        Object result = HttpClientUtil.send(url, method, paramMap, null);
        if (result == null)
            return null;
        return result.toString();
    }

    /**
     * 发送请求
     *
     * @param url             url
     * @param method          method
     * @param paramMap
     * @param handlerResponse 返回值处理
     * @param <T>             返回类型
     * @return
     * @throws CustomBusinessException
     */
    public static <T> T send(String url, String method, Map<String, Object> paramMap, HandlerResponse<T> handlerResponse)
            throws CustomBusinessException {
        return HttpClientUtil.send(url, method, paramMap, null, null, handlerResponse);
    }

    /**
     * 发送请求
     *
     * @param url
     * @param method
     * @param paramMap        参数map
     * @param headerMap       头部map
     * @param handlerResponse
     * @param <T>
     * @return
     * @throws CustomBusinessException
     */
    public static <T> T send(String url, String method, Map<String, Object> paramMap, Map<String, String> headerMap, HandlerResponse<T> handlerResponse)
            throws CustomBusinessException {
        return HttpClientUtil.send(url, method, paramMap, headerMap, null, handlerResponse);
    }


    /**
     * 发送请求
     *
     * @param url
     * @param method
     * @param headerMap       请求头参数
     * @param entity          body
     * @param handlerResponse 请求结果处理
     * @param <T>
     * @return 请求结果
     * @throws CustomBusinessException
     */
    public static <T> T sendWithNoParam(String url, String method, Map<String, String> headerMap, HttpEntity entity, HandlerResponse<T> handlerResponse)
            throws CustomBusinessException {
        return HttpClientUtil.send(url, method, null, headerMap, entity, handlerResponse);
    }

    /**
     * 发送请求，并获取原生httpResponse
     * 注意用完后关闭response
     * @param url   请求地址
     * @param method 请求方式，为空默认get请求
     * @param map 参数，可为空
     * @param headerMap 头部参数，可为空
     * @param entity 实体，可为空，用于传body
     * @param handlerResponse 返回结果处理                
     * @return
     */                                                                                         
    public static <T> T send(String url, String method, Map<String,Object> map, Map<String,String> headerMap
            , HttpEntity entity, HandlerResponse<T> handlerResponse)
            throws CustomBusinessException {
        //获取httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //构建uri
        URI uri = null;
        try{
            URIBuilder uriBuilder = new URIBuilder(url);
            if(map != null){
                for(String key:map.keySet()){
                    Object value = map.get(key);
                    uriBuilder.setParameter(key, value+"");
                }
            }
            uri = uriBuilder.build();
        }catch(URISyntaxException use){
            throw new CustomBusinessException("URL语法不正确:"+url);
        }
        //构建请求对象 
        HttpUriRequest httpUriRequest = HttpClientUtil.buildHttpUriRequest(uri,method);
        //构建请求头，如果需要加头的话
        buildHeaderBody(httpUriRequest,headerMap,entity);
        //准备发送请求
        T result = null;
        try{
            CloseableHttpResponse response = httpClient.execute(httpUriRequest);
            if(response.getStatusLine().getStatusCode() != 200){
                throw new CustomBusinessException("请求异常，返回状态码:["+response.getStatusLine().getStatusCode()+"]");
            }
            //若存在自定义解析返回数据，调用它，否则直接将结果转成string
            if(handlerResponse != null){
                result = handlerResponse.handler(response);
            }else{
                result = (T)EntityUtils.toString(response.getEntity(), "UTF-8");                
            }
        }catch(ClientProtocolException ce){
            ce.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }    
        return result;
    }

    /***
     * 上传文件到url
     * @param url              url
     * @param method           请求方式,post,put...
     * @param multipartFile     
     * @param paramMap          其他参数
     * @return
     * @throws CustomBusinessException
     */
    public static String upload(String url, String method, MultipartFile multipartFile, Map<String,Object> paramMap)
            throws CustomBusinessException{
        InputStream inputStream = null;
        try{
            inputStream = multipartFile.getInputStream();    
        }catch(IOException ioe){
            ioe.printStackTrace();
            throw new CustomBusinessException("获取文件InputStream失败，IO异常:"+ioe.getMessage());
        }
        Object resuObj = HttpClientUtil.upload(url,method,inputStream,null,paramMap,multipartFile.getOriginalFilename(),null);
        if(resuObj == null)
            return null;
        return resuObj.toString();
    }

    /***
     * 上传文件到url
     * @param url              url
     * @param method           请求方式,post,put...
     * @param bytes         文件对应的byte数组
     * @param fileName          文件名
     * @param paramMap          其他参数
     * @return
     * @throws CustomBusinessException
     */
    public static String upload(String url, String method,byte[] bytes,String fileName, Map<String,Object> paramMap)
            throws CustomBusinessException{
        Object resuObj = HttpClientUtil.upload(url,method,null,bytes,paramMap,fileName,null);
        if(resuObj == null)
            return null;
        return resuObj.toString();
    }
    
    /***
     * 上传文件到url
     * @param url              url
     * @param method           请求方式,post,put...
     * @param inputStream
     * @param fileName          文件名
     * @param paramMap          其他参数
     * @return
     * @throws CustomBusinessException
     */
    public static String upload(String url, String method, InputStream inputStream,String fileName, Map<String,Object> paramMap)
            throws CustomBusinessException{
        Object resuObj = HttpClientUtil.upload(url,method,inputStream,null,paramMap,fileName,null);
        if(resuObj == null)
            return null;
        return resuObj.toString();
    }
    
    /**
     * 上传文件到url
     * @param url
     * @param method        请求方式
     * @param inputStream   文件对应的inputstream
     * @param bytes         bytes   文件对应的bytes                     
     * @param paramMap      参数map
     * @param fileName      文件名
     * @param handlerResponse   请求返回值处理类
     * @return object       结果
     */
    public static Object upload(String url,String method,InputStream inputStream,byte[] bytes,Map<String,Object> paramMap,String fileName,HandlerResponse handlerResponse )
            throws CustomBusinessException{
        //获取httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //构建uri
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //添加文件到entity，并设置编码以及参数
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.setCharset(Charset.forName("UTF-8"));
        if(inputStream != null)
            builder.addBinaryBody("file", inputStream, ContentType.MULTIPART_FORM_DATA, fileName);
        if(bytes != null)
            builder.addBinaryBody("file", bytes, ContentType.MULTIPART_FORM_DATA, fileName);
        if(paramMap != null){
            for(String key:paramMap.keySet()){
                Object value = paramMap.get(key);
                builder.addTextBody(key, value+"",ContentType.TEXT_PLAIN);
            }
        }
        //构建请求对象 
        HttpUriRequest httpUriRequest = HttpClientUtil.buildHttpUriRequest(uri,method);
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = null;
        if(!(httpUriRequest instanceof  HttpEntityEnclosingRequestBase)){
            throw new CustomBusinessException("上传文件的方式只支持post或者put");        
        }
        //转换为httpEntityEnclosingRequestBase,才能调用setEntity
        httpEntityEnclosingRequestBase = (HttpEntityEnclosingRequestBase)httpUriRequest;
        
//        Header header = new BasicHeader("Content-type","multipart/form-data");
//        httpEntityEnclosingRequestBase.addHeader(header);  
        httpEntityEnclosingRequestBase.setEntity(builder.build());
        //准备发送请求
        CloseableHttpResponse response = null;
        Object result = null;
        try{
            response = httpClient.execute(httpEntityEnclosingRequestBase);
            if(handlerResponse == null){
                result = EntityUtils.toString(response.getEntity(), "UTF-8");                    
            }else{
                result = handlerResponse.handler(response);
            }
        }catch(ClientProtocolException ce){
            ce.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;             
    }
    
    /**
     * 构建HttpUriRequest对象
     * @param uri       uri对象
     * @param method    请求方式
     * @return
     */
    private static HttpUriRequest buildHttpUriRequest(URI uri,String method){
        HttpUriRequest httpUriRequest = null;
        if(StringUtils.isBlank(method))
            method = "get";
        switch (method.toLowerCase()){
            case "post":
                httpUriRequest = new HttpPost(uri);
                break;
            case "get":
                httpUriRequest = new HttpGet(uri);
                break;
            case "delete":
                httpUriRequest = new HttpDelete(uri);
                break;
            case "put":
                httpUriRequest = new HttpPut(uri);
                break;
            default:
                httpUriRequest = new HttpGet(uri);
                break;
        }
        return httpUriRequest;
    }

    /**
     * 构建请求头
     * @param httpUriRequest 
     * @param headerMap  header数据map
     * @param entity  entity
     */
    private static void buildHeaderBody(final HttpUriRequest httpUriRequest,Map<String,String> headerMap,HttpEntity entity){
        if(MapUtils.isNotEmpty(headerMap)) {
            headerMap.keySet().forEach(key->{
                String val = headerMap.get(key);
                if(val != null)
                    httpUriRequest.addHeader(key,val);
            });
        }
        if(entity != null){
            String method = httpUriRequest.getMethod();
            switch (method.toLowerCase()){
                case "post":
                    ((HttpPost)httpUriRequest).setEntity(entity);
                    break;
                case "put":
                    ((HttpPut)httpUriRequest).setEntity(entity);
                    break;
                default:
                    break;
            }
        }
    }
    
}
