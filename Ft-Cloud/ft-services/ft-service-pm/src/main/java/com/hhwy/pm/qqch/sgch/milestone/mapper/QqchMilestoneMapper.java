package com.hhwy.pm.qqch.sgch.milestone.mapper;

import com.hhwy.pm.qqch.sgch.milestone.domain.QqchMilestone;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 11:18:27
 * @remark
 */
public interface QqchMilestoneMapper {

    QqchMilestone getQqchMilestone(QqchMilestone qqchMilestone);

    List<QqchMilestone> getQqchMilestoneList(QqchMilestone qqchMilestone);

    int insertQqchMilestone(QqchMilestone qqchMilestone);

    int insertQqchMilestoneList(@Param("qqchMilestoneList") List<QqchMilestone> qqchMilestoneList);

    int updateQqchMilestone(QqchMilestone qqchMilestone);

    int updateQqchMilestoneList(@Param("list") List<QqchMilestone> qqchMilestoneList);

    int deleteQqchMilestone(QqchMilestone qqchMilestone);

    int deleteQqchMilestoneByPks(@Param("qqchMilestonePkList") List<Long> qqchMilestonePkList);
}
