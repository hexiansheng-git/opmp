package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslMainStructureHydrology;
import com.hhwy.pm.xmsl.implement.mapper.XmslMainStructureHydrologyMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslMainStructureHydrologyService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author zhenglili
 * @date 2023-07-03 12:53:02
 * @remark 主要构造物水文条件
 */
@Service
public class XmslMainStructureHydrologyServiceImpl implements IXmslMainStructureHydrologyService {

    @Autowired
    private XmslMainStructureHydrologyMapper xmslMainStructureHydrologyMapper;

    public List<XmslMainStructureHydrology> getXmslMainStructureHydrologyList(
        XmslMainStructureHydrology xmslMainStructureHydrology) {
        return xmslMainStructureHydrologyMapper.getXmslMainStructureHydrologyList(xmslMainStructureHydrology);
    }

    @Transactional
    public void save(List<XmslMainStructureHydrology> xmslMainStructureHydrologyList) {
        // 先清空旧数据
        XmslMainStructureHydrology deleteParam = new XmslMainStructureHydrology();
        deleteParam.setDelFlag("1");
        xmslMainStructureHydrologyMapper.updateXmslMainStructureHydrology(deleteParam);

        if (!CollectionUtils.isEmpty(xmslMainStructureHydrologyList)) {
            for (XmslMainStructureHydrology xmslMainStructureHydrology : xmslMainStructureHydrologyList) {
                xmslMainStructureHydrology.setId(IdWorker.createId());
                xmslMainStructureHydrology.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslMainStructureHydrology.setCreateUserName(SecurityUtils.getUserName());
                xmslMainStructureHydrology.setCreateTime(DateUtils.getNowDate());
            }
            xmslMainStructureHydrologyMapper.insertXmslMainStructureHydrologyList(xmslMainStructureHydrologyList);
        }
    }

    @Transactional
    public int deleteXmslMainStructureHydrologyByPks(List<Long> xmslMainStructureHydrologyPkList) {
        return xmslMainStructureHydrologyMapper.deleteXmslMainStructureHydrologyByPks(xmslMainStructureHydrologyPkList);
    }
}
