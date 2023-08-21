package com.hhwy.pm.qqch.tax.qqchTaxCost.mapper;

import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCostDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-11 13:56:55
 * @remark
 */
public interface QqchTaxCostDetailMapper {

    QqchTaxCostDetail getQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail);

    List<QqchTaxCostDetail> getQqchTaxCostDetailList(QqchTaxCostDetail qqchTaxCostDetail);

    int insertQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail);

    int insertQqchTaxCostDetailList(@Param("qqchTaxCostDetailList") List<QqchTaxCostDetail> qqchTaxCostDetailList);

    int updateQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail);

    int updateQqchTaxCostDetailList(@Param("qqchTaxCostDetailList") List<QqchTaxCostDetail> qqchTaxCostDetailList);

    int deleteQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail);

    int deleteQqchTaxCostDetailByPks(@Param("qqchTaxCostDetailPkList") List<Long> qqchTaxCostDetailPkList);

    List<QqchTaxCostDetail> getCostName(QqchTaxCostDetail costWhere);

    List<QqchTaxCostDetail> getAmtByGroup(QqchTaxCostDetail costWhere);
}
