package com.hhwy.utils.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-13 09:50:00
 * @remark 列表转树工具类
 */
public class ToTreeUtils {

    /**
     * 对应id字段名称为id,父id字段名称为pid,子集合名称为children的专用方法
     *
     * @param nodes
     * @param <T>
     * @return
     */
    public static <T> List<T> listToTree(List<T> nodes) {
        String parentIdField = "pid";
        String idField = "id";
        String childrenName = "children";

        List<T> treeNodes = new ArrayList<>();
        for (T node : nodes) {
            Object parentIdValue = getFieldValue(node, parentIdField);
            Long parentId = 0L;
            if (parentIdValue != null) {
                parentId = Long.parseLong(parentIdValue.toString());
            }
            if (parentId == 0) {
                // 父ID为0或null为根节点，直接加入列表中
                treeNodes.add(node);
                // 递归处理子节点
                setChildren(node, nodes, parentIdField, idField, childrenName);
            }
        }
        return treeNodes;
    }

    /**
     * @param nodes         list 列表集合
     * @param parentIdField 父id字段名
     * @param idField       id字段名
     * @param childrenName  子集合名称
     * @param <T>
     * @return
     */
    public static <T> List<T> listToTreeGeneric(List<T> nodes, String parentIdField, String idField,
        String childrenName) {
        List<T> treeNodes = new ArrayList<>();
        for (T node : nodes) {
            Object parentIdValue = getFieldValue(node, parentIdField);
            Long parentId = 0L;
            if (parentIdValue != null) {
                parentId = Long.parseLong(parentIdValue.toString());
            }
            if (parentId == 0) {
                // 父ID为0或null为根节点，直接加入列表中
                treeNodes.add(node);
                // 递归处理子节点
                setChildren(node, nodes, parentIdField, idField, childrenName);
            }
        }
        return treeNodes;
    }

    private static <T> void setChildren(T parent, List<T> nodes, String parentIdField, String idField,
        String childrenName) {
        List<T> children = new ArrayList<>();
        for (T node : nodes) {
            Object parentIdValue = getFieldValue(node, parentIdField);
            Long parentId = 0L;
            if (parentIdValue != null) {
                parentId = Long.parseLong(parentIdValue.toString());
            }
            Object idValue = getFieldValue(parent, idField);
            Long id = Long.parseLong(idValue.toString());
            if (id.equals(parentId)) {
                // 将节点加入对应的父节点中
                children.add(node);
                // 递归处理子节点
                setChildren(node, nodes, parentIdField, idField, childrenName);
            }
        }
        if (!children.isEmpty()) {
            setFieldValue(parent, childrenName, children);
        }
    }

    /**
     * 反射获取对象的属性值
     *
     * @param obj       目标对象
     * @param fieldName 属性名
     * @return 属性值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Object getFieldValue(Object obj, String fieldName) {
        try {
            Class<?> clazz = obj.getClass();
            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射设置对象的属性值
     *
     * @param obj       目标对象
     * @param fieldName 属性名
     * @param value     属性值
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static void setFieldValue(Object obj, String fieldName, Object value) {
        try {
            Class<?> clazz = obj.getClass();
            java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
