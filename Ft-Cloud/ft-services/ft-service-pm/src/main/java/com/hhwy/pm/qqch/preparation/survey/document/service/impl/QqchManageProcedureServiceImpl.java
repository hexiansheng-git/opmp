package com.hhwy.pm.qqch.preparation.survey.document.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchManageProcedure;
import com.hhwy.pm.qqch.preparation.survey.document.domain.vo.QqchManageProcedureVo;
import com.hhwy.pm.qqch.preparation.survey.document.mapper.QqchManageProcedureMapper;
import com.hhwy.pm.qqch.preparation.survey.document.service.IQqchManageProcedureService;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.tree.TreeUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author han
 * @date 2023-07-13 11:40:23
 * @remark
 */
@Service
public class QqchManageProcedureServiceImpl implements IQqchManageProcedureService {

    @Autowired
    private QqchManageProcedureMapper qqchManageProcedureMapper;

    @Autowired
    private CommonMapper commonMapper;

    /**
     * 获取管理程序Vo
     * @return
     */
    public QqchManageProcedureVo getQqchManageProcedureVo() {
        QqchManageProcedureVo qqchManageProcedureVo = new QqchManageProcedureVo();

        BigDecimal version = commonMapper.selectMaxVersion("qqch_manage_procedure");
        qqchManageProcedureVo.setVersion(version);

        QqchManageProcedure qqchManageProcedure = new QqchManageProcedure();
        qqchManageProcedure.setVersion(version);
        List<QqchManageProcedure> qqchManageProcedureList = qqchManageProcedureMapper.getQqchManageProcedureList(qqchManageProcedure);
        List<QqchManageProcedure> treeList = ListTreeUtil.formatTree(
                qqchManageProcedureList,
                o -> o.getId() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchManageProcedure::getChildren,
                QqchManageProcedure::setChildren);
        qqchManageProcedureVo.setQqchManageProcedureList(treeList);

        return qqchManageProcedureVo;
    }

    /**
     * 保存
     * @param qqchManageProcedureVo
     * @return
     */
    @Override
    public void save(QqchManageProcedureVo qqchManageProcedureVo) {
        //删除旧数据
        QqchManageProcedure qqchManageProcedure = new QqchManageProcedure();
        qqchManageProcedure.setVersion(qqchManageProcedureVo.getVersion());
        qqchManageProcedureMapper.deleteQqchManageProcedure(qqchManageProcedure);

        //插入新数据
        this.insertQqchManageProcedureList(qqchManageProcedureVo.getQqchManageProcedureList(),qqchManageProcedure.getVersion());
    }

    /**
     * 确认
     * @param qqchManageProcedureVo
     * @return
     */
    @Override
    public void confirm(QqchManageProcedureVo qqchManageProcedureVo) {
        this.save(qqchManageProcedureVo);
        //TODO 修改确认状态
    }

    /**
     * 批量插入
     * @param qqchManageProcedureList
     * @param version
     */
    @Transactional
    public void insertQqchManageProcedureList(List<QqchManageProcedure> qqchManageProcedureList, BigDecimal version) {
        List<QqchManageProcedure> insertList = TreeUtils.splitTreeList(qqchManageProcedureList);
        for (QqchManageProcedure qqchManageProcedure : insertList) {
            qqchManageProcedure.setVersion(version);
            qqchManageProcedure.setValid(Valid.NO);
            qqchManageProcedure.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchManageProcedure.setCreateUserName(SecurityUtils.getUserName());
            qqchManageProcedure.setCreateTime(DateUtils.getNowDate());
        }
        qqchManageProcedureMapper.insertQqchManageProcedureList(insertList);
    }
}
