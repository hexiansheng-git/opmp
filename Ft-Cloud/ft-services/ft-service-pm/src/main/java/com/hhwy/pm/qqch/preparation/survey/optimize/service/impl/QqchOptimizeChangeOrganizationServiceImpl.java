package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.List;
import java.util.Map;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeChangeOrganization;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeChangeOrganizationVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchOptimizeChangeOrganizationMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeChangeOrganizationService;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
        List<QqchOptimizeChangeOrganization> treeList = ListTreeUtil.formatTree(
                qqchOptimizeChangeOrganizationList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchOptimizeChangeOrganization::getChildren,
                QqchOptimizeChangeOrganization::setChildren);
        qqchOptimizeChangeOrganizationVo.setTreeList(treeList);

        //TODO 获取确认状态
        qqchOptimizeChangeOrganizationVo.setQqchModuleConfirmCase(new QqchModuleConfirmCase());

        return qqchOptimizeChangeOrganizationVo;
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
    public void editQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList){
        List<QqchOptimizeChangeOrganization> insertList = TreeUtils.splitTreeList(qqchOptimizeChangeOrganizationList);
        if(insertList.size() > 0){
            this.insertQqchOptimizeChangeOrganizationList(insertList);
        }
    }

    /**
     * 批量插入
     * @param qqchOptimizeChangeOrganizationList
     */
    public void insertQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList){
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : qqchOptimizeChangeOrganizationList) {
            qqchOptimizeChangeOrganization.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchOptimizeChangeOrganization.setCreateUserName(SecurityUtils.getUserName());
            qqchOptimizeChangeOrganization.setCreateTime(DateUtils.getNowDate());
        }
        qqchOptimizeChangeOrganizationMapper.insertQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationList);
    }

    /**
     * 批量修改
     * @param qqchOptimizeChangeOrganizationList
     */
    public void updateQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList){
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : qqchOptimizeChangeOrganizationList) {
            qqchOptimizeChangeOrganization.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchOptimizeChangeOrganization.setUpdateTime(DateUtils.getNowDate());
        }
        qqchOptimizeChangeOrganizationMapper.updateQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationList);
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
