package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlanBudget;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAdvancedVindicatePlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAdvancedVindicatePlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchAdvancedVindicatePlanBudgetMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchAdvancedVindicatePlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchAdvancedVindicatePlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.excelUtil.ExcelHeadStyle;
import com.hhwy.utils.excelUtil.HeadVo;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author han
 * @date 2023-07-27 15:51:15
 * @remark 高新维护计划
 */
@Service
public class QqchAdvancedVindicatePlanServiceImpl implements IQqchAdvancedVindicatePlanService {

    @Autowired
    private QqchAdvancedVindicatePlanMapper qqchAdvancedVindicatePlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private QqchAdvancedVindicatePlanBudgetMapper qqchAdvancedVindicatePlanBudgetMapper;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchAdvancedVindicatePlan getQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan) {
        return qqchAdvancedVindicatePlanMapper.getQqchAdvancedVindicatePlan(qqchAdvancedVindicatePlan);
    }

    public List<QqchAdvancedVindicatePlan> getQqchAdvancedVindicatePlanList(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan) {
        return qqchAdvancedVindicatePlanMapper.getQqchAdvancedVindicatePlanList(qqchAdvancedVindicatePlan);
    }

    @Transactional
    public int insertQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan) {
        qqchAdvancedVindicatePlan.setId(IdWorker.createId());
        qqchAdvancedVindicatePlan.setCreateUser(SecurityUtils.getUserName());
        qqchAdvancedVindicatePlan.setCreateTime(DateUtils.getNowDate());
        return qqchAdvancedVindicatePlanMapper.insertQqchAdvancedVindicatePlan(qqchAdvancedVindicatePlan);
    }

    @Transactional
    public void insertQqchAdvancedVindicatePlanList(List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList, BigDecimal version) {
        //删除旧数据
        QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan = new QqchAdvancedVindicatePlan();
        qqchAdvancedVindicatePlan.setVersion(version);
        qqchAdvancedVindicatePlanMapper.deleteQqchAdvancedVindicatePlan(qqchAdvancedVindicatePlan);

        if(CollectionUtils.isEmpty(qqchAdvancedVindicatePlanList)){
            return;
        }
        int sort = 1;
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchAdvancedVindicatePlan advancedVindicatePlan : qqchAdvancedVindicatePlanList) {
            advancedVindicatePlan.setId(IdWorker.createId());
            advancedVindicatePlan.setValid(valid);
            advancedVindicatePlan.setVersion(version);
            advancedVindicatePlan.setSort(sort++);
            advancedVindicatePlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            advancedVindicatePlan.setCreateUserName(SecurityUtils.getUserName());
            advancedVindicatePlan.setCreateTime(DateUtils.getNowDate());
        }

        //处理子表数据
        this.disposeBudgetData(qqchAdvancedVindicatePlanList,version);

        qqchAdvancedVindicatePlanMapper.insertQqchAdvancedVindicatePlanList(qqchAdvancedVindicatePlanList);
    }

    /**
     * 处理子表数据
     * @param qqchAdvancedVindicatePlanList
     * @param version
     */
    public void disposeBudgetData(List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList, BigDecimal version){
        //删除旧数据
        QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget = new QqchAdvancedVindicatePlanBudget();
        qqchAdvancedVindicatePlanBudget.setVersion(version);
        qqchAdvancedVindicatePlanBudgetMapper.deleteQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanBudget);

        List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetList = new ArrayList<>();
        for (QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan : qqchAdvancedVindicatePlanList) {
            Long id = qqchAdvancedVindicatePlan.getId();
            Map<String, BigDecimal> vintageBudgetMap = qqchAdvancedVindicatePlan.getVintageBudgetMap();
            for (Map.Entry<String, BigDecimal> next : vintageBudgetMap.entrySet()) {
                String key = next.getKey();
                BigDecimal value = next.getValue();
                QqchAdvancedVindicatePlanBudget advancedVindicatePlanBudget = new QqchAdvancedVindicatePlanBudget();
                advancedVindicatePlanBudget.setId(IdWorker.createId());
                advancedVindicatePlanBudget.setMasterId(id);
                advancedVindicatePlanBudget.setVintage(key);
                advancedVindicatePlanBudget.setBudget(value);
                advancedVindicatePlanBudget.setVersion(version);
                advancedVindicatePlanBudget.setValid(Valid.YES);
                advancedVindicatePlanBudget.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                advancedVindicatePlanBudget.setCreateUserName(SecurityUtils.getUserName());
                advancedVindicatePlanBudget.setCreateTime(DateUtils.getNowDate());
                qqchAdvancedVindicatePlanBudgetList.add(advancedVindicatePlanBudget);
            }
        }

        if(CollectionUtils.isEmpty(qqchAdvancedVindicatePlanList)){
            return;
        }
        //插入新数据
        qqchAdvancedVindicatePlanBudgetMapper.insertQqchAdvancedVindicatePlanBudgetList(qqchAdvancedVindicatePlanBudgetList);
    }

    @Transactional
    public int updateQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan) {
        qqchAdvancedVindicatePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchAdvancedVindicatePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchAdvancedVindicatePlanMapper.updateQqchAdvancedVindicatePlan(qqchAdvancedVindicatePlan);
    }

    @Transactional
    public int updateQqchAdvancedVindicatePlanList(List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList) {
        for (QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan : qqchAdvancedVindicatePlanList) {
            qqchAdvancedVindicatePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchAdvancedVindicatePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchAdvancedVindicatePlanMapper.updateQqchAdvancedVindicatePlanList(qqchAdvancedVindicatePlanList);
    }

    @Transactional
    public int deleteQqchAdvancedVindicatePlan(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan) {
        qqchAdvancedVindicatePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchAdvancedVindicatePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchAdvancedVindicatePlanMapper.deleteQqchAdvancedVindicatePlan(qqchAdvancedVindicatePlan);
    }

    @Transactional
    public int deleteQqchAdvancedVindicatePlanByPks(List<Long> qqchAdvancedVindicatePlanPkList) {
        return qqchAdvancedVindicatePlanMapper.deleteQqchAdvancedVindicatePlanByPks(qqchAdvancedVindicatePlanPkList);
    }

    /**
     * 获取高新维护计划Vo
     * @param qqchAdvancedVindicatePlan
     * @return
     */
    @Override
    public QqchAdvancedVindicatePlanVo getQqchAdvancedVindicatePlanVo(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan) {
        QqchAdvancedVindicatePlanVo qqchAdvancedVindicatePlanVo = new QqchAdvancedVindicatePlanVo();

        BigDecimal version = qqchAdvancedVindicatePlan.getVersion();
        version = VersionUtil.getVersion("qqch_advanced_vindicate_plan",version);

        qqchAdvancedVindicatePlan.setVersion(version);
        List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList = qqchAdvancedVindicatePlanMapper.getQqchAdvancedVindicatePlanList(qqchAdvancedVindicatePlan);

        //设置年份对应预算
        this.setQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanList,version);

        //获取年份集合
        List<String> vintageList = this.getVintageListByVersion(version);

        qqchAdvancedVindicatePlanVo.setVersion(version);
        qqchAdvancedVindicatePlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchAdvancedVindicatePlanVo.setVintageList(vintageList);
        qqchAdvancedVindicatePlanVo.setQqchAdvancedVindicatePlanList(qqchAdvancedVindicatePlanList);
        return qqchAdvancedVindicatePlanVo;
    }

    /**
     * 设置预算
     * @param qqchAdvancedVindicatePlanList
     * @param version
     */
    public void setQqchAdvancedVindicatePlanBudget(List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList,BigDecimal version){
        //获取当前版本预算信息
        QqchAdvancedVindicatePlanBudget qqchAdvancedVindicatePlanBudget = new QqchAdvancedVindicatePlanBudget();
        qqchAdvancedVindicatePlanBudget.setVersion(version);
        List<QqchAdvancedVindicatePlanBudget> qqchAdvancedVindicatePlanBudgetList = qqchAdvancedVindicatePlanBudgetMapper.getQqchAdvancedVindicatePlanBudgetList(qqchAdvancedVindicatePlanBudget);

        for (QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan : qqchAdvancedVindicatePlanList) {
            Long id = qqchAdvancedVindicatePlan.getId();
            Map<String,BigDecimal> vintageBudgetMap = new HashMap<>();

            for (QqchAdvancedVindicatePlanBudget advancedVindicatePlanBudget : qqchAdvancedVindicatePlanBudgetList) {
                if(id.equals(advancedVindicatePlanBudget.getMasterId())){
                    vintageBudgetMap.put(advancedVindicatePlanBudget.getVintage(),advancedVindicatePlanBudget.getBudget());
                }
            }
            qqchAdvancedVindicatePlan.setVintageBudgetMap(vintageBudgetMap);
        }
    }

    /**
     * 获取年份集合
     * @param version
     * @return
     */
    public List<String> getVintageListByVersion(BigDecimal version){
        List<String> vintageList = new ArrayList<>();
        //获取当前版本最小起始日期
        Date minStartDate = qqchAdvancedVindicatePlanMapper.getMinStartDateByVersion(version);

        //获取当前版本最大完成日期
        Date maxEndDate = qqchAdvancedVindicatePlanMapper.getMaxEndDateByVersion(version);

        if(minStartDate == null || maxEndDate == null){
            return vintageList;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(minStartDate);
        //开始年份
        int startYear = calendar.get(Calendar.YEAR);

        calendar.setTime(maxEndDate);
        //结束年份
        int endYear = calendar.get(Calendar.YEAR);

        do {
            vintageList.add(startYear++ + "年");
        }while (startYear <= endYear);

        return vintageList;
    }

    /**
     * 保存/确认/提交
     * @param qqchAdvancedVindicatePlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchAdvancedVindicatePlanVo qqchAdvancedVindicatePlanVo) {
        String buttonMark = qqchAdvancedVindicatePlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchAdvancedVindicatePlanVo.getVersion();
        List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList = qqchAdvancedVindicatePlanVo.getQqchAdvancedVindicatePlanList();

        this.insertQqchAdvancedVindicatePlanList(qqchAdvancedVindicatePlanList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchAdvancedVindicatePlanVo.getMenuId();
            String stageIdentity = qqchAdvancedVindicatePlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

//    @Override
//    public List<List<String>> getHead(BigDecimal version){
//        List<List<String>> list = new ArrayList<>();
//        Field[] declaredFields = QqchAdvancedVindicatePlanExportVo.class.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            ExcelProperty excelProperty = declaredField.getAnnotation(ExcelProperty.class);
//            String[] value = excelProperty.value();
//            List<String> head = new ArrayList<>(Arrays.asList(value));
//            list.add(head);
//        }
//
//        version = VersionUtil.getVersion("qqch_advanced_vindicate_plan",version);
//        List<String> vintageListByVersion = getVintageListByVersion(version);
//        for (String s : vintageListByVersion) {
//            List<String> vintageList = new ArrayList<>();
//            vintageList.add("研发费用预算（万元）");
//            vintageList.add(s);
//            list.add(vintageList);
//        }
//        return list;
//    }

    /**
     * 导出
     * @param response
     * @param qqchAdvancedVindicatePlan
     */
    @Override
    public void export(HttpServletResponse response, QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan) {
        //获取表头
        List<HeadVo> headVoList = this.getHead(qqchAdvancedVindicatePlan.getVersion());
        //存放所有表头
        List<List<String>> heads = new ArrayList<>();
        //存放字段名
        List<String> keys = new ArrayList<>();
        //一共有多少个表头循环多少次
        for (int i = 0; i <= headVoList.size() - 1; i++) {
            //获取每一个表头的名称
            heads.add(headVoList.get(i).getHeadTitle());
            //获取每一个表头的字段名
            keys.add(headVoList.get(i).getKey());
        }

        List<Map<String, Object>> list = getQqchAdvancedVindicatePlanExportVoList(qqchAdvancedVindicatePlan);
        //存放所有导出的数据
        List<List<Object>> objs = new ArrayList<>();
        for (Map<String, Object> map : list) {
            List<Object> obj = new ArrayList<>();
            for (String key : keys) {
                Object o = map.get(key);
                if(o == null){
                    obj.add("");
                }else {
                    obj.add(o);
                }
            }
            objs.add(obj);
        }
        try {
            EasyExcel.write(response.getOutputStream())
                    .head(heads)
                    .registerWriteHandler(ExcelHeadStyle.getHorizontalCellStyleStrategy(response,"111"))
                    .sheet("明细")
                    .doWrite(objs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<HeadVo> getHead(BigDecimal version) {
        List<HeadVo> headVoList = new ArrayList<>();
        Field[] fields = QqchAdvancedVindicatePlanExportVo.class.getDeclaredFields();
        for (Field field : fields) {
            ReflectionUtils.makeAccessible(field);// 设置属性是可以访问的
            //判断当前字段注解是否ExcelProperty
            boolean annotationPresent = field.isAnnotationPresent(ExcelProperty.class);
            if (annotationPresent) {
                //获取注解
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                String[] value = excelProperty.value();
                //获取注解内的值变为表头
                List<String> head = new ArrayList<>(Arrays.asList(value));
                //获取第几个下标
                int index = excelProperty.index();
                //添加 表头  下标   字段名称
                HeadVo headVO = HeadVo.builder().headTitle(head).index(index).key(field.getName()).build();
                //添加到集合内
                headVoList.add(headVO);
            } else {
                version = VersionUtil.getVersion("qqch_advanced_vindicate_plan",version);
                List<String> vintageListByVersion = getVintageListByVersion(version);
                for (String s : vintageListByVersion) {
                    List<String> vintageList = new ArrayList<>();
                    vintageList.add("研发费用预算（万元）");
                    vintageList.add(s);
                    HeadVo headVO = HeadVo.builder().headTitle(vintageList).index(7).key(s).build();
                    headVoList.add(headVO);
                }
            }
        }
        //按照下标排序
        Collections.sort(headVoList);
        return headVoList;
    }

    /**
     * 获取导出数据
     * @param qqchAdvancedVindicatePlan
     * @return
     */
    @Override
    public List<Map<String, Object>> getQqchAdvancedVindicatePlanExportVoList(QqchAdvancedVindicatePlan qqchAdvancedVindicatePlan) {
        List<QqchAdvancedVindicatePlanExportVo> qqchAdvancedVindicatePlanExportVoList = new ArrayList<>();
        BigDecimal version = qqchAdvancedVindicatePlan.getVersion();
        version = VersionUtil.getVersion("qqch_advanced_vindicate_plan",version);
        qqchAdvancedVindicatePlan.setVersion(version);
        List<QqchAdvancedVindicatePlan> qqchAdvancedVindicatePlanList = qqchAdvancedVindicatePlanMapper.getQqchAdvancedVindicatePlanList(qqchAdvancedVindicatePlan);
        this.setQqchAdvancedVindicatePlanBudget(qqchAdvancedVindicatePlanList,version);
        for (QqchAdvancedVindicatePlan advancedVindicatePlan : qqchAdvancedVindicatePlanList) {
            QqchAdvancedVindicatePlanExportVo qqchAdvancedVindicatePlanExportVo = new QqchAdvancedVindicatePlanExportVo();
            BeanUtils.copyProperties(advancedVindicatePlan,qqchAdvancedVindicatePlanExportVo);
            qqchAdvancedVindicatePlanExportVoList.add(qqchAdvancedVindicatePlanExportVo);
        }
        return this.getKeysAndValues(qqchAdvancedVindicatePlanExportVoList);
    }

    /**
     * List<QqchAdvancedVindicatePlan>转为List<Map<string,object>
     * @param qqchAdvancedVindicatePlanExportVoList
     * @return
     */
    public List<Map<String, Object>> getKeysAndValues(List<QqchAdvancedVindicatePlanExportVo> qqchAdvancedVindicatePlanExportVoList) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QqchAdvancedVindicatePlanExportVo exportVo : qqchAdvancedVindicatePlanExportVoList) {
            Class<? extends QqchAdvancedVindicatePlanExportVo> userClass = exportVo.getClass();
            Field[] declaredFields = userClass.getDeclaredFields();
            Map<String, Object> listChild = new HashMap<>();
            for (Field declaredField : declaredFields) {
                ReflectionUtils.makeAccessible(declaredField);// 设置属性是可以访问的
                String name = declaredField.getName();
                if(!"vintageBudgetMap".equals(name)){
                    try {
                        listChild.put(declaredField.getName(), declaredField.get(exportVo));// 设置键值
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("非法参数异常");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("非法访问异常");
                    }
                }else {
                    Map<String, BigDecimal> vintageBudgetMap = exportVo.getVintageBudgetMap();
                    if(vintageBudgetMap != null){
                        for (Map.Entry<String, BigDecimal> next : vintageBudgetMap.entrySet()) {
                            String key = next.getKey();
                            BigDecimal value = next.getValue();
                            listChild.put(key, value);// 设置键值
                        }
                    }
                }
            }
            list.add(listChild);
        }
        return list;
    }
}
