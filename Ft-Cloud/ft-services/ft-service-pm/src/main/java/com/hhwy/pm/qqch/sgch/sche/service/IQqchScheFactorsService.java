package com.hhwy.pm.qqch.sgch.sche.service;

import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheFactors;
import com.hhwy.pm.qqch.sgch.sche.vo.ScheFactorsVO;

import java.util.List;

/**
 * @author mls
 * @date 2023-07-31 11:22:52
 * @remark
 */
public interface IQqchScheFactorsService {

    QqchScheFactors getQqchScheFactors(QqchScheFactors qqchScheFactors);

    List<QqchScheFactors> getQqchScheFactorsList(QqchScheFactors qqchScheFactors);

    int insertQqchScheFactors(QqchScheFactors qqchScheFactors);

    int insertQqchScheFactorsList(List<QqchScheFactors> qqchScheFactorsList);

    int updateQqchScheFactors(QqchScheFactors qqchScheFactors);

    int updateQqchScheFactorsList(List<QqchScheFactors> qqchScheFactorsList);

    int deleteQqchScheFactors(QqchScheFactors qqchScheFactors);

    int deleteQqchScheFactorsByPks(List<Long> qqchScheFactorsPkList);

    void saveList(List<QqchScheFactors> dealSaveDto);

    ScheFactorsVO getList(QqchScheFactors dealSaveDto);

    ScheFactorsVO getList4jd(QqchScheFactors dealSaveDto);
}
