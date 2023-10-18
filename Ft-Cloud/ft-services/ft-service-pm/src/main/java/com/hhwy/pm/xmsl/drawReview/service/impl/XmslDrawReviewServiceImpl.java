package com.hhwy.pm.xmsl.drawReview.service.impl;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.common.tenant.utils.TenantDataSourceUtils;
import com.hhwy.domain.base.system.material.MaterialInfo;
import com.hhwy.pm.core.system.SystemApiService;
import com.hhwy.pm.xmsl.contractInfo.domain.XmslContractList;
import com.hhwy.pm.xmsl.contractInfo.service.IXmslContractListService;
import com.hhwy.pm.xmsl.drawReview.domain.*;
import com.hhwy.pm.xmsl.drawReview.dto.XmslDrawReviewDto;
import com.hhwy.pm.xmsl.drawReview.mapper.XmslDrawReviewMapper;
import com.hhwy.pm.xmsl.drawReview.service.*;
import com.hhwy.pm.xmsl.wbs.WbsRedisUtils;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbs;
import com.hhwy.pm.xmsl.wbs.domain.XmslWbsMain;
import com.hhwy.pm.xmsl.wbs.dto.XmslWbsDto;
import com.hhwy.pm.xmsl.wbs.service.IXmslWbsService;
import com.hhwy.pm.xmsl.xmslEngineeringReport.service.IXmslEngineeringReportService;
import com.hhwy.pm.xmsl.xmslMaterialReport.service.IXmslMaterialReportService;
import com.hhwy.system.api.domain.SysDictData;
import com.hhwy.utils.*;
import com.hhwy.utils.bigDecimalUtils.BigDecimalUtils;
import com.hhwy.utils.exception.CustomBusinessException;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

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
    @Autowired
    private IXmslEngineeringReportService engineeringReportService;
    @Autowired
    private IXmslMaterialReportService materialReportService;
    @Autowired
    private SystemApiService systemApiService;

    public XmslDrawReview getXmslDrawReview(XmslDrawReview xmslDrawReview) {
        return xmslDrawReviewMapper.getXmslDrawReview(xmslDrawReview);
    }

    public List<XmslDrawReview> getXmslDrawReviewList(XmslDrawReview xmslDrawReview) {
        return xmslDrawReviewMapper.getXmslDrawReviewList(xmslDrawReview);
    }

    @Override
    public List<XmslDrawReviewSourceMaterial> sourceMaterList() {
        List<SysDictData> list = systemApiService.selectDictDataByType("xmsl_source_material");
        List<MaterialInfo> materialInfoList = MaterialUtils.getMaterialInfoByCodes(list.stream().map(r->r.getDictValue()).collect(Collectors.toSet()));
        List<XmslDrawReviewSourceMaterial> resuList = materialInfoList.stream().map(r->{
            XmslDrawReviewSourceMaterial temp = new XmslDrawReviewSourceMaterial();
            temp.setCode(r.getMaterialCode());
            temp.setName(r.getMaterialName());
            temp.setSpec(r.getMaterialSpec());
            temp.setUnit(r.getUnit());
            return temp;
        }).collect(Collectors.toList());
        return resuList;
    }

    @Override
    public List wbsList(Map map) {
//        if(ObjectUtils.nvlString(map.get("valid")).equals("1")){
//            XmslDrawReviewWbs query = new XmslDrawReviewWbs();
//            query.setParentId(ObjectUtils.nvlLong(map.get("parentId"),-1L));
//            query.setVersion(ObjectUtils.nvl(map.get("version")));
//            query.setVersionFlag(Constant.YES_INT);
//            List<XmslDrawReviewWbs> list = drawReviewWbsService.getXmslDrawReviewWbsList(query);
//            return list;
//        }
        XmslWbs query = new XmslWbs();
        query.setParentId(ObjectUtils.nvlString(map.get("parentId")));
        List<XmslWbs> list = wbsService.latestData(query);
        if(CollectionUtils.isEmpty(list))
            return list;
        Map<String,XmslWbs> wbsMap = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            XmslWbs temp = list.get(i);
            temp.setWbsId(temp.getId());
            temp.setId(null);
            wbsMap.put(temp.getCode(),temp);
        }
        if(ObjectUtils.isEmpty(map.get("version")))
            return list;
        //查询对应的图纸复核wbs
        XmslDrawReviewWbs queryWbs = new XmslDrawReviewWbs();
        queryWbs.setVersion(ObjectUtils.nvl(map.get("version")));
        queryWbs.setVersionFlag(Constant.YES_INT);
        queryWbs.setParams(ObjectUtils.toMap("wbsCodes",wbsMap.keySet()));
        List<XmslDrawReviewWbs> wbsList = drawReviewWbsService.getXmslDrawReviewWbsList(queryWbs);
