package com.hhwy.pm.qqch.tax.qqchTaxStage.service;

import com.hhwy.pm.qqch.tax.qqchTaxStage.domain.QqchTaxStage;
import com.hhwy.pm.qqch.tax.qqchTaxStage.dto.StageDTO;

import java.util.List;

/**
 * @author mls
 * @date 2023-08-13 23:06:25
 * @remark
 */
public interface IQqchTaxStageService {

    QqchTaxStage getQqchTaxStage(QqchTaxStage qqchTaxStage);

    List<QqchTaxStage> getQqchTaxStageList(QqchTaxStage qqchTaxStage);

    int insertQqchTaxStage(QqchTaxStage qqchTaxStage);

    int insertQqchTaxStageList(List<QqchTaxStage> qqchTaxStageList);

    int updateQqchTaxStage(QqchTaxStage qqchTaxStage);

    int updateQqchTaxStageList(List<QqchTaxStage> qqchTaxStageList);

    int deleteQqchTaxStage(QqchTaxStage qqchTaxStage);

    int deleteQqchTaxStageByPks(List<Long> qqchTaxStagePkList);

    /**
     * 记录Id  数据类型
     * @param recordId 
     * @param dataType
     */
    void saveStage(Long recordId,String dataType);
    
}
