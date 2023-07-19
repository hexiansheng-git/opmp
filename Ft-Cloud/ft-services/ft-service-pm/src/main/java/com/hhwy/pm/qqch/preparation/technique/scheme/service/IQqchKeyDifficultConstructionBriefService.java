package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchKeyDifficultConstructionBriefVo;

/**
 * @author zhenlili
 * @date 2023-07-17 15:29:49
 * @remark 3.4.4重难点分项施工方案简述
 */
public interface IQqchKeyDifficultConstructionBriefService {

    QqchKeyDifficultConstructionBriefVo getQqchKeyDifficultConstructionBriefList();

    void batchSave(QqchKeyDifficultConstructionBriefVo qqchKeyDifficultConstructionBriefVo);
}
