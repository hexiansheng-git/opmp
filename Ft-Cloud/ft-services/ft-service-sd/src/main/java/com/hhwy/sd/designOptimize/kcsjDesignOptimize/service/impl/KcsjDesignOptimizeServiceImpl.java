package com.hhwy.sd.designOptimize.kcsjDesignOptimize.service.impl;

import java.util.List;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.core.text.Convert;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.domain.KcsjDesignOptimizeItem;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.service.IKcsjDesignOptimizeItemService;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.mapper.KcsjDesignOptimizeMapper;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.service.IKcsjDesignOptimizeService;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.domain.KcsjDesignOptimize;
import com.hhwy.utils.idworker.IdWorker;

/**
 * @author cjh
 * @date 2024-02-04 13:31:39
 * @remark
 */
@Service
public class KcsjDesignOptimizeServiceImpl implements IKcsjDesignOptimizeService {

    @Autowired
    private KcsjDesignOptimizeMapper kcsjDesignOptimizeMapper;

    @Autowired
    private IKcsjDesignOptimizeItemService kcsjDesignOptimizeItemService;

    public KcsjDesignOptimize getKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize) {
        KcsjDesignOptimize kcsjDesignOptimize1 = kcsjDesignOptimizeMapper.getKcsjDesignOptimize(kcsjDesignOptimize);
        if(kcsjDesignOptimize1 == null) {
            return kcsjDesignOptimize1;
        }
        Long id = kcsjDesignOptimize1.getId();
        KcsjDesignOptimizeItem kcsjDesignOptimizeItem = new KcsjDesignOptimizeItem();
        kcsjDesignOptimizeItem.setOptimizeId(id);
        List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList = kcsjDesignOptimizeItemService.getKcsjDesignOptimizeItemList(kcsjDesignOptimizeItem);
        kcsjDesignOptimize1.setKcsjDesignOptimizeItemList(kcsjDesignOptimizeItemList);
        return kcsjDesignOptimize1;
    }

    public List<KcsjDesignOptimize> getKcsjDesignOptimizeList(KcsjDesignOptimize kcsjDesignOptimize) {
        return kcsjDesignOptimizeMapper.getKcsjDesignOptimizeList(kcsjDesignOptimize);
    }

    @Transactional
    public int insertKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize) {
        kcsjDesignOptimize.setId(IdWorker.createId());
        kcsjDesignOptimize.setCreateUser(SecurityUtils.getUserName());
        kcsjDesignOptimize.setCreateTime(DateUtils.getNowDate());
        return kcsjDesignOptimizeMapper.insertKcsjDesignOptimize(kcsjDesignOptimize);
    }

    @Transactional
    public int insertKcsjDesignOptimizeList(List<KcsjDesignOptimize> kcsjDesignOptimizeList) {
        for (KcsjDesignOptimize kcsjDesignOptimize : kcsjDesignOptimizeList) {
            kcsjDesignOptimize.setId(IdWorker.createId());
            kcsjDesignOptimize.setCreateUser(SecurityUtils.getUserName());
            kcsjDesignOptimize.setCreateTime(DateUtils.getNowDate());
        }
        return kcsjDesignOptimizeMapper.insertKcsjDesignOptimizeList(kcsjDesignOptimizeList);
    }

    @Transactional
    public int updateKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize) {
        Long id = kcsjDesignOptimize.getId();
        int i = 0;
        if(id == null) {
            id = IdWorker.createId();
            kcsjDesignOptimize.setId(id);
            kcsjDesignOptimize.setCreateUser(SecurityUtils.getSysUser().getNickName());
            kcsjDesignOptimize.setCreateTime(DateUtils.getNowDate());
            i = kcsjDesignOptimizeMapper.insertKcsjDesignOptimize(kcsjDesignOptimize);
        } else {
            kcsjDesignOptimize.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            kcsjDesignOptimize.setUpdateTime(DateUtils.getNowDate());
            i = kcsjDesignOptimizeMapper.updateKcsjDesignOptimize(kcsjDesignOptimize);
        }
        List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList = kcsjDesignOptimize.getKcsjDesignOptimizeItemList();
        if(CollectionUtils.isNotEmpty(kcsjDesignOptimizeItemList)) {
            kcsjDesignOptimizeItemService.updateKcsjDesignOptimizeItemList(id, kcsjDesignOptimizeItemList);
        }
        return i;
    }

    @Transactional
    public int updateKcsjDesignOptimizeList(List<KcsjDesignOptimize> kcsjDesignOptimizeList) {
        for (KcsjDesignOptimize kcsjDesignOptimize : kcsjDesignOptimizeList) {
            kcsjDesignOptimize.setUpdateUser(SecurityUtils.getUserName());
            kcsjDesignOptimize.setUpdateTime(DateUtils.getNowDate());
        }
        return kcsjDesignOptimizeMapper.updateKcsjDesignOptimizeList(kcsjDesignOptimizeList);
    }

    @Transactional
    public int deleteKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize) {
        Long id = kcsjDesignOptimize.getId();
        int i = deleteKcsjDesignOptimizeById(id);
        kcsjDesignOptimizeItemService.deleteKcsjDesignOptimizeItem(id);
        return i;
    }

    public int deleteKcsjDesignOptimizeById(Long id) {
        return kcsjDesignOptimizeMapper.deleteKcsjDesignOptimizeById(id);
    }

    @Transactional
    public int deleteKcsjDesignOptimizeByPks(List<Long> kcsjDesignOptimizePkList) {
        return kcsjDesignOptimizeMapper.deleteKcsjDesignOptimizeByPks(kcsjDesignOptimizePkList);
    }

    @Override
    public List<KcsjDesignOptimize> getKcsjDesignOptimizeList4Ids(List<Long> ids) {
        return kcsjDesignOptimizeMapper.getKcsjDesignOptimizeList4Ids(ids);
    }
}
