package com.hhwy.pm.qqch.sgch.qqchconst.mapper;

import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstJob;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:17
 * @remark
 */
public interface QqchConstJobMapper {

    QqchConstJob getQqchConstJob(QqchConstJob qqchConstJob);

    List<QqchConstJob> getQqchConstJobList(QqchConstJob qqchConstJob);

    int insertQqchConstJob(QqchConstJob qqchConstJob);

    int insertQqchConstJobList(@Param("qqchConstJobList") List<QqchConstJob> qqchConstJobList);

    int updateQqchConstJob(QqchConstJob qqchConstJob);

    int updateQqchConstJobList(@Param("list") List<QqchConstJob> list);

    int deleteQqchConstJob(QqchConstJob qqchConstJob);

    int deleteQqchConstJobByPks(@Param("qqchConstJobPkList") List<Long> qqchConstJobPkList);

    int deleteByVersion(@Param("version") BigDecimal version);
    
}
