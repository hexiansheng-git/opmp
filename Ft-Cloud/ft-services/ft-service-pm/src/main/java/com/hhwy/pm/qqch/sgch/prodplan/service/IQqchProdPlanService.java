package com.hhwy.pm.qqch.sgch.prodplan.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.prodplan.domain.QqchProdPlan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-17 16:20:08
 * @remark
 */
public interface IQqchProdPlanService {

    QqchProdPlan getQqchProdPlan(QqchProdPlan qqchProdPlan);

    List<QqchProdPlan> getQqchProdPlanList(QqchProdPlan qqchProdPlan);

    List<QqchProdPlan> getValidList();

    int insertQqchProdPlan(QqchProdPlan qqchProdPlan);

    int insertQqchProdPlanList(List<QqchProdPlan> qqchProdPlanList);

    int updateQqchProdPlan(QqchProdPlan qqchProdPlan);

    int updateQqchProdPlanList(List<QqchProdPlan> qqchProdPlanList);

    int deleteQqchProdPlan(QqchProdPlan qqchProdPlan);

    int deleteQqchProdPlanByPks(List<Long> qqchProdPlanPkList);

    CompileEntity<List<QqchProdPlan>> getList(QqchProdPlan dto);

    public void save(List<QqchProdPlan> dto);

    CompileEntity<HashMap<String, Object>>  selectList(QqchProdPlan qqchProdPlanParam);

    int putProdPlanData(BigDecimal version);

    BigDecimal getQqchProdPlanAmt4DateRange(BigDecimal version, Date start, Date end);

}
