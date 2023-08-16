package com.hhwy.utils.tree;

import cn.hutool.core.builder.CompareToBuilder;
import cn.hutool.core.comparator.CompareUtil;
import com.hhwy.common.core.domain.R;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    /**
     * 导出树形集合，按照树形结构重新排序
     * 继承treeNode实体类用该方法
     * @param list 
     * @return
     */
    public static <T extends TreeNodeBase<T,R>> List<T> exportListFormat(List<T> list){
        return TreeUtil.exportListFormat(list,(Class)Long.class);
    }

    /**
     * 导出树形集合，按照树形结构重新排序
     * 继承treeNodeBase实体类用该方法
     * @param list
     * @return
     */
    public static <T extends TreeNodeBase<T,R>> List<T> exportListFormat(List<T> list,Class<R> t1){
        //id : 子级数据
        Map<R,List<T>> childMap = new HashMap<>(list.size());
        //第一级节点
        List<T> firstList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            Object poid = t.getPid();
            //是否为第一级
            if(t.getPid() == null || (t1.equals(Long.class) && ((Long)poid) < 1)
                    || ("-1".equals(poid) || "0".equals(poid))){
                firstList.add(t);
            }else{
                ObjectUtils.add2MapList(childMap, t.getPid(), t);
            }
        }
        //递归
        List<T> resuList = new ArrayList<>(list.size());
        chooseChild(firstList,t1,childMap,resuList);
        return resuList;
    }
    
    
    private static <T extends TreeNodeBase> void chooseChild(List<T> list,Class<R> t1,Map<R,List<T>> childMap,List<T> resuList){
        if(CollectionUtils.isEmpty(list))
            return;
        //排序
        list.sort((v1, v2) -> {return CompareUtil.compare(v1.getSort(), v2.getSort()); });
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            resuList.add(t);
            chooseChild(childMap.get(t.getId()),t1,childMap,resuList);
        }
    }
    
}
