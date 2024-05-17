package com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.service;

import java.util.List;

import com.hhwy.sd.designOptimize.kcsjDesignOptimize.domain.KcsjDesignOptimize;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.domain.KcsjDesignOptimizeItem;

/**
 * @author cjh
 * @date 2024-02-04 13:31:49
 * @remark
 */
public interface IKcsjDesignOptimizeItemService {

    KcsjDesignOptimizeItem getKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem);

    List<KcsjDesignOptimizeItem> getKcsjDesignOptimizeItemList(KcsjDesignOptimizeItem kcsjDesignOptimizeItem);

    int insertKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem);

    int insertKcsjDesignOptimizeItemList(List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList);

    int updateKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem);

    int updateKcsjDesignOptimizeItemList(Long optimizeId, List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList, KcsjDesignOptimize main);

    int deleteKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem);

    int deleteKcsjDesignOptimizeItem(Long optimizeId);

    int deleteKcsjDesignOptimizeItemByPks(List<Long> kcsjDesignOptimizeItemPkList);
}
