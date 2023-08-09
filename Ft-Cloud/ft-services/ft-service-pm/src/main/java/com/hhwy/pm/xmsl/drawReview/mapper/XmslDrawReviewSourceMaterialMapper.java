package com.hhwy.pm.xmsl.drawReview.mapper;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewSourceMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wk
 * @date 2023-08-08 18:43:48
 * @remark
 */
public interface XmslDrawReviewSourceMaterialMapper {

    XmslDrawReviewSourceMaterial getXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial);

    List<XmslDrawReviewSourceMaterial> getXmslDrawReviewSourceMaterialList(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial);

    int insertXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial);

    int insertXmslDrawReviewSourceMaterialList(@Param("xmslDrawReviewSourceMaterialList") List<XmslDrawReviewSourceMaterial> xmslDrawReviewSourceMaterialList);

    int updateXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial);

    int updateXmslDrawReviewSourceMaterialList(@Param("xmslDrawReviewSourceMaterialList") List<XmslDrawReviewSourceMaterial> xmslDrawReviewSourceMaterialList);

    int deleteXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial);

    int deleteXmslDrawReviewSourceMaterialByPks(@Param("xmslDrawReviewSourceMaterialPkList") List<Long> xmslDrawReviewSourceMaterialPkList);
}
