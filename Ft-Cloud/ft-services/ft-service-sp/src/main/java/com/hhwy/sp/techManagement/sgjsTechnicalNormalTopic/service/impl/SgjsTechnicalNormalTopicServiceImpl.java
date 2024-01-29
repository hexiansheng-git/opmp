package com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.handler.HandleHelper;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hhwy.common.core.annotation.Excel;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.poi.ExcelUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopicDTO;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCost;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.domain.SgjsTechnicalNormalTopicCostDTO;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.sgjsTechnicalNormalTopicCost.service.ISgjsTechnicalNormalTopicCostService;
import com.hhwy.utils.excelUtil.ExcelHeadStyle;
import com.hhwy.utils.excelUtil.HeadVo;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.mapper.SgjsTechnicalNormalTopicMapper;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.service.ISgjsTechnicalNormalTopicService;
import com.hhwy.sp.techManagement.sgjsTechnicalNormalTopic.domain.SgjsTechnicalNormalTopic;
import com.hhwy.utils.idworker.IdWorker;

import javax.servlet.http.HttpServletResponse;

/***
 * 功能描述: 科技管理 - 一般课题研发管理
 * 作者: fushudong
 * 时间: 2024/1/25
 */
@Service
public class SgjsTechnicalNormalTopicServiceImpl implements ISgjsTechnicalNormalTopicService {

    @Autowired
    private SgjsTechnicalNormalTopicMapper sgjsTechnicalNormalTopicMapper;
    @Autowired
    private ISgjsTechnicalNormalTopicCostService sgjsTechnicalNormalTopicCostService;


    public SgjsTechnicalNormalTopic getSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        return sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    public List<SgjsTechnicalNormalTopic> getSgjsTechnicalNormalTopicList(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        List<SgjsTechnicalNormalTopic> resultList = sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopic);
        if (CollUtil.isEmpty(resultList)) return resultList;
        //子表查询
        SgjsTechnicalNormalTopicCost param = new SgjsTechnicalNormalTopicCost();
        param.setIds(resultList.stream().map(SgjsTechnicalNormalTopic::getId).toArray(Long[]::new));
        List<SgjsTechnicalNormalTopicCost> chidrenList = sgjsTechnicalNormalTopicCostService.getSgjsTechnicalNormalTopicCostList(param);
        if (CollUtil.isEmpty(chidrenList)) return resultList;
        //子表数据拼到主表，同时需要处理行列转换导致的列数不同问题，因为每个课题的研发预算年的数量可能不同
        SgjsTechnicalNormalTopicCost maxYear = chidrenList.stream()
                .filter(p -> null != p.getYear())
                .max(Comparator.comparingInt(SgjsTechnicalNormalTopicCost::getYear)).get();
        SgjsTechnicalNormalTopicCost minYear = chidrenList.stream()
                .filter(p -> null != p.getYear())
                .min(Comparator.comparingInt(SgjsTechnicalNormalTopicCost::getYear)).get();
        //得到最小年份与最大年份的区间列表，填入resultList每个对象中
        int[] range = NumberUtil.range(minYear.getYear(), maxYear.getYear());

