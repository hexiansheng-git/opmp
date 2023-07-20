package com.hhwy.pm.qqch.preparation.survey.inventory.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.common.mapper.CommonMapper;
import com.hhwy.pm.qqch.module.contant.Valid;
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

    @Autowired
    private CommonMapper commonMapper;


    /**
     * 边设计边施工情况台账
     * @return
     * @param version
     */
    public QqchDesignConstructionSituationVo getQqchDesignConstructionSituationVo(BigDecimal version) {
        QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo = new QqchDesignConstructionSituationVo();

        if(version == null){
            version = commonMapper.selectMaxVersion("qqch_design_construction_situation");
        }
        qqchDesignConstructionSituationVo.setVersion(version);

        QqchDesignConstructionSituation qqchDesignConstructionSituation = new QqchDesignConstructionSituation();
        qqchDesignConstructionSituation.setVersion(version);
        List<QqchDesignConstructionSituation> qqchDesignConstructionSituationList = qqchDesignConstructionSituationMapper.getQqchDesignConstructionSituationList(qqchDesignConstructionSituation);

        qqchDesignConstructionSituationVo.setQqchDesignConstructionSituationList(qqchDesignConstructionSituationList);

        //TODO 获取确认状态

        return qqchDesignConstructionSituationVo;
    }

    /**
     * 保存
     * @param qqchDesignConstructionSituationVo
     * @return
     */
    @Override
    public void save(QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo) {
        //删除旧数据
        QqchDesignConstructionSituation qqchDesignConstructionSituation = new QqchDesignConstructionSituation();
        qqchDesignConstructionSituation.setVersion(qqchDesignConstructionSituationVo.getVersion());
        qqchDesignConstructionSituationMapper.deleteQqchDesignConstructionSituation(qqchDesignConstructionSituation);

        //插入新数据
        this.insertQqchDesignConstructionSituationList(qqchDesignConstructionSituationVo.getQqchDesignConstructionSituationList(), qqchDesignConstructionSituation.getVersion());
    }

    /**
     * 确认
     * @param qqchDesignConstructionSituationVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo) {
        this.save(qqchDesignConstructionSituationVo);

        //TODO 修改确认状态
    }

    /**
     * 批量编辑
     * @param qqchDesignConstructionSituationList
     * @param version
     */
    @Transactional
    public void insertQqchDesignConstructionSituationList(List<QqchDesignConstructionSituation> qqchDesignConstructionSituationList, BigDecimal version){
        for (QqchDesignConstructionSituation qqchDesignConstructionSituation : qqchDesignConstructionSituationList) {
            qqchDesignConstructionSituation.setId(IdWorker.createId());
            qqchDesignConstructionSituation.setVersion(version);
            qqchDesignConstructionSituation.setValid(Valid.YES);
            qqchDesignConstructionSituation.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchDesignConstructionSituation.setCreateUserName(SecurityUtils.getUserName());
            qqchDesignConstructionSituation.setCreateTime(DateUtils.getNowDate());
        }
        qqchDesignConstructionSituationMapper.insertQqchDesignConstructionSituationList(qqchDesignConstructionSituationList);
    }
}
