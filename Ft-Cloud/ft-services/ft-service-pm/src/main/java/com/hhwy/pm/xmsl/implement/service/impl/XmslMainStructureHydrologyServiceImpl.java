package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslMainStructureHydrology;
import com.hhwy.pm.xmsl.implement.mapper.XmslMainStructureHydrologyMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslMainStructureHydrologyService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (xmslMainStructureHydrologyList == null || xmslMainStructureHydrologyList.size() == 0) {
            return;
        }
        List<XmslMainStructureHydrology> insertList = new ArrayList<>();
        List<XmslMainStructureHydrology> updateList = new ArrayList<>();
        for (XmslMainStructureHydrology xmslMainStructureHydrology : xmslMainStructureHydrologyList) {
            if (xmslMainStructureHydrology.getId() == null) {
                xmslMainStructureHydrology.setId(IdWorker.createId());
                xmslMainStructureHydrology.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslMainStructureHydrology.setCreateUserName(SecurityUtils.getUserName());
                xmslMainStructureHydrology.setCreateTime(DateUtils.getNowDate());
                insertList.add(xmslMainStructureHydrology);
            } else {
                xmslMainStructureHydrology.setUpdateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslMainStructureHydrology.setUpdateTime(DateUtils.getNowDate());
                updateList.add(xmslMainStructureHydrology);
            }
        }

        if (insertList.size() > 0) {
            xmslMainStructureHydrologyMapper.insertXmslMainStructureHydrologyList(insertList);
        }
        if (updateList.size() > 0) {
            xmslMainStructureHydrologyMapper.updateXmslMainStructureHydrologyList(updateList);
        }
    }

    @Transactional
    public int deleteXmslMainStructureHydrologyByPks(List<Long> xmslMainStructureHydrologyPkList) {
        return xmslMainStructureHydrologyMapper.deleteXmslMainStructureHydrologyByPks(xmslMainStructureHydrologyPkList);
    }
}
