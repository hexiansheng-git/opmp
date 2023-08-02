package com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.domain.QqchTotalDemandTimeCount;

/**
 * @author ldd
 * @date 2023-08-02 10:55:17
 * @remark 
 */
public interface QqchTotalDemandTimeCountMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    QqchTotalDemandTimeCount getQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount);

    List<QqchTotalDemandTimeCount> getQqchTotalDemandTimeCountList(QqchTotalDemandTimeCount qqchTotalDemandTimeCount);

    int insertQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount);

    int insertQqchTotalDemandTimeCountList(@Param("qqchTotalDemandTimeCountList") List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountList);

    int updateQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount);

            int updateQqchTotalDemandTimeCountList(@Param("qqchTotalDemandTimeCountList") List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountList);
    
    int deleteQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount);

            int deleteQqchTotalDemandTimeCountByPks(@Param("qqchTotalDemandTimeCountPkList") List<Long> qqchTotalDemandTimeCountPkList);
    }
