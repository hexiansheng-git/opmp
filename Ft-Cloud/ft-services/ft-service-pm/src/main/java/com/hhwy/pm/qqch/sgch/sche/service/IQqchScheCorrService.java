package com.hhwy.pm.qqch.sgch.sche.service;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheCorr;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 15:12:04
 * @remark
 */
public interface IQqchScheCorrService {

    QqchScheCorr getQqchScheCorr(QqchScheCorr qqchScheCorr);

    List<QqchScheCorr> getQqchScheCorrList(QqchScheCorr qqchScheCorr);

    int insertQqchScheCorr(QqchScheCorr qqchScheCorr);

    int insertQqchScheCorrList(List<QqchScheCorr> qqchScheCorrList);

    int updateQqchScheCorr(QqchScheCorr qqchScheCorr);

    int updateQqchScheCorrList(List<QqchScheCorr> qqchScheCorrList);

    int deleteQqchScheCorr(QqchScheCorr qqchScheCorr);

    int deleteQqchScheCorrByPks(List<Long> qqchScheCorrPkList);

    void saveList(List<QqchScheCorr> dealSaveDto);

    List<QqchScheCorr> getList(QqchScheCorr dealSaveDto);
}
