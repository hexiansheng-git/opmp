package com.hhwy.utils.myUtilPrepare;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.multithreading.asyn.AsyncExecutor;
import com.hhwy.utils.redisUtil.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @author zqq
 * @create 2023-08-25 11:21
 */
@Slf4j
@Service
public class SetMaterialNameUtils {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private AsyncExecutor asyncExecutor;
    /**
     * 批量设置业务数据的物资信息
     *
     * @param tList                 业务数据
     * @param materialCodeFieldName 业务数据物资编码名称
     * @param busAndMaterialMap     k:业务数据字段名称; v:物资信息字段名称
     * @param <T>
     * @return 封装有物资信息的业务数据
     */
    public <T> List<T> setMaterialInfo(List<T> tList, String materialCodeFieldName, Map<String, String> busAndMaterialMap) {
        try {
            // 为空 返回
            if (CollectionUtils.isEmpty(tList)) return tList;
            T t = tList.get(0);
            Class<?> aClass = t.getClass();
            // 物资编码字段信息
            Field materialCodeField = aClass.getDeclaredField(materialCodeFieldName);
            materialCodeField.setAccessible(true);
            List<Field> busFieldInfoList = new ArrayList<>(busAndMaterialMap.size());
            for (String busFieldName : busAndMaterialMap.keySet()) {
                Field busField;
                try {
                    busField = aClass.getDeclaredField(busFieldName);
                } catch (Exception e) {
                    log.error("获取字段异常", e);
                    continue;
                }
                busField.setAccessible(true);
                busFieldInfoList.add(busField);
            }

            // 物资编码
            List materialCodeList = null;
            if (tList.size() > 1000) {
                // 用多线程
                materialCodeList = this.mulThreadGetMaterialCodeList(tList, materialCodeField);
            } else {
                // 单线程
                materialCodeList = this.getMaterialCodeList(tList, materialCodeField);
            }

            // 根据物资编码获取物资信息 这里只请求redis一次 避免浪费网络资源
            List materialListRedis = this.redisUtils.hMultiGet(PmsConstant.MATERIALREDISKEY, materialCodeList);

            if (materialListRedis.size() > 1000) {
                // 用多线程
                tList = this.mulThreadSetMaterial(tList, materialListRedis, materialCodeField, busFieldInfoList, busAndMaterialMap);
            } else {
                // 单线程
                tList = this.setMaterial(tList, materialListRedis, materialCodeField, busFieldInfoList, busAndMaterialMap);
            }

        } catch (Exception e) {
            log.error("查询物资信息失败", e);
            // throw new RuntimeException(e.getMessage());
        }
        return tList;
    }

    /**
     * 多线程获取物资编码
     *
     * @param <T>
     * @param tList
     * @param materialCodeField
     * @return
     */
    private <T> List<String> mulThreadGetMaterialCodeList(final List<T> tList, Field materialCodeField) {
        CopyOnWriteArrayList<String> resList = new CopyOnWriteArrayList<>();
        // 将原有集合每一百个分一组
        List<List<T>> partition = ListUtils.partition(tList, 100);
        CountDownLatch countDownLatch = new CountDownLatch(partition.size());

        // 多线程
        for (List<T> ts : partition) {
            // 在这里使用默认的多线程
            asyncExecutor.defaultAsyncExecute(() -> {
                try {
                    log.warn("start executeAsync");
                    // 获取物资编码
                    List<String> materialCodeList = this.getMaterialCodeList(ts, materialCodeField);
                    // 不为空才将数据加入
                    if (CollectionUtils.isNotEmpty(materialCodeList)) resList.addAll(materialCodeList);
                    log.warn("end executeAsync");
                } finally {
                    // 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
                    countDownLatch.countDown();
                }
            });
        }

        try {
            // 阻塞一下 保证之前的所有的线程都执行完成，才会走下面的；
            countDownLatch.await();
        } catch (Exception e) {
            log.error("阻塞异常:" + e.getMessage());
        }

        return resList;
    }

    /**
     * 单线程获取物资编码
     *
     * @param <T>
     * @param tList
     * @param materialCodeField
     * @return
     */
    private <T> List<String> getMaterialCodeList(final List<T> tList, Field materialCodeField) {

        CopyOnWriteArrayList<String> codes = new CopyOnWriteArrayList<>();
        tList.forEach(item -> {
            try {
                // 获取物资编码值
                String materialCode = (String) materialCodeField.get(item);
                codes.add(materialCode);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("物资编码不能为空");
            }
        });
        return codes;
    }

