package com.hhwy.sp.common.sgjsExpertLibrary.service;

import com.hhwy.sp.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;

import java.util.List;

/**
 * @author fsd
 * @date 2024-01-25 09:12:10
 * @remark
 */
public interface ISgjsExpertLibraryService {

    SgjsExpertLibrary getSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    List<SgjsExpertLibrary> getSgjsExpertLibraryList(SgjsExpertLibrary sgjsExpertLibrary);

    int insertSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    int insertSgjsExpertLibraryList(List<SgjsExpertLibrary> sgjsExpertLibraryList);

    int updateSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    int updateSgjsExpertLibraryList(List<SgjsExpertLibrary> sgjsExpertLibraryList);

    int deleteSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    int deleteSgjsExpertLibraryByPks(List<Long> sgjsExpertLibraryPkList);

    int saveSgjsExpertLibraryList(Long foreignId, String belongBusiness, List<SgjsExpertLibrary> sgjsExpertLibraryList);
}
