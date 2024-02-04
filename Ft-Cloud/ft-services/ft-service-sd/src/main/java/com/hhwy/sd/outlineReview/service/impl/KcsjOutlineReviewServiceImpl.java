package com.hhwy.sd.outlineReview.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.outlineReview.domain.KcsjOutlineReview;
import com.hhwy.sd.outlineReview.mapper.KcsjOutlineReviewMapper;
import com.hhwy.sd.outlineReview.service.IKcsjOutlineReviewService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public KcsjOutlineReview getKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview) {
        return kcsjOutlineReviewMapper.getKcsjOutlineReview(kcsjOutlineReview);
    }

    public List<KcsjOutlineReview> getKcsjOutlineReviewList(KcsjOutlineReview kcsjOutlineReview) {
        return kcsjOutlineReviewMapper.getKcsjOutlineReviewList(kcsjOutlineReview);
    }

    @Transactional
    public int insertKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview) {
        kcsjOutlineReview.setId(IdWorker.createId());
        kcsjOutlineReview.setCreateUser(SecurityUtils.getUserName());
        kcsjOutlineReview.setCreateTime(DateUtils.getNowDate());
        return kcsjOutlineReviewMapper.insertKcsjOutlineReview(kcsjOutlineReview);
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

    @Transactional
    public int updateKcsjOutlineReview(KcsjOutlineReview kcsjOutlineReview) {
        kcsjOutlineReview.setUpdateUser(SecurityUtils.getUserName());
        kcsjOutlineReview.setUpdateTime(DateUtils.getNowDate());
        return kcsjOutlineReviewMapper.updateKcsjOutlineReview(kcsjOutlineReview);
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
        kcsjOutlineReview.setUpdateUser(SecurityUtils.getUserName());
        kcsjOutlineReview.setUpdateTime(DateUtils.getNowDate());
        return kcsjOutlineReviewMapper.deleteKcsjOutlineReview(kcsjOutlineReview);
    }

    @Transactional
    public int deleteKcsjOutlineReviewByPks(List<Integer> kcsjOutlineReviewPkList) {
        return kcsjOutlineReviewMapper.deleteKcsjOutlineReviewByPks(kcsjOutlineReviewPkList);
    }
}
