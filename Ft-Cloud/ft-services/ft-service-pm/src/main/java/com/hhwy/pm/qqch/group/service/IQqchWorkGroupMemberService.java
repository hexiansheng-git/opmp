package com.hhwy.pm.qqch.group.service;


import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;

import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:45
 * @remark 前期策划工作小组成员
 */
public interface IQqchWorkGroupMemberService {

    /**
     * 获取工作小组成员历史
     * @param directorId 成员id
     * @return
     */
    List<QqchWorkGroupMember> getEstablishPreliminaryPlanHistory(Long directorId);
}
