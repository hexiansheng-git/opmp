package com.hhwy.sd.disclosureRecord.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.feign.service.PmServiceApi;
import com.hhwy.pm.qqch.preparation.survey.designDisclosurePlan.dto.DesignDisclosurePlanDto;
import com.hhwy.sd.disclosureRecord.domain.KcsjDisclosureRecord;
import com.hhwy.sd.disclosureRecord.domain.vo.DisclosureRecordQueryVo;
import com.hhwy.sd.disclosureRecord.domain.vo.DisclosureRecordVo;
import com.hhwy.sd.disclosureRecord.mapper.KcsjDisclosureRecordMapper;
import com.hhwy.sd.disclosureRecord.service.IKcsjDisclosureRecordService;
import com.hhwy.sd.sync.mq.ISysSyncInfoService4Sd;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author han
 * @date 2023-12-18 11:15:48
 * @remark
 */
@Service
public class KcsjDisclosureRecordServiceImpl implements IKcsjDisclosureRecordService {

    @Autowired
    private KcsjDisclosureRecordMapper kcsjDisclosureRecordMapper;
    
    @Autowired
    private PmServiceApi pmServiceApi;

    @Autowired
    private ISysSyncInfoService4Sd sysSyncInfoService4Sd;


    public KcsjDisclosureRecord getKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord) {
        return kcsjDisclosureRecordMapper.getKcsjDisclosureRecord(kcsjDisclosureRecord);
    }

    public List<KcsjDisclosureRecord> getKcsjDisclosureRecordList(DisclosureRecordQueryVo queryVo) {
        return kcsjDisclosureRecordMapper.getKcsjDisclosureRecordList(queryVo);
    }

    @Transactional
    public int insertKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord) {
        kcsjDisclosureRecord.setId(IdWorker.createId());
        kcsjDisclosureRecord.setCreateUser(SecurityUtils.getUserName());
        kcsjDisclosureRecord.setCreateTime(DateUtils.getNowDate());
        return kcsjDisclosureRecordMapper.insertKcsjDisclosureRecord(kcsjDisclosureRecord);
    }

    @Override
    @Transactional
    public void save(DisclosureRecordVo recordVo) {
        List<KcsjDisclosureRecord> recordList = recordVo.getRecordList();
        List<Long> delIdList = recordVo.getDelIdList();
        Set<Long> delIdSet = new HashSet<>(delIdList);
        recordList = recordList.stream().filter(o -> !delIdSet.contains(o.getId())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(recordList) && CollectionUtils.isEmpty(delIdList)){
            return;
        }
        List<KcsjDisclosureRecord> addList = new ArrayList<>();
        List<KcsjDisclosureRecord> updateList = new ArrayList<>();

        for (KcsjDisclosureRecord record : recordList) {
            if("1".equals(record.getIsAdd())) {
                addList.add(record);
            } else {
                record.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                record.setUpdateTime(DateUtils.getNowDate());
                updateList.add(record);
            }
        }
        if(CollectionUtils.isNotEmpty(addList)) {
            this.insertKcsjDisclosureRecordList(addList);
        }
        if(CollectionUtils.isNotEmpty(updateList)) {
            kcsjDisclosureRecordMapper.updateKcsjDisclosureRecordList(updateList);
        }

        if(CollectionUtils.isNotEmpty(delIdList)){
            kcsjDisclosureRecordMapper.deleteKcsjDisclosureRecordByPks(delIdList);
        }

        this.pushData();
    }

    @Transactional
    public int insertKcsjDisclosureRecordList(List<KcsjDisclosureRecord> kcsjDisclosureRecordList) {
        for (KcsjDisclosureRecord kcsjDisclosureRecord : kcsjDisclosureRecordList) {
            kcsjDisclosureRecord.setId(IdWorker.createId());
            kcsjDisclosureRecord.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            kcsjDisclosureRecord.setCreateUser(SecurityUtils.getUserName());
            kcsjDisclosureRecord.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjDisclosureRecordMapper.insertKcsjDisclosureRecordList(kcsjDisclosureRecordList);
    }

    @Transactional
    public int updateKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord) {
        kcsjDisclosureRecord.setUpdateUser(SecurityUtils.getUserName());
        kcsjDisclosureRecord.setUpdateTime(DateUtils.getNowDate());
        return kcsjDisclosureRecordMapper.updateKcsjDisclosureRecord(kcsjDisclosureRecord);
    }

    @Transactional
    public int updateKcsjDisclosureRecordList(List<KcsjDisclosureRecord> kcsjDisclosureRecordList) {
        for (KcsjDisclosureRecord kcsjDisclosureRecord : kcsjDisclosureRecordList) {
            kcsjDisclosureRecord.setUpdateUser(SecurityUtils.getUserName());
            kcsjDisclosureRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjDisclosureRecordMapper.updateKcsjDisclosureRecordList(kcsjDisclosureRecordList);
    }

    @Transactional
    public int deleteKcsjDisclosureRecord(KcsjDisclosureRecord kcsjDisclosureRecord) {
        kcsjDisclosureRecord.setUpdateUser(SecurityUtils.getUserName());
        kcsjDisclosureRecord.setUpdateTime(DateUtils.getNowDate());
        return kcsjDisclosureRecordMapper.deleteKcsjDisclosureRecord(kcsjDisclosureRecord);
    }

    @Transactional
    public int deleteKcsjDisclosureRecordByPks(List<Long> kcsjDisclosureRecordPkList) {
        return kcsjDisclosureRecordMapper.deleteKcsjDisclosureRecordByPks(kcsjDisclosureRecordPkList);
    }

    @Override
    public List<KcsjDisclosureRecord> getListByIds(List<Long> ids) {
        return kcsjDisclosureRecordMapper.getListByIds(ids);
    }

    @Override
    @Transactional
    public void sync() {
        //删除所有数据
        kcsjDisclosureRecordMapper.deleteAll();

        List<KcsjDisclosureRecord> recordList = new ArrayList<>();
        List<DesignDisclosurePlanDto> disclosurePlanDtoList = pmServiceApi.getDisclosurePlanDtoList();
        if(CollectionUtils.isEmpty(disclosurePlanDtoList)){
            return;
        }

        for (DesignDisclosurePlanDto dto : disclosurePlanDtoList) {
            KcsjDisclosureRecord record = new KcsjDisclosureRecord();
            record.setId(IdWorker.createId());
            record.setDisclosureName(dto.getName());
            record.setDisclosureUnit(dto.getDisclosureUnit());
            record.setBeDisclosureUnit(dto.getPassiveDisclosureUnit());
            record.setDisclosureContent(dto.getDisclosureContent());
            record.setActualDisclosureDate(dto.getPlanDisclosureDate());
            record.setDataSource("1");
            record.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
            record.setCreateUser(SecurityUtils.getUserName());
            record.setCreateTime(DateUtils.getNowDate());
            recordList.add(record);
        }

        //入库
        kcsjDisclosureRecordMapper.insertKcsjDisclosureRecordList(recordList);

        this.pushData();
    }

    private void pushData() {
        List<KcsjDisclosureRecord> list = kcsjDisclosureRecordMapper.getKcsjDisclosureRecordList(new DisclosureRecordQueryVo());
        sysSyncInfoService4Sd.pushDisclosureRecord(list);
    }
}