//        for (int i = 0; i < wbsList.size(); i++) {
//            XmslWbs tempWbs = wbsMap.get(wbsList.get(i).getCode());
////            tempWbs.setId(wbsList.get(i).getId()+"");
//        }
        return list;
    }

    @Override
    public List engineeringList(Map map) {
//        if(ObjectUtils.nvlString(map.get("valid")).equals("1")){
//            XmslDrawReviewList queryList = new XmslDrawReviewList();
//            queryList.setVersion(ObjectUtils.nvl(map.get("version")));
//            queryList.setVersionFlag(Constant.YES_INT);
//            queryList.setPid(ObjectUtils.nvlLong(map.get("parentId"),-1L));
//            List<XmslDrawReviewList> resuList = drawReviewListService.getXmslDrawReviewListList(queryList);
//            return resuList;
//        }
        XmslContractList queryList = new XmslContractList();
        if(ObjectUtils.nvlLong(map.get("parentId"),0L).equals(0L) ){ //合同清单的根级节点pid为null
            queryList.setPid(null);
            queryList.setPtVar1("1");
        }else{
            queryList.setPid(ObjectUtils.nvlLong(map.get("parentId"),0L));
        }
        List<XmslContractList> list = contractListService.getEffectList(queryList);
        if(CollectionUtils.isEmpty(list))
            return new ArrayList(2);
        Map<String,XmslContractList> listMap = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            XmslContractList temp = list.get(i);
            temp.setPtVar2(temp.getCode());
            temp.setPtVar1(String.valueOf(temp.getId()));
            temp.setListId(temp.getId());
            temp.setId(null);
            listMap.put(temp.getCode(),temp);
        }
        if(ObjectUtils.isEmpty(map.get("version")))
            return list;
        //查询对应的图纸复核清单 (图纸复核清单id 填充到list)
        XmslDrawReviewList queryDrawList = new XmslDrawReviewList();
        queryDrawList.setVersion(ObjectUtils.nvl(map.get("version")));
        queryDrawList.setVersionFlag(Constant.YES_INT);
        queryDrawList.setParams(ObjectUtils.toMap("listCodes",listMap.keySet()));
        List<XmslDrawReviewList> drawList = drawReviewListService.getXmslDrawReviewListList(queryDrawList);
        for (int i = 0; i < drawList.size(); i++) {
            XmslContractList tempList = listMap.get(drawList.get(i).getListCode());
//            tempList.setId(drawList.get(i).getId());
        }
        return list;
    }

    @Override
    public XmslDrawReview getById(Long id) {
        XmslDrawReview query = new XmslDrawReview();
        query.setId(id);
        return xmslDrawReviewMapper.getXmslDrawReview(query);
    }

    @Override
    public XmslDrawReview getLast() {
        return this.xmslDrawReviewMapper.getLast(null);
    }

    @Override
    public XmslDrawReview getEffectLast() {
        return this.xmslDrawReviewMapper.getLast(Constant.YES_INT);
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
            relationList = relationService.relationList(version,wbsCode);
        }else{
            relationList = relationService.relationList(version,wbsCode);
        }
        if(CollectionUtils.isEmpty(relationList)){
            if(ObjectUtils.nvl(version) == 1){  //加载默认wbs
                return getByListCodes(wbsCode);
            }else{                              //加载上一版本
                XmslDrawReview last = this.xmslDrawReviewMapper.getLast(1);
                if(last == null){
                    return getByListCodes(wbsCode);
                }
                mainId = last.getId();
                version = last.getVersion();
                relationList = relationService.relationList(version,wbsCode);
            }
        }

        Set<String> listCodeSet = relationList.stream().map(r->r.getListCode()).collect(Collectors.toSet());
        //查询清单
        XmslDrawReviewList query = new XmslDrawReviewList();
        query.setMainId(mainId);
        query.setWbsCode(wbsCode);
        query.setParams(ObjectUtils.toMap("listCodes",listCodeSet));
        List<XmslDrawReviewList> list = this.drawReviewListService.getXmslDrawReviewListList(query);
        //获取清单对应的细目、配合比
        Map<String,XmslDrawReviewList> listMap = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            XmslDrawReviewList temp = list.get(i);
            listMap.put(temp.getListCode(),temp);
        }
        //细目&配合比
        List<XmslDrawReviewMaterial> materialList = materialService.getByListId(mainId,wbsCode,listCodeSet);
        Map<Long,XmslDrawReviewMaterial> materMap = new HashMap<>(materialList.size());
        for (int i = 0; i < materialList.size(); i++) {
            XmslDrawReviewMaterial temp = materialList.get(i);
            materMap.put(temp.getId(),temp);
            //填充到清单
            XmslDrawReviewList tempList = listMap.get(temp.getListCode());
            tempList.setMaterialList(ObjectUtils.add2List(tempList.getMaterialList(),temp));
        }
        List<XmslDrawReviewSourceMaterial> sourceMaterList = sourceMaterialService.getByMaterId(materMap.keySet());
        //填充数据到父级
        for (int i = 0; i < sourceMaterList.size(); i++) {
            XmslDrawReviewSourceMaterial temp = sourceMaterList.get(i);
            XmslDrawReviewMaterial material = materMap.get(temp.getMaterialId());
            material.setSourceMaterialList(ObjectUtils.add2List(material.getSourceMaterialList(),temp));
        }
        return list;
    }

    private List<XmslDrawReviewList> getByListCodes(String wbsCode){
        String[] listCodes = WbsRedisUtils.getListCodeByWbsCode(wbsCode);
        if(ArrayUtils.isEmpty(listCodes))
            return new ArrayList<>();
        List<XmslContractList> list = contractListService.getByCodes(SetUtils.hashSet(listCodes));
        List<XmslDrawReviewList> resuList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            XmslContractList temp = list.get(i);
            XmslDrawReviewList drawReviewList = new XmslDrawReviewList();
            BeanUtils.copyProperties(temp, drawReviewList);
            drawReviewList.setListId(temp.getId());
            drawReviewList.setListCode(temp.getCode());
            new AddBaseInfoUtil<>().addBaseEntity(drawReviewList);
            resuList.add(drawReviewList);
        }
        return resuList;
    }

    private List<XmslDrawReviewWbs> getDefaultWbs(String listCode){
        String[] wbsCodes = WbsRedisUtils.getWbsCodeByListCode(listCode);
        if(ArrayUtils.isEmpty(wbsCodes))
            return new ArrayList<>();
        List<XmslWbs> list = WbsRedisUtils.getWbsByCodes(Arrays.asList(wbsCodes));
        List<XmslDrawReviewWbs> resuList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            XmslWbs temp = list.get(i);
            XmslDrawReviewWbs wbs = new XmslDrawReviewWbs();
            BeanUtils.copyProperties(temp, wbs);
            wbs.setWbsId(Long.valueOf(temp.getId()));
            wbs.setCode(temp.getCode());
            new AddBaseInfoUtil<>().addBaseEntity(wbs);
            resuList.add(wbs);
        }
        return resuList;
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
            relationList = getRelationList (version,listCode,wbsCodes);
        }else{
            relationList = getRelationList (version,listCode,null);
        }
        if(CollectionUtils.isEmpty(relationList)){
            if(ObjectUtils.nvl(version) == 1){  //加载默认wbs
                return getDefaultWbs(listCode);
            }else{                              //加载上一版本
                XmslDrawReview last = this.xmslDrawReviewMapper.getLast(1);
                if(last == null){
                    return getDefaultWbs(listCode);
                }
                mainId = last.getId();
                version = last.getVersion();
                relationList = getRelationList (version,listCode,null);
            }
        }
        Set<String> listCodeSet = relationList.stream().map(r->r.getListCode()).collect(Collectors.toSet());
        //查询清单
        List<XmslDrawReviewList> drawList = drawReviewListService.getByCodes(mainId,listCodeSet);
        List<XmslDrawReviewWbs> list = trans2Wbs(drawList);
        //获取清单对应的细目、配合比
        Set<String> wbsCodeSet = drawList.stream().map(r->r.getWbsCode()).collect(Collectors.toSet());
        Map<String,XmslDrawReviewWbs> listMap = new HashMap<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            XmslDrawReviewWbs temp = list.get(i);
            listMap.put(temp.getCode(),temp);
        }
        //细目&配合比
        List<XmslDrawReviewMaterial> materialList = materialService.getByWbsId(mainId,listCode,wbsCodeSet);
        Map<Long,XmslDrawReviewMaterial> materMap = new HashMap<>(materialList.size());
        for (int i = 0; i < materialList.size(); i++) {
            XmslDrawReviewMaterial temp = materialList.get(i);
            materMap.put(temp.getId(),temp);
            //填充到清单
            XmslDrawReviewWbs tempWbs = listMap.get(temp.getWbsCode());
            if(tempWbs.getMaterialList() == null)
                tempWbs.setMaterialList(new ArrayList<>());
            tempWbs.setMaterialList(ObjectUtils.add2List(tempWbs.getMaterialList(),temp));
        }
        List<XmslDrawReviewSourceMaterial> sourceMaterList = sourceMaterialService.getByMaterId(materMap.keySet());
        //填充数据到父级
        for (int i = 0; i < sourceMaterList.size(); i++) {
            XmslDrawReviewSourceMaterial temp = sourceMaterList.get(i);
            XmslDrawReviewMaterial material = materMap.get(temp.getMaterialId());
            material.setSourceMaterialList(ObjectUtils.add2List(material.getSourceMaterialList(),temp));
        }
        return list;
    }

    private List<XmslDrawReviewRelation> getRelationList(Integer version,String listCode,String[] wbsCodes){
        XmslDrawReviewRelation query = new XmslDrawReviewRelation();
        query.setVersion(version);
        query.setListCode(listCode);
        if(!ArrayUtils.isEmpty(wbsCodes))
            query.setWbsCodeSet(SetUtils.hashSet(wbsCodes));
        List<XmslDrawReviewRelation> relationList = relationService.getXmslDrawReviewRelationList(query);
        return relationList;
    }

    //清单转换为wbs
    public List<XmslDrawReviewWbs> trans2Wbs(List<XmslDrawReviewList> list){
        List<XmslDrawReviewWbs> resuList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            XmslDrawReviewList temp = list.get(i);
            XmslDrawReviewWbs tempWbs = new XmslDrawReviewWbs();
            BeanUtils.copyProperties(temp,tempWbs);
            tempWbs.setId(null);
            tempWbs.setCode(temp.getWbsCode());
            tempWbs.setCheckQuanlity(temp.getCheckNum());
            resuList.add(tempWbs);
        }
        return resuList;
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

    @Override
    public Integer hasChange() {
        Integer count =  this.xmslDrawReviewMapper.getXmslDrawReviewCount(new XmslDrawReview());
        return count!=null&&count>0?1:0;
    }

    @Override
    @Transactional
    public void save(XmslDrawReviewDto dto) {
        saveCheck(dto);
        Integer version = dto.getVersion()==null?1:dto.getVersion();
        boolean isNew = dto.getId()==null;
        if(isNew){
            new AddBaseInfoUtil<>(dto);
            dto.setCreateUserName(SecurityUtils.getSysUser().getNickName());
            dto.setVersion(version);
            dto.setValid(Constant.NO_INT);
            xmslDrawReviewMapper.insertXmslDrawReview(dto);
        }else{
            new AddBaseInfoUtil<>().update(dto);
            dto.setValid(Constant.NO_INT);
            xmslDrawReviewMapper.updateXmslDrawReview(dto);
        }
        //清单或wbs保存
        if(CollectionUtils.isNotEmpty(dto.getWbsList()))
            handlerWbsList(dto,version,isNew);
        else if(CollectionUtils.isNotEmpty(dto.getList()))
            handlerList(dto,version,isNew);
    }

    private void saveCheck(XmslDrawReviewDto dto){
        if(dto.getMainId() == null){
            //为空则判断是否已经有未完成的数据
            XmslDrawReview query = new XmslDrawReview();
            query.setValid(Constant.NO_INT);
            query.setParams(ObjectUtils.toMap("exceptId",dto.getId()));
            Integer count = this.xmslDrawReviewMapper.getXmslDrawReviewCount(query);
            if(count != null && count > 0){
                throw new RuntimeException("已存在未生效的数据，无法再新增新数据");
            }
//            Assert.isTrue(count != null || (count!= null && count < 1),"已存在未生效的数据，无法再新增新数据");
            return;
        }
        XmslDrawReview drawReview = getById(dto.getMainId());
        Assert.notNull(drawReview,"mainId有误，获取主数据失败");
        Assert.isTrue(drawReview.getValid()==Constant.NO_INT,"已生效的数据无法编辑");
    }

    private void handlerWbsList(XmslDrawReviewDto dto,Integer version,boolean isNew){
        List<XmslDrawReviewWbs> wbsList = dto.getWbsList();
        //处理wbs
        List<XmslDrawReviewWbs> addWbsList = new ArrayList<>();
        List<XmslDrawReviewWbs> updateWbsList = new ArrayList<>();
        List<XmslDrawReviewRelation> addRelationList = new ArrayList<>();
        List<XmslDrawReviewList> addList = new ArrayList<>();
        List<XmslDrawReviewMaterial> addMaterList = new ArrayList<>();
        List<XmslDrawReviewSourceMaterial> addSourceMaterList = new ArrayList<>();
        Set<String> wbsIdSet = new HashSet<>();
        for (int i = 0; i < wbsList.size(); i++) {
            XmslDrawReviewWbs temp = wbsList.get(i);
            wbsIdSet.add(temp.getCode());
            temp.setVersion(version);
            temp.setVersionFlag(Constant.YES_INT);
            temp.setMainId(dto.getId());
            if(temp.getId() ==null){
                temp.initAdd();
                addWbsList.add(temp);
            }else{
                new AddBaseInfoUtil().updateBaseEntity(temp);
                updateWbsList.add(temp);
            }
            if(CollectionUtils.isEmpty(temp.getList()))
                continue;
            Set<String> listCodeSet = new HashSet<>();
            BigDecimal sumCheck = BigDecimal.ZERO;
            //清单&挂接清单
            List<XmslDrawReviewList> list = temp.getList();
            for (int j = 0; j < list.size(); j++) {
                XmslDrawReviewList tempList = list.get(j);
                sumCheck = BigDecimalUtils.sum(sumCheck,tempList.getCheckNum());
                Assert.isTrue(!listCodeSet.contains(tempList.getListCode()),"WBS编号["+temp.getCode()+"]下包含了同名清单编号["+tempList.getListCode()+"]");
                tempList.setWbsCode(temp.getCode());
                tempList.setVersion(version);
                tempList.setVersionFlag(Constant.YES_INT);
                tempList.setMainId(dto.getId());
                tempList.setWbsId(temp.getId());
                tempList.setListCode(ObjectUtils.nvlString(tempList.getListCode(),tempList.getPtVar2()));
                tempList.setListId(ObjectUtils.nvlLong(tempList.getListId()));
                tempList.setPtVar1("1");
                tempList.setPtVar3(temp.getName());
                tempList.initAdd();
                addList.add(tempList);
                addRelationList.add(new XmslDrawReviewRelation(dto.getId(),temp.getId(),temp.getCode(),
                        tempList.getListCode(),tempList.getId(),version));
                if(CollectionUtils.isEmpty(tempList.getMaterialList()))
                    continue;
                //图纸材料细目
                List<XmslDrawReviewMaterial> materialList = tempList.getMaterialList();
                for (int k = 0; k < materialList.size(); k++) {
                    XmslDrawReviewMaterial tempMater = materialList.get(k);
                    tempMater.initAdd();
                    tempMater.setMainId(dto.getId());
                    tempMater.setWbsId(temp.getId());
                    tempMater.setWbsCode(temp.getCode());
                    tempMater.setListCode(tempList.getListCode());
                    tempMater.setListId(tempList.getId());
                    addMaterList.add(tempMater);
                    if(CollectionUtils.isEmpty(tempMater.getSourceMaterialList()))
                        continue;
                    //原材料
                    List<XmslDrawReviewSourceMaterial> sourceMaterialList = tempMater.getSourceMaterialList();
                    for (int l = 0; l < sourceMaterialList.size(); l++) {
                        XmslDrawReviewSourceMaterial tempSource = sourceMaterialList.get(l);
                        tempSource.initAdd();
                        tempSource.setMainId(dto.getId());
                        tempSource.setWbsId(temp.getId());
                        tempSource.setListId(tempList.getId());
                        tempSource.setListCode(tempList.getListCode());
                        tempSource.setMaterialId(tempMater.getId());
                        addSourceMaterList.add(tempSource);
                    }
                }
            }
            temp.setCheckQuanlity(sumCheck);
        }
        if(!isNew && CollectionUtils.isNotEmpty(wbsIdSet)){
            //删除wbs、挂接、清单、细目、配合比
            Map delMap = ObjectUtils.toMap("mainId",dto.getId(),"wbsIds",wbsIdSet);
            xmslDrawReviewMapper.deleteRelation(delMap);
            xmslDrawReviewMapper.deleteWbsByCode(delMap);
            xmslDrawReviewMapper.deleteList(delMap);
            xmslDrawReviewMapper.deleteMaterial(delMap);
            xmslDrawReviewMapper.deleteSourceMaterial(delMap);
        }
        drawReviewWbsService.insertXmslDrawReviewWbsList(addWbsList);
        drawReviewWbsService.updateXmslDrawReviewWbsList(updateWbsList);
        relationService.insertXmslDrawReviewRelationList(addRelationList);
        drawReviewListService.insertXmslDrawReviewListList(addList);
        materialService.insertXmslDrawReviewMaterialList(addMaterList);
        sourceMaterialService.insertXmslDrawReviewSourceMaterialList(addSourceMaterList);
    }

    private void handlerList(XmslDrawReviewDto dto,Integer version,boolean isNew){
        List<XmslDrawReviewList> list = dto.getList();
        List<XmslDrawReviewRelation> addRelationList = new ArrayList<>();
        List<XmslDrawReviewList> addList = new ArrayList<>();
        List<XmslDrawReviewMaterial> addMaterList = new ArrayList<>();
        List<XmslDrawReviewSourceMaterial> addSourceMaterList = new ArrayList<>();
        List<XmslDrawReviewWbs> addWbsList = new ArrayList<>();
        //处理清单
        Set<String> listCodeSet = new HashSet<>();
        Set<String> wbsCodeSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            XmslDrawReviewList tempList = list.get(i);
            if(tempList == null) //
                continue;
            listCodeSet.add(tempList.getListCode());
            tempList.setId(IdWorker.createId());
            tempList.setVersion(version);
            tempList.setVersionFlag(Constant.YES_INT);
            tempList.setMainId(dto.getId());
            //清单&挂接清单
            if(CollectionUtils.isEmpty(tempList.getWbsList()))
                continue;
            List<XmslDrawReviewWbs> wbsList = tempList.getWbsList();
            for (int j = 0; j < wbsList.size(); j++) {
                XmslDrawReviewWbs temp = wbsList.get(j);
                if(!wbsCodeSet.contains(temp.getCode())){
                    temp.setId(IdWorker.createId());
                    addWbsList.add(temp);
                }
                wbsCodeSet.add(temp.getCode());
                XmslDrawReviewList newList = new XmslDrawReviewList();
                tempList.setWbsId(temp.getId());
                BeanUtils.copyProperties(tempList,newList);
                newList.setCheckNum(temp.getCheckQuanlity());
                newList.initAdd();
                newList.setWbsCode(temp.getCode());
                newList.setPtVar1("1");
                newList.setPtVar3(temp.getName()); //wbs名称
                addList.add(newList);
                addRelationList.add(new XmslDrawReviewRelation(dto.getId(),temp.getId(),temp.getCode(),
                        tempList.getListCode(),tempList.getId(),version));
                //图纸材料细目
                List<XmslDrawReviewMaterial> materialList = temp.getMaterialList();
                if(CollectionUtils.isEmpty(materialList))
                    continue;
                for (int k = 0; k < materialList.size(); k++) {
                    XmslDrawReviewMaterial tempMater = materialList.get(k);
                    tempMater.initAdd();
                    tempMater.setMainId(dto.getId());
                    tempMater.setWbsId(temp.getId());
                    tempMater.setWbsCode(temp.getCode());
                    tempMater.setListCode(tempList.getListCode());
                    tempMater.setListId(tempList.getId());
                    addMaterList.add(tempMater);
                    //原材料
                    List<XmslDrawReviewSourceMaterial> sourceMaterialList = tempMater.getSourceMaterialList();
                    if(CollectionUtils.isEmpty(sourceMaterialList))
                        continue;
                    for (int l = 0; l < sourceMaterialList.size(); l++) {
                        XmslDrawReviewSourceMaterial tempSource = sourceMaterialList.get(l);
                        tempSource.initAdd();
                        tempSource.setMainId(dto.getId());
                        tempSource.setWbsId(temp.getId());
                        tempSource.setListId(tempList.getId());
                        tempSource.setListCode(tempList.getListCode());
                        tempSource.setMaterialId(tempMater.getId());
                        addSourceMaterList.add(tempSource);
                    }
                }
            }
        }
        if(!isNew && CollectionUtils.isNotEmpty(listCodeSet)){
            //删除挂接、清单、细目、配合比
            Map delMap = ObjectUtils.toMap("mainId",dto.getId(),"listIds",listCodeSet);
            xmslDrawReviewMapper.deleteRelation(delMap);
            xmslDrawReviewMapper.deleteList(delMap);
            xmslDrawReviewMapper.deleteMaterial(delMap);
            xmslDrawReviewMapper.deleteSourceMaterial(delMap);
            if(CollectionUtils.isNotEmpty(wbsCodeSet)){
                xmslDrawReviewMapper.deleteWbsByCode(ObjectUtils.toMap("mainId",dto.getId(),"wbsIds",wbsCodeSet));
            }
        }
        drawReviewWbsService.insertXmslDrawReviewWbsList(addWbsList);
        relationService.insertXmslDrawReviewRelationList(addRelationList);
        drawReviewListService.insertXmslDrawReviewListList(addList);
        materialService.insertXmslDrawReviewMaterialList(addMaterList);
        sourceMaterialService.insertXmslDrawReviewSourceMaterialList(addSourceMaterList);
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
    public void deleteXmslDrawReview(XmslDrawReview xmslDrawReview) {
        XmslDrawReview dbDrawReview = this.getById(xmslDrawReview.getId());
        Assert.notNull(dbDrawReview,"未找到要删除的数据，可能该数据已被删除");
        Assert.isTrue(dbDrawReview.getValid()!=Constant.YES_INT,"已生效数据无法删除");
//        xmslDrawReview.setUpdateUser(SecurityUtils.getUserName());
//        xmslDrawReview.setUpdateTime(DateUtils.getNowDate());
        xmslDrawReviewMapper.deleteXmslDrawReview(xmslDrawReview);
        //删除子表
        Map map= ObjectUtils.toMap("mainId",xmslDrawReview.getId());
        this.xmslDrawReviewMapper.deleteRelation(map);
        this.xmslDrawReviewMapper.deleteWbs(map);
        this.xmslDrawReviewMapper.deleteList(map);
        this.xmslDrawReviewMapper.deleteMaterial(map);
        this.xmslDrawReviewMapper.deleteSourceMaterial(map);
    }

    @Override
    @Transactional
    public void finishFlow(Long id) {
        //1、修改valid > 1
        XmslDrawReview drawReview = this.getById(id);
        Assert.notNull(drawReview,"获取图纸复核失败");
        drawReview.setValid(Constant.YES_INT);
        this.xmslDrawReviewMapper.updateXmslDrawReview(drawReview);
        //2、存储wbs以及清单的父级
//        loadParentWbsList(id);
        String tenantKey = SecurityUtils.getTenantKey();
        //3、生成工程量报表 & 主材报表
//        ThreadPoolUtil.execute(()->{
//            //切换租户
//            String oldDataSource = DynamicDataSourceContextHolder.peek();
//            DynamicDataSourceContextHolder.push(TenantDataSourceUtils.getDataSourceNameByTenantKey(tenantKey));
//            try {
                engineeringReportService.sync(tenantKey);
                materialReportService.sync(id,tenantKey);
//            }catch (Exception e){
//                e.printStackTrace();
//                throw new CustomBusinessException(e.getMessage());
//            }finally {
//                DynamicDataSourceContextHolder.poll();
//                DynamicDataSourceContextHolder.push(oldDataSource);
//            }
//        });
    }

    //加载图纸复核、
    private void loadParentWbsList(Long id){
        List<XmslDrawReviewWbs> addWbsList = new ArrayList<>();
        List<XmslDrawReviewList> addList = new ArrayList<>();
        Map<Long,Long> wbsIdMap = new HashMap<>();
        Map<Long,Long> listIdMap = new HashMap<>();
        //1、获取当前赋值复核下所有的wbs的祖级id以及父级id
        XmslDrawReview drawReview = this.getById(id);
        Assert.notNull(drawReview, "图纸复核信息获取失败");
        List<XmslDrawReviewWbs> wbsList = this.xmslDrawReviewMapper.selectWbsAncestor(drawReview.getVersion());
        List<XmslDrawReviewList> list = this.xmslDrawReviewMapper.selectListAncestor(drawReview.getVersion());
        List<String> wbsAncesList = new ArrayList<>(wbsList.size());
        List<String> listAncesList = new ArrayList<>(list.size());
        for (int i = 0; i < wbsList.size(); i++) {
            XmslDrawReviewWbs tempWbs = wbsList.get(i);
            wbsAncesList.add(tempWbs.getAncestors());
            wbsIdMap.put(tempWbs.getWbsId(), tempWbs.getId());
        }
        for (int i = 0; i < list.size(); i++) {
            XmslDrawReviewList tempList = list.get(i);
            listAncesList.add(tempList.getAncestors());
            listIdMap.put(tempList.getListId(), tempList.getId());
        }
        Set<String> wbsIdSet = new HashSet<>();
        Set<Long> listIdSet = new HashSet<>();
        //去重祖级id
        ancestorToList(wbsAncesList, wbsIdSet,false);
        ancestorToList(listAncesList, listIdSet,true);
        //2、获取wbs并转换为图纸复核wbs
        List<XmslWbs> pwbsList = WbsRedisUtils.getWbs(wbsIdSet);
        for (int i = 0; i < pwbsList.size(); i++) {
            XmslWbs temp = pwbsList.get(i);
            XmslDrawReviewWbs drawWbs = copyToWbs(temp);
            drawWbs.setMainId(drawReview.getId());
            drawWbs.setVersionFlag(Constant.YES_INT);
            drawWbs.setVersion(drawReview.getVersion());
            wbsIdMap.put(drawWbs.getWbsId(), drawWbs.getId());
            addWbsList.add(drawWbs);
        }
        //分页取清单
        List<Long> listIdList = new ArrayList<>(listIdSet);
        PageFuncUtils.exec(listIdList.size(),1000,(start,end)->{
            List<Long> tempList = listIdList.subList(start, end);
            List<XmslContractList> contractLists = contractListService.getByIds(tempList.toArray(new Long[]{}));
            for (int i = 0; i < contractLists.size(); i++) {
                XmslDrawReviewList temp = copyToList(contractLists.get(i));
                temp.setMainId(drawReview.getId());
                temp.setVersionFlag(Constant.YES_INT);
                temp.setVersion(drawReview.getVersion());
                addList.add(temp);
                listIdMap.put(temp.getListId(), temp.getId());
            }
            return true;
        });
        //保存wbs以及清单
        BiFunction<List<XmslDrawReviewWbs>,Map<Long,Long>,Integer> setWbsPidFunc = (l,map)->{
            for (int i = 0; i < l.size(); i++) {
                XmslDrawReviewWbs temp = l.get(i);
                temp.setParentId(ObjectUtils.nvlLong(map.get(temp.getParentId()),-1L));
                temp.setAncestors(ObjectUtils.replaceWithLongMap(temp.getAncestors(),map));
            }
            return 0;
        };
        BiFunction<List<XmslDrawReviewList>,Map<Long,Long>,Integer> setListPidFunc = (l,map)->{
            for (int i = 0; i < l.size(); i++) {
                XmslDrawReviewList temp = l.get(i);
                temp.setPid(ObjectUtils.nvlLong(map.get(temp.getPid()),-1L));
                temp.setAncestors(ObjectUtils.replaceWithLongMap(temp.getAncestors(),map));
                temp.setPtVar1("0");
            }
            return 0;
        };
        setWbsPidFunc.apply(addWbsList, wbsIdMap);
        setListPidFunc.apply(addList, listIdMap);
        drawReviewWbsService.insertXmslDrawReviewWbsList(addWbsList);
        drawReviewListService.insertXmslDrawReviewListList(addList);
        //4、修改图纸复核wbs、清单对应的父级id
        setWbsPidFunc.apply(wbsList, wbsIdMap);
        setListPidFunc.apply(list, listIdMap);
        drawReviewWbsService.updateParentId(wbsList);
        drawReviewListService.updateParentId(list);
    }
    private void ancestorToList(List<String> list,Set idSet,boolean isLong){
        for (int i = 0; i < list.size(); i++) {
            String temp = list.get(i);
            if(StringUtils.isBlank(temp))
                continue;
            String[] ances = temp.split(",");
            for (int j = 0; j < ances.length-1; j++) {
                idSet.add(isLong?Long.valueOf(ances[j]):ances[j]);
            }
        }
    }


    public XmslDrawReviewWbs copyToWbs(XmslWbs temp){
        XmslDrawReviewWbs drawWbs = new XmslDrawReviewWbs();
        drawWbs.setId(IdWorker.createId());
        drawWbs.setWbsId(Long.valueOf(temp.getId()));
        drawWbs.setMainId(temp.getMainId());
        drawWbs.setCode(temp.getCode());
        drawWbs.setParentId(Long.valueOf(temp.getParentId()));
        drawWbs.setHaveChildren(temp.getHaveChildren());
        drawWbs.setAncestors(temp.getAncestors());
        drawWbs.setAncestorsName(temp.getAncestorsName());
        drawWbs.setPartCode(temp.getPartCode());
        drawWbs.setName(temp.getName());
//        drawWbs.setListCode(temp.getListCode());
//        drawWbs.setStandardId(temp.getStandardId());
//        drawWbs.setStandardCode(temp.getStandardCode());
//        drawWbs.setStandardName(temp.getStandardName());
        drawWbs.setNodeType(temp.getNodeType());
        drawWbs.setUnit(temp.getUnit());
        drawWbs.setLevel(temp.getLevel());
        drawWbs.setStatus(temp.getStatus());
        drawWbs.setDesignQuanlity(temp.getDesignQuanlity());
        drawWbs.setCheckQuanlity(temp.getCheckQuanlity());
        drawWbs.setCreateUser(temp.getCreateUser());
        drawWbs.setCreateUserName(temp.getCreateUserName());
        drawWbs.setCreateTime(temp.getCreateTime());
        drawWbs.setUpdateUser(temp.getUpdateUser());
        drawWbs.setUpdateTime(temp.getUpdateTime());
        drawWbs.setDelFlag("0");
        return drawWbs;
    }

    public XmslDrawReviewList copyToList(XmslContractList temp){
        XmslDrawReviewList drawList = new XmslDrawReviewList();
        drawList.setId(IdWorker.createId());
        drawList.setListId(temp.getId());
        drawList.setListCode(temp.getCode());
        drawList.setPid(temp.getPid());
        drawList.setAncestors(temp.getAncestors());
        drawList.setChineseName(temp.getChineseName());
        drawList.setForeignName(temp.getForeignName());
        drawList.setListType(temp.getListType());
        drawList.setUnitCode(temp.getUnitCode());
        drawList.setUnit(temp.getUnit());
        drawList.setWinNum(temp.getWinNum());
        drawList.setRemark(temp.getRemark());
//        drawList.setCreateUser(temp.getCreateUser());
//        drawList.setCreateUserName(temp.getCreateUserName());
//        drawList.setCreateTime(temp.getCreateTime());
//        drawList.setUpdateUser(temp.getUpdateUser());
//        drawList.setUpdateTime(temp.getUpdateTime());
        drawList.setDelFlag("0");
        return drawList;
    }
}
