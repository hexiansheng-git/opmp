package com.hhwy.pm.qqch.preparation.survey.inventory.service;

import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchDesignConstructionSituationVo;

/**
 * @author han
 * @date 2023-07-13 10:09:03
 * @remark 边设计边施工情况
 */
public interface IQqchDesignConstructionSituationService {

    /**
     * 边设计边施工情况台账
     * @return
     */
    QqchDesignConstructionSituationVo getQqchDesignConstructionSituationVo();

    /**
     * 保存
     * @param qqchDesignConstructionSituationVo
     * @return
     */
    void save(QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo);

    /**
     * 确认
     * @param qqchDesignConstructionSituationVo
     * @return
     */
    void confirm(QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo);
}
