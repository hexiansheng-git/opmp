package com.hhwy.pm.qqch.sgch.sche.mapper;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheAnalyse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:46
 * @remark
 */
public interface QqchScheAnalyseMapper {

    QqchScheAnalyse getQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse);

    List<QqchScheAnalyse> getQqchScheAnalyseList(QqchScheAnalyse qqchScheAnalyse);

    int insertQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse);

    int insertQqchScheAnalyseList(@Param("qqchScheAnalyseList") List<QqchScheAnalyse> qqchScheAnalyseList);

    int updateQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse);

    int updateQqchScheAnalyseList(@Param("qqchScheAnalyseList") List<QqchScheAnalyse> qqchScheAnalyseList);

    int deleteQqchScheAnalyse(QqchScheAnalyse qqchScheAnalyse);

    int deleteQqchScheAnalyseByPks(@Param("qqchScheAnalysePkList") List<Long> qqchScheAnalysePkList);
}
