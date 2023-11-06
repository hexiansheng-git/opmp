package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service;

import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchSpecialBigEquList;
import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.vo.QqchSpecialBigEquListVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 11:39:00
 * @remark 
 */
public interface IQqchSpecialBigEquListService {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchSpecialBigEquList getQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList);

    QqchSpecialBigEquListVo getQqchSpecialBigEquListList(QqchSpecialBigEquList qqchSpecialBigEquList);

    int insertQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList);

    int updateQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList);

    int updateQqchSpecialBigEquListList(List<QqchSpecialBigEquList> qqchSpecialBigEquListList);
    
    int deleteQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList);

    int deleteQqchSpecialBigEquListByPks(List<Long> qqchSpecialBigEquListPkList);

    void save(QqchSpecialBigEquListVo vo);

    QqchSpecialBigEquListVo getSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList);
}
