package com.hhwy.pm.qqch.sgch.mainpl.mapper;

import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:27
 * @remark
 */
public interface QqchMainPlanItemMapper {

    QqchMainPlanItem getQqchMainPlanItem(QqchMainPlanItem jdglMainPlanItem);

    List<QqchMainPlanItem> getQqchMainPlanItemList(QqchMainPlanItem jdglMainPlanItem);

    int insertQqchMainPlanItem(QqchMainPlanItem jdglMainPlanItem);

    int insertQqchMainPlanItemList(@Param("jdglMainPlanItemList") List<QqchMainPlanItem> jdglMainPlanItemList);

    int updateQqchMainPlanItem(QqchMainPlanItem jdglMainPlanItem);

    int updateQqchMainPlanItemList(@Param("list") List<QqchMainPlanItem> jdglMainPlanItemList);

    int deleteQqchMainPlanItem(QqchMainPlanItem jdglMainPlanItem);

    int deleteQqchMainPlanItemByPks(@Param("jdglMainPlanItemPkList") List<Long> jdglMainPlanItemPkList);

    List<QqchMainPlanItem> getUsingQqchMainPlanItemListByDate(@Param("date") Date date, @Param("mainPlanId") Long mainPlanId);

    List<QqchMainPlanItem> getUsingQqchMainPlanItemListByDateRange(@Param("startDate") Date startDate, @Param("finishDate") Date endDate, @Param("mainPlanId") Long mainPlanId);

    QqchMainPlanItem getMaxActualStartDate(@Param("mainPlanId") Long mainPlanId);

    QqchMainPlanItem getProjStartAndFinish(@Param("version") BigDecimal version, String volid);
}
