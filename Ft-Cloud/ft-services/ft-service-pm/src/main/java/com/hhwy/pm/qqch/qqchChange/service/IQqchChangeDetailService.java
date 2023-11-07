package com.hhwy.pm.qqch.qqchChange.service;

import java.util.List;

import com.hhwy.pm.qqch.qqchChange.domain.QqchChangeDetail;

/**
 * @author wk
 * @date 2023-11-06 17:41:50
 * @remark 
 */
public interface IQqchChangeDetailService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchChangeDetail getQqchChangeDetail(QqchChangeDetail qqchChangeDetail);

    List<QqchChangeDetail> getQqchChangeDetailList(QqchChangeDetail qqchChangeDetail);

    int insertQqchChangeDetail(QqchChangeDetail qqchChangeDetail);

    int insertQqchChangeDetailList(List<QqchChangeDetail> qqchChangeDetailList);

    int updateQqchChangeDetail(QqchChangeDetail qqchChangeDetail);

    int updateQqchChangeDetailList(List<QqchChangeDetail> qqchChangeDetailList);
    
    int deleteQqchChangeDetail(QqchChangeDetail qqchChangeDetail);

    int deleteQqchChangeDetailByPks(List<Long> qqchChangeDetailPkList);
}
