package com.hhwy.pm.qqch.preparation.technique.scheme.service;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.vo.QqchConstructionListVo;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:14
 * @remark 3.4.2施工方案清单
 */
public interface IQqchConstructionListService {

    QqchConstructionListVo getQqchConstructionListList();

    void batchSave(QqchConstructionListVo qqchConstructionListVo);
}
