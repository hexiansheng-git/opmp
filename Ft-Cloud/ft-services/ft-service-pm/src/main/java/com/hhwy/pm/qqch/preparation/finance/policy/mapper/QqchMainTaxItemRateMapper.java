package com.hhwy.pm.qqch.preparation.finance.policy.mapper;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchMainTaxItemRate;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-01 16:03:00
 * @remark 10.2.2主要税目税率
 */
public interface QqchMainTaxItemRateMapper {

    QqchMainTaxItemRate getQqchMainTaxItemRate(QqchMainTaxItemRate qqchMainTaxItemRate);

    List<QqchMainTaxItemRate> getQqchMainTaxItemRateList(QqchMainTaxItemRate qqchMainTaxItemRate);

    int insertQqchMainTaxItemRate(QqchMainTaxItemRate qqchMainTaxItemRate);

    int insertQqchMainTaxItemRateList(
        @Param("qqchMainTaxItemRateList") List<QqchMainTaxItemRate> qqchMainTaxItemRateList);

    int updateQqchMainTaxItemRate(QqchMainTaxItemRate qqchMainTaxItemRate);

    int updateQqchMainTaxItemRateList(@Param("list") List<QqchMainTaxItemRate> qqchMainTaxItemRateList);

    int deleteQqchMainTaxItemRate(QqchMainTaxItemRate qqchMainTaxItemRate);

    int deleteQqchMainTaxItemRateByPks(@Param("qqchMainTaxItemRatePkList") List<Long> qqchMainTaxItemRatePkList);
}
