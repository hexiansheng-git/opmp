package com.hhwy.pm.qqch.preparation.quality.duty.mapper;

import com.hhwy.pm.qqch.preparation.quality.duty.domain.QqchQualityPostDuty;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-03 14:28:57
 * @remark 9.1.1 质量岗位职责
 */
public interface QqchQualityPostDutyMapper {

    QqchQualityPostDuty getQqchQualityPostDuty(QqchQualityPostDuty qqchQualityPostDuty);

    List<QqchQualityPostDuty> getQqchQualityPostDutyList(QqchQualityPostDuty qqchQualityPostDuty);

    int insertQqchQualityPostDuty(QqchQualityPostDuty qqchQualityPostDuty);

    int insertQqchQualityPostDutyList(
        @Param("qqchQualityPostDutyList") List<QqchQualityPostDuty> qqchQualityPostDutyList);

    int updateQqchQualityPostDuty(QqchQualityPostDuty qqchQualityPostDuty);

    int updateQqchQualityPostDutyList(@Param("list") List<QqchQualityPostDuty> qqchQualityPostDutyList);

    int deleteQqchQualityPostDuty(QqchQualityPostDuty qqchQualityPostDuty);

    int deleteQqchQualityPostDutyByPks(@Param("qqchQualityPostDutyPkList") List<Long> qqchQualityPostDutyPkList);
}
