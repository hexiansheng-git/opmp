package com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchase;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchaseDetails;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.dto.SbchEquipmentPurchaseDTO;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.mapper.SbchEquipmentPurchaseMapper;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.service.ISbchEquipmentPurchaseDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.service.ISbchEquipmentPurchaseService;
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
public class SbchEquipmentPurchaseServiceImpl implements ISbchEquipmentPurchaseService {
    @Autowired
    private SbchEquipmentPurchaseMapper sbchEquipmentPurchaseMapper;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private ISbchEquipmentPurchaseDetailsService detailsService;
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
     * 查询设备申购管理
     * 
     * @param id 设备申购管理ID
     * @return 设备申购管理
     */
    @Override
    public SbchEquipmentPurchase selectSbchEquipmentPurchaseById(Long id) {
        return sbchEquipmentPurchaseMapper.selectSbchEquipmentPurchaseById(id);
    }

    /**
     * 查询设备申购管理列表
     * 
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 设备申购管理
     */
    @SelfEmpty(clazz = SbchEquipmentPurchase.class)
    @Override
    public SbchEquipmentPurchase selectSbchEquipmentPurchaseList(BigDecimal version) {
        SbchEquipmentPurchase sbchEquipmentPurchase = new SbchEquipmentPurchase();
        version = VersionUtil.getVersion("sbch_equipment_purchase", version);
        sbchEquipmentPurchase.setVersion(version);
        List<SbchEquipmentPurchase> sbchEquipmentPurchases = sbchEquipmentPurchaseMapper.selectSbchEquipmentPurchaseList(sbchEquipmentPurchase);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentPurchases)){

            SbchEquipmentPurchase sbchEquipmentPurchase1 = sbchEquipmentPurchases.get(0);
            SbchEquipmentPurchaseDetails detailVo = new SbchEquipmentPurchaseDetails();
            detailVo.setMainId(sbchEquipmentPurchase1.getId());
            List<SbchEquipmentPurchaseDetails> sbchEquipmentPurchaseDetails = detailsService.selectSbchEquipmentPurchaseDetailsList(detailVo);
            //redis中获取设备name
            Map<String, String> busAndMaterialMap = new HashMap<>();
            busAndMaterialMap.put("materialName", "materialName");
            List<SbchEquipmentPurchaseDetails> detailsListVersion1 = setMaterialNameUtils.setMaterialInfo(sbchEquipmentPurchaseDetails, "materialCode", busAndMaterialMap);
            sbchEquipmentPurchase.setDetailsList(detailsListVersion1);
        }
        sbchEquipmentPurchase.setVersion(version);
        sbchEquipmentPurchase.setStageIdentity(qqchReviewService.getStage());
        return sbchEquipmentPurchase;
    }

    /**
     * 新增设备申购管理和详情
     * 
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    @Override
    @Transactional
    public String insertSbchEquipmentPurchaseAndDetails(SbchEquipmentPurchase sbchEquipmentPurchase) {
        // 获取前端传入的设备明细
        List<SbchEquipmentPurchaseDetails> detailList = sbchEquipmentPurchase.getDetailsList();
        SbchEquipmentPurchase temp = new SbchEquipmentPurchase();
        temp.setVersionCode(sbchEquipmentPurchase.getVersion());
        List<SbchEquipmentPurchase> sbchEquipmentPurchases = sbchEquipmentPurchaseMapper.selectSbchEquipmentPurchaseList(temp);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentPurchases)){
            sbchEquipmentPurchase.setId(sbchEquipmentPurchases.get(0).getId());
            MyUtilPrepareUtil.setUpdateInfoBase(sbchEquipmentPurchase);
            sbchEquipmentPurchaseMapper.updateSbchEquipmentPurchase(sbchEquipmentPurchase);
        }else{
            sbchEquipmentPurchase.setId(IdWorker.createId());
            String setCode = genCodeService.getSetCode(CodeEnum.EQU_PERCHASE);
            sbchEquipmentPurchase.setUnicode(setCode);
            sbchEquipmentPurchase.setTitleName("设备申购");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchEquipmentPurchase);
            // 设置单据编码
            sbchEquipmentPurchase.setTitle(sbchEquipmentPurchase.getTitleName());
            sbchEquipmentPurchase.setVersionCode(sbchEquipmentPurchase.getVersion());
            sbchEquipmentPurchase.setAdjustCode(setCode+"-"+sbchEquipmentPurchase.getVersionCode().setScale(0));
            // 新增
            this.sbchEquipmentPurchaseMapper.insertSbchEquipmentPurchase(sbchEquipmentPurchase);
        }

        if(!ObjectNullUtil.isEmpty(detailList)){
            JyDetailsUtil.jyDetails(detailList, ValidationGroups.Save.class);
            // 明细
            detailsService.insertOrEditBatchByMainId(detailList, sbchEquipmentPurchase.getId(), false);
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

    /**
     * 修改设备申购管理和详情
     * 
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSbchEquipmentPurchaseAndDetails(SbchEquipmentPurchaseDTO sbchEquipmentPurchase) {
//        EntityUtils.setUpdateInfo(sbchEquipmentPurchase);
        // 获取前端传入的设备明细
        List<SbchEquipmentPurchaseDetails> detailList = sbchEquipmentPurchase.getDetailsList();
        Boolean aBoolean = JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);
        // 修改
        sbchEquipmentPurchaseMapper.updateSbchEquipmentPurchase(sbchEquipmentPurchase);
        // 明细
        return detailsService.insertOrEditBatchByMainId(detailList,sbchEquipmentPurchase.getId(),false);
    }

    /**
     * 修改设备申购管理
     *
     * @param sbchEquipmentPurchase 设备申购管理
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentPurchase(SbchEquipmentPurchase sbchEquipmentPurchase) {
//        sbchEquipmentPurchase= new AddBaseInfoUtil<SbchEquipmentPurchase>().update(sbchEquipmentPurchase);
        // 修改
        return sbchEquipmentPurchaseMapper.updateSbchEquipmentPurchase(sbchEquipmentPurchase);
    }

    /**
     * 调整
     *
     * @param sbchEquipmentPurchase
     * @return
     */
    @Override
    @Transactional
    public String adjustSbchEquipmentPurchaseAndDetails(SbchEquipmentPurchaseDTO sbchEquipmentPurchase) {
        Long id = IdWorker.createId();
        // 设置id
        sbchEquipmentPurchase.setId(id);
        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(sbchEquipmentPurchase);

        //置为无效
        sbchEquipmentPurchase.setValid("0");

        // 获取前端传入的物资明细
        List<SbchEquipmentPurchaseDetails> detailList = sbchEquipmentPurchase.getDetailsList();
        Boolean aBoolean = JyDetailsUtil.jyDetails(detailList, ValidationGroups.Update.class);
        // 新增或者编辑
        this.sbchEquipmentPurchaseMapper.insertSbchEquipmentPurchase(sbchEquipmentPurchase);
        // 明细
        detailsService.insertOrEditBatchByMainId(detailList, sbchEquipmentPurchase.getId(),true);
        return id.toString();
    }

    /**
     * 删除设备申购管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentPurchaseByIds(String ids) {
        return sbchEquipmentPurchaseMapper.deleteSbchEquipmentPurchaseByIds(Convert.toStrArray(ids), SecurityUtils.getUserId().toString());
    }

    /**
     * 删除设备申购管理信息
     * 
     * @param id 设备申购管理ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentPurchaseById(Long id) {
        return sbchEquipmentPurchaseMapper.deleteSbchEquipmentPurchaseById(id);
    }
}
