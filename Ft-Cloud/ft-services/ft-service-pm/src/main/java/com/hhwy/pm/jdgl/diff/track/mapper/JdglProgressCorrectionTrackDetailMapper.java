package com.hhwy.pm.jdgl.diff.track.mapper;

import com.hhwy.pm.jdgl.diff.track.domain.JdglProgressCorrectionTrackDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-24 16:51:15
 * @remark 进度纠偏跟踪详情
 */
public interface JdglProgressCorrectionTrackDetailMapper {

    JdglProgressCorrectionTrackDetail getJdglProgressCorrectionTrackDetail(
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail);

    List<JdglProgressCorrectionTrackDetail> getJdglProgressCorrectionTrackDetailList(
        JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail);

    int insertJdglProgressCorrectionTrackDetail(JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail);

    int insertJdglProgressCorrectionTrackDetailList(
        @Param("jdglProgressCorrectionTrackDetailList") List<JdglProgressCorrectionTrackDetail> jdglProgressCorrectionTrackDetailList);

    int updateJdglProgressCorrectionTrackDetail(JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail);

    int updateJdglProgressCorrectionTrackDetailList(
        @Param("list") List<JdglProgressCorrectionTrackDetail> jdglProgressCorrectionTrackDetailList);

    int deleteJdglProgressCorrectionTrackDetail(JdglProgressCorrectionTrackDetail jdglProgressCorrectionTrackDetail);

    int deleteJdglProgressCorrectionTrackDetailByPks(
        @Param("jdglProgressCorrectionTrackDetailPkList") List<Long> jdglProgressCorrectionTrackDetailPkList);
}
