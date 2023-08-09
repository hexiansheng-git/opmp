package com.hhwy.pm.xmsl.drawReview.service;

import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewMaterial;

import java.util.List;
import java.util.Set;

/**
 * @author wk
 * @date 2023-08-08 18:43:56
 * @remark
 */
public interface IXmslDrawReviewMaterialService {


    XmslDrawReviewMaterial getXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial);

    List<XmslDrawReviewMaterial> getXmslDrawReviewMaterialList(XmslDrawReviewMaterial xmslDrawReviewMaterial);

    List<XmslDrawReviewMaterial> getByListId(Long mainId, Long wbsId, Set<Long> listIdSet);

    List<XmslDrawReviewMaterial> getByWbsId(Long mainId, Long listId, Set<Long> wbsIdSet);

    int insertXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial);

    int insertXmslDrawReviewMaterialList(List<XmslDrawReviewMaterial> xmslDrawReviewMaterialList);

    int updateXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial);

    int updateXmslDrawReviewMaterialList(List<XmslDrawReviewMaterial> xmslDrawReviewMaterialList);

    int deleteXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial);

    int deleteXmslDrawReviewMaterialByPks(List<Long> xmslDrawReviewMaterialPkList);
}
