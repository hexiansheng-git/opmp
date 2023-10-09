package com.hhwy.pm.qqch.preparation.quality.duty.service;

import com.hhwy.pm.qqch.preparation.quality.duty.domain.vo.QqchPersonControlPlanVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-03 14:29:43
 * @remark 9.1.2 人员管控策划
 */
public interface IQqchPersonControlPlanService {

    QqchPersonControlPlanVo getQqchPersonControlPlanList(BigDecimal version);

    void insertQqchPersonControlPlanList(QqchPersonControlPlanVo qqchPersonControlPlanVo);

    void personControlPlanWarn();
}
