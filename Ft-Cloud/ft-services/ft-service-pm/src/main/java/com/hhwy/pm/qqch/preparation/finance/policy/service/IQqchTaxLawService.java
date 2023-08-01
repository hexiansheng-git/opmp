package com.hhwy.pm.qqch.preparation.finance.policy.service;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxLaw;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-01 16:02:48
 * @remark 10.2.1税务监管环境概述-税法
 */
public interface IQqchTaxLawService {

    QqchTaxLaw getQqchTaxLaw(QqchTaxLaw qqchTaxLaw);

    List<QqchTaxLaw> getQqchTaxLawList(QqchTaxLaw qqchTaxLaw);

    int insertQqchTaxLaw(QqchTaxLaw qqchTaxLaw);

    int insertQqchTaxLawList(List<QqchTaxLaw> qqchTaxLawList);

    int updateQqchTaxLaw(QqchTaxLaw qqchTaxLaw);

    int updateQqchTaxLawList(List<QqchTaxLaw> qqchTaxLawList);

    int deleteQqchTaxLaw(QqchTaxLaw qqchTaxLaw);

    int deleteQqchTaxLawByPks(List<Long> qqchTaxLawPkList);
}
