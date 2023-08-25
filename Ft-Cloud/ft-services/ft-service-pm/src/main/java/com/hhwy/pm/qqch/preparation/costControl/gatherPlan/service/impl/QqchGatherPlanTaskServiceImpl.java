package com.hhwy.pm.qqch.preparation.costControl.gatherPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.domain.QqchGatherPlanTask;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.mapper.QqchGatherPlanTaskMapper;
import com.hhwy.pm.qqch.preparation.costControl.gatherPlan.service.IQqchGatherPlanTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-08-23 11:10:35
 * @remark
 */
@Service
public class QqchGatherPlanTaskServiceImpl implements IQqchGatherPlanTaskService {

    @Autowired
    private QqchGatherPlanTaskMapper qqchGatherPlanTaskMapper;


    public QqchGatherPlanTask getQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask) {
        return qqchGatherPlanTaskMapper.getQqchGatherPlanTask(qqchGatherPlanTask);
    }

    public List<QqchGatherPlanTask> getQqchGatherPlanTaskList(QqchGatherPlanTask qqchGatherPlanTask) {
        return qqchGatherPlanTaskMapper.getQqchGatherPlanTaskList(qqchGatherPlanTask);
    }

    @Transactional
    public int insertQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask) {
        qqchGatherPlanTask.setCreateUser(SecurityUtils.getUserName());
        qqchGatherPlanTask.setCreateTime(DateUtils.getNowDate());
        return qqchGatherPlanTaskMapper.insertQqchGatherPlanTask(qqchGatherPlanTask);
    }

    @Transactional
    public int insertQqchGatherPlanTaskList(List<QqchGatherPlanTask> qqchGatherPlanTaskList) {
        for (QqchGatherPlanTask qqchGatherPlanTask : qqchGatherPlanTaskList) {
            qqchGatherPlanTask.setCreateUser(SecurityUtils.getUserName());
            qqchGatherPlanTask.setCreateTime(DateUtils.getNowDate());
        }
        return qqchGatherPlanTaskMapper.insertQqchGatherPlanTaskList(qqchGatherPlanTaskList);
    }

    @Transactional
    public int updateQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask) {
        qqchGatherPlanTask.setUpdateUser(SecurityUtils.getUserName());
        qqchGatherPlanTask.setUpdateTime(DateUtils.getNowDate());
        return qqchGatherPlanTaskMapper.updateQqchGatherPlanTask(qqchGatherPlanTask);
    }

    @Transactional
    public int updateQqchGatherPlanTaskList(List<QqchGatherPlanTask> qqchGatherPlanTaskList) {
        for (QqchGatherPlanTask qqchGatherPlanTask : qqchGatherPlanTaskList) {
            qqchGatherPlanTask.setUpdateUser(SecurityUtils.getUserName());
            qqchGatherPlanTask.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchGatherPlanTaskMapper.updateQqchGatherPlanTaskList(qqchGatherPlanTaskList);
    }

    @Transactional
    public int deleteQqchGatherPlanTask(QqchGatherPlanTask qqchGatherPlanTask) {
        qqchGatherPlanTask.setUpdateUser(SecurityUtils.getUserName());
        qqchGatherPlanTask.setUpdateTime(DateUtils.getNowDate());
        return qqchGatherPlanTaskMapper.deleteQqchGatherPlanTask(qqchGatherPlanTask);
    }

    @Transactional
    public int deleteQqchGatherPlanTaskByPks(List<Long> qqchGatherPlanTaskPkList) {
        return qqchGatherPlanTaskMapper.deleteQqchGatherPlanTaskByPks(qqchGatherPlanTaskPkList);
    }
}
