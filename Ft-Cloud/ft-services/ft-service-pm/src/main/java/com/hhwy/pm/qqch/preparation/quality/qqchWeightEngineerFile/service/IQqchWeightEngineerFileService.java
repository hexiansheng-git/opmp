package com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.service;

import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.domain.QqchWeightEngineerFile;
import com.hhwy.pm.qqch.preparation.quality.qqchWeightEngineerFile.domain.vo.QqchWeightEngineerFileVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-07 09:51:59
 * @remark 
 */
public interface IQqchWeightEngineerFileService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchWeightEngineerFile getQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile);

   QqchWeightEngineerFileVo getQqchWeightEngineerFileList(QqchWeightEngineerFile qqchWeightEngineerFile);

    int insertQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile);

    int updateQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile);

    int updateQqchWeightEngineerFileList(List<QqchWeightEngineerFile> qqchWeightEngineerFileList);
    
    int deleteQqchWeightEngineerFile(QqchWeightEngineerFile qqchWeightEngineerFile);

    int deleteQqchWeightEngineerFileByPks(List<Long> qqchWeightEngineerFilePkList);

    void save(QqchWeightEngineerFileVo vo);
}
