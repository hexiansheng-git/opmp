package com.hhwy.utils.excelUtil;

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
    },
    importPlanningArrange("importPlanningArrange.xls", "便道部署") {
        @Override
        public Map<String, List> pullLists() {
            return new HashMap<>();
        }
    },
    importQqchAppInnovatePlan("importQqchAppInnovatePlan.xlsx","四新应用及创新计划导入") {
        @Override
        public Map<String, List> pullLists() {
            Map<String, List> map = new HashMap<>();
            List<String> appOrInnovateList = handleDict("app_or_innovate");
            map.put("app_or_innovate", appOrInnovateList);
            List<String> fourNewsTypeList = handleDict("four_news_type");
            map.put("four_news_type", fourNewsTypeList);
            return map;
        }
    },
    importQqchCraftDeclarePlan("importQqchCraftDeclarePlan.xlsx","工艺工法申报计划导入模板") {
        @Override
        public Map<String, List> pullLists() {
            Map<String, List> map = new HashMap<>();
            List<String> craftGradeList = handleDict("craft_grade");
            map.put("craft_grade", craftGradeList);
            return map;
        }
    },
    importConstructionList("importConstructionList.xls", "方案清单导入") {
        @Override
        public Map<String, List> pullLists() {
            Map<String, List> map = new HashMap<>();
            List<String> schemeTypeList = handleDict("scheme_type");
            map.put("schemeType", schemeTypeList);

            List<String> schemeLevelList = handleDict("scheme_level");
            map.put("schemeLevel", schemeLevelList);

            List<String> dangerLevelList = handleDict("danger_level");
            map.put("dangerLevel", dangerLevelList);

            List<String> commonYesList = handleDict("common_yes");
            map.put("commonYes", commonYesList);
            return map;
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
