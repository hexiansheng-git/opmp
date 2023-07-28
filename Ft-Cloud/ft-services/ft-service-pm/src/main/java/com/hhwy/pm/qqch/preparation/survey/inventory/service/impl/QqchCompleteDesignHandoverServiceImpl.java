package com.hhwy.pm.qqch.preparation.survey.inventory.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchCompleteDesignHandover;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchCompleteDesignHandoverVo;
import com.hhwy.pm.qqch.preparation.survey.inventory.mapper.QqchCompleteDesignHandoverMapper;
import com.hhwy.pm.qqch.preparation.survey.inventory.service.IQqchCompleteDesignHandoverService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-11 17:11:14
 * @remark 完整设计交接情况
 */
@Service
public class QqchCompleteDesignHandoverServiceImpl implements IQqchCompleteDesignHandoverService {

    @Autowired
    private QqchCompleteDesignHandoverMapper qqchCompleteDesignHandoverMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    /**
     * 完整设计交接情况台账
     * @return
     * @param version
     */
    public QqchCompleteDesignHandoverVo getQqchCompleteDesignHandoverVo(BigDecimal version) {
        QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo = new QqchCompleteDesignHandoverVo();

        version = VersionUtil.getVersion("qqch_complete_design_handover",version);
        QqchCompleteDesignHandover qqchCompleteDesignHandover = new QqchCompleteDesignHandover();
        qqchCompleteDesignHandover.setVersion(version);
        List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverList = qqchCompleteDesignHandoverMapper.getQqchCompleteDesignHandoverList(qqchCompleteDesignHandover);

        qqchCompleteDesignHandoverVo.setVersion(version);
        qqchCompleteDesignHandoverVo.setStageIdentity(qqchReviewService.getStage());
        qqchCompleteDesignHandoverVo.setQqchCompleteDesignHandoverList(qqchCompleteDesignHandoverList);
        return qqchCompleteDesignHandoverVo;
    }

    /**
     * 保存
     * @param qqchCompleteDesignHandoverVo
     * @return
     */
    @Override
    @Transactional
    public void save(QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo) {
        //删除旧数据
        QqchCompleteDesignHandover qqchCompleteDesignHandover = new QqchCompleteDesignHandover();
        qqchCompleteDesignHandover.setVersion(qqchCompleteDesignHandoverVo.getVersion());
        qqchCompleteDesignHandoverMapper.deleteQqchCompleteDesignHandover(qqchCompleteDesignHandover);

        //插入新数据
        this.insertQqchCompleteDesignHandoverList(qqchCompleteDesignHandoverVo.getQqchCompleteDesignHandoverList(), qqchCompleteDesignHandoverVo.getVersion());
    }

    /**
     * 确认
     * @param qqchCompleteDesignHandoverVo
     * @return
     */
    @Override
    @Transactional
    public void confirm(QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo) {
        this.save(qqchCompleteDesignHandoverVo);

        String buttonMark = qqchCompleteDesignHandoverVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchCompleteDesignHandoverVo.getMenuId();
            String stageIdentity = qqchCompleteDesignHandoverVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 批量插入
     * @param qqchCompleteDesignHandoverList
     * @param version
     * @return
     */
    @Transactional
    public void insertQqchCompleteDesignHandoverList(List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchCompleteDesignHandoverList)){
            return;
        }
        for (QqchCompleteDesignHandover qqchCompleteDesignHandover : qqchCompleteDesignHandoverList) {
            qqchCompleteDesignHandover.setId(IdWorker.createId());
            qqchCompleteDesignHandover.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchCompleteDesignHandover.setValid(Valid.YES);
            }
            qqchCompleteDesignHandover.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchCompleteDesignHandover.setCreateUserName(SecurityUtils.getUserName());
            qqchCompleteDesignHandover.setCreateTime(DateUtils.getNowDate());
        }
        qqchCompleteDesignHandoverMapper.insertQqchCompleteDesignHandoverList(qqchCompleteDesignHandoverList);
    }
}
