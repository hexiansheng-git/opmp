package com.hhwy.pm.qqch.group.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMemberMapper;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupMemberService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

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

    @Transactional
    public int insertQqchWorkGroupMember(QqchWorkGroupMember qqchWorkGroupMember) {
        qqchWorkGroupMember.setId(IdWorker.createId());
        qqchWorkGroupMember.setCreateUser(SecurityUtils.getUserName());
        qqchWorkGroupMember.setCreateTime(DateUtils.getNowDate());
        return qqchWorkGroupMemberMapper.insertQqchWorkGroupMember(qqchWorkGroupMember);
    }

    /**
     * 编辑数据
     * @param qqchWorkGroupMemberList
     * @param qqchWorkGroup
     */
    public void editQqchWorkGroupMemberList(List<QqchWorkGroupMember> qqchWorkGroupMemberList, QqchWorkGroup qqchWorkGroup) {
        List<QqchWorkGroupMember> insertList = new ArrayList<>();
        List<QqchWorkGroupMember> updateList = new ArrayList<>();
        for (QqchWorkGroupMember qqchWorkGroupMember : qqchWorkGroupMemberList) {
            Long id = qqchWorkGroupMember.getId();
            if(id == null){
                insertList.add(qqchWorkGroupMember);
            }else{
                updateList.add(qqchWorkGroupMember);
            }
        }
        if(insertList.size() > 0){
            this.insertQqchWorkGroupMemberList(insertList, qqchWorkGroup);
        }
        if(updateList.size() > 0){
            this.updateQqchWorkGroupMemberList(updateList);
        }
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

    @Transactional
    public int updateQqchWorkGroupMember(QqchWorkGroupMember qqchWorkGroupMember) {
        qqchWorkGroupMember.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkGroupMember.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkGroupMemberMapper.updateQqchWorkGroupMember(qqchWorkGroupMember);
    }

    /**
     * 批量修改工作小组成员
     * @param qqchWorkGroupMemberList
     * @return
     */
    @Transactional
    public int updateQqchWorkGroupMemberList(List<QqchWorkGroupMember> qqchWorkGroupMemberList) {
        for (QqchWorkGroupMember qqchWorkGroupMember : qqchWorkGroupMemberList) {
            qqchWorkGroupMember.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchWorkGroupMember.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchWorkGroupMemberMapper.updateQqchWorkGroupMemberList(qqchWorkGroupMemberList);
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
