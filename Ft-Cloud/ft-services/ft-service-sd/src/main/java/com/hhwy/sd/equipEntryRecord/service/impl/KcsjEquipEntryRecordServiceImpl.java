package com.hhwy.sd.equipEntryRecord.service.impl;

import cn.hutool.core.date.DateTime;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordInfo;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordVo;
import com.hhwy.sd.equipEntryRecord.mapper.KcsjEquipEntryRecordInfoMapper;
import com.hhwy.utils.tree.TreeUtil;
import java.util.ArrayList;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import java.util.stream.Collectors;
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


    public KcsjEquipEntryRecord getKcsjEquipEntryRecord(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        return kcsjEquipEntryRecordMapper.getKcsjEquipEntryRecord(kcsjEquipEntryRecord);
    }

    public KcsjEquipEntryRecordVo getKcsjEquipEntryRecordList(KcsjEquipEntryRecord kcsjEquipEntryRecord) {
        KcsjEquipEntryRecordVo kcsjEquipEntryRecordVo = new KcsjEquipEntryRecordVo();
        List<KcsjEquipEntryRecord> kcsjEquipEntryRecordList = kcsjEquipEntryRecordMapper.getKcsjEquipEntryRecordList(kcsjEquipEntryRecord);
        List<KcsjEquipEntryRecord> kcsjEquipEntryRecords = TreeUtil.newBuild(kcsjEquipEntryRecordList);
        if(!CollectionUtils.isEmpty(kcsjEquipEntryRecords)){
            for (int i = 0; i < kcsjEquipEntryRecords.size(); i++) {
                if(!CollectionUtils.isEmpty(kcsjEquipEntryRecords.get(i).getChildren())){
                    List<KcsjEquipEntryRecord> children = kcsjEquipEntryRecords.get(i).getChildren();
                    for (int j = 0; j < children.size(); j++) {
                        String equipCode = children.get(j).getEquipCode();
                        KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo = new KcsjEquipEntryRecordInfo();
                        kcsjEquipEntryRecordInfo.setTeamName(kcsjEquipEntryRecords.get(i).getTeamName());
                        kcsjEquipEntryRecordInfo.setEquipCode(equipCode);
                        List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList = kcsjEquipEntryRecordInfoService.selectInfos(kcsjEquipEntryRecordInfo);
                        if(!CollectionUtils.isEmpty(kcsjEquipEntryRecordInfoList)){
                            children.get(i).setKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfoList);
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
}
