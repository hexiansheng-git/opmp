package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeChangeOrganization;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeChangeOrganizationVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchOptimizeChangeOrganizationMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeChangeOrganizationService;
import com.hhwy.utils.tree.ListTreeUtil;
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


    /**
     * 优化变更组织策划台账
     * @return
     */
    @Override
    public QqchOptimizeChangeOrganizationVo getQqchOptimizeChangeOrganizationVo() {
        QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo = new QqchOptimizeChangeOrganizationVo();

        List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList = qqchOptimizeChangeOrganizationMapper.getQqchOptimizeChangeOrganizationList(new QqchOptimizeChangeOrganization());

        //转树列表
        List<QqchOptimizeChangeOrganization> treeList = ListTreeUtil.formatTree(qqchOptimizeChangeOrganizationList, o -> o.getPid() == null,
            (r, n) -> r.getId().equals(n.getPid()), QqchOptimizeChangeOrganization::getChildren, QqchOptimizeChangeOrganization::setChildren);
        qqchOptimizeChangeOrganizationVo.setTreeList(treeList);

        //TODO 获取确认状态
        qqchOptimizeChangeOrganizationVo.setQqchModuleConfirmCase(new QqchModuleConfirmCase());

        return qqchOptimizeChangeOrganizationVo;
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

    /**
     * 保存
     * @param qqchOptimizeChangeOrganizationVo
     */
    @Override
    public void save(QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo) {
        this.editQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationVo.getTreeList());
    }

    /**
     * 确认
     * @param qqchOptimizeChangeOrganizationVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo) {
        this.editQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationVo.getTreeList());

        //TODO 修改确认状态
    }

    /**
     * 批量编辑（新增和修改）
     * @param qqchOptimizeChangeOrganizationList
     * @return
     */
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

    /**
     * 批量插入
     * @param qqchOptimizeChangeOrganizationList
     * @return
     */
    @Transactional
    public int insertQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList) {
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : qqchOptimizeChangeOrganizationList) {
            qqchOptimizeChangeOrganization.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchOptimizeChangeOrganization.setCreateUserName(SecurityUtils.getUserName());
            qqchOptimizeChangeOrganization.setCreateTime(DateUtils.getNowDate());
        }
        return qqchOptimizeChangeOrganizationMapper.insertQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationList);
    }

    /**
     * 批量修改
     * @param qqchOptimizeChangeOrganizationList
     * @return
     */
    @Transactional
    public int updateQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList) {
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : qqchOptimizeChangeOrganizationList) {
            qqchOptimizeChangeOrganization.setUpdateUser(SecurityUtils.getUserName());
            qqchOptimizeChangeOrganization.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchOptimizeChangeOrganizationMapper.updateQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationList);
    }

    /**
     * 批量删除
     * @param qqchOptimizeChangeOrganizationPkList
     * @return
     */
    @Transactional
    public int deleteQqchOptimizeChangeOrganizationByPks(List<Long> qqchOptimizeChangeOrganizationPkList) {
        return qqchOptimizeChangeOrganizationMapper.deleteQqchOptimizeChangeOrganizationByPks(qqchOptimizeChangeOrganizationPkList);
    }
}
