package com.hhwy.pm.qqch.preparation.safe.monitor.mapper;

import com.hhwy.pm.qqch.preparation.safe.monitor.domain.QqchMonitorDataParam;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-10 16:50:46
 * @remark 8.12.2 监控数据参数
 */
public interface QqchMonitorDataParamMapper {

    QqchMonitorDataParam getQqchMonitorDataParam(QqchMonitorDataParam qqchMonitorDataParam);

    List<QqchMonitorDataParam> getQqchMonitorDataParamList(QqchMonitorDataParam qqchMonitorDataParam);

    int insertQqchMonitorDataParam(QqchMonitorDataParam qqchMonitorDataParam);

    int insertQqchMonitorDataParamList(
        @Param("qqchMonitorDataParamList") List<QqchMonitorDataParam> qqchMonitorDataParamList);

    int updateQqchMonitorDataParam(QqchMonitorDataParam qqchMonitorDataParam);

    int updateQqchMonitorDataParamList(@Param("list") List<QqchMonitorDataParam> qqchMonitorDataParamList);

    int deleteQqchMonitorDataParam(QqchMonitorDataParam qqchMonitorDataParam);

    int deleteQqchMonitorDataParamByPks(@Param("qqchMonitorDataParamPkList") List<Long> qqchMonitorDataParamPkList);
}
