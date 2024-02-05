package com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.mapper.KcsjDesignOptimizeItemMapper;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.service.IKcsjDesignOptimizeItemService;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.domain.KcsjDesignOptimizeItem;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2024-02-04 13:31:49
 * @remark
 */
@Service
public class KcsjDesignOptimizeItemServiceImpl implements IKcsjDesignOptimizeItemService {

    @Autowired
    private KcsjDesignOptimizeItemMapper kcsjDesignOptimizeItemMapper;


    public KcsjDesignOptimizeItem getKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem) {
        return kcsjDesignOptimizeItemMapper.getKcsjDesignOptimizeItem(kcsjDesignOptimizeItem);
    }

    public List<KcsjDesignOptimizeItem> getKcsjDesignOptimizeItemList(KcsjDesignOptimizeItem kcsjDesignOptimizeItem) {
        return kcsjDesignOptimizeItemMapper.getKcsjDesignOptimizeItemList(kcsjDesignOptimizeItem);
    }

    @Transactional
    public int insertKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem) {
        kcsjDesignOptimizeItem.setId(IdWorker.createId());
        kcsjDesignOptimizeItem.setCreateUser(SecurityUtils.getUserName());
        kcsjDesignOptimizeItem.setCreateTime(DateUtils.getNowDate());
        return kcsjDesignOptimizeItemMapper.insertKcsjDesignOptimizeItem(kcsjDesignOptimizeItem);
    }

    @Transactional
    public int insertKcsjDesignOptimizeItemList(List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList) {
        for (KcsjDesignOptimizeItem kcsjDesignOptimizeItem : kcsjDesignOptimizeItemList) {
            kcsjDesignOptimizeItem.setId(IdWorker.createId());
            kcsjDesignOptimizeItem.setCreateUser(SecurityUtils.getUserName());
            kcsjDesignOptimizeItem.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjDesignOptimizeItemMapper.insertKcsjDesignOptimizeItemList(kcsjDesignOptimizeItemList);
    }

    @Transactional
    public int updateKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem) {
        kcsjDesignOptimizeItem.setUpdateUser(SecurityUtils.getUserName());
        kcsjDesignOptimizeItem.setUpdateTime(DateUtils.getNowDate());
        return kcsjDesignOptimizeItemMapper.updateKcsjDesignOptimizeItem(kcsjDesignOptimizeItem);
    }

    @Transactional
    public int updateKcsjDesignOptimizeItemList(Long optimizeId, List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList) {
        KcsjDesignOptimizeItem kcsjDesignOptimizeItem = new KcsjDesignOptimizeItem();
        kcsjDesignOptimizeItem.setOptimizeId(optimizeId);
        deleteKcsjDesignOptimizeItem(kcsjDesignOptimizeItem);
        for (KcsjDesignOptimizeItem vo : kcsjDesignOptimizeItemList) {
            vo.setOptimizeId(optimizeId);
        }
        return insertKcsjDesignOptimizeItemList(kcsjDesignOptimizeItemList);
    }

    @Transactional
    public int deleteKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem) {
        return kcsjDesignOptimizeItemMapper.deleteKcsjDesignOptimizeItem(kcsjDesignOptimizeItem);
    }

    @Transactional
    public int deleteKcsjDesignOptimizeItem(Long optimizeId) {
        return kcsjDesignOptimizeItemMapper.deleteKcsjDesignOptimizeItemByOptimizeId(optimizeId);
    }

    @Transactional
    public int deleteKcsjDesignOptimizeItemByPks(List<Long> kcsjDesignOptimizeItemPkList) {
        return kcsjDesignOptimizeItemMapper.deleteKcsjDesignOptimizeItemByPks(kcsjDesignOptimizeItemPkList);
    }
}
