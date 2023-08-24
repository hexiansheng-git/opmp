package com.hhwy.pm.jdgl.diff.track.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrackDetail;
import com.hhwy.pm.jdgl.diff.track.mapper.JdglProgressCorrectionTrackMapper;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackDetailService;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-08-24 16:51:08
 * @remark 进度纠偏跟踪
 */
@Service
public class JdglProgressCorrectionTrackServiceImpl implements IJdglProgressCorrectionTrackService {

    @Autowired
    private JdglProgressCorrectionTrackMapper jdglProgressCorrectionTrackMapper;

    @Autowired
    private IJdglProgressCorrectionTrackDetailService jdglProgressCorrectionTrackDetailService;

    public JdglProgressCorrectionTrack getJdglProgressCorrectionTrack(
        JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        return jdglProgressCorrectionTrackMapper.getJdglProgressCorrectionTrack(jdglProgressCorrectionTrack);
    }

    /**
     * 列表查询
     *
     * @param jdglProgressCorrectionTrack
     * @return
     */
    public List<JdglProgressCorrectionTrack> getJdglProgressCorrectionTrackList(
        JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {

        List<JdglProgressCorrectionTrack> list = jdglProgressCorrectionTrackMapper
            .getJdglProgressCorrectionTrackList(jdglProgressCorrectionTrack);
        if (!CollectionUtils.isEmpty(list)) {
            for (JdglProgressCorrectionTrack track : list) {
                List<JdglProgressCorrectionTrackDetail> detailList = jdglProgressCorrectionTrackDetailService
                    .getDetailListByTackId(track.getId());
                track.setDetailList(detailList);
            }
        }
        return list;
    }

    @Transactional
    public int insertJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        jdglProgressCorrectionTrack.setId(IdWorker.createId());
        jdglProgressCorrectionTrack.setCreateUser(SecurityUtils.getUserName());
        jdglProgressCorrectionTrack.setCreateTime(DateUtils.getNowDate());
        return jdglProgressCorrectionTrackMapper.insertJdglProgressCorrectionTrack(jdglProgressCorrectionTrack);
    }

    @Transactional
    public int insertJdglProgressCorrectionTrackList(
        List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList) {
        for (JdglProgressCorrectionTrack jdglProgressCorrectionTrack : jdglProgressCorrectionTrackList) {
            jdglProgressCorrectionTrack.setId(IdWorker.createId());
            jdglProgressCorrectionTrack.setCreateUser(SecurityUtils.getUserName());
            jdglProgressCorrectionTrack.setCreateTime(DateUtils.getNowDate());
        }
        return jdglProgressCorrectionTrackMapper.insertJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackList);
    }

    @Transactional
    public int updateJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        List<JdglProgressCorrectionTrackDetail> detailList = jdglProgressCorrectionTrack.getDetailList();
        int num = 0;
        if (!CollectionUtils.isEmpty(detailList)) {
            // 执行更改下操作
            num = jdglProgressCorrectionTrackDetailService.updateJdglProgressCorrectionTrackDetailList(detailList);
        }
        return num;
    }

    @Transactional
    public int updateJdglProgressCorrectionTrackList(
        List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList) {
        for (JdglProgressCorrectionTrack jdglProgressCorrectionTrack : jdglProgressCorrectionTrackList) {
            jdglProgressCorrectionTrack.setUpdateUser(SecurityUtils.getUserName());
            jdglProgressCorrectionTrack.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglProgressCorrectionTrackMapper.updateJdglProgressCorrectionTrackList(jdglProgressCorrectionTrackList);
    }

    @Transactional
    public int deleteJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        jdglProgressCorrectionTrack.setUpdateUser(SecurityUtils.getUserName());
        jdglProgressCorrectionTrack.setUpdateTime(DateUtils.getNowDate());
        return jdglProgressCorrectionTrackMapper.deleteJdglProgressCorrectionTrack(jdglProgressCorrectionTrack);
    }

    @Transactional
    public int deleteJdglProgressCorrectionTrackByPks(List<Long> jdglProgressCorrectionTrackPkList) {
        return jdglProgressCorrectionTrackMapper
            .deleteJdglProgressCorrectionTrackByPks(jdglProgressCorrectionTrackPkList);
    }
}
