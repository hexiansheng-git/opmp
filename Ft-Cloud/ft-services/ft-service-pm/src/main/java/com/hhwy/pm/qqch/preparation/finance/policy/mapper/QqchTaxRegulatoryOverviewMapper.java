package com.hhwy.pm.qqch.preparation.finance.policy.mapper;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxRegulatoryOverview;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-01 16:02:35
 * @remark 10.2.1税务监管环境概述
 */
public interface QqchTaxRegulatoryOverviewMapper {

    QqchTaxRegulatoryOverview getQqchTaxRegulatoryOverview(QqchTaxRegulatoryOverview qqchTaxRegulatoryOverview);

    List<QqchTaxRegulatoryOverview> getQqchTaxRegulatoryOverviewList(
        QqchTaxRegulatoryOverview qqchTaxRegulatoryOverview);

    int insertQqchTaxRegulatoryOverview(QqchTaxRegulatoryOverview qqchTaxRegulatoryOverview);

    int insertQqchTaxRegulatoryOverviewList(
        @Param("qqchTaxRegulatoryOverviewList") List<QqchTaxRegulatoryOverview> qqchTaxRegulatoryOverviewList);

    int updateQqchTaxRegulatoryOverview(QqchTaxRegulatoryOverview qqchTaxRegulatoryOverview);

    int updateQqchTaxRegulatoryOverviewList(
        @Param("list") List<QqchTaxRegulatoryOverview> qqchTaxRegulatoryOverviewList);

    int deleteQqchTaxRegulatoryOverview(QqchTaxRegulatoryOverview qqchTaxRegulatoryOverview);

    int deleteQqchTaxRegulatoryOverviewByPks(
        @Param("qqchTaxRegulatoryOverviewPkList") List<Long> qqchTaxRegulatoryOverviewPkList);
}
