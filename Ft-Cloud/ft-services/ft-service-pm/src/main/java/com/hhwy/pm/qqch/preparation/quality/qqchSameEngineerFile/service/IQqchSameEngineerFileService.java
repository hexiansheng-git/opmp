package com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.service;

import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.QqchSameEngineerFile;
import com.hhwy.pm.qqch.preparation.quality.qqchSameEngineerFile.domain.vo.QqchSameEngineerFileVo;

import java.util.List;

/**
 * @author ldd
 * @date 2023-08-07 09:52:57
 * @remark 
 */
public interface IQqchSameEngineerFileService {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    QqchSameEngineerFile getQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile);

    QqchSameEngineerFileVo getQqchSameEngineerFileList(QqchSameEngineerFile qqchSameEngineerFile);

    int insertQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile);

    int updateQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile);

    int updateQqchSameEngineerFileList(List<QqchSameEngineerFile> qqchSameEngineerFileList);
    
    int deleteQqchSameEngineerFile(QqchSameEngineerFile qqchSameEngineerFile);

    int deleteQqchSameEngineerFileByPks(List<Long> qqchSameEngineerFilePkList);

    void save(QqchSameEngineerFileVo vo);
}
