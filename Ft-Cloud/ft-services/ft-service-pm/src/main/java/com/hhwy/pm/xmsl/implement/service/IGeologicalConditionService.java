package com.hhwy.pm.xmsl.implement.service;

import com.hhwy.pm.xmsl.implement.domain.GeologicalCondition;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:07
 * @remark 地质条件-主线典型地质勘察
 */
public interface IGeologicalConditionService {

    GeologicalCondition getList(GeologicalCondition geologicalCondition);

    void save(GeologicalCondition geologicalCondition);
}
