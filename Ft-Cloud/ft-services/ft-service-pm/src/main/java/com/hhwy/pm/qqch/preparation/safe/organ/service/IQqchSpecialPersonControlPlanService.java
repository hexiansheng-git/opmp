package com.hhwy.pm.qqch.preparation.safe.organ.service;

import com.hhwy.pm.qqch.preparation.safe.organ.domain.vo.QqchSpecialPersonControlPlanVo;
import java.math.BigDecimal;

/**
 * @author zhenglili
 * @date 2023-08-07 13:50:54
 * @remark 8.1.3 特种作业人员管控策划
 */
public interface IQqchSpecialPersonControlPlanService {

    QqchSpecialPersonControlPlanVo getQqchSpecialPersonControlPlanList(BigDecimal version);

    void batchSave(QqchSpecialPersonControlPlanVo qqchSpecialPersonControlPlanVo);
}
