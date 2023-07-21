package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchOptimizeChangeOrganization;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchOptimizeChangeOrganizationVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchOptimizeChangeOrganizationMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchOptimizeChangeOrganizationService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.ListTreeUtil;
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
     * @param version
     */
    @Override
    public QqchOptimizeChangeOrganizationVo getQqchOptimizeChangeOrganizationVo(BigDecimal version) {
        QqchOptimizeChangeOrganizationVo qqchOptimizeChangeOrganizationVo = new QqchOptimizeChangeOrganizationVo();

        version = VersionUtil.getVersion("qqch_optimize_change_organization",version);

        List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList = qqchOptimizeChangeOrganizationMapper.getQqchOptimizeChangeOrganizationList(version);
        qqchOptimizeChangeOrganizationVo.setVersion(version);
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

        String buttonMark = qqchOptimizeChangeOrganizationVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //确认 TODO 修改确认状态
        }
    }

    /**
     * 批量新增
     * @param qqchOptimizeChangeOrganizationList
     * @param version
     * @return
     */
    @Transactional
    public void insertQqchOptimizeChangeOrganizationList(List<QqchOptimizeChangeOrganization> qqchOptimizeChangeOrganizationList, BigDecimal version){
        List<QqchOptimizeChangeOrganization> insertList = ListTreeUtil.formatList(
                qqchOptimizeChangeOrganizationList,
                QqchOptimizeChangeOrganization::setId,
                QqchOptimizeChangeOrganization::setPid,
                QqchOptimizeChangeOrganization::setSort,
                QqchOptimizeChangeOrganization::getChildren,
                QqchOptimizeChangeOrganization::setChildren);
        for (QqchOptimizeChangeOrganization qqchOptimizeChangeOrganization : insertList) {
            qqchOptimizeChangeOrganization.setVersion(version);
            if(version.compareTo(BigDecimal.valueOf(1)) == 0){
                qqchOptimizeChangeOrganization.setValid(Valid.YES);
            }
            qqchOptimizeChangeOrganization.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchOptimizeChangeOrganization.setCreateUserName(SecurityUtils.getUserName());
            qqchOptimizeChangeOrganization.setCreateTime(DateUtils.getNowDate());
        }
        qqchOptimizeChangeOrganizationMapper.insertQqchOptimizeChangeOrganizationList(insertList);
    }
}
