package com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.mapper;

import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.QqchSameEngineerFileWbs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-07 11:28:39
 * @remark 
 */
public interface QqchSameEngineerFileWbsMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
    QqchSameEngineerFileWbs getQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs);

    List<QqchSameEngineerFileWbs> getQqchSameEngineerFileWbsList(QqchSameEngineerFileWbs qqchSameEngineerFileWbs);

    int insertQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs);

    int insertQqchSameEngineerFileWbsList(@Param("qqchSameEngineerFileWbsList") List<QqchSameEngineerFileWbs> qqchSameEngineerFileWbsList);

    int updateQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs);

     int updateQqchSameEngineerFileWbsList(@Param("qqchSameEngineerFileWbsList") List<QqchSameEngineerFileWbs> qqchSameEngineerFileWbsList);
    
    int deleteQqchSameEngineerFileWbs(QqchSameEngineerFileWbs qqchSameEngineerFileWbs);

     int deleteQqchSameEngineerFileWbsByPks(@Param("qqchSameEngineerFileWbsPkList") List<Long> qqchSameEngineerFileWbsPkList);
    }
