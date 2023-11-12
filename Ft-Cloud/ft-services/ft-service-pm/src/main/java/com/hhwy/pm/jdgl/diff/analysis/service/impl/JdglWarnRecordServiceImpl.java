package com.hhwy.pm.jdgl.diff.analysis.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.jdgl.diff.analysis.domain.JdglWarnRecord;
import com.hhwy.pm.jdgl.diff.analysis.mapper.JdglWarnRecordMapper;
import com.hhwy.pm.jdgl.diff.analysis.service.IJdglWarnRecordService;
import com.hhwy.utils.common.CommonAssert;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-09-01 13:29:05
 * @remark
 */
@Service
public class JdglWarnRecordServiceImpl implements IJdglWarnRecordService {

    @Autowired
    private JdglWarnRecordMapper jdglWarnRecordMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    public JdglWarnRecord getJdglWarnRecord(JdglWarnRecord jdglWarnRecord) {
        return jdglWarnRecordMapper.getJdglWarnRecord(jdglWarnRecord);
    }

    public List<JdglWarnRecord> getJdglWarnRecordList(JdglWarnRecord jdglWarnRecord) {
        return jdglWarnRecordMapper.getJdglWarnRecordList(jdglWarnRecord);
    }

    @Transactional
    public int insertJdglWarnRecord(JdglWarnRecord jdglWarnRecord) {
        jdglWarnRecord.setId(IdWorker.createId());
        jdglWarnRecord.setCreateUser(SecurityUtils.getUserName());
        jdglWarnRecord.setCreateTime(DateUtils.getNowDate());
        return jdglWarnRecordMapper.insertJdglWarnRecord(jdglWarnRecord);
    }

    @Transactional
    public int insertJdglWarnRecordList(List<JdglWarnRecord> jdglWarnRecordList) {
        for (JdglWarnRecord jdglWarnRecord : jdglWarnRecordList) {
            jdglWarnRecord.setId(IdWorker.createId());
            jdglWarnRecord.setCreateUser(SecurityUtils.getUserName());
            jdglWarnRecord.setCreateTime(DateUtils.getNowDate());
        }
        return jdglWarnRecordMapper.insertJdglWarnRecordList(jdglWarnRecordList);
    }

    @Transactional
    public int updateJdglWarnRecord(JdglWarnRecord jdglWarnRecord) {
        jdglWarnRecord.setUpdateUser(SecurityUtils.getUserName());
        jdglWarnRecord.setUpdateTime(DateUtils.getNowDate());
        return jdglWarnRecordMapper.updateJdglWarnRecord(jdglWarnRecord);
    }

    @Transactional
    public int updateJdglWarnRecordList(List<JdglWarnRecord> jdglWarnRecordList) {
        for (JdglWarnRecord jdglWarnRecord : jdglWarnRecordList) {
            jdglWarnRecord.setUpdateUser(SecurityUtils.getUserName());
            jdglWarnRecord.setUpdateTime(DateUtils.getNowDate());
        }
        return jdglWarnRecordMapper.updateJdglWarnRecordList(jdglWarnRecordList);
    }

    @Transactional
    public int deleteJdglWarnRecord(JdglWarnRecord jdglWarnRecord) {
        jdglWarnRecord.setUpdateUser(SecurityUtils.getUserName());
        jdglWarnRecord.setUpdateTime(DateUtils.getNowDate());
        return jdglWarnRecordMapper.deleteJdglWarnRecord(jdglWarnRecord);
    }

    @Transactional
    public int deleteJdglWarnRecordByPks(List<Long> jdglWarnRecordPkList) {
        return jdglWarnRecordMapper.deleteJdglWarnRecordByPks(jdglWarnRecordPkList);
    }
}
