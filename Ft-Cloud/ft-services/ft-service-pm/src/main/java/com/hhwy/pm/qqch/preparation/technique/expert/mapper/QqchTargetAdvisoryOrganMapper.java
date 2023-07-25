package com.hhwy.pm.qqch.preparation.technique.expert.mapper;

import com.hhwy.pm.qqch.preparation.technique.expert.domain.QqchTargetAdvisoryOrgan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:56:43
 * @remark
 */
@Repository
public interface QqchTargetAdvisoryOrganMapper {

    QqchTargetAdvisoryOrgan getQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    List<QqchTargetAdvisoryOrgan> getQqchTargetAdvisoryOrganList(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int insertQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int insertQqchTargetAdvisoryOrganList(@Param("qqchTargetAdvisoryOrganList") List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList);

    int updateQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int updateQqchTargetAdvisoryOrganList(@Param("list") List<QqchTargetAdvisoryOrgan> qqchTargetAdvisoryOrganList);

    int deleteQqchTargetAdvisoryOrgan(QqchTargetAdvisoryOrgan qqchTargetAdvisoryOrgan);

    int deleteQqchTargetAdvisoryOrganByPks(@Param("qqchTargetAdvisoryOrganPkList") List<Long> qqchTargetAdvisoryOrganPkList);
}
