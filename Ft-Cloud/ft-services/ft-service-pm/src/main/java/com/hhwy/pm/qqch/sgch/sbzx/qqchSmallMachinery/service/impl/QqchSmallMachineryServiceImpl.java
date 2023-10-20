package com.hhwy.pm.qqch.sgch.sbzx.qqchSmallMachinery.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstFacilityPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstFacilityPlanService;
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

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Resource
    private IQqchConstFacilityPlanService facilityPlanService;

                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    public QqchSmallMachinery getQqchSmallMachinery(QqchSmallMachinery qqchSmallMachinery) {
        return qqchSmallMachineryMapper.getQqchSmallMachinery(qqchSmallMachinery);
    }

    /***
     * 功能描述: 拉取施工部署数据
     * 作者: fushudong
     * 时间: 2023/8/17
     */
    @Override
    public QqchSmallMachineryVo syncData(QqchSmallMachineryVo param) {
        BigDecimal version = param.getVersion();
        List<QqchSmallMachinery> paramList = param.getQqchSmallMachineryList();
//        if (CollectionUtils.isEmpty(paramList)){
//            QqchSmallMachinery qqchSmallMachinery = new QqchSmallMachinery();
//            qqchSmallMachinery.setVersion(param.getVersion());
//            return this.getQqchSmallMachineryList(qqchSmallMachinery);
//        }
        //按设备编号分组，用于判断是否已存在
        Map<String, List<QqchSmallMachinery>> collect = new HashMap<>();
        if (CollectionUtils.isNotEmpty(paramList)) {
            collect = paramList.stream().collect(Collectors.groupingBy(QqchSmallMachinery::getEquCode));
        }
        //获取设备策划数据
        List<QqchConstFacilityPlan> facilityPlanList = this.facilityPlanService.list(CompileEntity.dealListDto(version, new QqchConstFacilityPlan()));
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        //入库数据拼装
        List<QqchSmallMachinery> saveList = new ArrayList<>();
        for (QqchConstFacilityPlan constFacilityPlan : facilityPlanList) {
            if (collect.containsKey(constFacilityPlan.getFacilityCode())){
                //只入库新增的数据
                continue;
            }
            //小型机具 实体
            QqchSmallMachinery smallMachinery = new QqchSmallMachinery();
            smallMachinery.setEquType(constFacilityPlan.getPtVar2());
            smallMachinery.setPtVar1(constFacilityPlan.getPtVar1());
            smallMachinery.setEquCode(constFacilityPlan.getFacilityCode());
            smallMachinery.setEquName(constFacilityPlan.getFacilityName());
            smallMachinery.setUnit(constFacilityPlan.getUnits());
            smallMachinery.setNum(NumberUtil.toBigDecimal(constFacilityPlan.getCount()));
            smallMachinery.setSpec(constFacilityPlan.getSpecificationModel());
            smallMachinery.setId(IdWorker.createId());
            smallMachinery.setValid(valid);
            smallMachinery.setVersion(version);
            smallMachinery.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            smallMachinery.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            smallMachinery.setCreateTime(DateUtils.getNowDate());
            saveList.add(smallMachinery);
        }
        //保存界面已有数据
        this.insertQqchMeasuringInstrumentList(paramList, version);
        if (CollectionUtils.isEmpty(saveList)){
            QqchSmallMachinery qqchSmallMachinery = new QqchSmallMachinery();
            qqchSmallMachinery.setVersion(param.getVersion());
            return this.getQqchSmallMachineryList(qqchSmallMachinery);
        }
        //保存新增的数据
        qqchSmallMachineryMapper.insertQqchSmallMachineryList(saveList);
        QqchSmallMachinery qqchSmallMachinery = new QqchSmallMachinery();
        qqchSmallMachinery.setVersion(param.getVersion());
        return this.getQqchSmallMachineryList(qqchSmallMachinery);
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
            smallMachinery.setId(IdWorker.createId());
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
