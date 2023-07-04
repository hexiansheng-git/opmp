package com.hhwy.pm.xmsl.implement.service;

import java.util.List;
import com.hhwy.pm.xmsl.implement.domain.XmslKeyPersonCommunication;

/**
 * @author zhenglili
 * @date 2023-07-03 13:13:27
 * @remark 重要干系人识别及沟通
 */
public interface IXmslKeyPersonCommunicationService {

    List<XmslKeyPersonCommunication> getXmslKeyPersonCommunicationList(
        XmslKeyPersonCommunication xmslKeyPersonCommunication);

    void save(List<XmslKeyPersonCommunication> xmslKeyPersonCommunicationList);

    int deleteXmslKeyPersonCommunicationByPks(List<Long> xmslKeyPersonCommunicationPkList);
}
