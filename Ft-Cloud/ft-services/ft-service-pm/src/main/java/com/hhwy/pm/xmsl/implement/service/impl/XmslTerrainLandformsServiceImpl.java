package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.GeologicalCondition;
import com.hhwy.pm.xmsl.implement.domain.LocalResourceSupply;
import com.hhwy.pm.xmsl.implement.domain.XmslBasicFacilitiesConditions;
import com.hhwy.pm.xmsl.implement.domain.XmslClimateCondition;
import com.hhwy.pm.xmsl.implement.domain.XmslConstructionInterference;
import com.hhwy.pm.xmsl.implement.domain.XmslExtend;
import com.hhwy.pm.xmsl.implement.domain.XmslKeyPersonCommunication;
import com.hhwy.pm.xmsl.implement.domain.XmslMainStructureHydrology;
import com.hhwy.pm.xmsl.implement.domain.XmslTerrainLandforms;
import com.hhwy.pm.xmsl.implement.domain.vo.ImplementVo;
import com.hhwy.pm.xmsl.implement.mapper.XmslTerrainLandformsMapper;
import com.hhwy.pm.xmsl.implement.service.IGeologicalConditionService;
import com.hhwy.pm.xmsl.implement.service.ILocalResourceSupplyService;
import com.hhwy.pm.xmsl.implement.service.IXmslBasicFacilitiesConditionsService;
import com.hhwy.pm.xmsl.implement.service.IXmslClimateConditionService;
import com.hhwy.pm.xmsl.implement.service.IXmslConstructionInterferenceService;
import com.hhwy.pm.xmsl.implement.service.IXmslExtendService;
import com.hhwy.pm.xmsl.implement.service.IXmslKeyPersonCommunicationService;
import com.hhwy.pm.xmsl.implement.service.IXmslMainStructureHydrologyService;
import com.hhwy.pm.xmsl.implement.service.IXmslTerrainLandformsService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:13
 * @remark 地形地貌
 */
@Service
public class XmslTerrainLandformsServiceImpl implements IXmslTerrainLandformsService {

    @Autowired
    private XmslTerrainLandformsMapper xmslTerrainLandformsMapper;
    @Autowired
    private IGeologicalConditionService geologicalConditionService;
    @Autowired
    private IXmslMainStructureHydrologyService mainStructureHydrologyService;
    @Autowired
    private IXmslClimateConditionService climateConditionService;
    @Autowired
    private IXmslBasicFacilitiesConditionsService basicFacilitiesConditionsService;
    @Autowired
    private IXmslConstructionInterferenceService constructionInterferenceService;
    @Autowired
    private ILocalResourceSupplyService localResourceSupplyService;
    @Autowired
    private IXmslExtendService extendService;
    @Autowired
    private IXmslKeyPersonCommunicationService keyPersonCommunicationService;

    public List<XmslTerrainLandforms> getXmslTerrainLandformsList(XmslTerrainLandforms xmslTerrainLandforms) {
        return xmslTerrainLandformsMapper.getXmslTerrainLandformsList(xmslTerrainLandforms);
    }

    @Transactional
    public void save(List<XmslTerrainLandforms> xmslTerrainLandformsList) {
        // 先清空旧数据
        XmslTerrainLandforms deleteParam = new XmslTerrainLandforms();
        deleteParam.setDelFlag("1");
        xmslTerrainLandformsMapper.updateXmslTerrainLandforms(deleteParam);

        if (!CollectionUtils.isEmpty(xmslTerrainLandformsList)) {
            for (XmslTerrainLandforms xmslTerrainLandforms : xmslTerrainLandformsList) {
                xmslTerrainLandforms.setId(IdWorker.createId());
                xmslTerrainLandforms.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslTerrainLandforms.setCreateUserName(SecurityUtils.getUserName());
                xmslTerrainLandforms.setCreateTime(DateUtils.getNowDate());
            }
            xmslTerrainLandformsMapper.insertXmslTerrainLandformsList(xmslTerrainLandformsList);
        }
    }

    @Transactional
    public int deleteXmslTerrainLandformsByPks(List<Long> xmslTerrainLandformsPkList) {
        return xmslTerrainLandformsMapper.deleteXmslTerrainLandformsByPks(xmslTerrainLandformsPkList);
    }

    public ImplementVo getAllList() {
        ImplementVo implementVo = new ImplementVo();

        implementVo.setTerrainLandformsList(this.getXmslTerrainLandformsList(new XmslTerrainLandforms()));
        implementVo.setGeologicalCondition(geologicalConditionService.getList(new GeologicalCondition()));
        implementVo.setMainStructureHydrologyList(
            mainStructureHydrologyService.getXmslMainStructureHydrologyList(new XmslMainStructureHydrology()));
        implementVo
            .setClimateConditionList(climateConditionService.getXmslClimateConditionList(new XmslClimateCondition()));
        implementVo.setBasicFacilitiesConditionsList(
            basicFacilitiesConditionsService.getXmslBasicFacilitiesConditionsList(new XmslBasicFacilitiesConditions()));
        implementVo.setConstructionInterferenceList(
            constructionInterferenceService.getXmslConstructionInterferenceList(new XmslConstructionInterference()));
        implementVo.setLocalResourceSupply(localResourceSupplyService.getList(new LocalResourceSupply()));
        implementVo.setXmslExtend(extendService.getXmslExtend(new XmslExtend()));
        implementVo.setKeyPersonCommunicationList(
            keyPersonCommunicationService.getXmslKeyPersonCommunicationList(new XmslKeyPersonCommunication()));
        return implementVo;
    }
}
