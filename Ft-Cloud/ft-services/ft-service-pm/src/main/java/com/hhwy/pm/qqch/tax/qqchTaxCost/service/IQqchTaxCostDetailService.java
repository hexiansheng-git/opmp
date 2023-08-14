package com.hhwy.pm.qqch.tax.qqchTaxCost.service;

import com.hhwy.pm.qqch.tax.qqchTaxCost.domain.QqchTaxCostDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:26
 * @remark
 */
public interface IQqchTaxCostDetailService {

    QqchTaxCostDetail getQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail);

    List<QqchTaxCostDetail> getQqchTaxCostDetailList(QqchTaxCostDetail qqchTaxCostDetail);

    int insertQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail);

    int insertQqchTaxCostDetailList(List<QqchTaxCostDetail> qqchTaxCostDetailList);

    int updateQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail);

    int updateQqchTaxCostDetailList(List<QqchTaxCostDetail> qqchTaxCostDetailList);

    int deleteQqchTaxCostDetail(QqchTaxCostDetail qqchTaxCostDetail);

    int deleteQqchTaxCostDetailByPks(List<Long> qqchTaxCostDetailPkList);

    void save(List<QqchTaxCostDetail> dealSaveDto);
}
