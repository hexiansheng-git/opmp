package com.hhwy.pm.qqch.preparation.quality.qc.service;

import com.hhwy.pm.qqch.preparation.quality.qc.domain.QqchQcTopicList;
import com.hhwy.pm.qqch.preparation.quality.qc.domain.vo.QqchQcTopicListVo;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:25
 * @remark 9.6.1 QC课题清单
 */
public interface IQqchQcTopicListService {

    QqchQcTopicListVo getQqchQcTopicListList(BigDecimal version);

    void batchSave(QqchQcTopicListVo qqchQcTopicListVo);

    List<QqchQcTopicList> getHistoryList(QqchQcTopicList qqchQcTopicList);
}
