package com.hhwy.system.utils;

import com.hhwy.domain.base.system.SysTreeUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tree
 *
 * @date 20221130
 * @author lcf
 */
public class TreeObject {
    /**
     * tree 递归
     *
     * @param list
     * @return
     */
    public static List getDeptTree(List<SysTreeUtil> list) {
        List<SysTreeUtil> newData = new ArrayList<>();
        /* List<SysTreeUtil> list = myCommonMapper.getDeptTree(where);*/
        if(!ObjectNullUtil.isEmpty(list)){
            //把部门信息放到map中
            Map<String,SysTreeUtil> map = new HashMap<>();
            list.stream().forEach(temp -> {
                map.put(temp.getId() + "", temp);
            });
            for(SysTreeUtil temp : list){
                if(!map.containsKey(temp.getPId() + "")){
                    //顶级节点
                    newData.add(temp);
                }
            }
            for(SysTreeUtil temp : list){
                SysTreeUtil parent = map.get(temp.getPId() + "");
                if(!ObjectNullUtil.isEmpty(parent)){ // 不等于null，也就意味着有父节点
                    if(parent.getChildren() == null){
                        parent.setChildren(new ArrayList<SysTreeUtil>());
                    }
                    parent.getChildren().add(temp); // 添加到父节点的ChildList集合下
                    map.put(temp.getPId() + "",parent);  // 把放好的数据放回到map中
                }
            }
        }
        return newData;
    }
}
