package com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.mapper;

import java.util.List;

import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprint;
import com.hhwy.sp.techFile.sgjsTechnicalFileBlueprint.domain.SgjsTechnicalFileBlueprintParam;
import org.apache.ibatis.annotations.Param;

/**
 * @author fsd
 * @date 2024-01-22 08:54:05
 * @remark
 */
public interface SgjsTechnicalFileBlueprintMapper {

    SgjsTechnicalFileBlueprint getSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint);

    List<SgjsTechnicalFileBlueprint> getSgjsTechnicalFileBlueprintList(SgjsTechnicalFileBlueprintParam sgjsTechnicalFileBlueprint);

    int insertSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint);

    int insertSgjsTechnicalFileBlueprintList(@Param("sgjsTechnicalFileBlueprintList") List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList);

    int updateSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint);

    int updateSgjsTechnicalFileBlueprintList(@Param("list") List<SgjsTechnicalFileBlueprint> sgjsTechnicalFileBlueprintList);

    int deleteSgjsTechnicalFileBlueprint(SgjsTechnicalFileBlueprint sgjsTechnicalFileBlueprint);

    int deleteSgjsTechnicalFileBlueprintByPks(@Param("sgjsTechnicalFileBlueprintPkList") List<Long> sgjsTechnicalFileBlueprintPkList);

    int deleteWithChildren(@Param("sgjsTechnicalFileBlueprintPkList") List<Long> sgjsTechnicalFileBlueprintPkList);
}
