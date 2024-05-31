package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlan.service.IJdglMainPlanService;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.QqchSafetyTrain;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.domain.vo.QqchSafetyTrainVo;
import com.hhwy.pm.qqch.preparation.quality.qqchSafetyTrain.service.IQqchSafetyTrainService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.domain.SbchEquipmentSpecialDetails;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.mapper.SbchEquipmentSpecialDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentspecial.service.ISbchEquipmentSpecialDetailsService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.objectUtil.ObjectNullUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 特种设备管理详情Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-05
 */
@Service
public class SbchEquipmentSpecialDetailsServiceImpl implements ISbchEquipmentSpecialDetailsService {
    @Autowired
    private SbchEquipmentSpecialDetailsMapper sbchEquipmentSpecialDetailsMapper;
    @Autowired
    private IQqchSafetyTrainService qqchSafetyTrainService;
    @Autowired
    private IJdglMainPlanService jdglMainPlanService;

    /**
     * 查询特种设备管理详情
     * 
     * @param id 特种设备管理详情ID
     * @return 特种设备管理详情
     */
    @Override
    public SbchEquipmentSpecialDetails selectSbchEquipmentSpecialDetailsById(Long id) {
        return sbchEquipmentSpecialDetailsMapper.selectSbchEquipmentSpecialDetailsById(id);
    }

    /**
     * 查询特种设备管理详情列表
     * 
     * @param sbchEquipmentSpecialDetails 特种设备管理详情
     * @return 特种设备管理详情
     */
    @Override
    public List<SbchEquipmentSpecialDetails> selectSbchEquipmentSpecialDetailsList(SbchEquipmentSpecialDetails sbchEquipmentSpecialDetails) {
        return sbchEquipmentSpecialDetailsMapper.selectSbchEquipmentSpecialDetailsList(sbchEquipmentSpecialDetails);
    }

