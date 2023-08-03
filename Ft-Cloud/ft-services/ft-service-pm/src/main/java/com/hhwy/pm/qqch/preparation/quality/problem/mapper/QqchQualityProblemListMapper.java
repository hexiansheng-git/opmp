package com.hhwy.pm.qqch.preparation.quality.problem.mapper;

import com.hhwy.pm.qqch.preparation.quality.problem.domain.QqchQualityProblemList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-03 14:30:34
 * @remark 9.2.1 质量通病清单
 */
public interface QqchQualityProblemListMapper {

    QqchQualityProblemList getQqchQualityProblemList(QqchQualityProblemList qqchQualityProblemList);

    List<QqchQualityProblemList> getQqchQualityProblemListList(QqchQualityProblemList qqchQualityProblemList);

    int insertQqchQualityProblemList(QqchQualityProblemList qqchQualityProblemList);

    int insertQqchQualityProblemListList(
        @Param("qqchQualityProblemListList") List<QqchQualityProblemList> qqchQualityProblemListList);

    int updateQqchQualityProblemList(QqchQualityProblemList qqchQualityProblemList);

    int updateQqchQualityProblemListList(@Param("list") List<QqchQualityProblemList> qqchQualityProblemListList);

    int deleteQqchQualityProblemList(QqchQualityProblemList qqchQualityProblemList);

    int deleteQqchQualityProblemListByPks(
        @Param("qqchQualityProblemListPkList") List<Long> qqchQualityProblemListPkList);
}
