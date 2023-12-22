package com.hhwy.system.controller;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.system.api.domain.*;
import com.hhwy.system.core.processor.ITenantProcessor;
import com.hhwy.system.core.service.*;
import com.hhwy.system.mapper.SysPmMapper;
import com.hhwy.system.service.IDeptService;
import com.hhwy.system.service.ISysPmService;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/syspm")
public class SysPmController {
    @Autowired
    ISysDictTypeService dictTypeService;

    @Autowired
    ISysPmService dictService;

    @Autowired
    ISysMenuV2Service sysMenuService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IMenuService menuService;
    @Autowired
    private ISysTenantService tenantService;
    
    @Autowired
    private ISysTenantDbService  dbService;


    @Autowired
    private IDeptService deptService;


    @Autowired
    SysPmMapper sysPmMapper;
    /**
     * 查询字典项，导出使用  , 根据value查询 label
     * @param dictType
     * @param dictValue
     * @return
     */
    @GetMapping("/resolveDict")
    public AjaxResult resolveDictList(@RequestParam("dictType") String dictType, @RequestParam("dictValue") String dictValue) {
        List<SysDictData> list = dictTypeService.selectDictDataByTypeAndValues(dictType, dictValue);
        return AjaxResult.success(list);
    }

    /**
     * 查询字典项，导入使用  , 根据label查询 value
     * @param dictType
     * @param dictValue
     * @return
     */
    @GetMapping("/reverseDict")
    public AjaxResult reverseDictList(@RequestParam("dictType") String dictType, @RequestParam("dictLabel") String dictLabel) {
        List<SysDictData> list = dictService.selectDictValueByTypeAndLabel(dictType, dictLabel);
        return AjaxResult.success(list);
    }

