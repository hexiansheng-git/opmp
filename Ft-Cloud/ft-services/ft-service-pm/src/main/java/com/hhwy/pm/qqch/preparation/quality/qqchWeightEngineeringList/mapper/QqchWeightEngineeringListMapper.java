package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.mapper;

import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineeringList.domain.QqchWeightEngineeringList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 14:24:06
 * @remark 
 */
public interface QqchWeightEngineeringListMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchWeightEngineeringList getQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);

    List<QqchWeightEngineeringList> getQqchWeightEngineeringListList(QqchWeightEngineeringList qqchWeightEngineeringList);

    int insertQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);

    int insertQqchWeightEngineeringListList(@Param("qqchWeightEngineeringListList") List<QqchWeightEngineeringList> qqchWeightEngineeringListList);

    int updateQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);

    int updateQqchWeightEngineeringListList(@Param("qqchWeightEngineeringListList") List<QqchWeightEngineeringList> qqchWeightEngineeringListList);
    
    int deleteQqchWeightEngineeringList(QqchWeightEngineeringList qqchWeightEngineeringList);

    int deleteQqchWeightEngineeringListByPks(@Param("qqchWeightEngineeringListPkList") List<Long> qqchWeightEngineeringListPkList);
    }
