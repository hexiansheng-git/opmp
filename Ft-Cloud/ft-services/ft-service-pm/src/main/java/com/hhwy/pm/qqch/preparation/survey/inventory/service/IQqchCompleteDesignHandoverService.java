package com.hhwy.pm.qqch.preparation.survey.inventory.service;

import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchCompleteDesignHandover;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchCompleteDesignHandoverVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-11 17:11:14
 * @remark 完整设计交接情况
 */
public interface IQqchCompleteDesignHandoverService {

    /**
     * 完整设计交接情况台账
     * @param qqchCompleteDesignHandover
     * @return
     */
    QqchCompleteDesignHandoverVo getQqchCompleteDesignHandoverVo(QqchCompleteDesignHandover qqchCompleteDesignHandover);

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

    /**
     * 批量删除
     * @param qqchCompleteDesignHandoverPkList
     * @return
     */
    int deleteQqchCompleteDesignHandoverByPks(List<Long> qqchCompleteDesignHandoverPkList);
}
