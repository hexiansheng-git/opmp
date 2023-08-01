package com.hhwy.pm.qqch.sgch.sche.mapper;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheCorr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 15:12:04
 * @remark
 */
public interface QqchScheCorrMapper {

    QqchScheCorr getQqchScheCorr(QqchScheCorr qqchScheCorr);

    List<QqchScheCorr> getQqchScheCorrList(QqchScheCorr qqchScheCorr);

    int insertQqchScheCorr(QqchScheCorr qqchScheCorr);

    int insertQqchScheCorrList(@Param("qqchScheCorrList") List<QqchScheCorr> qqchScheCorrList);

    int updateQqchScheCorr(QqchScheCorr qqchScheCorr);

    int updateQqchScheCorrList(@Param("qqchScheCorrList") List<QqchScheCorr> qqchScheCorrList);

    int deleteQqchScheCorr(QqchScheCorr qqchScheCorr);

    int deleteQqchScheCorrByPks(@Param("qqchScheCorrPkList") List<Long> qqchScheCorrPkList);
}
