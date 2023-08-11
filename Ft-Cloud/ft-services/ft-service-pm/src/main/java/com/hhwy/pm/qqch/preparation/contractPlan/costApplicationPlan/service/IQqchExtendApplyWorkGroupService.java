package com.hhwy.pm.qqch.preparation.contractPlan.costApplicationPlan.service;

import com.hhwy.pm.qqch.preparation.contractPlan.costApplicationPlan.domain.QqchExtendApplyWorkGroup;
import com.hhwy.pm.qqch.preparation.contractPlan.costApplicationPlan.domain.vo.QqchExtendApplyWorkGroupVo;

import java.util.List;

/**
 * @author han
 * @date 2023-08-08 17:48:45
 * @remark
 */
public interface IQqchExtendApplyWorkGroupService {

    QqchExtendApplyWorkGroup getQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    List<QqchExtendApplyWorkGroup> getQqchExtendApplyWorkGroupList(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    int insertQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    int updateQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    int updateQqchExtendApplyWorkGroupList(List<QqchExtendApplyWorkGroup> qqchExtendApplyWorkGroupList);

    int deleteQqchExtendApplyWorkGroup(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    int deleteQqchExtendApplyWorkGroupByPks(List<Long> qqchExtendApplyWorkGroupPkList);

    /**
     * 获取Vo
     * @param qqchExtendApplyWorkGroup
     * @return
     */
    QqchExtendApplyWorkGroupVo getQqchExtendApplyWorkGroupVo(QqchExtendApplyWorkGroup qqchExtendApplyWorkGroup);

    /**
     * 保存/确认/提交
     * @param qqchExtendApplyWorkGroupVo
     * @return
     */
    void save(QqchExtendApplyWorkGroupVo qqchExtendApplyWorkGroupVo);
}
