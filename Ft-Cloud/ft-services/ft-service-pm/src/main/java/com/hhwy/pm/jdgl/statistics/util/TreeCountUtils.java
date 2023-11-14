package com.hhwy.pm.jdgl.statistics.util;

import com.hhwy.utils.tree.TreeNode;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class TreeCountUtils<T extends TreeNode> {

    public List<T> upCountValue(List<T> treeList, String columnName) {

        if(CollectionUtils.isNotEmpty(treeList)) {
            List<T> leafList = new ArrayList<>();
            for (T t : treeList) {
                Long id = t.getId();
                T t1 = treeList.stream().filter(vo -> id.equals(vo.getPid())).findFirst().orElse(null);
                if(t1 == null) leafList.add(t);
            }

            this.toAncestrals(treeList, null);

//            iteratorCountValue(treeList, leafList, columnName);
            iteratorCountValue4Level(treeList, columnName);
        }

        return treeList;

    }

    public void toAncestrals(List<T> treeList, Long pid) {
        if(CollectionUtils.isEmpty(treeList)) {
            return;
        }
        List<T> childList = new ArrayList<>();
        if(pid == null) {
            childList = treeList.stream().filter(vo -> vo.getPid() == null).collect(Collectors.toList());
        } else {
            childList = treeList.stream().filter(vo -> pid.equals(vo.getPid())).collect(Collectors.toList());
        }
        childList.stream().forEach(vo -> {
            vo.setPtVar5(vo.getId() + "");
        });
        iteratorAncestrals(treeList, childList);
    }

    public void iteratorAncestrals(List<T> treeList, List<T> parentList) {
        if(CollectionUtils.isEmpty(treeList) || CollectionUtils.isEmpty(parentList)) {
            return;
        }
        List<T> childList = new ArrayList<>();
        for (T t: parentList) {
            List<T> collect = treeList.stream().filter(vo -> t.getId().equals(vo.getPid())).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(collect)) {
                for (T t1: collect) {
                    t1.setPtVar5(t.getPtVar5() + "," + t1.getId());
                }
                childList.addAll(collect);
            } else {
                t.setLeaf("1");
            }
        }
        iteratorAncestrals(treeList, childList);
    }

    public void iteratorCountValue4Level(List<T> treeList, String columnName) {
        if(CollectionUtils.isEmpty(treeList)) {
            return;
        }
        for (T t: treeList) {
            if(!"1".equals(t.getLeaf())) {
                List<T> collect = treeList.stream().filter(vo -> "1".equals(vo.getLeaf()) && vo.getPtVar5().contains(t.getPtVar5())).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(collect)) {
                    try {
                        Field declaredField = t.getClass().getDeclaredField(columnName);
                        declaredField.setAccessible(true);
                        BigDecimal value = BigDecimal.ZERO;
                        for (T t1: collect) {
                            BigDecimal thisValue = (BigDecimal)declaredField.get(t1);
                            if(thisValue != null) {
                                value = value.add(thisValue);
                            }
                        }
                        declaredField.set(t, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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
