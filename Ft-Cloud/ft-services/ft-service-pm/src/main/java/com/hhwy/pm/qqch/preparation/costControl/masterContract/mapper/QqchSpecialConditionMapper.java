package com.hhwy.pm.qqch.preparation.costControl.masterContract.mapper;

import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchSpecialCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:48
 * @remark 专用条件梳理
 */
@Repository
public interface QqchSpecialConditionMapper {

    QqchSpecialCondition getQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition);

    List<QqchSpecialCondition> getQqchSpecialConditionList(QqchSpecialCondition qqchSpecialCondition);

    int insertQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition);

    int insertQqchSpecialConditionList(@Param("qqchSpecialConditionList") List<QqchSpecialCondition> qqchSpecialConditionList);

    int updateQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition);

    int updateQqchSpecialConditionList(@Param("list") List<QqchSpecialCondition> qqchSpecialConditionList);

    int deleteQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition);

    int deleteQqchSpecialConditionByPks(@Param("qqchSpecialConditionPkList") List<Long> qqchSpecialConditionPkList);
}
