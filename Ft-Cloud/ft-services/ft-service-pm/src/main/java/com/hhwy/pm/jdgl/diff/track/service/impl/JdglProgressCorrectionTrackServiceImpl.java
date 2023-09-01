package com.hhwy.pm.jdgl.diff.track.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrackDetail;
import com.hhwy.pm.jdgl.diff.track.domain.vo.ProgressCorrectionTrackQueryVo;
import com.hhwy.pm.jdgl.diff.track.mapper.JdglProgressCorrectionTrackMapper;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackDetailService;
import com.hhwy.pm.jdgl.diff.track.service.IJdglProgressCorrectionTrackService;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 查询单条数据-详情
     *
     * @param jdglProgressCorrectionTrack
     * @return
     */
    public JdglProgressCorrectionTrack getJdglProgressCorrectionTrack(
        JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        JdglProgressCorrectionTrack track = jdglProgressCorrectionTrackMapper
            .getJdglProgressCorrectionTrack(jdglProgressCorrectionTrack);
        if (track != null) {
            List<JdglProgressCorrectionTrackDetail> detailList = jdglProgressCorrectionTrackDetailService
                .getDetailListByTackId(track.getId());
            track.setDetailList(TreeUtil.build(detailList, null));
        }
        return track;
    }

    /**
     * 列表查询
     *
     * @param jdglProgressCorrectionTrack
     * @return
     */
    public List<JdglProgressCorrectionTrack> getJdglProgressCorrectionTrackList(
        JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        return jdglProgressCorrectionTrackMapper.getJdglProgressCorrectionTrackList(jdglProgressCorrectionTrack);
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

    /**
     * 更新保存
     *
     * @param jdglProgressCorrectionTrack
     * @return
     */
    @Transactional
    public int updateJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack) {
        List<JdglProgressCorrectionTrackDetail> detailList = jdglProgressCorrectionTrack.getDetailList();
        int num = 0;
        if (!CollectionUtils.isEmpty(detailList)) {
            // 树转列表
            List<JdglProgressCorrectionTrackDetail> treeList = TreeUtil.treeToList(detailList);
            // 执行更改下操作
            num = jdglProgressCorrectionTrackDetailService.updateJdglProgressCorrectionTrackDetailList(treeList);
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

    @Override
    public List<JdglProgressCorrectionTrack> gmList(ProgressCorrectionTrackQueryVo queryVo) {
        return jdglProgressCorrectionTrackMapper.gmList(queryVo);
    }
}
