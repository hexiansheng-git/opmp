package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.mapper;

import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.QqchQualityRiskList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 10:02:39
 * @remark
 */
public interface QqchQualityRiskListMapper {

    QqchQualityRiskList getQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList);

    List<QqchQualityRiskList> getQqchQualityRiskListList(QqchQualityRiskList qqchQualityRiskList);

    int insertQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList);

    int insertQqchQualityRiskListList(@Param("qqchQualityRiskListList") List<QqchQualityRiskList> qqchQualityRiskListList);

    int updateQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList);

    int updateQqchQualityRiskListList(@Param("qqchQualityRiskListList") List<QqchQualityRiskList> qqchQualityRiskListList);

    int deleteQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList);

    int deleteQqchQualityRiskListByPks(@Param("qqchQualityRiskListPkList") List<Long> qqchQualityRiskListPkList);
}
