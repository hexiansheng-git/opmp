package com.hhwy.pm.qqch.preparation.survey.optimize.service;

import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchDesignTechnologyOptimize;

import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:48
 * @remark 设计技术优化要点
 */
public interface IQqchDesignTechnologyOptimizeService {

    QqchDesignTechnologyOptimize getQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize);

    /**
     * 获取设计技术优化要点集合
     * @return
     */
    List<QqchDesignTechnologyOptimize> getQqchDesignTechnologyOptimizeList();

    /**
     * 批量编辑（新增和修改）
     * @param qqchDesignTechnologyOptimizeListParam
     * @return
     */
    int editQqchDesignTechnologyOptimizeList(List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeListParam);

    int insertQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize);

    int insertQqchDesignTechnologyOptimizeList(List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList);

    int updateQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize);

    int updateQqchDesignTechnologyOptimizeList(List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList);

    int deleteQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize);

    int deleteQqchDesignTechnologyOptimizeByPks(List<Long> qqchDesignTechnologyOptimizePkList);
}
