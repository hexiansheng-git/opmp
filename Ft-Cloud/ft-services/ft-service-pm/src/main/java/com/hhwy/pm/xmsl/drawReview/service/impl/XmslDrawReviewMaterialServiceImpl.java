package com.hhwy.pm.xmsl.drawReview.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.drawReview.domain.XmslDrawReviewMaterial;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewMaterialMapper;
import com.hhwy.pm.xmsl.drawReview.service.IXmslDrawReviewMaterialService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author wk
 * @date 2023-08-08 18:43:56
 * @remark
 */
@Service
public class XmslDrawReviewMaterialServiceImpl implements IXmslDrawReviewMaterialService {

    @Autowired
    private XmslDrawReviewMaterialMapper xmslDrawReviewMaterialMapper;


    public XmslDrawReviewMaterial getXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial) {
        return xmslDrawReviewMaterialMapper.getXmslDrawReviewMaterial(xmslDrawReviewMaterial);
    }

    public List<XmslDrawReviewMaterial> getXmslDrawReviewMaterialList(XmslDrawReviewMaterial xmslDrawReviewMaterial) {
        return xmslDrawReviewMaterialMapper.getXmslDrawReviewMaterialList(xmslDrawReviewMaterial);
    }

    @Override
    public List<XmslDrawReviewMaterial> getByListId(Long mainId, Long wbsId, Set<Long> listIdSet) {
        XmslDrawReviewMaterial queryMater = new XmslDrawReviewMaterial();
        queryMater.setMainId(mainId);
        queryMater.setWbsId(wbsId);
        queryMater.setListIds(listIdSet);
        if(CollectionUtils.isEmpty(listIdSet))
            return new ArrayList<>();
        List<XmslDrawReviewMaterial> materialList = xmslDrawReviewMaterialMapper.getXmslDrawReviewMaterialList(queryMater);
        return materialList;
    }

    @Override
    public List<XmslDrawReviewMaterial> getByWbsId(Long mainId, Long listId, Set<String> wbsIdSet) {
        XmslDrawReviewMaterial queryMater = new XmslDrawReviewMaterial();
        queryMater.setMainId(mainId);
        queryMater.setListId(listId);
        queryMater.setWbsCodes(wbsIdSet);
        List<XmslDrawReviewMaterial> materialList = xmslDrawReviewMaterialMapper.getXmslDrawReviewMaterialList(queryMater);
        return materialList;
    }

    @Transactional
    public int insertXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial) {
        xmslDrawReviewMaterial.setId(IdWorker.createId());
        xmslDrawReviewMaterial.setCreateUser(SecurityUtils.getUserName());
        xmslDrawReviewMaterial.setCreateTime(DateUtils.getNowDate());
        return xmslDrawReviewMaterialMapper.insertXmslDrawReviewMaterial(xmslDrawReviewMaterial);
    }

    @Transactional
    public int insertXmslDrawReviewMaterialList(List<XmslDrawReviewMaterial> xmslDrawReviewMaterialList) {
        if(CollectionUtils.isEmpty(xmslDrawReviewMaterialList))
            return 0;
        return xmslDrawReviewMaterialMapper.insertXmslDrawReviewMaterialList(xmslDrawReviewMaterialList);
    }

    @Transactional
    public int updateXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial) {
        xmslDrawReviewMaterial.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReviewMaterial.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewMaterialMapper.updateXmslDrawReviewMaterial(xmslDrawReviewMaterial);
    }

    @Transactional
    public int updateXmslDrawReviewMaterialList(List<XmslDrawReviewMaterial> xmslDrawReviewMaterialList) {
        for (XmslDrawReviewMaterial xmslDrawReviewMaterial : xmslDrawReviewMaterialList) {
            xmslDrawReviewMaterial.setUpdateUser(SecurityUtils.getUserName());
            xmslDrawReviewMaterial.setUpdateTime(DateUtils.getNowDate());
        }
        return xmslDrawReviewMaterialMapper.updateXmslDrawReviewMaterialList(xmslDrawReviewMaterialList);
    }

    @Transactional
    public int deleteXmslDrawReviewMaterial(XmslDrawReviewMaterial xmslDrawReviewMaterial) {
        xmslDrawReviewMaterial.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReviewMaterial.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewMaterialMapper.deleteXmslDrawReviewMaterial(xmslDrawReviewMaterial);
    }

    @Transactional
    public int deleteXmslDrawReviewMaterialByPks(List<Long> xmslDrawReviewMaterialPkList) {
        return xmslDrawReviewMaterialMapper.deleteXmslDrawReviewMaterialByPks(xmslDrawReviewMaterialPkList);
    }
}
