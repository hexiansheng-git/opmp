package com.hhwy.system.dataReceive;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.http.HttpUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.system.currency.service.ICurrencyInfoService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.redisUtil.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：货币数据
 * 作者: fushudong
 * 时间: 2023/09/04
 */
public class CurrencyData {

    @Autowired
    private ICurrencyInfoService currencyInfoService;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取币种信息
     *
     * @return
     */
    @PostMapping("/test3")
    public AjaxResult getCuntry() {
//        String url="https://esb.cfhec.net/env-101/por-902/mdm/route/esbmule/services/query/MDM_Q_SJFC_BZ";
//        Map<String, String> headerMap=new HashMap<>();
//        headerMap.put("apikey","dWimEXY79qmkhj3lHJpF2a50oAjWYeUk");
//        String s = HttpUtils.sendPost(url, null, headerMap);
//        System.out.println(s);
        int count = currencyInfoService.selectMaxSort();
        Integer t = 0;
        List<CurrencyInfo> rstList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            t = t + 1;
            String param = "{ \"ESB\": { \"DATA\": { \"DATAINFOS\": { \"PUUID\": \"1\", \"DATAINFO\": [ { \"zgbnum\": \"\", \"LASTMODIFYRECORDTIME\": \"~\" } ] }, \"SPLITPAGE\": { \"CURRENTPAGE\": \"" + t + "\", \"COUNTPERPAGE\": \"200\" } } } }";
            String url = "https://esb.cfhec.net/env-101/por-902/mdm/route/esbmule/services/query/MDM_Q_SJFC_BZ";
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("apikey", "dWimEXY79qmkhj3lHJpF2a50oAjWYeUk");
            String s = HttpUtils.sendPost(url, param, headerMap);
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONObject object = JSONObject.parseObject(jsonObject.get("ESB").toString());
            JSONObject data = JSONObject.parseObject(object.get("DATA").toString());
            JSONObject das = JSONObject.parseObject(data.get("DATAINFOS").toString());
            JSONArray datainfo = JSONObject.parseArray(das.get("DATAINFO").toString());
            for (int j = 0; j < datainfo.size(); j++) {
                CurrencyInfo info = new CurrencyInfo();
                JSONObject o = (JSONObject) datainfo.get(j);
                String code = o.get("zcurrencyalphabet").toString();
                String name = o.get("zcurrencyname").toString();
                info.setId(IdWorker.createId());
                info.setCreateTime(DateUtils.getNowDate());
                info.setCurrencyName(name);
                info.setCurrencyCode(code);
                info.setSort(count + 1l + j);
                rstList.add(info);
                redisUtils.hPut("baishanyunCurrencyInfo", code, JSONObject.toJSONString(o));
                //批量入库
                currencyInfoService.batchInsert(rstList);
                return AjaxResult.success();
            }
        }
        return null;
    }
}