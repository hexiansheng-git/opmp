package com.hhwy.pm.qqch.preparation.technique.disclose.service.impl;

import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.constant.ButtonMark;
import com.hhwy.pm.qqch.module.contant.Valid;
import com.hhwy.pm.qqch.module.service.IQqchModuleConfirmCaseService;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThird;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.QqchDiscloseThirdDetail;
import com.hhwy.pm.qqch.preparation.technique.disclose.domain.vo.QqchDiscloseThirdVo;
import com.hhwy.pm.qqch.preparation.technique.disclose.mapper.QqchDiscloseThirdDetailMapper;
import com.hhwy.pm.qqch.preparation.technique.disclose.mapper.QqchDiscloseThirdMapper;
import com.hhwy.pm.qqch.preparation.technique.disclose.service.IQqchDiscloseThirdService;
import com.hhwy.pm.qqch.review.service.IQqchReviewService;
import com.hhwy.pm.qqch.utils.VersionUtil;
import com.hhwy.utils.AddBaseInfoUtil;
import com.hhwy.utils.ObjectUtils;
import com.hhwy.utils.idworker.IdWorker;
import com.hhwy.utils.tree.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhenglili
 * @date 2023-07-21 14:26:42
 * @remark 3.5.2三级交底
 */
@Service
public class QqchDiscloseThirdServiceImpl implements IQqchDiscloseThirdService {

    @Autowired
    private QqchDiscloseThirdMapper qqchDiscloseThirdMapper;
    @Autowired
    private QqchDiscloseThirdDetailMapper qqchDiscloseThirdDetailMapper;
    @Autowired
    private IQqchModuleConfirmCaseService qqchModuleConfirmCaseService;
    @Autowired
    private IQqchReviewService qqchReviewService;

    public QqchDiscloseThirdVo getQqchDiscloseThirdList(BigDecimal version) {
        QqchDiscloseThirdVo vo = new QqchDiscloseThirdVo();
        version = VersionUtil.getVersion("qqch_disclose_third", version);
        vo.setVersion(version);

        QqchDiscloseThird qryParam = new QqchDiscloseThird();
        qryParam.setVersion(version);
        List<QqchDiscloseThird> list = qqchDiscloseThirdMapper.getQqchDiscloseThirdList(qryParam);

        // 全部详情
//        QqchDiscloseThirdDetail qryParamDetail = new QqchDiscloseThirdDetail();
//        qryParamDetail.setVersion(version);
//        List<QqchDiscloseThirdDetail> deTailList = qqchDiscloseThirdDetailMapper.getQqchDiscloseThirdDetailList(qryParamDetail);
//
//        if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(deTailList)) {
//            for (QqchDiscloseThird qqchDiscloseThird : list) {
//                List<QqchDiscloseThirdDetail> detailListChild = new ArrayList<>();
//                for (QqchDiscloseThirdDetail deTail : deTailList) {
//                    if (qqchDiscloseThird.getId().equals(deTail.getMasterId())) {
//                        detailListChild.add(deTail);
//                    }
//                }
//                qqchDiscloseThird.setDetailTreeList(TreeUtil.build(detailListChild, null));
//            }
//        }

        vo.setStageIdentity(qqchReviewService.getStage());
        vo.setTreeList(TreeUtil.build(list, -1L));
//        vo.setAllDetailTreeList(TreeUtil.build(deTailList, null));
        return vo;
    }

    @Override
    public List<QqchDiscloseThirdDetail> getDetailList(Long masterId) {
        if(masterId == null || masterId < 0L)
            return new ArrayList<>(2);
        //获取子级交底人数据
        QqchDiscloseThird thirdQuery = new QqchDiscloseThird();
        thirdQuery.setPid(masterId);
        List<QqchDiscloseThird> thirdList = this.qqchDiscloseThirdMapper.getQqchDiscloseThirdList(thirdQuery);
        List<Long> idList = thirdList.stream().map(r->r.getId()).collect(Collectors.toList());
        idList.add(masterId);
        QqchDiscloseThirdDetail query = new QqchDiscloseThirdDetail();
        query.setParams(ObjectUtils.toMap("masterIds", idList));
        List<QqchDiscloseThirdDetail> list = qqchDiscloseThirdDetailMapper.getQqchDiscloseThirdDetailList(query);
        //根据wbs编号去重
        List<QqchDiscloseThirdDetail> finalList = filtersRepeatWbs(list);
        //转树形
        return TreeUtil.build(finalList,-1L);
    }
    
