package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchKeyDifficultConstructionBrief;
import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchKeyDifficultConstructionBriefVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-07-17 15:29:49
 * @remark 3.4.4重难点分项施工方案简述
 */
public interface IQqchKeyDifficultConstructionBriefService {

    QqchKeyDifficultConstructionBriefVo getQqchKeyDifficultConstructionBriefList(BigDecimal version);

    void batchSave(QqchKeyDifficultConstructionBriefVo qqchKeyDifficultConstructionBriefVo);

    List<QqchKeyDifficultConstructionBrief> getByWbsCodes(String[] wbsCodes);

    /**
     * 查询最新版的重难点分项施工方案简述数据
     * @return
     */
    List<QqchKeyDifficultConstructionBrief> getLatestList();
}
