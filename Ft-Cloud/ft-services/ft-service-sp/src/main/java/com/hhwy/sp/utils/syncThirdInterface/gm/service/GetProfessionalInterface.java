package com.hhwy.sp.utils.syncThirdInterface.gm.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.utils.http.HttpUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.core.ziyuanku.vo.TProfessionalInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取总部版专家库
 *
 * @author lcf
 * @date 2023-12-15
 */
@Service
public class GetProfessionalInterface {

    private Logger logger= LoggerFactory.getLogger(GetProfessionalInterface.class);
    @Value("${gm.url}")
    private String url;

    public AjaxResult syncGmProfessional(Map<String,Object> map){
        //拉取总部数据
        logger.info("获取总部专家库接口请求参数：【{}】",JSONObject.toJSONString(map));
        String rst = HttpUtils.sendPost(url+"gm/professional/getData", JSONObject.toJSONString(map), null);
        if(StringUtils.isEmpty(rst)){
            return AjaxResult.error("数据获取异常");
        }
        JSONObject result= JSONObject.parseObject(rst);
        Object data = result.get("data");
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(data), JSONObject.class);
        Object items = jsonObject.get("items");
        Object total = jsonObject.get("total");
        List<TProfessionalInfo> list = JSONArray.parseArray(JSONObject.toJSONString(items), TProfessionalInfo.class);
        Map<String,Object> rstMap=new HashMap<>();
        rstMap.put("items",list);
        rstMap.put("total",total);
        return AjaxResult.success(rstMap);
    }


}
