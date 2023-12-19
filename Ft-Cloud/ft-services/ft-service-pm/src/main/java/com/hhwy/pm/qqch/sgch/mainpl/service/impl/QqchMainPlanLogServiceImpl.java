package com.hhwy.pm.qqch.sgch.mainpl.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.sgch.mainpl.domain.QqchMainPlanLog;
import com.hhwy.pm.qqch.sgch.mainpl.mapper.QqchMainPlanLogMapper;
import com.hhwy.pm.qqch.sgch.mainpl.service.IQqchMainPlanLogService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2023-12-15 17:00:32
 * @remark
 */
@Service
public class QqchMainPlanLogServiceImpl implements IQqchMainPlanLogService {

    @Autowired
    private QqchMainPlanLogMapper qqchMainPlanLogMapper;


    public QqchMainPlanLog getQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog) {
        QqchMainPlanLog qqchMainPlanLog1 = qqchMainPlanLogMapper.getQqchMainPlanLog(qqchMainPlanLog);
        if(qqchMainPlanLog1 == null) {
            qqchMainPlanLog1 = new QqchMainPlanLog();
            qqchMainPlanLog1.setTimeNumUnit("m");
            qqchMainPlanLog1.setTimeNum(5);
        }
        return qqchMainPlanLog1;
    }

    public List<QqchMainPlanLog> getQqchMainPlanLogList(QqchMainPlanLog qqchMainPlanLog) {
        return qqchMainPlanLogMapper.getQqchMainPlanLogList(qqchMainPlanLog);
    }

    @Transactional
    public int insertQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog) {
        qqchMainPlanLog.setId(IdWorker.createId());
//        qqchMainPlanLog.setCreateUser(SecurityUtils.getUserName());
        qqchMainPlanLog.setCreateTime(DateUtils.getNowDate());
        return qqchMainPlanLogMapper.insertQqchMainPlanLog(qqchMainPlanLog);
    }

    @Transactional
    public int insertQqchMainPlanLogList(List<QqchMainPlanLog> qqchMainPlanLogList) {
        for (QqchMainPlanLog qqchMainPlanLog : qqchMainPlanLogList) {
            qqchMainPlanLog.setId(IdWorker.createId());
            qqchMainPlanLog.setCreateUser(SecurityUtils.getUserName());
            qqchMainPlanLog.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMainPlanLogMapper.insertQqchMainPlanLogList(qqchMainPlanLogList);
    }

    @Transactional
    public int updateQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog) {
//        qqchMainPlanLog.setUpdateUser(SecurityUtils.getUserName());
        qqchMainPlanLog.setUpdateTime(DateUtils.getNowDate());
        return qqchMainPlanLogMapper.updateQqchMainPlanLog(qqchMainPlanLog);
    }

    @Transactional
    public int updateQqchMainPlanLogList(List<QqchMainPlanLog> qqchMainPlanLogList) {
        for (QqchMainPlanLog qqchMainPlanLog : qqchMainPlanLogList) {
            qqchMainPlanLog.setUpdateUser(SecurityUtils.getUserName());
            qqchMainPlanLog.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMainPlanLogMapper.updateQqchMainPlanLogList(qqchMainPlanLogList);
    }

    @Transactional
    public int deleteQqchMainPlanLog(QqchMainPlanLog qqchMainPlanLog) {
        qqchMainPlanLog.setUpdateUser(SecurityUtils.getUserName());
        qqchMainPlanLog.setUpdateTime(DateUtils.getNowDate());
        return qqchMainPlanLogMapper.deleteQqchMainPlanLog(qqchMainPlanLog);
    }

    @Transactional
    public int deleteQqchMainPlanLogByPks(List<Long> qqchMainPlanLogPkList) {
        return qqchMainPlanLogMapper.deleteQqchMainPlanLogByPks(qqchMainPlanLogPkList);
    }

    @Override
    public int updateQqchMainPlanLog(String operation, String logContent, int timeNum, String timeNumUnit) {
        QqchMainPlanLog query = new QqchMainPlanLog();
        query.setOperation(operation);
        QqchMainPlanLog qqchMainPlanLog1 = getQqchMainPlanLog(query);
        int i = 0;
        if(qqchMainPlanLog1 == null) {
            query.setLogContent(logContent);
            query.setTimeNum(timeNum);
            query.setTimeNumUnit(timeNumUnit);
            i = insertQqchMainPlanLog(query);
        } else {
            qqchMainPlanLog1.setLogContent(logContent);
            qqchMainPlanLog1.setTimeNum(timeNum);
            qqchMainPlanLog1.setTimeNumUnit(timeNumUnit);
            i = updateQqchMainPlanLog(qqchMainPlanLog1);
        }

        return i;
    }
}
