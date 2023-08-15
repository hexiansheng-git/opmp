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

    QqchCostControlPostDuty getQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty);

    List<QqchCostControlPostDuty> getQqchExtendApplyWorkGroupList(QqchCostControlPostDuty qqchCostControlPostDuty);

    int insertQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty);

    int insertQqchExtendApplyWorkGroupList(@Param("qqchExtendApplyWorkGroupList") List<QqchCostControlPostDuty> qqchCostControlPostDutyList);

    int updateQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty);

    int updateQqchExtendApplyWorkGroupList(@Param("list") List<QqchCostControlPostDuty> qqchCostControlPostDutyList);

    int deleteQqchExtendApplyWorkGroup(QqchCostControlPostDuty qqchCostControlPostDuty);

    int deleteQqchExtendApplyWorkGroupByPks(@Param("qqchExtendApplyWorkGroupPkList") List<Long> qqchExtendApplyWorkGroupPkList);
}
