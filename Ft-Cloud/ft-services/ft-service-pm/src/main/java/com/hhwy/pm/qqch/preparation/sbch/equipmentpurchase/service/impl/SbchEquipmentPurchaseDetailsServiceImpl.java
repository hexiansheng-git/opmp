package com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.domain.SbchEquipmentPurchaseDetails;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.mapper.SbchEquipmentPurchaseDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.equipmentpurchase.service.ISbchEquipmentPurchaseDetailsService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.dict.DictUtil;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 设备申购管理详情Service业务层处理
 * 
 * @author hwj
 * @date 2022-11-22
 */
@Service
public class SbchEquipmentPurchaseDetailsServiceImpl implements ISbchEquipmentPurchaseDetailsService {
    @Autowired
    private SbchEquipmentPurchaseDetailsMapper sbchEquipmentPurchaseDetailsMapper;

    /**
     * 查询设备申购管理详情
     *
     * @param id 设备申购管理详情ID
     * @return 设备申购管理详情
     */
    @Override
    public SbchEquipmentPurchaseDetails selectSbchEquipmentPurchaseDetailsById(Long id) {
        return sbchEquipmentPurchaseDetailsMapper.selectSbchEquipmentPurchaseDetailsById(id);
    }

    /**
     * 查询设备申购管理详情列表
     * 
     * @param sbchEquipmentPurchaseDetails 设备申购管理详情
     * @return 设备申购管理详情
     */
    @Override
    public List<SbchEquipmentPurchaseDetails> selectSbchEquipmentPurchaseDetailsList(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails) {
        List<SbchEquipmentPurchaseDetails> sbchEquipmentPurchaseDetails1 = sbchEquipmentPurchaseDetailsMapper.selectSbchEquipmentPurchaseDetailsList(sbchEquipmentPurchaseDetails);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentPurchaseDetails1)){
            LinkedHashMap<String, String> purchaseSource = DictUtil.getDictData("purchase_source");
            for (SbchEquipmentPurchaseDetails equipmentPurchaseDetails : sbchEquipmentPurchaseDetails1) {
                equipmentPurchaseDetails.setSbPurchaseSourceStr(purchaseSource.get(equipmentPurchaseDetails.getSbPurchaseSource()));
            }
        }
        return sbchEquipmentPurchaseDetails1;
    }

    /**
     * 新增设备申购管理详情
     * 
     * @param sbchEquipmentPurchaseDetails 设备申购管理详情
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentPurchaseDetails(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails) {
        sbchEquipmentPurchaseDetails= new AddBaseInfoUtil<SbchEquipmentPurchaseDetails>().add(sbchEquipmentPurchaseDetails);
        return sbchEquipmentPurchaseDetailsMapper.insertSbchEquipmentPurchaseDetails(sbchEquipmentPurchaseDetails);
    }

    /**
     * 修改设备申购管理详情
     * 
     * @param sbchEquipmentPurchaseDetails 设备申购管理详情
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentPurchaseDetails(SbchEquipmentPurchaseDetails sbchEquipmentPurchaseDetails) {
        sbchEquipmentPurchaseDetails= new AddBaseInfoUtil<SbchEquipmentPurchaseDetails>().update(sbchEquipmentPurchaseDetails);
        return sbchEquipmentPurchaseDetailsMapper.updateSbchEquipmentPurchaseDetails(sbchEquipmentPurchaseDetails);
    }

    /**
     * 删除设备申购管理详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentPurchaseDetailsByIds(String ids) {
        return sbchEquipmentPurchaseDetailsMapper.deleteSbchEquipmentPurchaseDetailsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备申购管理详情信息
     * 
     * @param id 设备申购管理详情ID
     * @return 结果
     */
    public int deleteSbchEquipmentPurchaseDetailsById(Long id) {
        return sbchEquipmentPurchaseDetailsMapper.deleteSbchEquipmentPurchaseDetailsById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrEditBatchByMainId(List<SbchEquipmentPurchaseDetails> detailList, Long mainId,Boolean isAdjust) {
//        if (CollectionUtils.isEmpty(detailList))
//            throw new CustomBusinessException(CustomBusinessException.ErrorCodes.Error, "物资详情不能为空");
//        SbchEquipmentPurchaseDetails details = new SbchEquipmentPurchaseDetails();
//        details.setDelFlag("1");
//        details.setDelTime(DateUtils.getNowDate());
//        details.setDelUser(SecurityUtils.getUserId().toString());
//        details.setMainId(mainId);
//        // 先将之前数据都进行删除
//        sbchEquipmentPurchaseDetailsMapper.deleteSbchEquipmentPurchaseDetailsByMainId(details);

        sbchEquipmentPurchaseDetailsMapper.deleteSbchEquipmentPurchaseDetailsByMainId(mainId);

        if (detailList != null && detailList.size() > 0) {
            // 集合类型转化 设置id 设置purchaseId
            List<SbchEquipmentPurchaseDetails> insertOrUpdateData = detailList.stream().map(item -> {
                Long id = IdWorker.createId();
//                /*不是调整*/
//                if (!isAdjust) {
//                    id = item.getId() == null ? IdWorker.createId() : item.getId();
//                }
                item.setPtVar2(item.getCategoryCode());
                item.setPtVar3(item.getCategoryName());
                item.setId(id);
                item.setMainId(mainId);
                EntityUtils.setCreateUpdateInfo(item);
                return item;

            }).collect(Collectors.toList());
            return sbchEquipmentPurchaseDetailsMapper.batchInsert(insertOrUpdateData);
        }
        return 1;
    }
}
