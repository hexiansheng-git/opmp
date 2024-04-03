package com.hhwy.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.ServletUtils;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 *  获取项目版的token 
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2023/8/23 16:13   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2023/8/23 16:13    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
public class GmTokenUtils {
    //token获取地址
    private static String ssoUrl;
    //token获取密钥
    private static String ssoSecrekey;
    private static RedisUtils redisUtils;
    
    static{
        ssoUrl = SpringUtils.getApplicationContext().getEnvironment().getProperty("gm.sso.url");
        ssoSecrekey = SpringUtils.getApplicationContext().getEnvironment().getProperty("gm.sso.secrekey");
        redisUtils = SpringUtils.getBean(RedisUtils.class);
        
    }

    /**
     * 获取master租户的token
     * @return
     */
    public static String getToken(){
        return getToken(ssoUrl,ssoSecrekey,"master");
    }
    
    public static String getToken(String tenantKey){
        return getToken(ssoUrl,ssoSecrekey,tenantKey);
    }
    
    public static String getToken(String ssoUrl,String ssoSecrekey,String tenantKey){
        if(StringUtils.isBlank(tenantKey))
            return "";
        String key = "gmToken::"+tenantKey;
        String entenantKey = EncryptUtils.AESEncode(tenantKey,ssoSecrekey);
        String userName = "admin";
        if (StrUtil.isNotBlank(ServletUtils.getRequest().getHeader("username"))) {
            userName = SecurityUtils.getUserName();
        }
        String enUsername = EncryptUtils.AESEncode(userName,ssoSecrekey);
        //封装请求参数并发送
        String json = JSONObject.toJSONString(ObjectUtils.toMap("tenantKey",entenantKey,"userName",enUsername));
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        String result =  HttpClientUtil.send(ssoUrl, HttpClientUtil.METHOD_POST,
                null, ObjectUtils.toMap(String.class, "tenantKey", tenantKey),
                stringEntity,null);
        //获取返回token
        AjaxResult ajaxResult = JSONObject.parseObject(result, AjaxResult.class);
        Assert.isTrue(AjaxResult.isSuccess(ajaxResult), ObjectUtils.nvlString(ajaxResult.get(AjaxResult.MSG_TAG)));
        JSONObject dataObj = (JSONObject)ajaxResult.get(AjaxResult.DATA_TAG);
        Assert.isTrue(ObjectUtils.isNotBlank(dataObj.get("access_token")),"token获取失败");
        String token = dataObj.get("access_token").toString();
        redisUtils.setAndExpire(key, token, 5, TimeUnit.MINUTES);
        return token;
    }
}
