package com.hhwy.pm.gm.wbs.mapper;

import com.hhwy.pm.gm.wbs.domain.TWbs;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * @author wk
 * @date 2023-08-01 11:26:43
 * @remark
 */
public interface TWbsMapper {

    TWbs getTWbs(TWbs tWbs);

    /**
     * 查询最新的wbs
     * @param ancestorsName  完整标准wbs编号
     * @return
     */
    TWbs getLatestTWbsByFullCode(String ancestorsName);

    List<TWbs> getTWbsParentList(@Param("ids")Long[] ids);

    List<TWbs> getAllChildTWbs(@Param("id")Long id);
    
    List<TWbs> getTWbsList(TWbs tWbs);

    List<TWbs> getTWbsId(TWbs tWbs);

    /**
     * 获取主id
     * @param type
     * @return
     */
    Long getEffectMainIdByType(String type);

    /**
     * 根据产品类型获取生效的工程类型
     * @param type
     * @return
     */
    String getEffectEngineeringTypeByProType(String type);
    String getEngineeringTypeByMainId(Long mainId);

    int insertTWbs(TWbs tWbs);

    int insertTWbsMain(Map map);

    int insertTWbsList(@Param("tWbsList") List<TWbs> tWbsList);

    int updateTWbs(TWbs tWbs);

    int updateTWbsList(@Param("list") List<TWbs> tWbsList);

    int deleteTWbs(TWbs tWbs);
    
    int deleteTWbsMain(Long id);

    int deleteTWbsByPks(@Param("tWbsPkList") List<Long> tWbsPkList);
    
    int updateValid(Long globalId);
}
