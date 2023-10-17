package com.hhwy.pm.qqch.sgch.mainpl.mapper;

import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItemPre;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cjh
 * @date 2023-09-19 11:49:57
 * @remark 
 */
public interface QqchMainPlanItemPreMapper {
                                                                                                                                                                                                                                                                                                                
    QqchMainPlanItemPre getQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre);

    List<QqchMainPlanItemPre> getQqchMainPlanItemPreList(QqchMainPlanItemPre qqchMainPlanItemPre);

    int insertQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre);

    int insertQqchMainPlanItemPreList(@Param("qqchMainPlanItemPreList") List<QqchMainPlanItemPre> qqchMainPlanItemPreList);

    int updateQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre);

            int updateQqchMainPlanItemPreList(@Param("qqchMainPlanItemPreList") List<QqchMainPlanItemPre> qqchMainPlanItemPreList);
    
    int deleteQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre);

            int deleteQqchMainPlanItemPreByPks(@Param("qqchMainPlanItemPrePkList") List<Long> qqchMainPlanItemPrePkList);
    }
