package com.hhwy.pm.qqch.preparation.survey.designCheckPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.QqchDesignCheckPlan;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.mapper.QqchDesignCheckPlanMapper;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.service.IQqchDesignCheckPlanService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ldd
 * @date 2023-07-21 16:48:41
 * @remark  2.3.3 设计成果验收计划
 */
@Service
public class QqchDesignCheckPlanServiceImpl implements IQqchDesignCheckPlanService{

    @Autowired
    private QqchDesignCheckPlanMapper qqchDesignCheckPlanMapper;
    @Autowired
    private CommonMapper commonMapper;

                                                                                                                                                                                                                                                                                                                                                                                                                                        
    public QqchDesignCheckPlan getQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan) {
        return qqchDesignCheckPlanMapper.getQqchDesignCheckPlan(qqchDesignCheckPlan);
    }

    /**
     * 列表查询
     *
     * @param qqchDesignCheckPlan
     * @return
     */
    public List<QqchDesignCheckPlan> getQqchDesignCheckPlanList(QqchDesignCheckPlan qqchDesignCheckPlan) {
        BigDecimal version=new BigDecimal(1);
        if (qqchDesignCheckPlan.getVersion() == null) {
            // 获取最大版本号
            version = commonMapper.selectMaxVersion("qqch_design_check_plan");
        }
        qqchDesignCheckPlan.setVersion(version);
        return qqchDesignCheckPlanMapper.getQqchDesignCheckPlanList(qqchDesignCheckPlan);
    }


    @Transactional
    public int insertQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan) {
        qqchDesignCheckPlan.setId(IdWorker.createId());
        qqchDesignCheckPlan.setCreateUser(SecurityUtils.getUserName());
        qqchDesignCheckPlan.setCreateTime(DateUtils.getNowDate());
        return qqchDesignCheckPlanMapper.insertQqchDesignCheckPlan(qqchDesignCheckPlan);
    }

    /**
     *  批量新增 修改
     * @param qqchDesignCheckPlanList
     * @return
     */
    @Transactional
    public int insertQqchDesignCheckPlanList(List<QqchDesignCheckPlan> qqchDesignCheckPlanList) {
        List<QqchDesignCheckPlan> insertList = new ArrayList<>();
        List<QqchDesignCheckPlan> updateList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(qqchDesignCheckPlanList)){
            for (QqchDesignCheckPlan qqchDesignCheckPlan : qqchDesignCheckPlanList) {
                if(qqchDesignCheckPlan.getId()==null){
                    qqchDesignCheckPlan.setId(IdWorker.createId());
                    EntityUtils.setCreateUpdateInfo(qqchDesignCheckPlan);
                    insertList.add(qqchDesignCheckPlan);
                }else {
                    EntityUtils.setUpdateInfo(qqchDesignCheckPlan);
                    updateList.add(qqchDesignCheckPlan);
                }
            }
        }
         qqchDesignCheckPlanMapper.insertQqchDesignCheckPlanList(qqchDesignCheckPlanList);
        qqchDesignCheckPlanMapper.updateQqchDesignCheckPlanList(qqchDesignCheckPlanList);
        return 1;
    }

    @Transactional
    public int updateQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan) {
        qqchDesignCheckPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDesignCheckPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDesignCheckPlanMapper.updateQqchDesignCheckPlan(qqchDesignCheckPlan);
    }

            @Transactional
        public int updateQqchDesignCheckPlanList(List<QqchDesignCheckPlan> qqchDesignCheckPlanList) {
            for (QqchDesignCheckPlan qqchDesignCheckPlan : qqchDesignCheckPlanList) {
                qqchDesignCheckPlan.setUpdateUser(SecurityUtils.getUserName());
                qqchDesignCheckPlan.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchDesignCheckPlanMapper.updateQqchDesignCheckPlanList(qqchDesignCheckPlanList);
        }
    
    @Transactional
    public int deleteQqchDesignCheckPlan(QqchDesignCheckPlan qqchDesignCheckPlan) {
        qqchDesignCheckPlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDesignCheckPlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDesignCheckPlanMapper.deleteQqchDesignCheckPlan(qqchDesignCheckPlan);
    }

            @Transactional
        public int deleteQqchDesignCheckPlanByPks(List<Long> qqchDesignCheckPlanPkList) {
            return qqchDesignCheckPlanMapper.deleteQqchDesignCheckPlanByPks(qqchDesignCheckPlanPkList);
        }
    }
