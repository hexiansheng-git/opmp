package com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemand.domain.QqchTotalDemand;

/**
 * @author ldd
 * @date 2023-08-02 10:55:15
 * @remark 
 */
public interface QqchTotalDemandMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchTotalDemand getQqchTotalDemand(QqchTotalDemand qqchTotalDemand);

    List<QqchTotalDemand> getQqchTotalDemandList(QqchTotalDemand qqchTotalDemand);

    int insertQqchTotalDemand(QqchTotalDemand qqchTotalDemand);

    int insertQqchTotalDemandList(@Param("qqchTotalDemandList") List<QqchTotalDemand> qqchTotalDemandList);

    int updateQqchTotalDemand(QqchTotalDemand qqchTotalDemand);

            int updateQqchTotalDemandList(@Param("qqchTotalDemandList") List<QqchTotalDemand> qqchTotalDemandList);
    
    int deleteQqchTotalDemand(QqchTotalDemand qqchTotalDemand);

            int deleteQqchTotalDemandByPks(@Param("qqchTotalDemandPkList") List<Long> qqchTotalDemandPkList);
    }
