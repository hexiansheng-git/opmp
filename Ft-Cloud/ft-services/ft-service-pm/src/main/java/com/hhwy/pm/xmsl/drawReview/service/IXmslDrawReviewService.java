package com.hhwy.pm.xmsl.drawReview.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReview;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewList;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewSourceMaterial;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewWbs;
import com.hhwy.pm.xmsl.drawReview.dto.XmslDrawReviewDto;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.utils.AddBaseInfoUtil;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;

/**
 * @author wk
 * @date 2023-08-07 11:23:32
 * @remark 
 */
public interface IXmslDrawReviewService {
                                                                                                                                                                                                        
    XmslDrawReview getXmslDrawReview(XmslDrawReview xmslDrawReview);

    List<XmslDrawReview> getXmslDrawReviewList(XmslDrawReview xmslDrawReview);

    /**
     * 获取库中 图纸复核-细目-原材料
     * @return
     */
    List<XmslDrawReviewSourceMaterial> sourceMaterList();

    XmslDrawReview getById(Long id);

    XmslDrawReview getLast();

    XmslDrawReview getEffectLast();

    Integer hasChange();

    /**
     * wbs列表
     * 未生效时，获取全量最新的数据，否则根据版本查询
     * @param map{version,valid,parentId}
     * @return
     */
    List wbsList(Map map);

    /**
     * 工程量清单列表
     * 未生效时，获取全量最新的数据，否则根据版本查询
     * @param map{version,valid,parentId}
     * @return
     */
    List engineeringList(Map map);

    /**
     * 根据wbs信息获取其下明细
     * @param version
     * @param mainId
     * @param wbsCode
     * @param wbsId
     * @return
     */
    List<XmslDrawReviewList> relationWbsList(Integer version, Long mainId,String wbsCode,Long wbsId);

    /**
     * 获取清单信息获取明细
     * @param version
     * @param mainId
     * @param listCode
     * @param listId
     * @return
     */
    List<XmslDrawReviewWbs> relationList(Integer version, Long mainId, String listCode, Long listId);

    
    public List<XmslDrawReviewList> getDefaultListRelation(String wbsCode);

    public List<XmslDrawReviewWbs> getDefaultWbsRelation(String listCode);

    /**
     * 保存
     * @param dto
     */
    void save(XmslDrawReviewDto dto);

    int insertXmslDrawReview(XmslDrawReview xmslDrawReview);

    int insertXmslDrawReviewList(List<XmslDrawReview> xmslDrawReviewList);

    int updateXmslDrawReview(XmslDrawReview xmslDrawReview);

    
    void deleteXmslDrawReview(XmslDrawReview xmslDrawReview);

    /**
     * 流程结束
     * @param id
     */
    void finishFlow(Long id);

}
