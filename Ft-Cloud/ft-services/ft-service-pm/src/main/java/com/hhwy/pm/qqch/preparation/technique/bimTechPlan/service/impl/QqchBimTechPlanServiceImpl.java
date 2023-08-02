package com.hhwy.pm.qqch.preparation.technique.bimTechPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.constant.CommonYesNo;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.QqchBimTechPlan;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.domain.vo.QqchBimTechPlanVo;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.mapper.QqchBimTechPlanExtendMapper;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.mapper.QqchBimTechPlanMapper;
import com.hhwy.pm.qqch.preparation.technique.bimTechPlan.service.IQqchBimTechPlanService;
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
 * @date 2023-07-25 10:45:57
 * @remark BIM技术策划
 */
@Service
public class QqchBimTechPlanServiceImpl implements IQqchBimTechPlanService {

    @Autowired
    private QqchBimTechPlanMapper qqchBimTechPlanMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private QqchBimTechPlanExtendServiceImpl qqchBimTechPlanExtendService;

    @Autowired
    private QqchBimTechPlanExtendMapper qqchBimTechPlanExtendMapper;

    @Autowired
    private IQqchReviewService qqchReviewService;


    public QqchBimTechPlan getQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan) {
        return qqchBimTechPlanMapper.getQqchBimTechPlan(qqchBimTechPlan);
    }

    public List<QqchBimTechPlan> getQqchBimTechPlanList(QqchBimTechPlan qqchBimTechPlan) {
        return qqchBimTechPlanMapper.getQqchBimTechPlanList(qqchBimTechPlan);
    }

    @Transactional
    public int insertQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan) {
        qqchBimTechPlan.setId(IdWorker.createId());
        qqchBimTechPlan.setCreateUser(SecurityUtils.getUserName());
        qqchBimTechPlan.setCreateTime(DateUtils.getNowDate());
        return qqchBimTechPlanMapper.insertQqchBimTechPlan(qqchBimTechPlan);
    }

    @Transactional
    public void insertQqchBimTechPlanList(List<QqchBimTechPlan> qqchBimTechPlanList, BigDecimal version, String bimMark) {
        //删除旧数据
        QqchBimTechPlan qqchBimTechPlan = new QqchBimTechPlan();
        qqchBimTechPlan.setVersion(version);
        qqchBimTechPlanMapper.deleteQqchBimTechPlan(qqchBimTechPlan);

        if(CommonYesNo.NO.equals(bimMark) || CollectionUtils.isEmpty(qqchBimTechPlanList)){
            return;
        }

        int sort = 1;
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        for (QqchBimTechPlan bimTechPlan : qqchBimTechPlanList) {
            bimTechPlan.setId(IdWorker.createId());
            bimTechPlan.setValid(valid);
            bimTechPlan.setVersion(version);
            bimTechPlan.setSort(sort++);
            bimTechPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            bimTechPlan.setCreateUserName(SecurityUtils.getUserName());
            bimTechPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchBimTechPlanMapper.insertQqchBimTechPlanList(qqchBimTechPlanList);
    }

    @Transactional
    public int updateQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan) {
        qqchBimTechPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchBimTechPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchBimTechPlanMapper.updateQqchBimTechPlan(qqchBimTechPlan);
    }

    @Transactional
    public int updateQqchBimTechPlanList(List<QqchBimTechPlan> qqchBimTechPlanList) {
        for (QqchBimTechPlan qqchBimTechPlan : qqchBimTechPlanList) {
            qqchBimTechPlan.setUpdateUser(SecurityUtils.getUserName());
            qqchBimTechPlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchBimTechPlanMapper.updateQqchBimTechPlanList(qqchBimTechPlanList);
    }

    @Transactional
    public int deleteQqchBimTechPlan(QqchBimTechPlan qqchBimTechPlan) {
        qqchBimTechPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchBimTechPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchBimTechPlanMapper.deleteQqchBimTechPlan(qqchBimTechPlan);
    }

    @Transactional
    public int deleteQqchBimTechPlanByPks(List<Long> qqchBimTechPlanPkList) {
        return qqchBimTechPlanMapper.deleteQqchBimTechPlanByPks(qqchBimTechPlanPkList);
    }

    /**
     * 获取BIM技术策划Vo
     * @param qqchBimTechPlan
     * @return
     */
    @Override
    public QqchBimTechPlanVo getQqchBimTechPlanVo(QqchBimTechPlan qqchBimTechPlan) {
        QqchBimTechPlanVo qqchBimTechPlanVo = new QqchBimTechPlanVo();

        BigDecimal version = qqchBimTechPlan.getVersion();
        version = VersionUtil.getVersion("qqch_bim_tech_plan",version);
        qqchBimTechPlanVo.setVersion(version);
        qqchBimTechPlanVo.setStageIdentity(qqchReviewService.getStage());

        //获取当前项目是否应用bim技术
        String bimMark = qqchBimTechPlanExtendMapper.getBimMark(version);
        if(StringUtils.isBlank(bimMark) || CommonYesNo.NO.equals(bimMark)){
            return qqchBimTechPlanVo;
        }

        qqchBimTechPlanVo.setVersion(version);
        List<QqchBimTechPlan> qqchBimTechPlanList = qqchBimTechPlanMapper.getQqchBimTechPlanList(qqchBimTechPlan);

        qqchBimTechPlanVo.setBimMark(bimMark);
        qqchBimTechPlanVo.setQqchBimTechPlanList(qqchBimTechPlanList);
        return qqchBimTechPlanVo;
    }

    /**
     * 保存/确认/提交
     * @param qqchBimTechPlanVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchBimTechPlanVo qqchBimTechPlanVo) {
        String buttonMark = qqchBimTechPlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchBimTechPlanVo.getVersion();
        String bimMark = qqchBimTechPlanVo.getBimMark();

        //处理bim标记
        qqchBimTechPlanExtendService.disposeBimMark(bimMark,version);

        //处理数据
        List<QqchBimTechPlan> qqchBimTechPlanList = qqchBimTechPlanVo.getQqchBimTechPlanList();
        this.insertQqchBimTechPlanList(qqchBimTechPlanList,version,bimMark);

        //处理确认状态是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchBimTechPlanVo.getMenuId();
            String stageIdentity = qqchBimTechPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
