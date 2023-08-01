package com.hhwy.pm.qqch.preparation.technique.techTrainPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.QqchTechTrainPlan;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.domain.vo.QqchTechTrainPlanVo;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.mapper.QqchTechTrainPlanMapper;
import com.hhwy.pm.qqch.preparation.technique.techTrainPlan.service.IQqchTechTrainPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:57:39
 * @remark 技术培训策划
 */
@Service
public class QqchTechTrainPlanServiceImpl implements IQqchTechTrainPlanService {

    @Autowired
    private QqchTechTrainPlanMapper qqchTechTrainPlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchTechTrainPlan getQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan) {
        return qqchTechTrainPlanMapper.getQqchTechTrainPlan(qqchTechTrainPlan);
    }

    public List<QqchTechTrainPlan> getQqchTechTrainPlanList(QqchTechTrainPlan qqchTechTrainPlan) {
        return qqchTechTrainPlanMapper.getQqchTechTrainPlanList(qqchTechTrainPlan);
    }

    @Transactional
    public int insertQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan) {
        qqchTechTrainPlan.setId(IdWorker.createId());
        qqchTechTrainPlan.setCreateUser(SecurityUtils.getUserName());
        qqchTechTrainPlan.setCreateTime(DateUtils.getNowDate());
        return qqchTechTrainPlanMapper.insertQqchTechTrainPlan(qqchTechTrainPlan);
    }

    @Transactional
    public void insertQqchTechTrainPlanList(List<QqchTechTrainPlan> qqchTechTrainPlanList, BigDecimal version) {
        //删除旧数据
        QqchTechTrainPlan qqchTechTrainPlan = new QqchTechTrainPlan();
        qqchTechTrainPlan.setVersion(version);
        qqchTechTrainPlanMapper.deleteQqchTechTrainPlan(qqchTechTrainPlan);

        if(CollectionUtils.isEmpty(qqchTechTrainPlanList)){
            return;
        }
        int sort = 1;
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchTechTrainPlan techTrainPlan : qqchTechTrainPlanList) {
            techTrainPlan.setId(IdWorker.createId());
            techTrainPlan.setValid(valid);
            techTrainPlan.setVersion(version);
            techTrainPlan.setSort(sort++);
            techTrainPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            techTrainPlan.setCreateUserName(SecurityUtils.getUserName());
            techTrainPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchTechTrainPlanMapper.insertQqchTechTrainPlanList(qqchTechTrainPlanList);
    }

    @Transactional
    public int updateQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan) {
        qqchTechTrainPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchTechTrainPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchTechTrainPlanMapper.updateQqchTechTrainPlan(qqchTechTrainPlan);
    }

    @Transactional
    public int updateQqchTechTrainPlanList(List<QqchTechTrainPlan> qqchTechTrainPlanList) {
        for (QqchTechTrainPlan qqchTechTrainPlan : qqchTechTrainPlanList) {
            qqchTechTrainPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchTechTrainPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTechTrainPlanMapper.updateQqchTechTrainPlanList(qqchTechTrainPlanList);
    }

    @Transactional
    public int deleteQqchTechTrainPlan(QqchTechTrainPlan qqchTechTrainPlan) {
        qqchTechTrainPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchTechTrainPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchTechTrainPlanMapper.deleteQqchTechTrainPlan(qqchTechTrainPlan);
    }

    @Transactional
    public int deleteQqchTechTrainPlanByPks(List<Long> qqchTechTrainPlanPkList) {
        return qqchTechTrainPlanMapper.deleteQqchTechTrainPlanByPks(qqchTechTrainPlanPkList);
    }

    /**
     * 获取技术培训策划Vo
     * @param qqchTechTrainPlan
     * @return
     */
    @Override
    public QqchTechTrainPlanVo getQqchTechTrainPlanVo(QqchTechTrainPlan qqchTechTrainPlan) {
        QqchTechTrainPlanVo qqchTechTrainPlanVo = new QqchTechTrainPlanVo();

        BigDecimal version = qqchTechTrainPlan.getVersion();
        version = VersionUtil.getVersion("qqch_tech_train_plan",version);

        qqchTechTrainPlan.setVersion(version);
        List<QqchTechTrainPlan> qqchTechTrainPlanList = qqchTechTrainPlanMapper.getQqchTechTrainPlanList(qqchTechTrainPlan);

        qqchTechTrainPlanVo.setVersion(version);
        qqchTechTrainPlanVo.setStageIdentity(qqchReviewService.getStage());
        qqchTechTrainPlanVo.setQqchTechTrainPlanList(qqchTechTrainPlanList);
        return qqchTechTrainPlanVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchTechTrainPlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchTechTrainPlanVo qqchTechTrainPlanVo) {
        String buttonMark = qqchTechTrainPlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchTechTrainPlanVo.getVersion();
        List<QqchTechTrainPlan> qqchTechTrainPlanList = qqchTechTrainPlanVo.getQqchTechTrainPlanList();

        this.insertQqchTechTrainPlanList(qqchTechTrainPlanList,version);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchTechTrainPlanVo.getMenuId();
            String stageIdentity = qqchTechTrainPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
