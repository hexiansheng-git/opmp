package com.hhwy.utils.field;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * 字段工具类
 *
 * @author m
 */
@Slf4j
public class FieldUtils {
    /**
     * 方法名和方法实例容器
     * 此变量要用实例变量 否则会发生内存泄露
     */
    private final Map<String, Method> methodMap;

    {
        methodMap = new HashMap<>();

    }

    /**
     * 初始化方法
     * 如果有循环的话 必须要在循环外调用此方法
     */
    public static FieldUtils init() {
        return new FieldUtils();
    }

    /**
     * 获取指定字段的值
     *
     * @param fieldName 字段名称
     * @param t         实体
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> Object getFieldVal(String fieldName, T t) {
        try {
            String methodName = getMethodName(fieldName);
            Method method = methodMap.get(methodName);
            if (method == null) {
                Class<?> aClass = t.getClass();
                method = aClass.getMethod(methodName);
                methodMap.put(methodName, method);
            }
            return method.invoke(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 设置指定字段的值
     *
     * @param fieldName 字段名称
     * @param v         字段要赋的值
     * @param t         实体
     * @param <T>
     */
    public <T> void setFieldVal(String fieldName, Object v, T t) {
        try {
            String methodName = setMethodName(fieldName);
            Method method = methodMap.get(methodName);
            if (method == null) {
                Class<?> aClass = t.getClass();
                method = aClass.getMethod(methodName, getField(aClass, fieldName).getType());
                methodMap.put(methodName, method);
            }
            method.invoke(t, v);
        } catch (Exception e) {
          log.debug("反射异常,设置指定字段{}的值失败,{}",fieldName,e.getMessage());
        }

    }


    public static String getMethodName(String fieldName) {
        fieldName = fieldName.trim();
        char[] chars = fieldName.toCharArray();
        chars[0] = toUpperCase(chars[0]);
        return "get" + String.valueOf(chars);
    }

    public static String setMethodName(String fieldName) {
        fieldName = fieldName.trim();
        char[] chars = fieldName.toCharArray();
        chars[0] = toUpperCase(chars[0]);
        return "set" + String.valueOf(chars);
    }

    private static char toUpperCase(char c) {
        if (97 <= c && c <= 122) {
            c ^= 32;
        }
        return c;
    }


    public static Field getField(Class<?> aClass, String fieldName) {
        Field declaredField = null;
        try {
            declaredField = aClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            declaredField = getSuperFieldByName(aClass, fieldName);
        }
        return declaredField;
    }


    private static Field getSuperFieldByName(Class<?> cls, String field) {
        Class<?> superclass = cls.getSuperclass();
        if (superclass.equals(Object.class)) return null;
        try {
            return superclass.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            return getSuperFieldByName(superclass, field);
        }
    }


    public static Object convertFiledVal(Field field, Object val) {
        if (field == null || val == null) return null;
        String typeName = field.getType().getTypeName();
        switch (typeName) {
            case "java.lang.Byte":
                return Convert.toByte(val);
            case "java.lang.Short":
                return Convert.toShort(val);
            case "java.lang.Integer":
                return Convert.toInt(val);
            case "java.lang.Long":
                return Convert.toLong(val);
            case "java.lang.Double":
                return Convert.toDouble(val);
            case "java.lang.Float":
                return Convert.toFloat(val);
            case "java.lang.Character":
                return Convert.toChar(val);
            case "java.lang.Boolean":
                return Convert.toBool(val);
            case "java.lang.String":
                return Convert.toStr(val);
            case "java.math.BigDecimal":
                return Convert.toBigDecimal(val);
            case "java.util.Date":
                if (val instanceof String) {
                    val = DateUtils.parseDate(val);
                } else if (val instanceof Double) {
                    val = DateUtil.getJavaDate((Double) val);
                }
                return val;


            default:
                return val;
        }

    }


    public static void main(String[] args) {

        List<String> strings = Arrays.asList(Byte.class.getTypeName(),
                Short.class.getTypeName(),
                Integer.class.getTypeName(),
                Long.class.getTypeName(),
                Double.class.getTypeName(),
                Float.class.getTypeName(),
                Character.class.getTypeName(),
                Boolean.class.getTypeName(),
                String.class.getTypeName(),
                BigDecimal.class.getTypeName(),
                Date.class.getTypeName());
        for (String string : strings) {
            System.out.println(string);
        }
    }

}