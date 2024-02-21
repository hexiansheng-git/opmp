package com.hhwy.pm.qqch.sgch.sche.service.impl;

import com.hhwy.pm.qqch.common.aspect.CompileAspect;
import com.hhwy.pm.qqch.common.aspect.CompileOptEnum;
import com.hhwy.pm.qqch.common.domain.CompileEntity;
import com.hhwy.pm.qqch.sgch.sche.domain.*;
import com.hhwy.pm.qqch.sgch.sche.dto.QqchScheDTO;
import com.hhwy.pm.qqch.sgch.sche.service.*;
import com.hhwy.pm.qqch.utils.VersionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @CompileAspect(type = CompileOptEnum.LIST, tableName = "qqch_sche_diff")
    public QqchScheDTO list(QqchScheDTO dto) {
        QqchScheDTO qqchScheDTO = new QqchScheDTO();
        // 说明
        qqchScheDTO.setDiffDesc(diffDescService.getDesc(CompileEntity.dealListDto(dto.getVersion(), dto.getDiffDesc())));
        // 差异化计算方法
        qqchScheDTO.setDiffList(diffService.getList(CompileEntity.dealListDto(dto.getVersion(), new QqchScheDiff())));
        // 进度分析要素
        qqchScheDTO.setAnalyseList(analyseService.getList(CompileEntity.dealListDto(dto.getVersion(), new QqchScheAnalyse())));
        // 进度影响要素
        qqchScheDTO.setScheFactorsVO(factorsService.getList(CompileEntity.dealListDto(dto.getVersion(), new QqchScheFactors())));
        // 纠偏措施
        qqchScheDTO.setCorrList(corrService.getList(CompileEntity.dealListDto(dto.getVersion(), new QqchScheCorr())));
        qqchScheDTO.setVersion(new BigDecimal("1.0"));
        qqchScheDTO.setStageIdentity("1");
        qqchScheDTO.setFactorsVOList(new ArrayList<>(2));
        return qqchScheDTO;

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(QqchScheDTO dto) {
        // 保存说明
        diffDescService.save(CompileEntity.dealSaveDtoWithoutSubmitFlag(dto, dto.getDiffDesc()));
        // 保差异化计算方法
        diffService.saveList(CompileEntity.dealSaveDtoWithoutSubmitFlag(dto, dto.getDiffList()));
        // 保存进度分析要素
        analyseService.saveList(CompileEntity.dealSaveDtoWithoutSubmitFlag(dto, dto.getAnalyseList()));
        // 保存进度影响要素
        List<List<QqchScheFactors>> factorsVOList = dto.getScheFactorsVO() != null ? dto.getScheFactorsVO().getFactorsVOList() : new ArrayList<>();

        List<QqchScheFactors> iFactorList = new ArrayList<>();

        // 每行数据加上行号 不然前端不好回显数据
        if (!CollectionUtils.isEmpty(factorsVOList)) {
            int rowNum = 1;
            for (List<QqchScheFactors> qqchScheFactors : factorsVOList) {
                for (QqchScheFactors qqchScheFactor : qqchScheFactors) {
                    qqchScheFactor.setRowNum(BigDecimal.valueOf(rowNum));
                    iFactorList.add(qqchScheFactor);
                }
                rowNum++;
            }

            factorsService.saveList(CompileEntity.dealSaveDtoWithoutSubmitFlag(dto,iFactorList));
        } else {
            factorsService.saveList(CompileEntity.dealSaveDtoWithoutSubmitFlag(dto,iFactorList));
        }
        // 保存纠偏措施
        corrService.saveList(CompileEntity.dealSaveDto(dto,dto.getCorrList()));
    }

    @Override
    public QqchScheDTO getItems() {
        QqchScheDTO qqchScheDTO = new QqchScheDTO();

        BigDecimal version = VersionUtil.getMaxVersion("qqch_sche_diff_desc");

        // 说明
        QqchScheDiffDesc qqchScheDiffDesc = new QqchScheDiffDesc();
        qqchScheDiffDesc.setVersion(version);
        qqchScheDTO.setDiffDesc(diffDescService.getQqchScheDiffDesc(qqchScheDiffDesc));
        // 差异化计算方法
        QqchScheDiff qqchScheDiff = new QqchScheDiff();
        qqchScheDiff.setVersion(version);
        qqchScheDTO.setDiffList(diffService.getQqchScheDiffList(qqchScheDiff));
        // 进度分析要素
        QqchScheAnalyse qqchScheAnalyse = new QqchScheAnalyse();
        qqchScheAnalyse.setVersion(version);
        qqchScheDTO.setAnalyseList(analyseService.getQqchScheAnalyseList(qqchScheAnalyse));
        // 进度影响要素
        QqchScheFactors qqchScheFactors = new QqchScheFactors();
        qqchScheFactors.setVersion(version);
        qqchScheDTO.setScheFactorsVO(factorsService.getList4jd(qqchScheFactors));
        // 纠偏措施
//            qqchScheDTO.setCorrList(corrService.getList(CompileEntity.dealListDto(dto.getVersion(), new QqchScheCorr())));
        qqchScheDTO.setVersion(new BigDecimal("1.0"));
        qqchScheDTO.setStageIdentity("1");
        return qqchScheDTO;
    }
}
