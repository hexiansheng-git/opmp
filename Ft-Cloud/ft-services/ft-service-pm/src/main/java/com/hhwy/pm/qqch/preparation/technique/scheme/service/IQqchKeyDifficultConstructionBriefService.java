package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchKeyDifficultConstructionBriefVo;

/**
 * @author zhenlili
 * @date 2023-07-17 15:29:49
 * @remark
 */
public interface IQqchKeyDifficultConstructionBriefService {

    QqchKeyDifficultConstructionBriefVo getQqchKeyDifficultConstructionBriefList();

    void batchSave(QqchKeyDifficultConstructionBriefVo qqchKeyDifficultConstructionBriefVo);
}
