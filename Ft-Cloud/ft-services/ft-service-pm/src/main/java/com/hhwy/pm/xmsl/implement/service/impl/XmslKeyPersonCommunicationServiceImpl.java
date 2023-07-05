package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslKeyPersonCommunication;
import com.hhwy.pm.xmsl.implement.mapper.XmslKeyPersonCommunicationMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslKeyPersonCommunicationService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhenglili
 * @date 2023-07-03 13:13:27
 * @remark 重要干系人识别及沟通
 */
@Service
public class XmslKeyPersonCommunicationServiceImpl implements IXmslKeyPersonCommunicationService {

    @Autowired
    private XmslKeyPersonCommunicationMapper xmslKeyPersonCommunicationMapper;

    public List<XmslKeyPersonCommunication> getXmslKeyPersonCommunicationList(
        XmslKeyPersonCommunication xmslKeyPersonCommunication) {
        return xmslKeyPersonCommunicationMapper.getXmslKeyPersonCommunicationList(xmslKeyPersonCommunication);
    }

    @Transactional
    public void save(List<XmslKeyPersonCommunication> xmslKeyPersonCommunicationList) {
        if (xmslKeyPersonCommunicationList == null || xmslKeyPersonCommunicationList.size() == 0) {
            return;
        }

        List<XmslKeyPersonCommunication> insertList = new ArrayList<>();
        List<XmslKeyPersonCommunication> updateList = new ArrayList<>();
        for (XmslKeyPersonCommunication xmslKeyPersonCommunication : xmslKeyPersonCommunicationList) {
            xmslKeyPersonCommunication.setCreateUser(SecurityUtils.getUserName());
            if (xmslKeyPersonCommunication.getId() == null) {
                xmslKeyPersonCommunication.setId(IdWorker.createId());
                xmslKeyPersonCommunication.setCreateUser(SecurityUtils.getUserName());
                xmslKeyPersonCommunication.setCreateTime(DateUtils.getNowDate());
                insertList.add(xmslKeyPersonCommunication);
            } else {
                xmslKeyPersonCommunication.setUpdateUser(SecurityUtils.getUserName());
                xmslKeyPersonCommunication.setUpdateTime(DateUtils.getNowDate());
                updateList.add(xmslKeyPersonCommunication);
            }
        }

        if (insertList.size() > 0) {
            xmslKeyPersonCommunicationMapper.insertXmslKeyPersonCommunicationList(insertList);
        }
        if (updateList.size() > 0) {
            xmslKeyPersonCommunicationMapper.updateXmslKeyPersonCommunicationList(updateList);
        }
    }

    @Transactional
    public int deleteXmslKeyPersonCommunicationByPks(
        List<Long> xmslKeyPersonCommunicationPkList) {
        return xmslKeyPersonCommunicationMapper.deleteXmslKeyPersonCommunicationByPks(xmslKeyPersonCommunicationPkList);
    }
}
