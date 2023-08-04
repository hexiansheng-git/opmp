package com.hhwy.pm.qqch.preparation.quality.qc.service;

import com.hhwy.pm.qqch.preparation.quality.qc.domain.vo.QqchQcImplementPlanVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-04 10:30:39
 * @remark 9.6.2 QC实施计划
 */
public interface IQqchQcImplementPlanService {

    /**
     * 列表
     *
     * @param version
     * @return
     */
    QqchQcImplementPlanVo getQqchQcImplementPlanList(BigDecimal version);

    /**
     * 保存/确认/提交
     *
     * @param qqchQcImplementPlanVo
     * @return
     */
    void updateQqchQcImplementPlan(QqchQcImplementPlanVo qqchQcImplementPlanVo);

    /**
     * 课题清单清单数据保存时，将数据同步实施计划
     *
     * @param qqchQcImplementPlanVo
     */
    void syncData(QqchQcImplementPlanVo qqchQcImplementPlanVo);

}
