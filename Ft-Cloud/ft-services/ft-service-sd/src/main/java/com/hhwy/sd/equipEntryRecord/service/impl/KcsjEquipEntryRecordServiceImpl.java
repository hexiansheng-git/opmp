package com.hhwy.sd.equipEntryRecord.service.impl;

import cn.hutool.core.date.DateTime;
import com.hhwy.common.core.exception.BaseException;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.excel.Util;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordInfo;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordVo;
import com.hhwy.sd.equipEntryRecord.mapper.KcsjEquipEntryRecordInfoMapper;
import com.hhwy.utils.Constant;
import com.hhwy.utils.tree.TreeNode;
import com.hhwy.utils.tree.TreeUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sd.equipEntryRecord.mapper.KcsjEquipEntryRecordMapper;
import com.hhwy.sd.equipEntryRecord.service.IKcsjEquipEntryRecordService;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecord;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

/**
 * @author zmh
 * @date 2023-12-14 11:08:08
 * @remark
 */
@Service
public class KcsjEquipEntryRecordServiceImpl implements IKcsjEquipEntryRecordService {

    @Autowired
    private KcsjEquipEntryRecordMapper kcsjEquipEntryRecordMapper;
    @Autowired
    private KcsjEquipEntryRecordInfoMapper kcsjEquipEntryRecordInfoMapper;
    @Autowired
    private KcsjEquipEntryRecordInfoServiceImpl kcsjEquipEntryRecordInfoService;
    @Autowired
    private PmServiceApi pmServiceApi;

//    public KcsjEquipEntryRecord getKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
//        return kcsjEquipEntryRecordMapper.getKcsjEquipEntryRecord(kcsjEquipEntryRecord);
//    }

