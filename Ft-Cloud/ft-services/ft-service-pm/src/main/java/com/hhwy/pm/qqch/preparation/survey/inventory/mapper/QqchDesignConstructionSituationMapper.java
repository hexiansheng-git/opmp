package com.hhwy.pm.qqch.preparation.survey.inventory.mapper;

import java.util.List;

import com.hhwy.pm.qqch.preparation.survey.inventory.domain.QqchDesignConstructionSituation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author han
 * @date 2023-07-13 10:09:03
 * @remark 边设计边施工情况
 */
@Repository
public interface QqchDesignConstructionSituationMapper {

    QqchDesignConstructionSituation getQqchDesignConstructionSituation(QqchDesignConstructionSituation qqchDesignConstructionSituation);

    List<QqchDesignConstructionSituation> getQqchDesignConstructionSituationList(QqchDesignConstructionSituation qqchDesignConstructionSituation);

    int insertQqchDesignConstructionSituation(QqchDesignConstructionSituation qqchDesignConstructionSituation);

    int insertQqchDesignConstructionSituationList(@Param("qqchDesignConstructionSituationList") List<QqchDesignConstructionSituation> qqchDesignConstructionSituationList);

    int updateQqchDesignConstructionSituation(QqchDesignConstructionSituation qqchDesignConstructionSituation);

    int updateQqchDesignConstructionSituationList(@Param("list") List<QqchDesignConstructionSituation> qqchDesignConstructionSituationList);

    int deleteQqchDesignConstructionSituation(QqchDesignConstructionSituation qqchDesignConstructionSituation);

    int deleteQqchDesignConstructionSituationByPks(@Param("qqchDesignConstructionSituationPkList") List<Long> qqchDesignConstructionSituationPkList);
}
