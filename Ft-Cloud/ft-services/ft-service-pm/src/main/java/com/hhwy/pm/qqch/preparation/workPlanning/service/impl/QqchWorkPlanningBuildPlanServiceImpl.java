package com.hhwy.pm.qqch.preparation.workPlanning.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.doc.dwg.service.IQqchDocDwgService;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlaningArrange;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlan;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlanVo;
import com.hhwy.pm.qqch.preparation.workPlanning.mapper.QqchWorkPlanningBuildPlanMapper;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlanningBuildPlanService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.myEnum.InitVersionConstant;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hhwy.utils.idworker.IdWorker;

/**
 * @author zq
 * @date 2023-07-19 11:30:27
 * @remark 
 */
@Service
public class QqchWorkPlanningBuildPlanServiceImpl implements IQqchWorkPlanningBuildPlanService {

    @Autowired
    private QqchWorkPlanningBuildPlanMapper qqchWorkPlanningBuildPlanMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchReviewService iQqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

    public QqchWorkPlanningBuildPlan getQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan) {
        return qqchWorkPlanningBuildPlanMapper.getQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlan);
    }

    public List<QqchWorkPlanningBuildPlan> getQqchWorkPlanningBuildPlanList(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan) {
        return qqchWorkPlanningBuildPlanMapper.getQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlan);
    }

    @Transactional
    public int insertQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan) {
        qqchWorkPlanningBuildPlan.setId(IdWorker.createId());
        qqchWorkPlanningBuildPlan.setCreateUser(SecurityUtils.getUserName());
        qqchWorkPlanningBuildPlan.setCreateTime(DateUtils.getNowDate());
        return qqchWorkPlanningBuildPlanMapper.insertQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlan);
    }

    @Transactional
    public int insertQqchWorkPlanningBuildPlanList(QqchWorkPlanningBuildPlanVo qqchWorkPlanningBuildPlanVo) {
        List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList = new ArrayList<>();
        //判断是确认还是保存
        if("0".equals(qqchWorkPlanningBuildPlanVo.getSubmitFlag())){
            //先删除旧的 再添加新的
            QqchWorkPlanningBuildPlan temp = new QqchWorkPlanningBuildPlan();
            temp.setVersion(qqchWorkPlanningBuildPlanVo.getVersion());
            qqchWorkPlanningBuildPlanMapper.deleteQqchWorkPlanningBuildPlan(temp);
            String valid = "1";
            if(new BigDecimal(InitVersionConstant.INIT_VERSION).compareTo(qqchWorkPlanningBuildPlanVo.getVersion()) != 0){
                valid = "0";
            }
            if(!ObjectNullUtil.isEmpty(qqchWorkPlanningBuildPlanVo.getDataList())){
                qqchWorkPlanningBuildPlanList = qqchWorkPlanningBuildPlanVo.getDataList();
                for (QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan : qqchWorkPlanningBuildPlanList) {
                    qqchWorkPlanningBuildPlan.setId(IdWorker.createId());
                    qqchWorkPlanningBuildPlan.setCreateUser(SecurityUtils.getUserName());
                    qqchWorkPlanningBuildPlan.setCreateTime(DateUtils.getNowDate());
                    qqchWorkPlanningBuildPlan.setVersion(qqchWorkPlanningBuildPlanVo.getVersion());
                    qqchWorkPlanningBuildPlan.setVersion(ObjectNullUtil.isEmpty(qqchWorkPlanningBuildPlanVo.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : qqchWorkPlanningBuildPlanVo.getVersion());

                    qqchWorkPlanningBuildPlan.setValid(valid);
                }
            }else{
                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"营地场站规划不可为空");
            }
        }else if("1".equals(qqchWorkPlanningBuildPlanVo.getSubmitFlag())){//确认
            //确认
            //新增一条确认记录
            String valid = "1";
            if(!ObjectNullUtil.isEmpty(qqchWorkPlanningBuildPlanVo.getDataList())){
               qqchWorkPlanningBuildPlanList = qqchWorkPlanningBuildPlanVo.getDataList();
                for (QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan : qqchWorkPlanningBuildPlanList) {
                    qqchWorkPlanningBuildPlan.setId(IdWorker.createId());
                    qqchWorkPlanningBuildPlan.setCreateUser(SecurityUtils.getUserName());
                    qqchWorkPlanningBuildPlan.setCreateTime(DateUtils.getNowDate());
                    qqchWorkPlanningBuildPlan.setVersion(qqchWorkPlanningBuildPlanVo.getVersion());
                    qqchWorkPlanningBuildPlan.setVersion(ObjectNullUtil.isEmpty(qqchWorkPlanningBuildPlanVo.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : qqchWorkPlanningBuildPlanVo.getVersion());

                    qqchWorkPlanningBuildPlan.setValid(valid);
                }
                qqchModuleConfirmCaseService.addConfirmRecord(qqchWorkPlanningBuildPlanVo.getMenuId(),qqchWorkPlanningBuildPlanVo.getStageIdentity());
                qqchReviewService.updateFinishNum(qqchWorkPlanningBuildPlanVo.getStageIdentity(),qqchWorkPlanningBuildPlanVo.getModuleIdentity());
            }else{
                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"营地场站规划不可为空");
            }
        }else if("2".equals(qqchWorkPlanningBuildPlanVo.getSubmitFlag())){////提交
            String valid = "0";
            if(!ObjectNullUtil.isEmpty(qqchWorkPlanningBuildPlanVo.getDataList())){
                qqchWorkPlanningBuildPlanList = qqchWorkPlanningBuildPlanVo.getDataList();
                for (QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan : qqchWorkPlanningBuildPlanList) {
                    qqchWorkPlanningBuildPlan.setId(IdWorker.createId());
                    qqchWorkPlanningBuildPlan.setCreateUser(SecurityUtils.getUserName());
                    qqchWorkPlanningBuildPlan.setCreateTime(DateUtils.getNowDate());
                    qqchWorkPlanningBuildPlan.setVersion(qqchWorkPlanningBuildPlanVo.getVersion());
                    qqchWorkPlanningBuildPlan.setVersion(ObjectNullUtil.isEmpty(qqchWorkPlanningBuildPlanVo.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : qqchWorkPlanningBuildPlanVo.getVersion());
                    qqchWorkPlanningBuildPlan.setValid(valid);
                }
            }else{
                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"营地场站规划不可为空");
            }
        }else{
            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"标识不符合规范");
        }
        ////先删除旧的 再添加新的
        QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan = new QqchWorkPlanningBuildPlan();
        qqchWorkPlanningBuildPlan.setVersion(qqchWorkPlanningBuildPlanVo.getVersion());
        qqchWorkPlanningBuildPlanMapper.deleteQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlan);
        qqchWorkPlanningBuildPlanMapper.insertQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlanList);

        return 1;
    }

    @Transactional
    public int updateQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan) {
        qqchWorkPlanningBuildPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlanningBuildPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanningBuildPlanMapper.updateQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlan);
    }

            @Transactional
        public int updateQqchWorkPlanningBuildPlanList(List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList) {
            for (QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan : qqchWorkPlanningBuildPlanList) {
                qqchWorkPlanningBuildPlan.setUpdateUser(SecurityUtils.getUserName());
                qqchWorkPlanningBuildPlan.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchWorkPlanningBuildPlanMapper.updateQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlanList);
        }
    
    @Transactional
    public int deleteQqchWorkPlanningBuildPlan(QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan) {
        qqchWorkPlanningBuildPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchWorkPlanningBuildPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchWorkPlanningBuildPlanMapper.deleteQqchWorkPlanningBuildPlan(qqchWorkPlanningBuildPlan);
    }

            @Transactional
        public int deleteQqchWorkPlanningBuildPlanByPks(List<Long> qqchWorkPlanningBuildPlanPkList) {
            return qqchWorkPlanningBuildPlanMapper.deleteQqchWorkPlanningBuildPlanByPks(qqchWorkPlanningBuildPlanPkList);
        }

    @Override
    public List<QqchWorkPlanningBuildPlan> getQqchWorkPlanningBuildPlanListHistory(QqchWorkPlanningBuildPlan plan) {
        return qqchWorkPlanningBuildPlanMapper.getQqchWorkPlanningBuildPlanListHistory(plan);
    }

    @Override
    public List<QqchWorkPlanningBuildPlan> getMaxVVData(QqchWorkPlanningBuildPlan plan) {
        plan.setValid("1");
        BigDecimal version = commonMapper.selectMaxVersion("qqch_work_planning_build_plan");
        plan.setVersion(version);
        List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList = getQqchWorkPlanningBuildPlanList(plan);
        return qqchWorkPlanningBuildPlanList;
    }

    @Override
    public QqchWorkPlanningBuildPlanVo detail(QqchWorkPlanningBuildPlan plan) {
        List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList = null;
        if(ObjectNullUtil.isEmpty(plan.getVersion())){//直接版本号最大且有效版本
            qqchWorkPlanningBuildPlanList = getMaxVVData(plan);
        }else{//历史版本的详情
            qqchWorkPlanningBuildPlanList = getQqchWorkPlanningBuildPlanList(plan);
        }
        QqchWorkPlanningBuildPlanVo qqchWorkPlanningBuildPlanVo = new QqchWorkPlanningBuildPlanVo();
        qqchWorkPlanningBuildPlanVo.setDataList(qqchWorkPlanningBuildPlanList);
        if(ObjectNullUtil.isEmpty(qqchWorkPlanningBuildPlanList)){
            qqchWorkPlanningBuildPlanVo.setVersion(new BigDecimal(0));
        }else{
            qqchWorkPlanningBuildPlanVo.setVersion(qqchWorkPlanningBuildPlanList.get(0).getVersion());
        }
        //查询阶段
        String stage = iQqchReviewService.getStage();
        qqchWorkPlanningBuildPlanVo.setStageIdentity(stage);
        return qqchWorkPlanningBuildPlanVo;
    }

    @Override
    public void listener(Long businessId) {

    }
}
