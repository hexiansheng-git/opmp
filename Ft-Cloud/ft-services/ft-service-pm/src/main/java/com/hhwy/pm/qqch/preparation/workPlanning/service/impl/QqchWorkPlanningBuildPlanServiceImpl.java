package com.hhwy.pm.qqch.preparation.workPlanning.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlan;
import com.hhwy.pm.qqch.preparation.workPlanning.domain.QqchWorkPlanningBuildPlanVo;
import com.hhwy.pm.qqch.preparation.workPlanning.mapper.QqchWorkPlanningBuildPlanMapper;
import com.hhwy.pm.qqch.preparation.workPlanning.service.IQqchWorkPlanningBuildPlanService;
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
        //判断是确认还是保存
        if("0".equals(qqchWorkPlanningBuildPlanVo.getSubmitFlag())){
            //先删除旧的 再添加新的
            QqchWorkPlanningBuildPlan temp = new QqchWorkPlanningBuildPlan();
            temp.setVersion(qqchWorkPlanningBuildPlanVo.getVersion());
            qqchWorkPlanningBuildPlanMapper.deleteQqchWorkPlanningBuildPlan(temp);
            String valid = "1";
            if(!InitVersionConstant.INIT_VERSION.equals(String.valueOf(qqchWorkPlanningBuildPlanVo.getVersion()))){
                valid = "0";
            }
            if(!ObjectNullUtil.isEmpty(qqchWorkPlanningBuildPlanVo.getDataList())){
                List<QqchWorkPlanningBuildPlan> qqchWorkPlanningBuildPlanList = qqchWorkPlanningBuildPlanVo.getDataList();
                for (QqchWorkPlanningBuildPlan qqchWorkPlanningBuildPlan : qqchWorkPlanningBuildPlanList) {
                    qqchWorkPlanningBuildPlan.setId(IdWorker.createId());
                    qqchWorkPlanningBuildPlan.setCreateUser(SecurityUtils.getUserName());
                    qqchWorkPlanningBuildPlan.setCreateTime(DateUtils.getNowDate());
                    qqchWorkPlanningBuildPlan.setVersion(qqchWorkPlanningBuildPlanVo.getVersion());
                    qqchWorkPlanningBuildPlan.setVersion(ObjectNullUtil.isEmpty(qqchWorkPlanningBuildPlanVo.getVersion()) ? new BigDecimal(InitVersionConstant.INIT_VERSION) : qqchWorkPlanningBuildPlanVo.getVersion());

                    qqchWorkPlanningBuildPlan.setValid(valid);
                }
                qqchWorkPlanningBuildPlanMapper.insertQqchWorkPlanningBuildPlanList(qqchWorkPlanningBuildPlanList);
                return 1;
            }else{
                throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error,"营地场站规划不可为空");
            }
        }else{
            //确认
            //新增一条确认记录

            return 1;
        }
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
}
