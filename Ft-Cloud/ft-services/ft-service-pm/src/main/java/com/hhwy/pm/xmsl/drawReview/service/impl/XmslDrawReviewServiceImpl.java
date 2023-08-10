package com.hhwy.pm.xmsl.drawReview.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.*;
import com.hhwy.pm.xmsl.drawReview.dto.XmslDrawReviewDto;
import com.hhwy.pm.xmsl.drawReview.service.*;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.Constant;
import com.hhwy.utils.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewMapper;
import com.hhwy.utils.idworker.IdWorker;

/**
 * 图纸复核service
 * @author wk
 * @date 2023-08-07 11:23:32
 * @remark 
 */
@Service
public class XmslDrawReviewServiceImpl implements IXmslDrawReviewService{
    @Autowired
    private XmslDrawReviewMapper xmslDrawReviewMapper;
    @Autowired
    private IXmslWbsService wbsService;
    @Autowired
    private IXmslContractListService contractListService;
    @Autowired
    private IXmslDrawReviewWbsService drawReviewWbsService;
    @Autowired
    private IXmslDrawReviewListService drawReviewListService;
    @Autowired
    private IXmslDrawReviewMaterialService materialService;
    @Autowired
    private IXmslDrawReviewSourceMaterialService sourceMaterialService;
    @Autowired
    private IXmslDrawReviewRelationService relationService;
                                                                                                                                                                                                        
    public XmslDrawReview getXmslDrawReview(XmslDrawReview xmslDrawReview) {
        return xmslDrawReviewMapper.getXmslDrawReview(xmslDrawReview);
    }

    public List<XmslDrawReview> getXmslDrawReviewList(XmslDrawReview xmslDrawReview) {
        return xmslDrawReviewMapper.getXmslDrawReviewList(xmslDrawReview);
    }

    @Override
    public XmslDrawReview getById(Long id) {
        XmslDrawReview query = new XmslDrawReview();
        query.setId(id);
        return xmslDrawReviewMapper.getXmslDrawReview(query);
    }

    @Override
    public XmslDrawReview getLast() {
        return this.xmslDrawReviewMapper.getLast();
    }

    @Override
    public List<XmslDrawReviewList> relationWbsList(Integer version, Long mainId,String wbsCode,Long wbsId) {
        if(StringUtils.isBlank(wbsCode))
            return new ArrayList<>(2);
        List<XmslDrawReviewRelation> relationList = null;
        if(version==null){ //未保存版本的话，取最新
            version = this.xmslDrawReviewMapper.selectMaxEffectVersion();
            //取最新wbs对应的清单
            XmslWbs wbs = wbsService.getByCode(wbsCode);
            if(wbs == null)
                return new ArrayList<>();
            String listCodeStr = wbs.getListCode();
            Set<String> listCodeSet = SetUtils.hashSet(listCodeStr.split(","));
            relationList = relationService.relationList(version,wbsCode,listCodeSet);
        }else{
            relationList = relationService.relationList(version,wbsCode);
            if(CollectionUtils.isEmpty(relationList))
                return new ArrayList<>(2);
        }
        Set<Long> listIdSet = relationList.stream().map(r->r.getListId()).collect(Collectors.toSet());
        //查询清单
        List<XmslDrawReviewList> list = this.drawReviewListService.getByIds(listIdSet);
        //获取清单对应的细目、配合比
        Map<Long,XmslDrawReviewList> listMap = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            XmslDrawReviewList temp = list.get(i);
            listMap.put(temp.getId(),temp);
        }
        //细目&配合比
        List<XmslDrawReviewMaterial> materialList = materialService.getByListId(mainId,wbsId,listIdSet);
        Map<Long,XmslDrawReviewMaterial> materMap = new HashMap<>(materialList.size());
        for (int i = 0; i < materialList.size(); i++) {
            XmslDrawReviewMaterial temp = materialList.get(i);
            materMap.put(temp.getId(),temp);
            //填充到清单
            XmslDrawReviewList tempList = listMap.get(temp.getListId());
            tempList.setMaterialList(ObjectUtils.add2List(tempList.getMaterialList(),temp));
        }
        List<XmslDrawReviewSourceMaterial> sourceMaterList = sourceMaterialService.getByMaterId(materMap.keySet());
        //填充数据到父级
        for (int i = 0; i < sourceMaterList.size(); i++) {
            XmslDrawReviewSourceMaterial temp = sourceMaterList.get(i);
            XmslDrawReviewMaterial material = materMap.get(temp.getMaterialId());
            material.setMaterialList(ObjectUtils.add2List(material.getMaterialList(),temp));
        }
        return list;
    }

    @Override
    public List<XmslDrawReviewWbs> relationList(Integer version, Long mainId, String listCode, Long listId) {
        if(StringUtils.isBlank(listCode))
            return new ArrayList<>(2);
        List<XmslDrawReviewRelation> relationList = null;
        if(version==null){ //未保存版本的话，取最新
            version = this.xmslDrawReviewMapper.selectMaxEffectVersion();
            //取最新wbs对应的清单
            String[] wbsCodes = WbsRedisUtils.getWbsCodeByListCode(listCode);
            if(ArrayUtils.isEmpty(wbsCodes))
                return new ArrayList<>();
            XmslDrawReviewRelation query = new XmslDrawReviewRelation();
            query.setVersion(version);
            query.setListCode(listCode);
            query.setWbsCodeSet(SetUtils.hashSet(wbsCodes));
            relationList = relationService.getXmslDrawReviewRelationList(query);
        }else{
            XmslDrawReviewRelation query = new XmslDrawReviewRelation();
            query.setVersion(version);
            query.setListCode(listCode);
            relationList = relationService.getXmslDrawReviewRelationList(query);
            if(CollectionUtils.isEmpty(relationList))
                return new ArrayList<>(2);
        }
        if(CollectionUtils.isEmpty(relationList))
            return new ArrayList<>(2);

        Set<Long> wbsIdSet = relationList.stream().map(r->r.getWbsId()).collect(Collectors.toSet());
        //查询清单
        List<XmslDrawReviewWbs> list = this.drawReviewWbsService.getByIds(wbsIdSet);
        //获取清单对应的细目、配合比
        Map<Long,XmslDrawReviewWbs> listMap = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            XmslDrawReviewWbs temp = list.get(i);
            listMap.put(temp.getId(),temp);
        }
        //细目&配合比
        List<XmslDrawReviewMaterial> materialList = materialService.getByWbsId(mainId,listId,wbsIdSet);
        Map<Long,XmslDrawReviewMaterial> materMap = new HashMap<>(materialList.size());
        for (int i = 0; i < materialList.size(); i++) {
            XmslDrawReviewMaterial temp = materialList.get(i);
            materMap.put(temp.getId(),temp);
            //填充到清单
            XmslDrawReviewWbs tempWbs = listMap.get(temp.getListId());
            tempWbs.setMaterialList(ObjectUtils.add2List(tempWbs.getMaterialList(),temp));
        }
        List<XmslDrawReviewSourceMaterial> sourceMaterList = sourceMaterialService.getByMaterId(materMap.keySet());
        //填充数据到父级
        for (int i = 0; i < sourceMaterList.size(); i++) {
            XmslDrawReviewSourceMaterial temp = sourceMaterList.get(i);
            XmslDrawReviewMaterial material = materMap.get(temp.getMaterialId());
            material.setMaterialList(ObjectUtils.add2List(material.getMaterialList(),temp));
        }
        return list;
    }

    /**
     * 将合同清单 》 图纸复核清单
     * @param codeSet
     * @return
     */
    private List<XmslDrawReviewList> getDrawListByListCodes(Set<String> codeSet){
        List<XmslContractList> list = contractListService.getByCodes(codeSet);
        List<XmslDrawReviewList> resuList = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            XmslContractList temp = list.get(i);
            XmslDrawReviewList tempList = new XmslDrawReviewList();
            BeanUtils.copyProperties(temp,tempList);
            resuList.add(tempList);
            tempList.setListId(tempList.getId());
            tempList.setId(null);
        }
        return resuList;
    }

