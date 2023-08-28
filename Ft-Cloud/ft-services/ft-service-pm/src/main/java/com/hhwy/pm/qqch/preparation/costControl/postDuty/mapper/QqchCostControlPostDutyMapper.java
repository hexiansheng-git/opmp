package com.hhwy.pm.qqch.preparation.costControl.postDuty.mapper;

import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.QqchCostControlPostDuty;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:48:45
 * @remark
 */
@Repository
public interface QqchCostControlPostDutyMapper {

    QqchCostControlPostDuty getQqchCostControlPostDuty(QqchCostControlPostDuty qqchCostControlPostDuty);

    List<QqchCostControlPostDuty> getQqchCostControlPostDutyList(QqchCostControlPostDuty qqchCostControlPostDuty);

    int insertQqchCostControlPostDuty(QqchCostControlPostDuty qqchCostControlPostDuty);

    int insertQqchCostControlPostDutyList(@Param("qqchCostControlPostDutyList") List<QqchCostControlPostDuty> qqchCostControlPostDutyList);

    int updateQqchCostControlPostDuty(QqchCostControlPostDuty qqchCostControlPostDuty);

    int updateQqchCostControlPostDutyList(@Param("list") List<QqchCostControlPostDuty> qqchCostControlPostDutyList);

    int deleteQqchCostControlPostDuty(QqchCostControlPostDuty qqchCostControlPostDuty);

    int deleteQqchCostControlPostDutyByPks(@Param("qqchCostControlPostDutyPkList") List<Long> qqchCostControlPostDutyPkList);
}
