package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.*;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.*;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.ISbchEquipmentAllotTransnationalDetailsService;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 跨国别设备调拨详情Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Service
public class SbchEquipmentAllotTransnationalDetailsServiceImpl implements ISbchEquipmentAllotTransnationalDetailsService {
    @Autowired
    private SbchEquipmentAllotTransnationalDetailsMapper sbchEquipmentAllotTransnationalDetailsMapper;

    @Autowired
    private SbchEquipmentAllotTransnationalTechnologyMapper sbchEquipmentAllotTransnationalTechnologyMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalDisassemblyMapper sbchEquipmentAllotTransnationalDisassemblyMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalClearanceMapper sbchEquipmentAllotTransnationalClearanceMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalClearanceDetailsMapper sbchEquipmentAllotTransnationalClearanceDetailsMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalBulkMapper sbchEquipmentAllotTransnationalBulkMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalContainerMapper sbchEquipmentAllotTransnationalContainerMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalPortMapper sbchEquipmentAllotTransnationalPortMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalExitMapper sbchEquipmentAllotTransnationalExitMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalImportMapper sbchEquipmentAllotTransnationalImportMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalCostMapper sbchEquipmentAllotTransnationalCostMapper;

    /**
     * 查询跨国别设备调拨详情
     * 
     * @param id 跨国别设备调拨详情ID
     * @return 跨国别设备调拨详情
     */
    @Override
    public SbchEquipmentAllotTransnationalDetails selectSbchEquipmentAllotTransnationalDetailsById(Long id) {
        return sbchEquipmentAllotTransnationalDetailsMapper.selectSbchEquipmentAllotTransnationalDetailsById(id);
    }

    /**
     * 查询跨国别设备调拨详情列表
     * 
     * @param sbchEquipmentAllotTransnationalDetails 跨国别设备调拨详情
     * @return 跨国别设备调拨详情
     */
    @Override
    public List<SbchEquipmentAllotTransnationalDetails> selectSbchEquipmentAllotTransnationalDetailsList(SbchEquipmentAllotTransnationalDetails sbchEquipmentAllotTransnationalDetails) {
        return sbchEquipmentAllotTransnationalDetailsMapper.selectSbchEquipmentAllotTransnationalDetailsList(sbchEquipmentAllotTransnationalDetails);
    }

    /**
     * 新增跨国别设备调拨详情
     * 
     * @param sbchEquipmentAllotTransnationalDetails 跨国别设备调拨详情
     * @return 结果
     */
    @Override
    public int insertSbchEquipmentAllotTransnationalDetails(SbchEquipmentAllotTransnationalDetails sbchEquipmentAllotTransnationalDetails) {

    sbchEquipmentAllotTransnationalDetails.setId(IdWorker.createId());

        sbchEquipmentAllotTransnationalDetails.setCreateTime(DateUtils.getNowDate());

        return sbchEquipmentAllotTransnationalDetailsMapper.insertSbchEquipmentAllotTransnationalDetails(sbchEquipmentAllotTransnationalDetails);
    }

    /**
     * 修改跨国别设备调拨详情
     * 
     * @param sbchEquipmentAllotTransnationalDetails 跨国别设备调拨详情
     * @return 结果
     */
    @Override
    public int updateSbchEquipmentAllotTransnationalDetails(SbchEquipmentAllotTransnationalDetails sbchEquipmentAllotTransnationalDetails) {
        sbchEquipmentAllotTransnationalDetails.setUpdateTime(DateUtils.getNowDate());
        return sbchEquipmentAllotTransnationalDetailsMapper.updateSbchEquipmentAllotTransnationalDetails(sbchEquipmentAllotTransnationalDetails);
    }

