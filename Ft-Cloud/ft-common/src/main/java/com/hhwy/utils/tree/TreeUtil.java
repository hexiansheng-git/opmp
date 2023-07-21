package com.hhwy.utils.tree;

import com.alibaba.excel.util.CollectionUtils;
import com.hhwy.utils.idworker.IdWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeUtil {
    /**
     * 根据pid，构建树节点
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes, Long pid) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return new ArrayList<>();
        }
        treeNodes.stream().forEach(treeVO -> {
            treeVO.setChildren(
                    treeNodes.stream().filter((item) -> treeVO.getId().equals(item.getPid())).collect(Collectors.toList()));
        });
        List<T> collect;
        if (pid == null) {
            collect = treeNodes.stream().filter((item) -> item.getPid() == null)
                    .collect(Collectors.toList());
        } else {
            collect = treeNodes.stream().filter((item) -> pid.equals(item.getPid()))
                    .collect(Collectors.toList());
        }
        return collect;
    }
    /**
     * 树形list转list
     * @param source
     * @return
     */
    public static <T extends TreeNode<T>> List<T> treeToList(List<T> source) {
        List<T> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(source)) {
            return result;
        }

        int sort = 1;
        for (T node : source) {
            node.setSort(sort++);
            split(node,result);
        }
        return result;
    }

    private static <T extends TreeNode<T>> void split(T node, List<T> resultList){
        Long id = IdWorker.createId();
        int sort = 1;
        List<T> children = node.getChildren();
        node.setId(id);
        node.setChildren(null);
        resultList.add(node);
        if(!CollectionUtils.isEmpty(children)){
            for (T child : children) {
                child.setPid(id);
                child.setSort(sort++);
                split(child,resultList);
            }
        }
    }
}
