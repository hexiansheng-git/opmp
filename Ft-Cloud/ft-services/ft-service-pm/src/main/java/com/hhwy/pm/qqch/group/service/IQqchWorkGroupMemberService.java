package com.hhwy.pm.qqch.group.service;

import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:45
 * @remark 前期策划工作小组成员
 */
public interface IQqchWorkGroupMemberService {

    QqchWorkGroupMember getQqchWorkGroupMember(QqchWorkGroupMember qqchWorkGroupMember);

    List<QqchWorkGroupMember> getQqchWorkGroupMemberList(QqchWorkGroupMember qqchWorkGroupMember);

    /**
     * 批量删除工作小组成员
     * @param qqchWorkGroupMemberPkList
     * @return
     */
    int deleteQqchWorkGroupMemberByPks(List<Long> qqchWorkGroupMemberPkList);
}
