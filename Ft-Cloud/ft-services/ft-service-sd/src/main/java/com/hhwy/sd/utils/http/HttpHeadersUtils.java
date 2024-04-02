package com.hhwy.sd.utils.http;

import com.hhwy.utils.Constant;
import com.hhwy.utils.GmTokenUtils;
import org.springframework.http.HttpHeaders;

public class HttpHeadersUtils {

    public static HttpHeaders getCommonHeaders(){
        String token = GmTokenUtils.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.AUTHORIZATION,token);
        headers.add(Constant.TENANT_KEY,"master");
        return headers;
    }
}
