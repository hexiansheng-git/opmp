package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchDesignTechnologyOptimizeVo;

/**
 * @author han
 * @date 2023-07-07 18:35:48
 * @remark 设计技术优化要点
 */
public interface IQqchDesignTechnologyOptimizeService {

    /**
     * 获取设计技术优化要点集合
     * @return
     */
    QqchDesignTechnologyOptimizeVo getQqchDesignTechnologyOptimizeVo();

    /**
     * 保存
     * @param qqchDesignTechnologyOptimizeVo
     */
    void save(QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo);

    /**
     * 确认  添加已确认状态，并保存数据
     * @param qqchDesignTechnologyOptimizeVo
     */
    void confirm(QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo);
}
