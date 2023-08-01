package com.hhwy.pm.xmsl.implement.domain.vo;

import com.hhwy.pm.xmsl.implement.domain.GeologicalCondition;
import com.hhwy.pm.xmsl.implement.domain.LocalResourceSupply;
import com.hhwy.pm.xmsl.implement.domain.XmslBasicFacilitiesConditions;
import com.hhwy.pm.xmsl.implement.domain.XmslClimateCondition;
import com.hhwy.pm.xmsl.implement.domain.XmslConstructionInterference;
import com.hhwy.pm.xmsl.implement.domain.XmslExtend;
import com.hhwy.pm.xmsl.implement.domain.XmslKeyPersonCommunication;
import com.hhwy.pm.xmsl.implement.domain.XmslMainStructureHydrology;
import com.hhwy.pm.xmsl.implement.domain.XmslTerrainLandforms;
import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-28 17:53:07
 * @remark 实施条件所有集合
 */
@Data
public class ImplementVo {

    /**
     * 地形地貌
     */
    private List<XmslTerrainLandforms> terrainLandformsList;

    /**
     * 地质条件
     */
    private GeologicalCondition geologicalCondition;

    /**
     * 主要构造物水文条件
     */
    private List<XmslMainStructureHydrology> mainStructureHydrologyList;

    /**
     * 气候条件
     */
    private List<XmslClimateCondition> climateConditionList;

    /**
     * 水、电、交通、通讯条件
     */
    private List<XmslBasicFacilitiesConditions> basicFacilitiesConditionsList;

    /**
     * 施工干扰
     */
    private List<XmslConstructionInterference> constructionInterferenceList;

    /**
     * 当地资源供应
     */
    private LocalResourceSupply localResourceSupply;

    /**
     * 当地政策要点/社会和人文条件说明/气候条件附件
     */
    private XmslExtend xmslExtend;

    /**
     * 重要干系人识别及沟通
     */
    private List<XmslKeyPersonCommunication> keyPersonCommunicationList;
}
