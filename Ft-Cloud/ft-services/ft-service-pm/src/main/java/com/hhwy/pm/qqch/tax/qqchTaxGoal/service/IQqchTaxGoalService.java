package com.hhwy.pm.qqch.tax.qqchTaxGoal.service;

import java.util.List;
import com.hhwy.pm.qqch.tax.qqchTaxGoal.domain.QqchTaxGoal;

/**
 * @author mls
 * @date 2023-08-09 18:17:29
 * @remark 
 */
public interface IQqchTaxGoalService {
                                                                                                                                                                                                                                                                                                                                                                
    QqchTaxGoal getQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

    List<QqchTaxGoal> getQqchTaxGoalList(QqchTaxGoal qqchTaxGoal);

    int insertQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

    int insertQqchTaxGoalList(List<QqchTaxGoal> qqchTaxGoalList);

    int updateQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

            int updateQqchTaxGoalList(List<QqchTaxGoal> qqchTaxGoalList);
    
    int deleteQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

            int deleteQqchTaxGoalByPks(List<Long> qqchTaxGoalPkList);
    }
