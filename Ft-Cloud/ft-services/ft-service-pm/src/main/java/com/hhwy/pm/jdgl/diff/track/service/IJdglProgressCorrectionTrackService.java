package com.hhwy.pm.jdgl.diff.track.service;

import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import com.hhwy.pm.jdgl.diff.track.domain.vo.ProgressCorrectionTrackQueryVo;

import java.util.List;

/**
 * @author zhenglili
 * @date 2023-08-24 16:51:08
 * @remark 进度纠偏跟踪
 */
public interface IJdglProgressCorrectionTrackService {

    JdglProgressCorrectionTrack getJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack);

    List<JdglProgressCorrectionTrack> getJdglProgressCorrectionTrackList(
        JdglProgressCorrectionTrack jdglProgressCorrectionTrack);

    int insertJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack);

    int insertJdglProgressCorrectionTrackList(List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList);

    int updateJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack);

    int updateJdglProgressCorrectionTrackList(List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList);

    int deleteJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack);

    int deleteJdglProgressCorrectionTrackByPks(List<Long> jdglProgressCorrectionTrackPkList);

    List<JdglProgressCorrectionTrack> gmList(ProgressCorrectionTrackQueryVo queryVo);
}
