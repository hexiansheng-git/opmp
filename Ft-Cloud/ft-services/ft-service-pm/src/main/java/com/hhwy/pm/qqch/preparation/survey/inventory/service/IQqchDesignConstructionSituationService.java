package com.hhwy.pm.qqch.preparation.survey.inventory.service;

import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchDesignConstructionSituation;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchDesignConstructionSituationVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-13 10:09:03
 * @remark 边设计边施工情况
 */
public interface IQqchDesignConstructionSituationService {

    /**
     * 边设计边施工情况台账
     * @param qqchDesignConstructionSituation
     * @return
     */
    QqchDesignConstructionSituationVo getQqchDesignConstructionSituationVo(QqchDesignConstructionSituation qqchDesignConstructionSituation);

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

    /**
     * 批量删除
     * @param qqchDesignConstructionSituationPkList
     * @return
     */
    int deleteQqchDesignConstructionSituationByPks(List<Long> qqchDesignConstructionSituationPkList);
}