    /**
     * 多线程设置物资信息
     *
     * @param tList
     * @param materialListRedis
     * @param materialCodeField
     * @param <T>
     * @return
     */
    private <T> List<T> mulThreadSetMaterial(final List<T> tList, final List materialListRedis, Field materialCodeField, final List<Field> busFieldList, Map<String, String> busAndMaterialMap) {
        CopyOnWriteArrayList<T> resList = new CopyOnWriteArrayList<>();
        // 将原有集合每一百个分一组
        List<List<T>> partition = ListUtils.partition(tList, 100);
        CountDownLatch countDownLatch = new CountDownLatch(partition.size());

        // 多线程
        for (List<T> ts : partition) {
            // 在这里使用默认的多线程
            asyncExecutor.defaultAsyncExecute(() -> {
                try {
                    log.warn("start executeAsync");
                    // 获取物资编码
                    List<T> mtlList = this.setMaterial(ts, materialListRedis, materialCodeField, busFieldList, busAndMaterialMap);
                    // 不为空才将数据加入
                    if (CollectionUtils.isNotEmpty(mtlList)) resList.addAll(mtlList);
                    log.warn("end executeAsync");
                } catch (Exception e) {
                    throw new RuntimeException("mulThreadSetMaterial 异常");
                } finally {
                    // 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
                    countDownLatch.countDown();
                }
            });
        }

        try {
            // 阻塞一下 保证之前的所有的线程都执行完成，才会走下面的；
            countDownLatch.await();
        } catch (Exception e) {
            log.error("阻塞异常:" + e.getMessage());
        }

        return resList;
    }

    /**
     * 单线程设置物资信息
     *
     * @param <T>
     * @param materialListRedis
     * @param materialCodeField
     * @return
     */
    private <T> List<T> setMaterial(final List<T> tList, final List materialListRedis, Field materialCodeField, final List<Field> busFieldList, final Map<String, String> busAndMaterialMap) throws Exception {
        // 从redis中获取的物资信息
        // List<MaterialInfo> materialInfos = JSONObject.parseArray(JSONObject.toJSONString(materialListRedis), MaterialInfo.class);
        List<JSONObject> materialInfos = new ArrayList<>();
        for (Object materialRedis : materialListRedis) {
            String mtlStr = (String) materialRedis;
            JSONObject materialInfo = JSONObject.parseObject(mtlStr);
            materialInfos.add(materialInfo);
        }

        for (T t : tList) {
            String materialCode = (String) materialCodeField.get(t);
            materialInfos.stream().filter(item -> item != null && item.get("materialCode") != null && item.get("materialCode").equals(materialCode))
                    .findFirst()
                    .ifPresent(materialInfo -> {
                        // 根据 busAndMaterialMap 中的业务字段和物资字段的 k-v 关系 设置业务数据的字段值
                        for (String busFieldName : busAndMaterialMap.keySet()) {
                            String materialFieldName = busAndMaterialMap.get(busFieldName);
                            busFieldList.stream().filter(field -> field.getName().equals(busFieldName)).findFirst().ifPresent(fieldInfo -> {
                                this.setFieldValue(t, fieldInfo, materialInfo.get(materialFieldName));
                            });
                        }

                    });
        }

        return tList;
    }

