package com.hhwy.utils.excelUtil;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class EasyExcelUtil {

    /**
     * 动态导出列
     * @param entityClass 实体类的class对象
     * @param customizeHeads 要动态生成的表头
     * @param list  查出的数据
     * @author furao
     */
    public void excelHelper(HttpServletResponse response, Class<T> entityClass, List<HeadVo> customizeHeads, List<Map<String, Object>> list) throws IOException {
        //获取声明字段
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);// 设置属性是可以访问的
            //判断当前字段注解是否ExcelProperty
            boolean annotationPresent = field.isAnnotationPresent(ExcelProperty.class);
            if (annotationPresent) {
                //获取注解
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                //获取注解内的值变为表头
                List<String> head = Arrays.asList(excelProperty.value());
                //获取第几个下标
                int index = excelProperty.index();
                //添加 表头  下标   字段名称
                HeadVo headVO = HeadVo.builder().headTitle(head).index(index).key(field.getName()).build();
                //添加到集合内
                customizeHeads.add(headVO);
            }
        }
        //按照下标排序
        Collections.sort(customizeHeads);
        //定义一个list 存放所有表头
        List<List<String>> heads = new ArrayList<>();
        //存放字段名
        List<String> keys = new ArrayList<>();
        //一共有多少个表头循环多少次
        for (int i = 0; i <= customizeHeads.size() - 1; i++) {
            //获取每一个表头的名称
            heads.add(customizeHeads.get(i).getHeadTitle());
            //获取每一个表头的字段名
            keys.add(customizeHeads.get(i).getKey());
        }
        //存放所有导出的数据
        List<List<Object>> objs = new ArrayList<>();
        for (Map<String, Object> map : list) {
            List<Object> obj = new ArrayList<>();
            List<Object> groupVos= (List<Object>) map.get("groupVos");
            List<Map<String, Object>> keysAndValues = getKeysAndValues(groupVos);
            //List<Object>转为List<Map<String,Object>>
            //循环每个字段
            for (String key : keys) {
                //在数据集合中找到每个字段相对应的值并添加 一一对应
                Object o = map.get(key);
                //数据为null，当前keys.get(i)可能是具体原因
                if (o == null) {
                    boolean add = false;
                    //循环当前数据下的GroupVos数组
                    for (Map<String, Object> keyAndValue : keysAndValues) {
                        //把具体原因取出来
                        Object GroupName = map.get("GroupName");
                        //判断GroupName值(具体原因)是否跟当前key相等
                        if (GroupName.equals(key)) {
                            //相等置为true
                            add = true;
                            //相等把得分获取到 直接返回
                            Object score = map.get("score");
                            obj.add(score);
                            break;
                        }
                    }
                    //GroupVos数组循环结束还未添加数据表示此数据内不存在当前的原因补0
                    if (!add) {
                        obj.add(0);
                    }
                } else {
                    obj.add(o);
                }
                //添加数据到集合中
                objs.add(obj);
            }
        }


        try {
            EasyExcel.write(response.getOutputStream())
                    .head(heads)
                    .registerWriteHandler(ExcelHeadStyle.getHorizontalCellStyleStrategy(response,"111"))
                    .sheet("明细")
                    .doWrite(objs);
        } catch (IOException e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            try {
                response.getWriter().println(JSON.toJSONString(map));
            } catch (IOException ioException) {
                throw new IOException("IO异常");
            }
        }
    }

    /**
     * List<Object>转为List<Map<string,object>
     * @param object
     * @return
     * @author furao
     */
    public static List<Map<String, Object>> getKeysAndValues(List<Object> object) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object obj : object) {
            Class userCla;
            // 得到类对象
            userCla = obj.getClass();
            /* 得到类中的所有属性集合 */
            Field[] fs = userCla.getDeclaredFields();
            Map<String, Object> listChild = new HashMap<>();
            for (Field f : fs) {
                ReflectionUtils.makeAccessible(f);// 设置属性是可以访问的
                Object val;
                try {
                    val = f.get(obj);
                    // 得到此属性的值
                    listChild.put(f.getName(), val);// 设置键值
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("非法参数异常");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("非法访问异常");
                }
            }
            list.add(listChild);// 将map加入到list集合中
        }
        return list;
    }
}
