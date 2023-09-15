package com.hhwy.pm.qqch.tax.qqchTaxInstallment.service;

import com.hhwy.pm.qqch.tax.qqchTaxInstallment.domain.QqchTaxInstallment;
import com.hhwy.pm.qqch.tax.qqchTaxInstallment.vo.InstallmentVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-14 00:02:28
 * @remark
 */
public interface IQqchTaxInstallmentService {

    QqchTaxInstallment getQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment);

    List<QqchTaxInstallment> getQqchTaxInstallmentList(QqchTaxInstallment qqchTaxInstallment);

    int insertQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment);

    int insertQqchTaxInstallmentList(List<QqchTaxInstallment> qqchTaxInstallmentList);

    int updateQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment);

    int updateQqchTaxInstallmentList(List<QqchTaxInstallment> qqchTaxInstallmentList);

    int deleteQqchTaxInstallment(QqchTaxInstallment qqchTaxInstallment);

    int deleteQqchTaxInstallmentByPks(List<Long> qqchTaxInstallmentPkList);

    void confirm(QqchTaxInstallment dto);

    InstallmentVO refresh(QqchTaxInstallment dto);

    ArrayList<InstallmentVO.ListVO> getList(QqchTaxInstallment qqchTaxInstallment);

    InstallmentVO list(QqchTaxInstallment qqchTaxInstallment);
    
}
