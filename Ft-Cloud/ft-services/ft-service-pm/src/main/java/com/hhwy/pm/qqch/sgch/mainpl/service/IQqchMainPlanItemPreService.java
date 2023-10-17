package com.hhwy.pm.qqch.sgch.mainpl.service;

import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItemPre;

import java.util.List;

/**
 * @author cjh
 * @date 2023-09-19 11:49:57
 * @remark
 */
public interface IQqchMainPlanItemPreService {

    QqchMainPlanItemPre getQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre);

    List<QqchMainPlanItemPre> getQqchMainPlanItemPreList(QqchMainPlanItemPre qqchMainPlanItemPre);

    int insertQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre);

    int insertQqchMainPlanItemPreList(List<QqchMainPlanItemPre> qqchMainPlanItemPreList);

    int updateQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre);

    int updateQqchMainPlanItemPreList(List<QqchMainPlanItemPre> qqchMainPlanItemPreList);

    int deleteQqchMainPlanItemPre(QqchMainPlanItemPre qqchMainPlanItemPre);

    int deleteQqchMainPlanItemPreByPks(List<Long> qqchMainPlanItemPrePkList);
}
