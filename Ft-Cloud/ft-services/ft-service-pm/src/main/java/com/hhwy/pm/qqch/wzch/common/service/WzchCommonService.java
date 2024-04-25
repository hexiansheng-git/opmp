package com.hhwy.pm.qqch.wzch.common.service;

import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.domain.base.system.currency.CurrencyInfo;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.domain.base.system.periodCurrency.PeriodCurrency;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.qqch.wzch.common.mapper.WzchCommonMapper;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.AjaxResultUtil;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.ParamUtils;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.common.PmsConstant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.field.FieldUtils;
import com.hhwy.utils.multithreading.asyn.AsyncExecutor;
import com.hhwy.utils.redisUtil.RedisUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * 物资策划通用业务类
 *
 * @author mls
 */

@Slf4j
@Service
public class WzchCommonService {

//    @Resource
//    private IPeriodCurrencyService periodCurrencyService;

    @Resource
    private RedisUtils redisUtils;

//    @Autowired
//    private ICurrencyInfoService currencyInfoService;

    @Resource
    private AsyncExecutor asyncExecutor;

    @Autowired
    private WzchCommonMapper commonMapper;
    @Resource
    private SystemApiService systemApiService;
    @Resource
    private SystemServiceApi systemServiceApi;
    
//    @Resource
//    private IMaterialInfoService materialInfoService;

    /**
     * 校验项目是否已经被选择 (新增时候用)
     *
     * @param projectId 项目id
     * @param tableName 表名称
     * @return
     */
    public void verifyProjectSelected(Long projectId, String tableName) {
        this.verifyProjectSelected(projectId, tableName, null);
    }

    /**
     * 校验项目是否已经被选择 (编辑, 调整时候用)
     *
     * @param projectId  项目id
     * @param tableName  表名称
     * @param businessId 业务id (编辑数据的时候会进行id查询, 调整的时候也会根据id查询)
     * @return
     */
    public void verifyProjectSelected(Long projectId, String tableName, Long businessId) {
        verifyProjectSelected(projectId, tableName, businessId, null, null);
    }

    public void verifyProjectSelected(Long projectId, String tableName, Long businessId, String code, String codeFiledName) {
        Assert.notNull(projectId, "项目id不能为空");
        Assert.notNull(tableName, "表名称不能为空");
        int i = this.commonMapper.verifyProjectSelected(projectId, tableName, businessId, code, codeFiledName);
        if (i != 0) {
            throw new RuntimeException("单据选择的项目已经被选择, 请选择其他项目");
        }
    }


    /**
     * 校验单据能否被调整 (数据的状态是生效的才可以调整)
     *
     * @param tableName  表名称
     * @param businessId 业务id (编辑数据的时候会进行id查询, 调整的时候也会根据id查询)
     * @return
     */
    public void canAdjust(Long businessId, String tableName) {
        Assert.notNull(businessId, "主键不能为空");
        Assert.notNull(tableName, "表名称不能为空");
        String valid = this.commonMapper.selectCanAdjust(businessId, tableName);
        if (!"1".equals(valid)) {
            throw new CustomBusinessException("只能调整生效的单据");
        }
    }

    /**
     * 校验单据能否被调整 (单条数据只能调整一次)
     *
     * @param tableName  表名称
     * @param businessId 业务id (编辑数据的时候会进行id查询, 调整的时候也会根据id查询)
     * @return
     */
    public void canAdjustOnly(Long businessId, String tableName, Long projectId) {
        Assert.notNull(businessId, "主键不能为空");
        Assert.notNull(tableName, "表名称不能为空");
        Assert.notNull(projectId, "项目id不能为空");
        String valid = this.commonMapper.selectCanAdjust(businessId, tableName);
        if (!"1".equals(valid)) {
            throw new CustomBusinessException("只能调整生效的单据");
        }
        //项目中 版本号最大的id
        Long idMax = this.commonMapper.selectCanAdjustOnly(tableName, projectId);
        if (businessId.longValue() != idMax.longValue()) {
            throw new CustomBusinessException("此条数据只能调整一次");
        }
    }

