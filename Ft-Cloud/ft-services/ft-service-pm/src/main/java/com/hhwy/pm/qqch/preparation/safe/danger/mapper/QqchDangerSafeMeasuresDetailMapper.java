package com.hhwy.pm.qqch.preparation.safe.danger.mapper;

import com.hhwy.pm.qqch.preparation.safe.danger.domain.QqchDangerSafeMeasuresDetail;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhenglili
 * @date 2023-08-17 13:32:20
 * @remark 8.3.2 危大工程安全技术措施明细
 */
public interface QqchDangerSafeMeasuresDetailMapper {

    QqchDangerSafeMeasuresDetail getQqchDangerSafeMeasuresDetail(
        QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail);

    List<QqchDangerSafeMeasuresDetail> getQqchDangerSafeMeasuresDetailList(
        QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail);

    int insertQqchDangerSafeMeasuresDetail(QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail);

    int insertQqchDangerSafeMeasuresDetailList(
        @Param("qqchDangerSafeMeasuresDetailList") List<QqchDangerSafeMeasuresDetail> qqchDangerSafeMeasuresDetailList);

    int updateQqchDangerSafeMeasuresDetail(QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail);

    int updateQqchDangerSafeMeasuresDetailList(
        @Param("list") List<QqchDangerSafeMeasuresDetail> qqchDangerSafeMeasuresDetailList);

    int deleteQqchDangerSafeMeasuresDetail(QqchDangerSafeMeasuresDetail qqchDangerSafeMeasuresDetail);

    int deleteQqchDangerSafeMeasuresDetailByPks(
        @Param("qqchDangerSafeMeasuresDetailPkList") List<Long> qqchDangerSafeMeasuresDetailPkList);
}
