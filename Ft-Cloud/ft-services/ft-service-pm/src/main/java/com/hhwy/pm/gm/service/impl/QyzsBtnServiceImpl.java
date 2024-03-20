package com.hhwy.pm.gm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.enums.QyzsBtnEnum;
import com.hhwy.pm.gm.service.IQyzsBtnService;
import com.hhwy.pm.utils.HttpHeadersUtils;
import com.hhwy.pm.utils.RestTemplateUtils;
import com.hhwy.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *  总部版知识库按钮名称获取
 * <br/>@Author:       wk
 * <br/>@CreateDate:   2024/3/19 17:07   
 * <br/>@UpdateUser:   wk   
 * <br/>@UpdateDate:   2024/3/19 17:07    
 * <br/>@UpdateRemark: 说明本次修改内容 
 * <br/>@Version:      v1.0    
 */
@Service
public class QyzsBtnServiceImpl implements IQyzsBtnService {
    @Value("${gm.back-url}")
    private String gmUrl;
    
    @Override
    public Map qyzsSystemManageMethod(QyzsBtnEnum e) {
        String url = gmUrl + "/gm/qyzsSystemManageMethod/list?ptVar1={ptVar1}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(headers);
        AjaxResult result = RestTemplateUtils.get(url, httpEntity,AjaxResult.class, ObjectUtils.toMap("ptVar1",e.key()));
        return parse(result,e,"methodName");
    }

    @Override
    public Map qyzsFileMode(QyzsBtnEnum e) {
        String url = gmUrl + "/gm/qyzsFileMode/list?ptVar1={ptVar1}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(headers);
        AjaxResult result = RestTemplateUtils.get(url, httpEntity,AjaxResult.class, ObjectUtils.toMap("ptVar1",e.key()));
        return parse(result,e,"fileName");
    }

    @Override
    public Map qyzsConstructionManageMethod(QyzsBtnEnum e) {
        String url = gmUrl + "/gm/qyzsConstructionManageMethod/list?ptVar1={ptVar1}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity httpEntity = new HttpEntity<>(headers);
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("ptVar1",e.key());
        AjaxResult result = RestTemplateUtils.get(url,httpEntity,AjaxResult.class, map);
        return parse(result,e,"methodName");
    }

    @Override
    public Map qyzsConstructionFileMode(QyzsBtnEnum e) {
        String url = gmUrl + "/gm/qyzsConstructionFileMode/list?ptVar1={ptVar1}";
        HttpHeaders headers = HttpHeadersUtils.getCommonHeaders();
        HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(headers);
        AjaxResult result = RestTemplateUtils.get(url, httpEntity,AjaxResult.class, ObjectUtils.toMap("ptVar1",e.key()));
        return parse(result,e,"fileName");
    }

    private Map parse(AjaxResult result, QyzsBtnEnum e, String fieldName){
        String btnName = e.key();
        if(!AjaxResult.isSuccess(result)){
            return ObjectUtils.toMap("btnName", btnName,"filegroupid","","msg","总部版本接口返回失败");
        }
        List<Map> datas = JSONObject.parseArray(JSONObject.toJSONString(((Map)result.getData()).get("items")), Map.class);
        String filegroupid = "";
        if(datas.size() > 0){
            String sort = ObjectUtils.nvlString(datas.get(0).get("ptVar2"));
            String temp = ObjectUtils.nvlString(datas.get(0).get(fieldName));
            filegroupid = ObjectUtils.nvlString(datas.get(0).get("fileGroupId"));
            btnName = sort+"、"+temp;
        }
        return ObjectUtils.toMap("btnName", btnName,"filegroupid",filegroupid);
    }
    
}