    private List<QqchDiscloseThirdDetail> filtersRepeatWbs(List<QqchDiscloseThirdDetail> list){
        //根据wbs编号去重
        list.sort((v1,v2)->v1.getId()>v2.getId()?-1:1);
        //不同的班组可能会选择同一个wbs。需要去重,并处理ID
        Map<String,List<Long>> wbsCodeParentMap = new HashMap<>();
        List<QqchDiscloseThirdDetail> finalList = new ArrayList<>();
        Set<Long> finalIdSet = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            QqchDiscloseThirdDetail temp = list.get(i);
            boolean containWbs = wbsCodeParentMap.containsKey(temp.getWbsCode());
            ObjectUtils.add2MapList(wbsCodeParentMap, temp.getWbsCode(), temp.getPid());
            if(!containWbs){ //如果WBS编号不存在，放入结果list
                finalList.add(temp);
                finalIdSet.add(temp.getId());
            }
        }
        //处理父级ID
        for (int i = 0; i < finalList.size(); i++) {
            QqchDiscloseThirdDetail temp = finalList.get(i);
            List<Long> pidList = wbsCodeParentMap.get(temp.getWbsCode());
            for (int j = 0; j < pidList.size(); j++) {
                if(finalIdSet.contains(pidList.get(j)))
                    temp.setPid(pidList.get(j));
            }
        }
        return finalList;
    }

    @Transactional
    @Deprecated
    public void batchSave(QqchDiscloseThirdVo qqchDiscloseThirdVo) {
        // 三级交底主表数据
        List<QqchDiscloseThird> treeList = qqchDiscloseThirdVo.getTreeList();

        if (!CollectionUtils.isEmpty(treeList)) {
            List<Long> ids = treeList.stream().map(QqchDiscloseThird::getId).collect(Collectors.toList());
            // 批量删除子表数据
            qqchDiscloseThirdDetailMapper.deleteQqchDiscloseThirdDetailByPks(ids);
        }

        // 批量删除主表数据
        QqchDiscloseThird deleteParam = new QqchDiscloseThird();
        deleteParam.setVersion(qqchDiscloseThirdVo.getVersion());
        qqchDiscloseThirdMapper.deleteQqchDiscloseThird(deleteParam);

        if (!CollectionUtils.isEmpty(treeList)) {
            // 新主表集合
            List<QqchDiscloseThird> newMainList = TreeUtil.treeToList(treeList);
            for (QqchDiscloseThird newMain : newMainList) {
                newMain.setVersion(qqchDiscloseThirdVo.getVersion());
                if (qqchDiscloseThirdVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                    newMain.setValid(Valid.YES);
                }
                newMain.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                newMain.setCreateUserName(SecurityUtils.getUserName());
                newMain.setCreateTime(DateUtils.getNowDate());
            }

            // 新子列表集合
            List<QqchDiscloseThirdDetail> newDetailList = new ArrayList<>();
            for (QqchDiscloseThird qqchDiscloseThird : newMainList) {
                List<QqchDiscloseThirdDetail> detailList = TreeUtil.treeToList(qqchDiscloseThird.getDetailTreeList());
                if (!CollectionUtils.isEmpty(detailList)) {
                    for (QqchDiscloseThirdDetail detail : detailList) {
                        detail.setMasterId(qqchDiscloseThird.getId());
                        detail.setVersion(qqchDiscloseThirdVo.getVersion());
                        if (qqchDiscloseThirdVo.getVersion().compareTo(BigDecimal.ONE) == 0) {
                            detail.setValid(Valid.YES);
                        }
                        detail.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                        detail.setCreateUserName(SecurityUtils.getUserName());
                        detail.setCreateTime(DateUtils.getNowDate());
                        newDetailList.add(detail);
                    }
                }
            }

            // 主表全量入库
            qqchDiscloseThirdMapper.insertQqchDiscloseThirdList(newMainList);

            if (!CollectionUtils.isEmpty(newDetailList)) {
                // 子全量入库
                qqchDiscloseThirdDetailMapper.insertQqchDiscloseThirdDetailList(newDetailList);
            }
        }

        String buttonMark = qqchDiscloseThirdVo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = qqchDiscloseThirdVo.getMenuId();
            String stageIdentity = qqchDiscloseThirdVo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Override
    @Transactional
    public void saveData(QqchDiscloseThirdVo vo){
        this.save(vo);
        String buttonMark = vo.getButtonMark();
        if (ButtonMark.CONFIRM.equals(buttonMark)) {
            // 插入确认状态
            String menuId = vo.getMenuId();
            String stageIdentity = vo.getStageIdentity();
            qqchModuleConfirmCaseService.addConfirmRecord(menuId, stageIdentity);
        }
    }

    @Override
    @Transactional
    public void save(QqchDiscloseThirdVo vo) {
        if(CollectionUtils.isEmpty(vo.getTreeList()) && StringUtils.isBlank(vo.getDelIds()))
            return ;
        //新增交底明细
        List<QqchDiscloseThirdDetail> detailAddList = new ArrayList<>();
        //新增交底集合
        List<QqchDiscloseThird> addList = new ArrayList<>();
        //修改交底集合
        List<QqchDiscloseThird> updateList = new ArrayList<>();
        Set<Long> delIdSet = new HashSet<>();
        saveThird(vo.getTreeList(),vo.getVersion(),-1L,detailAddList,addList,updateList,delIdSet);
        if(!CollectionUtils.isEmpty(addList))
            this.qqchDiscloseThirdMapper.insertQqchDiscloseThirdList(addList);
        if(!CollectionUtils.isEmpty(updateList))
            this.qqchDiscloseThirdMapper.updateQqchDiscloseThirdList(updateList);
        if(!CollectionUtils.isEmpty(delIdSet))
            qqchDiscloseThirdMapper.deleteDetailByMasterIds(delIdSet);
        if(!CollectionUtils.isEmpty(detailAddList))
            qqchDiscloseThirdDetailMapper.insertQqchDiscloseThirdDetailList(detailAddList);
        //删除交底以及明细
        if(StringUtils.isBlank(vo.getDelIds()))
            return;
        Long[] delIds = Convert.toLongArray(vo.getDelIds());
        List<Long> discloseIdList = qqchDiscloseThirdMapper.getChildIdsByPids(delIds);
        discloseIdList.addAll(Arrays.asList(delIds));
        if(!CollectionUtils.isEmpty(discloseIdList)){
            this.qqchDiscloseThirdMapper.deleteQqchDiscloseThirdByPks(discloseIdList);
            this.qqchDiscloseThirdMapper.deleteDetailByMasterIds(new HashSet<>(discloseIdList));    
        }
    }

    /**
     * 保存交底人信息
     * 第一级的人员，不处理其明细
     * @param list          交底人信息
     * @param version        版本
     * @param pid            父级ID
     * @param detailAddList   需要保存的交底明细数据
     * @param addList         需要新增的交底
     * @param updateList      需要修改的交底
     * @param delIdSet      需要修改的交底                       
     */
    public void saveThird(List<QqchDiscloseThird> list,BigDecimal version,Long pid,List<QqchDiscloseThirdDetail> detailAddList,List<QqchDiscloseThird> addList,List<QqchDiscloseThird> updateList,Set<Long> delIdSet){
        if(CollectionUtils.isEmpty(list))
            return;
        for (int i = 0; i < list.size(); i++) {
            QqchDiscloseThird temp = list.get(i);
            temp.setPid(pid);
            temp.setVersion(version);
            if(temp.getId()== null){
                new AddBaseInfoUtil<>().addBaseEntity(temp);
                temp.setId(IdWorker.createId());
                addList.add(temp);
            }else{
                new AddBaseInfoUtil<>().updateBaseEntity(temp);
                updateList.add(temp);
            }
            //递归子级
            saveThird(temp.getChildren(),version,temp.getId(),detailAddList,addList,updateList,delIdSet);
            //最上级节点为用户，用户不绑定wbs，跳过
            if(temp.getPid() == null || temp.getPid() < 0L)
                continue;
            //处理挂接的wbs
            if(temp.getDetailTreeList() == null)
                continue;
            delIdSet.add(temp.getId());
            //删除交底明细，并获取需要新增的交底明细
            saveThirdDetail(temp.getDetailTreeList(),-1L,temp.getId(),detailAddList);
        }
    }

    /**
     * 保存交底明细信息  
     * 
     * @param list      交底明细集合
     * @param pid       父级ID
     * @param masterId  对应交底ID
     * @param addList   需要保存的交底明细数据
     */
    public void saveThirdDetail(List<QqchDiscloseThirdDetail> list,Long pid,Long masterId,List<QqchDiscloseThirdDetail> addList){
        if(CollectionUtils.isEmpty(list))
            return;
        for (int i = 0; i < list.size(); i++) {
            QqchDiscloseThirdDetail temp = list.get(i);
            temp.setId(IdWorker.createId());
            temp.setPid(pid);
            new AddBaseInfoUtil<>().addBaseEntity(temp);
            temp.setMasterId(masterId);
            addList.add(temp);
            //递归子级
            saveThirdDetail(temp.getChildren(),temp.getId(),masterId,addList);
        }
    }
}

