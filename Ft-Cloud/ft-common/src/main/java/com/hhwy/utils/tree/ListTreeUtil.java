package com.hhwy.utils.tree;

import com.hhwy.constant.CommonYesNo;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListTreeUtil {
    /**
     * 线性列表转树形列表
     *
     * @param source      数据源
     * @param checkRoot   如何判断是根节点,接收一个参数，当前节点
     * @param checkParent 如何判断是父节点，接收两个参数分别为根节点、当前节点
     * @param getChildren 如何拿到子节点列表
     * @param setChildren 如何设置子节点列表
     * @param <T>         节点类型
     * @return 树形列表
     */
    public static <T> List<T> formatTree(List<T> source, Predicate<T> checkRoot, BiPredicate<T, T> checkParent, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        List<T> tree = new ArrayList<>();
        List<T> children = new ArrayList<>();
        for (T node : source) {
            if (checkRoot.test(node)) {
                tree.add(node);
            } else {
                children.add(node);
            }
        }
        for (T root : tree) {
            recur(root, children, checkParent, getChildren, setChildren);
        }
        return tree;
    }

    private static <T> void recur(T rootNode, List<T> children, BiPredicate<T, T> checkParent, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        for (T node : children) {
            if (checkParent.test(rootNode, node)) {
                // 说明为此根的子节点
                List<T> list = getChildren.apply(rootNode);
                if (list == null) list = new ArrayList<>();
                list.add(node);
                setChildren.accept(rootNode, list);
                recur(node, children, checkParent, getChildren, setChildren);
            }
        }
    }

    /**
     * 树形列表转线性列表（不维护id和pid，平铺数据）
     * @param source 数据源
     * @param getChildren 如何拿到子节点列表
     * @param setChildren 如何设置子节点列表
     * @param <T> 节点类型
     * @return
     */
    public static <T> List<T> formatList(List<T> source, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        List<T> resultList = new ArrayList<>();
        for (T node : source) {
            recur(node, resultList, getChildren, setChildren);
        }
        return resultList;
    }

    private static <T> void recur(T node, List<T> resultList, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        resultList.add(node);

        List<T> children = getChildren.apply(node);
        setChildren.accept(node, null);

        if(!CollectionUtils.isEmpty(children)){
            for (T child : children) {
                recur(child,resultList, getChildren, setChildren);
            }
        }
    }

    /**
     * 树形列表转线性列表
     * @param source 数据源
     * @param setId 如何设置id
     * @param setPid 如何设置pid
     * @param getChildren 如何拿到子节点列表
     * @param setChildren 如何设置子节点列表
     * @param <T> 节点类型
     * @return
     */
    public static <T> List<T> formatList(List<T> source,BiConsumer<T,Long> setId,BiConsumer<T,Long> setPid, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        List<T> resultList = new ArrayList<>();
        for (T node : source) {
            recur(node, resultList, setId, setPid, getChildren, setChildren);
        }
        return resultList;
    }

    private static <T> void recur(T node, List<T> resultList, BiConsumer<T,Long> setId, BiConsumer<T,Long> setPid, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        Long id = IdWorker.createId();
        setId.accept(node,id);
        resultList.add(node);

        List<T> children = getChildren.apply(node);
        setChildren.accept(node, null);

        if(!CollectionUtils.isEmpty(children)){
            for (T child : children) {
                setPid.accept(child,id);
                recur(child,resultList, setId,setPid, getChildren, setChildren);
            }
        }
    }

    /**
     * 树形列表转线性列表，加排序号
     * @param source 数据源
     * @param setId 如何设置id
     * @param setPid 如何设置pid
     * @param setSort 如何设置排序号
     * @param getChildren 如何拿到子节点列表
     * @param setChildren 如何设置子节点列表
     * @param <T> 节点类型
     * @return
     */
    public static <T> List<T> formatList(List<T> source,BiConsumer<T,Long> setId,BiConsumer<T,Long> setPid, BiConsumer<T,Integer> setSort, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        List<T> resultList = new ArrayList<>();
        int sort = 1;
        for (T node : source) {
            setSort.accept(node,sort++);
            recur(node, resultList, setId, setPid, setSort, getChildren, setChildren);
        }
        return resultList;
    }

    private static <T> void recur(T node, List<T> resultList, BiConsumer<T,Long> setId, BiConsumer<T,Long> setPid, BiConsumer<T,Integer> setSort, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        Long id = IdWorker.createId();
        int sort = 1;
        setId.accept(node,id);
        resultList.add(node);

        List<T> children = getChildren.apply(node);
        setChildren.accept(node, null);

        if(!CollectionUtils.isEmpty(children)){
            for (T child : children) {
                setPid.accept(child,id);
                setSort.accept(child,sort++);
                recur(child, resultList, setId, setPid, setSort, getChildren, setChildren);
            }
        }
    }

    /**
     * 树形列表转线性列表，加排序号，加叶子节点
     * @param source 数据源
     * @param setId 如何设置id
     * @param setPid 如何设置pid
     * @param setSort 如何设置排序号
     * @param setLeaf 如何设置叶子节点
     * @param getChildren 如何拿到子节点列表
     * @param setChildren 如何设置子节点列表
     * @param <T> 节点类型
     * @return
     */
    public static <T> List<T> formatList(List<T> source,BiConsumer<T,Long> setId,BiConsumer<T,Long> setPid, BiConsumer<T,Integer> setSort,BiConsumer<T,String> setLeaf, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        List<T> resultList = new ArrayList<>();
        int sort = 1;
        for (T node : source) {
            setSort.accept(node,sort++);
            recur(node, resultList, setId, setPid, setSort,setLeaf, getChildren, setChildren);
        }
        return resultList;
    }

    private static <T> void recur(T node, List<T> resultList, BiConsumer<T,Long> setId, BiConsumer<T,Long> setPid, BiConsumer<T,Integer> setSort,BiConsumer<T,String> setLeaf, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        Long id = IdWorker.createId();
        int sort = 1;
        setId.accept(node,id);
        resultList.add(node);

        List<T> children = getChildren.apply(node);
        setChildren.accept(node, null);

        if(!CollectionUtils.isEmpty(children)){
            for (T child : children) {
                setPid.accept(child,id);
                setSort.accept(child,sort++);
                recur(child, resultList, setId, setPid, setSort,setLeaf, getChildren, setChildren);
            }
        }else {
            setLeaf.accept(node, CommonYesNo.YES);
        }
    }


    /**
     * 树形列表转线性列表(并把第一层pid转为null，把叶子节点标识加到ptVar1里面)
     * @param source 数据源
     * @param setId 如何设置id
     * @param setPid 如何设置pid
     * @param getChildren 如何拿到子节点列表
     * @param setChildren 如何设置子节点列表
     * @param <T> 节点类型
     * @return
     */
    public static <T> List<T> formatListPidNull(List<T> source,BiConsumer<T,Long> setId,BiConsumer<T,Long> setPid, BiConsumer<T,String> setPtVar1, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        List<T> resultList = new ArrayList<>();
        for (T node : source) {
            setPid.accept(node,null);
            recurnew(node, resultList, setId, setPid,setPtVar1, getChildren, setChildren);
        }
        return resultList;
    }
    private static <T> void recurnew(T node, List<T> resultList, BiConsumer<T,Long> setId, BiConsumer<T,Long> setPid,BiConsumer<T,String> setPtVar1, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        Long id = IdWorker.createId();
        setId.accept(node,id);
        resultList.add(node);

        List<T> children = getChildren.apply(node);
        setChildren.accept(node, null);

        if (!CollectionUtils.isEmpty(children)) {
            for (T child : children) {
                setPid.accept(child, id);
                recurnew(child, resultList, setId, setPid, setPtVar1, getChildren, setChildren);
            }
        } else {
            setPtVar1.accept(node,"1");//是叶子节点
        }
    }
}
