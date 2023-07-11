package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeChangeOrganization;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchOptimizeChangeOrganizationMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeChangeOrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

/**
 * @author han
 * @date 2023-07-07 18:35:50
 * @remark 优化变更组织策划
 */
@Service
public class QqchOptimizeChangeOrganizationServiceImpl implements IQqchOptimizeChangeOrganizationService {

    @Autowired
    private QqchOptimizeChangeOrganizationMapper qqchOptimizeChangeOrganizationMapper;


    public QqchOptimizeChangeOrganization getQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization) {
        return qqchOptimizeChangeOrganizationMapper.getQqchOptimizeChangeOrganization(qqchOptimizeChangeOrganization);
    }

    /**
     * 优化变更组织策划台账
     * @return
     */
    @Override
    public List<QqchOptimizeChangeOrganization> getQqchOptimizeChangeOrganizationTreeList() {
        List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList = qqchOptimizeChangeOrganizationMapper.getQqchOptimizeChangeOrganizationList(new QqchOptimizeChangeOrganization());
        return this.assembleTreeList(qqchOptimizeChangeOrganizationList);
    }

    /**
     * 组装树列表
     * @param qqchOptimizeChangeOrganizationList
     * @return
     */
    public List<QqchOptimizeChangeOrganization> assembleTreeList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList){
        List<QqchOptimizeChangeOrganization> treeList = new ArrayList<>();
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : qqchOptimizeChangeOrganizationList) {
            Long pid = qqchOptimizeChangeOrganization.getPid();
            if(pid == null || pid == 0){
                this.getChildren(qqchOptimizeChangeOrganization,qqchOptimizeChangeOrganizationList);
                treeList.add(qqchOptimizeChangeOrganization);
            }
        }
        return treeList;
    }

    /**
     * 获取子集
     * @param root
     * @param qqchOptimizeChangeOrganizationList
     */
    private void getChildren(QqchOptimizeChangeOrganization root, List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList) {
        List<QqchOptimizeChangeOrganization> children = new ArrayList<>();
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : qqchOptimizeChangeOrganizationList) {
            if (qqchOptimizeChangeOrganization.getPid() != null && qqchOptimizeChangeOrganization.getPid().equals(root.getId())) {
                getChildren(qqchOptimizeChangeOrganization, qqchOptimizeChangeOrganizationList);
                children.add(qqchOptimizeChangeOrganization);
            }
        }
        root.setChildren(children);
    }

    public List<QqchOptimizeChangeOrganization> getQqchOptimizeChangeOrganizationList(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization) {
        return qqchOptimizeChangeOrganizationMapper.getQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganization);
    }

    @Transactional
    public int insertQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization) {
        qqchOptimizeChangeOrganization.setId(IdWorker.createId());
        qqchOptimizeChangeOrganization.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
        qqchOptimizeChangeOrganization.setCreateUserName(SecurityUtils.getUserName());
        qqchOptimizeChangeOrganization.setCreateTime(DateUtils.getNowDate());
        return qqchOptimizeChangeOrganizationMapper.insertQqchOptimizeChangeOrganization(qqchOptimizeChangeOrganization);
    }

    @Transactional
    public int insertQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList) {
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : qqchOptimizeChangeOrganizationList) {
            qqchOptimizeChangeOrganization.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchOptimizeChangeOrganization.setCreateUserName(SecurityUtils.getUserName());
            qqchOptimizeChangeOrganization.setCreateTime(DateUtils.getNowDate());
        }
        return qqchOptimizeChangeOrganizationMapper.insertQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationList);
    }

    @Transactional
    public int updateQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization) {
        qqchOptimizeChangeOrganization.setUpdateUser(SecurityUtils.getUserName());
        qqchOptimizeChangeOrganization.setUpdateTime(DateUtils.getNowDate());
        return qqchOptimizeChangeOrganizationMapper.updateQqchOptimizeChangeOrganization(qqchOptimizeChangeOrganization);
    }

    @Transactional
    public int updateQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList) {
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : qqchOptimizeChangeOrganizationList) {
            qqchOptimizeChangeOrganization.setUpdateUser(SecurityUtils.getUserName());
            qqchOptimizeChangeOrganization.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchOptimizeChangeOrganizationMapper.updateQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationList);
    }

    @Transactional
    public int deleteQqchOptimizeChangeOrganization(QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization) {
        qqchOptimizeChangeOrganization.setUpdateUser(SecurityUtils.getUserName());
        qqchOptimizeChangeOrganization.setUpdateTime(DateUtils.getNowDate());
        return qqchOptimizeChangeOrganizationMapper.deleteQqchOptimizeChangeOrganization(qqchOptimizeChangeOrganization);
    }

    @Transactional
    public int deleteQqchOptimizeChangeOrganizationByPks(List<Long> qqchOptimizeChangeOrganizationPkList) {
        return qqchOptimizeChangeOrganizationMapper.deleteQqchOptimizeChangeOrganizationByPks(qqchOptimizeChangeOrganizationPkList);
    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchOptimizeChangeOrganizationList
     * @return
     */
    @Override
    @Transactional
    public int editQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList){
        List<QqchOptimizeChangeOrganization> insertList = new ArrayList<>();
        List<QqchOptimizeChangeOrganization> updateList = new ArrayList<>();
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : qqchOptimizeChangeOrganizationList) {
            this.maintainSubset(qqchOptimizeChangeOrganization,insertList,updateList);
        }
        if(insertList.size() > 0){
            this.insertQqchOptimizeChangeOrganizationList(insertList);
        }
        if(updateList.size() > 0){
            this.updateQqchOptimizeChangeOrganizationList(updateList);
        }
        return 1;
    }

    /**
     * 维护子集
     * @param root
     */
    public void maintainSubset(QqchOptimizeChangeOrganization root, List<QqchOptimizeChangeOrganization> insertList, List<QqchOptimizeChangeOrganization> updateList){
        Long id = root.getId();
        if(id == null){
            id = IdWorker.createId();
            root.setId(id);
            insertList.add(root);
        }else {
            updateList.add(root);
        }
        List<QqchOptimizeChangeOrganization> children = root.getChildren();
        if(!CollectionUtils.isEmpty(children)){
            for (QqchOptimizeChangeOrganization child : children) {
                child.setPid(id);
                this.maintainSubset(child,insertList,updateList);
            }
        }
    }

}
