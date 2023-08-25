package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.service;

import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.QqchFirstArticleEngineeringControl;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.vo.QqchFirstArticleEngineeringControlVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 17:06:12
 * @remark 
 */
public interface IQqchFirstArticleEngineeringControlService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchFirstArticleEngineeringControl getQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

    QqchFirstArticleEngineeringControlVo getQqchFirstArticleEngineeringControlList(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

    int insertQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);


    int updateQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

    int updateQqchFirstArticleEngineeringControlList(List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList);
    
    int deleteQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

    int deleteQqchFirstArticleEngineeringControlByPks(List<Long> qqchFirstArticleEngineeringControlPkList);

    void save(QqchFirstArticleEngineeringControlVo vo);
}
