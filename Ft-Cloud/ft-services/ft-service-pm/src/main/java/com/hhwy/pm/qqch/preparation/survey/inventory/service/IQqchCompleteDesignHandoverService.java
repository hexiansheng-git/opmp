package com.hhwy.pm.qqch.preparation.survey.inventory.service;

import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchCompleteDesignHandoverVo;

import java.math.BigDecimal;

/**
 * @author han
 * @date 2023-07-11 17:11:14
 * @remark 完整设计交接情况
 */
public interface IQqchCompleteDesignHandoverService {

    /**
     * 完整设计交接情况台账
     * @return
     * @param version
     */
    QqchCompleteDesignHandoverVo getQqchCompleteDesignHandoverVo(BigDecimal version);

    /**
     * 保存
     * @param qqchCompleteDesignHandoverVo
     * @return
     */
    void save(QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo);

    /**
     * 确认
     * @param qqchCompleteDesignHandoverVo
     * @return
     */
    void confirm(QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo);
}
