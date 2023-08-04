package com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.QqchKeyPointContractClause;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.QqchSecondManageKeyPoint;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.vo.QqchSecondManageKeyPointVo;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.domain.vo.SecondManageKeyPointPlanVo;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.mapper.QqchKeyPointContractClauseMapper;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.mapper.QqchSecondManageKeyPointMapper;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.service.IQqchKeyPointContractClauseService;
import com.hhwy.pm.qqch.preparation.costControl.secondManagePlan.service.IQqchSecondManageKeyPointService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.ListTreeUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2023-08-04 10:47:11
 * @remark
 */
@Service
public class QqchSecondManageKeyPointServiceImpl implements IQqchSecondManageKeyPointService {

    @Autowired
    private QqchSecondManageKeyPointMapper qqchSecondManageKeyPointMapper;

    @Autowired
    private IQqchKeyPointContractClauseService qqchKeyPointContractClauseService;

    @Autowired
    private QqchKeyPointContractClauseMapper qqchKeyPointContractClauseMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchSecondManageKeyPoint getQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint) {
        return qqchSecondManageKeyPointMapper.getQqchSecondManageKeyPoint(qqchSecondManageKeyPoint);
    }

    public List<QqchSecondManageKeyPoint> getQqchSecondManageKeyPointList(QqchSecondManageKeyPoint qqchSecondManageKeyPoint) {
        return qqchSecondManageKeyPointMapper.getQqchSecondManageKeyPointList(qqchSecondManageKeyPoint);
    }

    @Transactional
    public int insertQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint) {
        qqchSecondManageKeyPoint.setId(IdWorker.createId());
        qqchSecondManageKeyPoint.setCreateUser(SecurityUtils.getUserName());
        qqchSecondManageKeyPoint.setCreateTime(DateUtils.getNowDate());
        return qqchSecondManageKeyPointMapper.insertQqchSecondManageKeyPoint(qqchSecondManageKeyPoint);
    }

    @Transactional
    public int insertQqchSecondManageKeyPointList(List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList) {
        for (QqchSecondManageKeyPoint qqchSecondManageKeyPoint : qqchSecondManageKeyPointList) {
            qqchSecondManageKeyPoint.setId(IdWorker.createId());
            qqchSecondManageKeyPoint.setCreateUser(SecurityUtils.getUserName());
            qqchSecondManageKeyPoint.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSecondManageKeyPointMapper.insertQqchSecondManageKeyPointList(qqchSecondManageKeyPointList);
    }

    @Transactional
    public int updateQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint) {
        qqchSecondManageKeyPoint.setUpdateUser(SecurityUtils.getUserName());
        qqchSecondManageKeyPoint.setUpdateTime(DateUtils.getNowDate());
        return qqchSecondManageKeyPointMapper.updateQqchSecondManageKeyPoint(qqchSecondManageKeyPoint);
    }

    @Transactional
    public int updateQqchSecondManageKeyPointList(List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList) {
        for (QqchSecondManageKeyPoint qqchSecondManageKeyPoint : qqchSecondManageKeyPointList) {
            qqchSecondManageKeyPoint.setUpdateUser(SecurityUtils.getUserName());
            qqchSecondManageKeyPoint.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSecondManageKeyPointMapper.updateQqchSecondManageKeyPointList(qqchSecondManageKeyPointList);
    }

    @Transactional
    public int deleteQqchSecondManageKeyPoint(QqchSecondManageKeyPoint qqchSecondManageKeyPoint) {
        qqchSecondManageKeyPoint.setUpdateUser(SecurityUtils.getUserName());
        qqchSecondManageKeyPoint.setUpdateTime(DateUtils.getNowDate());
        return qqchSecondManageKeyPointMapper.deleteQqchSecondManageKeyPoint(qqchSecondManageKeyPoint);
    }

    @Transactional
    public int deleteQqchSecondManageKeyPointByPks(List<Long> qqchSecondManageKeyPointPkList) {
        return qqchSecondManageKeyPointMapper.deleteQqchSecondManageKeyPointByPks(qqchSecondManageKeyPointPkList);
    }

    /**
     * 获取普通要点策划/变更策划/索赔策划
     * @param qqchSecondManageKeyPoint
     * @return
     */
    @Override
    public List<SecondManageKeyPointPlanVo> getSecondManageKeyPointPlanVo(QqchSecondManageKeyPoint qqchSecondManageKeyPoint) {
        //获取要点类型
        String keyPointType = qqchSecondManageKeyPoint.getKeyPointType();
        CommonAssert.notBlank(keyPointType,"要点类型不能为空！");

        BigDecimal version = qqchSecondManageKeyPoint.getVersion();
        version = VersionUtil.getVersion("qqch_second_manage_key_point",version);

        List<SecondManageKeyPointPlanVo> resultList = new ArrayList<>();

        qqchSecondManageKeyPoint.setKeyPointType(keyPointType);
        qqchSecondManageKeyPoint.setVersion(version);
        //获取二次经营要点识别数据
        List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList = qqchSecondManageKeyPointMapper.getQqchSecondManageKeyPointList(qqchSecondManageKeyPoint);

        if(CollectionUtils.isEmpty(qqchSecondManageKeyPointList)){
            return resultList;
        }

        for (QqchSecondManageKeyPoint secondManageKeyPoint : qqchSecondManageKeyPointList) {
            SecondManageKeyPointPlanVo secondManageKeyPointPlanVo = new SecondManageKeyPointPlanVo();
            secondManageKeyPointPlanVo.setId(secondManageKeyPointPlanVo.getId());
            secondManageKeyPointPlanVo.setPid(secondManageKeyPoint.getPid());
            secondManageKeyPointPlanVo.setOptimizedDirection(secondManageKeyPoint.getOptimizedDirection());
            secondManageKeyPointPlanVo.setContentDescription(secondManageKeyPoint.getContentDescription());
            secondManageKeyPointPlanVo.setContractBasis(secondManageKeyPointPlanVo.getContractBasis());
            secondManageKeyPointPlanVo.setRemark(secondManageKeyPoint.getRemark());
            resultList.add(secondManageKeyPointPlanVo);
        }

        //获取二次经营要点识别关联合同条款（子表数据）
        QqchKeyPointContractClause qqchKeyPointContractClause = new QqchKeyPointContractClause();
        qqchKeyPointContractClause.setVersion(version);
        qqchKeyPointContractClause.setKeyPointType(keyPointType);
        List<QqchKeyPointContractClause> qqchKeyPointContractClauseList = qqchKeyPointContractClauseMapper.getQqchKeyPointContractClauseList(qqchKeyPointContractClause);

        for (SecondManageKeyPointPlanVo secondManageKeyPointPlanVo : resultList) {
            Long masterId = secondManageKeyPointPlanVo.getId();
            StringBuilder contractRight = new StringBuilder();
            StringBuilder triggerCondition = new StringBuilder();

            for (QqchKeyPointContractClause keyPointContractClause : qqchKeyPointContractClauseList) {
                if(masterId.equals(keyPointContractClause.getMasterId())){
                    contractRight.append(keyPointContractClause.getClauseContent());
                    triggerCondition.append(keyPointContractClause.getTriggerCondition());
                }
            }
            secondManageKeyPointPlanVo.setContractRight(contractRight.toString());
            secondManageKeyPointPlanVo.setTriggerCondition(triggerCondition.toString());
        }

        //转树列表
        resultList = ListTreeUtil.formatTree(
                resultList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                SecondManageKeyPointPlanVo::getChildren,
                SecondManageKeyPointPlanVo::setChildren);

        return resultList;
    }

    /**
     * 获取二次经营要点识别Vo
     * @param qqchSecondManageKeyPoint
     * @return
     */
    @Override
    public QqchSecondManageKeyPointVo getQqchSecondManageKeyPointVo(QqchSecondManageKeyPoint qqchSecondManageKeyPoint) {
        QqchSecondManageKeyPointVo qqchSecondManageKeyPointVo = new QqchSecondManageKeyPointVo();

        BigDecimal version = qqchSecondManageKeyPoint.getVersion();
        version = VersionUtil.getVersion("qqch_second_manage_key_point",version);

        qqchSecondManageKeyPoint.setVersion(version);
        List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList = qqchSecondManageKeyPointMapper.getQqchSecondManageKeyPointList(qqchSecondManageKeyPoint);

        //设置子表数据
        this.setSublist(qqchSecondManageKeyPointList,version);

        //转树列表
        List<QqchSecondManageKeyPoint> treeList = ListTreeUtil.formatTree(
                qqchSecondManageKeyPointList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchSecondManageKeyPoint::getChildren,
                QqchSecondManageKeyPoint::setChildren);

        qqchSecondManageKeyPointVo.setVersion(version);
        qqchSecondManageKeyPointVo.setStageIdentity(qqchReviewService.getStage());
        qqchSecondManageKeyPointVo.setQqchSecondManageKeyPointList(treeList);

        return qqchSecondManageKeyPointVo;
    }

    /**
     * 查询子表数据
     * @param qqchSecondManageKeyPointList
     * @param version
     */
    private void setSublist(List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList, BigDecimal version) {
        //二次经营要点识别关联合同条款
        QqchKeyPointContractClause qqchKeyPointContractClause = new QqchKeyPointContractClause();
        qqchKeyPointContractClause.setVersion(version);
        List<QqchKeyPointContractClause> qqchKeyPointContractClauseList = qqchKeyPointContractClauseMapper.getQqchKeyPointContractClauseList(qqchKeyPointContractClause);

        for (QqchSecondManageKeyPoint qqchSecondManageKeyPoint : qqchSecondManageKeyPointList) {
            Long id = qqchSecondManageKeyPoint.getId();

            List<QqchKeyPointContractClause> keyPointContractClauseList = new ArrayList<>();

            for (QqchKeyPointContractClause keyPointContractClause : qqchKeyPointContractClauseList) {
                if(id.equals(keyPointContractClause.getMasterId())){
                    keyPointContractClauseList.add(keyPointContractClause);
                }
            }

            qqchSecondManageKeyPoint.setQqchKeyPointContractClauseList(keyPointContractClauseList);
        }
    }

    /**
     * 保存/确认/提交
     * @param qqchSecondManageKeyPointVo
     * @return
     */
    @Override
    public void save(QqchSecondManageKeyPointVo qqchSecondManageKeyPointVo) {
        String buttonMark = qqchSecondManageKeyPointVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchSecondManageKeyPointVo.getVersion();
        List<QqchSecondManageKeyPoint> qqchSecondManageKeyPointList = qqchSecondManageKeyPointVo.getQqchSecondManageKeyPointList();

        List<QqchSecondManageKeyPoint> tileList = ListTreeUtil.formatList(
                qqchSecondManageKeyPointList,
                QqchSecondManageKeyPoint::setId,
                QqchSecondManageKeyPoint::setPid,
                QqchSecondManageKeyPoint::setSort,
                QqchSecondManageKeyPoint::setLeaf,
                QqchSecondManageKeyPoint::getChildren,
                QqchSecondManageKeyPoint::setChildren);

        //校验非空
        if(!ButtonMark.SAVE.equals(buttonMark)){
            JyDetailsUtil.jyDetails(tileList, QqchSecondManageKeyPoint::getLeaf, ValidationGroups.Save.class);
        }

        //处理数据
        this.disposeData(tileList, version, buttonMark);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchSecondManageKeyPointVo.getMenuId();
            String stageIdentity = qqchSecondManageKeyPointVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     *
     * @param tileList
     * @param version
     * @param buttonMark
     */
    private void disposeData(List<QqchSecondManageKeyPoint> tileList, BigDecimal version, String buttonMark) {
        if(CollectionUtils.isEmpty(tileList)){
            return;
        }

        List<QqchKeyPointContractClause> keyPointContractClauseList = new ArrayList<>();

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchSecondManageKeyPoint secondManageKeyPoint : tileList) {
            this.disposeQqchKeyPointContractClause(version,secondManageKeyPoint,keyPointContractClauseList);

            secondManageKeyPoint.setQqchKeyPointContractClauseList(null);
            secondManageKeyPoint.setValid(valid);
            secondManageKeyPoint.setVersion(version);
            secondManageKeyPoint.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            secondManageKeyPoint.setCreateUserName(SecurityUtils.getUserName());
            secondManageKeyPoint.setCreateTime(DateUtils.getNowDate());
        }

        //校验子表非空
        if(!ButtonMark.SAVE.equals(buttonMark)){
            JyDetailsUtil.jyDetails(keyPointContractClauseList, ValidationGroups.Save.class);
        }

        //根据版本删除数据
        this.deleteDate(version);

        /*插入数据*/
        qqchSecondManageKeyPointMapper.insertQqchSecondManageKeyPointList(tileList);

        //插入二次经营要点识别关联合同条款
        if(CollectionUtils.isNotEmpty(keyPointContractClauseList)){
            qqchKeyPointContractClauseMapper.insertQqchKeyPointContractClauseList(keyPointContractClauseList);
        }
    }

    /**
     * 删除数据
     * @param version
     */
    private void deleteDate(BigDecimal version) {
        //删除主表数据
        QqchSecondManageKeyPoint qqchSecondManageKeyPoint = new QqchSecondManageKeyPoint();
        qqchSecondManageKeyPoint.setVersion(version);
        qqchSecondManageKeyPointMapper.deleteQqchSecondManageKeyPoint(qqchSecondManageKeyPoint);

        //删除二次经营要点识别关联合同条款
        QqchKeyPointContractClause  qqchKeyPointContractClause = new QqchKeyPointContractClause();
        qqchKeyPointContractClause.setVersion(version);
        qqchKeyPointContractClauseMapper.deleteQqchKeyPointContractClause(qqchKeyPointContractClause);
    }

    /**
     * 处理二次经营要点识别关联合同条款数据
     * @param version
     * @param secondManageKeyPoint
     * @param keyPointContractClauseList
     */
    private void disposeQqchKeyPointContractClause(BigDecimal version, QqchSecondManageKeyPoint secondManageKeyPoint, List<QqchKeyPointContractClause> keyPointContractClauseList) {
        Long masterId = secondManageKeyPoint.getId();
        //二次经营要点识别关联合同条款数据
        List<QqchKeyPointContractClause> qqchKeyPointContractClauseList = secondManageKeyPoint.getQqchKeyPointContractClauseList();

        for (QqchKeyPointContractClause qqchKeyPointContractClause : qqchKeyPointContractClauseList) {
            qqchKeyPointContractClause.setId(IdWorker.createId());
            qqchKeyPointContractClause.setMasterId(masterId);
            qqchKeyPointContractClause.setVersion(version);
            qqchKeyPointContractClause.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchKeyPointContractClause.setCreateUserName(SecurityUtils.getUserName());
            qqchKeyPointContractClause.setCreateTime(DateUtils.getNowDate());
        }

        keyPointContractClauseList.addAll(qqchKeyPointContractClauseList);
    }
}
