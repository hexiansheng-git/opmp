package com.hhwy.pm.qqch.preparation.survey.document.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.survey.document.domain.QqchBlueprintManageInventory;
import com.hhwy.pm.qqch.preparation.survey.document.mapper.QqchBlueprintManageInventoryMapper;
import com.hhwy.pm.qqch.preparation.survey.document.service.IQqchBlueprintManageInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author han
 * @date 2023-07-13 11:40:34
 * @remark 勘察设计图纸管理清单
 */
@Service
public class QqchBlueprintManageInventoryServiceImpl implements IQqchBlueprintManageInventoryService {

    @Autowired
    private QqchBlueprintManageInventoryMapper qqchBlueprintManageInventoryMapper;


    public QqchBlueprintManageInventory getQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory) {
        return qqchBlueprintManageInventoryMapper.getQqchBlueprintManageInventory(qqchBlueprintManageInventory);
    }

    public List<QqchBlueprintManageInventory> getQqchBlueprintManageInventoryList(QqchBlueprintManageInventory qqchBlueprintManageInventory) {
        return qqchBlueprintManageInventoryMapper.getQqchBlueprintManageInventoryList(qqchBlueprintManageInventory);
    }

    @Transactional
    public int insertQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory) {
        qqchBlueprintManageInventory.setId(IdWorker.createId());
        qqchBlueprintManageInventory.setCreateUser(SecurityUtils.getUserName());
        qqchBlueprintManageInventory.setCreateTime(DateUtils.getNowDate());
        return qqchBlueprintManageInventoryMapper.insertQqchBlueprintManageInventory(qqchBlueprintManageInventory);
    }

    @Transactional
    public int insertQqchBlueprintManageInventoryList(List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList) {
        for (QqchBlueprintManageInventory qqchBlueprintManageInventory : qqchBlueprintManageInventoryList) {
            qqchBlueprintManageInventory.setId(IdWorker.createId());
            qqchBlueprintManageInventory.setCreateUser(SecurityUtils.getUserName());
            qqchBlueprintManageInventory.setCreateTime(DateUtils.getNowDate());
        }
        return qqchBlueprintManageInventoryMapper.insertQqchBlueprintManageInventoryList(qqchBlueprintManageInventoryList);
    }

    @Transactional
    public int updateQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory) {
        qqchBlueprintManageInventory.setUpdateUser(SecurityUtils.getUserName());
        qqchBlueprintManageInventory.setUpdateTime(DateUtils.getNowDate());
        return qqchBlueprintManageInventoryMapper.updateQqchBlueprintManageInventory(qqchBlueprintManageInventory);
    }

    @Transactional
    public int updateQqchBlueprintManageInventoryList(List<QqchBlueprintManageInventory> qqchBlueprintManageInventoryList) {
        for (QqchBlueprintManageInventory qqchBlueprintManageInventory : qqchBlueprintManageInventoryList) {
            qqchBlueprintManageInventory.setUpdateUser(SecurityUtils.getUserName());
            qqchBlueprintManageInventory.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchBlueprintManageInventoryMapper.updateQqchBlueprintManageInventoryList(qqchBlueprintManageInventoryList);
    }

    @Transactional
    public int deleteQqchBlueprintManageInventory(QqchBlueprintManageInventory qqchBlueprintManageInventory) {
        qqchBlueprintManageInventory.setUpdateUser(SecurityUtils.getUserName());
        qqchBlueprintManageInventory.setUpdateTime(DateUtils.getNowDate());
        return qqchBlueprintManageInventoryMapper.deleteQqchBlueprintManageInventory(qqchBlueprintManageInventory);
    }

    @Transactional
    public int deleteQqchBlueprintManageInventoryByPks(List<Long> qqchBlueprintManageInventoryPkList) {
        return qqchBlueprintManageInventoryMapper.deleteQqchBlueprintManageInventoryByPks(qqchBlueprintManageInventoryPkList);
    }
}
