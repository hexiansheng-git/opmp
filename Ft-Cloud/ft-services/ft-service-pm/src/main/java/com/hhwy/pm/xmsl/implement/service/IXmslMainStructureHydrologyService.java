package com.hhwy.pm.xmsl.implement.service;

import java.util.List;
import com.hhwy.pm.xmsl.implement.domain.XmslMainStructureHydrology;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:02
 * @remark 主要构造物水文条件
 */
public interface IXmslMainStructureHydrologyService {

    List<XmslMainStructureHydrology> getXmslMainStructureHydrologyList(
        XmslMainStructureHydrology xmslMainStructureHydrology);

    void save(List<XmslMainStructureHydrology> xmslMainStructureHydrologyList);

    int deleteXmslMainStructureHydrologyByPks(List<Long> xmslMainStructureHydrologyPkList);
}
