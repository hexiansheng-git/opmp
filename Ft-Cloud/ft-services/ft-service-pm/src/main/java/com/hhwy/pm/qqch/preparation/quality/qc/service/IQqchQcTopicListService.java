package com.hhwy.pm.qqch.preparation.quality.qc.service;

import com.hhwy.pm.qqch.preparation.quality.qc.domain.vo.QqchQcTopicListVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:25
 * @remark 9.6.1 QC课题清单
 */
public interface IQqchQcTopicListService {

    QqchQcTopicListVo getQqchQcTopicListList(BigDecimal version);

    void batchSave(QqchQcTopicListVo qqchQcTopicListVo);
}
