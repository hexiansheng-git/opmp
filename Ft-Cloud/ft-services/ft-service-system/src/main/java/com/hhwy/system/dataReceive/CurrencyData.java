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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：货币数据
 * 作者: fushudong
 * 时间: 2023/09/04
 */
@RestController
public class CurrencyData {

    @Autowired
    private ICurrencyInfoService currencyInfoService;

    @Autowired
    private RedisUtils redisUtils;

    private static final String reqParam = "{ \"ESB\": { \"DATA\": { \"DATAINFOS\": { \"PUUID\": \"1\", \"DATAINFO\": [ { \"zgbnum\": \"\", \"LASTMODIFYRECORDTIME\": \"~\" } ] }, \"SPLITPAGE\": { \"CURRENTPAGE\": \"" + 1 + "\", \"COUNTPERPAGE\": \"500\" } } } }";
    private static final String apikey = "dWimEXY79qmkhj3lHJpF2a50oAjWYeUk";
    private static final String reqUrl = "https://esb.cfhec.net/env-101/por-902/mdm/route/esbmule/services/query/MDM_Q_SJFC_BZ";
    /**
     * 获取币种信息
     *
     * @return
     */
    @GetMapping("/test3")
    public AjaxResult getCuntry() {
        List<CurrencyInfo> saveList = new ArrayList<>();
        int sort = 0;
//        sort = currencyInfoService.selectMaxSort();
        int pageNum = 0;
        for (int i = 0; i < 2 ; i++) {
            pageNum = pageNum + 1;
            String reqParam = "{ \"ESB\": { \"DATA\": { \"DATAINFOS\": { \"PUUID\": \"1\", \"DATAINFO\": [ { \"zgbnum\": \"\", \"LASTMODIFYRECORDTIME\": \"~\" } ] }, \"SPLITPAGE\": { \"CURRENTPAGE\": \"" + pageNum + "\", \"COUNTPERPAGE\": \"200\" } } } }";
            Map<String, String> reqHeader = new HashMap<>();
            reqHeader.put("apikey", apikey);
            String rspStr = HttpUtils.sendPost(reqUrl, reqParam, reqHeader);
            JSONObject jsonObject = JSONObject.parseObject(rspStr);
            JSONObject object = JSONObject.parseObject(jsonObject.get("ESB").toString());
            JSONObject data = JSONObject.parseObject(object.get("DATA").toString());
            JSONObject das = JSONObject.parseObject(data.get("DATAINFOS").toString());
            JSONArray datainfo = JSONObject.parseArray(das.get("DATAINFO").toString());
            if (datainfo == null) {
                return AjaxResult.success("无数据");
            }
            for (int j = 0; j < datainfo.size(); j++) {
                CurrencyInfo info = new CurrencyInfo();
                JSONObject o = (JSONObject) datainfo.get(j);
                String code = o.get("zcurrencyalphabet").toString();
                String name = o.get("zcurrencyname").toString();
                info.setId(IdWorker.createId());
                info.setCreateTime(DateUtils.getNowDate());
                info.setCurrencyName(name);
                info.setCurrencyCode(code);
                info.setSort(j + 1l + sort);
                saveList.add(info);
                redisUtils.hPut("baishanyunCurrencyInfo", code, JSONObject.toJSONString(o));
            }
            sort = datainfo.size();
        }
        //批量入库
        currencyInfoService.dataSync(saveList);
        return AjaxResult.success();
    }
}