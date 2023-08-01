package com.hhwy.pm.qqch.preparation.survey.optimize.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchDesignTechnologyOptimize;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.vo.QqchDesignTechnologyOptimizeVo;
import com.hhwy.pm.qqch.preparation.survey.optimize.mapper.QqchDesignTechnologyOptimizeMapper;
import com.hhwy.pm.qqch.preparation.survey.optimize.service.IQqchDesignTechnologyOptimizeService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author han
 * @date 2023-07-07 18:35:48
 * @remark 设计技术优化要点
 */
@Service
public class QqchDesignTechnologyOptimizeServiceImpl implements IQqchDesignTechnologyOptimizeService {

    @Autowired
    private QqchDesignTechnologyOptimizeMapper qqchDesignTechnologyOptimizeMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;

    @Autowired
    private IQqchReviewService qqchReviewService;


    /**
     * 获取设计技术优化要点集合
     * @return
     * @param version
     */
    @Override
    @Transactional
    public QqchDesignTechnologyOptimizeVo getQqchDesignTechnologyOptimizeVo(BigDecimal version) {
        QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo = new QqchDesignTechnologyOptimizeVo();

        version = VersionUtil.getVersion("qqch_design_technology_optimize",version);
        List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList = qqchDesignTechnologyOptimizeMapper.getQqchDesignTechnologyOptimizeList(version);

        qqchDesignTechnologyOptimizeVo.setVersion(version);
        qqchDesignTechnologyOptimizeVo.setStageIdentity(qqchReviewService.getStage());
        qqchDesignTechnologyOptimizeVo.setQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeList);
        return qqchDesignTechnologyOptimizeVo;
    }

    /**
     * 保存
     * @param qqchDesignTechnologyOptimizeVo
     */
    @Override
    @Transactional
    public void save(QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo){
        //删除旧数据
        QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize = new QqchDesignTechnologyOptimize();
        qqchDesignTechnologyOptimize.setVersion(qqchDesignTechnologyOptimizeVo.getVersion());
        qqchDesignTechnologyOptimizeMapper.deleteQqchDesignTechnologyOptimize(qqchDesignTechnologyOptimize);

        //插入新数据
        this.insertQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeVo.getQqchDesignTechnologyOptimizeList(), qqchDesignTechnologyOptimizeVo.getVersion());
    }

    /**
     * 确认
     * @param qqchDesignTechnologyOptimizeVo
     */
    @Override
    @Transactional
    public void confirm(QqchDesignTechnologyOptimizeVo qqchDesignTechnologyOptimizeVo){
        this.save(qqchDesignTechnologyOptimizeVo);

        String buttonMark = qqchDesignTechnologyOptimizeVo.getButtonMark();
        if(ButtonMark.CONFIRM.equals(buttonMark)){
            //插入确认状态
            String menuId = qqchDesignTechnologyOptimizeVo.getMenuId();
            String stageIdentity = qqchDesignTechnologyOptimizeVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }

    /**
     * 批量插入
     * @param qqchDesignTechnologyOptimizeList
     * @param version
     * @return
     */
    @Transactional
    public void insertQqchDesignTechnologyOptimizeList(List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList, BigDecimal version) {
        if(CollectionUtils.isEmpty(qqchDesignTechnologyOptimizeList)){
            return;
        }
        for (QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize : qqchDesignTechnologyOptimizeList) {
            qqchDesignTechnologyOptimize.setId(IdWorker.createId());
            qqchDesignTechnologyOptimize.setVersion(version);
            if(version.compareTo(BigDecimal.ONE) == 0){
                qqchDesignTechnologyOptimize.setValid(Valid.YES);
            }
            qqchDesignTechnologyOptimize.setCreateUser(StringUtils.valueOf(SecurityUtils.getUserId()));
            qqchDesignTechnologyOptimize.setCreateUserName(SecurityUtils.getUserName());
            qqchDesignTechnologyOptimize.setCreateTime(DateUtils.getNowDate());
        }
        qqchDesignTechnologyOptimizeMapper.insertQqchDesignTechnologyOptimizeList(qqchDesignTechnologyOptimizeList);
    }
}
