package com.hhwy.pm.qqch.preparation.survey.inventory.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchCompleteDesignHandover;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-11 17:11:14
 * @remark 完整设计交接情况
 */
@Repository
public interface QqchCompleteDesignHandoverMapper {

    QqchCompleteDesignHandover getQqchCompleteDesignHandover(QqchCompleteDesignHandover qqchCompleteDesignHandover);

    List<QqchCompleteDesignHandover> getQqchCompleteDesignHandoverList(QqchCompleteDesignHandover qqchCompleteDesignHandover);

    int insertQqchCompleteDesignHandover(QqchCompleteDesignHandover qqchCompleteDesignHandover);

    int insertQqchCompleteDesignHandoverList(@Param("qqchCompleteDesignHandoverList") List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverList);

    int updateQqchCompleteDesignHandover(QqchCompleteDesignHandover qqchCompleteDesignHandover);

    int updateQqchCompleteDesignHandoverList(@Param("list") List<QqchCompleteDesignHandover> qqchCompleteDesignHandoverList);

    int deleteQqchCompleteDesignHandover(QqchCompleteDesignHandover qqchCompleteDesignHandover);

    int deleteQqchCompleteDesignHandoverByPks(@Param("qqchCompleteDesignHandoverPkList") List<Long> qqchCompleteDesignHandoverPkList);
}
