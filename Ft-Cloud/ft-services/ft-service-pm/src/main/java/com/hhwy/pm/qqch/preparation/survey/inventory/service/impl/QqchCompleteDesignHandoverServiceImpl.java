package com.hhwy.pm.qqch.preparation.survey.inventory.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchCompleteDesignHandover;
import com.hhwy.pm.qqch.preparation.survey.inventory.domain.vo.QqchCompleteDesignHandoverVo;
import com.hhwy.pm.qqch.preparation.survey.inventory.mapper.QqchCompleteDesignHandoverMapper;
import com.hhwy.pm.qqch.preparation.survey.inventory.service.IQqchCompleteDesignHandoverService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-11 17:11:14
 * @remark 完整设计交接情况
 */
@Service
public class QqchCompleteDesignHandoverServiceImpl implements IQqchCompleteDesignHandoverService {

    @Autowired
    private QqchCompleteDesignHandoverMapper qqchCompleteDesignHandoverMapper;


    /**
     * 完整设计交接情况台账
     * @return
     * @param version
     */
    public QqchCompleteDesignHandoverVo getQqchCompleteDesignHandoverVo(BigDecimal version) {
        QqchCompleteDesignHandoverVo qqchCompleteDesignHandoverVo = new QqchCompleteDesignHandoverVo();

        version = VersionUtil.getVersion("qqch_complete_design_handover",version);
        qqchCompleteDesignHandoverVo.setVersion(version);

        QqchCompleteDesignHandover qqchCompleteDesignHandover = new QqchCompleteDesignHandover();
        qqchCompleteDesignHandover.setVersion(version);
        List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverList = qqchCompleteDesignHandoverMapper.getQqchCompleteDesignHandoverList(qqchCompleteDesignHandover);
        qqchCompleteDesignHandoverVo.setQqchCompleteDesignHandoverList(qqchCompleteDesignHandoverList);

        //TODO 获取确认状态

        return qqchCompleteDesignHandoverVo;
    }

    /**
     * 保存
     * @param qqchCompleteDesignHandoverVo
     * @return
     */
    @Override
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
            //确认 TODO 修改确认状态
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
        for (QqchCompleteDesignHandover qqchCompleteDesignHandover : qqchCompleteDesignHandoverList) {
            qqchCompleteDesignHandover.setId(IdWorker.createId());
            qqchCompleteDesignHandover.setVersion(version);
            if(version.compareTo(BigDecimal.valueOf(1)) == 0){
                qqchCompleteDesignHandover.setValid(Valid.YES);
            }
            qqchCompleteDesignHandover.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            qqchCompleteDesignHandover.setCreateUserName(SecurityUtils.getUserName());
            qqchCompleteDesignHandover.setCreateTime(DateUtils.getNowDate());
        }
        qqchCompleteDesignHandoverMapper.insertQqchCompleteDesignHandoverList(qqchCompleteDesignHandoverList);
    }
}
