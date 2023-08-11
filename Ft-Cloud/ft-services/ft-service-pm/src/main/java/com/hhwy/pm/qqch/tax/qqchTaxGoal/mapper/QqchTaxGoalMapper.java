package com.hhwy.pm.qqch.tax.qqchTaxGoal.mapper;

import com.hhwy.pm.qqch.tax.qqchTaxGoal.domain.QqchTaxGoal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-09 18:17:29
 * @remark
 */
public interface QqchTaxGoalMapper {

    QqchTaxGoal getQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

    List<QqchTaxGoal> getQqchTaxGoalList(QqchTaxGoal qqchTaxGoal);

    int insertQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

    int insertQqchTaxGoalList(@Param("qqchTaxGoalList") List<QqchTaxGoal> qqchTaxGoalList);

    int updateQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

    int updateQqchTaxGoalList(@Param("qqchTaxGoalList") List<QqchTaxGoal> qqchTaxGoalList);

    int deleteQqchTaxGoal(QqchTaxGoal qqchTaxGoal);

    int deleteQqchTaxGoalByPks(@Param("qqchTaxGoalPkList") List<Long> qqchTaxGoalPkList);
}
