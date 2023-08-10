package com.hhwy.pm.xmsl.drawReview.mapper;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-08 18:43:56
 * @remark
 */
public interface XmslDrawReviewMaterialMapper {

    XmslDrawReviewMaterial getXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial);

    List<XmslDrawReviewMaterial> getXmslDrawReviewMaterialList(XmslDrawReviewMaterial xmslDrawReviewMaterial);

    int insertXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial);

    int insertXmslDrawReviewMaterialList(@Param("xmslDrawReviewMaterialList") List<XmslDrawReviewMaterial> xmslDrawReviewMaterialList);

    int updateXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial);

    int updateXmslDrawReviewMaterialList(@Param("xmslDrawReviewMaterialList") List<XmslDrawReviewMaterial> xmslDrawReviewMaterialList);

    int deleteXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial);

    int deleteXmslDrawReviewMaterialByPks(@Param("xmslDrawReviewMaterialPkList") List<Long> xmslDrawReviewMaterialPkList);
}
