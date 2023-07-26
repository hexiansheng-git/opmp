package com.hhwy.pm.qqch.preparation.technique.expert.mapper;

import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetExpert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:56:35
 * @remark 内外部目标专家选择
 */
@Repository
public interface QqchTargetExpertMapper {

    QqchTargetExpert getQqchTargetExpert(QqchTargetExpert qqchTargetExpert);

    List<QqchTargetExpert> getQqchTargetExpertList(QqchTargetExpert qqchTargetExpert);

    int insertQqchTargetExpert(QqchTargetExpert qqchTargetExpert);

    int insertQqchTargetExpertList(@Param("qqchTargetExpertList") List<QqchTargetExpert> qqchTargetExpertList);

    int updateQqchTargetExpert(QqchTargetExpert qqchTargetExpert);

    int updateQqchTargetExpertList(@Param("list") List<QqchTargetExpert> qqchTargetExpertList);

    int deleteQqchTargetExpert(QqchTargetExpert qqchTargetExpert);

    int deleteQqchTargetExpertByPks(@Param("qqchTargetExpertPkList") List<Long> qqchTargetExpertPkList);
}
