package com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.domain.QqchTotalDemand;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.domain.vo.QqchTotalDemandVo;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.mapper.QqchTotalDemandMapper;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.service.IQqchTotalDemandService;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.domain.QqchTotalDemandTimeCount;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.service.IQqchTotalDemandTimeCountService;
import com.hhwy.pm.qqch.utils.ButtonMarkUtil;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import io.seata.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ldd
 * @date 2023-08-02 10:55:15
 * @remark
 */
@Service
public class QqchTotalDemandServiceImpl implements IQqchTotalDemandService{

    @Autowired
    private QqchTotalDemandMapper qqchTotalDemandMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchTotalDemandTimeCountService qqchTotalDemandTimeCountService;


    public QqchTotalDemand getQqchTotalDemand(QqchTotalDemand qqchTotalDemand) {
        return qqchTotalDemandMapper.getQqchTotalDemand(qqchTotalDemand);
    }


    /**
     *  列表
     * @param qqchTotalDemand
     * @return
     */
    public QqchTotalDemandVo getQqchTotalDemandList(QqchTotalDemand qqchTotalDemand) {
        QqchTotalDemandVo vo = new QqchTotalDemandVo();
        BigDecimal version = qqchTotalDemand.getVersion();
        version = VersionUtil.getVersion("qqch_measuring_instrument", version);
        qqchTotalDemand.setVersion(version);
        List<QqchTotalDemand> qqchTotalDemandList = qqchTotalDemandMapper.getQqchTotalDemandList(qqchTotalDemand);
        QqchTotalDemandTimeCount qqchTotalDemandTimeCount = new QqchTotalDemandTimeCount();
        qqchTotalDemandTimeCount.setVersion(version);
        List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountList = qqchTotalDemandTimeCountService.getQqchTotalDemandTimeCountList(qqchTotalDemandTimeCount);
        Map<Long, List<QqchTotalDemandTimeCount>> timeCountMap = qqchTotalDemandTimeCountList.stream().collect(Collectors.groupingBy(QqchTotalDemandTimeCount::getDemandId));
        for (QqchTotalDemand totalDemand : qqchTotalDemandList) {
            //是否优先进场:0-否;1-是
            String firstEnterFlag = totalDemand.getFirstEnterFlag();
            if (StringUtils.isNotEmpty(firstEnterFlag) && firstEnterFlag.equals("0")){
                totalDemand.setFirstEnterFlagBool(false);
            }else {
                totalDemand.setFirstEnterFlagBool(true);
            }
            List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCounts = timeCountMap.get(totalDemand.getId());
            totalDemand.setQqchTotalDemandTimeCountList(qqchTotalDemandTimeCounts);
        }
        vo.setVersion(version);
        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setQqchTotalDemandList(qqchTotalDemandList);
        return vo;
    }


