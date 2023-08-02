package com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.service.impl;

import java.util.List;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.mapper.QqchTotalDemandTimeCountMapper;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.service.IQqchTotalDemandTimeCountService;
import com.hhwy.pm.qqch.sgch.wzzx.qqchTotalDemandTimeCount.domain.QqchTotalDemandTimeCount;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author ldd
 * @date 2023-08-02 10:55:17
 * @remark 
 */
@Service
public class QqchTotalDemandTimeCountServiceImpl implements IQqchTotalDemandTimeCountService{

    @Autowired
    private QqchTotalDemandTimeCountMapper qqchTotalDemandTimeCountMapper;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    public QqchTotalDemandTimeCount getQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount) {
        return qqchTotalDemandTimeCountMapper.getQqchTotalDemandTimeCount(qqchTotalDemandTimeCount);
    }

    public List<QqchTotalDemandTimeCount> getQqchTotalDemandTimeCountList(QqchTotalDemandTimeCount qqchTotalDemandTimeCount) {
        return qqchTotalDemandTimeCountMapper.getQqchTotalDemandTimeCountList(qqchTotalDemandTimeCount);
    }

    @Transactional
    public int insertQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount) {
        qqchTotalDemandTimeCount.setId(IdWorker.createId());
        qqchTotalDemandTimeCount.setCreateUser(SecurityUtils.getUserName());
        qqchTotalDemandTimeCount.setCreateTime(DateUtils.getNowDate());
        return qqchTotalDemandTimeCountMapper.insertQqchTotalDemandTimeCount(qqchTotalDemandTimeCount);
    }

    @Transactional
    public int insertQqchTotalDemandTimeCountList(List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountList) {
        for (QqchTotalDemandTimeCount qqchTotalDemandTimeCount : qqchTotalDemandTimeCountList) {
            qqchTotalDemandTimeCount.setId(IdWorker.createId());
            qqchTotalDemandTimeCount.setCreateUser(SecurityUtils.getUserName());
            qqchTotalDemandTimeCount.setCreateTime(DateUtils.getNowDate());
        }
        return qqchTotalDemandTimeCountMapper.insertQqchTotalDemandTimeCountList(qqchTotalDemandTimeCountList);
    }

    @Transactional
    public int updateQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount) {
        qqchTotalDemandTimeCount.setUpdateUser(SecurityUtils.getUserName());
        qqchTotalDemandTimeCount.setUpdateTime(DateUtils.getNowDate());
        return qqchTotalDemandTimeCountMapper.updateQqchTotalDemandTimeCount(qqchTotalDemandTimeCount);
    }

            @Transactional
        public int updateQqchTotalDemandTimeCountList(List<QqchTotalDemandTimeCount> qqchTotalDemandTimeCountList) {
            for (QqchTotalDemandTimeCount qqchTotalDemandTimeCount : qqchTotalDemandTimeCountList) {
                qqchTotalDemandTimeCount.setUpdateUser(SecurityUtils.getUserName());
                qqchTotalDemandTimeCount.setUpdateTime(DateUtils.getNowDate());
            }
            return qqchTotalDemandTimeCountMapper.updateQqchTotalDemandTimeCountList(qqchTotalDemandTimeCountList);
        }
    
    @Transactional
    public int deleteQqchTotalDemandTimeCount(QqchTotalDemandTimeCount qqchTotalDemandTimeCount) {
        qqchTotalDemandTimeCount.setUpdateUser(SecurityUtils.getUserName());
        qqchTotalDemandTimeCount.setUpdateTime(DateUtils.getNowDate());
        return qqchTotalDemandTimeCountMapper.deleteQqchTotalDemandTimeCount(qqchTotalDemandTimeCount);
    }

            @Transactional
        public int deleteQqchTotalDemandTimeCountByPks(List<Long> qqchTotalDemandTimeCountPkList) {
            return qqchTotalDemandTimeCountMapper.deleteQqchTotalDemandTimeCountByPks(qqchTotalDemandTimeCountPkList);
        }
    }
