package com.hhwy.pm.qqch.sgch.sche.service;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiff;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:48
 * @remark
 */
public interface IQqchScheDiffService {

    QqchScheDiff getQqchScheDiff(QqchScheDiff qqchScheDiff);

    List<QqchScheDiff> getQqchScheDiffList(QqchScheDiff qqchScheDiff);

    int insertQqchScheDiff(QqchScheDiff qqchScheDiff);

    int insertQqchScheDiffList(List<QqchScheDiff> qqchScheDiffList);

    int updateQqchScheDiff(QqchScheDiff qqchScheDiff);

    int updateQqchScheDiffList(List<QqchScheDiff> qqchScheDiffList);

    int deleteQqchScheDiff(QqchScheDiff qqchScheDiff);

    int deleteQqchScheDiffByPks(List<Long> qqchScheDiffPkList);
}
