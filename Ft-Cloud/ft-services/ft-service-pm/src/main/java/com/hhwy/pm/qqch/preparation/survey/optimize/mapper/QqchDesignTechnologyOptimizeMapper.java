package com.hhwy.pm.qqch.preparation.survey.optimize.mapper;

import java.util.List;
import com.hhwy.pm.qqch.preparation.survey.optimize.domain.QqchDesignTechnologyOptimize;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-07 18:35:48
 * @remark 设计技术优化要点
 */
@Repository
public interface QqchDesignTechnologyOptimizeMapper {

    QqchDesignTechnologyOptimize getQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize);

    List<QqchDesignTechnologyOptimize> getQqchDesignTechnologyOptimizeList(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize);

    int insertQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize);

    int insertQqchDesignTechnologyOptimizeList(@Param("qqchDesignTechnologyOptimizeList") List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList);

    int updateQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize);

    int updateQqchDesignTechnologyOptimizeList(@Param("list") List<QqchDesignTechnologyOptimize> qqchDesignTechnologyOptimizeList);

    int deleteQqchDesignTechnologyOptimize(QqchDesignTechnologyOptimize qqchDesignTechnologyOptimize);

    int deleteQqchDesignTechnologyOptimizeByPks(@Param("qqchDesignTechnologyOptimizePkList") List<Long> qqchDesignTechnologyOptimizePkList);
}
