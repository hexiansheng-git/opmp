package com.hhwy.utils.dict;/**
 * @description TODO
 * @date 2022-11-28 15:59
 * @author zq
 */

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.SystemServiceApi;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zq
 * @date 2022年11月28日 15:59
 */

public class DictUtil {
    private static SystemServiceApi systemServiceApi;

    static {
        systemServiceApi = SpringUtils.getBean(SystemServiceApi.class);
    }


    public static Map<String, String> getDictData(String dictType) {
        AjaxResult result = systemServiceApi.dictType(dictType);
        List<Map> list = (List<Map>) result.get("data");
        Map<String, String> dictTypesMap = new HashMap<>();
        list.stream().forEach(temp -> {
            dictTypesMap.put(temp.get("dictLabel").toString(), temp.get("dictValue").toString());
        });
        return dictTypesMap;
    }

    public static Map<String, String> getDictDataName(String dictType) {
        AjaxResult result = systemServiceApi.dictType(dictType);
        List<Map> list = (List<Map>) result.get("data");
        Map<String, String> dictTypesMap = new HashMap<>();
        list.stream().forEach(temp -> {
            dictTypesMap.put(temp.get("dictValue").toString(), temp.get("dictLabel").toString());
        });
        return dictTypesMap;
    }

    public static List<String> getDictLableList(String dictType) {
        AjaxResult result = systemServiceApi.dictType(dictType);
        List<Map> list = (List<Map>) result.get("data");
        List<String> valuelist = new ArrayList<>();
        list.stream().forEach(temp -> {
            valuelist.add(temp.get("dictLabel").toString());
        });
        return valuelist;
    }

    public static List<String> getDictValueList(String dictType) {
        AjaxResult result = systemServiceApi.dictType(dictType);
        List<Map> list = (List<Map>) result.get("data");
        List<String> valuelist = new ArrayList<>();
        list.stream().forEach(temp -> {
            valuelist.add(temp.get("dictValue").toString());
        });
        return valuelist;
    }
}
