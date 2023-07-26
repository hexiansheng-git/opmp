package com.hhwy.pm.qqch.preparation.measureexp.person.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.measureexp.person.domain.QqchMeasureExpPerson;
import org.apache.ibatis.annotations.Param;

/**
 * @author mls
 * @date 2023-07-25 18:01:30
 * @remark 
 */
public interface QqchMeasureExpPersonMapper {
                                                                                                                                                                                                                                                                                                                            
    QqchMeasureExpPerson getQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson);

    List<QqchMeasureExpPerson> getQqchMeasureExpPersonList(QqchMeasureExpPerson qqchMeasureExpPerson);

    int insertQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson);

    int insertQqchMeasureExpPersonList(@Param("qqchMeasureExpPersonList") List<QqchMeasureExpPerson> qqchMeasureExpPersonList);

    int updateQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson);

            int updateQqchMeasureExpPersonList(@Param("qqchMeasureExpPersonList") List<QqchMeasureExpPerson> qqchMeasureExpPersonList);
    
    int deleteQqchMeasureExpPerson(QqchMeasureExpPerson qqchMeasureExpPerson);

            int deleteQqchMeasureExpPersonByPks(@Param("qqchMeasureExpPersonPkList") List<Long> qqchMeasureExpPersonPkList);
    }
