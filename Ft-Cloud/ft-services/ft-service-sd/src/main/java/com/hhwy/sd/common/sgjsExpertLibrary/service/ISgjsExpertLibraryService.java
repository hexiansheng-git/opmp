package com.hhwy.sd.common.sgjsExpertLibrary.service;


import com.hhwy.sd.common.sgjsExpertLibrary.domain.SgjsExpertLibrary;

import java.util.List;

/**
 * @author fsd
 * @date 2024-01-25 09:12:10
 * @remark
 */
public interface ISgjsExpertLibraryService {

    SgjsExpertLibrary getSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    List<SgjsExpertLibrary> getSgjsExpertLibraryList(SgjsExpertLibrary sgjsExpertLibrary);

    List<SgjsExpertLibrary> getListByForeignId(Long foreignId);

    /**
     * 保存专家库数据集
     * @param foreignId 外键id
     * @param belongBusiness 所属功能
     * @param saveList 成果数据集
     */
    void saveExpertLibraryList(Long foreignId, String belongBusiness, List<SgjsExpertLibrary> saveList);

    int insertSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    int insertSgjsExpertLibraryList(List<SgjsExpertLibrary> sgjsExpertLibraryList);

    int updateSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    int updateSgjsExpertLibraryList(List<SgjsExpertLibrary> sgjsExpertLibraryList);

    int deleteSgjsExpertLibrary(SgjsExpertLibrary sgjsExpertLibrary);

    int deleteSgjsExpertLibraryByPks(List<Long> sgjsExpertLibraryPkList);

    void deleteSgjsExpertLibraryByForeignId(Long foreignId);
}
