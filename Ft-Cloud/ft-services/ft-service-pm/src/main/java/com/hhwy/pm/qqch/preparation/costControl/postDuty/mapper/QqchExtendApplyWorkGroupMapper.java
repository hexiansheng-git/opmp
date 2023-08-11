package com.hhwy.pm.qqch.preparation.costControl.postDuty.mapper;

import com.hhwy.pm.qqch.preparation.costControl.postDuty.domain.QqchExtendApplyWorkGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:48:45
 * @remark
 */
@Repository
public interface QqchExtendApplyWorkGroupMapper {

    QqchExtendApplyWorkGroup getQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    List<QqchExtendApplyWorkGroup> getQqchExtendApplyWorkGroupList(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    int insertQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    int insertQqchExtendApplyWorkGroupList(@Param("qqchExtendApplyWorkGroupList") List<QqchExtendApplyWorkGroup> qqchExtendApplyWorkGroupList);

    int updateQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    int updateQqchExtendApplyWorkGroupList(@Param("list") List<QqchExtendApplyWorkGroup> qqchExtendApplyWorkGroupList);

    int deleteQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    int deleteQqchExtendApplyWorkGroupByPks(@Param("qqchExtendApplyWorkGroupPkList") List<Long> qqchExtendApplyWorkGroupPkList);
}
