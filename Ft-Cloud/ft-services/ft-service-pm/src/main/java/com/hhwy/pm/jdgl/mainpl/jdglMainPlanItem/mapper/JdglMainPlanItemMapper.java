package com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.jdgl.mainpl.jdglMainPlanItem.domain.JdglMainPlanItem;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:27
 * @remark
 */
public interface JdglMainPlanItemMapper {

    JdglMainPlanItem getJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem);

    List<JdglMainPlanItem> getJdglMainPlanItemList(JdglMainPlanItem jdglMainPlanItem);

    int insertJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem);

    int insertJdglMainPlanItemList(@Param("jdglMainPlanItemList") List<JdglMainPlanItem> jdglMainPlanItemList);

    int updateJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem);

    int updateJdglMainPlanItemList(@Param("list") List<JdglMainPlanItem> jdglMainPlanItemList);

    int deleteJdglMainPlanItem(JdglMainPlanItem jdglMainPlanItem);

    int deleteJdglMainPlanItemByPks(@Param("jdglMainPlanItemPkList") List<Long> jdglMainPlanItemPkList);

    List<JdglMainPlanItem> getUsingJdglMainPlanItemListByDate(@Param("date") Date date,@Param("mainPlanId") Long mainPlanId);

    List<JdglMainPlanItem> getUsingJdglMainPlanItemListByDateRange(@Param("startDate") Date startDate, @Param("finishDate") Date endDate,@Param("mainPlanId") Long mainPlanId);

    JdglMainPlanItem getMaxActualStartDate(@Param("mainPlanId") Long mainPlanId);

    JdglMainPlanItem getProjStartAndFinish(@Param("mainPlanId") Long mainPlanId);

    List<JdglMainPlanItem> getUsingJdglMainPlanItemByItemCodes(@Param("mainPlanId") Long mainPlanId,@Param("itemCodes") List<String> itemCodes);
}
