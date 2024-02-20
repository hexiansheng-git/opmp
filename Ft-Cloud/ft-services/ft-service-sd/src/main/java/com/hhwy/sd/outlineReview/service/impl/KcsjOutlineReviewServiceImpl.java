package com.hhwy.sd.outlineReview.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.enums.FlowEnum;
import com.hhwy.sd.common.FlowInfoSearchUtil;
import com.hhwy.sd.common.constant.BelongBusiness;
import com.hhwy.sd.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import com.hhwy.sd.common.sgjsExpertLibrary.service.ISgjsExpertLibraryService;
import com.hhwy.sd.outlineReview.domain.KcsjOutlineReview;
import com.hhwy.sd.outlineReview.mapper.KcsjOutlineReviewMapper;
import com.hhwy.sd.outlineReview.service.IKcsjOutlineReviewService;
import com.hhwy.utils.Constant;
import com.hhwy.utils.idworker.IdWorker;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author fushudong
 * @date 2024-02-04 15:29:15
 * @remark
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
        KcsjOutlineReview result;
        if (param != null && param.getId() == null) {
            //参数为空，默认获取最新有效版本，最高版本 = 有效版本
            result = kcsjOutlineReviewMapper.getMaxVersionData();
        } else {
            //参数不为空，获取指定版本数据
            result = kcsjOutlineReviewMapper.getKcsjOutlineReview(param);
        }
        if (BeanUtil.isEmpty(result)) return result;
        List<SgjsExpertLibrary> listByForeignId = sgjsExpertLibraryService.getListByForeignId(result.getId());
        result.setChildList(listByForeignId);
        FlowInfoSearchUtil.getFlowInfo(result, FlowEnum.KCSJ_PATENT_DECLARE);
        return result;
    }

    //调整
    @Override
    public KcsjOutlineReview adjust(KcsjOutlineReview param) {
        Assert.isTrue(param.getId()!=null, "参数不能为空");
        KcsjOutlineReview result = kcsjOutlineReviewMapper.getKcsjOutlineReview(param);
        if (BeanUtil.isEmpty(result)) return result;
        List<SgjsExpertLibrary> listByForeignId = sgjsExpertLibraryService.getListByForeignId(result.getId());
        result.setChildList(listByForeignId);
        return result;
    }

    //保存
    @Transactional
    public Long insertKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview) {
        Long id = IdWorker.createId();
        kcsjOutlineReview.setId(id);
        kcsjOutlineReview.setCreateUser(SecurityUtils.getUserName());
        kcsjOutlineReview.setCreateTime(DateUtils.getNowDate());
        kcsjOutlineReview.setTaskStatus("0");
        KcsjOutlineReview result = kcsjOutlineReviewMapper.getMaxVersionData();
        if ( result == null ){
            kcsjOutlineReview.setVersion(BigDecimal.ONE);
        }else {
            BigDecimal version = result.getVersion();
            kcsjOutlineReview.setVersion(version.add(BigDecimal.ONE));
        }
        kcsjOutlineReviewMapper.insertKcsjOutlineReview(kcsjOutlineReview);
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
}
