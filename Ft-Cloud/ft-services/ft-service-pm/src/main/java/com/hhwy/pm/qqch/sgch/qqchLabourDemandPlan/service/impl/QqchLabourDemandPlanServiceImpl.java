package com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.QqchLabourDemandPlan;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.domain.vo.QqchLabourDemandPlanVo;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.mapper.QqchLabourDemandPlanMapper;
import com.hhwy.pm.qqch.sgch.qqchLabourDemandPlan.service.IQqchLabourDemandPlanService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-31 16:38:26
 * @remark 
 */
@Service
public class QqchLabourDemandPlanServiceImpl implements IQqchLabourDemandPlanService{

    @Autowired
    private QqchLabourDemandPlanMapper qqchLabourDemandPlanMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

                                                                                                                                                                                                                                                                                                                                                                                                                
    public QqchLabourDemandPlan getQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        return qqchLabourDemandPlanMapper.getQqchLabourDemandPlan(qqchLabourDemandPlan);
    }



    @Transactional
    public int insertQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        qqchLabourDemandPlan.setId(IdWorker.createId());
        qqchLabourDemandPlan.setCreateUser(SecurityUtils.getUserName());
        qqchLabourDemandPlan.setCreateTime(DateUtils.getNowDate());
        return qqchLabourDemandPlanMapper.insertQqchLabourDemandPlan(qqchLabourDemandPlan);
    }



    @Transactional
    public int updateQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchLabourDemandPlanMapper.updateQqchLabourDemandPlan(qqchLabourDemandPlan);
    }

            @Transactional
        public int updateQqchLabourDemandPlanList(List<QqchLabourDemandPlan> qqchLabourDemandPlanList) {
            for (QqchLabourDemandPlan qqchLabourDemandPlan : qqchLabourDemandPlanList) {
                qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
                qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchLabourDemandPlanMapper.updateQqchLabourDemandPlanList(qqchLabourDemandPlanList);
        }
    
    @Transactional
    public int deleteQqchLabourDemandPlan(QqchLabourDemandPlan qqchLabourDemandPlan) {
        qqchLabourDemandPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchLabourDemandPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlan(qqchLabourDemandPlan);
    }

            @Transactional
        public int deleteQqchLabourDemandPlanByPks(List<Long> qqchLabourDemandPlanPkList) {
            return qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlanByPks(qqchLabourDemandPlanPkList);
        }


    /**
     *  列表接口
     * @param qqchLabourDemandPlan
     * @return
     */
    public QqchLabourDemandPlanVo getQqchLabourDemandPlanList(QqchLabourDemandPlan qqchLabourDemandPlan) {
        QqchLabourDemandPlanVo labourDemandPlanVo = new QqchLabourDemandPlanVo();
        BigDecimal version = qqchLabourDemandPlan.getVersion();
        version = VersionUtil.getVersion("qqch_labour_demand_plan", version);
        qqchLabourDemandPlan.setVersion(version);
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanMapper.getQqchLabourDemandPlanList(qqchLabourDemandPlan);
        List<QqchLabourDemandPlan> treeList = TreeUtil.build(qqchLabourDemandPlanList, 0l);
        labourDemandPlanVo.setVersion(version);
        labourDemandPlanVo.setStageIdentity(qqchReviewService.getStage());
        labourDemandPlanVo.setQqchLabourDemandPlanList(treeList);
        return labourDemandPlanVo;
    }

    /**
     *  保存/确认/提交
     * @param qqchLabourDemandPlanVo
     */
    @Override
    @Transactional
    public void save(QqchLabourDemandPlanVo qqchLabourDemandPlanVo) {
        String buttonMark = qqchLabourDemandPlanVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchLabourDemandPlanVo.getVersion();
        List<QqchLabourDemandPlan> qqchLabourDemandPlanList = qqchLabourDemandPlanVo.getQqchLabourDemandPlanList();

        this.insertQqchLabourDemandPlanList(qqchLabourDemandPlanList,version);

        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认记录
            String menuId = qqchLabourDemandPlanVo.getMenuId();
            String stageIdentity = qqchLabourDemandPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    @Override
    public void selectCount(QqchLabourDemandPlanVo qqchLabourDemandPlanVo) {

    }


    public void insertQqchLabourDemandPlanList(List<QqchLabourDemandPlan> qqchLabourDemandPlanList,BigDecimal version) {
        //删除旧数据
        QqchLabourDemandPlan qqchLabourDemandPlan = new QqchLabourDemandPlan();
        qqchLabourDemandPlan.setVersion(version);
        qqchLabourDemandPlanMapper.deleteQqchLabourDemandPlan(qqchLabourDemandPlan);

        if(CollectionUtils.isEmpty(qqchLabourDemandPlanList)){
            return;
        }
        String valid = Valid.NO;
        if(version.compareTo(BigDecimal.ONE) == 0){
            valid = Valid.YES;
        }
        List<QqchLabourDemandPlan> configs = TreeUtil.treeToList(qqchLabourDemandPlanList);
        for (QqchLabourDemandPlan labourDemandPlan : configs) {
            labourDemandPlan.setValid(valid);
            labourDemandPlan.setVersion(version);
            labourDemandPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            labourDemandPlan.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            labourDemandPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchLabourDemandPlanMapper.insertQqchLabourDemandPlanList(configs);
    }
}
