package com.hhwy.pm.qqch.preparation.contractPlan.masterContract.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.QqchSpecialCondition;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.domain.vo.QqchSpecialConditionVo;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.mapper.QqchSpecialConditionMapper;
import com.hhwy.pm.qqch.preparation.contractPlan.masterContract.service.IQqchSpecialConditionService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.DataCheckUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:48
 * @remark 专用条件梳理
 */
@Service
public class QqchSpecialConditionServiceImpl implements IQqchSpecialConditionService {

    @Autowired
    private QqchSpecialConditionMapper qqchSpecialConditionMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;

    @Autowired
    private CommonMapper commonMapper;


    public QqchSpecialCondition getQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition) {
        return qqchSpecialConditionMapper.getQqchSpecialCondition(qqchSpecialCondition);
    }

    public List<QqchSpecialCondition> getQqchSpecialConditionList(QqchSpecialCondition qqchSpecialCondition) {
        return qqchSpecialConditionMapper.getQqchSpecialConditionList(qqchSpecialCondition);
    }

    @Transactional
    public int insertQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition) {
        qqchSpecialCondition.setId(IdWorker.createId());
        qqchSpecialCondition.setCreateUser(SecurityUtils.getUserName());
        qqchSpecialCondition.setCreateTime(DateUtils.getNowDate());
        return qqchSpecialConditionMapper.insertQqchSpecialCondition(qqchSpecialCondition);
    }

    @Transactional
    public void insertQqchSpecialConditionList(List<QqchSpecialCondition> qqchSpecialConditionList, BigDecimal version) {
        //删除旧数据
        QqchSpecialCondition qqchSpecialCondition = new QqchSpecialCondition();
        qqchSpecialCondition.setVersion(version);
        qqchSpecialConditionMapper.deleteQqchSpecialCondition(qqchSpecialCondition);

        if(CollectionUtils.isEmpty(qqchSpecialConditionList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchSpecialCondition specialCondition : qqchSpecialConditionList) {
            specialCondition.setValid(valid);
            specialCondition.setVersion(version);
            specialCondition.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            specialCondition.setCreateUserName(SecurityUtils.getUserName());
            specialCondition.setCreateTime(DateUtils.getNowDate());
        }
        qqchSpecialConditionMapper.insertQqchSpecialConditionList(qqchSpecialConditionList);
    }

    @Transactional
    public int updateQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition) {
        qqchSpecialCondition.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialCondition.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialConditionMapper.updateQqchSpecialCondition(qqchSpecialCondition);
    }

    @Transactional
    public int updateQqchSpecialConditionList(List<QqchSpecialCondition> qqchSpecialConditionList) {
        for (QqchSpecialCondition qqchSpecialCondition : qqchSpecialConditionList) {
            qqchSpecialCondition.setUpdateUser(SecurityUtils.getUserName());
            qqchSpecialCondition.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSpecialConditionMapper.updateQqchSpecialConditionList(qqchSpecialConditionList);
    }

    @Transactional
    public int deleteQqchSpecialCondition(QqchSpecialCondition qqchSpecialCondition) {
        qqchSpecialCondition.setUpdateUser(SecurityUtils.getUserName());
        qqchSpecialCondition.setUpdateTime(DateUtils.getNowDate());
        return qqchSpecialConditionMapper.deleteQqchSpecialCondition(qqchSpecialCondition);
    }

    @Transactional
    public int deleteQqchSpecialConditionByPks(List<Long> qqchSpecialConditionPkList) {
        return qqchSpecialConditionMapper.deleteQqchSpecialConditionByPks(qqchSpecialConditionPkList);
    }

    /**
     * 获取专用条件梳理Vo
     * @param qqchSpecialCondition
     * @return
     */
    @Override
    public QqchSpecialConditionVo getQqchSpecialConditionVo(QqchSpecialCondition qqchSpecialCondition) {
        QqchSpecialConditionVo qqchSpecialConditionVo = new QqchSpecialConditionVo();

        BigDecimal version = qqchSpecialCondition.getVersion();
        version = VersionUtil.getVersion("qqch_special_condition",version);

        qqchSpecialCondition.setVersion(version);
        List<QqchSpecialCondition> qqchSpecialConditionList = qqchSpecialConditionMapper.getQqchSpecialConditionList(qqchSpecialCondition);

        //转树列表
        List<QqchSpecialCondition> treeList = ListTreeUtil.formatTree(
                qqchSpecialConditionList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchSpecialCondition::getChildren,
                QqchSpecialCondition::setChildren);

        qqchSpecialConditionVo.setVersion(version);
        qqchSpecialConditionVo.setStageIdentity(qqchReviewService.getStage());
        qqchSpecialConditionVo.setList(treeList);
        return qqchSpecialConditionVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchSpecialConditionVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchSpecialConditionVo qqchSpecialConditionVo) {
        String buttonMark = qqchSpecialConditionVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchSpecialConditionVo.getVersion();
        List<QqchSpecialCondition> qqchSpecialConditionList = qqchSpecialConditionVo.getList();

        List<QqchSpecialCondition> tileList = ListTreeUtil.formatList(
                qqchSpecialConditionList,
                QqchSpecialCondition::setId,
                QqchSpecialCondition::setPid,
                QqchSpecialCondition::setSort,
                QqchSpecialCondition::setLeaf,
                QqchSpecialCondition::getChildren,
                QqchSpecialCondition::setChildren);

        //校验唯一
        DataCheckUtil.checkSingle(tileList,QqchSpecialCondition::getSpecialCode);

        //校验非空
        if(!ButtonMark.SAVE.equals(buttonMark)){
            JyDetailsUtil.jyDetails(tileList, QqchSpecialCondition::getLeaf, ValidationGroups.Save.class);
        }

        //处理数据
        this.insertQqchSpecialConditionList(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchSpecialConditionVo.getMenuId();
            String stageIdentity = qqchSpecialConditionVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 10.1财务相关主合同条款 弹窗
     * @param qqchSpecialCondition
     * @return
     */
    @Override
    public List<QqchSpecialCondition> popUpWindows(QqchSpecialCondition qqchSpecialCondition) {
        List<QqchSpecialCondition> resultList;
        //获取最大有效版本
        BigDecimal version = commonMapper.selectMaxVersion("qqch_special_condition");

        /*查询版本全量数据*/
        QqchSpecialCondition query = new QqchSpecialCondition();
        query.setVersion(version);
        List<QqchSpecialCondition> allList = qqchSpecialConditionMapper.getQqchSpecialConditionList(query);

        /*条件名称*/
        String name = qqchSpecialCondition.getName();
        /*条件内容*/
        String content = qqchSpecialCondition.getContent();
        if(StringUtils.isNotBlank(name) || StringUtils.isNotBlank(content)){
            query.setName(name);
            query.setContent(content);
            List<QqchSpecialCondition> subList = qqchSpecialConditionMapper.getQqchSpecialConditionList(query);
            resultList = ListTreeUtil.getUpListBySublistToTree(
                    subList,
                    allList,
                    QqchSpecialCondition::getId,
                    QqchSpecialCondition::getPid,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchSpecialCondition::getChildren,
                    QqchSpecialCondition::setChildren);
        }else {
            resultList = ListTreeUtil.formatTree(
                    allList,
                    o -> o.getPid() == null,
                    (r, n) -> r.getId().equals(n.getPid()),
                    QqchSpecialCondition::getChildren,
                    QqchSpecialCondition::setChildren);
        }
        return resultList;
    }
}
