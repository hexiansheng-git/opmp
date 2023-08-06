package com.hhwy.pm.qqch.preparation.measureexp.tech.mapper;

import com.hhwy.pm.qqch.preparation.measureexp.tech.domain.QqchMeasureExpTech;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-04 16:09:57
 * @remark 3.6.3测量技术方案计划、3.7.3试验方案计划
 */
public interface QqchMeasureExpTechMapper {

    QqchMeasureExpTech getQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

    List<QqchMeasureExpTech> getQqchMeasureExpTechList(QqchMeasureExpTech qqchMeasureExpTech);

    int insertQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

    int insertQqchMeasureExpTechList(@Param("qqchMeasureExpTechList") List<QqchMeasureExpTech> qqchMeasureExpTechList);

    int updateQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

    int updateQqchMeasureExpTechList(@Param("list") List<QqchMeasureExpTech> qqchMeasureExpTechList);

    int deleteQqchMeasureExpTech(QqchMeasureExpTech qqchMeasureExpTech);

    int deleteQqchMeasureExpTechByPks(@Param("qqchMeasureExpTechPkList") List<Long> qqchMeasureExpTechPkList);
}
