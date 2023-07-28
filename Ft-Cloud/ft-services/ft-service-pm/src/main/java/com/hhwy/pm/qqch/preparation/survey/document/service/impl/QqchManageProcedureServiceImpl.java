package com.hhwy.pm.qqch.preparation.survey.document.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchManageProcedure;
import com.hhwy.pm.qqch.preparation.survey.document.domain.vo.QqchManageProcedureVo;
import com.hhwy.pm.qqch.preparation.survey.document.mapper.QqchManageProcedureMapper;
import com.hhwy.pm.qqch.preparation.survey.document.service.IQqchManageProcedureService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.tree.ListTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    /**
     * 获取管理程序Vo
     * @return
     * @param version
     */
    public QqchManageProcedureVo getQqchManageProcedureVo(BigDecimal version) {
        QqchManageProcedureVo qqchManageProcedureVo = new QqchManageProcedureVo();

        version = VersionUtil.getVersion("qqch_manage_procedure",version);
        QqchManageProcedure qqchManageProcedure = new QqchManageProcedure();
        qqchManageProcedure.setVersion(version);
        List<QqchManageProcedure> qqchManageProcedureList = qqchManageProcedureMapper.getQqchManageProcedureList(qqchManageProcedure);
        //转换树列表
        List<QqchManageProcedure> treeList = ListTreeUtil.formatTree(
                qqchManageProcedureList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchManageProcedure::getChildren,
                QqchManageProcedure::setChildren);
        qqchManageProcedureVo.setQqchManageProcedureList(treeList);

        qqchManageProcedureVo.setVersion(version);
        qqchManageProcedureVo.setStageIdentity(qqchReviewService.getStage());
        return qqchManageProcedureVo;
    }

    /**
     * 保存
     * @param qqchManageProcedureVo
     * @return
     */
    @Override
    @Transactional
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
    @Transactional
    public void confirm(QqchManageProcedureVo qqchManageProcedureVo) {
        this.save(qqchManageProcedureVo);

        String buttonMark = qqchManageProcedureVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchManageProcedureVo.getMenuId();
            String stageIdentity = qqchManageProcedureVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 批量插入
     * @param qqchManageProcedureList
     * @param version
     */
    @Transactional
    public void insertQqchManageProcedureList(List<QqchManageProcedure> qqchManageProcedureList, BigDecimal version) {
        List<QqchManageProcedure> insertList = ListTreeUtil.formatList(
                qqchManageProcedureList,
                QqchManageProcedure::setId,
                QqchManageProcedure::setPid,
                QqchManageProcedure::setSort,
                QqchManageProcedure::getChildren,
                QqchManageProcedure::setChildren);
        for (QqchManageProcedure qqchManageProcedure : insertList) {
            qqchManageProcedure.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchManageProcedure.setValid(Valid.YES);
            }
            qqchManageProcedure.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchManageProcedure.setCreateUserName(SecurityUtils.getUserName());
            qqchManageProcedure.setCreateTime(DateUtils.getNowDate());
        }
        qqchManageProcedureMapper.insertQqchManageProcedureList(insertList);
    }
}
