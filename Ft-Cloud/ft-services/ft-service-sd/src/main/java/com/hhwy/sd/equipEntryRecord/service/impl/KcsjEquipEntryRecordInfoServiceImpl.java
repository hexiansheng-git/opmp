package com.hhwy.sd.equipEntryRecord.service.impl;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecord;
import com.hhwy.sd.equipEntryRecord.mapper.KcsjEquipEntryRecordInfoMapper;
import com.hhwy.sd.equipEntryRecord.service.IKcsjEquipEntryRecordInfoService;
import com.hhwy.utils.date.FtDateUtils;
import com.hhwy.utils.tree.TreeUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sd.equipEntryRecord.domain.KcsjEquipEntryRecordInfo;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.util.CollectionUtils;

/**
 * @author zmh
 * @date 2023-12-14 11:08:15
 * @remark
 */
@Service
public class KcsjEquipEntryRecordInfoServiceImpl implements IKcsjEquipEntryRecordInfoService {

    @Autowired
    private KcsjEquipEntryRecordInfoMapper kcsjEquipEntryRecordInfoMapper;


    public KcsjEquipEntryRecordInfo getKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo) {
        return kcsjEquipEntryRecordInfoMapper.getKcsjEquipEntryRecordInfo(kcsjEquipEntryRecordInfo);
    }

    public List<KcsjEquipEntryRecordInfo> getKcsjEquipEntryRecordInfoList(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo) {
        List<KcsjEquipEntryRecordInfo> list = new ArrayList<>();
        //判断日期
        if (StringUtils.isNotEmpty(kcsjEquipEntryRecordInfo.getEntryDateStr())){
            String entryDateStr = kcsjEquipEntryRecordInfo.getEntryDateStr();
            String[] split = entryDateStr.split(",");
            kcsjEquipEntryRecordInfo.setEntryDateStr(split[0].replaceAll("(?:年|月|日)", "-"));
            kcsjEquipEntryRecordInfo.setEntryEndDateStr(split[1].replaceAll("(?:年|月|日)", "-"));
        }
        List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList = kcsjEquipEntryRecordInfoMapper.getKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfo);
        for (KcsjEquipEntryRecordInfo info:kcsjEquipEntryRecordInfoList) {
            info.setEntryDateStr(info.getEntryDate() == null ? null : FtDateUtils.formatDate(info.getEntryDate()));
            info.setExitDateStr(info.getExitDate() == null ? null : FtDateUtils.formatDate(info.getExitDate()));
        }
        List<KcsjEquipEntryRecordInfo> allList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(kcsjEquipEntryRecordInfoList)){
            List<KcsjEquipEntryRecordInfo> list1 = kcsjEquipEntryRecordInfoList.stream().filter(e -> StringUtils.isNotEmpty(e.getPid().toString()) && !e.getPid().toString().equals("0")).collect(Collectors.toList());
            if(list1.size()>0){
                allList.addAll(list1);
                KcsjEquipEntryRecordInfo entryRecordInfo = new KcsjEquipEntryRecordInfo();
                List<Long> pidAll = new ArrayList<>();
                for (int i = 0; i < list1.size(); i++) {
                    pidAll.add(list1.get(i).getPid());
                }
                entryRecordInfo.setIds(pidAll);
                List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecords1 = kcsjEquipEntryRecordInfoMapper.getKcsjEquipEntryRecordInfoList(entryRecordInfo);
                kcsjEquipEntryRecordInfoList.addAll(kcsjEquipEntryRecords1);
                allList.addAll(kcsjEquipEntryRecords1);
            } else {
                return new ArrayList<>();
            }
            List<KcsjEquipEntryRecordInfo> collect = allList.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(KcsjEquipEntryRecordInfo::getId))), ArrayList::new));
            list = collect.stream().sorted(Comparator.comparing(KcsjEquipEntryRecordInfo::getId)).collect(Collectors.toList());
        }
        return TreeUtil.newBuild(list);
    }

    @Transactional
    public int insertKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo) {
        kcsjEquipEntryRecordInfo.setId(IdWorker.createId());
        kcsjEquipEntryRecordInfo.setCreateUser(SecurityUtils.getUserName());
        kcsjEquipEntryRecordInfo.setCreateTime(DateUtils.getNowDate());
        return kcsjEquipEntryRecordInfoMapper.insertKcsjEquipEntryRecordInfo(kcsjEquipEntryRecordInfo);
    }

    @Transactional
    public int insertKcsjEquipEntryRecordInfoList(
        List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList) {
        for (KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo : kcsjEquipEntryRecordInfoList) {
            kcsjEquipEntryRecordInfo.setId(IdWorker.createId());
            kcsjEquipEntryRecordInfo.setCreateUser(SecurityUtils.getUserName());
            kcsjEquipEntryRecordInfo.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjEquipEntryRecordInfoMapper.insertKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfoList);
    }

    @Transactional
    public int updateKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo) {
        kcsjEquipEntryRecordInfo.setUpdateUser(SecurityUtils.getUserName());
        kcsjEquipEntryRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return kcsjEquipEntryRecordInfoMapper.updateKcsjEquipEntryRecordInfo(kcsjEquipEntryRecordInfo);
    }

    @Transactional
    public int updateKcsjEquipEntryRecordInfoList(List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList) {
        for (KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo : kcsjEquipEntryRecordInfoList) {
            kcsjEquipEntryRecordInfo.setUpdateUser(SecurityUtils.getUserName());
            kcsjEquipEntryRecordInfo.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjEquipEntryRecordInfoMapper.updateKcsjEquipEntryRecordInfoList(kcsjEquipEntryRecordInfoList);
    }

    @Transactional
    public int deleteKcsjEquipEntryRecordInfo(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo) {
        kcsjEquipEntryRecordInfo.setUpdateUser(SecurityUtils.getUserName());
        kcsjEquipEntryRecordInfo.setUpdateTime(DateUtils.getNowDate());
        return kcsjEquipEntryRecordInfoMapper.deleteKcsjEquipEntryRecordInfo(kcsjEquipEntryRecordInfo);
    }

    @Transactional
    public int deleteKcsjEquipEntryRecordInfoByPks(List<Long> kcsjEquipEntryRecordInfoPkList) {
        return kcsjEquipEntryRecordInfoMapper.deleteKcsjEquipEntryRecordInfoByPks(kcsjEquipEntryRecordInfoPkList);
    }

    public List<KcsjEquipEntryRecordInfo> selectInfos(KcsjEquipEntryRecordInfo kcsjEquipEntryRecordInfo) {
        List<KcsjEquipEntryRecordInfo> list = kcsjEquipEntryRecordInfoMapper.selectInfos(kcsjEquipEntryRecordInfo);
        return list;
    }

    @Override
    public List<KcsjEquipEntryRecordInfo> getIds(List<Long> ids) {
        List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecordInfoList = kcsjEquipEntryRecordInfoMapper.getIds(ids);
        if(kcsjEquipEntryRecordInfoList.size()>0){
            List<KcsjEquipEntryRecordInfo> list1 = kcsjEquipEntryRecordInfoList.stream().filter(e -> StringUtils.isNotEmpty(e.getPid().toString()) && e.getPid().toString().equals("0")).collect(Collectors.toList());
            if(list1.size()>0){
                KcsjEquipEntryRecordInfo entryRecordInfo = new KcsjEquipEntryRecordInfo();
                List<Long> pidAll = new ArrayList<>();
                for (int i = 0; i < list1.size(); i++) {
                    pidAll.add(list1.get(i).getId());
                }
                entryRecordInfo.setPids(pidAll);
                List<KcsjEquipEntryRecordInfo> kcsjEquipEntryRecords1 = kcsjEquipEntryRecordInfoMapper.getKcsjEquipEntryRecordInfoList(entryRecordInfo);
                kcsjEquipEntryRecordInfoList.addAll(kcsjEquipEntryRecords1);
            }
            List<KcsjEquipEntryRecordInfo> collect = kcsjEquipEntryRecordInfoList.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(KcsjEquipEntryRecordInfo::getId))), ArrayList::new));
            kcsjEquipEntryRecordInfoList = collect.stream().sorted(Comparator.comparing(KcsjEquipEntryRecordInfo::getId)).collect(Collectors.toList());
        }
        return kcsjEquipEntryRecordInfoList;
    }
}
