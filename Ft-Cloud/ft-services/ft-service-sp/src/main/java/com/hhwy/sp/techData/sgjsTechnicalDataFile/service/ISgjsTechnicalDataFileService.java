package com.hhwy.sp.techData.sgjsTechnicalDataFile.service;

import java.util.List;

import com.hhwy.sp.techData.sgjsTechnicalDataFile.domain.SgjsTechnicalDataFile;

/**
 * @author cjh
 * @date 2024-02-27 15:10:21
 * @remark
 */
public interface ISgjsTechnicalDataFileService {

    SgjsTechnicalDataFile getSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile);

    List<SgjsTechnicalDataFile> getSgjsTechnicalDataFileList(SgjsTechnicalDataFile sgjsTechnicalDataFile);

    int insertSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile);

    int insertSgjsTechnicalDataFileList(List<SgjsTechnicalDataFile> sgjsTechnicalDataFileList);

    int updateSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile);

    int updateSgjsTechnicalDataFileList(List<SgjsTechnicalDataFile> sgjsTechnicalDataFileList);

    int deleteSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile);

    int deleteSgjsTechnicalDataFileByPks(List<Long> sgjsTechnicalDataFilePkList);
}
