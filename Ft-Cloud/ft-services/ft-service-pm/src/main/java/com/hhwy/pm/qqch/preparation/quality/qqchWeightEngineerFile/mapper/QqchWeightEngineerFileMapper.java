package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.mapper;

import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.domain.QqchWeightEngineerFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-07 09:51:59
 * @remark 
 */
public interface QqchWeightEngineerFileMapper {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchWeightEngineerFile getQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile);

    List<QqchWeightEngineerFile> getQqchWeightEngineerFileList(QqchWeightEngineerFile qqchWeightEngineerFile);

    int insertQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile);

    int insertQqchWeightEngineerFileList(@Param("qqchWeightEngineerFileList") List<QqchWeightEngineerFile> qqchWeightEngineerFileList);

    int updateQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile);

            int updateQqchWeightEngineerFileList(@Param("qqchWeightEngineerFileList") List<QqchWeightEngineerFile> qqchWeightEngineerFileList);
    
    int deleteQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile);

            int deleteQqchWeightEngineerFileByPks(@Param("qqchWeightEngineerFilePkList") List<Long> qqchWeightEngineerFilePkList);
    }
