package com.hhwy.pm.qqch.tax.qqchTaxIn.service;

import java.util.List;
import com.hhwy.pm.qqch.tax.qqchTaxIn.domain.QqchTaxIn;

/**
 * @author mls
 * @date 2023-08-09 18:17:32
 * @remark 
 */
public interface IQqchTaxInService {
                                                                                                                                                                                                                                                                                                                                                                                        
    QqchTaxIn getQqchTaxIn(QqchTaxIn qqchTaxIn);

    List<QqchTaxIn> getQqchTaxInList(QqchTaxIn qqchTaxIn);

    int insertQqchTaxIn(QqchTaxIn qqchTaxIn);

    int insertQqchTaxInList(List<QqchTaxIn> qqchTaxInList);

    int updateQqchTaxIn(QqchTaxIn qqchTaxIn);

            int updateQqchTaxInList(List<QqchTaxIn> qqchTaxInList);
    
    int deleteQqchTaxIn(QqchTaxIn qqchTaxIn);

            int deleteQqchTaxInByPks(List<Long> qqchTaxInPkList);
    }
