package com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.SecurityUtils;

import com.hhwy.enums.FlowEnum;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.*;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.*;
import com.hhwy.utils.EntityUtils;
import com.hhwy.utils.common.CommonBaseEntity;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.selfEmpty.SelfEmpty;
import com.hhwy.utils.validation.JyDetailsUtil;
import com.hhwy.utils.validation.ValidationGroups;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 跨国别设备调拨Service业务层处理
 * 
 * @author hwj
 * @date 2022-12-20
 */
@Service
public class SbchEquipmentAllotTransnationalServiceImpl implements ISbchEquipmentAllotTransnationalService {
    @Autowired
    private SbchEquipmentAllotTransnationalMapper sbchEquipmentAllotTransnationalMapper;
    
    @Autowired
    private GenCodeService genCodeService;

    @Autowired
    private ISbchEquipmentAllotTransnationalDetailsService sbchEquipmentAllotTransnationalDetailsService;

    @Autowired
    private ISbchEquipmentAllotTransnationalDisassemblyService sbchEquipmentAllotTransnationalDisassemblyService;
    @Autowired
    private ISbchEquipmentAllotTransnationalTechnologyService sbchEquipmentAllotTransnationalTechnologyService;
    @Autowired
    private ISbchEquipmentAllotTransnationalClearanceService sbchEquipmentAllotTransnationalClearanceService;
    @Autowired
    private ISbchEquipmentAllotTransnationalClearanceDetailsService sbchEquipmentAllotTransnationalClearanceDetailsService;
    @Autowired
    private ISbchEquipmentAllotTransnationalBulkService sbchEquipmentAllotTransnationalBulkService;
    @Autowired
    private ISbchEquipmentAllotTransnationalContainerService sbchEquipmentAllotTransnationalContainerService;
    @Autowired
    private ISbchEquipmentAllotTransnationalPortService sbchEquipmentAllotTransnationalPortService;
    @Autowired
    private ISbchEquipmentAllotTransnationalExitService sbchEquipmentAllotTransnationalExitService;
    @Autowired
    private ISbchEquipmentAllotTransnationalImportService sbchEquipmentAllotTransnationalImportService;
    @Autowired
    private ISbchEquipmentAllotTransnationalCostService sbchEquipmentAllotTransnationalCostService;

