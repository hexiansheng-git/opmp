package com.hhwy.pm.qqch.preparation.costControl.masterContract.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.domain.QqchOtherContractItem;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.mapper.QqchOtherContractItemMapper;
import com.hhwy.pm.qqch.preparation.costControl.masterContract.service.IQqchOtherContractItemService;
import com.hhwy.utils.idworker.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author han
 * @date 2023-08-02 11:39:36
 * @remark 其他合同事项分析
 */
@Service
public class QqchOtherContractItemServiceImpl implements IQqchOtherContractItemService {

    @Autowired
    private QqchOtherContractItemMapper qqchOtherContractItemMapper;


    public QqchOtherContractItem getQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem) {
        return qqchOtherContractItemMapper.getQqchOtherContractItem(qqchOtherContractItem);
    }

    public List<QqchOtherContractItem> getQqchOtherContractItemList(QqchOtherContractItem qqchOtherContractItem) {
        return qqchOtherContractItemMapper.getQqchOtherContractItemList(qqchOtherContractItem);
    }

    @Transactional
    public int insertQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem) {
        qqchOtherContractItem.setId(IdWorker.createId());
        qqchOtherContractItem.setCreateUser(SecurityUtils.getUserName());
        qqchOtherContractItem.setCreateTime(DateUtils.getNowDate());
        return qqchOtherContractItemMapper.insertQqchOtherContractItem(qqchOtherContractItem);
    }

    @Transactional
    public int insertQqchOtherContractItemList(List<QqchOtherContractItem> qqchOtherContractItemList) {
        for (QqchOtherContractItem qqchOtherContractItem : qqchOtherContractItemList) {
            qqchOtherContractItem.setId(IdWorker.createId());
            qqchOtherContractItem.setCreateUser(SecurityUtils.getUserName());
            qqchOtherContractItem.setCreateTime(DateUtils.getNowDate());
        }
        return qqchOtherContractItemMapper.insertQqchOtherContractItemList(qqchOtherContractItemList);
    }

    @Transactional
    public int updateQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem) {
        qqchOtherContractItem.setUpdateUser(SecurityUtils.getUserName());
        qqchOtherContractItem.setUpdateTime(DateUtils.getNowDate());
        return qqchOtherContractItemMapper.updateQqchOtherContractItem(qqchOtherContractItem);
    }

    @Transactional
    public int updateQqchOtherContractItemList(List<QqchOtherContractItem> qqchOtherContractItemList) {
        for (QqchOtherContractItem qqchOtherContractItem : qqchOtherContractItemList) {
            qqchOtherContractItem.setUpdateUser(SecurityUtils.getUserName());
            qqchOtherContractItem.setUpdateTime(DateUtils.getNowDate());
        }
        return qqchOtherContractItemMapper.updateQqchOtherContractItemList(qqchOtherContractItemList);
    }

    @Transactional
    public int deleteQqchOtherContractItem(QqchOtherContractItem qqchOtherContractItem) {
        qqchOtherContractItem.setUpdateUser(SecurityUtils.getUserName());
        qqchOtherContractItem.setUpdateTime(DateUtils.getNowDate());
        return qqchOtherContractItemMapper.deleteQqchOtherContractItem(qqchOtherContractItem);
    }

    @Transactional
    public int deleteQqchOtherContractItemByPks(List<Long> qqchOtherContractItemPkList) {
        return qqchOtherContractItemMapper.deleteQqchOtherContractItemByPks(qqchOtherContractItemPkList);
    }
}
