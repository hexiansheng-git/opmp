package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslLocalEquipmentSupply;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:47
 * @remark 当地资源供应-属地设备供应情况
 */
public interface XmslLocalEquipmentSupplyMapper {

    XmslLocalEquipmentSupply getXmslLocalEquipmentSupply(
        XmslLocalEquipmentSupply xmslLocalEquipmentSupply);

    List<XmslLocalEquipmentSupply> getXmslLocalEquipmentSupplyList(
        XmslLocalEquipmentSupply xmslLocalEquipmentSupply);

    int insertXmslLocalEquipmentSupply(XmslLocalEquipmentSupply xmslLocalEquipmentSupply);

    int insertXmslLocalEquipmentSupplyList(
        @Param("xmslLocalEquipmentSupplyList") List<XmslLocalEquipmentSupply> xmslLocalEquipmentSupplyList);

    int updateXmslLocalEquipmentSupply(XmslLocalEquipmentSupply xmslLocalEquipmentSupply);

    int updateXmslLocalEquipmentSupplyList(@Param("list") List<XmslLocalEquipmentSupply> xmslLocalEquipmentSupplyList);

    int deleteXmslLocalEquipmentSupply(XmslLocalEquipmentSupply xmslLocalEquipmentSupply);

    int deleteXmslLocalEquipmentSupplyByPks(
        @Param("xmslLocalEquipmentSupplyPkList") List<Long> xmslLocalEquipmentSupplyPkList);
}
