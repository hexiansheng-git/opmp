package com.hhwy.pm.qqch.preparation.quality.problem.mapper;

import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemControl;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:43
 * @remark 9.2.2 质量通病控制措施
 */
public interface QqchQualityProblemControlMapper {

    QqchQualityProblemControl getQqchQualityProblemControl(QqchQualityProblemControl qqchQualityProblemControl);

    List<QqchQualityProblemControl> getQqchQualityProblemControlList(
        QqchQualityProblemControl qqchQualityProblemControl);

    int insertQqchQualityProblemControl(QqchQualityProblemControl qqchQualityProblemControl);

    int insertQqchQualityProblemControlList(
        @Param("qqchQualityProblemControlList") List<QqchQualityProblemControl> qqchQualityProblemControlList);

    int updateQqchQualityProblemControl(QqchQualityProblemControl qqchQualityProblemControl);

    int updateQqchQualityProblemControlList(
        @Param("list") List<QqchQualityProblemControl> qqchQualityProblemControlList);

    int deleteQqchQualityProblemControl(QqchQualityProblemControl qqchQualityProblemControl);

    int deleteQqchQualityProblemControlByPks(
        @Param("qqchQualityProblemControlPkList") List<Long> qqchQualityProblemControlPkList);
}
