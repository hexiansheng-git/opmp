package com.hhwy.sp.common.sgjsExpertLibrary.mapper;

import java.util.List;

import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;
import org.apache.ibatis.annotations.Param;

/**
 * @author fsd
 * @date 2024-01-25 09:12:10
 * @remark
 */
public interface SgjsExpertLibraryMapper {

    SgjsExpertLibrary getSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    List<SgjsExpertLibrary> getSgjsExpertLibraryList(SgjsExpertLibrary sgjsExpertLibrary);

    int insertSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    int insertSgjsExpertLibraryList(@Param("sgjsExpertLibraryList") List<SgjsExpertLibrary> sgjsExpertLibraryList);

    int updateSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    int updateSgjsExpertLibraryList(@Param("sgjsExpertLibraryList") List<SgjsExpertLibrary> sgjsExpertLibraryList);

    int deleteSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    int deleteSgjsExpertLibraryByPks(@Param("sgjsExpertLibraryPkList") List<Long> sgjsExpertLibraryPkList);
}
