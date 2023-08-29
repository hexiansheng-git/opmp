package com.hhwy.pm.qqch.preparation.sbch.sblease.service.impl;

import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllot;
import com.hhwy.pm.qqch.preparation.sbch.sblease.domain.*;
import com.hhwy.pm.qqch.preparation.sbch.sblease.mapper.SbchEquipmentLeaseDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sblease.mapper.SbchEquipmentLeaseMapper;
import com.hhwy.pm.qqch.preparation.sbch.sblease.mapper.SbchEquipmentSupplierDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sblease.mapper.SbchEquipmentSupplierMapper;
import com.hhwy.pm.qqch.preparation.sbch.sblease.service.ISbchEquipmentLeaseDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.sblease.service.ISbchEquipmentService;
import com.hhwy.pm.qqch.preparation.sbch.sblease.service.ISbchEquipmentSupplierDetailsService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myUtilPrepare.MyUtilPrepareUtil;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zqq
 * @create 2023-08-26 16:46
 */
@Service
public class ISbchEquipmentServiceImpl implements ISbchEquipmentService {
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private GenCodeService genCodeService;

    @Autowired
    private SbchEquipmentSupplierMapper sbchEquipmentSupplierMapper;
    @Autowired
    private SbchEquipmentLeaseMapper sbchEquipmentLeaseMapper;
    @Autowired
    private SbchEquipmentSupplierDetailsMapper sbchEquipmentSupplierDetailsMapper;
    @Autowired
    private SbchEquipmentLeaseDetailsMapper sbchEquipmentLeaseDetailsMapper;
    @Autowired
    private ISbchEquipmentSupplierDetailsService sbchEquipmentSupplierDetailsService;
    @Autowired
    private ISbchEquipmentLeaseDetailsService sbchEquipmentLeaseDetailsService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Override
    public LeaseVo getList(BigDecimal version) {
        LeaseVo returnVo = new LeaseVo();
        LeaseVo leaseVo = new LeaseVo();
        version = VersionUtil.getVersion("sbch_equipment_supplier", version);//因为设备租赁供应商调查表和租赁设备信息调查现在是一个接口 所以两个主表的version一定是同步的
        SbchEquipmentLease sbchEquipmentLease = new SbchEquipmentLease();
        sbchEquipmentLease.setVersionCode(version);
        List<SbchEquipmentLease> sbchEquipmentLeases = sbchEquipmentLeaseMapper.selectSbchEquipmentLeaseList(sbchEquipmentLease);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentLeases)){
            SbchEquipmentLease sbchEquipmentLease1 = sbchEquipmentLeases.get(0);
            BeanUtils.copyProperties(sbchEquipmentLease1,returnVo);
            SbchEquipmentLeaseDetails sbchEquipmentLeaseDetails = new SbchEquipmentLeaseDetails();
            sbchEquipmentLeaseDetails.setMainId(sbchEquipmentLease1.getId());
            List<SbchEquipmentLeaseDetails> sbchEquipmentLeaseDetails1 = sbchEquipmentLeaseDetailsMapper.selectSbchEquipmentLeaseDetailsList(sbchEquipmentLeaseDetails);
            returnVo.setLeaseDetailsList(sbchEquipmentLeaseDetails1);
        }

        SbchEquipmentSupplier sbchEquipmentSupplier = new SbchEquipmentSupplier();
        sbchEquipmentSupplier.setVersionCode(version);
        List<SbchEquipmentSupplier> sbchEquipmentSuppliers = sbchEquipmentSupplierMapper.selectSbchEquipmentSupplierList(sbchEquipmentSupplier);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentSuppliers)){
            SbchEquipmentSupplier sbchEquipmentSupplier1 = sbchEquipmentSuppliers.get(0);

            SbchEquipmentSupplierDetails sbchEquipmentSupplierDetails = new SbchEquipmentSupplierDetails();
            sbchEquipmentSupplierDetails.setMainId(sbchEquipmentSupplier1.getId());
            List<SbchEquipmentSupplierDetails> sbchEquipmentSupplierDetails1 = sbchEquipmentSupplierDetailsMapper.selectSbchEquipmentSupplierDetailsList(sbchEquipmentSupplierDetails);
            if(!ObjectNullUtil.isEmpty(sbchEquipmentSupplierDetails1)){
                //set country信息
            }
            returnVo.setSupplierList(sbchEquipmentSupplierDetails1);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    public void batchAdd(LeaseVo leaseVo) {
        //处理设备租赁供应商调查表
        SbchEquipmentSupplier sbchEquipmentSupplier = new SbchEquipmentSupplier();
        sbchEquipmentSupplier.setVersionCode(leaseVo.getVersion());
        List<SbchEquipmentSupplier> sbchEquipmentSuppliers = sbchEquipmentSupplierMapper.selectSbchEquipmentSupplierList(sbchEquipmentSupplier);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentSuppliers)){
            SbchEquipmentSupplier sbchEquipmentSupplier1 = sbchEquipmentSuppliers.get(0);
            BeanUtils.copyProperties(sbchEquipmentSupplier1,sbchEquipmentSupplier);
            MyUtilPrepareUtil.setUpdateInfoBase(sbchEquipmentSupplier);
            sbchEquipmentSupplierMapper.updateSbchEquipmentSupplier(sbchEquipmentSupplier);
        }else{
            BeanUtils.copyProperties(leaseVo,sbchEquipmentSupplier);
            sbchEquipmentSupplier.setId(IdWorker.createId());
            String setCode = genCodeService.getSetCode(CodeEnum.EQU_SUPPLIER);
            sbchEquipmentSupplier.setUnicode(setCode);
            sbchEquipmentSupplier.setTitleName("租赁供应商调查表");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchEquipmentSupplier);
            // 设置单据编码
            sbchEquipmentSupplier.setTitle(sbchEquipmentSupplier.getTitleName());
            sbchEquipmentSupplier.setVersionCode(sbchEquipmentSupplier.getVersion());
            sbchEquipmentSupplier.setAdjustCode(setCode+"-"+sbchEquipmentSupplier.getVersionCode().setScale(0));
            // 新增
            sbchEquipmentSupplierMapper.insertSbchEquipmentSupplier(sbchEquipmentSupplier);
        }
        //处理租赁设备信息调查
        SbchEquipmentLease sbchEquipmentLease = new SbchEquipmentLease();
        sbchEquipmentLease.setVersionCode(leaseVo.getVersion());
        List<SbchEquipmentLease> sbchEquipmentLeases = sbchEquipmentLeaseMapper.selectSbchEquipmentLeaseList(sbchEquipmentLease);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentLeases)){
            SbchEquipmentLease sbchEquipmentLease1 = sbchEquipmentLeases.get(0);
            BeanUtils.copyProperties(sbchEquipmentLease1,sbchEquipmentLease);
            MyUtilPrepareUtil.setUpdateInfoBase(sbchEquipmentLease);
            sbchEquipmentLeaseMapper.updateSbchEquipmentLease(sbchEquipmentLease);
        }else{
            BeanUtils.copyProperties(leaseVo,sbchEquipmentLease);
            sbchEquipmentLease.setId(IdWorker.createId());
            String setCode = genCodeService.getSetCode(CodeEnum.EQU_LEASE);
            sbchEquipmentLease.setUnicode(setCode);
            sbchEquipmentLease.setTitleName("租赁设备信息调查");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchEquipmentLease);
            // 设置单据编码
            sbchEquipmentLease.setTitle(sbchEquipmentLease.getTitleName());
            sbchEquipmentLease.setVersionCode(sbchEquipmentLease.getVersion());
            sbchEquipmentLease.setAdjustCode(setCode+"-"+sbchEquipmentLease.getVersionCode().setScale(0));
            sbchEquipmentLeaseMapper.insertSbchEquipmentLease(sbchEquipmentLease);
        }

        List<SbchEquipmentSupplierDetails> supplierList = leaseVo.getSupplierList();
        if(!ObjectNullUtil.isEmpty(supplierList)){
            JyDetailsUtil.jyDetails(supplierList, ValidationGroups.Save.class);
            sbchEquipmentSupplierDetailsService.insertOrEditBatchByMainId(supplierList, sbchEquipmentSupplier.getId(), false);
        }
        List<SbchEquipmentLeaseDetails> leaseDetailsList = leaseVo.getLeaseDetailsList();
        if(!ObjectNullUtil.isEmpty(leaseDetailsList)){
            JyDetailsUtil.jyDetails(leaseDetailsList, ValidationGroups.Save.class);
            sbchEquipmentLeaseDetailsService.insertOrEditBatchByMainId(leaseDetailsList, sbchEquipmentLease.getId(), false);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(leaseVo.getButtonMark())){
            //插入确认记录
            String menuId = leaseVo.getMenuId();
            String stageIdentity = leaseVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
    }
}
