package com.hhwy.pm.jdgl.statistics.util;

import com.hhwy.utils.tree.TreeNode;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class TreeCountUtils<T extends TreeNode> {

    public List<T> upCountValue(List<T> treeList, String columnName) {

        if(CollectionUtils.isNotEmpty(treeList)) {
            List<T> leafList = new ArrayList<>();
            for (T t : treeList) {
                Long id = t.getId();
                T t1 = treeList.stream().filter(vo -> id.equals(vo.getPid())).findFirst().orElse(null);
                if(t1 == null) leafList.add(t);
            }

            iteratorCountValue(treeList, leafList, columnName);
        }

        return treeList;

    }

    public void iteratorCountValue(List<T> treeList, List<T> leafList, String columnName) {

        if(CollectionUtils.isEmpty(treeList) || CollectionUtils.isEmpty(leafList)) {
            return ;
        }

        Map<Long, BigDecimal> valueMap = new HashMap<>();
        for (T t : leafList) {
            Long pid = t.getPid();
            T t1 = treeList.stream().filter(vo -> vo.getId().equals(pid)).findFirst().orElse(null);
            if(t1 != null) {
                BigDecimal thisValue = BigDecimal.ZERO;
                try {
                    Field declaredField = t.getClass().getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    thisValue = (BigDecimal)declaredField.get(t);
                    if(thisValue.compareTo(BigDecimal.ZERO) > 0) {
                        System.out.println(t1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BigDecimal bigDecimal = valueMap.get(pid) == null ? BigDecimal.ZERO : valueMap.get(pid);
                valueMap.put(pid, bigDecimal.add(thisValue));
            }
        }

        List<T> parentList = new ArrayList<>();
        Set<Long> keys = valueMap.keySet();
        for (Long id : keys) {
            T t = treeList.stream().filter(vo -> id.equals(vo.getId())).findFirst().orElse(null);
            if(t != null) {
                try {
                    Field declaredField = t.getClass().getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(t, valueMap.get(id));
                    if(valueMap.get(id).compareTo(BigDecimal.ZERO) > 0) {
                        System.out.println(valueMap.get(id));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                parentList.add(t);
            }
        }

        iteratorCountValue(treeList, parentList, columnName);

    }

}
