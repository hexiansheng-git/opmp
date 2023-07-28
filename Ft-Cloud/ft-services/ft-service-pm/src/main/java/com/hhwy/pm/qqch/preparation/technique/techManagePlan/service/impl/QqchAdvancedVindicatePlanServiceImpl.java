package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchAdvancedVindicatePlanBudget;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchAdvancedVindicatePlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchAdvancedVindicatePlanBudgetMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchAdvancedVindicatePlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchAdvancedVindicatePlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
