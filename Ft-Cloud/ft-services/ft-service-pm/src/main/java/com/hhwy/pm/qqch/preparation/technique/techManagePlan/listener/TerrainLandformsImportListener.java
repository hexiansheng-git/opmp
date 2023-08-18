package com.hhwy.pm.qqch.preparation.technique.techManagePlan.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hhwy.pm.xmsl.implement.domain.XmslTerrainLandforms;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * @author : zhenglili
 * @date : 2023/8/18
 */
@Data
public class TerrainLandformsImportListener extends AnalysisEventListener<XmslTerrainLandforms> {

    //表头数据（存储所有的表头数据）
    private List<Map<Integer, String>> headList = new ArrayList<>();
    //数据体
    private List<Map<Integer, String>> dataList = new ArrayList<>();

    private List<XmslTerrainLandforms> list = new ArrayList<>();

    @Override
    public void invoke(XmslTerrainLandforms data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}