    /**
     * 删除跨国别设备调拨详情对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalDetailsByIds(String ids) {
        return sbchEquipmentAllotTransnationalDetailsMapper.deleteSbchEquipmentAllotTransnationalDetailsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除跨国别设备调拨详情信息
     * 
     * @param id 跨国别设备调拨详情ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalDetailsById(Long id) {
        return sbchEquipmentAllotTransnationalDetailsMapper.deleteSbchEquipmentAllotTransnationalDetailsById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrEditBatchByMainId(List<SbchEquipmentAllotTransnationalDetails> detailList, Long mainId, Boolean isAdjust) {
        /*获取 数据库中原有的调拨设备详情*/
        List<SbchEquipmentAllotTransnationalDetails> detailsListOld = sbchEquipmentAllotTransnationalDetailsMapper.selectDetailsByMainId(mainId);
        /*清空子表数据*/
        detailsDel(detailsListOld);
        /*清空调拨设备详情*/
        sbchEquipmentAllotTransnationalDetailsMapper.deleteDetailsByMainId(mainId);

        if (detailList != null && detailList.size() > 0) {
            // 集合类型转化 设置id 设置purchaseId
            List<SbchEquipmentAllotTransnationalDetails> insertOrUpdateData = detailList.stream().map(item -> {
                Long id = IdWorker.createId();
                /*不是调整*/
//                if (!isAdjust) {
//                    id = item.getId() == null ? IdWorker.createId() : item.getId();
//                }
                item.setId(id);
                item.setMainId(mainId);
                EntityUtils.setCreateUpdateInfo(item);
                /*添加子表数据*/
                detailsSet(item,isAdjust);
                return item;
            }).collect(Collectors.toList());
            sbchEquipmentAllotTransnationalDetailsMapper.batchInsert(insertOrUpdateData);
        }
        return 1;
    }

    private void detailsDel(List<SbchEquipmentAllotTransnationalDetails> detailsListOld) {
        if (detailsListOld!=null&&detailsListOld.size()>0) {
            for (int i = 0; i <detailsListOld.size(); i++) {
                /*往这里加详情*/
                /*清空 技术状态评估*/
                sbchEquipmentAllotTransnationalTechnologyMapper.deleteDetailsByMainId(detailsListOld.get(i).getId());
                //清空 拆卸吊装方案
                sbchEquipmentAllotTransnationalDisassemblyMapper.deleteDetailsByMainId(detailsListOld.get(i).getId());

                /*获取 清关档案核查old*/
                List<SbchEquipmentAllotTransnationalClearance> clearanceListOld = sbchEquipmentAllotTransnationalClearanceMapper.selectDetailsByMainId(detailsListOld.get(i).getId());
                if (clearanceListOld!=null&&clearanceListOld.size()>0) {
                    for (int j = 0; j < clearanceListOld.size(); j++) {
                        /*清空 清关档案核查结果明细*/
                        sbchEquipmentAllotTransnationalClearanceDetailsMapper.deleteDetailsByMainId(clearanceListOld.get(j).getId());
                    }
                }
                //清空 清关档案核查
                sbchEquipmentAllotTransnationalClearanceMapper.deleteDetailsByMainId(detailsListOld.get(i).getId());
                //清空 散货运输方案
                sbchEquipmentAllotTransnationalBulkMapper.deleteDetailsByMainId(detailsListOld.get(i).getId());
                //清空 集装箱运输方案
                sbchEquipmentAllotTransnationalContainerMapper.deleteDetailsByMainId(detailsListOld.get(i).getId());
                //清空 港口调查
                sbchEquipmentAllotTransnationalPortMapper.deleteDetailsByMainId(detailsListOld.get(i).getId());
                //清空 再出口调查
                sbchEquipmentAllotTransnationalExitMapper.deleteDetailsByMainId(detailsListOld.get(i).getId());
                //清空 进口调查
                sbchEquipmentAllotTransnationalImportMapper.deleteDetailsByMainId(detailsListOld.get(i).getId());
                //清空 费用估算
                sbchEquipmentAllotTransnationalCostMapper.deleteDetailsByMainId(detailsListOld.get(i).getId());
            }
        }
    }

    private void detailsSet(SbchEquipmentAllotTransnationalDetails item, Boolean isAdjust) {
        /*往这里加详情*/
        /*技术状体评估详情*/
        if (item.getDetailsListTechnology()!=null&&item.getDetailsListTechnology().size()>0) {
            List<SbchEquipmentAllotTransnationalTechnology> detailsDetailsList=item.getDetailsListTechnology().stream().map(detailsDetails -> {
                Long detailId = IdWorker.createId();
//                /*不是调整*/
//                if (!isAdjust) {
//                    detailId = detailsDetails.getId() == null ? IdWorker.createId() : detailsDetails.getId();
//                }
                detailsDetails.setId(detailId);
                detailsDetails.setMainId(item.getId());
                EntityUtils.setCreateUpdateInfo(detailsDetails);
                return detailsDetails;
            }).collect(Collectors.toList());
            sbchEquipmentAllotTransnationalTechnologyMapper.batchInsert(detailsDetailsList);
        }

        /*拆卸吊装方案*/
        if (item.getDetailsListDisassembly()!=null&&item.getDetailsListDisassembly().size()>0) {
            List<SbchEquipmentAllotTransnationalDisassembly> DisassemblyList=item.getDetailsListDisassembly().stream().map(detailsDetails -> {
                Long detailId = IdWorker.createId();
//                /*不是调整*/
//                if (!isAdjust) {
//                    detailId = detailsDetails.getId() == null ? IdWorker.createId() : detailsDetails.getId();
//                }
                detailsDetails.setId(detailId);
                detailsDetails.setMainId(item.getId());
                EntityUtils.setCreateUpdateInfo(detailsDetails);
                return detailsDetails;
            }).collect(Collectors.toList());
            sbchEquipmentAllotTransnationalDisassemblyMapper.batchInsert(DisassemblyList);
        }

        /*清关档案核查*/
        if (item.getDetailsListClearance()!=null&&item.getDetailsListClearance().size()>0) {
            List<SbchEquipmentAllotTransnationalClearance> clearanceList=item.getDetailsListClearance().stream().map(detailsDetails -> {
                Long detailId = IdWorker.createId();
//                /*不是调整*/
//                if (!isAdjust) {
//                    detailId = detailsDetails.getId() == null ? IdWorker.createId() : detailsDetails.getId();
//                }
                detailsDetails.setId(detailId);
                detailsDetails.setMainId(item.getId());
                EntityUtils.setCreateUpdateInfo(detailsDetails);
                /*清关档案结果明细*/
                if (detailsDetails.getClearanceDetailsList() != null && detailsDetails.getClearanceDetailsList().size() > 0) {
                    Boolean aBooleanClearanceDetails = JyDetailsUtil.jyDetails(detailsDetails.getClearanceDetailsList(), ValidationGroups.Save.class);
                    detailsDetails.getClearanceDetailsList().stream().map(clearanceDetails -> {
                        Long clearanceDetailsId = IdWorker.createId();
//                        /*不是调整*/
//                        if (!isAdjust) {
//                            clearanceDetailsId = clearanceDetails.getId() == null ? IdWorker.createId() : clearanceDetails.getId();
//                        }
                        clearanceDetails.setId(clearanceDetailsId);
                        clearanceDetails.setMainId(detailsDetails.getId());
                        EntityUtils.setCreateUpdateInfo(clearanceDetails);
                        return clearanceDetails;
                    }).collect(Collectors.toList());
                    sbchEquipmentAllotTransnationalClearanceDetailsMapper.batchInsert(detailsDetails.getClearanceDetailsList());
                }

                return detailsDetails;
            }).collect(Collectors.toList());
            sbchEquipmentAllotTransnationalClearanceMapper.batchInsert(clearanceList);
        }

        /*散货运输方案*/
        if (item.getDetailsListBulk()!=null&&item.getDetailsListBulk().size()>0) {
            List<SbchEquipmentAllotTransnationalBulk> bulkList=item.getDetailsListBulk().stream().map(detailsDetails -> {
                Long detailId = IdWorker.createId();
//                /*不是调整*/
//                if (!isAdjust) {
//                    detailId = detailsDetails.getId() == null ? IdWorker.createId() : detailsDetails.getId();
//                }
                detailsDetails.setId(detailId);
                detailsDetails.setMainId(item.getId());
                EntityUtils.setCreateUpdateInfo(detailsDetails);
                return detailsDetails;
            }).collect(Collectors.toList());
            sbchEquipmentAllotTransnationalBulkMapper.batchInsert(bulkList);
        }

        /*集装箱运输方案*/
        if (item.getDetailsListContainer()!=null&&item.getDetailsListContainer().size()>0) {
            List<SbchEquipmentAllotTransnationalContainer> containerList=item.getDetailsListContainer().stream().map(detailsDetails -> {
                Long detailId = IdWorker.createId();
//                /*不是调整*/
//                if (!isAdjust) {
//                    detailId = detailsDetails.getId() == null ? IdWorker.createId() : detailsDetails.getId();
//                }
                detailsDetails.setId(detailId);
                detailsDetails.setMainId(item.getId());
                EntityUtils.setCreateUpdateInfo(detailsDetails);
                return detailsDetails;
            }).collect(Collectors.toList());
            sbchEquipmentAllotTransnationalContainerMapper.batchInsert(containerList);
        }

        /*港口调查*/
        if (item.getDetailsListPort()!=null&&item.getDetailsListPort().size()>0) {
            List<SbchEquipmentAllotTransnationalPort> portList=item.getDetailsListPort().stream().map(detailsDetails -> {
                Long detailId = IdWorker.createId();
//                /*不是调整*/
//                if (!isAdjust) {
//                    detailId = detailsDetails.getId() == null ? IdWorker.createId() : detailsDetails.getId();
//                }
                detailsDetails.setId(detailId);
                detailsDetails.setMainId(item.getId());
                EntityUtils.setCreateUpdateInfo(detailsDetails);
                return detailsDetails;
            }).collect(Collectors.toList());
            sbchEquipmentAllotTransnationalPortMapper.batchInsert(portList);
        }

        /*再出口调查*/
        if (item.getDetailsListExit()!=null&&item.getDetailsListExit().size()>0) {
            List<SbchEquipmentAllotTransnationalExit> exitList=item.getDetailsListExit().stream().map(detailsDetails -> {
                Long detailId = IdWorker.createId();
//                /*不是调整*/
//                if (!isAdjust) {
//                    detailId = detailsDetails.getId() == null ? IdWorker.createId() : detailsDetails.getId();
//                }
                detailsDetails.setId(detailId);
                detailsDetails.setMainId(item.getId());
                EntityUtils.setCreateUpdateInfo(detailsDetails);
                return detailsDetails;
            }).collect(Collectors.toList());
            sbchEquipmentAllotTransnationalExitMapper.batchInsert(exitList);
        }

        /*进口调查*/
        if (item.getDetailsListImport()!=null&&item.getDetailsListImport().size()>0) {
            List<SbchEquipmentAllotTransnationalImport> importList=item.getDetailsListImport().stream().map(detailsDetails -> {
                Long detailId = IdWorker.createId();
//                /*不是调整*/
//                if (!isAdjust) {
//                    detailId = detailsDetails.getId() == null ? IdWorker.createId() : detailsDetails.getId();
//                }
                detailsDetails.setId(detailId);
                detailsDetails.setMainId(item.getId());
                EntityUtils.setCreateUpdateInfo(detailsDetails);
                return detailsDetails;
            }).collect(Collectors.toList());
            sbchEquipmentAllotTransnationalImportMapper.batchInsert(importList);
        }

        /*费用估算*/
        if (item.getDetailsListCost()!=null&&item.getDetailsListCost().size()>0) {
            List<SbchEquipmentAllotTransnationalCost> costList=item.getDetailsListCost().stream().map(detailsDetails -> {
                Long detailId = IdWorker.createId();
//                /*不是调整*/
//                if (!isAdjust) {
//                    detailId = detailsDetails.getId() == null ? IdWorker.createId() : detailsDetails.getId();
//                }
                detailsDetails.setId(detailId);
                detailsDetails.setMainId(item.getId());
                EntityUtils.setCreateUpdateInfo(detailsDetails);
                return detailsDetails;
            }).collect(Collectors.toList());
            sbchEquipmentAllotTransnationalCostMapper.batchInsert(costList);
        }
    }
}

