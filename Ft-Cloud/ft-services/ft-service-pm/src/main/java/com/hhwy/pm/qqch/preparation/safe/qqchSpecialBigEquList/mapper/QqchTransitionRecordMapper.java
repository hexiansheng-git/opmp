package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchTransitionRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 11:37:26
 * @remark 
 */
public interface QqchTransitionRecordMapper {
                                                                                                                                                                                                                                                                                                                
    QqchTransitionRecord getQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord);

    List<QqchTransitionRecord> getQqchTransitionRecordList(QqchTransitionRecord qqchTransitionRecord);

    int insertQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord);

    int insertQqchTransitionRecordList(@Param("qqchTransitionRecordList") List<QqchTransitionRecord> qqchTransitionRecordList);

    int updateQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord);

    int updateQqchTransitionRecordList(@Param("qqchTransitionRecordList") List<QqchTransitionRecord> qqchTransitionRecordList);
    
    int deleteQqchTransitionRecord(QqchTransitionRecord qqchTransitionRecord);

    int deleteQqchTransitionRecordByPks(@Param("qqchTransitionRecordPkList") List<Long> qqchTransitionRecordPkList);
    }