    public void canAdjustOnly2(Long businessId, String tableName, Long projectId) {
        Assert.notNull(businessId, "主键不能为空");
        Assert.notNull(tableName, "表名称不能为空");
        Assert.notNull(projectId, "项目id不能为空");
        String valid = this.commonMapper.selectCanAdjust2(businessId, tableName);
        if (!"1".equals(valid)) {
            throw new CustomBusinessException("只能调整生效的单据");
        }
        //项目中 版本号最大的id
        Long idMax = this.commonMapper.selectCanAdjustOnly2(tableName, projectId);
        if (businessId.longValue() != idMax.longValue()) {
            throw new CustomBusinessException("此条数据只能调整一次");
        }
    }

    /**
     * 物资策划 设置单个物资的 物资名称 规格型号 单位
     * <p>
     * 默认
     * 物资编码 字段名称 : materialCode
     * 物资名称 字段名称 : materialName
     * 规格型号 字段名称 : materialSpec
     * 单位    字段名称 : unit
     *
     * @param t   业务数据
     * @param <T>
     * @return 封装有物资信息的业务数据
     */
    public <T> T setWzchtMaterialInfo(T t) {
        return this.setWzchtMaterialInfo(Collections.singletonList(t)).get(0);
    }


    /**
     * 物资策划 批量设置物资的 物资名称 规格型号 单位
     * <p>
     * 默认字段名
     * 物资编码 字段名称 : materialCode
     * 物资名称 字段名称 : materialName
     * 规格型号 字段名称 : materialSpec
     * 单位    字段名称 : unit
     *
     * @return 封装有物资信息的业务数据
     */

    private Map<String, String> busAndMaterialMap() {
        Map<String, String> busAndMaterialMap = new HashMap<>(4);
        busAndMaterialMap.put("materialName", "materialName");
        busAndMaterialMap.put("materialSpec", "materialSpec");
        busAndMaterialMap.put("unit", "unit");
        return busAndMaterialMap;
    }

    /**
     * redis
     *
     * @param tList
     * @param <T>
     * @return
     */
    public <T> List<T> setWzchtMaterialInfo(List<T> tList) {
        Map<String, String> busAndMaterialMap = busAndMaterialMap();
        return this.setMaterialInfo(tList, "materialCode", busAndMaterialMap);
    }


    public <T> List<T> setPartsInfo(List<T> tList) {
        Map<String, String> busAndMaterialMap = new HashMap<>(4);
        busAndMaterialMap.put("materialName", "materialName");
        busAndMaterialMap.put("materialSpec", "materialSpec");
        busAndMaterialMap.put("unit", "unit");
        busAndMaterialMap.put("partNo", "partNo");
        busAndMaterialMap.put("ownType", "ownType");
        busAndMaterialMap.put("ownSite", "ownSite");
        return this.setMaterialInfo(tList, "materialCode", busAndMaterialMap);
    }


    /**
     * 数据库
     *
     * @param tList
     * @param materialInfo
     * @param <T>
     * @return
     */
//    public <T> List<T> setWzchtMaterialInfoOfDB(List<T> tList, MaterialInfo materialInfo) {
//        Map<String, String> busAndMaterialMap = busAndMaterialMap();
//        return this.setMaterialInfoOfDB(tList, "materialCode", materialInfo, busAndMaterialMap);
//    }
//
//    private <T> List<T> setMaterialInfoOfDB(List<T> tList, String materialCodeFieldName, MaterialInfo materialInfo, Map<String, String> busAndMaterialMap) {
//        try {
//            List<MaterialInfo> materialInfos = queryMaterialInfo(materialInfo);
//            if (CollectionUtils.isEmpty(materialInfos)) {
//                return new ArrayList<>();
//            }
//            T t = tList.get(0);
//            Class<?> aClass = t.getClass();
//            Field materialCodeField = aClass.getDeclaredField(materialCodeFieldName);
//            materialCodeField.setAccessible(true);
//            List<Field> busFieldInfoList = new ArrayList<>(busAndMaterialMap.size());
//            for (String busFieldName : busAndMaterialMap.keySet()) {
//                Field busField = null;
//
//                busField = aClass.getDeclaredField(busFieldName);
//
//                busField.setAccessible(true);
//                busFieldInfoList.add(busField);
//            }
//            setMaterialOfDB(tList, materialInfos, materialCodeField, busFieldInfoList, busAndMaterialMap);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return tList;
//    }
//
//    public List<MaterialInfo> queryMaterialInfo(MaterialInfo materialInfo) {
//        if (StringUtils.isBlank(materialInfo.getMaterialName()) && StringUtils.isBlank(materialInfo.getMaterialSpec())) {
//            return null;
//        }
//        return materialInfoService.selectMaterialInfoList(materialInfo);
//    }


