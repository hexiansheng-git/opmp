package com.hhwy.sp.techData.sgjsTechnicalDataFile.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.hhwy.sp.techData.sgjsTechnicalDataFile.domain.SgjsTechnicalDataFile;

/**
 * @author cjh
 * @date 2024-02-27 15:10:21
 * @remark
 */
public interface SgjsTechnicalDataFileMapper {

    SgjsTechnicalDataFile getSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile);

    List<SgjsTechnicalDataFile> getSgjsTechnicalDataFileList(SgjsTechnicalDataFile sgjsTechnicalDataFile);

    int insertSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile);

    int insertSgjsTechnicalDataFileList(@Param("sgjsTechnicalDataFileList") List<SgjsTechnicalDataFile> sgjsTechnicalDataFileList);

    int updateSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile);

    int updateSgjsTechnicalDataFileList(@Param("sgjsTechnicalDataFileList") List<SgjsTechnicalDataFile> sgjsTechnicalDataFileList);

    int deleteSgjsTechnicalDataFile(SgjsTechnicalDataFile sgjsTechnicalDataFile);

    int deleteSgjsTechnicalDataFileByPks(@Param("sgjsTechnicalDataFilePkList") List<Long> sgjsTechnicalDataFilePkList);
}
