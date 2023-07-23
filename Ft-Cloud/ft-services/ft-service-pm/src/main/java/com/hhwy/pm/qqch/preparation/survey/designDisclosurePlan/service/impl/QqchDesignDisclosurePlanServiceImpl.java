package com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.QqchDesignDisclosurePlan;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.mapper.QqchDesignDisclosurePlanMapper;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.service.IQqchDesignDisclosurePlanService;
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
 * @date 2023-07-21 16:47:23
 * @remark 2.4 设计交底策划
 */
@Service
public class QqchDesignDisclosurePlanServiceImpl implements IQqchDesignDisclosurePlanService {

    @Autowired
    private QqchDesignDisclosurePlanMapper qqchDesignDisclosurePlanMapper;
    @Autowired
    private CommonMapper commonMapper;


    public QqchDesignDisclosurePlan getQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan) {
        return qqchDesignDisclosurePlanMapper.getQqchDesignDisclosurePlan(qqchDesignDisclosurePlan);
    }

    /**
     *  列表查询
     *
     * @param qqchDesignDisclosurePlan
     * @return
     */
    public List<QqchDesignDisclosurePlan> getQqchDesignDisclosurePlanList(QqchDesignDisclosurePlan qqchDesignDisclosurePlan) {
        BigDecimal version=new BigDecimal(1);
        if (qqchDesignDisclosurePlan.getVersion() == null) {
            // 获取最大版本号
            version = commonMapper.selectMaxVersion("qqch_design_disclosure_plan");
        }
        qqchDesignDisclosurePlan.setVersion(version);
        return qqchDesignDisclosurePlanMapper.getQqchDesignDisclosurePlanList(qqchDesignDisclosurePlan);
    }

    @Transactional
    public int insertQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan) {
        qqchDesignDisclosurePlan.setId(IdWorker.createId());
        qqchDesignDisclosurePlan.setCreateUser(SecurityUtils.getUserName());
        qqchDesignDisclosurePlan.setCreateTime(DateUtils.getNowDate());
        return qqchDesignDisclosurePlanMapper.insertQqchDesignDisclosurePlan(qqchDesignDisclosurePlan);
    }

    /**
     *  新增，修改接口
     *
     * @param qqchDesignDisclosurePlanList
     * @return
     */
    @Transactional
    public int insertQqchDesignDisclosurePlanList(List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList) {
        List<QqchDesignDisclosurePlan> insertList = new ArrayList<>();
        List<QqchDesignDisclosurePlan> updateList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(qqchDesignDisclosurePlanList)){
            for (QqchDesignDisclosurePlan qqchDesignDisclosurePlan : qqchDesignDisclosurePlanList) {
                if(qqchDesignDisclosurePlan.getId()==null){
                    qqchDesignDisclosurePlan.setId(IdWorker.createId());
                    EntityUtils.setCreateUpdateInfo(qqchDesignDisclosurePlan);
                    insertList.add(qqchDesignDisclosurePlan);
                }else {
                    EntityUtils.setUpdateInfo(qqchDesignDisclosurePlan);
                    updateList.add(qqchDesignDisclosurePlan);
                }
            }
        }
        qqchDesignDisclosurePlanMapper.insertQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanList);
        qqchDesignDisclosurePlanMapper.updateQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanList);
        return 1;
    }

    @Transactional
    public int updateQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan) {
        qqchDesignDisclosurePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDesignDisclosurePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDesignDisclosurePlanMapper.updateQqchDesignDisclosurePlan(qqchDesignDisclosurePlan);
    }

    @Transactional
    public int updateQqchDesignDisclosurePlanList(List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList) {
        for (QqchDesignDisclosurePlan qqchDesignDisclosurePlan : qqchDesignDisclosurePlanList) {
            qqchDesignDisclosurePlan.setUpdateUser(SecurityUtils.getUserName());
            qqchDesignDisclosurePlan.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchDesignDisclosurePlanMapper.updateQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanList);
    }

    @Transactional
    public int deleteQqchDesignDisclosurePlan(QqchDesignDisclosurePlan qqchDesignDisclosurePlan) {
        qqchDesignDisclosurePlan.setUpdateUser(SecurityUtils.getUserName());
        qqchDesignDisclosurePlan.setUpdateTime(DateUtils.getNowDate());
        return qqchDesignDisclosurePlanMapper.deleteQqchDesignDisclosurePlan(qqchDesignDisclosurePlan);
    }

    @Transactional
    public int deleteQqchDesignDisclosurePlanByPks(List<Long> qqchDesignDisclosurePlanPkList) {
        return qqchDesignDisclosurePlanMapper.deleteQqchDesignDisclosurePlanByPks(qqchDesignDisclosurePlanPkList);
    }
}
