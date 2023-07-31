package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiff;
import com.hhwy.pm.qqch.sgch.sche.mapper.QqchScheDiffMapper;
import com.hhwy.pm.qqch.sgch.sche.service.IQqchScheDiffService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:48
 * @remark
 */
@Service
public class QqchScheDiffServiceImpl implements IQqchScheDiffService {

    @Autowired
    private QqchScheDiffMapper qqchScheDiffMapper;


    public QqchScheDiff getQqchScheDiff(QqchScheDiff qqchScheDiff) {
        return qqchScheDiffMapper.getQqchScheDiff(qqchScheDiff);
    }

    public List<QqchScheDiff> getQqchScheDiffList(QqchScheDiff qqchScheDiff) {
        return qqchScheDiffMapper.getQqchScheDiffList(qqchScheDiff);
    }

    @Transactional
    public int insertQqchScheDiff(QqchScheDiff qqchScheDiff) {
        qqchScheDiff.setId(IdWorker.createId());
        qqchScheDiff.setCreateUser(SecurityUtils.getUserName());
        qqchScheDiff.setCreateTime(DateUtils.getNowDate());
        return qqchScheDiffMapper.insertQqchScheDiff(qqchScheDiff);
    }

    @Transactional
    public int insertQqchScheDiffList(List<QqchScheDiff> qqchScheDiffList) {
        for (QqchScheDiff qqchScheDiff : qqchScheDiffList) {
            qqchScheDiff.setId(IdWorker.createId());
            qqchScheDiff.setCreateUser(SecurityUtils.getUserName());
            qqchScheDiff.setCreateTime(DateUtils.getNowDate());
        }
        return qqchScheDiffMapper.insertQqchScheDiffList(qqchScheDiffList);
    }

    @Transactional
    public int updateQqchScheDiff(QqchScheDiff qqchScheDiff) {
        qqchScheDiff.setUpdateUser(SecurityUtils.getUserName());
        qqchScheDiff.setUpdateTime(DateUtils.getNowDate());
        return qqchScheDiffMapper.updateQqchScheDiff(qqchScheDiff);
    }

    @Transactional
    public int updateQqchScheDiffList(List<QqchScheDiff> qqchScheDiffList) {
        for (QqchScheDiff qqchScheDiff : qqchScheDiffList) {
            qqchScheDiff.setUpdateUser(SecurityUtils.getUserName());
            qqchScheDiff.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchScheDiffMapper.updateQqchScheDiffList(qqchScheDiffList);
    }

    @Transactional
    public int deleteQqchScheDiff(QqchScheDiff qqchScheDiff) {
        qqchScheDiff.setUpdateUser(SecurityUtils.getUserName());
        qqchScheDiff.setUpdateTime(DateUtils.getNowDate());
        return qqchScheDiffMapper.deleteQqchScheDiff(qqchScheDiff);
    }

    @Transactional
    public int deleteQqchScheDiffByPks(List<Long> qqchScheDiffPkList) {
        return qqchScheDiffMapper.deleteQqchScheDiffByPks(qqchScheDiffPkList);
    }
}
