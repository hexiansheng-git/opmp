package com.hhwy.pm.qqch.preparation.finance.contract.mapper;

import com.hhwy.pm.qqch.preparation.finance.contract.domain.QqchFinancialMainContractTerms;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-02 14:24:03
 * @remark 10.1财务相关主合同条款
 */
public interface QqchFinancialMainContractTermsMapper {

    QqchFinancialMainContractTerms getQqchFinancialMainContractTerms(
        QqchFinancialMainContractTerms qqchFinancialMainContractTerms);

    List<QqchFinancialMainContractTerms> getQqchFinancialMainContractTermsList(
        QqchFinancialMainContractTerms qqchFinancialMainContractTerms);

    int insertQqchFinancialMainContractTerms(QqchFinancialMainContractTerms qqchFinancialMainContractTerms);

    int insertQqchFinancialMainContractTermsList(
        @Param("qqchFinancialMainContractTermsList") List<QqchFinancialMainContractTerms> qqchFinancialMainContractTermsList);

    int updateQqchFinancialMainContractTerms(QqchFinancialMainContractTerms qqchFinancialMainContractTerms);

    int updateQqchFinancialMainContractTermsList(
        @Param("list") List<QqchFinancialMainContractTerms> qqchFinancialMainContractTermsList);

    int deleteQqchFinancialMainContractTerms(QqchFinancialMainContractTerms qqchFinancialMainContractTerms);

    int deleteQqchFinancialMainContractTermsByPks(
        @Param("qqchFinancialMainContractTermsPkList") List<Long> qqchFinancialMainContractTermsPkList);
}