//    List<XmslDrawReviewRelation> relationList = relationService.relationList(version,wbsCode);
//    Set<Long> listIdSet = relationList.stream().map(r->r.getListId()).collect(Collectors.toSet());
//    list = drawReviewListService.getByIds(listIdSet);

    @Override
    public Integer hasChange() {
        Integer count =  this.xmslDrawReviewMapper.getXmslDrawReviewCount(new XmslDrawReview());
        return count!=null&&count>0?1:0;
    }

    @Override
    @Transactional
    public void save(XmslDrawReviewDto dto) {
        if(dto.getId()==null){
            new AddBaseInfoUtil<>(dto);
            dto.setVersion(dto.getVersion()==null?1:dto.getVersion());
            dto.setValid(Constant.NO_INT);
            xmslDrawReviewMapper.insertXmslDrawReview(dto);
        }else{
            new AddBaseInfoUtil<>().update(dto);
            dto.setValid(Constant.NO_INT);
            xmslDrawReviewMapper.updateXmslDrawReview(dto);
        }
        //清单或wbs保存

//        this.xmslDrawReviewMapper.getXmslDrawReview();
    }


    @Transactional
    public int insertXmslDrawReview(XmslDrawReview xmslDrawReview) {
        xmslDrawReview.setId(IdWorker.createId());
        xmslDrawReview.setCreateUser(SecurityUtils.getUserName());
        xmslDrawReview.setCreateTime(DateUtils.getNowDate());
        return xmslDrawReviewMapper.insertXmslDrawReview(xmslDrawReview);
    }

    @Transactional
    public int insertXmslDrawReviewList(List<XmslDrawReview> xmslDrawReviewList) {
        for (XmslDrawReview xmslDrawReview : xmslDrawReviewList) {
            xmslDrawReview.setId(IdWorker.createId());
            xmslDrawReview.setCreateUser(SecurityUtils.getUserName());
            xmslDrawReview.setCreateTime(DateUtils.getNowDate());
        }
        return xmslDrawReviewMapper.insertXmslDrawReviewList(xmslDrawReviewList);
    }

    @Transactional
    public int updateXmslDrawReview(XmslDrawReview xmslDrawReview) {
        xmslDrawReview.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReview.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewMapper.updateXmslDrawReview(xmslDrawReview);
    }

    
    @Transactional
    public int deleteXmslDrawReview(XmslDrawReview xmslDrawReview) {
        xmslDrawReview.setUpdateUser(SecurityUtils.getUserName());
        xmslDrawReview.setUpdateTime(DateUtils.getNowDate());
        return xmslDrawReviewMapper.deleteXmslDrawReview(xmslDrawReview);
    }
}
