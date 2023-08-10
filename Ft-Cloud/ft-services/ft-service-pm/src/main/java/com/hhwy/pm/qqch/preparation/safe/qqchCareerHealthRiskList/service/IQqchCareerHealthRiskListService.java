package com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.service;

import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.domain.QqchCareerHealthRiskList;
import com.hhwy.pm.qqch.preparation.safe.qqchCareerHealthRiskList.domain.vo.QqchCareerHealthRiskListVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-09 15:01:27
 * @remark 
 */
public interface IQqchCareerHealthRiskListService {
                                                                                                                                                                                                                                                                                                                                                                            
    QqchCareerHealthRiskList getQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList);

    QqchCareerHealthRiskListVo getQqchCareerHealthRiskListList(QqchCareerHealthRiskList qqchCareerHealthRiskList);

    int insertQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList);

    int updateQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList);

    int updateQqchCareerHealthRiskListList(List<QqchCareerHealthRiskList> qqchCareerHealthRiskListList);
    
    int deleteQqchCareerHealthRiskList(QqchCareerHealthRiskList qqchCareerHealthRiskList);

    int deleteQqchCareerHealthRiskListByPks(List<Long> qqchCareerHealthRiskListPkList);

    void save(QqchCareerHealthRiskListVo vo);
}
