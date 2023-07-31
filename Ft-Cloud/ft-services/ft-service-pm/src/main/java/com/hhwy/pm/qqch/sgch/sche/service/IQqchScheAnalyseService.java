package com.hhwy.pm.qqch.sgch.sche.service;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheAnalyse;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:46
 * @remark
 */
public interface IQqchScheAnalyseService {

    QqchScheAnalyse getQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse);

    List<QqchScheAnalyse> getQqchScheAnalyseList(QqchScheAnalyse qqchScheAnalyse);

    int insertQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse);

    int insertQqchScheAnalyseList(List<QqchScheAnalyse> qqchScheAnalyseList);

    int updateQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse);

    int updateQqchScheAnalyseList(List<QqchScheAnalyse> qqchScheAnalyseList);

    int deleteQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse);

    int deleteQqchScheAnalyseByPks(List<Long> qqchScheAnalysePkList);
}
