package com.hhwy.system.warn.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.system.warn.domain.TWarn;
import com.hhwy.system.warn.mapper.TWarnMapper;
import com.hhwy.system.warn.service.ITWarnService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-09-26 17:52:25
 * @remark
 */
@Service
public class TWarnServiceImpl implements ITWarnService {

    @Autowired
    private TWarnMapper tWarnMapper;


    public TWarn getTWarn(TWarn tWarn) {
        return tWarnMapper.getTWarn(tWarn);
    }

    public List<TWarn> getTWarnList(TWarn tWarn) {
        return tWarnMapper.getTWarnList(tWarn);
    }

    @Transactional
    public int insertTWarn(TWarn tWarn) {
        tWarn.setWarnId(IdWorker.createId());
        tWarn.setCreateUser(SecurityUtils.getUserName());
        tWarn.setCreateTime(DateUtils.getNowDate());
        return tWarnMapper.insertTWarn(tWarn);
    }

    @Transactional
    public int insertTWarnList(List<TWarn> tWarnList) {
        for (TWarn tWarn : tWarnList) {
            tWarn.setWarnId(IdWorker.createId());
            tWarn.setCreateUser(SecurityUtils.getUserName());
            tWarn.setCreateTime(DateUtils.getNowDate());
        }
        return tWarnMapper.insertTWarnList(tWarnList);
    }

    @Transactional
    public int updateTWarn(TWarn tWarn) {
        tWarn.setUpdateUser(SecurityUtils.getUserName());
        tWarn.setUpdateTime(DateUtils.getNowDate());
        return tWarnMapper.updateTWarn(tWarn);
    }

    @Transactional
    public int updateTWarnList(List<TWarn> tWarnList) {
        for (TWarn tWarn : tWarnList) {
            tWarn.setUpdateUser(SecurityUtils.getUserName());
            tWarn.setUpdateTime(DateUtils.getNowDate());
        }
        return tWarnMapper.updateTWarnList(tWarnList);
    }

    @Transactional
    public int deleteTWarn(TWarn tWarn) {
        tWarn.setUpdateUser(SecurityUtils.getUserName());
        tWarn.setUpdateTime(DateUtils.getNowDate());
        return tWarnMapper.deleteTWarn(tWarn);
    }

    @Transactional
    public int deleteTWarnByPks(List<Long> tWarnPkList) {
        return tWarnMapper.deleteTWarnByPks(tWarnPkList);
    }
}
