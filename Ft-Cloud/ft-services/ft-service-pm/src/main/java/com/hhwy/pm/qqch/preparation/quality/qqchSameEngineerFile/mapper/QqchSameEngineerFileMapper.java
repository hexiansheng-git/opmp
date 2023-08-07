package com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.mapper;

import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.QqchSameEngineerFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-07 09:52:57
 * @remark 
 */
public interface QqchSameEngineerFileMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchSameEngineerFile getQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile);

    List<QqchSameEngineerFile> getQqchSameEngineerFileList(QqchSameEngineerFile qqchSameEngineerFile);

    int insertQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile);

    int insertQqchSameEngineerFileList(@Param("qqchSameEngineerFileList") List<QqchSameEngineerFile> qqchSameEngineerFileList);

    int updateQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile);

            int updateQqchSameEngineerFileList(@Param("qqchSameEngineerFileList") List<QqchSameEngineerFile> qqchSameEngineerFileList);
    
    int deleteQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile);

            int deleteQqchSameEngineerFileByPks(@Param("qqchSameEngineerFilePkList") List<Long> qqchSameEngineerFilePkList);
    }
