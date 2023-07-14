package com.hhwy.pm.qqch.group.service.impl;

import java.math.BigDecimal;
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


    /**
     * 台账（历史记录）
     * @param qqchWorkGroup
     * @return
     */
    public List<QqchWorkGroup> getQqchWorkGroupList(QqchWorkGroup qqchWorkGroup) {
        return qqchWorkGroupMapper.getQqchWorkGroupList(qqchWorkGroup);
    }

    /**
     * 调整
     * @param id
     * @return
     */
    @Override
    public QqchWorkGroup adjustQqchWorkGroup(Long id) {
        QqchWorkGroup qqchWorkGroup = new QqchWorkGroup();
        if(id == null){
            //第一次新增
            qqchWorkGroup.setVersion(BigDecimal.valueOf(1.0));
            return qqchWorkGroup;
        }

        /*
        调整
         */
        qqchWorkGroup.setId(id);
        //获取调整数据
        qqchWorkGroup = qqchWorkGroupMapper.getQqchWorkGroup(qqchWorkGroup);
        qqchWorkGroup.setId(null);
        BigDecimal version = qqchWorkGroup.getVersion();
        version = version.add(BigDecimal.valueOf(1));
        qqchWorkGroup.setVersion(version);

        return qqchWorkGroup;
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

        //获取工作小组成员
        QqchWorkGroupMember qqchWorkGroupMember = new QqchWorkGroupMember();
        qqchWorkGroupMember.setWorkGroupId(id);
        List<QqchWorkGroupMember> qqchWorkGroupMemberList = qqchWorkGroupMemberMapper.getQqchWorkGroupMemberList(qqchWorkGroupMember);

        qqchWorkGroup.setQqchWorkGroupMemberList(qqchWorkGroupMemberList);

        return qqchWorkGroup;
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
}
