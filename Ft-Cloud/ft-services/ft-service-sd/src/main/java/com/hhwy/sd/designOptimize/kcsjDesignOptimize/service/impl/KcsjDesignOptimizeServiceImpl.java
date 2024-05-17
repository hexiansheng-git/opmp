package com.hhwy.sd.designOptimize.kcsjDesignOptimize.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.domain.KcsjDesignOptimize;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.mapper.KcsjDesignOptimizeMapper;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.service.IKcsjDesignOptimizeService;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.domain.KcsjDesignOptimizeItem;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.service.IKcsjDesignOptimizeItemService;
import com.hhwy.sd.sync.mq.ISysSyncInfoService4Sd;
import com.hhwy.utils.idworker.IdWorker;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private ISysSyncInfoService4Sd sysSyncInfoService4Sd;

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
        boolean isNew = id == null;
        if(isNew)
            id = IdWorker.createId();
        List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList = kcsjDesignOptimize.getKcsjDesignOptimizeItemList();
        if(CollectionUtils.isNotEmpty(kcsjDesignOptimizeItemList)) {
            kcsjDesignOptimizeItemService.updateKcsjDesignOptimizeItemList(id, kcsjDesignOptimizeItemList,kcsjDesignOptimize);
        }
        if(isNew) {
            kcsjDesignOptimize.setId(id);
            kcsjDesignOptimize.setAddOrUpdate("add");
            kcsjDesignOptimize.setCreateUser(SecurityUtils.getSysUser().getNickName());
            kcsjDesignOptimize.setCreateTime(DateUtils.getNowDate());
            i = kcsjDesignOptimizeMapper.insertKcsjDesignOptimize(kcsjDesignOptimize);
        } else {
            kcsjDesignOptimize.setAddOrUpdate("update");
            kcsjDesignOptimize.setUpdateUser(SecurityUtils.getSysUser().getNickName());
            kcsjDesignOptimize.setUpdateTime(DateUtils.getNowDate());
            i = kcsjDesignOptimizeMapper.updateKcsjDesignOptimize(kcsjDesignOptimize);
        }
        this.pushData(kcsjDesignOptimize);
        return i;
    }

    public void pushData(KcsjDesignOptimize kcsjDesignOptimize){
        Long id = kcsjDesignOptimize.getId();
        KcsjDesignOptimizeItem item = new KcsjDesignOptimizeItem();
        item.setOptimizeId(id);
        List<KcsjDesignOptimizeItem> itemList = kcsjDesignOptimizeItemService.getKcsjDesignOptimizeItemList(item);
        kcsjDesignOptimize.setKcsjDesignOptimizeItemList(itemList);

        sysSyncInfoService4Sd.pushDesignOptimize(kcsjDesignOptimize);
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
        int i = kcsjDesignOptimizeMapper.deleteKcsjDesignOptimizeByPks(kcsjDesignOptimizePkList);

        sysSyncInfoService4Sd.pushDesignOptimize4Delete(kcsjDesignOptimizePkList);
        return i;
    }

    @Override
    public List<KcsjDesignOptimize> getKcsjDesignOptimizeList4Ids(List<Long> ids) {
        return kcsjDesignOptimizeMapper.getKcsjDesignOptimizeList4Ids(ids);
    }
}
