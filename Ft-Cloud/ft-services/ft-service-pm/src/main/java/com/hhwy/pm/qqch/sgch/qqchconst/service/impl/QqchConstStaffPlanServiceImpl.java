package com.hhwy.pm.qqch.sgch.qqchconst.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstJob;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstStaffPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstStaffPlanMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstStaffPlanService;
import com.hhwy.pm.qqch.utils.DistinctUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mls
 * @date 2023-08-03 16:08:24
 * @remark
 */
@Service
public class QqchConstStaffPlanServiceImpl implements IQqchConstStaffPlanService {


    private static final String TN = "qqch_const_staff_plan";

    @Autowired
    private QqchConstStaffPlanMapper qqchConstStaffPlanMapper;


    public QqchConstStaffPlan getQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan) {
        return qqchConstStaffPlanMapper.getQqchConstStaffPlan(qqchConstStaffPlan);
    }

    public List<QqchConstStaffPlan> getQqchConstStaffPlanList(QqchConstStaffPlan qqchConstStaffPlan) {
        return qqchConstStaffPlanMapper.getQqchConstStaffPlanList(qqchConstStaffPlan);
    }

    @Transactional
    public int insertQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan) {
        qqchConstStaffPlan.setId(IdWorker.createId());
        qqchConstStaffPlan.setCreateUser(SecurityUtils.getUserName());
        qqchConstStaffPlan.setCreateTime(DateUtils.getNowDate());
        return qqchConstStaffPlanMapper.insertQqchConstStaffPlan(qqchConstStaffPlan);
    }

    @Transactional
    public int insertQqchConstStaffPlanList(List<QqchConstStaffPlan> qqchConstStaffPlanList) {
        for (QqchConstStaffPlan qqchConstStaffPlan : qqchConstStaffPlanList) {
            qqchConstStaffPlan.setId(IdWorker.createId());
            qqchConstStaffPlan.setCreateUser(SecurityUtils.getUserName());
            qqchConstStaffPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchConstStaffPlanMapper.insertQqchConstStaffPlanList(qqchConstStaffPlanList);
    }

    @Transactional
    public int updateQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan) {
        qqchConstStaffPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchConstStaffPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchConstStaffPlanMapper.updateQqchConstStaffPlan(qqchConstStaffPlan);
    }

    @Transactional
    public int updateQqchConstStaffPlanList(List<QqchConstStaffPlan> qqchConstStaffPlanList) {
        for (QqchConstStaffPlan qqchConstStaffPlan : qqchConstStaffPlanList) {
            qqchConstStaffPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchConstStaffPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchConstStaffPlanMapper.updateQqchConstStaffPlanList(qqchConstStaffPlanList);
    }

    @Transactional
    public int deleteQqchConstStaffPlan(QqchConstStaffPlan qqchConstStaffPlan) {
        qqchConstStaffPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchConstStaffPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchConstStaffPlanMapper.deleteQqchConstStaffPlan(qqchConstStaffPlan);
    }

    @Transactional
    public int deleteQqchConstStaffPlanByPks(List<Long> qqchConstStaffPlanPkList) {
        return qqchConstStaffPlanMapper.deleteQqchConstStaffPlanByPks(qqchConstStaffPlanPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN, delFlag = false)
    public void saveList(List<QqchConstStaffPlan> iStaffList) {


        CompileEntity staffPlan = iStaffList.get(0);
//        if (iStaffList.size() == 1 && PmConstant.MINUS_ONE.equals(staffPlan.getSubmitFlag())) {
            // 如果前端将所有数据删除了 这边根据version删除数据
            this.qqchConstStaffPlanMapper.deleteByVersion(staffPlan.getVersion());
//            return;
//        }
        if(CollectionUtils.isNotEmpty(iStaffList) && staffPlan instanceof QqchConstStaffPlan){
            this.qqchConstStaffPlanMapper.insertQqchConstStaffPlanList(iStaffList);
        }
//        BigDecimal version = iStaffList.get(0).getVersion();

//        // 先将当前版本的所有的数据查询出来 跟前端传入的数据进行比较 交集进行更新 数据库有的前端没有的,删除 前端有的数据库没有的,新增
//        QqchConstStaffPlan where = new QqchConstStaffPlan();
//        where.setVersion(version);
//        where.setDelFlag("0");
//        List<QqchConstStaffPlan> dbJobList = this.qqchConstStaffPlanMapper.getQqchConstStaffPlanList(where);
//
//        // 数据库中的id
//        List<Long> dbIdList = dbJobList.stream().map(QqchConstStaffPlan::getId).collect(Collectors.toList());
//        // 前端的id
//        List<Long> paramIdList = iStaffList.stream().map(QqchConstStaffPlan::getId).collect(Collectors.toList());
//
//
//        // 数据库中有 但是前端没有的数据 删掉
//        List<Long> delIdList = dbIdList.stream().filter(item -> !paramIdList.contains(item)).collect(Collectors.toList());
//        if (!CollectionUtils.isEmpty(delIdList))  this.qqchConstStaffPlanMapper.deleteQqchConstStaffPlanByPks(delIdList);
//
//        // 前端有 数据库中没有 新增
//        List<QqchConstStaffPlan> insertDataList = iStaffList.stream().filter(item -> item.getId() == null || !dbIdList.contains(item.getId())).collect(Collectors.toList());
//        insertDataList.stream().forEach(r->r.setId(IdWorker.createId()));
//        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(insertDataList))  this.qqchConstStaffPlanMapper.insertQqchConstStaffPlanList(insertDataList);
//
//        // 前端和后台都有的数据 更新
//        List<QqchConstStaffPlan> updateDataList = iStaffList.stream().filter(item -> dbIdList.contains(item.getId())).collect(Collectors.toList());
//        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(updateDataList)) this.qqchConstStaffPlanMapper.updateQqchConstStaffPlanList(updateDataList);
        
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchConstStaffPlan> list(QqchConstStaffPlan dto) {
        return this.getQqchConstStaffPlanList(dto);
    }

    @Override
    public List<QqchConstStaffPlan> jobList(String codeOrName) {
        QqchConstStaffPlan query = new QqchConstStaffPlan();
        query.setCodeOrName(codeOrName);
        BigDecimal version = VersionUtil.getVersion("qqch_const",null);
        query.setVersion(version);
        List<QqchConstStaffPlan> qqchConstJobList = qqchConstStaffPlanMapper.getQqchConstStaffPlanList(query);
        List<QqchConstStaffPlan> list = qqchConstJobList.stream().filter(DistinctUtil.distinctByKey(QqchConstStaffPlan::getOccupationCode)).collect(Collectors.toList());
        Map<String, List<QqchConstStaffPlan>> mapList = qqchConstJobList.stream().collect(Collectors.groupingBy(QqchConstStaffPlan::getOccupationCode));
        list.stream().forEach(o -> o.setTotalCount(mapList.get(o.getOccupationCode()).stream().map(j -> j.getTotalCount() == null ? 0:j.getTotalCount()).reduce(Integer::sum).get()));
        return list;
    }

}
