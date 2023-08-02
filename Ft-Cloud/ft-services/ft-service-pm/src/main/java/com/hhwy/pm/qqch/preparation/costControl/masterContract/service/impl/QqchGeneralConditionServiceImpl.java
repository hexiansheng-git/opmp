package com.hhwy.pm.qqch.preparation.costControl.masterContract.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchGeneralCondition;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.vo.QqchGeneralConditionVo;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.mapper.QqchGeneralConditionMapper;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.service.IQqchGeneralConditionService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:46
 * @remark 通用条件梳理
 */
@Service
public class QqchGeneralConditionServiceImpl implements IQqchGeneralConditionService {

    @Autowired
    private QqchGeneralConditionMapper qqchGeneralConditionMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchGeneralCondition getQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition) {
        return qqchGeneralConditionMapper.getQqchGeneralCondition(qqchGeneralCondition);
    }

    public List<QqchGeneralCondition> getQqchGeneralConditionList(QqchGeneralCondition qqchGeneralCondition) {
        return qqchGeneralConditionMapper.getQqchGeneralConditionList(qqchGeneralCondition);
    }

    @Transactional
    public int insertQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition) {
        qqchGeneralCondition.setId(IdWorker.createId());
        qqchGeneralCondition.setCreateUser(SecurityUtils.getUserName());
        qqchGeneralCondition.setCreateTime(DateUtils.getNowDate());
        return qqchGeneralConditionMapper.insertQqchGeneralCondition(qqchGeneralCondition);
    }

    @Transactional
    public void insertQqchGeneralConditionList(List<QqchGeneralCondition> qqchGeneralConditionList, BigDecimal version) {
        //删除旧数据
        QqchGeneralCondition qqchGeneralCondition = new QqchGeneralCondition();
        qqchGeneralCondition.setVersion(version);
        qqchGeneralConditionMapper.deleteQqchGeneralCondition(qqchGeneralCondition);

        if(CollectionUtils.isEmpty(qqchGeneralConditionList)){
            return;
        }

        List<QqchGeneralCondition> insertList = ListTreeUtil.formatList(
                qqchGeneralConditionList,
                QqchGeneralCondition::setId,
                QqchGeneralCondition::setPid,
                QqchGeneralCondition::setSort,
                QqchGeneralCondition::setLeaf,
                QqchGeneralCondition::getChildren,
                QqchGeneralCondition::setChildren);

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchGeneralCondition generalCondition : insertList) {
            generalCondition.setValid(valid);
            generalCondition.setVersion(version);
            generalCondition.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            generalCondition.setCreateUserName(SecurityUtils.getUserName());
            generalCondition.setCreateTime(DateUtils.getNowDate());
        }
        qqchGeneralConditionMapper.insertQqchGeneralConditionList(insertList);
    }

    @Transactional
    public int updateQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition) {
        qqchGeneralCondition.setUpdateUser(SecurityUtils.getUserName());
        qqchGeneralCondition.setUpdateTime(DateUtils.getNowDate());
        return qqchGeneralConditionMapper.updateQqchGeneralCondition(qqchGeneralCondition);
    }

    @Transactional
    public int updateQqchGeneralConditionList(List<QqchGeneralCondition> qqchGeneralConditionList) {
        for (QqchGeneralCondition qqchGeneralCondition : qqchGeneralConditionList) {
            qqchGeneralCondition.setUpdateUser(SecurityUtils.getUserName());
            qqchGeneralCondition.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchGeneralConditionMapper.updateQqchGeneralConditionList(qqchGeneralConditionList);
    }

    @Transactional
    public int deleteQqchGeneralCondition(QqchGeneralCondition qqchGeneralCondition) {
        qqchGeneralCondition.setUpdateUser(SecurityUtils.getUserName());
        qqchGeneralCondition.setUpdateTime(DateUtils.getNowDate());
        return qqchGeneralConditionMapper.deleteQqchGeneralCondition(qqchGeneralCondition);
    }

    @Transactional
    public int deleteQqchGeneralConditionByPks(List<Long> qqchGeneralConditionPkList) {
        return qqchGeneralConditionMapper.deleteQqchGeneralConditionByPks(qqchGeneralConditionPkList);
    }

    /**
     * 获取通用条件梳理Vo
     * @param qqchGeneralCondition
     * @return
     */
    @Override
    public QqchGeneralConditionVo getQqchGeneralConditionVo(QqchGeneralCondition qqchGeneralCondition) {
        QqchGeneralConditionVo qqchGeneralConditionVo = new QqchGeneralConditionVo();

        BigDecimal version = qqchGeneralCondition.getVersion();
        version = VersionUtil.getVersion("qqch_general_condition",version);

        qqchGeneralCondition.setVersion(version);
        List<QqchGeneralCondition> qqchGeneralConditionList = qqchGeneralConditionMapper.getQqchGeneralConditionList(qqchGeneralCondition);

        //转树列表
        List<QqchGeneralCondition> treeList = ListTreeUtil.formatTree(
                qqchGeneralConditionList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchGeneralCondition::getChildren,
                QqchGeneralCondition::setChildren);

        qqchGeneralConditionVo.setVersion(version);
        qqchGeneralConditionVo.setStageIdentity(qqchReviewService.getStage());
        qqchGeneralConditionVo.setQqchGeneralConditionList(treeList);
        return qqchGeneralConditionVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchGeneralConditionVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchGeneralConditionVo qqchGeneralConditionVo) {
        String buttonMark = qqchGeneralConditionVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchGeneralConditionVo.getVersion();
        List<QqchGeneralCondition> qqchGeneralConditionList = qqchGeneralConditionVo.getQqchGeneralConditionList();

        //处理数据
        this.insertQqchGeneralConditionList(qqchGeneralConditionList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchGeneralConditionVo.getMenuId();
            String stageIdentity = qqchGeneralConditionVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