    /**
     * 新增特种设备管理详情
     * 
     * @param sbchEquipmentSpecialDetails 特种设备管理详情
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentSpecialDetails(SbchEquipmentSpecialDetails sbchEquipmentSpecialDetails) {

    sbchEquipmentSpecialDetails.setId(IdWorker.createId());

        sbchEquipmentSpecialDetails.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentSpecialDetailsMapper.insertSbchEquipmentSpecialDetails(sbchEquipmentSpecialDetails);
    }

    /**
     * 修改特种设备管理详情
     * 
     * @param sbchEquipmentSpecialDetails 特种设备管理详情
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentSpecialDetails(SbchEquipmentSpecialDetails sbchEquipmentSpecialDetails) {
        sbchEquipmentSpecialDetails.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentSpecialDetailsMapper.updateSbchEquipmentSpecialDetails(sbchEquipmentSpecialDetails);
    }

    /**
     * 删除特种设备管理详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentSpecialDetailsByIds(String ids) {
        return sbchEquipmentSpecialDetailsMapper.deleteSbchEquipmentSpecialDetailsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除特种设备管理详情信息
     * 
     * @param id 特种设备管理详情ID
     * @return 结果
     */
    public int deleteSbchEquipmentSpecialDetailsById(Long id) {
        return sbchEquipmentSpecialDetailsMapper.deleteSbchEquipmentSpecialDetailsById(id);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrEditBatchByMainId(List<SbchEquipmentSpecialDetails> detailList, Long mainId, Boolean isAdjust, BigDecimal version) {
        version = VersionUtil.getVersion("qqch_danger_process_control_plan", version);
        List<QqchSafetyTrain> qqchSafetyTrainNew = new ArrayList<>();
        //获取8.9的针对8.3.3同步过去的数据
        QqchSafetyTrain qqchSafetyTrain = new QqchSafetyTrain();
//        qqchSafetyTrain.setPtVar2("3");
        QqchSafetyTrainVo vo = qqchSafetyTrainService.getQqchSafetyTrainList(qqchSafetyTrain);

        sbchEquipmentSpecialDetailsMapper.deleteSbchEquipmentSpecialDetailsByMainId(mainId);

        if (detailList != null && detailList.size() > 0) {
            // 集合类型转化 设置id 设置purchaseId
            List<SbchEquipmentSpecialDetails> insertOrUpdateData = detailList.stream().map(item -> {
//                Long id = item.getId() == null ? IdWorker.createId() : item.getId();
                item.setId(IdWorker.createId());
                item.setMainId(mainId);
                item.setMaterialName(item.getMaterialName().trim());
                if (StringUtils.isBlank(item.getPtVar1())) {
                    item.setPtVar1(IdWorker.createId()+"");
                }
                EntityUtils.setCreateUpdateInfo(item);
                if (item.getPlanEntryTime() != null) {
                    //8.9若为空，直接组装
                    if (vo != null && vo.getQqchSafetyTrainList() != null && vo.getQqchSafetyTrainList().size() == 0) {
                        QqchSafetyTrain train = new QqchSafetyTrain();
                        train.setContent("特种设备操作培训");
                        train.setTrainType("特种设备操作培训");
                        train.setTime(FtDateUtils.getDateNextOneWeek(item.getPlanEntryTime()));
                        train.setPtVar2("3");
                        train.setPtVar1(item.getPtVar1());
                        train.setUpdateTime(new Date());
                        qqchSafetyTrainNew.add(train);
                    } else {//8.9不为空
                        //根据唯一标识获取获取8.9对应的数据，重新组装培训时间，队伍
                        List<QqchSafetyTrain> safetyTrains = vo.getQqchSafetyTrainList().stream().filter(item1 -> (StringUtils.isNotBlank(item1.getPtVar1()) && item1.getPtVar1().equals(item.getPtVar1()))).collect(Collectors.toList());
                        if (ObjectNullUtil.isEmpty(safetyTrains)) {
                            QqchSafetyTrain train = new QqchSafetyTrain();
                            train.setContent("特种设备操作培训");
                            train.setTrainType("特种设备操作培训");
                            train.setTime(FtDateUtils.getDateNextOneWeek(item.getPlanEntryTime()));
                            train.setPtVar2("3");
                            train.setPtVar1(item.getPtVar1());
                            train.setUpdateTime(new Date());
                            qqchSafetyTrainNew.add(train);
                        } else {
                            safetyTrains.forEach(item3 -> {
                                item3.setContent("特种设备操作培训");
                                item3.setTrainType("特种设备操作培训");
                                if (item3.getUpdateTime() == null) {
                                    item3.setUpdateTime(new Date());
                                }
                                item3.setTime(FtDateUtils.getDateNextOneWeek(item.getPlanEntryTime()));
                            });
                            qqchSafetyTrainNew.addAll(safetyTrains);
                        }
                    }
                }
                return item;

            }).collect(Collectors.toList());
            sbchEquipmentSpecialDetailsMapper.batchInsert(insertOrUpdateData);
            if (vo!=null&&vo.getQqchSafetyTrainList()!=null&&vo.getQqchSafetyTrainList().size()>0) {
                List<QqchSafetyTrain> safetyTrainsHave = vo.getQqchSafetyTrainList().stream().filter(item -> !"3".equals(item.getPtVar2())).collect(Collectors.toList());
                qqchSafetyTrainNew.addAll(safetyTrainsHave);
            }
            //往8.9同步数据
            if (!ObjectNullUtil.isEmpty(qqchSafetyTrainNew)) {
                //        时间排序
                qqchSafetyTrainNew.sort((t1, t2) -> t2.getUpdateTime().compareTo(t1.getUpdateTime()));
                BigDecimal versionTrain = VersionUtil.getVersion("qqch_safety_train", vo.getVersion());
                qqchSafetyTrainService.insertQqchSafetyTrainList(qqchSafetyTrainNew,versionTrain);
            }
        }
        return 1;
    }
}
