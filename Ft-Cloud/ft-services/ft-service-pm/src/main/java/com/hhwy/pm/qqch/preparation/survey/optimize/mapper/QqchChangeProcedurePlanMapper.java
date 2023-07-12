package com.hhwy.pm.qqch.preparation.survey.optimize.mapper;

import java.util.List;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
@Repository
public interface QqchChangeProcedurePlanMapper {

    QqchChangeProcedurePlan getQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan);

    /**
     * 获取变更程序策划集合
     * @return
     */
    List<QqchChangeProcedurePlan> getQqchChangeProcedurePlanList();

    int insertQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan);

    int insertQqchChangeProcedurePlanList(@Param("qqchChangeProcedurePlanList") List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList);

    int updateQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan);

    int updateQqchChangeProcedurePlanList(@Param("list") List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList);

    int deleteQqchChangeProcedurePlan(QqchChangeProcedurePlan qqchChangeProcedurePlan);

    int deleteQqchChangeProcedurePlanByPks(@Param("qqchChangeProcedurePlanPkList") List<Long> qqchChangeProcedurePlanPkList);
}
