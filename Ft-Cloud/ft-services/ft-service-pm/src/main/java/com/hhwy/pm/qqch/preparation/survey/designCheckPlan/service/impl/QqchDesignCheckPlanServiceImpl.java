package com.hhwy.pm.qqch.preparation.survey.designCheckPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.QqchDesignCheckPlan;
import com.hhwy.pm.qqch.preparation.survey.designCheckPlan.domain.vo.QqchDesignCheckPlanVo;
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
 * @remark 2.3.3 设计成果验收计划
 */
@Service
public class QqchDesignCheckPlanServiceImpl implements IQqchDesignCheckPlanService {

    @Autowired
    private QqchDesignCheckPlanMapper qqchDesignCheckPlanMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    /**
     * 列表查询
     *
     * @param qqchDesignCheckPlan
     * @return
     */
    public List<QqchDesignCheckPlan> getQqchDesignCheckPlanList(QqchDesignCheckPlan qqchDesignCheckPlan) {
        BigDecimal version = new BigDecimal(1);
        if (qqchDesignCheckPlan.getVersion() == null) {
            // 获取最大版本号
            version = commonMapper.selectMaxVersion("qqch_design_check_plan");
        }
        qqchDesignCheckPlan.setVersion(version);
        return qqchDesignCheckPlanMapper.getQqchDesignCheckPlanList(qqchDesignCheckPlan);
    }

    /**
     *  新增
     * @param qqchDesignCheckPlanVo
     */
    @Override
    public void save(QqchDesignCheckPlanVo qqchDesignCheckPlanVo) {
        //删除旧数据
        QqchDesignCheckPlan qqchDesignCheckPlan = new QqchDesignCheckPlan();
        qqchDesignCheckPlan.setVersion(qqchDesignCheckPlanVo.getVersion());
        qqchDesignCheckPlanMapper.deleteQqchDesignCheckPlan(qqchDesignCheckPlan);
        //插入新数据
        this.insertQqchDesignCheckPlanList(qqchDesignCheckPlanVo.getQqchDesignCheckPlanList(), qqchDesignCheckPlanVo.getVersion());
    }

    /**
     *  确认
     *
     * @param qqchDesignCheckPlanVo
     */
    @Override
    public void confirm(QqchDesignCheckPlanVo qqchDesignCheckPlanVo) {
        this.save(qqchDesignCheckPlanVo);
        String buttonMark = qqchDesignCheckPlanVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchDesignCheckPlanVo.getMenuId();
            String stageIdentity = qqchDesignCheckPlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }


    private void insertQqchDesignCheckPlanList(List<QqchDesignCheckPlan> qqchDesignCheckPlanList, BigDecimal version) {
        for (QqchDesignCheckPlan qqchDesignCheckPlan : qqchDesignCheckPlanList) {
            qqchDesignCheckPlan.setVersion(version);
            if(version.compareTo(BigDecimal.valueOf(1)) == 0){
                qqchDesignCheckPlan.setId(IdWorker.createId());
                qqchDesignCheckPlan.setValid(Valid.YES);
            }
            qqchDesignCheckPlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchDesignCheckPlan.setCreateUserName(SecurityUtils.getUserName());
            qqchDesignCheckPlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchDesignCheckPlanMapper.insertQqchDesignCheckPlanList(qqchDesignCheckPlanList);
    }


    /**
     * 批量新增 修改
     *
     * @param qqchDesignCheckPlanList
     * @return
     */
    @Transactional
    public int batchAdd(List<QqchDesignCheckPlan> qqchDesignCheckPlanList) {
        List<QqchDesignCheckPlan> insertList = new ArrayList<>();
        List<QqchDesignCheckPlan> updateList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(qqchDesignCheckPlanList)) {
            for (QqchDesignCheckPlan qqchDesignCheckPlan : qqchDesignCheckPlanList) {
                if (qqchDesignCheckPlan.getId() == null) {
                    qqchDesignCheckPlan.setId(IdWorker.createId());
                    EntityUtils.setCreateUpdateInfo(qqchDesignCheckPlan);
                    insertList.add(qqchDesignCheckPlan);
                } else {
                    EntityUtils.setUpdateInfo(qqchDesignCheckPlan);
                    updateList.add(qqchDesignCheckPlan);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(insertList)) {
            qqchDesignCheckPlanMapper.insertQqchDesignCheckPlanList(insertList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
            qqchDesignCheckPlanMapper.updateQqchDesignCheckPlanList(updateList);
        }
        return 1;
    }


}
