package com.hhwy.pm.xmsl.implement.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hhwy.pm.xmsl.implement.domain.XmslLocalWorkerSupply;

/**
 * @author zhenglili
 * @date 2023-07-03 12:52:57
 * @remark 属地工人供应情况
 */
public interface XmslLocalWorkerSupplyMapper {

    XmslLocalWorkerSupply getXmslLocalWorkerSupply(XmslLocalWorkerSupply xmslLocalWorkerSupply);

    List<XmslLocalWorkerSupply> getXmslLocalWorkerSupplyList(
        XmslLocalWorkerSupply xmslLocalWorkerSupply);

    int insertXmslLocalWorkerSupply(XmslLocalWorkerSupply xmslLocalWorkerSupply);

    int insertXmslLocalWorkerSupplyList(
        @Param("xmslLocalWorkerSupplyList") List<XmslLocalWorkerSupply> xmslLocalWorkerSupplyList);

    int updateXmslLocalWorkerSupply(XmslLocalWorkerSupply xmslLocalWorkerSupply);

    int updateXmslLocalWorkerSupplyList(@Param("list") List<XmslLocalWorkerSupply> xmslLocalWorkerSupplyList);

    int deleteXmslLocalWorkerSupply(XmslLocalWorkerSupply xmslLocalWorkerSupply);

    int deleteXmslLocalWorkerSupplyByPks(
        @Param("xmslLocalWorkerSupplyPkList") List<Long> xmslLocalWorkerSupplyPkList);
}
