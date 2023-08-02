package com.hhwy.pm.qqch.preparation.finance.policy.mapper;

import com.hhwy.pm.qqch.preparation.finance.policy.domain.QqchLocalAccountingPolicy;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:44
 * @remark 10.2.3当地会计政策描述
 */
public interface QqchLocalAccountingPolicyMapper {

    QqchLocalAccountingPolicy getQqchLocalAccountingPolicy(QqchLocalAccountingPolicy qqchLocalAccountingPolicy);

    List<QqchLocalAccountingPolicy> getQqchLocalAccountingPolicyList(
        QqchLocalAccountingPolicy qqchLocalAccountingPolicy);

    int insertQqchLocalAccountingPolicy(QqchLocalAccountingPolicy qqchLocalAccountingPolicy);

    int insertQqchLocalAccountingPolicyList(
        @Param("qqchLocalAccountingPolicyList") List<QqchLocalAccountingPolicy> qqchLocalAccountingPolicyList);

    int updateQqchLocalAccountingPolicy(QqchLocalAccountingPolicy qqchLocalAccountingPolicy);

    int updateQqchLocalAccountingPolicyList(
        @Param("list") List<QqchLocalAccountingPolicy> qqchLocalAccountingPolicyList);

    int deleteQqchLocalAccountingPolicy(QqchLocalAccountingPolicy qqchLocalAccountingPolicy);

    int deleteQqchLocalAccountingPolicyByPks(
        @Param("qqchLocalAccountingPolicyPkList") List<Long> qqchLocalAccountingPolicyPkList);
}
