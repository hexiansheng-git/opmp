package com.hhwy.pm.qqch.preparation.technique.expert.service;

import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetExpert;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:56:35
 * @remark 内外部目标专家选择
 */
public interface IQqchTargetExpertService {

    QqchTargetExpert getQqchTargetExpert(QqchTargetExpert qqchTargetExpert);

    List<QqchTargetExpert> getQqchTargetExpertList(QqchTargetExpert qqchTargetExpert);

    int insertQqchTargetExpert(QqchTargetExpert qqchTargetExpert);

    int insertQqchTargetExpertList(List<QqchTargetExpert> qqchTargetExpertList);

    int updateQqchTargetExpert(QqchTargetExpert qqchTargetExpert);

    int updateQqchTargetExpertList(List<QqchTargetExpert> qqchTargetExpertList);

    int deleteQqchTargetExpert(QqchTargetExpert qqchTargetExpert);

    int deleteQqchTargetExpertByPks(List<Long> qqchTargetExpertPkList);
}
