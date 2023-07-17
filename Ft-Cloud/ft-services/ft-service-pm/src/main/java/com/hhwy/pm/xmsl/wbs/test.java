package com.hhwy.pm.xmsl.wbs;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;

public class test {

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSONString(new XmslWbs(), SerializerFeature.WriteMapNullValue));
    }
}
