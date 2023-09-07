package com.hhwy.system.dataReceive;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.http.HttpUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：国家数据
 * 作者: fushudong
 * 时间: 2023/09/04
 */
@RestController
public class CountryData {

    @Autowired
    private RedisUtils redisUtils;

    private static final String reqParam = "{ \"ESB\": { \"DATA\": { \"DATAINFOS\": { \"PUUID\": \"1\", \"DATAINFO\": [ { \"zgbnum\": \"\", \"LASTMODIFYRECORDTIME\": \"~\" } ] }, \"SPLITPAGE\": { \"CURRENTPAGE\": \""+1+"\", \"COUNTPERPAGE\": \"500\" } } } }";
    private static final String reqUrl = "https://esb.cfhec.net/env-101/por-902/mdm/route/esbmule/services/query/MDM_Q_SJFC_GJDQ";
    private static final String apikey = "rPFLbaT5mWodSwjulaYENn8kMigDvyzj";

    @GetMapping("/test2")
    public AjaxResult getCountryInfo(){
        Map<String, String> reqHeader = new HashMap<>();
        reqHeader.put("apikey", apikey);
        String respStr = HttpUtils.sendPost(reqUrl, reqParam, reqHeader);
        JSONObject jsonObject = JSONObject.parseObject(respStr);
        JSONObject object = JSONObject.parseObject(jsonObject.get("ESB").toString());
        JSONObject data = JSONObject.parseObject(object.get("DATA").toString());
        JSONObject das = JSONObject.parseObject(data.get("DATAINFOS").toString());
        JSONArray datainfo = JSONObject.parseArray(das.get("DATAINFO").toString());
        for (int j = 0; j <datainfo.size() ;j++) {
            JSONObject o = (JSONObject)datainfo.get(j);
            String code = o.get("CODE").toString();
            redisUtils.hPut("baishanyunCountryInfo",code, JSONObject.toJSONString(o));
        }
        return AjaxResult.success();
    }
}