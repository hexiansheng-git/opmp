package com.hhwy.pm.xmsl.drawReview.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewMaterial;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;

/**
 * @author wk
 * @date 2023-08-07 11:23:32
 * @remark 
 */
public interface XmslDrawReviewMapper {

    Integer selectMaxEffectVersion();

    XmslDrawReview getXmslDrawReview(XmslDrawReview xmslDrawReview);

    List<XmslDrawReview> getXmslDrawReviewList(XmslDrawReview xmslDrawReview);

    /**
     * 查询所有版本的父级
     * @param version  版本号
     * @return
     */
    List<XmslDrawReviewWbs> selectWbsAncestor(Integer version);

    /**
     * 查询所有版本的父级
     * @param version  版本号
     * @return
     */
    List<XmslDrawReviewList> selectListAncestor(Integer version);

    Integer getXmslDrawReviewCount(XmslDrawReview xmslDrawReview);

    XmslDrawReview getLast(@Param("valid") Integer valid);

    List<XmslDrawReviewList> selectNullList(BigDecimal version);

    List<XmslDrawReviewMaterial> selectNullMater(Long id);

    List<XmslContractList> latestListId(@Param("masterId") Long masterId,@Param("ptVar1") String ptVar1);

    List<XmslDrawReviewList> relationListCode(XmslDrawReviewList list);

    int insertXmslDrawReview(XmslDrawReview xmslDrawReview);

    int insertXmslDrawReviewList(@Param("xmslDrawReviewList") List<XmslDrawReview> xmslDrawReviewList);

    int updateXmslDrawReview(XmslDrawReview xmslDrawReview);

    int updateVersionFlag(Map map);
    
    int deleteXmslDrawReview(XmslDrawReview xmslDrawReview);

    /**
     * 删除wbs到清单的挂接关系
     * @param map {mainId,wbsIds,listIds}
     * @return
     */
    int deleteRelation(Map map);
    int deleteWbs(Map map);
    int deleteWbsByCode(Map map);
    int deleteList(Map map);
    int deleteMaterial(Map map);
    int deleteSourceMaterial(Map map);

}
