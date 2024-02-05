package com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sd.designOptimize.kcsjDesignOptimizeItem.domain.KcsjDesignOptimizeItem;

/**
 * @author cjh
 * @date 2024-02-04 13:31:49
 * @remark
 */
public interface KcsjDesignOptimizeItemMapper {

    KcsjDesignOptimizeItem getKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem);

    List<KcsjDesignOptimizeItem> getKcsjDesignOptimizeItemList(KcsjDesignOptimizeItem kcsjDesignOptimizeItem);

    int insertKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem);

    int insertKcsjDesignOptimizeItemList(@Param("kcsjDesignOptimizeItemList") List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList);

    int updateKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem);

    int updateKcsjDesignOptimizeItemList(@Param("list") List<KcsjDesignOptimizeItem> kcsjDesignOptimizeItemList);

    int deleteKcsjDesignOptimizeItem(KcsjDesignOptimizeItem kcsjDesignOptimizeItem);

    int deleteKcsjDesignOptimizeItemByPks(@Param("kcsjDesignOptimizeItemPkList") List<Long> kcsjDesignOptimizeItemPkList);

    int deleteKcsjDesignOptimizeItemByOptimizeId(@Param("optimizeId") Long optimizeId);
}
