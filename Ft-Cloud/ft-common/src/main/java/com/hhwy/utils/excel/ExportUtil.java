package com.hhwy.utils.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.springframework.util.CollectionUtils;

/**
 * 导出维护序号
 */
public class ExportUtil {

    /**
     * list导出维护序号
     *
     * @param setSerialNumber
     * @param <T>
     * @return
     */
    public static <T> List<T> preserveSerialNumber(List<T> ts, BiConsumer<T, String> setSerialNumber) {
        int serialNum = 1;
        for (T t : ts) {
            setSerialNumber.accept(t, String.valueOf(serialNum++));
        }
        return ts;
    }

    /**
     * 树导出维护序号
     *
     * @param getChildren
     * @param setChildren
     * @param getSerialNumber
     * @param setSerialNumber
     * @param <T>
     * @return
     */
    public static <T> List<T> preserveSerialNumberTree(List<T> ts, Function<T, List<T>> getChildren,
        BiConsumer<T, List<T>> setChildren, Function<T, String> getSerialNumber,
        BiConsumer<T, String> setSerialNumber) {
        int serialNum = 1;
        for (T t : ts) {
            setSerialNumber.accept(t, String.valueOf(serialNum++));
            setSerialNumber(t, getSerialNumber, setSerialNumber, getChildren);
        }
        return formatList(ts, getChildren, setChildren);
    }

    private static <T> void setSerialNumber(T t, Function<T, String> getSerialNumber,
        BiConsumer<T, String> setSerialNumber, Function<T, List<T>> getChildren) {
        int serialNum = 1;
        String parentSerialNum = getSerialNumber.apply(t);
        List<T> children = getChildren.apply(t);
        if (!CollectionUtils.isEmpty(children)) {
            for (T child : children) {
                String serial = parentSerialNum + "." + serialNum;
                serialNum++;
                setSerialNumber.accept(child, serial);
                setSerialNumber(child, getSerialNumber, setSerialNumber, getChildren);
            }
        }
    }

    /**
     * 树形列表转线性列表（不维护id和pid，平铺数据）
     *
     * @param source      数据源
     * @param getChildren 如何拿到子节点列表
     * @param setChildren 如何设置子节点列表
     * @param <T>         节点类型
     * @return
     */
    public static <T> List<T> formatList(List<T> source, Function<T, List<T>> getChildren,
        BiConsumer<T, List<T>> setChildren) {
        List<T> resultList = new ArrayList<>();
        for (T node : source) {
            recur(node, resultList, getChildren, setChildren);
        }
        return resultList;
    }

    private static <T> void recur(T node, List<T> resultList, Function<T, List<T>> getChildren,
        BiConsumer<T, List<T>> setChildren) {
        resultList.add(node);

        List<T> children = getChildren.apply(node);
        setChildren.accept(node, null);

        if (!CollectionUtils.isEmpty(children)) {
            for (T child : children) {
                recur(child, resultList, getChildren, setChildren);
            }
        }
    }
}
