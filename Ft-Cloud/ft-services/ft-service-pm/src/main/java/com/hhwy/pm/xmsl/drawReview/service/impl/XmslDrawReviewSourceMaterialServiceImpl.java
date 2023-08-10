package com.hhwy.pm.xmsl.drawReview.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewSourceMaterial;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewSourceMaterialMapper;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewSourceMaterialService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author wk
 * @date 2023-08-08 18:43:48
 * @remark 
 */
@Service
public class XmslDrawReviewSourceMaterialServiceImpl implements IXmslDrawReviewSourceMaterialService {

    @Autowired
    private XmslDrawReviewSourceMaterialMapper xmslDrawReviewSourceMaterialMapper;

                                                                                                                                                                                                                                                                        
    public XmslDrawReviewSourceMaterial getXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial) {
        return xmslDrawReviewSourceMaterialMapper.getXmslDrawReviewSourceMaterial(xmslDrawReviewSourceMaterial);
    }

    public List<XmslDrawReviewSourceMaterial> getXmslDrawReviewSourceMaterialList(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial) {
        return xmslDrawReviewSourceMaterialMapper.getXmslDrawReviewSourceMaterialList(xmslDrawReviewSourceMaterial);
    }

    @Override
    public List<XmslDrawReviewSourceMaterial> getByMaterId(Set<Long> materIdSet) {
        if(CollectionUtils.isEmpty(materIdSet))
            return new ArrayList<>(2);
        XmslDrawReviewSourceMaterial material = new XmslDrawReviewSourceMaterial();
        material.setMaterialIds(materIdSet);
        return xmslDrawReviewSourceMaterialMapper.getXmslDrawReviewSourceMaterialList(material);
    }

    @Transactional
    public int insertXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial) {
        xmslDrawReviewSourceMaterial.setId(IdWorker.createId());
        xmslDrawReviewSourceMaterial.setCreateUser(SecurityUtils.getUserName());
        xmslDrawReviewSourceMaterial.setCreateTime(DateUtils.getNowDate());
        return xmslDrawReviewSourceMaterialMapper.insertXmslDrawReviewSourceMaterial(xmslDrawReviewSourceMaterial);
    }

    @Transactional
    public int insertXmslDrawReviewSourceMaterialList(List<XmslDrawReviewSourceMaterial> xmslDrawReviewSourceMaterialList) {
        if(CollectionUtils.isEmpty(xmslDrawReviewSourceMaterialList))
            return 0;
        return xmslDrawReviewSourceMaterialMapper.insertXmslDrawReviewSourceMaterialList(xmslDrawReviewSourceMaterialList);
    }

    @Transactional
    public int updateXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial) {
        xmslDrawReviewSourceMaterial.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReviewSourceMaterial.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewSourceMaterialMapper.updateXmslDrawReviewSourceMaterial(xmslDrawReviewSourceMaterial);
    }

            @Transactional
        public int updateXmslDrawReviewSourceMaterialList(List<XmslDrawReviewSourceMaterial> xmslDrawReviewSourceMaterialList) {
            for (XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial : xmslDrawReviewSourceMaterialList) {
                xmslDrawReviewSourceMaterial.setUpdateUser(SecurityUtils.getUserName());
                xmslDrawReviewSourceMaterial.setUpdateTime(DateUtils.getNowDate());
            }
            return xmslDrawReviewSourceMaterialMapper.updateXmslDrawReviewSourceMaterialList(xmslDrawReviewSourceMaterialList);
        }
    
    @Transactional
    public int deleteXmslDrawReviewSourceMaterial(XmslDrawReviewSourceMaterial xmslDrawReviewSourceMaterial) {
        xmslDrawReviewSourceMaterial.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReviewSourceMaterial.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewSourceMaterialMapper.deleteXmslDrawReviewSourceMaterial(xmslDrawReviewSourceMaterial);
    }

            @Transactional
        public int deleteXmslDrawReviewSourceMaterialByPks(List<Long> xmslDrawReviewSourceMaterialPkList) {
            return xmslDrawReviewSourceMaterialMapper.deleteXmslDrawReviewSourceMaterialByPks(xmslDrawReviewSourceMaterialPkList);
        }
    }
