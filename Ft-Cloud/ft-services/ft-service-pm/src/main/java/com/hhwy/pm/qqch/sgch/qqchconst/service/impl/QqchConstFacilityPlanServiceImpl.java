package com.hhwy.pm.qqch.sgch.qqchconst.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstFacilityPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstFacilityPlanMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstStaffPlanMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstFacilityPlanService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mls
 * @date 2023-08-03 16:08:12
 * @remark
 */
@Service
public class QqchConstFacilityPlanServiceImpl implements IQqchConstFacilityPlanService {


    private static final String TN = "qqch_const_facility_plan";
    @Autowired
    private QqchConstFacilityPlanMapper qqchConstFacilityPlanMapper;

    @Autowired
    private QqchConstStaffPlanMapper constStaffPlanMapper;


    public QqchConstFacilityPlan getQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan) {
        return qqchConstFacilityPlanMapper.getQqchConstFacilityPlan(qqchConstFacilityPlan);
    }

    public List<QqchConstFacilityPlan> getQqchConstFacilityPlanList(QqchConstFacilityPlan qqchConstFacilityPlan) {
        return qqchConstFacilityPlanMapper.getQqchConstFacilityPlanList(qqchConstFacilityPlan);
    }

    @Transactional
    public int insertQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan) {
        qqchConstFacilityPlan.setId(IdWorker.createId());
        qqchConstFacilityPlan.setCreateUser(SecurityUtils.getUserName());
        qqchConstFacilityPlan.setCreateTime(DateUtils.getNowDate());
        return qqchConstFacilityPlanMapper.insertQqchConstFacilityPlan(qqchConstFacilityPlan);
    }

    @Transactional
    public int insertQqchConstFacilityPlanList(List<QqchConstFacilityPlan> qqchConstFacilityPlanList) {
        for (QqchConstFacilityPlan qqchConstFacilityPlan : qqchConstFacilityPlanList) {
            qqchConstFacilityPlan.setId(IdWorker.createId());
            qqchConstFacilityPlan.setCreateUser(SecurityUtils.getUserName());
            qqchConstFacilityPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchConstFacilityPlanMapper.insertQqchConstFacilityPlanList(qqchConstFacilityPlanList);
    }

    @Transactional
    public int updateQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan) {
        qqchConstFacilityPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchConstFacilityPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchConstFacilityPlanMapper.updateQqchConstFacilityPlan(qqchConstFacilityPlan);
    }

    @Transactional
    public int updateQqchConstFacilityPlanList(List<QqchConstFacilityPlan> qqchConstFacilityPlanList) {
        for (QqchConstFacilityPlan qqchConstFacilityPlan : qqchConstFacilityPlanList) {
            qqchConstFacilityPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchConstFacilityPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchConstFacilityPlanMapper.updateQqchConstFacilityPlanList(qqchConstFacilityPlanList);
    }

    @Transactional
    public int deleteQqchConstFacilityPlan(QqchConstFacilityPlan qqchConstFacilityPlan) {
        qqchConstFacilityPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchConstFacilityPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchConstFacilityPlanMapper.deleteQqchConstFacilityPlan(qqchConstFacilityPlan);
    }

    @Transactional
    public int deleteQqchConstFacilityPlanByPks(List<Long> qqchConstFacilityPlanPkList) {
        return qqchConstFacilityPlanMapper.deleteQqchConstFacilityPlanByPks(qqchConstFacilityPlanPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void saveList(List<QqchConstFacilityPlan> iFacList) {
        if (CollectionUtils.isEmpty(iFacList)) return;
        this.insertQqchConstFacilityPlanList(iFacList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchConstFacilityPlan> list(QqchConstFacilityPlan dto) {
        return this.getQqchConstFacilityPlanList(dto);
    }

    /***
     * 功能描述:  获取人员策划和设备策划，根据主表明细字段
     * @param qqchConst
     * @return com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConst
     * 作者: fushudong
     * 时间: 2023/10/11
     */
    @Override
    public Map queryDevicePlanListByConstDesc(QqchConst qqchConst) {
        Map result = new HashMap<>();
        List<QqchConstStaffPlan> staffPlanByConstDesc = this.getStaffPlanByConstDesc(qqchConst);
        List<QqchConstFacilityPlan> facilityPlanByConstDesc = this.getFacilityPlanByConstDesc(qqchConst);
        result.put("facilityPlanList", facilityPlanByConstDesc);
        result.put("staffList", staffPlanByConstDesc);
        return result;
    }
    private List<QqchConstStaffPlan> getStaffPlanByConstDesc(QqchConst qqchConst) {
        return constStaffPlanMapper.getStaffPlanByConstDesc(qqchConst);
    }
    private List<QqchConstFacilityPlan> getFacilityPlanByConstDesc(QqchConst qqchConst) {
        return qqchConstFacilityPlanMapper.getFacilityPlanByConstDesc(qqchConst);
    }
}
