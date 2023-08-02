package com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.domain.QqchSmallMachinery;
import com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.domain.vo.QqchSmallMachineryVo;
import com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.mapper.QqchSmallMachineryMapper;
import com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.service.IQqchSmallMachineryService;
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
 * @date 2023-08-02 10:50:59
 * @remark 
 */
@Service
public class QqchSmallMachineryServiceImpl implements IQqchSmallMachineryService{

    @Autowired
    private QqchSmallMachineryMapper qqchSmallMachineryMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;

                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    public QqchSmallMachinery getQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery) {
        return qqchSmallMachineryMapper.getQqchSmallMachinery(qqchSmallMachinery);
    }

    /**
     *  列表接口
     * @param qqchSmallMachinery
     * @return
     */
    public QqchSmallMachineryVo getQqchSmallMachineryList(QqchSmallMachinery qqchSmallMachinery) {
        QqchSmallMachineryVo vo = new QqchSmallMachineryVo();
        BigDecimal version = qqchSmallMachinery.getVersion();
        version = VersionUtil.getVersion("qqch_small_machinery", version);
        qqchSmallMachinery.setVersion(version);
        List<QqchSmallMachinery> qqchSmallMachineryList = qqchSmallMachineryMapper.getQqchSmallMachineryList(qqchSmallMachinery);
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchSmallMachineryList(qqchSmallMachineryList);
        return vo;
    }


    /**
     *  保存/确认
     * @param qqchSmallMachineryVo
     */
    @Override
    public void save(QqchSmallMachineryVo qqchSmallMachineryVo) {
        String buttonMark = qqchSmallMachineryVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchSmallMachineryVo.getVersion();
        List<QqchSmallMachinery> qqchSmallMachineryList = qqchSmallMachineryVo.getQqchSmallMachineryList();

        this.insertQqchMeasuringInstrumentList(qqchSmallMachineryList, version);

        //判断是否是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = qqchSmallMachineryVo.getMenuId();
            String stageIdentity = qqchSmallMachineryVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    public void insertQqchMeasuringInstrumentList(List<QqchSmallMachinery> qqchSmallMachineryList, BigDecimal version) {
        //删除旧数据
        QqchSmallMachinery qqchSmallMachinery = new QqchSmallMachinery();
        qqchSmallMachinery.setVersion(version);
        qqchSmallMachineryMapper.deleteQqchSmallMachinery(qqchSmallMachinery);

        if (CollectionUtils.isEmpty(qqchSmallMachineryList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchSmallMachinery smallMachinery : qqchSmallMachineryList) {
            smallMachinery.setValid(valid);
            smallMachinery.setVersion(version);
            smallMachinery.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            smallMachinery.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            smallMachinery.setCreateTime(DateUtils.getNowDate());
        }
        qqchSmallMachineryMapper.insertQqchSmallMachineryList(qqchSmallMachineryList);
    }


    @Transactional
    public int insertQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery) {
        qqchSmallMachinery.setId(IdWorker.createId());
        qqchSmallMachinery.setCreateUser(SecurityUtils.getUserName());
        qqchSmallMachinery.setCreateTime(DateUtils.getNowDate());
        return qqchSmallMachineryMapper.insertQqchSmallMachinery(qqchSmallMachinery);
    }


    @Transactional
    public int updateQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery) {
        qqchSmallMachinery.setUpdateUser(SecurityUtils.getUserName());
        qqchSmallMachinery.setUpdateTime(DateUtils.getNowDate());
        return qqchSmallMachineryMapper.updateQqchSmallMachinery(qqchSmallMachinery);
    }

            @Transactional
        public int updateQqchSmallMachineryList(List<QqchSmallMachinery> qqchSmallMachineryList) {
            for (QqchSmallMachinery qqchSmallMachinery : qqchSmallMachineryList) {
                qqchSmallMachinery.setUpdateUser(SecurityUtils.getUserName());
                qqchSmallMachinery.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchSmallMachineryMapper.updateQqchSmallMachineryList(qqchSmallMachineryList);
        }
    
    @Transactional
    public int deleteQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery) {
        qqchSmallMachinery.setUpdateUser(SecurityUtils.getUserName());
        qqchSmallMachinery.setUpdateTime(DateUtils.getNowDate());
        return qqchSmallMachineryMapper.deleteQqchSmallMachinery(qqchSmallMachinery);
    }

            @Transactional
        public int deleteQqchSmallMachineryByPks(List<Long> qqchSmallMachineryPkList) {
            return qqchSmallMachineryMapper.deleteQqchSmallMachineryByPks(qqchSmallMachineryPkList);
        }


}
