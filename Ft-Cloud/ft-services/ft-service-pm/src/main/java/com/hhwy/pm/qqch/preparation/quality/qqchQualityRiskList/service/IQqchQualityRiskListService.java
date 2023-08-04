package com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.service;

import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.QqchQualityRiskList;
import com.hhwy.pm.qqch.preparation.quality.qqchQualityRiskList.domain.vo.QqchQualityRiskListVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-04 10:02:39
 * @remark
 */
public interface IQqchQualityRiskListService {

    QqchQualityRiskList getQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList);

    QqchQualityRiskListVo getQqchQualityRiskListList(QqchQualityRiskList qqchQualityRiskList);

    int insertQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList);

    int updateQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList);

    int updateQqchQualityRiskListList(List<QqchQualityRiskList> qqchQualityRiskListList);

    int deleteQqchQualityRiskList(QqchQualityRiskList qqchQualityRiskList);

    int deleteQqchQualityRiskListByPks(List<Long> qqchQualityRiskListPkList);

    void save(QqchQualityRiskListVo vo);
}
