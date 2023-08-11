package com.hhwy.pm.qqch.preparation.safe.danger.service;

import com.hhwy.pm.qqch.preparation.safe.danger.domain.vo.QqchDangerProcessControlPlanVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-07 14:24:24
 * @remark 8.3.3 危大工程过程管控策划
 */
public interface IQqchDangerProcessControlPlanService {

    /**
     * 列表
     *
     * @param version
     * @return
     */
    QqchDangerProcessControlPlanVo getQqchDangerProcessControlPlanList(BigDecimal version);

    /**
     * 保存/确认/提交
     *
     * @param qqchDangerProcessControlPlanVo
     * @return
     */
    void batchSave(QqchDangerProcessControlPlanVo qqchDangerProcessControlPlanVo);

    /**
     * 同步数据
     *
     * @return
     */
    void syncData(QqchDangerProcessControlPlanVo qqchDangerProcessControlPlanVo);
}
