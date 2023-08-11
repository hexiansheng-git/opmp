package com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.service;

import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.QqchEnhanceEffectOtherMeasure;
import com.hhwy.pm.qqch.preparation.contractPlan.otherMeasure.domain.vo.QqchEnhanceEffectOtherMeasureVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:08:56
 * @remark
 */
public interface IQqchEnhanceEffectOtherMeasureService {

    QqchEnhanceEffectOtherMeasure getQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    List<QqchEnhanceEffectOtherMeasure> getQqchEnhanceEffectOtherMeasureList(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    int insertQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    int updateQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    int updateQqchEnhanceEffectOtherMeasureList(List<QqchEnhanceEffectOtherMeasure> qqchEnhanceEffectOtherMeasureList);

    int deleteQqchEnhanceEffectOtherMeasure(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    int deleteQqchEnhanceEffectOtherMeasureByPks(List<Long> qqchEnhanceEffectOtherMeasurePkList);

    /**
     * 获取Vo
     * @param qqchEnhanceEffectOtherMeasure
     * @return
     */
    QqchEnhanceEffectOtherMeasureVo getQqchEnhanceEffectOtherMeasureVo(QqchEnhanceEffectOtherMeasure qqchEnhanceEffectOtherMeasure);

    /**
     * 保存/确认/提交
     * @param qqchEnhanceEffectOtherMeasureVo
     * @return
     */
    void save(QqchEnhanceEffectOtherMeasureVo qqchEnhanceEffectOtherMeasureVo);
}