        Map<Long, List<SgjsTechnicalNormalTopicCost>> childMap = chidrenList.stream()
                .collect(Collectors.groupingBy(SgjsTechnicalNormalTopicCost::getForeignId));
        Map<Long, SgjsTechnicalNormalTopic> mainMap = resultList.stream()
                .collect(Collectors.toMap(SgjsTechnicalNormalTopic::getId, value -> value));
        //遍历主表数据，将年份，预算数据填入，并对未包含全部年份的主表数据填充缺失年份和预算，预算默认0
        for (Map.Entry<Long, SgjsTechnicalNormalTopic> next : mainMap.entrySet()) {
            Long key = next.getKey();
            SgjsTechnicalNormalTopic value = next.getValue();
            List<SgjsTechnicalNormalTopicCost> sgjsTechnicalNormalTopicCosts = childMap.get(key);
            List<SgjsTechnicalNormalTopicCost> objects = new ArrayList<>();
            if (CollUtil.isNotEmpty(sgjsTechnicalNormalTopicCosts) && sgjsTechnicalNormalTopicCosts.size() == range.length) {
                //当前主表数有年份及预算数据,并且包含了全部年份
                sgjsTechnicalNormalTopicCosts.forEach(p -> {
                    objects.add(p);
                });
                value.setChildList(objects);
                continue;
            }
            Map<String, BigDecimal> map = new HashMap<>();
            if (CollUtil.isEmpty(sgjsTechnicalNormalTopicCosts)) {
                //当前主表数没有年份及预算数据
                for (int i = 0; i < range.length; i++) {
                    SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost = new SgjsTechnicalNormalTopicCost();
                    sgjsTechnicalNormalTopicCost.setYear(range[i]);
                    sgjsTechnicalNormalTopicCost.setRdCost(BigDecimal.ZERO);
                    objects.add(sgjsTechnicalNormalTopicCost);
                }
                value.setChildList(objects);
                continue;
            }
            //当前主表有年份及预算数据,但部分缺失
            Set<Integer> collect = sgjsTechnicalNormalTopicCosts.stream().map(SgjsTechnicalNormalTopicCost::getYear).collect(Collectors.toSet());
            objects.addAll(sgjsTechnicalNormalTopicCosts);
            for (int i = 0; i < range.length; i++) {
                if (!collect.contains(range[i])) {
                    SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost = new SgjsTechnicalNormalTopicCost();
                    sgjsTechnicalNormalTopicCost.setYear(range[i]);
                    sgjsTechnicalNormalTopicCost.setRdCost(BigDecimal.ZERO);
                    objects.add(sgjsTechnicalNormalTopicCost);
                }
            }
            value.setChildList(objects);
        }
        return new ArrayList<>(mainMap.values());
    }

    @Transactional
    public int insertSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        sgjsTechnicalNormalTopic.setId(IdWorker.createId());
        sgjsTechnicalNormalTopic.setCreateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopic.setCreateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicMapper.insertSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    @Transactional
    public int insertSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList) {
        if (CollUtil.isEmpty(sgjsTechnicalNormalTopicList)) {
            SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic = new SgjsTechnicalNormalTopic();
            sgjsTechnicalNormalTopicMapper.deleteSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
            SgjsTechnicalNormalTopicCost sgjsTechnicalNormalTopicCost = new SgjsTechnicalNormalTopicCost();
            sgjsTechnicalNormalTopicCostService.deleteSgjsTechnicalNormalTopicCost(sgjsTechnicalNormalTopicCost);
            return 1;
        }
        List<SgjsTechnicalNormalTopicCost> childSave = new ArrayList<>();
        for (SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic : sgjsTechnicalNormalTopicList) {
            Long id = IdWorker.createId();
            sgjsTechnicalNormalTopic.setId(id);
            sgjsTechnicalNormalTopic.setCreateUser(SecurityUtils.getUserName());
            sgjsTechnicalNormalTopic.setCreateTime(DateUtils.getNowDate());
            List<SgjsTechnicalNormalTopicCost> childList = sgjsTechnicalNormalTopic.getChildList();
            childList.forEach(p -> p.setForeignId(id));
            childSave.addAll(childList);
        }
        sgjsTechnicalNormalTopicMapper.insertSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicList);
        return sgjsTechnicalNormalTopicCostService.insertSgjsTechnicalNormalTopicCostList(childSave);
    }

    @Transactional
    public int updateSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        sgjsTechnicalNormalTopic.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopic.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicMapper.updateSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    @Transactional
    public int updateSgjsTechnicalNormalTopicList(List<SgjsTechnicalNormalTopic> sgjsTechnicalNormalTopicList) {
        for (SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic : sgjsTechnicalNormalTopicList) {
            sgjsTechnicalNormalTopic.setUpdateUser(SecurityUtils.getUserName());
            sgjsTechnicalNormalTopic.setUpdateTime(DateUtils.getNowDate());
        }
        return sgjsTechnicalNormalTopicMapper.updateSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicList);
    }

    @Transactional
    public int deleteSgjsTechnicalNormalTopic(SgjsTechnicalNormalTopic sgjsTechnicalNormalTopic) {
        sgjsTechnicalNormalTopic.setUpdateUser(SecurityUtils.getUserName());
        sgjsTechnicalNormalTopic.setUpdateTime(DateUtils.getNowDate());
        return sgjsTechnicalNormalTopicMapper.deleteSgjsTechnicalNormalTopic(sgjsTechnicalNormalTopic);
    }

    @Transactional
    public int deleteSgjsTechnicalNormalTopicByPks(List<Long> sgjsTechnicalNormalTopicPkList) {
        return sgjsTechnicalNormalTopicMapper.deleteSgjsTechnicalNormalTopicByPks(sgjsTechnicalNormalTopicPkList);
    }

    @Override
    public void export(HttpServletResponse response, SgjsTechnicalNormalTopic sgjsTechnicalNormalTopicParam) throws Exception {
        List<SgjsTechnicalNormalTopic> resultList = sgjsTechnicalNormalTopicMapper.getSgjsTechnicalNormalTopicList(sgjsTechnicalNormalTopicParam);
        if (CollUtil.isEmpty(resultList)) return;
        //子表查询
        SgjsTechnicalNormalTopicCost param = new SgjsTechnicalNormalTopicCost();
        param.setIds(resultList.stream().map(SgjsTechnicalNormalTopic::getId).toArray(Long[]::new));
        List<SgjsTechnicalNormalTopicCost> chidrenList = sgjsTechnicalNormalTopicCostService.getSgjsTechnicalNormalTopicCostList(param);
        if (CollUtil.isEmpty(chidrenList)) {
            ExcelUtils<SgjsTechnicalNormalTopic> util = new ExcelUtils<>(SgjsTechnicalNormalTopic.class);
            util.exportExcel(response, resultList, DateUtils.getDate());
        }
        Map<Long, List<SgjsTechnicalNormalTopicCost>> collect = chidrenList.stream().collect(Collectors.groupingBy(SgjsTechnicalNormalTopicCost::getForeignId));
        for (SgjsTechnicalNormalTopic obj : resultList) {
            Class<?> aClass = SgjsTechnicalNormalTopic.class;

//            Class<? extends SgjsTechnicalNormalTopic> aClass = obj.getClass();
            List<SgjsTechnicalNormalTopicCost> childList = collect.get(obj.getId());
            for (SgjsTechnicalNormalTopicCost cost : childList) {
                //新增对象属性
                Field field = aClass.getDeclaredField("id");
                field.setAccessible(true);
                Object obj1 = aClass.newInstance();
                field.set(obj1, 1L);
//                Field newField = new Field(SgjsTechnicalNormalTopic.class, cost.getYear() + "年", String.class);
//                Field newField = aClass.getDeclaredField(cost.getYear() + "年");
                Field newField = aClass.getDeclaredField("value");
                //设置属性值
                newField.set(obj, cost.getRdCost());

//                Annotation annotation = newField.getAnnotation(Excel.class);
                //为属性设置注解
                Excel excelAnnotation = getExcelAnnotation();
                Annotation[] annotations = newField.getAnnotations();
                Annotation[] newAnnotations = Arrays.copyOf(annotations, annotations.length + 1);
                newAnnotations[annotations.length] = excelAnnotation;

                Field annotationsField = Field.class.getDeclaredField("annotations");
                annotationsField.setAccessible(true);
                annotationsField.set(newField, newAnnotations);
            }
        }
        ExcelUtils<SgjsTechnicalNormalTopic> util = new ExcelUtils<>(SgjsTechnicalNormalTopic.class);
        util.exportExcel(response, resultList, DateUtils.getDate());
    }

    private Excel getExcelAnnotation() {

        return new Excel(){
            @Override
            public int sort() {
                return 0;
            }

            @Override
            public String name() {
                return null;
            }

            @Override
            public String dateFormat() {
                return null;
            }

            @Override
            public String readConverterExp() {
                return null;
            }

            @Override
            public String dictType() {
                return null;
            }

            @Override
            public String resolveMethod() {
                return null;
            }

            @Override
            public String resolveMethodForExport() {
                return null;
            }

            @Override
            public String resolveMethodForImport() {
                return null;
            }

            @Override
            public String separator() {
                return null;
            }

            @Override
            public int scale() {
                return 0;
            }

            @Override
            public int roundingMode() {
                return 0;
            }

            @Override
            public ColumnType cellType() {
                return null;
            }

            @Override
            public double height() {
                return 0;
            }

            @Override
            public double width() {
                return 0;
            }

            @Override
            public String suffix() {
                return null;
            }

            @Override
            public String defaultValue() {
                return null;
            }

            @Override
            public String prompt() {
                return null;
            }

            @Override
            public String[] combo() {
                return new String[0];
            }

            @Override
            public boolean isExport() {
                return false;
            }

            @Override
            public String targetAttr() {
                return null;
            }

            @Override
            public boolean isStatistics() {
                return false;
            }

            @Override
            public Align align() {
                return null;
            }

            @Override
            public Type type() {
                return null;
            }
            @Override
            public Class<? extends Annotation> annotationType() {
                return Excel.class;
            }
        };
    }

    public List<List<String>> getTitle(List<SgjsTechnicalNormalTopicCost> chidrenList){
        List<List<String>> title = new ArrayList<>();
        Field[] declaredFields = SgjsTechnicalNormalTopicDTO.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            String[] value = annotation.value();
            title.add(Arrays.asList(value));
        }
        if (CollUtil.isEmpty(chidrenList)) {
            return title;
        }
        Set<Integer> collect = chidrenList.stream().map(SgjsTechnicalNormalTopicCost::getYear).collect(Collectors.toSet());
        if (CollUtil.isEmpty(title)) {
            return title;
        }
        collect.forEach(p ->{
            List<String> objects = new ArrayList<>();
            objects.add(p + "年");
            title.add(objects);
        });
        return title;
    }
}
