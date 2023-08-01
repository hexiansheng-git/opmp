package com.hhwy.pm.qqch.preparation.finance.policy.mapper;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchTaxLaw;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-01 16:02:48
 * @remark 10.2.1税务监管环境概述-税法
 */
public interface QqchTaxLawMapper {

    QqchTaxLaw getQqchTaxLaw(QqchTaxLaw qqchTaxLaw);

    List<QqchTaxLaw> getQqchTaxLawList(QqchTaxLaw qqchTaxLaw);

    int insertQqchTaxLaw(QqchTaxLaw qqchTaxLaw);

    int insertQqchTaxLawList(@Param("qqchTaxLawList") List<QqchTaxLaw> qqchTaxLawList);

    int updateQqchTaxLaw(QqchTaxLaw qqchTaxLaw);

    int updateQqchTaxLawList(@Param("list") List<QqchTaxLaw> qqchTaxLawList);

    int deleteQqchTaxLaw(QqchTaxLaw qqchTaxLaw);

    int deleteQqchTaxLawByPks(@Param("qqchTaxLawPkList") List<Long> qqchTaxLawPkList);
}
