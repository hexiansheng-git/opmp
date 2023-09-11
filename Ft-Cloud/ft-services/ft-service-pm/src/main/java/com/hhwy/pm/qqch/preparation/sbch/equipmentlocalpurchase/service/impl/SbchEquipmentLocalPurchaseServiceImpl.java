package com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.service.impl;

import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.web.domain.AjaxResult;

import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.domain.SbchEquipmentLocalPurchase;
import com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.domain.SbchEquipmentLocalPurchaseDetails;
import com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.mapper.SbchEquipmentLocalPurchaseMapper;
import com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.service.ISbchEquipmentLocalPurchaseDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.equipmentlocalpurchase.service.ISbchEquipmentLocalPurchaseService;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchaseDetails;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.myUtilPrepare.MyUtilPrepareUtil;
import com.hhwy.utils.myUtilPrepare.SetMaterialNameUtils;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 设备申购管理Service业务层处理
 * 
 * @author hwj
 * @date 2022-11-22
 */
@Service
public class SbchEquipmentLocalPurchaseServiceImpl implements ISbchEquipmentLocalPurchaseService {
    @Autowired
    private SbchEquipmentLocalPurchaseMapper sbchEquipmentPurchaseMapper;

    @Autowired
    private GenCodeService genCodeService;

    @Autowired
    private ISbchEquipmentLocalPurchaseDetailsService detailsService;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private SetMaterialNameUtils setMaterialNameUtils;

    private final static String ONE = "1";
    private final static String TWO = "2";
    private final static String THREE = "3";
    private final static String FOUR = "4";

    /**
     * 查询设备申购管理列表
     * 
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 设备申购管理
     */
    @SelfEmpty(clazz = SbchEquipmentLocalPurchase.class)
    @Override
    //@CustomDatascope（alias = "c")
    public SbchEquipmentLocalPurchase selectSbchEquipmentPurchaseList(BigDecimal version) {
        SbchEquipmentLocalPurchase returnVo = new SbchEquipmentLocalPurchase();

        SbchEquipmentLocalPurchase sbchEquipmentLocalPurchase = new SbchEquipmentLocalPurchase();
        version = VersionUtil.getVersion("sbch_equipment_local_purchase", version);
        sbchEquipmentLocalPurchase.setVersion(version);
        List<SbchEquipmentLocalPurchase> sbchEquipmentLocalPurchases = sbchEquipmentPurchaseMapper.selectSbchEquipmentPurchaseList(sbchEquipmentLocalPurchase);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentLocalPurchases)){
            SbchEquipmentLocalPurchase sbchEquipmentLocalPurchase1 = sbchEquipmentLocalPurchases.get(0);
            returnVo = sbchEquipmentLocalPurchase1;
            SbchEquipmentLocalPurchaseDetails sbchEquipmentLocalPurchaseDetails = new SbchEquipmentLocalPurchaseDetails();
            sbchEquipmentLocalPurchaseDetails.setMainId(sbchEquipmentLocalPurchase1.getId());
            List<SbchEquipmentLocalPurchaseDetails> sbchEquipmentLocalPurchaseDetails1 = detailsService.selectSbchEquipmentPurchaseDetailsList(sbchEquipmentLocalPurchaseDetails);
            //redis中获取设备name
            Map<String, String> busAndMaterialMap = new HashMap<>();
            busAndMaterialMap.put("materialName", "materialName");
            List<SbchEquipmentLocalPurchaseDetails> detailsListVersion1 = setMaterialNameUtils.setMaterialInfo(sbchEquipmentLocalPurchaseDetails1, "materialCode", busAndMaterialMap);
            returnVo.setDetailsList(detailsListVersion1);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    /**
     * 新增设备申购管理和详情
     * 
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    @Override
    @Transactional
    public String insertSbchEquipmentPurchaseAndDetails(SbchEquipmentLocalPurchase sbchEquipmentPurchase) {
        // 获取前端传入的设备明细
        List<SbchEquipmentLocalPurchaseDetails> detailsList = sbchEquipmentPurchase.getDetailsList();
        SbchEquipmentLocalPurchase temp = new SbchEquipmentLocalPurchase();
        temp.setVersionCode(sbchEquipmentPurchase.getVersion());
        List<SbchEquipmentLocalPurchase> sbchEquipmentLocalPurchases = sbchEquipmentPurchaseMapper.selectSbchEquipmentPurchaseList(temp);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentLocalPurchases)){
            sbchEquipmentPurchase.setId(sbchEquipmentLocalPurchases.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchEquipmentPurchase);
            sbchEquipmentPurchaseMapper.updateSbchEquipmentPurchase(sbchEquipmentPurchase);
        }else{
            sbchEquipmentPurchase.setId(IdWorker.createId());
            String setCode = genCodeService.getSetCode(CodeEnum.EQU_LOCALPERCHASE);
            sbchEquipmentPurchase.setUnicode(setCode);
            sbchEquipmentPurchase.setTitleName("设备属地化采购");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchEquipmentPurchase);
            // 设置单据编码
            sbchEquipmentPurchase.setTitle(sbchEquipmentPurchase.getTitleName());
            sbchEquipmentPurchase.setVersionCode(sbchEquipmentPurchase.getVersion());
            sbchEquipmentPurchase.setAdjustCode(setCode+"-"+sbchEquipmentPurchase.getVersionCode().setScale(0));
            // 新增
            this.sbchEquipmentPurchaseMapper.insertSbchEquipmentPurchase(sbchEquipmentPurchase);
        }

        if(!ObjectNullUtil.isEmpty(detailsList)){
            JyDetailsUtil.jyDetails(detailsList, ValidationGroups.Save.class);
            // 明细
            detailsService.insertOrEditBatchByMainId(detailsList, sbchEquipmentPurchase.getId(), false);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(sbchEquipmentPurchase.getButtonMark())){
            //插入确认记录
            String menuId = sbchEquipmentPurchase.getMenuId();
            String stageIdentity = sbchEquipmentPurchase.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
        return sbchEquipmentPurchase.getId().toString();
    }
}
