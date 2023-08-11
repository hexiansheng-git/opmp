package com.hhwy.pm.qqch.sgch.milestone.service;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.milestone.domain.QqchMilestone;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 11:18:27
 * @remark
 */
public interface IQqchMilestoneService {

    QqchMilestone getQqchMilestone(QqchMilestone qqchMilestone);

    List<QqchMilestone> getQqchMilestoneList(QqchMilestone qqchMilestone);

    int insertQqchMilestone(QqchMilestone qqchMilestone);

    int insertQqchMilestoneList(List<QqchMilestone> qqchMilestoneList);

    int updateQqchMilestone(QqchMilestone qqchMilestone);

    int updateQqchMilestoneList(List<QqchMilestone> qqchMilestoneList);

    int deleteQqchMilestone(QqchMilestone qqchMilestone);

    int deleteQqchMilestoneByPks(List<Long> qqchMilestonePkList);

    CompileEntity list(QqchMilestone qqchMilestoneParam);

    void save(List<QqchMilestone> list);
}