    /**
     * 设置业务数据的物资信息
     *
     * @param t                     业务数据
     * @param materialCodeFieldName 业务数据物资编码名称
     * @param busAndMaterialMap     k:业务数据字段名称; v:物资信息字段名称
     * @param <T>
     * @return 封装有物资信息的业务数据
     */
    public <T> T setMaterialInfo(T t, String materialCodeFieldName, Map<String, String> busAndMaterialMap) {
        List<T> ts = this.setMaterialInfo(Collections.singletonList(t), materialCodeFieldName, busAndMaterialMap);
        return ts.get(0);
    }


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
            if(CollectionUtils.isEmpty(materialCodeList)){
                return tList;
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

    private <T> List<T> setMaterialOfDB(final List<T> tList, final List<MaterialInfo> materialList, Field materialCodeField, final List<Field> busFieldList, final Map<String, String> busAndMaterialMap) throws Exception {
        // 从redis中获取的物资信息
        // List<MaterialInfo> materialInfos = JSONObject.parseArray(JSONObject.toJSONString(materialListRedis), MaterialInfo.class);
        List<JSONObject> materialInfos = new ArrayList<>();
        for (MaterialInfo material : materialList) {
            String mtlStr = (String) JSONObject.toJSONString(material);
            JSONObject materialInfo = JSONObject.parseObject(mtlStr);
            materialInfos.add(materialInfo);
        }
        for (T t : tList) {
            String materialCode = (String) materialCodeField.get(t);
            materialInfos.stream().filter(item -> item.get("materialCode") != null && item.get("materialCode").equals(materialCode))
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
                Object o = materialCodeField.get(item);
                if(ObjectUtils.isEmpty(o))
                    return ;
                // 获取物资编码值
                String materialCode = (String) o;
                codes.add(materialCode);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("物资编码不能为空");
            }
        });
        return codes;
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


    @Deprecated
    public <T> List<T> importDealDict(List<T> detailList, Map<String, String> filedDictMap) {
        if (CollectionUtils.isEmpty(detailList)) return detailList;
        T t = detailList.get(0);
        Class<?> aClass = t.getClass();

        Map<String, List<SysDictData>> fieldDicListMap = new HashMap<>(filedDictMap.size());

        List<Field> fields = new ArrayList<>();

        // 字段对应的字典项进行封装
        for (String fieldName : filedDictMap.keySet()) {
            String dictType = filedDictMap.get(fieldName);
            List<SysDictData> sysDictData = systemApiService.selectDictDataByType(dictType);
            fieldDicListMap.put(fieldName, sysDictData);
            Field declaredField = null;
            try {
                declaredField = aClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                declaredField = getSuperFieldByName(fieldName, aClass);
            }
            declaredField.setAccessible(true);
            fields.add(declaredField);
        }

        if (detailList.size() > 1000) {
            // 用多线程
            detailList = this.mulThreadGetDic(detailList, fields, fieldDicListMap);
        } else {
            // 单线程
            detailList = this.getDic(detailList, fields, fieldDicListMap);
        }
        
        return detailList;
    }

