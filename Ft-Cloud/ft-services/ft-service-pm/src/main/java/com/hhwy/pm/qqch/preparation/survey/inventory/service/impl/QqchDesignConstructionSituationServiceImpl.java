package com.hhwy.pm.qqch.preparation.survey.inventory.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.domain.QqchModuleConfirmCase;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchDesignConstructionSituation;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchDesignConstructionSituationVo;
import com.hhwy.pm.qqch.preparation.survey.inventory.mapper.QqchDesignConstructionSituationMapper;
import com.hhwy.pm.qqch.preparation.survey.inventory.service.IQqchDesignConstructionSituationService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-13 10:09:03
 * @remark 边设计边施工情况
 */
@Service
public class QqchDesignConstructionSituationServiceImpl implements IQqchDesignConstructionSituationService {

    @Autowired
    private QqchDesignConstructionSituationMapper qqchDesignConstructionSituationMapper;


    /**
     * 边设计边施工情况台账
     * @param qqchDesignConstructionSituation
     * @return
     */
    public QqchDesignConstructionSituationVo getQqchDesignConstructionSituationVo(QqchDesignConstructionSituation qqchDesignConstructionSituation) {
        QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo = new QqchDesignConstructionSituationVo();
        List<QqchDesignConstructionSituation> qqchDesignConstructionSituationList = qqchDesignConstructionSituationMapper.getQqchDesignConstructionSituationList(qqchDesignConstructionSituation);
        qqchDesignConstructionSituationVo.setQqchDesignConstructionSituationList(qqchDesignConstructionSituationList);

        //TODO 获取确认状态
        qqchDesignConstructionSituationVo.setQqchModuleConfirmCase(new QqchModuleConfirmCase());

        return qqchDesignConstructionSituationVo;
    }

    /**
     * 保存
     * @param qqchDesignConstructionSituationVo
     * @return
     */
    @Override
    public void save(QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo) {
        this.editQqchDesignConstructionSituationList(qqchDesignConstructionSituationVo.getQqchDesignConstructionSituationList());
    }

    /**
     * 确认
     * @param qqchDesignConstructionSituationVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo) {
        this.editQqchDesignConstructionSituationList(qqchDesignConstructionSituationVo.getQqchDesignConstructionSituationList());

        //TODO 修改确认状态
    }

    /**
     * 批量编辑
     * @param qqchDesignConstructionSituationList
     */
    @Transactional
    public void editQqchDesignConstructionSituationList(List<QqchDesignConstructionSituation> qqchDesignConstructionSituationList){
        List<QqchDesignConstructionSituation> insertList = new ArrayList<>();
        List<QqchDesignConstructionSituation> updateList = new ArrayList<>();
        for (QqchDesignConstructionSituation qqchDesignConstructionSituation : qqchDesignConstructionSituationList) {
            Long id = qqchDesignConstructionSituation.getId();
            if(id == null){
                qqchDesignConstructionSituation.setId(IdWorker.createId());
                qqchDesignConstructionSituation.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                qqchDesignConstructionSituation.setCreateUserName(SecurityUtils.getUserName());
                qqchDesignConstructionSituation.setCreateTime(DateUtils.getNowDate());
                insertList.add(qqchDesignConstructionSituation);
            }else{
                qqchDesignConstructionSituation.setUpdateUser(String.valueOf(SecurityUtils.getUserName()));
                qqchDesignConstructionSituation.setUpdateTime(DateUtils.getNowDate());
                updateList.add(qqchDesignConstructionSituation);
            }
        }
        if(insertList.size() > 0){
            qqchDesignConstructionSituationMapper.insertQqchDesignConstructionSituationList(insertList);
        }
        if(updateList.size() > 0){
            qqchDesignConstructionSituationMapper.updateQqchDesignConstructionSituationList(updateList);
        }
    }

    /**
     * 批量删除
     * @param qqchDesignConstructionSituationPkList
     * @return
     */
    @Transactional
    public int deleteQqchDesignConstructionSituationByPks(List<Long> qqchDesignConstructionSituationPkList) {
        return qqchDesignConstructionSituationMapper.deleteQqchDesignConstructionSituationByPks(qqchDesignConstructionSituationPkList);
    }
}
