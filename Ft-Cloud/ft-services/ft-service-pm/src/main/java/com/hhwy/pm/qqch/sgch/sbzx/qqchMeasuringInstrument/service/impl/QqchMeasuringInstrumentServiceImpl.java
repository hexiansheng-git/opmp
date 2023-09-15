package com.hhwy.pm.qqch.sgch.sbzx.qqchMeasuringInstrument.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstFacilityPlan;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstFacilityPlanService;
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

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Resource
    private IQqchConstFacilityPlanService facilityPlanService;


    public QqchMeasuringInstrument getQqchMeasuringInstrument(QqchMeasuringInstrument qqchMeasuringInstrument) {
        return qqchMeasuringInstrumentMapper.getQqchMeasuringInstrument(qqchMeasuringInstrument);
    }

    /***
     * 功能描述: 拉取施工部署数据
     * 作者: fushudong
     * 时间: 2023/8/17
     */
    @Override
    public QqchMeasuringInstrumentVo syncData(QqchMeasuringInstrumentVo param) {
        //保存界面已有数据
        BigDecimal version = param.getVersion();
        List<QqchMeasuringInstrument> paramList = param.getQqchMeasuringInstrumentList();
        if (CollectionUtils.isEmpty(paramList)){
            QqchMeasuringInstrument qqchMeasuringInstrument = new QqchMeasuringInstrument();
            qqchMeasuringInstrument.setVersion(param.getVersion());
            return this.getQqchMeasuringInstrumentList(qqchMeasuringInstrument);
        }
        //按设备编号分组，用于判断是否已存在
        Map<String, List<QqchMeasuringInstrument>> collect = new HashMap<>();
        if (CollectionUtils.isNotEmpty(paramList)) {
            collect = paramList.stream().collect(Collectors.groupingBy(QqchMeasuringInstrument::getEquCode));
        }
        //获取设备策划数据
        List<QqchConstFacilityPlan> facilityPlanList = this.facilityPlanService.list(CompileEntity.dealListDto(version, new QqchConstFacilityPlan()));
        String valid = Valid.NO;
        if (version.compareTo(BigDecimal.ONE) == 0) {
            valid = Valid.YES;
        }
        //入库数据拼装
        List<QqchMeasuringInstrument> saveList = new ArrayList<>();
        for (QqchConstFacilityPlan constFacilityPlan : facilityPlanList) {
            if (collect.containsKey(constFacilityPlan.getFacilityCode())){
                //只新增数据
                continue;
            }
            //试验测量仪器 实体
            QqchMeasuringInstrument measuringInstrument = new QqchMeasuringInstrument();
            measuringInstrument.setEquCode(constFacilityPlan.getFacilityCode());
            measuringInstrument.setEquName(constFacilityPlan.getFacilityName());
            measuringInstrument.setUnit(constFacilityPlan.getUnits());
            measuringInstrument.setNum(BigDecimal.valueOf(constFacilityPlan.getCount()));
            measuringInstrument.setSpec(constFacilityPlan.getSpecificationModel());
            measuringInstrument.setId(IdWorker.createId());
            measuringInstrument.setValid(valid);
            measuringInstrument.setVersion(version);
            measuringInstrument.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            measuringInstrument.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            measuringInstrument.setCreateTime(DateUtils.getNowDate());
            saveList.add(measuringInstrument);
        }
        this.insertQqchMeasuringInstrumentList(paramList, version);
        if (CollectionUtils.isEmpty(saveList)){
            QqchMeasuringInstrument qqchMeasuringInstrument = new QqchMeasuringInstrument();
            qqchMeasuringInstrument.setVersion(param.getVersion());
            return this.getQqchMeasuringInstrumentList(qqchMeasuringInstrument);
        }
        qqchMeasuringInstrumentMapper.insertQqchMeasuringInstrumentList(saveList);
        QqchMeasuringInstrument qqchMeasuringInstrument = new QqchMeasuringInstrument();
        qqchMeasuringInstrument.setVersion(param.getVersion());
        return this.getQqchMeasuringInstrumentList(qqchMeasuringInstrument);
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
