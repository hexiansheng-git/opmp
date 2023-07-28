package com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.QqchTopicResearchPlan;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchTopicResearchPlanExportVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.domain.vo.QqchTopicResearchPlanVo;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.mapper.QqchTopicResearchPlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techManagePlan.service.IQqchTopicResearchPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:39:25
 * @remark 课题研究计划
 */
@Service
public class QqchTopicResearchPlanServiceImpl implements IQqchTopicResearchPlanService {

    @Autowired
    private QqchTopicResearchPlanMapper qqchTopicResearchPlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchTopicResearchPlan getQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan) {
        return qqchTopicResearchPlanMapper.getQqchTopicResearchPlan(qqchTopicResearchPlan);
    }

    public List<QqchTopicResearchPlan> getQqchTopicResearchPlanList(QqchTopicResearchPlan qqchTopicResearchPlan) {
        return qqchTopicResearchPlanMapper.getQqchTopicResearchPlanList(qqchTopicResearchPlan);
    }

    @Transactional
    public int insertQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan) {
        qqchTopicResearchPlan.setId(IdWorker.createId());
        qqchTopicResearchPlan.setCreateUser(SecurityUtils.getUserName());
        qqchTopicResearchPlan.setCreateTime(DateUtils.getNowDate());
        return qqchTopicResearchPlanMapper.insertQqchTopicResearchPlan(qqchTopicResearchPlan);
    }

    @Transactional
    public void insertQqchTopicResearchPlanList(List<QqchTopicResearchPlan> qqchTopicResearchPlanList, BigDecimal version) {
        //删除旧数据
        QqchTopicResearchPlan qqchTopicResearchPlan = new QqchTopicResearchPlan();
        qqchTopicResearchPlan.setVersion(version);
        qqchTopicResearchPlanMapper.deleteQqchTopicResearchPlan(qqchTopicResearchPlan);

        if(CollectionUtils.isEmpty(qqchTopicResearchPlanList)){
            return;
        }
        int sort = 1;
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        //插入新数据
        for (QqchTopicResearchPlan topicResearchPlan : qqchTopicResearchPlanList) {
            topicResearchPlan.setId(IdWorker.createId());
            topicResearchPlan.setValid(valid);
            topicResearchPlan.setVersion(version);
            topicResearchPlan.setSort(sort++);
            topicResearchPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            topicResearchPlan.setCreateUserName(SecurityUtils.getUserName());
            topicResearchPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchTopicResearchPlanMapper.insertQqchTopicResearchPlanList(qqchTopicResearchPlanList);
    }

    @Transactional
    public int updateQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan) {
        qqchTopicResearchPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchTopicResearchPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchTopicResearchPlanMapper.updateQqchTopicResearchPlan(qqchTopicResearchPlan);
    }

    @Transactional
    public int updateQqchTopicResearchPlanList(List<QqchTopicResearchPlan> qqchTopicResearchPlanList) {
        for (QqchTopicResearchPlan qqchTopicResearchPlan : qqchTopicResearchPlanList) {
            qqchTopicResearchPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchTopicResearchPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTopicResearchPlanMapper.updateQqchTopicResearchPlanList(qqchTopicResearchPlanList);
    }

    @Transactional
    public int deleteQqchTopicResearchPlan(QqchTopicResearchPlan qqchTopicResearchPlan) {
        qqchTopicResearchPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchTopicResearchPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchTopicResearchPlanMapper.deleteQqchTopicResearchPlan(qqchTopicResearchPlan);
    }

    @Transactional
    public int deleteQqchTopicResearchPlanByPks(List<Long> qqchTopicResearchPlanPkList) {
        return qqchTopicResearchPlanMapper.deleteQqchTopicResearchPlanByPks(qqchTopicResearchPlanPkList);
    }

    /**
     * 获取课题研究计划Vo
     * @param qqchTopicResearchPlan
     * @return
     */
    @Override
    public QqchTopicResearchPlanVo getQqchTopicResearchPlanVo(QqchTopicResearchPlan qqchTopicResearchPlan) {
        QqchTopicResearchPlanVo qqchTopicResearchPlanVo = new QqchTopicResearchPlanVo();

        BigDecimal version = qqchTopicResearchPlan.getVersion();
        version = VersionUtil.getVersion("qqch_topic_research_plan",version);

        qqchTopicResearchPlan.setVersion(version);
        List<QqchTopicResearchPlan> qqchTopicResearchPlanList = qqchTopicResearchPlanMapper.getQqchTopicResearchPlanList(qqchTopicResearchPlan);

        qqchTopicResearchPlanVo.setVersion(version);
        qqchTopicResearchPlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchTopicResearchPlanVo.setQqchTopicResearchPlanList(qqchTopicResearchPlanList);
        return qqchTopicResearchPlanVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchTopicResearchPlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchTopicResearchPlanVo qqchTopicResearchPlanVo) {
        String buttonMark = qqchTopicResearchPlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchTopicResearchPlanVo.getVersion();
        List<QqchTopicResearchPlan> qqchTopicResearchPlanList = qqchTopicResearchPlanVo.getQqchTopicResearchPlanList();

        //保存数据
        this.insertQqchTopicResearchPlanList(qqchTopicResearchPlanList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchTopicResearchPlanVo.getMenuId();
            String stageIdentity = qqchTopicResearchPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 获取导出数据
     * @param qqchTopicResearchPlan
     * @return
     */
    @Override
    public List<QqchTopicResearchPlanExportVo> getQqchTopicResearchPlanExportVoList(QqchTopicResearchPlan qqchTopicResearchPlan) {
        List<QqchTopicResearchPlan> qqchTopicResearchPlanList = qqchTopicResearchPlanMapper.getQqchTopicResearchPlanList(qqchTopicResearchPlan);
        List<QqchTopicResearchPlanExportVo> qqchTopicResearchPlanExportVoList = new ArrayList<>();
        for (QqchTopicResearchPlan topicResearchPlan : qqchTopicResearchPlanList) {
            QqchTopicResearchPlanExportVo qqchTopicResearchPlanExportVo = new QqchTopicResearchPlanExportVo();
            BeanUtils.copyProperties(topicResearchPlan,qqchTopicResearchPlanExportVo);
            qqchTopicResearchPlanExportVoList.add(qqchTopicResearchPlanExportVo);
        }
        return qqchTopicResearchPlanExportVoList;
    }
}
