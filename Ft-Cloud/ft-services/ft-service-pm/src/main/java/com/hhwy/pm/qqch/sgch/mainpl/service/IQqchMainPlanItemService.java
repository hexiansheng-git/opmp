package com.hhwy.pm.qqch.sgch.mainpl.service;

import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanItem;
import com.hhwy.pm.qqch.sgch.mainpl.domain.vo.QqchMainPlanItemVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 陈锦豪
 * @date 2023-08-29 15:12:27
 * @remark
 */
public interface IQqchMainPlanItemService {

    QqchMainPlanItem getQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem);

    List<QqchMainPlanItem> getQqchMainPlanItemList(QqchMainPlanItem qqchMainPlanItem);

    List<QqchMainPlanItem> getListByItemCodes(String itemCodes);

    List<QqchMainPlanItem> getQqchMainPlanItemList4Lazy(QqchMainPlanItem qqchMainPlanItem);

    List<QqchMainPlanItem> getQqchMainPlanItemListNoTree(QqchMainPlanItem qqchMainPlanItem);

    int insertQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem);

    int insertQqchMainPlanItemList(List<QqchMainPlanItem> qqchMainPlanItemList);

    int updateQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem);

    int updateQqchMainPlanItemList(List<QqchMainPlanItem> qqchMainPlanItemList);

    int deleteQqchMainPlanItem(QqchMainPlanItem qqchMainPlanItem);

    int deleteQqchMainPlanItemByPks(List<Long> qqchMainPlanItemPkList);

    List<QqchMainPlanItem> getKeyRoad(QqchMainPlanItem qqchMainPlanItemParam);

    QqchMainPlanItem getProjStartAndFinish(BigDecimal version);

    /**
     * 获取里程碑数据
     * @param version
     * @return
     */
    List<QqchMainPlanItem> getMilestoneList(BigDecimal version);

    int deleteQqchMainPlanByVersion(BigDecimal version);

    void confirm(QqchMainPlanItemVo qqchMainPlanItemVoParam);

    /**
     * 根据id获取所有有关联的数据
     * @param ids
     * @return
     */
    List<QqchMainPlanItem> getAllLinkList(List<Long> ids);
}
