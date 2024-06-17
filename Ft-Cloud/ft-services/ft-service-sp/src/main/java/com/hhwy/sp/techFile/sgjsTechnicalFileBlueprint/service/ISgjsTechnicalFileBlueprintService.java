package com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprint;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprintParam;

import java.util.List;
import java.util.Map;

/**
 * @author fsd
 * @date 2024-01-22 08:54:05
 * @remark
 */
public interface ISgjsTechnicalFileBlueprintService {

    SgjsTechnicalFileBlueprint getSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint);

    List<SgjsTechnicalFileBlueprint> getTreeList(SgjsTechnicalFileBlueprintParam sgjsTechnicalFileBlueprint);
    List<SgjsTechnicalFileBlueprint> getList(SgjsTechnicalFileBlueprintParam sgjsTechnicalFileBlueprint);

    int insertSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint);

    void insertSgjsTechnicalFileBlueprintList(List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList);

    int updateSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint);

    int updateSgjsTechnicalFileBlueprintList(List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList);

    int deleteSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint);

    int deleteSgjsTechnicalFileBlueprintByPks(List<Long> sgjsTechnicalFileBlueprintPkList);

    int deleteWithChildren(List<Long> sgjsTechnicalFileBlueprintPkList);

    AjaxResult importData(List<Map<Integer, String>> headList, List<Map<Integer, String>> dataList);
}
