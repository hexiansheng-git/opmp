package com.hhwy.pm.xmsl.implement.service.impl;

import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.pm.xmsl.implement.domain.XmslKeyPersonCommunication;
import com.hhwy.pm.xmsl.implement.mapper.XmslKeyPersonCommunicationMapper;
import com.hhwy.pm.xmsl.implement.service.IXmslKeyPersonCommunicationService;
import com.hhwy.utils.idworker.IdWorker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        // 先清空旧数据
        XmslKeyPersonCommunication deleteParam = new XmslKeyPersonCommunication();
        deleteParam.setDelFlag("1");
        xmslKeyPersonCommunicationMapper.updateXmslKeyPersonCommunication(deleteParam);

        if (!CollectionUtils.isEmpty(xmslKeyPersonCommunicationList)) {
            for (XmslKeyPersonCommunication xmslKeyPersonCommunication : xmslKeyPersonCommunicationList) {
                xmslKeyPersonCommunication.setId(IdWorker.createId());
                xmslKeyPersonCommunication.setCreateUser(String.valueOf(SecurityUtils.getUserId()));
                xmslKeyPersonCommunication.setCreateUserName(SecurityUtils.getUserName());
                xmslKeyPersonCommunication.setCreateTime(DateUtils.getNowDate());
            }
            xmslKeyPersonCommunicationMapper.insertXmslKeyPersonCommunicationList(xmslKeyPersonCommunicationList);
        }
    }

    @Transactional
    public int deleteXmslKeyPersonCommunicationByPks(
        List<Long> xmslKeyPersonCommunicationPkList) {
        return xmslKeyPersonCommunicationMapper.deleteXmslKeyPersonCommunicationByPks(xmslKeyPersonCommunicationPkList);
    }
}
