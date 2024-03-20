package com.hhwy.sp.buildScheme.sgjsBuildSchemeList.service;

import java.util.List;

import com.hhwy.sp.buildScheme.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:37
 * @remark
 */
public interface ISgjsBuildSchemeListService {

    SgjsBuildSchemeList getSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    List<SgjsBuildSchemeList> getSgjsBuildSchemeListList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int insertSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int insertSgjsBuildSchemeListList(List<SgjsBuildSchemeList> sgjsBuildSchemeListList);

    /*
    * 功能描述: 新增
    * @param: sgjsBuildSchemeListList  待处理集合
    * @param: foreignId     主表id
    * @return: int
    * 作者: fsd
    * 时间: 2024/3/20
    */
    void insertSgjsBuildSchemeList(List<SgjsBuildSchemeList> sgjsBuildSchemeListList, Long foreignId);

    int updateSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int updateSgjsBuildSchemeListList(List<SgjsBuildSchemeList> sgjsBuildSchemeListList);

    int deleteSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int deleteSgjsBuildSchemeListByPks(List<Long> sgjsBuildSchemeListPkList);
}
