package com.hhwy.sp.utils.syncThirdInterface.wushe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.http.HttpUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.utils.syncThirdInterface.wushe.vo.GetMaterialInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取物设设备进场记录
 *
 * @author lcf
 * @date 2023-12-15
 */
@Service
public class GetMaterialInfoInterface {

    private Logger logger= LoggerFactory.getLogger(GetMaterialInfoInterface.class);

    @Value("${baishanyun.wushe.url}")
    private String url;
    @Value("${baishanyun.wushe.apikey}")
    private String apikey;


    /**
     * 同步物设信息
     * curl -X POST -H 'Accept: text/html,text/plain,application/xhtml+xml,application/xml,application/json' -H 'Content-Type: application/json'
     * -H 'apikey: pJWDnryyVsmDiiPeEI5Bfv0B4Lm3nOoI' -d '{ "projectCode": "123", "params": { "manageCodes": [ "dfgdfg", "dfgdfg" ] } }'
     * 'http://esb.cfhec.net/env-101/hhwy-wsxt/wsxt/xg/fms/xcsb/chooseEqu/getEquipList'
     *
     * @param map
     * @return
     */
    public AjaxResult syncMaterialInfo(@RequestBody Map<String,Object> map){
        Map<String,String> headerMap=new HashMap();
        headerMap.put("apikey",apikey);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("projectCode",map.get("projectCode"));
        JSONObject params=new JSONObject();
        params.put("manageCodes",map.get("manageCodes"));
        jsonObject.put("params",params);
        String rst = HttpUtils.sendPost(url, jsonObject.toJSONString(), headerMap);
        logger.info("获取物设系统【设备进场记录】接口返回结果信息【{}】",rst);
        if(StringUtils.isBlank(rst)){
            logger.error("物设接口空了。。。。。。。。。。。。。。");
            return AjaxResult.error("接口返回结果为空");
        }
        try {
            //响应码校验
            JSONObject object = JSONObject.parseObject(rst);
            String code = (String) object.get("code");
            if(!code.equals("200")){
                return AjaxResult.error("获取物设系统【设备进场记录】接口返回响应码异常");
            }
            List<GetMaterialInfoVo> dataList = JSONArray.parseArray(JSONObject.toJSONString(object.get("data")), GetMaterialInfoVo.class);
            return AjaxResult.success(dataList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxResult.success();
    }

}
