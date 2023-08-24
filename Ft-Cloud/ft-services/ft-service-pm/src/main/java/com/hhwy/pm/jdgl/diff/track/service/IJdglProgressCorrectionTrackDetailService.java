package com.hhwy.pm.jdgl.diff.track.service;

import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrackDetail;
import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-24 16:51:15
 * @remark 进度纠偏跟踪详情
 */
public interface IJdglProgressCorrectionTrackDetailService {

    JdglProgressCorrectionTrackDetail getJdglProgressCorrectionTrackDetail(
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail);

    List<JdglProgressCorrectionTrackDetail> getJdglProgressCorrectionTrackDetailList(
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail);

    int insertJdglProgressCorrectionTrackDetail(JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail);

    int insertJdglProgressCorrectionTrackDetailList(
        List<JdglProgressCorrectionTrackDetail> jdglProgressCorrectionTrackDetailList);

    int updateJdglProgressCorrectionTrackDetail(JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail);

    int updateJdglProgressCorrectionTrackDetailList(
        List<JdglProgressCorrectionTrackDetail> jdglProgressCorrectionTrackDetailList);

    int deleteJdglProgressCorrectionTrackDetail(JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail);

    int deleteJdglProgressCorrectionTrackDetailByPks(List<Long> jdglProgressCorrectionTrackDetailPkList);

    List<JdglProgressCorrectionTrackDetail> getDetailListByTackId(Long trackId);
}
