package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.service;

import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.QqchWeightEngineeringList;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.vo.QqchWeightEngineeringListVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 14:24:06
 * @remark 
 */
public interface IQqchWeightEngineeringListService {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchWeightEngineeringList getQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);

    QqchWeightEngineeringListVo getQqchWeightEngineeringListList(QqchWeightEngineeringList qqchWeightEngineeringList);

    int insertQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);


    int updateQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);

    int updateQqchWeightEngineeringListList(List<QqchWeightEngineeringList> qqchWeightEngineeringListList);
    
    int deleteQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);

    int deleteQqchWeightEngineeringListByPks(List<Long> qqchWeightEngineeringListPkList);

    void save(QqchWeightEngineeringListVo vo);
}
