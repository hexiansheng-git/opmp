package com.hhwy.pm.gm.wbs.mapper;

import com.hhwy.pm.gm.wbs.domain.TWbs;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author wk
 * @date 2023-08-01 11:26:43
 * @remark
 */
public interface TWbsMapper {

    TWbs getTWbs(TWbs tWbs);

    List<TWbs> getTWbsParentList(@Param("ids")Long[] ids);
    
    List<TWbs> getTWbsList(TWbs tWbs);

    List<TWbs> getTWbsId(TWbs tWbs);

    /**
     * 获取主id
     * @param type
     * @return
     */
    Long getEffectMainIdByType(String type);

    int insertTWbs(TWbs tWbs);

    int insertTWbsList(@Param("tWbsList") List<TWbs> tWbsList);

    int updateTWbs(TWbs tWbs);

    int updateTWbsList(@Param("list") List<TWbs> tWbsList);

    int deleteTWbs(TWbs tWbs);

    int deleteTWbsByPks(@Param("tWbsPkList") List<Long> tWbsPkList);
}
