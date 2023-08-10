package com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.domain.QqchTechRiskSolutions;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.domain.vo.QqchTechRiskSolutionsVo;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.mapper.QqchTechRiskSolutionsMapper;
import com.hhwy.pm.qqch.preparation.technique.techRiskCounterMeasure.service.IQqchTechRiskSolutionsService;
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
 * @date 2023-07-25 10:55:16
 * @remark 技术风险及应对措施
 */
@Service
public class QqchTechRiskSolutionsServiceImpl implements IQqchTechRiskSolutionsService {

    @Autowired
    private QqchTechRiskSolutionsMapper qqchTechRiskSolutionsMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchTechRiskSolutions getQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions) {
        return qqchTechRiskSolutionsMapper.getQqchTechRiskSolutions(qqchTechRiskSolutions);
    }

    public List<QqchTechRiskSolutions> getQqchTechRiskSolutionsList(QqchTechRiskSolutions qqchTechRiskSolutions) {
        return qqchTechRiskSolutionsMapper.getQqchTechRiskSolutionsList(qqchTechRiskSolutions);
    }

    @Transactional
    public int insertQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions) {
        qqchTechRiskSolutions.setId(IdWorker.createId());
        qqchTechRiskSolutions.setCreateUser(SecurityUtils.getUserName());
        qqchTechRiskSolutions.setCreateTime(DateUtils.getNowDate());
        return qqchTechRiskSolutionsMapper.insertQqchTechRiskSolutions(qqchTechRiskSolutions);
    }

    @Transactional
    public void insertQqchTechRiskSolutionsList(List<QqchTechRiskSolutions> qqchTechRiskSolutionsList, BigDecimal version) {
        //删除旧数据
        QqchTechRiskSolutions qqchTechRiskSolutions = new QqchTechRiskSolutions();
        qqchTechRiskSolutions.setVersion(version);
        qqchTechRiskSolutionsMapper.deleteQqchTechRiskSolutions(qqchTechRiskSolutions);

        if(CollectionUtils.isEmpty(qqchTechRiskSolutionsList)){
            return;
        }
        List<QqchTechRiskSolutions> insertList = ListTreeUtil.formatList(
                qqchTechRiskSolutionsList,
                QqchTechRiskSolutions::setId,
                QqchTechRiskSolutions::setPid,
                QqchTechRiskSolutions::setSort,
                QqchTechRiskSolutions::getChildren,
                QqchTechRiskSolutions::setChildren);

        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchTechRiskSolutions techRiskSolutions : insertList) {
            techRiskSolutions.setValid(valid);
            techRiskSolutions.setVersion(version);
            techRiskSolutions.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            techRiskSolutions.setCreateUserName(SecurityUtils.getUserName());
            techRiskSolutions.setCreateTime(DateUtils.getNowDate());
        }
        qqchTechRiskSolutionsMapper.insertQqchTechRiskSolutionsList(insertList);
    }

    @Transactional
    public int updateQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions) {
        qqchTechRiskSolutions.setUpdateUser(SecurityUtils.getUserName());
        qqchTechRiskSolutions.setUpdateTime(DateUtils.getNowDate());
        return qqchTechRiskSolutionsMapper.updateQqchTechRiskSolutions(qqchTechRiskSolutions);
    }

    @Transactional
    public int updateQqchTechRiskSolutionsList(List<QqchTechRiskSolutions> qqchTechRiskSolutionsList) {
        for (QqchTechRiskSolutions qqchTechRiskSolutions : qqchTechRiskSolutionsList) {
            qqchTechRiskSolutions.setUpdateUser(SecurityUtils.getUserName());
            qqchTechRiskSolutions.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTechRiskSolutionsMapper.updateQqchTechRiskSolutionsList(qqchTechRiskSolutionsList);
    }

    @Transactional
    public int deleteQqchTechRiskSolutions(QqchTechRiskSolutions qqchTechRiskSolutions) {
        qqchTechRiskSolutions.setUpdateUser(SecurityUtils.getUserName());
        qqchTechRiskSolutions.setUpdateTime(DateUtils.getNowDate());
        return qqchTechRiskSolutionsMapper.deleteQqchTechRiskSolutions(qqchTechRiskSolutions);
    }

    @Transactional
    public int deleteQqchTechRiskSolutionsByPks(List<Long> qqchTechRiskSolutionsPkList) {
        return qqchTechRiskSolutionsMapper.deleteQqchTechRiskSolutionsByPks(qqchTechRiskSolutionsPkList);
    }

    /**
     * 获取技术风险及应对措施Vo
     * @param qqchTechRiskSolutions
     * @return
     */
    @Override
    public QqchTechRiskSolutionsVo getQqchTechRiskSolutionsVo(QqchTechRiskSolutions qqchTechRiskSolutions) {
        QqchTechRiskSolutionsVo qqchTechRiskSolutionsVo = new QqchTechRiskSolutionsVo();

        BigDecimal version = qqchTechRiskSolutions.getVersion();
        version = VersionUtil.getVersion("qqch_tech_risk_solutions",version);

        qqchTechRiskSolutions.setVersion(version);
        List<QqchTechRiskSolutions> qqchTechRiskSolutionsList = qqchTechRiskSolutionsMapper.getQqchTechRiskSolutionsList(qqchTechRiskSolutions);

        //转换树列表
        List<QqchTechRiskSolutions> treeList = ListTreeUtil.formatTree(
                qqchTechRiskSolutionsList,
                o -> o.getPid() == null,
                (r, n) -> r.getId().equals(n.getPid()),
                QqchTechRiskSolutions::getChildren,
                QqchTechRiskSolutions::setChildren);

        qqchTechRiskSolutionsVo.setVersion(version);
        qqchTechRiskSolutionsVo.setStageIdentity(qqchReviewService.getStage());
        qqchTechRiskSolutionsVo.setList(treeList);
        return qqchTechRiskSolutionsVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchTechRiskSolutionsVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchTechRiskSolutionsVo qqchTechRiskSolutionsVo) {
        String buttonMark = qqchTechRiskSolutionsVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchTechRiskSolutionsVo.getVersion();
        List<QqchTechRiskSolutions> qqchTechRiskSolutionsList = qqchTechRiskSolutionsVo.getList();

        //处理数据
        this.insertQqchTechRiskSolutionsList(qqchTechRiskSolutionsList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchTechRiskSolutionsVo.getMenuId();
            String stageIdentity = qqchTechRiskSolutionsVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
