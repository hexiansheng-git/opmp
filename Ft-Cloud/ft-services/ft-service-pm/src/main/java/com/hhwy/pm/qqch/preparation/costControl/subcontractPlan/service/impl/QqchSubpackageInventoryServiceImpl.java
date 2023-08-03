package com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.module.service.impl.QqchModuleConfirmCaseServiceImpl;
import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.domain.QqchSubpackageInventory;
import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.mapper.QqchSubpackageInventoryMapper;
import com.hhwy.pm.qqch.preparation.costControl.subcontractPlan.service.IQqchSubpackageInventoryService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-08-03 13:35:47
 * @remark
 */
@Service
public class QqchSubpackageInventoryServiceImpl implements IQqchSubpackageInventoryService {

    @Autowired
    private QqchSubpackageInventoryMapper qqchSubpackageInventoryMapper;

    @Autowired
    private QqchModuleConfirmCaseServiceImpl qqchModuleConfirmCaseService;


    public QqchSubpackageInventory getQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory) {
        return qqchSubpackageInventoryMapper.getQqchSubpackageInventory(qqchSubpackageInventory);
    }

    public List<QqchSubpackageInventory> getQqchSubpackageInventoryList(QqchSubpackageInventory qqchSubpackageInventory) {
        return qqchSubpackageInventoryMapper.getQqchSubpackageInventoryList(qqchSubpackageInventory);
    }

    @Transactional
    public int insertQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory) {
        qqchSubpackageInventory.setId(IdWorker.createId());
        qqchSubpackageInventory.setCreateUser(SecurityUtils.getUserName());
        qqchSubpackageInventory.setCreateTime(DateUtils.getNowDate());
        return qqchSubpackageInventoryMapper.insertQqchSubpackageInventory(qqchSubpackageInventory);
    }

    @Transactional
    public int insertQqchSubpackageInventoryList(List<QqchSubpackageInventory> qqchSubpackageInventoryList) {
        for (QqchSubpackageInventory qqchSubpackageInventory : qqchSubpackageInventoryList) {
            qqchSubpackageInventory.setId(IdWorker.createId());
            qqchSubpackageInventory.setCreateUser(SecurityUtils.getUserName());
            qqchSubpackageInventory.setCreateTime(DateUtils.getNowDate());
        }
        return qqchSubpackageInventoryMapper.insertQqchSubpackageInventoryList(qqchSubpackageInventoryList);
    }

    @Transactional
    public int updateQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory) {
        qqchSubpackageInventory.setUpdateUser(SecurityUtils.getUserName());
        qqchSubpackageInventory.setUpdateTime(DateUtils.getNowDate());
        return qqchSubpackageInventoryMapper.updateQqchSubpackageInventory(qqchSubpackageInventory);
    }

    @Transactional
    public int updateQqchSubpackageInventoryList(List<QqchSubpackageInventory> qqchSubpackageInventoryList) {
        for (QqchSubpackageInventory qqchSubpackageInventory : qqchSubpackageInventoryList) {
            qqchSubpackageInventory.setUpdateUser(SecurityUtils.getUserName());
            qqchSubpackageInventory.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchSubpackageInventoryMapper.updateQqchSubpackageInventoryList(qqchSubpackageInventoryList);
    }

    @Transactional
    public int deleteQqchSubpackageInventory(QqchSubpackageInventory qqchSubpackageInventory) {
        qqchSubpackageInventory.setUpdateUser(SecurityUtils.getUserName());
        qqchSubpackageInventory.setUpdateTime(DateUtils.getNowDate());
        return qqchSubpackageInventoryMapper.deleteQqchSubpackageInventory(qqchSubpackageInventory);
    }

    @Transactional
    public int deleteQqchSubpackageInventoryByPks(List<Long> qqchSubpackageInventoryPkList) {
        return qqchSubpackageInventoryMapper.deleteQqchSubpackageInventoryByPks(qqchSubpackageInventoryPkList);
    }
}