    /**
     *  新增接口
     * @param qqchTotalDemandVo
     */
    @Override
    @Transactional
    public void save(QqchTotalDemandVo qqchTotalDemandVo) {
        String buttonMark = qqchTotalDemandVo.getButtonMark();
        ButtonMarkUtil.checkButtonMark(buttonMark);

        BigDecimal version = qqchTotalDemandVo.getVersion();
        List<QqchTotalDemand> qqchTotalDemandList = qqchTotalDemandVo.getQqchTotalDemandList();

        this.insertQqchTotalDemandList(qqchTotalDemandList, version);

        //判断是否是确认
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            //插入确认记录
            String menuId = qqchTotalDemandVo.getMenuId();
            String stageIdentity = qqchTotalDemandVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    public void insertQqchTotalDemandList(List<QqchTotalDemand> qqchTotalDemandList, BigDecimal version) {
        //删除旧数据
        QqchTotalDemand qqchTotalDemand = new QqchTotalDemand();
        qqchTotalDemand.setVersion(version);
        QqchTotalDemand demand = qqchTotalDemandMapper.getQqchTotalDemand(qqchTotalDemand);
        if(demand!=null){
            QqchTotalDemandTimeCount qqchTotalDemandTimeCount = new QqchTotalDemandTimeCount();
            qqchTotalDemandTimeCount.setDemandId(demand.getId());
            qqchTotalDemandTimeCountService.deleteQqchTotalDemandTimeCount(qqchTotalDemandTimeCount);
            qqchTotalDemandMapper.deleteQqchTotalDemand(qqchTotalDemand);
        }

        if (CollectionUtils.isEmpty(qqchTotalDemandList)) {
            return;
        }
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        for (QqchTotalDemand totalDemand : qqchTotalDemandList) {
            totalDemand.setId(IdWorker.createId());
            totalDemand.setValid(valid);
            totalDemand.setVersion(version);
            totalDemand.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            totalDemand.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            totalDemand.setCreateTime(DateUtils.getNowDate());
            List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountList = totalDemand.getQqchTotalDemandTimeCountList();

            if(CollectionUtils.isNotEmpty(qqchTotalDemandTimeCountList)){
                for (QqchTotalDemandTimeCount qqchTotalDemandTimeCount : qqchTotalDemandTimeCountList) {
                    qqchTotalDemandTimeCount.setId(IdWorker.createId());
                    qqchTotalDemandTimeCount.setDemandId(totalDemand.getId());
                    qqchTotalDemandTimeCount.setValid(valid);
                    qqchTotalDemandTimeCount.setVersion(version);
                    qqchTotalDemandTimeCount.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                    qqchTotalDemandTimeCount.setCreateUserName(SecurityUtils.getSysUser().getNickName());
                    qqchTotalDemandTimeCount.setCreateTime(DateUtils.getNowDate());
                }
                qqchTotalDemandTimeCountService.insertQqchTotalDemandTimeCountList(qqchTotalDemandTimeCountList);
            }
        }
        qqchTotalDemandMapper.insertQqchTotalDemandList(qqchTotalDemandList);
    }

    @Transactional
    public int insertQqchTotalDemand(QqchTotalDemand qqchTotalDemand) {
        qqchTotalDemand.setId(IdWorker.createId());
        qqchTotalDemand.setCreateUser(SecurityUtils.getUserName());
        qqchTotalDemand.setCreateTime(DateUtils.getNowDate());
        return qqchTotalDemandMapper.insertQqchTotalDemand(qqchTotalDemand);
    }



    @Transactional
    public int updateQqchTotalDemand(QqchTotalDemand qqchTotalDemand) {
        qqchTotalDemand.setUpdateUser(SecurityUtils.getUserName());
        qqchTotalDemand.setUpdateTime(DateUtils.getNowDate());
        return qqchTotalDemandMapper.updateQqchTotalDemand(qqchTotalDemand);
    }

    @Transactional
    public int updateQqchTotalDemandList(List<QqchTotalDemand> qqchTotalDemandList) {
        for (QqchTotalDemand qqchTotalDemand : qqchTotalDemandList) {
            qqchTotalDemand.setUpdateUser(SecurityUtils.getUserName());
            qqchTotalDemand.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchTotalDemandMapper.updateQqchTotalDemandList(qqchTotalDemandList);
    }

    @Transactional
    public int deleteQqchTotalDemand(QqchTotalDemand qqchTotalDemand) {
        qqchTotalDemand.setUpdateUser(SecurityUtils.getUserName());
        qqchTotalDemand.setUpdateTime(DateUtils.getNowDate());
        return qqchTotalDemandMapper.deleteQqchTotalDemand(qqchTotalDemand);
    }

    @Transactional
    public int deleteQqchTotalDemandByPks(List<Long> qqchTotalDemandPkList) {
        return qqchTotalDemandMapper.deleteQqchTotalDemandByPks(qqchTotalDemandPkList);
    }


}
