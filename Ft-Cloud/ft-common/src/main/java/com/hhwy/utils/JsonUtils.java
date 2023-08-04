package com.hhwy.utils;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.utils.excel.FtExcel;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 将空对象转为json,方便测试
 * 实际业务中不可使用
 *
 * @author mls
 */
public class JsonUtils {

    public static void soutJsonStr(Class<?> aClass) {
        Field[] fields = aClass.getDeclaredFields();
        Map<String, String> map = new LinkedHashMap<>();


        String[] ignoreField = {  
                "valid",
                "createUser","masterId", "createUserName", "delFlag", "delTime", "ptVar1", "ptVar2", "ptVar3", "ptVar4",
                "ptVar5", "ptVar6", "version", "updateUser","delUser", "updateUserName", "fileGroupId", "params", "deptId", "dataSource"};
        List<String> strings = Arrays.asList(ignoreField);
        for (Field field : fields) {
            String fieldName = field.getName();
            Excel annotation = field.getAnnotation(Excel.class);
            if (strings.contains(fieldName) || annotation == null) {
                continue;
            }
            map.put(fieldName, annotation.name());
        }
        String s = JSONObject.toJSONString(map);
        System.out.println(s);

    }


    public static void soutFtJsonStr(Class<?> aClass) {
        Field[] fields = aClass.getDeclaredFields();
        Map<String, String> map = new LinkedHashMap<>();


        String[] ignoreField = {
                "valid",
                "createUser","masterId", "createUserName", "delFlag", "delTime", "ptVar1", "ptVar2", "ptVar3", "ptVar4",
                "ptVar5", "ptVar6", "version", "updateUser","delUser", "updateUserName", "fileGroupId", "params", "deptId", "dataSource"};
        List<String> strings = Arrays.asList(ignoreField);
        for (Field field : fields) {
            String fieldName = field.getName();
            FtExcel annotation = field.getAnnotation(FtExcel.class);
            if (strings.contains(fieldName) || annotation == null) {
                continue;
            }
            map.put(fieldName, annotation.name());
        }
        String s = JSONObject.toJSONString(map);
        System.out.println(s);

    }

}
