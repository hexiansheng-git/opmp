package com.hhwy.pm.qqch.group.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.SystemServiceApi;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroup;
import com.hhwy.pm.qqch.group.domain.QqchWorkGroupMember;
import com.hhwy.pm.qqch.group.domain.vo.WorkGroupMemberQueryVo;
import com.hhwy.pm.qqch.group.mapper.QqchWorkGroupMemberMapper;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupMemberService;
import com.hhwy.pm.qqch.group.service.IQqchWorkGroupService;
import com.hhwy.system.api.domain.SysTenant;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import io.seata.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author han
 * @date 2023-07-06 15:23:45
 * @remark 前期策划工作小组成员
 */
@Service
public class QqchWorkGroupMemberServiceImpl implements IQqchWorkGroupMemberService {

    @Autowired
    private QqchWorkGroupMemberMapper qqchWorkGroupMemberMapper;

    @Autowired
    private SystemServiceApi systemServiceApi;

    @Autowired
    private IQqchWorkGroupService qqchWorkGroupService;


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
    public void insertQqchWorkGroupMemberList(List<QqchWorkGroupMember> qqchWorkGroupMemberList, QqchWorkGroup qqchWorkGroup) {
        if(CollectionUtils.isEmpty(qqchWorkGroupMemberList)){
            return;
        }
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
        qqchWorkGroupMemberMapper.insertQqchWorkGroupMemberList(qqchWorkGroupMemberList);
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
     * 获取工作小组成员历史
     *
     * @param queryVo
     * @return
     */
    @Override
    public List<QqchWorkGroupMember> getEstablishPreliminaryPlanHistory(WorkGroupMemberQueryVo queryVo) {
        List<QqchWorkGroupMember> allMember = new ArrayList<>();

        List<QqchWorkGroupMember> resultMember = new ArrayList<>();

        //获取当前租户
        String currentTenantKey = SecurityUtils.getTenantKey();

        //切换到master
        String oldDataSource = DynamicDataSourceContextHolder.peek();
        DynamicDataSourceContextHolder.push("master");
        //获取所有租户
        List<SysTenant> tenantList = systemServiceApi.tenantList();

        try {
            //TODO 不好使
//            for (SysTenant tenant : tenantList) {
//                if(!currentTenantKey.equals(tenant.getTenantKey())){
//                    //切换租户
//                    DynamicDataSourceContextHolder.push(tenant.getTenantKey());
//                    //获取数据
//                    List<QqchWorkGroupMember> validMaxVersionWorkGroupMemberList = this.getValidMaxVersionWorkGroupMemberList(queryVo);
//                    allMember.addAll(validMaxVersionWorkGroupMemberList);
//                }
//            }

            List<QqchWorkGroupMember> validMaxVersionWorkGroupMemberList = this.getValidMaxVersionWorkGroupMemberList(queryVo);
            allMember.addAll(validMaxVersionWorkGroupMemberList);

            //选出所有不同的数据
            Map<Long,String> memberMap = new HashMap<>();
            for (QqchWorkGroupMember qqchWorkGroupMember : allMember) {
                if(qqchWorkGroupMember.getDirectorId() != null && StringUtils.isNotBlank(qqchWorkGroupMember.getDirector())){
                    memberMap.put(qqchWorkGroupMember.getDirectorId(),qqchWorkGroupMember.getDirector());
                }
            }

            //制作数组
            for (Map.Entry<Long, String> map : memberMap.entrySet()) {
                Long key = map.getKey();
                String value = map.getValue();
                QqchWorkGroupMember member = new QqchWorkGroupMember();
                member.setId(IdWorker.createId());
                member.setDirector(value);
                member.setDirectorId(key);

                List<QqchWorkGroupMember> children = new ArrayList<>();
                for (QqchWorkGroupMember qqchWorkGroupMember : allMember) {
                    if(key.equals(qqchWorkGroupMember.getDirectorId())){
                        children.add(qqchWorkGroupMember);
                    }
                }
                member.setChildren(children);
                resultMember.add(member);
            }

        }catch (Exception e){
            throw new CustomBusinessException(e.getMessage());
        }finally {
            DynamicDataSourceContextHolder.poll();
            DynamicDataSourceContextHolder.push(oldDataSource);
        }

        return resultMember;
    }

    /**
     * 获取最新生效版本的工作小组成员
     * @return
     */
    public List<QqchWorkGroupMember> getValidMaxVersionWorkGroupMemberList(WorkGroupMemberQueryVo queryVo) {
        List<QqchWorkGroupMember> workGroupMemberList = new ArrayList<>();

        //获取当前最新生效版本的工作小组
        QqchWorkGroup validMaxVersionQqchWorkGroup = qqchWorkGroupService.getValidMaxVersionQqchWorkGroup();
        if(validMaxVersionQqchWorkGroup != null){
            queryVo.setMasterId(validMaxVersionQqchWorkGroup.getId());
            workGroupMemberList = qqchWorkGroupMemberMapper.getWorkGroupMemberHistory(queryVo);
        }
        return workGroupMemberList;
    }
    @Override
    public List<QqchWorkGroupMember> getQqchWorkGroupMemberList(QqchWorkGroupMember qqchWorkGroupMember) {
        return qqchWorkGroupMemberMapper.getQqchWorkGroupMemberList(qqchWorkGroupMember);
    }
}
