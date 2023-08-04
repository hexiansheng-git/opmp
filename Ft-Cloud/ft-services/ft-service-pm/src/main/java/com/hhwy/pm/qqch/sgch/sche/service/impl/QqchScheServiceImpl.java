package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.pm.qqch.common.domain.CompileDTO;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheAnalyse;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheCorr;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheDiff;
import com.hhwy.pm.qqch.sgch.sche.domain.QqchScheFactors;
import com.hhwy.pm.qqch.sgch.sche.dto.QqchScheDTO;
import com.hhwy.pm.qqch.sgch.sche.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 进度差异化管控策划Service
 *
 * @author m
 */
@Service
public class QqchScheServiceImpl implements IQqchScheService {

    @Resource
    private IQqchScheDiffDescService diffDescService;
    @Resource
    private IQqchScheDiffService diffService;
    @Resource
    private IQqchScheAnalyseService analyseService;
    @Resource
    private IQqchScheFactorsService factorsService;
    @Resource
    private IQqchScheCorrService corrService;

    @Override
    public QqchScheDTO list(QqchScheDTO dto) {
        QqchScheDTO qqchScheDTO = new QqchScheDTO();
        // 说明
        qqchScheDTO.setDiffDesc(diffDescService.getDesc(CompileDTO.dealListDto(dto.getVersion(), dto.getDiffDesc())));
        // 差异化计算方法
        qqchScheDTO.setDiffList(diffService.getList(CompileDTO.dealListDto(dto.getVersion(), new QqchScheDiff())));
        // 进度分析要素
        qqchScheDTO.setAnalyseList(analyseService.getList(CompileDTO.dealListDto(dto.getVersion(), new QqchScheAnalyse())));
        // 进度影响要素
        qqchScheDTO.setScheFactorsVO(factorsService.getList(CompileDTO.dealListDto(dto.getVersion(), new QqchScheFactors())));
        // 纠偏措施
        qqchScheDTO.setCorrList(corrService.getList(CompileDTO.dealListDto(dto.getVersion(), new QqchScheCorr())));
        return qqchScheDTO;

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(QqchScheDTO dto) {
        // 保存说明
        diffDescService.save(CompileDTO.dealSaveDto(dto.getVersion(), dto.getSubmitFlag(), dto.getDiffDesc()));
        // 保差异化计算方法
        diffService.saveList(CompileDTO.dealSaveDto(dto.getVersion(), dto.getSubmitFlag(), dto.getDiffList()));
        // 保存进度分析要素
        analyseService.saveList(CompileDTO.dealSaveDto(dto.getVersion(), dto.getSubmitFlag(), dto.getAnalyseList()));
        // 保存进度影响要素
        factorsService.saveList(CompileDTO.dealSaveDto(dto.getVersion(), dto.getSubmitFlag(), dto.getFactorsList()));
        // 保存纠偏措施
        corrService.saveList(CompileDTO.dealSaveDto(dto.getVersion(), dto.getSubmitFlag(), dto.getCorrList()));
    }
}
