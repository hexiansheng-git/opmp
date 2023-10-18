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

    QqchMainPlanItem getQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem);

    List<QqchMainPlanItem> getQqchMainPlanItemList(QqchMainPlanItem qqchMainPlanItem);

    int insertQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem);

    int insertQqchMainPlanItemList(@Param("qqchMainPlanItemList") List<QqchMainPlanItem> qqchMainPlanItemList);

    int updateQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem);

    int updateQqchMainPlanItemList(@Param("list") List<QqchMainPlanItem> qqchMainPlanItemList);

    int deleteQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem);

    int deleteQqchMainPlanItemByPks(@Param("qqchMainPlanItemPkList") List<Long> qqchMainPlanItemPkList);

    List<QqchMainPlanItem> getUsingQqchMainPlanItemListByDate(@Param("date") Date date, @Param("mainPlanId") Long mainPlanId);

    List<QqchMainPlanItem> getUsingQqchMainPlanItemListByDateRange(@Param("startDate") Date startDate, @Param("finishDate") Date endDate, @Param("mainPlanId") Long mainPlanId);

    QqchMainPlanItem getMaxActualStartDate(@Param("mainPlanId") Long mainPlanId);

    QqchMainPlanItem getProjStartAndFinish(@Param("version") BigDecimal version,@Param("valid") String valid);

    int deleteQqchMainPlanByVersion(@Param("version") BigDecimal version);
}
