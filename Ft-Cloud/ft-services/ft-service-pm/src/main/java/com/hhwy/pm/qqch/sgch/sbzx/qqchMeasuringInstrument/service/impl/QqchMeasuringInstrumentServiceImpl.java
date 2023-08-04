package com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.domain.QqchMeasuringInstrument;
import com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.domain.vo.QqchMeasuringInstrumentVo;
import com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.mapper.QqchMeasuringInstrumentMapper;
import com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.service.IQqchMeasuringInstrumentService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ldd
 * @date 2023-08-02 10:50:39
 * @remark
 */
@Service
public class QqchMeasuringInstrumentServiceImpl implements IQqchMeasuringInstrumentService {

    @Autowired
    private QqchMeasuringInstrumentMapper qqchMeasuringInstrumentMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;


    public QqchMeasuringInstrument getQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument) {
        return qqchMeasuringInstrumentMapper.getQqchMeasuringInstrument(qqchMeasuringInstrument);
    }


    /**
     *  列表接口
     * @param qqchMeasuringInstrument
     * @return
     */
    public QqchMeasuringInstrumentVo getQqchMeasuringInstrumentList(QqchMeasuringInstrument qqchMeasuringInstrument) {
        QqchMeasuringInstrumentVo vo = new QqchMeasuringInstrumentVo();
        BigDecimal version = qqchMeasuringInstrument.getVersion();
        version = VersionUtil.getVersion("qqch_measuring_instrument", version);
        qqchMeasuringInstrument.setVersion(version);
        List<QqchMeasuringInstrument> qqchMeasuringInstrumentList = qqchMeasuringInstrumentMapper.getQqchMeasuringInstrumentList(qqchMeasuringInstrument);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchMeasuringInstrumentList(qqchMeasuringInstrumentList);
        return vo;
    }


    /**
     *  保存/确认/提交
     *
     * @param qqchMeasuringInstrumentVo
     */
    @Override
    @Transactional
    public void save(QqchMeasuringInstrumentVo qqchMeasuringInstrumentVo) {
        String buttonMark = qqchMeasuringInstrumentVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchMeasuringInstrumentVo.getVersion();
        List<QqchMeasuringInstrument> qqchMeasuringInstrumentList = qqchMeasuringInstrumentVo.getQqchMeasuringInstrumentList();

        this.insertQqchMeasuringInstrumentList(qqchMeasuringInstrumentList, version);

        //判断是否是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = qqchMeasuringInstrumentVo.getMenuId();
            String stageIdentity = qqchMeasuringInstrumentVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    public void insertQqchMeasuringInstrumentList(List<QqchMeasuringInstrument> qqchMeasuringInstrumentList, BigDecimal version) {
        //删除旧数据
        QqchMeasuringInstrument qqchMeasuringInstrument = new QqchMeasuringInstrument();
        qqchMeasuringInstrument.setVersion(version);
        qqchMeasuringInstrumentMapper.deleteQqchMeasuringInstrument(qqchMeasuringInstrument);

        if (CollectionUtils.isEmpty(qqchMeasuringInstrumentList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchMeasuringInstrument measuringInstrument : qqchMeasuringInstrumentList) {
            measuringInstrument.setId(IdWorker.createId());
            measuringInstrument.setValid(valid);
            measuringInstrument.setVersion(version);
            measuringInstrument.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            measuringInstrument.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            measuringInstrument.setCreateTime(DateUtils.getNowDate());
        }
        qqchMeasuringInstrumentMapper.insertQqchMeasuringInstrumentList(qqchMeasuringInstrumentList);
    }




    @Transactional
    public int insertQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument) {
        qqchMeasuringInstrument.setId(IdWorker.createId());
        qqchMeasuringInstrument.setCreateUser(SecurityUtils.getUserName());
        qqchMeasuringInstrument.setCreateTime(DateUtils.getNowDate());
        return qqchMeasuringInstrumentMapper.insertQqchMeasuringInstrument(qqchMeasuringInstrument);
    }


    @Transactional
    public int updateQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument) {
        qqchMeasuringInstrument.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasuringInstrument.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasuringInstrumentMapper.updateQqchMeasuringInstrument(qqchMeasuringInstrument);
    }

    @Transactional
    public int updateQqchMeasuringInstrumentList(List<QqchMeasuringInstrument> qqchMeasuringInstrumentList) {
        for (QqchMeasuringInstrument qqchMeasuringInstrument : qqchMeasuringInstrumentList) {
            qqchMeasuringInstrument.setUpdateUser(SecurityUtils.getUserName());
            qqchMeasuringInstrument.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMeasuringInstrumentMapper.updateQqchMeasuringInstrumentList(qqchMeasuringInstrumentList);
    }

    @Transactional
    public int deleteQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument) {
        qqchMeasuringInstrument.setUpdateUser(SecurityUtils.getUserName());
        qqchMeasuringInstrument.setUpdateTime(DateUtils.getNowDate());
        return qqchMeasuringInstrumentMapper.deleteQqchMeasuringInstrument(qqchMeasuringInstrument);
    }

    @Transactional
    public int deleteQqchMeasuringInstrumentByPks(List<Long> qqchMeasuringInstrumentPkList) {
        return qqchMeasuringInstrumentMapper.deleteQqchMeasuringInstrumentByPks(qqchMeasuringInstrumentPkList);
    }


}
