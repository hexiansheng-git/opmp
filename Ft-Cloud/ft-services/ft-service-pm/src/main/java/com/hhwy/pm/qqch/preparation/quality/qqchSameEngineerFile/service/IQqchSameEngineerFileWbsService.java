package com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.service;

import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.QqchSameEngineerFileWbs;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-07 11:28:39
 * @remark 
 */
public interface IQqchSameEngineerFileWbsService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchSameEngineerFileWbs getQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs);

    List<QqchSameEngineerFileWbs> getQqchSameEngineerFileWbsList(QqchSameEngineerFileWbs qqchSameEngineerFileWbs);

    int insertQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs);

    int insertQqchSameEngineerFileWbsList(List<QqchSameEngineerFileWbs> qqchSameEngineerFileWbsList);

    int updateQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs);

            int updateQqchSameEngineerFileWbsList(List<QqchSameEngineerFileWbs> qqchSameEngineerFileWbsList);
    
    int deleteQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs);

            int deleteQqchSameEngineerFileWbsByPks(List<Long> qqchSameEngineerFileWbsPkList);
    }
