package com.hhwy.utils;

import com.hhwy.system.api.domain.SysMenu;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 平台工具类
 * 将sysMenu转为树形
 * (new PlatMenuTreeUtils()).menuList(authAllList)
 */
public class PlatMenuTreeUtils {
    private List<SysMenu> menuCommon;
    private List<SysMenu> list = new ArrayList();
    private List<Long> idList = new ArrayList();

    public PlatMenuTreeUtils() {
    }

    public List<SysMenu> menuList(List<SysMenu> menu) {
        this.menuCommon = menu;
        Iterator var2 = menu.iterator();

        SysMenu x;
        while(var2.hasNext()) {
            x = (SysMenu)var2.next();
            this.idList.add(x.getMenuId());
        }

        var2 = menu.iterator();

        while(true) {
            do {
                if (!var2.hasNext()) {
                    return this.list;
                }

                x = (SysMenu)var2.next();
            } while(x.getParentId() != null && x.getParentId() != 0L && this.idList.contains(x.getParentId()));

            x.setChildren(this.menuChild(x.getMenuId()));
            this.list.add(x);
        }
    }

    private List<SysMenu> menuChild(Long id) {
        List<SysMenu> lists = new ArrayList();
        Iterator var3 = this.menuCommon.iterator();

        while(var3.hasNext()) {
            SysMenu x = (SysMenu)var3.next();
            if (id.equals(x.getParentId())) {
                x.setChildren(this.menuChild(x.getMenuId()));
                lists.add(x);
            }
        }

        return lists;
    }

    /**
     * 树形转list
     * @param list
     * @return
     */
    public static List<SysMenu> menuTree2List(List<SysMenu> list){
        List<SysMenu> resuList = new ArrayList<>();
        menuTree2List(list,resuList);
        return resuList;
    }

    private static void menuTree2List(List<SysMenu> list,List<SysMenu> resuList){
        if(CollectionUtils.isEmpty(list))
            return;
        for (int i = 0; i < list.size(); i++) {
            SysMenu temp = list.get(i);
            resuList.add(temp);
            menuTree2List(temp.getChildren(),resuList);
        }
    }
}
