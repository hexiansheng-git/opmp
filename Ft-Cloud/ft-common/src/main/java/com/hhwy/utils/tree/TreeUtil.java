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
    public static <T extends TreeNode> List<T> build(List<T> treeNodes, Long pid) {
        if (CollectionUtils.isEmpty(treeNodes)) {
            return null;
        }
        treeNodes.stream().forEach(treeVO -> {
            treeVO.setChildren(
                    treeNodes.stream().filter((item) -> treeVO.getId().equals(item.getPid())).collect(Collectors.toList()));
        });
        List<T> collect = null;
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
     * @param list
     * @return
     */
    public static <T extends TreeNode> List<T> treeToList(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Long id = IdWorker.createId();
        List<T> result = new ArrayList<>();
        for (T t : list) {
            List<T> c = t.getChildren();
            t.setId(id);
            result.add(t);
            if (!CollectionUtils.isEmpty(c)) {
                c.stream().forEach(item->item.setPid(id));
                result.addAll(treeToList(c));
                t.setChildren(null);
            }
        }
        return result;
    }
}
