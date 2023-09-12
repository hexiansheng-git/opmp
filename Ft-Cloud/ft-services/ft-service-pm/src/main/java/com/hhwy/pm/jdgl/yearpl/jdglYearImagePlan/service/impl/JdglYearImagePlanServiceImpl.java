package com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.domain.JdglMainPlan;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service.IJdglMainPlanItemService;
import com.hhwy.pm.jdgl.statistics.util.StatisticsUtils;
import com.hhwy.pm.jdgl.yearpl.jdglYearPlan.domain.JdglYearPlan;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.mapper.JdglYearImagePlanMapper;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.service.IJdglYearImagePlanService;
import com.hhwy.pm.jdgl.yearpl.jdglYearImagePlan.domain.JdglYearImagePlan;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author chenjinhao
 * @date 2023-08-21 15:48:25
 * @remark
 */
@Service
public class JdglYearImagePlanServiceImpl implements IJdglYearImagePlanService {

    @Autowired
    private JdglYearImagePlanMapper jdglYearImagePlanMapper;

    @Autowired
    private IJdglMainPlanItemService jdglMainPlanItemService;

    @Autowired
    private IJdglMainPlanService iJdglMainPlanService;


    public JdglYearImagePlan getJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        return jdglYearImagePlanMapper.getJdglYearImagePlan(jdglYearImagePlan);
    }

    public List<JdglYearImagePlan> getJdglYearImagePlanList(JdglYearImagePlan jdglYearImagePlan) {
        Long pid = jdglYearImagePlan.getPid();
        List<JdglYearImagePlan> jdglYearImagePlanList = jdglYearImagePlanMapper.getJdglYearImagePlanList(jdglYearImagePlan);
        if(CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            return jdglYearImagePlanList;
        }
        List<JdglYearImagePlan> build = TreeUtil.build(jdglYearImagePlanList, pid);
        return build;
    }

    public List<JdglYearImagePlan> getJdglYearImagePlanListByYearPlanId(Long yearPlanId) {
        JdglYearImagePlan jdglYearImagePlan = new JdglYearImagePlan();
        jdglYearImagePlan.setYearPlanId(yearPlanId);
        return getJdglYearImagePlanList(jdglYearImagePlan);
    }

    @Transactional
    public int insertJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        jdglYearImagePlan.setId(IdWorker.createId());
        jdglYearImagePlan.setCreateUser(SecurityUtils.getUserName());
        jdglYearImagePlan.setCreateTime(DateUtils.getNowDate());
        return jdglYearImagePlanMapper.insertJdglYearImagePlan(jdglYearImagePlan);
    }

    @Transactional
    public int insertJdglYearImagePlanList(List<JdglYearImagePlan> jdglYearImagePlanList) {
        for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList) {
            jdglYearImagePlan.setId(IdWorker.createId());
            jdglYearImagePlan.setCreateUser(SecurityUtils.getUserName());
            jdglYearImagePlan.setCreateTime(DateUtils.getNowDate());
        }
        return jdglYearImagePlanMapper.insertJdglYearImagePlanList(jdglYearImagePlanList);
    }

    @Transactional
    public int updateJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        jdglYearImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglYearImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglYearImagePlanMapper.updateJdglYearImagePlan(jdglYearImagePlan);
    }

    @Transactional
    public int updateJdglYearImagePlanList(List<JdglYearImagePlan> jdglYearImagePlanList) {
        if(!CollectionUtils.isEmpty(jdglYearImagePlanList)) {
            List<JdglYearImagePlan> jdglYearImagePlanList1 = TreeUtil.treeToList(jdglYearImagePlanList);
            for (JdglYearImagePlan jdglYearImagePlan : jdglYearImagePlanList1) {
                jdglYearImagePlan.setUpdateUser(SecurityUtils.getUserName());
                jdglYearImagePlan.setUpdateTime(DateUtils.getNowDate());
            }
            return jdglYearImagePlanMapper.updateJdglYearImagePlanList(jdglYearImagePlanList1);
        }
        return 0;
    }

    @Transactional
    public int deleteJdglYearImagePlan(JdglYearImagePlan jdglYearImagePlan) {
        jdglYearImagePlan.setUpdateUser(SecurityUtils.getUserName());
        jdglYearImagePlan.setUpdateTime(DateUtils.getNowDate());
        return jdglYearImagePlanMapper.deleteJdglYearImagePlan(jdglYearImagePlan);
    }

    @Transactional
    public int deleteJdglYearImagePlanByPks(List<Long> jdglYearImagePlanPkList) {
        return jdglYearImagePlanMapper.deleteJdglYearImagePlanByPks(jdglYearImagePlanPkList);
    }

    @Override
    public int deleteJdglYearImagePlanByYearPlanId(Long yearPlanId) {
        JdglYearImagePlan jdglYearImagePlan = new JdglYearImagePlan();
        jdglYearImagePlan.setYearPlanId(yearPlanId);
        return deleteJdglYearImagePlan(jdglYearImagePlan);
    }

    /**
     * 从总进度计划获取数据&未完&
     * @param jdglYearPlanParam
     * @return
     */
    @Override
    public JdglYearPlan syncFromTotalPlan(JdglYearPlan jdglYearPlanParam) {

        // 年份
        String year = jdglYearPlanParam.getYear();

        if(StringUtils.isEmpty(year)) {
            throw new RuntimeException("传参异常!");
        }

        List<JdglYearImagePlan> returnList = new ArrayList<JdglYearImagePlan>();

        // 最新获取总进度计划数据（根据年份日期区间获取总计划、形象计划及关联wbs数据）
        JdglMainPlan usingJdglMainPlan = iJdglMainPlanService.getUsingJdglMainPlan();
        Map<String, Date> dateRange4Year = StatisticsUtils.getDateRange4Year(year);
        List<JdglMainPlanItem> jdglMainPlanItemList = jdglMainPlanItemService.getUsingJdglMainPlanItemListByDateRange(dateRange4Year.get("start"), dateRange4Year.get("end"));

        if(CollectionUtils.isEmpty(jdglMainPlanItemList)) {
            return jdglYearPlanParam;
        }

        // 获取当前版本形象计划数据
        if(jdglYearPlanParam.getId() != null) {
            // 增修年进度计划数据
        } else {
            for (JdglMainPlanItem jdglMainPlanItem : jdglMainPlanItemList) {
                JdglYearImagePlan jdglYearImagePlan = new JdglYearImagePlan();

                jdglYearImagePlan.setId(jdglMainPlanItem.getId());
                jdglYearImagePlan.setPid(jdglMainPlanItem.getPid());
                jdglYearImagePlan.setYearPlanId(jdglYearPlanParam.getId());
                jdglYearImagePlan.setWorkId(jdglMainPlanItem.getId());
                jdglYearImagePlan.setWorkCode(jdglMainPlanItem.getItemCode());
                jdglYearImagePlan.setWorkName(jdglMainPlanItem.getItemName());
                jdglYearImagePlan.setUnit(jdglMainPlanItem.getUnit());
                jdglYearImagePlan.setDesignQuantity(jdglMainPlanItem.getQuantity());
                jdglYearImagePlan.setTotalCompQuantity(null);
                jdglYearImagePlan.setRemainQuantity(null);
                jdglYearImagePlan.setPlanStartDate(jdglMainPlanItem.getStartDate());
                jdglYearImagePlan.setPlanEndDate(jdglMainPlanItem.getFinishDate());
                jdglYearImagePlan.setWbsCode(jdglMainPlanItem.getWbsCode());
                jdglYearImagePlan.setWbsName(jdglMainPlanItem.getWbsName());
//                jdglYearImagePlan.setWbsId();
                jdglYearImagePlan.setResponsePerson(jdglMainPlanItem.getExecuter());
                jdglYearImagePlan.setResponsePersonId(jdglMainPlanItem.getExecuterId());
                returnList.add(jdglYearImagePlan);
            }
        }

        // 维护returnList树结构
        List<JdglYearImagePlan> build = TreeUtil.build(returnList, null);

        jdglYearPlanParam.setJdglYearImagePlanList(build);

        // 修改年进度计划主表引用总体计划的版本号
        if(usingJdglMainPlan != null) {
            jdglYearPlanParam.setThisTotalVersion(usingJdglMainPlan.getVersion());
        }

        return jdglYearPlanParam;
    }

    @Override
    public List<JdglYearImagePlan> getWbsListByYear(String year) {
        return jdglYearImagePlanMapper.getWbsListByYear(year);
    }
}
