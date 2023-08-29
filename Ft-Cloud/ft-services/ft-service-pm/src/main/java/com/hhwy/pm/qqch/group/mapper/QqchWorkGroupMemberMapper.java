package com.hhwy.pm.qqch.group.mapper;

import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.domain.vo.WorkGroupMemberQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-06 15:23:45
 * @remark 前期策划工作小组成员
 */
@Repository
public interface QqchWorkGroupMemberMapper {

    QqchWorkGroupMember getQqchWorkGroupMember(QqchWorkGroupMember qqchWorkGroupMember);

    List<QqchWorkGroupMember> getQqchWorkGroupMemberList(QqchWorkGroupMember qqchWorkGroupMember);

    int insertQqchWorkGroupMember(QqchWorkGroupMember qqchWorkGroupMember);

    int insertQqchWorkGroupMemberList(@Param("qqchWorkGroupMemberList") List<QqchWorkGroupMember> qqchWorkGroupMemberList);

    int updateQqchWorkGroupMember(QqchWorkGroupMember qqchWorkGroupMember);

    int updateQqchWorkGroupMemberList(@Param("list") List<QqchWorkGroupMember> qqchWorkGroupMemberList);

    int deleteQqchWorkGroupMember(QqchWorkGroupMember qqchWorkGroupMember);

    int deleteQqchWorkGroupMemberByPks(@Param("qqchWorkGroupMemberPkList") List<Long> qqchWorkGroupMemberPkList);

    List<QqchWorkGroupMember> getWorkGroupMemberHistory(WorkGroupMemberQueryVo queryVo);
}
