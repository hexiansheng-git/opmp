package com.hhwy.system.controller;

import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.system.api.domain.SysTenantDb;
import com.hhwy.system.core.service.ISysDictTypeService;
import com.hhwy.system.core.service.ISysMenuV2Service;
import com.hhwy.system.core.service.ISysTenantDbService;
import com.hhwy.system.core.service.ISysTenantService;
import com.hhwy.system.mapper.SysPmMapper;
import com.hhwy.system.service.ISysPmService;
import com.hhwy.utils.ObjectUtils;
import io.seata.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sysSp")
public class SysSpController {

    @Autowired
    ISysDictTypeService dictTypeService;
    @Autowired
    ISysPmService dictService;
    @Autowired
    ISysMenuV2Service sysMenuService;
    @Autowired
    private ISysTenantService tenantService;
    @Autowired
    private ISysTenantDbService dbService;
    @Autowired
    private SysPmMapper sysPmMapper;

    //查询部分数据使用情况
    @PostMapping("/statisData")
    public String statisData(String type) {
        String loginStr = "登录人数";
        ArrayList<Map> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        Map<String, String> chmap = new HashMap<>();
        //施工方案技术
        if(type.equals("1")) {//施工技术
            map.put("施工方案清单", "sgjs_build_scheme");
            map.put("危大工程清单", "sgjs_build_scheme_list");
            map.put("施工方案评审", "sgjs_build_scheme_review");
            map.put("施工方案进展", "sgjs_build_scheme_review");
        }
        if(type.equals("0")) {
            //勘祭设计
            map.put("设计优化管理", "kcsj_design_optimize");
        }
        if(type.equals("1")) {
            chmap.put("施工技术", "sgjs");
        }
        if(type.equals("0")) {
            chmap.put("勘察设计", "Kcsj");
        }

        //调用数据
        Map<String, Integer> tenantChMap = new HashMap<>();
        for(String key:chmap.keySet()){
            String chValue = chmap.get(key);
            //调用次数
            List<Map> tenantChlist = sysPmMapper.selectChCount(key);
            for(Map item:tenantChlist){
                Integer count = ObjectUtils.toInteger(item.get("count"));
                String tenantKey = ObjectUtils.toString(item.get("tenantkey"));
                tenantChMap.put(tenantKey+"_"+chValue,count);
            }
        }

        List<SysTenant> tenantList = tenantService.selectSysTenantList(new SysTenant());

        //登录情况
        Map<Object, Object> countMap = new HashMap<>();
        List<Map> loginCountList = sysPmMapper.selectCountLogin();
        for(Map item:loginCountList){
            countMap.put(item.get("tenantKey"),item.get("count"));
        }
        //开通账号
        Map<Object, Object> accountMap = new HashMap<>();
        List<Map> acountList = sysPmMapper.selectAccountLogin();
        for(Map item:acountList){
            accountMap.put(item.get("tenantKey"),item.get("count"));
        }
        //数据库信息
        SysTenantDb db = new SysTenantDb();
        if(type.equals("1")){//施工技术
            db.setServiceName("ft-service-sp");
        }
        if(type.equals("0")){
            db.setServiceName("ft-service-sd");
        }
        List<SysTenantDb> dbList = dbService.selectSysTenantDbList(db);
        Map<String, String> dbMap = new HashMap<>();
        for(SysTenantDb item:dbList){
            dbMap.put(item.getTenantKey(),item.getDbName());
        }
        //查询
        for(SysTenant item:tenantList){
            String tenantKey = item.getTenantKey();
            //结果数据集合
            Map<Object, Object> dataMap = new HashMap<>();
            dataMap.put("projectName",item.getTenantName());
            dataMap.put("projectCode",item.getTenantKey());
            dataMap.put("loginCount",countMap.get(item.getTenantKey()));
            dataMap.put("account",accountMap.get(item.getTenantKey()));

            String dbStr = dbMap.get(tenantKey);
            if(StringUtils.isBlank(dbStr)){
                continue;
            }
            for(String key:map.keySet()){
                String value = map.get(key).toString();
                int count = sysPmMapper.selectDbCount(dbStr,value);
                dataMap.put(value,count);
            }
            for(String key:chmap.keySet()){
                String value = chmap.get(key);
                Integer chcount = tenantChMap.get(tenantKey + "_" + value);
                dataMap.put(value,chcount);
            }
            list.add(dataMap);
        }
        Map<Object, Integer> menucountMap = new HashMap<>();
        Map<Object, Integer> projectcountMap = new HashMap<>();
        for(Map item:list){
//            map.put("项目设立合同信息","xmsl_contract_info");
//            map.put("前期策划小组","qqch_work_group");
//            map.put("前期策划工作计划","qqch_work_plan");
//            map.put("进度管理-年度产值计划","jdgl_year_plan");
//            map.put("进度管理-季度产值计划","jdgl_quarter_plan");
//            map.put("进度管理-月度产值计划","jdgl_month_plan");
//            map.put("进度管理-每周产值计划","jdgl_week_plan");
//            map.put("进度管理-进度填报","jdgl_day_schedule");
            Integer loginCount= ObjectUtils.toInteger(item.get("loginCount"));
            if(loginCount!=0){
                if(menucountMap.containsKey(loginStr)){
                    menucountMap.put(loginStr,menucountMap.get(loginStr)+loginCount);
                }else{
                    menucountMap.put(loginStr,loginCount);
                }
                if(projectcountMap.containsKey(loginStr)){
                    projectcountMap.put(loginStr,projectcountMap.get(loginStr)+1);
                }else{
                    projectcountMap.put(loginStr,1);
                }
            };

            for(String key:map.keySet()){
                String value = map.get(key).toString();
                Integer count= ObjectUtils.toInteger(item.get(value));
                if(count!=0){
                    if(menucountMap.containsKey(key)){
                        menucountMap.put(key,menucountMap.get(key)+count);
                    }else{
                        menucountMap.put(key,count);
                    }
                    if(projectcountMap.containsKey(key)){
                        projectcountMap.put(key,projectcountMap.get(key)+1);
                    }else{
                        projectcountMap.put(key,1);
                    }
                };
            }
            Integer bianzhiCount=0;
            for(String key:chmap.keySet()){
                String value = chmap.get(key).toString();
                Integer c = ObjectUtils.toInteger(item.get(value),0);
                bianzhiCount+=c;
            }
            item.put("bianzhi",bianzhiCount);

        }
        StringBuffer resStr = new StringBuffer();
        resStr.append(loginStr)
                .append(":")
                .append(projectcountMap.get(loginStr))
                .append("个项目")
                .append(menucountMap.get(loginStr))
                .append("人次;")
                .append("\n");
        for(String key:map.keySet()){
            resStr.append(key)
                    .append(":")
                    .append(projectcountMap.get(key))
                    .append("个项目")
                    .append(menucountMap.get(key))
                    .append("条数据;")
                    .append("\n");
        }

        return  resStr.toString();
    }
}
