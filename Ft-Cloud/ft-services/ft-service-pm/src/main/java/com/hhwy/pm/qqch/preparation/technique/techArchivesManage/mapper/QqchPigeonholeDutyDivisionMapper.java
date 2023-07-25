package com.hhwy.pm.qqch.preparation.technique.techArchivesManage.mapper;

import com.hhwy.pm.qqch.preparation.technique.techArchivesManage.domain.QqchPigeonholeDutyDivision;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author han
 * @date 2023-07-25 10:48:27
 * @remark 技术档案归档责任分工
 */
@Repository
public interface QqchPigeonholeDutyDivisionMapper {

    QqchPigeonholeDutyDivision getQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision);

    List<QqchPigeonholeDutyDivision> getQqchPigeonholeDutyDivisionList(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision);

    int insertQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision);

    int insertQqchPigeonholeDutyDivisionList(@Param("qqchPigeonholeDutyDivisionList") List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList);

    int updateQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision);

    int updateQqchPigeonholeDutyDivisionList(@Param("list") List<QqchPigeonholeDutyDivision> qqchPigeonholeDutyDivisionList);

    int deleteQqchPigeonholeDutyDivision(QqchPigeonholeDutyDivision qqchPigeonholeDutyDivision);

    int deleteQqchPigeonholeDutyDivisionByPks(@Param("qqchPigeonholeDutyDivisionPkList") List<Long> qqchPigeonholeDutyDivisionPkList);
}
