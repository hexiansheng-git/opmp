package com.hhwy.utils.tree;

import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhenglili
 * @date 2023-07-13 09:50:00
 * @remark 列表转树工具类
 */
public class TreeUtils {

    private static String idField = "id";
    private static String parentIdField = "pid";
    private static String childrenName = "children";

    /**
     * 对应id字段名称为id,父id字段名称为pid,子集合名称为children的专用方法
     *
     * @param nodes
     * @param <T>
     * @return
     */
    public static <T> List<T> listToTree(List<T> nodes) {
        return listToTreeGeneric(nodes, parentIdField, idField, childrenName);
    }

    /**
     * 通用方法
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
            long parentId = 0L;
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
            long parentId = 0L;
            if (parentIdValue != null) {
                parentId = Long.parseLong(parentIdValue.toString());
            }
            Object idValue = getFieldValue(parent, idField);
            assert idValue != null;
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
     * 拆分树列表
     * @param <T>
     * @param nodes 需要拆分的树列表
     * @return
     */
    public static <T> List<T> splitTreeList(List<T> nodes){
        return splitTreeList(nodes,idField,parentIdField,childrenName);
    }

    /**
     * 拆分树列表
     * @param <T>
     * @param nodes 需要拆分的树列表
     * @param idField 主键字段名
     * @param parentIdField 关联主键id字段名
     * @param childrenName 子集字段名
     * @return
     */
    public static <T> List<T> splitTreeList(List<T> nodes, String idField, String parentIdField, String childrenName){
        List<T> resultList = new ArrayList<>();
        for (T node : nodes) {
            split(node,idField,parentIdField,childrenName,resultList);
        }
        return resultList;
    }

    private static <T> void split(T node, String idField, String parentIdField, String childrenName, List<T> resultList){
        Long id = IdWorker.createId();
        setFieldValue(node,idField,id);
        resultList.add(node);

        List<T> children = (List<T>) getFieldValue(node, childrenName);
        if(!CollectionUtils.isEmpty(children)){
            for (T child : children) {
                setFieldValue(child,parentIdField,id);
                split(child,idField,parentIdField,childrenName, resultList);
            }
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
