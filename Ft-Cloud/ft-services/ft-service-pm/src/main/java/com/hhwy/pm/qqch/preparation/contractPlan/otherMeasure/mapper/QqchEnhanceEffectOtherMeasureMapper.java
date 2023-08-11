package com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.mapper;

import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.QqchEnhanceEffectOtherMeasure;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:08:56
 * @remark
 */
@Repository
public interface QqchEnhanceEffectOtherMeasureMapper {

    QqchEnhanceEffectOtherMeasure getQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    List<QqchEnhanceEffectOtherMeasure> getQqchEnhanceEffectOtherMeasureList(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    int insertQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    int insertQqchEnhanceEffectOtherMeasureList(@Param("qqchEnhanceEffectOtherMeasureList") List<QqchEnhanceEffectOtherMeasure> qqchEnhanceEffectOtherMeasureList);

    int updateQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    int updateQqchEnhanceEffectOtherMeasureList(@Param("list") List<QqchEnhanceEffectOtherMeasure> qqchEnhanceEffectOtherMeasureList);

    int deleteQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    int deleteQqchEnhanceEffectOtherMeasureByPks(@Param("qqchEnhanceEffectOtherMeasurePkList") List<Long> qqchEnhanceEffectOtherMeasurePkList);
}
