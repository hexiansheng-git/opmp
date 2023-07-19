package com.hhwy.utils.excelUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hhwy.common.core.utils.SpringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.feign.service.SystemServiceApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 下载模板枚举类
 * @author zq
 * @date 2022年11月24日 20:00
 */
public enum MyDownTemplateEnum {
    importEquPlanDemand("importEquPlanDemand.xls", "总需详情导入") {
        @Override
        public Map<String, List> pullLists() {
            Map<String, List> map = new HashMap<>();
            List<String> isSpecialList = handleDict("is_special");
            map.put("isSpecial", isSpecialList);
            return map;
        }
    },
    importCampsiteStationPlanning("importCampsiteStationPlanning.xls", "营地场站规划导入") {
        @Override
        public Map<String, List> pullLists() {
            return new HashMap<>();
        }
    }
    ;

    private final String name;
    private final String desc;
    MyDownTemplateEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    //枚举类属性和方法
    public abstract Map<String, List> pullLists();

    public Map<String, List> getPullListsByParams(Map<String,String> params){
        return new HashMap<>();
    }

    private static SystemServiceApi systemServiceApi;

    static{
        systemServiceApi = SpringUtils.getBean(SystemServiceApi.class);
    }

    public List<String> handleDict(String dictType){
        AjaxResult result = systemServiceApi.dictType(dictType);
        List<Map> list = (List<Map>) result.get("data");
        ArrayList<String> strings = new ArrayList<>();
        for (Map map : list) {
            strings.add(map.get("dictLabel").toString());
        }
        return strings;
    }

}
