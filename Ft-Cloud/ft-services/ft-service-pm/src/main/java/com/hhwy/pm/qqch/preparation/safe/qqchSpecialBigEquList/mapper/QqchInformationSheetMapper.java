package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.mapper;

import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchInformationSheet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 11:36:31
 * @remark 
 */
public interface QqchInformationSheetMapper {
                                                                                                                                                                                                                                                                                                                
    QqchInformationSheet getQqchInformationSheet(QqchInformationSheet qqchInformationSheet);

    List<QqchInformationSheet> getQqchInformationSheetList(QqchInformationSheet qqchInformationSheet);

    int insertQqchInformationSheet(QqchInformationSheet qqchInformationSheet);

    int insertQqchInformationSheetList(@Param("qqchInformationSheetList") List<QqchInformationSheet> qqchInformationSheetList);

    int updateQqchInformationSheet(QqchInformationSheet qqchInformationSheet);

    int updateQqchInformationSheetList(@Param("qqchInformationSheetList") List<QqchInformationSheet> qqchInformationSheetList);
    
    int deleteQqchInformationSheet(QqchInformationSheet qqchInformationSheet);

    int deleteQqchInformationSheetByPks(@Param("qqchInformationSheetPkList") List<Long> qqchInformationSheetPkList);
    }
