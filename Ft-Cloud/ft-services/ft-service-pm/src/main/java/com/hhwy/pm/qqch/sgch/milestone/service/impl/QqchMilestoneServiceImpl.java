package com.hhwy.pm.qqch.sgch.milestone.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.sgch.milestone.domain.QqchMilestone;
import com.hhwy.pm.qqch.sgch.milestone.mapper.QqchMilestoneMapper;
import com.hhwy.pm.qqch.sgch.milestone.service.IQqchMilestoneService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-03 11:18:27
 * @remark
 */
@Service
public class QqchMilestoneServiceImpl implements IQqchMilestoneService {

    private final static String TN = "qqch_milestone";

    @Autowired
    private QqchMilestoneMapper qqchMilestoneMapper;


    public QqchMilestone getQqchMilestone(QqchMilestone qqchMilestone) {
        return qqchMilestoneMapper.getQqchMilestone(qqchMilestone);
    }

    public List<QqchMilestone> getQqchMilestoneList(QqchMilestone qqchMilestone) {
        return qqchMilestoneMapper.getQqchMilestoneList(qqchMilestone);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMilestone(QqchMilestone qqchMilestone) {
        qqchMilestone.setId(IdWorker.createId());
        qqchMilestone.setCreateUser(SecurityUtils.getUserName());
        qqchMilestone.setCreateTime(DateUtils.getNowDate());
        return qqchMilestoneMapper.insertQqchMilestone(qqchMilestone);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertQqchMilestoneList(List<QqchMilestone> qqchMilestoneList) {
        for (QqchMilestone qqchMilestone : qqchMilestoneList) {
            qqchMilestone.setId(IdWorker.createId());
            qqchMilestone.setCreateUser(SecurityUtils.getUserName());
            qqchMilestone.setCreateTime(DateUtils.getNowDate());
        }
        return qqchMilestoneMapper.insertQqchMilestoneList(qqchMilestoneList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMilestone(QqchMilestone qqchMilestone) {
        qqchMilestone.setUpdateUser(SecurityUtils.getUserName());
        qqchMilestone.setUpdateTime(DateUtils.getNowDate());
        return qqchMilestoneMapper.updateQqchMilestone(qqchMilestone);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateQqchMilestoneList(List<QqchMilestone> qqchMilestoneList) {
        for (QqchMilestone qqchMilestone : qqchMilestoneList) {
            qqchMilestone.setUpdateUser(SecurityUtils.getUserName());
            qqchMilestone.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchMilestoneMapper.updateQqchMilestoneList(qqchMilestoneList);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMilestone(QqchMilestone qqchMilestone) {
        qqchMilestone.setUpdateUser(SecurityUtils.getUserName());
        qqchMilestone.setUpdateTime(DateUtils.getNowDate());
        return qqchMilestoneMapper.deleteQqchMilestone(qqchMilestone);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteQqchMilestoneByPks(List<Long> qqchMilestonePkList) {
        return qqchMilestoneMapper.deleteQqchMilestoneByPks(qqchMilestonePkList);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.LIST, tableName = TN)
    public List<QqchMilestone> list(QqchMilestone qqchMilestoneParam) {
        return this.qqchMilestoneMapper.getQqchMilestoneList(qqchMilestoneParam);
    }

    @Override
    @CompileAspect(type = CompileOptEnum.SAVE_LIST, tableName = TN)
    @Transactional(rollbackFor = Exception.class)
    public void save(List<QqchMilestone> list) {
        for (QqchMilestone qqchMilestone : list) {
            qqchMilestone.setId(IdWorker.createId());
        }
        this.qqchMilestoneMapper.insertQqchMilestoneList(list);
    }
}
