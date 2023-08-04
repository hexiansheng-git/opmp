package com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.service;

import java.util.List;
import com.hhwy.pm.qqch.preparation.quality.qqchFirstArticleEngineeringControl.domain.QqchFirstArticleEngineeringControl;

/**
 * @author ldd
 * @date 2023-08-04 17:06:12
 * @remark 
 */
public interface IQqchFirstArticleEngineeringControlService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchFirstArticleEngineeringControl getQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

    List<QqchFirstArticleEngineeringControl> getQqchFirstArticleEngineeringControlList(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

    int insertQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

    int insertQqchFirstArticleEngineeringControlList(List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList);

    int updateQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

            int updateQqchFirstArticleEngineeringControlList(List<QqchFirstArticleEngineeringControl> qqchFirstArticleEngineeringControlList);
    
    int deleteQqchFirstArticleEngineeringControl(QqchFirstArticleEngineeringControl qqchFirstArticleEngineeringControl);

            int deleteQqchFirstArticleEngineeringControlByPks(List<Long> qqchFirstArticleEngineeringControlPkList);
    }
