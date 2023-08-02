package com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.service;

import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.domain.QqchTotalDemand;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.domain.vo.QqchTotalDemandVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-02 10:55:15
 * @remark 
 */
public interface IQqchTotalDemandService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchTotalDemand getQqchTotalDemand(QqchTotalDemand qqchTotalDemand);

    QqchTotalDemandVo getQqchTotalDemandList(QqchTotalDemand qqchTotalDemand);

    int insertQqchTotalDemand(QqchTotalDemand qqchTotalDemand);

    int updateQqchTotalDemand(QqchTotalDemand qqchTotalDemand);

    int updateQqchTotalDemandList(List<QqchTotalDemand> qqchTotalDemandList);
    
    int deleteQqchTotalDemand(QqchTotalDemand qqchTotalDemand);

    int deleteQqchTotalDemandByPks(List<Long> qqchTotalDemandPkList);

    void save(QqchTotalDemandVo vo);
}
