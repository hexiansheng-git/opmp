package com.hhwy.pm.qqch.preparation.survey.inventory.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchDesignConstructionSituation;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchDesignConstructionSituationVo;
import com.hhwy.pm.qqch.preparation.survey.inventory.mapper.QqchDesignConstructionSituationMapper;
import com.hhwy.pm.qqch.preparation.survey.inventory.service.IQqchDesignConstructionSituationService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    /**
     * 边设计边施工情况台账
     * @return
     * @param version
     */
    public QqchDesignConstructionSituationVo getQqchDesignConstructionSituationVo(BigDecimal version) {
        QqchDesignConstructionSituationVo qqchDesignConstructionSituationVo = new QqchDesignConstructionSituationVo();

        version = VersionUtil.getVersion("qqch_design_construction_situation",version);
        QqchDesignConstructionSituation qqchDesignConstructionSituation = new QqchDesignConstructionSituation();
        qqchDesignConstructionSituation.setVersion(version);
        List<QqchDesignConstructionSituation> qqchDesignConstructionSituationList = qqchDesignConstructionSituationMapper.getQqchDesignConstructionSituationList(qqchDesignConstructionSituation);

        qqchDesignConstructionSituationVo.setVersion(version);
        qqchDesignConstructionSituationVo.setStageIdentity(qqchReviewService.getStage());
        qqchDesignConstructionSituationVo.setQqchDesignConstructionSituationList(qqchDesignConstructionSituationList);
        return qqchDesignConstructionSituationVo;
    }

    /**
     * 保存
     * @param qqchDesignConstructionSituationVo
     * @return
     */
    @Override
    @Transactional
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

        String buttonMark = qqchDesignConstructionSituationVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchDesignConstructionSituationVo.getMenuId();
            String stageIdentity = qqchDesignConstructionSituationVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
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
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchDesignConstructionSituation.setValid(Valid.YES);
            }
            qqchDesignConstructionSituation.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchDesignConstructionSituation.setCreateUserName(SecurityUtils.getUserName());
            qqchDesignConstructionSituation.setCreateTime(DateUtils.getNowDate());
        }
        qqchDesignConstructionSituationMapper.insertQqchDesignConstructionSituationList(qqchDesignConstructionSituationList);
    }
}
