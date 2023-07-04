package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslMainStructureHydrology;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:02
 * @remark 主要构造物水文条件
 */
public interface XmslMainStructureHydrologyMapper {

    XmslMainStructureHydrology getXmslMainStructureHydrology(
        XmslMainStructureHydrology xmslMainStructureHydrology);

    List<XmslMainStructureHydrology> getXmslMainStructureHydrologyList(
        XmslMainStructureHydrology xmslMainStructureHydrology);

    int insertXmslMainStructureHydrology(XmslMainStructureHydrology xmslMainStructureHydrology);

    int insertXmslMainStructureHydrologyList(
        @Param("xmslMainStructureHydrologyList") List<XmslMainStructureHydrology> xmslMainStructureHydrologyList);

    int updateXmslMainStructureHydrology(XmslMainStructureHydrology xmslMainStructureHydrology);

    int updateXmslMainStructureHydrologyList(@Param("list") List<XmslMainStructureHydrology> xmslMainStructureHydrologyList);

    int deleteXmslMainStructureHydrology(XmslMainStructureHydrology xmslMainStructureHydrology);

    int deleteXmslMainStructureHydrologyByPks(
        @Param("xmslMainStructureHydrologyPkList") List<Long> xmslMainStructureHydrologyPkList);
}
