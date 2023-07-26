package com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.QqchDesignDisclosurePlan;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.domain.vo.QqchDesignDisclosurePlanVo;
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
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


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

    /**
     *  新增
     * @param qqchDesignDisclosurePlanVo
     */
    @Override
    public void save(QqchDesignDisclosurePlanVo qqchDesignDisclosurePlanVo) {
        //删除旧数据
        QqchDesignDisclosurePlan qqchDesignDisclosurePlan = new QqchDesignDisclosurePlan();
        qqchDesignDisclosurePlan.setVersion(qqchDesignDisclosurePlanVo.getVersion());
        qqchDesignDisclosurePlanMapper.deleteQqchDesignDisclosurePlan(qqchDesignDisclosurePlan);
        //插入新数据
        this.insertQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanVo.getQqchDesignDisclosurePlanList(), qqchDesignDisclosurePlanVo.getVersion());
    }


    /**
     *  确认
     * @param qqchDesignDisclosurePlanVo
     */
    @Override
    public void confirm(QqchDesignDisclosurePlanVo qqchDesignDisclosurePlanVo) {
        this.save(qqchDesignDisclosurePlanVo);
        String buttonMark = qqchDesignDisclosurePlanVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchDesignDisclosurePlanVo.getMenuId();
            String stageIdentity = qqchDesignDisclosurePlanVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    private void insertQqchDesignDisclosurePlanList(List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList, BigDecimal version) {
        for (QqchDesignDisclosurePlan qqchDesignDisclosurePlan : qqchDesignDisclosurePlanList) {
            qqchDesignDisclosurePlan.setId(IdWorker.createId());
            qqchDesignDisclosurePlan.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchDesignDisclosurePlan.setValid(Valid.YES);
            }
            qqchDesignDisclosurePlan.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchDesignDisclosurePlan.setCreateUserName(SecurityUtils.getUserName());
            qqchDesignDisclosurePlan.setCreateTime(DateUtils.getNowDate());
        }
        qqchDesignDisclosurePlanMapper.insertQqchDesignDisclosurePlanList(qqchDesignDisclosurePlanList);
    }

    /**
     *  新增，修改接口
     *
     * @param qqchDesignDisclosurePlanList
     * @return
     */
    @Transactional
    public int batchAdd(List<QqchDesignDisclosurePlan> qqchDesignDisclosurePlanList) {
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
        if(CollectionUtils.isNotEmpty(insertList)){
            qqchDesignDisclosurePlanMapper.insertQqchDesignDisclosurePlanList(insertList);
        }
        if(CollectionUtils.isNotEmpty(updateList)){
            qqchDesignDisclosurePlanMapper.updateQqchDesignDisclosurePlanList(updateList);
        }
        return 1;
    }


}