    /**
     * 根据字典类型查询字典数据
     * @param dictType
     * @return
     */
    @GetMapping("/typeData")
    public AjaxResult dictType(@RequestParam String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList();
        }
        return AjaxResult.success(data);
    }
    //根据菜单名称获取下级菜单信息集合，工作计划获取菜单
    @GetMapping("/menu/qqch")
    public AjaxResult getQqchMenu(@RequestParam String name) {
        SysUser sysUser = this.tokenService.getSysUser();
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuType("menu");
        List<SysMenu> list = this.menuService.selectMenuTreeList(sysMenu, sysUser);
        List<SysMenu> resList = this.findChildTree(list, name);
        return AjaxResult.success(resList);
    }

    public List<SysMenu> findChildTree(List<SysMenu> list,String name){
        for(SysMenu item:list){
            String menuTitle = item.getTitle();
            if(name.equals(menuTitle)){
                return item.getChildren();
            }else {
                if(CollectionUtils.isNotEmpty(item.getChildren())){
                    List<SysMenu> l = this.findChildTree(item.getChildren(), name);
                    if(CollectionUtils.isNotEmpty(l)){
                        return  l;
                    }
                }
            }
        }
        return null;
    }

    @RequestMapping({"/importDict"})
    public AjaxResult importDict(MultipartFile file) {
        AjaxResult resu = null;
        try {
            dictService.importDict(file);
            resu = AjaxResult.success("导入成功");
        }catch (Exception var4) {
            var4.printStackTrace();
            resu = AjaxResult.error("导入失败"+var4.getMessage());
        }
        return resu;
    }

    /**
     * 获取全部租户信息
     * @return
     */
    @PostMapping("/tenantList")
    public List<SysTenant> tenantList() {
        SysTenant tenant = new SysTenant();
        tenant.setTenantStatus("0");
        List<SysTenant> tenantList = tenantService.selectSysTenantList(tenant);
        return tenantList;
    }

    /**
     * 查询档期啊用户项目+区域信息
     * @return
     */
    @PostMapping("/prjInfo")
    public Map prjInfo() {
        Map<Object, Object> res = new HashMap<>();
        SysUser user = tokenService.getSysUser();
        SysDept dept = user.getDept();
        List<SysDept> deptList = deptService.selectPrjInfo(dept.getDeptId(), dept.getAncestors());
        for(SysDept item:deptList){
            if(item.getDeptType().equals("prjInfo")){
                res.put("prjName",item.getDeptName());
                res.put("prjId",item.getPtVar5());
            }
            if(item.getDeptType().equals("region")){
                res.put("regionName",item.getDeptName());
                res.put("regionId",item.getDeptId());
            }
        }
        return res;
    }


    //生成同步sql
    @PostMapping("/createSql")
    public String createSql(@RequestBody Map<String,String> map) {
        String sql=map.get("sql");
        String flag=map.get("flag");
        StringBuffer sb = new StringBuffer();
        SysTenantDb db = new SysTenantDb();
        db.setServiceName(map.get("serviceName"));//服务名称
        List<SysTenantDb> dbList = dbService.selectSysTenantDbList(db);
        for(SysTenantDb item:dbList){
            String sbStr = new String(sql);
            String newStr = sbStr.replaceAll(flag, item.getDbName());
            sb.append(newStr);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String sql="ALTER TABLE `ft_service_pm_pj2022003778`.`qqch_perform_inspection` MODIFY COLUMN `pt_var2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程发起人' AFTER `pt_var1`;\n" +
                "\n" +
                "ALTER TABLE `ft_service_pm_pj2022003778`.`qqch_person_control_plan` ADD COLUMN `pid` bigint(20) NULL DEFAULT NULL COMMENT '父id' AFTER `id`;\n" +
                "\n" +
                "ALTER TABLE `ft_service_pm_pj2022003778`.`qqch_qc_topic_list` MODIFY COLUMN `pt_var5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留字段5' AFTER `pt_var4`;\n" +
                "\n" +
                "ALTER TABLE `ft_service_pm_pj2022003778`.`xmsl_contract_info` MODIFY COLUMN `pt_var1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预留字段1  调整时返回生效版本id' AFTER `del_flag`;\n" +
                "\n" +
                "ALTER TABLE `ft_service_pm_pj2022003778`.`xmsl_draw_review_source_material` DROP INDEX `main_id`;\n" +
                "\n" +
                "ALTER TABLE `ft_service_pm_pj2022003778`.`xmsl_draw_review_source_material` ADD INDEX `main_id`(`main_id`, `wbs_code`, `list_code`) USING BTREE;\n";
        StringBuffer sb = new StringBuffer();
        SysTenantDb db = new SysTenantDb();
        ArrayList<String> list = new ArrayList<>();
        list.add("ft-1111");
        list.add("ft-2222");
        list.add("ft-3333");
        for(String item:list){
            String sbStr = new String(sql);
            String newStr = sbStr.replaceAll("ft_service_pm_pj2022003778", item);
            sb.append(newStr);
        }
        System.out.println(sb.toString());

    }
    @Autowired
    ITenantProcessor  tenantProcessor;
    @PostMapping("/createRoleTest")
    public void createRoleTest(@RequestBody SysTenant sysTenant) {
        tenantProcessor.doPostForInsert(sysTenant);
    }

    //查询部分数据使用情况
    @PostMapping("/createData")
    public String createData() {
        String loginStr = "登录人数";
        
        ArrayList<Map> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("项目设立合同信息","xmsl_contract_info");
        map.put("前期策划小组","qqch_work_group");
        map.put("前期策划工作计划","qqch_work_plan");
//        map.put("前期策划编制","");
//        map.put("前期策划评","");
        map.put("进度管理-年度产值计划","jdgl_year_plan");
        map.put("进度管理-季度产值计划","jdgl_quarter_plan");
        map.put("进度管理-月度产值计划","jdgl_month_plan");
        map.put("进度管理-每周产值计划","jdgl_week_plan");
        map.put("进度管理-进度填报","jdgl_day_schedule");
        List<SysTenant> tenantList = tenantService.selectSysTenantList(new SysTenant());

        //登录情况
        Map<Object, Object> countMap = new HashMap<>();
        List<Map> loginCountList = sysPmMapper.selectCountLogin();
        for(Map item:loginCountList){
            countMap.put(item.get("tenantKey"),item.get("count"));
        }

        //数据库信息
        List<SysTenantDb> dbList = dbService.selectSysTenantDbList(new SysTenantDb());
        Map<String, String> dbMap = new HashMap<>();
        for(SysTenantDb item:dbList){
            dbMap.put(item.getTenantKey(),item.getDbName());
        }
        Map<String, Object> resMap = new HashMap<>();
        //查询
        for(SysTenant item:tenantList){
            //结果数据集合
            Map<Object, Object> dataMap = new HashMap<>();
            dataMap.put("projectName",item.getTenantName());
            dataMap.put("projectCode",item.getTenantKey());
            dataMap.put("loginCount",countMap.get(item.getTenantKey()));

            String dbStr = dbMap.get(item.getTenantKey());
            for(String key:map.keySet()){
                String value = map.get(key).toString();
                int count = sysPmMapper.selectDbCount(dbStr,value);
                dataMap.put(value,count);
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
        }



        sysPmMapper.delete();
        sysPmMapper.batchInsert(list);

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
