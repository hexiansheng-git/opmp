package com.hhwy.pm.xmsl.implement.domain;

import java.util.List;
import lombok.Data;

/**
 * @author zhenglili
 * @date 2023-07-04 13:15:00
 * @remark 当地资源供应
 */
@Data
public class LocalResourceSupply {

    /**
     * 当地工人供应情况集合
     */
    private List<XmslLocalWorkerSupply> localWorkerSupplyList;

    /**
     * 当地物资供应情况集合
     */
    private List<XmslLocalMaterialsSupply> localMaterialsSupplyList;

    /**
     * 当地设备供应情况集合
     */
    private List<XmslLocalEquipmentSupply> localEquipmentSupplyList;
}
