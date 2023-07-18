package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
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

    @Autowired
    private CommonMapper commonMapper;


    /**
     * 优化变更组织策划台账
     * @return
     */
    @Override
    public QqchOptimizeChangeOrganizationVo getQqchOptimizeChangeOrganizationVo() {
        QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo = new QqchOptimizeChangeOrganizationVo();

        BigDecimal version = commonMapper.selectMaxVersion("qqch_optimize_change_organization");
        qqchOptimizeChangeOrganizationVo.setVersion(version);

        List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList = qqchOptimizeChangeOrganizationMapper.getQqchOptimizeChangeOrganizationList(version);
        //转树列表
        List<QqchOptimizeChangeOrganization> treeList = ListTreeUtil.formatTree(
                qqchOptimizeChangeOrganizationList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchOptimizeChangeOrganization::getChildren,
                QqchOptimizeChangeOrganization::setChildren);
        qqchOptimizeChangeOrganizationVo.setTreeList(treeList);

        //TODO 获取确认状态

        return qqchOptimizeChangeOrganizationVo;
    }

    /**
     * 保存
     * @param qqchOptimizeChangeOrganizationVo
     */
    @Override
    @Transactional
    public void save(QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo) {
        //删除旧数据
        QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization = new QqchOptimizeChangeOrganization();
        qqchOptimizeChangeOrganization.setVersion(qqchOptimizeChangeOrganizationVo.getVersion());
        qqchOptimizeChangeOrganizationMapper.deleteQqchOptimizeChangeOrganization(qqchOptimizeChangeOrganization);

        //插入新数据
        this.insertQqchOptimizeChangeOrganizationList(qqchOptimizeChangeOrganizationVo.getTreeList(),qqchOptimizeChangeOrganization.getVersion());
    }

    /**
     * 确认
     * @param qqchOptimizeChangeOrganizationVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo) {
        this.save(qqchOptimizeChangeOrganizationVo);

        //TODO 修改确认状态
    }

    /**
     * 批量新增
     * @param qqchOptimizeChangeOrganizationList
     * @param version
     * @return
     */
    @Transactional
    public void insertQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList, BigDecimal version){
        List<QqchOptimizeChangeOrganization> insertList = TreeUtils.splitTreeList(qqchOptimizeChangeOrganizationList);
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : insertList) {
            qqchOptimizeChangeOrganization.setVersion(version);
            qqchOptimizeChangeOrganization.setValid(Valid.YES);
            qqchOptimizeChangeOrganization.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchOptimizeChangeOrganization.setCreateUserName(SecurityUtils.getUserName());
            qqchOptimizeChangeOrganization.setCreateTime(DateUtils.getNowDate());
        }
        qqchOptimizeChangeOrganizationMapper.insertQqchOptimizeChangeOrganizationList(insertList);
    }
}
