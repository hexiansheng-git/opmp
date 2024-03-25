package com.hhwy.utils.dict;/**
 * @description TODO
 * @date 2022-11-28 15:59
 * @author zq
 */

import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.SystemServiceApi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author zq
 * @date 2022年11月28日 15:59
 */

public class DictUtil {
    private static SystemServiceApi systemServiceApi;

    static {
        systemServiceApi = SpringUtils.getBean(SystemServiceApi.class);
    }


    public static LinkedHashMap<String, String> getDictData(String dictType) {
        AjaxResult result = systemServiceApi.dictType(dictType);
        List<LinkedHashMap> list = (List<LinkedHashMap>) result.get("data");
        LinkedHashMap<String, String> dictTypesMap = new LinkedHashMap<>();
        list.stream().forEach(temp -> {
            dictTypesMap.put(temp.get("dictLabel").toString(), temp.get("dictValue").toString());
        });
        return dictTypesMap;
    }

    public static LinkedHashMap<String, String> getDictDataName(String dictType) {
        AjaxResult result = systemServiceApi.dictType(dictType);
        List<LinkedHashMap> list = (List<LinkedHashMap>) result.get("data");
        LinkedHashMap<String, String> dictTypesMap = new LinkedHashMap<>();
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

    /**
     * 字典转换
     * @param source
     * @param dictType
     * @param getDictValue
     * @param setDictLabel
     * @param <T>
     */
    public static <T> void dictValueToLabel(List<T> source, String dictType, Function<T,String> getDictValue, BiConsumer<T,String> setDictLabel){
        LinkedHashMap<String, String> valueLabelMap = getDictDataName(dictType);
        if(valueLabelMap.size() == 0){
           return;
        }
        for (T t : source) {
            String dictValue = getDictValue.apply(t);
            if(StringUtils.isNotBlank(dictValue)){
                String dictLabel = valueLabelMap.get(dictValue);
                if(StringUtils.isNotBlank(dictLabel)){
                    setDictLabel.accept(t,dictLabel);
                }
            }
        }
    }
}
