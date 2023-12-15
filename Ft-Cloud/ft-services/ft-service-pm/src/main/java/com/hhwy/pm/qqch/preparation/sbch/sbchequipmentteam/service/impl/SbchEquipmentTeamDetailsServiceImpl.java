package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;

import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.domain.SbchEquipmentTeamDetailsDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.mapper.SbchEquipmentTeamDetailsDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.mapper.SbchEquipmentTeamDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentteam.service.ISbchEquipmentTeamDetailsService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 协作单位详情Service业务层处理
 * 
 * @author hwj
 * @date 2022-11-30
 */
@Service
public class SbchEquipmentTeamDetailsServiceImpl implements ISbchEquipmentTeamDetailsService {
    @Autowired
    private SbchEquipmentTeamDetailsMapper sbchEquipmentTeamDetailsMapper;
    @Autowired
    private SbchEquipmentTeamDetailsDetailsMapper detailsDetailsMapper;

    /**
     * 查询协作单位详情
     * 
     * @param id 协作单位详情ID
     * @return 协作单位详情
     */
    @Override
    public SbchEquipmentTeamDetails selectSbchEquipmentTeamDetailsById(Long id) {
        return sbchEquipmentTeamDetailsMapper.selectSbchEquipmentTeamDetailsById(id);
    }

    /**
     * 查询协作单位详情列表
     * 
     * @param sbchEquipmentTeamDetails 协作单位详情
     * @return 协作单位详情
     */
    @Override
    public List<SbchEquipmentTeamDetails> selectSbchEquipmentTeamDetailsList(SbchEquipmentTeamDetails sbchEquipmentTeamDetails) {
        return sbchEquipmentTeamDetailsMapper.selectSbchEquipmentTeamDetailsList(sbchEquipmentTeamDetails);
    }

    /**
     * 新增协作单位详情
     * 
     * @param sbchEquipmentTeamDetails 协作单位详情
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentTeamDetails(SbchEquipmentTeamDetails sbchEquipmentTeamDetails) {

    sbchEquipmentTeamDetails.setId(IdWorker.createId());

        sbchEquipmentTeamDetails.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentTeamDetailsMapper.insertSbchEquipmentTeamDetails(sbchEquipmentTeamDetails);
    }

    /**
     * 修改协作单位详情
     * 
     * @param sbchEquipmentTeamDetails 协作单位详情
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentTeamDetails(SbchEquipmentTeamDetails sbchEquipmentTeamDetails) {
        sbchEquipmentTeamDetails.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentTeamDetailsMapper.updateSbchEquipmentTeamDetails(sbchEquipmentTeamDetails);
    }

    /**
     * 删除协作单位详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentTeamDetailsByIds(String ids) {
        return sbchEquipmentTeamDetailsMapper.deleteSbchEquipmentTeamDetailsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除协作单位详情信息
     * 
     * @param id 协作单位详情ID
     * @return 结果
     */
    public int deleteSbchEquipmentTeamDetailsById(Long id) {
        return sbchEquipmentTeamDetailsMapper.deleteSbchEquipmentTeamDetailsById(id);
    }

    /**
     * 更新详情信息
     *
     * @param isAdjust  是否调整
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrEditBatchByMainId(List<SbchEquipmentTeamDetails> detailList, Long mainId, Boolean isAdjust) {
        /*获取 数据库中原有的协作单位详情*/
        List<SbchEquipmentTeamDetails> detailsListOld = sbchEquipmentTeamDetailsMapper.selectSbchEquipmentTeamDetailsByMainId(mainId);
        if (detailsListOld!=null&&detailsListOld.size()>0) {
            for (int i = 0; i <detailsListOld.size(); i++) {
                /*删除 协作单位设备详情*/
                detailsDetailsMapper.deleteSbchEquipmentTeamDetailsDetailsByMainId(detailsListOld.get(i).getId());
            }
        }
        /*删除协作单位详情*/
        sbchEquipmentTeamDetailsMapper.deleteSbchEquipmentTeamDetailsByMainId(mainId);

        if (detailList != null && detailList.size() > 0) {
            // 集合类型转化 设置id 设置purchaseId
            List<SbchEquipmentTeamDetails> insertOrUpdateData = detailList.stream().map(item -> {
                Long id = IdWorker.createId();
                /*不是调整*/
//                if (!isAdjust) {
//                    id = item.getId() == null ? IdWorker.createId() : item.getId();
//                }
                item.setId(id);
                item.setMainId(mainId);
                EntityUtils.setCreateUpdateInfo(item);

                /*处理协作单位设备详情*/
                if (item.getDetailsDetailsList()!=null&&item.getDetailsDetailsList().size()>0) {
                    List<SbchEquipmentTeamDetailsDetails> detailsDetailsList=item.getDetailsDetailsList().stream().map(detailsDetails -> {
                        Long detailId = IdWorker.createId();
                        /*不是调整*/
//                        if (!isAdjust) {
//                            detailId = detailsDetails.getId() == null ? IdWorker.createId() : detailsDetails.getId();
//                        }
                        detailsDetails.setId(detailId);
                        detailsDetails.setMainId(item.getId());
                        detailsDetails.setPtVar2(detailsDetails.getCategoryCode());
                        detailsDetails.setPtVar3(detailsDetails.getCategoryName());
                        EntityUtils.setCreateUpdateInfo(detailsDetails);
                        return detailsDetails;
                    }).collect(Collectors.toList());
                    detailsDetailsMapper.batchInsert(detailsDetailsList);
                }

                return item;

            }).collect(Collectors.toList());
            sbchEquipmentTeamDetailsMapper.batchInsert(insertOrUpdateData);
        }
        return 1;
    }
}
