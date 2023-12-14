package com.hhwy.sp.utils;

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.utils.file.FtFileProperties;
import com.hhwy.utils.http.HttpRequestUtils;
import org.apache.commons.collections4.map.HashedMap;

import java.util.List;
import java.util.Map;

public class FileUtils {

    public static FtFileProperties bean;

    static {
        bean = SpringUtils.getBean(FtFileProperties.class);
    }

    public static void  getFileByGroupIds(List<String> fileGroupIds){
        // 参数
        Map<String, Object> params = new HashedMap<>(1);
        params.put("fileGroupIds", fileGroupIds);
        // 请求头
        Map<String, String> headers = new HashedMap<>(1);
        headers.put("content-type", "application/json");
        // 请求接口 获取结果
        String httpRes = HttpRequestUtils.post(bean.getUrl() + bean.getFileInfoByGroupIdsPath(), headers, params);

    }
}

