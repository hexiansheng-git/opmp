package com.hhwy.sd.designOptimize.kcsjDesignOptimize.service;

import java.util.List;

import com.hhwy.sd.designOptimize.kcsjDesignOptimize.domain.KcsjDesignOptimize;

/**
 * @author cjh
 * @date 2024-02-04 13:31:39
 * @remark
 */
public interface IKcsjDesignOptimizeService {

    KcsjDesignOptimize getKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize);

    List<KcsjDesignOptimize> getKcsjDesignOptimizeList(KcsjDesignOptimize kcsjDesignOptimize);

    int insertKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize);

    int insertKcsjDesignOptimizeList(List<KcsjDesignOptimize> kcsjDesignOptimizeList);

    int updateKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize);

    int updateKcsjDesignOptimizeList(List<KcsjDesignOptimize> kcsjDesignOptimizeList);

    int deleteKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize);

    int deleteKcsjDesignOptimizeByPks(List<Long> kcsjDesignOptimizePkList);
}