    @Override
    public List baseInfo(Map<String, String> map) {
        String flag = map.get("flag");//标识
        String id = map.get("id");//方案id
        switch (flag){
            case "0"://技术评估状态
                SbchEquipmentAllotTransnationalTechnology sbchEquipmentAllotTransnationalTechnology = new SbchEquipmentAllotTransnationalTechnology();
                sbchEquipmentAllotTransnationalTechnology.setMainId(Long.parseLong(id));
                List<SbchEquipmentAllotTransnationalTechnology> sbchEquipmentAllotTransnationalTechnologies = sbchEquipmentAllotTransnationalTechnologyService.selectSbchEquipmentAllotTransnationalTechnologyList(sbchEquipmentAllotTransnationalTechnology);
                return sbchEquipmentAllotTransnationalTechnologies;
            case "1"://拆卸吊装方案
                SbchEquipmentAllotTransnationalDisassembly sbchEquipmentAllotTransnationalDisassembly = new SbchEquipmentAllotTransnationalDisassembly();
                sbchEquipmentAllotTransnationalDisassembly.setMainId(Long.parseLong(id));
                List<SbchEquipmentAllotTransnationalDisassembly> sbchEquipmentAllotTransnationalDisassemblies = sbchEquipmentAllotTransnationalDisassemblyService.selectSbchEquipmentAllotTransnationalDisassemblyList(sbchEquipmentAllotTransnationalDisassembly);
                return sbchEquipmentAllotTransnationalDisassemblies;
            case "2"://清关档案核查
                SbchEquipmentAllotTransnationalClearance clearance = new SbchEquipmentAllotTransnationalClearance();
                clearance.setMainId(Long.parseLong(id));
                clearance.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalClearance> clearanceDetailList = sbchEquipmentAllotTransnationalClearanceService.selectSbchEquipmentAllotTransnationalClearanceList(clearance);
                /*获取清关档案明细*/
                if (clearanceDetailList!=null && clearanceDetailList.size()>0) {
                    clearanceDetailList.stream().map(clearanceItem -> {
                        SbchEquipmentAllotTransnationalClearanceDetails clearanceDetails = new SbchEquipmentAllotTransnationalClearanceDetails();
                        clearanceDetails.setMainId(clearanceItem.getId());
                        clearanceDetails.setDelFlag("0");
                        List<SbchEquipmentAllotTransnationalClearanceDetails> clearanceDetailsList = sbchEquipmentAllotTransnationalClearanceDetailsService.selectSbchEquipmentAllotTransnationalClearanceDetailsList(clearanceDetails);
                        clearanceItem.setClearanceDetailsList(clearanceDetailsList);
                        return clearanceItem;
                    }).collect(Collectors.toList());
                }
                return clearanceDetailList;
            case "3":////散货运输方案
                SbchEquipmentAllotTransnationalBulk bulkDetail = new SbchEquipmentAllotTransnationalBulk();
                bulkDetail.setMainId(Long.parseLong(id));
                bulkDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalBulk> bulkDetailList = sbchEquipmentAllotTransnationalBulkService.selectSbchEquipmentAllotTransnationalBulkList(bulkDetail);
                return bulkDetailList;
            case "4":////集装箱运输方案
                SbchEquipmentAllotTransnationalContainer containerDetail = new SbchEquipmentAllotTransnationalContainer();
                containerDetail.setMainId(Long.parseLong(id));
                containerDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalContainer> containerDetailList = sbchEquipmentAllotTransnationalContainerService.selectSbchEquipmentAllotTransnationalContainerList(containerDetail);
                return containerDetailList;
            case "5"://港口调查
                SbchEquipmentAllotTransnationalPort portDetail = new SbchEquipmentAllotTransnationalPort();
                portDetail.setMainId(Long.parseLong(id));
                portDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalPort> portDetailList = sbchEquipmentAllotTransnationalPortService.selectSbchEquipmentAllotTransnationalPortList(portDetail);
                return portDetailList;
            case "6"://再出口调查
                SbchEquipmentAllotTransnationalExit exitDetail = new SbchEquipmentAllotTransnationalExit();
                exitDetail.setMainId(Long.parseLong(id));
                exitDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalExit> exitDetailList = sbchEquipmentAllotTransnationalExitService.selectSbchEquipmentAllotTransnationalExitList(exitDetail);
                return exitDetailList;
            case "7"://进口调查
                SbchEquipmentAllotTransnationalImport importDetail = new SbchEquipmentAllotTransnationalImport();
                importDetail.setMainId(Long.parseLong(id));
                importDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalImport> importDetailList = sbchEquipmentAllotTransnationalImportService.selectSbchEquipmentAllotTransnationalImportList(importDetail);
                return importDetailList;
            case "8"://费用估算
                SbchEquipmentAllotTransnationalCost costDetail = new SbchEquipmentAllotTransnationalCost();
                costDetail.setMainId(Long.parseLong(id));
                costDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalCost> costDetailList = sbchEquipmentAllotTransnationalCostService.selectSbchEquipmentAllotTransnationalCostList(costDetail);
                return costDetailList;
        }
        return null;
    }

    /**
     * 查询跨国别设备调拨
     * 
     * @param id 跨国别设备调拨ID
     * @return 跨国别设备调拨
     */
    @Override
    public SbchEquipmentAllotTransnational selectSbchEquipmentAllotTransnationalById(Long id) {
        return sbchEquipmentAllotTransnationalMapper.selectSbchEquipmentAllotTransnationalById(id);
    }

    /**
     * 查询跨国别设备调拨列表
     * 
     * @param sbchEquipmentAllotTransnational 跨国别设备调拨
     * @return 跨国别设备调拨
     */
    @SelfEmpty(clazz = SbchEquipmentAllotTransnational.class)
    @Override
    //@CustomDatascope(alias = "c")
    public List<SbchEquipmentAllotTransnational> selectSbchEquipmentAllotTransnationalList(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational) {
        return sbchEquipmentAllotTransnationalMapper.selectSbchEquipmentAllotTransnationalList(sbchEquipmentAllotTransnational);
    }

    /**
     * 新增跨国别设备调拨
     * 
     * @param sbchEquipmentAllotTransnational 跨国别设备调拨
     * @return 结果
     */
    @Transactional
    @Override
    public String insertSbchEquipmentAllotTransnational(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational) {
        // 校验项目是否被选择
//        wzchCommonService.verifyProjectSelected(sbchEquipmentAllotTransnational.getProjectId(), FlowEnum.KGBSB.getTableName());
        // 获取前端传入的设备明细
        List<SbchEquipmentAllotTransnationalDetails> detailList = sbchEquipmentAllotTransnational.getDetailsList();
        /*整体校验*/
        jyDetailsList(detailList);

        Long id = IdWorker.createId();
        // 设置id
        sbchEquipmentAllotTransnational.setId(id);
        // 设置版本号码
        sbchEquipmentAllotTransnational.setVersionCode(new BigDecimal("1.0"));
        // 设置单据编码
//        sbchEquipmentAllotTransnational.setAdjustCode(genCodeService.getSetCode(CodeEnum.EQU_CROSSALLO)+"-"+sbchEquipmentAllotTransnational.getVersionCode().setScale(0));
        // 是否生效
        sbchEquipmentAllotTransnational.setValid("0");

        EntityUtils.setCreateUpdateInfo(sbchEquipmentAllotTransnational);

        // 新增
        this.sbchEquipmentAllotTransnationalMapper.insertSbchEquipmentAllotTransnational(sbchEquipmentAllotTransnational);
        // 明细
        sbchEquipmentAllotTransnationalDetailsService.insertOrEditBatchByMainId(detailList, sbchEquipmentAllotTransnational.getId(), false);
        return id.toString();
    }

