package com.hhwy.pm.qqch.tax.qqchTaxInDetail.service;

import java.util.List;
import com.hhwy.pm.qqch.tax.qqchTaxInDetail.domain.QqchTaxInDetail;

/**
 * @author mls
 * @date 2023-08-09 18:17:35
 * @remark 
 */
public interface IQqchTaxInDetailService {
                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchTaxInDetail getQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail);

    List<QqchTaxInDetail> getQqchTaxInDetailList(QqchTaxInDetail qqchTaxInDetail);

    int insertQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail);

    int insertQqchTaxInDetailList(List<QqchTaxInDetail> qqchTaxInDetailList);

    int updateQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail);

            int updateQqchTaxInDetailList(List<QqchTaxInDetail> qqchTaxInDetailList);
    
    int deleteQqchTaxInDetail(QqchTaxInDetail qqchTaxInDetail);

            int deleteQqchTaxInDetailByPks(List<Long> qqchTaxInDetailPkList);
    }
