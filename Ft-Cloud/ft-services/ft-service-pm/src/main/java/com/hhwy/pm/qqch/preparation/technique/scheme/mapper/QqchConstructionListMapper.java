package com.hhwy.pm.qqch.preparation.technique.scheme.mapper;

import com.hhwy.pm.qqch.preparation.technique.scheme.domain.QqchConstructionList;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zhenglili
 * @date 2023-07-13 14:27:14
 * @remark 3.4.2施工方案清单
 */
public interface QqchConstructionListMapper {

    QqchConstructionList getQqchConstructionList(QqchConstructionList qqchConstructionList);

    List<QqchConstructionList> getQqchConstructionListList(QqchConstructionList qqchConstructionList);

    /**
     * 查询最大流水号
     * @return
     */
    String selectMaxFlowCode();

    int insertQqchConstructionList(QqchConstructionList qqchConstructionList);

    int insertQqchConstructionListList(
        @Param("qqchConstructionListList") List<QqchConstructionList> qqchConstructionListList);

    int updateQqchConstructionList(QqchConstructionList qqchConstructionList);

    int updateQqchConstructionListList(@Param("list") List<QqchConstructionList> qqchConstructionListList);

    /**
     * 更新全部数据得清单通过时间
     * @param listPassTime
     * @return
     */
    int updatePassTime(@Param("listPassTime") Date listPassTime,@Param("version") BigDecimal version);
    
    int deleteQqchConstructionList(QqchConstructionList qqchConstructionList);

    int deleteQqchConstructionListByPks(@Param("qqchConstructionListPkList") List<Long> qqchConstructionListPkList);

    int deleteByWbsCode(@Param("wbsCodeSet") Set<String> wbsCodeSet);

    List<QqchConstructionList> getBigDangerLevelConstructionList(@Param("version") BigDecimal version);

    List<QqchConstructionList> getByWbsCodes(@Param("wbsCodes") String[] wbsCodes,
        @Param("maxVersion") BigDecimal maxVersion);
}
