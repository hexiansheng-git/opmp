package com.hhwy.system.controller;/*
 * @Description: TODO
 * @Author: zgx$
 * @Date: $
 **/

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.common.security.service.TokenService;
import com.hhwy.system.api.domain.SysMenu;
import com.hhwy.system.api.domain.SysUser;
import com.hhwy.system.core.domain.SysDictData;
import com.hhwy.system.core.service.IMenuService;
import com.hhwy.system.core.service.ISysDictTypeService;
import com.hhwy.system.core.service.ISysMenuV2Service;
import com.hhwy.system.service.ISysPmService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.record.PageBreakRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public AjaxResult reverseDictList(@RequestParam("dictType") String dictType, @RequestParam("dictValue") String dictLabel) {
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


}
