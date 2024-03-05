//package com.hhwy.sp.techManagement.sgsjTechnicalScienceTopicApply.service.impl;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.bean.copier.CopyOptions;
//import cn.hutool.core.collection.CollUtil;
//import cn.hutool.core.util.StrUtil;
//import com.hhwy.common.core.utils.DateUtils;
//import com.hhwy.common.security.util.SecurityUtils;
//import com.hhwy.enums.FlowEnum;
//import com.hhwy.sp.common.FlowInfoSearchUtil;
//import com.hhwy.sp.common.constant.BelongBusiness;
//import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
//import com.hhwy.sp.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
//import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopic.domain.SgsjTechnicalScienceTopic;
//import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopicApply.domain.SgsjTechnicalScienceTopicApply;
//import com.hhwy.utils.common.CommonBaseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopicApply.mapper.SgsjTechnicalScienceTopicApplyMapper;
//import com.hhwy.sp.techManagement.sgsjTechnicalScienceTopicApply.service.ISgsjTechnicalScienceTopicApplyService;
//import com.hhwy.utils.idworker.IdWorker;
//import org.springframework.util.Assert;
//
///**
// * @author fushudong
// * @date 2024-03-05 16:56:06
// * @remark
// */
//@Service
//public class SgsjTechnicalScienceTopicApplyServiceImpl implements ISgsjTechnicalScienceTopicApplyService {
//
//    @Autowired
//    private SgsjTechnicalScienceTopicApplyMapper sgsjTechnicalScienceTopicApplyMapper;
//
//    @Autowired
//    private ISgjsExpertLibraryService sgjsExpertLibraryService;
//
//
//    public SgsjTechnicalScienceTopicApply getSgsjTechnicalScienceTopicApply(SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply) {
//        return sgsjTechnicalScienceTopicApplyMapper.getSgsjTechnicalScienceTopicApply(sgsjTechnicalScienceTopicApply);
//    }
//
//    public List<SgsjTechnicalScienceTopicApply> getSgsjTechnicalScienceTopicApplyList(SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply) {
//        return sgsjTechnicalScienceTopicApplyMapper.getSgsjTechnicalScienceTopicApplyList(sgsjTechnicalScienceTopicApply);
//    }
//
//    @Transactional
//    public int insertSgsjTechnicalScienceTopicApply(SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply) {
//        sgsjTechnicalScienceTopicApply.setId(IdWorker.createId());
//        sgsjTechnicalScienceTopicApply.setCreateUser(SecurityUtils.getUserName());
//        sgsjTechnicalScienceTopicApply.setCreateTime(DateUtils.getNowDate());
//        return sgsjTechnicalScienceTopicApplyMapper.insertSgsjTechnicalScienceTopicApply(sgsjTechnicalScienceTopicApply);
//    }
//
//    @Transactional
//    public int insertSgsjTechnicalScienceTopicApplyList(List<SgsjTechnicalScienceTopicApply> sgsjTechnicalScienceTopicApplyList) {
//        for (SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply : sgsjTechnicalScienceTopicApplyList) {
//            sgsjTechnicalScienceTopicApply.setId(IdWorker.createId());
//            sgsjTechnicalScienceTopicApply.setCreateUser(SecurityUtils.getUserName());
//            sgsjTechnicalScienceTopicApply.setCreateTime(DateUtils.getNowDate());
//        }
//        return sgsjTechnicalScienceTopicApplyMapper.insertSgsjTechnicalScienceTopicApplyList(sgsjTechnicalScienceTopicApplyList);
//    }
//
//    @Transactional
//    public int updateSgsjTechnicalScienceTopicApply(SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply) {
//        sgsjTechnicalScienceTopicApply.setUpdateUser(SecurityUtils.getUserName());
//        sgsjTechnicalScienceTopicApply.setUpdateTime(DateUtils.getNowDate());
//        return sgsjTechnicalScienceTopicApplyMapper.updateSgsjTechnicalScienceTopicApply(sgsjTechnicalScienceTopicApply);
//    }
//
//    @Transactional
//    public int updateSgsjTechnicalScienceTopicApplyList(List<SgsjTechnicalScienceTopicApply> sgsjTechnicalScienceTopicApplyList) {
//        for (SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply : sgsjTechnicalScienceTopicApplyList) {
//            sgsjTechnicalScienceTopicApply.setUpdateUser(SecurityUtils.getUserName());
//            sgsjTechnicalScienceTopicApply.setUpdateTime(DateUtils.getNowDate());
//        }
//        return sgsjTechnicalScienceTopicApplyMapper.updateSgsjTechnicalScienceTopicApplyList(sgsjTechnicalScienceTopicApplyList);
//    }
//
//    @Transactional
//    public int deleteSgsjTechnicalScienceTopicApply(SgsjTechnicalScienceTopicApply sgsjTechnicalScienceTopicApply) {
//        sgsjTechnicalScienceTopicApply.setUpdateUser(SecurityUtils.getUserName());
//        sgsjTechnicalScienceTopicApply.setUpdateTime(DateUtils.getNowDate());
//        return sgsjTechnicalScienceTopicApplyMapper.deleteSgsjTechnicalScienceTopicApply(sgsjTechnicalScienceTopicApply);
//    }
//
//    @Transactional
//    public int deleteSgsjTechnicalScienceTopicApplyByPks(List<Long> sgsjTechnicalScienceTopicApplyPkList) {
//        return sgsjTechnicalScienceTopicApplyMapper.deleteSgsjTechnicalScienceTopicApplyByPks(sgsjTechnicalScienceTopicApplyPkList);
//    }
//
//
//    @Transactional
//    public int updateSgsjTechnicalScienceTopicList(List<SgsjTechnicalScienceTopic> sgsjTechnicalScienceTopicList) {
//        for (SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic : sgsjTechnicalScienceTopicList) {
//            sgsjTechnicalScienceTopic.setUpdateUser(SecurityUtils.getUserName());
//            sgsjTechnicalScienceTopic.setUpdateTime(DateUtils.getNowDate());
//        }
//        return sgsjTechnicalScienceTopicApplyMapper.updateSgsjTechnicalScienceTopicList(sgsjTechnicalScienceTopicList);
//    }
//
//    /**
//     * 功能描述: 申请明细
//     */
//    @Override
//    public SgsjTechnicalScienceTopic applyDetail(SgsjTechnicalScienceTopic param) {
//        SgsjTechnicalScienceTopic resultBean = sgsjTechnicalScienceTopicApplyMapper.getSgsjTechnicalScienceTopic(param);
//        Assert.isTrue(resultBean != null, "课题不存在,请检查参数是否正确");
//        Long id = resultBean.getId();
//        //知识库
//        List<SgjsExpertLibrary> listByForeignId = sgjsExpertLibraryService.getListByForeignId(id);
//        if (CollUtil.isNotEmpty(listByForeignId)) {
//            Map<String, List<SgjsExpertLibrary>> collect = listByForeignId.stream().collect(Collectors.groupingBy(SgjsExpertLibrary::getBelongBusiness));
//            resultBean.setListApply(collect.get(BelongBusiness.BELONG_BUSINESS_1));
//        }
//        //获取流程信息
//        if (StrUtil.isNotBlank(resultBean.getPtVar1())) {
//            SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic = new SgsjTechnicalScienceTopic();
//            sgsjTechnicalScienceTopic.setId(Long.valueOf(resultBean.getPtVar1()));
//            FlowInfoSearchUtil.getFlowInfo(sgsjTechnicalScienceTopic, FlowEnum.SGJS_TECH_SCIENCE_TOPIC);
//            sgsjTechnicalScienceTopic.setId(null);
//            BeanUtil.copyProperties(sgsjTechnicalScienceTopic, resultBean, CopyOptions.create(CommonBaseEntity.class, true));
//        }
//        return resultBean;
//    }
//
//    /***
//     * 功能描述: 课题申请里的保存
//     */
//    @Transactional
//    public void applyAdd(SgsjTechnicalScienceTopic sgsjTechnicalScienceTopic) {
//        if (sgsjTechnicalScienceTopic == null) {
//            return;
//        }
//        if (StrUtil.isNotBlank(sgsjTechnicalScienceTopic.getTopicFileGroupId())) {
//            String fileName = getFileName(sgsjTechnicalScienceTopic.getTopicFileGroupId());
//            sgsjTechnicalScienceTopic.setPtVar5(fileName);
//        }
//        //保存
//        if (sgsjTechnicalScienceTopic.getId() == null){
//            //保存主表
//            sgsjTechnicalScienceTopic.setId(IdWorker.createId());
//            sgsjTechnicalScienceTopic.setPtVar1(String.valueOf(IdWorker.createId()));
//            sgsjTechnicalScienceTopic.setPtVar2(String.valueOf(IdWorker.createId()));
//            sgsjTechnicalScienceTopic.setCreateUser(SecurityUtils.getUserName());
//            sgsjTechnicalScienceTopic.setCreateTime(DateUtils.getNowDate());
//            sgsjTechnicalScienceTopicApplyMapper.insertSgsjTechnicalScienceTopicApply(sgsjTechnicalScienceTopic);
//        }else {
//            //修改
//            sgsjTechnicalScienceTopicApplyMapper.updateSgsjTechnicalScienceTopic(sgsjTechnicalScienceTopic);
//        }
//        //保存子表
//        Long id = sgsjTechnicalScienceTopic.getId();
//        List<SgjsExpertLibrary> libraryList = sgsjTechnicalScienceTopic.getListApply();
//        sgjsExpertLibraryService.saveExpertLibrary(id, BelongBusiness.BELONG_BUSINESS_1, libraryList);
//    }
//
//}
