package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchChangeProcedurePlan;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchChangeProcedurePlanVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchChangeProcedurePlanMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchChangeProcedurePlanService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-07 18:35:34
 * @remark 变更程序策划
 */
@Service
public class QqchChangeProcedurePlanServiceImpl implements IQqchChangeProcedurePlanService {

    @Autowired
    private QqchChangeProcedurePlanMapper qqchChangeProcedurePlanMapper;


    /**
     * 获取变更程序策划
     * @return
     */
    public QqchChangeProcedurePlanVo getQqchChangeProcedurePlanVo() {
        QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo = new QqchChangeProcedurePlanVo();

        List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList = qqchChangeProcedurePlanMapper.getQqchChangeProcedurePlanList();
        qqchChangeProcedurePlanVo.setQqchChangeProcedurePlanList(qqchChangeProcedurePlanList);

        //TODO 获取确认状态
        qqchChangeProcedurePlanVo.setQqchModuleConfirmCase(new QqchModuleConfirmCase());

        return qqchChangeProcedurePlanVo;
    }

    /**
     * 保存
     * @param qqchChangeProcedurePlanVo
     */
    @Override
    @Transactional
    public void save(QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo) {
        List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList = qqchChangeProcedurePlanVo.getQqchChangeProcedurePlanList();
        this.editQqchChangeProcedurePlanList(qqchChangeProcedurePlanList);
    }

    /**
     * 确认
     * @param qqchChangeProcedurePlanVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchChangeProcedurePlanVo qqchChangeProcedurePlanVo) {
        this.editQqchChangeProcedurePlanList(qqchChangeProcedurePlanVo.getQqchChangeProcedurePlanList());

        //TODO 修改确认状态
    }

    /**
     * 批量编辑
     * @param qqchChangeProcedurePlanList
     */
    @Transactional
    public void editQqchChangeProcedurePlanList(List<QqchChangeProcedurePlan> qqchChangeProcedurePlanList){
        List<QqchChangeProcedurePlan> insertList = new ArrayList<>();
        List<QqchChangeProcedurePlan> updateList = new ArrayList<>();
        for (QqchChangeProcedurePlan qqchChangeProcedurePlan : qqchChangeProcedurePlanList) {
            Long id = qqchChangeProcedurePlan.getId();
            if(id == null){
                qqchChangeProcedurePlan.setId(IdWorker.createId());
                qqchChangeProcedurePlan.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
                qqchChangeProcedurePlan.setCreateUserName(SecurityUtils.getUserName());
                qqchChangeProcedurePlan.setCreateTime(DateUtils.getNowDate());
                insertList.add(qqchChangeProcedurePlan);
            }else {
                qqchChangeProcedurePlan.setUpdateUser(SecurityUtils.getUserName());
                qqchChangeProcedurePlan.setUpdateTime(DateUtils.getNowDate());
                updateList.add(qqchChangeProcedurePlan);
            }
        }
        if(insertList.size() > 0){
            qqchChangeProcedurePlanMapper.insertQqchChangeProcedurePlanList(insertList);
        }
        if(updateList.size() > 0){
            qqchChangeProcedurePlanMapper.updateQqchChangeProcedurePlanList(updateList);
        }
    }

    /**
     * 批量删除
     * @param qqchChangeProcedurePlanPkList
     * @return
     */
    @Transactional
    public int deleteQqchChangeProcedurePlanByPks(List<Long> qqchChangeProcedurePlanPkList) {
        return qqchChangeProcedurePlanMapper.deleteQqchChangeProcedurePlanByPks(qqchChangeProcedurePlanPkList);
    }

}
