package com.hhwy.pm.qqch.tax.qqchTaxCostDetail.mapper;

import com.hhwy.pm.qqch.tax.qqchTaxCostDetail.domain.QqchTaxCostDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:26
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
}
