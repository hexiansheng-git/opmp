package com.hhwy.pm.qqch.tax.qqchTaxCost.mapper;

import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:14
 * @remark
 */
public interface QqchTaxCostMapper {

    QqchTaxCost getQqchTaxCost(QqchTaxCost qqchTaxCost);

    List<QqchTaxCost> getQqchTaxCostList(QqchTaxCost qqchTaxCost);

    int insertQqchTaxCost(QqchTaxCost qqchTaxCost);

    int insertQqchTaxCostList(@Param("qqchTaxCostList") List<QqchTaxCost> qqchTaxCostList);

    int updateQqchTaxCost(QqchTaxCost qqchTaxCost);

    int updateQqchTaxCostList(@Param("qqchTaxCostList") List<QqchTaxCost> qqchTaxCostList);

    int deleteQqchTaxCost(QqchTaxCost qqchTaxCost);

    int deleteQqchTaxCostByPks(@Param("qqchTaxCostPkList") List<Long> qqchTaxCostPkList);
}
