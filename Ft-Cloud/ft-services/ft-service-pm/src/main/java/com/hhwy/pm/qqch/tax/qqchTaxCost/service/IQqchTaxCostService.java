package com.hhwy.pm.qqch.tax.qqchTaxCost.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCost;
import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCostDetail;
import com.hhwy.pm.qqch.tax.qqchTaxCost.vo.TaxCostVO;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:14
 * @remark
 */
public interface IQqchTaxCostService {

    QqchTaxCost getQqchTaxCost(QqchTaxCost qqchTaxCost);

    List<QqchTaxCost> getQqchTaxCostList(QqchTaxCost qqchTaxCost);

    int insertQqchTaxCost(QqchTaxCost qqchTaxCost);

    int insertQqchTaxCostList(List<QqchTaxCost> qqchTaxCostList);

    int updateQqchTaxCost(QqchTaxCost qqchTaxCost);

    int updateQqchTaxCostList(List<QqchTaxCost> qqchTaxCostList);

    int deleteQqchTaxCost(QqchTaxCost qqchTaxCost);

    int deleteQqchTaxCostByPks(List<Long> qqchTaxCostPkList);

    CompileEntity<TaxCostVO> getList(QqchTaxCost taxCost);

    void save(CompileEntity<TaxCostVO> dto);


    public List<QqchTaxCost> getCostList(QqchTaxCost dto);

    List<QqchTaxCostDetail> saveCostList(List<QqchTaxCost> qqchTaxCosts);
}
