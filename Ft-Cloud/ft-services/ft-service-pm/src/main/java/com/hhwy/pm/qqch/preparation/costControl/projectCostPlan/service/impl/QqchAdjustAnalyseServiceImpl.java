package com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.QqchAdjustAnalyse;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.domain.vo.QqchAdjustAnalyseVo;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.mapper.QqchAdjustAnalyseMapper;
import com.hhwy.pm.qqch.preparation.costControl.projectCostPlan.service.IQqchAdjustAnalyseService;
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
 * @date 2023-08-04 10:45:37
 * @remark
 */
@Service
public class QqchAdjustAnalyseServiceImpl implements IQqchAdjustAnalyseService {

    @Autowired
    private QqchAdjustAnalyseMapper qqchAdjustAnalyseMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchAdjustAnalyse getQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse) {
        return qqchAdjustAnalyseMapper.getQqchAdjustAnalyse(qqchAdjustAnalyse);
    }

    public List<QqchAdjustAnalyse> getQqchAdjustAnalyseList(QqchAdjustAnalyse qqchAdjustAnalyse) {
        return qqchAdjustAnalyseMapper.getQqchAdjustAnalyseList(qqchAdjustAnalyse);
    }

    @Transactional
    public int insertQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse) {
        qqchAdjustAnalyse.setId(IdWorker.createId());
        qqchAdjustAnalyse.setCreateUser(SecurityUtils.getUserName());
        qqchAdjustAnalyse.setCreateTime(DateUtils.getNowDate());
        return qqchAdjustAnalyseMapper.insertQqchAdjustAnalyse(qqchAdjustAnalyse);
    }

    @Transactional
    public int updateQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse) {
        qqchAdjustAnalyse.setUpdateUser(SecurityUtils.getUserName());
        qqchAdjustAnalyse.setUpdateTime(DateUtils.getNowDate());
        return qqchAdjustAnalyseMapper.updateQqchAdjustAnalyse(qqchAdjustAnalyse);
    }

    @Transactional
    public int updateQqchAdjustAnalyseList(List<QqchAdjustAnalyse> qqchAdjustAnalyseList) {
        for (QqchAdjustAnalyse qqchAdjustAnalyse : qqchAdjustAnalyseList) {
            qqchAdjustAnalyse.setUpdateUser(SecurityUtils.getUserName());
            qqchAdjustAnalyse.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchAdjustAnalyseMapper.updateQqchAdjustAnalyseList(qqchAdjustAnalyseList);
    }

    @Transactional
    public int deleteQqchAdjustAnalyse(QqchAdjustAnalyse qqchAdjustAnalyse) {
        qqchAdjustAnalyse.setUpdateUser(SecurityUtils.getUserName());
        qqchAdjustAnalyse.setUpdateTime(DateUtils.getNowDate());
        return qqchAdjustAnalyseMapper.deleteQqchAdjustAnalyse(qqchAdjustAnalyse);
    }

    @Transactional
    public int deleteQqchAdjustAnalyseByPks(List<Long> qqchAdjustAnalysePkList) {
        return qqchAdjustAnalyseMapper.deleteQqchAdjustAnalyseByPks(qqchAdjustAnalysePkList);
    }

    /**
     * 获取调差分析Vo
     * @param qqchAdjustAnalyse
     * @return
     */
    @Override
    public QqchAdjustAnalyseVo getQqchAdjustAnalyseVo(QqchAdjustAnalyse qqchAdjustAnalyse) {
        QqchAdjustAnalyseVo qqchAdjustAnalyseVo = new QqchAdjustAnalyseVo();

        BigDecimal version = qqchAdjustAnalyse.getVersion();
        version = VersionUtil.getVersion("qqch_adjust_analyse",version);

        qqchAdjustAnalyse.setVersion(version);
        List<QqchAdjustAnalyse> qqchAdjustAnalyseList = qqchAdjustAnalyseMapper.getQqchAdjustAnalyseList(qqchAdjustAnalyse);

        //转树列表
        List<QqchAdjustAnalyse> treeList = ListTreeUtil.formatTree(
                qqchAdjustAnalyseList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchAdjustAnalyse::getChildren,
                QqchAdjustAnalyse::setChildren);

        qqchAdjustAnalyseVo.setVersion(version);
        qqchAdjustAnalyseVo.setStageIdentity(qqchReviewService.getStage());
        qqchAdjustAnalyseVo.setList(treeList);
        return qqchAdjustAnalyseVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchAdjustAnalyseVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchAdjustAnalyseVo qqchAdjustAnalyseVo) {
        String buttonMark = qqchAdjustAnalyseVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchAdjustAnalyseVo.getVersion();
        List<QqchAdjustAnalyse> qqchAdjustAnalyseList = qqchAdjustAnalyseVo.getList();

        List<QqchAdjustAnalyse> tileList = ListTreeUtil.formatList(
                qqchAdjustAnalyseList,
                QqchAdjustAnalyse::setId,
                QqchAdjustAnalyse::setPid,
                QqchAdjustAnalyse::setSort,
                QqchAdjustAnalyse::setLeaf,
                QqchAdjustAnalyse::getChildren,
                QqchAdjustAnalyse::setChildren);

        //处理数据
        this.disposeData(tileList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchAdjustAnalyseVo.getMenuId();
            String stageIdentity = qqchAdjustAnalyseVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 处理数据
     * @param tileList
     * @param version
     */
    @Transactional
    public void disposeData(List<QqchAdjustAnalyse> tileList, BigDecimal version) {
        //删除旧数据
        QqchAdjustAnalyse qqchAdjustAnalyse = new QqchAdjustAnalyse();
        qqchAdjustAnalyse.setVersion(version);
        qqchAdjustAnalyseMapper.deleteQqchAdjustAnalyse(qqchAdjustAnalyse);

        this.insertQqchAdjustAnalyseList(tileList,version);
    }

    /**
     * 批量插入
     * @param qqchAdjustAnalyseList
     * @param version
     */
    @Transactional
    public void insertQqchAdjustAnalyseList(List<QqchAdjustAnalyse> qqchAdjustAnalyseList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchAdjustAnalyseList)){
            return;
        }

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchAdjustAnalyse qqchAdjustAnalyse : qqchAdjustAnalyseList) {
            qqchAdjustAnalyse.setValid(valid);
            qqchAdjustAnalyse.setVersion(version);
            qqchAdjustAnalyse.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchAdjustAnalyse.setCreateUserName(SecurityUtils.getUserName());
            qqchAdjustAnalyse.setCreateTime(DateUtils.getNowDate());
        }
        qqchAdjustAnalyseMapper.insertQqchAdjustAnalyseList(qqchAdjustAnalyseList);
    }
}
