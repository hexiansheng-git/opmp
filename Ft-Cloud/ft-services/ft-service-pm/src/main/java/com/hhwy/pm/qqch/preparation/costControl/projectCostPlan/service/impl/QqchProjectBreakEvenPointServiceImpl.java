package com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.QqchProjectBreakEvenPoint;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.vo.QqchProjectBreakEvenPointVo;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.mapper.QqchProjectBreakEvenPointMapper;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.service.IQqchProjectBreakEvenPointService;
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
 * @date 2023-08-03 13:39:59
 * @remark
 */
@Service
public class QqchProjectBreakEvenPointServiceImpl implements IQqchProjectBreakEvenPointService {

    @Autowired
    private QqchProjectBreakEvenPointMapper qqchProjectBreakEvenPointMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchProjectBreakEvenPoint getQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint) {
        return qqchProjectBreakEvenPointMapper.getQqchProjectBreakEvenPoint(qqchProjectBreakEvenPoint);
    }

    public List<QqchProjectBreakEvenPoint> getQqchProjectBreakEvenPointList(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint) {
        return qqchProjectBreakEvenPointMapper.getQqchProjectBreakEvenPointList(qqchProjectBreakEvenPoint);
    }

    @Transactional
    public int insertQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint) {
        qqchProjectBreakEvenPoint.setId(IdWorker.createId());
        qqchProjectBreakEvenPoint.setCreateUser(SecurityUtils.getUserName());
        qqchProjectBreakEvenPoint.setCreateTime(DateUtils.getNowDate());
        return qqchProjectBreakEvenPointMapper.insertQqchProjectBreakEvenPoint(qqchProjectBreakEvenPoint);
    }

    @Transactional
    public int updateQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint) {
        qqchProjectBreakEvenPoint.setUpdateUser(SecurityUtils.getUserName());
        qqchProjectBreakEvenPoint.setUpdateTime(DateUtils.getNowDate());
        return qqchProjectBreakEvenPointMapper.updateQqchProjectBreakEvenPoint(qqchProjectBreakEvenPoint);
    }

    @Transactional
    public int updateQqchProjectBreakEvenPointList(List<QqchProjectBreakEvenPoint> qqchProjectBreakEvenPointList) {
        for (QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint : qqchProjectBreakEvenPointList) {
            qqchProjectBreakEvenPoint.setUpdateUser(SecurityUtils.getUserName());
            qqchProjectBreakEvenPoint.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchProjectBreakEvenPointMapper.updateQqchProjectBreakEvenPointList(qqchProjectBreakEvenPointList);
    }

    @Transactional
    public int deleteQqchProjectBreakEvenPoint(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint) {
        qqchProjectBreakEvenPoint.setUpdateUser(SecurityUtils.getUserName());
        qqchProjectBreakEvenPoint.setUpdateTime(DateUtils.getNowDate());
        return qqchProjectBreakEvenPointMapper.deleteQqchProjectBreakEvenPoint(qqchProjectBreakEvenPoint);
    }

    @Transactional
    public int deleteQqchProjectBreakEvenPointByPks(List<Long> qqchProjectBreakEvenPointPkList) {
        return qqchProjectBreakEvenPointMapper.deleteQqchProjectBreakEvenPointByPks(qqchProjectBreakEvenPointPkList);
    }

    /**
     * 获取项目主要盈亏点分析Vo
     * @param qqchProjectBreakEvenPoint
     * @return
     */
    @Override
    public QqchProjectBreakEvenPointVo getQqchProjectBreakEvenPointVo(QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint) {
        QqchProjectBreakEvenPointVo qqchProjectBreakEvenPointVo = new QqchProjectBreakEvenPointVo();

        BigDecimal version = qqchProjectBreakEvenPoint.getVersion();
        version = VersionUtil.getVersion("qqch_project_operation_objective",version);

        qqchProjectBreakEvenPoint.setVersion(version);
        List<QqchProjectBreakEvenPoint> qqchProjectBreakEvenPointList = qqchProjectBreakEvenPointMapper.getQqchProjectBreakEvenPointList(qqchProjectBreakEvenPoint);

        //转树列表
        List<QqchProjectBreakEvenPoint> treeList = ListTreeUtil.formatTree(
                qqchProjectBreakEvenPointList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchProjectBreakEvenPoint::getChildren,
                QqchProjectBreakEvenPoint::setChildren);

        qqchProjectBreakEvenPointVo.setVersion(version);
        qqchProjectBreakEvenPointVo.setStageIdentity(qqchReviewService.getStage());
        qqchProjectBreakEvenPointVo.setQqchProjectBreakEvenPointList(treeList);
        return qqchProjectBreakEvenPointVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchProjectBreakEvenPointVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchProjectBreakEvenPointVo qqchProjectBreakEvenPointVo) {
        String buttonMark = qqchProjectBreakEvenPointVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchProjectBreakEvenPointVo.getVersion();
        List<QqchProjectBreakEvenPoint> qqchProjectBreakEvenPointList = qqchProjectBreakEvenPointVo.getQqchProjectBreakEvenPointList();

        List<QqchProjectBreakEvenPoint> tileList = ListTreeUtil.formatList(
                qqchProjectBreakEvenPointList,
                QqchProjectBreakEvenPoint::setId,
                QqchProjectBreakEvenPoint::setPid,
                QqchProjectBreakEvenPoint::setSort,
                QqchProjectBreakEvenPoint::setLeaf,
                QqchProjectBreakEvenPoint::getChildren,
                QqchProjectBreakEvenPoint::setChildren);

        //处理数据
        this.disposeData(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchProjectBreakEvenPointVo.getMenuId();
            String stageIdentity = qqchProjectBreakEvenPointVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param tileList
     * @param version
     */
    @Transactional
    public void disposeData(List<QqchProjectBreakEvenPoint> tileList, BigDecimal version) {
        //删除旧数据
        QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint = new QqchProjectBreakEvenPoint();
        qqchProjectBreakEvenPoint.setVersion(version);
        qqchProjectBreakEvenPointMapper.deleteQqchProjectBreakEvenPoint(qqchProjectBreakEvenPoint);

        this.insertQqchProjectBreakEvenPointList(tileList,version);
    }

    /**
     * 批量插入
     * @param qqchProjectBreakEvenPointList
     * @param version
     */
    @Transactional
    public void insertQqchProjectBreakEvenPointList(List<QqchProjectBreakEvenPoint> qqchProjectBreakEvenPointList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchProjectBreakEvenPointList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchProjectBreakEvenPoint qqchProjectBreakEvenPoint : qqchProjectBreakEvenPointList) {
            qqchProjectBreakEvenPoint.setValid(valid);
            qqchProjectBreakEvenPoint.setVersion(version);
            qqchProjectBreakEvenPoint.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchProjectBreakEvenPoint.setCreateUserName(SecurityUtils.getUserName());
            qqchProjectBreakEvenPoint.setCreateTime(DateUtils.getNowDate());
        }
        qqchProjectBreakEvenPointMapper.insertQqchProjectBreakEvenPointList(qqchProjectBreakEvenPointList);
    }
}
