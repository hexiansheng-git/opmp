package com.hhwy.pm.qqch.tax.qqchTaxInstallment.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.domain.QqchTaxInstallment;

/**
 * @author mls
 * @date 2023-08-14 00:02:28
 * @remark 
 */
public interface QqchTaxInstallmentMapper {
                                                                                                                                                                                                                                                                                                                                        
    QqchTaxInstallment getQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment);

    List<QqchTaxInstallment> getQqchTaxInstallmentList(QqchTaxInstallment qqchTaxInstallment);

    int insertQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment);

    int insertQqchTaxInstallmentList(@Param("qqchTaxInstallmentList") List<QqchTaxInstallment> qqchTaxInstallmentList);

    int updateQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment);

            int updateQqchTaxInstallmentList(@Param("qqchTaxInstallmentList") List<QqchTaxInstallment> qqchTaxInstallmentList);
    
    int deleteQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment);

            int deleteQqchTaxInstallmentByPks(@Param("qqchTaxInstallmentPkList") List<Long> qqchTaxInstallmentPkList);
    }
