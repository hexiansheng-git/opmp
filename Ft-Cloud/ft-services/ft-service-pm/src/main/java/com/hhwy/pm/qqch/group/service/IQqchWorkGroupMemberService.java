package com.hhwy.pm.qqch.group.service;

import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.domain.vo.WorkGroupMemberQueryVo;

import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:45
 * @remark 前期策划工作小组成员
 */
public interface IQqchWorkGroupMemberService {

    /**
     * 获取工作小组成员历史
     *
     * @param queryVo
     * @return
     */
    List<QqchWorkGroupMember> getEstablishPreliminaryPlanHistory(WorkGroupMemberQueryVo queryVo);

    List<QqchWorkGroupMember> getQqchWorkGroupMemberList(QqchWorkGroupMember qqchWorkGroupMember);
}
