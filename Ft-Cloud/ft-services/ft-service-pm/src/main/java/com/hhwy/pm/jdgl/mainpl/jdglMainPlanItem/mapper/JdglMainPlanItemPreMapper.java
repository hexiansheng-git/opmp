package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.mapper;

import java.util.List;

import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItemPre;
import org.apache.ibatis.annotations.Param;

/**
 * @author cjh
 * @date 2023-09-19 11:49:57
 * @remark 
 */
public interface JdglMainPlanItemPreMapper {
                                                                                                                                                                                                                                                                                                                
    JdglMainPlanItemPre getJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre);

    List<JdglMainPlanItemPre> getJdglMainPlanItemPreList(JdglMainPlanItemPre jdglMainPlanItemPre);

    int insertJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre);

    int insertJdglMainPlanItemPreList(@Param("jdglMainPlanItemPreList") List<JdglMainPlanItemPre> jdglMainPlanItemPreList);

    int updateJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre);

            int updateJdglMainPlanItemPreList(@Param("jdglMainPlanItemPreList") List<JdglMainPlanItemPre> jdglMainPlanItemPreList);
    
    int deleteJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre);

            int deleteJdglMainPlanItemPreByPks(@Param("jdglMainPlanItemPrePkList") List<Long> jdglMainPlanItemPrePkList);
    }
