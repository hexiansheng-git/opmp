package com.hhwy.pm.qqch.sgch.prodplan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.prodplan.domain.QqchProdPlan;
import com.hhwy.pm.qqch.sgch.prodplan.mapper.QqchProdPlanMapper;
import com.hhwy.pm.qqch.sgch.prodplan.service.IQqchProdPlanService;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author mls
 * @date 2023-08-17 16:20:08
 * @remark
 */
@Service
public class QqchProdPlanServiceImpl implements IQqchProdPlanService {

    @Autowired
    private QqchProdPlanMapper qqchProdPlanMapper;


    public QqchProdPlan getQqchProdPlan(QqchProdPlan qqchProdPlan) {
        return qqchProdPlanMapper.getQqchProdPlan(qqchProdPlan);
    }

    public List<QqchProdPlan> getQqchProdPlanList(QqchProdPlan qqchProdPlan) {
        return qqchProdPlanMapper.getQqchProdPlanList(qqchProdPlan);
    }

    @Transactional
    public int insertQqchProdPlan(QqchProdPlan qqchProdPlan) {
        qqchProdPlan.setId(IdWorker.createId());
        qqchProdPlan.setCreateUser(SecurityUtils.getUserName());
        qqchProdPlan.setCreateTime(DateUtils.getNowDate());
        return qqchProdPlanMapper.insertQqchProdPlan(qqchProdPlan);
    }

    @Transactional
    public int insertQqchProdPlanList(List<QqchProdPlan> qqchProdPlanList) {
        for (QqchProdPlan qqchProdPlan : qqchProdPlanList) {
            qqchProdPlan.setId(IdWorker.createId());
            qqchProdPlan.setCreateUser(SecurityUtils.getUserName());
            qqchProdPlan.setCreateTime(DateUtils.getNowDate());
        }
        return qqchProdPlanMapper.insertQqchProdPlanList(qqchProdPlanList);
    }

    @Transactional
    public int updateQqchProdPlan(QqchProdPlan qqchProdPlan) {
        qqchProdPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchProdPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchProdPlanMapper.updateQqchProdPlan(qqchProdPlan);
    }

    @Transactional
    public int updateQqchProdPlanList(List<QqchProdPlan> qqchProdPlanList) {
        for (QqchProdPlan qqchProdPlan : qqchProdPlanList) {
            qqchProdPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchProdPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchProdPlanMapper.updateQqchProdPlanList(qqchProdPlanList);
    }

    @Transactional
    public int deleteQqchProdPlan(QqchProdPlan qqchProdPlan) {
        qqchProdPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchProdPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchProdPlanMapper.deleteQqchProdPlan(qqchProdPlan);
    }

    @Transactional
    public int deleteQqchProdPlanByPks(List<Long> qqchProdPlanPkList) {
        return qqchProdPlanMapper.deleteQqchProdPlanByPks(qqchProdPlanPkList);
    }

    @Override
    public CompileEntity<List<QqchProdPlan>> getList(QqchProdPlan dto) {


        return null;
    }

    // TODO 获取p6计划的开始时间和结束时间
    private Date[] getTime() {
        Date[] dates = new Date[2];
        dates[0] = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(dates[0]);
        instance.set(Calendar.MONTH, 1);
        dates[0] = instance.getTime();
        dates[1] = new Date();
        return dates;
    }


    // 获取p6的
    private HashMap<Date, List<String>> getWbsList(Date startDate, Date endDate) {
        HashMap<Date, List<String>> res = new HashMap<>();

        // 根据开始时间结束时间获取其中的月份
        List<Date> dateList = FtDateUtils.getDateList(startDate, endDate);
        // 根据每个月的作业项
        for (Date date : dateList) {
            // 伪代码
            ArrayList<String> ids = new ArrayList<>();
            ids.add("1");
            ids.add("2");
            ids.add("3");
            ids.add("4");
            ids.add("5");
            ids.add("6");
            res.put(date,ids);
        }
        
        return res;
    }


}
