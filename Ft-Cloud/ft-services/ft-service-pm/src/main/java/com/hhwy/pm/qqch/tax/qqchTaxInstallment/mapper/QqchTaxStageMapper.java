package com.hhwy.pm.qqch.tax.qqchTaxInstallment.mapper;

import com.hhwy.pm.qqch.tax.qqchTaxInstallment.domain.QqchTaxStage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-13 23:06:25
 * @remark
 */
public interface QqchTaxStageMapper {

    QqchTaxStage getQqchTaxStage(QqchTaxStage qqchTaxStage);

    List<QqchTaxStage> getQqchTaxStageList(QqchTaxStage qqchTaxStage);

    int insertQqchTaxStage(QqchTaxStage qqchTaxStage);

    int insertQqchTaxStageList(@Param("qqchTaxStageList") List<QqchTaxStage> qqchTaxStageList);

    int updateQqchTaxStage(QqchTaxStage qqchTaxStage);

    int updateQqchTaxStageList(@Param("qqchTaxStageList") List<QqchTaxStage> qqchTaxStageList);

    int deleteQqchTaxStage(QqchTaxStage qqchTaxStage);

    int deleteQqchTaxStageByPks(@Param("qqchTaxStagePkList") List<Long> qqchTaxStagePkList);

    List<Long> selectNewestRecordId();
}
