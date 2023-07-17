package com.hhwy.pm.qqch.group.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMemberMapper;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupMemberService;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

import javax.validation.constraints.NotNull;

/**
 * @author han
 * @date 2023-07-06 15:23:45
 * @remark 前期策划工作小组成员
 */
@Service
public class QqchWorkGroupMemberServiceImpl implements IQqchWorkGroupMemberService {

    @Autowired
    private QqchWorkGroupMemberMapper qqchWorkGroupMemberMapper;


    public QqchWorkGroupMember getQqchWorkGroupMember(QqchWorkGroupMember qqchWorkGroupMember) {
        return qqchWorkGroupMemberMapper.getQqchWorkGroupMember(qqchWorkGroupMember);
    }

    public List<QqchWorkGroupMember> getQqchWorkGroupMemberList(QqchWorkGroupMember qqchWorkGroupMember) {
        return qqchWorkGroupMemberMapper.getQqchWorkGroupMemberList(qqchWorkGroupMember);
    }

    /**
     * 编辑数据
     * @param qqchWorkGroupMemberList
     * @param qqchWorkGroup
     */
    @Transactional
    public void editQqchWorkGroupMemberList(List<QqchWorkGroupMember> qqchWorkGroupMemberList, QqchWorkGroup qqchWorkGroup) {

        //删除旧数据
        Long workGroupId = qqchWorkGroup.getId();
        QqchWorkGroupMember qqchWorkGroupMember = new QqchWorkGroupMember();
        qqchWorkGroupMember.setWorkGroupId(workGroupId);
        qqchWorkGroupMemberMapper.deleteQqchWorkGroupMember(qqchWorkGroupMember);

        //插入新数据
        this.insertQqchWorkGroupMemberList(qqchWorkGroupMemberList,qqchWorkGroup);
    }

    /**
     * 批量插入工作小组成员
     * @param qqchWorkGroupMemberList
     * @param qqchWorkGroup
     * @return
     */
    @Transactional
    public int insertQqchWorkGroupMemberList(List<QqchWorkGroupMember> qqchWorkGroupMemberList, QqchWorkGroup qqchWorkGroup) {
        for (QqchWorkGroupMember qqchWorkGroupMember : qqchWorkGroupMemberList) {
            qqchWorkGroupMember.setId(IdWorker.createId());
            qqchWorkGroupMember.setWorkGroupId(qqchWorkGroup.getId());
            qqchWorkGroupMember.setProjectId(qqchWorkGroup.getProjectId());
            qqchWorkGroupMember.setProjectName(qqchWorkGroup.getProjectName());
            qqchWorkGroupMember.setRegionId(qqchWorkGroup.getRegionId());
            qqchWorkGroupMember.setRegionName(qqchWorkGroup.getRegionName());
            qqchWorkGroupMember.setDeptId(qqchWorkGroup.getDeptId());
            qqchWorkGroupMember.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchWorkGroupMember.setCreateUserName(SecurityUtils.getUserName());
            qqchWorkGroupMember.setCreateTime(DateUtils.getNowDate());
        }
        return qqchWorkGroupMemberMapper.insertQqchWorkGroupMemberList(qqchWorkGroupMemberList);
    }

    /**
     * 根据条件删除工作小组成员
     * @param qqchWorkGroupMember
     * @return
     */
    @Transactional
    public int deleteQqchWorkGroupMember(QqchWorkGroupMember qqchWorkGroupMember) {
        qqchWorkGroupMember.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchWorkGroupMember.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkGroupMemberMapper.deleteQqchWorkGroupMember(qqchWorkGroupMember);
    }

    /**
     * 批量删除工作小组成员
     * @param qqchWorkGroupMemberPkList
     * @return
     */
    @Transactional
    public int deleteQqchWorkGroupMemberByPks(List<Long> qqchWorkGroupMemberPkList) {
        return qqchWorkGroupMemberMapper.deleteQqchWorkGroupMemberByPks(qqchWorkGroupMemberPkList);
    }
}
