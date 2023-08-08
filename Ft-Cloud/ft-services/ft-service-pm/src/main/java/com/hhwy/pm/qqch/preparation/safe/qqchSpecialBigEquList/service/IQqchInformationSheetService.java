package com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.service;

import com.hhwy.pm.qqch.preparation.safe.qqchSpecialBigEquList.domain.QqchInformationSheet;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-08 11:36:31
 * @remark 
 */
public interface IQqchInformationSheetService {
                                                                                                                                                                                                                                                                                                                
    QqchInformationSheet getQqchInformationSheet(QqchInformationSheet qqchInformationSheet);

    List<QqchInformationSheet> getQqchInformationSheetList(QqchInformationSheet qqchInformationSheet);

    int insertQqchInformationSheet(QqchInformationSheet qqchInformationSheet);

    int insertQqchInformationSheetList(List<QqchInformationSheet> qqchInformationSheetList);

    int updateQqchInformationSheet(QqchInformationSheet qqchInformationSheet);

    int updateQqchInformationSheetList(List<QqchInformationSheet> qqchInformationSheetList);
    
    int deleteQqchInformationSheet(QqchInformationSheet qqchInformationSheet);

    int deleteQqchInformationSheetByPks(List<Long> qqchInformationSheetPkList);
    }
