package com.hhwy.pm.qqch.preparation.measureexp.org.service;

import java.util.List;
import com.hhwy.pm.qqch.preparation.measureexp.org.domain.QqchMeasureOrg;

/**
 * @author mls
 * @date 2023-07-25 18:01:39
 * @remark 
 */
public interface IQqchMeasureOrgService {
                                                                                                                                                                                                                                        
    QqchMeasureOrg getQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg);

    List<QqchMeasureOrg> getQqchMeasureOrgList(QqchMeasureOrg qqchMeasureOrg);

    int insertQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg);

    int insertQqchMeasureOrgList(List<QqchMeasureOrg> qqchMeasureOrgList);

    int updateQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg);

            int updateQqchMeasureOrgList(List<QqchMeasureOrg> qqchMeasureOrgList);
    
    int deleteQqchMeasureOrg(QqchMeasureOrg qqchMeasureOrg);

            int deleteQqchMeasureOrgByPks(List<Long> qqchMeasureOrgPkList);
    }
