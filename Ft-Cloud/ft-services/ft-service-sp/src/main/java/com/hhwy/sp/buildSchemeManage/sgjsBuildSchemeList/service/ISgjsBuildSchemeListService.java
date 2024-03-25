package com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.service;

import com.hhwy.common.core.web.domain.AjaxResult;
import com.hhwy.sp.buildSchemeManage.review.domain.SgjsBuildSchemeEvolve;
import com.hhwy.sp.buildSchemeManage.review.domain.vo.BuildSchemeEvolveQueryVo;
import com.hhwy.sp.buildSchemeManage.sgjsBuildSchemeList.domain.SgjsBuildSchemeList;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    List<SgjsBuildSchemeList> getLastValidScheme(SgjsBuildSchemeList sgjsBuildSchemeListParam);

    List<SgjsBuildSchemeList> getRiskList(SgjsBuildSchemeList sgjsBuildSchemeListParam);

    AjaxResult importData(List<Map<Integer, String>> headList, List<Map<Integer, String>> dataList);

    //批量查询，根据foreignId
    List<SgjsBuildSchemeList> getListByforeignList(Collection<Long> foreignId);

    List<SgjsBuildSchemeEvolve> getListByEvolveQueryVo(BuildSchemeEvolveQueryVo queryVo);

    List<SgjsBuildSchemeEvolve> getEvolveListByIds(List<Long> ids);
}
