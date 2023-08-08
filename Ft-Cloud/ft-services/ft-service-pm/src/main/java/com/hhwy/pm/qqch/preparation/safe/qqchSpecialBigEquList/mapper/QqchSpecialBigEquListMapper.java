package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchSpecialBigEquList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 11:39:00
 * @remark 
 */
public interface QqchSpecialBigEquListMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchSpecialBigEquList getQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList);

    List<QqchSpecialBigEquList> getQqchSpecialBigEquListList(QqchSpecialBigEquList qqchSpecialBigEquList);

    int insertQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList);

    int insertQqchSpecialBigEquListList(@Param("qqchSpecialBigEquListList") List<QqchSpecialBigEquList> qqchSpecialBigEquListList);

    int updateQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList);

    int updateQqchSpecialBigEquListList(@Param("qqchSpecialBigEquListList") List<QqchSpecialBigEquList> qqchSpecialBigEquListList);
    
    int deleteQqchSpecialBigEquList(QqchSpecialBigEquList qqchSpecialBigEquList);

    int deleteQqchSpecialBigEquListByPks(@Param("qqchSpecialBigEquListPkList") List<Long> qqchSpecialBigEquListPkList);
    }
