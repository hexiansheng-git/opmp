package com.hhwy.pm.jdgl.diff.track.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrackDetail;
import com.hhwy.pm.jdgl.diff.track.mapper.JdglProgressCorrectionTrackDetailMapper;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackDetailService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-08-24 16:51:15
 * @remark 进度纠偏跟踪详情
 */
@Service
public class JdglProgressCorrectionTrackDetailServiceImpl implements IJdglProgressCorrectionTrackDetailService {

    @Autowired
    private JdglProgressCorrectionTrackDetailMapper jdglProgressCorrectionTrackDetailMapper;

    public JdglProgressCorrectionTrackDetail getJdglProgressCorrectionTrackDetail(
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail) {
        return jdglProgressCorrectionTrackDetailMapper
            .getJdglProgressCorrectionTrackDetail(jdglProgressCorrectionTrackDetail);
    }

    public List<JdglProgressCorrectionTrackDetail> getJdglProgressCorrectionTrackDetailList(
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail) {
        return jdglProgressCorrectionTrackDetailMapper
            .getJdglProgressCorrectionTrackDetailList(jdglProgressCorrectionTrackDetail);
    }

    @Transactional
    public int insertJdglProgressCorrectionTrackDetail(
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail) {
        jdglProgressCorrectionTrackDetail.setId(IdWorker.createId());
        jdglProgressCorrectionTrackDetail.setCreateUser(SecurityUtils.getUserName());
        jdglProgressCorrectionTrackDetail.setCreateTime(DateUtils.getNowDate());
        return jdglProgressCorrectionTrackDetailMapper
            .insertJdglProgressCorrectionTrackDetail(jdglProgressCorrectionTrackDetail);
    }

    @Transactional
    public int insertJdglProgressCorrectionTrackDetailList(
        List<JdglProgressCorrectionTrackDetail> jdglProgressCorrectionTrackDetailList) {
        for (JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail : jdglProgressCorrectionTrackDetailList) {
//            jdglProgressCorrectionTrackDetail.setId(IdWorker.createId());
            jdglProgressCorrectionTrackDetail.setCreateUser(SecurityUtils.getUserName());
            jdglProgressCorrectionTrackDetail.setCreateTime(DateUtils.getNowDate());
        }
        return jdglProgressCorrectionTrackDetailMapper.insertJdglProgressCorrectionTrackDetailList(jdglProgressCorrectionTrackDetailList);
    }

    @Transactional
    public int updateJdglProgressCorrectionTrackDetail(
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail) {
        jdglProgressCorrectionTrackDetail.setUpdateUser(SecurityUtils.getUserName());
        jdglProgressCorrectionTrackDetail.setUpdateTime(DateUtils.getNowDate());
        return jdglProgressCorrectionTrackDetailMapper
            .updateJdglProgressCorrectionTrackDetail(jdglProgressCorrectionTrackDetail);
    }

    @Transactional
    public int updateJdglProgressCorrectionTrackDetailList(
        List<JdglProgressCorrectionTrackDetail> jdglProgressCorrectionTrackDetailList) {
        for (JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail : jdglProgressCorrectionTrackDetailList) {
            jdglProgressCorrectionTrackDetail.setUpdateUser(SecurityUtils.getUserName());
            jdglProgressCorrectionTrackDetail.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglProgressCorrectionTrackDetailMapper
            .updateJdglProgressCorrectionTrackDetailList(jdglProgressCorrectionTrackDetailList);
    }

    @Transactional
    public int deleteJdglProgressCorrectionTrackDetail(JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail) {
//        jdglProgressCorrectionTrackDetail.setUpdateUser(SecurityUtils.getUserName());
//        jdglProgressCorrectionTrackDetail.setUpdateTime(DateUtils.getNowDate());
        return jdglProgressCorrectionTrackDetailMapper .deleteJdglProgressCorrectionTrackDetail(jdglProgressCorrectionTrackDetail);
    }

    @Transactional
    public int deleteJdglProgressCorrectionTrackDetailByPks(List<Long> jdglProgressCorrectionTrackDetailPkList) {
        return jdglProgressCorrectionTrackDetailMapper
            .deleteJdglProgressCorrectionTrackDetailByPks(jdglProgressCorrectionTrackDetailPkList);
    }

    public List<JdglProgressCorrectionTrackDetail> getDetailListByTackId(Long trackId) {
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail = new JdglProgressCorrectionTrackDetail();
        jdglProgressCorrectionTrackDetail.setTrackId(trackId);
        return jdglProgressCorrectionTrackDetailMapper
            .getJdglProgressCorrectionTrackDetailList(jdglProgressCorrectionTrackDetail);
    }
}
