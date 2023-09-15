package com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.service;

import com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.domain.QqchSmallMachinery;
import com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.domain.vo.QqchSmallMachineryVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-02 10:50:59
 * @remark 
 */
public interface IQqchSmallMachineryService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchSmallMachinery getQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery);

    QqchSmallMachineryVo getQqchSmallMachineryList(QqchSmallMachinery qqchSmallMachinery);

    int insertQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery);


    int updateQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery);

    int updateQqchSmallMachineryList(List<QqchSmallMachinery> qqchSmallMachineryList);
    
    int deleteQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery);

    int deleteQqchSmallMachineryByPks(List<Long> qqchSmallMachineryPkList);

    void save(QqchSmallMachineryVo qqchSmallMachineryVo);

    QqchSmallMachineryVo syncData(QqchSmallMachineryVo qqchSmallMachineryVo);
}