    /**
     * 设置字段值
     *
     * @param t
     * @param field
     * @param fieldValue
     * @param <T>
     */
    private <T> void setFieldValue(T t, Field field, Object fieldValue) {
        try {
            field.set(t, fieldValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }
    /**
     * 批量设置业务数据的分类信息
     *
     * @param tList                 业务数据
     * @param categoryCodeFieldName 业务数据分类编码名称
     * @param busAndCategoryMap     k:业务数据字段名称; v:信息字段名称
     * @param <T>
     * @return 封装有物资信息的业务数据
     */
    public <T> List<T> setCategoryInfo(List<T> tList, String categoryCodeFieldName, Map<String, String> busAndCategoryMap) {

        try{
            if (CollectionUtils.isEmpty(tList)) return tList;
            T t = tList.get(0);
            Class<?> aClass = t.getClass();
            Field categoryCodeField = aClass.getDeclaredField(categoryCodeFieldName);
            categoryCodeField.setAccessible(true);
            List<Field> busFieldInfoList = new ArrayList<>(busAndCategoryMap.size());
            for (String busFieldName : busAndCategoryMap.keySet()) {
                Field busField;
                try {
                    busField = aClass.getDeclaredField(busFieldName);
                } catch (Exception e) {
                    log.error("获取字段异常", e);
                    continue;
                }
                busField.setAccessible(true);
                busFieldInfoList.add(busField);
            }
            // 分类编码
            List<String> categoryCodeList = null;
            if (tList.size() > 1000) {
                // 用多线程
                categoryCodeList = this.mulThreadGetMaterialCodeList(tList, categoryCodeField);
            } else {
                // 单线程
                categoryCodeList = this.getMaterialCodeList(tList, categoryCodeField);
            }
            List collect = categoryCodeList.stream().filter(p -> StrUtil.isNotEmpty(p)).collect(Collectors.toList());
            List categoryListRedis = this.redisUtils.hMultiGet(PmsConstant.CATEGORYREDISKEY, collect);
            if (categoryListRedis.size() > 1000) {
                // 用多线程
                tList = this.mulThreadSetCategory(tList, categoryListRedis, categoryCodeField, busFieldInfoList, busAndCategoryMap);
            } else {
                // 单线程
                tList = this.setCategory(tList, categoryListRedis, categoryCodeField, busFieldInfoList, busAndCategoryMap);
            }
        }catch (Exception e){
            log.error("查询设备分类信息失败", e);
        }
        return tList;
    }

    private <T> List<T> mulThreadSetCategory(List<T> tList, List categoryListRedis, Field categoryCodeField, List<Field> busFieldInfoList, Map<String, String> busAndCategoryMap) {
        CopyOnWriteArrayList<T> resList = new CopyOnWriteArrayList<>();
        // 将原有集合每一百个分一组
        List<List<T>> partition = ListUtils.partition(tList, 100);
        CountDownLatch countDownLatch = new CountDownLatch(partition.size());
        // 多线程
        for (List<T> ts : partition) {
            // 在这里使用默认的多线程
            asyncExecutor.defaultAsyncExecute(() -> {
                try {
                    log.warn("start executeAsync");
                    // 获取分类编码
                    List<T> mtlList = this.setCategory(ts, categoryListRedis, categoryCodeField, busFieldInfoList, busAndCategoryMap);
                    // 不为空才将数据加入
                    if (CollectionUtils.isNotEmpty(mtlList)) resList.addAll(mtlList);
                    log.warn("end executeAsync");
                } catch (Exception e) {
                    throw new RuntimeException("mulThreadSetCategory 异常");
                } finally {
                    // 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
                    countDownLatch.countDown();
                }
            });
        }
        try {
            // 阻塞一下 保证之前的所有的线程都执行完成，才会走下面的；
            countDownLatch.await();
        } catch (Exception e) {
            log.error("阻塞异常:" + e.getMessage());
        }

        return resList;
    }

    private <T> List<T> setCategory(List<T> tList, List categoryListRedis, Field categoryCodeField, List<Field> busFieldInfoList, Map<String, String> busAndCategoryMap) throws Exception {
        List<JSONObject> categoryInfos = new ArrayList<>();
        for (Object categoryRedis : categoryListRedis) {
            String mtlStr = (String) categoryRedis;
            JSONObject categoryInfo = JSONObject.parseObject(mtlStr);
            categoryInfos.add(categoryInfo);
        }
        for (T t : tList) {
            String categoryCode = (String) categoryCodeField.get(t);
            categoryInfos.stream().filter(item->item != null && item.get("categoryCode") != null && item.get("categoryCode").equals(categoryCode))
                    .findFirst()
                    .ifPresent(materialInfo -> {
                        for (String busFieldName : busAndCategoryMap.keySet()) {
                            String categoryFieldName = busAndCategoryMap.get(busFieldName);
                            busFieldInfoList.stream().filter(field -> field.getName().equals(busFieldName)).findFirst().ifPresent(fieldInfo -> {
                                this.setFieldValue(t, fieldInfo, materialInfo.get(categoryFieldName));
                            });
                        }
                    });
        }
        return tList;
    }

}
