package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslLocalMaterialsSupply;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:53
 * @remark 当地资源供应-属地物资供应情况
 */
public interface XmslLocalMaterialsSupplyMapper {

    XmslLocalMaterialsSupply getXmslLocalMaterialsSupply(
        XmslLocalMaterialsSupply xmslLocalMaterialsSupply);

    List<XmslLocalMaterialsSupply> getXmslLocalMaterialsSupplyList(
        XmslLocalMaterialsSupply xmslLocalMaterialsSupply);

    int insertXmslLocalMaterialsSupply(XmslLocalMaterialsSupply xmslLocalMaterialsSupply);

    int insertXmslLocalMaterialsSupplyList(
        @Param("xmslLocalMaterialsSupplyList") List<XmslLocalMaterialsSupply> xmslLocalMaterialsSupplyList);

    int updateXmslLocalMaterialsSupply(XmslLocalMaterialsSupply xmslLocalMaterialsSupply);

    int updateXmslLocalMaterialsSupplyList(
        @Param("xmslLocalMaterialsSupplyList") List<XmslLocalMaterialsSupply> xmslLocalMaterialsSupplyList);

    int deleteXmslLocalMaterialsSupply(XmslLocalMaterialsSupply xmslLocalMaterialsSupply);

    int deleteXmslLocalMaterialsSupplyByPks(
        @Param("xmslLocalMaterialsSupplyPkList") List<Long> xmslLocalMaterialsSupplyPkList);
}
