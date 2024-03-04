package com.hhwy.sd.outlineReview.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sd.common.FlowInfoSearchUtil;
import com.hhwy.sd.common.constant.BelongBusiness;
import com.hhwy.sd.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sd.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.sd.designEngineeringQuantityManage.kcsjMaterialsList.domain.FileUploadUtil;
import com.hhwy.sd.outlineReview.domain.KcsjOutlineReview;
import com.hhwy.sd.outlineReview.mapper.KcsjOutlineReviewMapper;
import com.hhwy.sd.outlineReview.service.IKcsjOutlineReviewService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能描述: 勘察设计 - 勘察设计大纲评审
 * @author fushudong
 * @date 2024-02-04 15:29:15
 */
@Service
public class KcsjOutlineReviewServiceImpl implements IKcsjOutlineReviewService {

    @Autowired
    private KcsjOutlineReviewMapper kcsjOutlineReviewMapper;
    @Autowired
    private ISgjsExpertLibraryService sgjsExpertLibraryService;


    public KcsjOutlineReview getKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview) {
        return kcsjOutlineReviewMapper.getKcsjOutlineReview(kcsjOutlineReview);
    }

    //历史记录 台账
    public List<KcsjOutlineReview> getKcsjOutlineReviewList(KcsjOutlineReview kcsjOutlineReview) {
        List<KcsjOutlineReview> resultList = kcsjOutlineReviewMapper.getKcsjOutlineReviewList(kcsjOutlineReview);
        if (CollUtil.isEmpty(resultList)) return Collections.emptyList();
        FlowInfoSearchUtil.getFlowInfo(resultList, FlowEnum.KCSJ_PATENT_DECLARE);
        return resultList;
    }

    //详情，编辑
    @Override
    public KcsjOutlineReview getDetail(KcsjOutlineReview param) {
        //参数为空，默认获取最新有效版本，最高版本 = 有效版本
        KcsjOutlineReview result = kcsjOutlineReviewMapper.getMaxVersionData();
        KcsjOutlineReview kcsjOutlineReview = new KcsjOutlineReview();
        kcsjOutlineReview.setVersion(BigDecimal.ONE);
        kcsjOutlineReview.setPtVar4("V1.0");
        if (result == null) return kcsjOutlineReview;
        Long maxVersionId = result.getId();
        if (param != null && param.getId() != null) {
            //参数不为空，获取指定版本数据
            result = kcsjOutlineReviewMapper.getKcsjOutlineReview(param);
        }
        if (BeanUtil.isEmpty(result)) return result;
        List<SgjsExpertLibrary> listByForeignId = sgjsExpertLibraryService.getListByForeignId(result.getId());
        result.setChildList(listByForeignId);
        FlowInfoSearchUtil.getFlowInfo(result, FlowEnum.KCSJ_PATENT_DECLARE);
        //-----------给ptVar1和ptVar2赋值，以下逻辑用于给前端判断按钮显隐------------------
        //调整按钮显隐， 逻辑：当前请求数据如果是最高版本，并且流程结束即显示，否则不显示
        if (maxVersionId.equals(result.getId()) && result.getTaskStatus().equals("4")){
            result.setPtVar1("4");
        }else {
            result.setPtVar1("0");
        }
        //历史记录按钮显隐，逻辑：所有数据中，只要有一条已审批完成即显示，否则不显示
        List<KcsjOutlineReview> kcsjOutlineReviewList = kcsjOutlineReviewMapper.getKcsjOutlineReviewList(new KcsjOutlineReview());
        List<KcsjOutlineReview> collect = kcsjOutlineReviewList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getTaskStatus()) && p.getTaskStatus().equals("5")).collect(Collectors.toList());
        result.setPtVar2(CollUtil.isEmpty(collect)?"0":"4");
        return result;
    }

    @Autowired
    private FileUploadUtil fileUploadUtil;
    //调整
    @Override
    public KcsjOutlineReview adjust(KcsjOutlineReview param) {

//        Assert.isTrue(param.getId()!=null, "参数不能为空");
        KcsjOutlineReview result = kcsjOutlineReviewMapper.getMaxVersionData();
        if (BeanUtil.isEmpty(result)) return result;
        String taskStatus = result.getTaskStatus();
        if (taskStatus.equals("5")){
            //创建新的数据
            result.setVersion(result.getVersion().add(BigDecimal.ONE));
            result.setPtVar4("V" + result.getVersion());
            result.setTaskStatus("0");
            result.setId(null);
            result.setRemodifyDate(null);
            //附件组id更新
            String fileGroupId = result.getFileGroupId();
            if (StringUtils.isNotEmpty(fileGroupId)){
                result.setFileGroupId(fileUploadUtil.copyFile(fileGroupId));
            }
        }
        //历史记录按钮显隐，逻辑：所有数据中，只要有一条已审批完成即显示，否则不显示
        List<KcsjOutlineReview> kcsjOutlineReviewList = kcsjOutlineReviewMapper.getKcsjOutlineReviewList(new KcsjOutlineReview());
        List<KcsjOutlineReview> collect = kcsjOutlineReviewList.stream()
                .filter(p -> StrUtil.isNotBlank(p.getTaskStatus()) && p.getTaskStatus().equals("5")).collect(Collectors.toList());
        result.setPtVar2(CollUtil.isEmpty(collect)?"0":"4");
        return result;
    }

    //保存
    @Transactional
    public Long insertKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview) {
        Long id = kcsjOutlineReview.getId();
        if (ObjectUtil.isEmpty(id)) {
            id = IdWorker.createId();
            kcsjOutlineReview.setId(id);
            kcsjOutlineReview.setCreateUser(SecurityUtils.getUserName());
            kcsjOutlineReview.setCreateTime(DateUtils.getNowDate());
            kcsjOutlineReview.setTaskStatus("0");
            if (StrUtil.isBlank(kcsjOutlineReview.getPtVar4())){
                kcsjOutlineReview.setVersion(BigDecimal.ONE);
                kcsjOutlineReview.setPtVar4("V1.0");
            }else {
                String versionStr = kcsjOutlineReview.getPtVar4();
                kcsjOutlineReview.setVersion(new BigDecimal(StrUtil.sub(versionStr, 1, 2)));
            }
            kcsjOutlineReviewMapper.insertKcsjOutlineReview(kcsjOutlineReview);
        }else {
            String versionStr = kcsjOutlineReview.getPtVar4();
            kcsjOutlineReview.setVersion(new BigDecimal( StrUtil.sub(versionStr, 1, 2)));
            kcsjOutlineReview.setUpdateUser(SecurityUtils.getUserName());
            kcsjOutlineReview.setUpdateTime(DateUtils.getNowDate());
            kcsjOutlineReviewMapper.updateKcsjOutlineReview(kcsjOutlineReview);
        }
        List<SgjsExpertLibrary> childList = kcsjOutlineReview.getChildList();
        if (CollUtil.isEmpty(childList)) return id;
        sgjsExpertLibraryService.saveExpertLibraryList(id, BelongBusiness.BELONG_BUSINESS_1, childList);
        return id;
    }

    @Transactional
    public int insertKcsjOutlineReviewList(List<KcsjOutlineReview> kcsjOutlineReviewList) {
        for (KcsjOutlineReview kcsjOutlineReview : kcsjOutlineReviewList) {
            kcsjOutlineReview.setId(IdWorker.createId());
            kcsjOutlineReview.setCreateUser(SecurityUtils.getUserName());
            kcsjOutlineReview.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjOutlineReviewMapper.insertKcsjOutlineReviewList(kcsjOutlineReviewList);
    }

    //修改
    @Transactional
    public void updateKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview) {
        kcsjOutlineReview.setUpdateUser(SecurityUtils.getUserName());
        kcsjOutlineReview.setUpdateTime(DateUtils.getNowDate());
        int i = kcsjOutlineReviewMapper.updateKcsjOutlineReview(kcsjOutlineReview);
        Assert.isTrue( i > 0, "未找到数据");
        List<SgjsExpertLibrary> childList = kcsjOutlineReview.getChildList();
        if (CollUtil.isEmpty(childList)) return;
        sgjsExpertLibraryService.saveExpertLibraryList(kcsjOutlineReview.getId(), BelongBusiness.BELONG_BUSINESS_1, childList);
    }

    @Transactional
    public int updateKcsjOutlineReviewList(List<KcsjOutlineReview> kcsjOutlineReviewList) {
        for (KcsjOutlineReview kcsjOutlineReview : kcsjOutlineReviewList) {
            kcsjOutlineReview.setUpdateUser(SecurityUtils.getUserName());
            kcsjOutlineReview.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjOutlineReviewMapper.updateKcsjOutlineReviewList(kcsjOutlineReviewList);
    }

    @Transactional
    public int deleteKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview) {
        return kcsjOutlineReviewMapper.deleteKcsjOutlineReview(kcsjOutlineReview);
    }

    @Transactional
    public int deleteKcsjOutlineReviewByPks(List<Integer> kcsjOutlineReviewPkList) {
        return kcsjOutlineReviewMapper.deleteKcsjOutlineReviewByPks(kcsjOutlineReviewPkList);
    }

    //导出专家意见
    @Override
    public Map<String, Object> getExpertSuggest(KcsjOutlineReview param) {
        KcsjOutlineReview kcsjOutlineReview = kcsjOutlineReviewMapper.getKcsjOutlineReview(param);
        List<SgjsExpertLibrary> expertList = sgjsExpertLibraryService.getListByForeignId(param.getId());
        Assert.isTrue(ObjectUtil.isNotEmpty(kcsjOutlineReview), "id不存在");
        Assert.isTrue(CollUtil.isNotEmpty(expertList), "该大纲无专家意见");
        Map<String, Object>  resultMap = new HashMap<>();
        //大纲
        resultMap.put("outlineName", StrUtil.isBlank(kcsjOutlineReview.getOutlineName())?"-":kcsjOutlineReview.getOutlineName());
        resultMap.put("projectName", StrUtil.isBlank(kcsjOutlineReview.getProjectName())?"-":kcsjOutlineReview.getProjectName());
        resultMap.put("version", StrUtil.isBlank(kcsjOutlineReview.getPtVar4())?"-":kcsjOutlineReview.getPtVar4());
        resultMap.put("leadEngineerName", StrUtil.isBlank(kcsjOutlineReview.getLeadEngineerName())?"-":kcsjOutlineReview.getLeadEngineerName());
        resultMap.put("submitPlanDate", kcsjOutlineReview.getSubmitPlanDate()==null?"-": DateUtil.format(kcsjOutlineReview.getSubmitPlanDate(), DatePattern.CHINESE_DATE_PATTERN));
        resultMap.put("reviewPlanDate", kcsjOutlineReview.getReviewPlanDate()==null?"-": DateUtil.format(kcsjOutlineReview.getReviewPlanDate(), DatePattern.CHINESE_DATE_PATTERN));
        resultMap.put("startPersonName", StrUtil.isBlank(kcsjOutlineReview.getStartPersonName())?"-":kcsjOutlineReview.getStartPersonName());
        resultMap.put("startDate", kcsjOutlineReview.getStartDate()==null?"-": DateUtil.format(kcsjOutlineReview.getStartDate(), DatePattern.CHINESE_DATE_PATTERN));
        resultMap.put("outlineSummary", StrUtil.isBlank(kcsjOutlineReview.getOutlineSummary())?"-":kcsjOutlineReview.getOutlineSummary());
        resultMap.put("expertGroupSuggest", StrUtil.isBlank(expertList.get(0).getPtVar1())?"-":expertList.get(0).getPtVar1());
        //专家意见
        List<Map> list = new ArrayList<>();
        for (int i = 0; i < expertList.size(); i++) {
            SgjsExpertLibrary sgjsExpertLibrary = expertList.get(i);
            HashMap<String, Object> map = new HashMap<>();
            map.put("serialNumber", i+1);
            map.put("expertName", StrUtil.isBlank(sgjsExpertLibrary.getExpertName())?"-":sgjsExpertLibrary.getExpertName());
            map.put("belongUnit", StrUtil.isBlank(sgjsExpertLibrary.getBelongUnit())?"-":sgjsExpertLibrary.getBelongUnit());
            map.put("businessAreas", StrUtil.isBlank(sgjsExpertLibrary.getBusinessAreas())?"-":sgjsExpertLibrary.getBusinessAreas());
            map.put("suggest", StrUtil.isBlank(sgjsExpertLibrary.getSuggest())?"-":sgjsExpertLibrary.getSuggest());
            map.put("remark", StrUtil.isBlank(sgjsExpertLibrary.getRemark())?"-":sgjsExpertLibrary.getRemark());
            list.add(map);
        }
        resultMap.put("list", list);
        return resultMap;
    }
}
