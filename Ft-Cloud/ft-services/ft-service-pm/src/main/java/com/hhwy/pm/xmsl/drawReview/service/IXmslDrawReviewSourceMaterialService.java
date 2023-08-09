package com.hhwy.pm.xmsl.drawReview.service;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewSourceMaterial;

import java.util.List;
import java.util.Set;

/**
 * @author wk
 * @date 2023-08-08 18:43:48
 * @remark
 */
public interface IXmslDrawReviewSourceMaterialService {

    XmslDrawReviewSourceMaterial getXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial);

    List<XmslDrawReviewSourceMaterial> getXmslDrawReviewSourceMaterialList(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial);

    List<XmslDrawReviewSourceMaterial> getByMaterId(Set<Long> materIdSet);

    int insertXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial);

    int insertXmslDrawReviewSourceMaterialList(List<XmslDrawReviewSourceMaterial> xmslDrawReviewSourceMaterialList);

    int updateXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial);

    int updateXmslDrawReviewSourceMaterialList(List<XmslDrawReviewSourceMaterial> xmslDrawReviewSourceMaterialList);

    int deleteXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial);

    int deleteXmslDrawReviewSourceMaterialByPks(List<Long> xmslDrawReviewSourceMaterialPkList);
}
