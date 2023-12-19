package com.hhwy.sp.sgjsDiscloseRecord.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.core.utils.StringUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sp.sgjsDiscloseRecord.domain.SgjsDiscloseRecord;
import com.hhwy.sp.sgjsDiscloseRecord.mapper.SgjsDiscloseRecordMapper;
import com.hhwy.sp.sgjsDiscloseRecord.service.ISgjsDiscloseRecordService;
import com.hhwy.sp.sync.mq.service.ISysSyncInfoService4Sp;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2023-11-23 14:47:22
 * @remark
 */
@Service
public class SgjsDiscloseRecordServiceImpl implements ISgjsDiscloseRecordService {

    @Autowired
    private SgjsDiscloseRecordMapper sgjsDiscloseRecordMapper;
    @Autowired
    private ISysSyncInfoService4Sp syncInfoService;


    public SgjsDiscloseRecord getSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord) {
        return sgjsDiscloseRecordMapper.getSgjsDiscloseRecord(sgjsDiscloseRecord);
    }

    public List<SgjsDiscloseRecord> getSgjsDiscloseRecordList(SgjsDiscloseRecord sgjsDiscloseRecord) {
        String dataType = sgjsDiscloseRecord.getDataType();
        if(StringUtils.isEmpty(dataType)) {
            throw new RuntimeException("参数异常！");
        }
        return sgjsDiscloseRecordMapper.getSgjsDiscloseRecordList(sgjsDiscloseRecord);
    }

    @Transactional
    public int insertSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord) {
        sgjsDiscloseRecord.setId(IdWorker.createId());
        sgjsDiscloseRecord.setCreateUser(SecurityUtils.getSysUser().getNickName());
        sgjsDiscloseRecord.setCreateTime(DateUtils.getNowDate());
        return sgjsDiscloseRecordMapper.insertSgjsDiscloseRecord(sgjsDiscloseRecord);
    }

    @Transactional
    public int insertSgjsDiscloseRecordList(List<SgjsDiscloseRecord> sgjsDiscloseRecordList) {
        if(CollectionUtils.isEmpty(sgjsDiscloseRecordList)) {
            return 0;
        }
        for (SgjsDiscloseRecord sgjsDiscloseRecord : sgjsDiscloseRecordList) {
            sgjsDiscloseRecord.setId(IdWorker.createId());
            sgjsDiscloseRecord.setCreateUser(SecurityUtils.getSysUser().getNickName());
            sgjsDiscloseRecord.setCreateTime(DateUtils.getNowDate());
        }
        return sgjsDiscloseRecordMapper.insertSgjsDiscloseRecordList(sgjsDiscloseRecordList);
    }

    @Transactional
    public int updateSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord) {
        sgjsDiscloseRecord.setUpdateUser(SecurityUtils.getSysUser().getNickName());
        sgjsDiscloseRecord.setUpdateTime(DateUtils.getNowDate());
        return sgjsDiscloseRecordMapper.updateSgjsDiscloseRecord(sgjsDiscloseRecord);
    }

    @Transactional
    public int updateSgjsDiscloseRecordList(List<SgjsDiscloseRecord> sgjsDiscloseRecordList) {
        if(CollectionUtils.isEmpty(sgjsDiscloseRecordList)) {
            return 0;
        }
        String dataType = sgjsDiscloseRecordList.get(0).getDataType();
        List<SgjsDiscloseRecord> addList = new ArrayList<>();
        List<SgjsDiscloseRecord> updateList = new ArrayList<>();

        for (SgjsDiscloseRecord sgjsDiscloseRecord : sgjsDiscloseRecordList) {
            if("1".equals(sgjsDiscloseRecord.getIsAdd())) {
                addList.add(sgjsDiscloseRecord);
            } else {
                sgjsDiscloseRecord.setUpdateUser(SecurityUtils.getSysUser().getNickName());
                sgjsDiscloseRecord.setUpdateTime(DateUtils.getNowDate());
                updateList.add(sgjsDiscloseRecord);
            }
        }
        int i = 0;
        if(CollectionUtils.isNotEmpty(addList)) {
            i = i + insertSgjsDiscloseRecordList(addList);
        }
        if(CollectionUtils.isNotEmpty(updateList)) {
            i = i + sgjsDiscloseRecordMapper.updateSgjsDiscloseRecordList(updateList);
        }
        if(i > 0) {
            pushDataToHeadquarters(dataType);
        }
        return i;
    }

    /**
     * 推送数据到总部
     * @param
     */
    public void pushDataToHeadquarters(String dataType) {
        if(StringUtils.isEmpty(dataType)) {
            return;
        }
        SgjsDiscloseRecord sgjsDiscloseRecord = new SgjsDiscloseRecord();
        sgjsDiscloseRecord.setDataType(dataType);
        List<SgjsDiscloseRecord> sgjsDiscloseRecordList = getSgjsDiscloseRecordList(sgjsDiscloseRecord);
        if(CollectionUtils.isNotEmpty(sgjsDiscloseRecordList)) {
           // syncInfoService.pushSgjsDiscloseRecord(sgjsDiscloseRecordList);
        }
    };

    @Transactional
    public int deleteSgjsDiscloseRecord(SgjsDiscloseRecord sgjsDiscloseRecord) {
        return sgjsDiscloseRecordMapper.deleteSgjsDiscloseRecord(sgjsDiscloseRecord);
    }

    @Transactional
    public int deleteSgjsDiscloseRecordByPks(List<Long> sgjsDiscloseRecordPkList) {
        int i = sgjsDiscloseRecordMapper.deleteSgjsDiscloseRecordByPks(sgjsDiscloseRecordPkList);
        if(i > 0) {
            List<SgjsDiscloseRecord> sgjsDiscloseRecordList = sgjsDiscloseRecordMapper.getSgjsDiscloseRecordListByIds(sgjsDiscloseRecordPkList);
            if(CollectionUtils.isNotEmpty(sgjsDiscloseRecordList)){
                String dataType = sgjsDiscloseRecordList.get(0).getDataType();
                pushDataToHeadquarters(dataType);
            }
        }

        return i;
    }

    public List<SgjsDiscloseRecord> getSgjsDiscloseRecordListByNames(List<String> discloseNames) {
        return sgjsDiscloseRecordMapper.getSgjsDiscloseRecordListByNames(discloseNames);
    }

    @Override
    public int importData(List<SgjsDiscloseRecord> sgjsDiscloseRecordList, String dataType) {
        if(CollectionUtils.isEmpty(sgjsDiscloseRecordList)) {
            return 0;
        }
        List<String> discloseNames = sgjsDiscloseRecordList.stream().map(SgjsDiscloseRecord::getDiscloseName).collect(Collectors.toList());
        List<SgjsDiscloseRecord> exists = getSgjsDiscloseRecordListByNames(discloseNames);
        for (SgjsDiscloseRecord sgjsDiscloseRecord : sgjsDiscloseRecordList) {
            sgjsDiscloseRecord.setDataType(dataType);
            String discloseName = sgjsDiscloseRecord.getDiscloseName();
            sgjsDiscloseRecord.setIsAdd("1");
            if(CollectionUtils.isNotEmpty(exists)) {
                SgjsDiscloseRecord sgjsDiscloseRecord1 = exists.stream().filter(vo -> discloseName.equals(vo.getDiscloseName())).findFirst().orElse(null);
                if(sgjsDiscloseRecord1 != null) {
                    sgjsDiscloseRecord.setId(sgjsDiscloseRecord1.getId());
                    sgjsDiscloseRecord.setFileGroupId(sgjsDiscloseRecord1.getFileGroupId());
                    sgjsDiscloseRecord.setIsAdd(null);
                }
            }
        }

        return updateSgjsDiscloseRecordList(sgjsDiscloseRecordList);
    }
}
