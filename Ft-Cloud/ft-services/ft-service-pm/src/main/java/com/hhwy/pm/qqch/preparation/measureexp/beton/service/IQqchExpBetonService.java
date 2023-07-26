package com.hhwy.pm.qqch.preparation.measureexp.beton.service;

import java.util.List;
import com.hhwy.pm.qqch.preparation.measureexp.beton.domain.QqchExpBeton;

/**
 * @author mls
 * @date 2023-07-25 18:31:38
 * @remark 
 */
public interface IQqchExpBetonService {
                                                                                                                                                                                                                                                                                                                                                    
    QqchExpBeton getQqchExpBeton(QqchExpBeton qqchExpBeton);

    List<QqchExpBeton> getQqchExpBetonList(QqchExpBeton qqchExpBeton);

    int insertQqchExpBeton(QqchExpBeton qqchExpBeton);

    int insertQqchExpBetonList(List<QqchExpBeton> qqchExpBetonList);

    int updateQqchExpBeton(QqchExpBeton qqchExpBeton);

            int updateQqchExpBetonList(List<QqchExpBeton> qqchExpBetonList);
    
    int deleteQqchExpBeton(QqchExpBeton qqchExpBeton);

            int deleteQqchExpBetonByPks(List<Long> qqchExpBetonPkList);
    }