    private <T> List<T> mulThreadGetDic(List<T> detailList, List<Field> fields, Map<String, List<SysDictData>> fieldDicListMap) {

        CopyOnWriteArrayList<T> resList = new CopyOnWriteArrayList<>();
        // 将原有集合每一百个分一组
        List<List<T>> partition = ListUtils.partition(detailList, 100);
        CountDownLatch countDownLatch = new CountDownLatch(partition.size());

        // 多线程
        for (List<T> ts : partition) {
            // 在这里使用默认的多线程
            asyncExecutor.defaultAsyncExecute(() -> {
                try {
                    log.warn("start executeAsync");

                    List<T> partDetailList = this.getDic(ts, fields, fieldDicListMap);
                    // 不为空才将数据加入
                    if (CollectionUtils.isNotEmpty(partDetailList)) resList.addAll(partDetailList);
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


    @Deprecated
    public <T> List<T> exportDealDict(List<T> detailList, Map<String, String> filedDictMap) {
        if (CollectionUtils.isEmpty(detailList)) return detailList;
        try {
            T t = detailList.get(0);
            Class<?> aClass = t.getClass();

            Map<String, List<SysDictData>> fieldDicListMap = new HashMap<>(filedDictMap.size());

            List<Field> fields = new ArrayList<>();

            // 字段对应的字典项进行封装
            for (String fieldName : filedDictMap.keySet()) {
                String dictType = filedDictMap.get(fieldName);
                List<SysDictData> sysDictData = systemApiService.selectDictDataByType(dictType);
                fieldDicListMap.put(fieldName, sysDictData);
                Field declaredField = null;
                try {
                    declaredField = aClass.getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                    try {
                        declaredField = getSuperFieldByName(fieldName, aClass);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                declaredField.setAccessible(true);
                fields.add(declaredField);
            }

            if (detailList.size() > 1000) {
                // 用多线程
                detailList = this.mulThreadSetDic(detailList, fields, fieldDicListMap);
            } else {
                // 单线程
                detailList = this.setDic(detailList, fields, fieldDicListMap);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return detailList;
    }

    private <T> List<T> mulThreadSetDic(List<T> detailList, List<Field> fields, Map<String, List<SysDictData>> fieldDicListMap) {

        CopyOnWriteArrayList<T> resList = new CopyOnWriteArrayList<>();
        // 将原有集合每一百个分一组
        List<List<T>> partition = ListUtils.partition(detailList, 100);
        CountDownLatch countDownLatch = new CountDownLatch(partition.size());

        // 多线程
        for (List<T> ts : partition) {
            // 在这里使用默认的多线程
            asyncExecutor.defaultAsyncExecute(() -> {
                try {
                    log.warn("start executeAsync");

                    List<T> partDetailList = this.setDic(ts, fields, fieldDicListMap);
                    // 不为空才将数据加入
                    if (CollectionUtils.isNotEmpty(partDetailList)) resList.addAll(partDetailList);
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

    private <T> List<T> setDic(List<T> detailList, List<Field> fields, Map<String, List<SysDictData>> fieldDicListMap) {
        for (T detail : detailList) {
            for (Field field : fields) {
                // 字段名称
                String fieldName = field.getName();
                // 字典项目数据 根据字段名称获取查询好的字典项
                List<SysDictData> sysDictData = fieldDicListMap.get(fieldName);
                // 获取当前字段的值 此时是dicValue 要查询dicType
                try {
                    Object o = field.get(detail);

                    // 如果当前值为null 就直接跳过
                    if (o == null || CollectionUtils.isEmpty(sysDictData)) continue;

                    // 从字典项目中根据dicValue 获取 dicType
                    sysDictData.stream()
                            .filter(i -> i.getDictValue() != null && i.getDictValue().equals(String.valueOf(o)))
                            .findFirst()
                            .ifPresent(f -> {
                                setFieldValue(detail, field, f.getDictLabel());
                            });
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException(e.getMessage());
                }

            }
        }
        return detailList;
    }


    private <T> List<T> getDic(List<T> detailList, List<Field> fields, Map<String, List<SysDictData>> fieldDicListMap) {
        for (T detail : detailList) {
            for (Field field : fields) {
                // 字段名称
                String fieldName = field.getName();
                // 字典项目数据 根据字段名称获取查询好的字典项
                List<SysDictData> sysDictData = fieldDicListMap.get(fieldName);
                // 获取当前字段的值 此时是dicValue 要查询dicType
                try {
                    Object o = field.get(detail);

                    // 如果当前值为null 就直接跳过
                    if (o == null || CollectionUtils.isEmpty(sysDictData)) continue;

                    // 从字典项目中根据dicValue 获取 dicType
                    sysDictData.stream()
                            .filter(i -> i.getDictLabel() != null && i.getDictLabel().equals(String.valueOf(o)))
                            .findFirst()
                            .ifPresent(f -> {
                                setFieldValue(detail, field, f.getDictValue());
                            });
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException(e.getMessage());
                }

            }
        }
        return detailList;
    }

    /**
     * @param detailList
     * @param filedDictMap
     * @param <T>
     * @return
     */
    @Deprecated
    public <T> List<T> setDicValue(List<T> detailList, Map<String, String> filedDictMap) {
        if (CollectionUtils.isEmpty(detailList)) return detailList;
        T t = detailList.get(0);
        Class<?> aClass = t.getClass();

        Map<String, List<SysDictData>> fieldDicListMap = new HashMap<>(filedDictMap.size());

        Map<Field, Field> fieldFieldMap = new HashMap<>();
        // 字段对应的字典项进行封装
        for (String fieldName : filedDictMap.keySet()) {
            String dictType = filedDictMap.get(fieldName);
            List<SysDictData> sysDictData = systemApiService.selectDictDataByType(dictType);
            fieldDicListMap.put(fieldName, sysDictData);
            Field declaredField = null;
            Field nameField = null;
            try {
                declaredField = aClass.getDeclaredField(fieldName.split("_")[0]);
                nameField = aClass.getDeclaredField(fieldName.split("_")[1]);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                declaredField = getSuperFieldByName(fieldName.split("_")[0], aClass);
                nameField = getSuperFieldByName(fieldName.split("_")[1], aClass);
            }
            declaredField.setAccessible(true);
            nameField.setAccessible(true);
            fieldFieldMap.put(declaredField, nameField);
        }

        if (detailList.size() > 1000) {
            // 用多线程
            detailList = this.mulThreadSetNameDic(detailList, fieldFieldMap, fieldDicListMap);
        } else {
            // 单线程
            detailList = this.setNameDic(detailList, fieldFieldMap, fieldDicListMap);
        }
        return detailList;
    }

    private <T> List<T> mulThreadSetNameDic(List<T> detailList, Map<Field, Field> fieldFieldMap, Map<String, List<SysDictData>> fieldDicListMap) {
        CopyOnWriteArrayList<T> resList = new CopyOnWriteArrayList<>();
        // 将原有集合每一百个分一组
        List<List<T>> partition = ListUtils.partition(detailList, 100);
        CountDownLatch countDownLatch = new CountDownLatch(partition.size());

        // 多线程
        for (List<T> ts : partition) {
            // 在这里使用默认的多线程
            asyncExecutor.defaultAsyncExecute(() -> {
                try {
                    log.warn("start executeAsync");

                    List<T> partDetailList = this.setNameDic(ts, fieldFieldMap, fieldDicListMap);
                    // 不为空才将数据加入
                    if (CollectionUtils.isNotEmpty(partDetailList)) resList.addAll(partDetailList);
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

    private <T> List<T> setNameDic(List<T> detailList, Map<Field, Field> fieldFieldMap, Map<String, List<SysDictData>> fieldDicListMap) {
        for (T detail : detailList) {
            for (Field field : fieldFieldMap.keySet()) {
                // 字段名称
                String fieldName = field.getName();
                Field nameField = fieldFieldMap.get(field);
                String nameFieldName = nameField.getName();
                // 字典项目数据 根据字段名称获取查询好的字典项
                List<SysDictData> sysDictData = fieldDicListMap.get(fieldName + "_" + nameFieldName);
                // 获取当前字段的值 此时是dicValue 要查询dicType
                try {
                    Object o = field.get(detail);

                    // 如果当前值为null 就直接跳过
                    if (o == null || CollectionUtils.isEmpty(sysDictData)) continue;

                    // 从字典项目中根据dicValue 获取 dicType
                    sysDictData.stream()
                            .filter(i -> i.getDictValue() != null && i.getDictValue().equals(String.valueOf(o)))
                            .findFirst()
                            .ifPresent(f -> {
                                setFieldValue(detail, nameField, f.getDictLabel());
                            });
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException(e.getMessage());
                }

            }
        }
        return detailList;
    }


    private Field getSuperFieldByName(String field, Class cls) {
        Class superclass = cls.getSuperclass();
        if (superclass.equals(Object.class)) return null;
        try {
            return superclass.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            return getSuperFieldByName(field, superclass);
        }
    }

    public static void main(String[] args) {
    }

    /**
     * 校验物资编码在不在基础数据
     *
     * @param mltCodes
     */
    public void verifyMtlCodes(List mltCodes) {
        List<Object> materialListRedis = this.redisUtils.hMultiGet(PmsConstant.MATERIALREDISKEY, mltCodes);
        materialListRedis = materialListRedis.stream().filter(Objects::nonNull).collect(Collectors.toList());
        StringBuilder errorMsg = new StringBuilder();
        if (CollectionUtils.isEmpty(materialListRedis)) {
            throw new CustomBusinessException("物资明细输入的所有物资编号全部不存在");
        }
        List<JSONObject> materialInfos = new ArrayList<>();
        for (Object materialRedis : materialListRedis) {
            String mtlStr = (String) materialRedis;
            JSONObject materialInfo = JSONObject.parseObject(mtlStr);
            materialInfos.add(materialInfo);
        }

        for (Object mltCode : mltCodes) {
            String code = (String) mltCode;
            List<JSONObject> materialCode = materialInfos.stream().filter(item -> item.get("materialCode").equals(code)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(materialCode)) {
                errorMsg.append("物资编码：【").append(code).append("】不存在；");
            }
        }
        if (StringUtils.isNotEmpty(errorMsg.toString())) {
            throw new CustomBusinessException(errorMsg.toString());
        }
    }


    /**
     * 根据指定表名称 字段名称+字段值 删除数据
     *
     * @param tn
     * @param fieldName
     * @param fieldVal
     * @return
     */
    public int deleteByField(String tn, String fieldName, Long fieldVal) {
        return commonMapper.deleteByField(tn, fieldName, fieldVal);
    }

    public int batchDeleteByField(String tn, String fieldName, List<String> fieldVal) {
        return commonMapper.batchDeleteByField(tn, fieldName, fieldVal);
    }





    /**
     * 根据指定字段换算人民币金额
     *
     * @param list               数据
     * @param periodDate         期次时间
     * @param amountFiledName    原币种金额字段名称
     * @param currencyFiledName  币种字典名称
     * @param rmbAmountFieldName 换算成人民币的金额
     * @param <T>
     * @return
     */
//    public <T> List<T> calculateRmbPrice(List<T> list, Date periodDate, String amountFiledName, String currencyFiledName, String rmbAmountFieldName) {
//        try {
//            // 存放所有的币种编码信息
//            StringBuilder codes = new StringBuilder();
//            // 初始化字段工具类 防止在for循环中浪费资源
//            FieldUtils fieldUtils = FieldUtils.init();
//            if (CollectionUtils.isEmpty(list)) return list;
//            // 获取所有的币种编码
//            for (T t : list) {
//                // 获取字段值
//                Object fieldValue = fieldUtils.getFieldVal(currencyFiledName, t);
//                codes.append(fieldValue).append(",");
//            }
//
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String pDate = sdf.format(periodDate);
//            Map<String, String> map = new HashMap<>(2);
//            map.put("currency", codes.toString());
//            map.put("periodDate", pDate);
//            // k-币种 v-汇率
//            Map<String, BigDecimal> currRateMap = null;
//            // 查询币种汇率信息
//            List<PeriodCurrency> periodCurrencies = periodCurrencyService.selectRatePeriodByCodeAndCurrent(map);
//            // 将数据放在map中 方便使用
//            currRateMap = periodCurrencies.stream().collect(Collectors.toMap(PeriodCurrency::getCurrencyCode, PeriodCurrency::getRate));
//
//            // 人民币汇率
//            BigDecimal rmbRate = BigDecimal.ONE;
//            if (currRateMap.get(PmsConstant.CNY) == null) {
//                rmbRate = getRmbRate(periodDate);
//            }
//
//            // 计算公式 币种对应金额/币种对美元汇率*人民币对美元汇率
//            for (T t : list) {
//                BigDecimal amount = new BigDecimal(fieldUtils.getFieldVal(amountFiledName, t) + "");
//                BigDecimal currRate = currRateMap.get(String.valueOf(fieldUtils.getFieldVal(currencyFiledName, t)));
//                // 换算出人民币价格
//                BigDecimal rmbAmount = getRmbAmount(amount, currRate, rmbRate);
//                // 设置字段值
//                fieldUtils.setFieldVal(rmbAmountFieldName, rmbAmount, t);
//            }
//
//
//        } catch (Exception e) {
//            log.error("设置币种信息异常",e);
//            return list;
//        }
//
//        return null;
//    }


    private static BigDecimal getUsdAmount(BigDecimal amount, BigDecimal currRate) {
        return BigDecimalUtils.divide(amount, currRate, 4);
    }
    private static BigDecimal getRmbAmount(BigDecimal amount, BigDecimal currRate, BigDecimal rmbRate) {
        return BigDecimalUtils.multiply(BigDecimalUtils.divide(amount, currRate, 4), rmbRate);
    }
    public <T> void setCurrency(List<T> tList, String currencyFiledName, String currencyNameFiledName) {
        StringBuilder codes = new StringBuilder();
        FieldUtils fieldUtils = FieldUtils.init();

        if (CollectionUtils.isEmpty(tList)) return;
        try {
            // 获取所有的币种编码
            for (T t : tList) {
                // 获取字段值
                Object fieldValue = fieldUtils.getFieldVal(currencyNameFiledName, t);
                if(ObjectUtils.isBlank(fieldValue))
                    continue;
                codes.append(fieldValue).append(",");
            }
            if(ObjectUtils.isBlank(codes.toString()))
                return;
            // 获取币种信息
            CurrencyInfo currencyInfo = new CurrencyInfo();
            currencyInfo.setParams(ParamUtils.init().add("currencyNames", codes.toString()).get());

            List<CurrencyInfo> currencyInfos = AjaxResultUtil.getDataList(systemServiceApi.selectCurrencyList(currencyInfo),CurrencyInfo.class);
            for (T t : tList) {
                // 获取字段值
                String finalName = String.valueOf(fieldUtils.getFieldVal(currencyNameFiledName, t));

                currencyInfos.stream().filter(item -> finalName != null && finalName.equals(item.getCurrencyName())).findFirst().ifPresent(curr -> {
                    // 获取到币种名称并进行设置
                    fieldUtils.setFieldVal(currencyFiledName, curr.getCurrencyCode(), t);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

    public <T> void setCurrentName(T t, String currencyFiledName, String currencyNameFiledName) {
        setCurrentName(Collections.singletonList(t), "currency", "currencyName");
    }

    public <T> void setCurrentName(List<T> tList) {
        setCurrentName(tList, "currency", "currencyName");
    }

    /**
     * 设置币种名称
     *
     * @param tList
     * @param currencyFiledName
     * @param currencyNameFiledName
     * @param <T>
     */
    public <T> void setCurrentName(List<T> tList, String currencyFiledName, String currencyNameFiledName) {
        StringBuilder codes = new StringBuilder();
        FieldUtils fieldUtils = FieldUtils.init();

        if (CollectionUtils.isEmpty(tList)) return;
        try {
            // 获取所有的币种编码
            for (T t : tList) {
                // 获取字段值
                Object fieldValue = fieldUtils.getFieldVal(currencyFiledName, t);
                codes.append(fieldValue).append(",");
            }

            // 获取币种信息
            CurrencyInfo currencyInfo = new CurrencyInfo();
            currencyInfo.setParams(ParamUtils.init().add("currencyCodes", codes.toString()).get());

            List<CurrencyInfo> currencyInfos = AjaxResultUtil.getDataList(systemServiceApi.selectCurrencyList(currencyInfo), CurrencyInfo.class);
            for (T t : tList) {
                // 获取字段值
                String finalCode = String.valueOf(fieldUtils.getFieldVal(currencyFiledName, t));

                currencyInfos.stream().filter(item -> finalCode != null && finalCode.equals(item.getCurrencyCode())).findFirst().ifPresent(curr -> {
                    // 获取到币种名称并进行设置
                    fieldUtils.setFieldVal(currencyNameFiledName, curr.getCurrencyName(), t);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public <T> void setCurrentName(T t) {
        setCurrentName(t, "currency", "currencyName");
    }

    public <T> List<T> setTotalDemadCategoryCode(List<T> wzchTotalDemandDetailList) {
        HashMap<String, String> map = new HashMap<>(1);
        map.put("ptVar1", "categoryCode");
        return setMaterialInfo(wzchTotalDemandDetailList, "materialCode", map);

    }
}
