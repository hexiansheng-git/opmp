package com.hhwy.utils.tree;

import com.hhwy.constant.CommonYesNo;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListTreeUtil {

    /**
     * 设置树结构的id和pid
     * @param source
     * @param setId
     * @param setPid
     * @param getChildren
     * @param <T>
     */
    public static <T> void preserveIdPid(List<T> source,BiConsumer<T,Long> setId,BiConsumer<T,Long> setPid,Function<T, List<T>> getChildren){
        for (T t : source) {
            preserve(t,setId,setPid,getChildren);
        }
    }

    private static <T> void preserve(T node,BiConsumer<T,Long> setId,BiConsumer<T,Long> setPid,Function<T, List<T>> getChildren){
        Long id = IdWorker.createId();
        setId.accept(node,id);

        List<T> children = getChildren.apply(node);
        if(!CollectionUtils.isEmpty(children)){
            for (T child : children) {
                setPid.accept(child,id);
                preserve(child,setId, setPid, getChildren);
            }
        }
    }



    /**
     * 导出维护序号（普通列表）
     * @param source
     * @param setSerialNumber
     * @return
     * @param <T>
     */
    public static <T> List<T> preserveSerialNumber(List<T> source, BiConsumer<T,String> setSerialNumber) {
        int serialNum = 1;
        for (T t : source) {
            setSerialNumber.accept(t,String.valueOf(serialNum++));
        }
        return source;
    }

    /**
     * 导出维护序号（树列表）
     * @param source
     * @param checkRoot
     * @param checkParent
     * @param getChildren
     * @param setChildren
     * @param getSerialNumber
     * @param setSerialNumber
     * @return
     * @param <T>
     */
    public static <T> List<T> preserveSerialNumber(List<T> source, Predicate<T> checkRoot, BiPredicate<T, T> checkParent, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren, Function<T, String> getSerialNumber, BiConsumer<T,String> setSerialNumber) {
        List<T> ts = formatTree(source, checkRoot, checkParent, getChildren, setChildren);
        int serialNum = 1;
        for (T t : ts) {
            setSerialNumber.accept(t,String.valueOf(serialNum++));
            setSerialNumber(t,getSerialNumber,setSerialNumber,getChildren);
        }
        return formatList(ts,getChildren,setChildren);
    }

    private static <T> void setSerialNumber(T t, Function<T, String> getSerialNumber, BiConsumer<T,String> setSerialNumber, Function<T, List<T>> getChildren){
        int serialNum = 1;
        String parentSerialNum = getSerialNumber.apply(t);
        List<T> children = getChildren.apply(t);
        if(!CollectionUtils.isEmpty(children)) {
            for (T child : children) {
                String serial = parentSerialNum + "." + serialNum;
                serialNum++;
                setSerialNumber.accept(child,serial);
                setSerialNumber(child,getSerialNumber,setSerialNumber,getChildren);
            }
        }
    }

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
        if(CollectionUtils.isEmpty(source)){
            return tree;
        }
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
            setPid.accept(node,null);
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
            setPid.accept(node,null);
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
                setLeaf.accept(node,CommonYesNo.NO);
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

    /**
     * 根据子集递归查询父级数据 ，封装成树结构返回
     * @param sublist 子集列表
     * @param allList 全量数据
     * @param getId
     * @param getPid
     * @param checkRoot
     * @param checkParent
     * @param getChildren
     * @param setChildren
     * @return
     * @param <T>
     */
    public static <T> List<T> getUpListBySublistToTree(List<T> sublist,List<T> allList,Function<T,Long> getId,Function<T,Long> getPid,Predicate<T> checkRoot, BiPredicate<T, T> checkParent, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren){
        List<T> tList = getUpListBySublist(sublist, allList, getId, getPid);
        return formatTree(tList,checkRoot,checkParent,getChildren,setChildren);
    }

    /**
     * 根据子集递归查询父级数据
     * @param sublist
     * @param allList
     * @param getId
     * @param getPid
     * @return
     * @param <T>
     */
    public static <T> List<T> getUpListBySublist(List<T> sublist,List<T> allList,Function<T,Long> getId,Function<T,Long> getPid) {
        List<T> resultList = new ArrayList<>();
        Map<Long,T> resultMap = new HashMap<>();

        for (T t : sublist) {
            recursion(t,allList,resultMap,getId,getPid);
            resultMap.putIfAbsent(getId.apply(t), t);
        }

        for (Map.Entry<Long, T> t : resultMap.entrySet()) {
            resultList.add(t.getValue());
        }
        return resultList;
    }

    /**
     * 递归查询父级数据
     * @param down
     * @param allList
     * @param resultMap
     */
    public static <T> void recursion(T down,List<T> allList,Map<Long,T> resultMap,Function<T,Long> getId,Function<T,Long> getPid){
        Long pid = getPid.apply(down);
        if(pid != null){
            for (T t : allList) {
                Long id = getId.apply(t);
                if(pid.equals(id)){
                    recursion(t,allList,resultMap,getId,getPid);
                    resultMap.putIfAbsent(id, t);
                }
            }
        }
    }
}
