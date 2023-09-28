package com.hhwy.pm.qqch.sgch.qqchconst.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.constant.PmConstant;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.qqchconst.domain.QqchConstJob;
import com.hhwy.pm.qqch.sgch.qqchconst.mapper.QqchConstJobMapper;
import com.hhwy.pm.qqch.sgch.qqchconst.service.IQqchConstJobService;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN, delFlag = false)
    public void saveList(List<QqchConstJob> paramJobList) {
        QqchConstJob qqchConstJob = paramJobList.get(0);
        if (paramJobList.size() == 1 && PmConstant.MINUS_ONE.equals(qqchConstJob.getSubmitFlag())) {
            // 如果前端将所有数据删除了 这边根据version删除数据
            this.qqchConstJobMapper.deleteByVersion(qqchConstJob.getVersion());
            return;
        }
        BigDecimal version = paramJobList.get(0).getVersion();

        // 先将当前版本的所有的数据查询出来 跟前端传入的数据进行比较 交集进行更新 数据库有的前端没有的,删除 前端有的数据库没有的,新增
        QqchConstJob where = new QqchConstJob();
        where.setVersion(version);
        where.setDelFlag("0");
        List<QqchConstJob> dbJobList = this.qqchConstJobMapper.getQqchConstJobList(where);

        // 数据库中的id
        List<Long> dbIdList = dbJobList.stream().map(QqchConstJob::getId).collect(Collectors.toList());
        // 前端的id
        List<Long> paramIdList = paramJobList.stream().map(QqchConstJob::getId).collect(Collectors.toList());


        // 数据库中有 但是前端没有的数据 删掉
        List<Long> delIdList = dbIdList.stream().filter(item -> !paramIdList.contains(item)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(delIdList))  this.qqchConstJobMapper.deleteQqchConstJobByPks(delIdList);

        // 前端有 数据库中没有 新增
        List<QqchConstJob> insertDataList = paramJobList.stream().filter(item -> item.getId() == null || !dbIdList.contains(item.getId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(insertDataList))  this.qqchConstJobMapper.insertQqchConstJobList(insertDataList);

        // 前端和后台都有的数据 更新
        List<QqchConstJob> updateDataList = paramJobList.stream().filter(item -> dbIdList.contains(item.getId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(updateDataList)) this.qqchConstJobMapper.updateQqchConstJobList(updateDataList);


    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchConstJob> list(QqchConstJob dealListDto) {
        return this.getQqchConstJobList(dealListDto);
    }
}
