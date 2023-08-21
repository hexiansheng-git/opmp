package com.hhwy.pm.qqch.preparation.technique.techManagePlan.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hhwy.pm.xmsl.implement.domain.XmslClimateCondition;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @author : zhenglili
 * @date : 2023/8/21
 */
@Data
public class ClimateConditionListener extends AnalysisEventListener<XmslClimateCondition> {

    private List<XmslClimateCondition> list = new ArrayList<>();

    @Override
    public void invoke(XmslClimateCondition data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}
