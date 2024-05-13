package com.hhwy.utils.dict;/**
 * @description TODO
 * @date 2022-11-28 15:59
 * @author zq
 */

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * 格式化多层级字典项 (目前只支持俩级)
     * 1,3 > 工程类型/公路
     * @param dictType 
     * @param val
     * @return
     */
    public static String formatMultiDict(String dictType,String val){
        if(StringUtils.isBlank(dictType) || StringUtils.isBlank(val))
            return val;
        AjaxResult result = systemServiceApi.dictType(dictType);
        List<SysDictData> list = JSONObject.parseArray(JSONObject.toJSONString(result.get("data")),SysDictData.class);
        if(CollectionUtils.isEmpty(list))
            return val;
        Map<Long,SysDictData> dictMap = list.stream().collect(Collectors.toMap(r->r.getDictDataId(), r->r));
        Map<String,String> map = list.stream().collect(
                Collectors.toMap(r->{
                            String parent = (r.getParentId()==null || r.getParentId().equals(0L) )?"":dictMap.get(r.getParentId()).getDictValue()+",";
                            return parent+r.getDictValue();
                        }
                        , r->{
                            String parent = (r.getParentId()==null || r.getParentId().equals(0L) )?"":dictMap.get(r.getParentId()).getDictLabel()+"/";
                            return parent+r.getDictLabel();
                        })
        );
        String mixTypeStr = ObjectUtils.nvlString(map.get(val),val);
        return mixTypeStr;
    }
}
