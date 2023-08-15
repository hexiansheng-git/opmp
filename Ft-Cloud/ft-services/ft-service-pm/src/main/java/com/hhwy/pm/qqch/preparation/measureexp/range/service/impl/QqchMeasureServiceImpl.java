package com.hhwy.pm.qqch.preparation.measureexp.range.service.impl;

import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureExpRange;
import com.hhwy.pm.qqch.preparation.measureexp.range.domain.QqchMeasureOrg;
import com.hhwy.pm.qqch.preparation.measureexp.range.dto.QqchMeasureExpDTO;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpPersonService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureExpRangeService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureOrgService;
import com.hhwy.pm.qqch.preparation.measureexp.range.service.IQqchMeasureService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service
public class QqchMeasureServiceImpl implements IQqchMeasureService {


    @Resource
    private IQqchMeasureOrgService orgService;

    @Resource
    private IQqchMeasureExpRangeService measureExpRangeService;

    @Resource
    private IQqchMeasureExpPersonService personService;
    @Override
    public void saveAll(QqchMeasureExpDTO expVO) {
        List<QqchMeasureExpRange> qqchMeasureExpRanges = CompileEntity.dealSaveDto(expVO.getVersion(), expVO.getSubmitFlag(), expVO.getExpRangeList());
        measureExpRangeService.saveTreeList(qqchMeasureExpRanges);
        personService.saveList(CompileEntity.dealSaveDto(expVO.getVersion(), expVO.getSubmitFlag(), expVO.getPersonList()));
        QqchMeasureOrg org = expVO.getOrg();
        orgService.save(CompileEntity.dealSaveDto(expVO.getVersion(), expVO.getSubmitFlag(),org));
        
    }
}