    public KcsjEquipEntryRecordVo getKcsjEquipEntryRecordList(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo = new KcsjEquipEntryRecordVo();
        List<KcsjEquipEntryRecord> kcsjEquipEntryRecordList = kcsjEquipEntryRecordMapper.getKcsjEquipEntryRecordList(kcsjEquipEntryRecord);
        List<KcsjEquipEntryRecord> kcsjEquipEntryRecords = TreeUtil.newBuild(kcsjEquipEntryRecordList);
        if(!CollectionUtils.isEmpty(kcsjEquipEntryRecords)){
            for (int i = 0; i < kcsjEquipEntryRecords.size(); i++) {
                if(!CollectionUtils.isEmpty(kcsjEquipEntryRecords.get(i).getChildren())){
                    List<KcsjEquipEntryRecord> children = kcsjEquipEntryRecords.get(i).getChildren();
                    for (int j = 0; j < children.size(); j++) {
                        KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo = new KcsjEquipEntryRecordInfo();
                        kcsjEquipEntryRecordInfo.setTeamName(kcsjEquipEntryRecords.get(i).getTeamName());
                        kcsjEquipEntryRecordInfo.setEquipCode(children.get(j).getEquipCode());
                        List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList = kcsjEquipEntryRecordInfoService.selectInfos(kcsjEquipEntryRecordInfo);
                        if(!CollectionUtils.isEmpty(kcsjEquipEntryRecordInfoList)){
                            children.get(j).setKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfoList);
                        }else{
                            children.get(j).setKcsjEquipEntryRecordInfoList(new ArrayList<>());
                        }
                    }
                }
            }
        }
        kcsjEquipEntryRecordVo.setTreeList(kcsjEquipEntryRecords);
        kcsjEquipEntryRecordVo.setDelIdList(new ArrayList<>());
        return kcsjEquipEntryRecordVo;
    }

    @Transactional
    public int insertKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        kcsjEquipEntryRecord.setId(IdWorker.createId());
        kcsjEquipEntryRecord.setCreateUser(SecurityUtils.getUserName());
        kcsjEquipEntryRecord.setCreateTime(DateUtils.getNowDate());
        return kcsjEquipEntryRecordMapper.insertKcsjEquipEntryRecord(kcsjEquipEntryRecord);
    }

    @Transactional
    public AjaxResult insertKcsjEquipEntryRecordList(KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo) {
        //批量删除
        if(!CollectionUtils.isEmpty(kcsjEquipEntryRecordVo.getDelIdList())){
            deleteByIds(kcsjEquipEntryRecordVo.getDelIdList());
        }

        List<KcsjEquipEntryRecord> treeToList = null;
        if (!CollectionUtils.isEmpty(kcsjEquipEntryRecordVo.getTreeList())) {
            //批量删除实验设备数据
            deleteByIdInfo();
            //设备数据处理
            inFoAdd(kcsjEquipEntryRecordVo.getTreeList());
            //数据处理
            treeToList = TreeUtil.treeToListWithoutId(kcsjEquipEntryRecordVo.getTreeList());
            for (int i = 0; i < treeToList.size(); i++) {
                KcsjEquipEntryRecord kcsjEquipEntryRecord = treeToList.get(i);
                kcsjEquipEntryRecord.setCreateTime(DateTime.now());
                kcsjEquipEntryRecord.setCreateUser(SecurityUtils.getUserId() + "");
                kcsjEquipEntryRecord.setCreateUserName(SecurityUtils.getUserName() + "");
                kcsjEquipEntryRecord.setUpdateTime(DateTime.now());
                kcsjEquipEntryRecord.setUpdateUser(SecurityUtils.getUserId() + "");
                kcsjEquipEntryRecord.setDelFlag("0");
            }
            List<KcsjEquipEntryRecord> insertList = treeToList.stream().filter(e -> StringUtils.isNotEmpty(e.getIsAdd()) && e.getIsAdd().equals("1")).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(insertList)){
                //批量入库
                insertList.forEach(i->{
                    KcsjEquipEntryRecord kcsjEquipEntryRecord = new KcsjEquipEntryRecord();
                    kcsjEquipEntryRecord.setTeamName(i.getTeamName());
                    kcsjEquipEntryRecord.setEquipCode(i.getEquipCode());
                    List<KcsjEquipEntryRecord> kcsjEquipEntryRecordList = kcsjEquipEntryRecordMapper.getKcsjEquipEntryRecordList(kcsjEquipEntryRecord);
                    if(kcsjEquipEntryRecordList.size()>0){
                        throw new RuntimeException("同一班组下的设备编码不可重复！");
                    }
                });

                kcsjEquipEntryRecordMapper.insertKcsjEquipEntryRecordList(insertList);
            }
            //批量编辑
            List<KcsjEquipEntryRecord> updateList = treeToList.stream().filter(e -> StringUtils.isEmpty(e.getIsAdd())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(updateList)){
                kcsjEquipEntryRecordMapper.updateKcsjEquipEntryRecordList(updateList);
            }
        }
        return AjaxResult.success();
    }

    /**
     * 设备数据处理
     * @param list
     */
    private void inFoAdd(List<KcsjEquipEntryRecord> list){
        for (int i = 0; i < list.size(); i++) {
            if(!CollectionUtils.isEmpty(list.get(i).getChildren())){
               List<KcsjEquipEntryRecord> children = list.get(i).getChildren();
                KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo = new KcsjEquipEntryRecordInfo();
                kcsjEquipEntryRecordInfo.setTeamName(list.get(i).getTeamName());
                kcsjEquipEntryRecordInfo.setId(IdWorker.createId());
                kcsjEquipEntryRecordInfo.setCreateTime(DateTime.now());
                kcsjEquipEntryRecordInfo.setCreateUser(SecurityUtils.getUserId() + "");
                kcsjEquipEntryRecordInfo.setCreateUserName(SecurityUtils.getUserName() + "");
                kcsjEquipEntryRecordInfoMapper.insertKcsjEquipEntryRecordInfo(kcsjEquipEntryRecordInfo);
                for (int j = 0; j < children.size(); j++) {
                    if(!CollectionUtils.isEmpty(children.get(j).getKcsjEquipEntryRecordInfoList())){
                        List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList = children.get(j).getKcsjEquipEntryRecordInfoList();
                        for (int k = 0; k < kcsjEquipEntryRecordInfoList.size(); k++) {
                            //校验
                            KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo1 = new KcsjEquipEntryRecordInfo();
                            kcsjEquipEntryRecordInfo1.setTeamName(list.get(i).getTeamName());
                            kcsjEquipEntryRecordInfo1.setEquipCode(children.get(j).getEquipCode());
//                            List<KcsjEquipEntryRecordInfo> list1 = kcsjEquipEntryRecordInfoMapper.selectInfos(kcsjEquipEntryRecordInfo1);
//                            if(list1.size()>0){
//                                throw new RuntimeException("同一班组下的设备编码不可重复！");
//                            }
                            // 班组名称
                            kcsjEquipEntryRecordInfoList.get(k).setTeamName(list.get(i).getTeamName() == null ? null : list.get(i).getTeamName());
                            // 设备编码
                            kcsjEquipEntryRecordInfoList.get(k).setEquipCode(children.get(j).getEquipCode() == null ? null : children.get(j).getEquipCode());
                            // 设备名称
                            kcsjEquipEntryRecordInfoList.get(k).setEquipName(children.get(j).getEquipName() == null ? null : children.get(j).getEquipName());
                            // 规格型号
                            kcsjEquipEntryRecordInfoList.get(k).setEquipSpec(children.get(j).getEquipSpec() == null ? null : children.get(j).getEquipSpec());
                            // 单位
                            kcsjEquipEntryRecordInfoList.get(k).setEquipUnit(children.get(j).getEquipUnit() == null ? null : children.get(j).getEquipUnit());
                            kcsjEquipEntryRecordInfoList.get(k).setId(IdWorker.createId());
                            kcsjEquipEntryRecordInfoList.get(k).setRecordId(kcsjEquipEntryRecordInfo.getId());
                            kcsjEquipEntryRecordInfoList.get(k).setCreateTime(DateTime.now());
                            kcsjEquipEntryRecordInfoList.get(k).setCreateUser(SecurityUtils.getUserId() + "");
                            kcsjEquipEntryRecordInfoList.get(k).setCreateUserName(SecurityUtils.getUserName() + "");
                        }
                        //新增或覆盖 设备数据
                        kcsjEquipEntryRecordInfoMapper.insertKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfoList);
                    }
                }
            }
        }
    }

    /**
     * 批量删除
     *
     * @param delIdList
     */
    private void deleteByIds(List<String> delIdList){
        List<KcsjEquipEntryRecord> list =new ArrayList<>();
        for (int i = 0; i < delIdList.size(); i++) {
            KcsjEquipEntryRecord info=new KcsjEquipEntryRecord();
            info.setId(Long.parseLong(delIdList.get(i)));
            info.setUpdateUser(SecurityUtils.getUserId()+"");
            info.setUpdateTime(DateUtils.getNowDate());
            info.setDelFlag("1");
            list.add(info);
        }
        //删除主表数据
        if(!CollectionUtils.isEmpty(list)){
            kcsjEquipEntryRecordMapper.deleteInfoData(list);
        }
    }

    /**
     * 删除设备数据
     * @param
     */
    private void deleteByIdInfo(){
        KcsjEquipEntryRecordInfo info=new KcsjEquipEntryRecordInfo();
        info.setUpdateUser(SecurityUtils.getUserId()+"");
        info.setUpdateTime(DateUtils.getNowDate());
        info.setDelFlag("1");
        //删除
        kcsjEquipEntryRecordInfoMapper.deleteInfoData(info);
    }


    @Transactional
    public int updateKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        kcsjEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
        kcsjEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        return kcsjEquipEntryRecordMapper.updateKcsjEquipEntryRecord(kcsjEquipEntryRecord);
    }

    @Transactional
    public int updateKcsjEquipEntryRecordList(List<KcsjEquipEntryRecord> kcsjEquipEntryRecordList) {
        for (KcsjEquipEntryRecord kcsjEquipEntryRecord : kcsjEquipEntryRecordList) {
            kcsjEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
            kcsjEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjEquipEntryRecordMapper.updateKcsjEquipEntryRecordList(kcsjEquipEntryRecordList);
    }

    @Transactional
    public int deleteKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        kcsjEquipEntryRecord.setUpdateUser(SecurityUtils.getUserName());
        kcsjEquipEntryRecord.setUpdateTime(DateUtils.getNowDate());
        return kcsjEquipEntryRecordMapper.deleteKcsjEquipEntryRecord(kcsjEquipEntryRecord);
    }

    @Transactional
    public int deleteKcsjEquipEntryRecordByPks(List<Long> kcsjEquipEntryRecordPkList) {
        return kcsjEquipEntryRecordMapper.deleteKcsjEquipEntryRecordByPks(kcsjEquipEntryRecordPkList);
    }

    @Override
    public KcsjEquipEntryRecordVo sync() {
        KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo = new KcsjEquipEntryRecordVo();
        List<KcsjEquipEntryRecord> treeToList = new ArrayList<>();
        List<KcsjEquipEntryRecord> treeToListNew = new ArrayList<>();
        AjaxResult ajaxResult = pmServiceApi.getqqchSurveyDesignTeams();
        if (!ajaxResult.get("code").toString().equals(Constant.SUCCESS_CODE)) {
            throw new BaseException("同步前期策划设备数据失败");
        }
        Map<String, Object> dataMap = (Map<String, Object>) ajaxResult.get("data");
        //qqchSurveyEquPlanList 设备list
        List<LinkedHashMap<String, Object>> riskBigProjList = (List<LinkedHashMap<String, Object>>) dataMap.get("qqchSurveyDesignTeamsList");
        //递归处理3.6.2数据结果
        if(!CollectionUtils.isEmpty(riskBigProjList)){
            digui(riskBigProjList, treeToList);
        }
        // 同步，进行提示，以覆盖形式同步数据，但同步是要保留原数据中对应的挂接数据，如挂接的实际进场设备
        if(!CollectionUtils.isEmpty(treeToList)){
            // 查询所有数据
            List<KcsjEquipEntryRecord> kcsjEquipEntryRecords = kcsjEquipEntryRecordMapper.getKcsjEquipEntryRecord(new KcsjEquipEntryRecord());
            if(kcsjEquipEntryRecords.size()>0){
                // 同步数据 tree转list
                List<KcsjEquipEntryRecord> list1 = TreeUtil.treeToListWithoutId(treeToList);
                kcsjEquipEntryRecords.forEach(k->{
                    k.setIsAdd(null);
                });
                // 已存在数据过滤
                List<String> list2 = kcsjEquipEntryRecords.stream().map(KcsjEquipEntryRecord::getSyncId).collect(Collectors.toList());
                // 获取差集
                List<KcsjEquipEntryRecord> list3 = list1.stream().filter(a -> !list2.contains(a.getSyncId())).collect(Collectors.toList());
                if(list3.size()>0){
                    list3.forEach(l3->{
                        l3.setIsAdd("1");
                    });
                    List<KcsjEquipEntryRecord> list5 = TreeUtil.newBuild(list3);
                    treeToListNew.addAll(list5);
                }
                List<KcsjEquipEntryRecord> list4 = TreeUtil.newBuild(kcsjEquipEntryRecords);
                for (int i = 0; i < list4.size(); i++) {
                    if(!CollectionUtils.isEmpty(list4.get(i).getChildren())){
                        List<KcsjEquipEntryRecord> children = list4.get(i).getChildren();
                        for (int j = 0; j < children.size(); j++) {
                            KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo = new KcsjEquipEntryRecordInfo();
                            kcsjEquipEntryRecordInfo.setTeamName(list4.get(i).getTeamName());
                            kcsjEquipEntryRecordInfo.setEquipCode(children.get(j).getEquipCode());
                            List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList = kcsjEquipEntryRecordInfoService.selectInfos(kcsjEquipEntryRecordInfo);
                            if(!CollectionUtils.isEmpty(kcsjEquipEntryRecordInfoList)){
                                children.get(j).setKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfoList);
                            }else{
                                children.get(j).setKcsjEquipEntryRecordInfoList(new ArrayList<>());
                            }
                        }
                    }
                }
                treeToListNew.addAll(list4);

            }else{
                treeToListNew.addAll(treeToList);
            }
        }
        kcsjEquipEntryRecordVo.setTreeList(treeToListNew);
        return kcsjEquipEntryRecordVo;
    }
    private void digui(List<LinkedHashMap<String, Object>> list, List<KcsjEquipEntryRecord> treeToList) {
        for (LinkedHashMap<String, Object> l : list) {
            KcsjEquipEntryRecord kcsjEquipEntryRecord = new KcsjEquipEntryRecord();
            kcsjEquipEntryRecord.setId(IdWorker.createId());
            kcsjEquipEntryRecord.setPid(0L);
            kcsjEquipEntryRecord.setSyncId(l.get("id").toString());
            kcsjEquipEntryRecord.setTeamId(l.get("teamId") == null ? null : l.get("teamId").toString());
            kcsjEquipEntryRecord.setTeamNumber(l.get("teamNumber") == null ? null : l.get("teamNumber").toString());
            kcsjEquipEntryRecord.setTeamName(l.get("teamName") == null ? null : l.get("teamName").toString());
            kcsjEquipEntryRecord.setCreateTime(DateTime.now());
            kcsjEquipEntryRecord.setCreateUser(SecurityUtils.getUserId() + "");
            kcsjEquipEntryRecord.setCreateUserName(SecurityUtils.getUserName() + "");
            kcsjEquipEntryRecord.setDataSource("1");
            kcsjEquipEntryRecord.setIsAdd("1");
            List<LinkedHashMap<String, Object>> qqchSurveyEquPlanList = (List<LinkedHashMap<String, Object>>) l.get("qqchSurveyEquPlanList");
            if(!CollectionUtils.isEmpty(qqchSurveyEquPlanList)){
                for (LinkedHashMap<String, Object> q : qqchSurveyEquPlanList) {
                    KcsjEquipEntryRecord kcsjEquipEntryRecord1 = new KcsjEquipEntryRecord();
                    kcsjEquipEntryRecord1.setId(IdWorker.createId());
                    kcsjEquipEntryRecord1.setPid(kcsjEquipEntryRecord.getId());
                    kcsjEquipEntryRecord1.setSyncId(q.get("id").toString());
                    kcsjEquipEntryRecord1.setTeamId(l.get("teamId") == null ? null : l.get("teamId").toString());
                    kcsjEquipEntryRecord1.setTeamNumber(l.get("teamNumber") == null ? null : l.get("teamNumber").toString());
                    kcsjEquipEntryRecord1.setTeamName(l.get("teamName") == null ? null : l.get("teamName").toString());
                    kcsjEquipEntryRecord1.setEquipCode(q.get("equCode") == null ? null : q.get("equCode").toString());
                    kcsjEquipEntryRecord1.setEquipName(q.get("equName") == null ? null : q.get("equName").toString());
                    kcsjEquipEntryRecord1.setEquipSpec(q.get("equSpec") == null ? null : q.get("equSpec").toString());
                    kcsjEquipEntryRecord1.setEquipUnit(q.get("unit") == null ? null : q.get("unit").toString());
                    kcsjEquipEntryRecord1.setPlanNum(Objects.equals(q.get("num"), new BigDecimal(0)) ? new BigDecimal(0) : new BigDecimal(q.get("num").toString()));
                    kcsjEquipEntryRecord1.setCreateTime(DateTime.now());
                    kcsjEquipEntryRecord1.setCreateUser(SecurityUtils.getUserId() + "");
                    kcsjEquipEntryRecord1.setCreateUserName(SecurityUtils.getUserName() + "");
                    kcsjEquipEntryRecord1.setDataSource("1");
                    kcsjEquipEntryRecord1.setIsAdd("1");
                    kcsjEquipEntryRecord.getChildren().add(kcsjEquipEntryRecord1);
                }
            }
            treeToList.add(kcsjEquipEntryRecord);
        }
    }
}
