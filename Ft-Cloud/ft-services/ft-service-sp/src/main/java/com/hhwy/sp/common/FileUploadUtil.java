package com.hhwy.sp.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.exception.CustomException;
import com.hhwy.common.core.utils.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wll
 * 2024/3/1
 */
@Component
public class FileUploadUtil {

    @Value("${fileService.fileCopyUrl}")
    private String fileCopyUrl;

    public String copyFile(String fileGroupId) {
        String result = null;
        if (StringUtils.isBlank(fileGroupId)) {
            return result;
        }
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(fileCopyUrl);
            //封装请求参数
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("fileGroupId", fileGroupId);
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(paraMap));
            httpPost.setEntity(stringEntity);

            //封装头部信息
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("charset", "utf-8");
            httpPost.addHeader("SOAPAction", null);
            //返回信息；
            response = closeableHttpClient.execute(httpPost);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            String resultJson = EntityUtils.toString(entity, "UTF-8");
            Map<String, Object> map = JSONObject.parseObject(resultJson, Map.class);
            if (map != null) {
                Object newFileGroupIdObj = map.get("newFileGroupId");
                if (newFileGroupIdObj != null) {
                    result = newFileGroupIdObj.toString();
                }
            }
            return result;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new CustomException(exception.getMessage(), exception);
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
            } catch (Exception ignored) {
            }
            if (closeableHttpClient != null) {
                try {
                    closeableHttpClient.close();
                } catch (Exception ignored) {

                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (Exception ignored) {

                }
            }
        }
    }
}
