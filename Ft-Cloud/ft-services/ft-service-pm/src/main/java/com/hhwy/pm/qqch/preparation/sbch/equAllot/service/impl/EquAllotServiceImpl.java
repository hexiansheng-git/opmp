package com.hhwy.pm.qqch.preparation.sbch.equAllot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.pm.gencode.enums.CodeEnum;
import com.hhwy.pm.gencode.service.GenCodeService;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.ActiveEquResult;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.ActiveEquVo;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.EquAllotVo;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.domain.XcsbMonthSelfEquInfo;
import com.hhwy.pm.qqch.preparation.sbch.equAllot.service.EquAllotService;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllot;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.domain.SbchEquipmentAllotDetails;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.mapper.SbchEquipmentAllotDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.mapper.SbchEquipmentAllotMapper;
import com.hhwy.pm.qqch.preparation.sbch.samecountrytransfers.service.ISbchEquipmentAllotDetailsService;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.domain.*;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalDetailsMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.mapper.SbchEquipmentAllotTransnationalMapper;
import com.hhwy.pm.qqch.preparation.sbch.sbchequipmentallottransnational.service.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zqq
 * @create 2023-08-25 16:11
 */
@Service
public class EquAllotServiceImpl implements EquAllotService {
    @Autowired
    private SbchEquipmentAllotMapper sbchEquipmentAllotMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalMapper sbchEquipmentAllotTransnationalMapper;
    @Autowired
    private SbchEquipmentAllotDetailsMapper sbchEquipmentAllotDetailsMapper;
    @Autowired
    private SbchEquipmentAllotTransnationalDetailsMapper sbchEquipmentAllotTransnationalDetailsMapper;
    @Autowired
    private IQqchReviewService qqchReviewService;
    @Autowired
    private GenCodeService genCodeService;
    @Autowired
    private ISbchEquipmentAllotTransnationalTechnologyService sbchEquipmentAllotTransnationalTechnologyService;
    @Autowired
    private ISbchEquipmentAllotTransnationalDisassemblyService sbchEquipmentAllotTransnationalDisassemblyService;
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
    @Autowired
    private ISbchEquipmentAllotDetailsService sbchEquipmentAllotDetailsService;
    @Autowired
    private ISbchEquipmentAllotTransnationalDetailsService sbchEquipmentAllotTransnationalDetailsService;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Override
    public EquAllotVo getList(BigDecimal version) {
        EquAllotVo returnVo = new EquAllotVo();
        version = VersionUtil.getVersion("sbch_equipment_allot", version);//因为同国别和跨国别现在是一个接口 所以两个主表的version一定是同步的

        SbchEquipmentAllot allot = new SbchEquipmentAllot();
        allot.setVersion(version);
        List<SbchEquipmentAllot> sbchEquipmentAllots = sbchEquipmentAllotMapper.selectSbchEquipmentAllotList(allot);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentAllots)){
            SbchEquipmentAllot allot1 = sbchEquipmentAllots.get(0);
            BeanUtils.copyProperties(allot1,returnVo);
            //查询同国别调拨设备
            SbchEquipmentAllotDetails sbchEquipmentAllotDetails = new SbchEquipmentAllotDetails();
            sbchEquipmentAllotDetails.setMainId(allot1.getId());
            List<SbchEquipmentAllotDetails> allotDetailsList = sbchEquipmentAllotDetailsMapper.selectSbchEquipmentAllotDetailsList(sbchEquipmentAllotDetails);
            returnVo.setSameCountryList(allotDetailsList);
        }

        SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational = new SbchEquipmentAllotTransnational();
        sbchEquipmentAllotTransnational.setVersion(version);
        List<SbchEquipmentAllotTransnational> sbchEquipmentAllotTransnationals = sbchEquipmentAllotTransnationalMapper.selectSbchEquipmentAllotTransnationalList(sbchEquipmentAllotTransnational);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentAllotTransnationals)){
            SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational1 = sbchEquipmentAllotTransnationals.get(0);
            //查询跨国别设备
            SbchEquipmentAllotTransnationalDetails sbchEquipmentAllotTransnationalDetails = new SbchEquipmentAllotTransnationalDetails();
            sbchEquipmentAllotTransnationalDetails.setMainId(sbchEquipmentAllotTransnational1.getId());
            List<SbchEquipmentAllotTransnationalDetails> sbchEquipmentAllotTransnationalDetails1 = sbchEquipmentAllotTransnationalDetailsMapper.selectSbchEquipmentAllotTransnationalDetailsList(sbchEquipmentAllotTransnationalDetails);
            /*子表封装*/
            sbchEquipmentAllotTransnationalDetails1 = detailsListSet(sbchEquipmentAllotTransnationalDetails1);
            returnVo.setInternationList(sbchEquipmentAllotTransnationalDetails1);
        }
        returnVo.setVersion(version);
        returnVo.setStageIdentity(qqchReviewService.getStage());
        return returnVo;
    }

    @Override
    public void batchAdd(EquAllotVo equAllotVo) {
        //处理同国别数据
        SbchEquipmentAllot allot = new SbchEquipmentAllot();
        allot.setVersionCode(equAllotVo.getVersion());
        List<SbchEquipmentAllot> sbchEquipmentAllots = sbchEquipmentAllotMapper.selectSbchEquipmentAllotList(allot);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentAllots)){
            SbchEquipmentAllot allot1 = sbchEquipmentAllots.get(0);
            BeanUtils.copyProperties(allot1,allot);
            MyUtilPrepareUtil.setUpdateInfoBase(allot);
            sbchEquipmentAllotMapper.updateSbchEquipmentAllot(allot);
        }else{
            BeanUtils.copyProperties(equAllotVo,allot);
            allot.setId(IdWorker.createId());
            String setCode = genCodeService.getSetCode(CodeEnum.EQU_SAMEALLO);
            allot.setUnicode(setCode);
            allot.setTitleName("同国别调拨");
            MyUtilPrepareUtil.setCreateUpdateInfo(allot);
            // 设置单据编码
            allot.setTitle(allot.getTitleName());
            allot.setVersionCode(allot.getVersion());
            allot.setAdjustCode(setCode+"-"+allot.getVersionCode().setScale(0));
            // 新增
            sbchEquipmentAllotMapper.insertSbchEquipmentAllot(allot);
        }
        SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational = new SbchEquipmentAllotTransnational();
        sbchEquipmentAllotTransnational.setVersionCode(equAllotVo.getVersion());
        List<SbchEquipmentAllotTransnational> sbchEquipmentAllotTransnationals = sbchEquipmentAllotTransnationalMapper.selectSbchEquipmentAllotTransnationalList(sbchEquipmentAllotTransnational);
        if(!ObjectNullUtil.isEmpty(sbchEquipmentAllotTransnationals)){
            SbchEquipmentAllotTransnational sbchEquipmentAllotTransnational1 = sbchEquipmentAllotTransnationals.get(0);
            BeanUtils.copyProperties(sbchEquipmentAllotTransnational1,sbchEquipmentAllotTransnational);
            MyUtilPrepareUtil.setUpdateInfoBase(sbchEquipmentAllotTransnational);
            sbchEquipmentAllotTransnationalMapper.updateSbchEquipmentAllotTransnational(sbchEquipmentAllotTransnational);
        }else{
            BeanUtils.copyProperties(equAllotVo,sbchEquipmentAllotTransnational);
            sbchEquipmentAllotTransnational.setId(IdWorker.createId());
            String setCode = genCodeService.getSetCode(CodeEnum.EQU_CROSSALLO);
            sbchEquipmentAllotTransnational.setUnicode(setCode);
            sbchEquipmentAllotTransnational.setTitleName("跨国别调拨");
            MyUtilPrepareUtil.setCreateUpdateInfo(sbchEquipmentAllotTransnational);
            // 设置单据编码
            sbchEquipmentAllotTransnational.setTitle(sbchEquipmentAllotTransnational.getTitleName());
            sbchEquipmentAllotTransnational.setVersionCode(sbchEquipmentAllotTransnational.getVersion());
            sbchEquipmentAllotTransnational.setAdjustCode(setCode+"-"+sbchEquipmentAllotTransnational.getVersionCode().setScale(0));
            // 新增
            sbchEquipmentAllotTransnationalMapper.insertSbchEquipmentAllotTransnational(sbchEquipmentAllotTransnational);
        }

        //处理同国别
        List<SbchEquipmentAllotDetails> sameCountryList = equAllotVo.getSameCountryList();
        if(!ObjectNullUtil.isEmpty(sameCountryList)){
            // 明细
            sbchEquipmentAllotDetailsService.insertOrEditBatchByMainId(sameCountryList, allot.getId(), false);
        }

        //处理跨国别数据
        List<SbchEquipmentAllotTransnationalDetails> internationList = equAllotVo.getInternationList();
        if(!ObjectNullUtil.isEmpty(internationList)){
            // 明细
            sbchEquipmentAllotTransnationalDetailsService.insertOrEditBatchByMainId(internationList, sbchEquipmentAllotTransnational.getId(), false);
        }
        //判断是否是确认
        if(ButtonMark.CONFIRM.equals(equAllotVo.getButtonMark())){
            JyDetailsUtil.jyDetails(sameCountryList, ValidationGroups.Save.class);
            /*整体校验*/
            jyDetailsList(internationList);
            //插入确认记录
            String menuId = equAllotVo.getMenuId();
            String stageIdentity = equAllotVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId,stageIdentity);
        }
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

    @Override
    public AjaxResult xzxcsb(ActiveEquVo activeEquVo) {
        String url = "http://10.11.238.63:10003/basic-api/fms/xcsb/xcsbMonthSelfEquInfo/list";
        String resp = HttpUtil.post(url, JSON.toJSONString(activeEquVo));
        AjaxResult ajaxResult = JSON.parseObject(resp, AjaxResult.class);
        return ajaxResult;
//        if (ObjectUtil.isEmpty(ajaxResult) || (int)ajaxResult.get("code") != 200)
//            return null;
//        String data1 = JSON.toJSONString(ajaxResult.get("data"));
//        ActiveEquResult activeEquResult = JSON.parseObject(data1, ActiveEquResult.class);
//        List<XcsbMonthSelfEquInfo> rows = activeEquResult.getRows();
//        return rows;
    }
}
