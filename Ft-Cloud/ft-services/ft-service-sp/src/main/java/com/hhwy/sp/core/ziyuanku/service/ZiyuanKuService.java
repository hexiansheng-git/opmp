package com.hhwy.sp.core.ziyuanku.service;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.http.HttpUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.core.ziyuanku.vo.TProfessionalInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZiyuanKuService {

    public AjaxResult getData(){
        String s = HttpUtils.sendPost("", "", null);
        List<TProfessionalInfo> list = JSONObject.parseArray(s, TProfessionalInfo.class);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return AjaxResult.success(list);
    }
}
