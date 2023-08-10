package com.hhwy.pm.qqch.preparation.safe.monitor.mapper;

import com.hhwy.pm.qqch.preparation.safe.monitor.domain.QqchVideoMonitorInfo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-10 16:50:39
 * @remark 8.12.1 视频监控信息
 */
public interface QqchVideoMonitorInfoMapper {

    QqchVideoMonitorInfo getQqchVideoMonitorInfo(QqchVideoMonitorInfo qqchVideoMonitorInfo);

    List<QqchVideoMonitorInfo> getQqchVideoMonitorInfoList(QqchVideoMonitorInfo qqchVideoMonitorInfo);

    int insertQqchVideoMonitorInfo(QqchVideoMonitorInfo qqchVideoMonitorInfo);

    int insertQqchVideoMonitorInfoList(
        @Param("qqchVideoMonitorInfoList") List<QqchVideoMonitorInfo> qqchVideoMonitorInfoList);

    int updateQqchVideoMonitorInfo(QqchVideoMonitorInfo qqchVideoMonitorInfo);

    int updateQqchVideoMonitorInfoList(@Param("list") List<QqchVideoMonitorInfo> qqchVideoMonitorInfoList);

    int deleteQqchVideoMonitorInfo(QqchVideoMonitorInfo qqchVideoMonitorInfo);

    int deleteQqchVideoMonitorInfoByPks(@Param("qqchVideoMonitorInfoPkList") List<Long> qqchVideoMonitorInfoPkList);
}
