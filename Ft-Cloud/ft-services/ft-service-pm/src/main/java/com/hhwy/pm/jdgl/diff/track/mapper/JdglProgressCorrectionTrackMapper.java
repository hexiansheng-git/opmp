package com.hhwy.pm.jdgl.diff.track.mapper;

import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrack;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-24 16:51:08
 * @remark 进度纠偏跟踪
 */
public interface JdglProgressCorrectionTrackMapper {

    JdglProgressCorrectionTrack getJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack);

    List<JdglProgressCorrectionTrack> getJdglProgressCorrectionTrackList(
        JdglProgressCorrectionTrack jdglProgressCorrectionTrack);

    int insertJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack);

    int insertJdglProgressCorrectionTrackList(
        @Param("jdglProgressCorrectionTrackList") List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList);

    int updateJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack);

    int updateJdglProgressCorrectionTrackList(@Param("list") List<JdglProgressCorrectionTrack> jdglProgressCorrectionTrackList);

    int deleteJdglProgressCorrectionTrack(JdglProgressCorrectionTrack jdglProgressCorrectionTrack);

    int deleteJdglProgressCorrectionTrackByPks(
        @Param("jdglProgressCorrectionTrackPkList") List<Long> jdglProgressCorrectionTrackPkList);
}
