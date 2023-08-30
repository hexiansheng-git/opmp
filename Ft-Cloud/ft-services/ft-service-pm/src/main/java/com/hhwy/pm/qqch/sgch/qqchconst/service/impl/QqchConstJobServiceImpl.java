package com.hhwy.pm.qqch.sgch.qqchconst.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstJob;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstJobMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstJobService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 16:08:17
 * @remark
 */
@Service
public class QqchConstJobServiceImpl implements IQqchConstJobService {

    private static final String TN = "qqch_const_job";
    @Autowired
    private QqchConstJobMapper qqchConstJobMapper;


    public QqchConstJob getQqchConstJob(QqchConstJob qqchConstJob) {
        return qqchConstJobMapper.getQqchConstJob(qqchConstJob);
    }

    public List<QqchConstJob> getQqchConstJobList(QqchConstJob qqchConstJob) {
        return qqchConstJobMapper.getQqchConstJobList(qqchConstJob);
    }

    @Transactional
    public int insertQqchConstJob(QqchConstJob qqchConstJob) {
        qqchConstJob.setId(IdWorker.createId());
        qqchConstJob.setCreateUser(SecurityUtils.getUserName());
        qqchConstJob.setCreateTime(DateUtils.getNowDate());
        return qqchConstJobMapper.insertQqchConstJob(qqchConstJob);
    }

    @Transactional
    public int insertQqchConstJobList(List<QqchConstJob> qqchConstJobList) {
        for (QqchConstJob qqchConstJob : qqchConstJobList) {
            qqchConstJob.setId(IdWorker.createId());
            qqchConstJob.setCreateUser(SecurityUtils.getUserName());
            qqchConstJob.setCreateTime(DateUtils.getNowDate());
        }
        return qqchConstJobMapper.insertQqchConstJobList(qqchConstJobList);
    }

    @Transactional
    public int updateQqchConstJob(QqchConstJob qqchConstJob) {
        qqchConstJob.setUpdateUser(SecurityUtils.getUserName());
        qqchConstJob.setUpdateTime(DateUtils.getNowDate());
        return qqchConstJobMapper.updateQqchConstJob(qqchConstJob);
    }

    @Transactional
    public int updateQqchConstJobList(List<QqchConstJob> qqchConstJobList) {
        for (QqchConstJob qqchConstJob : qqchConstJobList) {
            qqchConstJob.setUpdateUser(SecurityUtils.getUserName());
            qqchConstJob.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchConstJobMapper.updateQqchConstJobList(qqchConstJobList);
    }

    @Transactional
    public int deleteQqchConstJob(QqchConstJob qqchConstJob) {
        qqchConstJob.setUpdateUser(SecurityUtils.getUserName());
        qqchConstJob.setUpdateTime(DateUtils.getNowDate());
        return qqchConstJobMapper.deleteQqchConstJob(qqchConstJob);
    }

    @Transactional
    public int deleteQqchConstJobByPks(List<Long> qqchConstJobPkList) {
        return qqchConstJobMapper.deleteQqchConstJobByPks(qqchConstJobPkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    public void saveList(List<QqchConstJob> iJobList) {
        if (CollectionUtils.isEmpty(iJobList)) return;
        this.qqchConstJobMapper.insertQqchConstJobList(iJobList);

    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchConstJob> list(QqchConstJob dealListDto) {
        return this.getQqchConstJobList(dealListDto);
    }
}
