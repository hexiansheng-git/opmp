package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.service;

import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.domain.QqchSpecialBigProcessControl;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigProcessControl.domain.vo.QqchSpecialBigProcessControlVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 16:17:26
 * @remark 
 */
public interface IQqchSpecialBigProcessControlService {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchSpecialBigProcessControl getQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl);

    QqchSpecialBigProcessControlVo getQqchSpecialBigProcessControlList(QqchSpecialBigProcessControl qqchSpecialBigProcessControl);

    int insertQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl);

    int updateQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl);

    int updateQqchSpecialBigProcessControlList(List<QqchSpecialBigProcessControl> qqchSpecialBigProcessControlList);
    
    int deleteQqchSpecialBigProcessControl(QqchSpecialBigProcessControl qqchSpecialBigProcessControl);

    int deleteQqchSpecialBigProcessControlByPks(List<Long> qqchSpecialBigProcessControlPkList);

    void save(QqchSpecialBigProcessControlVo vo);
}
