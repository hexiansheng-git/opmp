package com.hhwy.pm.qqch.preparation.technique.scheme.mapper;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchKeyDifficultConstructionBrief;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-07-17 15:29:49
 * @remark
 */
public interface QqchKeyDifficultConstructionBriefMapper {

    QqchKeyDifficultConstructionBrief getQqchKeyDifficultConstructionBrief(
        QqchKeyDifficultConstructionBrief qqchKeyDifficultConstructionBrief);

    List<QqchKeyDifficultConstructionBrief> getQqchKeyDifficultConstructionBriefList(
        QqchKeyDifficultConstructionBrief qqchKeyDifficultConstructionBrief);

    int insertQqchKeyDifficultConstructionBrief(QqchKeyDifficultConstructionBrief qqchKeyDifficultConstructionBrief);

    int insertQqchKeyDifficultConstructionBriefList(
        @Param("qqchKeyDifficultConstructionBriefList") List<QqchKeyDifficultConstructionBrief> qqchKeyDifficultConstructionBriefList);

    int updateQqchKeyDifficultConstructionBrief(QqchKeyDifficultConstructionBrief qqchKeyDifficultConstructionBrief);

    int updateQqchKeyDifficultConstructionBriefList(
        @Param("list") List<QqchKeyDifficultConstructionBrief> qqchKeyDifficultConstructionBriefList);

    int deleteQqchKeyDifficultConstructionBrief(QqchKeyDifficultConstructionBrief qqchKeyDifficultConstructionBrief);

    int deleteQqchKeyDifficultConstructionBriefByPks(
        @Param("qqchKeyDifficultConstructionBriefPkList") List<Long> qqchKeyDifficultConstructionBriefPkList);

    List<QqchKeyDifficultConstructionBrief> getByWbsCodes(@Param("wbsCodes") String[] wbsCodes);
}
