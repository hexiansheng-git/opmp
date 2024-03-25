package com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.mapper;

import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeEvolve;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeEvolveQueryVo;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author fushudong
 * @date 2024-03-19 15:57:37
 * @remark
 */
public interface SgjsBuildSchemeListMapper {

    SgjsBuildSchemeList getSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    List<SgjsBuildSchemeList> getSgjsBuildSchemeListList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int insertSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int insertSgjsBuildSchemeListList(@Param("sgjsBuildSchemeListList") List<SgjsBuildSchemeList> sgjsBuildSchemeListList);

    int updateSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int updateSgjsBuildSchemeListList(@Param("sgjsBuildSchemeListList") List<SgjsBuildSchemeList> sgjsBuildSchemeListList);

    int deleteSgjsBuildSchemeList(SgjsBuildSchemeList sgjsBuildSchemeList);

    int deleteSgjsBuildSchemeListByPks(@Param("sgjsBuildSchemeListPkList") List<Long> sgjsBuildSchemeListPkList);

    List<SgjsBuildSchemeList> getListByforeignList(@Param("foreignIds") Collection<Long> foreignIds);

    List<SgjsBuildSchemeEvolve> getListByEvolveQueryVo(BuildSchemeEvolveQueryVo queryVo);

    List<SgjsBuildSchemeEvolve> getEvolveListByIds(@Param("ids") List<Long> ids);
}