    /**
     * 修改跨国别设备调拨
     * 
     * @param sbchEquipmentAllotTransnational 跨国别设备调拨
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSbchEquipmentAllotTransnational(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational) {
        // 获取前端传入的设备明细
        List<SbchEquipmentAllotTransnationalDetails> detailList = sbchEquipmentAllotTransnational.getDetailsList();
        /*整体校验*/
        jyDetailsList(detailList);

//        EntityUtils.setUpdateInfo(sbchEquipmentAllotTransnational);

        // 修改
        sbchEquipmentAllotTransnationalMapper.updateSbchEquipmentAllotTransnational(sbchEquipmentAllotTransnational);
        // 明细
        return sbchEquipmentAllotTransnationalDetailsService.insertOrEditBatchByMainId(detailList,sbchEquipmentAllotTransnational.getId(),false);
    }
    /**
     * 调整
     *
     * @return
     */
    @Override
    @Transactional
    public String adjustSbchEquipmentAllotTransnational(SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational) {
        // 获取前端传入的物资明细
        List<SbchEquipmentAllotTransnationalDetails> detailList = sbchEquipmentAllotTransnational.getDetailsList();
        /*整体校验*/
        jyDetailsList(detailList);

        Long id = IdWorker.createId();
        // 设置id
        sbchEquipmentAllotTransnational.setId(id);
        // 设置创建信息
        EntityUtils.setCreateUpdateInfo(sbchEquipmentAllotTransnational);
        //置为无效
        sbchEquipmentAllotTransnational.setValid("0");

        // 新增或者编辑
        this.sbchEquipmentAllotTransnationalMapper.insertSbchEquipmentAllotTransnational(sbchEquipmentAllotTransnational);
        // 明细
        sbchEquipmentAllotTransnationalDetailsService.insertOrEditBatchByMainId(detailList, sbchEquipmentAllotTransnational.getId(), true);
        return id.toString();
    }

    /**
     * 删除跨国别设备调拨对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSbchEquipmentAllotTransnationalByIds(String ids) {
        return sbchEquipmentAllotTransnationalMapper.deleteSbchEquipmentAllotTransnationalByIds(Convert.toStrArray(ids), SecurityUtils.getUserId().toString());
    }

    /**
     * 删除跨国别设备调拨信息
     * 
     * @param id 跨国别设备调拨ID
     * @return 结果
     */
    public int deleteSbchEquipmentAllotTransnationalById(Long id) {
        return sbchEquipmentAllotTransnationalMapper.deleteSbchEquipmentAllotTransnationalById(id);
    }

    @Transactional
    @Override
    public int updateValidStatus(String mainId) {

        return 1;
    }

    /*整体校验*/
    private void jyDetailsList(List<SbchEquipmentAllotTransnationalDetails> detailList) {
        /*往这里加详情*/
        /*校验技术评估*/
        Boolean aBoolean = JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsListTechnology", ValidationGroups.Save.class);
        /*校验拆卸吊装*/
        Boolean aBooleanDisassembly = JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsListDisassembly", ValidationGroups.Save.class);
        /*校验清关档案*/
        Boolean aBooleanClearance = JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsListClearance", ValidationGroups.Save.class);
        /*散货运输方案*/
        JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsListBulk", ValidationGroups.Save.class);
        /*集装箱运输方案*/
        JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsListContainer", ValidationGroups.Save.class);
        /*港口调查*/
        JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsListPort", ValidationGroups.Save.class);
        /*再出口调查*/
        JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsListExit", ValidationGroups.Save.class);
        /*进口调查*/
        JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsListImport", ValidationGroups.Save.class);
        /*费用估算*/
        JyDetailsUtil.jyDetailsDetails(detailList,"getDetailsListCost", ValidationGroups.Save.class);
    }
    /*子表封装*/
    private List<SbchEquipmentAllotTransnationalDetails> detailsListSet(List<SbchEquipmentAllotTransnationalDetails> detailList) {
        if (detailList != null&&detailList.size()>0) {
            detailList.stream().map(item -> {
                /*往这里加详情*/
                /*技术状态评估*/
                SbchEquipmentAllotTransnationalTechnology technologyDetail = new SbchEquipmentAllotTransnationalTechnology();
                technologyDetail.setMainId(item.getId());
                technologyDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalTechnology> technologyDetailList = sbchEquipmentAllotTransnationalTechnologyService.selectSbchEquipmentAllotTransnationalTechnologyList(technologyDetail);
                item.setDetailsListTechnology(technologyDetailList);

                /*拆卸吊装*/
                SbchEquipmentAllotTransnationalDisassembly DisassemblyDetail = new SbchEquipmentAllotTransnationalDisassembly();
                DisassemblyDetail.setMainId(item.getId());
                DisassemblyDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalDisassembly> DisassemblyDetailList = sbchEquipmentAllotTransnationalDisassemblyService.selectSbchEquipmentAllotTransnationalDisassemblyList(DisassemblyDetail);
                item.setDetailsListDisassembly(DisassemblyDetailList);

                /*清关档案核查*/
                SbchEquipmentAllotTransnationalClearance clearance = new SbchEquipmentAllotTransnationalClearance();
                clearance.setMainId(item.getId());
                clearance.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalClearance> clearanceDetailList = sbchEquipmentAllotTransnationalClearanceService.selectSbchEquipmentAllotTransnationalClearanceList(clearance);
                /*获取清关档案明细*/
                if (clearanceDetailList!=null && clearanceDetailList.size()>0) {
                    clearanceDetailList.stream().map(clearanceItem -> {
                        SbchEquipmentAllotTransnationalClearanceDetails clearanceDetails = new SbchEquipmentAllotTransnationalClearanceDetails();
                        clearanceDetails.setMainId(clearanceItem.getId());
                        clearanceDetails.setDelFlag("0");
                        List<SbchEquipmentAllotTransnationalClearanceDetails> clearanceDetailsList = sbchEquipmentAllotTransnationalClearanceDetailsService.selectSbchEquipmentAllotTransnationalClearanceDetailsList(clearanceDetails);
                        clearanceItem.setClearanceDetailsList(clearanceDetailsList);
                        return clearanceItem;
                    }).collect(Collectors.toList());
                }
                item.setDetailsListClearance(clearanceDetailList);

                /*散货运输方案*/
                SbchEquipmentAllotTransnationalBulk bulkDetail = new SbchEquipmentAllotTransnationalBulk();
                bulkDetail.setMainId(item.getId());
                bulkDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalBulk> bulkDetailList = sbchEquipmentAllotTransnationalBulkService.selectSbchEquipmentAllotTransnationalBulkList(bulkDetail);
                item.setDetailsListBulk(bulkDetailList);

                /*集装箱运输方案*/
                SbchEquipmentAllotTransnationalContainer containerDetail = new SbchEquipmentAllotTransnationalContainer();
                containerDetail.setMainId(item.getId());
                containerDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalContainer> containerDetailList = sbchEquipmentAllotTransnationalContainerService.selectSbchEquipmentAllotTransnationalContainerList(containerDetail);
                item.setDetailsListContainer(containerDetailList);

                /*港口调查*/
                SbchEquipmentAllotTransnationalPort portDetail = new SbchEquipmentAllotTransnationalPort();
                portDetail.setMainId(item.getId());
                portDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalPort> portDetailList = sbchEquipmentAllotTransnationalPortService.selectSbchEquipmentAllotTransnationalPortList(portDetail);
                item.setDetailsListPort(portDetailList);

                /*再出口调查*/
                SbchEquipmentAllotTransnationalExit exitDetail = new SbchEquipmentAllotTransnationalExit();
                exitDetail.setMainId(item.getId());
                exitDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalExit> exitDetailList = sbchEquipmentAllotTransnationalExitService.selectSbchEquipmentAllotTransnationalExitList(exitDetail);
                item.setDetailsListExit(exitDetailList);

                /*进口调查*/
                SbchEquipmentAllotTransnationalImport importDetail = new SbchEquipmentAllotTransnationalImport();
                importDetail.setMainId(item.getId());
                importDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalImport> importDetailList = sbchEquipmentAllotTransnationalImportService.selectSbchEquipmentAllotTransnationalImportList(importDetail);
                item.setDetailsListImport(importDetailList);

                /*费用估算*/
                SbchEquipmentAllotTransnationalCost costDetail = new SbchEquipmentAllotTransnationalCost();
                costDetail.setMainId(item.getId());
                costDetail.setDelFlag("0");
                List<SbchEquipmentAllotTransnationalCost> costDetailList = sbchEquipmentAllotTransnationalCostService.selectSbchEquipmentAllotTransnationalCostList(costDetail);
                item.setDetailsListCost(costDetailList);

                return item;

            }).collect(Collectors.toList());
        }
        return detailList;
    }
}
