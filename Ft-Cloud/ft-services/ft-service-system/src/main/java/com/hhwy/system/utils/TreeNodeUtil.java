package com.hhwy.system.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.utils.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 功能：树结构处理
 */
public class TreeNodeUtil {


    /***
     * 功能描述: 获取给定结点的所有父级结点，包含当前结点
     * @param allList 全量数据
     * @param currentNode 给定节点
     */
    public static <T extends TreeNode<T>> List<T> getAncestral(List<T> allList, List<T> currentNode) {
        List<T> result = new ArrayList<>();
        currentNode.forEach(System.out::println);
        setAncestral(allList, currentNode, new ArrayList<>());
        currentNode.forEach(System.out::println);
        for (T t : currentNode) {
            if (StrUtil.isBlank(t.getPtVar5())) {
                continue;
            }
            Set<String> pidList = new HashSet<>(CollUtil.toList(t.getPtVar5().split(",")));
            List<T> collect = allList.stream() .filter(p -> pidList.contains(p.getId() + "")).collect(Collectors.toList());
            result.addAll(collect);
        }
        List<T> collect = result.stream().distinct().collect(Collectors.toList());
        return collect;
    }

    /***
     * 功能描述: 维护祖籍id ，赋予ptVar5祖籍id
     * @param allList 待处理集合
     * @param ids 每次递归的变量
     */
    public static <T extends TreeNode<T>> void setAncestral(List<T> allList, List<T> currentNode, List<String> ids) {
        for (T p : currentNode) {
            List<T> currentParent = allList.stream().filter(o -> o.getId().equals(p.getPid())).collect(Collectors.toList());
            if (CollectionUtil.isEmpty(currentParent)) {
                continue;
            }
            ids.addAll(currentParent.stream().map(v -> v.getId() + "").collect(Collectors.toList()));
            setAncestral(allList, currentParent, ids);
            if (CollectionUtil.isEmpty(ids)) {
                continue;
            }
            String ancestral = ids.stream().collect(Collectors.joining(","));
            p.setPtVar5(ancestral + "," + p.getId());
        }
    }
}