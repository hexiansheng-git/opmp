package com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.service;

import java.util.List;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.domain.QqchTotalDemandTimeCount;

/**
 * @author ldd
 * @date 2023-08-02 10:55:17
 * @remark 
 */
public interface IQqchTotalDemandTimeCountService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchTotalDemandTimeCount getQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount);

    List<QqchTotalDemandTimeCount> getQqchTotalDemandTimeCountList(QqchTotalDemandTimeCount qqchTotalDemandTimeCount);

    int insertQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount);

    int insertQqchTotalDemandTimeCountList(List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountList);

    int updateQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount);

            int updateQqchTotalDemandTimeCountList(List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountList);
    
    int deleteQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount);

            int deleteQqchTotalDemandTimeCountByPks(List<Long> qqchTotalDemandTimeCountPkList);
    }
