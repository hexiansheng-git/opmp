package com.hhwy.excel;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Util {
    private SystemServiceApi systemServiceApi = (SystemServiceApi) SpringUtils.getBean(SystemServiceApi.class);

    public Util() {
    }

    public String resolveDict(String dictType, String dictValue) {
        AjaxResult result = systemServiceApi.resolveDict(dictType, dictValue);
        List<Map> dictDataList=null;
        if(result.get("code").toString().equals("200")){
            dictDataList = JSONArray.parseArray(JSON.toJSONString(result.get("data")), Map.class);
        }
        if(CollectionUtils.isNotEmpty(dictDataList)){
            Map dictData = dictDataList.get(0);
            return ObjectUtils.toString(dictData.get("dictLabel"));
        }
        return null;
    }
    public String reverseDict(String dictType, String dictLabel) {
        AjaxResult result = systemServiceApi.reverseDict(dictType, dictLabel);
        List<Map> dictDataList=null;
        if(result.get("code").toString().equals("200")){
            dictDataList = JSONArray.parseArray(JSON.toJSONString(result.get("data")), Map.class);
        }
        if(CollectionUtils.isNotEmpty(dictDataList)){
            Map dictData = dictDataList.get(0);
            return ObjectUtils.toString(dictData.get("dictValue"));
        }
        return null;
    }

}
