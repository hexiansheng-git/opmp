package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.service;

import java.util.Date;
import java.util.List;

import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:27
 * @remark
 */
public interface IJdglMainPlanItemService {

    JdglMainPlanItem getJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem);

    List<JdglMainPlanItem> getJdglMainPlanItemList(JdglMainPlanItem jdglMainPlanItem);

    int insertJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem);

    int insertJdglMainPlanItemList(List<JdglMainPlanItem> jdglMainPlanItemList);

    int updateJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem);

    int updateJdglMainPlanItemList(List<JdglMainPlanItem> jdglMainPlanItemList);

    int deleteJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem);

    int deleteJdglMainPlanItemByPks(List<Long> jdglMainPlanItemPkList);

    List<JdglMainPlanItem> getUsingJdglMainPlanItemList(JdglMainPlanItem jdglMainPlanItem);

    List<JdglMainPlanItem> getUsingJdglMainPlanItemListByDate(Date date);

    List<JdglMainPlanItem> getUsingJdglMainPlanItemListByDateRange(Date startDate, Date endDate);
}
