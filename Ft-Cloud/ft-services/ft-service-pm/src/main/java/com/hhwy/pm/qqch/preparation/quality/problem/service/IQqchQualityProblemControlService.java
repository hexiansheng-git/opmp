package com.hhwy.pm.qqch.preparation.quality.problem.service;

import com.hhwy.pm.qqch.preparation.quality.problem.domain.vo.QqchQualityProblemControlVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:43
 * @remar 9.2.2 质量通病控制措施
 */
public interface IQqchQualityProblemControlService {

    /**
     * 列表
     *
     * @param version
     * @return
     */
    QqchQualityProblemControlVo getQqchQualityProblemControlList(BigDecimal version);

    /**
     * 保存/确认/提交
     *
     * @param qqchQualityProblemControlVo
     * @return
     */
    void updateQqchQualityProblemControlList(QqchQualityProblemControlVo qqchQualityProblemControlVo);

    /**
     * 质量通病清单数据保存时，将数据同步过来
     *
     * @param qqchQualityProblemControlVo
     */
    void syncData(QqchQualityProblemControlVo qqchQualityProblemControlVo);
}
