package com.hhwy.pm.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.pm.xmsl.contractInfo.domain.vo.ImportTreeNodeVo;
import com.hhwy.utils.tree.TreeNode;

import java.util.*;
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
        List<T> ts = setAncestral(allList, currentNode);
        for (T t : ts) {
            if (StrUtil.isBlank(t.getPtVar5())) {
                if (t.getPid() == null) result.add(t);
                continue;
            }
            Set<String> pidList = new HashSet<>(CollUtil.toList(t.getPtVar5().split(",")));
            List<T> collect = allList.stream() .filter(p -> pidList.contains(p.getId() + "")).collect(Collectors.toList());
            result.addAll(collect);
        }
        return result.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 功能描述: 赋予ptVar5祖籍id
     * @param allList 全量数据
     * @param nodeList 待处理集合
     */
    public static <T extends TreeNode<T>> List<T> setAncestral(List<T> allList, List<T> nodeList) {
        List<T> resultList = new ArrayList<>();
        for (T p : nodeList) {
            ArrayList<String> ids = new ArrayList<>();
            setIds(allList, p, ids);
            if (CollectionUtil.isEmpty(ids)) {
                continue;
            }
            List<String> reverse = CollUtil.reverse(ids);
            String ancestral = String.join(",", reverse);
            p.setPtVar5(ancestral + "," + p.getId());
            resultList.add(p);
        }
        return resultList;
    }

    /**
     * 功能描述: 为currentNode找到祖籍
     * @param allList 全量数据
     * @param currentNode 待处理对象
     * @param ids 每次递归的变量,祖籍集合
     */
    public static <T extends TreeNode<T>> void setIds(List<T> allList, T currentNode, List<String> ids) {
        Optional<T> first = allList.stream().filter(o -> o.getId().equals(currentNode.getPid())).findFirst();
        if (!first.isPresent()) {
            return;
        }
        ids.add(first.get().getId()+"");
        setIds(allList, first.get(), ids);
    }

    /**
     * 功能描述: 导入功能，将excel中的数据组成树形结构返回前端
     * @param list excel数据
     * @return java.util.List<T> 树形结构
     * 作者: fushudong
     * 时间: 2024/1/8
     */
    public static <T extends ImportTreeNodeVo> List<T> parseLevelStruct(List<T> list) {
        Map<String, T> collect = list.stream()
                .filter(p -> com.hhwy.common.core.utils.StringUtils.isNotEmpty(p.getInnerCode()))
                .collect(Collectors.toMap(key -> key.getInnerCode(), value -> value, (v1, v2) -> v1));
        for (int i = 0;  i< list.size(); i++) {
            T t = list.get(i);
            t.setId(IdUtil.getSnowflakeNextId());
            String innerCode = t.getInnerCode();
            if (!innerCode.contains("-")) {
                //第一层级
                continue;
            }
            String parentCode = innerCode.substring(0, innerCode.lastIndexOf("-"));
            String curentCode = innerCode.substring(innerCode.lastIndexOf("-") +1);
            //获取当前数据的父层级
            T parent = collect.get(parentCode);
            Assert.notNull(parent, "层级码：{} 未找到父层级：{}，请确认是否存在", innerCode, parentCode);
            //获取父层级的children，将当前记录add进去
            List<T> children = parent.getChildren();
            if (CollectionUtil.isEmpty(children)) {
                children = new ArrayList<>();
            }
            t.setPid(parent.getId());
            children.add(t);
        }
        return new ArrayList<>(collect.values());
    }
}