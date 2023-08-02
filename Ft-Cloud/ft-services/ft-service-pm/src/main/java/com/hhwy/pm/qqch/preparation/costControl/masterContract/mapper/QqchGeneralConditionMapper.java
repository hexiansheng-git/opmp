package com.hhwy.pm.qqch.preparation.costControl.masterContract.mapper;

import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchGeneralCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:46
 * @remark 通用条件梳理
 */
@Repository
public interface QqchGeneralConditionMapper {

    QqchGeneralCondition getQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition);

    List<QqchGeneralCondition> getQqchGeneralConditionList(QqchGeneralCondition qqchGeneralCondition);

    int insertQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition);

    int insertQqchGeneralConditionList(@Param("qqchGeneralConditionList") List<QqchGeneralCondition> qqchGeneralConditionList);

    int updateQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition);

    int updateQqchGeneralConditionList(@Param("list") List<QqchGeneralCondition> qqchGeneralConditionList);

    int deleteQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition);

    int deleteQqchGeneralConditionByPks(@Param("qqchGeneralConditionPkList") List<Long> qqchGeneralConditionPkList);
}
