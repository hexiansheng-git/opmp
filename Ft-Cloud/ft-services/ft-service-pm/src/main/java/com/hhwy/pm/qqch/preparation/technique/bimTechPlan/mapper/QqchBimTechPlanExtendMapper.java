package com.hhwy.pm.qqch.preparation.technique.bimTechPlan.mapper;

import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.QqchBimTechPlanExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-27 15:04:01
 * @remark
 */
@Repository
public interface QqchBimTechPlanExtendMapper {

    QqchBimTechPlanExtend getQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend);

    List<QqchBimTechPlanExtend> getQqchBimTechPlanExtendList(QqchBimTechPlanExtend qqchBimTechPlanExtend);

    int insertQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend);

    int insertQqchBimTechPlanExtendList(@Param("qqchBimTechPlanExtendList") List<QqchBimTechPlanExtend> qqchBimTechPlanExtendList);

    int updateQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend);

    int updateQqchBimTechPlanExtendList(@Param("list") List<QqchBimTechPlanExtend> qqchBimTechPlanExtendList);

    int deleteQqchBimTechPlanExtend(QqchBimTechPlanExtend qqchBimTechPlanExtend);

    int deleteQqchBimTechPlanExtendByPks(@Param("qqchBimTechPlanExtendPkList") List<Long> qqchBimTechPlanExtendPkList);

    /**
     * 获取该版本bim标识
     * @param version
     */
    String getBimMark(@Param("version") BigDecimal version);
}
