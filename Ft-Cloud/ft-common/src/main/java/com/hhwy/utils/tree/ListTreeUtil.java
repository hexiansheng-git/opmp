package com.hhwy.utils.tree;

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

    /**
     * 树形列表转线性列表
     *
     * @param source      数据源
     * @param getChildren 如何拿到子节点列表
     * @param setChildren 如何设置子节点列表
     * @param <T>         节点类型
     * @return 线性列表
     */
    public static <T> List<T> formatList(List<T> source, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        List<T> list = new ArrayList<>();
        for (T node : source) {
            list.add(node);
            recur(list, getChildren.apply(node), getChildren, setChildren);
            setChildren.accept(node, null);
        }
        return list;
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

    private static <T> void recur(List<T> list, List<T> children, Function<T, List<T>> getChildren, BiConsumer<T, List<T>> setChildren) {
        if (children == null) return;
        for (T node : children) {
            list.add(node);
            List<T> c = getChildren.apply(node);
            if (c != null && c.size() > 0) {
                recur(list, c, getChildren, setChildren);
                setChildren.accept(node, null);
            }
        }
    }
}
