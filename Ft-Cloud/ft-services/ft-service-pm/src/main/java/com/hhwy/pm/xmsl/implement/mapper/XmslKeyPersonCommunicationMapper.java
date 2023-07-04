package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslKeyPersonCommunication;

/**
 * @author zhenglili
 * @date 2023-07-03 13:13:27
 * @remark 重要干系人识别及沟通
 */
public interface XmslKeyPersonCommunicationMapper {

    XmslKeyPersonCommunication getXmslKeyPersonCommunication(
        XmslKeyPersonCommunication xmslKeyPersonCommunication);

    List<XmslKeyPersonCommunication> getXmslKeyPersonCommunicationList(
        XmslKeyPersonCommunication xmslKeyPersonCommunication);

    int insertXmslKeyPersonCommunication(XmslKeyPersonCommunication xmslKeyPersonCommunication);

    int insertXmslKeyPersonCommunicationList(
        @Param("xmslKeyPersonCommunicationList") List<XmslKeyPersonCommunication> xmslKeyPersonCommunicationList);

    int updateXmslKeyPersonCommunication(XmslKeyPersonCommunication xmslKeyPersonCommunication);

    int updateXmslKeyPersonCommunicationList(@Param("list") List<XmslKeyPersonCommunication> xmslKeyPersonCommunicationList);

    int deleteXmslKeyPersonCommunication(XmslKeyPersonCommunication xmslKeyPersonCommunication);

    int deleteXmslKeyPersonCommunicationByPks(
        @Param("xmslKeyPersonCommunicationPkList") List<Long> xmslKeyPersonCommunicationPkList);
}
