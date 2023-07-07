package com.hhwy.pm.qqch.group.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMapper;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMemberMapper;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

/**
 * @author han
 * @date 2023-07-06 15:23:42
 * @remark 前期策划工作小组
 */
@Service
public class QqchWorkGroupServiceImpl implements IQqchWorkGroupService {

    @Autowired
    private QqchWorkGroupMapper qqchWorkGroupMapper;

    @Autowired
    private QqchWorkGroupMemberServiceImpl qqchWorkGroupMemberService;

    @Autowired
    private QqchWorkGroupMemberMapper qqchWorkGroupMemberMapper;


    public QqchWorkGroup getQqchWorkGroup(QqchWorkGroup qqchWorkGroup) {
        return qqchWorkGroupMapper.getQqchWorkGroup(qqchWorkGroup);
    }

    public List<QqchWorkGroup> getQqchWorkGroupList(QqchWorkGroup qqchWorkGroup) {
        return qqchWorkGroupMapper.getQqchWorkGroupList(qqchWorkGroup);
    }

    /**
     * 新增工作小组
     * @param qqchWorkGroup
     * @return
     */
    @Transactional
    public int insertQqchWorkGroup(QqchWorkGroup qqchWorkGroup) {
        Long id = IdWorker.createId();
        qqchWorkGroup.setId(id);

        //新增工作小组成员
        List<QqchWorkGroupMember> qqchWorkGroupMemberList = qqchWorkGroup.getQqchWorkGroupMemberList();
        if(!CollectionUtils.isEmpty(qqchWorkGroupMemberList)){
            qqchWorkGroupMemberService.insertQqchWorkGroupMemberList(qqchWorkGroupMemberList,qqchWorkGroup);
        }

        qqchWorkGroup.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchWorkGroup.setCreateUserName(SecurityUtils.getUserName());
        qqchWorkGroup.setCreateTime(DateUtils.getNowDate());
        return qqchWorkGroupMapper.insertQqchWorkGroup(qqchWorkGroup);
    }

    /**
     * 修改工作小组
     * @param qqchWorkGroup
     * @return
     */
    @Transactional
    public int updateQqchWorkGroup(QqchWorkGroup qqchWorkGroup) {

        //修改工作小组成员
        List<QqchWorkGroupMember> qqchWorkGroupMemberList = qqchWorkGroup.getQqchWorkGroupMemberList();
        if(!CollectionUtils.isEmpty(qqchWorkGroupMemberList)){
            qqchWorkGroupMemberService.editQqchWorkGroupMemberList(qqchWorkGroupMemberList,qqchWorkGroup);
        }

        qqchWorkGroup.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchWorkGroup.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkGroupMapper.updateQqchWorkGroup(qqchWorkGroup);
    }

    /**
     * 删除工作小组
     * @param qqchWorkGroup
     * @return
     */
    @Transactional
    public int deleteQqchWorkGroup(QqchWorkGroup qqchWorkGroup) {

        //删除工作小组成员
        QqchWorkGroupMember qqchWorkGroupMember = new QqchWorkGroupMember();
        qqchWorkGroupMember.setWorkGroupId(qqchWorkGroup.getId());
        qqchWorkGroupMemberService.deleteQqchWorkGroupMember(qqchWorkGroupMember);

        qqchWorkGroup.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
        qqchWorkGroup.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkGroupMapper.deleteQqchWorkGroup(qqchWorkGroup);
    }

    @Transactional
    public int deleteQqchWorkGroupByPks(List<Long> qqchWorkGroupPkList) {
        return qqchWorkGroupMapper.deleteQqchWorkGroupByPks(qqchWorkGroupPkList);
    }

    /**
     * 根据id获取工作小组信息
     * @param id
     * @return
     */
    @Override
    public QqchWorkGroup getQqchWorkGroupById(Long id) {
        QqchWorkGroup qqchWorkGroup = new QqchWorkGroup();
        qqchWorkGroup.setId(id);
        qqchWorkGroup = qqchWorkGroupMapper.getQqchWorkGroup(qqchWorkGroup);
        return qqchWorkGroup;
    }
}
