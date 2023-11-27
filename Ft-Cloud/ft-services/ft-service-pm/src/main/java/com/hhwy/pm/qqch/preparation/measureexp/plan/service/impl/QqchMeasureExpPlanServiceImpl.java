package com.hhwy.pm.qqch.preparation.measureexp.plan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.CompileInterface;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.measureexp.plan.domain.QqchMeasureExpPlan;
import com.hhwy.pm.qqch.preparation.measureexp.plan.mapper.QqchMeasureExpPlanMapper;
import com.hhwy.pm.qqch.preparation.measureexp.plan.service.IQqchMeasureExpPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mls
 * @date 2023-07-25 18:01:32
 * @remark
 */
@Service
public class QqchMeasureExpPlanServiceImpl implements IQqchMeasureExpPlanService, CompileInterface<QqchMeasureExpPlan> {


    private final static String TN = "qqch_measure_exp_plan";
    @Resource
    private QqchMeasureExpPlanMapper qqchMeasureExpPlanMapper;


    public QqchMeasureExpPlan getQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan) {
        return qqchMeasureExpPlanMapper.getQqchMeasureExpPlan(qqchMeasureExpPlan);
    }

    public List<QqchMeasureExpPlan> getQqchMeasureExpPlanList(QqchMeasureExpPlan qqchMeasureExpPlan) {
        return qqchMeasureExpPlanMapper.getQqchMeasureExpPlanList(qqchMeasureExpPlan);
    }

    @Transactional
    public int insertQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan) {
        qqchMeasureExpPlan.setId(IdWorker.createId());
        qqchMeasureExpPlan.setCreateUser(SecurityUtils.getUserName());
        qqchMeasureExpPlan.setCreateTime(DateUtils.getNowDate());
        return qqchMeasureExpPlanMapper.insertQqchMeasureExpPlan(qqchMeasureExpPlan);
    }

    @Transactional
    public int insertQqchMeasureExpPlanList(List<QqchMeasureExpPlan> qqchMeasureExpPlanList) {
        for (QqchMeasureExpPlan qqchMeasureExpPlan : qqchMeasureExpPlanList) {
            qqchMeasureExpPlan.setId(IdWorker.createId());
            qqchMeasureExpPlan.setCreateUser(SecurityUtils.getUserName());
            qqchMeasureExpPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpPlanMapper.insertQqchMeasureExpPlanList(qqchMeasureExpPlanList);
    }

    @Transactional
    public int updateQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan) {
        qqchMeasureExpPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpPlanMapper.updateQqchMeasureExpPlan(qqchMeasureExpPlan);
    }

    @Transactional
    public int updateQqchMeasureExpPlanList(List<QqchMeasureExpPlan> qqchMeasureExpPlanList) {
        for (QqchMeasureExpPlan qqchMeasureExpPlan : qqchMeasureExpPlanList) {
            qqchMeasureExpPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchMeasureExpPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMeasureExpPlanMapper.updateQqchMeasureExpPlanList(qqchMeasureExpPlanList);
    }

    @Transactional
    public int deleteQqchMeasureExpPlan(QqchMeasureExpPlan qqchMeasureExpPlan) {
        qqchMeasureExpPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasureExpPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasureExpPlanMapper.deleteQqchMeasureExpPlan(qqchMeasureExpPlan);
    }

    @Transactional
    public int deleteQqchMeasureExpPlanByPks(List<Long> qqchMeasureExpPlanPkList) {
        return qqchMeasureExpPlanMapper.deleteQqchMeasureExpPlanByPks(qqchMeasureExpPlanPkList);
    }

    @Override
    public void saveTree(CompileEntity<List<QqchMeasureExpPlan>> map) {
        //删除旧数据
        QqchMeasureExpPlan delParam = new QqchMeasureExpPlan();
        delParam.setVersion(map.getVersion());
        delParam.setDataType(map.getDataType());
        qqchMeasureExpPlanMapper.deleteQqchMeasureExpPlan(delParam);

        //插入新数据
        List<QqchMeasureExpPlan> dto = map.dealSaveDto();
        this.checkData(dto);
        if (CollectionUtils.isEmpty(dto)) return;
        this.qqchMeasureExpPlanMapper.insertQqchMeasureExpPlanList(dto);
    }

    private void checkData(List<QqchMeasureExpPlan> dtos) {
        
    }

    @Override
    @CompileAspect(type = CompileOptEnum.TREE, tableName = TN)
    public List<QqchMeasureExpPlan> getQqchMeasureExpPlanListByVersion(QqchMeasureExpPlan qqchMeasureExpPlanParam) {
        List<QqchMeasureExpPlan> qqchMeasureExpPlanList = this.getQqchMeasureExpPlanList(qqchMeasureExpPlanParam);
        return qqchMeasureExpPlanList;
    }

   
}
