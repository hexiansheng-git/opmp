package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service;

import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItemPre;

import java.util.List;

/**
 * @author cjh
 * @date 2023-09-19 11:49:57
 * @remark
 */
public interface IJdglMainPlanItemPreService {

    JdglMainPlanItemPre getJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre);

    List<JdglMainPlanItemPre> getJdglMainPlanItemPreList(JdglMainPlanItemPre jdglMainPlanItemPre);

    int insertJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre);

    int insertJdglMainPlanItemPreList(List<JdglMainPlanItemPre> jdglMainPlanItemPreList);

    int updateJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre);

    int updateJdglMainPlanItemPreList(List<JdglMainPlanItemPre> jdglMainPlanItemPreList);

    int deleteJdglMainPlanItemPre(JdglMainPlanItemPre jdglMainPlanItemPre);

    int deleteJdglMainPlanItemPreByPks(List<Long> jdglMainPlanItemPrePkList);
}
