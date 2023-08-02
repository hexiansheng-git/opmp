package com.hhwy.pm.qqch.preparation.finance.policy.mapper;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalTariffPolicy;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:49
 * @remark 10.2.4当地关税政策描述
 */
public interface QqchLocalTariffPolicyMapper {

    QqchLocalTariffPolicy getQqchLocalTariffPolicy(QqchLocalTariffPolicy qqchLocalTariffPolicy);

    List<QqchLocalTariffPolicy> getQqchLocalTariffPolicyList(QqchLocalTariffPolicy qqchLocalTariffPolicy);

    int insertQqchLocalTariffPolicy(QqchLocalTariffPolicy qqchLocalTariffPolicy);

    int insertQqchLocalTariffPolicyList(
        @Param("qqchLocalTariffPolicyList") List<QqchLocalTariffPolicy> qqchLocalTariffPolicyList);

    int updateQqchLocalTariffPolicy(QqchLocalTariffPolicy qqchLocalTariffPolicy);

    int updateQqchLocalTariffPolicyList(@Param("list") List<QqchLocalTariffPolicy> qqchLocalTariffPolicyList);

    int deleteQqchLocalTariffPolicy(QqchLocalTariffPolicy qqchLocalTariffPolicy);

    int deleteQqchLocalTariffPolicyByPks(@Param("qqchLocalTariffPolicyPkList") List<Long> qqchLocalTariffPolicyPkList);
}
