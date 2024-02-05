package com.hhwy.sd.designOptimize.kcsjDesignOptimize.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sd.designOptimize.kcsjDesignOptimize.domain.KcsjDesignOptimize;

/**
 * @author cjh
 * @date 2024-02-04 13:31:39
 * @remark
 */
public interface KcsjDesignOptimizeMapper {

    KcsjDesignOptimize getKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize);

    List<KcsjDesignOptimize> getKcsjDesignOptimizeList(KcsjDesignOptimize kcsjDesignOptimize);

    int insertKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize);

    int insertKcsjDesignOptimizeList(@Param("kcsjDesignOptimizeList") List<KcsjDesignOptimize> kcsjDesignOptimizeList);

    int updateKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize);

    int updateKcsjDesignOptimizeList(@Param("list") List<KcsjDesignOptimize> kcsjDesignOptimizeList);

    int deleteKcsjDesignOptimize(KcsjDesignOptimize kcsjDesignOptimize);

    int deleteKcsjDesignOptimizeByPks(@Param("kcsjDesignOptimizePkList") List<Long> kcsjDesignOptimizePkList);

    int deleteKcsjDesignOptimizeById(@Param("id") Long id);
}
